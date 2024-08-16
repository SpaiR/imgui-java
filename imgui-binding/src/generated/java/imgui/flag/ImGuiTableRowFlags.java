package imgui.flag;


/**
 * Flags for {@link imgui.ImGui#tableNextRow(int)}
 */
public final class ImGuiTableRowFlags {
    private ImGuiTableRowFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Identify header row (set default background color + width of its contents accounted differently for auto column width)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Headers = 1;
}
