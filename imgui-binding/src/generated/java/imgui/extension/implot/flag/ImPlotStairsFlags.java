package imgui.extension.implot.flag;


public final class ImPlotStairsFlags {
    private ImPlotStairsFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * the y value is continued constantly to the left from every x position, i.e. the interval (x[i-1], x[i]] has the value y[i]
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int PreStep = 1024;

    /**
     * a filled region between the stairs and horizontal origin will be rendered; use PlotShaded for more advanced cases
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int Shaded = 2048;
}
