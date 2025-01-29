package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotStemsFlags {
    private ImPlotStemsFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotStemsFlags_")
    public Void __;
}
