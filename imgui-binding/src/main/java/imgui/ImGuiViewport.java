package imgui;

import imgui.binding.ImGuiStruct;

/**
 * The viewports created and managed by Dear ImGui. The role of the platform backend is to create the platform/OS windows corresponding to each viewport.
 * - Main Area = entire viewport.
 * - Work Area = entire viewport minus sections optionally used by menu bars, status bars. Some positioning code will prefer to use this. Window are also trying to stay within this area.
 */
public final class ImGuiViewport extends ImGuiStruct {
    private static final ImDrawData DRAW_DATA = new ImDrawData(0);

    public ImGuiViewport(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include "jni_common.h"
        #include "jni_binding_struct.h"

        #define IMGUI_VIEWPORT ((ImGuiViewport*)STRUCT_PTR)
     */

    /**
     * Unique identifier for the viewport.
     */
    public native int getID(); /*
        return IMGUI_VIEWPORT->ID;
    */

    /**
     * Unique identifier for the viewport.
     */
    public native void setID(int imGuiID); /*
        IMGUI_VIEWPORT->ID = imGuiID;
    */

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public native int getFlags(); /*
        return IMGUI_VIEWPORT->Flags;
    */

    /**
     * See {@link imgui.flag.ImGuiViewportFlags}.
     */
    public native void setFlags(int flags); /*
        IMGUI_VIEWPORT->Flags = flags;
    */

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

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public native void getPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_VIEWPORT->Pos, dstImVec2);
    */

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public native float getPosX(); /*
        return IMGUI_VIEWPORT->Pos.x;
    */

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public native float getPosY(); /*
        return IMGUI_VIEWPORT->Pos.y;
    */

    /**
     * Main Area: Position of the viewport (the imgui coordinates are the same as OS desktop/native coordinates).
     */
    public native void setPos(float x, float y); /*
        IMGUI_VIEWPORT->Pos = ImVec2(x, y);
    */

    /**
     * Main Area: Size of the viewport.
     */
    public native void getSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_VIEWPORT->Size, dstImVec2);
    */

    /**
     * Main Area: Size of the viewport.
     */
    public native float getSizeX(); /*
        return IMGUI_VIEWPORT->Size.x;
    */

    /**
     * Main Area: Size of the viewport.
     */
    public native float getSizeY(); /*
        return IMGUI_VIEWPORT->Size.y;
    */

    /**
     * Main Area: Size of the viewport.
     */
    public native void seSize(float x, float y); /*
        IMGUI_VIEWPORT->Size = ImVec2(x, y);
    */

    /**
     * Work Area: Offset from Pos to top-left corner of Work Area. Generally (0,0) or (0,+main_menu_bar_height).
     * Work Area is Full Area but without menu-bars/status-bars (so WorkArea always fit inside Pos/Size!)
     */
    public native void getWorkOffsetMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_VIEWPORT->WorkOffsetMin, dstImVec2);
    */

    /**
     * Work Area: Offset from Pos to top-left corner of Work Area. Generally (0,0) or (0,+main_menu_bar_height).
     * Work Area is Full Area but without menu-bars/status-bars (so WorkArea always fit inside Pos/Size!)
     */
    public native float getWorkOffsetMinX(); /*
        return IMGUI_VIEWPORT->WorkOffsetMin.x;
    */

    /**
     * Work Area: Offset from Pos to top-left corner of Work Area. Generally (0,0) or (0,+main_menu_bar_height).
     * Work Area is Full Area but without menu-bars/status-bars (so WorkArea always fit inside Pos/Size!)
     */
    public native float getWorkOffsetMinY(); /*
        return IMGUI_VIEWPORT->WorkOffsetMin.y;
    */

    /**
     * Work Area: Offset from Pos to top-left corner of Work Area. Generally (0,0) or (0,+main_menu_bar_height).
     * Work Area is Full Area but without menu-bars/status-bars (so WorkArea always fit inside Pos/Size!)
     */
    public native void seWorkOffsetMin(float x, float y); /*
        IMGUI_VIEWPORT->WorkOffsetMin = ImVec2(x, y);
    */

    /**
     * Work Area: Offset from Pos+Size to bottom-right corner of Work Area. Generally (0,0) or (0,-status_bar_height).
     */
    public native void getWorkOffsetMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IMGUI_VIEWPORT->WorkOffsetMax, dstImVec2);
    */

    /**
     * Work Area: Offset from Pos+Size to bottom-right corner of Work Area. Generally (0,0) or (0,-status_bar_height).
     */
    public native float getWorkOffsetMaxX(); /*
        return IMGUI_VIEWPORT->WorkOffsetMax.x;
    */

    /**
     * Work Area: Offset from Pos+Size to bottom-right corner of Work Area. Generally (0,0) or (0,-status_bar_height).
     */
    public native float getWorkOffsetMaxY(); /*
        return IMGUI_VIEWPORT->WorkOffsetMax.y;
    */

    /**
     * Work Area: Offset from Pos+Size to bottom-right corner of Work Area. Generally (0,0) or (0,-status_bar_height).
     */
    public native void seWorkOffsetMax(float x, float y); /*
        IMGUI_VIEWPORT->WorkOffsetMax = ImVec2(x, y);
    */

    /**
     * 1.0f = 96 DPI = No extra scale.
     */
    public native float getDpiScale(); /*
        return IMGUI_VIEWPORT->DpiScale;
    */

    /**
     * 1.0f = 96 DPI = No extra scale.
     */
    public native void setDpiScale(float dpiScale); /*
        IMGUI_VIEWPORT->DpiScale = dpiScale;
    */

    /**
     * The ImDrawData corresponding to this viewport. Valid after Render() and until the next call to NewFrame().
     */
    public ImDrawData getDrawData() {
        DRAW_DATA.ptr = nGetDrawData();
        return DRAW_DATA;
    }

    private native long nGetDrawData(); /*
        return (intptr_t)IMGUI_VIEWPORT->DrawData;
    */

    /**
     * (Advanced) 0: no parent. Instruct the platform backend to setup a parent/child relationship between platform windows.
     */
    public native int getParentViewportId(); /*
        return IMGUI_VIEWPORT->ParentViewportId;
    */

    /**
     * (Advanced) 0: no parent. Instruct the platform backend to setup a parent/child relationship between platform windows.
     */
    public native void setParentViewportId(int parentViewportId); /*
        IMGUI_VIEWPORT->ParentViewportId = parentViewportId;
    */

    // Our design separate the Renderer and Platform backends to facilitate combining default backends with each others.
    // When our create your own backend for a custom engine, it is possible that both Renderer and Platform will be handled
    // by the same system and you may not need to use all the UserData/Handle fields.
    // The library never uses those fields, they are merely storage to facilitate backend implementation.

    /**
     * void* to hold custom data structure for the renderer (e.g. swap chain, framebuffers etc.). generally set by your Renderer_CreateWindow function.
     */
    public native void setRendererUserData(Object data); /*
        if (IMGUI_VIEWPORT->RendererUserData != NULL) {
            env->DeleteGlobalRef((jobject)IMGUI_VIEWPORT->RendererUserData);
        }
        IMGUI_VIEWPORT->RendererUserData = (data == NULL ? NULL : (void*)env->NewGlobalRef(data));
    */

    /**
     * void* to hold custom data structure for the renderer (e.g. swap chain, framebuffers etc.). generally set by your Renderer_CreateWindow function.
     */
    public native Object getRendererUserData(); /*
        return (jobject)IMGUI_VIEWPORT->RendererUserData;
    */

    /**
     * void* to hold custom data structure for the OS / platform (e.g. windowing info, render context). generally set by your Platform_CreateWindow function.
     */
    public native void setPlatformUserData(Object data); /*
        if (IMGUI_VIEWPORT->PlatformUserData != NULL) {
            env->DeleteGlobalRef((jobject)IMGUI_VIEWPORT->PlatformUserData);
        }
        IMGUI_VIEWPORT->PlatformUserData = (data == NULL ? NULL : (void*)env->NewGlobalRef(data));
    */

    /**
     * void* to hold custom data structure for the OS / platform (e.g. windowing info, render context). generally set by your Platform_CreateWindow function.
     */
    public native Object getPlatformUserData(); /*
        return (jobject)IMGUI_VIEWPORT->PlatformUserData;
    */

    /**
     * void* for FindViewportByPlatformHandle(). (e.g. suggested to use natural platform handle such as HWND, GLFWWindow*, SDL_Window*)
     */
    public native void setPlatformHandle(long data); /*
        IMGUI_VIEWPORT->PlatformHandle = (void*)data;
    */

    /**
     * void* for FindViewportByPlatformHandle(). (e.g. suggested to use natural platform handle such as HWND, GLFWWindow*, SDL_Window*)
     */
    public native long getPlatformHandle(); /*
        return (intptr_t)IMGUI_VIEWPORT->PlatformHandle;
    */

    /**
     * void* to hold lower-level, platform-native window handle (e.g. the HWND) when using an abstraction layer like GLFW or SDL (where PlatformHandle would be a SDL_Window*)
     */
    public native void setPlatformHandleRaw(long data); /*
        IMGUI_VIEWPORT->PlatformHandleRaw = (void*)data;
    */

    /**
     * void* to hold lower-level, platform-native window handle (e.g. the HWND) when using an abstraction layer like GLFW or SDL (where PlatformHandle would be a SDL_Window*)
     */
    public native long getPlatformHandleRaw(); /*
        return (intptr_t)IMGUI_VIEWPORT->PlatformHandleRaw;
    */

    /**
     * Platform window requested move (e.g. window was moved by the OS / host window manager, authoritative position will be OS window position).
     */
    public native boolean getPlatformRequestMove(); /*
        return IMGUI_VIEWPORT->PlatformRequestMove;
    */

    /**
     * Platform window requested move (e.g. window was moved by the OS / host window manager, authoritative position will be OS window position).
     */
    public native void setPlatformRequestMove(boolean platformRequestMove); /*
        IMGUI_VIEWPORT->PlatformRequestMove = platformRequestMove;
    */

    /**
     * Platform window requested resize (e.g. window was resized by the OS / host window manager, authoritative size will be OS window size).
     */
    public native boolean getPlatformRequestResize(); /*
        return IMGUI_VIEWPORT->PlatformRequestResize;
    */

    /**
     * Platform window requested resize (e.g. window was resized by the OS / host window manager, authoritative size will be OS window size).
     */
    public native void setPlatformRequestResize(boolean platformRequestResize); /*
        IMGUI_VIEWPORT->PlatformRequestResize = platformRequestResize;
    */

    /**
     * Platform window requested closure (e.g. window was moved by the OS / host window manager, e.g. pressing ALT-F4).
     */
    public native boolean getPlatformRequestClose(); /*
        return IMGUI_VIEWPORT->PlatformRequestClose;
    */

    /**
     * Platform window requested closure (e.g. window was moved by the OS / host window manager, e.g. pressing ALT-F4).
     */
    public native void setPlatformRequestClose(boolean platformRequestClose); /*
        IMGUI_VIEWPORT->PlatformRequestClose = platformRequestClose;
    */

    // Access work-area rectangle with GetWorkXXX functions (see comments above)

    public native void getCenter(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, IMGUI_VIEWPORT->GetCenter(), dstImVec2);
    */

    public native float getCenterX(); /*
        return IMGUI_VIEWPORT->GetCenter().x;
    */

    public native float getCenterY(); /*
        return IMGUI_VIEWPORT->GetCenter().y;
    */

    public native void getWorkPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, IMGUI_VIEWPORT->GetWorkPos(), dstImVec2);
    */

    public native float getWorkPosX(); /*
        return IMGUI_VIEWPORT->GetWorkPos().x;
    */

    public native float getWorkPosY(); /*
        return IMGUI_VIEWPORT->GetWorkPos().y;
    */

    public native void getWorkSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, IMGUI_VIEWPORT->GetWorkSize(), dstImVec2);
    */

    public native float getWorkSizeX(); /*
        return IMGUI_VIEWPORT->GetWorkSize().x;
    */

    public native float getWorkSizeY(); /*
        return IMGUI_VIEWPORT->GetWorkSize().y;
    */
}
