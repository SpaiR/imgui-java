package imgui.extension.implot.flag;


public final class ImPlotAxisFlags {
    private ImPlotAxisFlags() {
    }

    /**
     * default
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * the axis label will not be displayed (axis labels also hidden if the supplied string name is NULL)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoLabel = 1;

    /**
     * no grid lines will be displayed
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoGridLines = 2;

    /**
     * no tick marks will be displayed
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoTickMarks = 4;

    /**
     * no text labels will be displayed
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoTickLabels = 8;

    /**
     * axis will not be initially fit to data extents on the first rendered frame
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoInitialFit = 16;

    /**
     * the user will not be able to open context menus with right-click
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoMenus = 32;

    /**
     * axis ticks and labels will be rendered on conventionally opposite side (i.e, right or top)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int Opposite = 64;

    /**
     * grid lines will be displayed in the foreground (i.e. on top of data) in stead of the background
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int Foreground = 128;

    /**
     * a logartithmic (base 10) axis scale will be used (mutually exclusive with ImPlotAxisFlags_Time)
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int LogScale = 256;

    /**
     * axis will display date/time formatted labels (mutually exclusive with ImPlotAxisFlags_LogScale)
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int Time = 512;

    /**
     * the axis will be inverted
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Invert = 1024;

    /**
     * axis will be auto-fitting to data extents
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int AutoFit = 2048;

    /**
     * axis will only fit points if the point is in the visible range of the **orthogonal** axis
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int RangeFit = 4096;

    /**
     * the axis minimum value will be locked when panning/zooming
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int LockMin = 8192;

    /**
     * the axis maximum value will be locked when panning/zooming
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int LockMax = 16384;

    /**
     * Definition: {@code ImPlotAxisFlags_LockMin | ImPlotAxisFlags_LockMax}
     */
    public static final int Lock = 24576;

    /**
     * Definition: {@code ImPlotAxisFlags_NoLabel | ImPlotAxisFlags_NoGridLines | ImPlotAxisFlags_NoTickMarks | ImPlotAxisFlags_NoTickLabels}
     */
    public static final int NoDecorations = 15;

    /**
     * Definition: {@code ImPlotAxisFlags_NoGridLines | ImPlotAxisFlags_Opposite}
     */
    public static final int AuxDefault = 66;
}
