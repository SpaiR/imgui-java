package imgui.lwjgl3.vk.callback;

/**
 * Callback to check the result of a vulkan call
 */
public abstract class ImGuiImplVkCheckResultCallback {
    public abstract void callback(int resultCode);
}
