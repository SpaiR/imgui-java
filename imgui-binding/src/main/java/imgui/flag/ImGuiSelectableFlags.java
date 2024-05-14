package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::Selectable()
 */
@BindingSource
public final class ImGuiSelectableFlags {
    private ImGuiSelectableFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiSelectableFlags_")
    public Void __;
}
