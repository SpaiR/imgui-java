package imgui.flag;

/**
 * Flags for ImGui::DockSpace(), shared/inherited by child nodes.
 * (Some flags can be applied to individual nodes directly)
 */
public final class ImGuiDockNodeFlags {
    private ImGuiDockNodeFlags() {
    }

    public static final int None = 0;
    /**
     * Don't display the dockspace node but keep it alive. Windows docked into this dockspace node won't be undocked.
     */
    public static final int KeepAliveOnly = 1;
    /**
     * Disable Central Node (the node which can stay empty)
     */
    public static final int NoCentralNode = 1 << 1;
    /**
     * Disable docking inside the Central Node, which will be always kept empty.
     */
    public static final int NoDockingInCentralNode = 1 << 2;
    /**
     * Enable passthru dockspace: 1) DockSpace() will render a ImGuiCol_WindowBg background covering everything excepted the Central
     * Node when empty. Meaning the host window should probably use SetNextWindowBgAlpha(0.0f) prior to Begin() when using this. 2)
     * When Central Node is empty: let inputs pass-through + won't display a DockingEmptyBg background. See demo for details.
     */
    public static final int PassthruCentralNode = 1 << 3;
    /**
     * Disable splitting the node into smaller nodes.
     * Useful e.g. when embedding dockspaces into a main root one (the root one may have splitting disabled to reduce confusion).
     * Note: when turned off, existing splits will be preserved.
     */
    public static final int NoSplit = 1 << 4;
    /**
     * Disable resizing node using the splitter/separators. Useful with programatically setup dockspaces.
     */
    public static final int NoResize = 1 << 5;
    /**
     * Tab bar will automatically hide when there is a single window in the dock node.
     */
    public static final int AutoHideTabBar = 1 << 6;
}
