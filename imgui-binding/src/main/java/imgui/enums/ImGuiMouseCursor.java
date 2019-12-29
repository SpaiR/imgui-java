package imgui.enums;

/**
 * Enumeration for GetMouseCursor()
 * User code may request binding to display given cursor by calling SetMouseCursor(), which is why we have some cursors that are marked unused here
 */
public final class ImGuiMouseCursor {
    private ImGuiMouseCursor() {
    }

    public static final int None = -1;
    public static final int Arrow = 0;
    public static final int TextInput = 1;   // When hovering over InputText, etc.
    public static final int ResizeAll = 2;   // (Unused by Dear ImGui functions)
    public static final int ResizeNS = 3;    // When hovering over an horizontal border
    public static final int ResizeEW = 4;    // When hovering over a vertical border or a column
    public static final int ResizeNESW = 5;  // When hovering over the bottom-left corner of a window
    public static final int ResizeNWSE = 6;  // When hovering over the bottom-right corner of a window
    public static final int Hand = 7;        // (Unused by Dear ImGui functions. Use for e.g. hyperlinks)
    public static final int COUNT = 8;
}
