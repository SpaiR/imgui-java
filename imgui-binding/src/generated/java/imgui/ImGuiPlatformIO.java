package imgui;

import imgui.binding.ImGuiStruct;
import imgui.callback.ImPlatformFuncViewport;
import imgui.callback.ImPlatformFuncViewportFloat;
import imgui.callback.ImPlatformFuncViewportImVec2;
import imgui.callback.ImPlatformFuncViewportString;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;
import imgui.callback.ImPlatformFuncViewportSuppFloat;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;

/**
 * -----------------------------------------------------------------------------
 * [BETA] Platform interface for multi-viewport support
 * -----------------------------------------------------------------------------
 * (Optional) This is completely optional, for advanced users!
 * If you are new to Dear ImGui and trying to integrate it into your engine, you can probably ignore this for now.
 * This feature allows you to seamlessly drag Dear ImGui windows outside of your application viewport.
 * This is achieved by creating new Platform/OS windows on the fly, and rendering into them.
 * Dear ImGui manages the viewport structures, and the backend create and maintain one Platform/OS window for each of those viewports.
 * See Glossary https://github.com/ocornut/imgui/wiki/Glossary for details about some of the terminology.
 * See Thread https://github.com/ocornut/imgui/issues/1542 for gifs, news and questions about this evolving feature.
 * About the coordinates system:
 * - When multi-viewports are enabled, all Dear ImGui coordinates become absolute coordinates (same as OS coordinates!)
 * - So e.g. ImGui::SetNextWindowPos(ImVec2(0,0)) will position a window relative to your primary monitor!
 * - If you want to position windows relative to your main application viewport, use ImGui::GetMainViewport().Pos as a base position.
 * Steps to use multi-viewports in your application, when using a default backend from the examples/ folder:
 * - Application:  Enable feature with 'io.ConfigFlags |= ImGuiConfigFlags_ViewportsEnable'.
 * - Backend:     The backend initialization will setup all necessary ImGuiPlatformIO's functions and update monitors info every frame.
 * - Application:  In your main loop, call ImGui::UpdatePlatformWindows(), ImGui::RenderPlatformWindowsDefault() after EndFrame() or Render().
 * - Application:  Fix absolute coordinates used in ImGui::SetWindowPos() or ImGui::SetNextWindowPos() calls.
 * Steps to use multi-viewports in your application, when using a custom backend:
 * - Important:    THIS IS NOT EASY TO DO and comes with many subtleties not described here!
 * It's also an experimental feature, so some of the requirements may evolve.
 * Consider using default backends if you can. Either way, carefully follow and refer to examples/ backends for details.
 * - Application:  Enable feature with 'io.ConfigFlags |= ImGuiConfigFlags_ViewportsEnable'.
 * - Backend:     Hook ImGuiPlatformIO's Platform_* and Renderer_* callbacks (see below).
 * Set 'io.BackendFlags |= ImGuiBackendFlags_PlatformHasViewports' and 'io.BackendFlags |= ImGuiBackendFlags_PlatformHasViewports'.
 * Update ImGuiPlatformIO's Monitors list every frame.
 * Update MousePos every frame, in absolute coordinates.
 * - Application:  In your main loop, call ImGui::UpdatePlatformWindows(), ImGui::RenderPlatformWindowsDefault() after EndFrame() or Render().
 * You may skip calling RenderPlatformWindowsDefault() if its API is not convenient for your needs. Read comments below.
 * - Application:  Fix absolute coordinates used in ImGui::SetWindowPos() or ImGui::SetNextWindowPos() calls.
 * About ImGui::RenderPlatformWindowsDefault():
 * - This function is a mostly a _helper_ for the common-most cases, and to facilitate using default backends.
 * - You can check its simple source code to understand what it does.
 * It basically iterates secondary viewports and call 4 functions that are setup in ImGuiPlatformIO, if available:
 * Platform_RenderWindow(), Renderer_RenderWindow(), Platform_SwapBuffers(), Renderer_SwapBuffers()
 * Those functions pointers exists only for the benefit of RenderPlatformWindowsDefault().
 * - If you have very specific rendering needs (e.g. flipping multiple swap-chain simultaneously, unusual sync/threading issues, etc.),
 * you may be tempted to ignore RenderPlatformWindowsDefault() and write customized code to perform your renderingg.
 * You may decide to setup the platform_io's *RenderWindow and *SwapBuffers pointers and call your functions through those pointers,
 * or you may decide to never setup those pointers and call your code directly. They are a convenience, not an obligatory interface.
 * -----------------------------------------------------------------------------
 * (Optional) Access via ImGui::GetPlatformIO()
 * <p>
 * ------------------------------------------------------------------
 * Input - Backend interface/functions + Monitor List
 * ------------------------------------------------------------------
 * (Optional) Platform functions (e.g. Win32, GLFW, SDL2)
 * For reference, the second column shows which function are generally calling the Platform Functions:
 * N = ImGui::NewFrame()                        ~ beginning of the dear imgui frame: read info from platform/OS windows (latest size/position)
 * F = ImGui::Begin(), ImGui::EndFrame()        ~ during the dear imgui frame
 * U = ImGui::UpdatePlatformWindows()           ~ after the dear imgui frame: create and update all platform/OS windows
 * R = ImGui::RenderPlatformWindowsDefault()    ~ render
 * D = ImGui::DestroyPlatformWindows()          ~ shutdown
 * The general idea is that NewFrame() we will read the current Platform/OS state, and UpdatePlatformWindows() will write to it.
 * The functions are designed so we can mix and match 2 imgui_impl_xxxx files, one for the Platform (~window/input handling), one for Renderer.
 * Custom engine backends will often provide both Platform and Renderer interfaces and so may not need to use all functions.
 * Platform functions are typically called before their Renderer counterpart, apart from Destroy which are called the other way.
 */
public final class ImGuiPlatformIO extends ImGuiStruct {
    private static final ImGuiViewport TMP_VIEWPORT = new ImGuiViewport(0);
    private static final ImGuiPlatformMonitor TMP_MONITOR = new ImGuiPlatformMonitor(0);
    private static final ImVec2 TMP_IM_VEC2 = new ImVec2();

    public ImGuiPlatformIO(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IMGUI_PLATFORM_IO ((ImGuiPlatformIO*)STRUCT_PTR)

        jobject jTmpViewport = NULL;
        jobject jTmpImVec2 = NULL;
     */

    static void init() {
        nInit(TMP_VIEWPORT, TMP_IM_VEC2);
    }

    private static native void nInit(ImGuiViewport tmpViewport, ImVec2 tmpImVec2); /*
        jTmpViewport = env->NewGlobalRef(tmpViewport);
        jTmpImVec2 = env->NewGlobalRef(tmpImVec2);
    */

    /*JNI
        #define IM_PLATFORM_FUNC_VIEWPORT_TMPL(name)\
            jobject platformCallback##name = NULL;\
            void PlatformStub##name(ImGuiViewport* vp) {\
                if (platformCallback##name != NULL) {\
                    JNIEnv* env = Jni::GetEnv();\
                    env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);\
                    Jni::CallImPlatformFuncViewport(env, platformCallback##name, jTmpViewport);\
                }\
            }\
            void PlatformStub##name(ImGuiViewport* vp, void*) {\
                PlatformStub##name(vp);\
            }

        IM_PLATFORM_FUNC_VIEWPORT_TMPL(CreateWindow)

        #define IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(name)\
            if (platformCallback##name != NULL) {\
                env->DeleteGlobalRef(platformCallback##name);\
            }\
            platformCallback##name = env->NewGlobalRef(func);\
            IMGUI_PLATFORM_IO->Platform_##name = PlatformStub##name;
     */

    /**
     *  // . . U . .  // Create a new platform window for the given viewport.
     */
    public native void setPlatformCreateWindow(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(CreateWindow)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(DestroyWindow)
     */

    /**
     *  // N . U . D  //
     */
    public native void setPlatformDestroyWindow(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(DestroyWindow)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(ShowWindow)
     */

    /**
     *  // . . U . .  // Newly created windows are initially hidden so SetWindowPos/Size/Title can be called on them before showing the window.
     */
    public native void setPlatformShowWindow(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(ShowWindow)
    */

    /*JNI
        #define IM_PLATFORM_FUNC_VIEWPORT_IM_VEC2_TMPL(name)\
            jobject platformCallback##name = NULL;\
            void PlatformStub##name(ImGuiViewport* vp, ImVec2 pos) {\
                if (platformCallback##name != NULL) {\
                    JNIEnv* env = Jni::GetEnv();\
                    env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);\
                    Jni::ImVec2Cpy(env, pos, jTmpImVec2);\
                    Jni::CallImPlatformFuncViewportImVec2(env, platformCallback##name, jTmpViewport, jTmpImVec2);\
                }\
            }

        IM_PLATFORM_FUNC_VIEWPORT_IM_VEC2_TMPL(SetWindowPos)

        #define IM_PLATFORM_FUNC_VIEWPORT_IM_VEC2_METHOD_TMPL(name)\
            if (platformCallback##name != NULL) {\
                env->DeleteGlobalRef(platformCallback##name);\
            }\
            platformCallback##name = env->NewGlobalRef(func);\
            IMGUI_PLATFORM_IO->Platform_##name = PlatformStub##name;
     */

    /**
     *  // . . U . .  // Set platform window position (given the upper-left corner of client area).
     */
    public native void setPlatformSetWindowPos(ImPlatformFuncViewportImVec2 func); /*
        IM_PLATFORM_FUNC_VIEWPORT_IM_VEC2_METHOD_TMPL(SetWindowPos)
    */

    /*JNI
        #define IM_PLATFORM_FUNC_VIEWPORT_SUPP_IM_VEC2_TMPL(name)\
            jobject platformCallback##name = NULL;\
            ImVec2 PlatformStub##name(ImGuiViewport* vp) {\
                ImVec2 dst;\
                if (platformCallback##name != NULL) {\
                    JNIEnv* env = Jni::GetEnv();\
                    env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);\
                    Jni::CallImPlatformFuncViewportSuppImVec2(env, platformCallback##name, jTmpViewport, jTmpImVec2);\
                    Jni::ImVec2Cpy(env, jTmpImVec2, &dst);\
                }\
                return dst;\
            }

        IM_PLATFORM_FUNC_VIEWPORT_SUPP_IM_VEC2_TMPL(GetWindowPos)

        #define IM_PLATFORM_FUNC_VIEWPORT_SUPP_IM_VEC2_METHOD_TMPL(name)\
            if (platformCallback##name != NULL) {\
                env->DeleteGlobalRef(platformCallback##name);\
            }\
            platformCallback##name = env->NewGlobalRef(func);\
            IMGUI_PLATFORM_IO->Platform_##name = PlatformStub##name;
     */

    /**
     *  // N . . . .  //
     */
    public native void setPlatformGetWindowPos(ImPlatformFuncViewportSuppImVec2 func); /*
        IM_PLATFORM_FUNC_VIEWPORT_SUPP_IM_VEC2_METHOD_TMPL(GetWindowPos)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_IM_VEC2_TMPL(SetWindowSize)
     */

    /**
     *  // . . U . .  // Set platform window client area size (ignoring OS decorations such as OS title bar etc.)
     */
    public native void setPlatformSetWindowSize(ImPlatformFuncViewportImVec2 func); /*
        IM_PLATFORM_FUNC_VIEWPORT_IM_VEC2_METHOD_TMPL(SetWindowSize)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_SUPP_IM_VEC2_TMPL(GetWindowSize)
     */

    /**
     *  // N . . . .  // Get platform window client area size
     */
    public native void setPlatformGetWindowSize(ImPlatformFuncViewportSuppImVec2 func); /*
        IM_PLATFORM_FUNC_VIEWPORT_SUPP_IM_VEC2_METHOD_TMPL(GetWindowSize)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(SetWindowFocus)
     */

    /**
     *  // N . . . .  // Move window to front and set input focus.
     */
    public native void setPlatformSetWindowFocus(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(SetWindowFocus)
    */

    /*JNI
        #define IM_PLATFORM_FUNC_VIEWPORT_SUPP_BOOLEAN_TMPL(name)\
            jobject platformCallback##name = NULL;\
            bool PlatformStub##name(ImGuiViewport* vp) {\
                if (platformCallback##name != NULL) {\
                    JNIEnv* env = Jni::GetEnv();\
                    env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);\
                    return Jni::CallImPlatformFuncViewportSuppBoolean(env, platformCallback##name, jTmpViewport);\
                }\
                return false;\
            }

        IM_PLATFORM_FUNC_VIEWPORT_SUPP_BOOLEAN_TMPL(GetWindowFocus)

        #define IM_PLATFORM_FUNC_VIEWPORT_SUPP_BOOLEAN_METHOD_TMPL(name)\
            if (platformCallback##name != NULL) {\
                env->DeleteGlobalRef(platformCallback##name);\
            }\
            platformCallback##name = env->NewGlobalRef(func);\
            IMGUI_PLATFORM_IO->Platform_##name = PlatformStub##name;
     */

    /**
     *  // . . U . .  //
     */
    public native void setPlatformGetWindowFocus(ImPlatformFuncViewportSuppBoolean func); /*
        IM_PLATFORM_FUNC_VIEWPORT_SUPP_BOOLEAN_METHOD_TMPL(GetWindowFocus)
    */

     /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_SUPP_BOOLEAN_TMPL(GetWindowMinimized)
     */

    /**
     *  // N . . . .  // Get platform window minimized state. When minimized, we generally won't attempt to get/set size and contents will be culled more easily.
     */
    public native void setPlatformGetWindowMinimized(ImPlatformFuncViewportSuppBoolean func); /*
        IM_PLATFORM_FUNC_VIEWPORT_SUPP_BOOLEAN_METHOD_TMPL(GetWindowMinimized)
    */

    /*JNI
        jobject platformCallbackSetWindowTitle = NULL;
        void PlatformStubSetWindowTitle(ImGuiViewport* vp, const char* str) {
            if (platformCallbackSetWindowTitle != NULL) {
                JNIEnv* env = Jni::GetEnv();
                env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);
                Jni::CallImPlatformFuncViewportString(env, platformCallbackSetWindowTitle, jTmpViewport, str);
            }
        }
     */

    /**
     *  // . . U . .  // Set platform window title (given an UTF-8 string)
     */
    public native void setPlatformSetWindowTitle(ImPlatformFuncViewportString func); /*
        if (platformCallbackSetWindowTitle != NULL) {
            env->DeleteGlobalRef(platformCallbackSetWindowTitle);
        }
        platformCallbackSetWindowTitle = env->NewGlobalRef(func);
        IMGUI_PLATFORM_IO->Platform_SetWindowTitle = PlatformStubSetWindowTitle;
    */

    /*JNI
        jobject platformCallbackSetWindowAlpha = NULL;
        void PlatformStubSetWindowAlpha(ImGuiViewport* vp, float alpha) {
            if (platformCallbackSetWindowAlpha != NULL) {
                JNIEnv* env = Jni::GetEnv();
                env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);
                Jni::CallImPlatformFuncViewportFloat(env, platformCallbackSetWindowAlpha, jTmpViewport, alpha);
            }
        }
     */

    /**
     *  // . . U . .  // (Optional) Setup window transparency
     */
    public native void setPlatformSetWindowAlpha(ImPlatformFuncViewportFloat func); /*
        if (platformCallbackSetWindowAlpha != NULL) {
            env->DeleteGlobalRef(platformCallbackSetWindowAlpha);
        }
        platformCallbackSetWindowAlpha = env->NewGlobalRef(func);
        IMGUI_PLATFORM_IO->Platform_SetWindowAlpha = PlatformStubSetWindowAlpha;
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(UpdateWindow)
     */

    /**
     *  // . . U . .  // (Optional) Called by UpdatePlatformWindows(). Optional hook to allow the platform backend from doing general book-keeping every frame.
     */
    public native void setPlatformUpdateWindow(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(UpdateWindow)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(RenderWindow)
     */

    /**
     *  // . . . R .  // (Optional) Main rendering (platform side! This is often unused, or just setting a "current" context for OpenGL bindings).
     */
    public native void setPlatformRenderWindow(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(RenderWindow)
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(SwapBuffers)
     */

    /**
     *  // . . . R .  // (Optional) Call Present/SwapBuffers (platform side! This is often unused!).
     */
    public native void setPlatformSwapBuffers(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(SwapBuffers)
    */

    /*JNI
        jobject platformCallbackGetWindowDpiScale = NULL;
        float PlatformStubGetWindowDpiScale(ImGuiViewport* vp) {
            if (platformCallbackGetWindowDpiScale != NULL) {
                JNIEnv* env = Jni::GetEnv();
                env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);
                return Jni::CallImPlatformFuncViewportSuppFloat(env, platformCallbackGetWindowDpiScale, jTmpViewport);
            }
            return false;
        }
     */

    /**
     *  // N . . . .  // (Optional) [BETA] FIXME-DPI: DPI handling: Return DPI scale for this viewport. 1.0f = 96 DPI.
     */
    public native void setPlatformGetWindowDpiScale(ImPlatformFuncViewportSuppFloat func); /*
         if (platformCallbackGetWindowDpiScale != NULL) {
            env->DeleteGlobalRef(platformCallbackGetWindowDpiScale);
        }
        platformCallbackGetWindowDpiScale = env->NewGlobalRef(func);
        IMGUI_PLATFORM_IO->Platform_GetWindowDpiScale = PlatformStubGetWindowDpiScale;
    */

    /*JNI
        IM_PLATFORM_FUNC_VIEWPORT_TMPL(OnChangedViewport)
     */

    /**
     *  // (Optional) [BETA] FIXME-DPI: DPI handling: Called during Begin() every time the viewport we are outputting into changes, so backend has a chance to swap fonts to adjust style.
     */
    public native void setPlatformOnChangedViewport(ImPlatformFuncViewport func); /*
        IM_PLATFORM_FUNC_VIEWPORT_METHOD_TMPL(OnChangedViewport)
    */

    // (Optional) Renderer functions (e.g. DirectX, OpenGL, Vulkan)

    /*JNI
        #define IM_RENDERER_FUNC_VIEWPORT_TMPL(name)\
            jobject rendererCallback##name = NULL;\
            void RendererStub##name(ImGuiViewport* vp) {\
                if (rendererCallback##name != NULL) {\
                    JNIEnv* env = Jni::GetEnv();\
                    env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);\
                    Jni::CallImPlatformFuncViewport(env, rendererCallback##name, jTmpViewport);\
                }\
            }\
            void RendererStub##name(ImGuiViewport* vp, void*) {\
                RendererStub##name(vp);\
            }

        IM_RENDERER_FUNC_VIEWPORT_TMPL(CreateWindow)

        #define IM_RENDERER_FUNC_VIEWPORT_METHOD_TMPL(name)\
            if (rendererCallback##name != NULL) {\
                env->DeleteGlobalRef(rendererCallback##name);\
            }\
            rendererCallback##name = env->NewGlobalRef(func);\
            IMGUI_PLATFORM_IO->Renderer_##name = RendererStub##name;
     */

    /**
     *  // . . U . .  // Create swap chain, frame buffers etc. (called after Platform_CreateWindow)
     */
    public native void setRendererCreateWindow(ImPlatformFuncViewport func); /*
        IM_RENDERER_FUNC_VIEWPORT_METHOD_TMPL(CreateWindow)
    */

    /*JNI
        IM_RENDERER_FUNC_VIEWPORT_TMPL(DestroyWindow)
     */

    /**
     *  // N . U . D  // Destroy swap chain, frame buffers etc. (called before Platform_DestroyWindow)
     */
    public native void setRendererDestroyWindow(ImPlatformFuncViewport func); /*
        IM_RENDERER_FUNC_VIEWPORT_METHOD_TMPL(DestroyWindow)
    */

    /*JNI
        jobject rendererCallbackSetWindowSize = NULL;
        void RendererStubSetWindowSize(ImGuiViewport* vp, ImVec2 pos) {
            if (rendererCallbackSetWindowSize != NULL) {
                JNIEnv* env = Jni::GetEnv();
                env->SetLongField(jTmpViewport, Jni::GetBindingStructPtrID(), (intptr_t)vp);
                Jni::ImVec2Cpy(env, pos, jTmpImVec2);
                Jni::CallImPlatformFuncViewportImVec2(env, rendererCallbackSetWindowSize, jTmpViewport, jTmpImVec2);
            }
        }
     */

    /**
     *  // . . U . .  // Resize swap chain, frame buffers etc. (called after Platform_SetWindowSize)
     */
    public native void setRendererSetWindowPos(ImPlatformFuncViewportImVec2 func); /*
        if (rendererCallbackSetWindowSize != NULL) {
            env->DeleteGlobalRef(rendererCallbackSetWindowSize);
        }
        rendererCallbackSetWindowSize = env->NewGlobalRef(func);
        IMGUI_PLATFORM_IO->Renderer_SetWindowSize = RendererStubSetWindowSize;
    */

    /*JNI
        IM_RENDERER_FUNC_VIEWPORT_TMPL(RenderWindow)
     */

    /**
     *  // . . . R .  // (Optional) Clear framebuffer, setup render target,
     *  then render the viewport.DrawData. 'render_arg' is the value passed to RenderPlatformWindowsDefault().
     */
    public native void setRendererRenderWindow(ImPlatformFuncViewport func); /*
        IM_RENDERER_FUNC_VIEWPORT_METHOD_TMPL(RenderWindow)
    */

    /*JNI
        IM_RENDERER_FUNC_VIEWPORT_TMPL(SwapBuffers)
     */

    /**
     *  // . . . R .  // (Optional) Call Present/SwapBuffers. 'render_arg' is the value passed to RenderPlatformWindowsDefault().
     */
    public native void setRendererSwapBuffers(ImPlatformFuncViewport func); /*
        IM_RENDERER_FUNC_VIEWPORT_METHOD_TMPL(SwapBuffers)
    */

    // (Optional) Monitor list
    // - Updated by: app/backend. Update every frame to dynamically support changing monitor or DPI configuration.
    // - Used by: dear imgui to query DPI info, clamp popups/tooltips within same monitor and not have them straddle monitors.

    //------------------------------------------------------------------
    // Output - List of viewports to render into platform windows
    //------------------------------------------------------------------

    public native void resizeMonitors(int size); /*
        IMGUI_PLATFORM_IO->Monitors.resize(0);
    */

    public native int getMonitorsSize(); /*
        return IMGUI_PLATFORM_IO->Monitors.Size;
    */

    public native void pushMonitors(float mainPosX, float mainPosY, float mainSizeX, float mainSizeY, float workPosX, float workPosY, float workSizeX, float workSizeY, float dpiScale); /*
        ImGuiPlatformMonitor monitor;

        monitor.MainPos = ImVec2(mainPosX, mainPosY);
        monitor.MainSize = ImVec2(mainSizeX, mainSizeY);

        monitor.WorkPos = ImVec2(workPosX, workPosY);
        monitor.WorkSize = ImVec2(workSizeX, workSizeY);

        monitor.DpiScale = dpiScale;

        IMGUI_PLATFORM_IO->Monitors.push_back(monitor);
    */

    public ImGuiPlatformMonitor getMonitors(final int idx) {
        TMP_MONITOR.ptr = nGetMonitors(idx);
        return TMP_MONITOR;
    }

    private native long nGetMonitors(int idx); /*
        return (intptr_t)&IMGUI_PLATFORM_IO->Monitors[idx];
    */

    // Viewports list (the list is updated by calling ImGui::EndFrame or ImGui::Render)
    // (in the future we will attempt to organize this feature to remove the need for a "main viewport")

    public native int getViewportsSize(); /*
        return IMGUI_PLATFORM_IO->Viewports.Size;
    */

    public ImGuiViewport getViewports(final int idx) {
        TMP_VIEWPORT.ptr = nGetViewports(idx);
        return TMP_VIEWPORT;
    }

    private native long nGetViewports(int idx); /*
        return (intptr_t)IMGUI_PLATFORM_IO->Viewports[idx];
    */
}
