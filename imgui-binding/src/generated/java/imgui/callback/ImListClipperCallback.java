package imgui.callback;

/**
 * Callback for {@link imgui.ImGuiListClipper} class.
 */
public abstract class ImListClipperCallback {
    public abstract void accept(int index);
}
