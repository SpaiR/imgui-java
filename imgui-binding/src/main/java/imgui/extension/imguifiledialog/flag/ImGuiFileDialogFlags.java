package imgui.extension.imguifiledialog.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiFileDialogFlags {
    private ImGuiFileDialogFlags() {
    }

    @BindingAstEnum(file = "ast-ImGuiFileDialog.json", qualType = "ImGuiFileDialogFlags_")
    public Void __;
}
