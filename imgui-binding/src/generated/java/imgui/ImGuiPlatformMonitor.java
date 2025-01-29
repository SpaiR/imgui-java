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
        #define THIS ((ImGuiPlatformMonitor*)STRUCT_PTR)
     */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public ImVec2 getMainPos() {
        final ImVec2 dst = new ImVec2();
        nGetMainPos(dst);
        return dst;
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public float getMainPosX() {
        return nGetMainPosX();
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public float getMainPosY() {
        return nGetMainPosY();
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public void getMainPos(final ImVec2 dst) {
        nGetMainPos(dst);
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public void setMainPos(final ImVec2 value) {
        nSetMainPos(value.x, value.y);
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public void setMainPos(final float valueX, final float valueY) {
        nSetMainPos(valueX, valueY);
    }

    private native void nGetMainPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MainPos, dst);
    */

    private native float nGetMainPosX(); /*
        return THIS->MainPos.x;
    */

    private native float nGetMainPosY(); /*
        return THIS->MainPos.y;
    */

    private native void nSetMainPos(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MainPos = value;
    */

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public ImVec2 getMainSize() {
        final ImVec2 dst = new ImVec2();
        nGetMainSize(dst);
        return dst;
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public float getMainSizeX() {
        return nGetMainSizeX();
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public float getMainSizeY() {
        return nGetMainSizeY();
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public void getMainSize(final ImVec2 dst) {
        nGetMainSize(dst);
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public void setMainSize(final ImVec2 value) {
        nSetMainSize(value.x, value.y);
    }

    /**
     * Coordinates of the area displayed on this monitor (Min = upper left, Max = bottom right)
     */
    public void setMainSize(final float valueX, final float valueY) {
        nSetMainSize(valueX, valueY);
    }

    private native void nGetMainSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MainSize, dst);
    */

    private native float nGetMainSizeX(); /*
        return THIS->MainSize.x;
    */

    private native float nGetMainSizeY(); /*
        return THIS->MainSize.y;
    */

    private native void nSetMainSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MainSize = value;
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public ImVec2 getWorkPos() {
        final ImVec2 dst = new ImVec2();
        nGetWorkPos(dst);
        return dst;
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public float getWorkPosX() {
        return nGetWorkPosX();
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public float getWorkPosY() {
        return nGetWorkPosY();
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public void getWorkPos(final ImVec2 dst) {
        nGetWorkPos(dst);
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public void setWorkPos(final ImVec2 value) {
        nSetWorkPos(value.x, value.y);
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public void setWorkPos(final float valueX, final float valueY) {
        nSetWorkPos(valueX, valueY);
    }

    private native void nGetWorkPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->WorkPos, dst);
    */

    private native float nGetWorkPosX(); /*
        return THIS->WorkPos.x;
    */

    private native float nGetWorkPosY(); /*
        return THIS->WorkPos.y;
    */

    private native void nSetWorkPos(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->WorkPos = value;
    */

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public ImVec2 getWorkSize() {
        final ImVec2 dst = new ImVec2();
        nGetWorkSize(dst);
        return dst;
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public float getWorkSizeX() {
        return nGetWorkSizeX();
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public float getWorkSizeY() {
        return nGetWorkSizeY();
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public void getWorkSize(final ImVec2 dst) {
        nGetWorkSize(dst);
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public void setWorkSize(final ImVec2 value) {
        nSetWorkSize(value.x, value.y);
    }

    /**
     * Coordinates without task bars / side bars / menu bars.
     * Used to avoid positioning popups/tooltips inside this region. If you don't have this info, please copy the value for MainPos/MainSize.
     */
    public void setWorkSize(final float valueX, final float valueY) {
        nSetWorkSize(valueX, valueY);
    }

    private native void nGetWorkSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->WorkSize, dst);
    */

    private native float nGetWorkSizeX(); /*
        return THIS->WorkSize.x;
    */

    private native float nGetWorkSizeY(); /*
        return THIS->WorkSize.y;
    */

    private native void nSetWorkSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->WorkSize = value;
    */

    /**
     * 1.0f = 96 DPI
     */
    public float getDpiScale() {
        return nGetDpiScale();
    }

    /**
     * 1.0f = 96 DPI
     */
    public void setDpiScale(final float value) {
        nSetDpiScale(value);
    }

    private native float nGetDpiScale(); /*
        return THIS->DpiScale;
    */

    private native void nSetDpiScale(float value); /*
        THIS->DpiScale = value;
    */

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
