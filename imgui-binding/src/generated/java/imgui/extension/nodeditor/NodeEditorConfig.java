package imgui.extension.nodeditor;

import imgui.binding.ImGuiStructDestroyable;

public final class NodeEditorConfig extends ImGuiStructDestroyable {
    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_nodeeditor.h"
        #define THIS ((ax::NodeEditor::Config*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ax::NodeEditor::Config());
    */

    public String getSettingsFile() {
        return nGetSettingsFile();
    }

    public void setSettingsFile(final String value) {
        nSetSettingsFile(value);
    }

    private native String nGetSettingsFile(); /*
        return env->NewStringUTF(THIS->SettingsFile);
    */

    private native void nSetSettingsFile(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        SET_STRING_FIELD(THIS->SettingsFile, value);
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    public int getCanvasSizeMode() {
        return nGetCanvasSizeMode();
    }

    public void setCanvasSizeMode(final int value) {
        nSetCanvasSizeMode(value);
    }

    public native int nGetCanvasSizeMode(); /*
        return static_cast<int>(THIS->CanvasSizeMode);
    */

    public native void nSetCanvasSizeMode(int value); /*
        THIS->CanvasSizeMode = static_cast<ax::NodeEditor::CanvasSizeMode>(value);
    */

    public int getDragButtonIndex() {
        return nGetDragButtonIndex();
    }

    public void setDragButtonIndex(final int value) {
        nSetDragButtonIndex(value);
    }

    private native int nGetDragButtonIndex(); /*
        return THIS->DragButtonIndex;
    */

    private native void nSetDragButtonIndex(int value); /*
        THIS->DragButtonIndex = value;
    */

    public int getSelectButtonIndex() {
        return nGetSelectButtonIndex();
    }

    public void setSelectButtonIndex(final int value) {
        nSetSelectButtonIndex(value);
    }

    private native int nGetSelectButtonIndex(); /*
        return THIS->SelectButtonIndex;
    */

    private native void nSetSelectButtonIndex(int value); /*
        THIS->SelectButtonIndex = value;
    */

    public int getNavigateButtonIndex() {
        return nGetNavigateButtonIndex();
    }

    public void setNavigateButtonIndex(final int value) {
        nSetNavigateButtonIndex(value);
    }

    private native int nGetNavigateButtonIndex(); /*
        return THIS->NavigateButtonIndex;
    */

    private native void nSetNavigateButtonIndex(int value); /*
        THIS->NavigateButtonIndex = value;
    */

    public int getContextMenuButtonIndex() {
        return nGetContextMenuButtonIndex();
    }

    public void setContextMenuButtonIndex(final int value) {
        nSetContextMenuButtonIndex(value);
    }

    private native int nGetContextMenuButtonIndex(); /*
        return THIS->ContextMenuButtonIndex;
    */

    private native void nSetContextMenuButtonIndex(int value); /*
        THIS->ContextMenuButtonIndex = value;
    */

    public boolean getEnableSmoothZoom() {
        return nGetEnableSmoothZoom();
    }

    public void setEnableSmoothZoom(final boolean value) {
        nSetEnableSmoothZoom(value);
    }

    private native boolean nGetEnableSmoothZoom(); /*
        return THIS->EnableSmoothZoom;
    */

    private native void nSetEnableSmoothZoom(boolean value); /*
        THIS->EnableSmoothZoom = value;
    */

    public float getSmoothZoomPower() {
        return nGetSmoothZoomPower();
    }

    public void setSmoothZoomPower(final float value) {
        nSetSmoothZoomPower(value);
    }

    private native float nGetSmoothZoomPower(); /*
        return THIS->SmoothZoomPower;
    */

    private native void nSetSmoothZoomPower(float value); /*
        THIS->SmoothZoomPower = value;
    */

    /*JNI
        #undef THIS
     */
}
