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
     * the mouse position, in plot coordinates, will not be displayed inside of the plot
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoMouseText = 4;

    /**
     * the user will not be able to interact with the plot
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoInputs = 8;

    /**
     * the user will not be able to open context menus
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoMenus = 16;

    /**
     * the user will not be able to box-select
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoBoxSelect = 32;

    /**
     * the ImGui frame will not be rendered
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoFrame = 64;

    /**
     * x and y axes pairs will be constrained to have the same units/pixel
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int Equal = 128;

    /**
     * the default mouse cursor will be replaced with a crosshair when hovered
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int Crosshairs = 256;

    /**
     * Definition: {@code ImPlotFlags_NoTitle | ImPlotFlags_NoLegend | ImPlotFlags_NoMenus | ImPlotFlags_NoBoxSelect | ImPlotFlags_NoMouseText}
     */
    public static final int CanvasOnly = 55;
}
