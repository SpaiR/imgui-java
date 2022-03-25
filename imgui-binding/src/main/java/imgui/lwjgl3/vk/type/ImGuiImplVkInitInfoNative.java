package imgui.lwjgl3.vk.type;

import imgui.binding.ImGuiStructDestroyable;
import imgui.lwjgl3.vk.callback.ImGuiVkCheckResultCallback;

public class ImGuiImplVkInitInfoNative extends ImGuiStructDestroyable {

    public ImGuiImplVkInitInfoNative() {

    }

    public ImGuiImplVkInitInfoNative(final long ptr) {
        super(ptr);
    }

    /*JNI
    #include "_common.h"
    #include "_vulkan.h"

    #define IM_VK_INIT_INFO ((ImGui_ImplVulkan_InitInfo*)STRUCT_PTR)
    */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t) malloc(sizeof(ImGui_ImplVulkan_InitInfo));
    */

    /**
     * Set the VkInstance using a pointer
     * @param instance The pointer to the VkInstance
     */
    public native void nSetInstance(long instance); /*
        IM_VK_INIT_INFO->Instance = (VkInstance) instance;
    */

    /**
     * Get the pointer to the VkInstance
     * @return The pointer to the VkInstance
     */
    public native long nGetInstance(); /*
        return (intptr_t) IM_VK_INIT_INFO->Instance;
    */

    /**
     * Set the VkPhysicalDevice using a pointer
     * @param physicalDevice The pointer to the VkPhysicalDevice
     */
    public native void nSetPhysicalDevice(long physicalDevice); /*
        IM_VK_INIT_INFO->PhysicalDevice = (VkPhysicalDevice) physicalDevice;
    */

    /**
     * Get the pointer to the VkPhysicalDevice
     * @return The pointer to the VkPhysicalDevice
     */
    public native long nGetPhysicalDevice(); /*
        return (intptr_t) IM_VK_INIT_INFO->PhysicalDevice;
    */

    /**
     * Set the VkDevice using a pointer
     * @param device The pointer to the VkDevice
     */
    public native void nSetDevice(long device); /*
        IM_VK_INIT_INFO->Device = (VkDevice) device;
    */

    /**
     * Get the pointer to the VkDevice
     * @return The pointer to the VkDevice
     */
    public native long nGetDevice(); /*
        return (intptr_t) IM_VK_INIT_INFO->Device;
    */

    /**
     * Set vulkan queue family
     * @param queueFamily vulkan queue family
     */
    public native void setQueueFamily(int queueFamily); /*
        IM_VK_INIT_INFO->QueueFamily = queueFamily;
    */

    /**
     * Get vulkan queue family
     * @return vulkan queue family
     */
    public native int getQueueFamily(); /*
        return IM_VK_INIT_INFO->QueueFamily;
    */

    /**
     * Set the pointer to the VkQueue
     * @param queue the pointer to the VkQueue
     */
    public native void nSetQueue(long queue); /*
        IM_VK_INIT_INFO->Queue = (VkQueue) queue;
    */

    /**
     * Set the pointer to the VkQueue
     * @return the pointer to the VkQueue
     */
    public native long nGetQueue(); /*
        return (intptr_t) IM_VK_INIT_INFO->Queue;
    */

    /**
     * Set the pointer to the VkPipelineCache
     * @param pipelineCache the pointer to the VkPipelineCache
     */
    public native void nSetPipelineCache(long pipelineCache); /*
        IM_VK_INIT_INFO->PipelineCache = (VkPipelineCache) pipelineCache;
    */

    /**
     * Get the pointer to the VkPipelineCache
     * @return the pointer to the VkPipelineCache
     */
    public native long nGetPipelineCache(); /*
        return (intptr_t) IM_VK_INIT_INFO->PipelineCache;
    */

    /**
     * Set the pointer to the VkDescriptorPool
     * @param descriptorPool the pointer to the VkDescriptorPool
     */
    public native void nSetDescriptorPool(long descriptorPool); /*
        IM_VK_INIT_INFO->DescriptorPool = (VkDescriptorPool) descriptorPool;
    */

    /**
     * Get the pointer to the VkDescriptorPool
     * @return the pointer to the VkDescriptorPool
     */
    public native long nGetDescriptorPool(); /*
        return (intptr_t) IM_VK_INIT_INFO->DescriptorPool;
    */

    /**
     * Set the render subpass
     * @param subpass the render subpass
     */
    public native void setSubpass(int subpass); /*
        IM_VK_INIT_INFO->Subpass = subpass;
    */

    /**
     * Get the render subpass
     * @return the render subpass
     */
    public native int getSubpass(); /*
        return IM_VK_INIT_INFO->Subpass;
    */

    /**
     * Set the min image count
     * Must be greater or equal to 2
     * @param minImageCount the min image count
     */
    public native void setMinImageCount(int minImageCount); /*
        IM_VK_INIT_INFO->MinImageCount = minImageCount;
    */

    /**
     * Get the min image count
     * @return the min image count
     */
    public native int getMinImageCount(); /*
        return IM_VK_INIT_INFO->MinImageCount;
    */

    /**
     * Set the image count
     * Must be greater or equal to min image count
     * @param imageCount the image count
     */
    public native void setImageCount(int imageCount); /*
        IM_VK_INIT_INFO->ImageCount = imageCount;
    */

    /**
     * Get the image count
     * @return the image count
     */
    public native int getImageCount(); /*
        return IM_VK_INIT_INFO->ImageCount;
    */

    /**
     * Set MSAA Samples
     * Must be greater or equal to VK_SAMPLE_COUNT_1_BIT (default to VK_SAMPLE_COUNT_1_BIT = 0)
     * @param msaaSamples MSAA Samples (VK_SAMPLE_COUNT_x)
     */
    public native void setMSAASamples(int msaaSamples); /*
        IM_VK_INIT_INFO->MSAASamples = (VkSampleCountFlagBits) msaaSamples;
    */

    /**
     * Get MSAA Samples
     * This will be returned in the integer form of VkSampleCountFlagBits
     * @return MSAA Samples (VK_SAMPLE_COUNT_x)
     */
    public native int getMSAASamples(); /*
        return (uint32_t) IM_VK_INIT_INFO->MSAASamples;
    */

    /**
     * Set the VkAllocationCallbacks pointer
     * @param allocator the VkAllocationCallbacks pointer
     */
    public native void nSetAllocator(long allocator); /*
        IM_VK_INIT_INFO->Allocator = (VkAllocationCallbacks*) allocator;
    */

    /**
     * Get the VkAllocationCallbacks pointer
     * @return the VkAllocationCallbacks pointer
     */
    public native long nGetAllocator(); /*
        return (intptr_t) IM_VK_INIT_INFO->Allocator;
    */

    public void setCheckVkResultFn(ImGuiVkCheckResultCallback checkVkResultFn) {
        //TODO
    }

    public ImGuiVkCheckResultCallback getCheckVkResultFn() {
        return null; //TODO
    }
}
