package imgui.enums;

public class ImDrawCornerFlags {
    public static ImDrawCornerFlags TopLeft = new ImDrawCornerFlags(1 << 0);
    public static ImDrawCornerFlags TopRight = new ImDrawCornerFlags(1 << 1);
    public static ImDrawCornerFlags BotLeft = new ImDrawCornerFlags(1 << 2);
    public static ImDrawCornerFlags BotRight = new ImDrawCornerFlags(1 << 3);
    public static ImDrawCornerFlags Top = new ImDrawCornerFlags(TopLeft.getValue() | TopRight.getValue());
    public static ImDrawCornerFlags Bot = new ImDrawCornerFlags(BotLeft.getValue() | BotRight.getValue());
    public static ImDrawCornerFlags Left = new ImDrawCornerFlags(TopLeft.getValue() | BotLeft.getValue());
    public static ImDrawCornerFlags Right = new ImDrawCornerFlags(TopRight.getValue() | BotRight.getValue());
    public static ImDrawCornerFlags All = new ImDrawCornerFlags(0xF);
    private static ImDrawCornerFlags Custom = new ImDrawCornerFlags(0);
    int value;

    private ImDrawCornerFlags(int code) {
        value = code;
    }

    public ImDrawCornerFlags or(ImDrawCornerFlags otherEnum) {
        ImDrawCornerFlags.Custom.value = value | otherEnum.value;
        return ImDrawCornerFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
