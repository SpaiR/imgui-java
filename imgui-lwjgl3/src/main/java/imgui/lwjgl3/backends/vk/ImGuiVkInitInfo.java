package imgui.lwjgl3.backends.vk;

import imgui.backends.vk.type.ImGuiImplVkInitInfo;
import org.lwjgl.vulkan.VkAllocationCallbacks;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkQueue;

public class ImGuiVkInitInfo extends ImGuiImplVkInitInfo {

    //Cache LWJGL Vk objects
    private VkInstance instance;
    private VkPhysicalDevice physicalDevice;
    private VkDevice device;
    private VkQueue queue;

    public ImGuiVkInitInfo() {

    }

    public ImGuiVkInitInfo(final long ptr) {
        super(ptr);
    }

    /**
     * Get LWJGL Vulkan Instance
     * @return Vulkan Instance
     */
    public VkInstance getInstance() {
        return instance;
    }

    /**
     * Set the LWJGL Vulkan Instance
     * @param instance Vulkan Instance
     */
    public void setInstance(final VkInstance instance) {
        this.nSetInstance(instance.address());
        this.instance = instance;
    }

    /**
     * Get the LWJGL Vulkan Physical Device
     * @return Vulkan Physical Device
     */
    public VkPhysicalDevice getPhysicalDevice() {
        return physicalDevice;
    }

    /**
     * Set the LWJGL Vulkan Physical Device
     * @param physicalDevice Vulkan Physical Device
     */
    public void setPhysicalDevice(final VkPhysicalDevice physicalDevice) {
        this.nSetPhysicalDevice(physicalDevice.address());
        this.physicalDevice = physicalDevice;
    }

    /**
     * Get the LWJGL Vulkan Device
     * @return Vulkan Device
     */
    public VkDevice getDevice() {
        return device;
    }

    /**
     * Set the LWJGL Vulkan Device
     * @param device Vulkan Device
     */
    public void setDevice(final VkDevice device) {
        this.nSetDevice(device.address());
        this.device = device;
    }

    /**
     * Get the LWJGL Vulkan Queue
     * @return Vulkan Queue
     */
    public VkQueue getQueue() {
        return queue;
    }

    /**
     * Set the LWJGL Vulkan Queue
     * @param queue Vulkan Queue
     */
    public void setQueue(final VkQueue queue) {
        this.nSetQueue(queue.address());
        this.queue = queue;
    }

    /**
     * Set the pipeline cache using address (pointer)
     * LWJGL does not have a safe wrapper for VkPipelineCache.
     * Take care to use the correct pointer
     * @param address The pointer to the pipeline cache
     */
    public void setPipelineCache(final long address) {
        this.nSetPipelineCache(address);
    }

    /**
     * Get the pointer to the vulkan pipeline cache.
     * LWJGL does not have a safe wrapper for VkPipelineCache.
     * @return The pointer to the pipeline cache
     */
    public long getPipelineCache() {
        return this.nGetPipelineCache();
    }

    /**
     * Set the descriptor pool using address (pointer)
     * LWJGL does not have a safe wrapper for VkDescriptorPool.
     * Take care to use the correct pointer
     * @param address The pointer to the descriptor pool
     */
    public void setDescriptorPool(final long address) {
        this.nSetDescriptorPool(address);
    }

    /**
     * Get the pointer to the vulkan descriptor pool.
     * LWJGL does not have a safe wrapper for VkDescriptorPool.
     * @return The pointer to the descriptor pool
     */
    public long getDescriptorPool() {
        return this.nGetDescriptorPool();
    }

    /**
     * Set the LWJGL Vulkan Allocation Callback
     * @param allocator Allocation Callback
     */
    public void setAllocator(final VkAllocationCallbacks allocator) {
        if (allocator != null) {
            this.nSetAllocator(allocator.address());
        } else {
            this.nSetAllocator(0L);
        }
    }
}
