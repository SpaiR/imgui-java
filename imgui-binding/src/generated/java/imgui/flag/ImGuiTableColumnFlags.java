package imgui.flag;

/**
 * Flags for {@link imgui.ImGui#tableSetupColumn(String, int)}
 */
public final class ImGuiTableColumnFlags {
    private ImGuiTableColumnFlags() {
    }

    // Input configuration flags
    public static final int None = 0;
    /**
     * Overriding/master disable flag: hide column, won't show in context menu (unlike calling TableSetColumnEnabled() which manipulates the user accessible state)
     */
    public static final int Disabled = 1;
    /**
     * Default as a hidden/disabled column.
     */
    public static final int DefaultHide = 1 << 1;
    /**
     * Default as a sorting column.
     */
    public static final int DefaultSort = 1 << 2;
    /**
     * Column will stretch. Preferable with horizontal scrolling disabled (default if table sizing policy is _SizingStretchSame or _SizingStretchProp).
     */
    public static final int WidthStretch = 1 << 3;
    /**
     * Column will not stretch. Preferable with horizontal scrolling enabled (default if table sizing policy is _SizingFixedFit and table is resizable).
     */
    public static final int WidthFixed = 1 << 4;
    /**
     * Disable manual resizing.
     */
    public static final int NoResize = 1 << 5;
    /**
     * Disable manual reordering this column, this will also prevent other columns from crossing over this column.
     */
    public static final int NoReorder = 1 << 6;
    /**
     * Disable ability to hide/disable this column.
     */
    public static final int NoHide = 1 << 7;
    /**
     * Disable clipping for this column (all NoClip columns will render in a same draw command).
     */
    public static final int NoClip = 1 << 8;
    /**
     * Disable ability to sort on this field (even if ImGuiTableFlags_Sortable is set on the table).
     */
    public static final int NoSort = 1 << 9;
    /**
     * Disable ability to sort in the ascending direction.
     */
    public static final int NoSortAscending = 1 << 10;
    /**
     * Disable ability to sort in the descending direction.
     */
    public static final int NoSortDescending = 1 << 11;
    /**
     * TableHeadersRow() will not submit label for this column. Convenient for some small columns. Name will still appear in context menu.
     */
    public static final int NoHeaderLabel = 1 << 12;
    /**
     * Disable header text width contribution to automatic column width.
     */
    public static final int NoHeaderWidth = 1 << 13;
    /**
     * Make the initial sort direction Ascending when first sorting on this column (default).
     */
    public static final int PreferSortAscending = 1 << 14;
    /**
     * Make the initial sort direction Descending when first sorting on this column.
     */
    public static final int PreferSortDescending = 1 << 15;
    /**
     * Use current Indent value when entering cell (default for column 0).
     */
    public static final int IndentEnable = 1 << 16;
    /**
     * Ignore current Indent value when entering cell (default for columns {@code >} 0). Indentation changes _within_ the cell will still be honored.
     */
    public static final int IndentDisable = 1 << 17;

    // Output status flags, read-only via TableGetColumnFlags()
    /**
     * Status: is enabled == not hidden by user/api (referred to as "Hide" in _DefaultHide and _NoHide) flags.
     */
    public static final int IsEnabled = 1 << 24;
    /**
     * Status: is visible == is enabled AND not clipped by scrolling.
     */
    public static final int IsVisible = 1 << 25;
    /**
     * Status: is currently part of the sort specs
     */
    public static final int IsSorted = 1 << 26;
    /**
     * Status: is hovered by mouse
     */
    public static final int IsHovered = 1 << 27;
}
