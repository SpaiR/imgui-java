package imgui.extension.implot.flag;


public final class ImPlotMarker {
    private ImPlotMarker() {
    }

    /**
     * no marker
     *
     * <p>Definition: {@code -1}
     */
    public static final int None = -1;

    /**
     * a circle marker (default)
     */
    public static final int Circle = 0;

    /**
     * a square maker
     */
    public static final int Square = 1;

    /**
     * a diamond marker
     */
    public static final int Diamond = 2;

    /**
     * an upward-pointing triangle marker
     */
    public static final int Up = 3;

    /**
     * an downward-pointing triangle marker
     */
    public static final int Down = 4;

    /**
     * an leftward-pointing triangle marker
     */
    public static final int Left = 5;

    /**
     * an rightward-pointing triangle marker
     */
    public static final int Right = 6;

    /**
     * a cross marker (not fillable)
     */
    public static final int Cross = 7;

    /**
     * a plus marker (not fillable)
     */
    public static final int Plus = 8;

    /**
     * a asterisk marker (not fillable)
     */
    public static final int Asterisk = 9;

    public static final int COUNT = 10;
}
