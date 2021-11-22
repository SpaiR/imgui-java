package io.github.spair.imgui;

import io.github.spair.imgui.binding.ImGuiStruct;
import io.github.spair.imgui.callback.ImStrConsumer;
import io.github.spair.imgui.callback.ImStrSupplier;

/**
 * Communicate most settings and inputs/outputs to Dear ImGui using this structure.
 * Access via ImGui::GetIO(). Read 'Programmer guide' section in .cpp file for general usage.
 */
public final class ImGuiIO extends ImGuiStruct {
    private ImFontAtlas imFontAtlas = new ImFontAtlas(0);

    public ImGuiIO(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IO ((ImGuiIO*)STRUCT_PTR)
     */

    //------------------------------------------------------------------
    // Configuration (fill once)
    //------------------------------------------------------------------

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public native int getConfigFlags(); /*
        return IO->ConfigFlags;
    */

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public native void setConfigFlags(int configFlags); /*
        IO->ConfigFlags = configFlags;
    */

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void addConfigFlags(final int configFlags) {
        setConfigFlags(getConfigFlags() | configFlags);
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void removeConfigFlags(final int configFlags) {
        setConfigFlags(getConfigFlags() & ~(configFlags));
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public boolean hasConfigFlags(final int flags) {
        return (getConfigFlags() & flags) != 0;
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public native int getBackendFlags(); /*
        return IO->BackendFlags;
    */

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public native void setBackendFlags(int backendFlags); /*
        IO->BackendFlags = backendFlags;
    */

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public void addBackendFlags(final int backendFlags) {
        setBackendFlags(getBackendFlags() | backendFlags);
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public void removeBackendFlags(final int backendFlags) {
        setBackendFlags(getBackendFlags() & ~(backendFlags));
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public boolean hasBackendFlags(final int flags) {
        return (getBackendFlags() & flags) != 0;
    }

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    public native float getIniSavingRate(); /*
        return IO->IniSavingRate;
    */

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    public native void setIniSavingRate(float iniSavingRate); /*
        IO->IniSavingRate = iniSavingRate;
    */

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    public native String getIniFilename(); /*
        return env->NewStringUTF(IO->IniFilename);
    */

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    public native void setIniFilename(String iniFilename); /*MANUAL
        IO->IniFilename = obj_iniFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniFilename, JNI_FALSE);
    */

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    public native String getLogFilename(); /*
        return env->NewStringUTF(IO->LogFilename);
    */

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    public native void setLogFilename(String logFilename); /*MANUAL
        IO->LogFilename = obj_logFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_logFilename, JNI_FALSE);
    */

    /**
     * Time for a double-click, in seconds.
     */
    public native float getMouseDoubleClickTime(); /*
        return IO->MouseDoubleClickTime;
    */

    /**
     * Time for a double-click, in seconds.
     */
    public native void setMouseDoubleClickTime(float mouseDoubleClickTime); /*
        IO->MouseDoubleClickTime = mouseDoubleClickTime;
    */

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    public native float getMouseDoubleClickMaxDist(); /*
        return IO->MouseDoubleClickTime;
    */

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    public native void setMouseDoubleClickMaxDist(float mouseDoubleClickMaxDist); /*
        IO->MouseDoubleClickMaxDist = mouseDoubleClickMaxDist;
    */

    /**
     * Distance threshold before considering we are dragging.
     */
    public native float getMouseDragThreshold(); /*
        return IO->MouseDoubleClickTime;
    */

    /**
     * Distance threshold before considering we are dragging.
     */
    public native void setMouseDragThreshold(float mouseDragThreshold); /*
        IO->MouseDragThreshold = mouseDragThreshold;
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native void getKeyMap(int[] buff); /*
        for(int i = 0; i < ImGuiKey_COUNT; i++)
            buff[i] = IO->KeyMap[i];
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native int getKeyMap(int idx); /*
        return IO->KeyMap[idx];
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native void setKeyMap(int idx, int code); /*
        IO->KeyMap[idx] = code;
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    public native void setKeyMap(int[] keyMap); /*
        for (int i = 0; i < ImGuiKey_COUNT; i++)
            IO->KeyMap[i] = keyMap[i];
    */

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    public native float getKeyRepeatDelay(); /*
        return IO->KeyRepeatDelay;
    */

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    public native void setKeyRepeatDelay(float keyRepeatDelay); /*
        IO->KeyRepeatDelay = keyRepeatDelay;
    */

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    public native float getKeyRepeatRate(); /*
        return IO->KeyRepeatRate;
    */

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    public native void setKeyRepeatRate(float keyRepeatRate); /*
        IO->KeyRepeatRate = keyRepeatRate;
    */

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     */
    public ImFontAtlas getFonts() {
        imFontAtlas.ptr = nGetFonts();
        return imFontAtlas;
    }

    private native long nGetFonts(); /*
        return (intptr_t)IO->Fonts;
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
        IO->Fonts = (ImFontAtlas*)imFontAtlasPtr;
    */

    /**
     * Global scale all fonts
     */
    public native float getFontGlobalScale(); /*
        return IO->FontGlobalScale;
    */

    /**
     * Global scale all fonts
     */
    public native void setFontGlobalScale(float fontGlobalScale); /*
        IO->FontGlobalScale = fontGlobalScale;
    */

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    public native boolean getFontAllowUserScaling(); /*
        return IO->FontAllowUserScaling;
    */

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    public native void setFontAllowUserScaling(boolean fontAllowUserScaling); /*
        IO->FontAllowUserScaling = fontAllowUserScaling;
    */

    public void setFontDefault(final ImFont fontDefault) {
        nSetFontDefault(fontDefault.ptr);
    }

    private native void nSetFontDefault(long fontDefaultPtr); /*
        IO->FontDefault = (ImFont*)fontDefaultPtr;
    */

    // Miscellaneous options

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by backend implementations.
     */
    public native boolean getMouseDrawCursor(); /*
        return IO->MouseDrawCursor;
    */

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by backend implementations.
     */
    public native void setMouseDrawCursor(boolean mouseDrawCursor); /*
        IO->MouseDrawCursor = mouseDrawCursor;
    */

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl
     */
    public native boolean getConfigMacOSXBehaviors(); /*
        return IO->ConfigMacOSXBehaviors;
    */

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl
     */
    public native void setConfigMacOSXBehaviors(boolean configMacOSXBehaviors); /*
        IO->ConfigMacOSXBehaviors = configMacOSXBehaviors;
    */

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting.
     */
    public native boolean getConfigInputTextCursorBlink(); /*
        return IO->ConfigInputTextCursorBlink;
    */

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting.
     */
    public native void setConfigInputTextCursorBlink(boolean configInputTextCursorBlink); /*
        IO->ConfigInputTextCursorBlink = configInputTextCursorBlink;
    */

    /**
     * [BETA] Enable turning DragXXX widgets into text input with a simple mouse click-release (without moving). Not desirable on devices without a keyboard.
     */
    public native boolean getConfigDragClickToInputText(); /*
        return IO->ConfigDragClickToInputText;
    */

    /**
     * [BETA] Enable turning DragXXX widgets into text input with a simple mouse click-release (without moving). Not desirable on devices without a keyboard.
     */
    public native void setConfigDragClickToInputText(boolean configDragClickToInputText); /*
        IO->ConfigDragClickToInputText = configDragClickToInputText;
    */

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    public native boolean getConfigWindowsResizeFromEdges(); /*
        return IO->ConfigWindowsResizeFromEdges;
    */

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    public native void setConfigWindowsResizeFromEdges(boolean configWindowsResizeFromEdges); /*
        IO->ConfigWindowsResizeFromEdges = configWindowsResizeFromEdges;
    */

    /**
     * Enable allowing to move windows only when clicking on their title bar. Does not apply to windows without a title bar.
     */
    public native boolean getConfigWindowsMoveFromTitleBarOnly(); /*
        return IO->ConfigWindowsMoveFromTitleBarOnly;
    */

    /**
     * Enable allowing to move windows only when clicking on their title bar. Does not apply to windows without a title bar.
     */
    public native void setConfigWindowsMoveFromTitleBarOnly(boolean configWindowsMoveFromTitleBarOnly); /*
        IO->ConfigWindowsMoveFromTitleBarOnly = configWindowsMoveFromTitleBarOnly;
    */

    /**
     * [Timer (in seconds) to free transient windows/tables memory buffers when unused. Set to -1.0f to disable.
     */
    public native float getConfigMemoryCompactTimer(); /*
        return IO->ConfigMemoryCompactTimer;
    */

    /**
     * Timer (in seconds) to free transient windows/tables memory buffers when unused. Set to -1.0f to disable.
     */
    public native void setConfigMemoryCompactTimer(float configMemoryCompactTimer); /*
        IO->ConfigMemoryCompactTimer = configMemoryCompactTimer;
    */

    //------------------------------------------------------------------
    // Platform Functions
    //------------------------------------------------------------------

    /**
     * Optional: Platform backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    public native String getBackendPlatformName(); /*
        return env->NewStringUTF(IO->BackendPlatformName);
    */

    /**
     * Optional: Platform backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    public native void setBackendPlatformName(String backendPlatformName); /*MANUAL
        IO->BackendPlatformName = obj_backendPlatformName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_backendPlatformName, JNI_FALSE);
    */

    /**
     * Optional: Renderer backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    public native String getBackendRendererName(); /*
        return env->NewStringUTF(IO->BackendRendererName);
    */

    /**
     * Optional: Renderer backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    public native void setBackendRendererName(String backendRendererName); /*MANUAL
        IO->BackendRendererName = obj_backendRendererName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_backendRendererName, JNI_FALSE);
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
        IO->SetClipboardTextFn = setClipboardTextStub;
    */

    public native void setGetClipboardTextFn(ImStrSupplier getClipboardTextCallback); /*
        if (_getClipboardTextCallback != NULL) {
            env->DeleteGlobalRef(_getClipboardTextCallback);
        }

        _getClipboardTextCallback = env->NewGlobalRef(getClipboardTextCallback);
        IO->GetClipboardTextFn = getClipboardTextStub;
    */

    //------------------------------------------------------------------
    // Input - Fill before calling NewFrame()
    //------------------------------------------------------------------

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public ImVec2 getDisplaySize() {
        final ImVec2 value = new ImVec2();
        getDisplaySize(value);
        return value;
    }

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void getDisplaySize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IO->DisplaySize, dstImVec2);
    */

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplaySizeX(); /*
        return IO->DisplaySize.x;
    */

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplaySizeY(); /*
        return IO->DisplaySize.y;
    */

    /**
     * Main display size, in pixels.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void setDisplaySize(float x, float y); /*
        IO->DisplaySize.x = x;
        IO->DisplaySize.y = y;
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public ImVec2 getDisplayFramebufferScale() {
        final ImVec2 value = new ImVec2();
        getDisplayFramebufferScale(value);
        return value;
    }

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void getDisplayFramebufferScale(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IO->DisplayFramebufferScale, dstImVec2);
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplayFramebufferScaleX(); /*
        return IO->DisplayFramebufferScale.x;
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native float getDisplayFramebufferScaleY(); /*
        return IO->DisplayFramebufferScale.y;
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     * <p>
     * BINDING NOTICE: This should be a "Config" part, but since those values may be different for every frame I don't see how it is possible to set them only once.
     */
    public native void setDisplayFramebufferScale(float x, float y); /*
        IO->DisplayFramebufferScale.x = x;
        IO->DisplayFramebufferScale.y = y;
    */

    // Docking options (when ImGuiConfigFlags_DockingEnable is set)

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    public native boolean getConfigDockingNoSplit(); /*
        return IO->ConfigDockingNoSplit;
    */

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    public native void setConfigDockingNoSplit(boolean value); /*
        IO->ConfigDockingNoSplit = value;
    */

    /**
     * [BETA] [FIXME: This currently creates regression with auto-sizing and general overhead]
     * Make every single floating window display within a docking node.
     */
    public native boolean getConfigDockingAlwaysTabBar(); /*
        return IO->ConfigDockingAlwaysTabBar;
    */

    /**
     * [BETA] [FIXME: This currently creates regression with auto-sizing and general overhead]
     * Make every single floating window display within a docking node.
     */
    public native void setConfigDockingAlwaysTabBar(boolean value); /*
        IO->ConfigDockingAlwaysTabBar = value;
    */

    /**
     * [BETA] Make window or viewport transparent when docking and only display docking boxes on the target viewport.
     * Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    public native boolean getConfigDockingTransparentPayload(); /*
        return IO->ConfigDockingTransparentPayload;
    */

    /**
     * [BETA] Make window or viewport transparent when docking and only display docking boxes on the target viewport.
     * Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    public native void setConfigDockingTransparentPayload(boolean value); /*
        IO->ConfigDockingTransparentPayload = value;
    */

    // Viewport options (when ImGuiConfigFlags_ViewportsEnable is set)

    /**
     * Set to make all floating imgui windows always create their own viewport.
     * Otherwise, they are merged into the main host viewports when overlapping it. May also set ImGuiViewportFlags_NoAutoMerge on individual viewport.
     */
    public native boolean getConfigViewportsNoAutoMerge(); /*
        return IO->ConfigViewportsNoAutoMerge;
    */

    /**
     * Set to make all floating imgui windows always create their own viewport.
     * Otherwise, they are merged into the main host viewports when overlapping it. May also set ImGuiViewportFlags_NoAutoMerge on individual viewport.
     */
    public native void setConfigViewportsNoAutoMerge(boolean value); /*
        IO->ConfigViewportsNoAutoMerge = value;
    */

    /**
     * Disable default OS task bar icon flag for secondary viewports. When a viewport doesn't want a task bar icon, ImGuiViewportFlags_NoTaskBarIcon will be set on it.
     */
    public native boolean getConfigViewportsNoTaskBarIcon(); /*
        return IO->ConfigViewportsNoTaskBarIcon;
    */

    /**
     * Disable default OS task bar icon flag for secondary viewports. When a viewport doesn't want a task bar icon, ImGuiViewportFlags_NoTaskBarIcon will be set on it.
     */
    public native void setConfigViewportsNoTaskBarIcon(boolean value); /*
        IO->ConfigViewportsNoTaskBarIcon = value;
    */

    /**
     * [BETA] Disable default OS window decoration flag for secondary viewports. When a viewport doesn't want window decorations,
     * ImGuiViewportFlags_NoDecoration will be set on it. Enabling decoration can create subsequent issues at OS levels (e.g. minimum window size).
     */
    public native boolean getConfigViewportsNoDecoration(); /*
        return IO->ConfigViewportsNoDecoration;
    */

    /**
     * [BETA] Disable default OS window decoration flag for secondary viewports. When a viewport doesn't want window decorations,
     * ImGuiViewportFlags_NoDecoration will be set on it. Enabling decoration can create subsequent issues at OS levels (e.g. minimum window size).
     */
    public native void setConfigViewportsNoDecoration(boolean value); /*
        IO->ConfigViewportsNoDecoration = value;
    */

    /**
     * Disable default OS parenting to main viewport for secondary viewports.
     * By default, viewports are marked with ParentViewportId = main_viewport,
     * expecting the platform backend to setup a parent/child relationship between the OS windows (some backend may ignore this).
     * Set to true if you want the default to be 0, then all viewports will be top-level OS windows.
     */
    public native boolean getConfigViewportsNoDefaultParent(); /*
        return IO->ConfigViewportsNoDefaultParent;
    */

    /**
     * Disable default OS parenting to main viewport for secondary viewports.
     * By default, viewports are marked with ParentViewportId = main_viewport,
     * expecting the platform backend to setup a parent/child relationship between the OS windows (some backend may ignore this).
     * Set to true if you want the default to be 0, then all viewports will be top-level OS windows.
     */
    public native void setConfigViewportsNoDefaultParent(boolean value); /*
        IO->ConfigViewportsNoDefaultParent = value;
    */


    /**
     * Time elapsed since last frame, in seconds.
     * <p>
     * BINDING NOTICE: Same as for DisplaySize. This should be modified every frame.
     */
    public native float getDeltaTime(); /*
        return IO->DeltaTime;
    */

    /**
     * Time elapsed since last frame, in seconds.
     * <p>
     * BINDING NOTICE: Same as for DisplaySize. This should be modified every frame.
     */
    public native void setDeltaTime(float deltaTime); /*
        IO->DeltaTime = deltaTime;
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public ImVec2 getMousePos() {
        final ImVec2 value = new ImVec2();
        getMousePos(value);
        return value;
    }

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native void getMousePos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IO->MousePos, dstImVec2);
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native float getMousePosX(); /*
        return IO->MousePos.x;
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native float getMousePosY(); /*
        return IO->MousePos.y;
    */

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public native void setMousePos(float x, float y); /*
        IO->MousePos.x = x;
        IO->MousePos.y = y;
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native void getMouseDown(boolean[] buff); /*
        for (int i = 0; i < 5; i++)
            buff[i] = IO->MouseDown[i];
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native boolean getMouseDown(int idx); /*
        return IO->MouseDown[idx];
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native void setMouseDown(int idx, boolean down); /*
        IO->MouseDown[idx] = down;
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public native void setMouseDown(boolean[] mouseDown); /*
        for (int i = 0; i < 5; i++)
            IO->MouseDown[i] = mouseDown[i];
    */

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    public native float getMouseWheel(); /*
        return IO->MouseWheel;
    */

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    public native void setMouseWheel(float mouseDeltaY); /*
        IO->MouseWheel = mouseDeltaY;
    */

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all backends.
     */
    public native float getMouseWheelH(); /*
        return IO->MouseWheelH;
    */

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all backends.
     */
    public native void setMouseWheelH(float mouseDeltaX); /*
        IO->MouseWheelH = mouseDeltaX;
    */

    /**
     * (Optional) When using multiple viewports: viewport the OS mouse cursor is hovering _IGNORING_ viewports with the ImGuiViewportFlags_NoInputs flag,
     * and _REGARDLESS_ of whether another viewport is focused. Set io.BackendFlags |= ImGuiBackendFlags_HasMouseHoveredViewport if you can provide this info.
     * If you don't imgui will infer the value using the rectangles and last focused time of the viewports it knows about (ignoring other OS windows).
     */
    public native float getMouseHoveredViewport(); /*
        return IO->MouseHoveredViewport;
    */

    /**
     * (Optional) When using multiple viewports: viewport the OS mouse cursor is hovering _IGNORING_ viewports with the ImGuiViewportFlags_NoInputs flag,
     * and _REGARDLESS_ of whether another viewport is focused. Set io.BackendFlags |= ImGuiBackendFlags_HasMouseHoveredViewport if you can provide this info.
     * If you don't imgui will infer the value using the rectangles and last focused time of the viewports it knows about (ignoring other OS windows).
     */
    public native void setMouseHoveredViewport(int imGuiId); /*
        IO->MouseHoveredViewport = imGuiId;
    */

    /**
     * Keyboard modifier pressed: Control
     */
    public native boolean getKeyCtrl(); /*
        return IO->KeyCtrl;
    */

    /**
     * Keyboard modifier pressed: Control
     */
    public native void setKeyCtrl(boolean value); /*
        IO->KeyCtrl = value;
    */

    /**
     * Keyboard modifier pressed: Shift
     */
    public native boolean getKeyShift(); /*
        return IO->KeyShift;
    */

    /**
     * Keyboard modifier pressed: Shift
     */
    public native void setKeyShift(boolean value); /*
        IO->KeyShift = value;
    */

    /**
     * Keyboard modifier pressed: Alt
     */
    public native boolean getKeyAlt(); /*
        return IO->KeyAlt;
    */

    /**
     * Keyboard modifier pressed: Alt
     */
    public native void setKeyAlt(boolean value); /*
        IO->KeyAlt = value;
    */

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    public native boolean getKeySuper(); /*
        return IO->KeySuper;
    */

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    public native void setKeySuper(boolean value); /*
        IO->KeySuper = value;
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native void getKeysDown(boolean[] buff); /*
        for (int i = 0; i < 512; i++)
            buff[i] = IO->KeysDown[i];
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native boolean getKeysDown(int idx); /*
        return IO->KeysDown[idx];
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native void setKeysDown(int idx, boolean pressed); /*
        IO->KeysDown[idx] = pressed;
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    public native void setKeysDown(boolean[] keysDown); /*
        for (int i = 0; i < 512; i++)
            IO->KeysDown[i] = keysDown[i];
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native void getNavInputs(float[] buff); /*
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            buff[i] = IO->NavInputs[i];
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native float getNavInputs(int idx); /*
        return IO->NavInputs[idx];
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native void setNavInputs(int idx, float input); /*
        IO->NavInputs[idx] = input;
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public native void setNavInputs(float[] navInputs); /*
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            IO->NavInputs[i] = navInputs[i];
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
        return IO->WantCaptureMouse;
    */

    /**
     * Set when Dear ImGui will use mouse inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass on mouse inputs to imgui).
     * (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
     */
    public native void setWantCaptureMouse(boolean wantCaptureMouse); /*
        IO->WantCaptureMouse = wantCaptureMouse;
    */

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    public native boolean getWantCaptureKeyboard(); /*
        return IO->WantCaptureKeyboard;
    */

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    public native void setWantCaptureKeyboard(boolean wantCaptureKeyboard); /*
        IO->WantCaptureKeyboard = wantCaptureKeyboard;
    */

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    public native boolean getWantTextInput(); /*
        return IO->WantTextInput;
    */

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    public native void setWantTextInput(boolean wantTextInput); /*
        IO->WantTextInput = wantTextInput;
    */

    /**
     * MousePos has been altered, backend should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    public native boolean getWantSetMousePos(); /*
        return IO->WantSetMousePos;
    */

    /**
     * MousePos has been altered, backend should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    public native void setWantSetMousePos(boolean wantSetMousePos); /*
        IO->WantSetMousePos = wantSetMousePos;
    */

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    public native boolean getWantSaveIniSettings(); /*
        return IO->WantSaveIniSettings;
    */

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    public native void setWantSaveIniSettings(boolean wantSaveIniSettings); /*
        IO->WantSaveIniSettings = wantSaveIniSettings;
    */

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    public native boolean getNavActive(); /*
        return IO->NavActive;
    */

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    public native void setNavActive(boolean navActive); /*
        IO->NavActive = navActive;
    */

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    public native boolean getNavVisible(); /*
        return IO->NavVisible;
    */

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    public native void setNavVisible(boolean navVisible); /*
        IO->NavVisible = navVisible;
    */

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    public native float getFramerate(); /*
        return IO->Framerate;
    */

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    public native void setFramerate(float framerate); /*
        IO->Framerate = framerate;
    */

    /**
     * Vertices output during last call to Render()
     */
    public native int getMetricsRenderVertices(); /*
        return IO->MetricsRenderVertices;
    */

    /**
     * Vertices output during last call to Render()
     */
    public native void setMetricsRenderVertices(int metricsRenderVertices); /*
        IO->MetricsRenderVertices = metricsRenderVertices;
    */

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    public native int getMetricsRenderIndices(); /*
        return IO->MetricsRenderIndices;
    */

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    public native void setMetricsRenderIndices(int metricsRenderIndices); /*
        IO->MetricsRenderIndices = metricsRenderIndices;
    */

    /**
     * Number of visible windows
     */
    public native int getMetricsRenderWindows(); /*
        return IO->MetricsRenderWindows;
    */

    /**
     * Number of visible windows
     */
    public native void setMetricsRenderWindows(int metricsRenderWindows); /*
        IO->MetricsRenderWindows = metricsRenderWindows;
    */

    /**
     * Number of active windows
     */
    public native int getMetricsActiveWindows(); /*
        return IO->MetricsActiveWindows;
    */

    /**
     * Number of active windows
     */
    public native void setMetricsActiveWindows(int metricsActiveWindows); /*
        IO->MetricsActiveWindows = metricsActiveWindows;
    */

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    public native int getMetricsActiveAllocations(); /*
        return IO->MetricsActiveAllocations;
    */

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    public native void setMetricsActiveAllocations(int metricsActiveAllocations); /*
        IO->MetricsActiveAllocations = metricsActiveAllocations;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public ImVec2 getMouseDelta() {
        final ImVec2 value = new ImVec2();
        getMouseDelta(value);
        return value;
    }

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native void getMouseDelta(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, &IO->MouseDelta, dstImVec2);
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native float getMouseDeltaX(); /*
        return IO->MouseDelta.x;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native float getMouseDeltaY(); /*
        return IO->MouseDelta.y;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public native void setMouseDelta(float x, float y); /*
        IO->MouseDelta.x = x;
        IO->MouseDelta.y = y;
    */

    // Functions

    /**
     * Queue new character input.
     */
    public native void addInputCharacter(int c); /*
        IO->AddInputCharacter((unsigned int)c);
    */

    /**
     * Queue new character input from an UTF-16 character, it can be a surrogate
     */
    public native void addInputCharacterUTF16(short c); /*
        IO->AddInputCharacterUTF16((ImWchar16)c);
    */

    /**
     * Queue new characters input from an UTF-8 string.
     */
    public native void addInputCharactersUTF8(String str); /*
        IO->AddInputCharactersUTF8(str);
    */

    /**
     * Notifies Dear ImGui when hosting platform windows lose or gain input focus
     */
    public native void addFocusEvent(boolean focused); /*
        IO->AddFocusEvent(focused);
    */

    /**
     * [Internal] Clear the text input buffer manually
     */
    public native void clearInputCharacters(); /*
        IO->ClearInputCharacters();
    */

    /**
     * [Internal] Release all keys
     */
    public native void clearInputKeys(); /*
        IO->ClearInputKeys();
    */
}
