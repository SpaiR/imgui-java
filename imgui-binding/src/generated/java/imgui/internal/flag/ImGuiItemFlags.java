package imgui.internal.flag;


/**
 * Transient per-window flags, reset at the beginning of the frame. For child window, inherited from parent on first Begin().
 * This is going to be exposed in imgui.h when stabilized enough.
 */
public final class ImGuiItemFlags {
    private ImGuiItemFlags() {
    }

    /**
     * (Default)
     *
     * <p>Definition: {@code o}
     */
    public static final int None = 0;

    /**
     * false    // Disable keyboard tabbing. This is a "lighter" version of ImGuiItemFlags_NoNav.
     *
     * <p>Definition: {@code dler);}
     */
    public static final int NoTabStop = 1;

    /**
     * false    // Disable any form of focusing (keyboard/gamepad directional navigation and SetKeyboardFocusHere() calls).
     *
     * <p>Definition: {@code }
     */
    public static final int NoNav = 2;

    /**
     * false    // Disable item being a candidate for default focus (e.g. used by title bar items).
     *
     * <p>Definition: {@code me);}
     */
    public static final int NoNavDefaultFocus = 4;

    /**
     * false    // Any button-like behavior will have repeat mode enabled (based on io.KeyRepeatDelay and io.KeyRepeatRate values). Note that you can also call IsItemActive() after any button to tell if it is being held.
     *
     * <p>Definition: {@code sHandl}
     */
    public static final int ButtonRepeat = 8;

    /**
     * true     // MenuItem()/Selectable() automatically close their parent popup window.
     *
     * <p>Definition: {@code r)}
     */
    public static final int AutoClosePopups = 16;

    /**
     * false    // Allow submitting an item with the same identifier as an item already submitted this frame without triggering a warning tooltip if io.ConfigDebugHighlightIdConflicts is set.
     *
     * <p>Definition: {@code ies in}
     */
    public static final int AllowDuplicateId = 32;

    /**
     * false    // [Internal] Disable interactions. DOES NOT affect visuals. This is used by BeginDisabled()/EndDisabled() and only provided here so you can read back via GetItemFlags().
     *
     * <p>Definition: {@code ------}
     */
    public static final int Disabled = 64;
}
