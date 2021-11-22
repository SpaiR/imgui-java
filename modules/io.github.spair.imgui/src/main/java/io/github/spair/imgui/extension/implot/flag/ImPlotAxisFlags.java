package io.github.spair.imgui.extension.implot.flag;

public final class ImPlotAxisFlags {
    private ImPlotAxisFlags() {
    }

    /** default */
    public static final int None = 0;
    /** the axis label will not be displayed (axis labels also hidden if the supplied string name is NULL) */
    public static final int NoLabel       = 1;
    /** no grid lines will be displayed */
    public static final int NoGridLines   = 1 << 1;
    /** no tick marks will be displayed */
    public static final int NoTickMarks   = 1 << 2;
    /** no text labels will be displayed */
    public static final int NoTickLabels  = 1 << 3;
    /** grid lines will be displayed in the foreground (i.e. on top of data) in stead of the background */
    public static final int Foreground    = 1 << 4;
    /** a logartithmic (base 10) axis scale will be used (mutually exclusive with ImPlotAxisFlags_Time) */
    public static final int LogScale      = 1 << 5;
    /** axis will display date/time formatted labels (mutually exclusive with ImPlotAxisFlags_LogScale) */
    public static final int Time          = 1 << 6;
    /** the axis will be inverted */
    public static final int Invert        = 1 << 7;
    /** axis will not be initially fit to data extents on the first rendered frame (also the case if SetNextPlotLimits explicitly called) */
    public static final int NoInitialFit  = 1 << 8;
    /** axis will be auto-fitting to data extents */
    public static final int AutoFit       = 1 << 9;
    /** axis will only fit points if the point is in the visible range of the **orthoganol** axis */
    public static final int RangeFit      = 1 << 10;
    /** the axis minimum value will be locked when panning/zooming */
    public static final int LockMin       = 1 << 11;
    /** the axis maximum value will be locked when panning/zooming */
    public static final int LockMax       = 1 << 12;
    public static final int Lock          = LockMin | LockMax;
    public static final int NoDecorations = NoLabel | NoGridLines | NoTickMarks | NoTickLabels;
}
