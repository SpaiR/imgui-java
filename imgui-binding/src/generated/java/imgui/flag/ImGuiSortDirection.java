package imgui.flag;


/**
 * A sorting direction
 */
public final class ImGuiSortDirection {
    private ImGuiSortDirection() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Ascending = 0{@code ->}9, A{@code ->}Z etc.
     *
     * <p>Definition: {@code 1}
     */
    public static final int Ascending = 1;

    /**
     * Descending = 9{@code ->}0, Z{@code ->}A etc.
     *
     * <p>Definition: {@code 2}
     */
    public static final int Descending = 2;
}
