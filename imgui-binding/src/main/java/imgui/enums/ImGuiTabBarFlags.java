package imgui.enums;

/**
 * Flags for ImGui::BeginTabBar()
 */
public class ImGuiTabBarFlags {
    public static ImGuiTabBarFlags None = new ImGuiTabBarFlags(0);
    public static ImGuiTabBarFlags Reorderable = new ImGuiTabBarFlags(1 << 0);
    public static ImGuiTabBarFlags AutoSelectNewTabs = new ImGuiTabBarFlags(1 << 1);
    public static ImGuiTabBarFlags TabListPopupButton = new ImGuiTabBarFlags(1 << 2);
    public static ImGuiTabBarFlags NoCloseWithMiddleMouseButton = new ImGuiTabBarFlags(1 << 3);
    public static ImGuiTabBarFlags NoTabListScrollingButtons = new ImGuiTabBarFlags(1 << 4);
    public static ImGuiTabBarFlags NoTooltip = new ImGuiTabBarFlags(1 << 5);
    public static ImGuiTabBarFlags FittingPolicyResizeDown = new ImGuiTabBarFlags(1 << 6);
    public static ImGuiTabBarFlags FittingPolicyScroll = new ImGuiTabBarFlags(1 << 7);
    public static ImGuiTabBarFlags FittingPolicyMask = new ImGuiTabBarFlags(FittingPolicyResizeDown.getValue() | FittingPolicyScroll.getValue());
    public static ImGuiTabBarFlags FittingPolicyDefault = new ImGuiTabBarFlags(FittingPolicyResizeDown.getValue());
    private static ImGuiTabBarFlags Custom = new ImGuiTabBarFlags(0);
    int value;

    private ImGuiTabBarFlags(int code) {
        value = code;
    }

    public ImGuiTabBarFlags or(ImGuiTabBarFlags otherEnum) {
        ImGuiTabBarFlags.Custom.value = value | otherEnum.value;
        return ImGuiTabBarFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
