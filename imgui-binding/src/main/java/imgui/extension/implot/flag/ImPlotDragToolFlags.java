package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotDragToolFlags {
    private ImPlotDragToolFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotDragToolFlags_")
    public Void __;
}
