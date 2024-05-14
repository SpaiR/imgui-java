package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiSliderFlags {
    private ImGuiSliderFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiSliderFlags_")
    public Void __;
}
