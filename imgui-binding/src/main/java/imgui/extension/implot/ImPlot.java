package imgui.extension.implot;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImFloat;

@BindingSource
public final class ImPlot {
    private ImPlot() {
    }

    /*JNI
        #include "_implot.h"
     */

    //-----------------------------------------------------------------------------
    // ImPlot Context
    //-----------------------------------------------------------------------------

    /**
     * Creates a new ImPlot context. Call this after ImGui.createContext().
     */
    @BindingMethod
    public static native ImPlotContext CreateContext();

    /**
     * Destroys an ImPlot context. Call this before ImGui.destroyContext(). NULL = destroy current context.
     */
    @BindingMethod
    public static native void DestroyContext(@OptArg ImPlotContext ctx);

    /**
     * Returns the current ImPlot context. NULL if no context has ben set.
     */
    @BindingMethod
    public static native ImPlotContext GetCurrentContext();

    /**
     * Sets the current ImPlot context.
     */
    @BindingMethod
    public static native void SetCurrentContext(ImPlotContext ctx);

    @BindingMethod
    public static native void SetImGuiContext(ImGuiContext ctx);

    //-----------------------------------------------------------------------------
    // Begin/End Plot
    //-----------------------------------------------------------------------------

    // Starts a 2D plotting context. If this function returns true, EndPlot() MUST
    // be called! You are encouraged to use the following convention:
    //
    // if (ImPlot.beginPlot(...)) {
    //     ImPlot.plotLine(...);
    //     ...
    //     ImPlot.endPlot();
    // }
    //
    // Important notes:
    //
    // - #titleID must be unique to the current ImGui ID scope. If you need to avoid ID
    //   collisions or don't want to display a title in the plot, use double hashes
    //   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
    // - If #xLabel and/or #yLabel are provided, axes labels will be displayed.
    // - #size is the **frame** size of the plot widget, not the plot area. The default
    //   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
    //   (default is 400x300 px).
    // - Auxiliary y-axes must be enabled with ImPlotFlags_YAxis2/3 to be displayed.
    // - See ImPlotFlags and ImPlotAxisFlags for more available options.

    @BindingMethod
    public static native boolean BeginPlot(String titleID,
                                           @OptArg String xLabel,
                                           @OptArg String yLabel,
                                           @OptArg(callValue = "ImVec2(-1,0)") ImVec2 size,
                                           @OptArg int flags,
                                           @OptArg int xFlags,
                                           @OptArg int yFlags,
                                           @OptArg int y2Flags,
                                           @OptArg int y3Flags,
                                           @OptArg String y2Label,
                                           @OptArg String y3Label);

    /**
     * Only call EndPlot() if beginPlot() returns true! Typically called at the end
     * of an if statement conditioned on BeginPlot(). See example in beginPlot().
     */
    @BindingMethod
    public static native void EndPlot();

    //-----------------------------------------------------------------------------
    // Begin/EndSubplots
    //-----------------------------------------------------------------------------

    // Starts a subdivided plotting context. If the function returns true,
    // EndSubplots() MUST be called! Call BeginPlot/EndPlot AT MOST [rows*cols]
    // times in  between the begining and end of the subplot context. Plots are
    // added in row major order.
    //
    // Example:
    //
    // if (BeginSubplots("My Subplot",2,3,ImVec2(800,400)) {
    //     for (int i = 0; i < 6; ++i) {
    //         if (BeginPlot(...)) {
    //             ImPlot::PlotLine(...);
    //             ...
    //             EndPlot();
    //         }
    //     }
    //     EndSubplots();
    // }
    //
    // Produces:
    //
    // [0][1][2]
    // [3][4][5]
    //
    // Important notes:
    //
    // - #title_id must be unique to the current ImGui ID scope. If you need to avoid ID
    //   collisions or don't want to display a title in the plot, use double hashes
    //   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
    // - #rows and #cols must be greater than 0.
    // - #size is the size of the entire grid of subplots, not the individual plots
    // - #row_ratios and #col_ratios must have AT LEAST #rows and #cols elements,
    //   respectively. These are the sizes of the rows and columns expressed in ratios.
    //   If the user adjusts the dimensions, the arrays are updated with new ratios.
    //
    // Important notes regarding BeginPlot from inside of BeginSubplots:
    //
    // - The #title_id parameter of _BeginPlot_ (see above) does NOT have to be
    //   unique when called inside of a subplot context. Subplot IDs are hashed
    //   for your convenience so you don't have call PushID or generate unique title
    //   strings. Simply pass an empty string to BeginPlot unless you want to title
    //   each subplot.
    // - The #size parameter of _BeginPlot_ (see above) is ignored when inside of a
    //   subplot context. The actual size of the subplot will be based on the
    //   #size value you pass to _BeginSubplots_ and #row/#col_ratios if provided.

    @BindingMethod
    public static native boolean BeginSubplots(String titleID,
                                               int rows,
                                               int cols,
                                               ImVec2 size,
                                               @OptArg(callValue = "ImPlotSubplotFlags_None") int flags,
                                               @OptArg float[] rowRatios,
                                               @OptArg float[] colRatios);

    /**
     * Only call EndSubplots() if BeginSubplots() returns true! Typically called at the end
     * of an if statement conditioned on BeginSublots(). See example above.
     */
    @BindingMethod
    public static native void EndSubplots();

    //-----------------------------------------------------------------------------
    // Plot Items
    //-----------------------------------------------------------------------------

    /*JNI
        // For a proper type conversion, since C++ doesn't have a "long" type.
        #define long ImS64
        #define LEN(arg) (int)env->GetArrayLength(obj_##arg)
     */

    // The template functions below are explicitly instantiated in implot_items.cpp.
    // They are not intended to be used generically with custom types. You will get
    // a linker error if you try! All functions support the following scalar types:
    //
    // float, double, ImS8, ImU8, ImS16, ImU16, ImS32, ImU32, ImS64, ImU64
    //
    //
    // If you need to plot custom or non-homogenous data you have a few options:
    //
    // 1. If your data is a simple struct/class (e.g. Vector2f), you can use striding.
    //    This is the most performant option if applicable.
    //
    //    struct Vector2f { float X, Y; };
    //    ...
    //    Vector2f data[42];
    //    ImPlot::PlotLine("line", &data[0].x, &data[0].y, 42, 0, sizeof(Vector2f)); // or sizeof(float)*2
    //
    // 2. Write a custom getter C function or C++ lambda and pass it and optionally your data to
    //    an ImPlot function post-fixed with a G (e.g. PlotScatterG). This has a slight performance
    //    cost, but probably not enough to worry about unless your data is very large. Examples:
    //
    //    ImPlotPoint MyDataGetter(void* data, int idx) {
    //        MyData* my_data = (MyData*)data;
    //        ImPlotPoint p;
    //        p.x = my_data->GetTime(idx);
    //        p.y = my_data->GetValue(idx);
    //        return p
    //    }
    //    ...
    //    auto my_lambda = [](void*, int idx) {
    //        double t = idx / 999.0;
    //        return ImPlotPoint(t, 0.5+0.5*std::sin(2*PI*10*t));
    //    };
    //    ...
    //    if (ImPlot::BeginPlot("MyPlot")) {
    //        MyData my_data;
    //        ImPlot::PlotScatterG("scatter", MyDataGetter, &my_data, my_data.Size());
    //        ImPlot::PlotLineG("line", my_lambda, nullptr, 1000);
    //        ImPlot::EndPlot();
    //    }
    //
    // NB: All types are converted to double before plotting. You may lose information
    // if you try plotting extremely large 64-bit integral types. Proceed with caution!

    // values

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    // xs,ys

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D line plot.
     */
    @BindingMethod
    public static native void PlotLine(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    // values

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    // xs,ys

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    @BindingMethod
    public static native void PlotScatter(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    // values

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    // xs,ys

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    @BindingMethod
    public static native void PlotStairs(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    // values

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    // xs,ys

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    // xs,ys1,ys2

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, short[] xs, short[] ys1, short[] ys2, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, int[] xs, int[] ys1, int[] ys2, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, long[] xs, long[] ys1, long[] ys2, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, float[] xs, float[] ys1, float[] ys2, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    @BindingMethod
    public static native void PlotShaded(String labelId, double[] xs, double[] ys1, double[] ys2, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    // values

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double width, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double width, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double width, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double width, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double width, @OptArg double shift, @OptArg int offset);

    // xs,ys

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void width, @OptArg int offset);

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    @BindingMethod
    public static native void PlotBars(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void width, @OptArg int offset);

    // values

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double height, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double height, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double height, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double height, @OptArg double shift, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double height, @OptArg double shift, @OptArg int offset);

    // xs,ys

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, double height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void height, @OptArg int offset);

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    @BindingMethod
    public static native void PlotBarsH(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @ArgValue(callValue = "0.67") Void height, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, short[] xs, short[] ys, short[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, int[] xs, int[] ys, int[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, long[] xs, long[] ys, long[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, float[] xs, float[] ys, float[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, double[] xs, double[] ys, double[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBars(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, short[] xs, short[] ys, short[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, int[] xs, int[] ys, int[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, long[] xs, long[] ys, long[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, float[] xs, float[] ys, float[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, double[] xs, double[] ys, double[] err, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    @BindingMethod
    public static native void PlotErrorBarsH(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    // values

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, short[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, int[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, long[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, float[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, double[] values, @ArgValue(callValue = "LEN(values)") Void count, @OptArg double yRef, @OptArg double xscale, @OptArg double x0, @OptArg int offset);

    // xs,ys

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots vertical stems.
     */
    @BindingMethod
    public static native void PlotStems(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg double yRef, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotVLines(String labelId, short[] xs, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotVLines(String labelId, int[] xs, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotVLines(String labelId, long[] xs, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotVLines(String labelId, float[] xs, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotVLines(String labelId, double[] xs, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotHLines(String labelId, short[] ys, @ArgValue(callValue = "LEN(ys)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotHLines(String labelId, int[] ys, @ArgValue(callValue = "LEN(ys)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotHLines(String labelId, long[] ys, @ArgValue(callValue = "LEN(ys)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotHLines(String labelId, float[] ys, @ArgValue(callValue = "LEN(ys)") Void count, @OptArg int offset);

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    @BindingMethod
    public static native void PlotHLines(String labelId, double[] ys, @ArgValue(callValue = "LEN(ys)") Void count, @OptArg int offset);

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotPieChart(String[] labelIds,
                                           short[] values,
                                           @ArgValue(callValue = "LEN(values)") Void count,
                                           double x,
                                           double y,
                                           double radius,
                                           @OptArg(callValue = "false") boolean normalize,
                                           @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                           @OptArg double angle0);

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotPieChart(String[] labelIds,
                                           int[] values,
                                           @ArgValue(callValue = "LEN(values)") Void count,
                                           double x,
                                           double y,
                                           double radius,
                                           @OptArg(callValue = "false") boolean normalize,
                                           @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                           @OptArg double angle0);

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotPieChart(String[] labelIds,
                                           long[] values,
                                           @ArgValue(callValue = "LEN(values)") Void count,
                                           double x,
                                           double y,
                                           double radius,
                                           @OptArg(callValue = "false") boolean normalize,
                                           @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                           @OptArg double angle0);

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotPieChart(String[] labelIds,
                                           float[] values,
                                           @ArgValue(callValue = "LEN(values)") Void count,
                                           double x,
                                           double y,
                                           double radius,
                                           @OptArg(callValue = "false") boolean normalize,
                                           @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                           @OptArg double angle0);

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotPieChart(String[] labelIds,
                                           double[] values,
                                           @ArgValue(callValue = "LEN(values)") Void count,
                                           double x,
                                           double y,
                                           double radius,
                                           @OptArg(callValue = "false") boolean normalize,
                                           @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                           @OptArg double angle0);

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotHeatmap(String labelId,
                                          short[] values,
                                          int rows,
                                          int cols,
                                          @OptArg double scaleMin,
                                          @OptArg double scaleMax,
                                          @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                          @OptArg ImPlotPoint boundsMin,
                                          @OptArg ImPlotPoint boundsMax);

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotHeatmap(String labelId,
                                          int[] values,
                                          int rows,
                                          int cols,
                                          @OptArg double scaleMin,
                                          @OptArg double scaleMax,
                                          @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                          @OptArg ImPlotPoint boundsMin,
                                          @OptArg ImPlotPoint boundsMax);

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotHeatmap(String labelId,
                                          long[] values,
                                          int rows,
                                          int cols,
                                          @OptArg double scaleMin,
                                          @OptArg double scaleMax,
                                          @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                          @OptArg ImPlotPoint boundsMin,
                                          @OptArg ImPlotPoint boundsMax);

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotHeatmap(String labelId,
                                          float[] values,
                                          int rows,
                                          int cols,
                                          @OptArg double scaleMin,
                                          @OptArg double scaleMax,
                                          @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                          @OptArg ImPlotPoint boundsMin,
                                          @OptArg ImPlotPoint boundsMax);

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    @BindingMethod
    public static native void PlotHeatmap(String labelId,
                                          double[] values,
                                          int rows,
                                          int cols,
                                          @OptArg double scaleMin,
                                          @OptArg double scaleMax,
                                          @OptArg(callValue = "\"%.1f\"") String labelFmt,
                                          @OptArg ImPlotPoint boundsMin,
                                          @OptArg ImPlotPoint boundsMax);

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram(String labelId,
                                              short[] values,
                                              @ArgValue(callValue = "LEN(values)") Void count,
                                              @OptArg(callValue = "ImPlotBin_Sturges") int bins,
                                              @OptArg boolean cumulative,
                                              @OptArg boolean density,
                                              @OptArg(callValue = "ImPlotRange()") ImPlotRange range,
                                              @OptArg(callValue = "true") boolean outliers,
                                              @OptArg double barScale);

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram(String labelId,
                                              int[] values,
                                              @ArgValue(callValue = "LEN(values)") Void count,
                                              @OptArg(callValue = "ImPlotBin_Sturges") int bins,
                                              @OptArg boolean cumulative,
                                              @OptArg boolean density,
                                              @OptArg(callValue = "ImPlotRange()") ImPlotRange range,
                                              @OptArg(callValue = "true") boolean outliers,
                                              @OptArg double barScale);

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram(String labelId,
                                              long[] values,
                                              @ArgValue(callValue = "LEN(values)") Void count,
                                              @OptArg(callValue = "ImPlotBin_Sturges") int bins,
                                              @OptArg boolean cumulative,
                                              @OptArg boolean density,
                                              @OptArg(callValue = "ImPlotRange()") ImPlotRange range,
                                              @OptArg(callValue = "true") boolean outliers,
                                              @OptArg double barScale);

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram(String labelId,
                                              float[] values,
                                              @ArgValue(callValue = "LEN(values)") Void count,
                                              @OptArg(callValue = "ImPlotBin_Sturges") int bins,
                                              @OptArg boolean cumulative,
                                              @OptArg boolean density,
                                              @OptArg(callValue = "ImPlotRange()") ImPlotRange range,
                                              @OptArg(callValue = "true") boolean outliers,
                                              @OptArg double barScale);

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram(String labelId,
                                              double[] values,
                                              @ArgValue(callValue = "LEN(values)") Void count,
                                              @OptArg(callValue = "ImPlotBin_Sturges") int bins,
                                              @OptArg boolean cumulative,
                                              @OptArg boolean density,
                                              @OptArg(callValue = "ImPlotRange()") ImPlotRange range,
                                              @OptArg(callValue = "true") boolean outliers,
                                              @OptArg double barScale);

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram2D(String labelId,
                                                short[] xs,
                                                short[] ys,
                                                @ArgValue(callValue = "LEN(xs)") Void count,
                                                @OptArg int xBins,
                                                @OptArg int yBins,
                                                @OptArg(callValue = "false") boolean density,
                                                @OptArg(callValue = "ImPlotLimits()") ImPlotLimits range,
                                                @OptArg boolean outliers);

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram2D(String labelId,
                                                int[] xs,
                                                int[] ys,
                                                @ArgValue(callValue = "LEN(xs)") Void count,
                                                @OptArg int xBins,
                                                @OptArg int yBins,
                                                @OptArg(callValue = "false") boolean density,
                                                @OptArg(callValue = "ImPlotLimits()") ImPlotLimits range,
                                                @OptArg boolean outliers);

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram2D(String labelId,
                                                long[] xs,
                                                long[] ys,
                                                @ArgValue(callValue = "LEN(xs)") Void count,
                                                @OptArg int xBins,
                                                @OptArg int yBins,
                                                @OptArg(callValue = "false") boolean density,
                                                @OptArg(callValue = "ImPlotLimits()") ImPlotLimits range,
                                                @OptArg boolean outliers);

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram2D(String labelId,
                                                float[] xs,
                                                float[] ys,
                                                @ArgValue(callValue = "LEN(xs)") Void count,
                                                @OptArg int xBins,
                                                @OptArg int yBins,
                                                @OptArg(callValue = "false") boolean density,
                                                @OptArg(callValue = "ImPlotLimits()") ImPlotLimits range,
                                                @OptArg boolean outliers);

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    @BindingMethod
    public static native double PlotHistogram2D(String labelId,
                                                double[] xs,
                                                double[] ys,
                                                @ArgValue(callValue = "LEN(xs)") Void count,
                                                @OptArg int xBins,
                                                @OptArg int yBins,
                                                @OptArg(callValue = "false") boolean density,
                                                @OptArg(callValue = "ImPlotLimits()") ImPlotLimits range,
                                                @OptArg boolean outliers);

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    @BindingMethod
    public static native void PlotDigital(String labelId, short[] xs, short[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    @BindingMethod
    public static native void PlotDigital(String labelId, int[] xs, int[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    @BindingMethod
    public static native void PlotDigital(String labelId, long[] xs, long[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    @BindingMethod
    public static native void PlotDigital(String labelId, float[] xs, float[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    @BindingMethod
    public static native void PlotDigital(String labelId, double[] xs, double[] ys, @ArgValue(callValue = "LEN(xs)") Void count, @OptArg int offset);

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    @BindingMethod
    public static native void PlotImage(String labelId, @ArgValue(callPrefix = "(ImTextureID)(intptr_t)") int userTextureId, ImPlotPoint boundsMin, ImPlotPoint boundsMax, @OptArg ImVec2 uv0, @OptArg ImVec2 uv1, @OptArg ImVec4 tintCol);

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    @BindingMethod
    public static native void PlotText(String text, double x, double y, @OptArg(callValue = "false") boolean vertical, @OptArg ImVec2 pixOffset);

    /**
     * Plots a dummy item (i.e. adds a legend entry colored by ImPlotCol_Line)
     */
    @BindingMethod
    public static native void PlotDummy(String labelID);

    /*JNI
        #undef LEN
        #undef long
     */

    //-----------------------------------------------------------------------------
    // Plot Utils
    //-----------------------------------------------------------------------------

    /**
     * Set the axes range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the axes limits will be locked.
     */
    @BindingMethod
    public static native void SetNextPlotLimits(double xMin, double xMax, double yMin, double yMax, @OptArg int cond);

    /**
     * Set the X axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the X axis limits will be locked.
     */
    @BindingMethod
    public static native void SetNextPlotLimitsX(double xMin, double xMax, @OptArg int cond);

    /**
     * Set the Y axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the Y axis limits will be locked.
     */
    @BindingMethod
    public static native void SetNextPlotLimitsY(double yMin, double yMax, @OptArg int cond);

    /**
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    @BindingMethod
    public static native void LinkNextPlotLimits(ImDouble xMin, ImDouble xMax, ImDouble yMin, ImDouble yMax, @OptArg ImDouble yMin2, @OptArg ImDouble yMax2, @OptArg ImDouble yMin3, @OptArg ImDouble yMax3);

    /**
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    @BindingMethod
    public static native void FitNextPlotAxes(@OptArg boolean x, @OptArg boolean y, @OptArg boolean y2, @OptArg boolean y3);

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    @BindingMethod
    public static native void SetNextPlotTicksX(double[] values, int nTicks, @OptArg(callValue = "NULL") String[] labels, @OptArg boolean keepDefault);

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    @BindingMethod
    public static native void SetNextPlotTicksX(double xMin, double xMax, int nTicks, @OptArg(callValue = "NULL") String[] labels, @OptArg boolean keepDefault);

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    @BindingMethod
    public static native void SetNextPlotTicksY(double[] values, int nTicks, @OptArg(callValue = "NULL") String[] labels, @OptArg(callValue = "false") boolean keepDefault, @OptArg int yAxis);

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    @BindingMethod
    public static native void SetNextPlotTicksY(double yMin, double yMax, int nTicks, @OptArg(callValue = "NULL") String[] labels, @OptArg(callValue = "false") boolean keepDefault, @OptArg int yAxis);

    /**
     * Set the format for numeric X axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.). Not applicable if ImPlotAxisFlags_Time enabled.
     */
    @BindingMethod
    public static native void SetNextPlotFormatX(String fmt);

    /**
     * Set the format for numeric Y axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.).
     */
    @BindingMethod
    public static native void SetNextPlotFormatY(String fmt, @OptArg int yAxis);

    /**
     * Select which Y axis will be used for subsequent plot elements. The default is ImPlotYAxis_1, or the first (left) Y axis. Enable 2nd and 3rd axes with ImPlotFlags_YAxisX.
     */
    @BindingMethod
    public static native void SetPlotYAxis(int yAxis);

    /**
     * Hides the next plot item. Use ImGuiCond.Always if you need to forcefully set this every frame (default ImGuiCond.Once).
     */
    @BindingMethod
    public static native void HideNextItem(@OptArg(callValue = "true") boolean hidden, @OptArg int cond);

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    @BindingMethod
    public static native ImPlotPoint PixelsToPlot(ImVec2 pix, @OptArg int yAxis);

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    @BindingMethod
    public static native ImVec2 PlotToPixels(ImPlotPoint plt, @OptArg int yAxis);

    /**
     * Get the current Plot position (top-left) in pixels.
     */
    @BindingMethod
    public static native ImVec2 GetPlotPos();

    /**
     * Get the current Plot size in pixels.
     */
    @BindingMethod
    public static native ImVec2 GetPlotSize();

    /**
     * Returns true if the plot area in the current plot is hovered.
     */
    @BindingMethod
    public static native boolean IsPlotHovered();

    /**
     * Returns true if the XAxis plot area in the current plot is hovered.
     */
    @BindingMethod
    public static native boolean IsPlotXAxisHovered();

    /**
     * Returns true if the YAxis[n] plot area in the current plot is hovered.
     */
    @BindingMethod
    public static native boolean IsPlotYAxisHovered(@OptArg int yAxis);

    /**
     * Returns the mouse position in x,y coordinates of the current plot. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    @BindingMethod
    public static native ImPlotPoint GetPlotMousePos(@OptArg int yAxis);

    /**
     * Returns the current plot axis range. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    @BindingMethod
    public static native ImPlotLimits GetPlotLimits(@OptArg int yAxis);

    /**
     * Returns true if the current plot is being box selected.
     */
    @BindingMethod
    public static native boolean IsPlotSelected();

    /**
     * Returns the current plot box selection bounds.
     */
    @BindingMethod
    public static native ImPlotLimits GetPlotSelection(@OptArg int yAxis);

    /**
     * Returns true if the current plot is being queried or has an active query. Query must be enabled with ImPlotFlags_Query.
     */
    @BindingMethod
    public static native boolean IsPlotQueried();

    /**
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    @BindingMethod
    public static native ImPlotLimits GetPlotQuery(@OptArg int yAxis);

    /**
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    @BindingMethod
    public static native void SetPlotQuery(ImPlotLimits query, @OptArg int yAxis);

    /**
     * Returns true if the bounding frame of a subplot is hovered
     */
    @BindingMethod
    public static native boolean IsSubplotsHovered();

    //-----------------------------------------------------------------------------
    // Aligned Plots
    //-----------------------------------------------------------------------------

    // Consider using Begin/EndSubplots first. They are more feature rich and
    // accomplish the same behaviour by default. The functions below offer lower
    // level control of plot alignment.

    /**
     * Align axis padding over multiple plots in a single row or column. If this function returns true, EndAlignedPlots() must be called. #group_id must be unique.
     */
    @BindingMethod
    public static native boolean BeginAlignedPlots(String groupId, @OptArg int orientation);

    /**
     * Only call EndAlignedPlots() if BeginAlignedPlots() returns true!
     */
    @BindingMethod
    public static native void EndAlignedPlots();

    //-----------------------------------------------------------------------------
    // Plot Tools
    //-----------------------------------------------------------------------------

    /**
     * Shows an annotation callout at a chosen point.
     */
    @BindingMethod
    public static native void Annotate(double x, double y, ImVec2 pixOffset, String fmt, Void NULL);

    /**
     * Shows an annotation callout at a chosen point.
     */
    @BindingMethod
    public static native void Annotate(double x, double y, ImVec2 pixOffset, ImVec4 color, String fmt, Void NULL);

    /**
     * Same as above, but the annotation will always be clamped to stay inside the plot area.
     */
    @BindingMethod
    public static native void AnnotateClamped(double x, double y, ImVec2 pixOffset, String fmt, Void NULL);

    /**
     * Same as above, but the annotation will always be clamped to stay inside the plot area.
     */
    @BindingMethod
    public static native void AnnotateClamped(double x, double y, ImVec2 pixOffset, ImVec4 color, String fmt, Void NULL);

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    @BindingMethod
    public static native boolean DragLineX(String id, ImDouble xValue, @OptArg(callValue = "true") boolean showLabel, @OptArg(callValue = "IMPLOT_AUTO_COL") ImVec4 color, @OptArg float thickness);

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    @BindingMethod
    public static native boolean DragLineY(String id, ImDouble yValue, @OptArg(callValue = "true") boolean showLabel, @OptArg(callValue = "IMPLOT_AUTO_COL") ImVec4 color, @OptArg float thickness);

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    @BindingMethod
    public static native boolean DragPoint(String id, ImDouble x, ImDouble y, @OptArg(callValue = "true") boolean showLabel, @OptArg(callValue = "IMPLOT_AUTO_COL") ImVec4 color, @OptArg float radius);

    //-----------------------------------------------------------------------------
    // Legend Utils and Tools
    //-----------------------------------------------------------------------------

    /**
     * Set the location of the current plot's legend
     */
    @BindingMethod
    public static native void SetLegendLocation(int location, @OptArg(callValue = "ImPlotOrientation_Vertical") int orientation, @OptArg boolean outside);

    /**
     * Set the location of the current plot's mouse position text
     */
    @BindingMethod
    public static native void SetMousePosLocation(int location);

    /**
     * Returns true if a plot item legend entry is hovered.
     */
    @BindingMethod
    public static native boolean IsLegendEntryHovered(String labelId);

    /**
     * Begin a popup for a legend entry.
     */
    @BindingMethod
    public static native boolean BeginLegendPopup(String labelID, @OptArg int mouseButton);

    /**
     * End a popup for a legend entry.
     */
    @BindingMethod
    public static native void EndLegendPopup();

    //-----------------------------------------------------------------------------
    // Drag and Drop Utils
    //-----------------------------------------------------------------------------

    /**
     * Turns the current plot's plotting area into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    @BindingMethod
    public static native boolean BeginDragDropTarget();

    /**
     * Turns the current plot's X-axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    @BindingMethod
    public static native boolean BeginDragDropTargetX();

    /**
     * Turns the current plot's Y-Axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    @BindingMethod
    public static native boolean BeginDragDropTargetY(@OptArg int yAxis);

    /**
     * Turns the current plot's legend into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    @BindingMethod
    public static native boolean BeginDragDropTargetLegend();

    /**
     * Ends a drag and drop target.
     */
    @BindingMethod
    public static native void EndDragDropTarget();

    // NB: By default, plot and axes drag and drop *sources* require holding the Ctrl modifier to initiate the drag.
    // You can change the modifier if desired. If ImGuiKeyModFlags_None is provided, the axes will be locked from panning.

    /**
     * Turns the current plot's plotting area into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    @BindingMethod
    public static native boolean BeginDragDropSource(@OptArg int keyMods, @OptArg int dragDropFlags);

    /**
     * Turns the current plot's X-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    @BindingMethod
    public static native boolean BeginDragDropSourceX(@OptArg int keyMods, @OptArg int dragDropFlags);

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    @BindingMethod
    public static native boolean BeginDragDropSourceY(@OptArg int yAxis, @OptArg int keyMods, @OptArg int dragDropFlags);

    /**
     * Turns an item in the current plot's legend into drag and drop source. Don't forget to call EndDragDropSource!
     */
    @BindingMethod
    public static native boolean BeginDragDropSourceItem(String labelID, @OptArg int dragDropFlags);

    /**
     * Ends a drag and drop source.
     */
    @BindingMethod
    public static native void EndDragDropSource();

    //-----------------------------------------------------------------------------
    // Plot and Item Styling
    //-----------------------------------------------------------------------------

    // Styling colors in ImPlot works similarly to styling colors in ImGui, but
    // with one important difference. Like ImGui, all style colors are stored in an
    // indexable array in ImPlotStyle. You can permanently modify these values through
    // GetStyle().Colors, or temporarily modify them with Push/Pop functions below.
    // However, by default all style colors in ImPlot default to a special color
    // IMPLOT_AUTO_COL. The behavior of this color depends upon the style color to
    // which it as applied:
    //
    //     1) For style colors associated with plot items (e.g. ImPlotCol_Line),
    //        IMPLOT_AUTO_COL tells ImPlot to color the item with the next unused
    //        color in the current colormap. Thus, every item will have a different
    //        color up to the number of colors in the colormap, at which point the
    //        colormap will roll over. For most use cases, you should not need to
    //        set these style colors to anything but IMPLOT_COL_AUTO; you are
    //        probably better off changing the current colormap. However, if you
    //        need to explicitly color a particular item you may either Push/Pop
    //        the style color around the item in question, or use the SetNextXXXStyle
    //        API below. If you permanently set one of these style colors to a specific
    //        color, or forget to call Pop, then all subsequent items will be styled
    //        with the color you set.
    //
    //     2) For style colors associated with plot styling (e.g. ImPlotCol_PlotBg),
    //        IMPLOT_AUTO_COL tells ImPlot to set that color from color data in your
    //        **ImGuiStyle**. The ImGuiCol_ that these style colors default to are
    //        detailed above, and in general have been mapped to produce plots visually
    //        consistent with your current ImGui style. Of course, you are free to
    //        manually set these colors to whatever you like, and further can Push/Pop
    //        them around individual plots for plot-specific styling (e.g. coloring axes).

    /**
     * Provides access to plot style structure for permanant modifications to colors, sizes, etc.
     */
    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native ImPlotStyle GetStyle();

    /**
     * Style plot colors for current ImGui style (default).
     */
    @BindingMethod
    public static native void StyleColorsAuto(@OptArg ImPlotStyle dst);

    /**
     * Style plot colors for ImGui "Classic".
     */
    @BindingMethod
    public static native void StyleColorsClassic(@OptArg ImPlotStyle dst);

    /**
     * Style plot colors for ImGui "Dark".
     */
    @BindingMethod
    public static native void StyleColorsDark(@OptArg ImPlotStyle dst);

    /**
     * Style plot colors for ImGui "Light".
     */
    @BindingMethod
    public static native void StyleColorsLight(@OptArg ImPlotStyle dst);

    // Use PushStyleX to temporarily modify your ImPlotStyle. The modification
    // will last until the matching call to PopStyleX. You MUST call a pop for
    // every push, otherwise you will leak memory! This behaves just like ImGui.

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    public static native void pushStyleColor(int idx, long col); /*
        ImPlot::PushStyleColor(idx, col);
    */

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    @BindingMethod
    public static native void PushStyleColor(int idx, @ArgValue(staticCast = "ImU32") int col);

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    @BindingMethod
    public static native void PushStyleColor(int idx, ImVec4 col);

    @BindingMethod
    public static native void PopStyleColor(@OptArg int count);

    /**
     * Temporarily modify a style variable of float type. Don't forget to call PopStyleVar!
     */
    @BindingMethod
    public static native void PushStyleVar(int idx, @ArgValue(staticCast = "float") float val);

    /**
     * Temporarily modify a style variable of int type. Don't forget to call PopStyleVar!
     */
    @BindingMethod
    public static native void PushStyleVar(int idx, @ArgValue(staticCast = "int") int val);

    /**
     * Temporarily modify a style variable of ImVec2 type. Don't forget to call PopStyleVar!
     */
    @BindingMethod
    public static native void PushStyleVar(int idx, ImVec2 val);
    /**
     * Undo temporary style variable modification(s). Undo multiple pushes at once by increasing count.
     */
    @BindingMethod
    public static native void PopStyleVar(@OptArg int count);

    /**
     * Set the line color and weight for the next item only.
     */
    @BindingMethod
    public static native void SetNextLineStyle(@OptArg(callValue = "IMPLOT_AUTO_COL") ImVec4 col, @OptArg float weight);

    /**
     * Set the fill color for the next item only.
     */
    @BindingMethod
    public static native void SetNextFillStyle(@OptArg(callValue = "IMPLOT_AUTO_COL") ImVec4 col, @OptArg float alphaMod);

    /**
     * Set the marker style for the next item only.
     */
    @BindingMethod
    public static native void SetNextMarkerStyle(@OptArg int marker, @OptArg float size, @OptArg ImVec4 fill, @OptArg(callValue = "IMPLOT_AUTO") float weight, @OptArg ImVec4 outline);

    /**
     * Set the error bar style for the next item only.
     */
    @BindingMethod
    public static native void SetNextErrorBarStyle(@OptArg(callValue = "IMPLOT_AUTO_COL") ImVec4 col, @OptArg float size, @OptArg float weight);

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    @BindingMethod
    public static native ImVec4 GetLastItemColor();

    /**
     * Returns the string name for an ImPlotCol.
     */
    @BindingMethod
    public static native String GetStyleColorName(int idx);

    /**
     * Returns the null terminated string name for an ImPlotMarker.
     */
    @BindingMethod
    public static native String GetMarkerName(int idx);

    //-----------------------------------------------------------------------------
    // Colormaps
    //-----------------------------------------------------------------------------

    // Item styling is based on colormaps when the relevant ImPlotCol_XXX is set to
    // IMPLOT_AUTO_COL (default). Several built-in colormaps are available. You can
    // add and then push/pop your own colormaps as well. To permanently set a colormap,
    // modify the Colormap index member of your ImPlotStyle.

    // Colormap data will be ignored and a custom color will be used if you have done one of the following:
    //     1) Modified an item style color in your ImPlotStyle to anything other than IMPLOT_AUTO_COL.
    //     2) Pushed an item style color using PushStyleColor().
    //     3) Set the next item style with a SetNextXXXStyle function.

    // Add a new colormap. The color data will be copied. The colormap can be used by pushing either the returned index or the
    // string name with PushColormap. The colormap name must be unique and the size must be greater than 1. You will receive
    // an assert otherwise! By default colormaps are considered to be qualitative (i.e. discrete). If you want to create a
    // continuous colormap, set #qual=false. This will treat the colors you provide as keys, and ImPlot will build a linearly
    // interpolated lookup table. The memory footprint of this table will be exactly ((size-1)*255+1)*4 bytes.

    @BindingMethod
    public static native int AddColormap(String name, ImVec4[] cols, @ArgValue(callValue = "env->GetArrayLength(obj_cols)") Void size, @OptArg boolean qual);

    @BindingMethod
    public static native int AddColormap(String name, @ArgValue(reinterpretCast = "ImU32*") int[] cols, @ArgValue(callValue = "env->GetArrayLength(obj_cols)") Void size, @OptArg boolean qual);

    /**
     * Returns the number of available colormaps (i.e. the built-in + user-added count).
     */
    @BindingMethod
    public static native int GetColormapCount();

    /**
     * Returns a string name for a colormap given an index.
     */
    @BindingMethod
    public static native String GetColormapName(int cmap);

    /**
     * Returns an index number for a colormap given a valid string name. Returns -1 if the name is invalid.
     */
    @BindingMethod
    public static native int GetColormapIndex(String name);

    /**
     * Temporarily switch to one of the built-in (i.e. ImPlotColormap_XXX) or user-added colormaps (i.e. a return value of AddColormap). Don't forget to call PopColormap!
     */
    @BindingMethod
    public static native void PushColormap(int cmap);

    /**
     * Push a colormap by string name. Use built-in names such as "Default", "Deep", "Jet", etc. or a string you provided to AddColormap. Don't forget to call PopColormap!
     */
    @BindingMethod
    public static native void PushColormap(String name);

    /**
     * Undo temporary colormap modification(s). Undo multiple pushes at once by increasing count.
     */
    @BindingMethod
    public static native void PopColormap(@OptArg int count);

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    @BindingMethod
    public static native ImVec4 NextColormapColor();

    /**
     * Returns the size of a colormap.
     */
    @BindingMethod
    public static native int GetColormapSize(@OptArg int cmap);

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    @BindingMethod
    public static native ImVec4 GetColormapColor(int idx, @OptArg int cmap);

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    @BindingMethod
    public static native ImVec4 SampleColormap(float t, @OptArg int cmap);

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    @BindingMethod
    public static native void ColormapScale(String label, double scaleMin, double scaleMax, @OptArg(callValue = "ImVec2(0,0)") ImVec2 size, @OptArg(callValue = "IMPLOT_AUTO") int cmap, @OptArg String fmt);

    /**
     * Shows a horizontal slider with a colormap gradient background.
     * TODO: support our argument
     */
    @BindingMethod
    public static native boolean ColormapSlider(String label, ImFloat t, Void NULL, @OptArg(callValue = "\"\"") String format, @OptArg int cmap);

    /**
     * Shows a button with a colormap gradient brackground.
     */
    @BindingMethod
    public static native boolean ColormapButton(String label, @OptArg(callValue = "ImVec2(0,0)") ImVec2 size, @OptArg int cmap);

    /**
     * When items in a plot sample their color from a colormap, the color is cached and does not change
     * unless explicitly overriden. Therefore, if you change the colormap after the item has already been plotted,
     * item colors will NOT update. If you need item colors to resample the new colormap, then use this
     * function to bust the cached colors. If #plot_title_id is NULL, then every item in EVERY existing plot
     * will be cache busted. Otherwise only the plot specified by #plot_title_id will be busted. For the
     * latter, this function must be called in the same ImGui ID scope that the plot is in. You should rarely if ever
     * need this function, but it is available for applications that require runtime colormap swaps (e.g. Heatmaps demo).
     */
    @BindingMethod
    public static native void BustColorCache(@OptArg String plotTitleId);

    //-----------------------------------------------------------------------------
    // Miscellaneous
    //-----------------------------------------------------------------------------

    // Render icons similar to those that appear in legends (nifty for data lists).

    @BindingMethod
    public static native void ItemIcon(ImVec4 col);

    @BindingMethod
    public static native void ItemIcon(@ArgValue(staticCast = "ImU32") int col);

    @BindingMethod
    public static native void ColormapIcon(int cmap);

    /**
     * Get the plot draw list for custom rendering to the current plot area. Call between Begin/EndPlot.
     */
    @BindingMethod
    public static native ImDrawList GetPlotDrawList();

    /**
     * Push clip rect for rendering to current plot area. The rect can be expanded or contracted by #expand pixels. Call between Begin/EndPlot.
     */
    @BindingMethod
    public static native void PushPlotClipRect(@OptArg float expand);

    /**
     * Pop plot clip rect. Call between Begin/EndPlot.
     */
    @BindingMethod
    public static native void PopPlotClipRect();

    /**
     * Shows ImPlot style selector dropdown menu.
     */
    @BindingMethod
    public static native boolean ShowStyleSelector(String label);

    /**
     * Shows ImPlot colormap selector dropdown menu.
     */
    @BindingMethod
    public static native boolean ShowColormapSelector(String label);

    /**
     * Shows ImPlot style editor block (not a window).
     */
    @BindingMethod
    public static native void ShowStyleEditor(@OptArg ImPlotStyle ref);

    /**
     * Add basic help/info block for end users (not a window).
     */
    @BindingMethod
    public static native void ShowUserGuide();

    /**
     * Shows ImPlot metrics/debug information window.
     */
    @BindingMethod
    public static native void ShowMetricsWindow(@OptArg ImBoolean pOpen);

    //-----------------------------------------------------------------------------
    // Demo
    //-----------------------------------------------------------------------------

    /**
     * Shows the ImPlot demo window.
     */
    @BindingMethod
    public static native void ShowDemoWindow(@OptArg ImBoolean pOpen);
}
