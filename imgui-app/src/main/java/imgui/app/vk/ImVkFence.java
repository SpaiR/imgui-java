package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkFenceCreateInfo;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_FENCE_CREATE_SIGNALED_BIT;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkCreateFence;
import static org.lwjgl.vulkan.VK10.vkDestroyFence;
import static org.lwjgl.vulkan.VK10.vkResetFences;
import static org.lwjgl.vulkan.VK10.vkWaitForFences;

public class ImVkFence {

    private static final Logger LOGGER = Logger.getLogger(ImVkFence.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;
    private boolean signaled = false;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot create fence without a vulkan device");
        }

        createFence();
    }

    public void destroy() {
        destroyFence();
    }

    private void createFence() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkFenceCreateInfo fenceCreateInfo = VkFenceCreateInfo.calloc(stack)
                .sType$Default()
                .flags(isSignaled() ? VK_FENCE_CREATE_SIGNALED_BIT : 0);
            final LongBuffer longBuff = stack.callocLong(1);
            vkOK(
                vkCreateFence(
                    getDevice().getDevice(),
                    fenceCreateInfo,
                    null,
                    longBuff
                )
            );
            nativeHandle = longBuff.get();
        }
    }

    private void destroyFence() {
        vkDestroyFence(getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
    }

    public void waitFor() {
        vkWaitForFences(getDevice().getDevice(), nativeHandle, true, Long.MAX_VALUE);
    }

    public void reset() {
        vkResetFences(getDevice().getDevice(), nativeHandle);
    }

    public ImVkDevice getDevice() {
        return device;
    }

    public void setDevice(final ImVkDevice device) {
        this.device = device;
    }

    public boolean isSignaled() {
        return signaled;
    }

    public void setSignaled(final boolean signaled) {
        this.signaled = signaled;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }
}
