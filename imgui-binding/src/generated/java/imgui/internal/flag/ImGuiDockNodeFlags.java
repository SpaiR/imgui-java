package imgui.internal.flag;

/**
 * Extend ImGuiDockNodeFlags
 */
public final class ImGuiDockNodeFlags extends imgui.flag.ImGuiDockNodeFlags {
    private ImGuiDockNodeFlags() {
    }

    /**
     * A dockspace is a node that occupy space within an existing user window. Otherwise the node is floating and create its own window.
     */
    public static final int DockSpace = 1 << 10;
    /**
     * The central node has 2 main properties: stay visible when empty, only use "remaining" spaces from its neighbor.
     */
    public static final int CentralNode = 1 << 11;
    /**
     * Tab bar is completely unavailable. No triangle in the corner to enable it back.
     */
    public static final int NoTabBar = 1 << 12;
    /**
     * Tab bar is hidden, with a triangle in the corner to show it again (NB: actual tab-bar instance may be destroyed as this is only used for single-window tab bar)
     */
    public static final int HiddenTabBar = 1 << 13;
    /**
     * Disable window/docking menu (that one that appears instead of the collapse button)
     */
    public static final int NoWindowMenuButton = 1 << 14;
    public static final int NoCloseButton = 1 << 15;
    /**
     * Disable any form of docking in this dockspace or individual node. (On a whole dockspace, this pretty much defeat the purpose of using a dockspace at all). Note: when turned on, existing docked nodes will be preserved.
     */
    public static final int NoDocking = 1 << 16;
    /**
     * [EXPERIMENTAL] Prevent another window/node from splitting this node.
     */
    public static final int NoDockingSplitMe = 1 << 17;
    /**
     * [EXPERIMENTAL] Prevent this node from splitting another window/node.
     */
    public static final int NoDockingSplitOther = 1 << 18;
    /**
     * [EXPERIMENTAL] Prevent another window/node to be docked over this node.
     */
    public static final int NoDockingOverMe = 1 << 19;
    /**
     * [EXPERIMENTAL] Prevent this node to be docked over another window/node.
     */
    public static final int NoDockingOverOther = 1 << 20;
    public static final int NoResizeX = 1 << 21;
    public static final int NoResizeY = 1 << 22;
}
