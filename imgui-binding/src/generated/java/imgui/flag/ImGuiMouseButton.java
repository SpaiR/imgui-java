package imgui.flag;




/**
 * Identify a mouse button.
 * Those values are guaranteed to be stable and we frequently use 0/1 directly. Named enums provided for convenience.
 */

public final class ImGuiMouseButton {
    private ImGuiMouseButton() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int Left = 0;

    /**
     * Definition: {@code 1}
     */
    public static final int Right = 1;

    /**
     * Definition: {@code 2}
     */
    public static final int Middle = 2;

    /**
     * Definition: {@code 5}
     */
    public static final int COUNT = 5;
}
