package imgui.enums;

public final class ImFontAtlasFlags {
    private ImFontAtlasFlags() {
    }

    public static final int None = 0;
    /**
     * Don't round the height to next power of two
     */
    public static final int NoPowerOfTwoHeight = 1;
    /**
     * Don't build software mouse cursors into the atlas
     */
    public static final int NoMouseCursors = 1 << 1;
}
