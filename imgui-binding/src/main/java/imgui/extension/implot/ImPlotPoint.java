package imgui.extension.implot;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;

public final class ImPlotPoint extends ImGuiStructDestroyable {
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
        this(0);
        ptr = create(x, y);
    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

        #define IMPLOT_POINT ((ImPlotPoint*)STRUCT_PTR)
     */

    @Override
    protected native long create(); /*
        return (intptr_t)(new ImPlotPoint(0, 0));
    */

    protected native long create(double x, double y); /*
        return (intptr_t)(new ImPlotPoint(x, y));
    */

    public native double getX(); /*
        return IMPLOT_POINT->x;
    */

    public native double getY(); /*
        return IMPLOT_POINT->y;
     */

    public native void setX(double value); /*
        IMPLOT_POINT->x = value;
     */

    public native void setY(double value); /*
        IMPLOT_POINT->y = value;
     */
}
