package imgui.internal;

import imgui.binding.ImGuiStruct;




public class ImGuiWindow extends ImGuiStruct {
    public ImGuiWindow(final long ptr) {
        super(ptr);
        ImGui.init();
    }

    /*JNI
        #include "_common.h"
        #include "_internal.h"
        #define THIS ((ImGuiWindow*)STRUCT_PTR)
     */

     /**
     * Is scrollbar visible?
     */
    public boolean getScrollbarX() {
        return nGetScrollbarX();
    }

    private native boolean nGetScrollbarX(); /*
        return THIS->ScrollbarX;
    */

     /**
     * Is scrollbar visible?
     */
    public boolean getScrollbarY() {
        return nGetScrollbarY();
    }

    private native boolean nGetScrollbarY(); /*
        return THIS->ScrollbarY;
    */

    /*JNI
        #undef THIS
     */
}
