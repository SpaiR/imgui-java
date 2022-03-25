package imgui.app;

import imgui.app.vk.*;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.EXTDebugUtils.*;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK12.VK_API_VERSION_1_2;

public class ImGuiVkBackend implements Backend {

    //GLFW Window handle
    private long window = NULL;

    //Surface info
    private long surface = VK_NULL_HANDLE;

    //Vulkan Objects
    private final ImVkInstance instance = new ImVkInstance();
    private final ImVkPhysicalDevice physicalDevice = new ImVkPhysicalDevice();
    private final ImVkDevice device = new ImVkDevice();
    private final ImVkPipelineCache pipelineCache = new ImVkPipelineCache();
    private final ImVkQueue graphicsQueue = new ImVkQueue();
    private final ImVkQueue presentationQueue = new ImVkQueue();
    private final ImVkSwapchain swapchain = new ImVkSwapchain();
    private final ImVkRenderPass renderPass = new ImVkRenderPass();
    private final ImVkCommandPool commandPool = new ImVkCommandPool();
    private final ImVkDescriptorPool descriptorPool = new ImVkDescriptorPool();
    private final List<ImVkFence> fences = new ArrayList<>();

    //Buffers
    private final List<ImVkFrameBuffer> frameBuffers = new ArrayList<>();
    private final List<ImVkAttachment> depthBuffers = new ArrayList<>();
    private final List<ImVkCommandBuffer> commandBuffers = new ArrayList<>();

    //Logger
    private final Logger LOGGER = Logger.getLogger(ImGuiGlBackend.class.getName());

    //Engine Info
    private final String ENGINE_NAME = "imgui-app";
    private final int[] ENGINE_VERSION = {1, 86, 3}; //FIXME: We should set this automatically to the correct build version
    private final boolean VALIDATION_ENABLED = false;

    public ImGuiVkBackend() {

    }

    @Override
    public void preCreateWindow() {
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
    }

    @Override
    public void postCreateWindow(long windowHandle) {
        this.window = windowHandle;
    }

    @Override
    public void init(Color clearColor) {
        //Create instance
        instance.setEngineName(ENGINE_NAME);
        instance.setEngineVersion(ENGINE_VERSION[0], ENGINE_VERSION[1], ENGINE_VERSION[2]);
        instance.setValidationEnabled(VALIDATION_ENABLED);
        instance.getExtensions().addAll(Arrays.asList(getVulkanRequiredExtensions()));
        instance.create();

        //Create surface
        createSurface();

        //Create physical device
        physicalDevice.setInstance(instance);
        physicalDevice.setSurface(surface);
        physicalDevice.create();

        //Create logical device
        device.setPhysicalDevice(physicalDevice);
        device.create();

        //Create pipeline cache
        pipelineCache.setDevice(device);
        pipelineCache.create();

        //Create graphics queue
        graphicsQueue.setFamilyIndex(physicalDevice.getIndices().getGraphicsFamily());
        graphicsQueue.setDevice(device);
        graphicsQueue.create();

        //Create presentation queue
        presentationQueue.setFamilyIndex(physicalDevice.getIndices().getPresentFamily());
        presentationQueue.setDevice(device);
        presentationQueue.create();

        //Create swapchain
        swapchain.setSurface(surface);
        swapchain.setVsync(true);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1);
            final IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
            swapchain.setWidth(pWidth.get(0));
            swapchain.setHeight(pHeight.get(0));
        }
        swapchain.setGraphicsQueue(graphicsQueue);
        swapchain.create();

        //Create render pass
        renderPass.setSwapchain(swapchain);
        renderPass.create();

        //Create command pool
        commandPool.setDevice(device);
        commandPool.create();

        //Create buffers
        createDepthBuffers();
        createFrameBuffers();
        createFencesAndCommandBuffers();

        //Create descriptor pool
        descriptorPool.setDevice(device);
        descriptorPool.create();
    }


    private void createSurface() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer longBuff = stack.callocLong(1);
            if (GLFWVulkan.glfwCreateWindowSurface(instance.getInstance(), window, null, longBuff) == VK10.VK_SUCCESS) {
                surface = longBuff.get();
            } else {
                throw new RuntimeException("Failed to create glfw vulkan surface");
            }
        }
    }

    private void createDepthBuffers() {
        int numImages = swapchain.getImageViews().size();
        for (int i = 0; i < numImages; i++) {
            ImVkAttachment attachment = new ImVkAttachment();
            attachment.setFormat(VK_FORMAT_D32_SFLOAT);
            attachment.setUsage(VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT);
            attachment.setWidth(swapchain.getExtent().width());
            attachment.setHeight(swapchain.getExtent().height());
            attachment.setDevice(device);
            attachment.create();
            depthBuffers.add(attachment);
        }
    }

    private void createFrameBuffers() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer attachments = stack.callocLong(2);
            for (int i = 0; i < swapchain.getImageViews().size(); i++) {
                attachments.put(0, swapchain.getImageViews().get(i).getNativeHandle());
                attachments.put(1, depthBuffers.get(i).getImageView().getNativeHandle());
                ImVkFrameBuffer frameBuffer = new ImVkFrameBuffer();
                frameBuffers.add(frameBuffer);
                frameBuffer.setAttachments(attachments);
                frameBuffer.setRenderPass(renderPass);
                frameBuffer.create();
            }
        }
    }

    private void createFencesAndCommandBuffers() {
        for (int i = 0; i < swapchain.getImageViews().size(); i++) {
            //Create Fences
            ImVkFence fence = new ImVkFence();
            fences.add(fence);
            fence.setSignaled(true);
            fence.setDevice(device);
            fence.create();

            //Create Command Buffers
            ImVkCommandBuffer commandBuffer = new ImVkCommandBuffer();
            commandBuffers.add(commandBuffer);
            commandBuffer.setCommandPool(commandPool);
            commandBuffer.setPrimary(true);
            commandBuffer.setOneTimeSubmit(false);
            commandBuffer.create();
        }
    }

    private String[] getVulkanRequiredExtensions() {
        PointerBuffer glfwExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions();
        String[] extensions = new String[glfwExtensions.capacity()];
        for (int i = 0; i < extensions.length; i++) {
            extensions[i] = glfwExtensions.getStringASCII(i);
        }
        return extensions;
    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {

    }

    @Override
    public void destroy() {
        //Wait for GPU to be ready
        device.waitIdle();
        presentationQueue.waitIdle();
        graphicsQueue.waitIdle();

        //Destroy vulkan
        commandBuffers.forEach(ImVkCommandBuffer::destroy);
        commandBuffers.clear();

        fences.forEach(ImVkFence::destroy);
        fences.clear();

        commandPool.destroy();

        frameBuffers.forEach(ImVkFrameBuffer::destroy);
        frameBuffers.clear();

        depthBuffers.forEach(ImVkAttachment::destroy);
        depthBuffers.clear();

        swapchain.destroy();
        presentationQueue.destroy();
        graphicsQueue.destroy();
        pipelineCache.destroy();
        device.destroy();
        physicalDevice.destroy();
        destroySurface();
        instance.destroy();
    }

    @Override
    public void resize(long windowHandle, int width, int height) {

    }

    private void destroySurface() {
        vkDestroySurfaceKHR(instance.getInstance(), surface, null);
    }
}
