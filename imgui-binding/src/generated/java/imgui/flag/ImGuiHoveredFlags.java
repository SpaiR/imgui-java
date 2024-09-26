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
     * IsItemHovered() only: Return true even if the position is obstructed or overlapped by another window
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int AllowWhenOverlapped = 256;

    /**
     * IsItemHovered() only: Return true even if the item is disabled
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int AllowWhenDisabled = 512;

    /**
     * Definition: {@code ImGuiHoveredFlags_AllowWhenBlockedByPopup | ImGuiHoveredFlags_AllowWhenBlockedByActiveItem | ImGuiHoveredFlags_AllowWhenOverlapped}
     */
    public static final int RectOnly = 416;

    /**
     * Definition: {@code ImGuiHoveredFlags_RootWindow | ImGuiHoveredFlags_ChildWindows}
     */
    public static final int RootAndChildWindows = 3;
}
