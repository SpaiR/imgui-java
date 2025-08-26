package imgui.extension.implot.flag;


public final class ImPlotPieChartFlags {
    private ImPlotPieChartFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * force normalization of pie chart values (i.e. always make a full circle if sum{@code <}0)
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Normalize = 1024;

    /**
     * ignore hidden slices when drawing the pie chart (as if they were not there)
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int IgnoreHidden = 2048;
}
