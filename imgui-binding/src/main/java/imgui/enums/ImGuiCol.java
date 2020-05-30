package imgui.enums;

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
    public static final int TitleBg = 10;
    public static final int TitleBgActive = 11;
    public static final int TitleBgCollapsed = 12;
    public static final int MenuBarBg = 13;
    public static final int ScrollbarBg = 14;
    public static final int ScrollbarGrab = 15;
    public static final int ScrollbarGrabHovered = 16;
    public static final int ScrollbarGrabActive = 17;
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
    public static final int ResizeGrip = 30;
    public static final int ResizeGripHovered = 31;
    public static final int ResizeGripActive = 32;
    public static final int Tab = 33;
    public static final int TabHovered = 34;
    public static final int TabActive = 35;
    public static final int TabUnfocused = 36;
    public static final int TabUnfocusedActive = 37;
    /**
     * Preview overlay color when about to docking something
     */
    public static final int DockingPreview = 38;
    /**
     * Background color for empty node (e.g. CentralNode with no window docked into it)
     */
    public static final int DockingEmptyBg = 39;
    public static final int PlotLines = 40;
    public static final int PlotLinesHovered = 41;
    public static final int PlotHistogram = 42;
    public static final int PlotHistogramHovered = 43;
    public static final int TextSelectedBg = 44;
    public static final int DragDropTarget = 45;
    /**
     * Gamepad/keyboard: current highlighted item
     */
    public static final int NavHighlight = 46;
    /**
     * Highlight window when using CTRL+TAB
     */
    public static final int NavWindowingHighlight = 47;
    /**
     * Darken/colorize entire screen behind the CTRL+TAB window list, when active
     */
    public static final int NavWindowingDimBg = 48;
    /**
     * Darken/colorize entire screen behind a modal window, when one is active
     */
    public static final int ModalWindowDimBg = 49;
    public static final int COUNT = 49;
}
