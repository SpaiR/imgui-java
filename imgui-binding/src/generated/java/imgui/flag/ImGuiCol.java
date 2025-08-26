package imgui.flag;


/**
 * Enumeration for PushStyleColor() / PopStyleColor()
 */
public final class ImGuiCol {
    private ImGuiCol() {
    }

    public static final int Text = 0;

    public static final int TextDisabled = 1;

    /**
     * Background of normal windows
     */
    public static final int WindowBg = 2;

    /**
     * Background of child windows
     */
    public static final int ChildBg = 3;

    /**
     * Background of popups, menus, tooltips windows
     */
    public static final int PopupBg = 4;

    public static final int Border = 5;

    public static final int BorderShadow = 6;

    /**
     * Background of checkbox, radio button, plot, slider, text input
     */
    public static final int FrameBg = 7;

    public static final int FrameBgHovered = 8;

    public static final int FrameBgActive = 9;

    /**
     * Title bar
     */
    public static final int TitleBg = 10;

    /**
     * Title bar when focused
     */
    public static final int TitleBgActive = 11;

    /**
     * Title bar when collapsed
     */
    public static final int TitleBgCollapsed = 12;

    public static final int MenuBarBg = 13;

    public static final int ScrollbarBg = 14;

    public static final int ScrollbarGrab = 15;

    public static final int ScrollbarGrabHovered = 16;

    public static final int ScrollbarGrabActive = 17;

    /**
     * Checkbox tick and RadioButton circle
     */
    public static final int CheckMark = 18;

    public static final int SliderGrab = 19;

    public static final int SliderGrabActive = 20;

    public static final int Button = 21;

    public static final int ButtonHovered = 22;

    public static final int ButtonActive = 23;

    /**
     * Header* colors are used for CollapsingHeader, TreeNode, Selectable, MenuItem
     */
    public static final int Header = 24;

    public static final int HeaderHovered = 25;

    public static final int HeaderActive = 26;

    public static final int Separator = 27;

    public static final int SeparatorHovered = 28;

    public static final int SeparatorActive = 29;

    /**
     * Resize grip in lower-right and lower-left corners of windows.
     */
    public static final int ResizeGrip = 30;

    public static final int ResizeGripHovered = 31;

    public static final int ResizeGripActive = 32;

    /**
     * Tab background, when hovered
     */
    public static final int TabHovered = 33;

    /**
     * Tab background, when tab-bar is focused {@code &} tab is unselected
     */
    public static final int Tab = 34;

    /**
     * Tab background, when tab-bar is focused {@code &} tab is selected
     */
    public static final int TabSelected = 35;

    /**
     * Tab horizontal overline, when tab-bar is focused {@code &} tab is selected
     */
    public static final int TabSelectedOverline = 36;

    /**
     * Tab background, when tab-bar is unfocused {@code &} tab is unselected
     */
    public static final int TabDimmed = 37;

    /**
     * Tab background, when tab-bar is unfocused {@code &} tab is selected
     */
    public static final int TabDimmedSelected = 38;

    /**
     * ..horizontal overline, when tab-bar is unfocused {@code &} tab is selected
     */
    public static final int TabDimmedSelectedOverline = 39;

    /**
     * Preview overlay color when about to docking something
     */
    public static final int DockingPreview = 40;

    /**
     * Background color for empty node (e.g. CentralNode with no window docked into it)
     */
    public static final int DockingEmptyBg = 41;

    public static final int PlotLines = 42;

    public static final int PlotLinesHovered = 43;

    public static final int PlotHistogram = 44;

    public static final int PlotHistogramHovered = 45;

    /**
     * Table header background
     */
    public static final int TableHeaderBg = 46;

    /**
     * Table outer and header borders (prefer using Alpha=1.0 here)
     */
    public static final int TableBorderStrong = 47;

    /**
     * Table inner borders (prefer using Alpha=1.0 here)
     */
    public static final int TableBorderLight = 48;

    /**
     * Table row background (even rows)
     */
    public static final int TableRowBg = 49;

    /**
     * Table row background (odd rows)
     */
    public static final int TableRowBgAlt = 50;

    public static final int TextSelectedBg = 51;

    /**
     * Rectangle highlighting a drop target
     */
    public static final int DragDropTarget = 52;

    /**
     * Gamepad/keyboard: current highlighted item
     */
    public static final int NavHighlight = 53;

    /**
     * Highlight window when using CTRL+TAB
     */
    public static final int NavWindowingHighlight = 54;

    /**
     * Darken/colorize entire screen behind the CTRL+TAB window list, when active
     */
    public static final int NavWindowingDimBg = 55;

    /**
     * Darken/colorize entire screen behind a modal window, when one is active
     */
    public static final int ModalWindowDimBg = 56;

    public static final int COUNT = 57;

    /**
     * [renamed in 1.90.9]
     *
     * <p>Definition: {@code ImGuiCol_TabSelected}
     */
    public static final int TabActive = 35;

    /**
     * [renamed in 1.90.9]
     *
     * <p>Definition: {@code ImGuiCol_TabDimmed}
     */
    public static final int TabUnfocused = 37;

    /**
     * [renamed in 1.90.9]
     *
     * <p>Definition: {@code ImGuiCol_TabDimmedSelected}
     */
    public static final int TabUnfocusedActive = 38;
}
