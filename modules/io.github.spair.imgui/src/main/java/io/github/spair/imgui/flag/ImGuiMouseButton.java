package io.github.spair.imgui.flag;

/**
 * Identify a mouse button.
 * Those values are guaranteed to be stable and we frequently use 0/1 directly. Named enums provided for convenience.
 */
public final class ImGuiMouseButton {
    private ImGuiMouseButton() {
    }

    public static final int Left = 0;
    public static final int Right = 1;
    public static final int Middle = 2;
    public static final int COUNT = 5;
}
