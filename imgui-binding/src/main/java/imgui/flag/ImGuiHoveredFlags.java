package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImGui::IsItemHovered(), ImGui::IsWindowHovered()
 * Note:if you are trying to check whether your mouse should be dispatched to Dear ImGui or to your app,
 * you should use'io.WantCaptureMouse'instead!Please read the FAQ!Note: windows with the ImGuiWindowFlags_NoInputs flag are ignored by IsWindowHovered() calls.
 */
@BindingSource
public final class ImGuiHoveredFlags {
    private ImGuiHoveredFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiHoveredFlags_")
    public Void __;
}
