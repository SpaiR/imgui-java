package imgui.internal.flag;


public class ImGuiSeparatorFlags {

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Axis default to current layout type, so generally Horizontal unless e.g. in a menu bar
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Horizontal = 1;

    /**
     * Definition: {@code 1 << 1}
     */
    public static final int Vertical = 2;

    /**
     * Make separator cover all columns of a legacy Columns() set.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int SpanAllColumns = 4;
}
