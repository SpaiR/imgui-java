package imgui.extension.nodeditor.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class NodeEditorPinKind {
    private NodeEditorPinKind() {
    }

    @BindingAstEnum(file = "ast-imgui_node_editor.json", qualType = "ax::NodeEditor::PinKind")
    public Void __;
}
