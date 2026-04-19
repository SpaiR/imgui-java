package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * Font runtime data. A single logical font that can be baked at multiple sizes (see ImFontBaked via {@code getFontBaked}).
 *
 * <p>In Dear ImGui 1.92 the font subsystem was reworked: {@code ImFont} now represents the font source set,
 * while size-specific data (glyphs, ascent/descent, character metrics) lives on {@code ImFontBaked}.
 * Many previously-exposed fields moved there and are no longer reachable through {@code ImFont}.
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

    /**
     * Font size passed to {@code AddFontXXX()}. Use for legacy code calling {@link imgui.ImGui#pushFont(ImFont)}
     * that expected the original size (use {@code ImGui::GetFontBaked()} in new code).
     */
    public float getLegacySize() {
        return nGetLegacySize();
    }

    /**
     * Font size passed to {@code AddFontXXX()}. Use for legacy code calling {@link imgui.ImGui#pushFont(ImFont)}
     * that expected the original size (use {@code ImGui::GetFontBaked()} in new code).
     */
    public void setLegacySize(final float value) {
        nSetLegacySize(value);
    }

    private native float nGetLegacySize(); /*
        return THIS->LegacySize;
    */

    private native void nSetLegacySize(float value); /*
        THIS->LegacySize = value;
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

    /**
     * Character used if a glyph isn't found (U+FFFD, '?').
     */
    public short getFallbackChar() {
        return nGetFallbackChar();
    }

    /**
     * Character used if a glyph isn't found (U+FFFD, '?').
     */
    public void setFallbackChar(final short value) {
        nSetFallbackChar(value);
    }

    private native short nGetFallbackChar(); /*
        return THIS->FallbackChar;
    */

    private native void nSetFallbackChar(short value); /*
        THIS->FallbackChar = value;
    */

    /**
     * Legacy base font scale (~1.0f), multiplied by the per-window font scale which you can adjust with SetWindowFontScale().
     * Obsolete since 1.92; kept behind {@code IMGUI_DISABLE_OBSOLETE_FUNCTIONS}.
     */
    public float getScale() {
        return nGetScale();
    }

    /**
     * Legacy base font scale (~1.0f), multiplied by the per-window font scale which you can adjust with SetWindowFontScale().
     * Obsolete since 1.92; kept behind {@code IMGUI_DISABLE_OBSOLETE_FUNCTIONS}.
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

    // Methods

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

    /**
     *
     * @deprecated since imgui 1.92; prefer {@code CalcWordWrapPosition(size, ...)}. This overload is kept as a
    legacy redirect; internally it passes {@code LegacySize * scale} as the size.
     */
    @Deprecated
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

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd, final float wrapWidth) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd, wrapWidth);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd, final float wrapWidth) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd, wrapWidth);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd, final float wrapWidth, final int flags) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd, wrapWidth, flags);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd, final float wrapWidth, final int flags) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd, wrapWidth, flags);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final ImVec2 pos, final int col, final ImVec4 clipRect, final String textBegin, final String textEnd, final int flags) {
        nRenderText(drawList.ptr, size, pos.x, pos.y, col, clipRect.x, clipRect.y, clipRect.z, clipRect.w, textBegin, textEnd, flags);
    }

    /**
     * The {@code flags} parameter is {@code ImDrawTextFlags} (since imgui 1.92; previously a {@code bool cpu_fine_clip}).
     */
    public void renderText(final ImDrawList drawList, final float size, final float posX, final float posY, final int col, final float clipRectX, final float clipRectY, final float clipRectZ, final float clipRectW, final String textBegin, final String textEnd, final int flags) {
        nRenderText(drawList.ptr, size, posX, posY, col, clipRectX, clipRectY, clipRectZ, clipRectW, textBegin, textEnd, flags);
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

    private native void nRenderText(long drawList, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectZ, float clipRectW, String textBegin, String textEnd, float wrapWidth, int flags); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 clipRect = ImVec4(clipRectX, clipRectY, clipRectZ, clipRectW);
        THIS->RenderText(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, clipRect, textBegin, textEnd, wrapWidth, flags);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    private native void nRenderText(long drawList, float size, float posX, float posY, int col, float clipRectX, float clipRectY, float clipRectZ, float clipRectW, String textBegin, String textEnd, int flags); /*MANUAL
        auto textBegin = obj_textBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textBegin, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImVec4 clipRect = ImVec4(clipRectX, clipRectY, clipRectZ, clipRectW);
        THIS->RenderText(reinterpret_cast<ImDrawList*>(drawList), size, pos, col, clipRect, textBegin, textEnd, 0.0f, flags);
        if (textBegin != NULL) env->ReleaseStringUTFChars(obj_textBegin, textBegin);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    /*JNI
        #undef THIS
     */
}
