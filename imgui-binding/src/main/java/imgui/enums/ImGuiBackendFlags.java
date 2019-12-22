package imgui.enums;

/**
 * Back-end capabilities flags stored in io.BackendFlags. Set by imgui_impl_xxx or custom back-end.
 */
public final class ImGuiBackendFlags {
    public static ImGuiBackendFlags None = new ImGuiBackendFlags(0);
    public static ImGuiBackendFlags HasGamepad = new ImGuiBackendFlags(1 << 0);
    public static ImGuiBackendFlags HasMouseCursors = new ImGuiBackendFlags(1 << 1);
    public static ImGuiBackendFlags HasSetMousePos = new ImGuiBackendFlags(1 << 2);
    private static ImGuiBackendFlags Custom = new ImGuiBackendFlags(0);
    int value;

    private ImGuiBackendFlags(int code) {
        value = code;
    }

    public ImGuiBackendFlags or(ImGuiBackendFlags otherEnum) {
        ImGuiBackendFlags.Custom.value = value | otherEnum.value;
        return ImGuiBackendFlags.Custom;
    }

    public int getValue() {
        return value;
    }
}
