package imgui.enums;

/**
 * Flags for ImGui::IsItemHovered(), ImGui::IsWindowHovered()
 * Note: if you are trying to check whether your mouse should be dispatched to imgui or to your app, you should use the 'io.WantCaptureMouse' boolean for that. Please read the FAQ!
 * Note: windows with the ImGuiWindowFlags_NoInputs flag are ignored by IsWindowHovered() calls.
 */
public class ImGuiDragDropFlags {
    public static ImGuiDragDropFlags None = new ImGuiDragDropFlags(0);
    // BeginDragDropSource() flags
    public static ImGuiDragDropFlags SourceNoPreviewTooltip = new ImGuiDragDropFlags(1 << 0);
    public static ImGuiDragDropFlags SourceNoDisableHover = new ImGuiDragDropFlags(1 << 1);
    public static ImGuiDragDropFlags SourceNoHoldToOpenOthers = new ImGuiDragDropFlags(1 << 2);
    public static ImGuiDragDropFlags SourceAllowNullID = new ImGuiDragDropFlags(1 << 3);
    public static ImGuiDragDropFlags SourceExtern = new ImGuiDragDropFlags(1 << 4);
    public static ImGuiDragDropFlags SourceAutoExpirePayload = new ImGuiDragDropFlags(1 << 5);
    // AcceptDragDropPayload() flags
    public static ImGuiDragDropFlags AcceptBeforeDelivery = new ImGuiDragDropFlags(1 << 10);
    public static ImGuiDragDropFlags AcceptNoDrawDefaultRect = new ImGuiDragDropFlags(1 << 11);
    public static ImGuiDragDropFlags AcceptNoPreviewTooltip = new ImGuiDragDropFlags(1 << 12);
    public static ImGuiDragDropFlags AcceptPeekOnly = new ImGuiDragDropFlags(AcceptBeforeDelivery.getValue() | AcceptNoDrawDefaultRect.getValue());
    private static ImGuiDragDropFlags Custom = new ImGuiDragDropFlags(0);
    int value;

    private ImGuiDragDropFlags(int code) {
        value = code;
    }

    public ImGuiDragDropFlags or(ImGuiDragDropFlags otherEnum) {
        ImGuiDragDropFlags.Custom.value = value | otherEnum.value;
        return ImGuiDragDropFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
