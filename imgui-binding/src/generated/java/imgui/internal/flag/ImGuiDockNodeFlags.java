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
     * Shared       // Don't display the dockspace node but keep it alive. Windows docked into this dockspace node won't be undocked.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int KeepAliveOnly = 1;

    /**
     * Shared       // Disable docking inside the Central Node, which will be always kept empty.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoDockingInCentralNode = 4;

    /**
     * Shared       // Enable passthru dockspace: 1) DockSpace() will render a ImGuiCol_WindowBg background covering everything excepted the Central Node when empty. Meaning the host window should probably use SetNextWindowBgAlpha(0.0f) prior to Begin() when using this. 2) When Central Node is empty: let inputs pass-through + won't display a DockingEmptyBg background. See demo for details.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int PassthruCentralNode = 8;

    /**
     * Shared/Local // Disable splitting the node into smaller nodes. Useful e.g. when embedding dockspaces into a main root one (the root one may have splitting disabled to reduce confusion). Note: when turned off, existing splits will be preserved.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoSplit = 16;

    /**
     * Shared/Local // Disable resizing node using the splitter/separators. Useful with programmatically setup dockspaces.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoResize = 32;

    /**
     * Shared/Local // Tab bar will automatically hide when there is a single window in the dock node.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int AutoHideTabBar = 64;

    /**
     * Local, Saved  // A dockspace is a node that occupy space within an existing user window. Otherwise the node is floating and create its own window.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int DockSpace = 1024;

    /**
     * Local, Saved  // The central node has 2 main properties: stay visible when empty, only use "remaining" spaces from its neighbor.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int CentralNode = 2048;

    /**
     * Local, Saved  // Tab bar is completely unavailable. No triangle in the corner to enable it back.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int NoTabBar = 4096;

    /**
     * Local, Saved  // Tab bar is hidden, with a triangle in the corner to show it again (NB: actual tab-bar instance may be destroyed as this is only used for single-window tab bar)
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int HiddenTabBar = 8192;

    /**
     * Local, Saved  // Disable window/docking menu (that one that appears instead of the collapse button)
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int NoWindowMenuButton = 16384;

    /**
     * Local, Saved  //
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int NoCloseButton = 32768;

    /**
     * Local, Saved  // Disable any form of docking in this dockspace or individual node. (On a whole dockspace, this pretty much defeat the purpose of using a dockspace at all). Note: when turned on, existing docked nodes will be preserved.
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int NoDocking = 65536;

    /**
     * [EXPERIMENTAL] Prevent another window/node from splitting this node.
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NoDockingSplitMe = 131072;

    /**
     * [EXPERIMENTAL] Prevent this node from splitting another window/node.
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int NoDockingSplitOther = 262144;

    /**
     * [EXPERIMENTAL] Prevent another window/node to be docked over this node.
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int NoDockingOverMe = 524288;

    /**
     * [EXPERIMENTAL] Prevent this node to be docked over another window or non-empty node.
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int NoDockingOverOther = 1048576;

    /**
     * [EXPERIMENTAL] Prevent this node to be docked over an empty node (e.g. DockSpace with no other windows)
     *
     * <p>Definition: {@code 1 << 21}
     */
    public static final int NoDockingOverEmpty = 2097152;

    /**
     * [EXPERIMENTAL]
     *
     * <p>Definition: {@code 1 << 22}
     */
    public static final int NoResizeX = 4194304;

    /**
     * [EXPERIMENTAL]
     *
     * <p>Definition: {@code 1 << 23}
     */
    public static final int NoResizeY = 8388608;

    /**
     * Definition: {@code ~0}
     */
    public static final int SharedFlagsInheritMask_ = -1;

    /**
     * Definition: {@code ImGuiDockNodeFlags_NoResize | ImGuiDockNodeFlags_NoResizeX | ImGuiDockNodeFlags_NoResizeY}
     */
    public static final int NoResizeFlagsMask_ = 12582944;

    /**
     * Definition: {@code ImGuiDockNodeFlags_NoSplit | ImGuiDockNodeFlags_NoResizeFlagsMask_ | ImGuiDockNodeFlags_AutoHideTabBar | ImGuiDockNodeFlags_DockSpace | ImGuiDockNodeFlags_CentralNode | ImGuiDockNodeFlags_NoTabBar | ImGuiDockNodeFlags_HiddenTabBar | ImGuiDockNodeFlags_NoWindowMenuButton | ImGuiDockNodeFlags_NoCloseButton | ImGuiDockNodeFlags_NoDocking}
     */
    public static final int LocalFlagsMask_ = 12713072;

    /**
     * When splitting those flags are moved to the inheriting child, never duplicated
     *
     * <p>Definition: {@code ImGuiDockNodeFlags_LocalFlagsMask_ {@code &} ~ImGuiDockNodeFlags_DockSpace}
     */
    public static final int LocalFlagsTransferMask_ = 12712048;

    /**
     * Definition: {@code ImGuiDockNodeFlags_NoResizeFlagsMask_ | ImGuiDockNodeFlags_DockSpace | ImGuiDockNodeFlags_CentralNode | ImGuiDockNodeFlags_NoTabBar | ImGuiDockNodeFlags_HiddenTabBar | ImGuiDockNodeFlags_NoWindowMenuButton | ImGuiDockNodeFlags_NoCloseButton | ImGuiDockNodeFlags_NoDocking}
     */
    public static final int SavedFlagsMask_ = 12712992;
}
