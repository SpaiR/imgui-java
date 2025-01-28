package imgui.extension.implot.flag;


public final class ImPlotLegendFlags {
    private ImPlotLegendFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * legend icons will not function as hide/show buttons
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoButtons = 1;

    /**
     * plot items will not be highlighted when their legend entry is hovered
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoHighlightItem = 2;

    /**
     * axes will not be highlighted when legend entries are hovered (only relevant if x/y-axis count{@code >}1)
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoHighlightAxis = 4;

    /**
     * the user will not be able to open context menus with right-click
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoMenus = 8;

    /**
     * legend will be rendered outside of the plot area
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int Outside = 16;

    /**
     * legend entries will be displayed horizontally
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int Horizontal = 32;

    /**
     * legend entries will be displayed in alphabetical order
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int Sort = 64;
}
