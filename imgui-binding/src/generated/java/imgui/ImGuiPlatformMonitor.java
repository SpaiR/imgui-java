package imgui;

import imgui.binding.ImGuiStruct;

/**
 * (Optional) This is required when enabling multi-viewport. Represent the bounds of each connected monitor/display and their DPI.
 * We use this information for multiple DPI support + clamping the position of popups and tooltips so they don't straddle multiple monitors.
 */
public final class ImGuiPlatformMonitor extends ImGuiStruct {
    public ImGuiPlatformMonitor(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IMGUI_PLATFORM_MONITOR ((ImGuiPlatformMonitor*)STRUCT_PTR)
     */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native void getMainPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_PLATFORM_MONITOR->MainPos, dstImVec2);
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native float getMainPosX(); /*
        return IMGUI_PLATFORM_MONITOR->MainPos.x;
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native float getMainPosY(); /*
        return IMGUI_PLATFORM_MONITOR->MainPos.y;
    */

    public native void setMainPos(float x, float y); /*
        IMGUI_PLATFORM_MONITOR->MainPos = ImVec2(x, y);
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native void getMainSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_PLATFORM_MONITOR->MainSize, dstImVec2);
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native float getMainSizeX(); /*
        return IMGUI_PLATFORM_MONITOR->MainSize.x;
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native float getMainSizeY(); /*
        return IMGUI_PLATFORM_MONITOR->MainSize.y;
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public native void setMainSize(float x, float y); /*
        IMGUI_PLATFORM_MONITOR->MainSize = ImVec2(x, y);
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native void getWorkPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_PLATFORM_MONITOR->WorkPos, dstImVec2);
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native float getWorkPosX(); /*
        return IMGUI_PLATFORM_MONITOR->WorkPos.x;
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native float getWorkPosY(); /*
        return IMGUI_PLATFORM_MONITOR->WorkPos.y;
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native void setWorkPos(float x, float y); /*
        IMGUI_PLATFORM_MONITOR->WorkPos = ImVec2(x, y);
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native void getWorkSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_PLATFORM_MONITOR->WorkSize, dstImVec2);
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native float getWorkSizeX(); /*
        return IMGUI_PLATFORM_MONITOR->WorkSize.x;
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native float getWorkSizeY(); /*
        return IMGUI_PLATFORM_MONITOR->WorkSize.y;
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public native void setWorkSize(float x, float y); /*
        IMGUI_PLATFORM_MONITOR->WorkSize = ImVec2(x, y);
    */

    /**
     * 1.0f = 96 DPI
     */
    public native float getDpiScale(); /*
        return IMGUI_PLATFORM_MONITOR->DpiScale;
    */

    /**
     * 1.0f = 96 DPI
     */
    public native void setDpiScale(float dpiScale); /*
        IMGUI_PLATFORM_MONITOR->DpiScale = dpiScale;
    */
}
