package imgui.flag;

/**
 * Flags for {@link imgui.ImGui#tableNextRow(int)}
 */
public final class ImGuiTableRowFlags {
    private ImGuiTableRowFlags() {
    }

    public static final int None = 0;
    /**
     * Identify header row (set default background color + width of its contents accounted different for auto column width)
     */
    public static final int Headers = 1;
}
