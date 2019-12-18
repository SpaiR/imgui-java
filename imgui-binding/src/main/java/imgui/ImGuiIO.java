package imgui;

import imgui.enums.ImGuiConfigFlags;

public class ImGuiIO {

    //------------------------------------------------------------------
    // Output - Retrieve after calling NewFrame()
    //------------------------------------------------------------------

    public boolean WantCaptureMouse;        // When io.WantCaptureMouse is true, imgui will use the mouse inputs, do not dispatch them to your main game/application (in both cases, always pass on mouse inputs to imgui). (e.g. unclicked mouse is hovering over an imgui window, widget is active, mouse was clicked over an imgui window, etc.).
    public boolean WantCaptureKeyboard;    // When io.WantCaptureKeyboard is true, imgui will use the keyboard inputs, do not dispatch them to your main game/application (in both cases, always pass keyboard inputs to imgui). (e.g. InputText active, or an imgui window is focused and navigation is enabled, etc.).
    public boolean WantTextInput;            // Mobile/console: when io.WantTextInput is true, you may display an on-screen keyboard. This is set by ImGui when it wants textual keyboard input to happen (e.g. when a InputText widget is active).
    public boolean WantSetMousePos;        // MousePos has been altered, back-end should reposition mouse on next frame. Set only when ImGuiConfigFlags_NavEnableSetMousePos flag is enabled.
    public boolean WantSaveIniSettings;    // When manual .ini load/save is active (io.IniFilename == NULL), this will be set to notify your application that you can call SaveIniSettingsToMemory() and save yourself. IMPORTANT: You need to clear io.WantSaveIniSettings yourself.
    public boolean NavActive;                // Directional navigation is currently allowed (will handle ImGuiKey_NavXXX events) = a window is focused and it doesn't use the ImGuiWindowFlags_NoNavInputs flag.
    public boolean NavVisible;                // Directional navigation is visible and allowed (will handle ImGuiKey_NavXXX events).
    public float Framerate;                // Application framerate estimation, in frame per second. Solely for convenience. Rolling average estimation based on IO.DeltaTime over 120 frames
    public int MetricsRenderVertices;        // Vertices output during last call to Render()
    public int MetricsRenderIndices;        // Indices output during last call to Render() = number of triangles * 3
    public int MetricsRenderWindows;        // Number of visible windows
    public int MetricsActiveWindows;        // Number of active windows
    public int MetricsActiveAllocations;    // Number of active allocations, updated by MemAlloc/MemFree based on current context. May be off if you have multiple imgui contexts.
    public float MouseDeltaX; // Mouse delta. Note that this is zero if either current or previous position are invalid (-FLT_MAX,-FLT_MAX), so a disappearing/reappearing mouse won't have a huge delta.
    public float MouseDeltaY;

    public void SetConfigFlags(ImGuiConfigFlags flags) {
        Native.SetConfigFlags(flags.getValue());
    }

    // TODO: Available after docking comes from BETA
//    public void SetDockingFlags(boolean ConfigDockingNoSplit, boolean ConfigDockingWithShift, boolean ConfigDockingAlwaysTabBar, boolean ConfigDockingTransparentPayload) {
//        Native.SetDockingFlags(ConfigDockingNoSplit, ConfigDockingWithShift, ConfigDockingAlwaysTabBar, ConfigDockingTransparentPayload);
//    }
}
