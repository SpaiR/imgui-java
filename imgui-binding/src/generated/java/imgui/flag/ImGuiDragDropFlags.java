package imgui.flag;


/**
 * Flags for ImGui::BeginDragDropSource(), ImGui::AcceptDragDropPayload()
 */
public final class ImGuiDragDropFlags {
    private ImGuiDragDropFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Disable preview tooltip. By default, a successful call to BeginDragDropSource opens a tooltip so you can display a preview or description of the source contents. This flag disables this behavior.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int SourceNoPreviewTooltip = 1;

    /**
     * By default, when dragging we clear data so that IsItemHovered() will return false, to avoid subsequent user code submitting tooltips. This flag disables this behavior so you can still call IsItemHovered() on the source item.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int SourceNoDisableHover = 2;

    /**
     * Disable the behavior that allows to open tree nodes and collapsing header by holding over them while dragging a source item.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int SourceNoHoldToOpenOthers = 4;

    /**
     * Allow items such as Text(), Image() that have no unique identifier to be used as drag source, by manufacturing a temporary identifier based on their window-relative position. This is extremely unusual within the dear imgui ecosystem and so we made it explicit.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int SourceAllowNullID = 8;

    /**
     * External source (from outside of dear imgui), won't attempt to read current item/window info. Will always return true. Only one Extern source can be active simultaneously.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int SourceExtern = 16;

    /**
     * Automatically expire the payload if the source cease to be submitted (otherwise payloads are persisting while being dragged)
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int SourceAutoExpirePayload = 32;

    /**
     * AcceptDragDropPayload() will returns true even before the mouse button is released. You can then call IsDelivery() to test if the payload needs to be delivered.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int AcceptBeforeDelivery = 1024;

    /**
     * Do not draw the default highlight rectangle when hovering over target.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int AcceptNoDrawDefaultRect = 2048;

    /**
     * Request hiding the BeginDragDropSource tooltip from the BeginDragDropTarget site.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int AcceptNoPreviewTooltip = 4096;

    /**
     * For peeking ahead and inspecting the payload before delivery.
     *
     * <p>Definition: {@code ImGuiDragDropFlags_AcceptBeforeDelivery | ImGuiDragDropFlags_AcceptNoDrawDefaultRect}
     */
    public static final int AcceptPeekOnly = 3072;
}
