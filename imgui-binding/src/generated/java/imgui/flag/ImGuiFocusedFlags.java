package imgui.flag;


/**
 * Flags for ImGui::IsWindowFocused()
 */
public final class ImGuiFocusedFlags {
    private ImGuiFocusedFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Return true if any children of the window is focused
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int ChildWindows = 1;

    /**
     * Test from root window (top most parent of the current hierarchy)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int RootWindow = 2;

    /**
     * Return true if any window is focused. Important: If you are trying to tell how to dispatch your low-level inputs, do NOT use this. Use 'io.WantCaptureMouse' instead! Please read the FAQ!
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AnyWindow = 4;

    /**
     * Do not consider popup hierarchy (do not treat popup emitter as parent of popup) (when used with _ChildWindows or _RootWindow)
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoPopupHierarchy = 8;

    /**
     * Consider docking hierarchy (treat dockspace host as parent of docked window) (when used with _ChildWindows or _RootWindow)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int DockHierarchy = 16;

    /**
     * Definition: {@code ImGuiFocusedFlags_RootWindow | ImGuiFocusedFlags_ChildWindows}
     */
    public static final int RootAndChildWindows = 3;
}
