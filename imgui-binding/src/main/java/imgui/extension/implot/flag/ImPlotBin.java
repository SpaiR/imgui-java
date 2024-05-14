package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotBin {
    private ImPlotBin() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotBin_")
    public Void __;
}
