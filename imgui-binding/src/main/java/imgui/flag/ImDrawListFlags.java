package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImDrawList. Those are set automatically by ImGui:: functions from ImGuiIO settings, and generally not manipulated directly.
 * It is however possible to temporarily alter flags between calls to ImDrawList:: functions.
 */
@BindingSource
public final class ImDrawListFlags {
    private ImDrawListFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImDrawListFlags_")
    public Void __;
}
