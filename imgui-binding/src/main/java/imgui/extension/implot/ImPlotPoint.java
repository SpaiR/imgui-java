package imgui.extension.implot;

import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImPlotPoint extends ImGuiStruct {
    public ImPlotPoint(final long ptr) {
        super(ptr);
    }

    public ImPlotPoint() {
        this(0, 0);
    }

    public ImPlotPoint(final ImVec2 vec) {
        this(vec.x, vec.y);
    }

    public ImPlotPoint(final double x, final double y) {
        this(create(x, y));
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

     */

    private static native long create(double x, double y); /*
        return (intptr_t)(new ImPlotPoint(x, y));
    */

    public double getX() {
        return nGetX(this.ptr);
    }

    private static native double nGetX(long ptr); /*
        return ((ImPlotPoint*)ptr)->x;
    */

    public double getY() {
        return nGetY(this.ptr);
    }

    private static native double nGetY(long ptr); /*
        return ((ImPlotPoint*)ptr)->y;
     */

    public void setX(final double value) {
        nSetX(this.ptr, value);
    }

    private static native void nSetX(long ptr, double value); /*
        ((ImPlotPoint*)ptr)->x = value;
     */

    public void setY(final double value) {
        nSetY(this.ptr, value);
    }

    private static native void nSetY(long ptr, double value); /*
        ((ImPlotPoint*)ptr)->y = value;
     */
}
