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
     * [Internal]
     *
     * <p>Definition: {@code ImGuiButtonFlags_MouseButtonLeft}
     */
    public static final int MouseButtonDefault_ = 1;
}
