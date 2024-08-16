package imgui.extension.implot.flag;


public final class ImPlotCol {
    private ImPlotCol() {
    }

    /**
     * plot line/outline color (defaults to next unused color in current colormap)
     */
    public static final int Line = 0;

    /**
     * plot fill color for bars (defaults to the current line color)
     */
    public static final int Fill = 1;

    /**
     * marker outline color (defaults to the current line color)
     */
    public static final int MarkerOutline = 2;

    /**
     * marker fill color (defaults to the current line color)
     */
    public static final int MarkerFill = 3;

    /**
     * error bar color (defaults to ImGuiCol_Text)
     */
    public static final int ErrorBar = 4;

    /**
     * plot frame background color (defaults to ImGuiCol_FrameBg)
     */
    public static final int FrameBg = 5;

    /**
     * plot area background color (defaults to ImGuiCol_WindowBg)
     */
    public static final int PlotBg = 6;

    /**
     * plot area border color (defaults to ImGuiCol_Border)
     */
    public static final int PlotBorder = 7;

    /**
     * legend background color (defaults to ImGuiCol_PopupBg)
     */
    public static final int LegendBg = 8;

    /**
     * legend border color (defaults to ImPlotCol_PlotBorder)
     */
    public static final int LegendBorder = 9;

    /**
     * legend text color (defaults to ImPlotCol_InlayText)
     */
    public static final int LegendText = 10;

    /**
     * plot title text color (defaults to ImGuiCol_Text)
     */
    public static final int TitleText = 11;

    /**
     * color of text appearing inside of plots (defaults to ImGuiCol_Text)
     */
    public static final int InlayText = 12;

    /**
     * axis label and tick lables color (defaults to ImGuiCol_Text)
     */
    public static final int AxisText = 13;

    /**
     * axis grid color (defaults to 25% ImPlotCol_AxisText)
     */
    public static final int AxisGrid = 14;

    /**
     * axis tick color (defaults to AxisGrid)
     */
    public static final int AxisTick = 15;

    /**
     * background color of axis hover region (defaults to transparent)
     */
    public static final int AxisBg = 16;

    /**
     * axis hover color (defaults to ImGuiCol_ButtonHovered)
     */
    public static final int AxisBgHovered = 17;

    /**
     * axis active color (defaults to ImGuiCol_ButtonActive)
     */
    public static final int AxisBgActive = 18;

    /**
     * box-selection color (defaults to yellow)
     */
    public static final int Selection = 19;

    /**
     * crosshairs color (defaults to ImPlotCol_PlotBorder)
     */
    public static final int Crosshairs = 20;

    public static final int COUNT = 21;
}
