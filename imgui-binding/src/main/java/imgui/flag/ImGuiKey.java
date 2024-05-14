package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
 */
@BindingSource
public final class ImGuiKey {
    private ImGuiKey() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiKey_")
    public Void __;
}
