package imgui.extension.implot.flag;

public class ImPlotLocation {
    private ImPlotLocation() {
    }

    /** center-center */
    public static final int Center    = 0;
    /** top-center */
    public static final int North     = 1;
    /** bottom-center */
    public static final int South     = 1 << 1;
    /** center-left */
    public static final int West      = 1 << 2;
    /** center-right */
    public static final int East      = 1 << 3;
    /** top-left */
    public static final int NorthWest = North | West;
    /** top-right */
    public static final int NorthEast = North | East;
    /** bottom-left */
    public static final int SouthWest = South | West;
    /** bottom-right */
    public static final int SouthEast = South | East;
}
