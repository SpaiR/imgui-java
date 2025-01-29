package imgui;

import imgui.binding.ImGuiStruct;

/**
 * The viewports created and managed by Dear ImGui. The role of the platform backend is to create the platform/OS windows corresponding to each viewport.
 * - Main Area = entire viewport.
 * - Work Area = entire viewport minus sections optionally used by menu bars, status bars. Some positioning code will prefer to use this. Window are also trying to stay within this area.
 */
public final class ImGuiViewport extends ImGuiStruct {
    public ImGuiViewport(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiViewport*)STRUCT_PTR)
     */

    /**
     * Unique identifier for the viewport.
     */
    public int getID() {
        return nGetID();
    }

    /**
     * Unique identifier for the viewport.
     */
    public void setID(final int value) {
        nSetID(value);
    }

    private native int nGetID(); /*
        return THIS->ID;
    */

    private native void nSetID(int value); /*
        THIS->ID = value;
    */

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public int getFlags() {
        return nGetFlags();
    }

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public void setFlags(final int value) {
        nSetFlags(value);
    }

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public void addFlags(final int flags) {
        setFlags(getFlags() | flags);
    }

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public void removeFlags(final int flags) {
        setFlags(getFlags() & ~(flags));
    }

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public boolean hasFlags(final int flags) {
        return (getFlags() & flags) != 0;
    }

    private native int nGetFlags(); /*
        return THIS->Flags;
    */

    private native void nSetFlags(int value); /*
        THIS->Flags = value;
    */

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public ImVec2 getPos() {
        final ImVec2 dst = new ImVec2();
        nGetPos(dst);
        return dst;
    }

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public float getPosX() {
        return nGetPosX();
    }

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public float getPosY() {
        return nGetPosY();
    }

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public void getPos(final ImVec2 dst) {
        nGetPos(dst);
    }

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public void setPos(final ImVec2 value) {
        nSetPos(value.x, value.y);
    }

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public void setPos(final float valueX, final float valueY) {
        nSetPos(valueX, valueY);
    }

    private native void nGetPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->Pos, dst);
    */

    private native float nGetPosX(); /*
        return THIS->Pos.x;
    */

    private native float nGetPosY(); /*
        return THIS->Pos.y;
    */

    private native void nSetPos(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->Pos = value;
    */

    /**
     * Main Area: Size of the viewport.
     */
    public ImVec2 getSize() {
        final ImVec2 dst = new ImVec2();
        nGetSize(dst);
        return dst;
    }

    /**
     * Main Area: Size of the viewport.
     */
    public float getSizeX() {
        return nGetSizeX();
    }

    /**
     * Main Area: Size of the viewport.
     */
    public float getSizeY() {
        return nGetSizeY();
    }

    /**
     * Main Area: Size of the viewport.
     */
    public void getSize(final ImVec2 dst) {
        nGetSize(dst);
    }

    /**
     * Main Area: Size of the viewport.
     */
    public void setSize(final ImVec2 value) {
        nSetSize(value.x, value.y);
    }

    /**
     * Main Area: Size of the viewport.
     */
    public void setSize(final float valueX, final float valueY) {
        nSetSize(valueX, valueY);
    }

    private native void nGetSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->Size, dst);
    */

    private native float nGetSizeX(); /*
        return THIS->Size.x;
    */

    private native float nGetSizeY(); /*
        return THIS->Size.y;
    */

    private native void nSetSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->Size = value;
    */

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
     */
    public ImVec2 getWorkPos() {
        final ImVec2 dst = new ImVec2();
        nGetWorkPos(dst);
        return dst;
    }

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
     */
    public float getWorkPosX() {
        return nGetWorkPosX();
    }

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
     */
    public float getWorkPosY() {
        return nGetWorkPosY();
    }

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
     */
    public void getWorkPos(final ImVec2 dst) {
        nGetWorkPos(dst);
    }

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
     */
    public void setWorkPos(final ImVec2 value) {
        nSetWorkPos(value.x, value.y);
    }

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
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
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
     */
    public ImVec2 getWorkSize() {
        final ImVec2 dst = new ImVec2();
        nGetWorkSize(dst);
        return dst;
    }

    /**
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
     */
    public float getWorkSizeX() {
        return nGetWorkSizeX();
    }

    /**
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
     */
    public float getWorkSizeY() {
        return nGetWorkSizeY();
    }

    /**
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
     */
    public void getWorkSize(final ImVec2 dst) {
        nGetWorkSize(dst);
    }

    /**
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
     */
    public void setWorkSize(final ImVec2 value) {
        nSetWorkSize(value.x, value.y);
    }

    /**
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
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
     * 1.0f = 96 DPI = No extra scale.
     */
    public float getDpiScale() {
        return nGetDpiScale();
    }

    /**
     * 1.0f = 96 DPI = No extra scale.
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
     * (Advanced) 0: no parent. Instruct the platform backend to setup a parent/child relationship between platform windows.
     */
    public int getParentViewportId() {
        return nGetParentViewportId();
    }

    /**
     * (Advanced) 0: no parent. Instruct the platform backend to setup a parent/child relationship between platform windows.
     */
    public void setParentViewportId(final int value) {
        nSetParentViewportId(value);
    }

    private native int nGetParentViewportId(); /*
        return THIS->ParentViewportId;
    */

    private native void nSetParentViewportId(int value); /*
        THIS->ParentViewportId = value;
    */

    /**
     * The ImDrawData corresponding to this viewport. Valid after Render() and until the next call to NewFrame().
     */
    public ImDrawData getDrawData() {
        return new ImDrawData(nGetDrawData());
    }

    /**
     * The ImDrawData corresponding to this viewport. Valid after Render() and until the next call to NewFrame().
     */
    public void setDrawData(final ImDrawData value) {
        nSetDrawData(value.ptr);
    }

    private native long nGetDrawData(); /*
        return (uintptr_t)THIS->DrawData;
    */

    private native void nSetDrawData(long value); /*
        THIS->DrawData = reinterpret_cast<ImDrawData*>(value);
    */

    // Our design separate the Renderer and Platform backends to facilitate combining default backends with each others.
    // When our create your own backend for a custom engine, it is possible that both Renderer and Platform will be handled
    // by the same system and you may not need to use all the UserData/Handle fields.
    // The library never uses those fields, they are merely storage to facilitate backend implementation.

    /**
     * void* to hold custom data structure for the renderer (e.g. swap chain, framebuffers etc.). generally set by your Renderer_CreateWindow function.
     */
    public native void setRendererUserData(Object data); /*
        if (THIS->RendererUserData != NULL) {
            env->DeleteGlobalRef((jobject)THIS->RendererUserData);
        }
        THIS->RendererUserData = (data == NULL ? NULL : (void*)env->NewGlobalRef(data));
    */

    /**
     * void* to hold custom data structure for the renderer (e.g. swap chain, framebuffers etc.). generally set by your Renderer_CreateWindow function.
     */
    public native Object getRendererUserData(); /*
        return (jobject)THIS->RendererUserData;
    */

    /**
     * void* to hold custom data structure for the OS / platform (e.g. windowing info, render context). generally set by your Platform_CreateWindow function.
     */
    public native void setPlatformUserData(Object data); /*
        if (THIS->PlatformUserData != NULL) {
            env->DeleteGlobalRef((jobject)THIS->PlatformUserData);
        }
        THIS->PlatformUserData = (data == NULL ? NULL : (void*)env->NewGlobalRef(data));
    */

    /**
     * void* to hold custom data structure for the OS / platform (e.g. windowing info, render context). generally set by your Platform_CreateWindow function.
     */
    public native Object getPlatformUserData(); /*
        return (jobject)THIS->PlatformUserData;
    */

    /**
     * void* for FindViewportByPlatformHandle(). (e.g. suggested to use natural platform handle such as HWND, GLFWWindow*, SDL_Window*)
     */
    public native void setPlatformHandle(long data); /*
        THIS->PlatformHandle = (void*)data;
    */

    /**
     * void* for FindViewportByPlatformHandle(). (e.g. suggested to use natural platform handle such as HWND, GLFWWindow*, SDL_Window*)
     */
    public native long getPlatformHandle(); /*
        return (uintptr_t)THIS->PlatformHandle;
    */

    /**
     * void* to hold lower-level, platform-native window handle (e.g. the HWND) when using an abstraction layer like GLFW or SDL (where PlatformHandle would be a SDL_Window*)
     */
    public native void setPlatformHandleRaw(long data); /*
        THIS->PlatformHandleRaw = (void*)data;
    */

    /**
     * void* to hold lower-level, platform-native window handle (e.g. the HWND) when using an abstraction layer like GLFW or SDL (where PlatformHandle would be a SDL_Window*)
     */
    public native long getPlatformHandleRaw(); /*
        return (uintptr_t)THIS->PlatformHandleRaw;
    */

    /**
     * Platform window has been created (Platform_CreateWindow() has been called). This is false during the first frame where a viewport is being created.
     */
    public boolean getPlatformWindowCreated() {
        return nGetPlatformWindowCreated();
    }

    /**
     * Platform window has been created (Platform_CreateWindow() has been called). This is false during the first frame where a viewport is being created.
     */
    public void setPlatformWindowCreated(final boolean value) {
        nSetPlatformWindowCreated(value);
    }

    private native boolean nGetPlatformWindowCreated(); /*
        return THIS->PlatformWindowCreated;
    */

    private native void nSetPlatformWindowCreated(boolean value); /*
        THIS->PlatformWindowCreated = value;
    */

    /**
     * Platform window requested move (e.g. window was moved by the OS / host window manager, authoritative position will be OS window position).
     */
    public boolean getPlatformRequestMove() {
        return nGetPlatformRequestMove();
    }

    /**
     * Platform window requested move (e.g. window was moved by the OS / host window manager, authoritative position will be OS window position).
     */
    public void setPlatformRequestMove(final boolean value) {
        nSetPlatformRequestMove(value);
    }

    private native boolean nGetPlatformRequestMove(); /*
        return THIS->PlatformRequestMove;
    */

    private native void nSetPlatformRequestMove(boolean value); /*
        THIS->PlatformRequestMove = value;
    */

    /**
     * Platform window requested resize (e.g. window was resized by the OS / host window manager, authoritative size will be OS window size).
     */
    public boolean getPlatformRequestResize() {
        return nGetPlatformRequestResize();
    }

    /**
     * Platform window requested resize (e.g. window was resized by the OS / host window manager, authoritative size will be OS window size).
     */
    public void setPlatformRequestResize(final boolean value) {
        nSetPlatformRequestResize(value);
    }

    private native boolean nGetPlatformRequestResize(); /*
        return THIS->PlatformRequestResize;
    */

    private native void nSetPlatformRequestResize(boolean value); /*
        THIS->PlatformRequestResize = value;
    */

    /**
     * Platform window requested closure (e.g. window was moved by the OS / host window manager, e.g. pressing ALT-F4).
     */
    public boolean getPlatformRequestClose() {
        return nGetPlatformRequestClose();
    }

    /**
     * Platform window requested closure (e.g. window was moved by the OS / host window manager, e.g. pressing ALT-F4).
     */
    public void setPlatformRequestClose(final boolean value) {
        nSetPlatformRequestClose(value);
    }

    private native boolean nGetPlatformRequestClose(); /*
        return THIS->PlatformRequestClose;
    */

    private native void nSetPlatformRequestClose(boolean value); /*
        THIS->PlatformRequestClose = value;
    */

    // Helpers

    public ImVec2 getCenter() {
        final ImVec2 dst = new ImVec2();
        nGetCenter(dst);
        return dst;
    }

    public float getCenterX() {
        return nGetCenterX();
    }

    public float getCenterY() {
        return nGetCenterY();
    }

    public void getCenter(final ImVec2 dst) {
        nGetCenter(dst);
    }

    private native void nGetCenter(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->GetCenter(), dst);
    */

    private native float nGetCenterX(); /*
        return THIS->GetCenter().x;
    */

    private native float nGetCenterY(); /*
        return THIS->GetCenter().y;
    */

    public ImVec2 getWorkCenter() {
        final ImVec2 dst = new ImVec2();
        nGetWorkCenter(dst);
        return dst;
    }

    public float getWorkCenterX() {
        return nGetWorkCenterX();
    }

    public float getWorkCenterY() {
        return nGetWorkCenterY();
    }

    public void getWorkCenter(final ImVec2 dst) {
        nGetWorkCenter(dst);
    }

    private native void nGetWorkCenter(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->GetWorkCenter(), dst);
    */

    private native float nGetWorkCenterX(); /*
        return THIS->GetWorkCenter().x;
    */

    private native float nGetWorkCenterY(); /*
        return THIS->GetWorkCenter().y;
    */

    /*JNI
        #undef THIS
     */
}
