package imgui.extension.implot.flag;


public final class ImPlotStyleVar {
    private ImPlotStyleVar() {
    }

    /**
     * ImVec2, default size used when ImVec2(0,0) is passed to BeginPlot
     */
    public static final int PlotDefaultSize = 0;

    /**
     * ImVec2, minimum size plot frame can be when shrunk
     */
    public static final int PlotMinSize = 1;

    /**
     * float,  thickness of border around plot area
     */
    public static final int PlotBorderSize = 2;

    /**
     * float,  alpha multiplier applied to minor axis grid lines
     */
    public static final int MinorAlpha = 3;

    /**
     * ImVec2, major tick lengths for X and Y axes
     */
    public static final int MajorTickLen = 4;

    /**
     * ImVec2, minor tick lengths for X and Y axes
     */
    public static final int MinorTickLen = 5;

    /**
     * ImVec2, line thickness of major ticks
     */
    public static final int MajorTickSize = 6;

    /**
     * ImVec2, line thickness of minor ticks
     */
    public static final int MinorTickSize = 7;

    /**
     * ImVec2, line thickness of major grid lines
     */
    public static final int MajorGridSize = 8;

    /**
     * ImVec2, line thickness of minor grid lines
     */
    public static final int MinorGridSize = 9;

    /**
     * ImVec2, padding between widget frame and plot area, labels, or outside legends (i.e. main padding)
     */
    public static final int PlotPadding = 10;

    /**
     * ImVec2, padding between axes labels, tick labels, and plot edge
     */
    public static final int LabelPadding = 11;

    /**
     * ImVec2, legend padding from plot edges
     */
    public static final int LegendPadding = 12;

    /**
     * ImVec2, legend inner padding from legend edges
     */
    public static final int LegendInnerPadding = 13;

    /**
     * ImVec2, spacing between legend entries
     */
    public static final int LegendSpacing = 14;

    /**
     * ImVec2, padding between plot edge and interior info text
     */
    public static final int MousePosPadding = 15;

    /**
     * ImVec2, text padding around annotation labels
     */
    public static final int AnnotationPadding = 16;

    /**
     * ImVec2, additional fit padding as a percentage of the fit extents (e.g. ImVec2(0.1f,0.1f) adds 10% to the fit extents of X and Y)
     */
    public static final int FitPadding = 17;

    /**
     * float,  digital plot padding from bottom in pixels
     */
    public static final int DigitalPadding = 18;

    /**
     * float,  digital plot spacing gap in pixels
     */
    public static final int DigitalSpacing = 19;

    public static final int COUNT = 20;
}
