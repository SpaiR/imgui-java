package io.github.spair.imgui.callback;

import io.github.spair.imgui.ImGuiViewport;
import io.github.spair.imgui.ImVec2;

/**
 * Callback to represent ImGuiPlatformIO function with args: (ImGuiViewport*) - ImVec2
 */
public abstract class ImPlatformFuncViewportSuppImVec2 {
    public abstract void get(ImGuiViewport vp, ImVec2 dstImVec2);
}
