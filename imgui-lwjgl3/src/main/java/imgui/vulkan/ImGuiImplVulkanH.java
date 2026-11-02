package imgui.vulkan;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_STORE_OP_STORE;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_UNDEFINED;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_UNDEFINED;
import static org.lwjgl.vulkan.VK10.VK_SAMPLE_COUNT_1_BIT;

/**
 * Java port of the {@code ImGui_ImplVulkanH_XXX} helper structures from
 * {@code imgui_impl_vulkan.h}.
 */
public final class ImGuiImplVulkanH {

    private ImGuiImplVulkanH() { }

    /** VK_PRESENT_MODE_MAX_ENUM_KHR = 0x7FFFFFFF (not exposed by LWJGL) */
    static final int PRESENT_MODE_MAX_ENUM_KHR = 0x7FFFFFFF;

    public static final class Frame {
        public long commandPool;
        public long commandBuffer;
        public long fence;
        public long backbuffer;
        public long backbufferView;
        public long framebuffer;
    }

    public static final class FrameSemaphores {
        public long imageAcquiredSemaphore;
        public long renderCompleteSemaphore;
    }

    public static final class Window {
        public boolean useDynamicRendering;
        public long surface;

        public int surfaceFormat;
        public int presentMode;

        public int attachmentFormat;
        public int attachmentSamples;
        public int attachmentLoadOp;
        public int attachmentStoreOp;
        public int attachmentStencilLoadOp;
        public int attachmentStencilStoreOp;
        public int attachmentInitialLayout;
        public int attachmentFinalLayout;

        public float[] clearValue = new float[4];

        public int width;
        public int height;
        public long swapchain;
        public boolean swapChainRebuild;
        public long renderPass;
        public long pipeline;
        public int frameIndex;
        public int imageCount;
        public int semaphoreCount;
        public int semaphoreIndex;
        public final List<Long> images = new ArrayList<Long>();
        public final List<Long> imageViews = new ArrayList<Long>();
        public final List<Long> framebuffers = new ArrayList<Long>();
        public final List<Frame> frames = new ArrayList<Frame>();
        public final List<FrameSemaphores> frameSemaphores = new ArrayList<FrameSemaphores>();

        public Window() {
            presentMode = PRESENT_MODE_MAX_ENUM_KHR;
            attachmentFormat = VK_FORMAT_UNDEFINED;
            attachmentSamples = VK_SAMPLE_COUNT_1_BIT;
            attachmentLoadOp = VK_ATTACHMENT_LOAD_OP_CLEAR;
            attachmentStoreOp = VK_ATTACHMENT_STORE_OP_STORE;
            attachmentStencilLoadOp = VK_ATTACHMENT_LOAD_OP_DONT_CARE;
            attachmentStencilStoreOp = VK_ATTACHMENT_STORE_OP_DONT_CARE;
            attachmentInitialLayout = VK_IMAGE_LAYOUT_UNDEFINED;
            attachmentFinalLayout = VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
        }
    }
}
