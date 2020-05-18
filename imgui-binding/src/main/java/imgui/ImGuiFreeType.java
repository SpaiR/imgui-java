package imgui;

/**
 * Read: https://raw.githubusercontent.com/ocornut/imgui/v1.76/misc/freetype/README.md
 */
public final class ImGuiFreeType {
    private ImGuiFreeType() {
    }

    /*JNI
        #include <imgui.h>
        #include <imgui_freetype.h>
     */

    public static void buildFontAtlas(final ImFontAtlas atlas) {
        nBuildFontAtlas(atlas.ptr, 0);
    }

    public static void buildFontAtlas(final ImFontAtlas atlas, int extraFlags) {
        nBuildFontAtlas(atlas.ptr, extraFlags);
    }

    private static native void nBuildFontAtlas(long atlasPtr, int extraFlags); /*
        ImGuiFreeType::BuildFontAtlas((ImFontAtlas*)atlasPtr, (unsigned int)extraFlags);
    */

    /**
     * Hinting greatly impacts visuals (and glyph sizes).
     * When disabled, FreeType generates blurrier glyphs, more or less matches the stb's output.
     * The Default hinting mode usually looks good, but may distort glyphs in an unusual way.
     * The Light hinting mode generates fuzzier glyphs but better matches Microsoft's rasterizer.
     * <p>
     * You can set those flags on a per font basis in ImFontConfig::RasterizerFlags.
     * Use the 'extra_flags' parameter of BuildFontAtlas() to force a flag on all your fonts.
     */
    public static final class RasterizerFlags {
        /**
         * By default, hinting is enabled and the font's native hinter is preferred over the auto-hinter.
         * Disable hinting. This generally generates 'blurrier' bitmap glyphs when the glyph are rendered in any of the anti-aliased modes.
         */
        public static final int NoHinting = 1;
        /**
         * Disable auto-hinter.
         */
        public static final int NoAutoHint = 1 << 1;
        /**
         * Indicates that the auto-hinter is preferred over the font's native hinter.
         */
        public static final int ForceAutoHint = 1 << 2;
        /**
         * A lighter hinting algorithm for gray-level modes. Many generated glyphs are fuzzier but better resemble their original shape.
         * This is achieved by snapping glyphs to the pixel grid only vertically (Y-axis),
         * as is done by Microsoft's ClearType and Adobe's proprietary font renderer.
         * This preserves inter-glyph spacing in horizontal text.
         */
        public static final int LightHinting = 1 << 3;
        /**
         * Strong hinting algorithm that should only be used for monochrome output.
         */
        public static final int MonoHinting = 1 << 4;
        /**
         * Styling: Should we artificially embolden the font?
         */
        public static final int Bold = 1 << 5;
        /**
         * Styling: Should we slant the font, emulating italic style?
         */
        public static final int Oblique = 1 << 6;
        /**
         * Disable anti-aliasing. Combine this with MonoHinting for best results!
         */
        public static final int Monochrome = 1 << 7;
    }
}
