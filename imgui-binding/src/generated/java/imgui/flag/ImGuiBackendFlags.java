package imgui.flag;


/**
 * Backend capabilities flags stored in io.BackendFlags. Set by imgui_impl_xxx or custom backend.
 */
public final class ImGuiBackendFlags {
    private ImGuiBackendFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Backend Platform supports gamepad and currently has one connected.
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int HasGamepad = 1;

    /**
     * Backend Platform supports honoring GetMouseCursor() value to change the OS cursor shape.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int HasMouseCursors = 2;

    /**
     * Backend Platform supports io.WantSetMousePos requests to reposition the OS mouse position (only used if ImGuiConfigFlags_NavEnableSetMousePos is set).
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int HasSetMousePos = 4;

    /**
     * Backend Renderer supports ImDrawCmd::VtxOffset. This enables output of large meshes (64K+ vertices) while still using 16-bit indices.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int RendererHasVtxOffset = 8;

    /**
     * Backend Platform supports multiple viewports.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int PlatformHasViewports = 1024;

    /**
     * Backend Platform supports setting io.MouseHoveredViewport to the viewport directly under the mouse _IGNORING_ viewports with the ImGuiViewportFlags_NoInputs flag and _REGARDLESS_ of whether another viewport is focused and may be capturing the mouse. This information is _NOT EASY_ to provide correctly with most high-level engines! Don't set this without studying _carefully_ how the backends handle ImGuiViewportFlags_NoInputs!
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int HasMouseHoveredViewport = 2048;

    /**
     * Backend Renderer supports multiple viewports.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int RendererHasViewports = 4096;
}
