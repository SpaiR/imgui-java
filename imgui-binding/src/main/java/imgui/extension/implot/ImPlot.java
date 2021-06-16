package imgui.extension.implot;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.extension.implot.flag.ImPlotAxisFlags;
import imgui.extension.implot.flag.ImPlotFlags;
import imgui.extension.implot.flag.ImPlotYAxis;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiMouseButton;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;

public final class ImPlot {
    private static final ImDrawList IM_DRAW_LIST;

    private static final ImPlotContext IMPLOT_CONTEXT;
    private static final ImPlotPoint IMPLOT_POINT;
    private static final ImPlotLimits IMPLOT_LIMITS;

    static {
        IM_DRAW_LIST = new ImDrawList(0);

        IMPLOT_CONTEXT = new ImPlotContext(0);
        IMPLOT_POINT = new ImPlotPoint(0);
        IMPLOT_LIMITS = new ImPlotLimits(0);
    }

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
     * - #title_id must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     */
    public static boolean beginPlot(final String title_id) {
        return beginPlot(title_id, null, null);
    }

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
     * - #title_id must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #x_label and/or #y_label are provided, axes labels will be displayed.
     */
    public static boolean beginPlot(final String title_id,
                                    final String x_label,
                                    final String y_label) {
        return beginPlot(title_id, x_label, y_label, new ImVec2(-1, 0));
    }

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
     * - #title_id must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #x_label and/or #y_label are provided, axes labels will be displayed.
     * - #size is the **frame** size of the plot widget, not the plot area. The default
     *   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
     *   (default is 400x300 px).
     */
    public static boolean beginPlot(final String title_id,
                                    final String x_label,
                                    final String y_label,
                                    final ImVec2 size) {
        return beginPlot(title_id, x_label, y_label, size, ImPlotFlags.None, ImPlotAxisFlags.None, ImPlotAxisFlags.None);
    }

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
     * - #title_id must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #x_label and/or #y_label are provided, axes labels will be displayed.
     * - #size is the **frame** size of the plot widget, not the plot area. The default
     *   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
     *   (default is 400x300 px).
     * - See ImPlotFlags and ImPlotAxisFlags for more available options.
     */
    public static boolean beginPlot(final String title_id,
                                    final String x_label,
                                    final String y_label,
                                    final ImVec2 size,
                                    int flags,
                                    int x_flags,
                                    int y_flags) {
        return beginPlot(title_id, x_label, y_label, size, flags, x_flags, y_flags, ImPlotAxisFlags.None, ImPlotAxisFlags.None, null, null);
    }

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
     * - #title_id must be unique to the current ImGui ID scope. If you need to avoid ID
     *   collisions or don't want to display a title in the plot, use double hashes
     *   (e.g. "MyPlot##HiddenIdText" or "##NoTitle").
     * - If #x_label and/or #y_label are provided, axes labels will be displayed.
     * - #size is the **frame** size of the plot widget, not the plot area. The default
     *   size of plots (i.e. when ImVec2(0,0)) can be modified in your ImPlotStyle
     *   (default is 400x300 px).
     * - Auxiliary y-axes must be enabled with ImPlotFlags_YAxis2/3 to be displayed.
     * - See ImPlotFlags and ImPlotAxisFlags for more available options.
     */
    public static boolean beginPlot(final String title_id,
                                    final String x_label,
                                    final String y_label,
                                    final ImVec2 size,
                                    int flags,
                                    int x_flags,
                                    int y_flags,
                                    int y2_flags,
                                    int y3_flags,
                                    final String y2_label,
                                    final String y3_label) {
        return nBeginPlot(title_id, x_label, y_label, size.x, size.y, flags, x_flags, y_flags, y2_flags, y3_flags, y2_label, y3_label);
    }

    private static native boolean nBeginPlot(final String title_id,
                                             final String x_label,
                                             final String y_label,
                                             float x,
                                             float y,
                                             int flags,
                                             int x_flags,
                                             int y_flags,
                                             int y2_flags,
                                             int y3_flags,
                                             final String y2_label,
                                             final String y3_label); /*
        return ImPlot::BeginPlot(title_id,
                                 x_label,
                                 y_label,
                                 ImVec2(x, y),
                                 flags,
                                 x_flags,
                                 y_flags,
                                 y2_flags,
                                 y3_flags,
                                 y2_label,
                                 y3_label);
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

    //TODO Bindings for plot items

//    // The template functions below are explicitly instantiated in implot_items.cpp.
//    // They are not intended to be used generically with custom types. You will get
//    // a linker error if you try! All functions support the following scalar types:
//    //
//    // float, double, ImS8, ImU8, ImS16, ImU16, ImS32, ImU32, ImS64, ImU64
//    //
//    //
//    // If you need to plot custom or non-homogenous data you have a few options:
//    //
//    // 1. If your data is a simple struct/class (e.g. Vector2f), you can use striding.
//    //    This is the most performant option if applicable.
//    //
//    //    struct Vector2f { float X, Y; };
//    //    ...
//    //    Vector2f data[42];
//    //    ImPlot::PlotLine("line", &data[0].x, &data[0].y, 42, 0, sizeof(Vector2f)); // or sizeof(float)*2
//    //
//    // 2. Write a custom getter C function or C++ lambda and pass it and optionally your data to
//    //    an ImPlot function post-fixed with a G (e.g. PlotScatterG). This has a slight performance
//    //    cost, but probably not enough to worry about unless your data is very large. Examples:
//    //
//    //    ImPlotPoint MyDataGetter(void* data, int idx) {
//    //        MyData* my_data = (MyData*)data;
//    //        ImPlotPoint p;
//    //        p.x = my_data->GetTime(idx);
//    //        p.y = my_data->GetValue(idx);
//    //        return p
//    //    }
//    //    ...
//    //    auto my_lambda = [](void*, int idx) {
//    //        double t = idx / 999.0;
//    //        return ImPlotPoint(t, 0.5+0.5*std::sin(2*PI*10*t));
//    //    };
//    //    ...
//    //    if (ImPlot::BeginPlot("MyPlot")) {
//    //        MyData my_data;
//    //        ImPlot::PlotScatterG("scatter", MyDataGetter, &my_data, my_data.Size());
//    //        ImPlot::PlotLineG("line", my_lambda, nullptr, 1000);
//    //        ImPlot::EndPlot();
//    //    }
//    //
//    // NB: All types are converted to double before plotting. You may lose information
//    // if you try plotting extremely large 64-bit integral types. Proceed with caution!
//
//    // Plots a standard 2D line plot.
//    template <typename T> IMPLOT_API void PlotLine(const char* label_id, const T* values, int count, double xscale=1, double x0=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotLine(const char* label_id, const T* xs, const T* ys, int count, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotLineG(const char* label_id, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, int offset=0);
//
//    // Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
//    template <typename T> IMPLOT_API  void PlotScatter(const char* label_id, const T* values, int count, double xscale=1, double x0=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API  void PlotScatter(const char* label_id, const T* xs, const T* ys, int count, int offset=0, int stride=sizeof(T));
//    IMPLOT_API  void PlotScatterG(const char* label_id, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, int offset=0);
//
//    // Plots a a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
//    template <typename T> IMPLOT_API void PlotStairs(const char* label_id, const T* values, int count, double xscale=1, double x0=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotStairs(const char* label_id, const T* xs, const T* ys, int count, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotStairsG(const char* label_id, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, int offset=0);
//
//    // Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
//    template <typename T> IMPLOT_API void PlotShaded(const char* label_id, const T* values, int count, double y_ref=0, double xscale=1, double x0=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotShaded(const char* label_id, const T* xs, const T* ys, int count, double y_ref=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotShaded(const char* label_id, const T* xs, const T* ys1, const T* ys2, int count, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotShadedG(const char* label_id, ImPlotPoint (*getter1)(void* data, int idx), void* data1, ImPlotPoint (*getter2)(void* data, int idx), void* data2, int count, int offset=0);
//
//    // Plots a vertical bar graph. #width and #shift are in X units.
//    template <typename T> IMPLOT_API void PlotBars(const char* label_id, const T* values, int count, double width=0.67, double shift=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotBars(const char* label_id, const T* xs, const T* ys, int count, double width, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotBarsG(const char* label_id, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, double width, int offset=0);
//
//    // Plots a horizontal bar graph. #height and #shift are in Y units.
//    template <typename T> IMPLOT_API void PlotBarsH(const char* label_id, const T* values, int count, double height=0.67, double shift=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotBarsH(const char* label_id, const T* xs, const T* ys, int count, double height, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotBarsHG(const char* label_id, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, double height,  int offset=0);
//
//    // Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
//    template <typename T> IMPLOT_API void PlotErrorBars(const char* label_id, const T* xs, const T* ys, const T* err, int count, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotErrorBars(const char* label_id, const T* xs, const T* ys, const T* neg, const T* pos, int count, int offset=0, int stride=sizeof(T));
//
//    // Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
//    template <typename T> IMPLOT_API void PlotErrorBarsH(const char* label_id, const T* xs, const T* ys, const T* err, int count, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotErrorBarsH(const char* label_id, const T* xs, const T* ys, const T* neg, const T* pos, int count, int offset=0, int stride=sizeof(T));
//
//    /// Plots vertical stems.
//    template <typename T> IMPLOT_API void PlotStems(const char* label_id, const T* values, int count, double y_ref=0, double xscale=1, double x0=0, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotStems(const char* label_id, const T* xs, const T* ys, int count, double y_ref=0, int offset=0, int stride=sizeof(T));
//
//    /// Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
//    template <typename T> IMPLOT_API void PlotVLines(const char* label_id, const T* xs, int count, int offset=0, int stride=sizeof(T));
//    template <typename T> IMPLOT_API void PlotHLines(const char* label_id, const T* ys, int count, int offset=0, int stride=sizeof(T));
//
//    // Plots a pie chart. If the sum of values > 1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
//    template <typename T> IMPLOT_API void PlotPieChart(const char* const label_ids[], const T* values, int count, double x, double y, double radius, bool normalize=false, const char* label_fmt="%.1f", double angle0=90);
//
//    // Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
//    template <typename T> IMPLOT_API void PlotHeatmap(const char* label_id, const T* values, int rows, int cols, double scale_min=0, double scale_max=0, const char* label_fmt="%.1f", const ImPlotPoint& bounds_min=ImPlotPoint(0,0), const ImPlotPoint& bounds_max=ImPlotPoint(1,1));
//
//    // Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
//    // If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
//    // If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
//    template <typename T> IMPLOT_API double PlotHistogram(const char* label_id, const T* values, int count, int bins=ImPlotBin_Sturges, bool cumulative=false, bool density=false, ImPlotRange range=ImPlotRange(), bool outliers=true, double bar_scale=1.0);
//
//    // Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
//    // If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
//    // However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
//    template <typename T> IMPLOT_API double PlotHistogram2D(const char* label_id, const T* xs, const T* ys, int count, int x_bins=ImPlotBin_Sturges, int y_bins=ImPlotBin_Sturges, bool density=false, ImPlotLimits range=ImPlotLimits(), bool outliers=true);
//
//    // Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
//    template <typename T> IMPLOT_API void PlotDigital(const char* label_id, const T* xs, const T* ys, int count, int offset=0, int stride=sizeof(T));
//    IMPLOT_API void PlotDigitalG(const char* label_id, ImPlotPoint (*getter)(void* data, int idx), void* data, int count, int offset=0);
//
//    // Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
//    IMPLOT_API void PlotImage(const char* label_id, ImTextureID user_texture_id, const ImPlotPoint& bounds_min, const ImPlotPoint& bounds_max, const ImVec2& uv0=ImVec2(0,0), const ImVec2& uv1=ImVec2(1,1), const ImVec4& tint_col=ImVec4(1,1,1,1));
//
//    // Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
//    IMPLOT_API void PlotText(const char* text, double x, double y, bool vertical=false, const ImVec2& pix_offset=ImVec2(0,0));
//
//    // Plots a dummy item (i.e. adds a legend entry colored by ImPlotCol_Line)
//    IMPLOT_API void PlotDummy(const char* label_id);

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
    public static void linkNextPlotLimits(ImDouble xmin, ImDouble xmax, ImDouble ymin, ImDouble ymax) {
        linkNextPlotLimits(xmin, xmax, ymin, ymax, null, null, null, null);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(ImDouble xmin, ImDouble xmax, ImDouble ymin, ImDouble ymax, ImDouble ymin2, ImDouble ymax2) {
        linkNextPlotLimits(xmin, xmax, ymin, ymax, ymin2, ymax2, null, null);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(ImDouble xmin, ImDouble xmax, ImDouble ymin, ImDouble ymax, ImDouble ymin2, ImDouble ymax2, ImDouble ymin3, ImDouble ymax3) {
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
    public static void fitNextPlotAxes(boolean x, boolean y) {
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
    public static void setNextPlotTicksX(double x_min, double x_max, int n_ticks) {
        setNextPlotTicksX(x_min, x_max, n_ticks, null, false);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(double x_min, double x_max, int n_ticks, final String[] labels, boolean keep_default) {
        String[] labelStrings = new String[4];
        for (int i = 0; i < 4; i++) {
            if (labels != null && i < labels.length)
                labelStrings[i] = labels[i];
            else
                labelStrings[i] = null;
        }

        nSetNextPlotTicksX(x_min, x_max, n_ticks, labelStrings[0], labelStrings[1], labelStrings[2], labelStrings[3], keep_default);
    }

    private static native void nSetNextPlotTicksX(double x_min, double x_max, int n_ticks, final String s1, final String s2, final String s3, final String s4, boolean keep_default); /*
        char* strings[] = {s1, s2, s3, s4};
        ImPlot::SetNextPlotTicksX(x_min, x_max, n_ticks, strings, keep_default);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot.
     */
    public static void setNextPlotTicksY(double x_min, double x_max, int n_ticks) {
        setNextPlotTicksY(x_min, x_max, n_ticks, null, false, ImPlotYAxis.YAxis_1);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(double x_min, double x_max, int n_ticks, final String[] labels, boolean keep_default) {
        setNextPlotTicksY(x_min, x_max, n_ticks, labels, keep_default, ImPlotYAxis.YAxis_1);
    }

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(double x_min, double x_max, int n_ticks, final String[] labels, boolean keep_default, int y_axis) {
        String[] labelStrings = new String[4];
        for (int i = 0; i < 4; i++) {
            if (labels != null && i < labels.length)
                labelStrings[i] = labels[i];
            else
                labelStrings[i] = null;
        }

        nSetNextPlotTicksY(x_min, x_max, n_ticks, labelStrings[0], labelStrings[1], labelStrings[2], labelStrings[3], keep_default, y_axis);
    }

    public static native void nSetNextPlotTicksY(double x_min, double x_max, int n_ticks, final String s1, final String s2, final String s3, final String s4, boolean keep_default, int y_axis); /*
        char* strings[] = {s1, s2, s3, s4};
        ImPlot::SetNextPlotTicksY(x_min, x_max, n_ticks, strings, keep_default, y_axis);
    */

    /**
     * This function MUST be called BEFORE beginPlot!
     * Set the format for numeric X axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.). Not applicable if ImPlotAxisFlags_Time enabled.
     */
    public static native void setNextPlotFormatX(final String fmt); /*
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
    public static native void setNextPlotFormatY(final String fmt, int y_axis); /*
        ImPlot::SetNextPlotFormatY(fmt);
     */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Select which Y axis will be used for subsequent plot elements. The default is ImPlotYAxis_1, or the first (left) Y axis. Enable 2nd and 3rd axes with ImPlotFlags_YAxisX.
     */
    public static native void setPlotYAxis(int y_axis); /*
        ImPlot::SetPlotYAxis(y_axis);
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
    public static native void hideNextItem(boolean hidden, int imgui_cond); /*
        ImPlot::HideNextItem(hidden, imgui_cond);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert pixels to a position in the current plot's coordinate system. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final ImVec2 pix, int y_axis) {
        IMPLOT_POINT.ptr = nPixelsToPlot(pix.x, pix.y, y_axis);
        return IMPLOT_POINT;
    }

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert pixels to a position in the current plot's coordinate system. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(float x, float y, int y_axis) {
        IMPLOT_POINT.ptr = nPixelsToPlot(x, y, y_axis);
        return IMPLOT_POINT;
    }

    private static native long nPixelsToPlot(float x, float y, int y_axis); /*
        ImPlotPoint* p = new ImPlotPoint(ImPlot::PixelsToPlot(x, y, y_axis)); //avoid complaining about taking the address of an rvalue
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert a position in the current plot's coordinate system to pixels. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(ImPlotPoint plt, int y_axis) {
        return plotToPixels(plt.getX(), plt.getY(), y_axis);
    }

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Convert a position in the current plot's coordinate system to pixels. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(double x, double y, int y_axis) {
        final ImVec2 value = new ImVec2();
        nPlotToPixels(x, y, y_axis, value);
        return value;
    }

    private static native void nPlotToPixels(double x, double y, int y_axis, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ImPlot::PlotToPixels(x, y, y_axis), vec);
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
    public static native boolean isPlotYAxisHovered(int y_axis); /*
        return ImPlot::IsPlotYAxisHovered(y_axis);
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns the mouse position in x,y coordinates of the current plot. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint getPlotMousePos(int y_axis) {
        IMPLOT_POINT.ptr = nGetPlotMousePos(y_axis);
        return IMPLOT_POINT;
    }

    private static native long nGetPlotMousePos(int y_axis); /*
        ImPlotPoint* p = new ImPlotPoint(ImPlot::GetPlotMousePos(y_axis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Returns the current plot axis range. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotLimits getPlotLimits(int y_axis) {
        IMPLOT_LIMITS.ptr = nGetPlotLimits(y_axis);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotLimits(int y_axis); /*
        ImPlotLimits* p = new ImPlotLimits(ImPlot::GetPlotLimits(y_axis));
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
     * Returns the current plot box selection bounds.
     */
    public static ImPlotLimits getPlotSelection(int y_axis) {
        IMPLOT_LIMITS.ptr = nGetPlotSelection(y_axis);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotSelection(int y_axis); /*
        ImPlotLimits* p = new ImPlotLimits(ImPlot::GetPlotSelection(y_axis));
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
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static ImPlotLimits getPlotQuery(int y_axis) {
        IMPLOT_LIMITS.ptr = nGetPlotQuery(y_axis);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotQuery(int y_axis); /*
        ImPlotLimits* p = new ImPlotLimits(ImPlot::GetPlotQuery(y_axis));
        return (intptr_t)p;
    */

    /**
     * This function MUST be called BETWEEN begin/endPlot!
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void setPlotQuery(ImPlotLimits query, int y_axis) {
        nSetPlotQuery(query.ptr, y_axis);
    }

    private static native void nSetPlotQuery(long ptr, int y_axis); /*
        ImPlotLimits* query = (ImPlotLimits*)ptr;
        ImPlot::SetPlotQuery(*query, y_axis);
    */

    //-----------------------------------------------------------------------------
    // Plot Tools
    //-----------------------------------------------------------------------------

    /**
     * Shows an annotation callout at a chosen point.
     * Uses default color
     */
    public static void annotate(double x, double y, ImVec2 pix_offset, String... fmt) {
        annotate(x, y, pix_offset, new ImVec4(0, 0, 0, 0), fmt);
    }

    /**
     * Shows an annotation callout at a chosen point.
     */
    public static void annotate(double x, double y, ImVec2 pix_offset, ImVec4 color, String... fmt) {
        nAnnotate(x, y, pix_offset.x, pix_offset.y, color.w, color.x, color.y, color.z,
                  fmt.length > 0 ? fmt[0] : null,
                  fmt.length > 1 ? fmt[1] : null,
                  fmt.length > 2 ? fmt[2] : null,
                  fmt.length > 3 ? fmt[3] : null,
                  fmt.length > 4 ? fmt[4] : null);
    }

    private static native void nAnnotate(double x, double y, float pix_x, float pix_y, float col_a, float col_b, float col_c, float col_d, String a, String b, String c, String d, String e); /*
        ImVec2 pix_offset(pix_x, pix_y);
        ImVec4 col(col_a, col_b, col_c, col_d);

        if (b == nullptr)
            ImPlot::Annotate(x, y, pix_offset, col, a);
        else if (b == nullptr)
            ImPlot::Annotate(x, y, pix_offset, col, a, b);
        else if (b == nullptr)
            ImPlot::Annotate(x, y, pix_offset, col, a, b, c);
        else if (b == nullptr)
            ImPlot::Annotate(x, y, pix_offset, col, a, b, c, d);
        else
            ImPlot::Annotate(x, y, pix_offset, col, a, b, c, d, e);
    */

    /**
     * Shows an annotation callout at a chosen point, clamped inside the plot area.
     * Uses default color
     */
    public static void annotateClamped(double x, double y, ImVec2 pix_offset, String... fmt) {
        annotateClamped(x, y, pix_offset, new ImVec4(0, 0, 0, 0), fmt);
    }

    /**
     * Shows an annotation callout at a chosen point, clamped inside the plot area.
     */
    public static void annotateClamped(double x, double y, ImVec2 pix_offset, ImVec4 color, String... fmt) {
        nAnnotateClamped(x, y, pix_offset.x, pix_offset.y, color.w, color.x, color.y, color.z,
                  fmt.length > 0 ? fmt[0] : null,
                  fmt.length > 1 ? fmt[1] : null,
                  fmt.length > 2 ? fmt[2] : null,
                  fmt.length > 3 ? fmt[3] : null,
                  fmt.length > 4 ? fmt[4] : null);
    }

    private static native void nAnnotateClamped(double x, double y, float pix_x, float pix_y, float col_a, float col_b, float col_c, float col_d, String a, String b, String c, String d, String e); /*
        ImVec2 pix_offset(pix_x, pix_y);
        ImVec4 col(col_a, col_b, col_c, col_d);

        if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pix_offset, col, a);
        else if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pix_offset, col, a, b);
        else if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pix_offset, col, a, b, c);
        else if (b == nullptr)
            ImPlot::AnnotateClamped(x, y, pix_offset, col, a, b, c, d);
        else
            ImPlot::AnnotateClamped(x, y, pix_offset, col, a, b, c, d, e);
    */

//
//    //
//    IMPLOT_API bool DragLineX(const char* id, double* x_value, bool show_label = true, const ImVec4& col = IMPLOT_AUTO_COL, float thickness = 1);

    /**
     * Shows a draggable vertical guide line at an x-value.
     */
    public static boolean dragLineX(String id, double x_value, boolean show_label, ImVec4 color, float thickness) {
        return nDragLineX(id, x_value, show_label, color.w, color.x, color.y, color.z, thickness);
    }

    private static native boolean nDragLineX(String id, double x_value, boolean show_label, float w, float x, float y, float z, float thickness); /*
        return ImPlot::DragLineX(id, &x_value, show_label, ImVec4(w, x, y, z), thickness);
    */

    /**
     * Shows a draggable horizontal guide line at a y-value.
     */
    public static boolean dragLineY(String id, double y_value, boolean show_label, ImVec4 color, float thickness) {
        return nDragLineY(id, y_value, show_label, color.w, color.x, color.y, color.z, thickness);
    }

    private static native boolean nDragLineY(String id, double y_value, boolean show_label, float w, float x, float y, float z, float thickness); /*
        return ImPlot::DragLineY(id, &y_value, show_label, ImVec4(w, x, y, z), thickness);
    */

    /**
     * Shows a draggable point at x,y.
     */
    public static boolean dragPoint(String id, double x, double y, boolean show_label, ImVec4 color, float radius) {
        return nDragPoint(id, x, y, show_label, color.w, color.x, color.y, color.z, radius);
    }

    private static native boolean nDragPoint(String id, double x_value, double y_value, boolean show_label, float w, float x, float y, float z, float radius); /*
        return ImPlot::DragPoint(id, &x_value, &y_value, show_label, ImVec4(w, x, y, z), radius);
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
    public static native boolean isLegendEntryHovered(String label_id); /*
        return ImPlot::IsLegendEntryHovered(label_id);
    */

    /**
     * Begin a popup for a legend entry.
     */
    public static boolean beginLegendPopup(String label_id) {
        return beginLegendPopup(label_id, ImGuiMouseButton.Right);
    }

    /**
     * Begin a popup for a legend entry.
     */
    public static native boolean beginLegendPopup(String label_id, int mouse_button); /*
        return ImPlot::BeginLegendPopup(label_id, mouse_button);
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
    public static native boolean beginDragDropTargetY(int y_axis); /*
        return ImPlot::BeginDragDropTargetY(y_axis);
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
    public static native boolean beginDragDropSource(int key_mods, int drag_drop_flags); /*
        return ImPlot::BeginDragDropSource(key_mods, drag_drop_flags);
    */

    /**
     * Turns the current plot's X-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSourceX(int key_mods, int drag_drop_flags); /*
        return ImPlot::BeginDragDropSourceX(key_mods, drag_drop_flags);
    */

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSourceY(int y_axis, int key_mods, int drag_drop_flags); /*
        return ImPlot::BeginDragDropSourceY(y_axis, key_mods, drag_drop_flags);
    */

    /**
     * Turns an item in the current plot's legend into drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static native boolean beginDragDropSourceItem(String label_id, int drag_drop_flags); /*
        return ImPlot::BeginDragDropSourceItem(label_id, drag_drop_flags);
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

    //TODO plot and item styling

    //-----------------------------------------------------------------------------
    // Colormaps
    //-----------------------------------------------------------------------------

    //TODO colormaps

    //-----------------------------------------------------------------------------
    // Miscellaneous
    //-----------------------------------------------------------------------------

    /**
     * Render icons similar to those that appear in legends (nifty for data lists).
     */
    public static void itemIcon(final ImVec4 col) {
        nItemIcon(col.w, col.x, col.y, col.z);
    }

    private static native void nItemIcon(final double a, final double b, final double c, final double d); /*
        ImPlot::ItemIcon(ImVec4(a, b, c, d));
    */

    /**
     * Render icons similar to those that appear in legends (nifty for data lists).
     */
    public static native void colormapIcon(int color_map); /*
        ImPlot::ColormapIcon(color_map);
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
