package imgui.flag;


/**
 * Flags for ImGui::Selectable()
 */
public final class ImGuiSelectableFlags {
    private ImGuiSelectableFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Clicking this doesn't close parent popup window (overrides ImGuiItemFlags_AutoClosePopups)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoAutoClosePopups = 1;

    /**
     * Frame will span all columns of its container table (text will still fit in current column)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int SpanAllColumns = 2;

    /**
     * Generate press events on double clicks too
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AllowDoubleClick = 4;

    /**
     * Cannot be selected, display grayed out text
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int Disabled = 8;

    /**
     * Hit testing will allow subsequent widgets to overlap this one. Require previous frame HoveredId to match before being usable. Shortcut to calling SetNextItemAllowOverlap().
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int AllowOverlap = 16;

    /**
     * Make the item be displayed as if it is hovered
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int Highlight = 32;

    /**
     * Auto-select when moved into, unless Ctrl is held. Automatic when in a BeginMultiSelect() block.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int SelectOnNav = 64;

    /**
     * Renamed in 1.91.0
     *
     * <p>Definition: {@code ImGuiSelectableFlags_NoAutoClosePopups}
     */
    public static final int DontClosePopups = 1;
}
