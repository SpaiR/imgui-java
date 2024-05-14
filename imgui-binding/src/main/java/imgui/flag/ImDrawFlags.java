package imgui.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * Flags for ImDrawList functions
 */
@BindingSource
public final class ImDrawFlags {
    private ImDrawFlags() {
    }

    @BindingAstEnum(file = "ast-imgui.json", qualType = "ImDrawFlags_")
    public Void __;
}
