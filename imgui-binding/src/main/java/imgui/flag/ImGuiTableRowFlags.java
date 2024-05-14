package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for {@link imgui.ImGui#tableNextRow(int)}
 */
@BindingSource
public final class ImGuiTableRowFlags {
    private ImGuiTableRowFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiTableRowFlags_")
    public Void __;
}
