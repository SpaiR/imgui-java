package imgui.callback;

import imgui.ImGuiViewport;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*) - Boolean
 */
public abstract class ImPlatformFuncViewportSuppFloat {
    public abstract float get(ImGuiViewport vp);
}
