package imgui.flag;

/**
 * To test io.KeyMods (which is a combination of individual fields io.KeyCtrl, io.KeyShift, io.KeyAlt set by user/backend)
 */
public final class ImGuiKeyModFlags {
    private ImGuiKeyModFlags() {
    }

    public static final int None = 0;
    public static final int Ctrl = 1;
    public static final int Shift = 1 << 1;
    public static final int Alt = 1 << 2;
    public static final int Super = 1 << 3;
}
