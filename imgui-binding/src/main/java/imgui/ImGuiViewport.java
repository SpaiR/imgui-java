package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ReturnValue;

/**
 * The viewports created and managed by Dear ImGui. The role of the platform backend is to create the platform/OS windows corresponding to each viewport.
 * - Main Area = entire viewport.
 * - Work Area = entire viewport minus sections optionally used by menu bars, status bars. Some positioning code will prefer to use this. Window are also trying to stay within this area.
 */
@BindingSource
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
    @BindingField
    public int ID;

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    @BindingField(isFlag = true)
    public int Flags;

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    @BindingField
    public ImVec2 Pos;

    /**
     * Main Area: Size of the viewport.
     */
    @BindingField
    public ImVec2 Size;

    /**
     * Work Area: Position of the viewport minus task bars, menus bars, status bars.
     */
    @BindingField
    public ImVec2 WorkPos;

    /**
     * Work Area: Size of the viewport minus task bars, menu bars, status bars.
     */
    @BindingField
    public ImVec2 WorkSize;

    /**
     * 1.0f = 96 DPI = No extra scale.
     */
    @BindingField
    public float DpiScale;

    /**
     * (Advanced) 0: no parent. Instruct the platform backend to setup a parent/child relationship between platform windows.
     */
    @BindingField
    public int ParentViewportId;

    /**
     * The ImDrawData corresponding to this viewport. Valid after Render() and until the next call to NewFrame().
     */
    @BindingField
    public ImDrawData DrawData;

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
     * Platform window requested move (e.g. window was moved by the OS / host window manager, authoritative position will be OS window position).
     */
    @BindingField
    public boolean PlatformRequestMove;

    /**
     * Platform window requested resize (e.g. window was resized by the OS / host window manager, authoritative size will be OS window size).
     */
    @BindingField
    public boolean PlatformRequestResize;

    /**
     * Platform window requested closure (e.g. window was moved by the OS / host window manager, e.g. pressing ALT-F4).
     */
    @BindingField
    public boolean PlatformRequestClose;

    // Helpers

    @BindingMethod
    public native ImVec2 GetCenter();

    @BindingMethod
    public native ImVec2 GetWorkCenter();

    /*JNI
        #undef THIS
     */
}
