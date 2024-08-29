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
        return (uintptr_t)(new ImGuiTextFilter(defaultFilter));
    */

    public boolean draw() {
        return nDraw();
    }

    public boolean draw(final String label) {
        return nDraw(label);
    }

    public boolean draw(final String label, final float width) {
        return nDraw(label, width);
    }

    public boolean draw(final float width) {
        return nDraw(width);
    }

    private native boolean nDraw(); /*
        return THIS->Draw();
    */

    private native boolean nDraw(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = THIS->Draw(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private native boolean nDraw(String obj_label, float width); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = THIS->Draw(label, width);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private native boolean nDraw(float width); /*
        return THIS->Draw("Filter (inc,-exc)", width);
    */

    public boolean passFilter(final String text) {
        return nPassFilter(text);
    }

    private native boolean nPassFilter(String obj_text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = THIS->PassFilter(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    public void build() {
        nBuild();
    }

    private native void nBuild(); /*
        THIS->Build();
    */

    public void clear() {
        nClear();
    }

    private native void nClear(); /*
        THIS->Clear();
    */

    public boolean isActive() {
        return nIsActive();
    }

    private native boolean nIsActive(); /*
        return THIS->IsActive();
    */

    public native String getInputBuffer(); /*
        return env->NewStringUTF(THIS->InputBuf);
    */

    public native void setInputBuffer(String inputBuf); /*
        strncpy(THIS->InputBuf, inputBuf, sizeof(THIS->InputBuf));
    */

    public int getCountGrep() {
        return nGetCountGrep();
    }

    public void setCountGrep(final int value) {
        nSetCountGrep(value);
    }

    private native int nGetCountGrep(); /*
        return THIS->CountGrep;
    */

    private native void nSetCountGrep(int value); /*
        THIS->CountGrep = value;
    */

    /*JNI
        #undef THIS
     */
}
