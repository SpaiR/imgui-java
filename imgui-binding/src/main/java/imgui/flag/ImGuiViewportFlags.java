package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags stored in ImGuiViewport::Flags, giving indications to the platform backends.
 */
@BindingSource
public final class ImGuiViewportFlags {
    private ImGuiViewportFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImGuiViewportFlags_")
    public Void __;
}
