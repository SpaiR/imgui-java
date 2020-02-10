package imgui.enums;

public final class ImDrawListFlags {
    private ImDrawListFlags() {
    }

    public static final int None = 0;

    /**
     * Lines are anti-aliased (*2 the number of triangles for 1.0f wide line, otherwise *3 the number of triangles)
     */
    public static final int AntiAliasedLines = 1;
    /**
     * Filled shapes have anti-aliased edges (*2 the number of vertices)
     */
    public static final int AntiAliasedFill = 1 << 1;
    /**
     * Can emit 'VtxOffset > 0' to allow large meshes. Set when 'ImGuiBackendFlags_RendererHasVtxOffset' is enabled.
     */
    public static final int AllowVtxOffset = 1 << 2;
}
