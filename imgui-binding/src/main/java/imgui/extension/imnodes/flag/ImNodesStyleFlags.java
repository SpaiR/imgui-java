package imgui.extension.imnodes.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImNodesStyleFlags {
    private ImNodesStyleFlags() {
    }

    @BindingAstEnum(file = "ast-imnodes.json", qualType = "ImNodesStyleFlags_")
    public Void __;
}
