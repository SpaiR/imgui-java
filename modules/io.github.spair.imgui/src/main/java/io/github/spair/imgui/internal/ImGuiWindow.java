package io.github.spair.imgui.internal;

import io.github.spair.imgui.binding.ImGuiStruct;

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
    public native boolean isScrollbarX(); /*
       return IMGUI_WINDOW->ScrollbarX;
    */

    /**
     * Is scrollbar visible?
     */
    public native boolean isScrollbarY(); /*
       return IMGUI_WINDOW->ScrollbarY;
    */

}
