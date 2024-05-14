package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotAxisFlags {
    private ImPlotAxisFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotAxisFlags_")
    public Void __;
}
