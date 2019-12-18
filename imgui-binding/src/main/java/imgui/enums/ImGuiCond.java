package imgui.enums;

/**
 * Enumateration for ImGui::SetWindow***(), SetNextWindow***(), SetNextTreeNode***() functions
 * Represent a condition.
 * Important: Treat as a regular enum! Do NOT combine multiple values using binary operators! All the functions above treat 0 as a shortcut to ImGuiCond_Always.
 */
public class ImGuiCond {
    public static ImGuiCond Always = new ImGuiCond(1 << 0);
    public static ImGuiCond Once = new ImGuiCond(1 << 1);
    public static ImGuiCond FirstUseEver = new ImGuiCond(1 << 2);
    public static ImGuiCond Appearing = new ImGuiCond(1 << 3);
    private static ImGuiCond Custom = new ImGuiCond(0);
    int value;

    private ImGuiCond(int code) {
        value = code;
    }

    public ImGuiCond or(ImGuiCond otherEnum) {
        ImGuiCond.Custom.value = value | otherEnum.value;
        return ImGuiCond.Custom;
    }

    public int getValue() {
        return value;
    }
}
