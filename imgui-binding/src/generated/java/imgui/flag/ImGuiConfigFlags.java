package imgui.flag;


/**
 * Configuration flags stored in io.ConfigFlags. Set by user/application.
 */
public final class ImGuiConfigFlags {
    private ImGuiConfigFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Master keyboard navigation enable flag. Enable full Tabbing + directional arrows + Space/Enter to activate. Note: some features such as basic Tabbing and CtrL+Tab are enabled by regardless of this flag (and may be disabled via other means, see #4828, #9218).
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NavEnableKeyboard = 1;

    /**
     * Master gamepad navigation enable flag. Backend also needs to set ImGuiBackendFlags_HasGamepad.
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NavEnableGamepad = 2;

    /**
     * Instruct dear imgui to disable mouse inputs and interactions.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoMouse = 16;

    /**
     * Instruct backend to not alter mouse cursor shape and visibility. Use if the backend cursor changes are interfering with yours and you don't want to use SetMouseCursor() to change mouse cursor. You may want to honor requests from imgui by reading GetMouseCursor() yourself instead.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoMouseCursorChange = 32;

    /**
     * Instruct dear imgui to disable keyboard inputs and interactions. This is done by ignoring keyboard events and clearing existing states.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoKeyboard = 64;

    /**
     * Docking enable flags.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int DockingEnable = 128;

    /**
     * Viewport enable flags (require both ImGuiBackendFlags_PlatformHasViewports + ImGuiBackendFlags_RendererHasViewports set by the respective backends)
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int ViewportsEnable = 1024;

    /**
     * Application is SRGB-aware.
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int IsSRGB = 1048576;

    /**
     * Application is using a touch screen instead of a mouse.
     *
     * <p>Definition: {@code 1 << 21}
     */
    public static final int IsTouchScreen = 2097152;

    /**
     * [moved/renamed in 1.91.4] {@code ->} use bool io.ConfigNavMoveSetMousePos
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NavEnableSetMousePos = 4;

    /**
     * [moved/renamed in 1.91.4] {@code ->} use bool io.ConfigNavCaptureKeyboard
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NavNoCaptureKeyboard = 8;

    /**
     * [moved/renamed in 1.92.0] {@code ->} use bool io.ConfigDpiScaleFonts
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int DpiEnableScaleFonts = 16384;

    /**
     * [moved/renamed in 1.92.0] {@code ->} use bool io.ConfigDpiScaleViewports
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int DpiEnableScaleViewports = 32768;
}
