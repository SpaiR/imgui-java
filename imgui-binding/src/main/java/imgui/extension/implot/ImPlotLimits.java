package imgui.extension.implot;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;

public final class ImPlotLimits extends ImGuiStructDestroyable {
    public ImPlotLimits(final long ptr) {
        super(ptr);
    }

    public ImPlotLimits() {
        this(0, 0, 0, 0);
    }

    public ImPlotLimits(final double xMin, final double xMax, final double yMin, final double yMax) {
        this(0);
        ptr = create(xMin, xMax, yMin, yMax);
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

        #define IMPLOT_LIMITS ((ImPlotLimits*)STRUCT_PTR)
     */

    @Override
    protected native long create(); /*
        return (intptr_t)(new ImPlotLimits(0, 0, 0, 0));
    */

    protected native long create(double xMin, double xMax, double yMin, double yMax); /*
        return (intptr_t)(new ImPlotLimits(xMin, xMax, yMin, yMax));
    */

    public boolean contains(final ImPlotPoint plotPoint) {
        return contains(plotPoint.getX(), plotPoint.getY());
    }

    public native boolean contains(double x, double y); /*
        return IMPLOT_LIMITS->Contains(x, y);
    */

    /**
     * Returns the same as min(), but instead as an ImVec2.
     */
    public ImVec2 minVec() {
        final ImPlotPoint val = min();
        final ImVec2 vec = new ImVec2((float) val.getX(), (float) val.getY());
        val.destroy();
        return vec;
    }

    /**
     * The returned ImPlotPoint should be manually deallocated with destroy()!
     */
    public ImPlotPoint min() {
        return new ImPlotPoint(nMin());
    }

    private native long nMin(); /*
        ImPlotPoint* p = new ImPlotPoint(IMPLOT_LIMITS->Min());
        return (intptr_t)p;
    */

    /**
     * The returned ImPlotPoint should be manually deallocated with destroy()!
     */
    public ImPlotPoint max() {
        return new ImPlotPoint(nMax());
    }

    /**
     * Returns the same as max(), but instead as an ImVec2.
     */
    public ImVec2 maxVec() {
        final ImPlotPoint val = max();
        final ImVec2 vec = new ImVec2((float) val.getX(), (float) val.getY());
        val.destroy();
        return vec;
    }

    private native long nMax(); /*
        ImPlotPoint* p = new ImPlotPoint(IMPLOT_LIMITS->Max());
        return (intptr_t)p;
    */

    public ImPlotRange getX() {
        return new ImPlotRange(nGetX(this.ptr));
    }

    private native long nGetX(long ptr); /*
        return (intptr_t)&(IMPLOT_LIMITS->X);
    */

    public ImPlotRange getY() {
        return new ImPlotRange(nGetY(this.ptr));
    }

    private native long nGetY(long ptr); /*
        return (intptr_t)&(IMPLOT_LIMITS->Y);
     */

    public void setX(final ImPlotRange value) {
        nSetX(value.ptr);
    }

    private native void nSetX(long valueptr); /*
        IMPLOT_LIMITS->X = *((ImPlotRange*)valueptr);
     */

    public void setY(final ImPlotRange value) {
        nSetY(value.ptr);
    }

    private native void nSetY(long valueptr); /*
        IMPLOT_LIMITS->Y = *((ImPlotRange*)valueptr);
     */
}
