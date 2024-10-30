package imgui.internal.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public class ImGuiItemStatusFlags {
    private ImGuiItemStatusFlags() {
    }

    @BindingAstEnum(file = "ast-imgui_internal.json", qualType = "ImGuiItemStatusFlags_")
    public Void __;
}
