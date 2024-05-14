package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotStyleVar {
    private ImPlotStyleVar() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotStyleVar_")
    public Void __;
}
