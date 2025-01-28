package imgui.extension.texteditor.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ExcludedSource;

@ExcludedSource
@BindingSource
public final class TextEditorSelectionMode {
    private TextEditorSelectionMode() {
    }

    @BindingAstEnum(file = "ast-TextEditor.json", qualType = "TextEditor::SelectionMode")
    public Void __;
}
