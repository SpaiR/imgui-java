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
     * Backend Platform supports io.WantSetMousePos requests to reposition the OS mouse position (only used if io.ConfigNavMoveSetMousePos is set).
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
     * Backend Renderer supports ImTextureData requests to create/update/destroy textures. This enables incremental texture updates and texture reloads. See https://github.com/ocornut/imgui/blob/master/docs/BACKENDS.md for instructions on how to upgrade your custom backend.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int RendererHasTextures = 16;

    /**
     * Backend Renderer supports multiple viewports.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int RendererHasViewports = 1024;

    /**
     * Backend Platform supports multiple viewports.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int PlatformHasViewports = 2048;

    /**
     * Backend Platform supports calling io.AddMouseViewportEvent() with the viewport under the mouse. IF POSSIBLE, ignore viewports with the ImGuiViewportFlags_NoInputs flag (Win32 backend, GLFW 3.30+ backend can do this, SDL backend cannot). If this cannot be done, Dear ImGui needs to use a flawed heuristic to find the viewport under.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int HasMouseHoveredViewport = 4096;

    /**
     * Backend Platform supports honoring viewport{@code ->}ParentViewport/ParentViewportId value, by applying the corresponding parent/child relation at the Platform level.
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int HasParentViewport = 8192;
}
