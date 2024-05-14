package imgui.extension.imguiknobs.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiKnobFlags {
    private ImGuiKnobFlags() {
    }

    public static final int None = 0;

    @BindingAstEnum(file = "ast-imgui-knobs.json", qualType = "ImGuiKnobFlags_")
    public Void __;
}
