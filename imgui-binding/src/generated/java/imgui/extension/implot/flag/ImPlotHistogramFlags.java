package imgui.extension.implot.flag;


public final class ImPlotHistogramFlags {
    private ImPlotHistogramFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * histogram bars will be rendered horizontally (not supported by PlotHistogram2D)
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Horizontal = 1024;

    /**
     * each bin will contain its count plus the counts of all previous bins (not supported by PlotHistogram2D)
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int Cumulative = 2048;

    /**
     * counts will be normalized, i.e. the PDF will be visualized, or the CDF will be visualized if Cumulative is also set
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int Density = 4096;

    /**
     * exclude values outside the specifed histogram range from the count toward normalizing and cumulative counts
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int NoOutliers = 8192;

    /**
     * data will be read in column major order (not supported by PlotHistogram)
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int ColMajor = 16384;
}
