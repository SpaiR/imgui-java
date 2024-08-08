package imgui.flag;


/**
 * Flags for ImGui::TreeNodeEx(), ImGui::CollapsingHeader*()
 */
public final class ImGuiTreeNodeFlags {
    private ImGuiTreeNodeFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Draw as selected
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Selected = 1;

    /**
     * Draw frame with background (e.g. for CollapsingHeader)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int Framed = 2;

    /**
     * Hit testing to allow subsequent widgets to overlap this one
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AllowItemOverlap = 4;

    /**
     * Don't do a TreePush() when open (e.g. for CollapsingHeader) = no extra indent nor pushing on ID stack
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoTreePushOnOpen = 8;

    /**
     * Don't automatically and temporarily open node when Logging is active (by default logging will automatically open tree nodes)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoAutoOpenOnLog = 16;

    /**
     * Default node to be open
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int DefaultOpen = 32;

    /**
     * Need double-click to open node
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int OpenOnDoubleClick = 64;

    /**
     * Only open when clicking on the arrow part. If ImGuiTreeNodeFlags_OpenOnDoubleClick is also set, single-click arrow or double-click all box to open.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int OpenOnArrow = 128;

    /**
     * No collapsing, no arrow (use as a convenience for leaf nodes).
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int Leaf = 256;

    /**
     * Display a bullet instead of arrow
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int Bullet = 512;

    /**
     * Use FramePadding (even for an unframed text node) to vertically align text baseline to regular widget height. Equivalent to calling AlignTextToFramePadding().
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int FramePadding = 1024;

    /**
     * Extend hit box to the right-most edge, even if not framed. This is not the default in order to allow adding other items on the same line. In the future we may refactor the hit system to be front-to-back, allowing natural overlaps and then this can become the default.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int SpanAvailWidth = 2048;

    /**
     * Extend hit box to the left-most and right-most edges (bypass the indented area).
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int SpanFullWidth = 4096;

    /**
     * (WIP) Nav: left direction may move to this TreeNode() from any of its child (items submitted between TreeNode and TreePop)
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int NavLeftJumpsBackHere = 8192;

    /**
     * ImGuiTreeNodeFlags_NoScrollOnOpen     = 1 {@code <<} 14,  // FIXME: TODO: Disable automatic scroll on TreePop() if node got just open and contents is not visible
     *
     * <p>Definition: {@code ImGuiTreeNodeFlags_Framed | ImGuiTreeNodeFlags_NoTreePushOnOpen | ImGuiTreeNodeFlags_NoAutoOpenOnLog}
     */
    public static final int CollapsingHeader = 26;
}
