package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;

/**
 * Font runtime data and rendering
 * ImFontAtlas automatically loads a default embedded font for you when you call GetTexDataAsAlpha8() or GetTexDataAsRGBA32().
 */
@BindingSource
public final class ImFont extends ImGuiStructDestroyable {
    public ImFont() {
        super();
    }

    public ImFont(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImFont*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImFont());
    */

    // TODO IndexAdvanceX

    /**
     * = FallbackGlyph.AdvanceX
     */
    @BindingField
    public float FallbackAdvanceX;

    /**
     * Height of characters/line, set during loading (don't change after loading)
     */
    @BindingField
    public float FontSize;

    // TODO IndexLookup, Glyphs

    /**
     * = FindGlyph(FontFallbackChar)
     */
    @BindingField
    @ReturnValue(isStatic = true)
    public ImFontGlyph FallbackGlyph;

    // TODO ContainerAtlas, ConfigData

    /**
     * Number of ImFontConfig involved in creating this font.
     * Bigger than 1 when merging multiple font sources into one ImFont.
     */
    @BindingField
    public short ConfigDataCount;

    /**
     * Character used for ellipsis rendering.
     */
    @BindingField
    public short EllipsisChar;

    /**
     * Character used for ellipsis rendering (if a single '...' character isn't found)
     */
    @BindingField
    public short DotChar;

    @BindingField
    public boolean DirtyLookupTables;

    /**
     * Base font scale, multiplied by the per-window font scale which you can adjust with SetWindowFontScale()
     */
    @BindingField
    public float Scale;

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    @BindingField
    public float Ascent;

    @BindingField
    public float Descent;

    /**
     * Total surface in pixels to get an idea of the font rasterization/texture cost (not exact, we approximate the cost of padding between glyphs)
     */
    @BindingField
    public int MetricsTotalSurface;

    // Methods

    @BindingMethod
    public native ImFontGlyph FindGlyph(@ArgValue(callPrefix = "(ImWchar)") int c);

    @BindingMethod
    public native ImFontGlyph FindGlyphNoFallback(@ArgValue(callPrefix = "(ImWchar)") int c);

    @BindingMethod
    public native float GetCharAdvance(@ArgValue(callPrefix = "(ImWchar)") int c);

    @BindingMethod
    public native boolean IsLoaded();

    @BindingMethod
    public native String GetDebugName();

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    @BindingMethod
    public native ImVec2 CalcTextSizeA(float size, float maxWidth, float wrapWidth, String textBegin, @OptArg String textEnd);

    @BindingMethod
    public native String CalcWordWrapPositionA(float scale, String text, String textEnd, float wrapWidth);

    @BindingMethod
    public native void RenderChar(ImDrawList drawList, float size, ImVec2 pos, int col, @ArgValue(callPrefix = "(ImWchar)") int c);

    @BindingMethod
    public native void RenderText(ImDrawList drawList, float size, ImVec2 pos, int col, ImVec4 clipRect, String textBegin, String textEnd, @OptArg(callValue = "0.0f") float wrapWidth, @OptArg boolean cpuFineClip);

    /*JNI
        #undef THIS
     */
}
