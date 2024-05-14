package imgui.extension.nodeditor.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class NodeEditorStyleVar {
    private NodeEditorStyleVar() {
    }

    @BindingAstEnum(file = "ast-imgui_node_editor.json", qualType = "ax::NodeEditor::StyleVar", sanitizeName = "StyleVar_")
    public Void __;
}
