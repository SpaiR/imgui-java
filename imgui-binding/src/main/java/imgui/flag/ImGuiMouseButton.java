package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Identify a mouse button.
 * Those values are guaranteed to be stable and we frequently use 0/1 directly. Named enums provided for convenience.
 */
@BindingSource
public final class ImGuiMouseButton {
    private ImGuiMouseButton() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiMouseButton_")
    public Void __;
}
