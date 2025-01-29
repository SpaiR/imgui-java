package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotScatterFlags {
    private ImPlotScatterFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotScatterFlags_")
    public Void __;
}
