package imgui.extension.implot;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.TypeArray;

@BindingSource
public final class ImPlotStyle extends ImGuiStructDestroyable {
    public ImPlotStyle() {
        super();
    }

    public ImPlotStyle(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_implot.h"
        #define THIS ((ImPlotStyle*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (intptr_t)(new ImPlotStyle());
    */

    @BindingField
    public float LineWeight;

    @BindingField
    public int Marker;

    @BindingField
    public float MarkerSize;

    @BindingField
    public float MarkerWeight;

    @BindingField
    public float FillAlpha;

    @BindingField
    public float ErrorBarSize;

    @BindingField
    public float ErrorBarWeight;

    @BindingField
    public float DigitalBitHeight;

    @BindingField
    public float DigitalBitGap;

    @BindingField
    public float PlotBorderSize;

    @BindingField
    public float MinorAlpha;

    @BindingField
    public ImVec2 MajorTickLen;

    @BindingField
    public ImVec2 MinorTickLen;

    @BindingField
    public ImVec2 MajorTickSize;

    @BindingField
    public ImVec2 MinorTickSize;

    @BindingField
    public ImVec2 MajorGridSize;

    @BindingField
    public ImVec2 MinorGridSize;

    @BindingField
    public ImVec2 PlotPadding;

    @BindingField
    public ImVec2 LabelPadding;

    @BindingField
    public ImVec2 LegendPadding;

    @BindingField
    public ImVec2 LegendInnerPadding;

    @BindingField
    public ImVec2 LegendSpacing;

    @BindingField
    public ImVec2 MousePosPadding;

    @BindingField
    public ImVec2 AnnotationPadding;

    @BindingField
    public ImVec2 FitPadding;

    @BindingField
    public ImVec2 PlotDefaultSize;

    @BindingField
    public ImVec2 PlotMinSize;

    @BindingField
    @TypeArray(type = "ImVec4", size = "ImPlotCol_COUNT")
    public ImVec4[] Colors;

    @BindingField
    public int Colormap;

    @BindingField
    public boolean AntiAliasedLines;

    @BindingField
    public boolean UseLocalTime;

    @BindingField
    public boolean UseISO8601;

    @BindingField
    public boolean Use24HourClock;

    /*JNI
        #undef THIS
     */
}
