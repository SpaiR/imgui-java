package io.github.spair.imgui.flag;

/**
 * Flags for ImFontAtlas build
 */
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
    /**
     * Don't build thick line textures into the atlas (save a little texture memory).
     * The AntiAliasedLinesUseTex features uses them, otherwise they will be rendered using polygons (more expensive for CPU/GPU).
     */
    public static final int NoBakedLines = 1 << 2;
}
