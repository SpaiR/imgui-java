package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotFlags {
    private ImPlotFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotFlags_")
    public Void __;
}
