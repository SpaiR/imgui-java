package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::BeginTabItem()
 */
@BindingSource
public final class ImGuiTabItemFlags {
    private ImGuiTabItemFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiTabItemFlags_")
    public Void __;
}
