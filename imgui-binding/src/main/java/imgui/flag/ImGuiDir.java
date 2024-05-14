package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * A cardinal direction
 */
@BindingSource
public final class ImGuiDir {
    private ImGuiDir() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiDir_")
    public Void __;
}
