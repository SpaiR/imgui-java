package imgui.flag;

/**
 * Flags for ImGui::BeginTable()
 * [BETA API] API may evolve slightly! If you use this, please update to the next version when it comes out!
 * - Important! Sizing policies have complex and subtle side effects, more so than you would expect.
 * Read comments/demos carefully + experiment with live demos to get acquainted with them.
 * - The DEFAULT sizing policies are:
 * - Default to SizingFixedFit    if ScrollX is on, or if host window has ImGuiWindowFlags_AlwaysAutoResize.
 * - Default to SizingStretchSame if ScrollX is off.
 * - When ScrollX is off:
 * - Table defaults to SizingStretchSame {@code ->} all Columns defaults to ImGuiTableColumnFlags_WidthStretch with same weight.
 * - Columns sizing policy allowed: Stretch (default), Fixed/Auto.
 * - Fixed Columns will generally obtain their requested width (unless the table cannot fit them all).
 * - Stretch Columns will share the remaining width.
 * - Mixed Fixed/Stretch columns is possible but has various side-effects on resizing behaviors.
 * The typical use of mixing sizing policies is: any number of LEADING Fixed columns, followed by one or two TRAILING Stretch columns.
 * (this is because the visible order of columns have subtle but necessary effects on how they react to manual resizing).
 * - When ScrollX is on:
 * - Table defaults to SizingFixedFit {@code ->} all Columns defaults to ImGuiTableColumnFlags_WidthFixed
 * - Columns sizing policy allowed: Fixed/Auto mostly.
 * - Fixed Columns can be enlarged as needed. Table will show an horizontal scrollbar if needed.
 * - When using auto-resizing (non-resizable) fixed columns, querying the content width to use item right-alignment e.g. SetNextItemWidth(-FLT_MIN) doesn't make sense, would create a feedback loop.
 * - Using Stretch columns OFTEN DOES NOT MAKE SENSE if ScrollX is on, UNLESS you have specified a value for 'inner_width' in BeginTable().
 * If you specify a value for 'inner_width' then effectively the scrolling space is known and Stretch or mixed Fixed/Stretch columns become meaningful again.
 * - Read on documentation at the top of imgui_tables.cpp for details.
 */
public final class ImGuiTableFlags {
    private ImGuiTableFlags() {
    }

    // Features
    public static final int None = 0;
    /**
     * Enable resizing columns.
     */
    public static final int Resizable = 1;
    /**
     * Enable reordering columns in header row (need calling TableSetupColumn() + TableHeadersRow() to display headers)
     */
    public static final int Reorderable = 1 << 1;
    /**
     * Enable hiding/disabling columns in context menu.
     */
    public static final int Hideable = 1 << 2;
    /**
     * Enable sorting. Call TableGetSortSpecs() to obtain sort specs. Also see SortMulti and SortTristate.
     */
    public static final int Sortable = 1 << 3;
    /**
     * Disable persisting columns order, width and sort settings in the .ini file.
     */
    public static final int NoSavedSettings = 1 << 4;
    /**
     * Right-click on columns body/contents will display table context menu. By default it is available in TableHeadersRow().
     */
    public static final int ContextMenuInBody = 1 << 5;

    // Decorations
    /**
     * Set each RowBg color with ImGuiCol_TableRowBg or ImGuiCol_TableRowBgAlt (equivalent of calling TableSetBgColor with ImGuiTableBgFlags_RowBg0 on each row manually)
     */
    public static final int RowBg = 1 << 6;
    /**
     * Draw horizontal borders between rows.
     */
    public static final int BordersInnerH = 1 << 7;
    /**
     * Draw horizontal borders at the top and bottom.
     */
    public static final int BordersOuterH = 1 << 8;
    /**
     * Draw vertical borders between columns.
     */
    public static final int BordersInnerV = 1 << 9;
    /**
     * Draw vertical borders on the left and right sides.
     */
    public static final int BordersOuterV = 1 << 10;
    /**
     * Draw horizontal borders.
     */
    public static final int BordersH = BordersInnerH | BordersOuterH;
    /**
     * Draw vertical borders.
     */
    public static final int BordersV = BordersInnerV | BordersOuterV;
    /**
     * Draw inner borders.
     */
    public static final int BordersInner = BordersInnerV | BordersInnerH;
    /**
     * Draw outer borders.
     */
    public static final int BordersOuter = BordersOuterV | BordersOuterH;
    /**
     * Draw all borders.
     */
    public static final int Borders = BordersInner | BordersOuter;
    /**
     * [ALPHA] Disable vertical borders in columns Body (borders will always appears in Headers). {@code ->} May move to style
     */
    public static final int NoBordersInBody = 1 << 11;
    /**
     * [ALPHA] Disable vertical borders in columns Body until hovered for resize (borders will always appears in Headers). {@code ->} May move to style
     */
    public static final int NoBordersInBodyUntilResize = 1 << 12;

    // Sizing Policy (read above for defaults)
    /**
     * Columns default to _WidthFixed or _WidthAuto (if resizable or not resizable), matching contents width.
     */
    public static final int SizingFixedFit = 1 << 13;
    /**
     * Columns default to _WidthFixed or _WidthAuto (if resizable or not resizable), matching the maximum contents width of all columns. Implicitly enable NoKeepColumnsVisible.
     */
    public static final int SizingFixedSame = 2 << 13;
    /**
     * Columns default to _WidthStretch with default weights proportional to each columns contents widths.
     */
    public static final int SizingStretchProp = 3 << 13;
    /**
     * Columns default to _WidthStretch with default weights all equal, unless overriden by TableSetupColumn().
     */
    public static final int SizingStretchSame = 4 << 13;

    // Sizing Extra Options
    /**
     * Make outer width auto-fit to columns, overriding outer_size.x value. Only available when ScrollX/ScrollY are disabled and Stretch columns are not used.
     */
    public static final int NoHostExtendX = 1 << 16;
    /**
     * Make outer height stop exactly at outer_size.y (prevent auto-extending table past the limit). Only available when ScrollX/ScrollY are disabled. Data below the limit will be clipped and not visible.
     */
    public static final int NoHostExtendY = 1 << 17;
    /**
     * Disable keeping column always minimally visible when ScrollX is off and table gets too small. Not recommended if columns are resizable.
     */
    public static final int NoKeepColumnsVisible = 1 << 18;
    /**
     * Disable distributing remainder width to stretched columns (width allocation on a 100-wide table with 3 columns: Without this flag: 33,33,34. With this flag: 33,33,33).
     * With larger number of columns, resizing will appear to be less smooth.
     */
    public static final int PreciseWidths = 1 << 19;

    // Clipping
    /**
     * Disable clipping rectangle for every individual columns (reduce draw command count, items will be able to overflow into other columns). Generally incompatible with TableSetupScrollFreeze().
     */
    public static final int NoClip = 1 << 20;

    // Padding
    /**
     * Default if BordersOuterV is on. Enable outer-most padding. Generally desirable if you have headers.
     */
    public static final int PadOuterX = 1 << 21;
    /**
     * Default if BordersOuterV is off. Disable outer-most padding.
     */
    public static final int NoPadOuterX = 1 << 22;

    public static final int NoPadInnerX = 1 << 23;

    // Scrolling
    /**
     * Enable horizontal scrolling. Require 'outer_size' parameter of BeginTable() to specify the container size. Changes default sizing policy.
     * Because this create a child window, ScrollY is currently generally recommended when using ScrollX.
     */
    public static final int ScrollX = 1 << 24;
    /**
     * Enable vertical scrolling. Require 'outer_size' parameter of BeginTable() to specify the container size.
     */
    public static final int ScrollY = 1 << 25;

    // Sorting
    /**
     * Hold shift when clicking headers to sort on multiple column. TableGetSortSpecs() may return specs where (SpecsCount {@code >} 1).
     */
    public static final int SortMulti = 1 << 26;
    /**
     * Allow no sorting, disable default sorting. TableGetSortSpecs() may return specs where (SpecsCount == 0).
     */
    public static final int SortTristate = 1 << 27;
}
