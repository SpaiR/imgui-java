package imgui.extension.imguifiledialog.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ExcludedSource;

@ExcludedSource
@BindingSource
public final class ImGuiFileDialogFlags {
    private ImGuiFileDialogFlags() {
    }

    @BindingAstEnum(file = "ast-ImGuiFileDialog.json", qualType = "ImGuiFileDialogFlags_")
    public Void __;
}
