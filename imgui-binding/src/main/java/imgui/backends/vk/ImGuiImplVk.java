package imgui.backends.vk;

import imgui.ImDrawData;
import imgui.backends.vk.type.ImGuiImplVkInitInfo;

public final class ImGuiImplVk {

    private ImGuiImplVk() {
    }

    /*JNI
    #include "_common.h"
    #include "_vulkan.h"
     */

    public static void nInit(final ImGuiImplVkInitInfo info, final long renderPass) {
        nInit(info.ptr, renderPass);
    }

    //Init(ImGui_ImplVulkan_InitInfo* info, VkRenderPass render_pass)
    public static native void nInit(long info, long renderPass); /*
        ImGui_ImplVulkan_Init((ImGui_ImplVulkan_InitInfo*) info, (VkRenderPass) renderPass);
    */

    public static native void shutdown(); /*
        ImGui_ImplVulkan_Shutdown();
    */

    public static native void newFrame(); /*
        ImGui_ImplVulkan_NewFrame();
    */

    public static void nRenderDrawData(final ImDrawData drawData, final long commandBuffer, final long pipeline) {
        nRenderDrawData(drawData.ptr, commandBuffer, pipeline);
    }

    //RenderDrawData(ImDrawData* draw_data, VkCommandBuffer command_buffer, VkPipeline pipeline = VK_NULL_HANDLE);
    public static native void nRenderDrawData(long drawData, long commandBuffer, long pipeline); /*
        ImGui_ImplVulkan_RenderDrawData((ImDrawData*) drawData, (VkCommandBuffer) commandBuffer, (VkPipeline) pipeline);
    */

    //CreateFontsTexture(VkCommandBuffer command_buffer)
    public static native void nCreateFontsTexture(long commandBuffer); /*
        ImGui_ImplVulkan_CreateFontsTexture((VkCommandBuffer) commandBuffer);
    */

    public static native void destroyFontUploadObjects(); /*
        ImGui_ImplVulkan_DestroyFontUploadObjects();
    */

    public static native void setMinImageCount(int minImageCount); /*
        ImGui_ImplVulkan_SetMinImageCount(minImageCount);
    */

    //FIXME: This is currently implemented in the latest 'master' of Dear ImGui
    //FIXME: Once the latest master is merged in, this should be un-commented
    ////AddTexture(VkSampler sampler, VkImageView image_view, VkImageLayout image_layout);
    //public static native void nAddTexture(long sampler, long imageView, long imageLayout); /*
    //    ImGui_ImplVulkan_AddTexture((VkSampler) sampler, (VkImageView) imageView, (VkImageLayout) imageLayout);
    //*/

}
