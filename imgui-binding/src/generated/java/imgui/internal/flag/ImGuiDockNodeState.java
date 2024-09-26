package imgui.internal.flag;





public final class ImGuiDockNodeState {
    private ImGuiDockNodeState() {
    }

    public static final int Unknown = 0;

    public static final int HostWindowHiddenBecauseSingleWindow = 1;

    public static final int HostWindowHiddenBecauseWindowsAreResizing = 2;

    public static final int HostWindowVisible = 3;
}
