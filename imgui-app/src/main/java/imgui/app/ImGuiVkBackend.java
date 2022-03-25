package imgui.app;

import imgui.ImDrawData;
import imgui.ImGui;
import imgui.app.vk.*;
import imgui.lwjgl3.vk.callback.ImGuiVkCheckResultCallback;
import imgui.vk.ImGuiImplVk;
import imgui.vk.ImGuiImplVkInitInfo;
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

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.EXTDebugUtils.*;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK12.VK_API_VERSION_1_2;

public class ImGuiVkBackend implements Backend {

    //GLFW Window
    private long window = NULL;
    private boolean resizeFlag = false;

    //Vulkan Objects
    private long surface = VK_NULL_HANDLE;
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
    private final ImGuiImplVkInitInfo imguiVkInit = new ImGuiImplVkInitInfo();

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

    //Render vars
    private Color clearColor;
    private boolean transitionFonts = true;

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
        this.clearColor = clearColor;

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

        //Init imgui vulkan
        imguiVkInit.setCheckVkResultFn(new ImGuiVkCheckResultCallback() {
            @Override
            public void callback(int resultCode) {
                vkOK(resultCode);
            }
        });
        imguiVkInit.setInstance(instance.getInstance());
        imguiVkInit.setAllocator(instance.getAllocationCallbacks());
        imguiVkInit.setPhysicalDevice(physicalDevice.getPhysicalDevice());
        imguiVkInit.setDevice(device.getDevice());
        imguiVkInit.setMinImageCount(swapchain.getImageViews().size());
        imguiVkInit.setImageCount(swapchain.getImageViews().size());
        imguiVkInit.setDescriptorPool(descriptorPool.getNativeHandle());
        imguiVkInit.setPipelineCache(pipelineCache.getNativeHandle());
        imguiVkInit.setQueue(graphicsQueue.getQueue());
        imguiVkInit.setQueueFamily(physicalDevice.getIndices().getGraphicsFamily());
        imguiVkInit.setMSAASamples(VK_SAMPLE_COUNT_1_BIT);
        imguiVkInit.setSubpass(0);
        ImGuiImplVk.init(imguiVkInit, renderPass.getNativeHandle());


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
    public void resize(long windowHandle, int width, int height) {
        resizeFlag = true;
    }

    public void resize() {
        //=== Wait for gpu to be ready
        device.waitIdle();
        graphicsQueue.waitIdle();
        presentationQueue.waitIdle();

        //=== Unload things with old size
        //Unload frame buffers
        frameBuffers.forEach(ImVkFrameBuffer::destroy);
        frameBuffers.clear();

        //Unload depth buffers
        depthBuffers.forEach(ImVkAttachment::destroy);
        depthBuffers.clear();

        //Unload swap chain
        swapchain.destroy();

        //=== Load with new size
        //Update swapchain device support
        physicalDevice.resize();

        //Create swap chain
        swapchain.setGraphicsQueue(graphicsQueue);
        swapchain.setSurface(surface);
        swapchain.create();

        //Create depth buffers
        createDepthBuffers();

        //Create frame buffer
        createFrameBuffers();

        //Done
        resizeFlag = false;
    }

    @Override
    public void begin() {
        //Handle if we have been reized
        if (resizeFlag || swapchain.nextImage()) {
            resize();
            swapchain.nextImage();
        }

        //Create new imgui vulkan frame
        ImGuiImplVk.newFrame();

        //Begin the render pass for the frame
        beginRenderPass();
    }

    @Override
    public void end() {
        //Get draw calls from imgui
        ImDrawData drawData = ImGui.getDrawData();
        if (drawData.getDisplaySizeX() > 0.0f && drawData.getDisplaySizeY() > 0.0f) {
            int currentFrame = swapchain.getCurrentFrame();
            ImVkCommandBuffer commandBuffer = commandBuffers.get(currentFrame);
            ImGuiImplVk.renderDrawData(drawData, commandBuffer.getCommandBuffer(), VK_NULL_HANDLE);
        }

        //Complete render pass
        endRenderPass();

        //Submit command buffer to GPU for complete render of the scene
        submit(presentationQueue);

        //Check if fonts were transitioned, if so allow imgui to delete temp objects
        if (transitionFonts) {
            ImGuiImplVk.destroyFontUploadObjects();
            transitionFonts = false;
        }

        //Present new frame
        if (swapchain.presentImage()) {
            resizeFlag = true;
        }
    }

    public void submit(ImVkQueue queue) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int currentFrame = swapchain.getCurrentFrame();
            ImVkCommandBuffer commandBuffer = commandBuffers.get(currentFrame);
            ImVkFence currentFence = fences.get(currentFrame);
            queue.submit(
                stack.pointers(commandBuffer.getCommandBuffer()),
                stack.longs(swapchain.getImageAvailableSemaphores()[currentFrame].getNativeHandle()),
                stack.ints(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT),
                stack.longs(swapchain.getRenderFinishedSemaphores()[currentFrame].getNativeHandle()),
                currentFence
            );
        }
    }

    private void beginRenderPass() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkExtent2D swapChainExtent = swapchain.getExtent();
            int width = swapChainExtent.width();
            int height = swapChainExtent.height();
            int currentFrame = swapchain.getCurrentFrame();

            //Start render pass
            ImVkFence fence = fences.get(currentFrame);
            ImVkCommandBuffer commandBuffer = commandBuffers.get(currentFrame);
            ImVkFrameBuffer frameBuffer = frameBuffers.get(currentFrame);

            fence.waitFor();
            fence.reset();

            commandBuffer.reset();
            VkClearValue.Buffer clearValues = VkClearValue.calloc(2, stack);
            clearValues.apply(0, v -> v.color().float32(0, clearColor.getRed()).float32(1, clearColor.getGreen()).float32(2, clearColor.getBlue()).float32(3, clearColor.getAlpha()));
            clearValues.apply(1, v -> v.depthStencil().depth(1.0f));

            VkRenderPassBeginInfo renderPassBeginInfo = VkRenderPassBeginInfo.calloc(stack)
                .sType$Default()
                .renderPass(renderPass.getNativeHandle())
                .pClearValues(clearValues)
                .renderArea(a -> a.extent().set(width, height))
                .framebuffer(frameBuffer.getNativeHandle());

            commandBuffer.begin();

            //Check if we need to perform the one time upload of fonts to the GPU
            if (transitionFonts) {
                ImGuiImplVk.createFontsTexture(commandBuffer.getCommandBuffer());
            }

            //Start render pass
            vkCmdBeginRenderPass(commandBuffer.getCommandBuffer(), renderPassBeginInfo, VK_SUBPASS_CONTENTS_INLINE);
        }
    }

    private void endRenderPass() {
        ImVkCommandBuffer commandBuffer = commandBuffers.get(swapchain.getCurrentFrame());
        vkCmdEndRenderPass(commandBuffer.getCommandBuffer());
        commandBuffer.end();
    }

    @Override
    public void destroy() {
        //Destroy imgui vulkan backend
        ImGuiImplVk.shutdown();

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

    private void destroySurface() {
        vkDestroySurfaceKHR(instance.getInstance(), surface, null);
    }
}
