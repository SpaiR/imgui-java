package imgui.flag;


/**
 * Flags for ImGui::DockSpace(), shared/inherited by child nodes.
 * (Some flags can be applied to individual nodes directly)
 */
public final class ImGuiDockNodeFlags {
    private ImGuiDockNodeFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Don't display the dockspace node but keep it alive. Windows docked into this dockspace node won't be undocked.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int KeepAliveOnly = 1;

    /**
     * Disable docking over the Central Node, which will be always kept empty.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoDockingOverCentralNode = 4;

    /**
     * Enable passthru dockspace: 1) DockSpace() will render a ImGuiCol_WindowBg background covering everything excepted the Central Node when empty. Meaning the host window should probably use SetNextWindowBgAlpha(0.0f) prior to Begin() when using this. 2) When Central Node is empty: let inputs pass-through + won't display a DockingEmptyBg background. See demo for details.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int PassthruCentralNode = 8;

    /**
     * Disable other windows/nodes from splitting this node.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoDockingSplit = 16;

    /**
     * Saved // Disable resizing node using the splitter/separators. Useful with programmatically setup dockspaces.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoResize = 32;

    /**
     * Tab bar will automatically hide when there is a single window in the dock node.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int AutoHideTabBar = 64;

    /**
     * Disable undocking this node.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoUndocking = 128;

    /**
     * Renamed in 1.90
     *
     * <p>Definition: {@code ImGuiDockNodeFlags_NoDockingSplit}
     */
    public static final int NoSplit = 16;

    /**
     * Renamed in 1.90
     *
     * <p>Definition: {@code ImGuiDockNodeFlags_NoDockingOverCentralNode}
     */
    public static final int NoDockingInCentralNode = 4;
}
