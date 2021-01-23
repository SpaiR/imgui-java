package imgui.flag;

/**
 * Configuration flags stored in io.ConfigFlags. Set by user/application.
 */
public final class ImGuiConfigFlags {
    private ImGuiConfigFlags() {
    }

    public static final int None = 0;
    /**
     * Master keyboard navigation enable flag. NewFrame() will automatically fill io.NavInputs[] based on io.KeysDown[].
     */
    public static final int NavEnableKeyboard = 1;
    /**
     * Master gamepad navigation enable flag. This is mostly to instruct your imgui backend to fill io.NavInputs[]. Backend also needs to set ImGuiBackendFlags_HasGamepad.
     */
    public static final int NavEnableGamepad = 1 << 1;
    /**
     * Instruct navigation to move the mouse cursor.
     * May be useful on TV/console systems where moving a virtual mouse is awkward. Will update io.MousePos and set io.WantSetMousePos=true.
     * If enabled you MUST honor io.WantSetMousePos requests in your binding, otherwise ImGui will react as if the mouse is jumping around back and forth.
     */
    public static final int NavEnableSetMousePos = 1 << 2;
    /**
     * Instruct navigation to not set the io.WantCaptureKeyboard flag when io.NavActive is set.
     */
    public static final int NavNoCaptureKeyboard = 1 << 3;
    /**
     * Instruct imgui to clear mouse position/buttons in NewFrame(). This allows ignoring the mouse information set by the backend.
     */
    public static final int NoMouse = 1 << 4;
    /**
     * Instruct backend to not alter mouse cursor shape and visibility.
     * Use if the backend cursor changes are interfering with yours and you don't want to use SetMouseCursor() to change mouse cursor.
     * You may want to honor requests from imgui by reading GetMouseCursor() yourself instead.
     */
    public static final int NoMouseCursorChange = 1 << 5;

    /**
     * [BETA] Docking
     * Docking enable flags.
     */
    public static final int DockingEnable = 1 << 6;

    // [BETA] Viewports
    // When using viewports it is recommended that your default value for ImGuiCol_WindowBg is opaque (Alpha=1.0) so transition to a viewport won't be noticeable.

    /**
     * Viewport enable flags (require both ImGuiBackendFlags_PlatformHasViewports + ImGuiBackendFlags_RendererHasViewports set by the respective backends)
     */
    public static final int ViewportsEnable = 1 << 10;
    /**
     * [BETA: Don't use] FIXME-DPI: Reposition and resize imgui windows when the DpiScale of a viewport changed (mostly useful for the main viewport hosting other window).
     * Note that resizing the main window itself is up to your application.
     */
    public static final int DpiEnableScaleViewports = 1 << 14;
    /**
     * [BETA: Don't use] FIXME-DPI: Request bitmap-scaled fonts to match DpiScale. This is a very low-quality workaround.
     * The correct way to handle DPI is _currently_ to replace the atlas and/or fonts in the Platform_OnChangedViewport callback, but this is all early work in progress.
     */
    public static final int DpiEnableScaleFonts = 1 << 15;

    // User storage (to allow your backend/engine to communicate to code that may be shared between multiple projects. Those flags are not used by core Dear ImGui)

    /**
     * Application is SRGB-aware.
     */
    public static final int IsSRGB = 1 << 20;
    /**
     * Application is using a touch screen instead of a mouse.
     */
    public static final int IsTouchScreen = 1 << 21;
}
