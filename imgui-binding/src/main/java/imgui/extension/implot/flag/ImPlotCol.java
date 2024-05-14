package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotCol {
    private ImPlotCol() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotCol_")
    public Void __;
}
