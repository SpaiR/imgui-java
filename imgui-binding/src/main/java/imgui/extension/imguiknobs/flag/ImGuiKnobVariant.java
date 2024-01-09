package imgui.extension.imguiknobs.flag;

public final class ImGuiKnobVariant {
    private ImGuiKnobVariant() {
    }
    public static final int TICK = 1;
    public static final int DOT = 1 << 1;
    public static final int WIPER = 1 << 2;
    public static final int WIPER_ONLY = 1 << 3;
    public static final int WIPER_DOT = 1 << 4;
    public static final int STEPPED = 1 << 5;
    public static final int SPACE = 1 << 6;

}
