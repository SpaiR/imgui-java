package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;






/**
 * ImGui Club Memory Editor extension for ImGui
 * Repo: <a href="https://github.com/ocornut/imgui_club">https://github.com/ocornut/imgui_club</a>
 */

public final class MemoryEditor extends ImGuiStructDestroyable {
    public MemoryEditor() {
        super();
    }

    public MemoryEditor(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_memedit.h"
        #define THIS ((MemoryEditor*)STRUCT_PTR)
        #define MemoryEditorSizes MemoryEditor::Sizes
     */

    private native long nCreate(); /*
        return (uintptr_t)(new MemoryEditor());
    */

    public boolean getOpen() {
        return nGetOpen();
    }

    public void setOpen(final boolean value) {
        nSetOpen(value);
    }

    private native boolean nGetOpen(); /*
        return THIS->Open;
    */

    private native void nSetOpen(boolean value); /*
        THIS->Open = value;
    */

    public boolean getReadOnly() {
        return nGetReadOnly();
    }

    public void setReadOnly(final boolean value) {
        nSetReadOnly(value);
    }

    private native boolean nGetReadOnly(); /*
        return THIS->ReadOnly;
    */

    private native void nSetReadOnly(boolean value); /*
        THIS->ReadOnly = value;
    */

    public int getCols() {
        return nGetCols();
    }

    public void setCols(final int value) {
        nSetCols(value);
    }

    private native int nGetCols(); /*
        return THIS->Cols;
    */

    private native void nSetCols(int value); /*
        THIS->Cols = value;
    */

    public boolean getOptShowOptions() {
        return nGetOptShowOptions();
    }

    public void setOptShowOptions(final boolean value) {
        nSetOptShowOptions(value);
    }

    private native boolean nGetOptShowOptions(); /*
        return THIS->OptShowOptions;
    */

    private native void nSetOptShowOptions(boolean value); /*
        THIS->OptShowOptions = value;
    */

    public boolean getOptShowDataPreview() {
        return nGetOptShowDataPreview();
    }

    public void setOptShowDataPreview(final boolean value) {
        nSetOptShowDataPreview(value);
    }

    private native boolean nGetOptShowDataPreview(); /*
        return THIS->OptShowDataPreview;
    */

    private native void nSetOptShowDataPreview(boolean value); /*
        THIS->OptShowDataPreview = value;
    */

    public boolean getOptShowHexII() {
        return nGetOptShowHexII();
    }

    public void setOptShowHexII(final boolean value) {
        nSetOptShowHexII(value);
    }

    private native boolean nGetOptShowHexII(); /*
        return THIS->OptShowHexII;
    */

    private native void nSetOptShowHexII(boolean value); /*
        THIS->OptShowHexII = value;
    */

    public boolean getOptShowAscii() {
        return nGetOptShowAscii();
    }

    public void setOptShowAscii(final boolean value) {
        nSetOptShowAscii(value);
    }

    private native boolean nGetOptShowAscii(); /*
        return THIS->OptShowAscii;
    */

    private native void nSetOptShowAscii(boolean value); /*
        THIS->OptShowAscii = value;
    */

    public boolean getOptGreyOutZeroes() {
        return nGetOptGreyOutZeroes();
    }

    public void setOptGreyOutZeroes(final boolean value) {
        nSetOptGreyOutZeroes(value);
    }

    private native boolean nGetOptGreyOutZeroes(); /*
        return THIS->OptGreyOutZeroes;
    */

    private native void nSetOptGreyOutZeroes(boolean value); /*
        THIS->OptGreyOutZeroes = value;
    */

    public boolean getOptUpperCaseHex() {
        return nGetOptUpperCaseHex();
    }

    public void setOptUpperCaseHex(final boolean value) {
        nSetOptUpperCaseHex(value);
    }

    private native boolean nGetOptUpperCaseHex(); /*
        return THIS->OptUpperCaseHex;
    */

    private native void nSetOptUpperCaseHex(boolean value); /*
        THIS->OptUpperCaseHex = value;
    */

    public int getOptMidColsCount() {
        return nGetOptMidColsCount();
    }

    public void setOptMidColsCount(final int value) {
        nSetOptMidColsCount(value);
    }

    private native int nGetOptMidColsCount(); /*
        return THIS->OptMidColsCount;
    */

    private native void nSetOptMidColsCount(int value); /*
        THIS->OptMidColsCount = value;
    */

    public int getOptAddrDigitsCount() {
        return nGetOptAddrDigitsCount();
    }

    public void setOptAddrDigitsCount(final int value) {
        nSetOptAddrDigitsCount(value);
    }

    private native int nGetOptAddrDigitsCount(); /*
        return THIS->OptAddrDigitsCount;
    */

    private native void nSetOptAddrDigitsCount(int value); /*
        THIS->OptAddrDigitsCount = value;
    */

    public float getOptFooterExtraHeight() {
        return nGetOptFooterExtraHeight();
    }

    public void setOptFooterExtraHeight(final float value) {
        nSetOptFooterExtraHeight(value);
    }

    private native float nGetOptFooterExtraHeight(); /*
        return THIS->OptFooterExtraHeight;
    */

    private native void nSetOptFooterExtraHeight(float value); /*
        THIS->OptFooterExtraHeight = value;
    */

    public int getHighlightColor() {
        return nGetHighlightColor();
    }

    public void setHighlightColor(final int value) {
        nSetHighlightColor(value);
    }

    private native int nGetHighlightColor(); /*
        return THIS->HighlightColor;
    */

    private native void nSetHighlightColor(int value); /*
        THIS->HighlightColor = value;
    */

    public void gotoAddrAndHighlight(final long addrMin, final long addrMax) {
        nGotoAddrAndHighlight(addrMin, addrMax);
    }

    private native void nGotoAddrAndHighlight(long addrMin, long addrMax); /*
        THIS->GotoAddrAndHighlight(addrMin, addrMax);
    */

    public void calcSizes(final MemoryEditorSizes s, final long memSize, final long baseDisplayAddr) {
        nCalcSizes(s.ptr, memSize, baseDisplayAddr);
    }

    private native void nCalcSizes(long s, long memSize, long baseDisplayAddr); /*
        THIS->CalcSizes(*reinterpret_cast<MemoryEditorSizes*>(s), memSize, baseDisplayAddr);
    */

    public void drawWindow(final String title, final long memData, final long memSize) {
        nDrawWindow(title, memData, memSize);
    }

    public void drawWindow(final String title, final long memData, final long memSize, final long baseDisplayAddr) {
        nDrawWindow(title, memData, memSize, baseDisplayAddr);
    }

    private native void nDrawWindow(String title, long memData, long memSize); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        THIS->DrawWindow(title, reinterpret_cast<void*>(memData), memSize);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    private native void nDrawWindow(String title, long memData, long memSize, long baseDisplayAddr); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        THIS->DrawWindow(title, reinterpret_cast<void*>(memData), memSize, baseDisplayAddr);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
    */

    public void drawContents(final long memData, final long memSize) {
        nDrawContents(memData, memSize);
    }

    public void drawContents(final long memData, final long memSize, final long baseDisplayAddr) {
        nDrawContents(memData, memSize, baseDisplayAddr);
    }

    private native void nDrawContents(long memData, long memSize); /*
        THIS->DrawContents(reinterpret_cast<void*>(memData), memSize);
    */

    private native void nDrawContents(long memData, long memSize, long baseDisplayAddr); /*
        THIS->DrawContents(reinterpret_cast<void*>(memData), memSize, baseDisplayAddr);
    */

    public void drawOptionsLine(final MemoryEditorSizes s, final long memData, final long memSize, final long baseDisplayAddr) {
        nDrawOptionsLine(s.ptr, memData, memSize, baseDisplayAddr);
    }

    private native void nDrawOptionsLine(long s, long memData, long memSize, long baseDisplayAddr); /*
        THIS->DrawOptionsLine(*reinterpret_cast<MemoryEditorSizes*>(s), reinterpret_cast<void*>(memData), memSize, baseDisplayAddr);
    */

    public void drawPreviewLine(final MemoryEditorSizes s, final long memDataVoid, final long memSize, final long baseDisplayAddr) {
        nDrawPreviewLine(s.ptr, memDataVoid, memSize, baseDisplayAddr);
    }

    private native void nDrawPreviewLine(long s, long memDataVoid, long memSize, long baseDisplayAddr); /*
        THIS->DrawPreviewLine(*reinterpret_cast<MemoryEditorSizes*>(s), reinterpret_cast<void*>(memDataVoid), memSize, baseDisplayAddr);
    */

    /*JNI
        #undef MemoryEditorSizes
        #undef THIS
     */
}
