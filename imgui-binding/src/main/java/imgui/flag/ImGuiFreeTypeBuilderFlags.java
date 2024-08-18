package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiFreeTypeBuilderFlags {
    private ImGuiFreeTypeBuilderFlags() {
    }

    @BindingAstEnum(file = "ast-imgui_freetype.json", qualType = "ImGuiFreeTypeBuilderFlags", sanitizeName = "ImGuiFreeTypeBuilderFlags_")
    public Void __;
}
