package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotLineFlags {
    private ImPlotLineFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotLineFlags_")
    public Void __;
}
