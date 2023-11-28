package imgui.extension.imguiknobs.flag;

public final class ImGuiKnobVariant {
    private ImGuiKnobVariant() {
        throw new UnsupportedOperationException();
    }
    public static final int tick = 1;
    public static final int dot = 1 << 1;
    public static final int wiper = 1 << 2;
    public static final int wiperOnly = 1 << 3;
    public static final int wiperDot = 1 << 4;
    public static final int stepped = 1 << 5;
    public static final int space = 1 << 6;

}
