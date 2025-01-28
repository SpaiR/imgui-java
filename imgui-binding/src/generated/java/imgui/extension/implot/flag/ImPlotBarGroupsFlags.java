package imgui.extension.implot.flag;


public final class ImPlotBarGroupsFlags {
    private ImPlotBarGroupsFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * bar groups will be rendered horizontally on the current y-axis
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Horizontal = 1024;

    /**
     * items in a group will be stacked on top of each other
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int Stacked = 2048;
}
