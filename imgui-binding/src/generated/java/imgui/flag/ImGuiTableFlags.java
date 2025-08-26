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

    /**
     * Features
     *
     * <p>Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Enable resizing columns.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Resizable = 1;

    /**
     * Enable reordering columns in header row (need calling TableSetupColumn() + TableHeadersRow() to display headers)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int Reorderable = 2;

    /**
     * Enable hiding/disabling columns in context menu.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int Hideable = 4;

    /**
     * Enable sorting. Call TableGetSortSpecs() to obtain sort specs. Also see ImGuiTableFlags_SortMulti and ImGuiTableFlags_SortTristate.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int Sortable = 8;

    /**
     * Disable persisting columns order, width and sort settings in the .ini file.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoSavedSettings = 16;

    /**
     * Right-click on columns body/contents will display table context menu. By default it is available in TableHeadersRow().
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int ContextMenuInBody = 32;

    /**
     * Set each RowBg color with ImGuiCol_TableRowBg or ImGuiCol_TableRowBgAlt (equivalent of calling TableSetBgColor with ImGuiTableBgFlags_RowBg0 on each row manually)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int RowBg = 64;

    /**
     * Draw horizontal borders between rows.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int BordersInnerH = 128;

    /**
     * Draw horizontal borders at the top and bottom.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int BordersOuterH = 256;

    /**
     * Draw vertical borders between columns.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int BordersInnerV = 512;

    /**
     * Draw vertical borders on the left and right sides.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int BordersOuterV = 1024;

    /**
     * Draw horizontal borders.
     *
     * <p>Definition: {@code ImGuiTableFlags_BordersInnerH | ImGuiTableFlags_BordersOuterH}
     */
    public static final int BordersH = 384;

    /**
     * Draw vertical borders.
     *
     * <p>Definition: {@code ImGuiTableFlags_BordersInnerV | ImGuiTableFlags_BordersOuterV}
     */
    public static final int BordersV = 1536;

    /**
     * Draw inner borders.
     *
     * <p>Definition: {@code ImGuiTableFlags_BordersInnerV | ImGuiTableFlags_BordersInnerH}
     */
    public static final int BordersInner = 640;

    /**
     * Draw outer borders.
     *
     * <p>Definition: {@code ImGuiTableFlags_BordersOuterV | ImGuiTableFlags_BordersOuterH}
     */
    public static final int BordersOuter = 1280;

    /**
     * Draw all borders.
     *
     * <p>Definition: {@code ImGuiTableFlags_BordersInner | ImGuiTableFlags_BordersOuter}
     */
    public static final int Borders = 1920;

    /**
     * [ALPHA] Disable vertical borders in columns Body (borders will always appear in Headers). {@code ->} May move to style
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int NoBordersInBody = 2048;

    /**
     * [ALPHA] Disable vertical borders in columns Body until hovered for resize (borders will always appear in Headers). {@code ->} May move to style
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int NoBordersInBodyUntilResize = 4096;

    /**
     * Columns default to _WidthFixed or _WidthAuto (if resizable or not resizable), matching contents width.
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int SizingFixedFit = 8192;

    /**
     * Columns default to _WidthFixed or _WidthAuto (if resizable or not resizable), matching the maximum contents width of all columns. Implicitly enable ImGuiTableFlags_NoKeepColumnsVisible.
     *
     * <p>Definition: {@code 2 << 13}
     */
    public static final int SizingFixedSame = 16384;

    /**
     * Columns default to _WidthStretch with default weights proportional to each columns contents widths.
     *
     * <p>Definition: {@code 3 << 13}
     */
    public static final int SizingStretchProp = 24576;

    /**
     * Columns default to _WidthStretch with default weights all equal, unless overridden by TableSetupColumn().
     *
     * <p>Definition: {@code 4 << 13}
     */
    public static final int SizingStretchSame = 32768;

    /**
     * Make outer width auto-fit to columns, overriding outer_size.x value. Only available when ScrollX/ScrollY are disabled and Stretch columns are not used.
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int NoHostExtendX = 65536;

    /**
     * Make outer height stop exactly at outer_size.y (prevent auto-extending table past the limit). Only available when ScrollX/ScrollY are disabled. Data below the limit will be clipped and not visible.
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NoHostExtendY = 131072;

    /**
     * Disable keeping column always minimally visible when ScrollX is off and table gets too small. Not recommended if columns are resizable.
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int NoKeepColumnsVisible = 262144;

    /**
     * Disable distributing remainder width to stretched columns (width allocation on a 100-wide table with 3 columns: Without this flag: 33,33,34. With this flag: 33,33,33). With larger number of columns, resizing will appear to be less smooth.
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int PreciseWidths = 524288;

    /**
     * Disable clipping rectangle for every individual columns (reduce draw command count, items will be able to overflow into other columns). Generally incompatible with TableSetupScrollFreeze().
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int NoClip = 1048576;

    /**
     * Default if BordersOuterV is on. Enable outermost padding. Generally desirable if you have headers.
     *
     * <p>Definition: {@code 1 << 21}
     */
    public static final int PadOuterX = 2097152;

    /**
     * Default if BordersOuterV is off. Disable outermost padding.
     *
     * <p>Definition: {@code 1 << 22}
     */
    public static final int NoPadOuterX = 4194304;

    /**
     * Disable inner padding between columns (double inner padding if BordersOuterV is on, single inner padding if BordersOuterV is off).
     *
     * <p>Definition: {@code 1 << 23}
     */
    public static final int NoPadInnerX = 8388608;

    /**
     * Enable horizontal scrolling. Require 'outer_size' parameter of BeginTable() to specify the container size. Changes default sizing policy. Because this creates a child window, ScrollY is currently generally recommended when using ScrollX.
     *
     * <p>Definition: {@code 1 << 24}
     */
    public static final int ScrollX = 16777216;

    /**
     * Enable vertical scrolling. Require 'outer_size' parameter of BeginTable() to specify the container size.
     *
     * <p>Definition: {@code 1 << 25}
     */
    public static final int ScrollY = 33554432;

    /**
     * Hold shift when clicking headers to sort on multiple column. TableGetSortSpecs() may return specs where (SpecsCount{@code >}1).
     *
     * <p>Definition: {@code 1 << 26}
     */
    public static final int SortMulti = 67108864;

    /**
     * Allow no sorting, disable default sorting. TableGetSortSpecs() may return specs where (SpecsCount == 0).
     *
     * <p>Definition: {@code 1 << 27}
     */
    public static final int SortTristate = 134217728;

    /**
     * Highlight column headers when hovered (may evolve into a fuller highlight)
     *
     * <p>Definition: {@code 1 << 28}
     */
    public static final int HighlightHoveredColumn = 268435456;

    /**
     * [Internal] Combinations and masks
     *
     * <p>Definition: {@code ImGuiTableFlags_SizingFixedFit | ImGuiTableFlags_SizingFixedSame | ImGuiTableFlags_SizingStretchProp | ImGuiTableFlags_SizingStretchSame}
     */
    public static final int SizingMask_ = 57344;
}
