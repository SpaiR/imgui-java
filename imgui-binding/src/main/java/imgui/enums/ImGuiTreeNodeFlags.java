package imgui.enums;

/**
 * Flags for ImGui::TreeNodeEx(), ImGui::CollapsingHeader*()
 */
public final class ImGuiTreeNodeFlags {
    private ImGuiTreeNodeFlags() {
    }

    public static final int None = 0;
    /**
     * Draw as selected
     */
    public static final int Selected = 1;
    /**
     * Full colored frame (e.g. for CollapsingHeader)
     */
    public static final int Framed = 1 << 1;
    /**
     * Hit testing to allow subsequent widgets to overlap this one
     */
    public static final int AllowItemOverlap = 1 << 2;
    /**
     * Don't do a TreePush() when open (e.g. for CollapsingHeader) = no extra indent nor pushing on ID stack
     */
    public static final int NoTreePushOnOpen = 1 << 3;
    /**
     * Don't automatically and temporarily open node when Logging is active (by default logging will automatically open tree nodes)
     */
    public static final int NoAutoOpenOnLog = 1 << 4;
    /**
     * Default node to be open
     */
    public static final int DefaultOpen = 1 << 5;
    /**
     * Need double-click to open node
     */
    public static final int OpenOnDoubleClick = 1 << 6;
    /**
     * Only open when clicking on the arrow part. If ImGuiTreeNodeFlags_OpenOnDoubleClick is also set, single-click arrow or double-click all box to open.
     */
    public static final int OpenOnArrow = 1 << 7;
    /**
     * No collapsing, no arrow (use as a convenience for leaf nodes).
     */
    public static final int Leaf = 1 << 8;
    /**
     * Display a bullet instead of arrow
     */
    public static final int Bullet = 1 << 9;
    /**
     * Use FramePadding (even for an unframed text node) to vertically align text baseline to regular widget height. Equivalent to calling AlignTextToFramePadding().
     */
    public static final int FramePadding = 1 << 1;
    /**
     * Extend hit box to the right-most edge, even if not framed.
     * This is not the default in order to allow adding other items on the same line.
     * In the future we may refactor the hit system to be front-to-back, allowing natural overlaps and then this can become the default.
     */
    public static final int SpanAvailWidth = 1 << 1;
    /**
     * Extend hit box to the left-most and right-most edges (bypass the indented area).
     */
    public static final int SpanFullWidth = 1 << 1;
    /**
     * (WIP) Nav: left direction may move to this TreeNode() from any of its child (items submitted between TreeNode and TreePop)
     */
    public static final int NavLeftJumpsBackHere = 1 << 1;
    //ImGuiTreeNodeFlags_NoScrollOnOpen     = 1 << 14;     // FIXME: TODO: Disable automatic scroll on TreePop() if node got just open and contents is not visible
    public static final int CollapsingHeader = Framed | NoTreePushOnOpen | NoAutoOpenOnLog;
}
