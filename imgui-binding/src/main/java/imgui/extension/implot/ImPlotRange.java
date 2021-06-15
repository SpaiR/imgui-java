package imgui.extension.implot;

import imgui.binding.ImGuiStruct;

public final class ImPlotRange extends ImGuiStruct
{
    public ImPlotRange(long ptr)
    {
        super(ptr);
    }

    public ImPlotRange() {
        this(0, 0);
    }

    public ImPlotRange(double min, double max) {
        this(create(min, max));
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

     */

    private static native long create(double min, double max); /*
        return (intptr_t)(new ImPlotRange(min, max));
    */

    public boolean contains(double value) {
        return nContains(this.ptr, value);
    }

    private static native boolean nContains(long ptr, double value); /*
        return ((ImPlotRange*)ptr)->Contains(value);
    */

    public double size() {
        return nSize(this.ptr);
    }

    private static native double nSize(long ptr); /*
        return ((ImPlotRange*)ptr)->Size();
     */

    public double getMin() {
        return nGetMin(this.ptr);
    }

    private static native double nGetMin(long ptr); /*
        return ((ImPlotRange*)ptr)->Min;
    */

    public double getMax() {
        return nGetMax(this.ptr);
    }

    private static native double nGetMax(long ptr); /*
        return ((ImPlotRange*)ptr)->Max;
     */

    public void setMin(double min) {
        nSetMin(this.ptr, min);
    }

    private static native void nSetMin(long ptr, double value); /*
        ((ImPlotRange*)ptr)->Min = value;
     */

    public void setMax(double max) {
        nSetMax(this.ptr, max);
    }

    private static native void nSetMax(long ptr, double value); /*
        ((ImPlotRange*)ptr)->Max = value;
     */
}
