package imgui.internal.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiAxis {
    private ImGuiAxis() {
    }

    @BindingAstEnum(file = "ast-imgui_internal.json", qualType = "ImGuiAxis", sanitizeName = "ImGuiAxis_")
    public Void __;
}
