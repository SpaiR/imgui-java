package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotYAxis {
    private ImPlotYAxis() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotYAxis_", sanitizeName = "ImPlot")
    public Void __;
}
