package imgui.flag;




/**
 * Flags for ImGui::Selectable()
 */

public final class ImGuiSelectableFlags {
    private ImGuiSelectableFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Clicking this don't close parent popup window
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int DontClosePopups = 1;

    /**
     * Selectable frame can span all columns (text will still fit in current column)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int SpanAllColumns = 2;

    /**
     * Generate press events on double clicks too
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AllowDoubleClick = 4;

    /**
     * Cannot be selected, display grayed out text
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int Disabled = 8;

    /**
     * (WIP) Hit testing to allow subsequent widgets to overlap this one
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int AllowItemOverlap = 16;
}
