package imgui.vk;

import imgui.lwjgl3.vk.type.ImGuiImplVkInitInfoNative;
import org.lwjgl.vulkan.*;

public class ImGuiImplVkInitInfo extends ImGuiImplVkInitInfoNative {

    //Cache LWJGL Vk objects
    private VkInstance instance;
    private VkPhysicalDevice physicalDevice;
    private VkDevice device;
    private VkQueue queue;
    private VkAllocationCallbacks allocator;
    public ImGuiImplVkInitInfo(long ptr) {
        super(ptr);
    }


    public VkInstance getInstance() {
        return instance;
    }

    public void setInstance(VkInstance instance) {
        this.nSetInstance(instance.address());
        this.instance = instance;
    }

    public VkPhysicalDevice getPhysicalDevice() {
        return physicalDevice;
    }

    public void setPhysicalDevice(VkPhysicalDevice physicalDevice) {
        this.nSetPhysicalDevice(physicalDevice.address());
        this.physicalDevice = physicalDevice;
    }

    public VkDevice getDevice() {
        return device;
    }

    public void setDevice(VkDevice device) {
        this.nSetDevice(device.address());
        this.device = device;
    }

    public VkQueue getQueue() {
        return queue;
    }

    public void setQueue(VkQueue queue) {
        this.nSetQueue(queue.address());
        this.queue = queue;
    }

    public void setPipelineCache(long address) {
        this.nSetPipelineCache(address);
    }

    public long getPipelineCache() {
        return this.nGetPipelineCache();
    }

    public void setDescriptorPool(long address) {
        this.nSetDescriptorPool(address);
    }

    public long getDescriptorPool() {
        return this.nGetDescriptorPool();
    }

    public VkAllocationCallbacks getAllocator() {
        return allocator;
    }

    public void setAllocator(VkAllocationCallbacks allocator) {
        this.nSetAllocator(allocator.address());
        this.allocator = allocator;
    }
}
