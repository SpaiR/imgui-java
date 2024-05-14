package imgui.extension.imguiknobs.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiKnobVariant {
    private ImGuiKnobVariant() {
    }

    @BindingAstEnum(file = "ast-imgui-knobs.json", qualType = "ImGuiKnobVariant_")
    public Void __;
}
