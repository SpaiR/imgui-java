package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotBarsFlags {
    private ImPlotBarsFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotBarsFlags_")
    public Void __;
}
