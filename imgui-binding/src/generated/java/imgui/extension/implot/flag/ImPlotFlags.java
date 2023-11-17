package imgui.extension.implot.flag;

public final class ImPlotFlags {
    private ImPlotFlags() {
    }

    /** default */
    public static final int None = 0;
    /** the plot title will not be displayed (titles are also hidden if preceeded by double hashes, e.g. "##MyPlot") */
    public static final int NoTitle = 1;
    /** the legend will not be displayed */
    public static final int NoLegend    = 1 << 1;
    /** the user will not be able to open context menus with right-click */
    public static final int NoMenus     = 1 << 2;
    /** the user will not be able to box-select with right-click drag */
    public static final int NoBoxSelect = 1 << 3;
    /** the mouse position, in plot coordinates, will not be displayed inside of the plot */
    public static final int NoMousePos  = 1 << 4;
    /** plot items will not be highlighted when their legend entry is hovered */
    public static final int NoHighlight = 1 << 5;
    /** a child window region will not be used to capture mouse scroll (can boost performance for single ImGui window applications) */
    public static final int NoChild     = 1 << 6;
    /** primary x and y axes will be constrained to have the same units/pixel (does not apply to auxiliary y-axes) */
    public static final int Equal       = 1 << 7;
    /** enable a 2nd y-axis on the right side */
    public static final int YAxis2      = 1 << 8;
    /** enable a 3rd y-axis on the right side */
    public static final int YAxis3      = 1 << 9;
    /** the user will be able to draw query rects with middle-mouse or CTRL + right-click drag */
    public static final int Query       = 1 << 1;
    /** the default mouse cursor will be replaced with a crosshair when hovered */
    public static final int Crosshairs  = 1 << 1;
    /** plot lines will be software anti-aliased (not recommended for high density plots, prefer MSAA) */
    public static final int AntiAliased = 1 << 1;
    public static final int CanvasOnly  = NoTitle | NoLegend | NoMenus | NoBoxSelect | NoMousePos;
}
