package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * Font runtime data and rendering
 * ImFontAtlas automatically loads a default embedded font for you when you call GetTexDataAsAlpha8() or GetTexDataAsRGBA32().
 */
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
    public float getFallbackAdvanceX() {
        return nGetFallbackAdvanceX();
    }

    /**
     * = FallbackGlyph.AdvanceX
     */
    public void setFallbackAdvanceX(final float value) {
        nSetFallbackAdvanceX(value);
    }

    private native float nGetFallbackAdvanceX(); /*
        return THIS->FallbackAdvanceX;
    */

    private native void nSetFallbackAdvanceX(float value); /*
        THIS->FallbackAdvanceX = value;
    */

    /**
     * Height of characters/line, set during loading (don't change after loading)
     */
    public float getFontSize() {
        return nGetFontSize();
    }

    /**
     * Height of characters/line, set during loading (don't change after loading)
     */
    public void setFontSize(final float value) {
        nSetFontSize(value);
    }

    private native float nGetFontSize(); /*
        return THIS->FontSize;
    */

    private native void nSetFontSize(float value); /*
        THIS->FontSize = value;
    */

    // TODO IndexLookup, Glyphs

    private static final ImFontGlyph _GETFALLBACKGLYPH_1 = new ImFontGlyph(0);

    /**
     * = FindGlyph(FontFallbackChar)
     */
    public ImFontGlyph getFallbackGlyph() {
        _GETFALLBACKGLYPH_1.ptr = nGetFallbackGlyph();
        return _GETFALLBACKGLYPH_1;
    }

    /**
     * = FindGlyph(FontFallbackChar)
     */
    public void setFallbackGlyph(final ImFontGlyph value) {
        nSetFallbackGlyph(value.ptr);
    }

    private native long nGetFallbackGlyph(); /*
        return (uintptr_t)THIS->FallbackGlyph;
    */

    private native void nSetFallbackGlyph(long value); /*
        THIS->FallbackGlyph = reinterpret_cast<ImFontGlyph*>(value);
    */

    // TODO ContainerAtlas, ConfigData

    /**
     * Number of ImFontConfig involved in creating this font.
     * Bigger than 1 when merging multiple font sources into one ImFont.
     */
    public short getConfigDataCount() {
        return nGetConfigDataCount();
    }

    /**
     * Number of ImFontConfig involved in creating this font.
     * Bigger than 1 when merging multiple font sources into one ImFont.
     */
    public void setConfigDataCount(final short value) {
        nSetConfigDataCount(value);
    }

    private native short nGetConfigDataCount(); /*
        return THIS->ConfigDataCount;
    */

    private native void nSetConfigDataCount(short value); /*
        THIS->ConfigDataCount = value;
    */

    /**
     * Character used for ellipsis rendering.
     */
    public short getEllipsisChar() {
        return nGetEllipsisChar();
    }

    /**
     * Character used for ellipsis rendering.
     */
    public void setEllipsisChar(final short value) {
        nSetEllipsisChar(value);
    }

    private native short nGetEllipsisChar(); /*
        return THIS->EllipsisChar;
    */

    private native void nSetEllipsisChar(short value); /*
        THIS->EllipsisChar = value;
    */

    public short getEllipsisCharCount() {
        return nGetEllipsisCharCount();
    }

    public void setEllipsisCharCount(final short value) {
        nSetEllipsisCharCount(value);
    }

    private native short nGetEllipsisCharCount(); /*
        return THIS->EllipsisCharCount;
    */

    private native void nSetEllipsisCharCount(short value); /*
        THIS->EllipsisCharCount = value;
    */

    public float getEllipsisWidth() {
        return nGetEllipsisWidth();
    }

    public void setEllipsisWidth(final float value) {
        nSetEllipsisWidth(value);
    }

    private native float nGetEllipsisWidth(); /*
        return THIS->EllipsisWidth;
    */

    private native void nSetEllipsisWidth(float value); /*
        THIS->EllipsisWidth = value;
    */

    public float getEllipsisCharStep() {
        return nGetEllipsisCharStep();
    }

    public void setEllipsisCharStep(final float value) {
        nSetEllipsisCharStep(value);
    }

    private native float nGetEllipsisCharStep(); /*
        return THIS->EllipsisCharStep;
    */

    private native void nSetEllipsisCharStep(float value); /*
        THIS->EllipsisCharStep = value;
    */

    public boolean getDirtyLookupTables() {
        return nGetDirtyLookupTables();
    }

    public void setDirtyLookupTables(final boolean value) {
        nSetDirtyLookupTables(value);
    }

    private native boolean nGetDirtyLookupTables(); /*
        return THIS->DirtyLookupTables;
    */

    private native void nSetDirtyLookupTables(boolean value); /*
        THIS->DirtyLookupTables = value;
    */

    /**
     * Base font scale, multiplied by the per-window font scale which you can adjust with SetWindowFontScale()
     */
    public float getScale() {
        return nGetScale();
    }

    /**
     * Base font scale, multiplied by the per-window font scale which you can adjust with SetWindowFontScale()
     */
    public void setScale(final float value) {
        nSetScale(value);
    }

    private native float nGetScale(); /*
        return THIS->Scale;
    */

    private native void nSetScale(float value); /*
        THIS->Scale = value;
    */

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    public float getAscent() {
        return nGetAscent();
    }

    /**
     * Ascent: distance from top to bottom of e.g. 'A' [0..FontSize]
     */
    public void setAscent(final float value) {
        nSetAscent(value);
    }

    private native float nGetAscent(); /*
        return THIS->Ascent;
    */

    private native void nSetAscent(float value); /*
        THIS->Ascent = value;
    */

    public float getDescent() {
        return nGetDescent();
    }

    public void setDescent(final float value) {
        nSetDescent(value);
    }

    private native float nGetDescent(); /*
        return THIS->Descent;
    */

    private native void nSetDescent(float value); /*
        THIS->Descent = value;
    */

    /**
     * Total surface in pixels to get an idea of the font rasterization/texture cost (not exact, we approximate the cost of padding between glyphs)
     */
    public int getMetricsTotalSurface() {
        return nGetMetricsTotalSurface();
    }

    /**
     * Total surface in pixels to get an idea of the font rasterization/texture cost (not exact, we approximate the cost of padding between glyphs)
     */
    public void setMetricsTotalSurface(final int value) {
        nSetMetricsTotalSurface(value);
    }

    private native int nGetMetricsTotalSurface(); /*
        return THIS->MetricsTotalSurface;
    */

    private native void nSetMetricsTotalSurface(int value); /*
        THIS->MetricsTotalSurface = value;
    */

    // Methods

    public ImFontGlyph findGlyph(final int c) {
        return new ImFontGlyph(nFindGlyph(c));
    }

    private native long nFindGlyph(int c); /*
        return (uintptr_t)THIS->FindGlyph((ImWchar)c);
    */

    public ImFontGlyph findGlyphNoFallback(final int c) {
        return new ImFontGlyph(nFindGlyphNoFallback(c));
    }

    private native long nFindGlyphNoFallback(int c); /*
        return (uintptr_t)THIS->FindGlyphNoFallback((ImWchar)c);
    */

    public float getCharAdvance(final int c) {
        return nGetCharAdvance(c);
    }

    private native float nGetCharAdvance(int c); /*
        return THIS->GetCharAdvance((ImWchar)c);
    */

    public boolean isLoaded() {
        return nIsLoaded();
    }

    private native boolean nIsLoaded(); /*
        return THIS->IsLoaded();
    */

    public String getDebugName() {
        return nGetDebugName();
    }

    private native String nGetDebugName(); /*
        return env->NewStringUTF(THIS->GetDebugName());
    */

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public ImVec2 calcTextSizeA(final float size, final float maxWidth, final float wrapWidth, final String textBegin) {
        final ImVec2 dst = new ImVec2();
        nCalcTextSizeA(dst, size, maxWidth, wrapWidth, textBegin);
        return dst;
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public float calcTextSizeAX(final float size, final float maxWidth, final float wrapWidth, final String textBegin) {
        return nCalcTextSizeAX(size, maxWidth, wrapWidth, textBegin);
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public float calcTextSizeAY(final float size, final float maxWidth, final float wrapWidth, final String textBegin) {
        return nCalcTextSizeAY(size, maxWidth, wrapWidth, textBegin);
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public void calcTextSizeA(final ImVec2 dst, final float size, final float maxWidth, final float wrapWidth, final String textBegin) {
        nCalcTextSizeA(dst, size, maxWidth, wrapWidth, textBegin);
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public ImVec2 calcTextSizeA(final float size, final float maxWidth, final float wrapWidth, final String textBegin, final String textEnd) {
        final ImVec2 dst = new ImVec2();
        nCalcTextSizeA(dst, size, maxWidth, wrapWidth, textBegin, textEnd);
        return dst;
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public float calcTextSizeAX(final float size, final float maxWidth, final float wrapWidth, final String textBegin, final String textEnd) {
        return nCalcTextSizeAX(size, maxWidth, wrapWidth, textBegin, textEnd);
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public float calcTextSizeAY(final float size, final float maxWidth, final float wrapWidth, final String textBegin, final String textEnd) {
        return nCalcTextSizeAY(size, maxWidth, wrapWidth, textBegin, textEnd);
    }

    /**
     * 'max_width' stops rendering after a certain width (could be turned into a 2d size). FLT_MAX to disable.
     * 'wrap_width' enable automatic word-wrapping across multiple lines to fit into given width. 0.0f to disable.
     */
    public void calcTextSizeA(final ImVec2 dst, final float size, final float maxWidth, final float wrapWidth, final String textBegin, final String textEnd) {
        nCalcTextSizeA(dst, size, maxWidth, wrapWidth, textBegin, textEnd);
    }

    private native void nCalcTextSizeA(ImVec2 dst, float size, float maxWidth, float wrapWidth, String textBegin); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        Jni::ImVec2Cpy(env, THIS->CalcTextSizeA(size, maxWidth, wrapWidth, textBegin), dst);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
    */

    private native float nCalcTextSizeAX(float size, float maxWidth, float wrapWidth, String obj_textBegin); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto _result = THIS->CalcTextSizeA(size, maxWidth, wrapWidth, textBegin).x;
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        return _result;
    */

    private native float nCalcTextSizeAY(float size, float maxWidth, float wrapWidth, String obj_textBegin); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto _result = THIS->CalcTextSizeA(size, maxWidth, wrapWidth, textBegin).y;
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        return _result;
    */

    private native void nCalcTextSizeA(ImVec2 dst, float size, float maxWidth, float wrapWidth, String textBegin, String textEnd); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        Jni::ImVec2Cpy(env, THIS->CalcTextSizeA(size, maxWidth, wrapWidth, textBegin, textEnd), dst);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native float nCalcTextSizeAX(float size, float maxWidth, float wrapWidth, String obj_textBegin, String obj_textEnd); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        auto _result = THIS->CalcTextSizeA(size, maxWidth, wrapWidth, textBegin, textEnd).x;
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
        return _result;
    */

    private native float nCalcTextSizeAY(float size, float maxWidth, float wrapWidth, String obj_textBegin, String obj_textEnd); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        auto _result = THIS->CalcTextSizeA(size, maxWidth, wrapWidth, textBegin, textEnd).y;
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
        return _result;
    */

    public String calcWordWrapPositionA(final float scale, final String text, final String textEnd, final float wrapWidth) {
        return nCalcWordWrapPositionA(scale, text, textEnd, wrapWidth);
    }

    private native String nCalcWordWrapPositionA(float scale, String obj_text, String obj_textEnd, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        auto _result = env->NewStringUTF(THIS->CalcWordWrapPositionA(scale, text, textEnd, wrapWidth));
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
        return _result;
    */

    public void renderChar(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final int c) {
        nRenderChar(drawList.ptr, size, pos.x, pos.y, col, c);
    }

    public void renderChar(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final int c) {
        nRenderChar(drawList.ptr, size, posX, posY, col, c);
    }

    private native void nRenderChar(long drawList, float size, float posX, float posY, int col, int c); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        THIS->RenderChar(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, (ImWchar)c);
    */

    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd);
    }

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd);
    }

    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd, final float wrapWidth) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd, wrapWidth);
    }

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd, final float wrapWidth) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd, wrapWidth);
    }

    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd, final float wrapWidth, final boolean cpuFineClip) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd, wrapWidth, cpuFineClip);
    }

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd, final float wrapWidth, final boolean cpuFineClip) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd, wrapWidth, cpuFineClip);
    }

    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd, final boolean cpuFineClip) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd, cpuFineClip);
    }

    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd, final boolean cpuFineClip) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd, cpuFineClip);
    }

    private native void nRenderText(long drawList, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectZ, float clipRectW, String textBegin, String textEnd); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 clipRect = ImVec4(clipRectX, clipRectY, clipRectZ, clipRectW);
        THIS->RenderText(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, clipRect, textBegin, textEnd);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nRenderText(long drawList, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectZ, float clipRectW, String textBegin, String textEnd, float wrapWidth); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 clipRect = ImVec4(clipRectX, clipRectY, clipRectZ, clipRectW);
        THIS->RenderText(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, clipRect, textBegin, textEnd, wrapWidth);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nRenderText(long drawList, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectZ, float clipRectW, String textBegin, String textEnd, float wrapWidth, boolean cpuFineClip); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 clipRect = ImVec4(clipRectX, clipRectY, clipRectZ, clipRectW);
        THIS->RenderText(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, clipRect, textBegin, textEnd, wrapWidth, cpuFineClip);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nRenderText(long drawList, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectZ, float clipRectW, String textBegin, String textEnd, boolean cpuFineClip); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 clipRect = ImVec4(clipRectX, clipRectY, clipRectZ, clipRectW);
        THIS->RenderText(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, clipRect, textBegin, textEnd, 0.0f, cpuFineClip);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    /*JNI
        #undef THIS
     */
}
