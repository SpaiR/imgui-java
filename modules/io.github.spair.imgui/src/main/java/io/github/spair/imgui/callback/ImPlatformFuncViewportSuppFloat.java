package io.github.spair.imgui.callback;

import io.github.spair.imgui.ImGuiViewport;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*) - Boolean
 */
public abstract class ImPlatformFuncViewportSuppFloat {
    public abstract float get(ImGuiViewport vp);
}
