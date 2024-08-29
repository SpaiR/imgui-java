package imgui.extension.nodeditor;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

@BindingSource
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

    @BindingField
    public String SettingsFile;

    @BindingField
    public int DragButtonIndex;

    @BindingField
    public int SelectButtonIndex;

    @BindingField
    public int NavigateButtonIndex;

    @BindingField
    public int ContextMenuButtonIndex;

    /*JNI
        #undef THIS
     */
}
