package imgui.flag;


/**
 * Renamed from {@code ImGuiFreeTypeBuilderFlags} in imgui 1.92.
 */
public final class ImGuiFreeTypeLoaderFlags {
    private ImGuiFreeTypeLoaderFlags() {
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

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_NoHinting}
     */
    public static final int ImGuiFreeTypeBuilderFlags_NoHinting = 1;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_NoAutoHint}
     */
    public static final int ImGuiFreeTypeBuilderFlags_NoAutoHint = 2;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_ForceAutoHint}
     */
    public static final int ImGuiFreeTypeBuilderFlags_ForceAutoHint = 4;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_LightHinting}
     */
    public static final int ImGuiFreeTypeBuilderFlags_LightHinting = 8;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_MonoHinting}
     */
    public static final int ImGuiFreeTypeBuilderFlags_MonoHinting = 16;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_Bold}
     */
    public static final int ImGuiFreeTypeBuilderFlags_Bold = 32;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_Oblique}
     */
    public static final int ImGuiFreeTypeBuilderFlags_Oblique = 64;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_Monochrome}
     */
    public static final int ImGuiFreeTypeBuilderFlags_Monochrome = 128;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_LoadColor}
     */
    public static final int ImGuiFreeTypeBuilderFlags_LoadColor = 256;

    /**
     * Definition: {@code ImGuiFreeTypeLoaderFlags_Bitmap}
     */
    public static final int ImGuiFreeTypeBuilderFlags_Bitmap = 512;
}
