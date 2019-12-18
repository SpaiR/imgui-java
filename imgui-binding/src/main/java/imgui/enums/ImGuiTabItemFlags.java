package imgui.enums;

/**
 * Flags for ImGui::BeginTabItem()
 */
public class ImGuiTabItemFlags {
    public static ImGuiTabItemFlags None = new ImGuiTabItemFlags(0);
    public static ImGuiTabItemFlags UnsavedDocument = new ImGuiTabItemFlags(1 << 0);
    public static ImGuiTabItemFlags SetSelected = new ImGuiTabItemFlags(1 << 1);
    public static ImGuiTabItemFlags NoCloseWithMiddleMouseButton = new ImGuiTabItemFlags(1 << 2);
    public static ImGuiTabItemFlags NoPushId = new ImGuiTabItemFlags(1 << 3);
    private static ImGuiTabItemFlags Custom = new ImGuiTabItemFlags(0);
    int value;

    private ImGuiTabItemFlags(int code) {
        value = code;
    }

    public ImGuiTabItemFlags or(ImGuiTabItemFlags otherEnum) {
        ImGuiTabItemFlags.Custom.value = value | otherEnum.value;
        return ImGuiTabItemFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
