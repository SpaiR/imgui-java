package imgui.flag;


/**
 * Flags for ImDrawList. Those are set automatically by ImGui:: functions from ImGuiIO settings, and generally not manipulated directly.
 * It is however possible to temporarily alter flags between calls to ImDrawList:: functions.
 */
public final class ImDrawListFlags {
    private ImDrawListFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Enable anti-aliased lines/borders (*2 the number of triangles for 1.0f wide line or lines thin enough to be drawn using textures, otherwise *3 the number of triangles)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int AntiAliasedLines = 1;

    /**
     * Enable anti-aliased lines/borders using textures when possible. Require backend to render with bilinear filtering (NOT point/nearest filtering).
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int AntiAliasedLinesUseTex = 2;

    /**
     * Enable anti-aliased edge around filled shapes (rounded rectangles, circles).
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int AntiAliasedFill = 4;

    /**
     * Can emit 'VtxOffset{@code >}0' to allow large meshes. Set when 'ImGuiBackendFlags_RendererHasVtxOffset' is enabled.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int AllowVtxOffset = 8;
}
