package imgui.callback;

import imgui.ImGuiViewport;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*)
 */
public abstract class ImPlatformFuncViewport {
    public abstract void accept(ImGuiViewport vp);
}
