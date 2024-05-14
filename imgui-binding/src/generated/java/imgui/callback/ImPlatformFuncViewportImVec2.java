package imgui.callback;

import imgui.ImGuiViewport;
import imgui.ImVec2;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*, ImVec2)
 */
public abstract class ImPlatformFuncViewportImVec2 {
    public abstract void accept(ImGuiViewport vp, ImVec2 imVec2);
}
