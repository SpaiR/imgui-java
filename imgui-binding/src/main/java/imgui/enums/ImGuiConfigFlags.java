package imgui.enums;

/**
 * Configuration flags stored in io.ConfigFlags. Set by user/application.
 */
public final class ImGuiConfigFlags {
    private ImGuiConfigFlags() {
    }

    public static final int None = 0;
    public static final int NavEnableKeyboard = 1;     // Master keyboard navigation enable flag. NewFrame() will automatically fill io.NavInputs[] based on io.KeysDown[].
    public static final int NavEnableGamepad = 1 << 1;      // Master gamepad navigation enable flag. This is mostly to instruct your imgui back-end to fill io.NavInputs[]. Back-end also needs to set ImGuiBackendFlags_HasGamepad.
    public static final int NavEnableSetMousePos = 1 << 2;  // Instruct navigation to move the mouse cursor. May be useful on TV/console systems where moving a virtual mouse is awkward. Will update io.MousePos and set io.WantSetMousePos=true. If enabled you MUST honor io.WantSetMousePos requests in your binding, otherwise ImGui will react as if the mouse is jumping around back and forth.
    public static final int NavNoCaptureKeyboard = 1 << 3;  // Instruct navigation to not set the io.WantCaptureKeyboard flag when io.NavActive is set.
    public static final int NoMouse = 1 << 4;               // Instruct imgui to clear mouse position/buttons in NewFrame(). This allows ignoring the mouse information set by the back-end.
    public static final int NoMouseCursorChange = 1 << 5;   // Instruct back-end to not alter mouse cursor shape and visibility. Use if the back-end cursor changes are interfering with yours and you don't want to use SetMouseCursor() to change mouse cursor. You may want to honor requests from imgui by reading GetMouseCursor() yourself instead.

    // User storage (to allow your back-end/engine to communicate to code that may be shared between multiple projects. Those flags are not used by core Dear ImGui)
    public static final int IsSRGB = 1 << 20;         // Application is SRGB-aware.
    public static final int IsTouchScreen = 1 << 21;  // Application is using a touch screen instead of a mouse.
}
