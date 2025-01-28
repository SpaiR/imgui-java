package imgui.extension.implot.flag;


public final class ImPlotLineFlags {
    private ImPlotLineFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * a line segment will be rendered from every two consecutive points
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Segments = 1024;

    /**
     * the last and first point will be connected to form a closed loop
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int Loop = 2048;

    /**
     * NaNs values will be skipped instead of rendered as missing data
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int SkipNaN = 4096;

    /**
     * markers (if displayed) on the edge of a plot will not be clipped
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int NoClip = 8192;

    /**
     * a filled region between the line and horizontal origin will be rendered; use PlotShaded for more advanced cases
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int Shaded = 16384;
}
