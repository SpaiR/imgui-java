package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotErrorBarsFlags {
    private ImPlotErrorBarsFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotErrorBarsFlags_")
    public Void __;
}
