package imgui.flag;

/**
 * Flags for ImGui::IsWindowFocused()
 */
public final class ImGuiFocusedFlags {
    private ImGuiFocusedFlags() {
    }

    public static final int None = 0;

    /**
     * Return true if any children of the window is focused
     */
    public static final int ChildWindows = 1;

    /**
     * Test from root window (top most parent of the current hierarchy)
     */
    public static final int RootWindow = 1 << 1;

    /**
     * Return true if any window is focused. Important: If you are trying to tell how to dispatch your low-level inputs, do NOT use this.
     * Use 'io.WantCaptureMouse' instead! Please read the FAQ!
     */
    public static final int AnyWindow = 1 << 2;

    /**
     * Do not consider popup hierarchy (do not treat popup emitter as parent of popup) (when used with _ChildWindows or _RootWindow)
     */
    public static final int NoPopupHierarchy = 1 << 3;

    /**
     * Consider docking hierarchy (treat dockspace host as parent of docked window) (when used with _ChildWindows or _RootWindow)
     */
    public static final int DockHierarchy = 1 << 4;

    public static final int RootAndChildWindows = RootWindow | ChildWindows;
}
