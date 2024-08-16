package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotLegendFlags {
    private ImPlotLegendFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotLegendFlags_")
    public Void __;
}
