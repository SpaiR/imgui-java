package imgui.flag;

public final class ImGuiButtonFlags {
    private ImGuiButtonFlags() {
    }

    public static final int None = 0;
    /**
     * React on left mouse button (default).
     */
    public static final int MouseButtonLeft = 0;
    /**
     *  React on right mouse button.
     */
    public static final int MouseButtonRight = 1 << 1;
    /**
     * React on center mouse button.
     */
    public static final int MouseButtonMiddle = 1 << 2;
}
