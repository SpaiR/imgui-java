package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImFontConfig extends ImGuiStructDestroyable {
    public ImFontConfig() {
        super();
    }

    ImFontConfig(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImFontConfig*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        ImFontConfig* cfg = new ImFontConfig();
        cfg->FontDataOwnedByAtlas = false;
        return (intptr_t)cfg;
    */

    // TODO: FontData

    /**
     * TTF/OTF data size
     */
    @BindingField
    public int FontDataSize;

    /**
     * TTF/OTF data ownership taken by the container ImFontAtlas (will delete memory itself).
     */
    @BindingField
    public boolean FontDataOwnedByAtlas;

    /**
     * Index of font within TTF/OTF file
     */
    @BindingField
    public int FontNo;

    /**
     * Size in pixels for rasterizer (more or less maps to the resulting font height).
     */
    @BindingField
    public float SizePixels;

    /**
     * Rasterize at higher quality for sub-pixel positioning.
     * Note the difference between 2 and 3 is minimal so you can reduce this to 2 to save memory.
     * Read https://github.com/nothings/stb/blob/master/tests/oversample/README.md for details.
     */
    @BindingField
    public int OversampleH;

    /**
     * Rasterize at higher quality for sub-pixel positioning.
     * This is not really useful as we don't use sub-pixel positions on the Y axis.
     */
    @BindingField
    public int OversampleV;

    /**
     * Align every glyph to pixel boundary. Useful e.g. if you are merging a non-pixel aligned font with the default font.
     * If enabled, you can set OversampleH/V to 1.
     */
    @BindingField
    public boolean PixelSnapH;

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    @BindingField
    public ImVec2 GlyphExtraSpacing;

    /**
     * Offset all glyphs from this font input.
     */
    @BindingField
    public ImVec2 GlyphOffset;

    // TODO: GlyphRanges

    /**
     * Minimum AdvanceX for glyphs, set Min to align font icons, set both Min/Max to enforce mono-space font
     */
    @BindingField
    public float GlyphMinAdvanceX;

    /**
     * Maximum AdvanceX for glyphs
     */
    @BindingField
    public float GlyphMaxAdvanceX;

    /**
     * Merge into previous ImFont, so you can combine multiple inputs font into one ImFont (e.g. ASCII font + icons + Japanese glyphs).
     * You may want to use GlyphOffset.y when merge font of different heights.
     */
    @BindingField
    public boolean MergeMode;

    /**
     * Settings for custom font builder. THIS IS BUILDER IMPLEMENTATION DEPENDENT. Leave as zero if unsure.
     */
    @BindingField(isFlag = true)
    public int FontBuilderFlags;

    /**
     * Brighten ({@code >}1.0f) or darken ({@code <}1.0f) font output. Brightening small fonts may be a good workaround to make them more readable.
     */
    @BindingField
    public float RasterizerMultiply;

    /**
     * Explicitly specify unicode codepoint of ellipsis character. When fonts are being merged first specified ellipsis will be used.
     */
    @BindingField
    public short EllipsisChar;

    // [Internal]

    /**
     * Name (strictly to ease debugging)
     */
    public native void setName(String name); /*
        strcpy(THIS->Name, name);
    */

    @BindingField
    public ImFont DstFont;

    /*JNI
        #undef THIS
     */
}
