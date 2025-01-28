package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

/**
 * (Optional) This is required when enabling multi-viewport. Represent the bounds of each connected monitor/display and their DPI.
 * We use this information for multiple DPI support + clamping the position of popups and tooltips so they don't straddle multiple monitors.
 */
@BindingSource
public final class ImGuiPlatformMonitor extends ImGuiStruct {
    public ImGuiPlatformMonitor(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiPlatformMonitor*)STRUCT_PTR)
     */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    @BindingField
    public ImVec2 MainPos;

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    @BindingField
    public ImVec2 MainSize;

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    @BindingField
    public ImVec2 WorkPos;

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    @BindingField
    public ImVec2 WorkSize;

    /**
     * 1.0f = 96 DPI
     */
    @BindingField
    public float DpiScale;

    /**
     * Backend dependant data (e.g. HMONITOR, GLFWmonitor*, SDL Display Index, NSScreen*)
     */
    public native void setPlatformHandle(long platformHandle); /*
        THIS->PlatformHandle = (void*)platformHandle;
    */

    /**
     * Backend dependant data (e.g. HMONITOR, GLFWmonitor*, SDL Display Index, NSScreen*)
     */
    public native long getPlatformHandle(); /*
        return (uintptr_t)THIS->PlatformHandle;
    */

    /*JNI
        #undef THIS
     */
}
