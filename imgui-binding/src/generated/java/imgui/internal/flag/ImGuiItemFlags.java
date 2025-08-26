package imgui.internal.flag;


/**
 * Transient per-window flags, reset at the beginning of the frame. For child window, inherited from parent on first Begin().
 * This is going to be exposed in imgui.h when stabilized enough.
 */
public final class ImGuiItemFlags {
    private ImGuiItemFlags() {
    }

    /**
     * Controlled by user
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * false     // Disable keyboard tabbing. This is a "lighter" version of ImGuiItemFlags_NoNav.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoTabStop = 1;

    /**
     * false     // Button() will return true multiple times based on io.KeyRepeatDelay and io.KeyRepeatRate settings.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int ButtonRepeat = 2;

    /**
     * false     // Disable interactions but doesn't affect visuals. See BeginDisabled()/EndDisabled(). See github.com/ocornut/imgui/issues/211
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int Disabled = 4;

    /**
     * false     // Disable any form of focusing (keyboard/gamepad directional navigation and SetKeyboardFocusHere() calls)
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoNav = 8;

    /**
     * false     // Disable item being a candidate for default focus (e.g. used by title bar items)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoNavDefaultFocus = 16;

    /**
     * false     // Disable MenuItem/Selectable() automatically closing their popup window
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int SelectableDontClosePopup = 32;

    /**
     * false     // [BETA] Represent a mixed/indeterminate value, generally multi-selection where values differ. Currently only supported by Checkbox() (later should support all sorts of widgets)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int MixedValue = 64;

    /**
     * false     // [ALPHA] Allow hovering interactions but underlying value is not changed.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int ReadOnly = 128;

    /**
     * false     // Disable hoverable check in ItemHoverable()
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoWindowHoverableCheck = 256;

    /**
     * false     // Allow being overlapped by another widget. Not-hovered to Hovered transition deferred by a frame.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int AllowOverlap = 512;

    /**
     * false     // [WIP] Auto-activate input mode when tab focused. Currently only used and supported by a few items before it becomes a generic feature.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int Inputable = 1024;

    /**
     * false     // Set by SetNextItemSelectionUserData()
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int HasSelectionUserData = 2048;
}
