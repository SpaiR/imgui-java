package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::Begin()
 */
@BindingSource
public final class ImGuiWindowFlags {
    private ImGuiWindowFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiWindowFlags_")
    public Void __;
}
