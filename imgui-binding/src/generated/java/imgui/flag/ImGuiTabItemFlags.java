package imgui.flag;


/**
 * Flags for ImGui::BeginTabItem()
 */
public final class ImGuiTabItemFlags {
    private ImGuiTabItemFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Display a dot next to the title + set ImGuiTabItemFlags_NoAssumedClosure.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int UnsavedDocument = 1;

    /**
     * Trigger flag to programmatically make the tab selected when calling BeginTabItem()
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int SetSelected = 2;

    /**
     * Disable behavior of closing tabs (that are submitted with p_open != NULL) with middle mouse button. You may handle this behavior manually on user's side with if (IsItemHovered() {@code &} {@code &} IsMouseClicked(2)) *p_open = false.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoCloseWithMiddleMouseButton = 4;

    /**
     * Don't call PushID()/PopID() on BeginTabItem()/EndTabItem()
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoPushId = 8;

    /**
     * Disable tooltip for the given tab
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoTooltip = 16;

    /**
     * Disable reordering this tab or having another tab cross over this tab
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoReorder = 32;

    /**
     * Enforce the tab position to the left of the tab bar (after the tab list popup button)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int Leading = 64;

    /**
     * Enforce the tab position to the right of the tab bar (before the scrolling buttons)
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int Trailing = 128;

    /**
     * Tab is selected when trying to close + closure is not immediately assumed (will wait for user to stop submitting the tab). Otherwise closure is assumed when pressing the X, so if you keep submitting the tab may reappear at end of tab bar.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoAssumedClosure = 256;
}
