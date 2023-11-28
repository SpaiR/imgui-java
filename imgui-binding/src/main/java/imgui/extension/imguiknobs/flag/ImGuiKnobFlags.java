package imgui.extension.imguiknobs.flag;

public final class ImGuiKnobFlags {

    private ImGuiKnobFlags() {
        throw new UnsupportedOperationException();
    }

    public static final int none = 0;
    public static final int noTitle = 1;
    public static final int noInput = 1 << 1;
    public static final int valueTooltip = 1 << 2;
    public static final int dragHorizontal = 1 << 3;

}
