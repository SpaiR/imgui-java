package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::InputText()
 */
@BindingSource
public final class ImGuiInputTextFlags {
    private ImGuiInputTextFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiInputTextFlags_")
    public Void __;
}
