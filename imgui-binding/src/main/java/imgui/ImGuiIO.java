package imgui;

import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;

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

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public native int getConfigFlags(); /*
        return ImGui::GetIO().ConfigFlags;
    */

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public native void setConfigFlags(int configFlags); /*
        ImGui::GetIO().ConfigFlags = configFlags;
    */

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void addConfigFlags(int configFlags) {
        setConfigFlags(getConfigFlags() | configFlags);
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void removeConfigFlags(int configFlags) {
        setConfigFlags(getConfigFlags() & ~(configFlags));
    }

    /**
     * See ImGuiBackendFlags enum. Set by back-end to communicate features supported by the back-end.
     */
    public native int getBackendFlags(); /*
        return ImGui::GetIO().BackendFlags;
    */

    /**
     * See ImGuiBackendFlags enum. Set by back-end to communicate features supported by the back-end.
     */
    public native void setBackendFlags(int backendFlags); /*
        ImGui::GetIO().BackendFlags = backendFlags;
    */

    /**
     * See ImGuiBackendFlags enum. Set by back-end to communicate features supported by the back-end.
     */
    public void addBackendFlags(int backendFlags) {
        setBackendFlags(getBackendFlags() | backendFlags);
    }

    /**
     * See ImGuiBackendFlags enum. Set by back-end to communicate features supported by the back-end.
     */
    public void removeBackendFlags(int backendFlags) {
        setBackendFlags(getBackendFlags() & ~(backendFlags));
    }

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    public native float getIniSavingRate(); /*
        return ImGui::GetIO().IniSavingRate;
    */

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    public native void setIniSavingRate(float iniSavingRate); /*
        ImGui::GetIO().IniSavingRate = iniSavingRate;
    */

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    public native String getIniFilename(); /*
        return env->NewStringUTF(ImGui::GetIO().IniFilename);
    */

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    public native void setIniFilename(String iniFilename); /*MANUAL
        ImGui::GetIO().IniFilename = obj_iniFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniFilename, JNI_FALSE);
    */

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    public native String getLogFilename(); /*
        return env->NewStringUTF(ImGui::GetIO().LogFilename);
    */

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    public native void setLogFilename(String logFilename); /*MANUAL
        ImGui::GetIO().LogFilename = obj_logFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_logFilename, JNI_FALSE);
    */

    /**
     * Time for a double-click, in seconds.
     */
    public native float getMouseDoubleClickTime(); /*
        return ImGui::GetIO().MouseDoubleClickTime;
    */

    /**
     * Time for a double-click, in seconds.
     */
    public native void setMouseDoubleClickTime(float mouseDoubleClickTime); /*
        ImGui::GetIO().MouseDoubleClickTime = mouseDoubleClickTime;
    */

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    public native float getMouseDoubleClickMaxDist(); /*
        return ImGui::GetIO().MouseDoubleClickTime;
    */

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    public native void setMouseDoubleClickMaxDist(float mouseDoubleClickMaxDist); /*
        ImGui::GetIO().MouseDoubleClickMaxDist = mouseDoubleClickMaxDist;
    */

    /**
     * Distance threshold before considering we are dragging.
     */
    public native float getMouseDragThreshold(); /*
        return ImGui::GetIO().MouseDoubleClickTime;
    */

    /**
     * Distance threshold before considering we are dragging.
     */
    public native void setMouseDragThreshold(float mouseDragThreshold); /*
        ImGui::GetIO().MouseDragThreshold = mouseDragThreshold;
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native void getKeyMap(int[] buff); /*
        for(int i = 0; i < ImGuiKey_COUNT; i++)
            buff[i] = ImGui::GetIO().KeyMap[i];
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native int getKeyMap(int idx); /*
        return ImGui::GetIO().KeyMap[idx];
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native void setKeyMap(int idx, int code); /*
        ImGui::GetIO().KeyMap[idx] = code;
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native void setKeyMap(int[] keyMap); /*
        for (int i = 0; i < ImGuiKey_COUNT; i++)
            ImGui::GetIO().KeyMap[i] = keyMap[i];
    */

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    public native float getKeyRepeatDelay(); /*
        return ImGui::GetIO().KeyRepeatDelay;
    */

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    public native void setKeyRepeatDelay(float keyRepeatDelay); /*
        ImGui::GetIO().KeyRepeatDelay = keyRepeatDelay;
    */

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    public native float getKeyRepeatRate(); /*
        return ImGui::GetIO().KeyRepeatRate;
    */

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    public native void setKeyRepeatRate(float keyRepeatRate); /*
        ImGui::GetIO().KeyRepeatRate = keyRepeatRate;
    */

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     */
    public ImFontAtlas getFonts() {
        if (imFontAtlas == null) {
            imFontAtlas = new ImFontAtlas(nGetFonts());
        }
        return imFontAtlas;
    }

    private native long nGetFonts(); /*
        return (intptr_t)ImGui::GetIO().Fonts;
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

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by back-end implementations.
     */
    public native boolean getMouseDrawCursor(); /*
        return ImGui::GetIO().MouseDrawCursor;
    */

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by back-end implementations.
     */
    public native void setMouseDrawCursor(boolean mouseDrawCursor); /*
        ImGui::GetIO().MouseDrawCursor = mouseDrawCursor;
    */

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl (was called io.OptMacOSXBehaviors prior to 1.63)
     */
    public native boolean getConfigMacOSXBehaviors(); /*
        return ImGui::GetIO().ConfigMacOSXBehaviors;
    */

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl (was called io.OptMacOSXBehaviors prior to 1.63)
     */
    public native void setConfigMacOSXBehaviors(boolean configMacOSXBehaviors); /*
        ImGui::GetIO().ConfigMacOSXBehaviors = configMacOSXBehaviors;
    */

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting. (was called: io.OptCursorBlink prior to 1.63)
     */
    public native boolean getConfigInputTextCursorBlink(); /*
        return ImGui::GetIO().ConfigInputTextCursorBlink;
    */

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting. (was called: io.OptCursorBlink prior to 1.63)
     */
    public native void setConfigInputTextCursorBlink(boolean configInputTextCursorBlink); /*
        ImGui::GetIO().ConfigInputTextCursorBlink = configInputTextCursorBlink;
    */

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    public native boolean getConfigWindowsResizeFromEdges(); /*
        return ImGui::GetIO().ConfigWindowsResizeFromEdges;
    */

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    public native void setConfigWindowsResizeFromEdges(boolean configWindowsResizeFromEdges); /*
        ImGui::GetIO().ConfigWindowsResizeFromEdges = configWindowsResizeFromEdges;
    */

    /**
     * [BETA] Set to true to only allow moving windows when clicked+dragged from the title bar. Windows without a title bar are not affected.
     */
    public native boolean getConfigWindowsMoveFromTitleBarOnly(); /*
        return ImGui::GetIO().ConfigWindowsMoveFromTitleBarOnly;
    */

    /**
     * [BETA] Set to true to only allow moving windows when clicked+dragged from the title bar. Windows without a title bar are not affected.
     */
    public native void setConfigWindowsMoveFromTitleBarOnly(boolean configWindowsMoveFromTitleBarOnly); /*
        ImGui::GetIO().ConfigWindowsMoveFromTitleBarOnly = configWindowsMoveFromTitleBarOnly;
    */

    /**
     * [BETA] Compact window memory usage when unused. Set to -1.0f to disable.
     */
    public native float getConfigWindowsMemoryCompactTimer(); /*
        return ImGui::GetIO().ConfigWindowsMemoryCompactTimer;
    */

    /**
     * [BETA] Compact window memory usage when unused. Set to -1.0f to disable.
     */
    public native void setConfigWindowsMemoryCompactTimer(float configWindowsMemoryCompactTimer); /*
        ImGui::GetIO().ConfigWindowsMemoryCompactTimer = configWindowsMemoryCompactTimer;
    */

    //------------------------------------------------------------------
    // Platform Functions
    //------------------------------------------------------------------

    /**
     * Optional: Platform back-end name (informational only! will be displayed in About Window) + User data for back-end/wrappers to store their own stuff.
     */
    public native String getBackendPlatformName(); /*
        return env -> NewStringUTF(ImGui::GetIO().BackendPlatformName);
    */

    /**
     * Optional: Platform back-end name (informational only! will be displayed in About Window) + User data for back-end/wrappers to store their own stuff.
     */
    public native void setBackendPlatformName(String backendPlatformName); /*MANUAL
        ImGui::GetIO().BackendPlatformName = obj_backendPlatformName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_backendPlatformName, JNI_FALSE);
    */

    /**
     * Optional: Renderer back-end name (informational only! will be displayed in About Window) + User data for back-end/wrappers to store their own stuff.
     */
    public native String getBackendRendererName(); /*
        return env -> NewStringUTF(ImGui::GetIO().BackendRendererName);
    */

    /**
     * Optional: Renderer back-end name (informational only! will be displayed in About Window) + User data for back-end/wrappers to store their own stuff.
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

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void getDisplaySize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().DisplaySize, dstImVec2);
    */

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplaySizeX(); /*
        return ImGui::GetIO().DisplaySize.x;
    */

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplaySizeY(); /*
        return ImGui::GetIO().DisplaySize.y;
    */

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void setDisplaySize(float x, float y); /*
        ImGui::GetIO().DisplaySize.x = x;
        ImGui::GetIO().DisplaySize.y = y;
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void getDisplayFramebufferScale(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().DisplayFramebufferScale, dstImVec2);
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplayFramebufferScaleX(); /*
        return ImGui::GetIO().DisplayFramebufferScale.x;
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplayFramebufferScaleY(); /*
        return ImGui::GetIO().DisplayFramebufferScale.y;
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void setDisplayFramebufferScale(float x, float y); /*
        ImGui::GetIO().DisplayFramebufferScale.x = x;
        ImGui::GetIO().DisplayFramebufferScale.y = y;
    */

    // Docking options (when ImGuiConfigFlags_DockingEnable is set)

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    public native boolean getConfigDockingNoSplit(); /*
        return ImGui::GetIO().ConfigDockingNoSplit;
    */

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    public native void setConfigDockingNoSplit(boolean value); /*
        ImGui::GetIO().ConfigDockingNoSplit = value;
    */

    /**
     * Enable docking with holding Shift key (reduce visual noise, allows dropping in wider space)
     */
    public native boolean getConfigDockingWithShift(); /*
        return ImGui::GetIO().ConfigDockingWithShift;
    */

    /**
     * Enable docking with holding Shift key (reduce visual noise, allows dropping in wider space)
     */
    public native void setConfigDockingWithShift(boolean value); /*
        ImGui::GetIO().ConfigDockingWithShift = value;
    */

    /**
     * [BETA] [FIXME: This currently creates regression with auto-sizing and general overhead]
     * Make every single floating window display within a docking node.
     */
    public native boolean getConfigDockingAlwaysTabBar(); /*
        return ImGui::GetIO().ConfigDockingAlwaysTabBar;
    */

    /**
     * [BETA] [FIXME: This currently creates regression with auto-sizing and general overhead]
     * Make every single floating window display within a docking node.
     */
    public native void setConfigDockingAlwaysTabBar(boolean value); /*
        ImGui::GetIO().ConfigDockingAlwaysTabBar = value;
    */

    /**
     * [BETA] Make window or viewport transparent when docking and only display docking boxes on the target viewport.
     * Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    public native boolean getConfigDockingTransparentPayload(); /*
        return ImGui::GetIO().ConfigDockingTransparentPayload;
    */

    /**
     * [BETA] Make window or viewport transparent when docking and only display docking boxes on the target viewport.
     * Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    public native void setConfigDockingTransparentPayload(boolean value); /*
        ImGui::GetIO().ConfigDockingTransparentPayload = value;
    */

    /**
     * Time elapsed since last frame, in seconds.
     * <p>
     * BINDING NOTICE: Same as for DisplaySize. This should be modified every frame.
     */
    public native float getDeltaTime(); /*
        return ImGui::GetIO().DeltaTime;
    */

    /**
     * Time elapsed since last frame, in seconds.
     * <p>
     * BINDING NOTICE: Same as for DisplaySize. This should be modified every frame.
     */
    public native void setDeltaTime(float deltaTime); /*
        ImGui::GetIO().DeltaTime = deltaTime;
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native void getMousePos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().MousePos, dstImVec2);
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native float getMousePosX(); /*
        return ImGui::GetIO().MousePos.x;
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native float getMousePosY(); /*
        return ImGui::GetIO().MousePos.y;
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native void setMousePos(float x, float y); /*
        ImGui::GetIO().MousePos.x = x;
        ImGui::GetIO().MousePos.y = y;
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native void getMouseDown(boolean[] buff); /*
        for (int i = 0; i < 5; i++)
            buff[i] = ImGui::GetIO().MouseDown[i];
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native boolean getMouseDown(int idx); /*
        return ImGui::GetIO().MouseDown[idx];
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native void setMouseDown(int idx, boolean down); /*
        ImGui::GetIO().MouseDown[idx] = down;
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native void setMouseDown(boolean[] mouseDown); /*
        for (int i = 0; i < 5; i++)
            ImGui::GetIO().MouseDown[i] = mouseDown[i];
    */

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    public native float getMouseWheel(); /*
        return ImGui::GetIO().MouseWheel;
    */

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    public native void setMouseWheel(float mouseDeltaY); /*
        ImGui::GetIO().MouseWheel = mouseDeltaY;
    */

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all back-ends.
     */
    public native float getMouseWheelH(); /*
        return ImGui::GetIO().MouseWheelH;
    */

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all back-ends.
     */
    public native void setMouseWheelH(float mouseDeltaX); /*
        ImGui::GetIO().MouseWheelH = mouseDeltaX;
    */

    /**
     * Keyboard modifier pressed: Control
     */
    public native boolean getKeyCtrl(); /*
        return ImGui::GetIO().KeyCtrl;
    */

    /**
     * Keyboard modifier pressed: Control
     */
    public native void setKeyCtrl(boolean value); /*
        ImGui::GetIO().KeyCtrl = value;
    */

    /**
     * Keyboard modifier pressed: Shift
     */
    public native boolean getKeyShift(); /*
        return ImGui::GetIO().KeyShift;
    */

    /**
     * Keyboard modifier pressed: Shift
     */
    public native void setKeyShift(boolean value); /*
        ImGui::GetIO().KeyShift = value;
    */

    /**
     * Keyboard modifier pressed: Alt
     */
    public native boolean getKeyAlt(); /*
        return ImGui::GetIO().KeyAlt;
    */

    /**
     * Keyboard modifier pressed: Alt
     */
    public native void setKeyAlt(boolean value); /*
        ImGui::GetIO().KeyAlt = value;
    */

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    public native boolean getKeySuper(); /*
        return ImGui::GetIO().KeySuper;
    */

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    public native void setKeySuper(boolean value); /*
        ImGui::GetIO().KeySuper = value;
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native void getKeysDown(boolean[] buff); /*
        for (int i = 0; i < 512; i++)
            buff[i] = ImGui::GetIO().KeysDown[i];
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native boolean getKeysDown(int idx); /*
        return ImGui::GetIO().KeysDown[idx];
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native void setKeysDown(int idx, boolean pressed); /*
        ImGui::GetIO().KeysDown[idx] = pressed;
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native void setKeysDown(boolean[] keysDown); /*
        for (int i = 0; i < 512; i++)
            ImGui::GetIO().KeysDown[i] = keysDown[i];
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native void getNavInputs(float[] buff); /*
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            buff[i] = ImGui::GetIO().NavInputs[i];
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native float getNavInputs(int idx); /*
        return ImGui::GetIO().NavInputs[idx];
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native void setNavInputs(int idx, float input); /*
        ImGui::GetIO().NavInputs[idx] = input;
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native void setNavInputs(float[] navInputs); /*
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            ImGui::GetIO().NavInputs[i] = navInputs[i];
    */

    //------------------------------------------------------------------
    // Output - Updated by NewFrame() or EndFrame()/Render()
    // (when reading from the io.WantCaptureMouse, io.WantCaptureKeyboard flags to dispatch your inputs, it is
    //  generally easier and more correct to use their state BEFORE calling NewFrame(). See FAQ for details!)
    //------------------------------------------------------------------

    /**
     * Set when Dear ImGui will use mouse inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass on mouse inputs to imgui).
     * (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
     */
    public native boolean getWantCaptureMouse(); /*
        return ImGui::GetIO().WantCaptureMouse;
    */

    /**
     * Set when Dear ImGui will use mouse inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass on mouse inputs to imgui).
     * (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
     */
    public native void setWantCaptureMouse(boolean wantCaptureMouse); /*
        ImGui::GetIO().WantCaptureMouse = wantCaptureMouse;
    */

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    public native boolean getWantCaptureKeyboard(); /*
        return ImGui::GetIO().WantCaptureKeyboard;
    */

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    public native void setWantCaptureKeyboard(boolean wantCaptureKeyboard); /*
        ImGui::GetIO().WantCaptureKeyboard = wantCaptureKeyboard;
    */

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    public native boolean getWantTextInput(); /*
        return ImGui::GetIO().WantTextInput;
    */

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    public native void setWantTextInput(boolean wantTextInput); /*
        ImGui::GetIO().WantTextInput = wantTextInput;
    */

    /**
     * MousePos has been altered, back-end should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    public native boolean getWantSetMousePos(); /*
        return ImGui::GetIO().WantSetMousePos;
    */

    /**
     * MousePos has been altered, back-end should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    public native void setWantSetMousePos(boolean wantSetMousePos); /*
        ImGui::GetIO().WantSetMousePos = wantSetMousePos;
    */

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    public native boolean getWantSaveIniSettings(); /*
        return ImGui::GetIO().WantSaveIniSettings;
    */

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    public native void setWantSaveIniSettings(boolean wantSaveIniSettings); /*
        ImGui::GetIO().WantSaveIniSettings = wantSaveIniSettings;
    */

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    public native boolean getNavActive(); /*
        return ImGui::GetIO().NavActive;
    */

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    public native void setNavActive(boolean navActive); /*
        ImGui::GetIO().NavActive = navActive;
    */

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    public native boolean getNavVisible(); /*
        return ImGui::GetIO().NavVisible;
    */

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    public native void setNavVisible(boolean navVisible); /*
        ImGui::GetIO().NavVisible = navVisible;
    */

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    public native float getFramerate(); /*
        return ImGui::GetIO().Framerate;
    */

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    public native void setFramerate(float framerate); /*
        ImGui::GetIO().Framerate = framerate;
    */

    /**
     * Vertices output during last call to Render()
     */
    public native int getMetricsRenderVertices(); /*
        return ImGui::GetIO().MetricsRenderVertices;
    */

    /**
     * Vertices output during last call to Render()
     */
    public native void setMetricsRenderVertices(int metricsRenderVertices); /*
        ImGui::GetIO().MetricsRenderVertices = metricsRenderVertices;
    */

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    public native int getMetricsRenderIndices(); /*
        return ImGui::GetIO().MetricsRenderIndices;
    */

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    public native void setMetricsRenderIndices(int metricsRenderIndices); /*
        ImGui::GetIO().MetricsRenderIndices = metricsRenderIndices;
    */

    /**
     * Number of visible windows
     */
    public native int getMetricsRenderWindows(); /*
        return ImGui::GetIO().MetricsRenderWindows;
    */

    /**
     * Number of visible windows
     */
    public native void setMetricsRenderWindows(int metricsRenderWindows); /*
        ImGui::GetIO().MetricsRenderWindows = metricsRenderWindows;
    */

    /**
     * Number of active windows
     */
    public native int getMetricsActiveWindows(); /*
        return ImGui::GetIO().MetricsActiveWindows;
    */

    /**
     * Number of active windows
     */
    public native void setMetricsActiveWindows(int metricsActiveWindows); /*
        ImGui::GetIO().MetricsActiveWindows = metricsActiveWindows;
    */

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    public native int getMetricsActiveAllocations(); /*
        return ImGui::GetIO().MetricsActiveAllocations;
    */

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    public native void setMetricsActiveAllocations(int metricsActiveAllocations); /*
        ImGui::GetIO().MetricsActiveAllocations = metricsActiveAllocations;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native void getMouseDelta(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &ImGui::GetIO().MouseDelta, dstImVec2);
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native float getMouseDeltaX(); /*
        return ImGui::GetIO().MouseDelta.x;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native float getMouseDeltaY(); /*
        return ImGui::GetIO().MouseDelta.y;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
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
     * Queue new character input from an UTF-16 character, it can be a surrogate
     */
    public native void addInputCharacterUTF16(short c); /*
        ImGui::GetIO().AddInputCharacterUTF16((ImWchar16)c);
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
