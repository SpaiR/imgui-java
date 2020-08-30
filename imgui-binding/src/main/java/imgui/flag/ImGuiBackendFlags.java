package imgui.flag;

/**
 * Back-end capabilities flags stored in io.BackendFlags. Set by imgui_impl_xxx or custom back-end.
 */
public final class ImGuiBackendFlags {
    private ImGuiBackendFlags() {
    }

    public static final int None = 0;
    /**
     * Back-end Platform supports gamepad and currently has one connected.
     */
    public static final int HasGamepad = 1;
    /**
     * Back-end Platform supports honoring GetMouseCursor() value to change the OS cursor shape.
     */
    public static final int HasMouseCursors = 1 << 1;
    /**
     * Back-end Platform supports io.WantSetMousePos requests to reposition the OS mouse position (only used if ImGuiConfigFlags_NavEnableSetMousePos is set).
     */
    public static final int HasSetMousePos = 1 << 2;
    /**
     * Back-end Renderer supports ImDrawCmd::VtxOffset. This enables output of large meshes (64K+ vertices) while still using 16-bit indices.
     */
    public static final int RendererHasVtxOffset = 1 << 3;

    // [BETA] Viewports

    /**
     * Back-end Platform supports multiple viewports.
     */
    public static final int PlatformHasViewports = 1 << 10;
    /**
     * Back-end Platform supports setting io.MouseHoveredViewport to the viewport directly under the mouse _IGNORING_ viewports
     * with the ImGuiViewportFlags_NoInputs flag and _REGARDLESS_ of whether another viewport is focused and may be capturing the mouse.
     * This information is _NOT EASY_ to provide correctly with most high-level engines! Don't set this without studying how the examples/ back-end handle it!
     */
    public static final int HasMouseHoveredViewport = 1 << 11;
    /**
     * Back-end Renderer supports multiple viewports.
     */
    public static final int RendererHasViewports = 1 << 12;
}
