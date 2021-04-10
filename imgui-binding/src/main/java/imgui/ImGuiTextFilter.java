package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * Helper: Parse and apply text filters. In format "aaaaa[,bbbb][,ccccc]"
 */
public final class ImGuiTextFilter extends ImGuiStructDestroyable {
    public ImGuiTextFilter() {
        this("");
    }

    public ImGuiTextFilter(final String defaultFilter) {
        ptr = nCreate(defaultFilter);
    }

    ImGuiTextFilter(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IMGUI_TEXT_FILTER ((ImGuiTextFilter*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate("");
    }

    private native long nCreate(String defaultFilter); /*
        return (intptr_t)(new ImGuiTextFilter(defaultFilter));
    */

    public boolean draw() {
        return draw("Filter (inc,-exc)");
    }

    public boolean draw(final String label) {
        return draw(label, 0f);
    }

    public native boolean draw(String label, float width); /*
        return IMGUI_TEXT_FILTER->Draw(label, width);
    */

    public native boolean passFilter(String text); /*
        return IMGUI_TEXT_FILTER->PassFilter(text);
    */

    public native void build(); /*
        IMGUI_TEXT_FILTER->Build();
    */

    public native void clear(); /*
        IMGUI_TEXT_FILTER->Clear();
    */

    public native boolean isActive(); /*
        return IMGUI_TEXT_FILTER->IsActive();
    */
}
