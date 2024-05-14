package imgui.extension.imnodes.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImNodesStyleVar {
    private ImNodesStyleVar() {
    }

    @BindingAstEnum(file = "ast-imnodes.json", qualType = "ImNodesStyleVar_")
    public Void __;
}
