package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::IsWindowFocused()
 */
@BindingSource
public final class ImGuiFocusedFlags {
    private ImGuiFocusedFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiFocusedFlags_")
    public Void __;
}
