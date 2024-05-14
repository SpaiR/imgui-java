package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotMarker {
    private ImPlotMarker() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotMarker_")
    public Void __;
}
