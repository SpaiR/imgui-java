package imgui.flag;


/**
 * Flags for {@link imgui.ImGui#tableSetupColumn(String, int)}
 */
public final class ImGuiTableColumnFlags {
    private ImGuiTableColumnFlags() {
    }

    /**
     * Input configuration flags
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Overriding/master disable flag: hide column, won't show in context menu (unlike calling TableSetColumnEnabled() which manipulates the user accessible state)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Disabled = 1;

    /**
     * Default as a hidden/disabled column.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int DefaultHide = 2;

    /**
     * Default as a sorting column.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int DefaultSort = 4;

    /**
     * Column will stretch. Preferable with horizontal scrolling disabled (default if table sizing policy is _SizingStretchSame or _SizingStretchProp).
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int WidthStretch = 8;

    /**
     * Column will not stretch. Preferable with horizontal scrolling enabled (default if table sizing policy is _SizingFixedFit and table is resizable).
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int WidthFixed = 16;

    /**
     * Disable manual resizing.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoResize = 32;

    /**
     * Disable manual reordering this column, this will also prevent other columns from crossing over this column.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoReorder = 64;

    /**
     * Disable ability to hide/disable this column.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoHide = 128;

    /**
     * Disable clipping for this column (all NoClip columns will render in a same draw command).
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoClip = 256;

    /**
     * Disable ability to sort on this field (even if ImGuiTableFlags_Sortable is set on the table).
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int NoSort = 512;

    /**
     * Disable ability to sort in the ascending direction.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int NoSortAscending = 1024;

    /**
     * Disable ability to sort in the descending direction.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int NoSortDescending = 2048;

    /**
     * TableHeadersRow() will not submit horizontal label for this column. Convenient for some small columns. Name will still appear in context menu or in angled headers.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int NoHeaderLabel = 4096;

    /**
     * Disable header text width contribution to automatic column width.
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int NoHeaderWidth = 8192;

    /**
     * Make the initial sort direction Ascending when first sorting on this column (default).
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int PreferSortAscending = 16384;

    /**
     * Make the initial sort direction Descending when first sorting on this column.
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int PreferSortDescending = 32768;

    /**
     * Use current Indent value when entering cell (default for column 0).
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int IndentEnable = 65536;

    /**
     * Ignore current Indent value when entering cell (default for columns{@code >}0). Indentation changes _within_ the cell will still be honored.
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int IndentDisable = 131072;

    /**
     * TableHeadersRow() will submit an angled header row for this column. Note this will add an extra row.
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int AngledHeader = 262144;

    /**
     * Status: is enabled == not hidden by user/api (referred to as "Hide" in _DefaultHide and _NoHide) flags.
     *
     * <p>Definition: {@code 1 << 24}
     */
    public static final int IsEnabled = 16777216;

    /**
     * Status: is visible == is enabled AND not clipped by scrolling.
     *
     * <p>Definition: {@code 1 << 25}
     */
    public static final int IsVisible = 33554432;

    /**
     * Status: is currently part of the sort specs
     *
     * <p>Definition: {@code 1 << 26}
     */
    public static final int IsSorted = 67108864;

    /**
     * Status: is hovered by mouse
     *
     * <p>Definition: {@code 1 << 27}
     */
    public static final int IsHovered = 134217728;

    /**
     * [Internal] Combinations and masks
     *
     * <p>Definition: {@code ImGuiTableColumnFlags_WidthStretch | ImGuiTableColumnFlags_WidthFixed}
     */
    public static final int WidthMask_ = 24;

    /**
     * [Internal] Combinations and masks
     *
     * <p>Definition: {@code ImGuiTableColumnFlags_IndentEnable | ImGuiTableColumnFlags_IndentDisable}
     */
    public static final int IndentMask_ = 196608;

    /**
     * [Internal] Combinations and masks
     *
     * <p>Definition: {@code ImGuiTableColumnFlags_IsEnabled | ImGuiTableColumnFlags_IsVisible | ImGuiTableColumnFlags_IsSorted | ImGuiTableColumnFlags_IsHovered}
     */
    public static final int StatusMask_ = 251658240;

    /**
     * [Internal] Disable user resizing this column directly (it may however we resized indirectly from its left edge)
     *
     * <p>Definition: {@code 1 << 30}
     */
    public static final int NoDirectResize_ = 1073741824;
}
