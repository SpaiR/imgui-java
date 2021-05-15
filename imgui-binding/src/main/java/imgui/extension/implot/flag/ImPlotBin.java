package imgui.extension.implot.flag;

public class ImPlotBin {
    private ImPlotBin() {
    }

    /** k = sqrt(n) */
    public static final int Sqrt    = -1;
    /** k = 1 + log2(n) */
    public static final int Sturges = -2;
    /** k = 2 * cbrt(n) */
    public static final int Rice    = -3;
    /** w = 3.49 * sigma / cbrt(n) */
    public static final int Scott   = -4;
}
