package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

public class ImVkRenderPass {
    private final static Logger LOGGER = Logger.getLogger(ImVkRenderPass.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkSwapchain swapchain;

    public void create() {
        if (swapchain == null) {
            throw new IllegalStateException("Cannot create render pass without a vulkan swapchain");
        }

        LOGGER.fine("Creating vulkan render pass");
        createRenderPass();
        LOGGER.fine("Done creating vulkan render pass");
    }

    public void destroy() {
        destroyRenderPass();
    }

    private void createRenderPass() {
        try (MemoryStack stack = MemoryStack.stackPush()) {

            VkAttachmentDescription.Buffer attachmentBuffer = VkAttachmentDescription.calloc(2, stack);
            //Color
            attachmentBuffer.get(0)
                .format(getSwapchain().getSurfaceFormat().format())
                .samples(VK_SAMPLE_COUNT_1_BIT)
                .loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
                .storeOp(VK_ATTACHMENT_STORE_OP_STORE)
                //.stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
                //.stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                .initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                .finalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);
            //Depth
            attachmentBuffer.get(1)
                .format(VK_FORMAT_D32_SFLOAT)
                .samples(VK_SAMPLE_COUNT_1_BIT)
                .loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
                .storeOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                .initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                .finalLayout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);

            VkAttachmentReference colorAttachmentRef = VkAttachmentReference.calloc(stack)
                .attachment(0)
                .layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

            VkAttachmentReference.Buffer colorAttachmentRefBuff = VkAttachmentReference.calloc(1, stack)
                .put(colorAttachmentRef)
                .flip();

            VkAttachmentReference depthReference = VkAttachmentReference.malloc(stack)
                .attachment(1)
                .layout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);

            VkSubpassDescription.Buffer subpassBuff = VkSubpassDescription.calloc(1, stack);
            subpassBuff.get(0)
                .colorAttachmentCount(colorAttachmentRefBuff.remaining())
                .pColorAttachments(colorAttachmentRefBuff)
                .pDepthStencilAttachment(depthReference)
                .pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS);

            VkSubpassDependency.Buffer subpassDependencyBuff = VkSubpassDependency.calloc(1);
            subpassDependencyBuff.get(0)
                .srcSubpass(VK_SUBPASS_EXTERNAL)
                .dstSubpass(0)
                .srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .srcAccessMask(0)
                .dstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

            VkRenderPassCreateInfo renderPassInfo = VkRenderPassCreateInfo.calloc(stack)
                .sType$Default()
                .pAttachments(attachmentBuffer)
                .pSubpasses(subpassBuff)
                .pDependencies(subpassDependencyBuff);

            LongBuffer renderPassBuff = stack.callocLong(1);
            vkOK(vkCreateRenderPass(getSwapchain().getGraphicsQueue().getDevice().getDevice(), renderPassInfo, null, renderPassBuff));
            nativeHandle = renderPassBuff.get();
        }
    }

    private void destroyRenderPass() {
        vkDestroyRenderPass(getSwapchain().getGraphicsQueue().getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
    }

    public ImVkSwapchain getSwapchain() {
        return swapchain;
    }

    public void setSwapchain(ImVkSwapchain swapchain) {
        this.swapchain = swapchain;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }
}
