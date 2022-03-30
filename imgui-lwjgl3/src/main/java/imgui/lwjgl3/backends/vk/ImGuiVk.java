package imgui.lwjgl3.backends.vk;

import imgui.ImDrawData;
import imgui.backends.vk.ImGuiImplVk;
import org.lwjgl.vulkan.VkCommandBuffer;

public final class ImGuiVk {

    private ImGuiVk() {
    }

    public static void init(final ImGuiVkInitInfo info, final long renderPass) {
        ImGuiImplVk.nInit(info, renderPass);
    }

    public static void shutdown() {
        ImGuiImplVk.shutdown();
    }

    public static void newFrame() {
        ImGuiImplVk.newFrame();
    }

    public static void renderDrawData(final ImDrawData drawData, final VkCommandBuffer commandBuffer, final long pipeline) {
        ImGuiImplVk.nRenderDrawData(drawData, commandBuffer.address(), pipeline);
    }

    public static void createFontsTexture(final VkCommandBuffer commandBuffer) {
        ImGuiImplVk.nCreateFontsTexture(commandBuffer.address());
    }

    public static void destroyFontUploadObjects() {
        ImGuiImplVk.destroyFontUploadObjects();
    }

    public static void setMinImageCount(final int minImageCount) {
        ImGuiImplVk.setMinImageCount(minImageCount);
    }
}
