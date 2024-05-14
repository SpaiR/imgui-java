package imgui.extension.implot.flag;


public final class ImPlotFlags {
    private ImPlotFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * the plot title will not be displayed (titles are also hidden if preceeded by double hashes, e.g. "##MyPlot")
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoTitle = 1;

    /**
     * the legend will not be displayed
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoLegend = 2;

    /**
     * the user will not be able to open context menus with right-click
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoMenus = 4;

    /**
     * the user will not be able to box-select with right-click drag
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoBoxSelect = 8;

    /**
     * the mouse position, in plot coordinates, will not be displayed inside of the plot
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoMousePos = 16;

    /**
     * plot items will not be highlighted when their legend entry is hovered
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoHighlight = 32;

    /**
     * a child window region will not be used to capture mouse scroll (can boost performance for single ImGui window applications)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoChild = 64;

    /**
     * primary x and y axes will be constrained to have the same units/pixel (does not apply to auxiliary y-axes)
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int Equal = 128;

    /**
     * enable a 2nd y-axis on the right side
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int YAxis2 = 256;

    /**
     * enable a 3rd y-axis on the right side
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int YAxis3 = 512;

    /**
     * the user will be able to draw query rects with middle-mouse or CTRL + right-click drag
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Query = 1024;

    /**
     * the default mouse cursor will be replaced with a crosshair when hovered
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int Crosshairs = 2048;

    /**
     * plot lines will be software anti-aliased (not recommended for high density plots, prefer MSAA)
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int AntiAliased = 4096;

    /**
     * Definition: {@code ImPlotFlags_NoTitle | ImPlotFlags_NoLegend | ImPlotFlags_NoMenus | ImPlotFlags_NoBoxSelect | ImPlotFlags_NoMousePos}
     */
    public static final int CanvasOnly = 31;
}
