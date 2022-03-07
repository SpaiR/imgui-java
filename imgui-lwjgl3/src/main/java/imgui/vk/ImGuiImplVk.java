package imgui.vk;

import imgui.ImDrawData;
import imgui.lwjgl3.vk.ImGuiImplVkNative;
import org.lwjgl.vulkan.VkCommandBuffer;

public final class ImGuiImplVk {

    private ImGuiImplVk() {
    }

    public static void init(final ImGuiImplVkInitInfo info, final long renderPass) {
        ImGuiImplVkNative.nInit(info, renderPass);
    }

    public static void shutdown() {
        ImGuiImplVkNative.shutdown();
    }

    public static void newFrame() {
        ImGuiImplVkNative.newFrame();
    }

    public static void renderDrawData(final ImDrawData drawData, final VkCommandBuffer commandBuffer, final long pipeline) {
        ImGuiImplVkNative.nRenderDrawData(drawData, commandBuffer.address(), pipeline);
    }

    public static void createFontsTexture(final VkCommandBuffer commandBuffer) {
        ImGuiImplVkNative.nCreateFontsTexture(commandBuffer.address());
    }

    public static void destroyFontUploadObjects() {
        ImGuiImplVkNative.destroyFontUploadObjects();
    }

    public static void setMinImageCount(final int minImageCount) {
        ImGuiImplVkNative.setMinImageCount(minImageCount);
    }
}
