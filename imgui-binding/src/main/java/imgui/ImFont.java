package imgui;

/**
 * Font runtime data and rendering
 * ImFontAtlas automatically loads a default embedded font for you when you call GetTexDataAsAlpha8() or GetTexDataAsRGBA32().
 */
public final class ImFont implements ImDestroyable {
    final long ptr;

    private ImFontGlyph fallbackGlyph = null;

    /**
     * This class will create a native structure.
     * Call {@link #destroy()} method to manually free used memory.
     */
    public ImFont() {
        ImGui.touch();
        ptr = nCreate();
    }

    ImFont(final long ptr) {
        this.ptr = ptr;
    }

    @Override
    public void destroy() {
        nDestroy(ptr);
    }

    /*JNI
        #include <imgui.h>
        #include "jni_common.h"

        jfieldID imFontPtrID;

        #define IM_FONT ((ImFont*)env->GetLongField(object, imFontPtrID))
     */

    static native void nInit(); /*
        jclass jImFontClass = env->FindClass("imgui/ImFont");
        imFontPtrID = env->GetFieldID(jImFontClass, "ptr", "J");
    */

    private native long nCreate(); /*
        ImFont* imFont = new ImFont();
        return (long)imFont;
    */

    private native void nDestroy(long ptr); /*
        delete (ImFont*)ptr;
    */

    // TODO IndexAdvanceX

    /**
     * = FallbackGlyph->AdvanceX
     */
    public native float getFallbackAdvanceX(); /*
        return IM_FONT->FallbackAdvanceX;
    */

    /**
     * = FallbackGlyph->AdvanceX
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
        if (fallbackGlyph == null) {
            fallbackGlyph = new ImFontGlyph(nGetFallbackGlyphPtr());
        }
        return fallbackGlyph;
    }

    private native long nGetFallbackGlyphPtr(); /*
        return (long)IM_FONT->FallbackGlyph;
    */

    /**
     * Offset font rendering by xx pixels
     */
    public native void getDisplayOffset(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IM_FONT->DisplayOffset, dstImVec2);
    */

    /**
     * Offset font rendering by xx pixels
     */
    public native void setDisplayOffset(float x, float y); /*
        IM_FONT->DisplayOffset.x = x;
        IM_FONT->DisplayOffset.y = y;
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
    public native void setEllipsisChar(short ellipsisChar); /*
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

    // TODO methods
}
