package imgui.extension.nodeditor.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class NodeEditorStyleColor {
    private NodeEditorStyleColor() {
    }

    @BindingAstEnum(file = "ast-imgui_node_editor.json", qualType = "ax::NodeEditor::StyleColor", sanitizeName = "StyleColor_")
    public Void __;
}
