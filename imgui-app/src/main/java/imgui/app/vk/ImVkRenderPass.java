package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_LOAD_OP_CLEAR;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE;
import static org.lwjgl.vulkan.VK10.VK_ATTACHMENT_STORE_OP_STORE;
import static org.lwjgl.vulkan.VK10.VK_FORMAT_D32_SFLOAT;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL;
import static org.lwjgl.vulkan.VK10.VK_IMAGE_LAYOUT_UNDEFINED;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_BIND_POINT_GRAPHICS;
import static org.lwjgl.vulkan.VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
import static org.lwjgl.vulkan.VK10.VK_SAMPLE_COUNT_1_BIT;
import static org.lwjgl.vulkan.VK10.VK_SUBPASS_EXTERNAL;
import static org.lwjgl.vulkan.VK10.vkCreateRenderPass;
import static org.lwjgl.vulkan.VK10.vkDestroyRenderPass;

public class ImVkRenderPass {
    private static final Logger LOGGER = Logger.getLogger(ImVkRenderPass.class.getName());

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

            final VkAttachmentDescription.Buffer attachmentBuffer = VkAttachmentDescription.calloc(2, stack);
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

            final VkAttachmentReference colorAttachmentRef = VkAttachmentReference.calloc(stack)
                .attachment(0)
                .layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

            final VkAttachmentReference.Buffer colorAttachmentRefBuff = VkAttachmentReference.calloc(1, stack)
                .put(colorAttachmentRef)
                .flip();

            final VkAttachmentReference depthReference = VkAttachmentReference.malloc(stack)
                .attachment(1)
                .layout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);

            final VkSubpassDescription.Buffer subpassBuff = VkSubpassDescription.calloc(1, stack);
            subpassBuff.get(0)
                .colorAttachmentCount(colorAttachmentRefBuff.remaining())
                .pColorAttachments(colorAttachmentRefBuff)
                .pDepthStencilAttachment(depthReference)
                .pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS);

            final VkSubpassDependency.Buffer subpassDependencyBuff = VkSubpassDependency.calloc(1);
            subpassDependencyBuff.get(0)
                .srcSubpass(VK_SUBPASS_EXTERNAL)
                .dstSubpass(0)
                .srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .srcAccessMask(0)
                .dstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

            final VkRenderPassCreateInfo renderPassInfo = VkRenderPassCreateInfo.calloc(stack)
                .sType$Default()
                .pAttachments(attachmentBuffer)
                .pSubpasses(subpassBuff)
                .pDependencies(subpassDependencyBuff);

            final LongBuffer renderPassBuff = stack.callocLong(1);
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

    public void setSwapchain(final ImVkSwapchain swapchain) {
        this.swapchain = swapchain;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }
}
