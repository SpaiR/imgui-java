package imgui.extension.implot;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;
import imgui.extension.implot.flag.ImPlotCol;

public final class ImPlotStyle extends ImGuiStruct {
    public ImPlotStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

     */

    //Line weight
    public float getLineWeight()  {
        return getLineWeightStatic(this.ptr);
    }

    private static native float getLineWeightStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->LineWeight;
    */

    public void setLineWeight(final float lineWeight)  {
        setLineWeightStatic(this.ptr, lineWeight);
    }

    private static native void setLineWeightStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->LineWeight = input;
    */

    //Marker
    public int getMarker() {
        return getMarkerStatic(this.ptr);
    }

    private static native int getMarkerStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->Marker;
    */

    public void setMarker(final int marker) {
        setMarkerStatic(this.ptr, marker);
    }

    private static native void setMarkerStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->Marker = input;
     */

    //Marker size
    public float getMarkerSize() {
        return getMarkerSizeStatic(this.ptr);
    }

    private static native float getMarkerSizeStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->MarkerSize;
    */

    public void setMarkerSize(final float markerSize) {
        setMarkerSizeStatic(this.ptr, markerSize);
    }

    private static native void setMarkerSizeStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->MarkerSize = input;
     */

    //Marker weight
    public float getMarkerWeight() {
        return getMarkerWeightStatic(this.ptr);
    }

    private static native float getMarkerWeightStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->MarkerWeight;
    */

    public void setMarkerWeight(final float markerWeight) {
        setMarkerWeightStatic(this.ptr, markerWeight);
    }

    private static native void setMarkerWeightStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->MarkerWeight = input;
     */

    //Fill alpha
    public float getFillAlpha() {
        return getFillAlphaStatic(this.ptr);
    }

    private static native float getFillAlphaStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->FillAlpha;
    */

    public void setFillAlpha(final float fillAlpha) {
        setFillAlphaStatic(this.ptr, fillAlpha);
    }

    private static native void setFillAlphaStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->FillAlpha = input;
     */

    //Error bar size
    public float getErrorBarSize() {
        return getErrorBarSizeStatic(this.ptr);
    }

    private static native float getErrorBarSizeStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->ErrorBarSize;
    */

    public void setErrorBarSize(final float errorBarSize) {
        setErrorBarSizeStatic(this.ptr, errorBarSize);
    }

    private static native void setErrorBarSizeStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->ErrorBarSize = input;
     */

    //Error bar weight
    public float getErrorBarWeight() {
        return getErrorBarWeightStatic(this.ptr);
    }

    private static native float getErrorBarWeightStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->ErrorBarWeight;
    */

    public void setErrorBarWeight(final float errorBarWeight) {
        setErrorBarWeightStatic(this.ptr, errorBarWeight);
    }

    private static native void setErrorBarWeightStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->ErrorBarWeight = input;
     */

    //Digital big height
    public float getDigitalBitHeight() {
        return getDigitalBitHeightStatic(this.ptr);
    }

    private static native float getDigitalBitHeightStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->DigitalBitHeight;
    */

    public void setDigitalBitHeight(final float digitalBitHeight) {
        setDigitalBitHeightStatic(this.ptr, digitalBitHeight);
    }

    private static native void setDigitalBitHeightStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->DigitalBitHeight = input;
     */

    //Digital bit gap
    public float getDigitalBitGap() {
        return getDigitalBitHeightStatic(this.ptr);
    }

    private static native float getDigitalBitGapStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->DigitalBitGap;
    */

    public void setDigitalBitGap(final float digitalBitGap) {
        setDigitalBitGapStatic(this.ptr, digitalBitGap);
    }

    private static native void setDigitalBitGapStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->DigitalBitGap = input;
     */

    //Plot border size
    public float getPlotBorderSize() {
        return getPlotBorderSizeStatic(this.ptr);
    }

    private static native float getPlotBorderSizeStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->PlotBorderSize;
    */

    public void setPlotBorderSize(final float plotBorderSize) {
        setPlotBorderSizeStatic(this.ptr, plotBorderSize);
    }

    private static native void setPlotBorderSizeStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->PlotBorderSize = input;
     */

    //Minor alpha
    public float getMinorAlpha() {
        return getMinorAlphaStatic(this.ptr);
    }

    private static native float getMinorAlphaStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->MinorAlpha;
    */

    public void setMinorAlpha(final float minorAlpha) {
        setMinorAlphaStatic(this.ptr, minorAlpha);
    }

    private static native void setMinorAlphaStatic(long ptr, float input); /*
        ((ImPlotStyle*)ptr)->MinorAlpha = input;
     */

    //Major tick length
    public ImVec2 getMajorTickLen() {
        final ImVec2 vec = new ImVec2();
        getMajorTickLenStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMajorTickLenStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MajorTickLen, vec);
    */

    public void setMajorTickLen(final ImVec2 majorTickLen) {
        setMajorTickLenStatic(this.ptr, majorTickLen.x, majorTickLen.y);
    }

    private static native void setMajorTickLenStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MajorTickLen = ImVec2(x, y);
     */

    //Minor tick length
    public ImVec2 getMinorTickLen() {
        final ImVec2 vec = new ImVec2();
        getMinorTickLenStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMinorTickLenStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MinorTickLen, vec);
    */

    public void setMinorTickLen(final ImVec2 minorTickLen) {
        setMinorTickLenStatic(this.ptr, minorTickLen.x, minorTickLen.y);
    }

    private static native void setMinorTickLenStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MinorTickLen = ImVec2(x, y);
     */

    //Major tick size
    public ImVec2 getMajorTickSize() {
        final ImVec2 vec = new ImVec2();
        getMajorTickSizeStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMajorTickSizeStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MajorTickSize, vec);
    */

    public void setMajorTickSize(final ImVec2 majorTickSize) {
        setMajorTickSizeStatic(this.ptr, majorTickSize.x, majorTickSize.y);
    }

    private static native void setMajorTickSizeStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MajorTickSize = ImVec2(x, y);
     */

    //Minor tick size
    public ImVec2 getMinorTickSize() {
        final ImVec2 vec = new ImVec2();
        getMinorTickSizeStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMinorTickSizeStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MinorTickSize, vec);
    */

    public void setMinorTickSize(final ImVec2 minorTickSize) {
        setMinorTickSizeStatic(this.ptr, minorTickSize.x, minorTickSize.y);
    }

    private static native void setMinorTickSizeStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MinorTickSize = ImVec2(x, y);
     */

    //Major grid size
    public ImVec2 getMajorGridSize() {
        final ImVec2 vec = new ImVec2();
        getMajorGridSizeStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMajorGridSizeStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MajorGridSize, vec);
    */

    public void setMajorGridSize(final ImVec2 majorGridSize) {
        setMajorGridSizeStatic(this.ptr, majorGridSize.x, majorGridSize.y);
    }

    private static native void setMajorGridSizeStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MajorGridSize = ImVec2(x, y);
     */

    //Minor grid size
    public ImVec2 getMinorGridSize() {
        final ImVec2 vec = new ImVec2();
        getMinorGridSizeStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMinorGridSizeStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MinorGridSize, vec);
    */

    public void setMinorGridSize(final ImVec2 minorGridSize) {
        setMinorGridSizeStatic(this.ptr, minorGridSize.x, minorGridSize.y);
    }

    private static native void setMinorGridSizeStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MinorGridSize = ImVec2(x, y);
     */

    //Plot padding
    public ImVec2 getPlotPadding() {
        final ImVec2 vec = new ImVec2();
        getPlotPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getPlotPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->PlotPadding, vec);
    */

    public void setPlotPadding(final ImVec2 plotPadding) {
        setPlotPaddingStatic(this.ptr, plotPadding.x, plotPadding.y);
    }

    private static native void setPlotPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->PlotPadding = ImVec2(x, y);
     */

    //Label padding
    public ImVec2 getLabelPadding() {
        final ImVec2 vec = new ImVec2();
        getLabelPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getLabelPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->LabelPadding, vec);
    */

    public void setLabelPadding(final ImVec2 labelPadding) {
        setLabelPaddingStatic(this.ptr, labelPadding.x, labelPadding.y);
    }

    private static native void setLabelPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->LabelPadding = ImVec2(x, y);
     */

    //Legend padding
    public ImVec2 getLegendPadding() {
        final ImVec2 vec = new ImVec2();
        getLegendPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getLegendPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->LegendPadding, vec);
    */

    public void setLegendPadding(final ImVec2 legendPadding) {
        setLegendPaddingStatic(this.ptr, legendPadding.x, legendPadding.y);
    }

    private static native void setLegendPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->LegendPadding = ImVec2(x, y);
     */

    //Legend inner padding
    public ImVec2 getLegendInnerPadding() {
        final ImVec2 vec = new ImVec2();
        getLegendInnerPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getLegendInnerPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->LegendInnerPadding, vec);
    */

    public void setLegendInnerPadding(final ImVec2 legendInnerPadding) {
        setLegendInnerPaddingStatic(this.ptr, legendInnerPadding.x, legendInnerPadding.y);
    }

    private static native void setLegendInnerPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->LegendInnerPadding = ImVec2(x, y);
     */

    //Legend spacing
    public ImVec2 getLegendSpacing() {
        final ImVec2 vec = new ImVec2();
        getLegendSpacingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getLegendSpacingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->LegendSpacing, vec);
    */

    public void setLegendSpacing(final ImVec2 legendSpacing) {
        setLegendSpacingStatic(this.ptr, legendSpacing.x, legendSpacing.y);
    }

    private static native void setLegendSpacingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->LegendSpacing = ImVec2(x, y);
     */

    //Mouse position padding
    public ImVec2 getMousePosPadding() {
        final ImVec2 vec = new ImVec2();
        getMousePosPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getMousePosPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->MousePosPadding, vec);
    */

    public void setMousePosPadding(final ImVec2 mousePosPadding) {
        setMousePosPaddingStatic(this.ptr, mousePosPadding.x, mousePosPadding.y);
    }

    private static native void setMousePosPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->MousePosPadding = ImVec2(x, y);
     */

    //Annotation padding
    public ImVec2 getAnnotationPadding() {
        final ImVec2 vec = new ImVec2();
        getAnnotationPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getAnnotationPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->AnnotationPadding, vec);
    */

    public void setAnnotationPadding(final ImVec2 annotationPadding) {
        setAnnotationPaddingStatic(this.ptr, annotationPadding.x, annotationPadding.y);
    }

    private static native void setAnnotationPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->AnnotationPadding = ImVec2(x, y);
     */

    //Fit padding
    public ImVec2 getFitPadding() {
        final ImVec2 vec = new ImVec2();
        getFitPaddingStatic(this.ptr, vec);
        return vec;
    }

    private static native void getFitPaddingStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->FitPadding, vec);
    */

    public void setFitPadding(final ImVec2 fitPadding) {
        setFitPaddingStatic(this.ptr, fitPadding.x, fitPadding.y);
    }

    private static native void setFitPaddingStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->FitPadding = ImVec2(x, y);
     */

    //Plot default size
    public ImVec2 getPlotDefaultSize() {
        final ImVec2 vec = new ImVec2();
        getPlotDefaultSizeStatic(this.ptr, vec);
        return vec;
    }

    private static native void getPlotDefaultSizeStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->PlotDefaultSize, vec);
    */

    public void setPlotDefaultSize(final ImVec2 plotDefaultSize) {
        setPlotDefaultSizeStatic(this.ptr, plotDefaultSize.x, plotDefaultSize.y);
    }

    private static native void setPlotDefaultSizeStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->PlotDefaultSize = ImVec2(x, y);
     */

    //Plot minimum size
    public ImVec2 getPlotMinSize() {
        final ImVec2 vec = new ImVec2();
        getPlotMinSizeStatic(this.ptr, vec);
        return vec;
    }

    private static native void getPlotMinSizeStatic(long ptr, ImVec2 vec); /*
        Jni::ImVec2Cpy(env, ((ImPlotStyle*)ptr)->PlotMinSize, vec);
    */

    public void setPlotMinSize(final ImVec2 plotMinSize) {
        setPlotMinSizeStatic(this.ptr, plotMinSize.x, plotMinSize.y);
    }

    private static native void setPlotMinSizeStatic(long ptr, float x, float y); /*
        ((ImPlotStyle*)ptr)->PlotMinSize = ImVec2(x, y);
     */

    //Colors
    public ImVec4[] getColors() {
        final float[] w = new float[ImPlotCol.COUNT];
        final float[] x = new float[ImPlotCol.COUNT];
        final float[] y = new float[ImPlotCol.COUNT];
        final float[] z = new float[ImPlotCol.COUNT];

        getColorsStatic(this.ptr, w, x, y, z, ImPlotCol.COUNT);

        final ImVec4[] colors = new ImVec4[ImPlotCol.COUNT];
        for (int i = 0; i < ImPlotCol.COUNT; i++) {
            colors[i] = new ImVec4(w[i], x[i], y[i], z[i]);
        }
        return colors;
    }

    private static native void getColorsStatic(long ptr, float[] w, float[] x, float[] y, float[] z, int count); /*
        ImVec4* colors = ((ImPlotStyle*)ptr)->Colors;

        for (int i = 0; i < count; i++) {
            w[i] = colors->w;
            x[i] = colors->x;
            y[i] = colors->y;
            z[i] = colors->z;

            colors++;
        }
    */

    public void setColors(final ImVec4[] colors) {
        final float[] w = new float[ImPlotCol.COUNT];
        final float[] x = new float[ImPlotCol.COUNT];
        final float[] y = new float[ImPlotCol.COUNT];
        final float[] z = new float[ImPlotCol.COUNT];

        for (int i = 0; i < ImPlotCol.COUNT; i++) {
            w[i] = colors[i].w;
            x[i] = colors[i].x;
            y[i] = colors[i].y;
            z[i] = colors[i].z;
        }

        setColorsStatic(this.ptr, w, x, y, z, ImPlotCol.COUNT);
    }

    private static native void setColorsStatic(long ptr, float[] w, float[] x, float[] y, float[] z, int count); /*
        ImVec4* colors = ((ImPlotStyle*)ptr)->Colors;

        for (int i = 0; i < count; i++) {
            colors->w = w[i];
            colors->x = x[i];
            colors->y = y[i];
            colors->z = z[i];

            colors++;
        }
     */

    //Colormap
    public int getColormap() {
        return getColormapStatic(this.ptr);
    }

    private static native int getColormapStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->Colormap;
    */

    public void setColormap(final int colormap) {
        setColormapStatic(this.ptr, colormap);
    }

    private static native void setColormapStatic(long ptr, int input); /*
        ((ImPlotStyle*)ptr)->Colormap = input;
     */

    //Antialiasing
    public boolean isAntiAliasedLines() {
        return isAntiAliasedLinesStatic(this.ptr);
    }

    private static native boolean isAntiAliasedLinesStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->AntiAliasedLines;
    */

    public void setAntiAliasedLines(final boolean antiAliasedLines) {
        setAntiAliasedLinesStatic(this.ptr, antiAliasedLines);
    }

    private static native void setAntiAliasedLinesStatic(long ptr, boolean input); /*
        ((ImPlotStyle*)ptr)->AntiAliasedLines = input;
     */

    //Local time
    public boolean isUseLocalTime() {
        return isUseLocalTimeStatic(this.ptr);
    }

    private static native boolean isUseLocalTimeStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->UseLocalTime;
    */

    public void setUseLocalTime(final boolean useLocalTime) {
        setUseLocalTimeStatic(this.ptr, useLocalTime);
    }

    private static native void setUseLocalTimeStatic(long ptr, boolean input); /*
        ((ImPlotStyle*)ptr)->UseLocalTime = input;
     */

    //ISO 8601
    public boolean isUseISO8601() {
        return isUseISO8601Static(this.ptr);
    }

    private static native boolean isUseISO8601Static(long ptr); /*
        return ((ImPlotStyle*)ptr)->UseISO8601;
    */

    public void setUseISO8601(final boolean useISO8601) {
        setUseISO8601Static(this.ptr, useISO8601);
    }

    private static native void setUseISO8601Static(long ptr, boolean input); /*
        ((ImPlotStyle*)ptr)->UseISO8601 = input;
     */

    //24HR clock
    public boolean isUse24HourClock() {
        return isUse24HourClockStatic(this.ptr);
    }

    private static native boolean isUse24HourClockStatic(long ptr); /*
        return ((ImPlotStyle*)ptr)->Use24HourClock;
    */

    public void setUse24HourClock(final boolean use24HourClock) {
        setUse24HourClockStatic(this.ptr, use24HourClock);
    }

    private static native void setUse24HourClockStatic(long ptr, boolean input); /*
        ((ImPlotStyle*)ptr)->Use24HourClock = input;
     */
}
