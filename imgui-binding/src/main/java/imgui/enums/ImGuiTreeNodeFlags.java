package imgui.enums;

/**
 * Flags for ImGui::TreeNodeEx(), ImGui::CollapsingHeader*()
 */
public class ImGuiTreeNodeFlags {
    public static ImGuiTreeNodeFlags None = new ImGuiTreeNodeFlags(0);
    public static ImGuiTreeNodeFlags Selected = new ImGuiTreeNodeFlags(1 << 0);
    public static ImGuiTreeNodeFlags Framed = new ImGuiTreeNodeFlags(1 << 1);
    public static ImGuiTreeNodeFlags AllowItemOverlap = new ImGuiTreeNodeFlags(1 << 2);
    public static ImGuiTreeNodeFlags NoTreePushOnOpen = new ImGuiTreeNodeFlags(1 << 3);
    public static ImGuiTreeNodeFlags NoAutoOpenOnLog = new ImGuiTreeNodeFlags(1 << 4);
    public static ImGuiTreeNodeFlags DefaultOpen = new ImGuiTreeNodeFlags(1 << 5);
    public static ImGuiTreeNodeFlags OpenOnDoubleClick = new ImGuiTreeNodeFlags(1 << 6);
    public static ImGuiTreeNodeFlags OpenOnArrow = new ImGuiTreeNodeFlags(1 << 7);
    public static ImGuiTreeNodeFlags Leaf = new ImGuiTreeNodeFlags(1 << 8);
    public static ImGuiTreeNodeFlags Bullet = new ImGuiTreeNodeFlags(1 << 9);
    public static ImGuiTreeNodeFlags FramePadding = new ImGuiTreeNodeFlags(1 << 10);
    //    public static ImGuiTreeNodeFlags SpanAllAvailWidth = new ImGuiTreeNodeFlags(1 << 11);
//    public static ImGuiTreeNodeFlags NoScrollOnOpen = new ImGuiTreeNodeFlags(1 << 12);
    public static ImGuiTreeNodeFlags NavLeftJumpsBackHere = new ImGuiTreeNodeFlags(1 << 13);
    public static ImGuiTreeNodeFlags CollapsingHeader = new ImGuiTreeNodeFlags(Framed.getValue() | NoTreePushOnOpen.getValue() | NoAutoOpenOnLog.getValue());
    private static ImGuiTreeNodeFlags Custom = new ImGuiTreeNodeFlags(0);
    int value;

    private ImGuiTreeNodeFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public ImGuiTreeNodeFlags or(ImGuiTreeNodeFlags otherEnum) {
        ImGuiTreeNodeFlags.Custom.value = value | otherEnum.value;
        return ImGuiTreeNodeFlags.Custom;
    }
}
