package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.*;

public class ImVkFrameBuffer {
    private final static Logger LOGGER = Logger.getLogger(ImVkFrameBuffer.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkRenderPass renderPass;

    private LongBuffer attachments;

    public void create() {
        if (attachments == null) {
            throw new IllegalStateException("Frame Buffer must have attachments specified!");
        }

        if (renderPass == null) {
            throw new IllegalStateException("Cannot create frame buffer without vulkan render pass");
        }

        LOGGER.fine("Creating vulkan frame buffer");
        createFrameBuffer();
        LOGGER.fine("Done creating vulkan frame buffer");
    }

    public void destroy() {
        destroyFrameBuffer();
    }

    private void createFrameBuffer() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkFramebufferCreateInfo fci = VkFramebufferCreateInfo.calloc(stack)
                .sType$Default()
                .pAttachments(attachments)
                .width(getRenderPass().getSwapchain().getExtent().width())
                .height(getRenderPass().getSwapchain().getExtent().height())
                .layers(1)
                .renderPass(getRenderPass().getNativeHandle());
            LongBuffer longBuff = stack.callocLong(1);
            vkOK(
                vkCreateFramebuffer(getRenderPass().getSwapchain().getGraphicsQueue().getDevice().getDevice(), fci, null, longBuff)
            );
            nativeHandle = longBuff.get();
        }
    }

    private void destroyFrameBuffer() {
        vkDestroyFramebuffer(getRenderPass().getSwapchain().getGraphicsQueue().getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
    }

    public ImVkRenderPass getRenderPass() {
        return renderPass;
    }

    public void setRenderPass(ImVkRenderPass renderPass) {
        this.renderPass = renderPass;
    }

    public LongBuffer getAttachments() {
        return attachments;
    }

    public void setAttachments(LongBuffer attachments) {
        this.attachments = attachments;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }
}
