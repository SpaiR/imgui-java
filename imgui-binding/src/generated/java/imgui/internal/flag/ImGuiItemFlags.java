package imgui.internal.flag;

/**
 * Transient per-window flags, reset at the beginning of the frame. For child window, inherited from parent on first Begin().
 * This is going to be exposed in imgui.h when stabilized enough.
 */
public final class ImGuiItemFlags {
    private ImGuiItemFlags() {
    }

    public static final int None = 0;
    public static final int NoTabStop = 1;
    /**
     * Button() will return true multiple times based on io.KeyRepeatDelay and io.KeyRepeatRate settings.
     */
    public static final int ButtonRepeat = 1 << 1;
    /**
     * [BETA] Disable interactions but doesn't affect visuals yet. See github.com/ocornut/imgui/issues/211
     */
    public static final int Disabled = 1 << 2;
    public static final int NoNav = 1 << 3;
    public static final int NoNavDefaultFocus = 1 << 4;
    /**
     * MenuItem/Selectable() automatically closes current Popup window
     */
    public static final int SelectableDontClosePopup = 1 << 5;
    /**
     * [BETA] Represent a mixed/indeterminate value, generally multi-selection where values differ.
     * Currently only supported by Checkbox() (later should support all sorts of widgets)
     */
    public static final int MixedValue = 1 << 6;
    /**
     * [ALPHA] Allow hovering interactions but underlying value is not changed.
     */
    public static final int ReadOnly = 1 << 7;
}
