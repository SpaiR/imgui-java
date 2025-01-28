package imgui.extension.implot.flag;


public final class ImPlotErrorBarsFlags {
    private ImPlotErrorBarsFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * error bars will be rendered horizontally on the current y-axis
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Horizontal = 1024;
}
