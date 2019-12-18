package imgui.enums;

/**
 * Configuration flags stored in io.ConfigFlags. Set by user/application.
 */
public class ImGuiConfigFlags {
    public static ImGuiConfigFlags None = new ImGuiConfigFlags(0);
    public static ImGuiConfigFlags NavEnableKeyboard = new ImGuiConfigFlags(1 << 0);
    public static ImGuiConfigFlags NavEnableGamepad = new ImGuiConfigFlags(1 << 1);
    public static ImGuiConfigFlags NavEnableSetMousePos = new ImGuiConfigFlags(1 << 2);
    public static ImGuiConfigFlags NavNoCaptureKeyboard = new ImGuiConfigFlags(1 << 3);
    public static ImGuiConfigFlags NoMouse = new ImGuiConfigFlags(1 << 4);
    public static ImGuiConfigFlags NoMouseCursorChange = new ImGuiConfigFlags(1 << 5);
    public static ImGuiConfigFlags DockingEnable = new ImGuiConfigFlags(1 << 6);
    public static ImGuiConfigFlags IsSRGB = new ImGuiConfigFlags(1 << 20);
    public static ImGuiConfigFlags IsTouchScreen = new ImGuiConfigFlags(1 << 21);
    private static ImGuiConfigFlags Custom = new ImGuiConfigFlags(0);
    int value;

    private ImGuiConfigFlags(int code) {
        value = code;
    }

    public ImGuiConfigFlags or(ImGuiConfigFlags otherEnum) {
        ImGuiConfigFlags.Custom.value = value | otherEnum.value;
        return ImGuiConfigFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
