package imgui.extension.club;

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

    //void CalcSizes(Sizes& s, size_t mem_size, size_t base_display_addr)
    public native void calcSizes(MemoryEditorSizes s, long memSize, long baseDisplayAddr); //TODO

    //void DrawWindow(const char* title, void* mem_data, size_t mem_size, size_t base_display_addr = 0x0000)
    public void drawWindow(String title, long memData, long memSize) {
        drawWindow(title, memData, memSize, 0x000);
    }

    public native void drawWindow(String title, long memData, long memSize, long baseDisplayAddr); //TODO


    //void DrawContents(void* mem_data_void, size_t mem_size, size_t base_display_addr = 0x0000)
    public void drawContents(long memData, long memSize) {
        drawContents(memData, memSize, 0x0000);
    }

    public native void drawContents(long memData, long memSize, long baseDisplayAddress); //TODO


    //void DrawOptionsLine(const Sizes& s, void* mem_data, size_t mem_size, size_t base_display_addr)
    public native void drawOptionsLine(MemoryEditorSizes s, long memData, long memSize, long baseDisplayAddr); //TODO

    //void DrawPreviewLine(const Sizes& s, void* mem_data_void, size_t mem_size, size_t base_display_addr)
    public native void drawPreviewLine(MemoryEditorSizes s, long memDataVoid, long memSize, long baseDisplayAddr); //TODO


}
