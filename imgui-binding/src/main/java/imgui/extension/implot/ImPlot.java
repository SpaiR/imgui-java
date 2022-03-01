package imgui.extension.implot;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.extension.implot.flag.ImPlotYAxis;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiMouseButton;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;

public final class ImPlot {
    private static final ImDrawList IM_DRAW_LIST = new ImDrawList(0);

    private static final ImPlotContext IMPLOT_CONTEXT = new ImPlotContext(0);
    private static final ImPlotPoint IMPLOT_POINT = new ImPlotPoint(0);
    private static final ImPlotLimits IMPLOT_LIMITS = new ImPlotLimits(0);
    private static final ImPlotStyle IMPLOT_STYLE = new ImPlotStyle(0);

    private ImPlot() {

    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

     */

    //-----------------------------------------------------------------------------
    // ImPlot Context
    //-----------------------------------------------------------------------------

    /**
     * Creates a new ImPlot context. Call this after ImGui.createContext().
     */
    public static ImPlotContext createContext() {
        IMPLOT_CONTEXT.ptr = nCreateContext();
        return IMPLOT_CONTEXT;
    }

    private static native long nCreateContext(); /*
        return (intptr_t)ImPlot::CreateContext();
    */

    /**
     * Destroys an ImPlot context. Call this before ImGui.destroyContext(). NULL = destroy current context.
     */
    public static void destroyContext(final ImPlotContext ctx) {
        nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext(long ctx); /*
        ImPlot::DestroyContext((ImPlotContext*)ctx);
    */

    /**
     * Returns the current ImPlot context. NULL if no context has ben set.
     */
    public static ImPlotContext getCurrentContext() {
        IMPLOT_CONTEXT.ptr = nGetCurrentContext();
        return IMPLOT_CONTEXT;
    }

    private static native long nGetCurrentContext(); /*
        return (intptr_t)ImPlot::GetCurrentContext();
    */

    /**
     * Sets the current ImPlot context.
     */
    public static void setCurrentContext(final ImPlotContext ctx) {
        nSetCurrentContext(ctx.ptr);
    }

    private static native void nSetCurrentContext(long ctx); /*
        ImPlot::SetCurrentContext((ImPlotContext*)ctx);
    */

    //-----------------------------------------------------------------------------
    // Begin/End Plot
    //-----------------------------------------------------------------------------

    /**
     * Starts a 2D plotting context. If this function returns true, EndPlot() MUST
     * be called! You are encouraged to use the following convention:
     *
     * if (ImPlot.beginPlot(...)) {
     *     ImPlot.plotLine(...);
     *     ...
     *     ImPlot.endPlot();
     * }
     *
     * Important notes:
     *
     * - #titleID must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     */
    public static native boolean beginPlot(String titleID); /*
        return ImPlot::BeginPlot(titleID);
    */

    /**
     * Starts a 2D plotting context. If this function returns true, EndPlot() MUST
     * be called! You are encouraged to use the following convention:
     *
     * if (ImPlot.beginPlot(...)) {
     *     ImPlot.plotLine(...);
     *     ...
     *     ImPlot.endPlot();
     * }
     *
     * Important notes:
     *
     * - #titleID must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #xLabel and/or #yLabel are provided, axes labels will be displayed.
     */
    public static native boolean beginPlot(String titleID, String xLabel, String yLabel); /*
        return ImPlot::BeginPlot(titleID, xLabel, yLabel);
    */

    /**
     * Starts a 2D plotting context. If this function returns true, EndPlot() MUST
     * be called! You are encouraged to use the following convention:
     *
     * if (ImPlot.beginPlot(...)) {
     *     ImPlot.plotLine(...);
     *     ...
     *     ImPlot.endPlot();
     * }
     *
     * Important notes:
     *
     * - #titleID must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #xLabel and/or #yLabel are provided, axes labels will be displayed.
     * - #size is the **frame** size of the plot widget, not the plot area. The default
     *   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
     *   (default is 400x300 px).
     */
    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y);
    }

    private static native boolean nBeginPlot(String titleID, String xLabel, String yLabel, float x, float y); /*
        return ImPlot::BeginPlot(titleID, xLabel, yLabel, ImVec2(x, y));
    */

    /**
     * Starts a 2D plotting context. If this function returns true, EndPlot() MUST
     * be called! You are encouraged to use the following convention:
     *
     * if (ImPlot.beginPlot(...)) {
     *     ImPlot.plotLine(...);
     *     ...
     *     ImPlot.endPlot();
     * }
     *
     * Important notes:
     *
     * - #titleID must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #xLabel and/or #yLabel are provided, axes labels will be displayed.
     * - #size is the **frame** size of the plot widget, not the plot area. The default
     *   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
     *   (default is 400x300 px).
     * - See ImPlotFlags and ImPlotAxisFlags for more available options.
     */
    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags, final int yFlags) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags);
    }

    private static native boolean nBeginPlot(String titleID, String xLabel, String yLabel, float x, float y, int flags, int xFlags, int yFlags); /*
        return ImPlot::BeginPlot(titleID, xLabel, yLabel, ImVec2(x, y), flags, xFlags, yFlags);
    */

    /**
     * Starts a 2D plotting context. If this function returns true, EndPlot() MUST
     * be called! You are encouraged to use the following convention:
     *
     * if (ImPlot.beginPlot(...)) {
     *     ImPlot.plotLine(...);
     *     ...
     *     ImPlot.endPlot();
     * }
     *
     * Important notes:
     *
     * - #titleID must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #xLabel and/or #yLabel are provided, axes labels will be displayed.
     * - #size is the **frame** size of the plot widget, not the plot area. The default
     *   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
     *   (default is 400x300 px).
     * - Auxiliary y-axes must be enabled with ImPlotFlags_YAxis2/3 to be displayed.
     * - See ImPlotFlags and ImPlotAxisFlags for more available options.
     */
    public static boolean beginPlot(final String titleID,
                                    final String xLabel,
                                    final String yLabel,
                                    final ImVec2 size,
                                    final int flags,
                                    final int xFlags,
                                    final int yFlags,
                                    final int y2Flags,
                                    final int y3Flags,
                                    final String y2Label,
                                    final String y3Label) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label, y3Label);
    }

    private static native boolean nBeginPlot(String titleID,
                                             String xLabel,
                                             String yLabel,
                                             float x,
                                             float y,
                                             int flags,
                                             int xFlags,
                                             int yFlags,
                                             int y2Flags,
                                             int y3Flags,
                                             String y2Label,
                                             String y3Label); /*
        return ImPlot::BeginPlot(titleID,
                                 xLabel,
                                 yLabel,
                                 ImVec2(x, y),
                                 flags,
                                 xFlags,
                                 yFlags,
                                 y2Flags,
                                 y3Flags,
                                 y2Label,
                                 y3Label);
    */

    /**
     * Only call EndPlot() if beginPlot() returns true! Typically called at the end
     * of an if statement conditioned on BeginPlot(). See example in beginPlot().
     */
    public static native void endPlot(); /*
        ImPlot::EndPlot();
    */

    //-----------------------------------------------------------------------------
    // Plot Items
    //-----------------------------------------------------------------------------

    /**
     * Make sure to initialize xsOut and ysOut with length xs.length, ys.length before passing them, or data may be lost/errors may occur
     */
    private static <T extends Number> void convertArrays(final T[] xs, final T[] ys, final double[] xsOut, final double[] ysOut) {
        if (xs.length != ys.length) {
            throw new IllegalArgumentException("Invalid length for arrays");
        }

        for (int i = 0; i < xs.length; i++) {
            xsOut[i] = xs[i].doubleValue();
            ysOut[i] = ys[i].doubleValue();
        }
    }

    /**
     * Make sure to initialize xsOut, ysOut1, and ysOut2 with length xs.length, ys1.length, and ys2.length before passing them, or data may be lost/errors may occur
     */
    private static <T extends Number> void convertArrays(final T[] xs, final T[] ys1, final T[] ys2, final double[] xsOut, final double[] ysOut1, final double[] ysOut2) {
        if (xs.length != ys1.length || xs.length != ys2.length) {
            throw new IllegalArgumentException("Invalid length for arrays");
        }

        for (int i = 0; i < xs.length; i++) {
            xsOut[i] = xs[i].doubleValue();
            ysOut1[i] = ys1[i].doubleValue();
            ysOut2[i] = ys2[i].doubleValue();
        }
    }

    /**
     * Plots a standard 2D line plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotLine(final String labelID, final T[] xs, final T[] ys) {
        plotLine(labelID, xs, ys, 0);
    }

    /**
     * Plots a standard 2D line plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotLine(final String labelID, final T[] xs, final T[] ys, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotLine(labelID, x, y, x.length, offset);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelID, final double[] xs, final double[] ys, final int size, final int offset) {
        nPlotLine(labelID, xs, ys, size, offset);
    }

    private static native void nPlotLine(String labelID, double[] xs, double[] ys, int size, int offset); /*
        ImPlot::PlotLine(labelID, xs, ys, size, offset);
    */

    /**
     * Plots a standard 2D scatter plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotScatter(final String labelID, final T[] xs, final T[] ys) {
        plotScatter(labelID, xs, ys, 0);
    }

    /**
     * Plots a standard 2D scatter plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotScatter(final String labelID, final T[] xs, final T[] ys, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotScatter(labelID, x, y, x.length, offset);
    }

    public static void plotScatter(final String labelID, final double[] xs, final double[] ys, final int size, final int offset) {
        nPlotScatter(labelID, xs, ys, size, offset);
    }

    private static native void nPlotScatter(String labelID, double[] xs, double[] ys, int size, int offset); /*
        ImPlot::PlotScatter(labelID, xs, ys, size, offset);
    */

    /**
     * Plots a a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotStairs(final String labelID, final T[] xs, final T[] ys) {
        plotStairs(labelID, xs, ys, 0);
    }

    /**
     * Plots a a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotStairs(final String labelID, final T[] xs, final T[] ys, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotStairs(labelID, x, y, x.length, offset);
    }

    /**
     * Plots a a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelID, final double[] xs, final double[] ys, final int size, final int offset) {
        nPlotStairs(labelID, xs, ys, size, offset);
    }

    private static native void nPlotStairs(String labelID, double[] xs, double[] ys, int size, int offset); /*
        ImPlot::PlotStairs(labelID, xs, ys, size, offset);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set yRef (default 0) to +/-INFINITY for infinite fill extents.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotShaded(final String labelID, final T[] xs, final T[] ys, final int yRef) {
        plotShaded(labelID, xs, ys, yRef, 0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotShaded(final String labelID, final T[] xs, final T[] ys1, final T[] ys2) {
        plotShaded(labelID, xs, ys1, ys2, 0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set yRef (default 0) to +/-INFINITY for infinite fill extents.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotShaded(final String labelID, final T[] xs, final T[] ys, final int yRef, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotShaded(labelID, x, y, x.length, yRef, offset);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotShaded(final String labelID, final T[] xs, final T[] ys1, final T[] ys2, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y1 = new double[ys1.length];
        final double[] y2 = new double[ys2.length];
        convertArrays(xs, ys1, ys2, x, y1, y2);

        nPlotShaded(labelID, x, y1, y2, x.length, offset);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set yRef (default 0) to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelID, final double[] xs, final double[] ys, final int size, final int yRef, final int offset) {
        nPlotShaded(labelID, xs, ys, size, yRef, offset);
    }

    private static native void nPlotShaded(String labelID, double[] xs, double[] ys, int size, int yRef, int offset); /*
        ImPlot::PlotShaded(labelID, xs, ys, size, yRef, offset);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference.
     */
    public static void plotShaded(final String labelID, final double[] xs, final double[] ys1, final double[] ys2, final int size, final int offset) {
        nPlotShaded(labelID, xs, ys1, ys2, size, offset);
    }

    private static native void nPlotShaded(String labelID, double[] xs, double[] ys1, double[] ys2, int size, int offset); /*
        ImPlot::PlotShaded(labelID, xs, ys1, ys2, size, offset);
    */

    /**
     * Plots a vertical bar graph.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotBars(final String labelID, final T[] xs, final T[] ys) {
        plotBars(labelID, xs, ys, 0.67f, 0);
    }

    /**
     * Plots a vertical bar graph.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     * @param width is in X units
     */
    public static <T extends Number> void plotBars(final String labelID, final T[] xs, final T[] ys, final float width) {
        plotBars(labelID, xs, ys, width, 0);
    }

    /**
     * Plots a vertical bar graph.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     * @param width is in X units
     */
    public static <T extends Number> void plotBars(final String labelID, final T[] xs, final T[] ys, final float width, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotBars(labelID, x, y, x.length, width, offset);
    }

    /**
     * Plots a vertical bar graph.
     * @param width is in X units
     */
    public static void plotBars(final String labelID, final double[] xs, final double[] ys, final int size, final float width, final int offset) {
        nPlotBars(labelID, xs, ys, size, width, offset);
    }

    private static native void nPlotBars(String labelID, double[] xs, double[] ys, int size, float width, int offset); /*
        ImPlot::PlotBars(labelID, xs, ys, size, width, offset);
    */

    /**
     * Plots a horizontal bar graph.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotBarsH(final String labelID, final T[] xs, final T[] ys) {
        plotBarsH(labelID, xs, ys, 0.67f, 0);
    }

    /**
     * Plots a horizontal bar graph.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     * @param height is in Y units
     */
    public static <T extends Number> void plotBarsH(final String labelID, final T[] xs, final T[] ys, final float height) {
        plotBarsH(labelID, xs, ys, height, 0);
    }

    /**
     * Plots a horizontal bar graph.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     * @param height is in Y units
     */
    public static <T extends Number> void plotBarsH(final String labelID, final T[] xs, final T[] ys, final float height, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotBarsH(labelID, x, y, x.length, height, offset);
    }

    /**
     * Plots a horizontal bar graph.
     * @param height is in Y units
     */
    public static void plotBarsH(final String labelID, final double[] xs, final double[] ys, final int size, final float height, final int offset) {
        nPlotBarsH(labelID, xs, ys, size, height, offset);
    }

    private static native void nPlotBarsH(String labelID, double[] xs, double[] ys, int size, float height, int offset); /*
        ImPlot::PlotBarsH(labelID, xs, ys, size, height, offset);
    */

    /**
     * Plots vertical error bar. The labelID should be the same as the labelID of the associated line or bar plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotErrorBars(final String labelID, final T[] xs, final T[] ys, final T[] err) {
        plotErrorBars(labelID, xs, ys, err, 0);
    }

    /**
     * Plots vertical error bar. The labelID should be the same as the labelID of the associated line or bar plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotErrorBars(final String labelID, final T[] xs, final T[] ys, final T[] err, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        final double[] errOut = new double[err.length];
        convertArrays(xs, ys, x, y);
        convertArrays(xs, err, x, errOut); //It's easier here to just do the X array twice than process the err array alone

        nPlotErrorBars(labelID, x, y, errOut, x.length, offset);
    }

    /**
     * Plots vertical error bar. The labelID should be the same as the labelID of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelID, final double[] xs, final double[] ys, final double[] err, final int size, final int offset) {
        nPlotErrorBars(labelID, xs, ys, err, size, offset);
    }

    private static native void nPlotErrorBars(String labelID, double[] xs, double[] ys, double[] err, int size, int offset); /*
        ImPlot::PlotErrorBars(labelID, xs, ys, err, size, offset);
    */

    /**
     * Plots horizontal error bar. The labelID should be the same as the labelID of the associated line or bar plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotErrorBarsH(final String labelID, final T[] xs, final T[] ys, final T[] err) {
        plotErrorBarsH(labelID, xs, ys, err, 0);
    }

    /**
     * Plots horizontal error bar. The labelID should be the same as the labelID of the associated line or bar plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotErrorBarsH(final String labelID, final T[] xs, final T[] ys, final T[] err, final int offset) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        final double[] errOut = new double[err.length];
        convertArrays(xs, ys, x, y);
        convertArrays(xs, err, x, errOut); //It's easier here to just do the X array twice than process the err array alone

        nPlotErrorBarsH(labelID, x, y, errOut, x.length, offset);
    }

    /**
     * Plots horizontal error bar. The labelID should be the same as the labelID of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelID, final double[] xs, final double[] ys, final double[] err, final int size, final int offset) {
        nPlotErrorBarsH(labelID, xs, ys, err, size, offset);
    }

    private static native void nPlotErrorBarsH(String labelID, double[] xs, double[] ys, double[] err, int size, int offset); /*
        ImPlot::PlotErrorBarsH(labelID, xs, ys, err, size, offset);
    */

    /**
     * Plots vertical stems.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotStems(final String labelID, final T[] values, final int yRef) {
        plotStems(labelID, values, yRef, 0);
    }

    /**
     * Plots vertical stems.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotStems(final String labelID, final T[] values, final int yRef, final int offset) {
        final double[] v = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = values[i].doubleValue();
        }

        nPlotStems(labelID, v, v.length, yRef, offset);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelID, final double[] values, final int size, final int yRef, final int offset) {
        nPlotStems(labelID, values, size, yRef, offset);
    }

    private static native void nPlotStems(String labelID, double[] values, int size, int yRef, int offset); /*
        ImPlot::PlotStems(labelID, values, size, yRef, offset);
    */

    /**
     * Plots infinite vertical lines (e.g. for references or asymptotes).
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotVLines(final String labelID, final T[] values) {
        plotVLines(labelID, values, 0);
    }

    /**
     *Plots infinite vertical lines (e.g. for references or asymptotes).
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotVLines(final String labelID, final T[] values, final int offset) {
        final double[] v = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = values[i].doubleValue();
        }

        nPlotVLines(labelID, v, v.length, offset);
    }

    /**
     * Plots infinite vertical lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelID, final double[] values, final int size, final int offset) {
        nPlotVLines(labelID, values, size, offset);
    }

    private static native void nPlotVLines(String labelID, double[] values, int size, int offset); /*
        ImPlot::PlotVLines(labelID, values, size, offset);
    */

    /**
     * Plots infinite horizontal lines (e.g. for references or asymptotes).
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotHLines(final String labelID, final T[] values) {
        plotHLines(labelID, values, 0);
    }

    /**
     * Plots infinite horizontal lines (e.g. for references or asymptotes).
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotHLines(final String labelID, final T[] values, final int offset) {
        final double[] v = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = values[i].doubleValue();
        }

        nPlotHLines(labelID, v, v.length, offset);
    }

    /**
     * Plots infinite horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelID, final double[] values, final int size, final int offset) {
        nPlotHLines(labelID, values, size, offset);
    }

    private static native void nPlotHLines(String labelID, double[] values, int size, int offset); /*
        ImPlot::PlotHLines(labelID, values, size, offset);
    */

    /**
     * Plots a pie chart. If the sum of values {@code >} 1, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotPieChart(final String[] labelIDs, final T[] values, final double x, final double y, final double radius) {
        final double[] v = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = values[i].doubleValue();
        }

        String labelIDsSs = "";
        boolean first = true;
        int maxSize = 0;
        for (String s : labelIDs) {
            if (first) {
                first = false;
                continue;
            } else {
                labelIDsSs += "\n";
            }
            if (s.length() > maxSize) {
                maxSize = s.length();
            }

            labelIDsSs += s.replace("\n", "");
        }

        nPlotPieChart(labelIDsSs, maxSize, v, v.length, x, y, radius);
    }

    /**
     * Plots a pie chart. If the sum of values {@code >} 1, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String labelIDsSs, final int strLen, final double[] values, final int size, final double x, final double y, final double radius) {
        nPlotPieChart(labelIDsSs, strLen, values, size, x, y, radius);
    }

    //JNI function splits up passed string labelIDsSs to array for use in C++, as String[] is not converted by JNI
    private static native void nPlotPieChart(String labelIDsSs, int strLen, double[] values, int size, double x, double y, double radius); /*
        char** labelIDs = new char*[size];

        for (int pos = 0; pos < size; pos++) {
            char* str = new char[strLen + 1];
            char* str_i = str;

            while (*labelIDsSs != '\n' && *labelIDsSs != '\0') {
                *str = *labelIDsSs;
                labelIDsSs++;
                str++;
            }
            labelIDsSs++; //move past \n

            labelIDs[pos] = str_i;
        }

        ImPlot::PlotPieChart(labelIDs, values, size, x, y, radius);

        for (int i = 0; i < size; i++) {
            delete labelIDs[i];
        }
        delete[] labelIDs;
    */

    /**
     * Plots a 2D heatmap chart.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     * @param values must have fixed dimensions (all arrays are same length)
     */
    public static <T extends Number> void plotHeatmap(final String labelID, final T[][] values, final int offset) {
        final double[] v = new double[values.length * values[0].length];
        int pos = 0;
        for (T[] a : values) {
            for (T p : a) {
                v[pos++] = p.doubleValue();
            }
        }

        nPlotHeatmap(labelID, v, values[0].length, values.length);
    }

    /**
     * Plots a 2D heatmap chart.
     * @param values must have fixed dimensions (all arrays are same length)
     */
    public static void plotHeatmap(final String labelID, final double[] values, final int rows, final int cols) {
        nPlotHeatmap(labelID, values, rows, cols);
    }

    private static native void nPlotHeatmap(String labelID, double[] values, int rows, int cols); /*
        ImPlot::PlotHeatmap(labelID, values, rows, cols);
    */

//    // Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
//    // If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
//    // If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
//    template <typename T> IMPLOT_API double PlotHistogram(const char* labelID, const T* values, int count, int bins=ImPlotBin_Sturges, bool cumulative=false, bool density=false, ImPlotRange range=ImPlotRange(), bool outliers=true, double bar_scale=1.0);
//

    /**
     * Plots a horizontal histogram
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> double plotHistogram(final String labelID, final T[] values) {
        final double[] v = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = values[i].doubleValue();
        }

        return nPlotHistogram(labelID, v, v.length);
    }

    /**
     * Plots a horizontal histogram.
     */
    public static double plotHistogram(final String labelID, final double[] values, final int size) {
        return nPlotHistogram(labelID, values, size);
    }

    private static native double nPlotHistogram(String labelID, double[] values, int size); /*
        return ImPlot::PlotHistogram(labelID, values, size);
    */

    /**
     * Plots two dimensional, bivariate histogram as a heatmap
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> double plotHistogram2D(final String labelID, final T[] xs, final T[] ys) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        return nPlotHistogram2D(labelID, x, y, x.length);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap.
     */
    public static double plotHistogram2D(final String labelID, final double[] xs, final double[] ys, final int size) {
        return nPlotHistogram2D(labelID, xs, ys, size);
    }

    private static native double nPlotHistogram2D(String labelID, double[] xs, double[] ys, int size); /*
        return ImPlot::PlotHistogram2D(labelID, xs, ys, size);
    */

//    //
//    template <typename T> IMPLOT_API void PlotDigital(const char* labelID, const T* xs, const T* ys, int count, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotDigitalG(const char* labelID, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, int offset=0);
//

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     * Due to conversion from T to double, extremely large 64-bit integer (long) values may lose data!
     */
    public static <T extends Number> void plotDigital(final String labelID, final T[] xs, final T[] ys) {
        final double[] x = new double[xs.length];
        final double[] y = new double[ys.length];
        convertArrays(xs, ys, x, y);

        nPlotDigital(labelID, x, y, x.length);
    }

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelID, final double[] xs, final double[] ys, final int size) {
        nPlotDigital(labelID, xs, ys, size);
    }

    private static native void nPlotDigital(String labelID, double[] xs, double[] ys, int size); /*
        ImPlot::PlotDigital(labelID, xs, ys, size);
    */

    //TODO not yet supported
//    // Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
//    IMPLOT_API void PlotImage(const char* labelID, ImTextureID user_texture_id, const ImPlotPoint& bounds_min, const ImPlotPoint& bounds_max, const ImVec2& uv0=ImVec2(0,0), const ImVec2& uv1=ImVec2(1,1), const ImVec4& tint_col=ImVec4(1,1,1,1));

    /**
     * Plots a centered text label at point x,y
     */
    public static void plotText(final String text, final double x, final double y) {
        plotText(text, x, y, false);
    }

    /**
     * Plots a centered text label at point x,y
     */
    public static native void plotText(String text, double x, double y, boolean vertical); /*
        ImPlot::PlotText(text, x, y, vertical);
    */

    /**
     * Plots a dummy item (i.e. adds a legend entry colored by ImPlotCol_Line)
     */
    public static native void plotDummy(String labelID); /*
        ImPlot::PlotDummy(labelID);
    */

    //-----------------------------------------------------------------------------
    // Plot Utils
    //-----------------------------------------------------------------------------

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the axes range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the axes limits will be locked.
     */
    public static native void setNextPlotLimits(double xmin, double xmax, double ymin, double ymax, int imguicond); /*
        ImPlot::SetNextPlotLimits(xmin, xmax, ymin, ymax, imguicond);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the X axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the X axis limits will be locked.
     */
    public static native void setNextPlotLimitsX(double xmin, double xmax, int imguicond); /*
        ImPlot::SetNextPlotLimitsX(xmin, xmax, imguicond);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the Y axis limits will be locked.
     */
    public static native void setNextPlotLimitsY(double ymin, double ymax, int imguicond); /*
        ImPlot::SetNextPlotLimitsY(ymin, ymax, imguicond);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xmin, final ImDouble xmax, final ImDouble ymin, final ImDouble ymax) {
        linkNextPlotLimits(xmin, xmax, ymin, ymax, null, null, null, null);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xmin, final ImDouble xmax, final ImDouble ymin, final ImDouble ymax, final ImDouble ymin2, final ImDouble ymax2) {
        linkNextPlotLimits(xmin, xmax, ymin, ymax, ymin2, ymax2, null, null);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xmin, final ImDouble xmax, final ImDouble ymin, final ImDouble ymax, final ImDouble ymin2, final ImDouble ymax2, final ImDouble ymin3, final ImDouble ymax3) {
        nLinkNextPlotLimits(xmin.getData(), xmax.getData(), ymin.getData(), ymax.getData(), ymin2.getData(), ymax2.getData(), ymin3.getData(), ymax3.getData());
    }

    private static native void nLinkNextPlotLimits(double[] xmin, double[] xmax, double[] ymin, double[] ymax, double[] ymin2, double[] ymax2, double[] ymin3, double[] ymax3); /*
        ImPlot::LinkNextPlotLimits(&xmin[0], &xmax[0], &ymin[0], &ymax[0], &ymin2[0], &ymax2[0], &ymin3[0], &ymax3[0]);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes() {
        fitNextPlotAxes(true, true, true, true);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes(final boolean x, final boolean y) {
        fitNextPlotAxes(x, y, true, true);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static native void fitNextPlotAxes(boolean x, boolean y, boolean y2, boolean y3); /*
        ImPlot::FitNextPlotAxes(x, y, y2, y3);
    */


    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the X axis ticks and optionally the labels for the next plot.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks) {
        setNextPlotTicksX(xMin, xMax, nTicks, null, false);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keepDefault=true.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks, final String[] labels, final boolean keepDefault) {
        final String[] labelStrings = new String[4];
        for (int i = 0; i < 4; i++) {
            if (labels != null && i < labels.length) {
                labelStrings[i] = labels[i];
            } else {
                labelStrings[i] = null;
            }
        }

        nSetNextPlotTicksX(xMin, xMax, nTicks, labelStrings[0], labelStrings[1], labelStrings[2], labelStrings[3], keepDefault);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keepDefault=true.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks, final String s1, final String s2, final String s3, final String s4, final boolean keepDefault) {
        nSetNextPlotTicksX(xMin, xMax, nTicks, s1, s2, s3, s4, keepDefault);
    }

    private static native void nSetNextPlotTicksX(double xMin, double xMax, int nTicks, String s1, String s2, String s3, String s4, boolean keepDefault); /*
        char* strings[] = {s1, s2, s3, s4};
        ImPlot::SetNextPlotTicksX(xMin, xMax, nTicks, strings, keepDefault);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot.
     */
    public static void setNextPlotTicksY(final double xMin, final double xMax, final int nTicks) {
        setNextPlotTicksY(xMin, xMax, nTicks, null, false, ImPlotYAxis.YAxis_1);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keepDefault=true.
     */
    public static void setNextPlotTicksY(final double xMin, final double xMax, final int nTicks, final String[] labels, final boolean keepDefault) {
        setNextPlotTicksY(xMin, xMax, nTicks, labels, keepDefault, ImPlotYAxis.YAxis_1);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keepDefault=true.
     */
    public static void setNextPlotTicksY(final double xMin, final double xMax, final int nTicks, final String[] labels, final boolean keepDefault, final int yAxis) {
        final String[] labelStrings = new String[4];
        for (int i = 0; i < 4; i++) {
            if (labels != null && i < labels.length) {
                labelStrings[i] = labels[i];
            } else {
                labelStrings[i] = null;
            }
        }

        nSetNextPlotTicksY(xMin, xMax, nTicks, labelStrings[0], labelStrings[1], labelStrings[2], labelStrings[3], keepDefault, yAxis);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keepDefault=true.
     */
    public static void setNextPlotTicksY(final double xMin, final double xMax, final int nTicks, final String s1, final String s2, final String s3, final String s4, final boolean keepDefault, final int yAxis) {
        nSetNextPlotTicksY(xMin, xMax, nTicks, s1, s2, s3, s4, keepDefault, yAxis);
    }

    private static native void nSetNextPlotTicksY(double xMin, double xMax, int nTicks, String s1, String s2, String s3, String s4, boolean keepDefault, int yAxis); /*
        char* strings[] = {s1, s2, s3, s4};
        ImPlot::SetNextPlotTicksY(xMin, xMax, nTicks, strings, keepDefault, yAxis);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the format for numeric X axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.). Not applicable if ImPlotAxisFlags_Time enabled.
     */
    public static native void setNextPlotFormatX(String fmt); /*
        ImPlot::SetNextPlotFormatX(fmt);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the format for numeric Y axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.). Not applicable if ImPlotAxisFlags_Time enabled.
     */
    public static void setNextPlotFormatY(final String fmt) {
        setNextPlotFormatY(fmt, ImPlotYAxis.YAxis_1);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the format for numeric Y axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.). Not applicable if ImPlotAxisFlags_Time enabled.
     */
    public static native void setNextPlotFormatY(String fmt, int yAxis); /*
        ImPlot::SetNextPlotFormatY(fmt);
     */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Select which Y axis will be used for subsequent plot elements. The default is ImPlotYAxis_1, or the first (left) Y axis. Enable 2nd and 3rd axes with ImPlotFlags_YAxisX.
     */
    public static native void setPlotYAxis(int yAxis); /*
        ImPlot::SetPlotYAxis(yAxis);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Hides the next plot item.
     */
    public static void hideNextItem() {
        hideNextItem(true, ImGuiCond.Once);
    }

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Hides the next plot item. Use ImGuiCond. Always if you need to forcefully set this every frame (default ImGuiCond.Once).
     */
    public static native void hideNextItem(boolean hidden, int imguiCond); /*
        ImPlot::HideNextItem(hidden, imguiCond);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final ImVec2 pix, final int yAxis) {
        IMPLOT_POINT.ptr = nPixelsToPlot(pix.x, pix.y, yAxis);
        return IMPLOT_POINT;
    }

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * The returned ImPlotPoint should be manually deallocated with destroy()!
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final float x, final float y, final int yAxis) {
        IMPLOT_POINT.ptr = nPixelsToPlot(x, y, yAxis);
        return IMPLOT_POINT;
    }

    private static native long nPixelsToPlot(float x, float y, int yAxis); /*
        ImPlotPoint* p = new ImPlotPoint(ImPlot::PixelsToPlot(x, y, yAxis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(final ImPlotPoint plt, final int yAxis) {
        return plotToPixels(plt.getX(), plt.getY(), yAxis);
    }

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(final double x, final double y, final int yAxis) {
        final ImVec2 value = new ImVec2();
        nPlotToPixels(x, y, yAxis, value);
        return value;
    }

    private static native void nPlotToPixels(double x, double y, int yAxis, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ImPlot::PlotToPixels(x, y, yAxis), vec);
    */



    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Get the current Plot position (top-left) in pixels.
     */
    public static ImVec2 getPlotPos() {
        final ImVec2 value = new ImVec2();
        getPlotPos(value);
        return value;
    }

    public static native void getPlotPos(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ImPlot::GetPlotPos(), vec);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Get the current Plot size in pixels.
     */
    public static ImVec2 getPlotSize() {
        final ImVec2 value = new ImVec2();
        getPlotSize(value);
        return value;
    }

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Get the current Plot size in pixels.
     */
    public static native void getPlotSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ImPlot::GetPlotSize(), vec);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns true if the plot area in the current plot is hovered.
     */
    public static native boolean isPlotHovered(); /*
        return ImPlot::IsPlotHovered();
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns true if the XAxis plot area in the current plot is hovered.
     */
    public static native boolean isPlotXAxisHovered(); /*
        return ImPlot::IsPlotXAxisHovered();
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns true if the YAxis[n] plot area in the current plot is hovered.
     */
    public static native boolean isPlotYAxisHovered(int yAxis); /*
        return ImPlot::IsPlotYAxisHovered(yAxis);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * The returned ImPlotPoint should be manually deallocated with destroy()!
     * Returns the mouse position in x,y coordinates of the current plot. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint getPlotMousePos(final int yAxis) {
        IMPLOT_POINT.ptr = nGetPlotMousePos(yAxis);
        return IMPLOT_POINT;
    }

    private static native long nGetPlotMousePos(int yAxis); /*
        ImPlotPoint* p = new ImPlotPoint(ImPlot::GetPlotMousePos(yAxis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * The returned ImPlotLimits should be manually deallocated with destroy()!
     * Returns the current plot axis range. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotLimits getPlotLimits(final int yAxis) {
        IMPLOT_LIMITS.ptr = nGetPlotLimits(yAxis);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotLimits(int yAxis); /*
        ImPlotLimits* p = new ImPlotLimits(ImPlot::GetPlotLimits(yAxis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns true if the current plot is being box selected.
     */
    public static native boolean isPlotSelected(); /*
        return ImPlot::IsPlotSelected();
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * The returned ImPlotLimits should be manually deallocated with destroy()!
     * Returns the current plot box selection bounds.
     */
    public static ImPlotLimits getPlotSelection(final int yAxis) {
        IMPLOT_LIMITS.ptr = nGetPlotSelection(yAxis);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotSelection(int yAxis); /*
        ImPlotLimits* p = new ImPlotLimits(ImPlot::GetPlotSelection(yAxis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns true if the current plot is being queried or has an active query. Query must be enabled with ImPlotFlags_Query.
     */
    public static native boolean isPlotQueried(); /*
        return ImPlot::IsPlotQueried();
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * The returned ImPlotLimits should be manually deallocated with destroy()!
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static ImPlotLimits getPlotQuery(final int yAxis) {
        IMPLOT_LIMITS.ptr = nGetPlotQuery(yAxis);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotQuery(int yAxis); /*
        ImPlotLimits* p = new ImPlotLimits(ImPlot::GetPlotQuery(yAxis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void setPlotQuery(final ImPlotLimits query, final int yAxis) {
        nSetPlotQuery(query.ptr, yAxis);
    }

    private static native void nSetPlotQuery(long ptr, int yAxis); /*
        ImPlotLimits* query = (ImPlotLimits*)ptr;
        ImPlot::SetPlotQuery(*query, yAxis);
    */

    //-----------------------------------------------------------------------------
    // Plot Tools
    //-----------------------------------------------------------------------------

    /**
     * Shows an annotation callout at a chosen point.
     * Uses default color
     */
    public static void annotate(final double x, final double y, final ImVec2 pixOffset, final String... fmt) {
        annotate(x, y, pixOffset, new ImVec4(0, 0, 0, 0), fmt);
    }

    /**
     * Shows an annotation callout at a chosen point.
     */
    public static void annotate(final double x, final double y, final ImVec2 pixOffset, final ImVec4 color, final String... fmt) {
        nAnnotate(x, y, pixOffset.x, pixOffset.y, color.w, color.x, color.y, color.z,
                  fmt.length > 0 ? fmt[0] : null,
                  fmt.length > 1 ? fmt[1] : null,
                  fmt.length > 2 ? fmt[2] : null,
                  fmt.length > 3 ? fmt[3] : null,
                  fmt.length > 4 ? fmt[4] : null);
    }

    private static native void nAnnotate(double x, double y, float pixX, float pixY, float colA, float colB, float colC, float colD, String a, String b, String c, String d, String e); /*
        ImVec2 pixOffset(pixX, pixY);
        ImVec4 col(colA, colB, colC, colD);

        if (b == nullptr)
            ImPlot::Annotate(x, y, pixOffset, col, a);
        else if (b == nullptr)
            ImPlot::Annotate(x, y, pixOffset, col, a, b);
        else if (b == nullptr)
            ImPlot::Annotate(x, y, pixOffset, col, a, b, c);
        else if (b == nullptr)
            ImPlot::Annotate(x, y, pixOffset, col, a, b, c, d);
        else
            ImPlot::Annotate(x, y, pixOffset, col, a, b, c, d, e);
    */

    /**
     * Shows an annotation callout at a chosen point, clamped inside the plot area.
     * Uses default color
     */
    public static void annotateClamped(final double x, final double y, final ImVec2 pixOffset, final String... fmt) {
        annotateClamped(x, y, pixOffset, new ImVec4(0, 0, 0, 0), fmt);
    }

    /**
     * Shows an annotation callout at a chosen point, clamped inside the plot area.
     */
    public static void annotateClamped(final double x, final double y, final ImVec2 pixOffset, final ImVec4 color, final String... fmt) {
        nAnnotateClamped(x, y, pixOffset.x, pixOffset.y, color.w, color.x, color.y, color.z,
                  fmt.length > 0 ? fmt[0] : null,
                  fmt.length > 1 ? fmt[1] : null,
                  fmt.length > 2 ? fmt[2] : null,
                  fmt.length > 3 ? fmt[3] : null,
                  fmt.length > 4 ? fmt[4] : null);
    }

    private static native void nAnnotateClamped(double x, double y, float pixX, float pixY, float colA, float colB, float colC, float colD, String a, String b, String c, String d, String e); /*
        ImVec2 pixOffset(pixX, pixY);
        ImVec4 col(colA, colB, colC, colD);

        if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pixOffset, col, a);
        else if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pixOffset, col, a, b);
        else if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pixOffset, col, a, b, c);
        else if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pixOffset, col, a, b, c, d);
        else
            ImPlot::AnnotateClamped(x, y, pixOffset, col, a, b, c, d, e);
    */

//
//    //
//    IMPLOT_API bool DragLineX(const char* id, double* xValue, bool showLabel = true, const ImVec4& col = IMPLOT_AUTO_COL, float thickness = 1);

    /**
     * Shows a draggable vertical guide line at an x-value.
     */
    public static boolean dragLineX(final String id, final double xValue, final boolean showLabel, final ImVec4 color, final float thickness) {
        return nDragLineX(id, xValue, showLabel, color.w, color.x, color.y, color.z, thickness);
    }

    private static native boolean nDragLineX(String id, double xValue, boolean showLabel, float w, float x, float y, float z, float thickness); /*
        return ImPlot::DragLineX(id, &xValue, showLabel, ImVec4(w, x, y, z), thickness);
    */

    /**
     * Shows a draggable horizontal guide line at a y-value.
     */
    public static boolean dragLineY(final String id, final double yValue, final boolean showLabel, final ImVec4 color, final float thickness) {
        return nDragLineY(id, yValue, showLabel, color.w, color.x, color.y, color.z, thickness);
    }

    private static native boolean nDragLineY(String id, double yValue, boolean showLabel, float w, float x, float y, float z, float thickness); /*
        return ImPlot::DragLineY(id, &yValue, showLabel, ImVec4(w, x, y, z), thickness);
    */

    /**
     * Shows a draggable point at x,y.
     */
    public static boolean dragPoint(final String id, final double x, final double y, final boolean showLabel, final ImVec4 color, final float radius) {
        return nDragPoint(id, x, y, showLabel, color.w, color.x, color.y, color.z, radius);
    }

    private static native boolean nDragPoint(String id, double xValue, double yValue, boolean showLabel, float w, float x, float y, float z, float radius); /*
        return ImPlot::DragPoint(id, &xValue, &yValue, showLabel, ImVec4(w, x, y, z), radius);
    */


    //-----------------------------------------------------------------------------
    // Legend Utils and Tools
    //-----------------------------------------------------------------------------

    /**
     * Set the location of the current plot's legend
     */
    public static native void setLegendLocation(int location, int orientation, boolean outside); /*
        ImPlot::SetLegendLocation(location, orientation, outside);
    */

    /**
     * Set the location of the current plot's mouse position text
     */
    public static native void setMousePosLocation(int location); /*
        ImPlot::SetMousePosLocation(location);
    */

    /**
     * Returns true if a plot item legend entry is hovered.
     */
    public static native boolean isLegendEntryHovered(String labelID); /*
        return ImPlot::IsLegendEntryHovered(labelID);
    */

    /**
     * Begin a popup for a legend entry.
     */
    public static boolean beginLegendPopup(final String labelID) {
        return beginLegendPopup(labelID, ImGuiMouseButton.Right);
    }

    /**
     * Begin a popup for a legend entry.
     */
    public static native boolean beginLegendPopup(String labelID, int mouseButton); /*
        return ImPlot::BeginLegendPopup(labelID, mouseButton);
    */

    /**
     * End a popup for a legend entry.
     */
    public static native void endLegendPopup(); /*
        ImPlot::EndLegendPopup();
    */

    //-----------------------------------------------------------------------------
    // Drag and Drop Utils
    //-----------------------------------------------------------------------------

    /**
     * Turns the current plot's plotting area into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static native boolean beginDragDropTarget(); /*
        return ImPlot::BeginDragDropTarget();
    */

    /**
     * Turns the current plot's X-axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static native boolean beginDragDropTargetX(); /*
        return ImPlot::BeginDragDropTarget();
    */

    /**
     * Turns the current plot's Y-Axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static native boolean beginDragDropTargetY(int yAxis); /*
        return ImPlot::BeginDragDropTargetY(yAxis);
    */

    /**
     * Turns the current plot's legend into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static native boolean beginDragDropTargetLegend(); /*
        return ImPlot::BeginDragDropTargetLegend();
    */

    /**
     * Ends a drag and drop target.
     */
    public static native void endDragDropTarget(); /*
        ImPlot::EndDragDropTarget();
    */

    /**
     * Turns the current plot's plotting area into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSource(int keyMods, int dragDropFlags); /*
        return ImPlot::BeginDragDropSource(keyMods, dragDropFlags);
    */

    /**
     * Turns the current plot's X-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSourceX(int keyMods, int dragDropFlags); /*
        return ImPlot::BeginDragDropSourceX(keyMods, dragDropFlags);
    */

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSourceY(int yAxis, int keyMods, int dragDropFlags); /*
        return ImPlot::BeginDragDropSourceY(yAxis, keyMods, dragDropFlags);
    */

    /**
     * Turns an item in the current plot's legend into drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSourceItem(String labelID, int dragDropFlags); /*
        return ImPlot::BeginDragDropSourceItem(labelID, dragDropFlags);
    */

    /**
     * Ends a drag and drop source.
     */
    public static native void endDragDropSource(); /*
        ImPlot::EndDragDropSource();
    */

    //-----------------------------------------------------------------------------
    // Plot and Item Styling
    //-----------------------------------------------------------------------------

    /**
     * Provides access to plot style structure for permanant modifications to colors, sizes, etc.
     */
    public static ImPlotStyle getStyle() {
        IMPLOT_STYLE.ptr = nGetStyle();
        return IMPLOT_STYLE;
    }

    private static native long nGetStyle(); /*
        return (intptr_t)&ImPlot::GetStyle();
    */

    /**
     * Style plot colors for current ImGui style (default).
     */
    public static native void styleColorsAuto(); /*
        ImPlot::StyleColorsAuto();
    */

    /**
     * Style plot colors for ImGui "Classic".
     */
    public static native void styleColorsClassic(); /*
        ImPlot::StyleColorsClassic();
    */

    /**
     * Style plot colors for ImGui "Dark".
     */
    public static native void styleColorsDark(); /*
        ImPlot::StyleColorsDark();
    */

    /**
     * Style plot colors for ImGui "Light".
     */
    public static native void styleColorsLight(); /*
        ImPlot::StyleColorsLight();
    */

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    public static native void pushStyleColor(int idx, long col); /*
        ImPlot::PushStyleColor(idx, col);
    */

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    public static void pushStyleColor(final int idx, final ImVec4 col) {
        nPushStyleColor(idx, col.w, col.x, col.y, col.z);
    }

    private static native void nPushStyleColor(int idx, float w, float x, float y, float z); /*
        ImPlot::PushStyleColor(idx, ImVec4(w, x, y, z));
    */

    public static void popStyleColor() {
        popStyleColor(1);
    }

    public static native void popStyleColor(int count); /*
        ImPlot::PopStyleColor(count);
    */

    /**
     * Temporarily modify a style variable of float type. Don't forget to call PopStyleVar!
     */
    public static native void pushStyleVar(int idx, float val); /*
        ImPlot::PushStyleVar(idx, val);
    */

    /**
     * Temporarily modify a style variable of int type. Don't forget to call PopStyleVar!
     */
    public static native void pushStyleVar(int idx, int val); /*
        ImPlot::PushStyleVar(idx, (int)val);
    */

    /**
     * Temporarily modify a style variable of ImVec2 type. Don't forget to call PopStyleVar!
     */
    public static void pushStyleVar(final int idx, final ImVec2 val) {
        nPushStyleVar(idx, val.x, val.y);
    }

    private static native void nPushStyleVar(int idx, float x, float y); /*
        ImPlot::PushStyleVar(idx, ImVec2(x, y));
    */

    /**
     * Undo temporary style variable modification(s). Undo multiple pushes at once by increasing count.
     */
    public static void popStyleVar() {
        popStyleVar(1);
    }

    /**
     * Undo temporary style variable modification(s). Undo multiple pushes at once by increasing count.
     */
    public static native void popStyleVar(int count); /*
        ImPlot::PopStyleVar(count);
    */

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static ImVec4 getLastItemColor() {
        return new ImVec4(nGetLastItemColorS(0), nGetLastItemColorS(1), nGetLastItemColorS(2), nGetLastItemColorS(3));
    }

    private static native float nGetLastItemColorS(int selection); /*
        if (selection == 0)
            return ImPlot::GetLastItemColor().w;
        else if (selection == 1)
            return ImPlot::GetLastItemColor().x;
        else if (selection == 2)
            return ImPlot::GetLastItemColor().y;
        else
            return ImPlot::GetLastItemColor().z;
    */

    /**
     * Returns the string name for an ImPlotCol.
     */
    public static native String getStyleColorName(int idx); /*
        return env->NewStringUTF(ImPlot::GetStyleColorName(idx));
    */

    /**
     * Returns the null terminated string name for an ImPlotMarker.
     */
    public static native String getMarkerName(int idx); /*
        return env->NewStringUTF(ImPlot::GetMarkerName(idx));
    */

    //-----------------------------------------------------------------------------
    // Colormaps
    //-----------------------------------------------------------------------------

    /**
     * Add a new colormap. The color data will be copied. The colormap can be used by pushing either the returned index or the
     * string name with PushColormap. The colormap name must be unique and the size must be greater than 1. You will receive
     * an assert otherwise! Colormaps are considered to be qualitative (i.e. discrete).
     */
    public static int addColormap(final String name, final ImVec4[] cols) {
        final float[] w = new float[cols.length];
        final float[] x = new float[cols.length];
        final float[] y = new float[cols.length];
        final float[] z = new float[cols.length];

        for (int i = 0; i < cols.length; i++) {
            w[i] = cols[i].w;
            x[i] = cols[i].x;
            y[i] = cols[i].y;
            z[i] = cols[i].z;
        }

        return nAddColormap(name, w, x, y, z, cols.length);
    }

    private static native int nAddColormap(String name, float[] w, float[] x, float[] y, float[] z, int count); /*
        ImVec4* cols = new ImVec4[count];
        for (int i = 0; i < count; i++) {
            cols[i] = ImVec4(w[i], x[i], y[i], z[i]);
        }

        return ImPlot::AddColormap(name, cols, count);
    */

    /**
     * Returns the number of available colormaps (i.e. the built-in + user-added count).
     */
    public static native int getColormapCount(); /*
        return ImPlot::GetColormapCount();
    */

    /**
     * Returns a string name for a colormap given an index.
     */
    public static native String getColormapName(int cmap); /*
        return env->NewStringUTF(ImPlot::GetColormapName(cmap));
    */

    /**
     * Returns an index number for a colormap given a valid string name. Returns -1 if the name is invalid.
     */
    public static native int getColormapIndex(String name); /*
        return ImPlot::GetColormapIndex(name);
    */

    /**
     * Temporarily switch to one of the built-in (i.e. ImPlotColormap_XXX) or user-added colormaps (i.e. a return value of AddColormap). Don't forget to call PopColormap!
     */
    public static native void pushColormap(int cmap); /*
        ImPlot::PushColormap(cmap);
    */

    /**
     * Push a colormap by string name. Use built-in names such as "Default", "Deep", "Jet", etc. or a string you provided to AddColormap. Don't forget to call PopColormap!
     */
    public static native void pushColormap(String name); /*
        ImPlot::PushColormap(name);
    */

    /**
     * Undo temporary colormap modification(s). Undo multiple pushes at once by increasing count.
     */
    public static void popColormap() {
        popColormap(1);
    }

    /**
     * Undo temporary colormap modification(s). Undo multiple pushes at once by increasing count.
     */
    public static native void popColormap(int count); /*
        ImPlot::PopColormap(count);
    */

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static ImVec4 nextColormapColor() {
        final ImVec4 vec = new ImVec4();
        nNextColormapColor(vec);
        return vec;
    }

    private static native void nNextColormapColor(ImVec4 vec); /*
        Jni::ImVec4Cpy(env, ImPlot::NextColormapColor(), vec);
    */

    /**
     * Returns the size of a colormap.
     */
    public static native int getColormapSize(int cmap); /*
        return ImPlot::GetColormapSize(cmap);
    */

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static ImVec4 getColormapColor(final int idx, final int cmap) {
        final ImVec4 vec = new ImVec4();
        nGetColormapColor(idx, cmap, vec);
        return vec;
    }

    private static native void nGetColormapColor(int idx, int cmap, ImVec4 vec); /*
        Jni::ImVec4Cpy(env, ImPlot::GetColormapColor(idx, cmap), vec);
    */

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static ImVec4 sampleColormap(final float t, final int cmap) {
        final ImVec4 vec = new ImVec4();
        nSampleColormap(t, cmap, vec);
        return vec;
    }

    private static native void nSampleColormap(float t, int cmap, ImVec4 vec); /*
        Jni::ImVec4Cpy(env, ImPlot::SampleColormap(t, cmap), vec);
    */

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static native void colormapScale(String label, double scaleMin, double scaleMax, int cmap); /*
        ImPlot::ColormapScale(label, scaleMin, scaleMax, ImVec2(0,0), cmap);
    */

    /**
     * Shows a horizontal slider with a colormap gradient background.
     */
    public static native boolean colormapSlider(String label, float t, int cmap); /*
        return ImPlot::ColormapSlider(label, &t, NULL, "", cmap);
    */

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static native boolean colormapButton(String label, int cmap); /*
        return ImPlot::ColormapButton(label, ImVec2(0,0), cmap);
    */

    /**
     * When items in a plot sample their color from a colormap, the color is cached and does not change
     * unless explicitly overriden. Therefore, if you change the colormap after the item has already been plotted,
     * item colors will NOT update. If you need item colors to resample the new colormap, then use this
     * function to bust the cached colors. If #plot_title_id is NULL, then every item in EVERY existing plot
     * will be cache busted. Otherwise only the plot specified by #plot_title_id will be busted. For the
     * latter, this function must be called in the same ImGui ID scope that the plot is in. You should rarely if ever
     * need this function, but it is available for applications that require runtime colormap swaps (e.g. Heatmaps demo).
     */
    public static void bustColorCache() {
        bustColorCache(null);
    }

    /**
     * When items in a plot sample their color from a colormap, the color is cached and does not change
     * unless explicitly overriden. Therefore, if you change the colormap after the item has already been plotted,
     * item colors will NOT update. If you need item colors to resample the new colormap, then use this
     * function to bust the cached colors. If #plot_title_id is NULL, then every item in EVERY existing plot
     * will be cache busted. Otherwise only the plot specified by #plot_title_id will be busted. For the
     * latter, this function must be called in the same ImGui ID scope that the plot is in. You should rarely if ever
     * need this function, but it is available for applications that require runtime colormap swaps (e.g. Heatmaps demo).
     */
    public static native void bustColorCache(String plotTableID); /*
        ImPlot::BustColorCache(plotTableID);
    */

    //-----------------------------------------------------------------------------
    // Miscellaneous
    //-----------------------------------------------------------------------------

    /**
     * Render icons similar to those that appear in legends (nifty for data lists).
     */
    public static void itemIcon(final ImVec4 col) {
        nItemIcon(col.w, col.x, col.y, col.z);
    }

    private static native void nItemIcon(double a, double b, double c, double d); /*
        ImPlot::ItemIcon(ImVec4(a, b, c, d));
    */

    /**
     * Render icons similar to those that appear in legends (nifty for data lists).
     */
    public static native void colormapIcon(int colorMap); /*
        ImPlot::ColormapIcon(colorMap);
    */

    /**
     * Get the plot draw list for custom rendering to the current plot area. Call between Begin/EndPlot.
     */
    public static ImDrawList getPlotDrawList() {
        IM_DRAW_LIST.ptr = nGetPlotDrawList();
        return IM_DRAW_LIST;
    }

    private static native long nGetPlotDrawList(); /*
        return (intptr_t)ImPlot::GetPlotDrawList();
    */

    /**
     * Push clip rect for rendering to current plot area. The rect can be expanded or contracted by #expand pixels. Call between Begin/EndPlot.
     */
    public static native void pushPlotClipRect(float expand); /*
        ImPlot::PushPlotClipRect(expand);
    */

    /**
     * Pop plot clip rect. Call between Begin/EndPlot.
     */
    public static native void popPlotClipRect(); /*
        ImPlot::PopPlotClipRect();
    */

    /**
     * Shows ImPlot style selector dropdown menu.
     */
    public static native boolean showStyleSelector(String label); /*
        return ImPlot::ShowStyleSelector(label);
    */

    /**
     * Shows ImPlot colormap selector dropdown menu.
     */
    public static native boolean showColormapSelector(String label); /*
        return ImPlot::ShowColormapSelector(label);
    */

    /**
     * Shows ImPlot style editor block (not a window).
     */
    public static native void showStyleEditor(); /*
        ImPlot::ShowStyleEditor(NULL);
    */

    /**
     * Add basic help/info block for end users (not a window).
     */
    public static native void showUserGuide(); /*
        ImPlot::ShowUserGuide();
    */

    /**
     * Shows ImPlot metrics/debug information window.
     */
    public static native void showMetricsWindow(); /*
        ImPlot::ShowMetricsWindow(NULL);
    */

    //-----------------------------------------------------------------------------
    // Demo
    //-----------------------------------------------------------------------------

    /**
     * Shows the ImPlot demo window.
     */
    public static void showDemoWindow(final ImBoolean pOpen) {
        nShowDemoWindow(pOpen.getData());
    }

    private static native void nShowDemoWindow(boolean[] pOpen); /*
        ImPlot::ShowDemoWindow(&pOpen[0]);
    */
}
