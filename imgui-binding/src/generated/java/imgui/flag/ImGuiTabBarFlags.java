package imgui.flag;


/**
 * Flags for ImGui::BeginTabBar()
 */
public final class ImGuiTabBarFlags {
    private ImGuiTabBarFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Allow manually dragging tabs to re-order them + New tabs are appended at the end of list
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Reorderable = 1;

    /**
     * Automatically select new tabs when they appear
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int AutoSelectNewTabs = 2;

    /**
     * Disable buttons to open the tab list popup
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int TabListPopupButton = 4;

    /**
     * Disable behavior of closing tabs (that are submitted with p_open != NULL) with middle mouse button. You may handle this behavior manually on user's side with if (IsItemHovered() {@code &} {@code &} IsMouseClicked(2)) *p_open = false.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoCloseWithMiddleMouseButton = 8;

    /**
     * Disable scrolling buttons (apply when fitting policy is ImGuiTabBarFlags_FittingPolicyScroll)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoTabListScrollingButtons = 16;

    /**
     * Disable tooltips when hovering a tab
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoTooltip = 32;

    /**
     * Draw selected overline markers over selected tab
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int DrawSelectedOverline = 64;

    /**
     * Resize tabs when they don't fit
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int FittingPolicyResizeDown = 128;

    /**
     * Add scroll buttons when tabs don't fit
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int FittingPolicyScroll = 256;

    /**
     * Definition: {@code ImGuiTabBarFlags_FittingPolicyResizeDown | ImGuiTabBarFlags_FittingPolicyScroll}
     */
    public static final int FittingPolicyMask_ = 384;

    /**
     * Definition: {@code ImGuiTabBarFlags_FittingPolicyResizeDown}
     */
    public static final int FittingPolicyDefault_ = 128;
}
