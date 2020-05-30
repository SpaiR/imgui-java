package imgui.enums;

/**
 * Flags for ImGui::Begin()
 */
public final class ImGuiWindowFlags {
    private ImGuiWindowFlags() {
    }

    public static final int None = 0;
    public static final int NoTitleBar = 1;
    /**
     * Disable user resizing with the lower-right grip
     */
    public static final int NoResize = 1 << 1;
    /**
     * Disable user moving the window
     */
    public static final int NoMove = 1 << 2;
    /**
     * Disable scrollbars (window can still scroll with mouse or programmatically)
     */
    public static final int NoScrollbar = 1 << 3;
    /**
     * Disable user vertically scrolling with mouse wheel. On child window, mouse wheel will be forwarded to the parent unless NoScrollbar is also set.
     */
    public static final int NoScrollWithMouse = 1 << 4;
    /**
     * Disable user collapsing window by double-clicking on it
     */
    public static final int NoCollapse = 1 << 5;
    /**
     * Resize every window to its content every frame
     */
    public static final int AlwaysAutoResize = 1 << 6;
    /**
     * Disable drawing background color (WindowBg, etc.) and outside border. Similar as using SetNextWindowBgAlpha(0.0f).
     */
    public static final int NoBackground = 1 << 7;
    /**
     * Never load/save settings in .ini file
     */
    public static final int NoSavedSettings = 1 << 8;
    /**
     * Disable catching mouse, hovering test with pass through.
     */
    public static final int NoMouseInputs = 1 << 9;
    /**
     * Has a menu-bar
     */
    public static final int MenuBar = 1 << 10;
    /**
     * Allow horizontal scrollbar to appear (off by default). You may use SetNextWindowContentSize(ImVec2(width,0.0f)); prior to calling Begin() to specify width. Read code in imgui_demo in the "Horizontal Scrolling" section.
     */
    public static final int HorizontalScrollbar = 1 << 11;
    /**
     * Disable taking focus when transitioning from hidden to visible state
     */
    public static final int NoFocusOnAppearing = 1 << 12;
    /**
     * Disable bringing window to front when taking focus (e.g. clicking on it or programmatically giving it focus)
     */
    public static final int NoBringToFrontOnFocus = 1 << 13;
    /**
     * Always show vertical scrollbar (even if ContentSize.y {@code <} Size.y)
     */
    public static final int AlwaysVerticalScrollbar = 1 << 14;
    /**
     * Always show horizontal scrollbar (even if ContentSize.x {@code <} Size.x)
     */
    public static final int AlwaysHorizontalScrollbar = 1 << 15;
    /**
     * Ensure child windows without border uses style.WindowPadding (ignored by default for non-bordered child windows, because more convenient)
     */
    public static final int AlwaysUseWindowPadding = 1 << 16;
    /**
     * No gamepad/keyboard navigation within the window
     */
    public static final int NoNavInputs = 1 << 17;
    /**
     * No focusing toward this window with gamepad/keyboard navigation (e.g. skipped by CTRL+TAB)
     */
    public static final int NoNavFocus = 1 << 18;
    /**
     * Append '*' to title without affecting the ID, as a convenience to avoid using the ### operator. When used in a tab/docking context, tab is selected on closure and closure is deferred by one frame to allow code to cancel the closure (with a confirmation popup, etc.) without flicker.
     */
    public static final int UnsavedDocument = 1 << 20;
    /**
     * Disable docking of this window
     */
    public static final int NoDocking = 1 << 21;
    public static final int NoNav = NoNavInputs | NoNavFocus;
    public static final int NoDecoration = NoTitleBar | NoResize | NoScrollbar | NoCollapse;
    public static final int NoInputs = NoMouseInputs | NoNavInputs | NoNavFocus;
}
