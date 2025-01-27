package imgui.flag;


/**
 * Flags for ImGui::IsItemHovered(), ImGui::IsWindowHovered()
 * Note:if you are trying to check whether your mouse should be dispatched to Dear ImGui or to your app,
 * you should use'io.WantCaptureMouse'instead!Please read the FAQ!Note: windows with the ImGuiWindowFlags_NoInputs flag are ignored by IsWindowHovered() calls.
 */
public final class ImGuiHoveredFlags {
    private ImGuiHoveredFlags() {
    }

    /**
     * Return true if directly over the item/window, not obstructed by another window, not obstructed by an active popup or modal blocking inputs under them.
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * IsWindowHovered() only: Return true if any children of the window is hovered
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int ChildWindows = 1;

    /**
     * IsWindowHovered() only: Test from root window (top most parent of the current hierarchy)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int RootWindow = 2;

    /**
     * IsWindowHovered() only: Return true if any window is hovered
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AnyWindow = 4;

    /**
     * IsWindowHovered() only: Do not consider popup hierarchy (do not treat popup emitter as parent of popup) (when used with _ChildWindows or _RootWindow)
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoPopupHierarchy = 8;

    /**
     * IsWindowHovered() only: Consider docking hierarchy (treat dockspace host as parent of docked window) (when used with _ChildWindows or _RootWindow)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int DockHierarchy = 16;

    /**
     * Return true even if a popup window is normally blocking access to this item/window
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int AllowWhenBlockedByPopup = 32;

    /**
     * Return true even if an active item is blocking access to this item/window. Useful for Drag and Drop patterns.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int AllowWhenBlockedByActiveItem = 128;

    /**
     * IsItemHovered() only: Return true even if the item uses AllowOverlap mode and is overlapped by another hoverable item.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int AllowWhenOverlappedByItem = 256;

    /**
     * IsItemHovered() only: Return true even if the position is obstructed or overlapped by another window.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int AllowWhenOverlappedByWindow = 512;

    /**
     * IsItemHovered() only: Return true even if the item is disabled
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int AllowWhenDisabled = 1024;

    /**
     * IsItemHovered() only: Disable using gamepad/keyboard navigation state when active, always query mouse
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int NoNavOverride = 2048;

    /**
     * Definition: {@code ImGuiHoveredFlags_AllowWhenOverlappedByItem | ImGuiHoveredFlags_AllowWhenOverlappedByWindow}
     */
    public static final int AllowWhenOverlapped = 768;

    /**
     * Definition: {@code ImGuiHoveredFlags_AllowWhenBlockedByPopup | ImGuiHoveredFlags_AllowWhenBlockedByActiveItem | ImGuiHoveredFlags_AllowWhenOverlapped}
     */
    public static final int RectOnly = 928;

    /**
     * Definition: {@code ImGuiHoveredFlags_RootWindow | ImGuiHoveredFlags_ChildWindows}
     */
    public static final int RootAndChildWindows = 3;

    /**
     * Shortcut for standard flags when using IsItemHovered() + SetTooltip() sequence.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int ForTooltip = 4096;

    /**
     * Require mouse to be stationary for style.HoverStationaryDelay (~0.15 sec) _at least one time_. After this, can move on same item/window. Using the stationary test tends to reduces the need for a long delay.
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int Stationary = 8192;

    /**
     * IsItemHovered() only: Return true immediately (default). As this is the default you generally ignore this.
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int DelayNone = 16384;

    /**
     * IsItemHovered() only: Return true after style.HoverDelayShort elapsed (~0.15 sec) (shared between items) + requires mouse to be stationary for style.HoverStationaryDelay (once per item).
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int DelayShort = 32768;

    /**
     * IsItemHovered() only: Return true after style.HoverDelayNormal elapsed (~0.40 sec) (shared between items) + requires mouse to be stationary for style.HoverStationaryDelay (once per item).
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int DelayNormal = 65536;

    /**
     * IsItemHovered() only: Disable shared delay system where moving from one item to the next keeps the previous timer for a short time (standard for tooltips with long delays)
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NoSharedDelay = 131072;
}
