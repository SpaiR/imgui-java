package imgui.extension.implot.flag;





public final class ImPlotMouseTextFlags {
    private ImPlotMouseTextFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * only show the mouse position for primary axes
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoAuxAxes = 1;

    /**
     * axes label formatters won't be used to render text
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoFormat = 2;

    /**
     * always display mouse position even if plot not hovered
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int ShowAlways = 4;
}
