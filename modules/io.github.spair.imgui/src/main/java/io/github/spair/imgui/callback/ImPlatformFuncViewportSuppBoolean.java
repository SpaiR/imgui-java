package io.github.spair.imgui.callback;

import io.github.spair.imgui.ImGuiViewport;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*) - Boolean
 */
public abstract class ImPlatformFuncViewportSuppBoolean {
    public abstract boolean get(ImGuiViewport vp);
}
