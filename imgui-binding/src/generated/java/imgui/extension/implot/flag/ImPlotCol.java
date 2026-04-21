package imgui.extension.implot.flag;


public final class ImPlotCol {
    private ImPlotCol() {
    }

    /**
     * plot frame background color (defaults to ImGuiCol_FrameBg)
     */
    public static final int FrameBg = 0;

    /**
     * plot area background color (defaults to ImGuiCol_WindowBg)
     */
    public static final int PlotBg = 1;

    /**
     * plot area border color (defaults to ImGuiCol_Border)
     */
    public static final int PlotBorder = 2;

    /**
     * legend background color (defaults to ImGuiCol_PopupBg)
     */
    public static final int LegendBg = 3;

    /**
     * legend border color (defaults to ImPlotCol_PlotBorder)
     */
    public static final int LegendBorder = 4;

    /**
     * legend text color (defaults to ImPlotCol_InlayText)
     */
    public static final int LegendText = 5;

    /**
     * plot title text color (defaults to ImGuiCol_Text)
     */
    public static final int TitleText = 6;

    /**
     * color of text appearing inside of plots (defaults to ImGuiCol_Text)
     */
    public static final int InlayText = 7;

    /**
     * axis label and tick labels color (defaults to ImGuiCol_Text)
     */
    public static final int AxisText = 8;

    /**
     * axis grid color (defaults to 25% ImPlotCol_AxisText)
     */
    public static final int AxisGrid = 9;

    /**
     * axis tick color (defaults to AxisGrid)
     */
    public static final int AxisTick = 10;

    /**
     * background color of axis hover region (defaults to transparent)
     */
    public static final int AxisBg = 11;

    /**
     * axis hover color (defaults to ImGuiCol_ButtonHovered)
     */
    public static final int AxisBgHovered = 12;

    /**
     * axis active color (defaults to ImGuiCol_ButtonActive)
     */
    public static final int AxisBgActive = 13;

    /**
     * box-selection color (defaults to yellow)
     */
    public static final int Selection = 14;

    /**
     * crosshairs color (defaults to ImPlotCol_PlotBorder)
     */
    public static final int Crosshairs = 15;

    public static final int COUNT = 16;
}
