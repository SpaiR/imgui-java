package imgui.enums;

/**
 * Flags for ImGui::IsWindowFocused()
 */
public final class ImGuiFocusedFlags {
    public static ImGuiFocusedFlags None = new ImGuiFocusedFlags(0);
    public static ImGuiFocusedFlags ChildWindows = new ImGuiFocusedFlags(1 << 0);
    public static ImGuiFocusedFlags RootWindow = new ImGuiFocusedFlags(1 << 1);
    public static ImGuiFocusedFlags AnyWindow = new ImGuiFocusedFlags(1 << 2);
    public static ImGuiFocusedFlags RootAndChildWindows = new ImGuiFocusedFlags(RootWindow.getValue() | ChildWindows.getValue());
    private static ImGuiFocusedFlags Custom = new ImGuiFocusedFlags(0);
    int value;

    private ImGuiFocusedFlags(int code) {
        value = code;
    }

    public ImGuiFocusedFlags or(ImGuiFocusedFlags otherEnum) {
        ImGuiFocusedFlags.Custom.value = value | otherEnum.value;
        return ImGuiFocusedFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
