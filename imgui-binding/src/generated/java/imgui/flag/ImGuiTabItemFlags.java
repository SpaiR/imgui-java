package imgui.flag;

/**
 * Flags for ImGui::BeginTabItem()
 */
public final class ImGuiTabItemFlags {
    private ImGuiTabItemFlags() {
    }

    public static final int None = 0;
    /**
     * Append '*' to title without affecting the ID, as a convenience to avoid using the ### operator.
     * Also: tab is selected on closure and closure is deferred by one frame to allow code to undo it without flicker.
     */
    public static final int UnsavedDocument = 1;
    /**
     * Trigger flag to programmatically make the tab selected when calling BeginTabItem()
     */
    public static final int SetSelected = 1 << 1;
    /**
     * Disable behavior of closing tabs (that are submitted with p_open != NULL) with middle mouse button.
     * You can still repro this behavior on user's side with if (IsItemHovered() {@code &&} IsMouseClicked(2)) *p_open = false.
     */
    public static final int NoCloseWithMiddleMouseButton = 1 << 2;
    /**
     * Don't call PushID(tab.ID)/PopID() on BeginTabItem()/EndTabItem()
     */
    public static final int NoPushId = 1 << 3;
    /**
     * Disable tooltip for the given tab
     */
    public static final int NoTooltip = 1 << 4;
    /**
     * Disable reordering this tab or having another tab cross over this tab
     */
    public static final int NoReorder = 1 << 5;
    /**
     * Enforce the tab position to the left of the tab bar (after the tab list popup button)
     */
    public static final int Leading = 1 << 6;
    /**
     * Enforce the tab position to the right of the tab bar (before the scrolling buttons)
     */
    public static final int Trailing = 1 << 7;
}
