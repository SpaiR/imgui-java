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

    public float getPosHexStart() {
        return nGetPosHexStart();
    }

    public void setPosHexStart(final float value) {
        nSetPosHexStart(value);
    }

    private native float nGetPosHexStart(); /*
        return THIS->PosHexStart;
    */

    private native void nSetPosHexStart(float value); /*
        THIS->PosHexStart = value;
    */

    public float getPosHexEnd() {
        return nGetPosHexEnd();
    }

    public void setPosHexEnd(final float value) {
        nSetPosHexEnd(value);
    }

    private native float nGetPosHexEnd(); /*
        return THIS->PosHexEnd;
    */

    private native void nSetPosHexEnd(float value); /*
        THIS->PosHexEnd = value;
    */

    public float getPosAsciiStart() {
        return nGetPosAsciiStart();
    }

    public void setPosAsciiStart(final float value) {
        nSetPosAsciiStart(value);
    }

    private native float nGetPosAsciiStart(); /*
        return THIS->PosAsciiStart;
    */

    private native void nSetPosAsciiStart(float value); /*
        THIS->PosAsciiStart = value;
    */

    public float getPosAsciiEnd() {
        return nGetPosAsciiEnd();
    }

    public void setPosAsciiEnd(final float value) {
        nSetPosAsciiEnd(value);
    }

    private native float nGetPosAsciiEnd(); /*
        return THIS->PosAsciiEnd;
    */

    private native void nSetPosAsciiEnd(float value); /*
        THIS->PosAsciiEnd = value;
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
