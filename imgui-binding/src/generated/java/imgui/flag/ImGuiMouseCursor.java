package imgui.flag;


/**
 * Enumeration for GetMouseCursor()
 * User code may request binding to display given cursor by calling SetMouseCursor(), which is why we have some cursors that are marked unused here
 */
public final class ImGuiMouseCursor {
    private ImGuiMouseCursor() {
    }

    /**
     * Definition: {@code -1}
     */
    public static final int None = -1;

    /**
     * Definition: {@code 0}
     */
    public static final int Arrow = 0;

    /**
     * When hovering over InputText, etc.
     */
    public static final int TextInput = 1;

    /**
     * (Unused by Dear ImGui functions)
     */
    public static final int ResizeAll = 2;

    /**
     * When hovering over a horizontal border
     */
    public static final int ResizeNS = 3;

    /**
     * When hovering over a vertical border or a column
     */
    public static final int ResizeEW = 4;

    /**
     * When hovering over the bottom-left corner of a window
     */
    public static final int ResizeNESW = 5;

    /**
     * When hovering over the bottom-right corner of a window
     */
    public static final int ResizeNWSE = 6;

    /**
     * (Unused by Dear ImGui functions. Use for e.g. hyperlinks)
     */
    public static final int Hand = 7;

    /**
     * When hovering something with disallowed interaction. Usually a crossed circle.
     */
    public static final int NotAllowed = 8;

    public static final int COUNT = 9;
}
