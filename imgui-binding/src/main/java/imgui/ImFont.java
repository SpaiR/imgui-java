package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * Font runtime data and rendering
 * ImFontAtlas automatically loads a default embedded font for you when you call GetTexDataAsAlpha8() or GetTexDataAsRGBA32().
 */
public final class ImFont extends ImGuiStructDestroyable {
    private final ImFontGlyph fallbackGlyph = new ImFontGlyph(0);
    private final ImFontGlyph foundGlyph = new ImFontGlyph(0);

    public ImFont() {
    }

    public ImFont(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IM_FONT ((ImFont*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new ImFont());
    */

    // TODO IndexAdvanceX

    /**
     * = FallbackGlyph.AdvanceX
     */
    public native float getFallbackAdvanceX(); /*
        return IM_FONT->FallbackAdvanceX;
    */

    /**
     * = FallbackGlyph.AdvanceX
     */
    public native void setFallbackAdvanceX(float fallbackAdvanceX); /*
        IM_FONT->FallbackAdvanceX = fallbackAdvanceX;
    */

    /**
     * Height of characters/line, set during loading (don't change after loading)
     */
    public native float getFontSize(); /*
        return IM_FONT->FontSize;
    */

    // TODO IndexLookup, Glyphs

    /**
     * = FindGlyph(FontFallbackChar)
     */
    public ImFontGlyph getFallbackGlyph() {
        fallbackGlyph.ptr = nGetFallbackGlyphPtr();
        return fallbackGlyph;
    }

    private native long nGetFallbackGlyphPtr(); /*
        return (intptr_t)IM_FONT->FallbackGlyph;
    */

    // TODO ContainerAtlas, ConfigData

    /**
     * Number of ImFontConfig involved in creating this font.
     * Bigger than 1 when merging multiple font sources into one ImFont.
     */
    public native short getConfigDataCount(); /*
        return IM_FONT->ConfigDataCount;
    */

    /**
     * Replacement character if a glyph isn't found.
     */
    public native short getFallbackChar(); /*
        return IM_FONT->FallbackChar;
    */

    /**
     * Character used for ellipsis rendering.
     */
    public native short getEllipsisChar(); /*
        return IM_FONT->EllipsisChar;
    */

    /**
     * Character used for ellipsis rendering.
     */
    public native void setEllipsisChar(int ellipsisChar); /*
        IM_FONT->EllipsisChar = (ImWchar)ellipsisChar;
    */

    public native boolean getDirtyLookupTables(); /*
        return IM_FONT->DirtyLookupTables;
    */

    public native void setDirtyLookupTables(boolean dirtyLookupTables); /*
        IM_FONT->DirtyLookupTables = dirtyLookupTables;
    */

    /**
     * Base font scale, multiplied by the per-window font scale which you can adjust with SetWindowFontScale()
     */
    public native float getScale(); /*
        return IM_FONT->Scale;
    */

    /**
     * Base font scale, multiplied by the per-window font scale which you can adjust with SetWindowFontScale()
     */
    public native void setScale(float scale); /*
        IM_FONT->Scale = scale;
    */

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    public native float getAscent(); /*
        return IM_FONT->Ascent;
    */

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    public native void setAscent(float ascent); /*
        IM_FONT->Ascent = ascent;
    */

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    public native float getDescent(); /*
        return IM_FONT->Descent;
    */

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    public native void setDescent(float descent); /*
        IM_FONT->Descent = descent;
    */

    /**
     * Total surface in pixels to get an idea of the font rasterization/texture cost (not exact, we approximate the cost of padding between glyphs)
     */
    public native int getMetricsTotalSurface(); /*
        return IM_FONT->MetricsTotalSurface;
    */

    /**
     * Total surface in pixels to get an idea of the font rasterization/texture cost (not exact, we approximate the cost of padding between glyphs)
     */
    public native void setMetricsTotalSurface(int metricsTotalSurface); /*
        IM_FONT->MetricsTotalSurface = metricsTotalSurface;
    */

    // Methods

    public ImFontGlyph findGlyph(final int c) {
        foundGlyph.ptr = nFindGlyph(c);
        return foundGlyph;
    }

    private native long nFindGlyph(int c); /*
        return (intptr_t)IM_FONT->FindGlyph((ImWchar)c);
    */

    public ImFontGlyph findGlyphNoFallback(final int c) {
        foundGlyph.ptr = nFindGlyphNoFallback(c);
        return foundGlyph;
    }

    private native long nFindGlyphNoFallback(int c); /*
        return (intptr_t)IM_FONT->FindGlyphNoFallback((ImWchar)c);
    */

    public native float getCharAdvance(int c); /*
        return IM_FONT->GetCharAdvance((ImWchar)c);
    */

    public native boolean isLoaded(); /*
        return IM_FONT->IsLoaded();
    */

    public native String getDebugName(); /*
        return env->NewStringUTF(IM_FONT->GetDebugName());
    */

    public ImVec2 calcTextSizeA(final float size, final float maxWidth, final float wrapWidth, final String text) {
        final ImVec2 value = new ImVec2();
        calcTextSizeA(value, size, maxWidth, wrapWidth, text);
        return value;
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public native void calcTextSizeA(ImVec2 dstImVec2, float size, float maxWidth, float wrapWidth, String text); /*
        Jni::ImVec2Cpy(env, IM_FONT->CalcTextSizeA(size, maxWidth, wrapWidth, text), dstImVec2);
    */

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public native float calcTextSizeAX(float size, float maxWidth, float wrapWidth, String text); /*
        return IM_FONT->CalcTextSizeA(size, maxWidth, wrapWidth, text).x;
    */

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public native float calcTextSizeAY(float size, float maxWidth, float wrapWidth, String text); /*
        return IM_FONT->CalcTextSizeA(size, maxWidth, wrapWidth, text).y;
    */

    public native String calcWordWrapPositionA(float scale, String text, String textEnd, float wrapWidth); /*
        return env->NewStringUTF(IM_FONT->CalcWordWrapPositionA(scale, text, textEnd, wrapWidth));
    */

    public void renderChar(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final int c) {
        nRenderChar(drawList.ptr, size, posX, posY, col, c);
    }

    private native void nRenderChar(long drawListPtr, float size, float posX, float posY, int col, int c); /*
        IM_FONT->RenderChar((ImDrawList*)drawListPtr, size, ImVec2(posX, posY), col, (ImWchar)c);
    */

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectW, final float clipRectZ, final String text, final String textEnd) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectW, clipRectZ, text, textEnd, 0, false);
    }

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectW, final float clipRectZ, final String text, final String textEnd, final float wrapWidth) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectW, clipRectZ, text, textEnd, wrapWidth, false);
    }

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectW, final float clipRectZ, final String text, final String textEnd, final float wrapWidth, final boolean cpuFineClip) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectW, clipRectZ, text, textEnd, wrapWidth, cpuFineClip);
    }

    private native void nRenderText(long drawListPtr, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectW, float clipRectZ, String text, String textEnd, float wrapWidth, boolean cpuFineClip); /*
        IM_FONT->RenderText((ImDrawList*)drawListPtr, size, ImVec2(posX, posY), col, ImVec4(clipRectX, clipRectY, clipRectW, clipRectZ), text, textEnd, wrapWidth, cpuFineClip);
    */
}
