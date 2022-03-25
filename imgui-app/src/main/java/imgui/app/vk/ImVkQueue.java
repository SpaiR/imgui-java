package imgui.app.vk;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkSubmitInfo;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkGetDeviceQueue;
import static org.lwjgl.vulkan.VK10.vkQueueSubmit;
import static org.lwjgl.vulkan.VK10.vkQueueWaitIdle;

public class ImVkQueue {

    private static final Logger LOGGER = Logger.getLogger(ImVkQueue.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;
    private VkQueue queue;

    private ImVkDevice device;
    private Integer familyIndex = -1;
    private Integer index = 0;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot get a queue without a vulkan device");
        }

        if (familyIndex < 0) {
            throw new IllegalStateException("Index must be specified to get a vulkan queue");
        }

        LOGGER.fine("Loading vulkan queue");
        createQueue();
        LOGGER.fine("Done loading vulkan queue");
    }

    private void createQueue() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final PointerBuffer queuePointerBuff = stack.callocPointer(1);
            vkGetDeviceQueue(getDevice().getDevice(), familyIndex, index, queuePointerBuff);
            nativeHandle = queuePointerBuff.get();
            queue = new VkQueue(nativeHandle, getDevice().getDevice());
        }
    }

    public void destroy() {
        destroyQueue();
    }

    private void destroyQueue() {
        queue = null;
        nativeHandle = VK_NULL_HANDLE;
    }

    public void waitIdle() {
        vkQueueWaitIdle(queue);
    }

    public void submit(final PointerBuffer commandBuffers, final LongBuffer waitSemaphores, final IntBuffer dstStageMasks, final LongBuffer signalSemaphores, final ImVkFence fence) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkSubmitInfo submitInfo = VkSubmitInfo.calloc(stack)
                .sType$Default()
                .pCommandBuffers(commandBuffers)
                .pSignalSemaphores(signalSemaphores);

            if (waitSemaphores != null) {
                submitInfo.waitSemaphoreCount(waitSemaphores.capacity())
                    .pWaitSemaphores(waitSemaphores)
                    .pWaitDstStageMask(dstStageMasks);
            } else {
                submitInfo.waitSemaphoreCount(0);
            }

            final long fenceHandle = fence != null ? fence.getNativeHandle() : VK_NULL_HANDLE;

            vkOK(vkQueueSubmit(queue, submitInfo, fenceHandle));
        }
    }

    public long getNativeHandle() {
        return nativeHandle;
    }

    public ImVkDevice getDevice() {
        return device;
    }

    public void setDevice(final ImVkDevice device) {
        this.device = device;
    }

    public Integer getFamilyIndex() {
        return familyIndex;
    }

    public void setFamilyIndex(final Integer familyIndex) {
        this.familyIndex = familyIndex;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(final Integer index) {
        this.index = index;
    }

    public VkQueue getQueue() {
        return queue;
    }
}
