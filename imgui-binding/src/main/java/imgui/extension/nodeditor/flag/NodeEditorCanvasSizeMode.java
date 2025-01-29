package imgui.extension.nodeditor.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class NodeEditorCanvasSizeMode {
    private NodeEditorCanvasSizeMode() {
    }

    @BindingAstEnum(file = "ast-imgui_node_editor.json", qualType = "ax::NodeEditor::CanvasSizeMode")
    public Void __;
}
