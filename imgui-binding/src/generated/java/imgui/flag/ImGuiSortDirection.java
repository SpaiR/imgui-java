package imgui.flag;

/**
 * A sorting direction
 */
public final class ImGuiSortDirection {
    private ImGuiSortDirection() {
    }

    public static final int None = 0;
    /**
     * Ascending = {@code 0->9, A->Z} etc.
     */
    public static final int Ascending = 1;
    /**
     * Descending = {@code 9->0, Z->A} etc.
     */
    public static final int Descending = 2;
}
