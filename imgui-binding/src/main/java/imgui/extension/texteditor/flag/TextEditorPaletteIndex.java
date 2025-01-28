package imgui.extension.texteditor.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ExcludedSource;

@ExcludedSource
@BindingSource
public final class TextEditorPaletteIndex {
    private TextEditorPaletteIndex() {
    }

    @BindingAstEnum(file = "ast-TextEditor.json", qualType = "TextEditor::PaletteIndex")
    public Void __;
}
