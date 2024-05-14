package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotColormap {
    private ImPlotColormap() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotColormap_")
    public Void __;
}
