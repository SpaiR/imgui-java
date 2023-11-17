package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;

public final class MemoryEditorSizes extends ImGuiStructDestroyable {

    /*JNI
        #include "_memedit.h"

        #define MEMORY_EDITOR_SIZES ((MemoryEditor::Sizes*)STRUCT_PTR)
     */

    public MemoryEditorSizes() {

    }

    public MemoryEditorSizes(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new MemoryEditor::Sizes());
    */

    public native void setAddrDigitsCount(int addrDigitsCount); /*
        MEMORY_EDITOR_SIZES->AddrDigitsCount = addrDigitsCount;
    */

    public native int getAddrDigitsCount(); /*
        return MEMORY_EDITOR_SIZES->AddrDigitsCount;
    */

    public native void setLineHeight(float lineHeight); /*
        MEMORY_EDITOR_SIZES->LineHeight = lineHeight;
    */

    public native float getLineHeight(); /*
        return MEMORY_EDITOR_SIZES->LineHeight;
    */

    public native void setGlyphWidth(float glyphWidth); /*
        MEMORY_EDITOR_SIZES->GlyphWidth = glyphWidth;
    */

    public native float getGlyphWidth(); /*
        return MEMORY_EDITOR_SIZES->GlyphWidth;
    */

    public native void setHexCellWidth(float hexCellWidth); /*
        MEMORY_EDITOR_SIZES->HexCellWidth = hexCellWidth;
    */

    public native float getHexCellWidth(); /*
        return MEMORY_EDITOR_SIZES->HexCellWidth;
    */

    public native void setSpacingBetweenMidCols(float spacingBetweenMidCols); /*
        MEMORY_EDITOR_SIZES->SpacingBetweenMidCols = spacingBetweenMidCols;
    */

    public native float getSpacingBetweenMidCols(); /*
        return MEMORY_EDITOR_SIZES->SpacingBetweenMidCols;
    */

    public native void setPosHexStart(float posHexStart); /*
        MEMORY_EDITOR_SIZES->PosHexStart = posHexStart;
    */

    public native float getPosHexStart(); /*
        return MEMORY_EDITOR_SIZES->PosHexStart;
    */

    public native void setPosHexEnd(float posHexEnd); /*
        MEMORY_EDITOR_SIZES->PosHexEnd = posHexEnd;
    */

    public native float getPosHexEnd(); /*
        return MEMORY_EDITOR_SIZES->PosHexEnd;
    */

    public native void setPosAsciiStart(float posAsciiStart); /*
        MEMORY_EDITOR_SIZES->PosAsciiStart = posAsciiStart;
    */

    public native float getPosAsciiStart(); /*
        return MEMORY_EDITOR_SIZES->PosAsciiStart;
    */

    public native void setPosAsciiEnd(float posAsciiEnd); /*
        MEMORY_EDITOR_SIZES->PosAsciiEnd = posAsciiEnd;
    */

    public native float getPosAsciiEnd(); /*
        return MEMORY_EDITOR_SIZES->PosAsciiEnd;
    */

    public native void setWindowWidth(float windowWidth); /*
        MEMORY_EDITOR_SIZES->WindowWidth = windowWidth;
    */

    public native float getWindowWidth(); /*
        return MEMORY_EDITOR_SIZES->WindowWidth;
    */
}
