package imgui;

import imgui.binding.ImGuiStruct;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;

/**
 * Communicate most settings and inputs/outputs to Dear ImGui using this structure.
 * Access via ImGui::GetIO(). Read 'Programmer guide' section in .cpp file for general usage.
 */
public final class ImGuiIO extends ImGuiStruct {
    public ImGuiIO(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiIO*)STRUCT_PTR)
     */

    //------------------------------------------------------------------
    // Configuration (fill once)
    //------------------------------------------------------------------

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public int getConfigFlags() {
        return nGetConfigFlags();
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void setConfigFlags(final int value) {
        nSetConfigFlags(value);
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void addConfigFlags(final int flags) {
        setConfigFlags(getConfigFlags() | flags);
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public void removeConfigFlags(final int flags) {
        setConfigFlags(getConfigFlags() & ~(flags));
    }

    /**
     * See ImGuiConfigFlags enum. Set by user/application. Gamepad/keyboard navigation options, etc.
     */
    public boolean hasConfigFlags(final int flags) {
        return (getConfigFlags() & flags) != 0;
    }

    private native int nGetConfigFlags(); /*
        return THIS->ConfigFlags;
    */

    private native void nSetConfigFlags(int value); /*
        THIS->ConfigFlags = value;
    */

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public int getBackendFlags() {
        return nGetBackendFlags();
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public void setBackendFlags(final int value) {
        nSetBackendFlags(value);
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public void addBackendFlags(final int flags) {
        setBackendFlags(getBackendFlags() | flags);
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public void removeBackendFlags(final int flags) {
        setBackendFlags(getBackendFlags() & ~(flags));
    }

    /**
     * See ImGuiBackendFlags enum. Set by backend to communicate features supported by the backend.
     */
    public boolean hasBackendFlags(final int flags) {
        return (getBackendFlags() & flags) != 0;
    }

    private native int nGetBackendFlags(); /*
        return THIS->BackendFlags;
    */

    private native void nSetBackendFlags(int value); /*
        THIS->BackendFlags = value;
    */

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size})
     */
    public ImVec2 getDisplaySize() {
        final ImVec2 dst = new ImVec2();
        nGetDisplaySize(dst);
        return dst;
    }

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size})
     */
    public float getDisplaySizeX() {
        return nGetDisplaySizeX();
    }

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size})
     */
    public float getDisplaySizeY() {
        return nGetDisplaySizeY();
    }

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size})
     */
    public void getDisplaySize(final ImVec2 dst) {
        nGetDisplaySize(dst);
    }

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size})
     */
    public void setDisplaySize(final ImVec2 value) {
        nSetDisplaySize(value.x, value.y);
    }

    /**
     * Main display size, in pixels (generally == {@code GetMainViewport()->Size})
     */
    public void setDisplaySize(final float valueX, final float valueY) {
        nSetDisplaySize(valueX, valueY);
    }

    private native void nGetDisplaySize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->DisplaySize, dst);
    */

    private native float nGetDisplaySizeX(); /*
        return THIS->DisplaySize.x;
    */

    private native float nGetDisplaySizeY(); /*
        return THIS->DisplaySize.y;
    */

    private native void nSetDisplaySize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->DisplaySize = value;
    */

    /**
     * Time elapsed since last frame, in seconds.
     */
    public float getDeltaTime() {
        return nGetDeltaTime();
    }

    /**
     * Time elapsed since last frame, in seconds.
     */
    public void setDeltaTime(final float value) {
        nSetDeltaTime(value);
    }

    private native float nGetDeltaTime(); /*
        return THIS->DeltaTime;
    */

    private native void nSetDeltaTime(float value); /*
        THIS->DeltaTime = value;
    */

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    public float getIniSavingRate() {
        return nGetIniSavingRate();
    }

    /**
     * Minimum time between saving positions/sizes to .ini file, in seconds.
     */
    public void setIniSavingRate(final float value) {
        nSetIniSavingRate(value);
    }

    private native float nGetIniSavingRate(); /*
        return THIS->IniSavingRate;
    */

    private native void nSetIniSavingRate(float value); /*
        THIS->IniSavingRate = value;
    */

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    public String getIniFilename() {
        return nGetIniFilename();
    }

    /**
     * Path to .ini file. Set NULL to disable automatic .ini loading/saving, if e.g. you want to manually load/save from memory.
     */
    public void setIniFilename(final String value) {
        nSetIniFilename(value);
    }

    private native String nGetIniFilename(); /*
        return env->NewStringUTF(THIS->IniFilename);
    */

    private native void nSetIniFilename(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->IniFilename = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    public String getLogFilename() {
        return nGetLogFilename();
    }

    /**
     * Path to .log file (default parameter to ImGui::LogToFile when no file is specified).
     */
    public void setLogFilename(final String value) {
        nSetLogFilename(value);
    }

    private native String nGetLogFilename(); /*
        return env->NewStringUTF(THIS->LogFilename);
    */

    private native void nSetLogFilename(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->LogFilename = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    /**
     * Time for a double-click, in seconds.
     */
    public float getMouseDoubleClickTime() {
        return nGetMouseDoubleClickTime();
    }

    /**
     * Time for a double-click, in seconds.
     */
    public void setMouseDoubleClickTime(final float value) {
        nSetMouseDoubleClickTime(value);
    }

    private native float nGetMouseDoubleClickTime(); /*
        return THIS->MouseDoubleClickTime;
    */

    private native void nSetMouseDoubleClickTime(float value); /*
        THIS->MouseDoubleClickTime = value;
    */

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    public float getMouseDoubleClickMaxDist() {
        return nGetMouseDoubleClickMaxDist();
    }

    /**
     * Distance threshold to stay in to validate a double-click, in pixels.
     */
    public void setMouseDoubleClickMaxDist(final float value) {
        nSetMouseDoubleClickMaxDist(value);
    }

    private native float nGetMouseDoubleClickMaxDist(); /*
        return THIS->MouseDoubleClickMaxDist;
    */

    private native void nSetMouseDoubleClickMaxDist(float value); /*
        THIS->MouseDoubleClickMaxDist = value;
    */

    /**
     * Distance threshold before considering we are dragging.
     */
    public float getMouseDragThreshold() {
        return nGetMouseDragThreshold();
    }

    /**
     * Distance threshold before considering we are dragging.
     */
    public void setMouseDragThreshold(final float value) {
        nSetMouseDragThreshold(value);
    }

    private native float nGetMouseDragThreshold(); /*
        return THIS->MouseDragThreshold;
    */

    private native void nSetMouseDragThreshold(float value); /*
        THIS->MouseDragThreshold = value;
    */

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    public float getKeyRepeatDelay() {
        return nGetKeyRepeatDelay();
    }

    /**
     * When holding a key/button, time before it starts repeating, in seconds (for buttons in Repeat mode, etc.).
     */
    public void setKeyRepeatDelay(final float value) {
        nSetKeyRepeatDelay(value);
    }

    private native float nGetKeyRepeatDelay(); /*
        return THIS->KeyRepeatDelay;
    */

    private native void nSetKeyRepeatDelay(float value); /*
        THIS->KeyRepeatDelay = value;
    */

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    public float getKeyRepeatRate() {
        return nGetKeyRepeatRate();
    }

    /**
     * When holding a key/button, rate at which it repeats, in seconds.
     */
    public void setKeyRepeatRate(final float value) {
        nSetKeyRepeatRate(value);
    }

    private native float nGetKeyRepeatRate(); /*
        return THIS->KeyRepeatRate;
    */

    private native void nSetKeyRepeatRate(float value); /*
        THIS->KeyRepeatRate = value;
    */

    private static final ImFontAtlas _GETFONTS_1 = new ImFontAtlas(0);

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     */
    public ImFontAtlas getFonts() {
        _GETFONTS_1.ptr = nGetFonts();
        return _GETFONTS_1;
    }

    /**
     * Font atlas: load, rasterize and pack one or more fonts into a single texture.
     */
    public void setFonts(final ImFontAtlas value) {
        nSetFonts(value.ptr);
    }

    private native long nGetFonts(); /*
        return (intptr_t)THIS->Fonts;
    */

    private native void nSetFonts(long value); /*
        THIS->Fonts = reinterpret_cast<ImFontAtlas*>(value);
    */

    /**
     * Global scale all fonts
     */
    public float getFontGlobalScale() {
        return nGetFontGlobalScale();
    }

    /**
     * Global scale all fonts
     */
    public void setFontGlobalScale(final float value) {
        nSetFontGlobalScale(value);
    }

    private native float nGetFontGlobalScale(); /*
        return THIS->FontGlobalScale;
    */

    private native void nSetFontGlobalScale(float value); /*
        THIS->FontGlobalScale = value;
    */

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    public boolean getFontAllowUserScaling() {
        return nGetFontAllowUserScaling();
    }

    /**
     * Allow user scaling text of individual window with CTRL+Wheel.
     */
    public void setFontAllowUserScaling(final boolean value) {
        nSetFontAllowUserScaling(value);
    }

    private native boolean nGetFontAllowUserScaling(); /*
        return THIS->FontAllowUserScaling;
    */

    private native void nSetFontAllowUserScaling(boolean value); /*
        THIS->FontAllowUserScaling = value;
    */

    /**
     * Font to use on NewFrame(). Use NULL to uses Fonts{@code ->}Fonts[0].
     */
    public ImFont getFontDefault() {
        return new ImFont(nGetFontDefault());
    }

    /**
     * Font to use on NewFrame(). Use NULL to uses Fonts{@code ->}Fonts[0].
     */
    public void setFontDefault(final ImFont value) {
        nSetFontDefault(value.ptr);
    }

    private native long nGetFontDefault(); /*
        return (intptr_t)THIS->FontDefault;
    */

    private native void nSetFontDefault(long value); /*
        THIS->FontDefault = reinterpret_cast<ImFont*>(value);
    */

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    public ImVec2 getDisplayFramebufferScale() {
        final ImVec2 dst = new ImVec2();
        nGetDisplayFramebufferScale(dst);
        return dst;
    }

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    public float getDisplayFramebufferScaleX() {
        return nGetDisplayFramebufferScaleX();
    }

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    public float getDisplayFramebufferScaleY() {
        return nGetDisplayFramebufferScaleY();
    }

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    public void getDisplayFramebufferScale(final ImVec2 dst) {
        nGetDisplayFramebufferScale(dst);
    }

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    public void setDisplayFramebufferScale(final ImVec2 value) {
        nSetDisplayFramebufferScale(value.x, value.y);
    }

    /**
     * For retina display or other situations where window coordinates are different from framebuffer coordinates. This generally ends up in ImDrawData::FramebufferScale.
     */
    public void setDisplayFramebufferScale(final float valueX, final float valueY) {
        nSetDisplayFramebufferScale(valueX, valueY);
    }

    private native void nGetDisplayFramebufferScale(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->DisplayFramebufferScale, dst);
    */

    private native float nGetDisplayFramebufferScaleX(); /*
        return THIS->DisplayFramebufferScale.x;
    */

    private native float nGetDisplayFramebufferScaleY(); /*
        return THIS->DisplayFramebufferScale.y;
    */

    private native void nSetDisplayFramebufferScale(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->DisplayFramebufferScale = value;
    */

    // Docking options (when ImGuiConfigFlags_DockingEnable is set)

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    public boolean getConfigDockingNoSplit() {
        return nGetConfigDockingNoSplit();
    }

    /**
     * Simplified docking mode: disable window splitting, so docking is limited to merging multiple windows together into tab-bars.
     */
    public void setConfigDockingNoSplit(final boolean value) {
        nSetConfigDockingNoSplit(value);
    }

    private native boolean nGetConfigDockingNoSplit(); /*
        return THIS->ConfigDockingNoSplit;
    */

    private native void nSetConfigDockingNoSplit(boolean value); /*
        THIS->ConfigDockingNoSplit = value;
    */

    /**
     * Enable docking with holding Shift key (reduce visual noise, allows dropping in wider space)
     */
    public boolean getConfigDockingWithShift() {
        return nGetConfigDockingWithShift();
    }

    /**
     * Enable docking with holding Shift key (reduce visual noise, allows dropping in wider space)
     */
    public void setConfigDockingWithShift(final boolean value) {
        nSetConfigDockingWithShift(value);
    }

    private native boolean nGetConfigDockingWithShift(); /*
        return THIS->ConfigDockingWithShift;
    */

    private native void nSetConfigDockingWithShift(boolean value); /*
        THIS->ConfigDockingWithShift = value;
    */

    public boolean getConfigDockingAlwaysTabBar() {
        return nGetConfigDockingAlwaysTabBar();
    }

    public void setConfigDockingAlwaysTabBar(final boolean value) {
        nSetConfigDockingAlwaysTabBar(value);
    }

    private native boolean nGetConfigDockingAlwaysTabBar(); /*
        return THIS->ConfigDockingAlwaysTabBar;
    */

    private native void nSetConfigDockingAlwaysTabBar(boolean value); /*
        THIS->ConfigDockingAlwaysTabBar = value;
    */

    /**
     * Make window or viewport transparent when docking and only display docking boxes on the target viewport. Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    public boolean getConfigDockingTransparentPayload() {
        return nGetConfigDockingTransparentPayload();
    }

    /**
     * Make window or viewport transparent when docking and only display docking boxes on the target viewport. Useful if rendering of multiple viewport cannot be synced. Best used with ConfigViewportsNoAutoMerge.
     */
    public void setConfigDockingTransparentPayload(final boolean value) {
        nSetConfigDockingTransparentPayload(value);
    }

    private native boolean nGetConfigDockingTransparentPayload(); /*
        return THIS->ConfigDockingTransparentPayload;
    */

    private native void nSetConfigDockingTransparentPayload(boolean value); /*
        THIS->ConfigDockingTransparentPayload = value;
    */

    // Viewport options (when ImGuiConfigFlags_ViewportsEnable is set)

    /**
     * Set to make all floating imgui windows always create their own viewport. Otherwise, they are merged into the main host viewports when overlapping it. May also set ImGuiViewportFlags_NoAutoMerge on individual viewport.
     */
    public boolean getConfigViewportsNoAutoMerge() {
        return nGetConfigViewportsNoAutoMerge();
    }

    /**
     * Set to make all floating imgui windows always create their own viewport. Otherwise, they are merged into the main host viewports when overlapping it. May also set ImGuiViewportFlags_NoAutoMerge on individual viewport.
     */
    public void setConfigViewportsNoAutoMerge(final boolean value) {
        nSetConfigViewportsNoAutoMerge(value);
    }

    private native boolean nGetConfigViewportsNoAutoMerge(); /*
        return THIS->ConfigViewportsNoAutoMerge;
    */

    private native void nSetConfigViewportsNoAutoMerge(boolean value); /*
        THIS->ConfigViewportsNoAutoMerge = value;
    */

    /**
     * Disable default OS task bar icon flag for secondary viewports. When a viewport doesn't want a task bar icon, ImGuiViewportFlags_NoTaskBarIcon will be set on it.
     */
    public boolean getConfigViewportsNoTaskBarIcon() {
        return nGetConfigViewportsNoTaskBarIcon();
    }

    /**
     * Disable default OS task bar icon flag for secondary viewports. When a viewport doesn't want a task bar icon, ImGuiViewportFlags_NoTaskBarIcon will be set on it.
     */
    public void setConfigViewportsNoTaskBarIcon(final boolean value) {
        nSetConfigViewportsNoTaskBarIcon(value);
    }

    private native boolean nGetConfigViewportsNoTaskBarIcon(); /*
        return THIS->ConfigViewportsNoTaskBarIcon;
    */

    private native void nSetConfigViewportsNoTaskBarIcon(boolean value); /*
        THIS->ConfigViewportsNoTaskBarIcon = value;
    */

    /**
     * Disable default OS window decoration flag for secondary viewports. When a viewport doesn't want window decorations, ImGuiViewportFlags_NoDecoration will be set on it. Enabling decoration can create subsequent issues at OS levels (e.g. minimum window size).
     */
    public boolean getConfigViewportsNoDecoration() {
        return nGetConfigViewportsNoDecoration();
    }

    /**
     * Disable default OS window decoration flag for secondary viewports. When a viewport doesn't want window decorations, ImGuiViewportFlags_NoDecoration will be set on it. Enabling decoration can create subsequent issues at OS levels (e.g. minimum window size).
     */
    public void setConfigViewportsNoDecoration(final boolean value) {
        nSetConfigViewportsNoDecoration(value);
    }

    private native boolean nGetConfigViewportsNoDecoration(); /*
        return THIS->ConfigViewportsNoDecoration;
    */

    private native void nSetConfigViewportsNoDecoration(boolean value); /*
        THIS->ConfigViewportsNoDecoration = value;
    */

    /**
     * Disable default OS parenting to main viewport for secondary viewports. By default, viewports are marked with ParentViewportId = {@code <main_viewport>}, expecting the platform backend to setup a parent/child relationship between the OS windows (some backend may ignore this). Set to true if you want the default to be 0, then all viewports will be top-level OS windows.
     */
    public boolean getConfigViewportsNoDefaultParent() {
        return nGetConfigViewportsNoDefaultParent();
    }

    /**
     * Disable default OS parenting to main viewport for secondary viewports. By default, viewports are marked with ParentViewportId = {@code <main_viewport>}, expecting the platform backend to setup a parent/child relationship between the OS windows (some backend may ignore this). Set to true if you want the default to be 0, then all viewports will be top-level OS windows.
     */
    public void setConfigViewportsNoDefaultParent(final boolean value) {
        nSetConfigViewportsNoDefaultParent(value);
    }

    private native boolean nGetConfigViewportsNoDefaultParent(); /*
        return THIS->ConfigViewportsNoDefaultParent;
    */

    private native void nSetConfigViewportsNoDefaultParent(boolean value); /*
        THIS->ConfigViewportsNoDefaultParent = value;
    */

    // Miscellaneous options

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by backend implementations.
     */
    public boolean getMouseDrawCursor() {
        return nGetMouseDrawCursor();
    }

    /**
     * Request ImGui to draw a mouse cursor for you (if you are on a platform without a mouse cursor).
     * Cannot be easily renamed to 'io.ConfigXXX' because this is frequently used by backend implementations.
     */
    public void setMouseDrawCursor(final boolean value) {
        nSetMouseDrawCursor(value);
    }

    private native boolean nGetMouseDrawCursor(); /*
        return THIS->MouseDrawCursor;
    */

    private native void nSetMouseDrawCursor(boolean value); /*
        THIS->MouseDrawCursor = value;
    */

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl
     */
    public boolean getConfigMacOSXBehaviors() {
        return nGetConfigMacOSXBehaviors();
    }

    /**
     * OS X style: Text editing cursor movement using Alt instead of Ctrl, Shortcuts using Cmd/Super instead of Ctrl,
     * Line/Text Start and End using Cmd+Arrows instead of Home/End, Double click selects by word instead of selecting whole text,
     * Multi-selection in lists uses Cmd/Super instead of Ctrl
     */
    public void setConfigMacOSXBehaviors(final boolean value) {
        nSetConfigMacOSXBehaviors(value);
    }

    private native boolean nGetConfigMacOSXBehaviors(); /*
        return THIS->ConfigMacOSXBehaviors;
    */

    private native void nSetConfigMacOSXBehaviors(boolean value); /*
        THIS->ConfigMacOSXBehaviors = value;
    */

    /**
     * Enable input queue trickling: some types of events submitted during the same frame (e.g. button down + up) will be spread over multiple frames, improving interactions with low framerates.
     */
    public boolean getConfigInputTrickleEventQueue() {
        return nGetConfigInputTrickleEventQueue();
    }

    /**
     * Enable input queue trickling: some types of events submitted during the same frame (e.g. button down + up) will be spread over multiple frames, improving interactions with low framerates.
     */
    public void setConfigInputTrickleEventQueue(final boolean value) {
        nSetConfigInputTrickleEventQueue(value);
    }

    private native boolean nGetConfigInputTrickleEventQueue(); /*
        return THIS->ConfigInputTrickleEventQueue;
    */

    private native void nSetConfigInputTrickleEventQueue(boolean value); /*
        THIS->ConfigInputTrickleEventQueue = value;
    */

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting.
     */
    public boolean getConfigInputTextCursorBlink() {
        return nGetConfigInputTextCursorBlink();
    }

    /**
     * Set to false to disable blinking cursor, for users who consider it distracting.
     */
    public void setConfigInputTextCursorBlink(final boolean value) {
        nSetConfigInputTextCursorBlink(value);
    }

    private native boolean nGetConfigInputTextCursorBlink(); /*
        return THIS->ConfigInputTextCursorBlink;
    */

    private native void nSetConfigInputTextCursorBlink(boolean value); /*
        THIS->ConfigInputTextCursorBlink = value;
    */

    /**
     * [BETA] Enable turning DragXXX widgets into text input with a simple mouse click-release (without moving). Not desirable on devices without a keyboard.
     */
    public boolean getConfigDragClickToInputText() {
        return nGetConfigDragClickToInputText();
    }

    /**
     * [BETA] Enable turning DragXXX widgets into text input with a simple mouse click-release (without moving). Not desirable on devices without a keyboard.
     */
    public void setConfigDragClickToInputText(final boolean value) {
        nSetConfigDragClickToInputText(value);
    }

    private native boolean nGetConfigDragClickToInputText(); /*
        return THIS->ConfigDragClickToInputText;
    */

    private native void nSetConfigDragClickToInputText(boolean value); /*
        THIS->ConfigDragClickToInputText = value;
    */

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    public boolean getConfigWindowsResizeFromEdges() {
        return nGetConfigWindowsResizeFromEdges();
    }

    /**
     * Enable resizing of windows from their edges and from the lower-left corner.
     * This requires (io.BackendFlags {@code &} ImGuiBackendFlags_HasMouseCursors) because it needs mouse cursor feedback.
     * (This used to be a per-window ImGuiWindowFlags_ResizeFromAnySide flag)
     */
    public void setConfigWindowsResizeFromEdges(final boolean value) {
        nSetConfigWindowsResizeFromEdges(value);
    }

    private native boolean nGetConfigWindowsResizeFromEdges(); /*
        return THIS->ConfigWindowsResizeFromEdges;
    */

    private native void nSetConfigWindowsResizeFromEdges(boolean value); /*
        THIS->ConfigWindowsResizeFromEdges = value;
    */

    /**
     * Enable allowing to move windows only when clicking on their title bar. Does not apply to windows without a title bar.
     */
    public boolean getConfigWindowsMoveFromTitleBarOnly() {
        return nGetConfigWindowsMoveFromTitleBarOnly();
    }

    /**
     * Enable allowing to move windows only when clicking on their title bar. Does not apply to windows without a title bar.
     */
    public void setConfigWindowsMoveFromTitleBarOnly(final boolean value) {
        nSetConfigWindowsMoveFromTitleBarOnly(value);
    }

    private native boolean nGetConfigWindowsMoveFromTitleBarOnly(); /*
        return THIS->ConfigWindowsMoveFromTitleBarOnly;
    */

    private native void nSetConfigWindowsMoveFromTitleBarOnly(boolean value); /*
        THIS->ConfigWindowsMoveFromTitleBarOnly = value;
    */

    /**
     * [Timer (in seconds) to free transient windows/tables memory buffers when unused. Set to -1.0f to disable.
     */
    public boolean getConfigMemoryCompactTimer() {
        return nGetConfigMemoryCompactTimer();
    }

    /**
     * [Timer (in seconds) to free transient windows/tables memory buffers when unused. Set to -1.0f to disable.
     */
    public void setConfigMemoryCompactTimer(final boolean value) {
        nSetConfigMemoryCompactTimer(value);
    }

    private native boolean nGetConfigMemoryCompactTimer(); /*
        return THIS->ConfigMemoryCompactTimer;
    */

    private native void nSetConfigMemoryCompactTimer(boolean value); /*
        THIS->ConfigMemoryCompactTimer = value;
    */

    //------------------------------------------------------------------
    // Platform Functions
    //------------------------------------------------------------------

    /**
     * Optional: Platform backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    public String getBackendPlatformName() {
        return nGetBackendPlatformName();
    }

    /**
     * Optional: Platform backend name (informational only! will be displayed in About Window) + User data for backend/wrappers to store their own stuff.
     */
    public void setBackendPlatformName(final String value) {
        nSetBackendPlatformName(value);
    }

    private native String nGetBackendPlatformName(); /*
        return env->NewStringUTF(THIS->BackendPlatformName);
    */

    private native void nSetBackendPlatformName(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->BackendPlatformName = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    public String getBackendRendererName() {
        return nGetBackendRendererName();
    }

    public void setBackendRendererName(final String value) {
        nSetBackendRendererName(value);
    }

    private native String nGetBackendRendererName(); /*
        return env->NewStringUTF(THIS->BackendRendererName);
    */

    private native void nSetBackendRendererName(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        THIS->BackendRendererName = value;
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
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
        THIS->SetClipboardTextFn = setClipboardTextStub;
    */

    public native void setGetClipboardTextFn(ImStrSupplier getClipboardTextCallback); /*
        if (_getClipboardTextCallback != NULL) {
            env->DeleteGlobalRef(_getClipboardTextCallback);
        }
        _getClipboardTextCallback = env->NewGlobalRef(getClipboardTextCallback);
        THIS->GetClipboardTextFn = getClipboardTextStub;
    */

    //------------------------------------------------------------------
    // Input - Call before calling NewFrame()
    //------------------------------------------------------------------

    // Input Functions

    /**
     * Queue a new key down/up event. Key should be "translated" (as in, generally ImGuiKey_A matches the key end-user would use to emit an 'A' character)
     */
    public void addKeyEvent(final int key, final boolean down) {
        nAddKeyEvent(key, down);
    }

    private native void nAddKeyEvent(int key, boolean down); /*
        THIS->AddKeyEvent(static_cast<ImGuiKey>(key), down);
    */

    /**
     * Queue a new key down/up event for analog values (e.g. ImGuiKey_Gamepad_ values). Dead-zones should be handled by the backend.
     */
    public void addKeyAnalogEvent(final int key, final boolean down, final float v) {
        nAddKeyAnalogEvent(key, down, v);
    }

    private native void nAddKeyAnalogEvent(int key, boolean down, float v); /*
        THIS->AddKeyAnalogEvent(static_cast<ImGuiKey>(key), down, v);
    */

    /**
     * Queue a mouse position update. Use -FLT_MAX,-FLT_MAX to signify no mouse (e.g. app not focused and not hovered)
     */
    public void addMousePosEvent(final float x, final float y) {
        nAddMousePosEvent(x, y);
    }

    private native void nAddMousePosEvent(float x, float y); /*
        THIS->AddMousePosEvent(x, y);
    */

    /**
     * Queue a mouse button change
     */
    public void addMouseButtonEvent(final int button, final boolean down) {
        nAddMouseButtonEvent(button, down);
    }

    private native void nAddMouseButtonEvent(int button, boolean down); /*
        THIS->AddMouseButtonEvent(button, down);
    */

    /**
     * Queue a mouse wheel update
     */
    public void addMouseWheelEvent(final float whX, final float whY) {
        nAddMouseWheelEvent(whX, whY);
    }

    private native void nAddMouseWheelEvent(float whX, float whY); /*
        THIS->AddMouseWheelEvent(whX, whY);
    */

    /**
     * Queue a mouse hovered viewport. Requires backend to set ImGuiBackendFlags_HasMouseHoveredViewport to call this (for multi-viewport support).
     */
    public void addMouseViewportEvent(final int id) {
        nAddMouseViewportEvent(id);
    }

    private native void nAddMouseViewportEvent(int id); /*
        THIS->AddMouseViewportEvent(static_cast<ImGuiID>(id));
    */

    /**
     * Queue a gain/loss of focus for the application (generally based on OS/platform focus of your window)
     */
    public void addFocusEvent(final boolean focused) {
        nAddFocusEvent(focused);
    }

    private native void nAddFocusEvent(boolean focused); /*
        THIS->AddFocusEvent(focused);
    */

    /**
     * Queue new character input.
     */
    public void addInputCharacter(final int c) {
        nAddInputCharacter(c);
    }

    private native void nAddInputCharacter(int c); /*
        THIS->AddInputCharacter((unsigned int)c);
    */

    /**
     * Queue new character input from an UTF-16 character, it can be a surrogate
     */
    public void addInputCharacterUTF16(final short c) {
        nAddInputCharacterUTF16(c);
    }

    private native void nAddInputCharacterUTF16(short c); /*
        THIS->AddInputCharacterUTF16((ImWchar16)c);
    */

    /**
     * Queue new characters input from an UTF-8 string.
     */
    public void addInputCharactersUTF8(final String str) {
        nAddInputCharactersUTF8(str);
    }

    private native void nAddInputCharactersUTF8(String str); /*MANUAL
        auto str = obj_str == NULL ? NULL : (char*)env->GetStringUTFChars(obj_str, JNI_FALSE);
        THIS->AddInputCharactersUTF8(str);
        if (str != NULL) env->ReleaseStringUTFChars(obj_str, str);
    */

    /**
     * [Internal] Clear the text input buffer manually
     */
    public void clearInputCharacters() {
        nClearInputCharacters();
    }

    private native void nClearInputCharacters(); /*
        THIS->ClearInputCharacters();
    */

    /**
     * [Internal] Release all keys
     */
    public void clearInputKeys() {
        nClearInputKeys();
    }

    private native void nClearInputKeys(); /*
        THIS->ClearInputKeys();
    */

    /**
     * [Optional] Specify index for legacy before 1.87 IsKeyXXX() functions with native indices + specify native keycode, scancode.
     */
    public void setKeyEventNativeData(final int key, final int nativeKeycode, final int nativeScancode) {
        nSetKeyEventNativeData(key, nativeKeycode, nativeScancode);
    }

    /**
     * [Optional] Specify index for legacy before 1.87 IsKeyXXX() functions with native indices + specify native keycode, scancode.
     */
    public void setKeyEventNativeData(final int key, final int nativeKeycode, final int nativeScancode, final int nativeLegacyIndex) {
        nSetKeyEventNativeData(key, nativeKeycode, nativeScancode, nativeLegacyIndex);
    }

    private native void nSetKeyEventNativeData(int key, int nativeKeycode, int nativeScancode); /*
        THIS->SetKeyEventNativeData(static_cast<ImGuiKey>(key), nativeKeycode, nativeScancode);
    */

    private native void nSetKeyEventNativeData(int key, int nativeKeycode, int nativeScancode, int nativeLegacyIndex); /*
        THIS->SetKeyEventNativeData(static_cast<ImGuiKey>(key), nativeKeycode, nativeScancode, nativeLegacyIndex);
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
    public boolean getWantCaptureMouse() {
        return nGetWantCaptureMouse();
    }

    /**
     * Set when Dear ImGui will use mouse inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass on mouse inputs to imgui).
     * (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
     */
    public void setWantCaptureMouse(final boolean value) {
        nSetWantCaptureMouse(value);
    }

    private native boolean nGetWantCaptureMouse(); /*
        return THIS->WantCaptureMouse;
    */

    private native void nSetWantCaptureMouse(boolean value); /*
        THIS->WantCaptureMouse = value;
    */

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    public boolean getWantCaptureKeyboard() {
        return nGetWantCaptureKeyboard();
    }

    /**
     * Set when Dear ImGui will use keyboard inputs, in this case do not dispatch them to your main game/application
     * (either way, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
     */
    public void setWantCaptureKeyboard(final boolean value) {
        nSetWantCaptureKeyboard(value);
    }

    private native boolean nGetWantCaptureKeyboard(); /*
        return THIS->WantCaptureKeyboard;
    */

    private native void nSetWantCaptureKeyboard(boolean value); /*
        THIS->WantCaptureKeyboard = value;
    */

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    public boolean getWantTextInput() {
        return nGetWantTextInput();
    }

    /**
     * Mobile/console: when set, you may display an on-screen keyboard.
     * This is set by Dear ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
     */
    public void setWantTextInput(final boolean value) {
        nSetWantTextInput(value);
    }

    private native boolean nGetWantTextInput(); /*
        return THIS->WantTextInput;
    */

    private native void nSetWantTextInput(boolean value); /*
        THIS->WantTextInput = value;
    */

    /**
     * MousePos has been altered, backend should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    public boolean getWantSetMousePos() {
        return nGetWantSetMousePos();
    }

    /**
     * MousePos has been altered, backend should reposition mouse on next frame. Rarely used! Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
     */
    public void setWantSetMousePos(final boolean value) {
        nSetWantSetMousePos(value);
    }

    private native boolean nGetWantSetMousePos(); /*
        return THIS->WantSetMousePos;
    */

    private native void nSetWantSetMousePos(boolean value); /*
        THIS->WantSetMousePos = value;
    */

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    public boolean getWantSaveIniSettings() {
        return nGetWantSaveIniSettings();
    }

    /**
     * When manual .ini load/save is active (io.IniFilename == NULL),
     * this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself.
     * Important: clear io.WantSaveIniSettings yourself after saving!
     */
    public void setWantSaveIniSettings(final boolean value) {
        nSetWantSaveIniSettings(value);
    }

    private native boolean nGetWantSaveIniSettings(); /*
        return THIS->WantSaveIniSettings;
    */

    private native void nSetWantSaveIniSettings(boolean value); /*
        THIS->WantSaveIniSettings = value;
    */

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    public boolean getNavActive() {
        return nGetNavActive();
    }

    /**
     * Keyboard/Gamepad navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused
     * and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
     */
    public void setNavActive(final boolean value) {
        nSetNavActive(value);
    }

    private native boolean nGetNavActive(); /*
        return THIS->NavActive;
    */

    private native void nSetNavActive(boolean value); /*
        THIS->NavActive = value;
    */

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    public boolean getNavVisible() {
        return nGetNavVisible();
    }

    /**
     * Keyboard/Gamepad navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
     */
    public void setNavVisible(final boolean value) {
        nSetNavVisible(value);
    }

    private native boolean nGetNavVisible(); /*
        return THIS->NavVisible;
    */

    private native void nSetNavVisible(boolean value); /*
        THIS->NavVisible = value;
    */

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    public float getFramerate() {
        return nGetFramerate();
    }

    /**
     * Application framerate estimate, in frame per second. Solely for convenience. Rolling average estimation based on io.DeltaTime over 120 frames.
     * Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
     */
    public void setFramerate(final float value) {
        nSetFramerate(value);
    }

    private native float nGetFramerate(); /*
        return THIS->Framerate;
    */

    private native void nSetFramerate(float value); /*
        THIS->Framerate = value;
    */

    /**
     * Vertices output during last call to Render()
     */
    public int getMetricsRenderVertices() {
        return nGetMetricsRenderVertices();
    }

    /**
     * Vertices output during last call to Render()
     */
    public void setMetricsRenderVertices(final int value) {
        nSetMetricsRenderVertices(value);
    }

    private native int nGetMetricsRenderVertices(); /*
        return THIS->MetricsRenderVertices;
    */

    private native void nSetMetricsRenderVertices(int value); /*
        THIS->MetricsRenderVertices = value;
    */

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    public int getMetricsRenderIndices() {
        return nGetMetricsRenderIndices();
    }

    /**
     * Indices output during last call to Render() = number of triangles * 3
     */
    public void setMetricsRenderIndices(final int value) {
        nSetMetricsRenderIndices(value);
    }

    private native int nGetMetricsRenderIndices(); /*
        return THIS->MetricsRenderIndices;
    */

    private native void nSetMetricsRenderIndices(int value); /*
        THIS->MetricsRenderIndices = value;
    */

    /**
     * Number of visible windows
     */
    public int getMetricsRenderWindows() {
        return nGetMetricsRenderWindows();
    }

    /**
     * Number of visible windows
     */
    public void setMetricsRenderWindows(final int value) {
        nSetMetricsRenderWindows(value);
    }

    private native int nGetMetricsRenderWindows(); /*
        return THIS->MetricsRenderWindows;
    */

    private native void nSetMetricsRenderWindows(int value); /*
        THIS->MetricsRenderWindows = value;
    */

    /**
     * Number of active windows
     */
    public int getMetricsActiveWindows() {
        return nGetMetricsActiveWindows();
    }

    /**
     * Number of active windows
     */
    public void setMetricsActiveWindows(final int value) {
        nSetMetricsActiveWindows(value);
    }

    private native int nGetMetricsActiveWindows(); /*
        return THIS->MetricsActiveWindows;
    */

    private native void nSetMetricsActiveWindows(int value); /*
        THIS->MetricsActiveWindows = value;
    */

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    public int getMetricsActiveAllocations() {
        return nGetMetricsActiveAllocations();
    }

    /**
     * Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
     */
    public void setMetricsActiveAllocations(final int value) {
        nSetMetricsActiveAllocations(value);
    }

    private native int nGetMetricsActiveAllocations(); /*
        return THIS->MetricsActiveAllocations;
    */

    private native void nSetMetricsActiveAllocations(int value); /*
        THIS->MetricsActiveAllocations = value;
    */

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public ImVec2 getMouseDelta() {
        final ImVec2 dst = new ImVec2();
        nGetMouseDelta(dst);
        return dst;
    }

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public float getMouseDeltaX() {
        return nGetMouseDeltaX();
    }

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public float getMouseDeltaY() {
        return nGetMouseDeltaY();
    }

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public void getMouseDelta(final ImVec2 dst) {
        nGetMouseDelta(dst);
    }

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public void setMouseDelta(final ImVec2 value) {
        nSetMouseDelta(value.x, value.y);
    }

    /**
     * Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
     */
    public void setMouseDelta(final float valueX, final float valueY) {
        nSetMouseDelta(valueX, valueY);
    }

    private native void nGetMouseDelta(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MouseDelta, dst);
    */

    private native float nGetMouseDeltaX(); /*
        return THIS->MouseDelta.x;
    */

    private native float nGetMouseDeltaY(); /*
        return THIS->MouseDelta.y;
    */

    private native void nSetMouseDelta(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MouseDelta = value;
    */

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    @Deprecated
    public int[] getKeyMap() {
        return nGetKeyMap();
    }

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    @Deprecated
    public int getKeyMap(final int idx) {
        return nGetKeyMap(idx);
    }

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    @Deprecated
    public void setKeyMap(final int[] value) {
        nSetKeyMap(value);
    }

    /**
     * Map of indices into the KeysDown[512] entries array which represent your "native" keyboard state.
     */
    @Deprecated
    public void setKeyMap(final int idx, final int value) {
        nSetKeyMap(idx, value);
    }

    @Deprecated
    private native int[] nGetKeyMap(); /*
        jint jBuf[ImGuiKey_COUNT];
        for (int i = 0; i < ImGuiKey_COUNT; i++)
            jBuf[i] = THIS->KeyMap[i];
        jintArray result = env->NewIntArray(ImGuiKey_COUNT);
        env->SetIntArrayRegion(result, 0, ImGuiKey_COUNT, jBuf);
        return result;
    */

    @Deprecated
    private native int nGetKeyMap(int idx); /*
        return THIS->KeyMap[idx];
    */

    @Deprecated
    private native void nSetKeyMap(int[] value); /*
        for (int i = 0; i < ImGuiKey_COUNT; i++)
            THIS->KeyMap[i] = value[i];
    */

    @Deprecated
    private native int nSetKeyMap(int idx, int value); /*
        THIS->KeyMap[idx] = value;
    */

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    @Deprecated
    public boolean[] getKeysDown() {
        return nGetKeysDown();
    }

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    @Deprecated
    public boolean getKeysDown(final int idx) {
        return nGetKeysDown(idx);
    }

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    @Deprecated
    public void setKeysDown(final boolean[] value) {
        nSetKeysDown(value);
    }

    /**
     * Keyboard keys that are pressed (ideally left in the "native" order your engine has access to keyboard keys, so you can use your own defines/enums for keys).
     */
    @Deprecated
    public void setKeysDown(final int idx, final boolean value) {
        nSetKeysDown(idx, value);
    }

    @Deprecated
    private native boolean[] nGetKeysDown(); /*
        jboolean jBuf[512];
        for (int i = 0; i < 512; i++)
            jBuf[i] = THIS->KeysDown[i];
        jbooleanArray result = env->NewBooleanArray(512);
        env->SetBooleanArrayRegion(result, 0, 512, jBuf);
        return result;
    */

    @Deprecated
    private native boolean nGetKeysDown(int idx); /*
        return THIS->KeysDown[idx];
    */

    @Deprecated
    private native void nSetKeysDown(boolean[] value); /*
        for (int i = 0; i < 512; i++)
            THIS->KeysDown[i] = value[i];
    */

    @Deprecated
    private native boolean nSetKeysDown(int idx, boolean value); /*
        THIS->KeysDown[idx] = value;
    */

    //------------------------------------------------------------------
    // [Internal] Dear ImGui will maintain those fields. Forward compatibility not guaranteed!
    //------------------------------------------------------------------

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public ImVec2 getMousePos() {
        final ImVec2 dst = new ImVec2();
        nGetMousePos(dst);
        return dst;
    }

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public float getMousePosX() {
        return nGetMousePosX();
    }

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public float getMousePosY() {
        return nGetMousePosY();
    }

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public void getMousePos(final ImVec2 dst) {
        nGetMousePos(dst);
    }

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public void setMousePos(final ImVec2 value) {
        nSetMousePos(value.x, value.y);
    }

    /**
     * Mouse position, in pixels. Set to ImVec2(-FLT_MAX, -FLT_MAX) if mouse is unavailable (on another screen, etc.)
     */
    public void setMousePos(final float valueX, final float valueY) {
        nSetMousePos(valueX, valueY);
    }

    private native void nGetMousePos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MousePos, dst);
    */

    private native float nGetMousePosX(); /*
        return THIS->MousePos.x;
    */

    private native float nGetMousePosY(); /*
        return THIS->MousePos.y;
    */

    private native void nSetMousePos(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MousePos = value;
    */

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public boolean[] getMouseDown() {
        return nGetMouseDown();
    }

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public boolean getMouseDown(final int idx) {
        return nGetMouseDown(idx);
    }

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public void setMouseDown(final boolean[] value) {
        nSetMouseDown(value);
    }

    /**
     * Mouse buttons: 0=left, 1=right, 2=middle + extras (ImGuiMouseButton_COUNT == 5). Dear ImGui mostly uses left and right buttons.
     * Others buttons allows us to track if the mouse is being used by your application + available to user as a convenience via IsMouse** API.
     */
    public void setMouseDown(final int idx, final boolean value) {
        nSetMouseDown(idx, value);
    }

    private native boolean[] nGetMouseDown(); /*
        jboolean jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDown[i];
        jbooleanArray result = env->NewBooleanArray(5);
        env->SetBooleanArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native boolean nGetMouseDown(int idx); /*
        return THIS->MouseDown[idx];
    */

    private native void nSetMouseDown(boolean[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDown[i] = value[i];
    */

    private native boolean nSetMouseDown(int idx, boolean value); /*
        THIS->MouseDown[idx] = value;
    */

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    public float getMouseWheel() {
        return nGetMouseWheel();
    }

    /**
     * Mouse wheel Vertical: 1 unit scrolls about 5 lines text.
     */
    public void setMouseWheel(final float value) {
        nSetMouseWheel(value);
    }

    private native float nGetMouseWheel(); /*
        return THIS->MouseWheel;
    */

    private native void nSetMouseWheel(float value); /*
        THIS->MouseWheel = value;
    */

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all backends.
     */
    public float getMouseWheelH() {
        return nGetMouseWheelH();
    }

    /**
     * Mouse wheel Horizontal. Most users don't have a mouse with an horizontal wheel, may not be filled by all backends.
     */
    public void setMouseWheelH(final float value) {
        nSetMouseWheelH(value);
    }

    private native float nGetMouseWheelH(); /*
        return THIS->MouseWheelH;
    */

    private native void nSetMouseWheelH(float value); /*
        THIS->MouseWheelH = value;
    */

    /**
     * (Optional) When using multiple viewports: viewport the OS mouse cursor is hovering _IGNORING_ viewports with the ImGuiViewportFlags_NoInputs flag,
     * and _REGARDLESS_ of whether another viewport is focused. Set io.BackendFlags |= ImGuiBackendFlags_HasMouseHoveredViewport if you can provide this info.
     * If you don't imgui will infer the value using the rectangles and last focused time of the viewports it knows about (ignoring other OS windows).
     */
    public int getMouseHoveredViewport() {
        return nGetMouseHoveredViewport();
    }

    /**
     * (Optional) When using multiple viewports: viewport the OS mouse cursor is hovering _IGNORING_ viewports with the ImGuiViewportFlags_NoInputs flag,
     * and _REGARDLESS_ of whether another viewport is focused. Set io.BackendFlags |= ImGuiBackendFlags_HasMouseHoveredViewport if you can provide this info.
     * If you don't imgui will infer the value using the rectangles and last focused time of the viewports it knows about (ignoring other OS windows).
     */
    public void setMouseHoveredViewport(final int value) {
        nSetMouseHoveredViewport(value);
    }

    private native int nGetMouseHoveredViewport(); /*
        return THIS->MouseHoveredViewport;
    */

    private native void nSetMouseHoveredViewport(int value); /*
        THIS->MouseHoveredViewport = value;
    */

    /**
     * Keyboard modifier pressed: Control
     */
    public boolean getKeyCtrl() {
        return nGetKeyCtrl();
    }

    /**
     * Keyboard modifier pressed: Control
     */
    public void setKeyCtrl(final boolean value) {
        nSetKeyCtrl(value);
    }

    private native boolean nGetKeyCtrl(); /*
        return THIS->KeyCtrl;
    */

    private native void nSetKeyCtrl(boolean value); /*
        THIS->KeyCtrl = value;
    */

    /**
     * Keyboard modifier pressed: Shift
     */
    public boolean getKeyShift() {
        return nGetKeyShift();
    }

    /**
     * Keyboard modifier pressed: Shift
     */
    public void setKeyShift(final boolean value) {
        nSetKeyShift(value);
    }

    private native boolean nGetKeyShift(); /*
        return THIS->KeyShift;
    */

    private native void nSetKeyShift(boolean value); /*
        THIS->KeyShift = value;
    */

    /**
     * Keyboard modifier pressed: Alt
     */
    public boolean getKeyAlt() {
        return nGetKeyAlt();
    }

    /**
     * Keyboard modifier pressed: Alt
     */
    public void setKeyAlt(final boolean value) {
        nSetKeyAlt(value);
    }

    private native boolean nGetKeyAlt(); /*
        return THIS->KeyAlt;
    */

    private native void nSetKeyAlt(boolean value); /*
        THIS->KeyAlt = value;
    */

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    public boolean getKeySuper() {
        return nGetKeySuper();
    }

    /**
     * Keyboard modifier pressed: Cmd/Super/Windows
     */
    public void setKeySuper(final boolean value) {
        nSetKeySuper(value);
    }

    private native boolean nGetKeySuper(); /*
        return THIS->KeySuper;
    */

    private native void nSetKeySuper(boolean value); /*
        THIS->KeySuper = value;
    */

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public float[] getNavInputs() {
        return nGetNavInputs();
    }

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public float getNavInputs(final int idx) {
        return nGetNavInputs(idx);
    }

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public void setNavInputs(final float[] value) {
        nSetNavInputs(value);
    }

    /**
     * Gamepad inputs. Cleared back to zero by EndFrame(). Keyboard keys will be auto-mapped and be written here by NewFrame().
     */
    public void setNavInputs(final int idx, final float value) {
        nSetNavInputs(idx, value);
    }

    private native float[] nGetNavInputs(); /*
        jfloat jBuf[512];
        for (int i = 0; i < 512; i++)
            jBuf[i] = THIS->NavInputs[i];
        jfloatArray result = env->NewFloatArray(512);
        env->SetFloatArrayRegion(result, 0, 512, jBuf);
        return result;
    */

    private native float nGetNavInputs(int idx); /*
        return THIS->NavInputs[idx];
    */

    private native void nSetNavInputs(float[] value); /*
        for (int i = 0; i < 512; i++)
            THIS->NavInputs[i] = value[i];
    */

    private native float nSetNavInputs(int idx, float value); /*
        THIS->NavInputs[idx] = value;
    */

    // Other state maintained from data above + IO function calls

    /**
     * Key mods flags (same as io.KeyCtrl/KeyShift/KeyAlt/KeySuper but merged into flags), updated by NewFrame()
     */
    public int getKeyMods() {
        return nGetKeyMods();
    }

    /**
     * Key mods flags (same as io.KeyCtrl/KeyShift/KeyAlt/KeySuper but merged into flags), updated by NewFrame()
     */
    public void setKeyMods(final int value) {
        nSetKeyMods(value);
    }

    private native int nGetKeyMods(); /*
        return THIS->KeyMods;
    */

    private native void nSetKeyMods(int value); /*
        THIS->KeyMods = value;
    */

    /**
     * Key mods flags (from previous frame)
     */
    public int getKeyModsPrev() {
        return nGetKeyModsPrev();
    }

    /**
     * Key mods flags (from previous frame)
     */
    public void setKeyModsPrev(final int value) {
        nSetKeyModsPrev(value);
    }

    private native int nGetKeyModsPrev(); /*
        return THIS->KeyModsPrev;
    */

    private native void nSetKeyModsPrev(int value); /*
        THIS->KeyModsPrev = value;
    */

    /**
     * Key state for all known keys. Use IsKeyXXX() functions to access this.
     */
    public ImGuiKeyData[] getKeysData() {
        return nGetKeysData();
    }

    /**
     * Key state for all known keys. Use IsKeyXXX() functions to access this.
     */
    public void setKeysData(final ImGuiKeyData[] value) {
        nSetKeysData(value);
    }

    private native ImGuiKeyData[] nGetKeysData(); /*
        return Jni::NewImGuiKeyDataArray(env, THIS->KeysData, ImGuiKey_KeysData_SIZE);
    */

    private native void nSetKeysData(ImGuiKeyData[] value); /*
        Jni::ImGuiKeyDataArrayCpy(env, value, THIS->KeysData, ImGuiKey_KeysData_SIZE);
    */

    /**
     * Alternative to WantCaptureMouse: (WantCaptureMouse == true {@code &&} WantCaptureMouseUnlessPopupClose == false) when a click over void is expected to close a popup.
     */
    public boolean getWantCaptureMouseUnlessPopupClose() {
        return nGetWantCaptureMouseUnlessPopupClose();
    }

    /**
     * Alternative to WantCaptureMouse: (WantCaptureMouse == true {@code &&} WantCaptureMouseUnlessPopupClose == false) when a click over void is expected to close a popup.
     */
    public void setWantCaptureMouseUnlessPopupClose(final boolean value) {
        nSetWantCaptureMouseUnlessPopupClose(value);
    }

    private native boolean nGetWantCaptureMouseUnlessPopupClose(); /*
        return THIS->WantCaptureMouseUnlessPopupClose;
    */

    private native void nSetWantCaptureMouseUnlessPopupClose(boolean value); /*
        THIS->WantCaptureMouseUnlessPopupClose = value;
    */

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    public ImVec2 getMousePosPrev() {
        final ImVec2 dst = new ImVec2();
        nGetMousePosPrev(dst);
        return dst;
    }

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    public float getMousePosPrevX() {
        return nGetMousePosPrevX();
    }

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    public float getMousePosPrevY() {
        return nGetMousePosPrevY();
    }

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    public void getMousePosPrev(final ImVec2 dst) {
        nGetMousePosPrev(dst);
    }

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    public void setMousePosPrev(final ImVec2 value) {
        nSetMousePosPrev(value.x, value.y);
    }

    /**
     * Previous mouse position (note that MouseDelta is not necessary == MousePos-MousePosPrev, in case either position is invalid)
     */
    public void setMousePosPrev(final float valueX, final float valueY) {
        nSetMousePosPrev(valueX, valueY);
    }

    private native void nGetMousePosPrev(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MousePosPrev, dst);
    */

    private native float nGetMousePosPrevX(); /*
        return THIS->MousePosPrev.x;
    */

    private native float nGetMousePosPrevY(); /*
        return THIS->MousePosPrev.y;
    */

    private native void nSetMousePosPrev(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MousePosPrev = value;
    */

    /**
     * Position at time of clicking.
     */
    public ImVec2[] getMouseClickedPos() {
        return nGetMouseClickedPos();
    }

    /**
     * Position at time of clicking.
     */
    public void setMouseClickedPos(final ImVec2[] value) {
        nSetMouseClickedPos(value);
    }

    private native ImVec2[] nGetMouseClickedPos(); /*
        return Jni::NewImVec2Array(env, THIS->MouseClickedPos, 5);
    */

    private native void nSetMouseClickedPos(ImVec2[] value); /*
        Jni::ImVec2ArrayCpy(env, value, THIS->MouseClickedPos, 5);
    */

    /**
     * Time of last click (used to figure out double-click)
     */
    public double[] getMouseClickedTime() {
        return nGetMouseClickedTime();
    }

    /**
     * Time of last click (used to figure out double-click)
     */
    public double getMouseClickedTime(final int idx) {
        return nGetMouseClickedTime(idx);
    }

    /**
     * Time of last click (used to figure out double-click)
     */
    public void setMouseClickedTime(final double[] value) {
        nSetMouseClickedTime(value);
    }

    /**
     * Time of last click (used to figure out double-click)
     */
    public void setMouseClickedTime(final int idx, final double value) {
        nSetMouseClickedTime(idx, value);
    }

    private native double[] nGetMouseClickedTime(); /*
        jdouble jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseClickedTime[i];
        jdoubleArray result = env->NewDoubleArray(5);
        env->SetDoubleArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native double nGetMouseClickedTime(int idx); /*
        return THIS->MouseClickedTime[idx];
    */

    private native void nSetMouseClickedTime(double[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseClickedTime[i] = value[i];
    */

    private native double nSetMouseClickedTime(int idx, double value); /*
        THIS->MouseClickedTime[idx] = value;
    */

    /**
     * Mouse button went from !Down to Down (same as MouseClickedCount[x] != 0)
     */
    public boolean[] getMouseClicked() {
        return nGetMouseClicked();
    }

    /**
     * Mouse button went from !Down to Down (same as MouseClickedCount[x] != 0)
     */
    public boolean getMouseClicked(final int idx) {
        return nGetMouseClicked(idx);
    }

    /**
     * Mouse button went from !Down to Down (same as MouseClickedCount[x] != 0)
     */
    public void setMouseClicked(final boolean[] value) {
        nSetMouseClicked(value);
    }

    /**
     * Mouse button went from !Down to Down (same as MouseClickedCount[x] != 0)
     */
    public void setMouseClicked(final int idx, final boolean value) {
        nSetMouseClicked(idx, value);
    }

    private native boolean[] nGetMouseClicked(); /*
        jboolean jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseClicked[i];
        jbooleanArray result = env->NewBooleanArray(5);
        env->SetBooleanArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native boolean nGetMouseClicked(int idx); /*
        return THIS->MouseClicked[idx];
    */

    private native void nSetMouseClicked(boolean[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseClicked[i] = value[i];
    */

    private native boolean nSetMouseClicked(int idx, boolean value); /*
        THIS->MouseClicked[idx] = value;
    */

    /**
     * Has mouse button been double-clicked? (same as MouseClickedCount[x] == 2)
     */
    public boolean[] getMouseDoubleClicked() {
        return nGetMouseDoubleClicked();
    }

    /**
     * Has mouse button been double-clicked? (same as MouseClickedCount[x] == 2)
     */
    public boolean getMouseDoubleClicked(final int idx) {
        return nGetMouseDoubleClicked(idx);
    }

    /**
     * Has mouse button been double-clicked? (same as MouseClickedCount[x] == 2)
     */
    public void setMouseDoubleClicked(final boolean[] value) {
        nSetMouseDoubleClicked(value);
    }

    /**
     * Has mouse button been double-clicked? (same as MouseClickedCount[x] == 2)
     */
    public void setMouseDoubleClicked(final int idx, final boolean value) {
        nSetMouseDoubleClicked(idx, value);
    }

    private native boolean[] nGetMouseDoubleClicked(); /*
        jboolean jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDoubleClicked[i];
        jbooleanArray result = env->NewBooleanArray(5);
        env->SetBooleanArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native boolean nGetMouseDoubleClicked(int idx); /*
        return THIS->MouseDoubleClicked[idx];
    */

    private native void nSetMouseDoubleClicked(boolean[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDoubleClicked[i] = value[i];
    */

    private native boolean nSetMouseDoubleClicked(int idx, boolean value); /*
        THIS->MouseDoubleClicked[idx] = value;
    */

    /**
     * == 0 (not clicked), == 1 (same as MouseClicked[]), == 2 (double-clicked), == 3 (triple-clicked) etc. when going from !Down to Down
     */
    public int[] getMouseClickedCount() {
        return nGetMouseClickedCount();
    }

    /**
     * == 0 (not clicked), == 1 (same as MouseClicked[]), == 2 (double-clicked), == 3 (triple-clicked) etc. when going from !Down to Down
     */
    public int getMouseClickedCount(final int idx) {
        return nGetMouseClickedCount(idx);
    }

    /**
     * == 0 (not clicked), == 1 (same as MouseClicked[]), == 2 (double-clicked), == 3 (triple-clicked) etc. when going from !Down to Down
     */
    public void setMouseClickedCount(final int[] value) {
        nSetMouseClickedCount(value);
    }

    /**
     * == 0 (not clicked), == 1 (same as MouseClicked[]), == 2 (double-clicked), == 3 (triple-clicked) etc. when going from !Down to Down
     */
    public void setMouseClickedCount(final int idx, final int value) {
        nSetMouseClickedCount(idx, value);
    }

    private native int[] nGetMouseClickedCount(); /*
        jint jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseClickedCount[i];
        jintArray result = env->NewIntArray(5);
        env->SetIntArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native int nGetMouseClickedCount(int idx); /*
        return THIS->MouseClickedCount[idx];
    */

    private native void nSetMouseClickedCount(int[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseClickedCount[i] = value[i];
    */

    private native int nSetMouseClickedCount(int idx, int value); /*
        THIS->MouseClickedCount[idx] = value;
    */

    /**
     * Count successive number of clicks. Stays valid after mouse release. Reset after another click is done.
     */
    public int[] getMouseClickedLastCount() {
        return nGetMouseClickedLastCount();
    }

    /**
     * Count successive number of clicks. Stays valid after mouse release. Reset after another click is done.
     */
    public int getMouseClickedLastCount(final int idx) {
        return nGetMouseClickedLastCount(idx);
    }

    /**
     * Count successive number of clicks. Stays valid after mouse release. Reset after another click is done.
     */
    public void setMouseClickedLastCount(final int[] value) {
        nSetMouseClickedLastCount(value);
    }

    /**
     * Count successive number of clicks. Stays valid after mouse release. Reset after another click is done.
     */
    public void setMouseClickedLastCount(final int idx, final int value) {
        nSetMouseClickedLastCount(idx, value);
    }

    private native int[] nGetMouseClickedLastCount(); /*
        jint jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseClickedLastCount[i];
        jintArray result = env->NewIntArray(5);
        env->SetIntArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native int nGetMouseClickedLastCount(int idx); /*
        return THIS->MouseClickedLastCount[idx];
    */

    private native void nSetMouseClickedLastCount(int[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseClickedLastCount[i] = value[i];
    */

    private native int nSetMouseClickedLastCount(int idx, int value); /*
        THIS->MouseClickedLastCount[idx] = value;
    */

    /**
     * Mouse button went from Down to !Down
     */
    public boolean[] getMouseReleased() {
        return nGetMouseReleased();
    }

    /**
     * Mouse button went from Down to !Down
     */
    public boolean getMouseReleased(final int idx) {
        return nGetMouseReleased(idx);
    }

    /**
     * Mouse button went from Down to !Down
     */
    public void setMouseReleased(final boolean[] value) {
        nSetMouseReleased(value);
    }

    /**
     * Mouse button went from Down to !Down
     */
    public void setMouseReleased(final int idx, final boolean value) {
        nSetMouseReleased(idx, value);
    }

    private native boolean[] nGetMouseReleased(); /*
        jboolean jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseReleased[i];
        jbooleanArray result = env->NewBooleanArray(5);
        env->SetBooleanArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native boolean nGetMouseReleased(int idx); /*
        return THIS->MouseReleased[idx];
    */

    private native void nSetMouseReleased(boolean[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseReleased[i] = value[i];
    */

    private native boolean nSetMouseReleased(int idx, boolean value); /*
        THIS->MouseReleased[idx] = value;
    */

    /**
     * Track if button was clicked inside a dear imgui window or over void blocked by a popup. We don't request mouse capture from the application if click started outside ImGui bounds.
     */
    public boolean[] getMouseDownOwned() {
        return nGetMouseDownOwned();
    }

    /**
     * Track if button was clicked inside a dear imgui window or over void blocked by a popup. We don't request mouse capture from the application if click started outside ImGui bounds.
     */
    public boolean getMouseDownOwned(final int idx) {
        return nGetMouseDownOwned(idx);
    }

    /**
     * Track if button was clicked inside a dear imgui window or over void blocked by a popup. We don't request mouse capture from the application if click started outside ImGui bounds.
     */
    public void setMouseDownOwned(final boolean[] value) {
        nSetMouseDownOwned(value);
    }

    /**
     * Track if button was clicked inside a dear imgui window or over void blocked by a popup. We don't request mouse capture from the application if click started outside ImGui bounds.
     */
    public void setMouseDownOwned(final int idx, final boolean value) {
        nSetMouseDownOwned(idx, value);
    }

    private native boolean[] nGetMouseDownOwned(); /*
        jboolean jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDownOwned[i];
        jbooleanArray result = env->NewBooleanArray(5);
        env->SetBooleanArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native boolean nGetMouseDownOwned(int idx); /*
        return THIS->MouseDownOwned[idx];
    */

    private native void nSetMouseDownOwned(boolean[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDownOwned[i] = value[i];
    */

    private native boolean nSetMouseDownOwned(int idx, boolean value); /*
        THIS->MouseDownOwned[idx] = value;
    */

    /**
     * Track if button was clicked inside a dear imgui window.
     */
    public boolean[] getMouseDownOwnedUnlessPopupClose() {
        return nGetMouseDownOwnedUnlessPopupClose();
    }

    /**
     * Track if button was clicked inside a dear imgui window.
     */
    public boolean getMouseDownOwnedUnlessPopupClose(final int idx) {
        return nGetMouseDownOwnedUnlessPopupClose(idx);
    }

    /**
     * Track if button was clicked inside a dear imgui window.
     */
    public void setMouseDownOwnedUnlessPopupClose(final boolean[] value) {
        nSetMouseDownOwnedUnlessPopupClose(value);
    }

    /**
     * Track if button was clicked inside a dear imgui window.
     */
    public void setMouseDownOwnedUnlessPopupClose(final int idx, final boolean value) {
        nSetMouseDownOwnedUnlessPopupClose(idx, value);
    }

    private native boolean[] nGetMouseDownOwnedUnlessPopupClose(); /*
        jboolean jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDownOwnedUnlessPopupClose[i];
        jbooleanArray result = env->NewBooleanArray(5);
        env->SetBooleanArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native boolean nGetMouseDownOwnedUnlessPopupClose(int idx); /*
        return THIS->MouseDownOwnedUnlessPopupClose[idx];
    */

    private native void nSetMouseDownOwnedUnlessPopupClose(boolean[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDownOwnedUnlessPopupClose[i] = value[i];
    */

    private native boolean nSetMouseDownOwnedUnlessPopupClose(int idx, boolean value); /*
        THIS->MouseDownOwnedUnlessPopupClose[idx] = value;
    */

    /**
     * Duration the mouse button has been down (0.0f == just clicked)
     */
    public float[] getMouseDownDuration() {
        return nGetMouseDownDuration();
    }

    /**
     * Duration the mouse button has been down (0.0f == just clicked)
     */
    public float getMouseDownDuration(final int idx) {
        return nGetMouseDownDuration(idx);
    }

    /**
     * Duration the mouse button has been down (0.0f == just clicked)
     */
    public void setMouseDownDuration(final float[] value) {
        nSetMouseDownDuration(value);
    }

    /**
     * Duration the mouse button has been down (0.0f == just clicked)
     */
    public void setMouseDownDuration(final int idx, final float value) {
        nSetMouseDownDuration(idx, value);
    }

    private native float[] nGetMouseDownDuration(); /*
        jfloat jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDownDuration[i];
        jfloatArray result = env->NewFloatArray(5);
        env->SetFloatArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native float nGetMouseDownDuration(int idx); /*
        return THIS->MouseDownDuration[idx];
    */

    private native void nSetMouseDownDuration(float[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDownDuration[i] = value[i];
    */

    private native float nSetMouseDownDuration(int idx, float value); /*
        THIS->MouseDownDuration[idx] = value;
    */

    /**
     * Previous time the mouse button has been down
     */
    public float[] getMouseDownDurationPrev() {
        return nGetMouseDownDurationPrev();
    }

    /**
     * Previous time the mouse button has been down
     */
    public float getMouseDownDurationPrev(final int idx) {
        return nGetMouseDownDurationPrev(idx);
    }

    /**
     * Previous time the mouse button has been down
     */
    public void setMouseDownDurationPrev(final float[] value) {
        nSetMouseDownDurationPrev(value);
    }

    /**
     * Previous time the mouse button has been down
     */
    public void setMouseDownDurationPrev(final int idx, final float value) {
        nSetMouseDownDurationPrev(idx, value);
    }

    private native float[] nGetMouseDownDurationPrev(); /*
        jfloat jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDownDurationPrev[i];
        jfloatArray result = env->NewFloatArray(5);
        env->SetFloatArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native float nGetMouseDownDurationPrev(int idx); /*
        return THIS->MouseDownDurationPrev[idx];
    */

    private native void nSetMouseDownDurationPrev(float[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDownDurationPrev[i] = value[i];
    */

    private native float nSetMouseDownDurationPrev(int idx, float value); /*
        THIS->MouseDownDurationPrev[idx] = value;
    */

    /**
     * Maximum distance, absolute, on each axis, of how much mouse has traveled from the clicking point
     */
    public ImVec2[] getMouseDragMaxDistanceAbs() {
        return nGetMouseDragMaxDistanceAbs();
    }

    /**
     * Maximum distance, absolute, on each axis, of how much mouse has traveled from the clicking point
     */
    public void setMouseDragMaxDistanceAbs(final ImVec2[] value) {
        nSetMouseDragMaxDistanceAbs(value);
    }

    private native ImVec2[] nGetMouseDragMaxDistanceAbs(); /*
        return Jni::NewImVec2Array(env, THIS->MouseDragMaxDistanceAbs, 5);
    */

    private native void nSetMouseDragMaxDistanceAbs(ImVec2[] value); /*
        Jni::ImVec2ArrayCpy(env, value, THIS->MouseDragMaxDistanceAbs, 5);
    */

    /**
     * Squared maximum distance of how much mouse has traveled from the clicking point
     */
    public float[] getMouseDragMaxDistanceSqr() {
        return nGetMouseDragMaxDistanceSqr();
    }

    /**
     * Squared maximum distance of how much mouse has traveled from the clicking point
     */
    public float getMouseDragMaxDistanceSqr(final int idx) {
        return nGetMouseDragMaxDistanceSqr(idx);
    }

    /**
     * Squared maximum distance of how much mouse has traveled from the clicking point
     */
    public void setMouseDragMaxDistanceSqr(final float[] value) {
        nSetMouseDragMaxDistanceSqr(value);
    }

    /**
     * Squared maximum distance of how much mouse has traveled from the clicking point
     */
    public void setMouseDragMaxDistanceSqr(final int idx, final float value) {
        nSetMouseDragMaxDistanceSqr(idx, value);
    }

    private native float[] nGetMouseDragMaxDistanceSqr(); /*
        jfloat jBuf[5];
        for (int i = 0; i < 5; i++)
            jBuf[i] = THIS->MouseDragMaxDistanceSqr[i];
        jfloatArray result = env->NewFloatArray(5);
        env->SetFloatArrayRegion(result, 0, 5, jBuf);
        return result;
    */

    private native float nGetMouseDragMaxDistanceSqr(int idx); /*
        return THIS->MouseDragMaxDistanceSqr[idx];
    */

    private native void nSetMouseDragMaxDistanceSqr(float[] value); /*
        for (int i = 0; i < 5; i++)
            THIS->MouseDragMaxDistanceSqr[i] = value[i];
    */

    private native float nSetMouseDragMaxDistanceSqr(int idx, float value); /*
        THIS->MouseDragMaxDistanceSqr[idx] = value;
    */

    public float[] getNavInputsDownDuration() {
        return nGetNavInputsDownDuration();
    }

    public float getNavInputsDownDuration(final int idx) {
        return nGetNavInputsDownDuration(idx);
    }

    public void setNavInputsDownDuration(final float[] value) {
        nSetNavInputsDownDuration(value);
    }

    public void setNavInputsDownDuration(final int idx, final float value) {
        nSetNavInputsDownDuration(idx, value);
    }

    private native float[] nGetNavInputsDownDuration(); /*
        jfloat jBuf[ImGuiNavInput_COUNT];
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            jBuf[i] = THIS->NavInputsDownDuration[i];
        jfloatArray result = env->NewFloatArray(ImGuiNavInput_COUNT);
        env->SetFloatArrayRegion(result, 0, ImGuiNavInput_COUNT, jBuf);
        return result;
    */

    private native float nGetNavInputsDownDuration(int idx); /*
        return THIS->NavInputsDownDuration[idx];
    */

    private native void nSetNavInputsDownDuration(float[] value); /*
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            THIS->NavInputsDownDuration[i] = value[i];
    */

    private native float nSetNavInputsDownDuration(int idx, float value); /*
        THIS->NavInputsDownDuration[idx] = value;
    */

    public float[] getNavInputsDownDurationPrev() {
        return nGetNavInputsDownDurationPrev();
    }

    public float getNavInputsDownDurationPrev(final int idx) {
        return nGetNavInputsDownDurationPrev(idx);
    }

    public void setNavInputsDownDurationPrev(final float[] value) {
        nSetNavInputsDownDurationPrev(value);
    }

    public void setNavInputsDownDurationPrev(final int idx, final float value) {
        nSetNavInputsDownDurationPrev(idx, value);
    }

    private native float[] nGetNavInputsDownDurationPrev(); /*
        jfloat jBuf[ImGuiNavInput_COUNT];
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            jBuf[i] = THIS->NavInputsDownDurationPrev[i];
        jfloatArray result = env->NewFloatArray(ImGuiNavInput_COUNT);
        env->SetFloatArrayRegion(result, 0, ImGuiNavInput_COUNT, jBuf);
        return result;
    */

    private native float nGetNavInputsDownDurationPrev(int idx); /*
        return THIS->NavInputsDownDurationPrev[idx];
    */

    private native void nSetNavInputsDownDurationPrev(float[] value); /*
        for (int i = 0; i < ImGuiNavInput_COUNT; i++)
            THIS->NavInputsDownDurationPrev[i] = value[i];
    */

    private native float nSetNavInputsDownDurationPrev(int idx, float value); /*
        THIS->NavInputsDownDurationPrev[idx] = value;
    */

    /**
     * Touch/Pen pressure (0.0f to 1.0f, should be {@code >}0.0f only when MouseDown[0] == true). Helper storage currently unused by Dear ImGui.
     */
    public float getPenPressure() {
        return nGetPenPressure();
    }

    /**
     * Touch/Pen pressure (0.0f to 1.0f, should be {@code >}0.0f only when MouseDown[0] == true). Helper storage currently unused by Dear ImGui.
     */
    public void setPenPressure(final float value) {
        nSetPenPressure(value);
    }

    private native float nGetPenPressure(); /*
        return THIS->PenPressure;
    */

    private native void nSetPenPressure(float value); /*
        THIS->PenPressure = value;
    */

    public boolean getAppFocusLost() {
        return nGetAppFocusLost();
    }

    public void setAppFocusLost(final boolean value) {
        nSetAppFocusLost(value);
    }

    private native boolean nGetAppFocusLost(); /*
        return THIS->AppFocusLost;
    */

    private native void nSetAppFocusLost(boolean value); /*
        THIS->AppFocusLost = value;
    */

    /**
     * -1: unknown, 0: using AddKeyEvent(), 1: using legacy io.KeysDown[]
     */
    public short getBackendUsingLegacyKeyArrays() {
        return nGetBackendUsingLegacyKeyArrays();
    }

    /**
     * -1: unknown, 0: using AddKeyEvent(), 1: using legacy io.KeysDown[]
     */
    public void setBackendUsingLegacyKeyArrays(final short value) {
        nSetBackendUsingLegacyKeyArrays(value);
    }

    private native short nGetBackendUsingLegacyKeyArrays(); /*
        return THIS->BackendUsingLegacyKeyArrays;
    */

    private native void nSetBackendUsingLegacyKeyArrays(short value); /*
        THIS->BackendUsingLegacyKeyArrays = value;
    */

    /**
     * 0: using AddKeyAnalogEvent(), 1: writing to legacy io.NavInputs[] directly
     */
    public boolean getBackendUsingLegacyNavInputArray() {
        return nGetBackendUsingLegacyNavInputArray();
    }

    /**
     * 0: using AddKeyAnalogEvent(), 1: writing to legacy io.NavInputs[] directly
     */
    public void setBackendUsingLegacyNavInputArray(final boolean value) {
        nSetBackendUsingLegacyNavInputArray(value);
    }

    private native boolean nGetBackendUsingLegacyNavInputArray(); /*
        return THIS->BackendUsingLegacyNavInputArray;
    */

    private native void nSetBackendUsingLegacyNavInputArray(boolean value); /*
        THIS->BackendUsingLegacyNavInputArray = value;
    */

    /**
     * For AddInputCharacterUTF16
     */
    public short getInputQueueSurrogate() {
        return nGetInputQueueSurrogate();
    }

    /**
     * For AddInputCharacterUTF16
     */
    public void setInputQueueSurrogate(final short value) {
        nSetInputQueueSurrogate(value);
    }

    private native short nGetInputQueueSurrogate(); /*
        return THIS->InputQueueSurrogate;
    */

    private native void nSetInputQueueSurrogate(short value); /*
        THIS->InputQueueSurrogate = value;
    */

    // TODO: InputQueueCharacters

    /*JNI
        #undef THIS
     */
}
