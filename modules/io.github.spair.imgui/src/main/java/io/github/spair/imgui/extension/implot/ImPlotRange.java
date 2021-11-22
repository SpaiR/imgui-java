package io.github.spair.imgui.extension.implot;

import io.github.spair.imgui.binding.ImGuiStructDestroyable;

public final class ImPlotRange extends ImGuiStructDestroyable {
    public ImPlotRange(final long ptr) {
        super(ptr);
    }

    public ImPlotRange() {
        this(0, 0);
    }

    public ImPlotRange(final double min, final double max) {
        this(0);
        ptr = create(min, max);
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

        #define IMPLOT_RANGE ((ImPlotRange*)STRUCT_PTR)
     */

    @Override
    protected native long create(); /*
        return (intptr_t)(new ImPlotRange(0, 0));
    */

    protected native long create(double min, double max); /*
        return (intptr_t)(new ImPlotRange(min, max));
    */

    public native boolean contains(double value); /*
        return IMPLOT_RANGE->Contains(value);
    */

    public native double size(); /*
        return IMPLOT_RANGE->Size();
     */

    public native double getMin(); /*
        return IMPLOT_RANGE->Min;
    */

    public native double getMax(); /*
        return IMPLOT_RANGE->Max;
     */

    public native void setMin(double min); /*
        IMPLOT_RANGE->Min = min;
     */

    public native void nSetMax(double max); /*
        IMPLOT_RANGE->Max = max;
     */
}
