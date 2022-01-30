package imgui.extension.memedit;

import imgui.binding.ImGuiStructDestroyable;

/**
 * ImGui Club Memory Editor extension for ImGui
 * Repo: https://github.com/ocornut/imgui_club
 */
public final class MemoryEditor extends ImGuiStructDestroyable {

     /*JNI
        #include "_club.h"

        #define MEMORY_EDITOR ((MemoryEditor*)STRUCT_PTR)
      */

    public MemoryEditor() {

    }

    public MemoryEditor(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new MemoryEditor());
    */

    public native void gotoAddrAndHighlight(long addrMin, long addrMax); /*
        MEMORY_EDITOR->GotoAddrAndHighlight(addrMin, addrMax);
    */

    public void calcSizes(final MemoryEditorSizes s, final long memSize, final long baseDisplayAddr) {
        nCalcSizes(s.ptr, memSize, baseDisplayAddr);
    }

    public native void nCalcSizes(long ptr, long memSize, long baseDisplayAddr); /*
        MEMORY_EDITOR->CalcSizes(*((MemoryEditor::Sizes*)ptr), static_cast<size_t>(memSize), static_cast<size_t>(baseDisplayAddr));
    */

    public void drawWindow(final String title, final long memData, final long memSize) {
        drawWindow(title, memData, memSize, 0x000);
    }

    public native void drawWindow(String title, long memData, long memSize, long baseDisplayAddr); /*
        MEMORY_EDITOR->DrawWindow(title, reinterpret_cast<void*>(memData), static_cast<size_t>(memSize), static_cast<size_t>(baseDisplayAddr));
    */

    public void drawContents(final long memData, final long memSize) {
        drawContents(memData, memSize, 0x0000);
    }

    public native void drawContents(long memData, long memSize, long baseDisplayAddr); /*
        MEMORY_EDITOR->DrawContents(reinterpret_cast<void*>(memData), static_cast<size_t>(memSize), static_cast<size_t>(baseDisplayAddr));
    */

    public void drawOptionsLine(final MemoryEditorSizes s, final long memData, final long memSize, final long baseDisplayAddr) {
        nDrawOptionsLine(s.ptr, memData, memSize, baseDisplayAddr);
    }

    public native void nDrawOptionsLine(long ptr, long memData, long memSize, long baseDisplayAddr); /*
        MEMORY_EDITOR->DrawOptionsLine(*((MemoryEditor::Sizes*)ptr), reinterpret_cast<void*>(memData), static_cast<size_t>(memSize), static_cast<size_t>(baseDisplayAddr));
    */

    public void drawPreviewLine(final MemoryEditorSizes s, final long memDataVoid, final long memSize, final long baseDisplayAddr) {
        nDrawPreviewLine(s.ptr, memDataVoid, memSize, baseDisplayAddr);
    }

    public native void nDrawPreviewLine(long ptr, long memDataVoid, long memSize, long baseDisplayAddr); /*
        MEMORY_EDITOR->DrawPreviewLine(*((MemoryEditor::Sizes*)ptr), reinterpret_cast<void*>(memDataVoid), static_cast<size_t>(memSize), static_cast<size_t>(baseDisplayAddr));
    */
}
