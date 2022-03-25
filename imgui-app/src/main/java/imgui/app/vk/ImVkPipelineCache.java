package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineCacheCreateInfo;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.vkCreatePipelineCache;
import static org.lwjgl.vulkan.VK10.vkDestroyPipelineCache;

public class ImVkPipelineCache {

    private static final Logger LOGGER = Logger.getLogger(ImVkPipelineCache.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot create pipeline cache without a vulkan device");
        }

        LOGGER.fine("creating vulkan pipeline cache");
        createPipelineCache();
        LOGGER.fine("Done creating vulkan pipeline cache");
    }

    public void destroy() {
        destroyPipelineCache();
    }

    private void createPipelineCache() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final VkPipelineCacheCreateInfo createInfo = VkPipelineCacheCreateInfo.calloc(stack)
                .sType$Default();

            final LongBuffer lp = stack.mallocLong(1);
            vkOK(vkCreatePipelineCache(getDevice().getDevice(), createInfo, null, lp));
            nativeHandle = lp.get(0);
        }
    }

    private void destroyPipelineCache() {
        vkDestroyPipelineCache(getDevice().getDevice(), nativeHandle, null);
        nativeHandle = VK_NULL_HANDLE;
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
