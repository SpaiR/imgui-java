package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotOrientation {
    private ImPlotOrientation() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotOrientation_")
    public Void __;
}
