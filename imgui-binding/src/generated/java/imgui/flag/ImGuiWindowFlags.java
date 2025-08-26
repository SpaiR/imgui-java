package imgui.flag;


/**
 * Flags for ImGui::Begin()
 */
public final class ImGuiWindowFlags {
    private ImGuiWindowFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Disable title-bar
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoTitleBar = 1;

    /**
     * Disable user resizing with the lower-right grip
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoResize = 2;

    /**
     * Disable user moving the window
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoMove = 4;

    /**
     * Disable scrollbars (window can still scroll with mouse or programmatically)
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoScrollbar = 8;

    /**
     * Disable user vertically scrolling with mouse wheel. On child window, mouse wheel will be forwarded to the parent unless NoScrollbar is also set.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoScrollWithMouse = 16;

    /**
     * Disable user collapsing window by double-clicking on it. Also referred to as Window Menu Button (e.g. within a docking node).
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoCollapse = 32;

    /**
     * Resize every window to its content every frame
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int AlwaysAutoResize = 64;

    /**
     * Disable drawing background color (WindowBg, etc.) and outside border. Similar as using SetNextWindowBgAlpha(0.0f).
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoBackground = 128;

    /**
     * Never load/save settings in .ini file
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoSavedSettings = 256;

    /**
     * Disable catching mouse, hovering test with pass through.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int NoMouseInputs = 512;

    /**
     * Has a menu-bar
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int MenuBar = 1024;

    /**
     * Allow horizontal scrollbar to appear (off by default). You may use SetNextWindowContentSize(ImVec2(width,0.0f)); prior to calling Begin() to specify width. Read code in imgui_demo in the "Horizontal Scrolling" section.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int HorizontalScrollbar = 2048;

    /**
     * Disable taking focus when transitioning from hidden to visible state
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int NoFocusOnAppearing = 4096;

    /**
     * Disable bringing window to front when taking focus (e.g. clicking on it or programmatically giving it focus)
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int NoBringToFrontOnFocus = 8192;

    /**
     * Always show vertical scrollbar (even if ContentSize.y{@code <}Size.y)
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int AlwaysVerticalScrollbar = 16384;

    /**
     * Always show horizontal scrollbar (even if ContentSize.x{@code <}Size.x)
     *
     * <p>Definition: {@code 1<< 15}
     */
    public static final int AlwaysHorizontalScrollbar = 32768;

    /**
     * No gamepad/keyboard navigation within the window
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int NoNavInputs = 65536;

    /**
     * No focusing toward this window with gamepad/keyboard navigation (e.g. skipped by CTRL+TAB)
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int NoNavFocus = 131072;

    /**
     * Display a dot next to the title. When used in a tab/docking context, tab is selected when clicking the X + closure is not assumed (will wait for user to stop submitting the tab). Otherwise closure is assumed when pressing the X, so if you keep submitting the tab may reappear at end of tab bar.
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int UnsavedDocument = 262144;

    /**
     * Disable docking of this window
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int NoDocking = 524288;

    /**
     * Definition: {@code ImGuiWindowFlags_NoNavInputs | ImGuiWindowFlags_NoNavFocus}
     */
    public static final int NoNav = 196608;

    /**
     * Definition: {@code ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoCollapse}
     */
    public static final int NoDecoration = 43;

    /**
     * Definition: {@code ImGuiWindowFlags_NoMouseInputs | ImGuiWindowFlags_NoNavInputs | ImGuiWindowFlags_NoNavFocus}
     */
    public static final int NoInputs = 197120;

    /**
     * Don't use! For internal use by BeginChild()
     *
     * <p>Definition: {@code 1 << 24}
     */
    public static final int ChildWindow = 16777216;

    /**
     * Don't use! For internal use by BeginTooltip()
     *
     * <p>Definition: {@code 1 << 25}
     */
    public static final int Tooltip = 33554432;

    /**
     * Don't use! For internal use by BeginPopup()
     *
     * <p>Definition: {@code 1 << 26}
     */
    public static final int Popup = 67108864;

    /**
     * Don't use! For internal use by BeginPopupModal()
     *
     * <p>Definition: {@code 1 << 27}
     */
    public static final int Modal = 134217728;

    /**
     * Don't use! For internal use by BeginMenu()
     *
     * <p>Definition: {@code 1 << 28}
     */
    public static final int ChildMenu = 268435456;

    /**
     * Don't use! For internal use by Begin()/NewFrame()
     *
     * <p>Definition: {@code 1 << 29}
     */
    public static final int DockNodeHost = 536870912;

    /**
     * Obsoleted in 1.90: Use ImGuiChildFlags_AlwaysUseWindowPadding in BeginChild() call.
     *
     * <p>Definition: {@code 1 << 30}
     */
    public static final int AlwaysUseWindowPadding = 1073741824;

    /**
     * Obsoleted in 1.90.9: Use ImGuiChildFlags_NavFlattened in BeginChild() call.
     *
     * <p>Definition: {@code 1 << 31}
     */
    public static final int NavFlattened = -2147483648;
}
