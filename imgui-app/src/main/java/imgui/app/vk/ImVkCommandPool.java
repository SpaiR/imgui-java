package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkCreateCommandPool;
import static org.lwjgl.vulkan.VK10.vkDestroyCommandPool;

public class ImVkCommandPool {

    private static final Logger LOGGER = Logger.getLogger(ImVkCommandPool.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot create command pool without a vulkan device set");
        }

        createCommandPool();
    }

    public void destroy() {
        destroyCommandPool();
    }

    private void createCommandPool() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkCommandPoolCreateInfo cmdPoolInfo = VkCommandPoolCreateInfo.calloc(stack)
                .sType$Default()
                .flags(VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT)
                .queueFamilyIndex(getDevice().getPhysicalDevice().getIndices().getGraphicsFamily());

            final LongBuffer longBuff = stack.callocLong(1);
            vkOK(
                vkCreateCommandPool(
                    getDevice().getDevice(),
                    cmdPoolInfo,
                    null,
                    longBuff
                )
            );
            nativeHandle = longBuff.get();
        }
    }

    private void destroyCommandPool() {
        vkDestroyCommandPool(getDevice().getDevice(), nativeHandle, null);
    }

    public ImVkDevice getDevice() {
        return device;
    }

    public void setDevice(final ImVkDevice device) {
        this.device = device;
    }

    public long getNativeHandle() {
        return nativeHandle;
    }
}
