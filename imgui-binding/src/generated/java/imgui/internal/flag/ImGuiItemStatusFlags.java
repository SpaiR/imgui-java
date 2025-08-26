package imgui.internal.flag;


public class ImGuiItemStatusFlags {
    private ImGuiItemStatusFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Mouse position is within item rectangle (does NOT mean that the window is in correct z-order and can be hovered!, this is only one part of the most-common IsItemHovered test)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int HoveredRect = 1;

    /**
     * g.LastItemData.DisplayRect is valid
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int HasDisplayRect = 2;

    /**
     * Value exposed by item was edited in the current frame (should match the bool return value of most widgets)
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int Edited = 4;

    /**
     * Set when Selectable(), TreeNode() reports toggling a selection. We can't report "Selected", only state changes, in order to easily handle clipping with less issues.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int ToggledSelection = 8;

    /**
     * Set when TreeNode() reports toggling their open state.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int ToggledOpen = 16;

    /**
     * Set if the widget/group is able to provide data for the ImGuiItemStatusFlags_Deactivated flag.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int HasDeactivated = 32;

    /**
     * Only valid if ImGuiItemStatusFlags_HasDeactivated is set.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int Deactivated = 64;

    /**
     * Override the HoveredWindow test to allow cross-window hover testing.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int HoveredWindow = 128;

    /**
     * [WIP] Set when item is overlapping the current clipping rectangle (Used internally. Please don't use yet: API/system will change as we refactor Itemadd()).
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int Visible = 256;

    /**
     * g.LastItemData.ClipRect is valid.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int HasClipRect = 512;

    /**
     * g.LastItemData.Shortcut valid. Set by SetNextItemShortcut() {@code ->} ItemAdd().
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int HasShortcut = 1024;
}
