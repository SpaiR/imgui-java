package imgui.internal;

import imgui.binding.ImGuiStruct;

public class ImGuiWindow extends ImGuiStruct {
    public ImGuiWindow(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #include <imgui_internal.h>

        #define IMGUI_WINDOW ((ImGuiWindow*)STRUCT_PTR)
     */

    /**
     * Is scrollbar visible?
     */
    public native boolean getScrollbarX(); /*
       return IMGUI_WINDOW->ScrollbarX;
    */

    /**
     * Is scrollbar visible?
     */
    public native boolean getScrollbarY(); /*
       return IMGUI_WINDOW->ScrollbarY;
    */

}
