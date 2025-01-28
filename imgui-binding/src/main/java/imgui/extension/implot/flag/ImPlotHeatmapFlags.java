package imgui.extension.implot.flag;

import imgui.binding.annotation.BindingAstEnum;
import imgui.binding.annotation.BindingSource;

@BindingSource
public final class ImPlotHeatmapFlags {
    private ImPlotHeatmapFlags() {
    }

    @BindingAstEnum(file = "ast-implot.json", qualType = "ImPlotHeatmapFlags_")
    public Void __;
}
