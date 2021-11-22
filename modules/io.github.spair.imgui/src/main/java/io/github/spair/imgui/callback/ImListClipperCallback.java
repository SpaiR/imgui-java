package io.github.spair.imgui.callback;

import io.github.spair.imgui.ImGuiListClipper;

/**
 * Callback for {@link ImGuiListClipper} class.
 */
public abstract class ImListClipperCallback {
    public abstract void accept(int index);
}
