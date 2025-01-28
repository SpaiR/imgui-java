package imgui.extension.implot.flag;


public final class ImPlotScale {
    private ImPlotScale() {
    }

    /**
     * default linear scale
     *
     * <p>Definition: {@code 0}
     */
    public static final int Linear = 0;

    /**
     * date/time scale
     */
    public static final int Time = 1;

    /**
     * base 10 logartithmic scale
     */
    public static final int Log10 = 2;

    /**
     * symmetric log scale
     */
    public static final int SymLog = 3;
}
