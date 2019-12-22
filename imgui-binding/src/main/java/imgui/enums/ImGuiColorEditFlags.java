package imgui.enums;

/**
 * Enumeration for GetMouseCursor()
 * User code may request binding to display given cursor by calling SetMouseCursor(), which is why we have some cursors that are marked unused here
 */
public final class ImGuiColorEditFlags {
    public static ImGuiColorEditFlags None = new ImGuiColorEditFlags(0);
    public static ImGuiColorEditFlags NoAlpha = new ImGuiColorEditFlags(1 << 1);
    public static ImGuiColorEditFlags NoPicker = new ImGuiColorEditFlags(1 << 2);
    public static ImGuiColorEditFlags NoOptions = new ImGuiColorEditFlags(1 << 3);
    public static ImGuiColorEditFlags NoSmallPreview = new ImGuiColorEditFlags(1 << 4);
    public static ImGuiColorEditFlags NoInputs = new ImGuiColorEditFlags(1 << 5);
    public static ImGuiColorEditFlags NoTooltip = new ImGuiColorEditFlags(1 << 6);
    public static ImGuiColorEditFlags NoLabel = new ImGuiColorEditFlags(1 << 7);
    public static ImGuiColorEditFlags NoSidePreview = new ImGuiColorEditFlags(1 << 8);
    public static ImGuiColorEditFlags NoDragDrop = new ImGuiColorEditFlags(1 << 9);
    public static ImGuiColorEditFlags AlphaBar = new ImGuiColorEditFlags(1 << 16);
    public static ImGuiColorEditFlags AlphaPreview = new ImGuiColorEditFlags(1 << 17);
    public static ImGuiColorEditFlags AlphaPreviewHalf = new ImGuiColorEditFlags(1 << 18);
    public static ImGuiColorEditFlags HDR = new ImGuiColorEditFlags(1 << 19);
    public static ImGuiColorEditFlags DisplayRGB = new ImGuiColorEditFlags(1 << 20);
    public static ImGuiColorEditFlags DisplayHSV = new ImGuiColorEditFlags(1 << 21);
    public static ImGuiColorEditFlags DisplayHex = new ImGuiColorEditFlags(1 << 22);
    public static ImGuiColorEditFlags Uint8 = new ImGuiColorEditFlags(1 << 23);
    public static ImGuiColorEditFlags Float = new ImGuiColorEditFlags(1 << 24);
    public static ImGuiColorEditFlags PickerHueBar = new ImGuiColorEditFlags(1 << 25);
    public static ImGuiColorEditFlags PickerHueWheel = new ImGuiColorEditFlags(1 << 26);
    public static ImGuiColorEditFlags InputRGB = new ImGuiColorEditFlags(1 << 27);
    public static ImGuiColorEditFlags InputHSV = new ImGuiColorEditFlags(1 << 28);
    public static ImGuiColorEditFlags OptionsDefault = new ImGuiColorEditFlags(Uint8.getValue() | DisplayRGB.getValue() | InputRGB.getValue() | PickerHueBar.getValue());
    private static ImGuiColorEditFlags Custom = new ImGuiColorEditFlags(0);
    int value;

    private ImGuiColorEditFlags(int code) {
        value = code;
    }

    public ImGuiColorEditFlags or(ImGuiColorEditFlags otherEnum) {
        ImGuiColorEditFlags.Custom.value = value | otherEnum.value;
        return ImGuiColorEditFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
