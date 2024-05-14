package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Backend capabilities flags stored in io.BackendFlags. Set by imgui_impl_xxx or custom backend.
 */
@BindingSource
public final class ImGuiBackendFlags {
    private ImGuiBackendFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiBackendFlags_")
    public Void __;
}
