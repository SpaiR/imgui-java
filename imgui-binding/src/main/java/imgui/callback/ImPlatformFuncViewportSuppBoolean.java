package imgui.callback;

import imgui.ImGuiViewport;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*) -> Boolean
 */
public abstract class ImPlatformFuncViewportSuppBoolean {
    public abstract boolean get(ImGuiViewport vp);
}
