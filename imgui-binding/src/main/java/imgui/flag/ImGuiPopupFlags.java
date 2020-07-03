package imgui.flag;

/**
 * Flags for OpenPopup*(), BeginPopupContext*(), IsPopupOpen() functions.
 * - To be backward compatible with older API which took an 'int mouse_button = 1' argument, we need to treat
 *   small flags values as a mouse button index, so we encode the mouse button in the first few bits of the flags.
 *   It is therefore guaranteed to be legal to pass a mouse button index in ImGuiPopupFlags.
 * - For the same reason, we exceptionally default the ImGuiPopupFlags argument of BeginPopupContextXXX functions to 1 instead of 0.
 */
public final class ImGuiPopupFlags {
    private ImGuiPopupFlags() {
    }

    public static final int None = 0;
    /**
     * For BeginPopupContext*(): open on Left Mouse release. Guaranted to always be == 0 (same as ImGuiMouseButton_Left)
     */
    public static final int MouseButtonLeft = 0;
    /**
     * For BeginPopupContext*(): open on Right Mouse release. Guaranted to always be == 1 (same as ImGuiMouseButton_Right)
     */
    public static final int MouseButtonRight = 1;
    /**
     * For BeginPopupContext*(): open on Middle Mouse release. Guaranted to always be == 2 (same as ImGuiMouseButton_Middle)
     */
    public static final int MouseButtonMiddle = 2;
    public static final int MouseButtonMask = 0x1F;
    public static final int MouseButtonDefault = 1;
    /**
     * For OpenPopup*(), BeginPopupContext*(): don't open if there's already a popup at the same level of the popup stack
     */
    public static final int NoOpenOverExistingPopup = 1 << 5;
    /**
     * For BeginPopupContextWindow(): don't return true when hovering items, only when hovering empty space
     */
    public static final int NoOpenOverItems = 1 << 6;
    /**
     * For IsPopupOpen(): ignore the ImGuiID parameter and test for any popup.
     */
    public static final int AnyPopupId = 1 << 7;
    /**
     * For IsPopupOpen(): search/test at any level of the popup stack (default test in the current level)
     */
    public static final int AnyPopupLevel = 1 << 8;
    public static final int AnyPopup = AnyPopupId | AnyPopupLevel;
}
