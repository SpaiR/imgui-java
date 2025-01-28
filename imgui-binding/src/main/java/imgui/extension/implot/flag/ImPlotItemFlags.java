package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotItemFlags {
    private ImPlotItemFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotItemFlags_")
    public Void __;
}
