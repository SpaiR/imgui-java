package io.github.spair.imgui.flag;

/**
 * Flags for ImGui::IsWindowFocused()
 */
public final class ImGuiFocusedFlags {
    private ImGuiFocusedFlags() {
    }

    public static final int None = 0;
    /**
     * IsWindowFocused(): Return true if any children of the window is focused
     */
    public static final int ChildWindows = 1;
    /**
     * IsWindowFocused(): Test from root window (top most parent of the current hierarchy)
     */
    public static final int RootWindow = 1 << 1;
    /**
     * IsWindowFocused(): Return true if any window is focused.
     * Important: If you are trying to tell how to dispatch your low-level inputs, do NOT use this. Use 'io.WantCaptureMouse' instead! Please read the FAQ!
     */
    public static final int AnyWindow = 1 << 2;
    public static final int RootAndChildWindows = RootWindow | ChildWindows;
}
