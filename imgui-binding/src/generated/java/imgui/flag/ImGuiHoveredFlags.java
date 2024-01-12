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
     */
    public static final int None = 0;

    /**
     * IsWindowHovered() only: Return true if any children of the window is hovered
     */
    public static final int ChildWindows = 1;

    /**
     * IsWindowHovered() only: Test from root window (top most parent of the current hierarchy)
     */
    public static final int RootWindow = 1 << 1;

    /**
     * IsWindowHovered() only: Return true if any window is hovered
     */
    public static final int AnyWindow = 1 << 2;

    /**
     * IsWindowHovered() only: Do not consider popup hierarchy (do not treat popup emitter as parent of popup) (when used with _ChildWindows or _RootWindow)
     */
    public static final int NoPopupHierarchy = 1 << 3;

    /**
     * IsWindowHovered() only: Consider docking hierarchy (treat dockspace host as parent of docked window) (when used with _ChildWindows or _RootWindow)
     */
    public static final int DockHierarchy = 1 << 4;

    /**
     * Return true even if a popup window is normally blocking access to this item/window
     */
    public static final int AllowWhenBlockedByPopup = 1 << 5;

    //ImGuiHoveredFlags_AllowWhenBlockedByModal     = 1 << 6, //Return true even if a modal popup window is normally blocking access to this item/window. FIXME-TODO: Unavailable yet.

    /**
     * Return true even if an active item is blocking access to this item/window. Useful for Drag and Drop patterns.
     */
    public static final int AllowWhenBlockedByActiveItem = 1 << 7;

    /**
     * Return true even if the position is obstructed or overlapped by another window
     */
    public static final int AllowWhenOverlapped = 1 << 8;

    /**
     * Return true even if the item is disabled
     */
    public static final int AllowWhenDisabled = 1 << 9;

    public static final int RectOnly = AllowWhenBlockedByPopup | AllowWhenBlockedByActiveItem | AllowWhenOverlapped;

    public static final int RootAndChildWindows = RootWindow | ChildWindows;
}
