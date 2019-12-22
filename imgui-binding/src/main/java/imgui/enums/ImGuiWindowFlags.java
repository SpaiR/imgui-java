package imgui.enums;

/**
 * Flags for ImGui::Begin()
 */
public final class ImGuiWindowFlags {
    public static ImGuiWindowFlags None = new ImGuiWindowFlags(0);
    public static ImGuiWindowFlags NoTitleBar = new ImGuiWindowFlags(1 << 0);
    public static ImGuiWindowFlags NoResize = new ImGuiWindowFlags(1 << 1);
    public static ImGuiWindowFlags NoMove = new ImGuiWindowFlags(1 << 2);
    public static ImGuiWindowFlags NoScrollbar = new ImGuiWindowFlags(1 << 3);
    public static ImGuiWindowFlags NoScrollWithMouse = new ImGuiWindowFlags(1 << 4);
    public static ImGuiWindowFlags NoCollapse = new ImGuiWindowFlags(1 << 5);
    public static ImGuiWindowFlags AlwaysAutoResize = new ImGuiWindowFlags(1 << 6);
    public static ImGuiWindowFlags NoBackground = new ImGuiWindowFlags(1 << 7);
    public static ImGuiWindowFlags NoSavedSettings = new ImGuiWindowFlags(1 << 8);
    public static ImGuiWindowFlags NoMouseInputs = new ImGuiWindowFlags(1 << 9);
    public static ImGuiWindowFlags MenuBar = new ImGuiWindowFlags(1 << 10);
    public static ImGuiWindowFlags HorizontalScrollbar = new ImGuiWindowFlags(1 << 11);
    public static ImGuiWindowFlags NoFocusOnAppearing = new ImGuiWindowFlags(1 << 12);
    public static ImGuiWindowFlags NoBringToFrontOnFocus = new ImGuiWindowFlags(1 << 13);
    public static ImGuiWindowFlags AlwaysVerticalScrollbar = new ImGuiWindowFlags(1 << 14);
    public static ImGuiWindowFlags AlwaysHorizontalScrollbar = new ImGuiWindowFlags(1 << 15);
    public static ImGuiWindowFlags AlwaysUseWindowPadding = new ImGuiWindowFlags(1 << 16);
    public static ImGuiWindowFlags NoNavInputs = new ImGuiWindowFlags(1 << 18);
    public static ImGuiWindowFlags NoNavFocus = new ImGuiWindowFlags(1 << 19);
    public static ImGuiWindowFlags UnsavedDocument = new ImGuiWindowFlags(1 << 20);
    public static ImGuiWindowFlags NoDocking = new ImGuiWindowFlags(1 << 21);
    public static ImGuiWindowFlags NoNav = new ImGuiWindowFlags(NoNavInputs.getValue() | NoNavFocus.getValue());
    public static ImGuiWindowFlags NoDecoration = new ImGuiWindowFlags(NoTitleBar.getValue() | NoResize.getValue() | NoScrollbar.getValue() | NoCollapse.getValue());
    public static ImGuiWindowFlags NoInputs = new ImGuiWindowFlags(NoMouseInputs.getValue() | NoNavInputs.getValue() | NoNavFocus.getValue());
    private static ImGuiWindowFlags Custom = new ImGuiWindowFlags(0);
    int value;

    private ImGuiWindowFlags(int code) {
        value = code;
    }

    public ImGuiWindowFlags or(ImGuiWindowFlags otherEnum) {
        ImGuiWindowFlags.Custom.value = value | otherEnum.value;
        return ImGuiWindowFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
