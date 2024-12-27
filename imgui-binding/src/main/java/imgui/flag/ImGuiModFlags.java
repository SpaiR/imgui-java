package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * To test io.KeyMods (which is a combination of individual fields io.KeyCtrl, io.KeyShift, io.KeyAlt set by user/backend)
 */
@BindingSource
public final class ImGuiModFlags {
    private ImGuiModFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiModFlags_")
    public Void __;
}
