package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotMouseTextFlags {
    private ImPlotMouseTextFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotMouseTextFlags_")
    public Void __;
}
