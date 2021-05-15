package imgui.extension.implot.flag;

public final class ImPlotCol {
    private ImPlotCol() {
    }

    /** plot line/outline color (defaults to next unused color in current colormap)*/
    public static final int Line          = 0;
    /** plot fill color for bars (defaults to the current line color)*/
    public static final int Fill          = 1;
    /** marker outline color (defaults to the current line color)*/
    public static final int MarkerOutline = 2;
    /** marker fill color (defaults to the current line color)*/
    public static final int MarkerFill    = 3;
    /** error bar color (defaults to ImGuiCol_Text)*/
    public static final int ErrorBar      = 4;
    // plot styling colors
    /** plot frame background color (defaults to ImGuiCol_FrameBg) */
    public static final int FrameBg       = 5;
    /** plot area background color (defaults to ImGuiCol_WindowBg) */
    public static final int PlotBg        = 6;
    /** plot area border color (defaults to ImGuiCol_Border) */
    public static final int PlotBorder    = 7;
    /** legend background color (defaults to ImGuiCol_PopupBg) */
    public static final int LegendBg      = 8;
    /** legend border color (defaults to ImPlotCol_PlotBorder) */
    public static final int LegendBorder  = 9;
    /** legend text color (defaults to ImPlotCol_InlayText) */
    public static final int LegendText    = 10;
    /** plot title text color (defaults to ImGuiCol_Text) */
    public static final int TitleText     = 11;
    /** color of text appearing inside of plots (defaults to ImGuiCol_Text) */
    public static final int InlayText     = 12;
    /** x-axis label and tick lables color (defaults to ImGuiCol_Text) */
    public static final int XAxis         = 13;
    /** x-axis grid color (defaults to 25% ImPlotCol_XAxis) */
    public static final int XAxisGrid     = 14;
    /** y-axis label and tick labels color (defaults to ImGuiCol_Text) */
    public static final int YAxis         = 15;
    /** y-axis grid color (defaults to 25% ImPlotCol_YAxis) */
    public static final int YAxisGrid     = 16;
    /** 2nd y-axis label and tick labels color (defaults to ImGuiCol_Text) */
    public static final int YAxis2        = 17;
    /** 2nd y-axis grid/label color (defaults to 25% ImPlotCol_YAxis2) */
    public static final int YAxisGrid2    = 18;
    /** 3rd y-axis label and tick labels color (defaults to ImGuiCol_Text) */
    public static final int YAxis3        = 19;
    /** 3rd y-axis grid/label color (defaults to 25% ImPlotCol_YAxis3) */
    public static final int YAxisGrid3    = 21;
    /** box-selection color (defaults to yellow) */
    public static final int Selection     = 22;
    /** box-query color (defaults to green) */
    public static final int Query         = 23;
    /** crosshairs color (defaults to ImPlotCol_PlotBorder) */
    public static final int Crosshairs    = 24;
    public static final int COUNT         = 25;
}
