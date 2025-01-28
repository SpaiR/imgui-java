package imgui.extension.implot;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStructDestroyable;

public final class ImPlotStyle extends ImGuiStructDestroyable {
    public ImPlotStyle() {
        super();
    }

    public ImPlotStyle(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_implot.h"
        #define THIS ((ImPlotStyle*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImPlotStyle());
    */

    public float getLineWeight() {
        return nGetLineWeight();
    }

    public void setLineWeight(final float value) {
        nSetLineWeight(value);
    }

    private native float nGetLineWeight(); /*
        return THIS->LineWeight;
    */

    private native void nSetLineWeight(float value); /*
        THIS->LineWeight = value;
    */

    public int getMarker() {
        return nGetMarker();
    }

    public void setMarker(final int value) {
        nSetMarker(value);
    }

    private native int nGetMarker(); /*
        return THIS->Marker;
    */

    private native void nSetMarker(int value); /*
        THIS->Marker = value;
    */

    public float getMarkerSize() {
        return nGetMarkerSize();
    }

    public void setMarkerSize(final float value) {
        nSetMarkerSize(value);
    }

    private native float nGetMarkerSize(); /*
        return THIS->MarkerSize;
    */

    private native void nSetMarkerSize(float value); /*
        THIS->MarkerSize = value;
    */

    public float getMarkerWeight() {
        return nGetMarkerWeight();
    }

    public void setMarkerWeight(final float value) {
        nSetMarkerWeight(value);
    }

    private native float nGetMarkerWeight(); /*
        return THIS->MarkerWeight;
    */

    private native void nSetMarkerWeight(float value); /*
        THIS->MarkerWeight = value;
    */

    public float getFillAlpha() {
        return nGetFillAlpha();
    }

    public void setFillAlpha(final float value) {
        nSetFillAlpha(value);
    }

    private native float nGetFillAlpha(); /*
        return THIS->FillAlpha;
    */

    private native void nSetFillAlpha(float value); /*
        THIS->FillAlpha = value;
    */

    public float getErrorBarSize() {
        return nGetErrorBarSize();
    }

    public void setErrorBarSize(final float value) {
        nSetErrorBarSize(value);
    }

    private native float nGetErrorBarSize(); /*
        return THIS->ErrorBarSize;
    */

    private native void nSetErrorBarSize(float value); /*
        THIS->ErrorBarSize = value;
    */

    public float getErrorBarWeight() {
        return nGetErrorBarWeight();
    }

    public void setErrorBarWeight(final float value) {
        nSetErrorBarWeight(value);
    }

    private native float nGetErrorBarWeight(); /*
        return THIS->ErrorBarWeight;
    */

    private native void nSetErrorBarWeight(float value); /*
        THIS->ErrorBarWeight = value;
    */

    public float getDigitalBitHeight() {
        return nGetDigitalBitHeight();
    }

    public void setDigitalBitHeight(final float value) {
        nSetDigitalBitHeight(value);
    }

    private native float nGetDigitalBitHeight(); /*
        return THIS->DigitalBitHeight;
    */

    private native void nSetDigitalBitHeight(float value); /*
        THIS->DigitalBitHeight = value;
    */

    public float getDigitalBitGap() {
        return nGetDigitalBitGap();
    }

    public void setDigitalBitGap(final float value) {
        nSetDigitalBitGap(value);
    }

    private native float nGetDigitalBitGap(); /*
        return THIS->DigitalBitGap;
    */

    private native void nSetDigitalBitGap(float value); /*
        THIS->DigitalBitGap = value;
    */

    public float getPlotBorderSize() {
        return nGetPlotBorderSize();
    }

    public void setPlotBorderSize(final float value) {
        nSetPlotBorderSize(value);
    }

    private native float nGetPlotBorderSize(); /*
        return THIS->PlotBorderSize;
    */

    private native void nSetPlotBorderSize(float value); /*
        THIS->PlotBorderSize = value;
    */

    public float getMinorAlpha() {
        return nGetMinorAlpha();
    }

    public void setMinorAlpha(final float value) {
        nSetMinorAlpha(value);
    }

    private native float nGetMinorAlpha(); /*
        return THIS->MinorAlpha;
    */

    private native void nSetMinorAlpha(float value); /*
        THIS->MinorAlpha = value;
    */

    public ImVec2 getMajorTickLen() {
        final ImVec2 dst = new ImVec2();
        nGetMajorTickLen(dst);
        return dst;
    }

    public float getMajorTickLenX() {
        return nGetMajorTickLenX();
    }

    public float getMajorTickLenY() {
        return nGetMajorTickLenY();
    }

    public void getMajorTickLen(final ImVec2 dst) {
        nGetMajorTickLen(dst);
    }

    public void setMajorTickLen(final ImVec2 value) {
        nSetMajorTickLen(value.x, value.y);
    }

    public void setMajorTickLen(final float valueX, final float valueY) {
        nSetMajorTickLen(valueX, valueY);
    }

    private native void nGetMajorTickLen(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MajorTickLen, dst);
    */

    private native float nGetMajorTickLenX(); /*
        return THIS->MajorTickLen.x;
    */

    private native float nGetMajorTickLenY(); /*
        return THIS->MajorTickLen.y;
    */

    private native void nSetMajorTickLen(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MajorTickLen = value;
    */

    public ImVec2 getMinorTickLen() {
        final ImVec2 dst = new ImVec2();
        nGetMinorTickLen(dst);
        return dst;
    }

    public float getMinorTickLenX() {
        return nGetMinorTickLenX();
    }

    public float getMinorTickLenY() {
        return nGetMinorTickLenY();
    }

    public void getMinorTickLen(final ImVec2 dst) {
        nGetMinorTickLen(dst);
    }

    public void setMinorTickLen(final ImVec2 value) {
        nSetMinorTickLen(value.x, value.y);
    }

    public void setMinorTickLen(final float valueX, final float valueY) {
        nSetMinorTickLen(valueX, valueY);
    }

    private native void nGetMinorTickLen(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MinorTickLen, dst);
    */

    private native float nGetMinorTickLenX(); /*
        return THIS->MinorTickLen.x;
    */

    private native float nGetMinorTickLenY(); /*
        return THIS->MinorTickLen.y;
    */

    private native void nSetMinorTickLen(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MinorTickLen = value;
    */

    public ImVec2 getMajorTickSize() {
        final ImVec2 dst = new ImVec2();
        nGetMajorTickSize(dst);
        return dst;
    }

    public float getMajorTickSizeX() {
        return nGetMajorTickSizeX();
    }

    public float getMajorTickSizeY() {
        return nGetMajorTickSizeY();
    }

    public void getMajorTickSize(final ImVec2 dst) {
        nGetMajorTickSize(dst);
    }

    public void setMajorTickSize(final ImVec2 value) {
        nSetMajorTickSize(value.x, value.y);
    }

    public void setMajorTickSize(final float valueX, final float valueY) {
        nSetMajorTickSize(valueX, valueY);
    }

    private native void nGetMajorTickSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MajorTickSize, dst);
    */

    private native float nGetMajorTickSizeX(); /*
        return THIS->MajorTickSize.x;
    */

    private native float nGetMajorTickSizeY(); /*
        return THIS->MajorTickSize.y;
    */

    private native void nSetMajorTickSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MajorTickSize = value;
    */

    public ImVec2 getMinorTickSize() {
        final ImVec2 dst = new ImVec2();
        nGetMinorTickSize(dst);
        return dst;
    }

    public float getMinorTickSizeX() {
        return nGetMinorTickSizeX();
    }

    public float getMinorTickSizeY() {
        return nGetMinorTickSizeY();
    }

    public void getMinorTickSize(final ImVec2 dst) {
        nGetMinorTickSize(dst);
    }

    public void setMinorTickSize(final ImVec2 value) {
        nSetMinorTickSize(value.x, value.y);
    }

    public void setMinorTickSize(final float valueX, final float valueY) {
        nSetMinorTickSize(valueX, valueY);
    }

    private native void nGetMinorTickSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MinorTickSize, dst);
    */

    private native float nGetMinorTickSizeX(); /*
        return THIS->MinorTickSize.x;
    */

    private native float nGetMinorTickSizeY(); /*
        return THIS->MinorTickSize.y;
    */

    private native void nSetMinorTickSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MinorTickSize = value;
    */

    public ImVec2 getMajorGridSize() {
        final ImVec2 dst = new ImVec2();
        nGetMajorGridSize(dst);
        return dst;
    }

    public float getMajorGridSizeX() {
        return nGetMajorGridSizeX();
    }

    public float getMajorGridSizeY() {
        return nGetMajorGridSizeY();
    }

    public void getMajorGridSize(final ImVec2 dst) {
        nGetMajorGridSize(dst);
    }

    public void setMajorGridSize(final ImVec2 value) {
        nSetMajorGridSize(value.x, value.y);
    }

    public void setMajorGridSize(final float valueX, final float valueY) {
        nSetMajorGridSize(valueX, valueY);
    }

    private native void nGetMajorGridSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MajorGridSize, dst);
    */

    private native float nGetMajorGridSizeX(); /*
        return THIS->MajorGridSize.x;
    */

    private native float nGetMajorGridSizeY(); /*
        return THIS->MajorGridSize.y;
    */

    private native void nSetMajorGridSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MajorGridSize = value;
    */

    public ImVec2 getMinorGridSize() {
        final ImVec2 dst = new ImVec2();
        nGetMinorGridSize(dst);
        return dst;
    }

    public float getMinorGridSizeX() {
        return nGetMinorGridSizeX();
    }

    public float getMinorGridSizeY() {
        return nGetMinorGridSizeY();
    }

    public void getMinorGridSize(final ImVec2 dst) {
        nGetMinorGridSize(dst);
    }

    public void setMinorGridSize(final ImVec2 value) {
        nSetMinorGridSize(value.x, value.y);
    }

    public void setMinorGridSize(final float valueX, final float valueY) {
        nSetMinorGridSize(valueX, valueY);
    }

    private native void nGetMinorGridSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MinorGridSize, dst);
    */

    private native float nGetMinorGridSizeX(); /*
        return THIS->MinorGridSize.x;
    */

    private native float nGetMinorGridSizeY(); /*
        return THIS->MinorGridSize.y;
    */

    private native void nSetMinorGridSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MinorGridSize = value;
    */

    public ImVec2 getPlotPadding() {
        final ImVec2 dst = new ImVec2();
        nGetPlotPadding(dst);
        return dst;
    }

    public float getPlotPaddingX() {
        return nGetPlotPaddingX();
    }

    public float getPlotPaddingY() {
        return nGetPlotPaddingY();
    }

    public void getPlotPadding(final ImVec2 dst) {
        nGetPlotPadding(dst);
    }

    public void setPlotPadding(final ImVec2 value) {
        nSetPlotPadding(value.x, value.y);
    }

    public void setPlotPadding(final float valueX, final float valueY) {
        nSetPlotPadding(valueX, valueY);
    }

    private native void nGetPlotPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->PlotPadding, dst);
    */

    private native float nGetPlotPaddingX(); /*
        return THIS->PlotPadding.x;
    */

    private native float nGetPlotPaddingY(); /*
        return THIS->PlotPadding.y;
    */

    private native void nSetPlotPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->PlotPadding = value;
    */

    public ImVec2 getLabelPadding() {
        final ImVec2 dst = new ImVec2();
        nGetLabelPadding(dst);
        return dst;
    }

    public float getLabelPaddingX() {
        return nGetLabelPaddingX();
    }

    public float getLabelPaddingY() {
        return nGetLabelPaddingY();
    }

    public void getLabelPadding(final ImVec2 dst) {
        nGetLabelPadding(dst);
    }

    public void setLabelPadding(final ImVec2 value) {
        nSetLabelPadding(value.x, value.y);
    }

    public void setLabelPadding(final float valueX, final float valueY) {
        nSetLabelPadding(valueX, valueY);
    }

    private native void nGetLabelPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->LabelPadding, dst);
    */

    private native float nGetLabelPaddingX(); /*
        return THIS->LabelPadding.x;
    */

    private native float nGetLabelPaddingY(); /*
        return THIS->LabelPadding.y;
    */

    private native void nSetLabelPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->LabelPadding = value;
    */

    public ImVec2 getLegendPadding() {
        final ImVec2 dst = new ImVec2();
        nGetLegendPadding(dst);
        return dst;
    }

    public float getLegendPaddingX() {
        return nGetLegendPaddingX();
    }

    public float getLegendPaddingY() {
        return nGetLegendPaddingY();
    }

    public void getLegendPadding(final ImVec2 dst) {
        nGetLegendPadding(dst);
    }

    public void setLegendPadding(final ImVec2 value) {
        nSetLegendPadding(value.x, value.y);
    }

    public void setLegendPadding(final float valueX, final float valueY) {
        nSetLegendPadding(valueX, valueY);
    }

    private native void nGetLegendPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->LegendPadding, dst);
    */

    private native float nGetLegendPaddingX(); /*
        return THIS->LegendPadding.x;
    */

    private native float nGetLegendPaddingY(); /*
        return THIS->LegendPadding.y;
    */

    private native void nSetLegendPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->LegendPadding = value;
    */

    public ImVec2 getLegendInnerPadding() {
        final ImVec2 dst = new ImVec2();
        nGetLegendInnerPadding(dst);
        return dst;
    }

    public float getLegendInnerPaddingX() {
        return nGetLegendInnerPaddingX();
    }

    public float getLegendInnerPaddingY() {
        return nGetLegendInnerPaddingY();
    }

    public void getLegendInnerPadding(final ImVec2 dst) {
        nGetLegendInnerPadding(dst);
    }

    public void setLegendInnerPadding(final ImVec2 value) {
        nSetLegendInnerPadding(value.x, value.y);
    }

    public void setLegendInnerPadding(final float valueX, final float valueY) {
        nSetLegendInnerPadding(valueX, valueY);
    }

    private native void nGetLegendInnerPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->LegendInnerPadding, dst);
    */

    private native float nGetLegendInnerPaddingX(); /*
        return THIS->LegendInnerPadding.x;
    */

    private native float nGetLegendInnerPaddingY(); /*
        return THIS->LegendInnerPadding.y;
    */

    private native void nSetLegendInnerPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->LegendInnerPadding = value;
    */

    public ImVec2 getLegendSpacing() {
        final ImVec2 dst = new ImVec2();
        nGetLegendSpacing(dst);
        return dst;
    }

    public float getLegendSpacingX() {
        return nGetLegendSpacingX();
    }

    public float getLegendSpacingY() {
        return nGetLegendSpacingY();
    }

    public void getLegendSpacing(final ImVec2 dst) {
        nGetLegendSpacing(dst);
    }

    public void setLegendSpacing(final ImVec2 value) {
        nSetLegendSpacing(value.x, value.y);
    }

    public void setLegendSpacing(final float valueX, final float valueY) {
        nSetLegendSpacing(valueX, valueY);
    }

    private native void nGetLegendSpacing(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->LegendSpacing, dst);
    */

    private native float nGetLegendSpacingX(); /*
        return THIS->LegendSpacing.x;
    */

    private native float nGetLegendSpacingY(); /*
        return THIS->LegendSpacing.y;
    */

    private native void nSetLegendSpacing(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->LegendSpacing = value;
    */

    public ImVec2 getMousePosPadding() {
        final ImVec2 dst = new ImVec2();
        nGetMousePosPadding(dst);
        return dst;
    }

    public float getMousePosPaddingX() {
        return nGetMousePosPaddingX();
    }

    public float getMousePosPaddingY() {
        return nGetMousePosPaddingY();
    }

    public void getMousePosPadding(final ImVec2 dst) {
        nGetMousePosPadding(dst);
    }

    public void setMousePosPadding(final ImVec2 value) {
        nSetMousePosPadding(value.x, value.y);
    }

    public void setMousePosPadding(final float valueX, final float valueY) {
        nSetMousePosPadding(valueX, valueY);
    }

    private native void nGetMousePosPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MousePosPadding, dst);
    */

    private native float nGetMousePosPaddingX(); /*
        return THIS->MousePosPadding.x;
    */

    private native float nGetMousePosPaddingY(); /*
        return THIS->MousePosPadding.y;
    */

    private native void nSetMousePosPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MousePosPadding = value;
    */

    public ImVec2 getAnnotationPadding() {
        final ImVec2 dst = new ImVec2();
        nGetAnnotationPadding(dst);
        return dst;
    }

    public float getAnnotationPaddingX() {
        return nGetAnnotationPaddingX();
    }

    public float getAnnotationPaddingY() {
        return nGetAnnotationPaddingY();
    }

    public void getAnnotationPadding(final ImVec2 dst) {
        nGetAnnotationPadding(dst);
    }

    public void setAnnotationPadding(final ImVec2 value) {
        nSetAnnotationPadding(value.x, value.y);
    }

    public void setAnnotationPadding(final float valueX, final float valueY) {
        nSetAnnotationPadding(valueX, valueY);
    }

    private native void nGetAnnotationPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->AnnotationPadding, dst);
    */

    private native float nGetAnnotationPaddingX(); /*
        return THIS->AnnotationPadding.x;
    */

    private native float nGetAnnotationPaddingY(); /*
        return THIS->AnnotationPadding.y;
    */

    private native void nSetAnnotationPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->AnnotationPadding = value;
    */

    public ImVec2 getFitPadding() {
        final ImVec2 dst = new ImVec2();
        nGetFitPadding(dst);
        return dst;
    }

    public float getFitPaddingX() {
        return nGetFitPaddingX();
    }

    public float getFitPaddingY() {
        return nGetFitPaddingY();
    }

    public void getFitPadding(final ImVec2 dst) {
        nGetFitPadding(dst);
    }

    public void setFitPadding(final ImVec2 value) {
        nSetFitPadding(value.x, value.y);
    }

    public void setFitPadding(final float valueX, final float valueY) {
        nSetFitPadding(valueX, valueY);
    }

    private native void nGetFitPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->FitPadding, dst);
    */

    private native float nGetFitPaddingX(); /*
        return THIS->FitPadding.x;
    */

    private native float nGetFitPaddingY(); /*
        return THIS->FitPadding.y;
    */

    private native void nSetFitPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->FitPadding = value;
    */

    public ImVec2 getPlotDefaultSize() {
        final ImVec2 dst = new ImVec2();
        nGetPlotDefaultSize(dst);
        return dst;
    }

    public float getPlotDefaultSizeX() {
        return nGetPlotDefaultSizeX();
    }

    public float getPlotDefaultSizeY() {
        return nGetPlotDefaultSizeY();
    }

    public void getPlotDefaultSize(final ImVec2 dst) {
        nGetPlotDefaultSize(dst);
    }

    public void setPlotDefaultSize(final ImVec2 value) {
        nSetPlotDefaultSize(value.x, value.y);
    }

    public void setPlotDefaultSize(final float valueX, final float valueY) {
        nSetPlotDefaultSize(valueX, valueY);
    }

    private native void nGetPlotDefaultSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->PlotDefaultSize, dst);
    */

    private native float nGetPlotDefaultSizeX(); /*
        return THIS->PlotDefaultSize.x;
    */

    private native float nGetPlotDefaultSizeY(); /*
        return THIS->PlotDefaultSize.y;
    */

    private native void nSetPlotDefaultSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->PlotDefaultSize = value;
    */

    public ImVec2 getPlotMinSize() {
        final ImVec2 dst = new ImVec2();
        nGetPlotMinSize(dst);
        return dst;
    }

    public float getPlotMinSizeX() {
        return nGetPlotMinSizeX();
    }

    public float getPlotMinSizeY() {
        return nGetPlotMinSizeY();
    }

    public void getPlotMinSize(final ImVec2 dst) {
        nGetPlotMinSize(dst);
    }

    public void setPlotMinSize(final ImVec2 value) {
        nSetPlotMinSize(value.x, value.y);
    }

    public void setPlotMinSize(final float valueX, final float valueY) {
        nSetPlotMinSize(valueX, valueY);
    }

    private native void nGetPlotMinSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->PlotMinSize, dst);
    */

    private native float nGetPlotMinSizeX(); /*
        return THIS->PlotMinSize.x;
    */

    private native float nGetPlotMinSizeY(); /*
        return THIS->PlotMinSize.y;
    */

    private native void nSetPlotMinSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->PlotMinSize = value;
    */

    public ImVec4[] getColors() {
        return nGetColors();
    }

    public void setColors(final ImVec4[] value) {
        nSetColors(value);
    }

    private native ImVec4[] nGetColors(); /*
        return Jni::NewImVec4Array(env, THIS->Colors, ImPlotCol_COUNT);
    */

    private native void nSetColors(ImVec4[] value); /*
        Jni::ImVec4ArrayCpy(env, value, THIS->Colors, ImPlotCol_COUNT);
    */

    public int getColormap() {
        return nGetColormap();
    }

    public void setColormap(final int value) {
        nSetColormap(value);
    }

    private native int nGetColormap(); /*
        return THIS->Colormap;
    */

    private native void nSetColormap(int value); /*
        THIS->Colormap = value;
    */

    public boolean getUseLocalTime() {
        return nGetUseLocalTime();
    }

    public void setUseLocalTime(final boolean value) {
        nSetUseLocalTime(value);
    }

    private native boolean nGetUseLocalTime(); /*
        return THIS->UseLocalTime;
    */

    private native void nSetUseLocalTime(boolean value); /*
        THIS->UseLocalTime = value;
    */

    public boolean getUseISO8601() {
        return nGetUseISO8601();
    }

    public void setUseISO8601(final boolean value) {
        nSetUseISO8601(value);
    }

    private native boolean nGetUseISO8601(); /*
        return THIS->UseISO8601;
    */

    private native void nSetUseISO8601(boolean value); /*
        THIS->UseISO8601 = value;
    */

    public boolean getUse24HourClock() {
        return nGetUse24HourClock();
    }

    public void setUse24HourClock(final boolean value) {
        nSetUse24HourClock(value);
    }

    private native boolean nGetUse24HourClock(); /*
        return THIS->Use24HourClock;
    */

    private native void nSetUse24HourClock(boolean value); /*
        THIS->Use24HourClock = value;
    */

    /*JNI
        #undef THIS
     */
}
