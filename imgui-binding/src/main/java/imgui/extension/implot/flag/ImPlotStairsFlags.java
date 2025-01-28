package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotStairsFlags {
    private ImPlotStairsFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotStairsFlags_")
    public Void __;
}
