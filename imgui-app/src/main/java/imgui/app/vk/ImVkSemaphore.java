package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.*;

public class ImVkSemaphore {

    private final static Logger LOGGER = Logger.getLogger(ImVkSemaphore.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot create semaphore without a vulkan device");
        }
        createSemaphore();
    }

    public void destroy() {
        destroySemaphore();
    }

    private void createSemaphore() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSemaphoreCreateInfo semaphoreCreateInfo = VkSemaphoreCreateInfo.calloc(stack)
                .sType$Default();
            LongBuffer longBuff = stack.callocLong(1);
            vkOK(
                vkCreateSemaphore(getDevice().getDevice(),
                    semaphoreCreateInfo,
                    null,
                    longBuff
                )
            );
            nativeHandle = longBuff.get();
        }
    }

    private void destroySemaphore() {
        vkDestroySemaphore(getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }

    public void setNativeHandle(long nativeHandle) {
        this.nativeHandle = nativeHandle;
    }

    public ImVkDevice getDevice() {
        return device;
    }

    public void setDevice(ImVkDevice device) {
        this.device = device;
    }
}
