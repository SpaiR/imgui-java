package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotCond {
    private ImPlotCond() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotCond_")
    public Void __;
}
