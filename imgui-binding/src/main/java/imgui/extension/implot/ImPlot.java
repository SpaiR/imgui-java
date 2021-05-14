package imgui.extension.implot;

import imgui.type.ImBoolean;

public final class ImPlot {
    private ImPlot() {

    }

    /*JNI
        #include "_common.h"
        #include "_implot.h"

     */

    public static native void createContext(); /*
        ImPlot::CreateContext();
    */

    public static void showDemoWindow(ImBoolean pOpen) {
        nShowDemoWindow(pOpen.getData());
    }

    private static native void nShowDemoWindow(boolean[] pOpen); /*
        ImPlot::ShowDemoWindow(&pOpen[0]);
    */
}
