package io.github.spair.imgui.extension.implot;

import io.github.spair.imgui.ImVec2;
import io.github.spair.imgui.ImVec4;
import io.github.spair.imgui.binding.ImGuiStruct;
import io.github.spair.imgui.extension.implot.flag.ImPlotCol;

public final class ImPlotStyle extends ImGuiStruct {
    public ImPlotStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

        #define IMPLOT_STYLE ((ImPlotStyle*)STRUCT_PTR)
     */

    //Line weight
    public native float getLineWeight(); /*
        return IMPLOT_STYLE->LineWeight;
    */

    public native void setLineWeight(float lineWeight); /*
        IMPLOT_STYLE->LineWeight = lineWeight;
    */

    //Marker
    public native int getMarker(); /*
        return IMPLOT_STYLE->Marker;
    */

    public native void setMarker(float input); /*
        IMPLOT_STYLE->Marker = input;
     */

    //Marker size
    public native float getMarkerSize(); /*
        return IMPLOT_STYLE->MarkerSize;
    */

    public native void setMarkerSize(float input); /*
        IMPLOT_STYLE->MarkerSize = input;
     */

    //Marker weight
    public native float getMarkerWeight(); /*
        return IMPLOT_STYLE->MarkerWeight;
    */

    public native void setMarkerWeight(float input); /*
        IMPLOT_STYLE->MarkerWeight = input;
     */

    //Fill alpha
    public native float getFillAlpha(); /*
        return IMPLOT_STYLE->FillAlpha;
    */

    public native void setFillAlpha(float input); /*
        IMPLOT_STYLE->FillAlpha = input;
     */

    //Error bar size
    public native float getErrorBarSize(); /*
        return IMPLOT_STYLE->ErrorBarSize;
    */

    public native void setErrorBarSize(float input); /*
        IMPLOT_STYLE->ErrorBarSize = input;
     */

    //Error bar weight
    public native float getErrorBarWeight(); /*
        return IMPLOT_STYLE->ErrorBarWeight;
    */

    public native void setErrorBarWeight(float input); /*
        IMPLOT_STYLE->ErrorBarWeight = input;
     */

    //Digital big height
    public native float getDigitalBitHeight(); /*
        return IMPLOT_STYLE->DigitalBitHeight;
    */

    public native void setDigitalBitHeight(float input); /*
        IMPLOT_STYLE->DigitalBitHeight = input;
     */

    //Digital bit gap
    public native float getDigitalBitGap(); /*
        return IMPLOT_STYLE->DigitalBitGap;
    */

    public native void setDigitalBitGap(float input); /*
        IMPLOT_STYLE->DigitalBitGap = input;
     */

    //Plot border size
    public native float getPlotBorderSize(); /*
        return IMPLOT_STYLE->PlotBorderSize;
    */

    public native void setPlotBorderSize(float input); /*
        IMPLOT_STYLE->PlotBorderSize = input;
     */

    //Minor alpha
    public native float getMinorAlpha(); /*
        return IMPLOT_STYLE->MinorAlpha;
    */

    public native void setMinorAlpha(float input); /*
        IMPLOT_STYLE->MinorAlpha = input;
     */

    //Major tick length
    public ImVec2 getMajorTickLen() {
        final ImVec2 vec = new ImVec2();
        nGetMajorTickLen(vec);
        return vec;
    }

    private native void nGetMajorTickLen(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MajorTickLen, vec);
    */

    public void setMajorTickLen(final ImVec2 majorTickLen) {
        nSetMajorTickLen(majorTickLen.x, majorTickLen.y);
    }

    private native void nSetMajorTickLen(float x, float y); /*
        IMPLOT_STYLE->MajorTickLen = ImVec2(x, y);
     */

    //Minor tick length
    public ImVec2 getMinorTickLen() {
        final ImVec2 vec = new ImVec2();
        nGetMinorTickLen(vec);
        return vec;
    }

    private native void nGetMinorTickLen(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MinorTickLen, vec);
    */

    public void setMinorTickLen(final ImVec2 minorTickLen) {
        nSetMinorTickLen(minorTickLen.x, minorTickLen.y);
    }

    private native void nSetMinorTickLen(float x, float y); /*
        IMPLOT_STYLE->MinorTickLen = ImVec2(x, y);
     */

    //Major tick size
    public ImVec2 getMajorTickSize() {
        final ImVec2 vec = new ImVec2();
        nGetMajorTickSize(vec);
        return vec;
    }

    private native void nGetMajorTickSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MajorTickSize, vec);
    */

    public void setMajorTickSize(final ImVec2 majorTickSize) {
        nSetMajorTickSize(majorTickSize.x, majorTickSize.y);
    }

    private native void nSetMajorTickSize(float x, float y); /*
        IMPLOT_STYLE->MajorTickSize = ImVec2(x, y);
     */

    //Minor tick size
    public ImVec2 getMinorTickSize() {
        final ImVec2 vec = new ImVec2();
        nGetMinorTickSize(vec);
        return vec;
    }

    private native void nGetMinorTickSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MinorTickSize, vec);
    */

    public void setMinorTickSize(final ImVec2 minorTickSize) {
        nSetMinorTickSize(minorTickSize.x, minorTickSize.y);
    }

    private native void nSetMinorTickSize(float x, float y); /*
        IMPLOT_STYLE->MinorTickSize = ImVec2(x, y);
     */

    //Major grid size
    public ImVec2 getMajorGridSize() {
        final ImVec2 vec = new ImVec2();
        nGetMajorGridSize(vec);
        return vec;
    }

    private native void nGetMajorGridSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MajorGridSize, vec);
    */

    public void setMajorGridSize(final ImVec2 majorGridSize) {
        nSetMajorGridSize(majorGridSize.x, majorGridSize.y);
    }

    private native void nSetMajorGridSize(float x, float y); /*
        IMPLOT_STYLE->MajorGridSize = ImVec2(x, y);
     */

    //Minor grid size
    public ImVec2 getMinorGridSize() {
        final ImVec2 vec = new ImVec2();
        nGetMinorGridSize(vec);
        return vec;
    }

    private native void nGetMinorGridSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MinorGridSize, vec);
    */

    public void setMinorGridSize(final ImVec2 minorGridSize) {
        nSetMinorGridSize(minorGridSize.x, minorGridSize.y);
    }

    private native void nSetMinorGridSize(float x, float y); /*
        IMPLOT_STYLE->MinorGridSize = ImVec2(x, y);
     */

    //Plot padding
    public ImVec2 getPlotPadding() {
        final ImVec2 vec = new ImVec2();
        nGetPlotPadding(vec);
        return vec;
    }

    private native void nGetPlotPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->PlotPadding, vec);
    */

    public void setPlotPadding(final ImVec2 plotPadding) {
        nSetPlotPadding(plotPadding.x, plotPadding.y);
    }

    private native void nSetPlotPadding(float x, float y); /*
        IMPLOT_STYLE->PlotPadding = ImVec2(x, y);
     */

    //Label padding
    public ImVec2 getLabelPadding() {
        final ImVec2 vec = new ImVec2();
        nGetLabelPadding(vec);
        return vec;
    }

    private native void nGetLabelPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->LabelPadding, vec);
    */

    public void setLabelPadding(final ImVec2 labelPadding) {
        nSetLabelPadding(labelPadding.x, labelPadding.y);
    }

    private native void nSetLabelPadding(float x, float y); /*
        IMPLOT_STYLE->LabelPadding = ImVec2(x, y);
     */

    //Legend padding
    public ImVec2 getLegendPadding() {
        final ImVec2 vec = new ImVec2();
        nGetLegendPadding(vec);
        return vec;
    }

    private native void nGetLegendPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->LegendPadding, vec);
    */

    public void setLegendPadding(final ImVec2 legendPadding) {
        nSetLegendPadding(legendPadding.x, legendPadding.y);
    }

    private native void nSetLegendPadding(float x, float y); /*
        IMPLOT_STYLE->LegendPadding = ImVec2(x, y);
     */

    //Legend inner padding
    public ImVec2 getLegendInnerPadding() {
        final ImVec2 vec = new ImVec2();
        nGetLegendInnerPadding(vec);
        return vec;
    }

    private native void nGetLegendInnerPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->LegendInnerPadding, vec);
    */

    public void setLegendInnerPadding(final ImVec2 legendInnerPadding) {
        nSetLegendInnerPadding(legendInnerPadding.x, legendInnerPadding.y);
    }

    private native void nSetLegendInnerPadding(float x, float y); /*
        IMPLOT_STYLE->LegendInnerPadding = ImVec2(x, y);
     */

    //Legend spacing
    public ImVec2 getLegendSpacing() {
        final ImVec2 vec = new ImVec2();
        nGetLegendSpacing(vec);
        return vec;
    }

    private native void nGetLegendSpacing(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->LegendSpacing, vec);
    */

    public void setLegendSpacing(final ImVec2 legendSpacing) {
        nSetLegendSpacing(legendSpacing.x, legendSpacing.y);
    }

    private native void nSetLegendSpacing(float x, float y); /*
        IMPLOT_STYLE->LegendSpacing = ImVec2(x, y);
     */

    //Mouse position padding
    public ImVec2 getMousePosPadding() {
        final ImVec2 vec = new ImVec2();
        nGetMousePosPadding(vec);
        return vec;
    }

    private native void nGetMousePosPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->MousePosPadding, vec);
    */

    public void setMousePosPadding(final ImVec2 mousePosPadding) {
        nSetMousePosPadding(mousePosPadding.x, mousePosPadding.y);
    }

    private native void nSetMousePosPadding(float x, float y); /*
        IMPLOT_STYLE->MousePosPadding = ImVec2(x, y);
     */

    //Annotation padding
    public ImVec2 getAnnotationPadding() {
        final ImVec2 vec = new ImVec2();
        nGetAnnotationPadding(vec);
        return vec;
    }

    private native void nGetAnnotationPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->AnnotationPadding, vec);
    */

    public void setAnnotationPadding(final ImVec2 annotationPadding) {
        nSetAnnotationPadding(annotationPadding.x, annotationPadding.y);
    }

    private native void nSetAnnotationPadding(float x, float y); /*
        IMPLOT_STYLE->AnnotationPadding = ImVec2(x, y);
     */

    //Fit padding
    public ImVec2 getFitPadding() {
        final ImVec2 vec = new ImVec2();
        nGetFitPadding(vec);
        return vec;
    }

    private native void nGetFitPadding(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->FitPadding, vec);
    */

    public void setFitPadding(final ImVec2 fitPadding) {
        nSetFitPadding(fitPadding.x, fitPadding.y);
    }

    private native void nSetFitPadding(float x, float y); /*
        IMPLOT_STYLE->FitPadding = ImVec2(x, y);
     */

    //Plot default size
    public ImVec2 getPlotDefaultSize() {
        final ImVec2 vec = new ImVec2();
        nGetPlotDefaultSize(vec);
        return vec;
    }

    private native void nGetPlotDefaultSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->PlotDefaultSize, vec);
    */

    public void setPlotDefaultSize(final ImVec2 plotDefaultSize) {
        nSetPlotDefaultSize(plotDefaultSize.x, plotDefaultSize.y);
    }

    private native void nSetPlotDefaultSize(float x, float y); /*
        IMPLOT_STYLE->PlotDefaultSize = ImVec2(x, y);
     */

    //Plot minimum size
    public ImVec2 getPlotMinSize() {
        final ImVec2 vec = new ImVec2();
        nGetPlotMinSize(vec);
        return vec;
    }

    private native void nGetPlotMinSize(ImVec2 vec); /*
        Jni::ImVec2Cpy(env, IMPLOT_STYLE->PlotMinSize, vec);
    */

    public void setPlotMinSize(final ImVec2 plotMinSize) {
        nSetPlotMinSize(plotMinSize.x, plotMinSize.y);
    }

    private native void nSetPlotMinSize(float x, float y); /*
        IMPLOT_STYLE->PlotMinSize = ImVec2(x, y);
     */

    //Colors
    public ImVec4[] getColors() {
        final float[] w = new float[ImPlotCol.COUNT];
        final float[] x = new float[ImPlotCol.COUNT];
        final float[] y = new float[ImPlotCol.COUNT];
        final float[] z = new float[ImPlotCol.COUNT];

        nGetColors(w, x, y, z, ImPlotCol.COUNT);

        final ImVec4[] colors = new ImVec4[ImPlotCol.COUNT];
        for (int i = 0; i < ImPlotCol.COUNT; i++) {
            colors[i] = new ImVec4(w[i], x[i], y[i], z[i]);
        }
        return colors;
    }

    private native void nGetColors(float[] w, float[] x, float[] y, float[] z, int count); /*
        ImVec4* colors = IMPLOT_STYLE->Colors;

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

        nSetColors(w, x, y, z, ImPlotCol.COUNT);
    }

    private native void nSetColors(float[] w, float[] x, float[] y, float[] z, int count); /*
        ImVec4* colors = IMPLOT_STYLE->Colors;

        for (int i = 0; i < count; i++) {
            colors->w = w[i];
            colors->x = x[i];
            colors->y = y[i];
            colors->z = z[i];

            colors++;
        }
     */

    //Colormap
    public native int getColormap(); /*
        return IMPLOT_STYLE->Colormap;
    */

    public native void setColormap(int input); /*
        IMPLOT_STYLE->Colormap = input;
     */

    //Antialiasing
    public native boolean isAntiAliasedLines(); /*
        return IMPLOT_STYLE->AntiAliasedLines;
    */

    public native void setAntiAliasedLines(boolean input); /*
        IMPLOT_STYLE->AntiAliasedLines = input;
     */

    //Local time
    public native boolean isUseLocalTime(); /*
        return IMPLOT_STYLE->UseLocalTime;
    */

    public native void setUseLocalTime(boolean input); /*
        IMPLOT_STYLE->UseLocalTime = input;
     */

    //ISO 8601
    public native boolean isUseISO8601(); /*
        return IMPLOT_STYLE->UseISO8601;
    */

    public native void setUseISO8601(boolean input); /*
        IMPLOT_STYLE->UseISO8601 = input;
     */

    //24HR clock
    public native boolean isUse24HourClock(); /*
        return IMPLOT_STYLE->Use24HourClock;
    */

    public native void setUse24HourClock(boolean input); /*
        IMPLOT_STYLE->Use24HourClock = input;
     */
}
