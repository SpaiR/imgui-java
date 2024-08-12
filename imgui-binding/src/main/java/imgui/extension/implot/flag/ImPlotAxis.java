package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotAxis {
    private ImPlotAxis() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImAxis_")
    public Void __;
}
