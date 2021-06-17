package imgui.extension.implot;

import imgui.binding.ImGuiStruct;

public final class ImPlotLimits extends ImGuiStruct {
    public ImPlotLimits(final long ptr) {
        super(ptr);
    }

    public ImPlotLimits() {
        this(0, 0, 0, 0);
    }

    public ImPlotLimits(final double xMin, final double xMax, final double yMin, final double yMax) {
        this(create(xMin, xMax, yMin, yMax));
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

     */

    private static native long create(double xMin, double xMax, double yMin, double yMax); /*
        return (intptr_t)(new ImPlotLimits(xMin, xMax, yMin, yMax));
    */

    public boolean contains(final ImPlotPoint plotPoint) {
        return contains(plotPoint.getX(), plotPoint.getY());
    }

    public boolean contains(final double x, final double y) {
        return nContains(this.ptr, x, y);
    }

    private static native boolean nContains(long ptr, double x, double y); /*
        return ((ImPlotLimits*)ptr)->Contains(x, y);
    */

    public ImPlotPoint min() {
        return new ImPlotPoint(nMin(this.ptr));
    }

    private static native long nMin(long ptr); /*
        ImPlotPoint* p = new ImPlotPoint(((ImPlotLimits*)ptr)->Min());
        return (intptr_t)p;
    */

    public ImPlotPoint max() {
        return new ImPlotPoint(nMax(this.ptr));
    }

    private static native long nMax(long ptr); /*
        ImPlotPoint* p = new ImPlotPoint(((ImPlotLimits*)ptr)->Max());
        return (intptr_t)p;
    */

    public ImPlotRange getX() {
        return new ImPlotRange(nGetX(this.ptr));
    }

    private static native long nGetX(long ptr); /*
        return (intptr_t)&(((ImPlotLimits*)ptr)->X);
    */

    public ImPlotRange getY() {
        return new ImPlotRange(nGetY(this.ptr));
    }

    private native long nGetY(long ptr); /*
        return (intptr_t)&(((ImPlotLimits*)ptr)->Y);
     */

    public void setX(final ImPlotRange value) {
        nSetX(this.ptr, value.ptr);
    }

    private static native void nSetX(long ptr, long valueptr); /*
        ((ImPlotLimits*)ptr)->X = *((ImPlotRange*)valueptr);
     */

    public void setY(final ImPlotRange value) {
        nSetY(this.ptr, value.ptr);
    }

    private static native void nSetY(long ptr, long valueptr); /*
        ((ImPlotLimits*)ptr)->Y = *((ImPlotRange*)valueptr);
     */
}
