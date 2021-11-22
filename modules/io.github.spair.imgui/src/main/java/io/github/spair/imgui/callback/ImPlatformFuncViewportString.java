package io.github.spair.imgui.callback;

import io.github.spair.imgui.ImGuiViewport;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*, String)
 */
public abstract class ImPlatformFuncViewportString {
    public abstract void accept(ImGuiViewport vp, String str);
}
