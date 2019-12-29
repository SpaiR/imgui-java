package imgui.enums;

/**
 * Flags for ImGui::Selectable()
 */
public final class ImGuiSelectableFlags {
    private ImGuiSelectableFlags() {
    }

    public static final int None = 0;
    public static final int DontClosePopups = 1;       // Clicking this don't close parent popup window
    public static final int SpanAllColumns = 1 << 1;   // Selectable frame can span all columns (text will still fit in current column)
    public static final int AllowDoubleClick = 1 << 2; // Generate press events on double clicks too
    public static final int Disabled = 1 << 3;         // Cannot be selected, display grayed out text
    public static final int AllowItemOverlap = 1 << 4; // (WIP) Hit testing to allow subsequent widgets to overlap this one
}
