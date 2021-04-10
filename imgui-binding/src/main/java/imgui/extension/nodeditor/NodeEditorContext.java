package imgui.extension.nodeditor;

import imgui.binding.ImGuiStructDestroyable;

public final class NodeEditorContext extends ImGuiStructDestroyable {
    public NodeEditorContext() {
    }

    public NodeEditorContext(final NodeEditorConfig config) {
        this(nCreate(config.ptr));
    }

    public NodeEditorContext(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_nodeeditor.h"

        namespace ed = ax::NodeEditor;

        #define IM_NODE_EDITOR_CONTEXT ((ed::EditorContext*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate();
    }

    @Override
    public void destroy() {
        nDestroyEditorContext();
    }

    private native long nCreate(); /*
        return (intptr_t)ed::CreateEditor();
    */

    private static native long nCreate(long cfgPtr); /*
        return (intptr_t)ed::CreateEditor((ed::Config*)cfgPtr);
    */

    private native void nDestroyEditorContext(); /*
       ed::DestroyEditor(IM_NODE_EDITOR_CONTEXT);
    */
}
