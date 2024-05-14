package imgui.extension.implot;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImFloat;

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
    public static ImPlotContext createContext() {
        return new ImPlotContext(nCreateContext());
    }

    private static native long nCreateContext(); /*
        return (intptr_t)ImPlot::CreateContext();
    */

    /**
     * Destroys an ImPlot context. Call this before ImGui.destroyContext(). NULL = destroy current context.
     */
    public static void destroyContext() {
        nDestroyContext();
    }

    /**
     * Destroys an ImPlot context. Call this before ImGui.destroyContext(). NULL = destroy current context.
     */
    public static void destroyContext(final ImPlotContext ctx) {
        nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext(); /*
        ImPlot::DestroyContext();
    */

    private static native void nDestroyContext(long ctx); /*
        ImPlot::DestroyContext(reinterpret_cast<ImPlotContext*>(ctx));
    */

    /**
     * Returns the current ImPlot context. NULL if no context has ben set.
     */
    public static ImPlotContext getCurrentContext() {
        return new ImPlotContext(nGetCurrentContext());
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
        ImPlot::SetCurrentContext(reinterpret_cast<ImPlotContext*>(ctx));
    */

    public static void setImGuiContext(final ImGuiContext ctx) {
        nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long ctx); /*
        ImPlot::SetImGuiContext(reinterpret_cast<ImGuiContext*>(ctx));
    */

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

    public static boolean beginPlot(final String titleID) {
        return nBeginPlot(titleID);
    }

    public static boolean beginPlot(final String titleID, final String xLabel) {
        return nBeginPlot(titleID, xLabel);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel) {
        return nBeginPlot(titleID, xLabel, yLabel);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags, final int xFlags) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags, xFlags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags, final int yFlags) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags, final int xFlags, final int yFlags) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags, xFlags, yFlags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags, final int yFlags, final int y2Flags) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags, y2Flags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags, final int xFlags, final int yFlags, final int y2Flags) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags, xFlags, yFlags, y2Flags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags, y2Flags, y3Flags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags, xFlags, yFlags, y2Flags, y3Flags);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags, final String y2Label) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags, final String y2Label) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final ImVec2 size, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags, final String y2Label, final String y3Label) {
        return nBeginPlot(titleID, xLabel, yLabel, size.x, size.y, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label, y3Label);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final float sizeX, final float sizeY, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags, final String y2Label, final String y3Label) {
        return nBeginPlot(titleID, xLabel, yLabel, sizeX, sizeY, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label, y3Label);
    }

    public static boolean beginPlot(final String titleID, final String xLabel, final String yLabel, final int flags, final int xFlags, final int yFlags, final int y2Flags, final int y3Flags, final String y2Label, final String y3Label) {
        return nBeginPlot(titleID, xLabel, yLabel, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label, y3Label);
    }

    private static native boolean nBeginPlot(String obj_titleID); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto _result = ImPlot::BeginPlot(titleID);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto _result = ImPlot::BeginPlot(titleID, xLabel);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags, int xFlags); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags, xFlags);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags, int xFlags, int yFlags); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags, xFlags, yFlags);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags, int xFlags, int yFlags, int y2Flags); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags, xFlags, yFlags, y2Flags);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags, int xFlags, int yFlags, int y2Flags, int y3Flags); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags, xFlags, yFlags, y2Flags, y3Flags);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags, int xFlags, int yFlags, int y2Flags, int y3Flags, String obj_y2Label); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        auto y2Label = obj_y2Label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_y2Label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        if (y2Label != NULL) env->ReleaseStringUTFChars(obj_y2Label, y2Label);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, float sizeX, float sizeY, int flags, int xFlags, int yFlags, int y2Flags, int y3Flags, String obj_y2Label, String obj_y3Label); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        auto y2Label = obj_y2Label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_y2Label, JNI_FALSE);
        auto y3Label = obj_y3Label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_y3Label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, size, flags, xFlags, yFlags, y2Flags, y3Flags, y2Label, y3Label);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        if (y2Label != NULL) env->ReleaseStringUTFChars(obj_y2Label, y2Label);
        if (y3Label != NULL) env->ReleaseStringUTFChars(obj_y3Label, y3Label);
        return _result;
    */

    private static native boolean nBeginPlot(String obj_titleID, String obj_xLabel, String obj_yLabel, int flags, int xFlags, int yFlags, int y2Flags, int y3Flags, String obj_y2Label, String obj_y3Label); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto xLabel = obj_xLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_xLabel, JNI_FALSE);
        auto yLabel = obj_yLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_yLabel, JNI_FALSE);
        auto y2Label = obj_y2Label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_y2Label, JNI_FALSE);
        auto y3Label = obj_y3Label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_y3Label, JNI_FALSE);
        auto _result = ImPlot::BeginPlot(titleID, xLabel, yLabel, ImVec2(-1,0), flags, xFlags, yFlags, y2Flags, y3Flags, y2Label, y3Label);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (xLabel != NULL) env->ReleaseStringUTFChars(obj_xLabel, xLabel);
        if (yLabel != NULL) env->ReleaseStringUTFChars(obj_yLabel, yLabel);
        if (y2Label != NULL) env->ReleaseStringUTFChars(obj_y2Label, y2Label);
        if (y3Label != NULL) env->ReleaseStringUTFChars(obj_y3Label, y3Label);
        return _result;
    */

    /**
     * Only call EndPlot() if beginPlot() returns true! Typically called at the end
     * of an if statement conditioned on BeginPlot(). See example in beginPlot().
     */
    public static void endPlot() {
        nEndPlot();
    }

    private static native void nEndPlot(); /*
        ImPlot::EndPlot();
    */

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

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final ImVec2 size) {
        return nBeginSubplots(titleID, rows, cols, size.x, size.y);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final float sizeX, final float sizeY) {
        return nBeginSubplots(titleID, rows, cols, sizeX, sizeY);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final ImVec2 size, final int flags) {
        return nBeginSubplots(titleID, rows, cols, size.x, size.y, flags);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final float sizeX, final float sizeY, final int flags) {
        return nBeginSubplots(titleID, rows, cols, sizeX, sizeY, flags);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final ImVec2 size, final int flags, final float[] rowRatios) {
        return nBeginSubplots(titleID, rows, cols, size.x, size.y, flags, rowRatios);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final float sizeX, final float sizeY, final int flags, final float[] rowRatios) {
        return nBeginSubplots(titleID, rows, cols, sizeX, sizeY, flags, rowRatios);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final ImVec2 size, final int flags, final float[] rowRatios, final float[] colRatios) {
        return nBeginSubplots(titleID, rows, cols, size.x, size.y, flags, rowRatios, colRatios);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final float sizeX, final float sizeY, final int flags, final float[] rowRatios, final float[] colRatios) {
        return nBeginSubplots(titleID, rows, cols, sizeX, sizeY, flags, rowRatios, colRatios);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final ImVec2 size, final float[] rowRatios, final float[] colRatios) {
        return nBeginSubplots(titleID, rows, cols, size.x, size.y, rowRatios, colRatios);
    }

    public static boolean beginSubplots(final String titleID, final int rows, final int cols, final float sizeX, final float sizeY, final float[] rowRatios, final float[] colRatios) {
        return nBeginSubplots(titleID, rows, cols, sizeX, sizeY, rowRatios, colRatios);
    }

    private static native boolean nBeginSubplots(String obj_titleID, int rows, int cols, float sizeX, float sizeY); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginSubplots(titleID, rows, cols, size);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        return _result;
    */

    private static native boolean nBeginSubplots(String obj_titleID, int rows, int cols, float sizeX, float sizeY, int flags); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginSubplots(titleID, rows, cols, size, flags);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        return _result;
    */

    private static native boolean nBeginSubplots(String obj_titleID, int rows, int cols, float sizeX, float sizeY, int flags, float[] obj_rowRatios); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto rowRatios = obj_rowRatios == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_rowRatios, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginSubplots(titleID, rows, cols, size, flags, &rowRatios[0]);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (rowRatios != NULL) env->ReleasePrimitiveArrayCritical(obj_rowRatios, rowRatios, JNI_FALSE);
        return _result;
    */

    private static native boolean nBeginSubplots(String obj_titleID, int rows, int cols, float sizeX, float sizeY, int flags, float[] obj_rowRatios, float[] obj_colRatios); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto rowRatios = obj_rowRatios == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_rowRatios, JNI_FALSE);
        auto colRatios = obj_colRatios == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_colRatios, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginSubplots(titleID, rows, cols, size, flags, &rowRatios[0], &colRatios[0]);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (rowRatios != NULL) env->ReleasePrimitiveArrayCritical(obj_rowRatios, rowRatios, JNI_FALSE);
        if (colRatios != NULL) env->ReleasePrimitiveArrayCritical(obj_colRatios, colRatios, JNI_FALSE);
        return _result;
    */

    private static native boolean nBeginSubplots(String obj_titleID, int rows, int cols, float sizeX, float sizeY, float[] obj_rowRatios, float[] obj_colRatios); /*MANUAL
        auto titleID = obj_titleID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_titleID, JNI_FALSE);
        auto rowRatios = obj_rowRatios == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_rowRatios, JNI_FALSE);
        auto colRatios = obj_colRatios == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_colRatios, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::BeginSubplots(titleID, rows, cols, size, ImPlotSubplotFlags_None, &rowRatios[0], &colRatios[0]);
        if (titleID != NULL) env->ReleaseStringUTFChars(obj_titleID, titleID);
        if (rowRatios != NULL) env->ReleasePrimitiveArrayCritical(obj_rowRatios, rowRatios, JNI_FALSE);
        if (colRatios != NULL) env->ReleasePrimitiveArrayCritical(obj_colRatios, colRatios, JNI_FALSE);
        return _result;
    */

    /**
     * Only call EndSubplots() if BeginSubplots() returns true! Typically called at the end
     * of an if statement conditioned on BeginSublots(). See example above.
     */
    public static void endSubplots() {
        nEndSubplots();
    }

    private static native void nEndSubplots(); /*
        ImPlot::EndSubplots();
    */

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
    public static void plotLine(final String labelId, final short[] values) {
        nPlotLine(labelId, values);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final short[] values, final double xscale) {
        nPlotLine(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final short[] values, final double xscale, final double x0) {
        nPlotLine(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final short[] values, final double xscale, final double x0, final int offset) {
        nPlotLine(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotLine(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, short[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, short[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, short[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final int[] values) {
        nPlotLine(labelId, values);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final int[] values, final double xscale) {
        nPlotLine(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final int[] values, final double xscale, final double x0) {
        nPlotLine(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final int[] values, final double xscale, final double x0, final int offset) {
        nPlotLine(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotLine(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, int[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, int[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, int[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final long[] values) {
        nPlotLine(labelId, values);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final long[] values, final double xscale) {
        nPlotLine(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final long[] values, final double xscale, final double x0) {
        nPlotLine(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final long[] values, final double xscale, final double x0, final int offset) {
        nPlotLine(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotLine(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, long[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, long[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, long[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final float[] values) {
        nPlotLine(labelId, values);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final float[] values, final double xscale) {
        nPlotLine(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final float[] values, final double xscale, final double x0) {
        nPlotLine(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final float[] values, final double xscale, final double x0, final int offset) {
        nPlotLine(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotLine(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, float[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, float[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, float[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final double[] values) {
        nPlotLine(labelId, values);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final double[] values, final double xscale) {
        nPlotLine(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final double[] values, final double xscale, final double x0) {
        nPlotLine(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final double[] values, final double xscale, final double x0, final int offset) {
        nPlotLine(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotLine(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, double[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, double[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, double[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotLine(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final short[] xs, final short[] ys) {
        nPlotLine(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final short[] xs, final short[] ys, final int offset) {
        nPlotLine(labelId, xs, ys, offset);
    }

    private static native void nPlotLine(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, short[] xs, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final int[] xs, final int[] ys) {
        nPlotLine(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final int[] xs, final int[] ys, final int offset) {
        nPlotLine(labelId, xs, ys, offset);
    }

    private static native void nPlotLine(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, int[] xs, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final long[] xs, final long[] ys) {
        nPlotLine(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final long[] xs, final long[] ys, final int offset) {
        nPlotLine(labelId, xs, ys, offset);
    }

    private static native void nPlotLine(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, long[] xs, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final float[] xs, final float[] ys) {
        nPlotLine(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final float[] xs, final float[] ys, final int offset) {
        nPlotLine(labelId, xs, ys, offset);
    }

    private static native void nPlotLine(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, float[] xs, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final double[] xs, final double[] ys) {
        nPlotLine(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D line plot.
     */
    public static void plotLine(final String labelId, final double[] xs, final double[] ys, final int offset) {
        nPlotLine(labelId, xs, ys, offset);
    }

    private static native void nPlotLine(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotLine(String labelId, double[] xs, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotLine(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    // values

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final short[] values) {
        nPlotScatter(labelId, values);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final short[] values, final double xscale) {
        nPlotScatter(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final short[] values, final double xscale, final double x0) {
        nPlotScatter(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final short[] values, final double xscale, final double x0, final int offset) {
        nPlotScatter(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotScatter(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, short[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, short[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, short[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final int[] values) {
        nPlotScatter(labelId, values);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final int[] values, final double xscale) {
        nPlotScatter(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final int[] values, final double xscale, final double x0) {
        nPlotScatter(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final int[] values, final double xscale, final double x0, final int offset) {
        nPlotScatter(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotScatter(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, int[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, int[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, int[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final long[] values) {
        nPlotScatter(labelId, values);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final long[] values, final double xscale) {
        nPlotScatter(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final long[] values, final double xscale, final double x0) {
        nPlotScatter(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final long[] values, final double xscale, final double x0, final int offset) {
        nPlotScatter(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotScatter(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, long[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, long[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, long[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final float[] values) {
        nPlotScatter(labelId, values);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final float[] values, final double xscale) {
        nPlotScatter(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final float[] values, final double xscale, final double x0) {
        nPlotScatter(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final float[] values, final double xscale, final double x0, final int offset) {
        nPlotScatter(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotScatter(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, float[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, float[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, float[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final double[] values) {
        nPlotScatter(labelId, values);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final double[] values, final double xscale) {
        nPlotScatter(labelId, values, xscale);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final double[] values, final double xscale, final double x0) {
        nPlotScatter(labelId, values, xscale, x0);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final double[] values, final double xscale, final double x0, final int offset) {
        nPlotScatter(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotScatter(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, double[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, double[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, double[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final short[] xs, final short[] ys) {
        nPlotScatter(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final short[] xs, final short[] ys, final int offset) {
        nPlotScatter(labelId, xs, ys, offset);
    }

    private static native void nPlotScatter(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, short[] xs, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final int[] xs, final int[] ys) {
        nPlotScatter(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final int[] xs, final int[] ys, final int offset) {
        nPlotScatter(labelId, xs, ys, offset);
    }

    private static native void nPlotScatter(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, int[] xs, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final long[] xs, final long[] ys) {
        nPlotScatter(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final long[] xs, final long[] ys, final int offset) {
        nPlotScatter(labelId, xs, ys, offset);
    }

    private static native void nPlotScatter(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, long[] xs, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final float[] xs, final float[] ys) {
        nPlotScatter(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final float[] xs, final float[] ys, final int offset) {
        nPlotScatter(labelId, xs, ys, offset);
    }

    private static native void nPlotScatter(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, float[] xs, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final double[] xs, final double[] ys) {
        nPlotScatter(labelId, xs, ys);
    }

    /**
     * Plots a standard 2D scatter plot. Default marker is ImPlotMarker_Circle.
     */
    public static void plotScatter(final String labelId, final double[] xs, final double[] ys, final int offset) {
        nPlotScatter(labelId, xs, ys, offset);
    }

    private static native void nPlotScatter(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotScatter(String labelId, double[] xs, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotScatter(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    // values

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final short[] values) {
        nPlotStairs(labelId, values);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final short[] values, final double xscale) {
        nPlotStairs(labelId, values, xscale);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final short[] values, final double xscale, final double x0) {
        nPlotStairs(labelId, values, xscale, x0);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final short[] values, final double xscale, final double x0, final int offset) {
        nPlotStairs(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotStairs(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, short[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, short[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, short[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final int[] values) {
        nPlotStairs(labelId, values);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final int[] values, final double xscale) {
        nPlotStairs(labelId, values, xscale);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final int[] values, final double xscale, final double x0) {
        nPlotStairs(labelId, values, xscale, x0);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final int[] values, final double xscale, final double x0, final int offset) {
        nPlotStairs(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotStairs(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, int[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, int[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, int[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final long[] values) {
        nPlotStairs(labelId, values);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final long[] values, final double xscale) {
        nPlotStairs(labelId, values, xscale);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final long[] values, final double xscale, final double x0) {
        nPlotStairs(labelId, values, xscale, x0);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final long[] values, final double xscale, final double x0, final int offset) {
        nPlotStairs(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotStairs(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, long[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, long[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, long[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final float[] values) {
        nPlotStairs(labelId, values);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final float[] values, final double xscale) {
        nPlotStairs(labelId, values, xscale);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final float[] values, final double xscale, final double x0) {
        nPlotStairs(labelId, values, xscale, x0);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final float[] values, final double xscale, final double x0, final int offset) {
        nPlotStairs(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotStairs(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, float[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, float[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, float[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final double[] values) {
        nPlotStairs(labelId, values);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final double[] values, final double xscale) {
        nPlotStairs(labelId, values, xscale);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final double[] values, final double xscale, final double x0) {
        nPlotStairs(labelId, values, xscale, x0);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final double[] values, final double xscale, final double x0, final int offset) {
        nPlotStairs(labelId, values, xscale, x0, offset);
    }

    private static native void nPlotStairs(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, double[] values, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, double[] values, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, double[] values, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &values[0], LEN(values), xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final short[] xs, final short[] ys) {
        nPlotStairs(labelId, xs, ys);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final short[] xs, final short[] ys, final int offset) {
        nPlotStairs(labelId, xs, ys, offset);
    }

    private static native void nPlotStairs(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, short[] xs, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final int[] xs, final int[] ys) {
        nPlotStairs(labelId, xs, ys);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final int[] xs, final int[] ys, final int offset) {
        nPlotStairs(labelId, xs, ys, offset);
    }

    private static native void nPlotStairs(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, int[] xs, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final long[] xs, final long[] ys) {
        nPlotStairs(labelId, xs, ys);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final long[] xs, final long[] ys, final int offset) {
        nPlotStairs(labelId, xs, ys, offset);
    }

    private static native void nPlotStairs(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, long[] xs, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final float[] xs, final float[] ys) {
        nPlotStairs(labelId, xs, ys);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final float[] xs, final float[] ys, final int offset) {
        nPlotStairs(labelId, xs, ys, offset);
    }

    private static native void nPlotStairs(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, float[] xs, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final double[] xs, final double[] ys) {
        nPlotStairs(labelId, xs, ys);
    }

    /**
     * Plots a stairstep graph. The y value is continued constantly from every x position, i.e. the interval [x[i], x[i+1]) has the value y[i].
     */
    public static void plotStairs(final String labelId, final double[] xs, final double[] ys, final int offset) {
        nPlotStairs(labelId, xs, ys, offset);
    }

    private static native void nPlotStairs(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStairs(String labelId, double[] xs, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStairs(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    // values

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] values) {
        nPlotShaded(labelId, values);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] values, final double yRef) {
        nPlotShaded(labelId, values, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] values, final double yRef, final double xscale) {
        nPlotShaded(labelId, values, yRef, xscale);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] values, final double yRef, final double xscale, final double x0) {
        nPlotShaded(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotShaded(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotShaded(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] values) {
        nPlotShaded(labelId, values);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] values, final double yRef) {
        nPlotShaded(labelId, values, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] values, final double yRef, final double xscale) {
        nPlotShaded(labelId, values, yRef, xscale);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] values, final double yRef, final double xscale, final double x0) {
        nPlotShaded(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotShaded(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotShaded(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] values) {
        nPlotShaded(labelId, values);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] values, final double yRef) {
        nPlotShaded(labelId, values, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] values, final double yRef, final double xscale) {
        nPlotShaded(labelId, values, yRef, xscale);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] values, final double yRef, final double xscale, final double x0) {
        nPlotShaded(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotShaded(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotShaded(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] values) {
        nPlotShaded(labelId, values);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] values, final double yRef) {
        nPlotShaded(labelId, values, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] values, final double yRef, final double xscale) {
        nPlotShaded(labelId, values, yRef, xscale);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] values, final double yRef, final double xscale, final double x0) {
        nPlotShaded(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotShaded(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotShaded(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] values) {
        nPlotShaded(labelId, values);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] values, final double yRef) {
        nPlotShaded(labelId, values, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] values, final double yRef, final double xscale) {
        nPlotShaded(labelId, values, yRef, xscale);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] values, final double yRef, final double xscale, final double x0) {
        nPlotShaded(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotShaded(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotShaded(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] xs, final short[] ys) {
        nPlotShaded(labelId, xs, ys);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] xs, final short[] ys, final double yRef) {
        nPlotShaded(labelId, xs, ys, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] xs, final short[] ys, final double yRef, final int offset) {
        nPlotShaded(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotShaded(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] xs, short[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] xs, short[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] xs, final int[] ys) {
        nPlotShaded(labelId, xs, ys);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] xs, final int[] ys, final double yRef) {
        nPlotShaded(labelId, xs, ys, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] xs, final int[] ys, final double yRef, final int offset) {
        nPlotShaded(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotShaded(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] xs, int[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] xs, int[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] xs, final long[] ys) {
        nPlotShaded(labelId, xs, ys);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] xs, final long[] ys, final double yRef) {
        nPlotShaded(labelId, xs, ys, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] xs, final long[] ys, final double yRef, final int offset) {
        nPlotShaded(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotShaded(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] xs, long[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] xs, long[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] xs, final float[] ys) {
        nPlotShaded(labelId, xs, ys);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] xs, final float[] ys, final double yRef) {
        nPlotShaded(labelId, xs, ys, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] xs, final float[] ys, final double yRef, final int offset) {
        nPlotShaded(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotShaded(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] xs, float[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] xs, float[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] xs, final double[] ys) {
        nPlotShaded(labelId, xs, ys);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] xs, final double[] ys, final double yRef) {
        nPlotShaded(labelId, xs, ys, yRef);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] xs, final double[] ys, final double yRef, final int offset) {
        nPlotShaded(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotShaded(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] xs, double[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] xs, double[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    // xs,ys1,ys2

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] xs, final short[] ys1, final short[] ys2) {
        nPlotShaded(labelId, xs, ys1, ys2);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final short[] xs, final short[] ys1, final short[] ys2, final int offset) {
        nPlotShaded(labelId, xs, ys1, ys2, offset);
    }

    private static native void nPlotShaded(String labelId, short[] xs, short[] ys1, short[] ys2); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, short[] xs, short[] ys1, short[] ys2, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] xs, final int[] ys1, final int[] ys2) {
        nPlotShaded(labelId, xs, ys1, ys2);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final int[] xs, final int[] ys1, final int[] ys2, final int offset) {
        nPlotShaded(labelId, xs, ys1, ys2, offset);
    }

    private static native void nPlotShaded(String labelId, int[] xs, int[] ys1, int[] ys2); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, int[] xs, int[] ys1, int[] ys2, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] xs, final long[] ys1, final long[] ys2) {
        nPlotShaded(labelId, xs, ys1, ys2);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final long[] xs, final long[] ys1, final long[] ys2, final int offset) {
        nPlotShaded(labelId, xs, ys1, ys2, offset);
    }

    private static native void nPlotShaded(String labelId, long[] xs, long[] ys1, long[] ys2); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, long[] xs, long[] ys1, long[] ys2, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] xs, final float[] ys1, final float[] ys2) {
        nPlotShaded(labelId, xs, ys1, ys2);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final float[] xs, final float[] ys1, final float[] ys2, final int offset) {
        nPlotShaded(labelId, xs, ys1, ys2, offset);
    }

    private static native void nPlotShaded(String labelId, float[] xs, float[] ys1, float[] ys2); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, float[] xs, float[] ys1, float[] ys2, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] xs, final double[] ys1, final double[] ys2) {
        nPlotShaded(labelId, xs, ys1, ys2);
    }

    /**
     * Plots a shaded (filled) region between two lines, or a line and a horizontal reference. Set y_ref to +/-INFINITY for infinite fill extents.
     */
    public static void plotShaded(final String labelId, final double[] xs, final double[] ys1, final double[] ys2, final int offset) {
        nPlotShaded(labelId, xs, ys1, ys2, offset);
    }

    private static native void nPlotShaded(String labelId, double[] xs, double[] ys1, double[] ys2); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    private static native void nPlotShaded(String labelId, double[] xs, double[] ys1, double[] ys2, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys1 = obj_ys1 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys1, JNI_FALSE);
        auto ys2 = obj_ys2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys2, JNI_FALSE);
        ImPlot::PlotShaded(labelId, &xs[0], &ys1[0], &ys2[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys1 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys1, ys1, JNI_FALSE);
        if (ys2 != NULL) env->ReleasePrimitiveArrayCritical(obj_ys2, ys2, JNI_FALSE);
    */

    // values

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] values) {
        nPlotBars(labelId, values);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] values, final double width) {
        nPlotBars(labelId, values, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] values, final double width, final double shift) {
        nPlotBars(labelId, values, width, shift);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] values, final double width, final double shift, final int offset) {
        nPlotBars(labelId, values, width, shift, offset);
    }

    private static native void nPlotBars(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, short[] values, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, short[] values, double width, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, short[] values, double width, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] values) {
        nPlotBars(labelId, values);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] values, final double width) {
        nPlotBars(labelId, values, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] values, final double width, final double shift) {
        nPlotBars(labelId, values, width, shift);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] values, final double width, final double shift, final int offset) {
        nPlotBars(labelId, values, width, shift, offset);
    }

    private static native void nPlotBars(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, int[] values, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, int[] values, double width, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, int[] values, double width, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] values) {
        nPlotBars(labelId, values);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] values, final double width) {
        nPlotBars(labelId, values, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] values, final double width, final double shift) {
        nPlotBars(labelId, values, width, shift);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] values, final double width, final double shift, final int offset) {
        nPlotBars(labelId, values, width, shift, offset);
    }

    private static native void nPlotBars(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, long[] values, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, long[] values, double width, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, long[] values, double width, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] values) {
        nPlotBars(labelId, values);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] values, final double width) {
        nPlotBars(labelId, values, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] values, final double width, final double shift) {
        nPlotBars(labelId, values, width, shift);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] values, final double width, final double shift, final int offset) {
        nPlotBars(labelId, values, width, shift, offset);
    }

    private static native void nPlotBars(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, float[] values, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, float[] values, double width, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, float[] values, double width, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] values) {
        nPlotBars(labelId, values);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] values, final double width) {
        nPlotBars(labelId, values, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] values, final double width, final double shift) {
        nPlotBars(labelId, values, width, shift);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] values, final double width, final double shift, final int offset) {
        nPlotBars(labelId, values, width, shift, offset);
    }

    private static native void nPlotBars(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, double[] values, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, double[] values, double width, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, double[] values, double width, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBars(labelId, &values[0], LEN(values), width, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] xs, final short[] ys, final double width) {
        nPlotBars(labelId, xs, ys, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] xs, final short[] ys, final double width, final int offset) {
        nPlotBars(labelId, xs, ys, width, offset);
    }

    private static native void nPlotBars(String labelId, short[] xs, short[] ys, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, short[] xs, short[] ys, double width, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] xs, final int[] ys, final double width) {
        nPlotBars(labelId, xs, ys, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] xs, final int[] ys, final double width, final int offset) {
        nPlotBars(labelId, xs, ys, width, offset);
    }

    private static native void nPlotBars(String labelId, int[] xs, int[] ys, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, int[] xs, int[] ys, double width, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] xs, final long[] ys, final double width) {
        nPlotBars(labelId, xs, ys, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] xs, final long[] ys, final double width, final int offset) {
        nPlotBars(labelId, xs, ys, width, offset);
    }

    private static native void nPlotBars(String labelId, long[] xs, long[] ys, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, long[] xs, long[] ys, double width, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] xs, final float[] ys, final double width) {
        nPlotBars(labelId, xs, ys, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] xs, final float[] ys, final double width, final int offset) {
        nPlotBars(labelId, xs, ys, width, offset);
    }

    private static native void nPlotBars(String labelId, float[] xs, float[] ys, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, float[] xs, float[] ys, double width, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] xs, final double[] ys, final double width) {
        nPlotBars(labelId, xs, ys, width);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] xs, final double[] ys, final double width, final int offset) {
        nPlotBars(labelId, xs, ys, width, offset);
    }

    private static native void nPlotBars(String labelId, double[] xs, double[] ys, double width); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, double[] xs, double[] ys, double width, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), width, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] xs, final short[] ys) {
        nPlotBars(labelId, xs, ys);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final short[] xs, final short[] ys, final int offset) {
        nPlotBars(labelId, xs, ys, offset);
    }

    private static native void nPlotBars(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, short[] xs, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] xs, final int[] ys) {
        nPlotBars(labelId, xs, ys);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final int[] xs, final int[] ys, final int offset) {
        nPlotBars(labelId, xs, ys, offset);
    }

    private static native void nPlotBars(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, int[] xs, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] xs, final long[] ys) {
        nPlotBars(labelId, xs, ys);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final long[] xs, final long[] ys, final int offset) {
        nPlotBars(labelId, xs, ys, offset);
    }

    private static native void nPlotBars(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, long[] xs, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] xs, final float[] ys) {
        nPlotBars(labelId, xs, ys);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final float[] xs, final float[] ys, final int offset) {
        nPlotBars(labelId, xs, ys, offset);
    }

    private static native void nPlotBars(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, float[] xs, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] xs, final double[] ys) {
        nPlotBars(labelId, xs, ys);
    }

    /**
     * Plots a vertical bar graph. #width and #shift are in X units.
     */
    public static void plotBars(final String labelId, final double[] xs, final double[] ys, final int offset) {
        nPlotBars(labelId, xs, ys, offset);
    }

    private static native void nPlotBars(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBars(String labelId, double[] xs, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBars(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    // values

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] values) {
        nPlotBarsH(labelId, values);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] values, final double height) {
        nPlotBarsH(labelId, values, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] values, final double height, final double shift) {
        nPlotBarsH(labelId, values, height, shift);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] values, final double height, final double shift, final int offset) {
        nPlotBarsH(labelId, values, height, shift, offset);
    }

    private static native void nPlotBarsH(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, short[] values, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, short[] values, double height, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, short[] values, double height, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] values) {
        nPlotBarsH(labelId, values);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] values, final double height) {
        nPlotBarsH(labelId, values, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] values, final double height, final double shift) {
        nPlotBarsH(labelId, values, height, shift);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] values, final double height, final double shift, final int offset) {
        nPlotBarsH(labelId, values, height, shift, offset);
    }

    private static native void nPlotBarsH(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, int[] values, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, int[] values, double height, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, int[] values, double height, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] values) {
        nPlotBarsH(labelId, values);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] values, final double height) {
        nPlotBarsH(labelId, values, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] values, final double height, final double shift) {
        nPlotBarsH(labelId, values, height, shift);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] values, final double height, final double shift, final int offset) {
        nPlotBarsH(labelId, values, height, shift, offset);
    }

    private static native void nPlotBarsH(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, long[] values, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, long[] values, double height, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, long[] values, double height, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] values) {
        nPlotBarsH(labelId, values);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] values, final double height) {
        nPlotBarsH(labelId, values, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] values, final double height, final double shift) {
        nPlotBarsH(labelId, values, height, shift);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] values, final double height, final double shift, final int offset) {
        nPlotBarsH(labelId, values, height, shift, offset);
    }

    private static native void nPlotBarsH(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, float[] values, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, float[] values, double height, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, float[] values, double height, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] values) {
        nPlotBarsH(labelId, values);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] values, final double height) {
        nPlotBarsH(labelId, values, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] values, final double height, final double shift) {
        nPlotBarsH(labelId, values, height, shift);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] values, final double height, final double shift, final int offset) {
        nPlotBarsH(labelId, values, height, shift, offset);
    }

    private static native void nPlotBarsH(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, double[] values, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, double[] values, double height, double shift); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, double[] values, double height, double shift, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &values[0], LEN(values), height, shift, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] xs, final short[] ys, final double height) {
        nPlotBarsH(labelId, xs, ys, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] xs, final short[] ys, final double height, final int offset) {
        nPlotBarsH(labelId, xs, ys, height, offset);
    }

    private static native void nPlotBarsH(String labelId, short[] xs, short[] ys, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, short[] xs, short[] ys, double height, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] xs, final int[] ys, final double height) {
        nPlotBarsH(labelId, xs, ys, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] xs, final int[] ys, final double height, final int offset) {
        nPlotBarsH(labelId, xs, ys, height, offset);
    }

    private static native void nPlotBarsH(String labelId, int[] xs, int[] ys, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, int[] xs, int[] ys, double height, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] xs, final long[] ys, final double height) {
        nPlotBarsH(labelId, xs, ys, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] xs, final long[] ys, final double height, final int offset) {
        nPlotBarsH(labelId, xs, ys, height, offset);
    }

    private static native void nPlotBarsH(String labelId, long[] xs, long[] ys, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, long[] xs, long[] ys, double height, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] xs, final float[] ys, final double height) {
        nPlotBarsH(labelId, xs, ys, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] xs, final float[] ys, final double height, final int offset) {
        nPlotBarsH(labelId, xs, ys, height, offset);
    }

    private static native void nPlotBarsH(String labelId, float[] xs, float[] ys, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, float[] xs, float[] ys, double height, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] xs, final double[] ys, final double height) {
        nPlotBarsH(labelId, xs, ys, height);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] xs, final double[] ys, final double height, final int offset) {
        nPlotBarsH(labelId, xs, ys, height, offset);
    }

    private static native void nPlotBarsH(String labelId, double[] xs, double[] ys, double height); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, double[] xs, double[] ys, double height, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), height, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] xs, final short[] ys) {
        nPlotBarsH(labelId, xs, ys);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final short[] xs, final short[] ys, final int offset) {
        nPlotBarsH(labelId, xs, ys, offset);
    }

    private static native void nPlotBarsH(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, short[] xs, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] xs, final int[] ys) {
        nPlotBarsH(labelId, xs, ys);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final int[] xs, final int[] ys, final int offset) {
        nPlotBarsH(labelId, xs, ys, offset);
    }

    private static native void nPlotBarsH(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, int[] xs, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] xs, final long[] ys) {
        nPlotBarsH(labelId, xs, ys);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final long[] xs, final long[] ys, final int offset) {
        nPlotBarsH(labelId, xs, ys, offset);
    }

    private static native void nPlotBarsH(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, long[] xs, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] xs, final float[] ys) {
        nPlotBarsH(labelId, xs, ys);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final float[] xs, final float[] ys, final int offset) {
        nPlotBarsH(labelId, xs, ys, offset);
    }

    private static native void nPlotBarsH(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, float[] xs, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] xs, final double[] ys) {
        nPlotBarsH(labelId, xs, ys);
    }

    /**
     * Plots a horizontal bar graph. #height and #shift are in Y units.
     */
    public static void plotBarsH(final String labelId, final double[] xs, final double[] ys, final int offset) {
        nPlotBarsH(labelId, xs, ys, offset);
    }

    private static native void nPlotBarsH(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotBarsH(String labelId, double[] xs, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotBarsH(labelId, &xs[0], &ys[0], LEN(xs), 0.67, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final short[] xs, final short[] ys, final short[] err) {
        nPlotErrorBars(labelId, xs, ys, err);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final short[] xs, final short[] ys, final short[] err, final int offset) {
        nPlotErrorBars(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBars(String labelId, short[] xs, short[] ys, short[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, short[] xs, short[] ys, short[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final int[] xs, final int[] ys, final int[] err) {
        nPlotErrorBars(labelId, xs, ys, err);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final int[] xs, final int[] ys, final int[] err, final int offset) {
        nPlotErrorBars(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBars(String labelId, int[] xs, int[] ys, int[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, int[] xs, int[] ys, int[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final long[] xs, final long[] ys, final long[] err) {
        nPlotErrorBars(labelId, xs, ys, err);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final long[] xs, final long[] ys, final long[] err, final int offset) {
        nPlotErrorBars(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBars(String labelId, long[] xs, long[] ys, long[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, long[] xs, long[] ys, long[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final float[] xs, final float[] ys, final float[] err) {
        nPlotErrorBars(labelId, xs, ys, err);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final float[] xs, final float[] ys, final float[] err, final int offset) {
        nPlotErrorBars(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBars(String labelId, float[] xs, float[] ys, float[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, float[] xs, float[] ys, float[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final double[] xs, final double[] ys, final double[] err) {
        nPlotErrorBars(labelId, xs, ys, err);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final double[] xs, final double[] ys, final double[] err, final int offset) {
        nPlotErrorBars(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBars(String labelId, double[] xs, double[] ys, double[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, double[] xs, double[] ys, double[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final short[] xs, final short[] ys, final short[] neg, final short[] pos) {
        nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final short[] xs, final short[] ys, final short[] neg, final short[] pos, final int offset) {
        nPlotErrorBars(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBars(String labelId, short[] xs, short[] ys, short[] neg, short[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final int[] xs, final int[] ys, final int[] neg, final int[] pos) {
        nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final int[] xs, final int[] ys, final int[] neg, final int[] pos, final int offset) {
        nPlotErrorBars(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBars(String labelId, int[] xs, int[] ys, int[] neg, int[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final long[] xs, final long[] ys, final long[] neg, final long[] pos) {
        nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final long[] xs, final long[] ys, final long[] neg, final long[] pos, final int offset) {
        nPlotErrorBars(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBars(String labelId, long[] xs, long[] ys, long[] neg, long[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final float[] xs, final float[] ys, final float[] neg, final float[] pos) {
        nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final float[] xs, final float[] ys, final float[] neg, final float[] pos, final int offset) {
        nPlotErrorBars(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBars(String labelId, float[] xs, float[] ys, float[] neg, float[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final double[] xs, final double[] ys, final double[] neg, final double[] pos) {
        nPlotErrorBars(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots vertical error bar. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBars(final String labelId, final double[] xs, final double[] ys, final double[] neg, final double[] pos, final int offset) {
        nPlotErrorBars(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBars(String labelId, double[] xs, double[] ys, double[] neg, double[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBars(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBars(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final short[] xs, final short[] ys, final short[] err) {
        nPlotErrorBarsH(labelId, xs, ys, err);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final short[] xs, final short[] ys, final short[] err, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, short[] xs, short[] ys, short[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, short[] xs, short[] ys, short[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final int[] xs, final int[] ys, final int[] err) {
        nPlotErrorBarsH(labelId, xs, ys, err);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final int[] xs, final int[] ys, final int[] err, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, int[] xs, int[] ys, int[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, int[] xs, int[] ys, int[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final long[] xs, final long[] ys, final long[] err) {
        nPlotErrorBarsH(labelId, xs, ys, err);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final long[] xs, final long[] ys, final long[] err, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, long[] xs, long[] ys, long[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, long[] xs, long[] ys, long[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final float[] xs, final float[] ys, final float[] err) {
        nPlotErrorBarsH(labelId, xs, ys, err);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final float[] xs, final float[] ys, final float[] err, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, float[] xs, float[] ys, float[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, float[] xs, float[] ys, float[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final double[] xs, final double[] ys, final double[] err) {
        nPlotErrorBarsH(labelId, xs, ys, err);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final double[] xs, final double[] ys, final double[] err, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, err, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, double[] xs, double[] ys, double[] err); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, double[] xs, double[] ys, double[] err, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto err = obj_err == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_err, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &err[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (err != NULL) env->ReleasePrimitiveArrayCritical(obj_err, err, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final short[] xs, final short[] ys, final short[] neg, final short[] pos) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final short[] xs, final short[] ys, final short[] neg, final short[] pos, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, short[] xs, short[] ys, short[] neg, short[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, short[] xs, short[] ys, short[] neg, short[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final int[] xs, final int[] ys, final int[] neg, final int[] pos) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final int[] xs, final int[] ys, final int[] neg, final int[] pos, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, int[] xs, int[] ys, int[] neg, int[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, int[] xs, int[] ys, int[] neg, int[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final long[] xs, final long[] ys, final long[] neg, final long[] pos) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final long[] xs, final long[] ys, final long[] neg, final long[] pos, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, long[] xs, long[] ys, long[] neg, long[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, long[] xs, long[] ys, long[] neg, long[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final float[] xs, final float[] ys, final float[] neg, final float[] pos) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final float[] xs, final float[] ys, final float[] neg, final float[] pos, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, float[] xs, float[] ys, float[] neg, float[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, float[] xs, float[] ys, float[] neg, float[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final double[] xs, final double[] ys, final double[] neg, final double[] pos) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos);
    }

    /**
     * Plots horizontal error bars. The label_id should be the same as the label_id of the associated line or bar plot.
     */
    public static void plotErrorBarsH(final String labelId, final double[] xs, final double[] ys, final double[] neg, final double[] pos, final int offset) {
        nPlotErrorBarsH(labelId, xs, ys, neg, pos, offset);
    }

    private static native void nPlotErrorBarsH(String labelId, double[] xs, double[] ys, double[] neg, double[] pos); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    private static native void nPlotErrorBarsH(String labelId, double[] xs, double[] ys, double[] neg, double[] pos, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto neg = obj_neg == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_neg, JNI_FALSE);
        auto pos = obj_pos == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pos, JNI_FALSE);
        ImPlot::PlotErrorBarsH(labelId, &xs[0], &ys[0], &neg[0], &pos[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        if (neg != NULL) env->ReleasePrimitiveArrayCritical(obj_neg, neg, JNI_FALSE);
        if (pos != NULL) env->ReleasePrimitiveArrayCritical(obj_pos, pos, JNI_FALSE);
    */

    // values

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] values) {
        nPlotStems(labelId, values);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] values, final double yRef) {
        nPlotStems(labelId, values, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] values, final double yRef, final double xscale) {
        nPlotStems(labelId, values, yRef, xscale);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] values, final double yRef, final double xscale, final double x0) {
        nPlotStems(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotStems(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotStems(String labelId, short[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, short[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, short[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, short[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, short[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] values) {
        nPlotStems(labelId, values);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] values, final double yRef) {
        nPlotStems(labelId, values, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] values, final double yRef, final double xscale) {
        nPlotStems(labelId, values, yRef, xscale);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] values, final double yRef, final double xscale, final double x0) {
        nPlotStems(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotStems(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotStems(String labelId, int[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, int[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, int[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, int[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, int[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] values) {
        nPlotStems(labelId, values);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] values, final double yRef) {
        nPlotStems(labelId, values, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] values, final double yRef, final double xscale) {
        nPlotStems(labelId, values, yRef, xscale);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] values, final double yRef, final double xscale, final double x0) {
        nPlotStems(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotStems(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotStems(String labelId, long[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, long[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, long[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, long[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, long[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] values) {
        nPlotStems(labelId, values);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] values, final double yRef) {
        nPlotStems(labelId, values, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] values, final double yRef, final double xscale) {
        nPlotStems(labelId, values, yRef, xscale);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] values, final double yRef, final double xscale, final double x0) {
        nPlotStems(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotStems(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotStems(String labelId, float[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, float[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, float[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, float[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, float[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] values) {
        nPlotStems(labelId, values);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] values, final double yRef) {
        nPlotStems(labelId, values, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] values, final double yRef, final double xscale) {
        nPlotStems(labelId, values, yRef, xscale);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] values, final double yRef, final double xscale, final double x0) {
        nPlotStems(labelId, values, yRef, xscale, x0);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] values, final double yRef, final double xscale, final double x0, final int offset) {
        nPlotStems(labelId, values, yRef, xscale, x0, offset);
    }

    private static native void nPlotStems(String labelId, double[] values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, double[] values, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, double[] values, double yRef, double xscale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, double[] values, double yRef, double xscale, double x0); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, double[] values, double yRef, double xscale, double x0, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotStems(labelId, &values[0], LEN(values), yRef, xscale, x0, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    // xs,ys

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] xs, final short[] ys) {
        nPlotStems(labelId, xs, ys);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] xs, final short[] ys, final double yRef) {
        nPlotStems(labelId, xs, ys, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final short[] xs, final short[] ys, final double yRef, final int offset) {
        nPlotStems(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotStems(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, short[] xs, short[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, short[] xs, short[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] xs, final int[] ys) {
        nPlotStems(labelId, xs, ys);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] xs, final int[] ys, final double yRef) {
        nPlotStems(labelId, xs, ys, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final int[] xs, final int[] ys, final double yRef, final int offset) {
        nPlotStems(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotStems(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, int[] xs, int[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, int[] xs, int[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] xs, final long[] ys) {
        nPlotStems(labelId, xs, ys);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] xs, final long[] ys, final double yRef) {
        nPlotStems(labelId, xs, ys, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final long[] xs, final long[] ys, final double yRef, final int offset) {
        nPlotStems(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotStems(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, long[] xs, long[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, long[] xs, long[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] xs, final float[] ys) {
        nPlotStems(labelId, xs, ys);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] xs, final float[] ys, final double yRef) {
        nPlotStems(labelId, xs, ys, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final float[] xs, final float[] ys, final double yRef, final int offset) {
        nPlotStems(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotStems(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, float[] xs, float[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, float[] xs, float[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] xs, final double[] ys) {
        nPlotStems(labelId, xs, ys);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] xs, final double[] ys, final double yRef) {
        nPlotStems(labelId, xs, ys, yRef);
    }

    /**
     * Plots vertical stems.
     */
    public static void plotStems(final String labelId, final double[] xs, final double[] ys, final double yRef, final int offset) {
        nPlotStems(labelId, xs, ys, yRef, offset);
    }

    private static native void nPlotStems(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, double[] xs, double[] ys, double yRef); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotStems(String labelId, double[] xs, double[] ys, double yRef, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotStems(labelId, &xs[0], &ys[0], LEN(xs), yRef, offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final short[] xs) {
        nPlotVLines(labelId, xs);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final short[] xs, final int offset) {
        nPlotVLines(labelId, xs, offset);
    }

    private static native void nPlotVLines(String labelId, short[] xs); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    private static native void nPlotVLines(String labelId, short[] xs, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final int[] xs) {
        nPlotVLines(labelId, xs);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final int[] xs, final int offset) {
        nPlotVLines(labelId, xs, offset);
    }

    private static native void nPlotVLines(String labelId, int[] xs); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    private static native void nPlotVLines(String labelId, int[] xs, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final long[] xs) {
        nPlotVLines(labelId, xs);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final long[] xs, final int offset) {
        nPlotVLines(labelId, xs, offset);
    }

    private static native void nPlotVLines(String labelId, long[] xs); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    private static native void nPlotVLines(String labelId, long[] xs, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final float[] xs) {
        nPlotVLines(labelId, xs);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final float[] xs, final int offset) {
        nPlotVLines(labelId, xs, offset);
    }

    private static native void nPlotVLines(String labelId, float[] xs); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    private static native void nPlotVLines(String labelId, float[] xs, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final double[] xs) {
        nPlotVLines(labelId, xs);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotVLines(final String labelId, final double[] xs, final int offset) {
        nPlotVLines(labelId, xs, offset);
    }

    private static native void nPlotVLines(String labelId, double[] xs); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    private static native void nPlotVLines(String labelId, double[] xs, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        ImPlot::PlotVLines(labelId, &xs[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final short[] ys) {
        nPlotHLines(labelId, ys);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final short[] ys, final int offset) {
        nPlotHLines(labelId, ys, offset);
    }

    private static native void nPlotHLines(String labelId, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotHLines(String labelId, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final int[] ys) {
        nPlotHLines(labelId, ys);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final int[] ys, final int offset) {
        nPlotHLines(labelId, ys, offset);
    }

    private static native void nPlotHLines(String labelId, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotHLines(String labelId, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final long[] ys) {
        nPlotHLines(labelId, ys);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final long[] ys, final int offset) {
        nPlotHLines(labelId, ys, offset);
    }

    private static native void nPlotHLines(String labelId, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotHLines(String labelId, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final float[] ys) {
        nPlotHLines(labelId, ys);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final float[] ys, final int offset) {
        nPlotHLines(labelId, ys, offset);
    }

    private static native void nPlotHLines(String labelId, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotHLines(String labelId, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final double[] ys) {
        nPlotHLines(labelId, ys);
    }

    /**
     * Plots infinite vertical or horizontal lines (e.g. for references or asymptotes).
     */
    public static void plotHLines(final String labelId, final double[] ys, final int offset) {
        nPlotHLines(labelId, ys, offset);
    }

    private static native void nPlotHLines(String labelId, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotHLines(String labelId, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotHLines(labelId, &ys[0], LEN(ys), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius, final boolean normalize) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final short[] values, final double x, final double y, final double radius, final boolean normalize, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, angle0);
    }

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius, boolean normalize); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius, boolean normalize, String labelFmt); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius, boolean normalize, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, short[] values, double x, double y, double radius, boolean normalize, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius, final boolean normalize) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final int[] values, final double x, final double y, final double radius, final boolean normalize, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, angle0);
    }

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius, boolean normalize); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius, boolean normalize, String labelFmt); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius, boolean normalize, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, int[] values, double x, double y, double radius, boolean normalize, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius, final boolean normalize) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final long[] values, final double x, final double y, final double radius, final boolean normalize, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, angle0);
    }

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius, boolean normalize); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius, boolean normalize, String labelFmt); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius, boolean normalize, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, long[] values, double x, double y, double radius, boolean normalize, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius, final boolean normalize) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final float[] values, final double x, final double y, final double radius, final boolean normalize, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, angle0);
    }

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius, boolean normalize); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius, boolean normalize, String labelFmt); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius, boolean normalize, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, float[] values, double x, double y, double radius, boolean normalize, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius, final boolean normalize) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius, final boolean normalize, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius, final String labelFmt, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, labelFmt, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, angle0);
    }

    /**
     * Plots a pie chart. If the sum of values{@code >}1 or normalize is true, each value will be normalized. Center and radius are in plot units. #label_fmt can be set to NULL for no labels.
     */
    public static void plotPieChart(final String[] labelIds, final double[] values, final double x, final double y, final double radius, final boolean normalize, final double angle0) {
        nPlotPieChart(labelIds, labelIds.length, values, x, y, radius, normalize, angle0);
    }

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius, boolean normalize); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius, boolean normalize, String labelFmt); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius, boolean normalize, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius, String labelFmt, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, labelFmt, angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, false, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotPieChart(String[] obj_labelIds, int labelIdsCount, double[] values, double x, double y, double radius, boolean normalize, double angle0); /*MANUAL
        const char* labelIds[labelIdsCount];
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labelIds[i] = rawStr;
        };
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotPieChart(labelIds, &values[0], LEN(values), x, y, radius, normalize, "%.1f", angle0);
        for (int i = 0; i < labelIdsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labelIds, i);
            env->ReleaseStringUTFChars(str, labelIds[i]);
        };
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols) {
        nPlotHeatmap(labelId, values, rows, cols);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final short[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, short[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, "%.1f", ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols) {
        nPlotHeatmap(labelId, values, rows, cols);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final int[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, int[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, "%.1f", ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols) {
        nPlotHeatmap(labelId, values, rows, cols);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final long[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, long[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, "%.1f", ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols) {
        nPlotHeatmap(labelId, values, rows, cols);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final float[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, float[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, "%.1f", ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols) {
        nPlotHeatmap(labelId, values, rows, cols);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final String labelFmt, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, labelFmt, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots a 2D heatmap chart. Values are expected to be in row-major order. Leave #scale_min and scale_max both at 0 for automatic color scaling, or set them to a predefined range. #label_fmt can be set to NULL for no labels.
     */
    public static void plotHeatmap(final String labelId, final double[] values, final int rows, final int cols, final double scaleMin, final double scaleMax, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotHeatmap(labelId, values, rows, cols, scaleMin, scaleMax, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, String labelFmt, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto labelFmt = obj_labelFmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelFmt, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, labelFmt, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (labelFmt != NULL) env->ReleaseStringUTFChars(obj_labelFmt, labelFmt);
    */

    private static native void nPlotHeatmap(String labelId, double[] values, int rows, int cols, double scaleMin, double scaleMax, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::PlotHeatmap(labelId, &values[0], rows, cols, scaleMin, scaleMax, "%.1f", ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values) {
        return nPlotHistogram(labelId, values);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins) {
        return nPlotHistogram(labelId, values, bins);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative) {
        return nPlotHistogram(labelId, values, bins, cumulative);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density) {
        return nPlotHistogram(labelId, values, bins, cumulative, density);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final short[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, barScale);
    }

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), ImPlotBin_Sturges, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, short[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values) {
        return nPlotHistogram(labelId, values);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins) {
        return nPlotHistogram(labelId, values, bins);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative) {
        return nPlotHistogram(labelId, values, bins, cumulative);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density) {
        return nPlotHistogram(labelId, values, bins, cumulative, density);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final int[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, barScale);
    }

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), ImPlotBin_Sturges, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, int[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values) {
        return nPlotHistogram(labelId, values);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins) {
        return nPlotHistogram(labelId, values, bins);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative) {
        return nPlotHistogram(labelId, values, bins, cumulative);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density) {
        return nPlotHistogram(labelId, values, bins, cumulative, density);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final long[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, barScale);
    }

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), ImPlotBin_Sturges, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, long[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values) {
        return nPlotHistogram(labelId, values);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins) {
        return nPlotHistogram(labelId, values, bins);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative) {
        return nPlotHistogram(labelId, values, bins, cumulative);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density) {
        return nPlotHistogram(labelId, values, bins, cumulative, density);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final float[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, barScale);
    }

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), ImPlotBin_Sturges, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, float[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values) {
        return nPlotHistogram(labelId, values);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins) {
        return nPlotHistogram(labelId, values, bins);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative) {
        return nPlotHistogram(labelId, values, bins, cumulative);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density) {
        return nPlotHistogram(labelId, values, bins, cumulative, density);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final boolean cumulative, final boolean density, final ImPlotRange range, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, range.min, range.max, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, cumulative, density, rangeMin, rangeMax, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final boolean outliers, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, outliers, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final ImPlotRange range, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, range.min, range.max, barScale);
    }

    /**
     * Plots a horizontal histogram. #bins can be a positive integer or an ImPlotBin_ method. If #cumulative is true, each bin contains its count plus the counts of all previous bins.
     * If #density is true, the PDF is visualized. If both are true, the CDF is visualized. If #range is left unspecified, the min/max of #values will be used as the range.
     * If #range is specified, outlier values outside of the range are not binned. However, outliers still count toward normalizing and cumulative counts unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram(final String labelId, final double[] values, final int bins, final boolean cumulative, final boolean density, final double rangeMin, final double rangeMax, final double barScale) {
        return nPlotHistogram(labelId, values, bins, cumulative, density, rangeMin, rangeMax, barScale);
    }

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, boolean cumulative, boolean density, double rangeMin, double rangeMax, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), ImPlotBin_Sturges, cumulative, density, ImPlotRange(rangeMin, rangeMax), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density, boolean outliers, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), outliers, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram(String obj_labelId, double[] obj_values, int bins, boolean cumulative, boolean density, double rangeMin, double rangeMax, double barScale); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram(labelId, &values[0], LEN(values), bins, cumulative, density, ImPlotRange(rangeMin, rangeMax), true, barScale);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        return _result;
    */

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys) {
        return nPlotHistogram2D(labelId, xs, ys);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final boolean density) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final short[] xs, final short[] ys, final int xBins, final int yBins, final boolean density, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, outliers);
    }

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins, int yBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins, int yBins, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, false, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, short[] obj_xs, short[] obj_ys, int xBins, int yBins, boolean density, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys) {
        return nPlotHistogram2D(labelId, xs, ys);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final boolean density) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final int[] xs, final int[] ys, final int xBins, final int yBins, final boolean density, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, outliers);
    }

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins, int yBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins, int yBins, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, false, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, int[] obj_xs, int[] obj_ys, int xBins, int yBins, boolean density, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys) {
        return nPlotHistogram2D(labelId, xs, ys);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final boolean density) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final long[] xs, final long[] ys, final int xBins, final int yBins, final boolean density, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, outliers);
    }

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins, int yBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins, int yBins, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, false, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, long[] obj_xs, long[] obj_ys, int xBins, int yBins, boolean density, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys) {
        return nPlotHistogram2D(labelId, xs, ys);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final boolean density) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final float[] xs, final float[] ys, final int xBins, final int yBins, final boolean density, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, outliers);
    }

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins, int yBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins, int yBins, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, false, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, float[] obj_xs, float[] obj_ys, int xBins, int yBins, boolean density, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys) {
        return nPlotHistogram2D(labelId, xs, ys);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final boolean density) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final boolean density, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final boolean density, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final ImPlotLimits range, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, range.x.min, range.y.min, range.x.max, range.y.max, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final double rangeMinX, final double rangeMinY, final double rangeMaxX, final double rangeMaxY, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, rangeMinX, rangeMinY, rangeMaxX, rangeMaxY, outliers);
    }

    /**
     * Plots two dimensional, bivariate histogram as a heatmap. #x_bins and #y_bins can be a positive integer or an ImPlotBin. If #density is true, the PDF is visualized.
     * If #range is left unspecified, the min/max of #xs an #ys will be used as the ranges. If #range is specified, outlier values outside of range are not binned.
     * However, outliers still count toward the normalizing count for density plots unless #outliers is false. The largest bin count or density is returned.
     */
    public static double plotHistogram2D(final String labelId, final double[] xs, final double[] ys, final int xBins, final int yBins, final boolean density, final boolean outliers) {
        return nPlotHistogram2D(labelId, xs, ys, xBins, yBins, density, outliers);
    }

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins, int yBins); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins, int yBins, boolean density); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins, int yBins, boolean density, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins, int yBins, double rangeMinX, double rangeMinY, double rangeMaxX, double rangeMaxY, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, false, ImPlotLimits(rangeMinX, rangeMinY, rangeMaxX, rangeMaxY), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    private static native double nPlotHistogram2D(String obj_labelId, double[] obj_xs, double[] obj_ys, int xBins, int yBins, boolean density, boolean outliers); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        auto _result = ImPlot::PlotHistogram2D(labelId, &xs[0], &ys[0], LEN(xs), xBins, yBins, density, ImPlotLimits(), outliers);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
        return _result;
    */

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final short[] xs, final short[] ys) {
        nPlotDigital(labelId, xs, ys);
    }

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final short[] xs, final short[] ys, final int offset) {
        nPlotDigital(labelId, xs, ys, offset);
    }

    private static native void nPlotDigital(String labelId, short[] xs, short[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotDigital(String labelId, short[] xs, short[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final int[] xs, final int[] ys) {
        nPlotDigital(labelId, xs, ys);
    }

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final int[] xs, final int[] ys, final int offset) {
        nPlotDigital(labelId, xs, ys, offset);
    }

    private static native void nPlotDigital(String labelId, int[] xs, int[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotDigital(String labelId, int[] xs, int[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final long[] xs, final long[] ys) {
        nPlotDigital(labelId, xs, ys);
    }

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final long[] xs, final long[] ys, final int offset) {
        nPlotDigital(labelId, xs, ys, offset);
    }

    private static native void nPlotDigital(String labelId, long[] xs, long[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotDigital(String labelId, long[] xs, long[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final float[] xs, final float[] ys) {
        nPlotDigital(labelId, xs, ys);
    }

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final float[] xs, final float[] ys, final int offset) {
        nPlotDigital(labelId, xs, ys, offset);
    }

    private static native void nPlotDigital(String labelId, float[] xs, float[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotDigital(String labelId, float[] xs, float[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final double[] xs, final double[] ys) {
        nPlotDigital(labelId, xs, ys);
    }

    /**
     * Plots digital data. Digital plots do not respond to y drag or zoom, and are always referenced to the bottom of the plot.
     */
    public static void plotDigital(final String labelId, final double[] xs, final double[] ys, final int offset) {
        nPlotDigital(labelId, xs, ys, offset);
    }

    private static native void nPlotDigital(String labelId, double[] xs, double[] ys); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    private static native void nPlotDigital(String labelId, double[] xs, double[] ys, int offset); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto xs = obj_xs == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xs, JNI_FALSE);
        auto ys = obj_ys == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_ys, JNI_FALSE);
        ImPlot::PlotDigital(labelId, &xs[0], &ys[0], LEN(xs), offset);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        if (xs != NULL) env->ReleasePrimitiveArrayCritical(obj_xs, xs, JNI_FALSE);
        if (ys != NULL) env->ReleasePrimitiveArrayCritical(obj_ys, ys, JNI_FALSE);
    */

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY) {
        nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax, final ImVec2 uv0) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax, final float uv0X, final float uv0Y) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY, final ImVec2 uv0) {
        nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax, final ImVec2 uv0, final ImVec2 uv1) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y, uv1X, uv1Y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY, final ImVec2 uv0, final ImVec2 uv1) {
        nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax, final ImVec2 uv0, final ImVec2 uv1, final ImVec4 tintCol) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final ImPlotPoint boundsMin, final ImPlotPoint boundsMax, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        nPlotImage(labelId, userTextureId, boundsMin.x, boundsMin.y, boundsMax.x, boundsMax.y, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW);
    }

    /**
     * Plots an axis-aligned image. #bounds_min/bounds_max are in plot coordinates (y-up) and #uv0/uv1 are in texture coordinates (y-down).
     */
    public static void plotImage(final String labelId, final int userTextureId, final double boundsMinX, final double boundsMinY, final double boundsMaxX, final double boundsMaxY, final ImVec2 uv0, final ImVec2 uv1, final ImVec4 tintCol) {
        nPlotImage(labelId, userTextureId, boundsMinX, boundsMinY, boundsMaxX, boundsMaxY, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    private static native void nPlotImage(String labelId, int userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        ImPlot::PlotImage(labelId, (ImTextureID)(intptr_t)userTextureId, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY));
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
    */

    private static native void nPlotImage(String labelId, int userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, float uv0X, float uv0Y); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImPlot::PlotImage(labelId, (ImTextureID)(intptr_t)userTextureId, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY), uv0);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
    */

    private static native void nPlotImage(String labelId, int userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImPlot::PlotImage(labelId, (ImTextureID)(intptr_t)userTextureId, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY), uv0, uv1);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
    */

    private static native void nPlotImage(String labelId, int userTextureId, double boundsMinX, double boundsMinY, double boundsMaxX, double boundsMaxY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 tintCol = ImVec4(tintColX, tintColY, tintColZ, tintColW);
        ImPlot::PlotImage(labelId, (ImTextureID)(intptr_t)userTextureId, ImPlotPoint(boundsMinX, boundsMinY), ImPlotPoint(boundsMaxX, boundsMaxY), uv0, uv1, tintCol);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
    */

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    public static void plotText(final String text, final double x, final double y) {
        nPlotText(text, x, y);
    }

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    public static void plotText(final String text, final double x, final double y, final boolean vertical) {
        nPlotText(text, x, y, vertical);
    }

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    public static void plotText(final String text, final double x, final double y, final boolean vertical, final ImVec2 pixOffset) {
        nPlotText(text, x, y, vertical, pixOffset.x, pixOffset.y);
    }

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    public static void plotText(final String text, final double x, final double y, final boolean vertical, final float pixOffsetX, final float pixOffsetY) {
        nPlotText(text, x, y, vertical, pixOffsetX, pixOffsetY);
    }

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    public static void plotText(final String text, final double x, final double y, final ImVec2 pixOffset) {
        nPlotText(text, x, y, pixOffset.x, pixOffset.y);
    }

    /**
     * Plots a centered text label at point x,y with an optional pixel offset. Text color can be changed with ImPlot::PushStyleColor(ImPlotCol_InlayText, ...).
     */
    public static void plotText(final String text, final double x, final double y, final float pixOffsetX, final float pixOffsetY) {
        nPlotText(text, x, y, pixOffsetX, pixOffsetY);
    }

    private static native void nPlotText(String text, double x, double y); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImPlot::PlotText(text, x, y);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native void nPlotText(String text, double x, double y, boolean vertical); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImPlot::PlotText(text, x, y, vertical);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native void nPlotText(String text, double x, double y, boolean vertical, float pixOffsetX, float pixOffsetY); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImVec2 pixOffset = ImVec2(pixOffsetX, pixOffsetY);
        ImPlot::PlotText(text, x, y, vertical, pixOffset);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native void nPlotText(String text, double x, double y, float pixOffsetX, float pixOffsetY); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImVec2 pixOffset = ImVec2(pixOffsetX, pixOffsetY);
        ImPlot::PlotText(text, x, y, false, pixOffset);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    /**
     * Plots a dummy item (i.e. adds a legend entry colored by ImPlotCol_Line)
     */
    public static void plotDummy(final String labelID) {
        nPlotDummy(labelID);
    }

    private static native void nPlotDummy(String labelID); /*MANUAL
        auto labelID = obj_labelID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelID, JNI_FALSE);
        ImPlot::PlotDummy(labelID);
        if (labelID != NULL) env->ReleaseStringUTFChars(obj_labelID, labelID);
    */

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
    public static void setNextPlotLimits(final double xMin, final double xMax, final double yMin, final double yMax) {
        nSetNextPlotLimits(xMin, xMax, yMin, yMax);
    }

    /**
     * Set the axes range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the axes limits will be locked.
     */
    public static void setNextPlotLimits(final double xMin, final double xMax, final double yMin, final double yMax, final int cond) {
        nSetNextPlotLimits(xMin, xMax, yMin, yMax, cond);
    }

    private static native void nSetNextPlotLimits(double xMin, double xMax, double yMin, double yMax); /*
        ImPlot::SetNextPlotLimits(xMin, xMax, yMin, yMax);
    */

    private static native void nSetNextPlotLimits(double xMin, double xMax, double yMin, double yMax, int cond); /*
        ImPlot::SetNextPlotLimits(xMin, xMax, yMin, yMax, cond);
    */

    /**
     * Set the X axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the X axis limits will be locked.
     */
    public static void setNextPlotLimitsX(final double xMin, final double xMax) {
        nSetNextPlotLimitsX(xMin, xMax);
    }

    /**
     * Set the X axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the X axis limits will be locked.
     */
    public static void setNextPlotLimitsX(final double xMin, final double xMax, final int cond) {
        nSetNextPlotLimitsX(xMin, xMax, cond);
    }

    private static native void nSetNextPlotLimitsX(double xMin, double xMax); /*
        ImPlot::SetNextPlotLimitsX(xMin, xMax);
    */

    private static native void nSetNextPlotLimitsX(double xMin, double xMax, int cond); /*
        ImPlot::SetNextPlotLimitsX(xMin, xMax, cond);
    */

    /**
     * Set the Y axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the Y axis limits will be locked.
     */
    public static void setNextPlotLimitsY(final double yMin, final double yMax) {
        nSetNextPlotLimitsY(yMin, yMax);
    }

    /**
     * Set the Y axis range limits of the next plot. Call right before BeginPlot(). If ImGuiCond_Always is used, the Y axis limits will be locked.
     */
    public static void setNextPlotLimitsY(final double yMin, final double yMax, final int cond) {
        nSetNextPlotLimitsY(yMin, yMax, cond);
    }

    private static native void nSetNextPlotLimitsY(double yMin, double yMax); /*
        ImPlot::SetNextPlotLimitsY(yMin, yMax);
    */

    private static native void nSetNextPlotLimitsY(double yMin, double yMax, int cond); /*
        ImPlot::SetNextPlotLimitsY(yMin, yMax, cond);
    */

    /**
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xMin, final ImDouble xMax, final ImDouble yMin, final ImDouble yMax) {
        nLinkNextPlotLimits(xMin != null ? xMin.getData() : null, xMax != null ? xMax.getData() : null, yMin != null ? yMin.getData() : null, yMax != null ? yMax.getData() : null);
    }

    /**
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xMin, final ImDouble xMax, final ImDouble yMin, final ImDouble yMax, final ImDouble yMin2) {
        nLinkNextPlotLimits(xMin != null ? xMin.getData() : null, xMax != null ? xMax.getData() : null, yMin != null ? yMin.getData() : null, yMax != null ? yMax.getData() : null, yMin2 != null ? yMin2.getData() : null);
    }

    /**
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xMin, final ImDouble xMax, final ImDouble yMin, final ImDouble yMax, final ImDouble yMin2, final ImDouble yMax2) {
        nLinkNextPlotLimits(xMin != null ? xMin.getData() : null, xMax != null ? xMax.getData() : null, yMin != null ? yMin.getData() : null, yMax != null ? yMax.getData() : null, yMin2 != null ? yMin2.getData() : null, yMax2 != null ? yMax2.getData() : null);
    }

    /**
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xMin, final ImDouble xMax, final ImDouble yMin, final ImDouble yMax, final ImDouble yMin2, final ImDouble yMax2, final ImDouble yMin3) {
        nLinkNextPlotLimits(xMin != null ? xMin.getData() : null, xMax != null ? xMax.getData() : null, yMin != null ? yMin.getData() : null, yMax != null ? yMax.getData() : null, yMin2 != null ? yMin2.getData() : null, yMax2 != null ? yMax2.getData() : null, yMin3 != null ? yMin3.getData() : null);
    }

    /**
     * Links the next plot limits to external values. Set to NULL for no linkage. The pointer data must remain valid until the matching call to EndPlot.
     */
    public static void linkNextPlotLimits(final ImDouble xMin, final ImDouble xMax, final ImDouble yMin, final ImDouble yMax, final ImDouble yMin2, final ImDouble yMax2, final ImDouble yMin3, final ImDouble yMax3) {
        nLinkNextPlotLimits(xMin != null ? xMin.getData() : null, xMax != null ? xMax.getData() : null, yMin != null ? yMin.getData() : null, yMax != null ? yMax.getData() : null, yMin2 != null ? yMin2.getData() : null, yMax2 != null ? yMax2.getData() : null, yMin3 != null ? yMin3.getData() : null, yMax3 != null ? yMax3.getData() : null);
    }

    private static native void nLinkNextPlotLimits(double[] xMin, double[] xMax, double[] yMin, double[] yMax); /*MANUAL
        auto xMin = obj_xMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMin, JNI_FALSE);
        auto xMax = obj_xMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMax, JNI_FALSE);
        auto yMin = obj_yMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin, JNI_FALSE);
        auto yMax = obj_yMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax, JNI_FALSE);
        ImPlot::LinkNextPlotLimits((xMin != NULL ? &xMin[0] : NULL), (xMax != NULL ? &xMax[0] : NULL), (yMin != NULL ? &yMin[0] : NULL), (yMax != NULL ? &yMax[0] : NULL));
        if (xMin != NULL) env->ReleasePrimitiveArrayCritical(obj_xMin, xMin, JNI_FALSE);
        if (xMax != NULL) env->ReleasePrimitiveArrayCritical(obj_xMax, xMax, JNI_FALSE);
        if (yMin != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin, yMin, JNI_FALSE);
        if (yMax != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax, yMax, JNI_FALSE);
    */

    private static native void nLinkNextPlotLimits(double[] xMin, double[] xMax, double[] yMin, double[] yMax, double[] yMin2); /*MANUAL
        auto xMin = obj_xMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMin, JNI_FALSE);
        auto xMax = obj_xMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMax, JNI_FALSE);
        auto yMin = obj_yMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin, JNI_FALSE);
        auto yMax = obj_yMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax, JNI_FALSE);
        auto yMin2 = obj_yMin2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin2, JNI_FALSE);
        ImPlot::LinkNextPlotLimits((xMin != NULL ? &xMin[0] : NULL), (xMax != NULL ? &xMax[0] : NULL), (yMin != NULL ? &yMin[0] : NULL), (yMax != NULL ? &yMax[0] : NULL), (yMin2 != NULL ? &yMin2[0] : NULL));
        if (xMin != NULL) env->ReleasePrimitiveArrayCritical(obj_xMin, xMin, JNI_FALSE);
        if (xMax != NULL) env->ReleasePrimitiveArrayCritical(obj_xMax, xMax, JNI_FALSE);
        if (yMin != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin, yMin, JNI_FALSE);
        if (yMax != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax, yMax, JNI_FALSE);
        if (yMin2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin2, yMin2, JNI_FALSE);
    */

    private static native void nLinkNextPlotLimits(double[] xMin, double[] xMax, double[] yMin, double[] yMax, double[] yMin2, double[] yMax2); /*MANUAL
        auto xMin = obj_xMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMin, JNI_FALSE);
        auto xMax = obj_xMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMax, JNI_FALSE);
        auto yMin = obj_yMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin, JNI_FALSE);
        auto yMax = obj_yMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax, JNI_FALSE);
        auto yMin2 = obj_yMin2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin2, JNI_FALSE);
        auto yMax2 = obj_yMax2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax2, JNI_FALSE);
        ImPlot::LinkNextPlotLimits((xMin != NULL ? &xMin[0] : NULL), (xMax != NULL ? &xMax[0] : NULL), (yMin != NULL ? &yMin[0] : NULL), (yMax != NULL ? &yMax[0] : NULL), (yMin2 != NULL ? &yMin2[0] : NULL), (yMax2 != NULL ? &yMax2[0] : NULL));
        if (xMin != NULL) env->ReleasePrimitiveArrayCritical(obj_xMin, xMin, JNI_FALSE);
        if (xMax != NULL) env->ReleasePrimitiveArrayCritical(obj_xMax, xMax, JNI_FALSE);
        if (yMin != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin, yMin, JNI_FALSE);
        if (yMax != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax, yMax, JNI_FALSE);
        if (yMin2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin2, yMin2, JNI_FALSE);
        if (yMax2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax2, yMax2, JNI_FALSE);
    */

    private static native void nLinkNextPlotLimits(double[] xMin, double[] xMax, double[] yMin, double[] yMax, double[] yMin2, double[] yMax2, double[] yMin3); /*MANUAL
        auto xMin = obj_xMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMin, JNI_FALSE);
        auto xMax = obj_xMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMax, JNI_FALSE);
        auto yMin = obj_yMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin, JNI_FALSE);
        auto yMax = obj_yMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax, JNI_FALSE);
        auto yMin2 = obj_yMin2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin2, JNI_FALSE);
        auto yMax2 = obj_yMax2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax2, JNI_FALSE);
        auto yMin3 = obj_yMin3 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin3, JNI_FALSE);
        ImPlot::LinkNextPlotLimits((xMin != NULL ? &xMin[0] : NULL), (xMax != NULL ? &xMax[0] : NULL), (yMin != NULL ? &yMin[0] : NULL), (yMax != NULL ? &yMax[0] : NULL), (yMin2 != NULL ? &yMin2[0] : NULL), (yMax2 != NULL ? &yMax2[0] : NULL), (yMin3 != NULL ? &yMin3[0] : NULL));
        if (xMin != NULL) env->ReleasePrimitiveArrayCritical(obj_xMin, xMin, JNI_FALSE);
        if (xMax != NULL) env->ReleasePrimitiveArrayCritical(obj_xMax, xMax, JNI_FALSE);
        if (yMin != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin, yMin, JNI_FALSE);
        if (yMax != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax, yMax, JNI_FALSE);
        if (yMin2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin2, yMin2, JNI_FALSE);
        if (yMax2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax2, yMax2, JNI_FALSE);
        if (yMin3 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin3, yMin3, JNI_FALSE);
    */

    private static native void nLinkNextPlotLimits(double[] xMin, double[] xMax, double[] yMin, double[] yMax, double[] yMin2, double[] yMax2, double[] yMin3, double[] yMax3); /*MANUAL
        auto xMin = obj_xMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMin, JNI_FALSE);
        auto xMax = obj_xMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xMax, JNI_FALSE);
        auto yMin = obj_yMin == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin, JNI_FALSE);
        auto yMax = obj_yMax == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax, JNI_FALSE);
        auto yMin2 = obj_yMin2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin2, JNI_FALSE);
        auto yMax2 = obj_yMax2 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax2, JNI_FALSE);
        auto yMin3 = obj_yMin3 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMin3, JNI_FALSE);
        auto yMax3 = obj_yMax3 == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yMax3, JNI_FALSE);
        ImPlot::LinkNextPlotLimits((xMin != NULL ? &xMin[0] : NULL), (xMax != NULL ? &xMax[0] : NULL), (yMin != NULL ? &yMin[0] : NULL), (yMax != NULL ? &yMax[0] : NULL), (yMin2 != NULL ? &yMin2[0] : NULL), (yMax2 != NULL ? &yMax2[0] : NULL), (yMin3 != NULL ? &yMin3[0] : NULL), (yMax3 != NULL ? &yMax3[0] : NULL));
        if (xMin != NULL) env->ReleasePrimitiveArrayCritical(obj_xMin, xMin, JNI_FALSE);
        if (xMax != NULL) env->ReleasePrimitiveArrayCritical(obj_xMax, xMax, JNI_FALSE);
        if (yMin != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin, yMin, JNI_FALSE);
        if (yMax != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax, yMax, JNI_FALSE);
        if (yMin2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin2, yMin2, JNI_FALSE);
        if (yMax2 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax2, yMax2, JNI_FALSE);
        if (yMin3 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMin3, yMin3, JNI_FALSE);
        if (yMax3 != NULL) env->ReleasePrimitiveArrayCritical(obj_yMax3, yMax3, JNI_FALSE);
    */

    /**
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes() {
        nFitNextPlotAxes();
    }

    /**
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes(final boolean x) {
        nFitNextPlotAxes(x);
    }

    /**
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes(final boolean x, final boolean y) {
        nFitNextPlotAxes(x, y);
    }

    /**
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes(final boolean x, final boolean y, final boolean y2) {
        nFitNextPlotAxes(x, y, y2);
    }

    /**
     * Fits the next plot axes to all plotted data if they are unlocked (equivalent to double-clicks).
     */
    public static void fitNextPlotAxes(final boolean x, final boolean y, final boolean y2, final boolean y3) {
        nFitNextPlotAxes(x, y, y2, y3);
    }

    private static native void nFitNextPlotAxes(); /*
        ImPlot::FitNextPlotAxes();
    */

    private static native void nFitNextPlotAxes(boolean x); /*
        ImPlot::FitNextPlotAxes(x);
    */

    private static native void nFitNextPlotAxes(boolean x, boolean y); /*
        ImPlot::FitNextPlotAxes(x, y);
    */

    private static native void nFitNextPlotAxes(boolean x, boolean y, boolean y2); /*
        ImPlot::FitNextPlotAxes(x, y, y2);
    */

    private static native void nFitNextPlotAxes(boolean x, boolean y, boolean y2, boolean y3); /*
        ImPlot::FitNextPlotAxes(x, y, y2, y3);
    */

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double[] values, final int nTicks) {
        nSetNextPlotTicksX(values, nTicks);
    }

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double[] values, final int nTicks, final String[] labels) {
        nSetNextPlotTicksX(values, nTicks, labels, labels.length);
    }

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double[] values, final int nTicks, final String[] labels, final boolean keepDefault) {
        nSetNextPlotTicksX(values, nTicks, labels, labels.length, keepDefault);
    }

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double[] values, final int nTicks, final boolean keepDefault) {
        nSetNextPlotTicksX(values, nTicks, keepDefault);
    }

    private static native void nSetNextPlotTicksX(double[] values, int nTicks); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::SetNextPlotTicksX(&values[0], nTicks);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nSetNextPlotTicksX(double[] values, int nTicks, String[] obj_labels, int labelsCount); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksX(&values[0], nTicks, labels);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksX(double[] values, int nTicks, String[] obj_labels, int labelsCount, boolean keepDefault); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksX(&values[0], nTicks, labels, keepDefault);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksX(double[] values, int nTicks, boolean keepDefault); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::SetNextPlotTicksX(&values[0], nTicks, NULL, keepDefault);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks) {
        nSetNextPlotTicksX(xMin, xMax, nTicks);
    }

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks, final String[] labels) {
        nSetNextPlotTicksX(xMin, xMax, nTicks, labels, labels.length);
    }

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks, final String[] labels, final boolean keepDefault) {
        nSetNextPlotTicksX(xMin, xMax, nTicks, labels, labels.length, keepDefault);
    }

    /**
     * Set the X axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksX(final double xMin, final double xMax, final int nTicks, final boolean keepDefault) {
        nSetNextPlotTicksX(xMin, xMax, nTicks, keepDefault);
    }

    private static native void nSetNextPlotTicksX(double xMin, double xMax, int nTicks); /*
        ImPlot::SetNextPlotTicksX(xMin, xMax, nTicks);
    */

    private static native void nSetNextPlotTicksX(double xMin, double xMax, int nTicks, String[] obj_labels, int labelsCount); /*MANUAL
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksX(xMin, xMax, nTicks, labels);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksX(double xMin, double xMax, int nTicks, String[] obj_labels, int labelsCount, boolean keepDefault); /*MANUAL
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksX(xMin, xMax, nTicks, labels, keepDefault);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksX(double xMin, double xMax, int nTicks, boolean keepDefault); /*
        ImPlot::SetNextPlotTicksX(xMin, xMax, nTicks, NULL, keepDefault);
    */

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks) {
        nSetNextPlotTicksY(values, nTicks);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks, final String[] labels) {
        nSetNextPlotTicksY(values, nTicks, labels, labels.length);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks, final String[] labels, final boolean keepDefault) {
        nSetNextPlotTicksY(values, nTicks, labels, labels.length, keepDefault);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks, final String[] labels, final boolean keepDefault, final int yAxis) {
        nSetNextPlotTicksY(values, nTicks, labels, labels.length, keepDefault, yAxis);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks, final boolean keepDefault, final int yAxis) {
        nSetNextPlotTicksY(values, nTicks, keepDefault, yAxis);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks, final int yAxis) {
        nSetNextPlotTicksY(values, nTicks, yAxis);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double[] values, final int nTicks, final String[] labels, final int yAxis) {
        nSetNextPlotTicksY(values, nTicks, labels, labels.length, yAxis);
    }

    private static native void nSetNextPlotTicksY(double[] values, int nTicks); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::SetNextPlotTicksY(&values[0], nTicks);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nSetNextPlotTicksY(double[] values, int nTicks, String[] obj_labels, int labelsCount); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(&values[0], nTicks, labels);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksY(double[] values, int nTicks, String[] obj_labels, int labelsCount, boolean keepDefault); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(&values[0], nTicks, labels, keepDefault);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksY(double[] values, int nTicks, String[] obj_labels, int labelsCount, boolean keepDefault, int yAxis); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(&values[0], nTicks, labels, keepDefault, yAxis);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksY(double[] values, int nTicks, boolean keepDefault, int yAxis); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::SetNextPlotTicksY(&values[0], nTicks, NULL, keepDefault, yAxis);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nSetNextPlotTicksY(double[] values, int nTicks, int yAxis); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImPlot::SetNextPlotTicksY(&values[0], nTicks, NULL, false, yAxis);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nSetNextPlotTicksY(double[] values, int nTicks, String[] obj_labels, int labelsCount, int yAxis); /*MANUAL
        auto values = obj_values == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(&values[0], nTicks, labels, false, yAxis);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks) {
        nSetNextPlotTicksY(yMin, yMax, nTicks);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks, final String[] labels) {
        nSetNextPlotTicksY(yMin, yMax, nTicks, labels, labels.length);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks, final String[] labels, final boolean keepDefault) {
        nSetNextPlotTicksY(yMin, yMax, nTicks, labels, labels.length, keepDefault);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks, final String[] labels, final boolean keepDefault, final int yAxis) {
        nSetNextPlotTicksY(yMin, yMax, nTicks, labels, labels.length, keepDefault, yAxis);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks, final boolean keepDefault, final int yAxis) {
        nSetNextPlotTicksY(yMin, yMax, nTicks, keepDefault, yAxis);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks, final int yAxis) {
        nSetNextPlotTicksY(yMin, yMax, nTicks, yAxis);
    }

    /**
     * Set the Y axis ticks and optionally the labels for the next plot. To keep the default ticks, set #keep_default=true.
     */
    public static void setNextPlotTicksY(final double yMin, final double yMax, final int nTicks, final String[] labels, final int yAxis) {
        nSetNextPlotTicksY(yMin, yMax, nTicks, labels, labels.length, yAxis);
    }

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks); /*
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks);
    */

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks, String[] obj_labels, int labelsCount); /*MANUAL
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks, labels);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks, String[] obj_labels, int labelsCount, boolean keepDefault); /*MANUAL
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks, labels, keepDefault);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks, String[] obj_labels, int labelsCount, boolean keepDefault, int yAxis); /*MANUAL
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks, labels, keepDefault, yAxis);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks, boolean keepDefault, int yAxis); /*
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks, NULL, keepDefault, yAxis);
    */

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks, int yAxis); /*
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks, NULL, false, yAxis);
    */

    private static native void nSetNextPlotTicksY(double yMin, double yMax, int nTicks, String[] obj_labels, int labelsCount, int yAxis); /*MANUAL
        const char* labels[labelsCount];
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            labels[i] = rawStr;
        };
        ImPlot::SetNextPlotTicksY(yMin, yMax, nTicks, labels, false, yAxis);
        for (int i = 0; i < labelsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_labels, i);
            env->ReleaseStringUTFChars(str, labels[i]);
        };
    */

    /**
     * Set the format for numeric X axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.). Not applicable if ImPlotAxisFlags_Time enabled.
     */
    public static void setNextPlotFormatX(final String fmt) {
        nSetNextPlotFormatX(fmt);
    }

    private static native void nSetNextPlotFormatX(String fmt); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImPlot::SetNextPlotFormatX(fmt);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Set the format for numeric Y axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.).
     */
    public static void setNextPlotFormatY(final String fmt) {
        nSetNextPlotFormatY(fmt);
    }

    /**
     * Set the format for numeric Y axis labels (default="%g"). Formated values will be doubles (i.e. don't supply %d, %i, etc.).
     */
    public static void setNextPlotFormatY(final String fmt, final int yAxis) {
        nSetNextPlotFormatY(fmt, yAxis);
    }

    private static native void nSetNextPlotFormatY(String fmt); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImPlot::SetNextPlotFormatY(fmt);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    private static native void nSetNextPlotFormatY(String fmt, int yAxis); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImPlot::SetNextPlotFormatY(fmt, yAxis);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Select which Y axis will be used for subsequent plot elements. The default is ImPlotYAxis_1, or the first (left) Y axis. Enable 2nd and 3rd axes with ImPlotFlags_YAxisX.
     */
    public static void setPlotYAxis(final int yAxis) {
        nSetPlotYAxis(yAxis);
    }

    private static native void nSetPlotYAxis(int yAxis); /*
        ImPlot::SetPlotYAxis(yAxis);
    */

    /**
     * Hides the next plot item. Use ImGuiCond.Always if you need to forcefully set this every frame (default ImGuiCond.Once).
     */
    public static void hideNextItem() {
        nHideNextItem();
    }

    /**
     * Hides the next plot item. Use ImGuiCond.Always if you need to forcefully set this every frame (default ImGuiCond.Once).
     */
    public static void hideNextItem(final boolean hidden) {
        nHideNextItem(hidden);
    }

    /**
     * Hides the next plot item. Use ImGuiCond.Always if you need to forcefully set this every frame (default ImGuiCond.Once).
     */
    public static void hideNextItem(final boolean hidden, final int cond) {
        nHideNextItem(hidden, cond);
    }

    /**
     * Hides the next plot item. Use ImGuiCond.Always if you need to forcefully set this every frame (default ImGuiCond.Once).
     */
    public static void hideNextItem(final int cond) {
        nHideNextItem(cond);
    }

    private static native void nHideNextItem(); /*
        ImPlot::HideNextItem();
    */

    private static native void nHideNextItem(boolean hidden); /*
        ImPlot::HideNextItem(hidden);
    */

    private static native void nHideNextItem(boolean hidden, int cond); /*
        ImPlot::HideNextItem(hidden, cond);
    */

    private static native void nHideNextItem(int cond); /*
        ImPlot::HideNextItem(true, cond);
    */

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final ImVec2 pix) {
        final ImPlotPoint dst = new ImPlotPoint();
        nPixelsToPlot(dst, pix.x, pix.y);
        return dst;
    }

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final float pixX, final float pixY) {
        final ImPlotPoint dst = new ImPlotPoint();
        nPixelsToPlot(dst, pixX, pixY);
        return dst;
    }

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void pixelsToPlot(final ImPlotPoint dst, final ImVec2 pix) {
        nPixelsToPlot(dst, pix.x, pix.y);
    }

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final ImVec2 pix, final int yAxis) {
        final ImPlotPoint dst = new ImPlotPoint();
        nPixelsToPlot(dst, pix.x, pix.y, yAxis);
        return dst;
    }

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint pixelsToPlot(final float pixX, final float pixY, final int yAxis) {
        final ImPlotPoint dst = new ImPlotPoint();
        nPixelsToPlot(dst, pixX, pixY, yAxis);
        return dst;
    }

    /**
     * Convert pixels to a position in the current plot's coordinate system. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void pixelsToPlot(final ImPlotPoint dst, final ImVec2 pix, final int yAxis) {
        nPixelsToPlot(dst, pix.x, pix.y, yAxis);
    }

    private static native void nPixelsToPlot(ImPlotPoint dst, float pixX, float pixY); /*MANUAL
        ImVec2 pix = ImVec2(pixX, pixY);
        Jni::ImPlotPointCpy(env, ImPlot::PixelsToPlot(pix), dst);
    */

    private static native void nPixelsToPlot(ImPlotPoint dst, float pixX, float pixY, int yAxis); /*MANUAL
        ImVec2 pix = ImVec2(pixX, pixY);
        Jni::ImPlotPointCpy(env, ImPlot::PixelsToPlot(pix, yAxis), dst);
    */

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(final ImPlotPoint plt) {
        final ImVec2 dst = new ImVec2();
        nPlotToPixels(dst, plt.x, plt.y);
        return dst;
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(final double pltX, final double pltY) {
        final ImVec2 dst = new ImVec2();
        nPlotToPixels(dst, pltX, pltY);
        return dst;
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static float plotToPixelsX(final ImPlotPoint plt) {
        return nPlotToPixelsX(plt.x, plt.y);
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static float plotToPixelsY(final ImPlotPoint plt) {
        return nPlotToPixelsY(plt.x, plt.y);
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void plotToPixels(final ImVec2 dst, final ImPlotPoint plt) {
        nPlotToPixels(dst, plt.x, plt.y);
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(final ImPlotPoint plt, final int yAxis) {
        final ImVec2 dst = new ImVec2();
        nPlotToPixels(dst, plt.x, plt.y, yAxis);
        return dst;
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImVec2 plotToPixels(final double pltX, final double pltY, final int yAxis) {
        final ImVec2 dst = new ImVec2();
        nPlotToPixels(dst, pltX, pltY, yAxis);
        return dst;
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static float plotToPixelsX(final ImPlotPoint plt, final int yAxis) {
        return nPlotToPixelsX(plt.x, plt.y, yAxis);
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static float plotToPixelsY(final ImPlotPoint plt, final int yAxis) {
        return nPlotToPixelsY(plt.x, plt.y, yAxis);
    }

    /**
     * Convert a position in the current plot's coordinate system to pixels. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void plotToPixels(final ImVec2 dst, final ImPlotPoint plt, final int yAxis) {
        nPlotToPixels(dst, plt.x, plt.y, yAxis);
    }

    private static native void nPlotToPixels(ImVec2 dst, double pltX, double pltY); /*
        Jni::ImVec2Cpy(env, ImPlot::PlotToPixels(ImPlotPoint(pltX, pltY)), dst);
    */

    private static native float nPlotToPixelsX(double pltX, double pltY); /*
        return ImPlot::PlotToPixels(ImPlotPoint(pltX, pltY)).x;
    */

    private static native float nPlotToPixelsY(double pltX, double pltY); /*
        return ImPlot::PlotToPixels(ImPlotPoint(pltX, pltY)).y;
    */

    private static native void nPlotToPixels(ImVec2 dst, double pltX, double pltY, int yAxis); /*
        Jni::ImVec2Cpy(env, ImPlot::PlotToPixels(ImPlotPoint(pltX, pltY), yAxis), dst);
    */

    private static native float nPlotToPixelsX(double pltX, double pltY, int yAxis); /*
        return ImPlot::PlotToPixels(ImPlotPoint(pltX, pltY), yAxis).x;
    */

    private static native float nPlotToPixelsY(double pltX, double pltY, int yAxis); /*
        return ImPlot::PlotToPixels(ImPlotPoint(pltX, pltY), yAxis).y;
    */

    /**
     * Get the current Plot position (top-left) in pixels.
     */
    public static ImVec2 getPlotPos() {
        final ImVec2 dst = new ImVec2();
        nGetPlotPos(dst);
        return dst;
    }

    /**
     * Get the current Plot position (top-left) in pixels.
     */
    public static float getPlotPosX() {
        return nGetPlotPosX();
    }

    /**
     * Get the current Plot position (top-left) in pixels.
     */
    public static float getPlotPosY() {
        return nGetPlotPosY();
    }

    /**
     * Get the current Plot position (top-left) in pixels.
     */
    public static void getPlotPos(final ImVec2 dst) {
        nGetPlotPos(dst);
    }

    private static native void nGetPlotPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImPlot::GetPlotPos(), dst);
    */

    private static native float nGetPlotPosX(); /*
        return ImPlot::GetPlotPos().x;
    */

    private static native float nGetPlotPosY(); /*
        return ImPlot::GetPlotPos().y;
    */

    /**
     * Get the current Plot size in pixels.
     */
    public static ImVec2 getPlotSize() {
        final ImVec2 dst = new ImVec2();
        nGetPlotSize(dst);
        return dst;
    }

    /**
     * Get the current Plot size in pixels.
     */
    public static float getPlotSizeX() {
        return nGetPlotSizeX();
    }

    /**
     * Get the current Plot size in pixels.
     */
    public static float getPlotSizeY() {
        return nGetPlotSizeY();
    }

    /**
     * Get the current Plot size in pixels.
     */
    public static void getPlotSize(final ImVec2 dst) {
        nGetPlotSize(dst);
    }

    private static native void nGetPlotSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImPlot::GetPlotSize(), dst);
    */

    private static native float nGetPlotSizeX(); /*
        return ImPlot::GetPlotSize().x;
    */

    private static native float nGetPlotSizeY(); /*
        return ImPlot::GetPlotSize().y;
    */

    /**
     * Returns true if the plot area in the current plot is hovered.
     */
    public static boolean isPlotHovered() {
        return nIsPlotHovered();
    }

    private static native boolean nIsPlotHovered(); /*
        return ImPlot::IsPlotHovered();
    */

    /**
     * Returns true if the XAxis plot area in the current plot is hovered.
     */
    public static boolean isPlotXAxisHovered() {
        return nIsPlotXAxisHovered();
    }

    private static native boolean nIsPlotXAxisHovered(); /*
        return ImPlot::IsPlotXAxisHovered();
    */

    /**
     * Returns true if the YAxis[n] plot area in the current plot is hovered.
     */
    public static boolean isPlotYAxisHovered() {
        return nIsPlotYAxisHovered();
    }

    /**
     * Returns true if the YAxis[n] plot area in the current plot is hovered.
     */
    public static boolean isPlotYAxisHovered(final int yAxis) {
        return nIsPlotYAxisHovered(yAxis);
    }

    private static native boolean nIsPlotYAxisHovered(); /*
        return ImPlot::IsPlotYAxisHovered();
    */

    private static native boolean nIsPlotYAxisHovered(int yAxis); /*
        return ImPlot::IsPlotYAxisHovered(yAxis);
    */

    /**
     * Returns the mouse position in x,y coordinates of the current plot. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint getPlotMousePos() {
        final ImPlotPoint dst = new ImPlotPoint();
        nGetPlotMousePos(dst);
        return dst;
    }

    /**
     * Returns the mouse position in x,y coordinates of the current plot. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void getPlotMousePos(final ImPlotPoint dst) {
        nGetPlotMousePos(dst);
    }

    /**
     * Returns the mouse position in x,y coordinates of the current plot. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotPoint getPlotMousePos(final int yAxis) {
        final ImPlotPoint dst = new ImPlotPoint();
        nGetPlotMousePos(dst, yAxis);
        return dst;
    }

    /**
     * Returns the mouse position in x,y coordinates of the current plot. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void getPlotMousePos(final ImPlotPoint dst, final int yAxis) {
        nGetPlotMousePos(dst, yAxis);
    }

    private static native void nGetPlotMousePos(ImPlotPoint dst); /*
        Jni::ImPlotPointCpy(env, ImPlot::GetPlotMousePos(), dst);
    */

    private static native void nGetPlotMousePos(ImPlotPoint dst, int yAxis); /*
        Jni::ImPlotPointCpy(env, ImPlot::GetPlotMousePos(yAxis), dst);
    */

    /**
     * Returns the current plot axis range. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotLimits getPlotLimits() {
        final ImPlotLimits dst = new ImPlotLimits();
        nGetPlotLimits(dst);
        return dst;
    }

    /**
     * Returns the current plot axis range. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void getPlotLimits(final ImPlotLimits dst) {
        nGetPlotLimits(dst);
    }

    /**
     * Returns the current plot axis range. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static ImPlotLimits getPlotLimits(final int yAxis) {
        final ImPlotLimits dst = new ImPlotLimits();
        nGetPlotLimits(dst, yAxis);
        return dst;
    }

    /**
     * Returns the current plot axis range. A negative yAxis uses the current value of SetPlotYAxis (ImPlotYAxis_1 initially).
     */
    public static void getPlotLimits(final ImPlotLimits dst, final int yAxis) {
        nGetPlotLimits(dst, yAxis);
    }

    private static native void nGetPlotLimits(ImPlotLimits dst); /*
        Jni::ImPlotLimitsCpy(env, ImPlot::GetPlotLimits(), dst);
    */

    private static native void nGetPlotLimits(ImPlotLimits dst, int yAxis); /*
        Jni::ImPlotLimitsCpy(env, ImPlot::GetPlotLimits(yAxis), dst);
    */

    /**
     * Returns true if the current plot is being box selected.
     */
    public static boolean isPlotSelected() {
        return nIsPlotSelected();
    }

    private static native boolean nIsPlotSelected(); /*
        return ImPlot::IsPlotSelected();
    */

    /**
     * Returns the current plot box selection bounds.
     */
    public static ImPlotLimits getPlotSelection() {
        final ImPlotLimits dst = new ImPlotLimits();
        nGetPlotSelection(dst);
        return dst;
    }

    /**
     * Returns the current plot box selection bounds.
     */
    public static void getPlotSelection(final ImPlotLimits dst) {
        nGetPlotSelection(dst);
    }

    /**
     * Returns the current plot box selection bounds.
     */
    public static ImPlotLimits getPlotSelection(final int yAxis) {
        final ImPlotLimits dst = new ImPlotLimits();
        nGetPlotSelection(dst, yAxis);
        return dst;
    }

    /**
     * Returns the current plot box selection bounds.
     */
    public static void getPlotSelection(final ImPlotLimits dst, final int yAxis) {
        nGetPlotSelection(dst, yAxis);
    }

    private static native void nGetPlotSelection(ImPlotLimits dst); /*
        Jni::ImPlotLimitsCpy(env, ImPlot::GetPlotSelection(), dst);
    */

    private static native void nGetPlotSelection(ImPlotLimits dst, int yAxis); /*
        Jni::ImPlotLimitsCpy(env, ImPlot::GetPlotSelection(yAxis), dst);
    */

    /**
     * Returns true if the current plot is being queried or has an active query. Query must be enabled with ImPlotFlags_Query.
     */
    public static boolean isPlotQueried() {
        return nIsPlotQueried();
    }

    private static native boolean nIsPlotQueried(); /*
        return ImPlot::IsPlotQueried();
    */

    /**
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static ImPlotLimits getPlotQuery() {
        final ImPlotLimits dst = new ImPlotLimits();
        nGetPlotQuery(dst);
        return dst;
    }

    /**
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void getPlotQuery(final ImPlotLimits dst) {
        nGetPlotQuery(dst);
    }

    /**
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static ImPlotLimits getPlotQuery(final int yAxis) {
        final ImPlotLimits dst = new ImPlotLimits();
        nGetPlotQuery(dst, yAxis);
        return dst;
    }

    /**
     * Returns the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void getPlotQuery(final ImPlotLimits dst, final int yAxis) {
        nGetPlotQuery(dst, yAxis);
    }

    private static native void nGetPlotQuery(ImPlotLimits dst); /*
        Jni::ImPlotLimitsCpy(env, ImPlot::GetPlotQuery(), dst);
    */

    private static native void nGetPlotQuery(ImPlotLimits dst, int yAxis); /*
        Jni::ImPlotLimitsCpy(env, ImPlot::GetPlotQuery(yAxis), dst);
    */

    /**
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void setPlotQuery(final ImPlotLimits query) {
        nSetPlotQuery(query.x.min, query.y.min, query.x.max, query.y.max);
    }

    /**
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void setPlotQuery(final double queryMinX, final double queryMinY, final double queryMaxX, final double queryMaxY) {
        nSetPlotQuery(queryMinX, queryMinY, queryMaxX, queryMaxY);
    }

    /**
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void setPlotQuery(final ImPlotLimits query, final int yAxis) {
        nSetPlotQuery(query.x.min, query.y.min, query.x.max, query.y.max, yAxis);
    }

    /**
     * Set the current plot query bounds. Query must be enabled with ImPlotFlags_Query.
     */
    public static void setPlotQuery(final double queryMinX, final double queryMinY, final double queryMaxX, final double queryMaxY, final int yAxis) {
        nSetPlotQuery(queryMinX, queryMinY, queryMaxX, queryMaxY, yAxis);
    }

    private static native void nSetPlotQuery(double queryMinX, double queryMinY, double queryMaxX, double queryMaxY); /*
        ImPlot::SetPlotQuery(ImPlotLimits(queryMinX, queryMinY, queryMaxX, queryMaxY));
    */

    private static native void nSetPlotQuery(double queryMinX, double queryMinY, double queryMaxX, double queryMaxY, int yAxis); /*
        ImPlot::SetPlotQuery(ImPlotLimits(queryMinX, queryMinY, queryMaxX, queryMaxY), yAxis);
    */

    /**
     * Returns true if the bounding frame of a subplot is hovered
     */
    public static boolean isSubplotsHovered() {
        return nIsSubplotsHovered();
    }

    private static native boolean nIsSubplotsHovered(); /*
        return ImPlot::IsSubplotsHovered();
    */

    //-----------------------------------------------------------------------------
    // Aligned Plots
    //-----------------------------------------------------------------------------

    // Consider using Begin/EndSubplots first. They are more feature rich and
    // accomplish the same behaviour by default. The functions below offer lower
    // level control of plot alignment.

    /**
     * Align axis padding over multiple plots in a single row or column. If this function returns true, EndAlignedPlots() must be called. #group_id must be unique.
     */
    public static boolean beginAlignedPlots(final String groupId) {
        return nBeginAlignedPlots(groupId);
    }

    /**
     * Align axis padding over multiple plots in a single row or column. If this function returns true, EndAlignedPlots() must be called. #group_id must be unique.
     */
    public static boolean beginAlignedPlots(final String groupId, final int orientation) {
        return nBeginAlignedPlots(groupId, orientation);
    }

    private static native boolean nBeginAlignedPlots(String obj_groupId); /*MANUAL
        auto groupId = obj_groupId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_groupId, JNI_FALSE);
        auto _result = ImPlot::BeginAlignedPlots(groupId);
        if (groupId != NULL) env->ReleaseStringUTFChars(obj_groupId, groupId);
        return _result;
    */

    private static native boolean nBeginAlignedPlots(String obj_groupId, int orientation); /*MANUAL
        auto groupId = obj_groupId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_groupId, JNI_FALSE);
        auto _result = ImPlot::BeginAlignedPlots(groupId, orientation);
        if (groupId != NULL) env->ReleaseStringUTFChars(obj_groupId, groupId);
        return _result;
    */

    /**
     * Only call EndAlignedPlots() if BeginAlignedPlots() returns true!
     */
    public static void endAlignedPlots() {
        nEndAlignedPlots();
    }

    private static native void nEndAlignedPlots(); /*
        ImPlot::EndAlignedPlots();
    */

    //-----------------------------------------------------------------------------
    // Plot Tools
    //-----------------------------------------------------------------------------

    /**
     * Shows an annotation callout at a chosen point.
     */
    public static void annotate(final double x, final double y, final ImVec2 pixOffset, final String fmt) {
        nAnnotate(x, y, pixOffset.x, pixOffset.y, fmt);
    }

    /**
     * Shows an annotation callout at a chosen point.
     */
    public static void annotate(final double x, final double y, final float pixOffsetX, final float pixOffsetY, final String fmt) {
        nAnnotate(x, y, pixOffsetX, pixOffsetY, fmt);
    }

    private static native void nAnnotate(double x, double y, float pixOffsetX, float pixOffsetY, String fmt); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImVec2 pixOffset = ImVec2(pixOffsetX, pixOffsetY);
        ImPlot::Annotate(x, y, pixOffset, fmt, NULL);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Shows an annotation callout at a chosen point.
     */
    public static void annotate(final double x, final double y, final ImVec2 pixOffset, final ImVec4 color, final String fmt) {
        nAnnotate(x, y, pixOffset.x, pixOffset.y, color.x, color.y, color.z, color.w, fmt);
    }

    /**
     * Shows an annotation callout at a chosen point.
     */
    public static void annotate(final double x, final double y, final float pixOffsetX, final float pixOffsetY, final float colorX, final float colorY, final float colorZ, final float colorW, final String fmt) {
        nAnnotate(x, y, pixOffsetX, pixOffsetY, colorX, colorY, colorZ, colorW, fmt);
    }

    private static native void nAnnotate(double x, double y, float pixOffsetX, float pixOffsetY, float colorX, float colorY, float colorZ, float colorW, String fmt); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImVec2 pixOffset = ImVec2(pixOffsetX, pixOffsetY);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        ImPlot::Annotate(x, y, pixOffset, color, fmt, NULL);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Same as above, but the annotation will always be clamped to stay inside the plot area.
     */
    public static void annotateClamped(final double x, final double y, final ImVec2 pixOffset, final String fmt) {
        nAnnotateClamped(x, y, pixOffset.x, pixOffset.y, fmt);
    }

    /**
     * Same as above, but the annotation will always be clamped to stay inside the plot area.
     */
    public static void annotateClamped(final double x, final double y, final float pixOffsetX, final float pixOffsetY, final String fmt) {
        nAnnotateClamped(x, y, pixOffsetX, pixOffsetY, fmt);
    }

    private static native void nAnnotateClamped(double x, double y, float pixOffsetX, float pixOffsetY, String fmt); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImVec2 pixOffset = ImVec2(pixOffsetX, pixOffsetY);
        ImPlot::AnnotateClamped(x, y, pixOffset, fmt, NULL);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Same as above, but the annotation will always be clamped to stay inside the plot area.
     */
    public static void annotateClamped(final double x, final double y, final ImVec2 pixOffset, final ImVec4 color, final String fmt) {
        nAnnotateClamped(x, y, pixOffset.x, pixOffset.y, color.x, color.y, color.z, color.w, fmt);
    }

    /**
     * Same as above, but the annotation will always be clamped to stay inside the plot area.
     */
    public static void annotateClamped(final double x, final double y, final float pixOffsetX, final float pixOffsetY, final float colorX, final float colorY, final float colorZ, final float colorW, final String fmt) {
        nAnnotateClamped(x, y, pixOffsetX, pixOffsetY, colorX, colorY, colorZ, colorW, fmt);
    }

    private static native void nAnnotateClamped(double x, double y, float pixOffsetX, float pixOffsetY, float colorX, float colorY, float colorZ, float colorW, String fmt); /*MANUAL
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImVec2 pixOffset = ImVec2(pixOffsetX, pixOffsetY);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        ImPlot::AnnotateClamped(x, y, pixOffset, color, fmt, NULL);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final boolean showLabel) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, showLabel);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final boolean showLabel, final ImVec4 color) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, showLabel, color.x, color.y, color.z, color.w);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final boolean showLabel, final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, showLabel, colorX, colorY, colorZ, colorW);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final boolean showLabel, final ImVec4 color, final float thickness) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, showLabel, color.x, color.y, color.z, color.w, thickness);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final boolean showLabel, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, showLabel, colorX, colorY, colorZ, colorW, thickness);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final ImVec4 color, final float thickness) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, color.x, color.y, color.z, color.w, thickness);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, colorX, colorY, colorZ, colorW, thickness);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final float thickness) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, thickness);
    }

    /**
     * Shows a draggable vertical guide line at an x-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineX(final String id, final ImDouble xValue, final boolean showLabel, final float thickness) {
        return nDragLineX(id, xValue != null ? xValue.getData() : null, showLabel, thickness);
    }

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL));
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue, boolean showLabel); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL), showLabel);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue, boolean showLabel, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL), showLabel, color);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue, boolean showLabel, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL), showLabel, color, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL), true, color, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL), true, IMPLOT_AUTO_COL, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineX(String obj_id, double[] obj_xValue, boolean showLabel, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto xValue = obj_xValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_xValue, JNI_FALSE);
        auto _result = ImPlot::DragLineX(id, (xValue != NULL ? &xValue[0] : NULL), showLabel, IMPLOT_AUTO_COL, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (xValue != NULL) env->ReleasePrimitiveArrayCritical(obj_xValue, xValue, JNI_FALSE);
        return _result;
    */

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final boolean showLabel) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, showLabel);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final boolean showLabel, final ImVec4 color) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, showLabel, color.x, color.y, color.z, color.w);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final boolean showLabel, final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, showLabel, colorX, colorY, colorZ, colorW);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final boolean showLabel, final ImVec4 color, final float thickness) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, showLabel, color.x, color.y, color.z, color.w, thickness);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final boolean showLabel, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, showLabel, colorX, colorY, colorZ, colorW, thickness);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final ImVec4 color, final float thickness) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, color.x, color.y, color.z, color.w, thickness);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, colorX, colorY, colorZ, colorW, thickness);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final float thickness) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, thickness);
    }

    /**
     * Shows a draggable horizontal guide line at a y-value. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragLineY(final String id, final ImDouble yValue, final boolean showLabel, final float thickness) {
        return nDragLineY(id, yValue != null ? yValue.getData() : null, showLabel, thickness);
    }

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL));
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue, boolean showLabel); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL), showLabel);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue, boolean showLabel, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL), showLabel, color);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue, boolean showLabel, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL), showLabel, color, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL), true, color, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL), true, IMPLOT_AUTO_COL, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragLineY(String obj_id, double[] obj_yValue, boolean showLabel, float thickness); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto yValue = obj_yValue == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_yValue, JNI_FALSE);
        auto _result = ImPlot::DragLineY(id, (yValue != NULL ? &yValue[0] : NULL), showLabel, IMPLOT_AUTO_COL, thickness);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (yValue != NULL) env->ReleasePrimitiveArrayCritical(obj_yValue, yValue, JNI_FALSE);
        return _result;
    */

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final boolean showLabel) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, showLabel);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final boolean showLabel, final ImVec4 color) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, showLabel, color.x, color.y, color.z, color.w);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final boolean showLabel, final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, showLabel, colorX, colorY, colorZ, colorW);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final boolean showLabel, final ImVec4 color, final float radius) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, showLabel, color.x, color.y, color.z, color.w, radius);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final boolean showLabel, final float colorX, final float colorY, final float colorZ, final float colorW, final float radius) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, showLabel, colorX, colorY, colorZ, colorW, radius);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final ImVec4 color, final float radius) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, color.x, color.y, color.z, color.w, radius);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final float colorX, final float colorY, final float colorZ, final float colorW, final float radius) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, colorX, colorY, colorZ, colorW, radius);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final float radius) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, radius);
    }

    /**
     * Shows a draggable point at x,y. #col defaults to ImGuiCol_Text.
     */
    public static boolean dragPoint(final String id, final ImDouble x, final ImDouble y, final boolean showLabel, final float radius) {
        return nDragPoint(id, x != null ? x.getData() : null, y != null ? y.getData() : null, showLabel, radius);
    }

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL));
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y, boolean showLabel); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL), showLabel);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y, boolean showLabel, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL), showLabel, color);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y, boolean showLabel, float colorX, float colorY, float colorZ, float colorW, float radius); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL), showLabel, color, radius);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y, float colorX, float colorY, float colorZ, float colorW, float radius); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL), true, color, radius);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y, float radius); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL), true, IMPLOT_AUTO_COL, radius);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragPoint(String obj_id, double[] obj_x, double[] obj_y, boolean showLabel, float radius); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto x = obj_x == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_x, JNI_FALSE);
        auto y = obj_y == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_y, JNI_FALSE);
        auto _result = ImPlot::DragPoint(id, (x != NULL ? &x[0] : NULL), (y != NULL ? &y[0] : NULL), showLabel, IMPLOT_AUTO_COL, radius);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        if (x != NULL) env->ReleasePrimitiveArrayCritical(obj_x, x, JNI_FALSE);
        if (y != NULL) env->ReleasePrimitiveArrayCritical(obj_y, y, JNI_FALSE);
        return _result;
    */

    //-----------------------------------------------------------------------------
    // Legend Utils and Tools
    //-----------------------------------------------------------------------------

    /**
     * Set the location of the current plot's legend
     */
    public static void setLegendLocation(final int location) {
        nSetLegendLocation(location);
    }

    /**
     * Set the location of the current plot's legend
     */
    public static void setLegendLocation(final int location, final int orientation) {
        nSetLegendLocation(location, orientation);
    }

    /**
     * Set the location of the current plot's legend
     */
    public static void setLegendLocation(final int location, final int orientation, final boolean outside) {
        nSetLegendLocation(location, orientation, outside);
    }

    /**
     * Set the location of the current plot's legend
     */
    public static void setLegendLocation(final int location, final boolean outside) {
        nSetLegendLocation(location, outside);
    }

    private static native void nSetLegendLocation(int location); /*
        ImPlot::SetLegendLocation(location);
    */

    private static native void nSetLegendLocation(int location, int orientation); /*
        ImPlot::SetLegendLocation(location, orientation);
    */

    private static native void nSetLegendLocation(int location, int orientation, boolean outside); /*
        ImPlot::SetLegendLocation(location, orientation, outside);
    */

    private static native void nSetLegendLocation(int location, boolean outside); /*
        ImPlot::SetLegendLocation(location, ImPlotOrientation_Vertical, outside);
    */

    /**
     * Set the location of the current plot's mouse position text
     */
    public static void setMousePosLocation(final int location) {
        nSetMousePosLocation(location);
    }

    private static native void nSetMousePosLocation(int location); /*
        ImPlot::SetMousePosLocation(location);
    */

    /**
     * Returns true if a plot item legend entry is hovered.
     */
    public static boolean isLegendEntryHovered(final String labelId) {
        return nIsLegendEntryHovered(labelId);
    }

    private static native boolean nIsLegendEntryHovered(String obj_labelId); /*MANUAL
        auto labelId = obj_labelId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelId, JNI_FALSE);
        auto _result = ImPlot::IsLegendEntryHovered(labelId);
        if (labelId != NULL) env->ReleaseStringUTFChars(obj_labelId, labelId);
        return _result;
    */

    /**
     * Begin a popup for a legend entry.
     */
    public static boolean beginLegendPopup(final String labelID) {
        return nBeginLegendPopup(labelID);
    }

    /**
     * Begin a popup for a legend entry.
     */
    public static boolean beginLegendPopup(final String labelID, final int mouseButton) {
        return nBeginLegendPopup(labelID, mouseButton);
    }

    private static native boolean nBeginLegendPopup(String obj_labelID); /*MANUAL
        auto labelID = obj_labelID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelID, JNI_FALSE);
        auto _result = ImPlot::BeginLegendPopup(labelID);
        if (labelID != NULL) env->ReleaseStringUTFChars(obj_labelID, labelID);
        return _result;
    */

    private static native boolean nBeginLegendPopup(String obj_labelID, int mouseButton); /*MANUAL
        auto labelID = obj_labelID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelID, JNI_FALSE);
        auto _result = ImPlot::BeginLegendPopup(labelID, mouseButton);
        if (labelID != NULL) env->ReleaseStringUTFChars(obj_labelID, labelID);
        return _result;
    */

    /**
     * End a popup for a legend entry.
     */
    public static void endLegendPopup() {
        nEndLegendPopup();
    }

    private static native void nEndLegendPopup(); /*
        ImPlot::EndLegendPopup();
    */

    //-----------------------------------------------------------------------------
    // Drag and Drop Utils
    //-----------------------------------------------------------------------------

    /**
     * Turns the current plot's plotting area into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static boolean beginDragDropTarget() {
        return nBeginDragDropTarget();
    }

    private static native boolean nBeginDragDropTarget(); /*
        return ImPlot::BeginDragDropTarget();
    */

    /**
     * Turns the current plot's X-axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static boolean beginDragDropTargetX() {
        return nBeginDragDropTargetX();
    }

    private static native boolean nBeginDragDropTargetX(); /*
        return ImPlot::BeginDragDropTargetX();
    */

    /**
     * Turns the current plot's Y-Axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static boolean beginDragDropTargetY() {
        return nBeginDragDropTargetY();
    }

    /**
     * Turns the current plot's Y-Axis into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static boolean beginDragDropTargetY(final int yAxis) {
        return nBeginDragDropTargetY(yAxis);
    }

    private static native boolean nBeginDragDropTargetY(); /*
        return ImPlot::BeginDragDropTargetY();
    */

    private static native boolean nBeginDragDropTargetY(int yAxis); /*
        return ImPlot::BeginDragDropTargetY(yAxis);
    */

    /**
     * Turns the current plot's legend into a drag and drop target. Don't forget to call EndDragDropTarget!
     */
    public static boolean beginDragDropTargetLegend() {
        return nBeginDragDropTargetLegend();
    }

    private static native boolean nBeginDragDropTargetLegend(); /*
        return ImPlot::BeginDragDropTargetLegend();
    */

    /**
     * Ends a drag and drop target.
     */
    public static void endDragDropTarget() {
        nEndDragDropTarget();
    }

    private static native void nEndDragDropTarget(); /*
        ImPlot::EndDragDropTarget();
    */

    // NB: By default, plot and axes drag and drop *sources* require holding the Ctrl modifier to initiate the drag.
    // You can change the modifier if desired. If ImGuiKeyModFlags_None is provided, the axes will be locked from panning.

    /**
     * Turns the current plot's plotting area into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSource() {
        return nBeginDragDropSource();
    }

    /**
     * Turns the current plot's plotting area into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSource(final int keyMods) {
        return nBeginDragDropSource(keyMods);
    }

    /**
     * Turns the current plot's plotting area into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSource(final int keyMods, final int dragDropFlags) {
        return nBeginDragDropSource(keyMods, dragDropFlags);
    }

    private static native boolean nBeginDragDropSource(); /*
        return ImPlot::BeginDragDropSource();
    */

    private static native boolean nBeginDragDropSource(int keyMods); /*
        return ImPlot::BeginDragDropSource(keyMods);
    */

    private static native boolean nBeginDragDropSource(int keyMods, int dragDropFlags); /*
        return ImPlot::BeginDragDropSource(keyMods, dragDropFlags);
    */

    /**
     * Turns the current plot's X-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceX() {
        return nBeginDragDropSourceX();
    }

    /**
     * Turns the current plot's X-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceX(final int keyMods) {
        return nBeginDragDropSourceX(keyMods);
    }

    /**
     * Turns the current plot's X-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceX(final int keyMods, final int dragDropFlags) {
        return nBeginDragDropSourceX(keyMods, dragDropFlags);
    }

    private static native boolean nBeginDragDropSourceX(); /*
        return ImPlot::BeginDragDropSourceX();
    */

    private static native boolean nBeginDragDropSourceX(int keyMods); /*
        return ImPlot::BeginDragDropSourceX(keyMods);
    */

    private static native boolean nBeginDragDropSourceX(int keyMods, int dragDropFlags); /*
        return ImPlot::BeginDragDropSourceX(keyMods, dragDropFlags);
    */

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceY() {
        return nBeginDragDropSourceY();
    }

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceY(final int yAxis) {
        return nBeginDragDropSourceY(yAxis);
    }

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceY(final int yAxis, final int keyMods) {
        return nBeginDragDropSourceY(yAxis, keyMods);
    }

    /**
     * Turns the current plot's Y-axis into a drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceY(final int yAxis, final int keyMods, final int dragDropFlags) {
        return nBeginDragDropSourceY(yAxis, keyMods, dragDropFlags);
    }

    private static native boolean nBeginDragDropSourceY(); /*
        return ImPlot::BeginDragDropSourceY();
    */

    private static native boolean nBeginDragDropSourceY(int yAxis); /*
        return ImPlot::BeginDragDropSourceY(yAxis);
    */

    private static native boolean nBeginDragDropSourceY(int yAxis, int keyMods); /*
        return ImPlot::BeginDragDropSourceY(yAxis, keyMods);
    */

    private static native boolean nBeginDragDropSourceY(int yAxis, int keyMods, int dragDropFlags); /*
        return ImPlot::BeginDragDropSourceY(yAxis, keyMods, dragDropFlags);
    */

    /**
     * Turns an item in the current plot's legend into drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceItem(final String labelID) {
        return nBeginDragDropSourceItem(labelID);
    }

    /**
     * Turns an item in the current plot's legend into drag and drop source. Don't forget to call EndDragDropSource!
     */
    public static boolean beginDragDropSourceItem(final String labelID, final int dragDropFlags) {
        return nBeginDragDropSourceItem(labelID, dragDropFlags);
    }

    private static native boolean nBeginDragDropSourceItem(String obj_labelID); /*MANUAL
        auto labelID = obj_labelID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelID, JNI_FALSE);
        auto _result = ImPlot::BeginDragDropSourceItem(labelID);
        if (labelID != NULL) env->ReleaseStringUTFChars(obj_labelID, labelID);
        return _result;
    */

    private static native boolean nBeginDragDropSourceItem(String obj_labelID, int dragDropFlags); /*MANUAL
        auto labelID = obj_labelID == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelID, JNI_FALSE);
        auto _result = ImPlot::BeginDragDropSourceItem(labelID, dragDropFlags);
        if (labelID != NULL) env->ReleaseStringUTFChars(obj_labelID, labelID);
        return _result;
    */

    /**
     * Ends a drag and drop source.
     */
    public static void endDragDropSource() {
        nEndDragDropSource();
    }

    private static native void nEndDragDropSource(); /*
        ImPlot::EndDragDropSource();
    */

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

    private static final ImPlotStyle _GETSTYLE_1 = new ImPlotStyle(0);

    /**
     * Provides access to plot style structure for permanant modifications to colors, sizes, etc.
     */
    public static ImPlotStyle getStyle() {
        _GETSTYLE_1.ptr = nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle(); /*
        return (intptr_t)&ImPlot::GetStyle();
    */

    /**
     * Style plot colors for current ImGui style (default).
     */
    public static void styleColorsAuto() {
        nStyleColorsAuto();
    }

    /**
     * Style plot colors for current ImGui style (default).
     */
    public static void styleColorsAuto(final ImPlotStyle dst) {
        nStyleColorsAuto(dst.ptr);
    }

    private static native void nStyleColorsAuto(); /*
        ImPlot::StyleColorsAuto();
    */

    private static native void nStyleColorsAuto(long dst); /*
        ImPlot::StyleColorsAuto(reinterpret_cast<ImPlotStyle*>(dst));
    */

    /**
     * Style plot colors for ImGui "Classic".
     */
    public static void styleColorsClassic() {
        nStyleColorsClassic();
    }

    /**
     * Style plot colors for ImGui "Classic".
     */
    public static void styleColorsClassic(final ImPlotStyle dst) {
        nStyleColorsClassic(dst.ptr);
    }

    private static native void nStyleColorsClassic(); /*
        ImPlot::StyleColorsClassic();
    */

    private static native void nStyleColorsClassic(long dst); /*
        ImPlot::StyleColorsClassic(reinterpret_cast<ImPlotStyle*>(dst));
    */

    /**
     * Style plot colors for ImGui "Dark".
     */
    public static void styleColorsDark() {
        nStyleColorsDark();
    }

    /**
     * Style plot colors for ImGui "Dark".
     */
    public static void styleColorsDark(final ImPlotStyle dst) {
        nStyleColorsDark(dst.ptr);
    }

    private static native void nStyleColorsDark(); /*
        ImPlot::StyleColorsDark();
    */

    private static native void nStyleColorsDark(long dst); /*
        ImPlot::StyleColorsDark(reinterpret_cast<ImPlotStyle*>(dst));
    */

    /**
     * Style plot colors for ImGui "Light".
     */
    public static void styleColorsLight() {
        nStyleColorsLight();
    }

    /**
     * Style plot colors for ImGui "Light".
     */
    public static void styleColorsLight(final ImPlotStyle dst) {
        nStyleColorsLight(dst.ptr);
    }

    private static native void nStyleColorsLight(); /*
        ImPlot::StyleColorsLight();
    */

    private static native void nStyleColorsLight(long dst); /*
        ImPlot::StyleColorsLight(reinterpret_cast<ImPlotStyle*>(dst));
    */

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
    public static void pushStyleColor(final int idx, final int col) {
        nPushStyleColor(idx, col);
    }

    private static native void nPushStyleColor(int idx, int col); /*
        ImPlot::PushStyleColor(idx, static_cast<ImU32>(col));
    */

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    public static void pushStyleColor(final int idx, final ImVec4 col) {
        nPushStyleColor(idx, col.x, col.y, col.z, col.w);
    }

    /**
     * Temporarily modify a style color. Don't forget to call PopStyleColor!
     */
    public static void pushStyleColor(final int idx, final float colX, final float colY, final float colZ, final float colW) {
        nPushStyleColor(idx, colX, colY, colZ, colW);
    }

    private static native void nPushStyleColor(int idx, float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::PushStyleColor(idx, col);
    */

    public static void popStyleColor() {
        nPopStyleColor();
    }

    public static void popStyleColor(final int count) {
        nPopStyleColor(count);
    }

    private static native void nPopStyleColor(); /*
        ImPlot::PopStyleColor();
    */

    private static native void nPopStyleColor(int count); /*
        ImPlot::PopStyleColor(count);
    */

    /**
     * Temporarily modify a style variable of float type. Don't forget to call PopStyleVar!
     */
    public static void pushStyleVar(final int idx, final float val) {
        nPushStyleVar(idx, val);
    }

    private static native void nPushStyleVar(int idx, float val); /*
        ImPlot::PushStyleVar(idx, static_cast<float>(val));
    */

    /**
     * Temporarily modify a style variable of int type. Don't forget to call PopStyleVar!
     */
    public static void pushStyleVar(final int idx, final int val) {
        nPushStyleVar(idx, val);
    }

    private static native void nPushStyleVar(int idx, int val); /*
        ImPlot::PushStyleVar(idx, static_cast<int>(val));
    */

    /**
     * Temporarily modify a style variable of ImVec2 type. Don't forget to call PopStyleVar!
     */
    public static void pushStyleVar(final int idx, final ImVec2 val) {
        nPushStyleVar(idx, val.x, val.y);
    }

    /**
     * Temporarily modify a style variable of ImVec2 type. Don't forget to call PopStyleVar!
     */
    public static void pushStyleVar(final int idx, final float valX, final float valY) {
        nPushStyleVar(idx, valX, valY);
    }

    private static native void nPushStyleVar(int idx, float valX, float valY); /*MANUAL
        ImVec2 val = ImVec2(valX, valY);
        ImPlot::PushStyleVar(idx, val);
    */
    /**
     * Undo temporary style variable modification(s). Undo multiple pushes at once by increasing count.
     */
    public static void popStyleVar() {
        nPopStyleVar();
    }

    /**
     * Undo temporary style variable modification(s). Undo multiple pushes at once by increasing count.
     */
    public static void popStyleVar(final int count) {
        nPopStyleVar(count);
    }

    private static native void nPopStyleVar(); /*
        ImPlot::PopStyleVar();
    */

    private static native void nPopStyleVar(int count); /*
        ImPlot::PopStyleVar(count);
    */

    /**
     * Set the line color and weight for the next item only.
     */
    public static void setNextLineStyle() {
        nSetNextLineStyle();
    }

    /**
     * Set the line color and weight for the next item only.
     */
    public static void setNextLineStyle(final ImVec4 col) {
        nSetNextLineStyle(col.x, col.y, col.z, col.w);
    }

    /**
     * Set the line color and weight for the next item only.
     */
    public static void setNextLineStyle(final float colX, final float colY, final float colZ, final float colW) {
        nSetNextLineStyle(colX, colY, colZ, colW);
    }

    /**
     * Set the line color and weight for the next item only.
     */
    public static void setNextLineStyle(final ImVec4 col, final float weight) {
        nSetNextLineStyle(col.x, col.y, col.z, col.w, weight);
    }

    /**
     * Set the line color and weight for the next item only.
     */
    public static void setNextLineStyle(final float colX, final float colY, final float colZ, final float colW, final float weight) {
        nSetNextLineStyle(colX, colY, colZ, colW, weight);
    }

    /**
     * Set the line color and weight for the next item only.
     */
    public static void setNextLineStyle(final float weight) {
        nSetNextLineStyle(weight);
    }

    private static native void nSetNextLineStyle(); /*
        ImPlot::SetNextLineStyle();
    */

    private static native void nSetNextLineStyle(float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextLineStyle(col);
    */

    private static native void nSetNextLineStyle(float colX, float colY, float colZ, float colW, float weight); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextLineStyle(col, weight);
    */

    private static native void nSetNextLineStyle(float weight); /*
        ImPlot::SetNextLineStyle(IMPLOT_AUTO_COL, weight);
    */

    /**
     * Set the fill color for the next item only.
     */
    public static void setNextFillStyle() {
        nSetNextFillStyle();
    }

    /**
     * Set the fill color for the next item only.
     */
    public static void setNextFillStyle(final ImVec4 col) {
        nSetNextFillStyle(col.x, col.y, col.z, col.w);
    }

    /**
     * Set the fill color for the next item only.
     */
    public static void setNextFillStyle(final float colX, final float colY, final float colZ, final float colW) {
        nSetNextFillStyle(colX, colY, colZ, colW);
    }

    /**
     * Set the fill color for the next item only.
     */
    public static void setNextFillStyle(final ImVec4 col, final float alphaMod) {
        nSetNextFillStyle(col.x, col.y, col.z, col.w, alphaMod);
    }

    /**
     * Set the fill color for the next item only.
     */
    public static void setNextFillStyle(final float colX, final float colY, final float colZ, final float colW, final float alphaMod) {
        nSetNextFillStyle(colX, colY, colZ, colW, alphaMod);
    }

    /**
     * Set the fill color for the next item only.
     */
    public static void setNextFillStyle(final float alphaMod) {
        nSetNextFillStyle(alphaMod);
    }

    private static native void nSetNextFillStyle(); /*
        ImPlot::SetNextFillStyle();
    */

    private static native void nSetNextFillStyle(float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextFillStyle(col);
    */

    private static native void nSetNextFillStyle(float colX, float colY, float colZ, float colW, float alphaMod); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextFillStyle(col, alphaMod);
    */

    private static native void nSetNextFillStyle(float alphaMod); /*
        ImPlot::SetNextFillStyle(IMPLOT_AUTO_COL, alphaMod);
    */

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle() {
        nSetNextMarkerStyle();
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker) {
        nSetNextMarkerStyle(marker);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size) {
        nSetNextMarkerStyle(marker, size);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final ImVec4 fill) {
        nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final float fillX, final float fillY, final float fillZ, final float fillW) {
        nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final ImVec4 fill, final float weight) {
        nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w, weight);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final float fillX, final float fillY, final float fillZ, final float fillW, final float weight) {
        nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW, weight);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final ImVec4 fill, final float weight, final ImVec4 outline) {
        nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w, weight, outline.x, outline.y, outline.z, outline.w);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final float fillX, final float fillY, final float fillZ, final float fillW, final float weight, final float outlineX, final float outlineY, final float outlineZ, final float outlineW) {
        nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW, weight, outlineX, outlineY, outlineZ, outlineW);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final ImVec4 fill, final ImVec4 outline) {
        nSetNextMarkerStyle(marker, size, fill.x, fill.y, fill.z, fill.w, outline.x, outline.y, outline.z, outline.w);
    }

    /**
     * Set the marker style for the next item only.
     */
    public static void setNextMarkerStyle(final int marker, final float size, final float fillX, final float fillY, final float fillZ, final float fillW, final float outlineX, final float outlineY, final float outlineZ, final float outlineW) {
        nSetNextMarkerStyle(marker, size, fillX, fillY, fillZ, fillW, outlineX, outlineY, outlineZ, outlineW);
    }

    private static native void nSetNextMarkerStyle(); /*
        ImPlot::SetNextMarkerStyle();
    */

    private static native void nSetNextMarkerStyle(int marker); /*
        ImPlot::SetNextMarkerStyle(marker);
    */

    private static native void nSetNextMarkerStyle(int marker, float size); /*
        ImPlot::SetNextMarkerStyle(marker, size);
    */

    private static native void nSetNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW); /*MANUAL
        ImVec4 fill = ImVec4(fillX, fillY, fillZ, fillW);
        ImPlot::SetNextMarkerStyle(marker, size, fill);
    */

    private static native void nSetNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW, float weight); /*MANUAL
        ImVec4 fill = ImVec4(fillX, fillY, fillZ, fillW);
        ImPlot::SetNextMarkerStyle(marker, size, fill, weight);
    */

    private static native void nSetNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW, float weight, float outlineX, float outlineY, float outlineZ, float outlineW); /*MANUAL
        ImVec4 fill = ImVec4(fillX, fillY, fillZ, fillW);
        ImVec4 outline = ImVec4(outlineX, outlineY, outlineZ, outlineW);
        ImPlot::SetNextMarkerStyle(marker, size, fill, weight, outline);
    */

    private static native void nSetNextMarkerStyle(int marker, float size, float fillX, float fillY, float fillZ, float fillW, float outlineX, float outlineY, float outlineZ, float outlineW); /*MANUAL
        ImVec4 fill = ImVec4(fillX, fillY, fillZ, fillW);
        ImVec4 outline = ImVec4(outlineX, outlineY, outlineZ, outlineW);
        ImPlot::SetNextMarkerStyle(marker, size, fill, IMPLOT_AUTO, outline);
    */

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle() {
        nSetNextErrorBarStyle();
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final ImVec4 col) {
        nSetNextErrorBarStyle(col.x, col.y, col.z, col.w);
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final float colX, final float colY, final float colZ, final float colW) {
        nSetNextErrorBarStyle(colX, colY, colZ, colW);
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final ImVec4 col, final float size) {
        nSetNextErrorBarStyle(col.x, col.y, col.z, col.w, size);
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final float colX, final float colY, final float colZ, final float colW, final float size) {
        nSetNextErrorBarStyle(colX, colY, colZ, colW, size);
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final ImVec4 col, final float size, final float weight) {
        nSetNextErrorBarStyle(col.x, col.y, col.z, col.w, size, weight);
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final float colX, final float colY, final float colZ, final float colW, final float size, final float weight) {
        nSetNextErrorBarStyle(colX, colY, colZ, colW, size, weight);
    }

    /**
     * Set the error bar style for the next item only.
     */
    public static void setNextErrorBarStyle(final float size, final float weight) {
        nSetNextErrorBarStyle(size, weight);
    }

    private static native void nSetNextErrorBarStyle(); /*
        ImPlot::SetNextErrorBarStyle();
    */

    private static native void nSetNextErrorBarStyle(float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextErrorBarStyle(col);
    */

    private static native void nSetNextErrorBarStyle(float colX, float colY, float colZ, float colW, float size); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextErrorBarStyle(col, size);
    */

    private static native void nSetNextErrorBarStyle(float colX, float colY, float colZ, float colW, float size, float weight); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::SetNextErrorBarStyle(col, size, weight);
    */

    private static native void nSetNextErrorBarStyle(float size, float weight); /*
        ImPlot::SetNextErrorBarStyle(IMPLOT_AUTO_COL, size, weight);
    */

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static ImVec4 getLastItemColor() {
        final ImVec4 dst = new ImVec4();
        nGetLastItemColor(dst);
        return dst;
    }

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static float getLastItemColorX() {
        return nGetLastItemColorX();
    }

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static float getLastItemColorY() {
        return nGetLastItemColorY();
    }

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static float getLastItemColorZ() {
        return nGetLastItemColorZ();
    }

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static float getLastItemColorW() {
        return nGetLastItemColorW();
    }

    /**
     * Gets the last item primary color (i.e. its legend icon color)
     */
    public static void getLastItemColor(final ImVec4 dst) {
        nGetLastItemColor(dst);
    }

    private static native void nGetLastItemColor(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, ImPlot::GetLastItemColor(), dst);
    */

    private static native float nGetLastItemColorX(); /*
        return ImPlot::GetLastItemColor().x;
    */

    private static native float nGetLastItemColorY(); /*
        return ImPlot::GetLastItemColor().y;
    */

    private static native float nGetLastItemColorZ(); /*
        return ImPlot::GetLastItemColor().z;
    */

    private static native float nGetLastItemColorW(); /*
        return ImPlot::GetLastItemColor().w;
    */

    /**
     * Returns the string name for an ImPlotCol.
     */
    public static String getStyleColorName(final int idx) {
        return nGetStyleColorName(idx);
    }

    private static native String nGetStyleColorName(int idx); /*
        return env->NewStringUTF(ImPlot::GetStyleColorName(idx));
    */

    /**
     * Returns the null terminated string name for an ImPlotMarker.
     */
    public static String getMarkerName(final int idx) {
        return nGetMarkerName(idx);
    }

    private static native String nGetMarkerName(int idx); /*
        return env->NewStringUTF(ImPlot::GetMarkerName(idx));
    */

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

    public static int addColormap(final String name, final ImVec4[] cols) {
        return nAddColormap(name, cols);
    }

    public static int addColormap(final String name, final ImVec4[] cols, final boolean qual) {
        return nAddColormap(name, cols, qual);
    }

    private static native int nAddColormap(String obj_name, ImVec4[] obj_cols); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        int colsLength = env->GetArrayLength(obj_cols);
        ImVec4 cols[colsLength];
        for (int i = 0; i < colsLength; i++) {
            jobject src = env->GetObjectArrayElement(obj_cols, i);
            ImVec4 dst;
            Jni::ImVec4Cpy(env, src, &dst);
            cols[i] = dst;
        };
        auto _result = ImPlot::AddColormap(name, cols, env->GetArrayLength(obj_cols));
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        return _result;
    */

    private static native int nAddColormap(String obj_name, ImVec4[] obj_cols, boolean qual); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        int colsLength = env->GetArrayLength(obj_cols);
        ImVec4 cols[colsLength];
        for (int i = 0; i < colsLength; i++) {
            jobject src = env->GetObjectArrayElement(obj_cols, i);
            ImVec4 dst;
            Jni::ImVec4Cpy(env, src, &dst);
            cols[i] = dst;
        };
        auto _result = ImPlot::AddColormap(name, cols, env->GetArrayLength(obj_cols), qual);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        return _result;
    */

    public static int addColormap(final String name, final int[] cols) {
        return nAddColormap(name, cols);
    }

    public static int addColormap(final String name, final int[] cols, final boolean qual) {
        return nAddColormap(name, cols, qual);
    }

    private static native int nAddColormap(String obj_name, int[] obj_cols); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto cols = obj_cols == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_cols, JNI_FALSE);
        auto _result = ImPlot::AddColormap(name, reinterpret_cast<ImU32*>(&cols[0]), env->GetArrayLength(obj_cols));
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        if (cols != NULL) env->ReleasePrimitiveArrayCritical(obj_cols, cols, JNI_FALSE);
        return _result;
    */

    private static native int nAddColormap(String obj_name, int[] obj_cols, boolean qual); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto cols = obj_cols == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_cols, JNI_FALSE);
        auto _result = ImPlot::AddColormap(name, reinterpret_cast<ImU32*>(&cols[0]), env->GetArrayLength(obj_cols), qual);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        if (cols != NULL) env->ReleasePrimitiveArrayCritical(obj_cols, cols, JNI_FALSE);
        return _result;
    */

    /**
     * Returns the number of available colormaps (i.e. the built-in + user-added count).
     */
    public static int getColormapCount() {
        return nGetColormapCount();
    }

    private static native int nGetColormapCount(); /*
        return ImPlot::GetColormapCount();
    */

    /**
     * Returns a string name for a colormap given an index.
     */
    public static String getColormapName(final int cmap) {
        return nGetColormapName(cmap);
    }

    private static native String nGetColormapName(int cmap); /*
        return env->NewStringUTF(ImPlot::GetColormapName(cmap));
    */

    /**
     * Returns an index number for a colormap given a valid string name. Returns -1 if the name is invalid.
     */
    public static int getColormapIndex(final String name) {
        return nGetColormapIndex(name);
    }

    private static native int nGetColormapIndex(String obj_name); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto _result = ImPlot::GetColormapIndex(name);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        return _result;
    */

    /**
     * Temporarily switch to one of the built-in (i.e. ImPlotColormap_XXX) or user-added colormaps (i.e. a return value of AddColormap). Don't forget to call PopColormap!
     */
    public static void pushColormap(final int cmap) {
        nPushColormap(cmap);
    }

    private static native void nPushColormap(int cmap); /*
        ImPlot::PushColormap(cmap);
    */

    /**
     * Push a colormap by string name. Use built-in names such as "Default", "Deep", "Jet", etc. or a string you provided to AddColormap. Don't forget to call PopColormap!
     */
    public static void pushColormap(final String name) {
        nPushColormap(name);
    }

    private static native void nPushColormap(String name); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImPlot::PushColormap(name);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    /**
     * Undo temporary colormap modification(s). Undo multiple pushes at once by increasing count.
     */
    public static void popColormap() {
        nPopColormap();
    }

    /**
     * Undo temporary colormap modification(s). Undo multiple pushes at once by increasing count.
     */
    public static void popColormap(final int count) {
        nPopColormap(count);
    }

    private static native void nPopColormap(); /*
        ImPlot::PopColormap();
    */

    private static native void nPopColormap(int count); /*
        ImPlot::PopColormap(count);
    */

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static ImVec4 nextColormapColor() {
        final ImVec4 dst = new ImVec4();
        nNextColormapColor(dst);
        return dst;
    }

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static float nextColormapColorX() {
        return nNextColormapColorX();
    }

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static float nextColormapColorY() {
        return nNextColormapColorY();
    }

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static float nextColormapColorZ() {
        return nNextColormapColorZ();
    }

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static float nextColormapColorW() {
        return nNextColormapColorW();
    }

    /**
     * Returns the next color from the current colormap and advances the colormap for the current plot.
     * Can also be used with no return value to skip colors if desired. You need to call this between Begin/EndPlot!
     */
    public static void nextColormapColor(final ImVec4 dst) {
        nNextColormapColor(dst);
    }

    private static native void nNextColormapColor(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, ImPlot::NextColormapColor(), dst);
    */

    private static native float nNextColormapColorX(); /*
        return ImPlot::NextColormapColor().x;
    */

    private static native float nNextColormapColorY(); /*
        return ImPlot::NextColormapColor().y;
    */

    private static native float nNextColormapColorZ(); /*
        return ImPlot::NextColormapColor().z;
    */

    private static native float nNextColormapColorW(); /*
        return ImPlot::NextColormapColor().w;
    */

    /**
     * Returns the size of a colormap.
     */
    public static int getColormapSize() {
        return nGetColormapSize();
    }

    /**
     * Returns the size of a colormap.
     */
    public static int getColormapSize(final int cmap) {
        return nGetColormapSize(cmap);
    }

    private static native int nGetColormapSize(); /*
        return ImPlot::GetColormapSize();
    */

    private static native int nGetColormapSize(int cmap); /*
        return ImPlot::GetColormapSize(cmap);
    */

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static ImVec4 getColormapColor(final int idx) {
        final ImVec4 dst = new ImVec4();
        nGetColormapColor(dst, idx);
        return dst;
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorX(final int idx) {
        return nGetColormapColorX(idx);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorY(final int idx) {
        return nGetColormapColorY(idx);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorZ(final int idx) {
        return nGetColormapColorZ(idx);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorW(final int idx) {
        return nGetColormapColorW(idx);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static void getColormapColor(final ImVec4 dst, final int idx) {
        nGetColormapColor(dst, idx);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static ImVec4 getColormapColor(final int idx, final int cmap) {
        final ImVec4 dst = new ImVec4();
        nGetColormapColor(dst, idx, cmap);
        return dst;
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorX(final int idx, final int cmap) {
        return nGetColormapColorX(idx, cmap);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorY(final int idx, final int cmap) {
        return nGetColormapColorY(idx, cmap);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorZ(final int idx, final int cmap) {
        return nGetColormapColorZ(idx, cmap);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static float getColormapColorW(final int idx, final int cmap) {
        return nGetColormapColorW(idx, cmap);
    }

    /**
     * Returns a color from a colormap given an index {@code >=} 0 (modulo will be performed).
     */
    public static void getColormapColor(final ImVec4 dst, final int idx, final int cmap) {
        nGetColormapColor(dst, idx, cmap);
    }

    private static native void nGetColormapColor(ImVec4 dst, int idx); /*
        Jni::ImVec4Cpy(env, ImPlot::GetColormapColor(idx), dst);
    */

    private static native float nGetColormapColorX(int idx); /*
        return ImPlot::GetColormapColor(idx).x;
    */

    private static native float nGetColormapColorY(int idx); /*
        return ImPlot::GetColormapColor(idx).y;
    */

    private static native float nGetColormapColorZ(int idx); /*
        return ImPlot::GetColormapColor(idx).z;
    */

    private static native float nGetColormapColorW(int idx); /*
        return ImPlot::GetColormapColor(idx).w;
    */

    private static native void nGetColormapColor(ImVec4 dst, int idx, int cmap); /*
        Jni::ImVec4Cpy(env, ImPlot::GetColormapColor(idx, cmap), dst);
    */

    private static native float nGetColormapColorX(int idx, int cmap); /*
        return ImPlot::GetColormapColor(idx, cmap).x;
    */

    private static native float nGetColormapColorY(int idx, int cmap); /*
        return ImPlot::GetColormapColor(idx, cmap).y;
    */

    private static native float nGetColormapColorZ(int idx, int cmap); /*
        return ImPlot::GetColormapColor(idx, cmap).z;
    */

    private static native float nGetColormapColorW(int idx, int cmap); /*
        return ImPlot::GetColormapColor(idx, cmap).w;
    */

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static ImVec4 sampleColormap(final float t) {
        final ImVec4 dst = new ImVec4();
        nSampleColormap(dst, t);
        return dst;
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapX(final float t) {
        return nSampleColormapX(t);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapY(final float t) {
        return nSampleColormapY(t);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapZ(final float t) {
        return nSampleColormapZ(t);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapW(final float t) {
        return nSampleColormapW(t);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static void sampleColormap(final ImVec4 dst, final float t) {
        nSampleColormap(dst, t);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static ImVec4 sampleColormap(final float t, final int cmap) {
        final ImVec4 dst = new ImVec4();
        nSampleColormap(dst, t, cmap);
        return dst;
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapX(final float t, final int cmap) {
        return nSampleColormapX(t, cmap);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapY(final float t, final int cmap) {
        return nSampleColormapY(t, cmap);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapZ(final float t, final int cmap) {
        return nSampleColormapZ(t, cmap);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static float sampleColormapW(final float t, final int cmap) {
        return nSampleColormapW(t, cmap);
    }

    /**
     * Sample a color from a colormap given t between 0 and 1
     */
    public static void sampleColormap(final ImVec4 dst, final float t, final int cmap) {
        nSampleColormap(dst, t, cmap);
    }

    private static native void nSampleColormap(ImVec4 dst, float t); /*
        Jni::ImVec4Cpy(env, ImPlot::SampleColormap(t), dst);
    */

    private static native float nSampleColormapX(float t); /*
        return ImPlot::SampleColormap(t).x;
    */

    private static native float nSampleColormapY(float t); /*
        return ImPlot::SampleColormap(t).y;
    */

    private static native float nSampleColormapZ(float t); /*
        return ImPlot::SampleColormap(t).z;
    */

    private static native float nSampleColormapW(float t); /*
        return ImPlot::SampleColormap(t).w;
    */

    private static native void nSampleColormap(ImVec4 dst, float t, int cmap); /*
        Jni::ImVec4Cpy(env, ImPlot::SampleColormap(t, cmap), dst);
    */

    private static native float nSampleColormapX(float t, int cmap); /*
        return ImPlot::SampleColormap(t, cmap).x;
    */

    private static native float nSampleColormapY(float t, int cmap); /*
        return ImPlot::SampleColormap(t, cmap).y;
    */

    private static native float nSampleColormapZ(float t, int cmap); /*
        return ImPlot::SampleColormap(t, cmap).z;
    */

    private static native float nSampleColormapW(float t, int cmap); /*
        return ImPlot::SampleColormap(t, cmap).w;
    */

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax) {
        nColormapScale(label, scaleMin, scaleMax);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final ImVec2 size) {
        nColormapScale(label, scaleMin, scaleMax, size.x, size.y);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final float sizeX, final float sizeY) {
        nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final ImVec2 size, final int cmap) {
        nColormapScale(label, scaleMin, scaleMax, size.x, size.y, cmap);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final float sizeX, final float sizeY, final int cmap) {
        nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, cmap);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final ImVec2 size, final int cmap, final String fmt) {
        nColormapScale(label, scaleMin, scaleMax, size.x, size.y, cmap, fmt);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final float sizeX, final float sizeY, final int cmap, final String fmt) {
        nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, cmap, fmt);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final int cmap, final String fmt) {
        nColormapScale(label, scaleMin, scaleMax, cmap, fmt);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final String fmt) {
        nColormapScale(label, scaleMin, scaleMax, fmt);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final ImVec2 size, final String fmt) {
        nColormapScale(label, scaleMin, scaleMax, size.x, size.y, fmt);
    }

    /**
     * Shows a vertical color scale with linear spaced ticks using the specified color map. Use double hashes to hide label (e.g. "##NoLabel").
     */
    public static void colormapScale(final String label, final double scaleMin, final double scaleMax, final float sizeX, final float sizeY, final String fmt) {
        nColormapScale(label, scaleMin, scaleMax, sizeX, sizeY, fmt);
    }

    private static native void nColormapScale(String label, double scaleMin, double scaleMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImPlot::ColormapScale(label, scaleMin, scaleMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nColormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImPlot::ColormapScale(label, scaleMin, scaleMax, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nColormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, int cmap); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImPlot::ColormapScale(label, scaleMin, scaleMax, size, cmap);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nColormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, int cmap, String fmt); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImPlot::ColormapScale(label, scaleMin, scaleMax, size, cmap, fmt);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    private static native void nColormapScale(String label, double scaleMin, double scaleMax, int cmap, String fmt); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImPlot::ColormapScale(label, scaleMin, scaleMax, ImVec2(0,0), cmap, fmt);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    private static native void nColormapScale(String label, double scaleMin, double scaleMax, String fmt); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImPlot::ColormapScale(label, scaleMin, scaleMax, ImVec2(0,0), IMPLOT_AUTO, fmt);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    private static native void nColormapScale(String label, double scaleMin, double scaleMax, float sizeX, float sizeY, String fmt); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto fmt = obj_fmt == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fmt, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImPlot::ColormapScale(label, scaleMin, scaleMax, size, IMPLOT_AUTO, fmt);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (fmt != NULL) env->ReleaseStringUTFChars(obj_fmt, fmt);
    */

    /**
     * Shows a horizontal slider with a colormap gradient background.
     * TODO: support our argument
     */
    public static boolean colormapSlider(final String label, final ImFloat t) {
        return nColormapSlider(label, t != null ? t.getData() : null);
    }

    /**
     * Shows a horizontal slider with a colormap gradient background.
     * TODO: support our argument
     */
    public static boolean colormapSlider(final String label, final ImFloat t, final String format) {
        return nColormapSlider(label, t != null ? t.getData() : null, format);
    }

    /**
     * Shows a horizontal slider with a colormap gradient background.
     * TODO: support our argument
     */
    public static boolean colormapSlider(final String label, final ImFloat t, final String format, final int cmap) {
        return nColormapSlider(label, t != null ? t.getData() : null, format, cmap);
    }

    /**
     * Shows a horizontal slider with a colormap gradient background.
     * TODO: support our argument
     */
    public static boolean colormapSlider(final String label, final ImFloat t, final int cmap) {
        return nColormapSlider(label, t != null ? t.getData() : null, cmap);
    }

    private static native boolean nColormapSlider(String obj_label, float[] obj_t); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto t = obj_t == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_t, JNI_FALSE);
        auto _result = ImPlot::ColormapSlider(label, (t != NULL ? &t[0] : NULL), NULL);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (t != NULL) env->ReleasePrimitiveArrayCritical(obj_t, t, JNI_FALSE);
        return _result;
    */

    private static native boolean nColormapSlider(String obj_label, float[] obj_t, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto t = obj_t == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_t, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImPlot::ColormapSlider(label, (t != NULL ? &t[0] : NULL), NULL, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (t != NULL) env->ReleasePrimitiveArrayCritical(obj_t, t, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nColormapSlider(String obj_label, float[] obj_t, String obj_format, int cmap); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto t = obj_t == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_t, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImPlot::ColormapSlider(label, (t != NULL ? &t[0] : NULL), NULL, format, cmap);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (t != NULL) env->ReleasePrimitiveArrayCritical(obj_t, t, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nColormapSlider(String obj_label, float[] obj_t, int cmap); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto t = obj_t == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_t, JNI_FALSE);
        auto _result = ImPlot::ColormapSlider(label, (t != NULL ? &t[0] : NULL), NULL, "", cmap);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (t != NULL) env->ReleasePrimitiveArrayCritical(obj_t, t, JNI_FALSE);
        return _result;
    */

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static boolean colormapButton(final String label) {
        return nColormapButton(label);
    }

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static boolean colormapButton(final String label, final ImVec2 size) {
        return nColormapButton(label, size.x, size.y);
    }

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static boolean colormapButton(final String label, final float sizeX, final float sizeY) {
        return nColormapButton(label, sizeX, sizeY);
    }

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static boolean colormapButton(final String label, final ImVec2 size, final int cmap) {
        return nColormapButton(label, size.x, size.y, cmap);
    }

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static boolean colormapButton(final String label, final float sizeX, final float sizeY, final int cmap) {
        return nColormapButton(label, sizeX, sizeY, cmap);
    }

    /**
     * Shows a button with a colormap gradient brackground.
     */
    public static boolean colormapButton(final String label, final int cmap) {
        return nColormapButton(label, cmap);
    }

    private static native boolean nColormapButton(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImPlot::ColormapButton(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nColormapButton(String obj_label, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::ColormapButton(label, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nColormapButton(String obj_label, float sizeX, float sizeY, int cmap); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImPlot::ColormapButton(label, size, cmap);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nColormapButton(String obj_label, int cmap); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImPlot::ColormapButton(label, ImVec2(0,0), cmap);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
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
        nBustColorCache();
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
    public static void bustColorCache(final String plotTitleId) {
        nBustColorCache(plotTitleId);
    }

    private static native void nBustColorCache(); /*
        ImPlot::BustColorCache();
    */

    private static native void nBustColorCache(String plotTitleId); /*MANUAL
        auto plotTitleId = obj_plotTitleId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_plotTitleId, JNI_FALSE);
        ImPlot::BustColorCache(plotTitleId);
        if (plotTitleId != NULL) env->ReleaseStringUTFChars(obj_plotTitleId, plotTitleId);
    */

    //-----------------------------------------------------------------------------
    // Miscellaneous
    //-----------------------------------------------------------------------------

    // Render icons similar to those that appear in legends (nifty for data lists).

    public static void itemIcon(final ImVec4 col) {
        nItemIcon(col.x, col.y, col.z, col.w);
    }

    public static void itemIcon(final float colX, final float colY, final float colZ, final float colW) {
        nItemIcon(colX, colY, colZ, colW);
    }

    private static native void nItemIcon(float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImPlot::ItemIcon(col);
    */

    public static void itemIcon(final int col) {
        nItemIcon(col);
    }

    private static native void nItemIcon(int col); /*
        ImPlot::ItemIcon(static_cast<ImU32>(col));
    */

    public static void colormapIcon(final int cmap) {
        nColormapIcon(cmap);
    }

    private static native void nColormapIcon(int cmap); /*
        ImPlot::ColormapIcon(cmap);
    */

    /**
     * Get the plot draw list for custom rendering to the current plot area. Call between Begin/EndPlot.
     */
    public static ImDrawList getPlotDrawList() {
        return new ImDrawList(nGetPlotDrawList());
    }

    private static native long nGetPlotDrawList(); /*
        return (intptr_t)ImPlot::GetPlotDrawList();
    */

    /**
     * Push clip rect for rendering to current plot area. The rect can be expanded or contracted by #expand pixels. Call between Begin/EndPlot.
     */
    public static void pushPlotClipRect() {
        nPushPlotClipRect();
    }

    /**
     * Push clip rect for rendering to current plot area. The rect can be expanded or contracted by #expand pixels. Call between Begin/EndPlot.
     */
    public static void pushPlotClipRect(final float expand) {
        nPushPlotClipRect(expand);
    }

    private static native void nPushPlotClipRect(); /*
        ImPlot::PushPlotClipRect();
    */

    private static native void nPushPlotClipRect(float expand); /*
        ImPlot::PushPlotClipRect(expand);
    */

    /**
     * Pop plot clip rect. Call between Begin/EndPlot.
     */
    public static void popPlotClipRect() {
        nPopPlotClipRect();
    }

    private static native void nPopPlotClipRect(); /*
        ImPlot::PopPlotClipRect();
    */

    /**
     * Shows ImPlot style selector dropdown menu.
     */
    public static boolean showStyleSelector(final String label) {
        return nShowStyleSelector(label);
    }

    private static native boolean nShowStyleSelector(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImPlot::ShowStyleSelector(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Shows ImPlot colormap selector dropdown menu.
     */
    public static boolean showColormapSelector(final String label) {
        return nShowColormapSelector(label);
    }

    private static native boolean nShowColormapSelector(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImPlot::ShowColormapSelector(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Shows ImPlot style editor block (not a window).
     */
    public static void showStyleEditor() {
        nShowStyleEditor();
    }

    /**
     * Shows ImPlot style editor block (not a window).
     */
    public static void showStyleEditor(final ImPlotStyle ref) {
        nShowStyleEditor(ref.ptr);
    }

    private static native void nShowStyleEditor(); /*
        ImPlot::ShowStyleEditor();
    */

    private static native void nShowStyleEditor(long ref); /*
        ImPlot::ShowStyleEditor(reinterpret_cast<ImPlotStyle*>(ref));
    */

    /**
     * Add basic help/info block for end users (not a window).
     */
    public static void showUserGuide() {
        nShowUserGuide();
    }

    private static native void nShowUserGuide(); /*
        ImPlot::ShowUserGuide();
    */

    /**
     * Shows ImPlot metrics/debug information window.
     */
    public static void showMetricsWindow() {
        nShowMetricsWindow();
    }

    /**
     * Shows ImPlot metrics/debug information window.
     */
    public static void showMetricsWindow(final ImBoolean pOpen) {
        nShowMetricsWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowMetricsWindow(); /*
        ImPlot::ShowMetricsWindow();
    */

    private static native void nShowMetricsWindow(boolean[] pOpen); /*MANUAL
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        ImPlot::ShowMetricsWindow((pOpen != NULL ? &pOpen[0] : NULL));
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
    */

    //-----------------------------------------------------------------------------
    // Demo
    //-----------------------------------------------------------------------------

    /**
     * Shows the ImPlot demo window.
     */
    public static void showDemoWindow() {
        nShowDemoWindow();
    }

    /**
     * Shows the ImPlot demo window.
     */
    public static void showDemoWindow(final ImBoolean pOpen) {
        nShowDemoWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowDemoWindow(); /*
        ImPlot::ShowDemoWindow();
    */

    private static native void nShowDemoWindow(boolean[] pOpen); /*MANUAL
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        ImPlot::ShowDemoWindow((pOpen != NULL ? &pOpen[0] : NULL));
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
    */
}
