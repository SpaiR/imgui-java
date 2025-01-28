package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotTextFlags {
    private ImPlotTextFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotTextFlags_")
    public Void __;
}
