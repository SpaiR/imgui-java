package imgui.extension.imguiknobs.flag;

public final class ImGuiKnobFlags {

    private ImGuiKnobFlags() {
    }

    public static final int NONE = 0;
    public static final int NO_TITLE = 1;
    public static final int NO_INPUT = 1 << 1;
    public static final int VALUE_TOOLTIP = 1 << 2;
    public static final int DRAG_HORIZONTAL = 1 << 3;

}
