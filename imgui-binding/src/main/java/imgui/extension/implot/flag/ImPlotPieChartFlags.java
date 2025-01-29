package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotPieChartFlags {
    private ImPlotPieChartFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotPieChartFlags_")
    public Void __;
}
