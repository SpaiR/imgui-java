package imgui.internal.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImGuiDockNodeState {
    private ImGuiDockNodeState() {
    }

    @BindingAstEnum(file = "ast-imgui_internal.json", qualType = "ImGuiDockNodeState", sanitizeName = "ImGuiDockNodeState_")
    public Void __;
}
