package imgui.nodeditor;

import imgui.binding.ImGuiStructDestroyable;

public final class ImNodeEditorContext extends ImGuiStructDestroyable {

    /*JNI
        #include <imgui.h>
        #include <imgui_node_editor.h>
        #include "jni_common.h"
        #include "jni_binding_struct.h"
        namespace ed = ax::NodeEditor;

         #define IM_NODE_EDITOR_CONTEXT ((ed::EditorContext*)STRUCT_PTR)
     */

    public ImNodeEditorContext() {
        super();
    }

    public ImNodeEditorContext(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    @Override
    public void destroy() {
        nDestroyEditorContext();
    }

    private native long nCreate(); /*
        return (jlong)ed::CreateEditor();
    */

    private native void nDestroyEditorContext(); /*
       ed::DestroyEditor(IM_NODE_EDITOR_CONTEXT);
    */
}
