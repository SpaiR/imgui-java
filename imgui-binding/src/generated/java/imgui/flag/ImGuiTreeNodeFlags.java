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
     * Hit testing will allow subsequent widgets to overlap this one. Require previous frame HoveredId to match before being usable. Shortcut to calling SetNextItemAllowOverlap().
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AllowOverlap = 4;

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
     * Open on double-click instead of simple click (default for multi-select unless any _OpenOnXXX behavior is set explicitly). Both behaviors may be combined.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int OpenOnDoubleClick = 64;

    /**
     * Open when clicking on the arrow part (default for multi-select unless any _OpenOnXXX behavior is set explicitly). Both behaviors may be combined.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int OpenOnArrow = 128;

    /**
     * No collapsing, no arrow (use as a convenience for leaf nodes). Note: will always open a tree/id scope and return true. If you never use that scope, add ImGuiTreeNodeFlags_NoTreePushOnOpen.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int Leaf = 256;

    /**
     * Display a bullet instead of arrow. IMPORTANT: node can still be marked open/close if you don't set the _Leaf flag!
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int Bullet = 512;

    /**
     * Use FramePadding (even for an unframed text node) to vertically align text baseline to regular widget height. Equivalent to calling AlignTextToFramePadding() before the node.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int FramePadding = 1024;

    /**
     * Extend hit box to the right-most edge, even if not framed. This is not the default in order to allow adding other items on the same line without using AllowOverlap mode.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int SpanAvailWidth = 2048;

    /**
     * Extend hit box to the left-most and right-most edges (cover the indent area).
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int SpanFullWidth = 4096;

    /**
     * Narrow hit box + narrow hovering highlight, will only cover the label text.
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int SpanLabelWidth = 8192;

    /**
     * Frame will span all columns of its container table (label will still fit in current column)
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int SpanAllColumns = 16384;

    /**
     * Label will span all columns of its container table
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int LabelSpanAllColumns = 32768;

    /**
     * Nav: left arrow moves back to parent. This is processed in TreePop() when there's an unfulfilled Left nav request remaining.
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NavLeftJumpsToParent = 131072;

    /**
     * Definition: {@code ImGuiTreeNodeFlags_Framed | ImGuiTreeNodeFlags_NoTreePushOnOpen | ImGuiTreeNodeFlags_NoAutoOpenOnLog}
     */
    public static final int CollapsingHeader = 26;

    /**
     * No lines drawn
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int DrawLinesNone = 262144;

    /**
     * Horizontal lines to child nodes. Vertical line drawn down to TreePop() position: cover full contents. Faster (for large trees).
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int DrawLinesFull = 524288;

    /**
     * Horizontal lines to child nodes. Vertical line drawn down to bottom-most child node. Slower (for large trees).
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int DrawLinesToNodes = 1048576;

    /**
     * Renamed in 1.92.0
     *
     * <p>Definition: {@code ImGuiTreeNodeFlags_NavLeftJumpsToParent}
     */
    public static final int NavLeftJumpsBackHere = 131072;

    /**
     * Renamed in 1.90.7
     *
     * <p>Definition: {@code ImGuiTreeNodeFlags_SpanLabelWidth}
     */
    public static final int SpanTextWidth = 8192;
}
