package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotHistogramFlags {
    private ImPlotHistogramFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotHistogramFlags_")
    public Void __;
}
