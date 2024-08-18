package imgui.flag;


public final class ImGuiFreeTypeBuilderFlags {
    private ImGuiFreeTypeBuilderFlags() {
    }

    /**
     * Disable hinting. This generally generates 'blurrier' bitmap glyphs when the glyph are rendered in any of the anti-aliased modes.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoHinting = 1;

    /**
     * Disable auto-hinter.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoAutoHint = 2;

    /**
     * Indicates that the auto-hinter is preferred over the font's native hinter.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int ForceAutoHint = 4;

    /**
     * A lighter hinting algorithm for gray-level modes. Many generated glyphs are fuzzier but better resemble their original shape. This is achieved by snapping glyphs to the pixel grid only vertically (Y-axis), as is done by Microsoft's ClearType and Adobe's proprietary font renderer. This preserves inter-glyph spacing in horizontal text.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int LightHinting = 8;

    /**
     * Strong hinting algorithm that should only be used for monochrome output.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int MonoHinting = 16;

    /**
     * Styling: Should we artificially embolden the font?
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int Bold = 32;

    /**
     * Styling: Should we slant the font, emulating italic style?
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int Oblique = 64;

    /**
     * Disable anti-aliasing. Combine this with MonoHinting for best results!
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int Monochrome = 128;

    /**
     * Enable FreeType color-layered glyphs
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int LoadColor = 256;

    /**
     * Enable FreeType bitmap glyphs
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int Bitmap = 512;
}
