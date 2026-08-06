package imgui.app;

import imgui.ImGui;
import imgui.glfw.ImGuiImplGlfw;
import imgui.vulkan.ImGuiImplVulkan;
import imgui.vulkan.ImGuiImplVulkanH;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkClearValue;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkFenceCreateInfo;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkRenderPassBeginInfo;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;
import org.lwjgl.vulkan.VkSubmitInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.vulkan.KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_PRESENT_MODE_FIFO_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_PRESENT_MODE_IMMEDIATE_KHR;
import static org.lwjgl.vulkan.KHRSurface.VK_PRESENT_MODE_MAILBOX_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.VK_SUBOPTIMAL_KHR;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT;
import static org.lwjgl.vulkan.VK10.VK_API_VERSION_1_0;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_STORE_OP_STORE;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT;
import static org.lwjgl.vulkan.VK10.VK_FENCE_CREATE_SIGNALED_BIT;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_B8G8R8A8_UNORM;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_ASPECT_COLOR_BIT;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_UNDEFINED;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_VIEW_TYPE_2D;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
import static org.lwjgl.vulkan.VK10.VK_QUEUE_GRAPHICS_BIT;
import static org.lwjgl.vulkan.VK10.VK_SAMPLE_COUNT_1_BIT;
import static org.lwjgl.vulkan.VK10.VK_SHARING_MODE_EXCLUSIVE;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO;
import static org.lwjgl.vulkan.VK10.VK_SUBPASS_CONTENTS_INLINE;
import static org.lwjgl.vulkan.VK10.VK_SUBPASS_EXTERNAL;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

/**
 * GLFW + Vulkan implementation of {@link Window}.
 *
 * <p>Creates a GLFW window with {@code GLFW_CLIENT_API == GLFW_NO_API}, sets up a minimal Vulkan
 * stack (instance, surface, physical device, device, swapchain, render pass, framebuffers, and
 * per-frame sync objects), and renders ImGui through {@link ImGuiImplVulkan}. The swapchain is
 * recreated on window resize (including when minimized to zero size).
 *
 * <p>Multi-viewport is not supported.
 */
@SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:NeedBraces", "checkstyle:LocalVariableName", "checkstyle:FinalLocalVariable", "checkstyle:ParameterName", "checkstyle:EmptyBlock", "checkstyle:AvoidNestedBlocks"})
public class WindowVulkan extends Window {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplVulkan imGuiVulkan = new ImGuiImplVulkan();
    private final ImGuiImplVulkanH.Window wd = new ImGuiImplVulkanH.Window();

    /**
     * Pointer to the native GLFW window.
     */
    private long handle;

    private VkInstance instance;
    private VkPhysicalDevice physicalDevice;
    private VkDevice device;
    private VkQueue queue;
    private int queueFamily;

    private long[] imageAcquiredSemaphore;
    private long[] renderCompleteSemaphore;
    private long[] fence;
    private long[] commandPool;
    private long[] commandBuffer;

    @Override
    protected void init(final Configuration config) {
        initWindow(config);
        initVulkan();
        owner.initImGui(config);
        imGuiGlfw.init(handle, true);

        final ImGuiImplVulkan.InitInfo initInfo = new ImGuiImplVulkan.InitInfo();
        initInfo.instance = instance;
        initInfo.physicalDevice = physicalDevice;
        initInfo.device = device;
        initInfo.queueFamily = queueFamily;
        initInfo.queue = queue;
        initInfo.descriptorPoolSize = 1;
        initInfo.minImageCount = 2;
        initInfo.imageCount = wd.imageCount;
        initInfo.pipelineInfoMain.renderPass = wd.renderPass;
        initInfo.pipelineInfoMain.subpass = 0;
        initInfo.pipelineInfoMain.msaaSamples = VK_SAMPLE_COUNT_1_BIT;
        if (!imGuiVulkan.init(initInfo)) {
            throw new IllegalStateException("Failed to initialize the Vulkan ImGui backend");
        }
    }

    @Override
    protected void dispose() {
        imGuiVulkan.shutdown();
        imGuiGlfw.shutdown();
        owner.disposeImGui();

        VK10.vkDeviceWaitIdle(device);
        destroySyncObjects();
        destroySwapchainResources();
        VK10.vkDestroyRenderPass(device, wd.renderPass, null);
        KHRSurface.vkDestroySurfaceKHR(instance, wd.surface, null);
        VK10.vkDestroyDevice(device, null);
        VK10.vkDestroyInstance(instance, null);

        Callbacks.glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    /**
     * Method to create and initialize GLFW window (without a GL context).
     *
     * @param config configuration object with basic window information
     */
    protected void initWindow(final Configuration config) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
        glfwWindowHint(GLFW_SCALE_FRAMEBUFFER, GLFW_FALSE);
        glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_TRUE);
        handle = glfwCreateWindow(config.getWidth(), config.getHeight(), config.getTitle(), MemoryUtil.NULL, MemoryUtil.NULL);

        if (handle == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(handle, pWidth, pHeight);
            final org.lwjgl.glfw.GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));
            glfwSetWindowPos(handle, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwShowWindow(handle);


        glfwSetFramebufferSizeCallback(handle, new org.lwjgl.glfw.GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(final long window, final int width, final int height) {
                wd.swapChainRebuild = true;
            }
        });
    }

    @Override
    protected void run() {
        while (!glfwWindowShouldClose(handle)) {
            runFrame();
        }
    }

    @Override
    protected void runFrame() {
        glfwPollEvents();

        if (wd.swapChainRebuild) {
            recreateSwapchain();
        }
        if (wd.width == 0 || wd.height == 0) {
            return;
        }

        imGuiVulkan.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        owner.preProcess();
        owner.process();
        owner.postProcess();

        ImGui.render();
        renderFrame();
    }

    // -------------------------------------------------------------------------
    // Private: Vulkan setup
    // -------------------------------------------------------------------------

    private void initVulkan() {
        final PointerBuffer requiredExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions();
        if (requiredExtensions == null) {
            throw new IllegalStateException("GLFW reports no required Vulkan instance extensions");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkApplicationInfo appInfo = VkApplicationInfo.calloc(stack);
            appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);
            appInfo.pApplicationName(stack.UTF8("imgui-java"));
            appInfo.applicationVersion(1);
            appInfo.pEngineName(stack.UTF8("imgui-java"));
            appInfo.engineVersion(1);
            appInfo.apiVersion(VK_API_VERSION_1_0);

            final VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack);
            createInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
            createInfo.pApplicationInfo(appInfo);
            VkInstanceCreateInfo.nenabledExtensionCount(createInfo.address(), requiredExtensions.remaining());
            createInfo.ppEnabledExtensionNames(requiredExtensions);

            final PointerBuffer pInstance = stack.mallocPointer(1);
            final int err = VK10.vkCreateInstance(createInfo, null, pInstance);
            checkVkResult(err, "vkCreateInstance");
            instance = new VkInstance(pInstance.get(0), createInfo);
        }

        wd.surface = ImGuiImplGlfw.createVulkanSurface(handle, instance);
        if (wd.surface == 0) {
            throw new IllegalStateException("Failed to create the Vulkan surface");
        }

        pickPhysicalDevice();
        createDevice();
        selectSurfaceFormat();
        selectPresentMode();
        createSwapchain();
        createSyncObjects();
    }

    /**
     * Picks the first physical device exposing a queue family that supports both graphics and
     * presentation for our surface.
     */
    private void pickPhysicalDevice() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pCount = stack.mallocInt(1);
            int err = VK10.vkEnumeratePhysicalDevices(instance, pCount, null);
            checkVkResult(err, "vkEnumeratePhysicalDevices");
            if (pCount.get(0) == 0) {
                throw new IllegalStateException("No Vulkan physical device found");
            }

            final PointerBuffer pDevices = stack.mallocPointer(pCount.get(0));
            err = VK10.vkEnumeratePhysicalDevices(instance, pCount, pDevices);
            checkVkResult(err, "vkEnumeratePhysicalDevices");

            for (int i = 0; i < pDevices.capacity(); i++) {
                final long phy = pDevices.get(i);
                final IntBuffer pQueueCount = stack.mallocInt(1);
                VK10.vkGetPhysicalDeviceQueueFamilyProperties(new VkPhysicalDevice(phy, instance), pQueueCount, null);

                final VkQueueFamilyProperties.Buffer props = VkQueueFamilyProperties.calloc(pQueueCount.get(0), stack);
                VK10.vkGetPhysicalDeviceQueueFamilyProperties(new VkPhysicalDevice(phy, instance), pQueueCount, props);

                for (int q = 0; q < props.capacity(); q++) {
                    if ((props.get(q).queueFlags() & VK_QUEUE_GRAPHICS_BIT) != 0) {
                        final IntBuffer pSupported = stack.mallocInt(1);
                        err = KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(new VkPhysicalDevice(phy, instance), q, wd.surface, pSupported);
                        checkVkResult(err, "vkGetPhysicalDeviceSurfaceSupportKHR");
                        if (pSupported.get(0) != 0) {
                            physicalDevice = new VkPhysicalDevice(phy, instance);
                            queueFamily = q;
                            return;
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("No Vulkan physical device with graphics + present queue found");
    }

    private void createDevice() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkDeviceQueueCreateInfo.Buffer queueCreateInfo = VkDeviceQueueCreateInfo.calloc(1, stack);
            queueCreateInfo.sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO);
            queueCreateInfo.queueFamilyIndex(queueFamily);
            queueCreateInfo.pQueuePriorities(stack.floats(1.0f));

            final VkDeviceCreateInfo deviceInfo = VkDeviceCreateInfo.calloc(stack);
            deviceInfo.sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO);
            VkDeviceCreateInfo.nqueueCreateInfoCount(deviceInfo.address(), 1);
            deviceInfo.pQueueCreateInfos(queueCreateInfo);
            VkDeviceCreateInfo.nenabledExtensionCount(deviceInfo.address(), 1);
            deviceInfo.ppEnabledExtensionNames(stack.pointers(stack.UTF8("VK_KHR_swapchain")));

            final PointerBuffer pDevice = stack.mallocPointer(1);
            final int err = VK10.vkCreateDevice(physicalDevice, deviceInfo, null, pDevice);
            checkVkResult(err, "vkCreateDevice");
            device = new VkDevice(pDevice.get(0), physicalDevice, deviceInfo);

            final PointerBuffer pQueue = stack.mallocPointer(1);
            VK10.vkGetDeviceQueue(device, queueFamily, 0, pQueue);
            queue = new VkQueue(pQueue.get(0), device);
        }
    }

    /**
     * Selects the surface format, preferring B8G8R8A8 with the sRGB color space.
     */
    private void selectSurfaceFormat() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pCount = stack.mallocInt(1);
            int err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, wd.surface, pCount, null);
            checkVkResult(err, "vkGetPhysicalDeviceSurfaceFormatsKHR");
            if (pCount.get(0) == 0) {
                throw new IllegalStateException("Surface supports no formats");
            }

            final VkSurfaceFormatKHR.Buffer formats = VkSurfaceFormatKHR.calloc(pCount.get(0), stack);
            err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, wd.surface, pCount, formats);
            checkVkResult(err, "vkGetPhysicalDeviceSurfaceFormatsKHR");

            for (int i = 0; i < formats.capacity(); i++) {
                if (formats.get(i).format() == VK_FORMAT_B8G8R8A8_UNORM
                    && formats.get(i).colorSpace() == VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
                    wd.surfaceFormat = VK_FORMAT_B8G8R8A8_UNORM;
                    return;
                }
            }
            wd.surfaceFormat = formats.get(0).format();
        }
    }

    /**
     * Selects the present mode, preferring mailbox, then immediate, falling back to FIFO.
     */
    private void selectPresentMode() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pCount = stack.mallocInt(1);
            int err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, wd.surface, pCount, null);
            checkVkResult(err, "vkGetPhysicalDeviceSurfacePresentModesKHR");

            final IntBuffer presentModes = stack.mallocInt(pCount.get(0));
            err = KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(physicalDevice, wd.surface, pCount, presentModes);
            checkVkResult(err, "vkGetPhysicalDeviceSurfacePresentModesKHR");

            wd.presentMode = VK_PRESENT_MODE_FIFO_KHR;
            for (int i = 0; i < presentModes.capacity(); i++) {
                final int mode = presentModes.get(i);
                if (mode == VK_PRESENT_MODE_MAILBOX_KHR || mode == VK_PRESENT_MODE_IMMEDIATE_KHR) {
                    wd.presentMode = mode;
                    break;
                }
            }
        }
    }

    private void createSwapchain() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1);
            final IntBuffer pHeight = stack.mallocInt(1);
            glfwGetFramebufferSize(handle, pWidth, pHeight);
            wd.width = pWidth.get(0);
            wd.height = pHeight.get(0);
            if (wd.width == 0 || wd.height == 0) {
                return;
            }

            int minImageCount = 2;
            final VkSurfaceCapabilitiesKHR caps = VkSurfaceCapabilitiesKHR.calloc(stack);
            int err = KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDevice, wd.surface, caps);
            checkVkResult(err, "vkGetPhysicalDeviceSurfaceCapabilitiesKHR");
            if (caps.maxImageCount() > 0 && minImageCount > caps.maxImageCount()) {
                minImageCount = caps.maxImageCount();
            }
            if (minImageCount < caps.minImageCount()) {
                minImageCount = caps.minImageCount();
            }
            if (wd.width < caps.minImageExtent().width()) {
                wd.width = caps.minImageExtent().width();
            }
            if (wd.width > caps.maxImageExtent().width()) {
                wd.width = caps.maxImageExtent().width();
            }
            if (wd.height < caps.minImageExtent().height()) {
                wd.height = caps.minImageExtent().height();
            }
            if (wd.height > caps.maxImageExtent().height()) {
                wd.height = caps.maxImageExtent().height();
            }

            int compositeAlpha = VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
            if ((caps.supportedCompositeAlpha() & compositeAlpha) == 0) {
                compositeAlpha = KHRSurface.VK_COMPOSITE_ALPHA_INHERIT_BIT_KHR;
            }
            if ((caps.supportedCompositeAlpha() & compositeAlpha) == 0) {
                compositeAlpha = KHRSurface.VK_COMPOSITE_ALPHA_PRE_MULTIPLIED_BIT_KHR;
            }

            final VkSwapchainCreateInfoKHR createInfo = VkSwapchainCreateInfoKHR.calloc(stack);
            createInfo.sType(VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR);
            createInfo.surface(wd.surface);
            createInfo.minImageCount(minImageCount);
            createInfo.imageFormat(wd.surfaceFormat);
            createInfo.imageColorSpace(VK_COLOR_SPACE_SRGB_NONLINEAR_KHR);
            createInfo.imageExtent().set(wd.width, wd.height);
            createInfo.imageArrayLayers(1);
            createInfo.imageUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT);
            createInfo.imageSharingMode(VK_SHARING_MODE_EXCLUSIVE);
            createInfo.preTransform(caps.currentTransform());
            createInfo.compositeAlpha(compositeAlpha);
            createInfo.presentMode(wd.presentMode);
            createInfo.clipped(true);
            createInfo.oldSwapchain(wd.swapchain);

            final LongBuffer pSwapchain = stack.mallocLong(1);
            err = KHRSwapchain.vkCreateSwapchainKHR(device, createInfo, null, pSwapchain);
            checkVkResult(err, "vkCreateSwapchainKHR");
            wd.swapchain = pSwapchain.get(0);

            final IntBuffer pImageCount = stack.mallocInt(1);
            err = KHRSwapchain.vkGetSwapchainImagesKHR(device, wd.swapchain, pImageCount, null);
            checkVkResult(err, "vkGetSwapchainImagesKHR");
            wd.images.clear();
            final LongBuffer pImages = stack.mallocLong(pImageCount.get(0));
            err = KHRSwapchain.vkGetSwapchainImagesKHR(device, wd.swapchain, pImageCount, pImages);
            checkVkResult(err, "vkGetSwapchainImagesKHR");
            for (int i = 0; i < pImages.capacity(); i++) {
                wd.images.add(pImages.get(i));
            }
            wd.imageCount = wd.images.size();
        }

        createImageViews();
        if (wd.renderPass == 0) {
            createRenderPass();
        }
        createFramebuffers();
    }

    private void createImageViews() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            for (final long image : wd.images) {
                final VkImageViewCreateInfo viewInfo = VkImageViewCreateInfo.calloc(stack);
                viewInfo.sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO);
                viewInfo.image(image);
                viewInfo.viewType(VK_IMAGE_VIEW_TYPE_2D);
                viewInfo.format(wd.surfaceFormat);
                viewInfo.subresourceRange().aspectMask(VK_IMAGE_ASPECT_COLOR_BIT);
                viewInfo.subresourceRange().levelCount(1);
                viewInfo.subresourceRange().layerCount(1);

                final LongBuffer pView = stack.mallocLong(1);
                final int err = VK10.vkCreateImageView(device, viewInfo, null, pView);
                checkVkResult(err, "vkCreateImageView");
                wd.imageViews.add(pView.get(0));
            }
        }
    }

    private void createRenderPass() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkAttachmentDescription.Buffer attachment = VkAttachmentDescription.calloc(1, stack);
            attachment.format(wd.surfaceFormat);
            attachment.samples(VK_SAMPLE_COUNT_1_BIT);
            attachment.loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR);
            attachment.storeOp(VK_ATTACHMENT_STORE_OP_STORE);
            attachment.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE);
            attachment.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE);
            attachment.initialLayout(VK_IMAGE_LAYOUT_UNDEFINED);
            attachment.finalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);

            final VkAttachmentReference.Buffer colorAttachment = VkAttachmentReference.calloc(1, stack);
            colorAttachment.attachment(0);
            colorAttachment.layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

            final VkSubpassDescription.Buffer subpass = VkSubpassDescription.calloc(1, stack);
            subpass.pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS);
            subpass.colorAttachmentCount(1);
            subpass.pColorAttachments(colorAttachment);

            final VkSubpassDependency.Buffer dependency = VkSubpassDependency.calloc(1, stack);
            dependency.srcSubpass(VK_SUBPASS_EXTERNAL);
            dependency.dstSubpass(0);
            dependency.srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
            dependency.srcAccessMask(0);
            dependency.dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
            dependency.dstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

            final VkRenderPassCreateInfo rpInfo = VkRenderPassCreateInfo.calloc(stack);
            rpInfo.sType(VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO);
            VkRenderPassCreateInfo.nattachmentCount(rpInfo.address(), 1);
            rpInfo.pAttachments(attachment);
            VkRenderPassCreateInfo.nsubpassCount(rpInfo.address(), 1);
            rpInfo.pSubpasses(subpass);
            VkRenderPassCreateInfo.ndependencyCount(rpInfo.address(), 1);
            rpInfo.pDependencies(dependency);

            final LongBuffer pRenderPass = stack.mallocLong(1);
            final int err = VK10.vkCreateRenderPass(device, rpInfo, null, pRenderPass);
            checkVkResult(err, "vkCreateRenderPass");
            wd.renderPass = pRenderPass.get(0);
        }
    }

    private void createFramebuffers() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            for (final long view : wd.imageViews) {
                final VkFramebufferCreateInfo fbInfo = VkFramebufferCreateInfo.calloc(stack);
                fbInfo.sType(VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO);
                fbInfo.renderPass(wd.renderPass);
                fbInfo.pAttachments(stack.longs(view));
                fbInfo.width(wd.width);
                fbInfo.height(wd.height);
                fbInfo.layers(1);

                final LongBuffer pFramebuffer = stack.mallocLong(1);
                final int err = VK10.vkCreateFramebuffer(device, fbInfo, null, pFramebuffer);
                checkVkResult(err, "vkCreateFramebuffer");
                wd.framebuffers.add(pFramebuffer.get(0));
            }
        }
    }

    private void createSyncObjects() {
        imageAcquiredSemaphore = new long[wd.imageCount];
        renderCompleteSemaphore = new long[wd.imageCount];
        fence = new long[wd.imageCount];
        commandPool = new long[wd.imageCount];
        commandBuffer = new long[wd.imageCount];

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkSemaphoreCreateInfo semaphoreInfo = VkSemaphoreCreateInfo.calloc(stack);
            semaphoreInfo.sType(VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);

            final VkFenceCreateInfo fenceInfo = VkFenceCreateInfo.calloc(stack);
            fenceInfo.sType(VK_STRUCTURE_TYPE_FENCE_CREATE_INFO);
            fenceInfo.flags(VK_FENCE_CREATE_SIGNALED_BIT);

            final VkCommandPoolCreateInfo poolInfo = VkCommandPoolCreateInfo.calloc(stack);
            poolInfo.sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO);
            poolInfo.flags(0);
            poolInfo.queueFamilyIndex(queueFamily);

            final VkCommandBufferAllocateInfo allocInfo = VkCommandBufferAllocateInfo.calloc(stack);
            allocInfo.sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO);
            allocInfo.commandBufferCount(1);

            for (int i = 0; i < wd.imageCount; i++) {
                final LongBuffer pSemaphore = stack.mallocLong(1);
                int err = VK10.vkCreateSemaphore(device, semaphoreInfo, null, pSemaphore);
                checkVkResult(err, "vkCreateSemaphore");
                imageAcquiredSemaphore[i] = pSemaphore.get(0);
                err = VK10.vkCreateSemaphore(device, semaphoreInfo, null, pSemaphore);
                checkVkResult(err, "vkCreateSemaphore");
                renderCompleteSemaphore[i] = pSemaphore.get(0);

                final LongBuffer pFence = stack.mallocLong(1);
                err = VK10.vkCreateFence(device, fenceInfo, null, pFence);
                checkVkResult(err, "vkCreateFence");
                fence[i] = pFence.get(0);

                final LongBuffer pPool = stack.mallocLong(1);
                err = VK10.vkCreateCommandPool(device, poolInfo, null, pPool);
                checkVkResult(err, "vkCreateCommandPool");
                commandPool[i] = pPool.get(0);

                allocInfo.commandPool(commandPool[i]);
                final PointerBuffer pCmd = stack.mallocPointer(1);
                err = VK10.vkAllocateCommandBuffers(device, allocInfo, pCmd);
                checkVkResult(err, "vkAllocateCommandBuffers");
                commandBuffer[i] = pCmd.get(0);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Private: Frame rendering
    // -------------------------------------------------------------------------

    private void renderFrame() {

        wd.semaphoreIndex = (wd.semaphoreIndex + 1) % wd.imageCount;
        final int semaphoreIndex = wd.semaphoreIndex;

        int err = VK10.vkWaitForFences(device, fence[semaphoreIndex], true, ~0L);
        checkVkResult(err, "vkWaitForFences");

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pImageIndex = stack.mallocInt(1);
            err = KHRSwapchain.vkAcquireNextImageKHR(device, wd.swapchain, ~0L,
                imageAcquiredSemaphore[semaphoreIndex], 0, pImageIndex);
            if (err == VK_ERROR_OUT_OF_DATE_KHR || err == VK_SUBOPTIMAL_KHR) {
                wd.swapChainRebuild = true;
                return;
            }
            if (err != VK_SUCCESS) {
                System.err.println("[WindowVulkan] vkAcquireNextImageKHR returned " + err
                    + ", skipping frame");
                return;
            }
            wd.frameIndex = pImageIndex.get(0);

            err = VK10.vkResetFences(device, fence[semaphoreIndex]);
            checkVkResult(err, "vkResetFences");

            final VkCommandBuffer cmd = new VkCommandBuffer(commandBuffer[semaphoreIndex], device);

            VK10.vkResetCommandPool(device, commandPool[semaphoreIndex], 0);
            final VkCommandBufferBeginInfo beginInfo = VkCommandBufferBeginInfo.calloc(stack);
            beginInfo.sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO);
            beginInfo.flags(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
            err = VK10.vkBeginCommandBuffer(cmd, beginInfo);
            checkVkResult(err, "vkBeginCommandBuffer");

            final VkRenderPassBeginInfo rpInfo = VkRenderPassBeginInfo.calloc(stack);
            rpInfo.sType(VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO);
            rpInfo.renderPass(wd.renderPass);
            rpInfo.framebuffer(wd.framebuffers.get(wd.frameIndex));
            rpInfo.renderArea().offset().set(0, 0);
            rpInfo.renderArea().extent().set(wd.width, wd.height);
            final VkClearValue.Buffer clearValues = VkClearValue.calloc(1, stack);
            clearValues.color().float32(0, colorBg.getRed());
            clearValues.color().float32(1, colorBg.getGreen());
            clearValues.color().float32(2, colorBg.getBlue());
            clearValues.color().float32(3, colorBg.getAlpha());
            rpInfo.clearValueCount(1);
            rpInfo.pClearValues(clearValues);
            VK10.vkCmdBeginRenderPass(cmd, rpInfo, VK_SUBPASS_CONTENTS_INLINE);

            imGuiVulkan.renderDrawData(ImGui.getDrawData(), cmd, 0);

            VK10.vkCmdEndRenderPass(cmd);
            err = VK10.vkEndCommandBuffer(cmd);
            checkVkResult(err, "vkEndCommandBuffer");

            final VkSubmitInfo submitInfo = VkSubmitInfo.calloc(stack);
            submitInfo.sType(VK_STRUCTURE_TYPE_SUBMIT_INFO);
            VkSubmitInfo.nwaitSemaphoreCount(submitInfo.address(), 1);
            submitInfo.pWaitSemaphores(stack.longs(imageAcquiredSemaphore[semaphoreIndex]));
            submitInfo.pWaitDstStageMask(stack.ints(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT));
            VkSubmitInfo.ncommandBufferCount(submitInfo.address(), 1);
            submitInfo.pCommandBuffers(stack.pointers(cmd.address()));
            VkSubmitInfo.nsignalSemaphoreCount(submitInfo.address(), 1);
            submitInfo.pSignalSemaphores(stack.longs(renderCompleteSemaphore[semaphoreIndex]));
            err = VK10.vkQueueSubmit(queue, submitInfo, fence[semaphoreIndex]);
            checkVkResult(err, "vkQueueSubmit");

            final VkPresentInfoKHR presentInfo = VkPresentInfoKHR.calloc(stack);
            presentInfo.sType(VK_STRUCTURE_TYPE_PRESENT_INFO_KHR);
            VkPresentInfoKHR.nwaitSemaphoreCount(presentInfo.address(), 1);
            presentInfo.pWaitSemaphores(stack.longs(renderCompleteSemaphore[semaphoreIndex]));
            VkPresentInfoKHR.nswapchainCount(presentInfo.address(), 1);
            presentInfo.pSwapchains(stack.longs(wd.swapchain));
            presentInfo.pImageIndices(stack.ints(wd.frameIndex));
            err = KHRSwapchain.vkQueuePresentKHR(queue, presentInfo);
            if (err == VK_ERROR_OUT_OF_DATE_KHR || err == VK_SUBOPTIMAL_KHR) {
                wd.swapChainRebuild = true;
            } else {
                checkVkResult(err, "vkQueuePresentKHR");
            }
        }
    }

    // -------------------------------------------------------------------------
    // Private: Swapchain recreation & teardown
    // -------------------------------------------------------------------------

    private void recreateSwapchain() {
        VK10.vkDeviceWaitIdle(device);
        destroySwapchainResources();
        wd.swapChainRebuild = false;
        createSwapchain();
    }

    private void destroySwapchainResources() {
        for (final long framebuffer : wd.framebuffers) {
            VK10.vkDestroyFramebuffer(device, framebuffer, null);
        }
        wd.framebuffers.clear();
        for (final long view : wd.imageViews) {
            VK10.vkDestroyImageView(device, view, null);
        }
        wd.imageViews.clear();
        wd.images.clear();
        if (wd.swapchain != 0) {
            KHRSwapchain.vkDestroySwapchainKHR(device, wd.swapchain, null);
            wd.swapchain = 0;
        }
    }

    private void destroySyncObjects() {
        if (imageAcquiredSemaphore != null) {
            for (int i = 0; i < wd.imageCount && i < imageAcquiredSemaphore.length; i++) {
                if (imageAcquiredSemaphore[i] != 0) {
                    VK10.vkDestroySemaphore(device, imageAcquiredSemaphore[i], null);
                }
                if (renderCompleteSemaphore[i] != 0) {
                    VK10.vkDestroySemaphore(device, renderCompleteSemaphore[i], null);
                }
                if (fence[i] != 0) {
                    VK10.vkDestroyFence(device, fence[i], null);
                }
                if (commandBuffer[i] != 0) {
                    VK10.vkFreeCommandBuffers(device, commandPool[i], new VkCommandBuffer(commandBuffer[i], device));
                }
                if (commandPool[i] != 0) {
                    VK10.vkDestroyCommandPool(device, commandPool[i], null);
                }
            }
            imageAcquiredSemaphore = null;
            renderCompleteSemaphore = null;
            fence = null;
            commandPool = null;
            commandBuffer = null;
        }
    }

    private static void checkVkResult(final int err, final String op) {
        if (err != VK_SUCCESS) {
            throw new IllegalStateException(op + " failed with Vulkan error " + err);
        }
    }

    /**
     * @return pointer to the native GLFW window
     */
    public final long getHandle() {
        return handle;
    }

    /**
     * @return the Vulkan device handle
     */
    public final long getDevice() {
        return device.address();
    }

    /**
     * @return the swapchain handle
     */
    public final long getSwapchain() {
        return wd.swapchain;
    }
}
