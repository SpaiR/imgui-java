package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * A sorting direction
 */
@BindingSource
public final class ImGuiSortDirection {
    private ImGuiSortDirection() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiSortDirection", sanitizeName = "ImGuiSortDirection_")
    public Void __;
}
