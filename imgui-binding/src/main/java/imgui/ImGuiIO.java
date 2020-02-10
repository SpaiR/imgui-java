package imgui;

import imgui.callbacks.ImStrConsumer;
import imgui.callbacks.ImStrSupplier;

/**
 * Communicate most settings and inputs/outputs to Dear ImGui using this structure.
 * Access via ImGui::GetIO(). Read 'Programmer guide' section in .cpp file for general usage.
 */
public final class ImGuiIO {
    private ImFontAtlas imFontAtlas;

    ImGuiIO() {
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include "jni_common.h"
        #include "jni_callbacks.h"
     */

    //------------------------------------------------------------------
    // Configuration (fill once)
    //------------------------------------------------------------------

    // ConfigFlags
    // See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
    //
    public native int getConfigFlags(); /*
        return ImGui::GetIO().ConfigFlags;
    */

    public native void setConfigFlags(int configFlags); /*
        ImGui::GetIO().ConfigFlags = configFlags;
    */

    // BackendFlags
    // See ImGuiBackendFlags enum. Set by back-end to communicate features supported by the back-end.
    //
    public native int getBackendFlags(); /*
        return ImGui::GetIO().BackendFlags;
    */

    public native void setBackendFlags(int backendFlags); /*
        ImGui::GetIO().BackendFlags = backendFlags;
    */


    // IniSavingRate
    // Minimum time between saving positions/sizes to .ini file, in seconds.
    //
    public native float getIniSavingRate(); /*
        return ImGui::GetIO().IniSavingRate;
    */

    public native void setIniSavingRate(float iniSavingRate); /*
        ImGui::GetIO().IniSavingRate = iniSavingRate;
    */

    // IniFilename
    // Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
    //
    public native String getIniFilename(); /*
        return env->NewStringUTF(ImGui::GetIO().IniFilename);
    */

    public native void setIniFilename(String iniFilename); /*MANUAL
        ImGui::GetIO().IniFilename = obj_iniFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniFilename, JNI_FALSE);
    */

    // LogFilename
    // Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
    //
    public native String getLogFilename(); /*
        return env->NewStringUTF(ImGui::GetIO().LogFilename);
    */

    public native void setLogFilename(String logFilename); /*MANUAL
        ImGui::GetIO().LogFilename = obj_logFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_logFilename, JNI_FALSE);
    */

    // MouseDoubleClickTime
    // Time for a double-click, in seconds.
    //
    public native float getMouseDoubleClickTime(); /*
        return ImGui::GetIO().MouseDoubleClickTime;
    */

    public native void setMouseDoubleClickTime(float mouseDoubleClickTime); /*
        ImGui::GetIO().MouseDoubleClickTime = mouseDoubleClickTime;
    */

    // MouseDoubleClickTime
    // Distance threshold to stay in to validate a double-click, in pixels.
    //
    public native float getMouseDoubleClickMaxDist(); /*
        return ImGui::GetIO().MouseDoubleClickTime;
    */

    public native void setMouseDoubleClickMaxDist(float mouseDoubleClickMaxDist); /*
        ImGui::GetIO().MouseDoubleClickMaxDist = mouseDoubleClickMaxDist;
    */

    // MouseDoubleClickTime
    // Distance threshold before considering we are dragging.
    public native float getMouseDragThreshold(); /*
        return ImGui::GetIO().MouseDoubleClickTime;
    */

    public native void setMouseDragThreshold(float mouseDragThreshold); /*
        ImGui::GetIO().MouseDragThreshold = mouseDragThreshold;
    */

    // KeyMap
    // Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
    //
    public native void getKeyMap(int[] buff); /*
        for(int i = 0; i < ImGuiKey_COUNT; i++)
            buff[i] = ImGui::GetIO().KeyMap[i];
    */

    public native int getKeyMap(int idx); /*
        return ImGui::GetIO().KeyMap[idx];
    */

    public native void setKeyMap(int idx, int code); /*
        ImGui::GetIO().KeyMap[idx] = code;
    */

    public native void setKeyMap(int[] keyMap); /*
        for (int i = 0; i < ImGuiKey_COUNT; i++)
            ImGui::GetIO().KeyMap[i] = keyMap[i];
    */

    // KeyRepeatDelay
    // When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
    //
    public native float getKeyRepeatDelay(); /*
        return ImGui::GetIO().KeyRepeatDelay;
    */

    public native void setKeyRepeatDelay(float keyRepeatDelay); /*
        ImGui::GetIO().KeyRepeatDelay = keyRepeatDelay;
    */

    // KeyRepeatRate
    // When holding a key/button, rate at which it repeats, in seconds.
    //
    public native float getKeyRepeatRate(); /*
        return ImGui::GetIO().KeyRepeatRate;
    */

    public native void setKeyRepeatRate(float keyRepeatRate); /*
        ImGui::GetIO().KeyRepeatRate = keyRepeatRate;
    */

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     */
    public ImFontAtlas getFonts() {
        // on demand instantiation
        // will throw native exception if Dear ImGui context isn't created (like in the original library)
        if (imFontAtlas == null) {
            imFontAtlas = new ImFontAtlas(nGetFontsPtr());
        }
        return imFontAtlas;
    }

    private native long nGetFontsPtr(); /*
        return (long)(intptr_t)ImGui::GetIO().Fonts;
    */

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     * <p>
     * BINDING NOTICE: You SHOULD manually destroy previously used ImFontAtlas.
     */
    public void setFonts(final ImFontAtlas imFontAtlas) {
        this.imFontAtlas = imFontAtlas;
        nSetFonts(imFontAtlas.ptr);
    }

    private native void nSetFonts(long imFontAtlasPtr); /*
        ImGui::GetIO().Fonts = (ImFontAtlas*)imFontAtlasPtr;
    */

    /**
     * Global scale all fonts
     */
    public native float getFontGlobalScale(); /*
        return ImGui::GetIO().FontGlobalScale;
    */

    /**
     * Global scale all fonts
     */
    public native void setFontGlobalScale(float fontGlobalScale); /*
        ImGui::GetIO().FontGlobalScale = fontGlobalScale;
    */

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    public native boolean getFontAllowUserScaling(); /*
        return ImGui::GetIO().FontAllowUserScaling;
    */

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    public native void setFontAllowUserScaling(boolean fontAllowUserScaling); /*
        ImGui::GetIO().FontAllowUserScaling = fontAllowUserScaling;
    */

    public void setFontDefault(final ImFont fontDefault) {
        nSetFontDefault(fontDefault.ptr);
    }

    private native void nSetFontDefault(long fontDefaultPtr); /*
        ImGui::GetIO().FontDefault = (ImFont*)fontDefaultPtr;
    */

    // Miscellaneous options

    // MouseDrawCursor
    // Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
    // Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by back-end implementations.
    //
    public native boolean getMouseDrawCursor(); /*
        return ImGui::GetIO().MouseDrawCursor;
    */

    public native void setMouseDrawCursor(boolean mouseDrawCursor); /*
        ImGui::GetIO().MouseDrawCursor = mouseDrawCursor;
    */

    // ConfigMacOSXBehaviors
    // OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
    // Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
    // Multi-selection in lists uses Cmd/Super instead of Ctrl (was called io.OptMacOSXBehaviors prior to 1.63)
    //
    public native boolean getConfigMacOSXBehaviors(); /*
        return ImGui::GetIO().ConfigMacOSXBehaviors;
    */

    public native void setConfigMacOSXBehaviors(boolean configMacOSXBehaviors); /*
        ImGui::GetIO().ConfigMacOSXBehaviors = configMacOSXBehaviors;
    */

    // ConfigInputTextCursorBlink
    // Set to false to disable blinking cursor, for users who consider it distracting. (was called: io.OptCursorBlink prior to 1.63)
    //
    public native boolean getConfigInputTextCursorBlink(); /*
        return ImGui::GetIO().ConfigInputTextCursorBlink;
    */

    public native void setConfigInputTextCursorBlink(boolean configInputTextCursorBlink); /*
        ImGui::GetIO().ConfigInputTextCursorBlink = configInputTextCursorBlink;
    */

    // ConfigWindowsResizeFromEdges
    // Enable resizing of windows from their edges and from the lower-left corner.
    // This requires (io.BackendFlags & ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
    // (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
    //
    public native boolean getConfigWindowsResizeFromEdges(); /*
        return ImGui::GetIO().ConfigWindowsResizeFromEdges;
    */

    public native void setConfigWindowsResizeFromEdges(boolean configWindowsResizeFromEdges); /*
        ImGui::GetIO().ConfigWindowsResizeFromEdges = configWindowsResizeFromEdges;
    */

    // ConfigWindowsMoveFromTitleBarOnly
    // [BETA] Set to true to only allow moving windows when clicked+dragged from the title bar. Windows without a title bar are not affected.
    //
    public native boolean getConfigWindowsMoveFromTitleBarOnly(); /*
        return ImGui::GetIO().ConfigWindowsMoveFromTitleBarOnly;
    */

    public native void setConfigWindowsMoveFromTitleBarOnly(boolean configWindowsMoveFromTitleBarOnly); /*
        ImGui::GetIO().ConfigWindowsMoveFromTitleBarOnly = configWindowsMoveFromTitleBarOnly;
    */

    // ConfigWindowsMemoryCompactTimer
    // [BETA] Compact window memory usage when unused. Set to -1.0f to disable.
    //
    public native float getConfigWindowsMemoryCompactTimer(); /*
        return ImGui::GetIO().ConfigWindowsMemoryCompactTimer;
    */

    public native void setConfigWindowsMemoryCompactTimer(float configWindowsMemoryCompactTimer); /*
        ImGui::GetIO().ConfigWindowsMemoryCompactTimer = configWindowsMemoryCompactTimer;
    */

    //------------------------------------------------------------------
    // Platform Functions
    //------------------------------------------------------------------

    // BackendPlatformName
    // Optional: Platform/Renderer back-end name (informational only! will be displayed in About Window) + User data for back-end/wrappers to store their own stuff.
    //
    public native String getBackendPlatformName(); /*
        return env -> NewStringUTF(ImGui::GetIO().BackendPlatformName);
    */

    public native void setBackendPlatformName(String backendPlatformName); /*MANUAL
        ImGui::GetIO().BackendPlatformName = obj_backendPlatformName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_backendPlatformName, JNI_FALSE);
    */

    // BackendRendererName
    //
    public native String getBackendRendererName(); /*
        return env -> NewStringUTF(ImGui::GetIO().BackendRendererName);
    */

    public native void setBackendRendererName(String backendRendererName); /*MANUAL
        ImGui::GetIO().BackendRendererName = obj_backendRendererName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_backendRendererName, JNI_FALSE);
    */

    // Optional: Access OS clipboard
    // (default to use native Win32 clipIsMouseDraggingboard on Windows, otherwise uses a private clipboard. Override to access OS clipboard on other architectures)

    /*JNI
        jobject _setClipboardTextCallback = NULL;
        jobject _getClipboardTextCallback = NULL;

        void setClipboardTextStub(void* userData, const char* text) {
            Jni::CallImStrConsumer(Jni::GetEnv(), _setClipboardTextCallback, text);
        }

        const char* getClipboardTextStub(void* user_data) {
            JNIEnv* env = Jni::GetEnv();
            jstring jstr = Jni::CallImStrSupplier(env, _getClipboardTextCallback);
            return env->GetStringUTFChars(jstr, 0);
        }
     */

    public native void setSetClipboardTextFn(ImStrConsumer setClipboardTextCallback); /*
        if (_setClipboardTextCallback != NULL) {
            env->DeleteGlobalRef(_setClipboardTextCallback);
        }

        _setClipboardTextCallback = env->NewGlobalRef(setClipboardTextCallback);
        ImGui::GetIO().SetClipboardTextFn = setClipboardTextStub;
    */

    public native void setGetClipboardTextFn(ImStrSupplier getClipboardTextCallback); /*
        if (_getClipboardTextCallback != NULL) {
            env->DeleteGlobalRef(_getClipboardTextCallback);
        }

        _getClipboardTextCallback = env->NewGlobalRef(getClipboardTextCallback);
        ImGui::GetIO().GetClipboardTextFn = getClipboardTextStub;
    */

    //------------------------------------------------------------------
    // Input - Fill before calling NewFrame()
    //------------------------------------------------------------------

    // DisplaySize
    // Main display size, in pixels.
    // BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
    //
    public native void getDisplaySize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().DisplaySize, dstImVec2);
    */

    public native void setDisplaySize(float x, float y); /*
        ImGui::GetIO().DisplaySize.x = x;
        ImGui::GetIO().DisplaySize.y = y;
    */

    // DisplayFramebufferScale
    // For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
    // BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
    //
    public native void getDisplayFramebufferScale(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().DisplayFramebufferScale, dstImVec2);
    */

    public native void setDisplayFramebufferScale(float x, float y); /*
        ImGui::GetIO().DisplayFramebufferScale.x = x;
        ImGui::GetIO().DisplayFramebufferScale.y = y;
    */

    // DeltaTime
    // Time elapsed since last frame, in seconds.
    // BINDING NOTICE: Same as for DisplaySize. This should be modified every frame.
    //
    public native float getDeltaTime(); /*
        return ImGui::GetIO().DeltaTime;
    */

    public native void setDeltaTime(float deltaTime); /*
        ImGui::GetIO().DeltaTime = deltaTime;
    */

    // MousePos
    // Mouse position, in pixels. Set to ImVec2(-FLT_MAX,-FLT_MAX) if mouse is unavailable (on another screen, etc.)
    //
    public native void getMousePos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().MousePos, dstImVec2);
    */

    public native void setMousePos(float x, float y); /*
        ImGui::GetIO().MousePos.x = x;
        ImGui::GetIO().MousePos.y = y;
    */

    // MouseDown
    // Mouse buttons: 0=left, 1=right, 2=middle + extras. ImGui itself mostly only uses left button (BeginPopupContext** are using right button).
    // Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
    //
    public native void getMouseDown(boolean[] buff); /*
        for (int i = 0; i < 5; i++)
            buff[i] = ImGui::GetIO().MouseDown[i];
    */

    public native boolean getMouseDown(int idx); /*
        return ImGui::GetIO().MouseDown[idx];
    */

    public native void setMouseDown(int idx, boolean down); /*
        ImGui::GetIO().MouseDown[idx] = down;
    */

    public native void setMouseDown(boolean[] mouseDown); /*
        for (int i = 0; i < 5; i++)
            ImGui::GetIO().MouseDown[i] = mouseDown[i];
    */

    // MouseWheel
    // Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
    //
    public native float getMouseWheel(); /*
        return ImGui::GetIO().MouseWheel;
    */

    public native void setMouseWheel(float mouseDeltaY); /*
        ImGui::GetIO().MouseWheel = mouseDeltaY;
    */

    // MouseWheelH
    // Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all back-ends.
    //
    public native float getMouseWheelH(); /*
        return ImGui::GetIO().MouseWheelH;
    */

    public native void setMouseWheelH(float mouseDeltaX); /*
        ImGui::GetIO().MouseWheelH = mouseDeltaX;
    */

    // KeyCtrl
    // Keyboard modifier pressed: Control
    //
    public native boolean getKeyCtrl(); /*
        return ImGui::GetIO().KeyCtrl;
    */

    public native void setKeyCtrl(boolean value); /*
        ImGui::GetIO().KeyCtrl = value;
    */

    // KeyShift
    // Keyboard modifier pressed: Shift
    //
    public native boolean getKeyShift(); /*
        return ImGui::GetIO().KeyShift;
    */

    public native void setKeyShift(boolean value); /*
        ImGui::GetIO().KeyShift = value;
    */

    // KeyAlt
    // Keyboard modifier pressed: Alt
    //
    public native boolean getKeyAlt(); /*
        return ImGui::GetIO().KeyAlt;
    */

    public native void setKeyAlt(boolean value); /*
        ImGui::GetIO().KeyAlt = value;
    */

    // KeySuper
    // Keyboard modifier pressed: Cmd/Super/Windows
    //
    public native boolean getKeySuper(); /*
        return ImGui::GetIO().KeySuper;
    */

    public native void setKeySuper(boolean value); /*
        ImGui::GetIO().KeySuper = value;
    */

    // KeysDown
    // Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
    //
    public native void getKeysDown(boolean[] buff); /*
        for (int i = 0; i < 512; i++)
            buff[i] = ImGui::GetIO().KeysDown[i];
    */

    public native boolean getKeysDown(int idx); /*
        return ImGui::GetIO().KeysDown[idx];
    */

    public native void setKeysDown(int idx, boolean pressed); /*
        ImGui::GetIO().KeysDown[idx] = pressed;
    */

    public native void setKeysDown(boolean[] keysDown); /*
        for (int i = 0; i < 512; i++)
            ImGui::GetIO().KeysDown[i] = keysDown[i];
    */

    //------------------------------------------------------------------
    // Output - Retrieve after calling NewFrame()
    //------------------------------------------------------------------

    // WantCaptureMouse
    // When io.WantCaptureMouse is true, imgui will use the mouse inputs, do not dispatch them to your main game/application (in both cases, always pass on mouse inputs to imgui).
    // (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
    //
    public native boolean getWantCaptureMouse(); /*
        return ImGui::GetIO().WantCaptureMouse;
    */

    public native void setWantCaptureMouse(boolean wantCaptureMouse); /*
        ImGui::GetIO().WantCaptureMouse = wantCaptureMouse;
    */

    // WantCaptureKeyboard
    // When io.WantCaptureKeyboard is true, imgui will use the keyboard inputs, do not dispatch them to your main game/application (in both cases, always pass keyboard inputs to imgui).
    // (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
    //
    public native boolean getWantCaptureKeyboard(); /*
        return ImGui::GetIO().WantCaptureKeyboard;
    */

    public native void setWantCaptureKeyboard(boolean wantCaptureKeyboard); /*
        ImGui::GetIO().WantCaptureKeyboard = wantCaptureKeyboard;
    */

    // WantTextInput
    // Mobile/console: when io.WantTextInput is true, you may display an on-screen keyboard.
    // This is set by ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
    //
    public native boolean getWantTextInput(); /*
        return ImGui::GetIO().WantTextInput;
    */

    public native void setWantTextInput(boolean wantTextInput); /*
        ImGui::GetIO().WantTextInput = wantTextInput;
    */

    // WantSetMousePos
    // MousePos has been altered, back-end should reposition mouse on next frame. Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
    //
    public native boolean getWantSetMousePos(); /*
        return ImGui::GetIO().WantSetMousePos;
    */

    public native void setWantSetMousePos(boolean wantSetMousePos); /*
        ImGui::GetIO().WantSetMousePos = wantSetMousePos;
    */

    // WantSaveIniSettings
    // When manual .ini load/save is active (io.IniFilename == NULL),
    // this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
    // IMPORTANT: You need to clear io.WantSaveIniSettings yourself.
    //
    public native boolean getWantSaveIniSettings(); /*
        return ImGui::GetIO().WantSaveIniSettings;
    */

    public native void setWantSaveIniSettings(boolean wantSaveIniSettings); /*
        ImGui::GetIO().WantSaveIniSettings = wantSaveIniSettings;
    */

    // NavActive
    // Directional navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
    //
    public native boolean getNavActive(); /*
        return ImGui::GetIO().NavActive;
    */

    public native void setNavActive(boolean navActive); /*
        ImGui::GetIO().NavActive = navActive;
    */

    // NavVisible
    // Directional navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
    //
    public native boolean getNavVisible(); /*
        return ImGui::GetIO().NavVisible;
    */

    public native void setNavVisible(boolean navVisible); /*
        ImGui::GetIO().NavVisible = navVisible;
    */

    // Framerate
    // Application framerate estimation, in frame per second. Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
    //
    public native float getFramerate(); /*
        return ImGui::GetIO().Framerate;
    */

    public native void setFramerate(float framerate); /*
        ImGui::GetIO().Framerate = framerate;
    */

    // MetricsRenderVertices
    // Vertices output during last call to Render()
    //
    public native int getMetricsRenderVertices(); /*
        return ImGui::GetIO().MetricsRenderVertices;
    */

    public native void setMetricsRenderVertices(int metricsRenderVertices); /*
        ImGui::GetIO().MetricsRenderVertices = metricsRenderVertices;
    */

    // MetricsRenderIndices
    // Indices output during last call to Render() = number of triangles * 3
    //
    public native int getMetricsRenderIndices(); /*
        return ImGui::GetIO().MetricsRenderIndices;
    */

    public native void setMetricsRenderIndices(int metricsRenderIndices); /*
        ImGui::GetIO().MetricsRenderIndices = metricsRenderIndices;
    */

    // MetricsRenderWindows
    // Number of visible windows
    //
    public native int getMetricsRenderWindows(); /*
        return ImGui::GetIO().MetricsRenderWindows;
    */

    public native void setMetricsRenderWindows(int metricsRenderWindows); /*
        ImGui::GetIO().MetricsRenderWindows = metricsRenderWindows;
    */

    // MetricsActiveWindows
    // Number of active windows
    //
    public native int getMetricsActiveWindows(); /*
        return ImGui::GetIO().MetricsActiveWindows;
    */

    public native void setMetricsActiveWindows(int metricsActiveWindows); /*
        ImGui::GetIO().MetricsActiveWindows = metricsActiveWindows;
    */

    // MetricsActiveAllocations
    // Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
    //
    public native int getMetricsActiveAllocations(); /*
        return ImGui::GetIO().MetricsActiveAllocations;
    */

    public native void setMetricsActiveAllocations(int metricsActiveAllocations); /*
        ImGui::GetIO().MetricsActiveAllocations = metricsActiveAllocations;
    */

    // MouseDelta
    // Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
    //
    public native void getMouseDelta(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().MouseDelta, dstImVec2);
    */

    public native void setMouseDelta(float x, float y); /*
        ImGui::GetIO().MouseDelta.x = x;
        ImGui::GetIO().MouseDelta.y = y;
    */

    // Functions

    /**
     * Queue new character input.
     */
    public native void addInputCharacter(int c); /*
        ImGui::GetIO().AddInputCharacter((unsigned int)c);
    */

    /**
     * Queue new characters input from an UTF-8 string.
     */
    public native void addInputCharactersUTF8(String str); /*
        ImGui::GetIO().AddInputCharactersUTF8(str);
    */

    /**
     * Clear the text input buffer manually.
     */
    public native void clearInputCharacters(); /*
        ImGui::GetIO().ClearInputCharacters();
    */
}
