package imgui.extension.implot;

import imgui.type.ImBoolean;

public class ImPlot {
    private ImPlot() {

    }

    /*JNI
        #include "_implot.h"

     */

    public static void showDemoWindow(ImBoolean pOpen) {
        nShowDemoWindow(pOpen.getData());
    }

    private static native void nShowDemoWindow(boolean[] pOpen); /*
        ImPlot::ShowDemoWindow(&pOpen[0]);
    */
}
