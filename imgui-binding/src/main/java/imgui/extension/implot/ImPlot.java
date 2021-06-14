package imgui.extension.implot;

import imgui.ImVec2;
import imgui.extension.implot.flag.ImPlotAxisFlags;
import imgui.extension.implot.flag.ImPlotFlags;
import imgui.extension.implot.flag.ImPlotYAxis;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;

public final class ImPlot {
    private static final ImPlotContext IMPLOT_CONTEXT;

    static {
        IMPLOT_CONTEXT = new ImPlotContext(0);
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

//    // The following functions MUST be called BETWEEN Begin/EndPlot!


//    // Select which Y axis will be used for subsequent plot elements. The default is ImPlotYAxis_1, or the first (left) Y axis. Enable 2nd and 3rd axes with ImPlotFlags_YAxisX.
//    IMPLOT_API void SetPlotYAxis(ImPlotYAxis y_axis);

//    // Hides or shows the next plot item (i.e. as if it were toggled from the legend). Use ImGuiCond_Always if you need to forcefully set this every frame.
//    IMPLOT_API void HideNextItem(bool hidden = true, ImGuiCond cond = ImGuiCond_Once);



//    // Convert pixels to a position in the current plot's coordinate system. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
//    IMPLOT_API ImPlotPoint PixelsToPlot(const ImVec2& pix, ImPlotYAxis y_axis = IMPLOT_AUTO);

//    IMPLOT_API ImPlotPoint PixelsToPlot(float x, float y, ImPlotYAxis y_axis = IMPLOT_AUTO);



//    // Convert a position in the current plot's coordinate system to pixels. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
//    IMPLOT_API ImVec2 PlotToPixels(const ImPlotPoint& plt, ImPlotYAxis y_axis = IMPLOT_AUTO);

//    IMPLOT_API ImVec2 PlotToPixels(double x, double y, ImPlotYAxis y_axis = IMPLOT_AUTO);



//    // Get the current Plot position (top-left) in pixels.
//    IMPLOT_API ImVec2 GetPlotPos();

//    // Get the curent Plot size in pixels.
//    IMPLOT_API ImVec2 GetPlotSize();

//    // Returns true if the plot area in the current plot is hovered.
//    IMPLOT_API bool IsPlotHovered();

//    // Returns true if the XAxis plot area in the current plot is hovered.
//    IMPLOT_API bool IsPlotXAxisHovered();

//    // Returns true if the YAxis[n] plot area in the current plot is hovered.
//    IMPLOT_API bool IsPlotYAxisHovered(ImPlotYAxis y_axis = 0);

//    // Returns the mouse position in x,y coordinates of the current plot. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
//    IMPLOT_API ImPlotPoint GetPlotMousePos(ImPlotYAxis y_axis = IMPLOT_AUTO);

//    // Returns the current plot axis range. A negative y_axis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
//    IMPLOT_API ImPlotLimits GetPlotLimits(ImPlotYAxis y_axis = IMPLOT_AUTO);



//    // Returns true if the current plot is being box selected.
//    IMPLOT_API bool IsPlotSelected();

//    // Returns the current plot box selection bounds.
//    IMPLOT_API ImPlotLimits GetPlotSelection(ImPlotYAxis y_axis = IMPLOT_AUTO);



//    // Returns true if the current plot is being queried or has an active query. Query must be enabled with ImPlotFlags_Query.
//    IMPLOT_API bool IsPlotQueried();

//    // Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
//    IMPLOT_API ImPlotLimits GetPlotQuery(ImPlotYAxis y_axis = IMPLOT_AUTO);

//    // Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
//    IMPLOT_API void SetPlotQuery(const ImPlotLimits& query, ImPlotYAxis y_axis = IMPLOT_AUTO);

    //-----------------------------------------------------------------------------
    // Demo
    //-----------------------------------------------------------------------------

    public static void showDemoWindow(final ImBoolean pOpen) {
        nShowDemoWindow(pOpen.getData());
    }

    private static native void nShowDemoWindow(boolean[] pOpen); /*
        ImPlot::ShowDemoWindow(&pOpen[0]);
    */
}
