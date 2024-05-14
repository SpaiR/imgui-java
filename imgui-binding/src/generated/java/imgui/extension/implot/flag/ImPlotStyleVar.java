package imgui.extension.implot.flag;


public final class ImPlotStyleVar {
    private ImPlotStyleVar() {
    }

    /**
     * float,  plot item line weight in pixels
     */
    public static final int LineWeight = 0;

    /**
     * int,    marker specification
     */
    public static final int Marker = 1;

    /**
     * float,  marker size in pixels (roughly the marker's "radius")
     */
    public static final int MarkerSize = 2;

    /**
     * float,  plot outline weight of markers in pixels
     */
    public static final int MarkerWeight = 3;

    /**
     * float,  alpha modifier applied to all plot item fills
     */
    public static final int FillAlpha = 4;

    /**
     * float,  error bar whisker width in pixels
     */
    public static final int ErrorBarSize = 5;

    /**
     * float,  error bar whisker weight in pixels
     */
    public static final int ErrorBarWeight = 6;

    /**
     * float,  digital channels bit height (at 1) in pixels
     */
    public static final int DigitalBitHeight = 7;

    /**
     * float,  digital channels bit padding gap in pixels
     */
    public static final int DigitalBitGap = 8;

    /**
     * float,  thickness of border around plot area
     */
    public static final int PlotBorderSize = 9;

    /**
     * float,  alpha multiplier applied to minor axis grid lines
     */
    public static final int MinorAlpha = 10;

    /**
     * ImVec2, major tick lengths for X and Y axes
     */
    public static final int MajorTickLen = 11;

    /**
     * ImVec2, minor tick lengths for X and Y axes
     */
    public static final int MinorTickLen = 12;

    /**
     * ImVec2, line thickness of major ticks
     */
    public static final int MajorTickSize = 13;

    /**
     * ImVec2, line thickness of minor ticks
     */
    public static final int MinorTickSize = 14;

    /**
     * ImVec2, line thickness of major grid lines
     */
    public static final int MajorGridSize = 15;

    /**
     * ImVec2, line thickness of minor grid lines
     */
    public static final int MinorGridSize = 16;

    /**
     * ImVec2, padding between widget frame and plot area, labels, or outside legends (i.e. main padding)
     */
    public static final int PlotPadding = 17;

    /**
     * ImVec2, padding between axes labels, tick labels, and plot edge
     */
    public static final int LabelPadding = 18;

    /**
     * ImVec2, legend padding from plot edges
     */
    public static final int LegendPadding = 19;

    /**
     * ImVec2, legend inner padding from legend edges
     */
    public static final int LegendInnerPadding = 20;

    /**
     * ImVec2, spacing between legend entries
     */
    public static final int LegendSpacing = 21;

    /**
     * ImVec2, padding between plot edge and interior info text
     */
    public static final int MousePosPadding = 22;

    /**
     * ImVec2, text padding around annotation labels
     */
    public static final int AnnotationPadding = 23;

    /**
     * ImVec2, additional fit padding as a percentage of the fit extents (e.g. ImVec2(0.1f,0.1f) adds 10% to the fit extents of X and Y)
     */
    public static final int FitPadding = 24;

    /**
     * ImVec2, default size used when ImVec2(0,0) is passed to BeginPlot
     */
    public static final int PlotDefaultSize = 25;

    /**
     * ImVec2, minimum size plot frame can be when shrunk
     */
    public static final int PlotMinSize = 26;

    public static final int COUNT = 27;
}
