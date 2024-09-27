package imgui.extension.implot.flag;





public final class ImPlotLocation {
    private ImPlotLocation() {
    }

    /**
     * center-center
     *
     * <p>Definition: {@code 0}
     */
    public static final int Center = 0;

    /**
     * top-center
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int North = 1;

    /**
     * bottom-center
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int South = 2;

    /**
     * center-left
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int West = 4;

    /**
     * center-right
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int East = 8;

    /**
     * top-left
     *
     * <p>Definition: {@code ImPlotLocation_North | ImPlotLocation_West}
     */
    public static final int NorthWest = 5;

    /**
     * top-right
     *
     * <p>Definition: {@code ImPlotLocation_North | ImPlotLocation_East}
     */
    public static final int NorthEast = 9;

    /**
     * bottom-left
     *
     * <p>Definition: {@code ImPlotLocation_South | ImPlotLocation_West}
     */
    public static final int SouthWest = 6;

    /**
     * bottom-right
     *
     * <p>Definition: {@code ImPlotLocation_South | ImPlotLocation_East}
     */
    public static final int SouthEast = 10;
}
