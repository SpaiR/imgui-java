package imgui;

import imgui.binding.ImGuiStructDestroyable;



/**
 * Hold rendering data for one glyph.
 * (Note: some language parsers may fail to convert the 31+1 bitfield members, in this case maybe drop store a single u32 or we can rework this)
 */

public final class ImFontGlyph extends ImGuiStructDestroyable {
    public ImFontGlyph() {
        super();
    }

    public ImFontGlyph(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImFontGlyph*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImFontGlyph());
    */

     /**
     * Flag to indicate glyph is colored and should generally ignore tinting (make it usable with no shift on little-endian as this is used in loops).
     */
    public int getColored() {
        return nGetColored();
    }

    /**
     * Flag to indicate glyph is colored and should generally ignore tinting (make it usable with no shift on little-endian as this is used in loops).
     */
    public void setColored(final int value) {
        nSetColored(value);
    }

    private native int nGetColored(); /*
        return THIS->Colored;
    */

    private native void nSetColored(int value); /*
        THIS->Colored = value;
    */

     /**
     * Flag to indicate glyph has no visible pixels (e.g. space). Allow early out when rendering.
     */
    public int getVisible() {
        return nGetVisible();
    }

    /**
     * Flag to indicate glyph has no visible pixels (e.g. space). Allow early out when rendering.
     */
    public void setVisible(final int value) {
        nSetVisible(value);
    }

    private native int nGetVisible(); /*
        return THIS->Visible;
    */

    private native void nSetVisible(int value); /*
        THIS->Visible = value;
    */

     /**
     * 0x0000..0xFFFF
     */
    public int getCodepoint() {
        return nGetCodepoint();
    }

    /**
     * 0x0000..0xFFFF
     */
    public void setCodepoint(final int value) {
        nSetCodepoint(value);
    }

    private native int nGetCodepoint(); /*
        return THIS->Codepoint;
    */

    private native void nSetCodepoint(int value); /*
        THIS->Codepoint = value;
    */

     /**
     * Distance to next character (= data from font + ImFontConfig::GlyphExtraSpacing.x baked in)
     */
    public float getAdvanceX() {
        return nGetAdvanceX();
    }

    /**
     * Distance to next character (= data from font + ImFontConfig::GlyphExtraSpacing.x baked in)
     */
    public void setAdvanceX(final float value) {
        nSetAdvanceX(value);
    }

    private native float nGetAdvanceX(); /*
        return THIS->AdvanceX;
    */

    private native void nSetAdvanceX(float value); /*
        THIS->AdvanceX = value;
    */

     /**
     * Glyph corners
     */
    public float getX0() {
        return nGetX0();
    }

    /**
     * Glyph corners
     */
    public void setX0(final float value) {
        nSetX0(value);
    }

    private native float nGetX0(); /*
        return THIS->X0;
    */

    private native void nSetX0(float value); /*
        THIS->X0 = value;
    */

     /**
     * Glyph corners
     */
    public float getY0() {
        return nGetY0();
    }

    /**
     * Glyph corners
     */
    public void setY0(final float value) {
        nSetY0(value);
    }

    private native float nGetY0(); /*
        return THIS->Y0;
    */

    private native void nSetY0(float value); /*
        THIS->Y0 = value;
    */

     /**
     * Glyph corners
     */
    public float getX1() {
        return nGetX1();
    }

    /**
     * Glyph corners
     */
    public void setX1(final float value) {
        nSetX1(value);
    }

    private native float nGetX1(); /*
        return THIS->X1;
    */

    private native void nSetX1(float value); /*
        THIS->X1 = value;
    */

     /**
     * Glyph corners
     */
    public float getY1() {
        return nGetY1();
    }

    /**
     * Glyph corners
     */
    public void setY1(final float value) {
        nSetY1(value);
    }

    private native float nGetY1(); /*
        return THIS->Y1;
    */

    private native void nSetY1(float value); /*
        THIS->Y1 = value;
    */

     /**
     * Texture coordinates
     */
    public float getU0() {
        return nGetU0();
    }

    /**
     * Texture coordinates
     */
    public void setU0(final float value) {
        nSetU0(value);
    }

    private native float nGetU0(); /*
        return THIS->U0;
    */

    private native void nSetU0(float value); /*
        THIS->U0 = value;
    */

     /**
     * Texture coordinates
     */
    public float getV0() {
        return nGetV0();
    }

    /**
     * Texture coordinates
     */
    public void setV0(final float value) {
        nSetV0(value);
    }

    private native float nGetV0(); /*
        return THIS->V0;
    */

    private native void nSetV0(float value); /*
        THIS->V0 = value;
    */

     /**
     * Texture coordinates
     */
    public float getU1() {
        return nGetU1();
    }

    /**
     * Texture coordinates
     */
    public void setU1(final float value) {
        nSetU1(value);
    }

    private native float nGetU1(); /*
        return THIS->U1;
    */

    private native void nSetU1(float value); /*
        THIS->U1 = value;
    */

     /**
     * Texture coordinates
     */
    public float getV1() {
        return nGetV1();
    }

    /**
     * Texture coordinates
     */
    public void setV1(final float value) {
        nSetV1(value);
    }

    private native float nGetV1(); /*
        return THIS->V1;
    */

    private native void nSetV1(float value); /*
        THIS->V1 = value;
    */

    /*JNI
        #undef THIS
     */
}
