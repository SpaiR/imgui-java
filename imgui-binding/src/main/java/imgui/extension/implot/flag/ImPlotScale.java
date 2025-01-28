package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotScale {
    private ImPlotScale() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotScale_")
    public Void __;
}
