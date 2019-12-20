package imgui.enums;

/**
 * Flags for ImGui::IsItemHovered(), ImGui::IsWindowHovered()
 * Note: if you are trying to check whether your mouse should be dispatched to imgui or to your app, you should use the 'io.WantCaptureMouse' boolean for that. Please read the FAQ!
 * Note: windows with the ImGuiWindowFlags_NoInputs flag are ignored by IsWindowHovered() calls.
 */
public class ImGuiHoveredFlags {
    public static ImGuiHoveredFlags None = new ImGuiHoveredFlags(0);
    public static ImGuiHoveredFlags ChildWindows = new ImGuiHoveredFlags(1 << 0);
    public static ImGuiHoveredFlags RootWindow = new ImGuiHoveredFlags(1 << 1);
    public static ImGuiHoveredFlags AnyWindow = new ImGuiHoveredFlags(1 << 2);
    public static ImGuiHoveredFlags AllowWhenBlockedByPopup = new ImGuiHoveredFlags(1 << 3);
    //    public static ImGuiHoveredFlags AllowWhenBlockedByModal = new ImGuiHoveredFlags(1 << 4);
    public static ImGuiHoveredFlags AllowWhenBlockedByActiveItem = new ImGuiHoveredFlags(1 << 5);
    public static ImGuiHoveredFlags AllowWhenOverlapped = new ImGuiHoveredFlags(1 << 6);
    public static ImGuiHoveredFlags AllowWhenDisabled = new ImGuiHoveredFlags(1 << 7);
    public static ImGuiHoveredFlags RectOnly = new ImGuiHoveredFlags(AllowWhenBlockedByPopup.getValue() | AllowWhenBlockedByActiveItem.getValue() | AllowWhenOverlapped.getValue());
    public static ImGuiHoveredFlags RootAndChildWindows = new ImGuiHoveredFlags(RootWindow.getValue() | ChildWindows.getValue());
    private static ImGuiHoveredFlags Custom = new ImGuiHoveredFlags(0);
    int value;

    private ImGuiHoveredFlags(int code) {
        value = code;
    }

    public ImGuiHoveredFlags or(ImGuiHoveredFlags otherEnum) {
        ImGuiHoveredFlags.Custom.value = value | otherEnum.value;
        return ImGuiHoveredFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
