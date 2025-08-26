package imgui.flag;


/**
 * Flags for OpenPopup*(), BeginPopupContext*(), IsPopupOpen() functions.
 * - To be backward compatible with older API which took an 'int mouse_button = 1' argument, we need to treat
 *   small flags values as a mouse button index, so we encode the mouse button in the first few bits of the flags.
 *   It is therefore guaranteed to be legal to pass a mouse button index in ImGuiPopupFlags.
 * - For the same reason, we exceptionally default the ImGuiPopupFlags argument of BeginPopupContextXXX functions to 1 instead of 0.
 * - Multiple buttons currently cannot be combined/or-ed in those functions (we could allow it later).
 */
public final class ImGuiPopupFlags {
    private ImGuiPopupFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * For BeginPopupContext*(): open on Left Mouse release. Guaranteed to always be == 0 (same as ImGuiMouseButton_Left)
     *
     * <p>Definition: {@code 0}
     */
    public static final int MouseButtonLeft = 0;

    /**
     * For BeginPopupContext*(): open on Right Mouse release. Guaranteed to always be == 1 (same as ImGuiMouseButton_Right)
     *
     * <p>Definition: {@code 1}
     */
    public static final int MouseButtonRight = 1;

    /**
     * For BeginPopupContext*(): open on Middle Mouse release. Guaranteed to always be == 2 (same as ImGuiMouseButton_Middle)
     *
     * <p>Definition: {@code 2}
     */
    public static final int MouseButtonMiddle = 2;

    /**
     * Definition: {@code 0x1F}
     */
    public static final int MouseButtonMask_ = 31;

    /**
     * Definition: {@code 1}
     */
    public static final int MouseButtonDefault_ = 1;

    /**
     * For OpenPopup*(), BeginPopupContext*(): don't reopen same popup if already open (won't reposition, won't reinitialize navigation)
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoReopen = 32;

    /**
     * For OpenPopup*(), BeginPopupContext*(): don't open if there's already a popup at the same level of the popup stack
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoOpenOverExistingPopup = 128;

    /**
     * For BeginPopupContextWindow(): don't return true when hovering items, only when hovering empty space
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoOpenOverItems = 256;

    /**
     * For IsPopupOpen(): ignore the ImGuiID parameter and test for any popup.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int AnyPopupId = 1024;

    /**
     * For IsPopupOpen(): search/test at any level of the popup stack (default test in the current level)
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int AnyPopupLevel = 2048;

    /**
     * Definition: {@code ImGuiPopupFlags_AnyPopupId | ImGuiPopupFlags_AnyPopupLevel}
     */
    public static final int AnyPopup = 3072;
}
