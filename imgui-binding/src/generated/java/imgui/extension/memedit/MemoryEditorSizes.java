package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;

public final class MemoryEditorSizes extends ImGuiStructDestroyable {
    public MemoryEditorSizes() {
        super();
    }

    public MemoryEditorSizes(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_memedit.h"
        #define THIS ((MemoryEditor::Sizes*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new MemoryEditor::Sizes());
    */

    public int getAddrDigitsCount() {
        return nGetAddrDigitsCount();
    }

    public void setAddrDigitsCount(final int value) {
        nSetAddrDigitsCount(value);
    }

    private native int nGetAddrDigitsCount(); /*
        return THIS->AddrDigitsCount;
    */

    private native void nSetAddrDigitsCount(int value); /*
        THIS->AddrDigitsCount = value;
    */

    public float getLineHeight() {
        return nGetLineHeight();
    }

    public void setLineHeight(final float value) {
        nSetLineHeight(value);
    }

    private native float nGetLineHeight(); /*
        return THIS->LineHeight;
    */

    private native void nSetLineHeight(float value); /*
        THIS->LineHeight = value;
    */

    public float getGlyphWidth() {
        return nGetGlyphWidth();
    }

    public void setGlyphWidth(final float value) {
        nSetGlyphWidth(value);
    }

    private native float nGetGlyphWidth(); /*
        return THIS->GlyphWidth;
    */

    private native void nSetGlyphWidth(float value); /*
        THIS->GlyphWidth = value;
    */

    public float getHexCellWidth() {
        return nGetHexCellWidth();
    }

    public void setHexCellWidth(final float value) {
        nSetHexCellWidth(value);
    }

    private native float nGetHexCellWidth(); /*
        return THIS->HexCellWidth;
    */

    private native void nSetHexCellWidth(float value); /*
        THIS->HexCellWidth = value;
    */

    public float getSpacingBetweenMidCols() {
        return nGetSpacingBetweenMidCols();
    }

    public void setSpacingBetweenMidCols(final float value) {
        nSetSpacingBetweenMidCols(value);
    }

    private native float nGetSpacingBetweenMidCols(); /*
        return THIS->SpacingBetweenMidCols;
    */

    private native void nSetSpacingBetweenMidCols(float value); /*
        THIS->SpacingBetweenMidCols = value;
    */

    public float getOffsetHexMinX() {
        return nGetOffsetHexMinX();
    }

    public void setOffsetHexMinX(final float value) {
        nSetOffsetHexMinX(value);
    }

    private native float nGetOffsetHexMinX(); /*
        return THIS->OffsetHexMinX;
    */

    private native void nSetOffsetHexMinX(float value); /*
        THIS->OffsetHexMinX = value;
    */

    public float getOffsetHexMaxX() {
        return nGetOffsetHexMaxX();
    }

    public void setOffsetHexMaxX(final float value) {
        nSetOffsetHexMaxX(value);
    }

    private native float nGetOffsetHexMaxX(); /*
        return THIS->OffsetHexMaxX;
    */

    private native void nSetOffsetHexMaxX(float value); /*
        THIS->OffsetHexMaxX = value;
    */

    public float getOffsetAsciiMinX() {
        return nGetOffsetAsciiMinX();
    }

    public void setOffsetAsciiMinX(final float value) {
        nSetOffsetAsciiMinX(value);
    }

    private native float nGetOffsetAsciiMinX(); /*
        return THIS->OffsetAsciiMinX;
    */

    private native void nSetOffsetAsciiMinX(float value); /*
        THIS->OffsetAsciiMinX = value;
    */

    public float getOffsetAsciiMaxX() {
        return nGetOffsetAsciiMaxX();
    }

    public void setOffsetAsciiMaxX(final float value) {
        nSetOffsetAsciiMaxX(value);
    }

    private native float nGetOffsetAsciiMaxX(); /*
        return THIS->OffsetAsciiMaxX;
    */

    private native void nSetOffsetAsciiMaxX(float value); /*
        THIS->OffsetAsciiMaxX = value;
    */

    public float getWindowWidth() {
        return nGetWindowWidth();
    }

    public void setWindowWidth(final float value) {
        nSetWindowWidth(value);
    }

    private native float nGetWindowWidth(); /*
        return THIS->WindowWidth;
    */

    private native void nSetWindowWidth(float value); /*
        THIS->WindowWidth = value;
    */

    /*JNI
        #undef THIS
     */
}
