package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;

/**
 * ImGui Club Memory Editor extension for ImGui
 * Repo: <a href="https://github.com/ocornut/imgui_club">https://github.com/ocornut/imgui_club</a>
 */
@BindingSource
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

    @BindingField
    public boolean Open;

    @BindingField
    public boolean ReadOnly;

    @BindingField
    public int Cols;

    @BindingField
    public boolean OptShowOptions;

    @BindingField
    public boolean OptShowDataPreview;

    @BindingField
    public boolean OptShowHexII;

    @BindingField
    public boolean OptShowAscii;

    @BindingField
    public boolean OptGreyOutZeroes;

    @BindingField
    public boolean OptUpperCaseHex;

    @BindingField
    public int OptMidColsCount;

    @BindingField
    public int OptAddrDigitsCount;

    @BindingField
    public float OptFooterExtraHeight;

    @BindingField
    public int HighlightColor;

    @BindingMethod
    public native void GotoAddrAndHighlight(long addrMin, long addrMax);

    @BindingMethod
    public native void CalcSizes(@ArgValue(callPrefix = "*") MemoryEditorSizes s, long memSize, long baseDisplayAddr);

    @BindingMethod
    public native void DrawWindow(String title, @ArgValue(reinterpretCast = "void*") long memData, long memSize, @OptArg long baseDisplayAddr);

    @BindingMethod
    public native void DrawContents(@ArgValue(reinterpretCast = "void*") long memData, long memSize, @OptArg long baseDisplayAddr);

    @BindingMethod
    public native void DrawOptionsLine(@ArgValue(callPrefix = "*") MemoryEditorSizes s, @ArgValue(reinterpretCast = "void*") long memData, long memSize, long baseDisplayAddr);

    @BindingMethod
    public native void DrawPreviewLine(@ArgValue(callPrefix = "*") MemoryEditorSizes s, @ArgValue(reinterpretCast = "void*") long memDataVoid, long memSize, long baseDisplayAddr);

    /*JNI
        #undef MemoryEditorSizes
        #undef THIS
     */
}
