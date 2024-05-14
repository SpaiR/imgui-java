package imgui.extension.imnodes.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

/**
 * This enum controls the way the attribute pins behave.
 */
@BindingSource
public final class ImNodesAttributeFlags {
    private ImNodesAttributeFlags() {
    }

    @BindingAstEnum(file = "ast-imnodes.json", qualType = "ImNodesAttributeFlags_")
    public Void __;
}
