package imgui.flag;

/**
 * Flags for ImGui::BeginDragDropSource(), ImGui::AcceptDragDropPayload()
 */
public final class ImGuiDragDropFlags {
    private ImGuiDragDropFlags() {
    }

    public static final int None = 0;

    // BeginDragDropSource() flags

    /**
     * By default, a successful call to BeginDragDropSource opens a tooltip so you can display a preview or description of the source contents. This flag disable this behavior.
     */
    public static final int SourceNoPreviewTooltip = 1;
    /**
     * By default, when dragging we clear data so that IsItemHovered() will return false, to avoid subsequent user code submitting tooltips.
     * This flag disable this behavior so you can still call IsItemHovered() on the source item.
     */
    public static final int SourceNoDisableHover = 1 << 1;
    /**
     * Disable the behavior that allows to open tree nodes and collapsing header by holding over them while dragging a source item.
     */
    public static final int SourceNoHoldToOpenOthers = 1 << 2;
    /**
     * Allow items such as Text(), Image() that have no unique identifier to be used as drag source,
     * by manufacturing a temporary identifier based on their window-relative position.
     * This is extremely unusual within the dear imgui ecosystem and so we made it explicit.
     */
    public static final int SourceAllowNullID = 1 << 3;
    /**
     * External source (from outside of dear imgui), won't attempt to read current item/window info. Will always return true.
     * Only one Extern source can be active simultaneously.
     */
    public static final int SourceExtern = 1 << 4;
    /**
     * Automatically expire the payload if the source cease to be submitted (otherwise payloads are persisting while being dragged)
     */
    public static final int SourceAutoExpirePayload = 1 << 5;

    // AcceptDragDropPayload() flags

    /**
     * AcceptDragDropPayload() will returns true even before the mouse button is released. You can then call IsDelivery() to test if the payload needs to be delivered.
     */
    public static final int AcceptBeforeDelivery = 1 << 10;
    /**
     * Do not draw the default highlight rectangle when hovering over target.
     */
    public static final int AcceptNoDrawDefaultRect = 1 << 11;
    /**
     * Request hiding the BeginDragDropSource tooltip from the BeginDragDropTarget site.
     */
    public static final int AcceptNoPreviewTooltip = 1 << 12;
    /**
     * For peeking ahead and inspecting the payload before delivery.
     */
    public static final int AcceptPeekOnly = AcceptBeforeDelivery | AcceptNoDrawDefaultRect;
}
