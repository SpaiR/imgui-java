package imgui.enums;

/**
 * Flags for ImGui::BeginTabBar()
 */
public final class ImGuiTabBarFlags {
    private ImGuiTabBarFlags() {
    }

    public static final int None = 0;
    /**
     * Allow manually dragging tabs to re-order them + New tabs are appended at the end of list
     */
    public static final int Reorderable = 1;
    /**
     * Automatically select new tabs when they appear
     */
    public static final int AutoSelectNewTabs = 1 << 1;
    /**
     * Disable buttons to open the tab list popup
     */
    public static final int TabListPopupButton = 1 << 2;
    /**
     * Disable behavior of closing tabs (that are submitted with p_open != NULL) with middle mouse button.
     * You can still repro this behavior on user's side with if (IsItemHovered() {@code &&} IsMouseClicked(2)) *p_open = false.
     */
    public static final int NoCloseWithMiddleMouseButton = 1 << 3;
    /**
     * Disable scrolling buttons (apply when fitting policy is ImGuiTabBarFlags_FittingPolicyScroll)
     */
    public static final int NoTabListScrollingButtons = 1 << 4;
    /**
     * Disable tooltips when hovering a tab
     */
    public static final int NoTooltip = 1 << 5;
    /**
     * Resize tabs when they don't fit
     */
    public static final int FittingPolicyResizeDown = 1 << 6;
    /**
     * Add scroll buttons when tabs don't fit
     */
    public static final int FittingPolicyScroll = 1 << 7;
    public static final int FittingPolicyMask_ = FittingPolicyResizeDown | FittingPolicyScroll;
    public static final int FittingPolicyDefault_ = FittingPolicyResizeDown;
}
