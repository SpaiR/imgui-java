package imgui.enums;

public final class ImGuiDockNodeFlags {
    public static ImGuiDockNodeFlags KeepAliveOnly = new ImGuiDockNodeFlags(1 << 0);
    public static ImGuiDockNodeFlags NoDockingInCentralNode = new ImGuiDockNodeFlags(1 << 2);
    public static ImGuiDockNodeFlags PassthruCentralNode = new ImGuiDockNodeFlags(1 << 3);
    public static ImGuiDockNodeFlags NoSplit = new ImGuiDockNodeFlags(1 << 4);
    public static ImGuiDockNodeFlags NoResize = new ImGuiDockNodeFlags(1 << 5);
    public static ImGuiDockNodeFlags AutoHideTabBar = new ImGuiDockNodeFlags(1 << 6);
    private static ImGuiDockNodeFlags Custom = new ImGuiDockNodeFlags(0);
    int value;

    private ImGuiDockNodeFlags(int code) {
        value = code;
    }

    public ImGuiDockNodeFlags or(ImGuiDockNodeFlags otherEnum) {
        ImGuiDockNodeFlags.Custom.value = value | otherEnum.value;
        return ImGuiDockNodeFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
