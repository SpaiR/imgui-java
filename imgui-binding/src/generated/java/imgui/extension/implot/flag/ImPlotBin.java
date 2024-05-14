package imgui.extension.implot.flag;


public final class ImPlotBin {
    private ImPlotBin() {
    }

    /**
     * k = sqrt(n)
     *
     * <p>Definition: {@code -1}
     */
    public static final int Sqrt = -1;

    /**
     * k = 1 + log2(n)
     *
     * <p>Definition: {@code -2}
     */
    public static final int Sturges = -2;

    /**
     * k = 2 * cbrt(n)
     *
     * <p>Definition: {@code -3}
     */
    public static final int Rice = -3;

    /**
     * w = 3.49 * sigma / cbrt(n)
     *
     * <p>Definition: {@code -4}
     */
    public static final int Scott = -4;
}
