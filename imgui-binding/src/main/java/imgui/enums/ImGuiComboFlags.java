package imgui.enums;

/**
 * Flags for ImGui::BeginCombo()
 */
public class ImGuiComboFlags {
    public static ImGuiComboFlags None = new ImGuiComboFlags(0);
    public static ImGuiComboFlags PopupAlignLeft = new ImGuiComboFlags(1 << 0);
    public static ImGuiComboFlags HeightSmall = new ImGuiComboFlags(1 << 1);
    public static ImGuiComboFlags HeightRegular = new ImGuiComboFlags(1 << 2);
    public static ImGuiComboFlags HeightLarge = new ImGuiComboFlags(1 << 3);
    public static ImGuiComboFlags HeightLargest = new ImGuiComboFlags(1 << 4);
    public static ImGuiComboFlags NoArrowButton = new ImGuiComboFlags(1 << 5);
    public static ImGuiComboFlags NoPreview = new ImGuiComboFlags(1 << 6);
    public static ImGuiComboFlags HeightMask = new ImGuiComboFlags(HeightSmall.getValue() | HeightRegular.getValue() | HeightLarge.getValue() | HeightLargest.getValue());
    private static ImGuiComboFlags Custom = new ImGuiComboFlags(0);
    int value;

    private ImGuiComboFlags(int code) {
        value = code;
    }

    public ImGuiComboFlags or(ImGuiComboFlags otherEnum) {
        ImGuiComboFlags.Custom.value = value | otherEnum.value;
        return ImGuiComboFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
