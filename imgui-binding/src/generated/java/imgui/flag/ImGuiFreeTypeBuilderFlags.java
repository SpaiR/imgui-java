package imgui.flag;

public final class ImGuiFreeTypeBuilderFlags {
    private ImGuiFreeTypeBuilderFlags() {
    }

    /**
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
     * This is achieved by snapping glyphs to the pixel grid only vertically (Y-axis), as is done by Microsoft's ClearType and Adobe's proprietary font renderer.
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
    /**
     * Enable FreeType color-layered glyphs1
     */
    public static final int LoadColor = 1 << 8;
}
