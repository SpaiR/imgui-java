package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Enumeration for ImGui::SetWindow***(), SetNextWindow***(), SetNextItem***() functions
 * Represent a condition.
 * Important: Treat as a regular enum! Do NOT combine multiple values using binary operators! All the functions above treat 0 as a shortcut to ImGuiCond_Always.
 */
@BindingSource
public final class ImGuiCond {
    private ImGuiCond() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiCond_")
    public Void __;
}
