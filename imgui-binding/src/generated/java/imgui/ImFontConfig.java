package imgui;

import imgui.binding.ImGuiStructDestroyable;

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
        return (uintptr_t)cfg;
    */

    /**
     * TTF/OTF data
     */
    public native byte[] getFontData(); /*
        int size = THIS->FontDataSize;
        jbyteArray jBuf = env->NewByteArray(size);
        env->SetByteArrayRegion(jBuf, 0, size, (jbyte*)THIS->FontData);
        return jBuf;
    */

    /**
     * TTF/OTF data
     */
    public native void setFontData(byte[] fontData); /*
        THIS->FontData = &fontData[0];
    */

    /**
     * TTF/OTF data size
     */
    public int getFontDataSize() {
        return nGetFontDataSize();
    }

    /**
     * TTF/OTF data size
     */
    public void setFontDataSize(final int value) {
        nSetFontDataSize(value);
    }

    private native int nGetFontDataSize(); /*
        return THIS->FontDataSize;
    */

    private native void nSetFontDataSize(int value); /*
        THIS->FontDataSize = value;
    */

    /**
     * TTF/OTF data ownership taken by the container ImFontAtlas (will delete memory itself).
     */
    public boolean getFontDataOwnedByAtlas() {
        return nGetFontDataOwnedByAtlas();
    }

    /**
     * TTF/OTF data ownership taken by the container ImFontAtlas (will delete memory itself).
     */
    public void setFontDataOwnedByAtlas(final boolean value) {
        nSetFontDataOwnedByAtlas(value);
    }

    private native boolean nGetFontDataOwnedByAtlas(); /*
        return THIS->FontDataOwnedByAtlas;
    */

    private native void nSetFontDataOwnedByAtlas(boolean value); /*
        THIS->FontDataOwnedByAtlas = value;
    */

    /**
     * Index of font within TTF/OTF file
     */
    public int getFontNo() {
        return nGetFontNo();
    }

    /**
     * Index of font within TTF/OTF file
     */
    public void setFontNo(final int value) {
        nSetFontNo(value);
    }

    private native int nGetFontNo(); /*
        return THIS->FontNo;
    */

    private native void nSetFontNo(int value); /*
        THIS->FontNo = value;
    */

    /**
     * Size in pixels for rasterizer (more or less maps to the resulting font height).
     */
    public float getSizePixels() {
        return nGetSizePixels();
    }

    /**
     * Size in pixels for rasterizer (more or less maps to the resulting font height).
     */
    public void setSizePixels(final float value) {
        nSetSizePixels(value);
    }

    private native float nGetSizePixels(); /*
        return THIS->SizePixels;
    */

    private native void nSetSizePixels(float value); /*
        THIS->SizePixels = value;
    */

    /**
     * Rasterize at higher quality for sub-pixel positioning.
     * Note the difference between 2 and 3 is minimal so you can reduce this to 2 to save memory.
     * Read https://github.com/nothings/stb/blob/master/tests/oversample/README.md for details.
     */
    public int getOversampleH() {
        return nGetOversampleH();
    }

    /**
     * Rasterize at higher quality for sub-pixel positioning.
     * Note the difference between 2 and 3 is minimal so you can reduce this to 2 to save memory.
     * Read https://github.com/nothings/stb/blob/master/tests/oversample/README.md for details.
     */
    public void setOversampleH(final int value) {
        nSetOversampleH(value);
    }

    private native int nGetOversampleH(); /*
        return THIS->OversampleH;
    */

    private native void nSetOversampleH(int value); /*
        THIS->OversampleH = value;
    */

    /**
     * Rasterize at higher quality for sub-pixel positioning.
     * This is not really useful as we don't use sub-pixel positions on the Y axis.
     */
    public int getOversampleV() {
        return nGetOversampleV();
    }

    /**
     * Rasterize at higher quality for sub-pixel positioning.
     * This is not really useful as we don't use sub-pixel positions on the Y axis.
     */
    public void setOversampleV(final int value) {
        nSetOversampleV(value);
    }

    private native int nGetOversampleV(); /*
        return THIS->OversampleV;
    */

    private native void nSetOversampleV(int value); /*
        THIS->OversampleV = value;
    */

    /**
     * Align every glyph to pixel boundary. Useful e.g. if you are merging a non-pixel aligned font with the default font.
     * If enabled, you can set OversampleH/V to 1.
     */
    public boolean getPixelSnapH() {
        return nGetPixelSnapH();
    }

    /**
     * Align every glyph to pixel boundary. Useful e.g. if you are merging a non-pixel aligned font with the default font.
     * If enabled, you can set OversampleH/V to 1.
     */
    public void setPixelSnapH(final boolean value) {
        nSetPixelSnapH(value);
    }

    private native boolean nGetPixelSnapH(); /*
        return THIS->PixelSnapH;
    */

    private native void nSetPixelSnapH(boolean value); /*
        THIS->PixelSnapH = value;
    */

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    public ImVec2 getGlyphExtraSpacing() {
        final ImVec2 dst = new ImVec2();
        nGetGlyphExtraSpacing(dst);
        return dst;
    }

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    public float getGlyphExtraSpacingX() {
        return nGetGlyphExtraSpacingX();
    }

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    public float getGlyphExtraSpacingY() {
        return nGetGlyphExtraSpacingY();
    }

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    public void getGlyphExtraSpacing(final ImVec2 dst) {
        nGetGlyphExtraSpacing(dst);
    }

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    public void setGlyphExtraSpacing(final ImVec2 value) {
        nSetGlyphExtraSpacing(value.x, value.y);
    }

    /**
     * Extra spacing (in pixels) between glyphs. Only X axis is supported for now.
     */
    public void setGlyphExtraSpacing(final float valueX, final float valueY) {
        nSetGlyphExtraSpacing(valueX, valueY);
    }

    private native void nGetGlyphExtraSpacing(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->GlyphExtraSpacing, dst);
    */

    private native float nGetGlyphExtraSpacingX(); /*
        return THIS->GlyphExtraSpacing.x;
    */

    private native float nGetGlyphExtraSpacingY(); /*
        return THIS->GlyphExtraSpacing.y;
    */

    private native void nSetGlyphExtraSpacing(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->GlyphExtraSpacing = value;
    */

    /**
     * Offset all glyphs from this font input.
     */
    public ImVec2 getGlyphOffset() {
        final ImVec2 dst = new ImVec2();
        nGetGlyphOffset(dst);
        return dst;
    }

    /**
     * Offset all glyphs from this font input.
     */
    public float getGlyphOffsetX() {
        return nGetGlyphOffsetX();
    }

    /**
     * Offset all glyphs from this font input.
     */
    public float getGlyphOffsetY() {
        return nGetGlyphOffsetY();
    }

    /**
     * Offset all glyphs from this font input.
     */
    public void getGlyphOffset(final ImVec2 dst) {
        nGetGlyphOffset(dst);
    }

    /**
     * Offset all glyphs from this font input.
     */
    public void setGlyphOffset(final ImVec2 value) {
        nSetGlyphOffset(value.x, value.y);
    }

    /**
     * Offset all glyphs from this font input.
     */
    public void setGlyphOffset(final float valueX, final float valueY) {
        nSetGlyphOffset(valueX, valueY);
    }

    private native void nGetGlyphOffset(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->GlyphOffset, dst);
    */

    private native float nGetGlyphOffsetX(); /*
        return THIS->GlyphOffset.x;
    */

    private native float nGetGlyphOffsetY(); /*
        return THIS->GlyphOffset.y;
    */

    private native void nSetGlyphOffset(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->GlyphOffset = value;
    */

    private short[] glyphRanges;

    /**
     * Pointer to a user-provided list of Unicode range (2 value per range, values are inclusive, zero-terminated list).
     * THE ARRAY DATA NEEDS TO PERSIST AS LONG AS THE FONT IS ALIVE.
     */
    public short[] getGlyphRanges() {
        return glyphRanges;
    }

    /**
     * Pointer to a user-provided list of Unicode range (2 value per range, values are inclusive, zero-terminated list).
     * THE ARRAY DATA NEEDS TO PERSIST AS LONG AS THE FONT IS ALIVE.
     */
    public void setGlyphRanges(final short[] glyphRanges) {
        this.glyphRanges = glyphRanges;
        nSetGlyphRanges(glyphRanges);
    }

    private native void nSetGlyphRanges(short[] glyphRanges); /*
        THIS->GlyphRanges = glyphRanges != NULL ? (ImWchar*)&glyphRanges[0] : NULL;
    */

    /**
     * Minimum AdvanceX for glyphs, set Min to align font icons, set both Min/Max to enforce mono-space font
     */
    public float getGlyphMinAdvanceX() {
        return nGetGlyphMinAdvanceX();
    }

    /**
     * Minimum AdvanceX for glyphs, set Min to align font icons, set both Min/Max to enforce mono-space font
     */
    public void setGlyphMinAdvanceX(final float value) {
        nSetGlyphMinAdvanceX(value);
    }

    private native float nGetGlyphMinAdvanceX(); /*
        return THIS->GlyphMinAdvanceX;
    */

    private native void nSetGlyphMinAdvanceX(float value); /*
        THIS->GlyphMinAdvanceX = value;
    */

    /**
     * Maximum AdvanceX for glyphs
     */
    public float getGlyphMaxAdvanceX() {
        return nGetGlyphMaxAdvanceX();
    }

    /**
     * Maximum AdvanceX for glyphs
     */
    public void setGlyphMaxAdvanceX(final float value) {
        nSetGlyphMaxAdvanceX(value);
    }

    private native float nGetGlyphMaxAdvanceX(); /*
        return THIS->GlyphMaxAdvanceX;
    */

    private native void nSetGlyphMaxAdvanceX(float value); /*
        THIS->GlyphMaxAdvanceX = value;
    */

    /**
     * Merge into previous ImFont, so you can combine multiple inputs font into one ImFont (e.g. ASCII font + icons + Japanese glyphs).
     * You may want to use GlyphOffset.y when merge font of different heights.
     */
    public boolean getMergeMode() {
        return nGetMergeMode();
    }

    /**
     * Merge into previous ImFont, so you can combine multiple inputs font into one ImFont (e.g. ASCII font + icons + Japanese glyphs).
     * You may want to use GlyphOffset.y when merge font of different heights.
     */
    public void setMergeMode(final boolean value) {
        nSetMergeMode(value);
    }

    private native boolean nGetMergeMode(); /*
        return THIS->MergeMode;
    */

    private native void nSetMergeMode(boolean value); /*
        THIS->MergeMode = value;
    */

    /**
     * Settings for custom font builder. THIS IS BUILDER IMPLEMENTATION DEPENDENT. Leave as zero if unsure.
     */
    public int getFontBuilderFlags() {
        return nGetFontBuilderFlags();
    }

    /**
     * Settings for custom font builder. THIS IS BUILDER IMPLEMENTATION DEPENDENT. Leave as zero if unsure.
     */
    public void setFontBuilderFlags(final int value) {
        nSetFontBuilderFlags(value);
    }

    /**
     * Settings for custom font builder. THIS IS BUILDER IMPLEMENTATION DEPENDENT. Leave as zero if unsure.
     */
    public void addFontBuilderFlags(final int flags) {
        setFontBuilderFlags(getFontBuilderFlags() | flags);
    }

    /**
     * Settings for custom font builder. THIS IS BUILDER IMPLEMENTATION DEPENDENT. Leave as zero if unsure.
     */
    public void removeFontBuilderFlags(final int flags) {
        setFontBuilderFlags(getFontBuilderFlags() & ~(flags));
    }

    /**
     * Settings for custom font builder. THIS IS BUILDER IMPLEMENTATION DEPENDENT. Leave as zero if unsure.
     */
    public boolean hasFontBuilderFlags(final int flags) {
        return (getFontBuilderFlags() & flags) != 0;
    }

    private native int nGetFontBuilderFlags(); /*
        return THIS->FontBuilderFlags;
    */

    private native void nSetFontBuilderFlags(int value); /*
        THIS->FontBuilderFlags = value;
    */

    /**
     * Brighten ({@code >}1.0f) or darken ({@code <}1.0f) font output. Brightening small fonts may be a good workaround to make them more readable.
     */
    public float getRasterizerMultiply() {
        return nGetRasterizerMultiply();
    }

    /**
     * Brighten ({@code >}1.0f) or darken ({@code <}1.0f) font output. Brightening small fonts may be a good workaround to make them more readable.
     */
    public void setRasterizerMultiply(final float value) {
        nSetRasterizerMultiply(value);
    }

    private native float nGetRasterizerMultiply(); /*
        return THIS->RasterizerMultiply;
    */

    private native void nSetRasterizerMultiply(float value); /*
        THIS->RasterizerMultiply = value;
    */

    /**
     * Explicitly specify unicode codepoint of ellipsis character. When fonts are being merged first specified ellipsis will be used.
     */
    public short getEllipsisChar() {
        return nGetEllipsisChar();
    }

    /**
     * Explicitly specify unicode codepoint of ellipsis character. When fonts are being merged first specified ellipsis will be used.
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

    // [Internal]

    /**
     * Name (strictly to ease debugging)
     */
    public native void setName(String name); /*
        strcpy(THIS->Name, name);
    */

    public ImFont getDstFont() {
        return new ImFont(nGetDstFont());
    }

    public void setDstFont(final ImFont value) {
        nSetDstFont(value.ptr);
    }

    private native long nGetDstFont(); /*
        return (uintptr_t)THIS->DstFont;
    */

    private native void nSetDstFont(long value); /*
        THIS->DstFont = reinterpret_cast<ImFont*>(value);
    */

    /*JNI
        #undef THIS
     */
}
