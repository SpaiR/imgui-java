package imgui.internal.flag;




/**
 * Store the source authority (dock node vs window) of a field
 */

public final class ImGuiDataAuthority {
    private ImGuiDataAuthority() {
    }

    public static final int Auto = 0;

    public static final int DockNode = 1;

    public static final int Window = 2;
}
