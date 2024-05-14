package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Configuration flags stored in io.ConfigFlags. Set by user/application.
 */
@BindingSource
public final class ImGuiConfigFlags {
    private ImGuiConfigFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiConfigFlags_")
    public Void __;
}
