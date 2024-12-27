package imgui.flag;


/**
 * To test io.KeyMods (which is a combination of individual fields io.KeyCtrl, io.KeyShift, io.KeyAlt set by user/backend)
 */
public final class ImGuiModFlags {
    private ImGuiModFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Definition: {@code 1 << 0}
     */
    public static final int Ctrl = 1;

    /**
     * Definition: {@code 1 << 1}
     */
    public static final int Shift = 2;

    /**
     * Menu
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int Alt = 4;

    /**
     * Cmd/Super/Windows key
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int Super = 8;
}
