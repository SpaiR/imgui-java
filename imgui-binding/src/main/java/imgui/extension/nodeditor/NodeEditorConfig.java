package imgui.extension.nodeditor;

import imgui.binding.ImGuiStructDestroyable;

public final class NodeEditorConfig extends ImGuiStructDestroyable {
    public NodeEditorConfig() {
    }

    public NodeEditorConfig(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_nodeeditor.h"

        namespace ed = ax::NodeEditor;

        #define IM_NODE_EDITOR_CONFIG ((ed::Config*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new ed::Config());
    */

    public native String getSettingsFile(); /*
        return env->NewStringUTF(IM_NODE_EDITOR_CONFIG->SettingsFile);
    */

    public native void setSettingsFile(String settingsFile); /*MANUAL
        IM_NODE_EDITOR_CONFIG->SettingsFile = obj_settingsFile == NULL ? NULL : (char*)env->GetStringUTFChars(obj_settingsFile, JNI_FALSE);
    */
}
