package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotLocation {
    private ImPlotLocation() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotLocation_")
    public Void __;
}
