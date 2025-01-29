package imgui.extension.implot.flag;


public final class ImPlotItemFlags {
    private ImPlotItemFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * the item won't have a legend entry displayed
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoLegend = 1;

    /**
     * the item won't be considered for plot fits
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoFit = 2;
}
