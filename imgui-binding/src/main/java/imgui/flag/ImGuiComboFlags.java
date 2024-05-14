package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::BeginCombo()
 */
@BindingSource
public final class ImGuiComboFlags {
    private ImGuiComboFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiComboFlags_")
    public Void __;
}
