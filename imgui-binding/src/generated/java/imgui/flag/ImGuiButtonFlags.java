package imgui.flag;


public final class ImGuiButtonFlags {
    private ImGuiButtonFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * React on left mouse button (default)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int MouseButtonLeft = 1;

    /**
     * React on right mouse button
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int MouseButtonRight = 2;

    /**
     * React on center mouse button
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int MouseButtonMiddle = 4;

    /**
     * [Internal]
     *
     * <p>Definition: {@code ImGuiButtonFlags_MouseButtonLeft | ImGuiButtonFlags_MouseButtonRight | ImGuiButtonFlags_MouseButtonMiddle}
     */
    public static final int MouseButtonMask_ = 7;

    /**
     * InvisibleButton(): do not disable navigation/tabbing. Otherwise disabled by default.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int EnableNav = 8;

    /**
     * Hit testing will allow subsequent widgets to overlap this one. Require previous frame HoveredId to match before being usable. Shortcut to calling SetNextItemAllowOverlap().
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int AllowOverlap = 4096;
}
