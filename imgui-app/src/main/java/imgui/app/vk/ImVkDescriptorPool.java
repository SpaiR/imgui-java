package imgui.app.vk;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;

import java.nio.LongBuffer;
import java.util.logging.Logger;

import static imgui.app.vk.ImVkDebug.vkOK;
import static org.lwjgl.vulkan.VK10.VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT;
import static org.lwjgl.vulkan.VK10.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER;
import static org.lwjgl.vulkan.VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER;
import static org.lwjgl.vulkan.VK10.VK_NULL_HANDLE;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.vkCreateDescriptorPool;
import static org.lwjgl.vulkan.VK10.vkDestroyDescriptorPool;

public class ImVkDescriptorPool {
    private static final Logger LOGGER = Logger.getLogger(ImVkDescriptorPool.class.getName());

    private long nativeHandle = VK_NULL_HANDLE;

    private ImVkDevice device;

    public void create() {
        if (device == null) {
            throw new IllegalStateException("Cannot create command pool without a vulkan device set");
        }

        createDescriptorPool();
    }

    private void createDescriptorPool() {
        try (MemoryStack stack = MemoryStack.stackPush()) {

            //FIXME: We need to dynamically set these
            final VkDescriptorPoolSize.Buffer poolSizes = VkDescriptorPoolSize.calloc(2);
            poolSizes.get(0)
                .type(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
                .descriptorCount(1);
            poolSizes.get(1)
                .type(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
                .descriptorCount(1);

            final VkDescriptorPoolCreateInfo descriptorPoolInfo = VkDescriptorPoolCreateInfo.calloc(stack)
                .sType(VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO)
                .flags(VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT)
                .pPoolSizes(poolSizes)
                .maxSets(1000); //TODO: FIX ME

            final LongBuffer pDescriptorPool = stack.mallocLong(1);
            vkOK(vkCreateDescriptorPool(device.getDevice(), descriptorPoolInfo, null, pDescriptorPool));
            nativeHandle = pDescriptorPool.get(0);
        }
    }

    public void destroy() {
        destroyDescriptorPool();
    }

    private void destroyDescriptorPool() {
        vkDestroyDescriptorPool(device.getDevice(), nativeHandle, null);
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
