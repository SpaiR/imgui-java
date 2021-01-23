package imgui.flag;

/**
 * Flags stored in ImGuiViewport::Flags, giving indications to the platform backends.
 */
public final class ImGuiViewportFlags {
    private ImGuiViewportFlags() {
    }

    public static final int None = 0;
    /**
     * Platform Window: Disable platform decorations: title bar, borders, etc.
     * (generally set all windows, but if ImGuiConfigFlags_ViewportsDecoration is set we only set this on popups/tooltips)
     */
    public static final int NoDecoration = 1;
    /**
     * Platform Window: Disable platform task bar icon
     * (generally set on popups/tooltips, or all windows if ImGuiConfigFlags_ViewportsNoTaskBarIcon is set)
     */
    public static final int NoTaskBarIcon = 1 << 1;
    /**
     * Platform Window: Don't take focus when created.
     */
    public static final int NoFocusOnAppearing = 1 << 2;
    /**
     * Platform Window: Don't take focus when clicked on.
     */
    public static final int NoFocusOnClick = 1 << 3;
    /**
     * Platform Window: Make mouse pass through so we can drag this window while peaking behind it.
     */
    public static final int NoInputs = 1 << 4;
    /**
     * Platform Window: Renderer doesn't need to clear the framebuffer ahead (because we will fill it entirely).
     */
    public static final int NoRendererClear = 1 << 5;
    /**
     * Platform Window: Display on top (for tooltips only).
     */
    public static final int TopMost = 1 << 6;
    /**
     * Platform Window: Window is minimized, can skip render. When minimized we tend to avoid using the viewport pos/size for clipping window or testing if they are contained in the viewport.
     */
    public static final int Minimized = 1 << 7;
    /**
     * Platform Window: Avoid merging this window into another host window. This can only be set via ImGuiWindowClass viewport flags override
     * (because we need to now ahead if we are going to create a viewport in the first place!).
     */
    public static final int NoAutoMerge = 1 << 8;
    /**
     * Main viewport: can host multiple imgui windows (secondary viewports are associated to a single window).
     */
    public static final int CanHostOtherWindows = 1 << 9;
}
