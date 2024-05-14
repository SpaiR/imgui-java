package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiFreeTypeBuilderFlags {
    private ImGuiFreeTypeBuilderFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiFreeTypeBuilderFlags_")
    public Void __;
}
