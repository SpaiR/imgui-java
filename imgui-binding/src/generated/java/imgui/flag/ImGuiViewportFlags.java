package imgui.flag;


/**
 * Flags stored in ImGuiViewport::Flags, giving indications to the platform backends.
 */
public final class ImGuiViewportFlags {
    private ImGuiViewportFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Represent a Platform Window
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int IsPlatformWindow = 1;

    /**
     * Represent a Platform Monitor (unused yet)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int IsPlatformMonitor = 2;

    /**
     * Platform Window: Was created/managed by the user application? (rather than our backend)
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int OwnedByApp = 4;

    /**
     * Platform Window: Disable platform decorations: title bar, borders, etc. (generally set all windows, but if ImGuiConfigFlags_ViewportsDecoration is set we only set this on popups/tooltips)
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoDecoration = 8;

    /**
     * Platform Window: Disable platform task bar icon (generally set on popups/tooltips, or all windows if ImGuiConfigFlags_ViewportsNoTaskBarIcon is set)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoTaskBarIcon = 16;

    /**
     * Platform Window: Don't take focus when created.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoFocusOnAppearing = 32;

    /**
     * Platform Window: Don't take focus when clicked on.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoFocusOnClick = 64;

    /**
     * Platform Window: Make mouse pass through so we can drag this window while peaking behind it.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoInputs = 128;

    /**
     * Platform Window: Renderer doesn't need to clear the framebuffer ahead (because we will fill it entirely).
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoRendererClear = 256;

    /**
     * Platform Window: Avoid merging this window into another host window. This can only be set via ImGuiWindowClass viewport flags override (because we need to now ahead if we are going to create a viewport in the first place!).
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int NoAutoMerge = 512;

    /**
     * Platform Window: Display on top (for tooltips only).
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int TopMost = 1024;

    /**
     * Viewport can host multiple imgui windows (secondary viewports are associated to a single window). // FIXME: In practice there's still probably code making the assumption that this is always and only on the MainViewport. Will fix once we add support for "no main viewport".
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int CanHostOtherWindows = 2048;

    /**
     * Platform Window: Window is minimized, can skip render. When minimized we tend to avoid using the viewport pos/size for clipping window or testing if they are contained in the viewport.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int IsMinimized = 4096;

    /**
     * Platform Window: Window is focused (last call to Platform_GetWindowFocus() returned true)
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int IsFocused = 8192;
}
