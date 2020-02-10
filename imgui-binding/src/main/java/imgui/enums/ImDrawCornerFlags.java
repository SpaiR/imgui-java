package imgui.enums;

public final class ImDrawCornerFlags {
    private ImDrawCornerFlags() {
    }

    public static final int None = 0;
    /**
     * 0x1
     */
    public static final int TopLeft = 1;
    /**
     * 0x2
     */
    public static final int TopRight = 1 << 1;
    /**
     * 0x4
     */
    public static final int BotLeft = 1 << 2;
    /**
     * 0x8
     */
    public static final int BotRight = 1 << 3;
    /**
     * 0x3
     */
    public static final int Top = TopLeft | TopRight;
    /**
     * 0xC
     */
    public static final int Bot = BotLeft | BotRight;
    /**
     * 0x5
     */
    public static final int Left = TopLeft | BotLeft;

    /**
     * 0xA
     */
    public static final int Right = TopRight | BotRight;
    /**
     * In your function calls you may use ~0 (= all bits sets) instead of All, as a convenience
     */
    public static final int All = 0xF;
}
