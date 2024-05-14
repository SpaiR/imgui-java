package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;

/**
 * Helper: Parse and apply text filters. In format "aaaaa[,bbbb][,ccccc]"
 */
@BindingSource
public final class ImGuiTextFilter extends ImGuiStructDestroyable {
    public ImGuiTextFilter() {
        this("");
    }

    public ImGuiTextFilter(final String defaultFilter) {
        ptr = nCreate(defaultFilter);
    }

    public ImGuiTextFilter(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate("");
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiTextFilter*)STRUCT_PTR)
     */

    private native long nCreate(String defaultFilter); /*
        return (intptr_t)(new ImGuiTextFilter(defaultFilter));
    */

    @BindingMethod
    public native boolean Draw(@OptArg(callValue = "\"Filter (inc,-exc)\"") String label, @OptArg float width);

    @BindingMethod
    public native boolean PassFilter(String text, Void NULL);

    @BindingMethod
    public native void Build();

    @BindingMethod
    public native void Clear();

    @BindingMethod
    public native boolean IsActive();

    public native String getInputBuffer(); /*
        return env->NewStringUTF(THIS->InputBuf);
    */

    public native void setInputBuffer(String inputBuf); /*
        strncpy(THIS->InputBuf, inputBuf, sizeof(THIS->InputBuf));
    */

    @BindingField
    public int CountGrep;

    /*JNI
        #undef THIS
     */
}
