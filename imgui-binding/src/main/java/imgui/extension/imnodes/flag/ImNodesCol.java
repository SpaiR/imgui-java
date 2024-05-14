package imgui.extension.imnodes.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImNodesCol {
    private ImNodesCol() {
    }

    @BindingAstEnum(file = "ast-imnodes.json", qualType = "ImNodesCol_")
    public Void __;
}
