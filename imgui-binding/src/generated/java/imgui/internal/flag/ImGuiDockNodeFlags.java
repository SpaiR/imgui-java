package imgui.internal.flag;


/**
 * Extend ImGuiDockNodeFlags
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

    /**
     * Saved // A dockspace is a node that occupy space within an existing user window. Otherwise the node is floating and create its own window.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int DockSpace = 1024;

    /**
     * Saved // The central node has 2 main properties: stay visible when empty, only use "remaining" spaces from its neighbor.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int CentralNode = 2048;

    /**
     * Saved // Tab bar is completely unavailable. No triangle in the corner to enable it back.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int NoTabBar = 4096;

    /**
     * Saved // Tab bar is hidden, with a triangle in the corner to show it again (NB: actual tab-bar instance may be destroyed as this is only used for single-window tab bar)
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int HiddenTabBar = 8192;

    /**
     * Saved // Disable window/docking menu (that one that appears instead of the collapse button)
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int NoWindowMenuButton = 16384;

    /**
     * Saved // Disable close button
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int NoCloseButton = 32768;

    /**
     * <p>Definition: {@code 1 << 16}
     */
    public static final int NoResizeX = 65536;

    /**
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NoResizeY = 131072;

    /**
     * Any docked window will be automatically be focus-route chained (window{@code ->}ParentWindowForFocusRoute set to this) so Shortcut() in this window can run when any docked window is focused.
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int DockedWindowsInFocusRoute = 262144;

    /**
     * Disable this node from splitting other windows/nodes.
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int NoDockingSplitOther = 524288;

    /**
     * Disable other windows/nodes from being docked over this node.
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int NoDockingOverMe = 1048576;

    /**
     * Disable this node from being docked over another window or non-empty node.
     *
     * <p>Definition: {@code 1 << 21}
     */
    public static final int NoDockingOverOther = 2097152;

    /**
     * Disable this node from being docked over an empty node (e.g. DockSpace with no other windows)
     *
     * <p>Definition: {@code 1 << 22}
     */
    public static final int NoDockingOverEmpty = 4194304;

    /**
     * Definition: {@code ImGuiDockNodeFlags_NoDockingOverMe | ImGuiDockNodeFlags_NoDockingOverOther | ImGuiDockNodeFlags_NoDockingOverEmpty | ImGuiDockNodeFlags_NoDockingSplit | ImGuiDockNodeFlags_NoDockingSplitOther}
     */
    public static final int NoDocking = 7864336;

    /**
     * Masks
     *
     * <p>Definition: {@code ~0}
     */
    public static final int SharedFlagsInheritMask_ = -1;

    /**
     * Masks
     *
     * <p>Definition: {@code ImGuiDockNodeFlags_NoResize | ImGuiDockNodeFlags_NoResizeX | ImGuiDockNodeFlags_NoResizeY}
     */
    public static final int NoResizeFlagsMask_ = 196640;

    /**
     * When splitting, those local flags are moved to the inheriting child, never duplicated
     *
     * <p>Definition: {@code ImGuiDockNodeFlags_NoDockingSplit | ImGuiDockNodeFlags_NoResizeFlagsMask_ | ImGuiDockNodeFlags_AutoHideTabBar | ImGuiDockNodeFlags_CentralNode | ImGuiDockNodeFlags_NoTabBar | ImGuiDockNodeFlags_HiddenTabBar | ImGuiDockNodeFlags_NoWindowMenuButton | ImGuiDockNodeFlags_NoCloseButton}
     */
    public static final int LocalFlagsTransferMask_ = 260208;

    /**
     * When splitting, those local flags are moved to the inheriting child, never duplicated
     *
     * <p>Definition: {@code ImGuiDockNodeFlags_NoResizeFlagsMask_ | ImGuiDockNodeFlags_DockSpace | ImGuiDockNodeFlags_CentralNode | ImGuiDockNodeFlags_NoTabBar | ImGuiDockNodeFlags_HiddenTabBar | ImGuiDockNodeFlags_NoWindowMenuButton | ImGuiDockNodeFlags_NoCloseButton}
     */
    public static final int SavedFlagsMask_ = 261152;
}
