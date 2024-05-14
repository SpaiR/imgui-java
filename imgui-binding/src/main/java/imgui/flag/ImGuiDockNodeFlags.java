package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::DockSpace(), shared/inherited by child nodes.
 * (Some flags can be applied to individual nodes directly)
 */
@BindingSource
public final class ImGuiDockNodeFlags {
    private ImGuiDockNodeFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiDockNodeFlags_")
    public Void __;
}
