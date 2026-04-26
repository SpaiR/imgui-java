package imgui.sdl3;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiMouseSource;
import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.sdl.SDL_KeyboardEvent;
import org.lwjgl.sdl.SDL_MouseButtonEvent;
import org.lwjgl.sdl.SDL_MouseMotionEvent;
import org.lwjgl.sdl.SDL_MouseWheelEvent;
import org.lwjgl.sdl.SDL_TextInputEvent;
import org.lwjgl.sdl.SDL_WindowEvent;

import static org.lwjgl.sdl.SDLClipboard.SDL_GetClipboardText;
import static org.lwjgl.sdl.SDLClipboard.SDL_HasClipboardText;
import static org.lwjgl.sdl.SDLClipboard.SDL_SetClipboardText;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_DISPLAY_ADDED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_DISPLAY_CONTENT_SCALE_CHANGED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_DISPLAY_MOVED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_DISPLAY_ORIENTATION;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_DISPLAY_REMOVED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_DISPLAY_USABLE_BOUNDS_CHANGED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_GAMEPAD_ADDED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_GAMEPAD_REMOVED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_KEY_DOWN;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_KEY_UP;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_BUTTON_DOWN;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_BUTTON_UP;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_MOTION;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_WHEEL;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_TEXT_INPUT;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_CLOSE_REQUESTED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_FOCUS_GAINED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_FOCUS_LOST;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_MOUSE_ENTER;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_MOUSE_LEAVE;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_MOVED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_RESIZED;
import static org.lwjgl.sdl.SDLGamepad.SDL_CloseGamepad;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_AXIS_LEFTX;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_AXIS_LEFTY;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_AXIS_LEFT_TRIGGER;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_AXIS_RIGHTX;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_AXIS_RIGHTY;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_AXIS_RIGHT_TRIGGER;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_BACK;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_DPAD_DOWN;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_DPAD_LEFT;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_DPAD_RIGHT;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_DPAD_UP;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_EAST;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_LEFT_SHOULDER;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_LEFT_STICK;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_NORTH;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_RIGHT_STICK;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_SOUTH;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_START;
import static org.lwjgl.sdl.SDLGamepad.SDL_GAMEPAD_BUTTON_WEST;
import static org.lwjgl.sdl.SDLGamepad.SDL_GetGamepadAxis;
import static org.lwjgl.sdl.SDLGamepad.SDL_GetGamepadButton;
import static org.lwjgl.sdl.SDLGamepad.SDL_GetGamepads;
import static org.lwjgl.sdl.SDLGamepad.SDL_OpenGamepad;
import static org.lwjgl.sdl.SDLHints.SDL_HINT_MOUSE_AUTO_CAPTURE;
import static org.lwjgl.sdl.SDLHints.SDL_HINT_MOUSE_FOCUS_CLICKTHROUGH;
import static org.lwjgl.sdl.SDLHints.SDL_SetHint;
import static org.lwjgl.sdl.SDLKeycode.SDLK_0;
import static org.lwjgl.sdl.SDLKeycode.SDLK_1;
import static org.lwjgl.sdl.SDLKeycode.SDLK_2;
import static org.lwjgl.sdl.SDLKeycode.SDLK_3;
import static org.lwjgl.sdl.SDLKeycode.SDLK_4;
import static org.lwjgl.sdl.SDLKeycode.SDLK_5;
import static org.lwjgl.sdl.SDLKeycode.SDLK_6;
import static org.lwjgl.sdl.SDLKeycode.SDLK_7;
import static org.lwjgl.sdl.SDLKeycode.SDLK_8;
import static org.lwjgl.sdl.SDLKeycode.SDLK_9;
import static org.lwjgl.sdl.SDLKeycode.SDLK_A;
import static org.lwjgl.sdl.SDLKeycode.SDLK_AC_BACK;
import static org.lwjgl.sdl.SDLKeycode.SDLK_AC_FORWARD;
import static org.lwjgl.sdl.SDLKeycode.SDLK_APPLICATION;
import static org.lwjgl.sdl.SDLKeycode.SDLK_B;
import static org.lwjgl.sdl.SDLKeycode.SDLK_BACKSPACE;
import static org.lwjgl.sdl.SDLKeycode.SDLK_C;
import static org.lwjgl.sdl.SDLKeycode.SDLK_CAPSLOCK;
import static org.lwjgl.sdl.SDLKeycode.SDLK_COMMA;
import static org.lwjgl.sdl.SDLKeycode.SDLK_D;
import static org.lwjgl.sdl.SDLKeycode.SDLK_DELETE;
import static org.lwjgl.sdl.SDLKeycode.SDLK_DOWN;
import static org.lwjgl.sdl.SDLKeycode.SDLK_E;
import static org.lwjgl.sdl.SDLKeycode.SDLK_END;
import static org.lwjgl.sdl.SDLKeycode.SDLK_ESCAPE;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F1;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F10;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F11;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F12;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F13;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F14;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F15;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F16;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F17;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F18;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F19;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F2;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F20;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F21;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F22;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F23;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F24;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F3;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F4;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F5;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F6;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F7;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F8;
import static org.lwjgl.sdl.SDLKeycode.SDLK_F9;
import static org.lwjgl.sdl.SDLKeycode.SDLK_G;
import static org.lwjgl.sdl.SDLKeycode.SDLK_H;
import static org.lwjgl.sdl.SDLKeycode.SDLK_HOME;
import static org.lwjgl.sdl.SDLKeycode.SDLK_I;
import static org.lwjgl.sdl.SDLKeycode.SDLK_INSERT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_J;
import static org.lwjgl.sdl.SDLKeycode.SDLK_K;
import static org.lwjgl.sdl.SDLKeycode.SDLK_L;
import static org.lwjgl.sdl.SDLKeycode.SDLK_LALT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_LCTRL;
import static org.lwjgl.sdl.SDLKeycode.SDLK_LEFT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_LGUI;
import static org.lwjgl.sdl.SDLKeycode.SDLK_LSHIFT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_M;
import static org.lwjgl.sdl.SDLKeycode.SDLK_N;
import static org.lwjgl.sdl.SDLKeycode.SDLK_NUMLOCKCLEAR;
import static org.lwjgl.sdl.SDLKeycode.SDLK_O;
import static org.lwjgl.sdl.SDLKeycode.SDLK_P;
import static org.lwjgl.sdl.SDLKeycode.SDLK_PAGEDOWN;
import static org.lwjgl.sdl.SDLKeycode.SDLK_PAGEUP;
import static org.lwjgl.sdl.SDLKeycode.SDLK_PAUSE;
import static org.lwjgl.sdl.SDLKeycode.SDLK_PERIOD;
import static org.lwjgl.sdl.SDLKeycode.SDLK_PRINTSCREEN;
import static org.lwjgl.sdl.SDLKeycode.SDLK_Q;
import static org.lwjgl.sdl.SDLKeycode.SDLK_R;
import static org.lwjgl.sdl.SDLKeycode.SDLK_RALT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_RCTRL;
import static org.lwjgl.sdl.SDLKeycode.SDLK_RETURN;
import static org.lwjgl.sdl.SDLKeycode.SDLK_RGUI;
import static org.lwjgl.sdl.SDLKeycode.SDLK_RIGHT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_RSHIFT;
import static org.lwjgl.sdl.SDLKeycode.SDLK_S;
import static org.lwjgl.sdl.SDLKeycode.SDLK_SCROLLLOCK;
import static org.lwjgl.sdl.SDLKeycode.SDLK_SEMICOLON;
import static org.lwjgl.sdl.SDLKeycode.SDLK_SPACE;
import static org.lwjgl.sdl.SDLKeycode.SDLK_T;
import static org.lwjgl.sdl.SDLKeycode.SDLK_TAB;
import static org.lwjgl.sdl.SDLKeycode.SDLK_U;
import static org.lwjgl.sdl.SDLKeycode.SDLK_UP;
import static org.lwjgl.sdl.SDLKeycode.SDLK_V;
import static org.lwjgl.sdl.SDLKeycode.SDLK_W;
import static org.lwjgl.sdl.SDLKeycode.SDLK_X;
import static org.lwjgl.sdl.SDLKeycode.SDLK_Y;
import static org.lwjgl.sdl.SDLKeycode.SDLK_Z;
import static org.lwjgl.sdl.SDLKeycode.SDL_KMOD_ALT;
import static org.lwjgl.sdl.SDLKeycode.SDL_KMOD_CTRL;
import static org.lwjgl.sdl.SDLKeycode.SDL_KMOD_GUI;
import static org.lwjgl.sdl.SDLKeycode.SDL_KMOD_SHIFT;
import static org.lwjgl.sdl.SDLMouse.SDL_BUTTON_LEFT;
import static org.lwjgl.sdl.SDLMouse.SDL_BUTTON_MIDDLE;
import static org.lwjgl.sdl.SDLMouse.SDL_BUTTON_RIGHT;
import static org.lwjgl.sdl.SDLMouse.SDL_BUTTON_X1;
import static org.lwjgl.sdl.SDLMouse.SDL_BUTTON_X2;
import static org.lwjgl.sdl.SDLMouse.SDL_CaptureMouse;
import static org.lwjgl.sdl.SDLMouse.SDL_CreateSystemCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_DestroyCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_HideCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_DEFAULT;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_EW_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_MOVE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NESW_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NOT_ALLOWED;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NS_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NWSE_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_POINTER;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_PROGRESS;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_TEXT;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_WAIT;
import static org.lwjgl.sdl.SDLMouse.SDL_SetCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_ShowCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_WarpMouseInWindow;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_APOSTROPHE;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_BACKSLASH;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_COMMA;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_EQUALS;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_GRAVE;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_0;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_1;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_2;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_3;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_4;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_5;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_6;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_7;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_8;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_9;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_DIVIDE;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_ENTER;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_EQUALS;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_MINUS;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_MULTIPLY;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_PERIOD;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_KP_PLUS;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_LEFTBRACKET;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_MINUS;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_NONUSBACKSLASH;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_PERIOD;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_RIGHTBRACKET;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_SEMICOLON;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_SLASH;
import static org.lwjgl.sdl.SDLTimer.SDL_GetPerformanceCounter;
import static org.lwjgl.sdl.SDLTimer.SDL_GetPerformanceFrequency;
import static org.lwjgl.sdl.SDLTouch.SDL_TOUCH_MOUSEID;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowFlags;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowID;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowPosition;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowSize;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowSizeInPixels;
import static org.lwjgl.sdl.SDLVideo.SDL_WINDOW_INPUT_FOCUS;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Java 1-to-1 port of upstream {@code imgui_impl_sdl3.cpp}: Dear ImGui platform backend for SDL3.
 *
 * <p>Public lifecycle ({@link #init(long)}, {@link #shutdown()}, {@link #newFrame()}) and event
 * forwarding ({@link #processEvent(long)}) mirror the upstream C functions. Unlike the GLFW port,
 * SDL has an explicit poll loop, so callers feed events via {@link #processEvent(long)} after
 * {@code SDL_PollEvent} rather than installing window callbacks.
 *
 * <p>Multi-viewport, IME callbacks, and the {@code Platform_*Fn} function-pointer registrations on
 * {@code ImGuiPlatformIO} are not ported — they require C function pointers (see
 * {@code // NOTE: not ported} markers below). Single-viewport mode covers the visual smoke test.
 */
@SuppressWarnings("checkstyle:DesignForExtension")
public class ImGuiImplSdl3 {
    /**
     * Manual gamepad list mode: {@link #setGamepadMode(int, long[])} accepts gamepad handles directly.
     */
    public static final int GAMEPAD_MODE_AUTO_FIRST = 0;
    /**
     * Auto-discover and forward inputs from all connected gamepads merged.
     */
    public static final int GAMEPAD_MODE_AUTO_ALL = 1;
    /**
     * User-managed list of gamepads (call {@link #setGamepadMode(int, long[])} with the array).
     */
    public static final int GAMEPAD_MODE_MANUAL = 2;

    /**
     * Mouse capture disabled.
     */
    public static final int MOUSE_CAPTURE_MODE_DISABLED = 0;
    /**
     * Mouse capture starts on mouse-down. Default on most platforms.
     */
    public static final int MOUSE_CAPTURE_MODE_ENABLED = 1;
    /**
     * Mouse capture starts only after a drag is detected. Default on X11 to mitigate debugger issues.
     */
    public static final int MOUSE_CAPTURE_MODE_ENABLED_AFTER_DRAG = 2;

    protected static class Data {
        protected long window;
        protected int windowID;
        protected long time;
        protected boolean wantUpdateMonitors;

        // Mouse handling
        protected int mouseWindowID;
        protected int mouseButtonsDown;
        protected final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
        protected long mouseLastCursor;
        protected int mousePendingLeaveFrame;
        protected boolean mouseCanUseGlobalState;
        protected int mouseCaptureMode;

        // Gamepad handling
        protected final List<Long> gamepads = new ArrayList<>();
        protected int gamepadMode = GAMEPAD_MODE_AUTO_FIRST;
        protected boolean wantUpdateGamepadsList;
    }

    protected Data data = null;

    protected ImStrSupplier getClipboardTextFn() {
        return new ImStrSupplier() {
            @Override
            public String get() {
                if (SDL_HasClipboardText()) {
                    final String clipboardString = SDL_GetClipboardText();
                    return clipboardString != null ? clipboardString : "";
                }
                return null;
            }
        };
    }

    protected ImStrConsumer setClipboardTextFn() {
        return new ImStrConsumer() {
            @Override
            public void accept(final String text) {
                if (text != null) {
                    SDL_SetClipboardText(text);
                }
            }
        };
    }

    /**
     * Initializes the SDL3 platform backend bound to the given native {@code SDL_Window} handle.
     * Mirrors {@code ImGui_ImplSDL3_Init}.
     *
     * @param sdlWindow native {@code SDL_Window*} pointer
     * @return {@code true} on success
     */
    public boolean init(final long sdlWindow) {
        final ImGuiIO io = ImGui.getIO();
        // IM_ASSERT(io.BackendPlatformUserData == NULL && "Already initialized a platform backend!");

        // Setup backend capabilities flags
        io.setBackendPlatformName("imgui_impl_sdl3 (lwjgl)");
        io.addBackendFlags(ImGuiBackendFlags.HasMouseCursors);
        io.addBackendFlags(ImGuiBackendFlags.HasSetMousePos);

        data = new Data();
        data.window = sdlWindow;
        data.windowID = SDL_GetWindowID(sdlWindow);
        data.mouseCanUseGlobalState = false;
        data.mouseCaptureMode = MOUSE_CAPTURE_MODE_DISABLED;

        // Gamepad handling
        data.gamepadMode = GAMEPAD_MODE_AUTO_FIRST;
        data.wantUpdateGamepadsList = true;

        // Load mouse cursors
        data.mouseCursors[ImGuiMouseCursor.Arrow] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_DEFAULT);
        data.mouseCursors[ImGuiMouseCursor.TextInput] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_TEXT);
        data.mouseCursors[ImGuiMouseCursor.ResizeAll] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_MOVE);
        data.mouseCursors[ImGuiMouseCursor.ResizeNS] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_NS_RESIZE);
        data.mouseCursors[ImGuiMouseCursor.ResizeEW] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_EW_RESIZE);
        data.mouseCursors[ImGuiMouseCursor.ResizeNESW] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_NESW_RESIZE);
        data.mouseCursors[ImGuiMouseCursor.ResizeNWSE] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_NWSE_RESIZE);
        data.mouseCursors[ImGuiMouseCursor.Hand] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_POINTER);
        data.mouseCursors[ImGuiMouseCursor.Wait] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_WAIT);
        data.mouseCursors[ImGuiMouseCursor.Progress] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_PROGRESS);
        data.mouseCursors[ImGuiMouseCursor.NotAllowed] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_NOT_ALLOWED);

        io.setGetClipboardTextFn(getClipboardTextFn());
        io.setSetClipboardTextFn(setClipboardTextFn());

        // TODO: not ported — Platform_SetImeDataFn / Platform_OpenInShellFn registrations on ImGuiPlatformIO require C function pointers.

        // From 2.0.5: Set SDL hint to receive mouse click events on window focus
        SDL_SetHint(SDL_HINT_MOUSE_FOCUS_CLICKTHROUGH, "1");
        // From 2.0.22: Disable auto-capture, this is preventing drag and drop across multiple windows
        SDL_SetHint(SDL_HINT_MOUSE_AUTO_CAPTURE, "0");
        // SDL 3.x : see https://github.com/libsdl-org/SDL/issues/6659
        SDL_SetHint("SDL_BORDERLESS_WINDOWED_STYLE", "0");

        // TODO: not ported — multi-viewport (ImGui_ImplSDL3_InitMultiViewportSupport) requires C function pointers.

        return true;
    }

    /**
     * Should technically take a {@code SDL_GLContext} but the parameter is ignored on non-OpenGL paths.
     */
    public final boolean initForOpenGL(final long sdlWindow, final long sdlGlContext) {
        return init(sdlWindow);
    }

    public final boolean initForVulkan(final long sdlWindow) {
        return init(sdlWindow);
    }

    public final boolean initForD3D(final long sdlWindow) {
        return init(sdlWindow);
    }

    public final boolean initForMetal(final long sdlWindow) {
        return init(sdlWindow);
    }

    public final boolean initForSDLRenderer(final long sdlWindow, final long sdlRenderer) {
        return init(sdlWindow);
    }

    public final boolean initForSDLGPU(final long sdlWindow) {
        return init(sdlWindow);
    }

    public final boolean initForOther(final long sdlWindow) {
        return init(sdlWindow);
    }

    /**
     * Mirrors {@code ImGui_ImplSDL3_Shutdown}.
     */
    public void shutdown() {
        final ImGuiIO io = ImGui.getIO();

        // TODO: not ported — multi-viewport shutdown.

        for (int i = 0; i < ImGuiMouseCursor.COUNT; i++) {
            if (data.mouseCursors[i] != 0L) {
                SDL_DestroyCursor(data.mouseCursors[i]);
                data.mouseCursors[i] = 0L;
            }
        }
        closeGamepads();

        io.setBackendPlatformName(null);
        io.removeBackendFlags(ImGuiBackendFlags.HasMouseCursors
                | ImGuiBackendFlags.HasSetMousePos
                | ImGuiBackendFlags.HasGamepad
                | ImGuiBackendFlags.PlatformHasViewports
                | ImGuiBackendFlags.HasMouseHoveredViewport
                | ImGuiBackendFlags.HasParentViewport);

        data = null;
    }

    /**
     * Mirrors {@code ImGui_ImplSDL3_SetMouseCaptureMode}.
     */
    public void setMouseCaptureMode(final int mode) {
        if (mode == MOUSE_CAPTURE_MODE_DISABLED && data.mouseCaptureMode != MOUSE_CAPTURE_MODE_DISABLED) {
            SDL_CaptureMouse(false);
        }
        data.mouseCaptureMode = mode;
    }

    /**
     * Mirrors {@code ImGui_ImplSDL3_SetGamepadMode}.
     *
     * @param mode one of {@link #GAMEPAD_MODE_AUTO_FIRST}, {@link #GAMEPAD_MODE_AUTO_ALL}, {@link #GAMEPAD_MODE_MANUAL}
     * @param manualGamepads array of gamepad handles when {@code mode == GAMEPAD_MODE_MANUAL}, otherwise {@code null}
     */
    public void setGamepadMode(final int mode, final long[] manualGamepads) {
        closeGamepads();
        if (mode == GAMEPAD_MODE_MANUAL) {
            if (manualGamepads != null) {
                for (final long g : manualGamepads) {
                    data.gamepads.add(g);
                }
            }
        } else {
            data.wantUpdateGamepadsList = true;
        }
        data.gamepadMode = mode;
    }

    /**
     * Forwards an SDL event into Dear ImGui. Mirrors {@code ImGui_ImplSDL3_ProcessEvent}.
     *
     * @param sdlEventPtr native {@code SDL_Event*} pointer (from {@code SDL_PollEvent})
     * @return {@code true} when the event was consumed by the backend
     */
    public boolean processEvent(final long sdlEventPtr) {
        final SDL_Event event = SDL_Event.create(sdlEventPtr);
        final ImGuiIO io = ImGui.getIO();
        final int type = event.type();

        switch (type) {
            case SDL_EVENT_MOUSE_MOTION:
                return processMouseMotion(event, io);
            case SDL_EVENT_MOUSE_WHEEL:
                return processMouseWheel(event, io);
            case SDL_EVENT_MOUSE_BUTTON_DOWN:
            case SDL_EVENT_MOUSE_BUTTON_UP:
                return processMouseButton(event, io, type);
            case SDL_EVENT_TEXT_INPUT:
                return processTextInput(event, io);
            case SDL_EVENT_KEY_DOWN:
            case SDL_EVENT_KEY_UP:
                return processKey(event, io, type);
            case SDL_EVENT_DISPLAY_ORIENTATION:
            case SDL_EVENT_DISPLAY_ADDED:
            case SDL_EVENT_DISPLAY_REMOVED:
            case SDL_EVENT_DISPLAY_MOVED:
            case SDL_EVENT_DISPLAY_CONTENT_SCALE_CHANGED:
            case SDL_EVENT_DISPLAY_USABLE_BOUNDS_CHANGED:
                data.wantUpdateMonitors = true;
                return true;
            case SDL_EVENT_WINDOW_MOUSE_ENTER:
                return processWindowMouseEnter(event);
            case SDL_EVENT_WINDOW_MOUSE_LEAVE:
                return processWindowMouseLeave(event);
            case SDL_EVENT_WINDOW_FOCUS_GAINED:
            case SDL_EVENT_WINDOW_FOCUS_LOST:
                return processWindowFocus(event, io, type);
            case SDL_EVENT_WINDOW_CLOSE_REQUESTED:
            case SDL_EVENT_WINDOW_MOVED:
            case SDL_EVENT_WINDOW_RESIZED:
                // Single-viewport: nothing to forward to ImGui; user code reacts via SDL events directly.
                // TODO: not ported — viewport->PlatformRequestClose / Move / Resize hooks are multi-viewport only.
                return true;
            case SDL_EVENT_GAMEPAD_ADDED:
            case SDL_EVENT_GAMEPAD_REMOVED:
                data.wantUpdateGamepadsList = true;
                return true;
            default:
                return false;
        }
    }

    private boolean processMouseMotion(final SDL_Event event, final ImGuiIO io) {
        final SDL_MouseMotionEvent motion = event.motion();
        if (motion.windowID() != data.windowID) {
            return false;
        }
        float mx = motion.x();
        float my = motion.y();
        if ((io.getConfigFlags() & ImGuiConfigFlags.ViewportsEnable) != 0) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                final IntBuffer pwx = stack.mallocInt(1);
                final IntBuffer pwy = stack.mallocInt(1);
                SDL_GetWindowPosition(data.window, pwx, pwy);
                mx += pwx.get(0);
                my += pwy.get(0);
            }
        }
        io.addMouseSourceEvent(motion.which() == SDL_TOUCH_MOUSEID ? ImGuiMouseSource.TouchScreen : ImGuiMouseSource.Mouse);
        io.addMousePosEvent(mx, my);
        return true;
    }

    private boolean processMouseWheel(final SDL_Event event, final ImGuiIO io) {
        final SDL_MouseWheelEvent wheel = event.wheel();
        if (wheel.windowID() != data.windowID) {
            return false;
        }
        final float wheelX = -wheel.x();
        final float wheelY = wheel.y();
        io.addMouseSourceEvent(wheel.which() == SDL_TOUCH_MOUSEID ? ImGuiMouseSource.TouchScreen : ImGuiMouseSource.Mouse);
        io.addMouseWheelEvent(wheelX, wheelY);
        return true;
    }

    private boolean processMouseButton(final SDL_Event event, final ImGuiIO io, final int type) {
        final SDL_MouseButtonEvent button = event.button();
        if (button.windowID() != data.windowID) {
            return false;
        }
        int mouseButton = -1;
        final int btn = button.button() & 0xFF;
        if (btn == SDL_BUTTON_LEFT) {
            mouseButton = 0;
        } else if (btn == SDL_BUTTON_RIGHT) {
            mouseButton = 1;
        } else if (btn == SDL_BUTTON_MIDDLE) {
            mouseButton = 2;
        } else if (btn == SDL_BUTTON_X1) {
            mouseButton = 3;
        } else if (btn == SDL_BUTTON_X2) {
            mouseButton = 4;
        }
        if (mouseButton == -1) {
            return false;
        }
        final boolean down = type == SDL_EVENT_MOUSE_BUTTON_DOWN;
        io.addMouseSourceEvent(button.which() == SDL_TOUCH_MOUSEID ? ImGuiMouseSource.TouchScreen : ImGuiMouseSource.Mouse);
        io.addMouseButtonEvent(mouseButton, down);
        if (down) {
            data.mouseButtonsDown |= 1 << mouseButton;
        } else {
            data.mouseButtonsDown &= ~(1 << mouseButton);
        }
        return true;
    }

    private boolean processTextInput(final SDL_Event event, final ImGuiIO io) {
        final SDL_TextInputEvent text = event.text();
        if (text.windowID() != data.windowID) {
            return false;
        }
        io.addInputCharactersUTF8(text.textString());
        return true;
    }

    private boolean processKey(final SDL_Event event, final ImGuiIO io, final int type) {
        final SDL_KeyboardEvent key = event.key();
        if (key.windowID() != data.windowID) {
            return false;
        }
        updateKeyModifiers(key.mod());
        final int imguiKey = keyEventToImGuiKey(key.key(), key.scancode());
        io.addKeyEvent(imguiKey, type == SDL_EVENT_KEY_DOWN);
        io.setKeyEventNativeData(imguiKey, key.key(), key.scancode(), key.scancode());
        return true;
    }

    private boolean processWindowMouseEnter(final SDL_Event event) {
        final SDL_WindowEvent win = event.window();
        if (win.windowID() != data.windowID) {
            return false;
        }
        data.mouseWindowID = win.windowID();
        data.mousePendingLeaveFrame = 0;
        return true;
    }

    private boolean processWindowMouseLeave(final SDL_Event event) {
        final SDL_WindowEvent win = event.window();
        if (win.windowID() != data.windowID) {
            return false;
        }
        data.mousePendingLeaveFrame = ImGui.getFrameCount() + 1;
        return true;
    }

    private boolean processWindowFocus(final SDL_Event event, final ImGuiIO io, final int type) {
        final SDL_WindowEvent win = event.window();
        if (win.windowID() != data.windowID) {
            return false;
        }
        io.addFocusEvent(type == SDL_EVENT_WINDOW_FOCUS_GAINED);
        return true;
    }

    /**
     * Mirrors {@code ImGui_ImplSDL3_NewFrame}.
     */
    public void newFrame() {
        final ImGuiIO io = ImGui.getIO();

        // Setup main viewport size (every frame to accommodate for window resizing)
        final int[] wh = getWindowSize();
        final float[] fbScale = getFramebufferScale(wh[0], wh[1]);
        io.setDisplaySize((float) wh[0], (float) wh[1]);
        io.setDisplayFramebufferScale(fbScale[0], fbScale[1]);

        // Setup time step
        if (frequency == 0L) {
            frequency = SDL_GetPerformanceFrequency();
        }
        long currentTime = SDL_GetPerformanceCounter();
        if (currentTime <= data.time) {
            currentTime = data.time + 1;
        }
        io.setDeltaTime(data.time > 0L ? (float) ((double) (currentTime - data.time) / (double) frequency) : (1.0f / 60.0f));
        data.time = currentTime;

        if (data.mousePendingLeaveFrame != 0 && data.mousePendingLeaveFrame >= ImGui.getFrameCount() && data.mouseButtonsDown == 0) {
            data.mouseWindowID = 0;
            data.mousePendingLeaveFrame = 0;
            io.addMousePosEvent(-Float.MAX_VALUE, -Float.MAX_VALUE);
        }

        updateMouseData(io);
        updateMouseCursor(io);
        // TODO: not ported — IME update (UpdateIme) requires platform IO callback wiring.
        updateGamepads(io);
    }

    private long frequency;

    private int[] getWindowSize() {
        final int[] wh = new int[2];
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pw = stack.mallocInt(1);
            final IntBuffer ph = stack.mallocInt(1);
            SDL_GetWindowSize(data.window, pw, ph);
            wh[0] = pw.get(0);
            wh[1] = ph.get(0);
        }
        // SDL_WINDOW_MINIMIZED — when minimized, report 0,0
        if ((SDL_GetWindowFlags(data.window) & SDLWindowFlags.SDL_WINDOW_MINIMIZED) != 0L) {
            wh[0] = 0;
            wh[1] = 0;
        }
        return wh;
    }

    private float[] getFramebufferScale(final int w, final int h) {
        final int dw;
        final int dh;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pdw = stack.mallocInt(1);
            final IntBuffer pdh = stack.mallocInt(1);
            SDL_GetWindowSizeInPixels(data.window, pdw, pdh);
            dw = pdw.get(0);
            dh = pdh.get(0);
        }
        final float fx = w > 0 ? (float) dw / (float) w : 1.0f;
        final float fy = h > 0 ? (float) dh / (float) h : 1.0f;
        return new float[]{fx, fy};
    }

    private void updateMouseData(final ImGuiIO io) {
        final boolean isAppFocused = (SDL_GetWindowFlags(data.window) & SDL_WINDOW_INPUT_FOCUS) != 0L;
        if (isAppFocused) {
            if (io.getWantSetMousePos()) {
                SDL_WarpMouseInWindow(data.window, io.getMousePosX(), io.getMousePosY());
            }
            // TODO: not ported — global-state fallback (SDL_GetGlobalMouseState) for unhovered focus
            // because LWJGL's PointerBuffer/IntBuffer translation is non-trivial and the path
            // is only required when mouseCanUseGlobalState is true (multi-viewport regime).
        }
    }

    private void updateMouseCursor(final ImGuiIO io) {
        if ((io.getConfigFlags() & ImGuiConfigFlags.NoMouseCursorChange) != 0) {
            return;
        }
        final int imguiCursor = ImGui.getMouseCursor();
        if (io.getMouseDrawCursor() || imguiCursor == ImGuiMouseCursor.None) {
            SDL_HideCursor();
        } else {
            final long expected = data.mouseCursors[imguiCursor] != 0L
                    ? data.mouseCursors[imguiCursor]
                    : data.mouseCursors[ImGuiMouseCursor.Arrow];
            if (data.mouseLastCursor != expected) {
                SDL_SetCursor(expected);
                data.mouseLastCursor = expected;
            }
            SDL_ShowCursor();
        }
    }

    private void closeGamepads() {
        if (data.gamepadMode != GAMEPAD_MODE_MANUAL) {
            for (final Long g : data.gamepads) {
                SDL_CloseGamepad(g);
            }
        }
        data.gamepads.clear();
    }

    private void updateGamepads(final ImGuiIO io) {
        // Update list of gamepads to use
        if (data.wantUpdateGamepadsList && data.gamepadMode != GAMEPAD_MODE_MANUAL) {
            closeGamepads();
            final IntBuffer ids = SDL_GetGamepads();
            if (ids != null) {
                while (ids.hasRemaining()) {
                    final int id = ids.get();
                    final long gp = SDL_OpenGamepad(id);
                    if (gp != 0L) {
                        data.gamepads.add(gp);
                        if (data.gamepadMode == GAMEPAD_MODE_AUTO_FIRST) {
                            break;
                        }
                    }
                }
            }
            data.wantUpdateGamepadsList = false;
        }

        io.removeBackendFlags(ImGuiBackendFlags.HasGamepad);
        if (data.gamepads.isEmpty()) {
            return;
        }
        io.addBackendFlags(ImGuiBackendFlags.HasGamepad);

        final int thumbDeadZone = 8000;
        updateGamepadButton(io, ImGuiKey.GamepadStart, SDL_GAMEPAD_BUTTON_START);
        updateGamepadButton(io, ImGuiKey.GamepadBack, SDL_GAMEPAD_BUTTON_BACK);
        updateGamepadButton(io, ImGuiKey.GamepadFaceLeft, SDL_GAMEPAD_BUTTON_WEST);
        updateGamepadButton(io, ImGuiKey.GamepadFaceRight, SDL_GAMEPAD_BUTTON_EAST);
        updateGamepadButton(io, ImGuiKey.GamepadFaceUp, SDL_GAMEPAD_BUTTON_NORTH);
        updateGamepadButton(io, ImGuiKey.GamepadFaceDown, SDL_GAMEPAD_BUTTON_SOUTH);
        updateGamepadButton(io, ImGuiKey.GamepadDpadLeft, SDL_GAMEPAD_BUTTON_DPAD_LEFT);
        updateGamepadButton(io, ImGuiKey.GamepadDpadRight, SDL_GAMEPAD_BUTTON_DPAD_RIGHT);
        updateGamepadButton(io, ImGuiKey.GamepadDpadUp, SDL_GAMEPAD_BUTTON_DPAD_UP);
        updateGamepadButton(io, ImGuiKey.GamepadDpadDown, SDL_GAMEPAD_BUTTON_DPAD_DOWN);
        updateGamepadButton(io, ImGuiKey.GamepadL1, SDL_GAMEPAD_BUTTON_LEFT_SHOULDER);
        updateGamepadButton(io, ImGuiKey.GamepadR1, SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER);
        updateGamepadAnalog(io, ImGuiKey.GamepadL2, SDL_GAMEPAD_AXIS_LEFT_TRIGGER, 0.0f, 32767.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadR2, SDL_GAMEPAD_AXIS_RIGHT_TRIGGER, 0.0f, 32767.0f);
        updateGamepadButton(io, ImGuiKey.GamepadL3, SDL_GAMEPAD_BUTTON_LEFT_STICK);
        updateGamepadButton(io, ImGuiKey.GamepadR3, SDL_GAMEPAD_BUTTON_RIGHT_STICK);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickLeft, SDL_GAMEPAD_AXIS_LEFTX, -thumbDeadZone, -32768.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickRight, SDL_GAMEPAD_AXIS_LEFTX, thumbDeadZone, 32767.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickUp, SDL_GAMEPAD_AXIS_LEFTY, -thumbDeadZone, -32768.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickDown, SDL_GAMEPAD_AXIS_LEFTY, thumbDeadZone, 32767.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickLeft, SDL_GAMEPAD_AXIS_RIGHTX, -thumbDeadZone, -32768.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickRight, SDL_GAMEPAD_AXIS_RIGHTX, thumbDeadZone, 32767.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickUp, SDL_GAMEPAD_AXIS_RIGHTY, -thumbDeadZone, -32768.0f);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickDown, SDL_GAMEPAD_AXIS_RIGHTY, thumbDeadZone, 32767.0f);
    }

    private void updateGamepadButton(final ImGuiIO io, final int key, final int sdlButton) {
        boolean merged = false;
        for (final Long g : data.gamepads) {
            merged |= SDL_GetGamepadButton(g, sdlButton);
        }
        io.addKeyEvent(key, merged);
    }

    private void updateGamepadAnalog(final ImGuiIO io, final int key, final int sdlAxis, final float v0, final float v1) {
        float merged = 0.0f;
        for (final Long g : data.gamepads) {
            final float vn = saturate((SDL_GetGamepadAxis(g, sdlAxis) - v0) / (v1 - v0));
            if (merged < vn) {
                merged = vn;
            }
        }
        io.addKeyAnalogEvent(key, merged > 0.1f, merged);
    }

    private static float saturate(final float v) {
        return v < 0.0f ? 0.0f : (v > 1.0f ? 1.0f : v);
    }

    private static void updateKeyModifiers(final int sdlKeyMods) {
        final ImGuiIO io = ImGui.getIO();
        io.addKeyEvent(ImGuiKey.ImGuiMod_Ctrl, (sdlKeyMods & SDL_KMOD_CTRL) != 0);
        io.addKeyEvent(ImGuiKey.ImGuiMod_Shift, (sdlKeyMods & SDL_KMOD_SHIFT) != 0);
        io.addKeyEvent(ImGuiKey.ImGuiMod_Alt, (sdlKeyMods & SDL_KMOD_ALT) != 0);
        io.addKeyEvent(ImGuiKey.ImGuiMod_Super, (sdlKeyMods & SDL_KMOD_GUI) != 0);
    }

    /**
     * Mirrors {@code ImGui_ImplSDL3_KeyEventToImGuiKey}. Public so external code can reuse the mapping.
     */
    @SuppressWarnings({"checkstyle:CyclomaticComplexity", "checkstyle:JavaNCSS", "checkstyle:MethodLength"})
    public static int keyEventToImGuiKey(final int keycode, final int scancode) {
        // Keypad doesn't have individual key values in SDL3
        switch (scancode) {
            case SDL_SCANCODE_KP_0: return ImGuiKey.Keypad0;
            case SDL_SCANCODE_KP_1: return ImGuiKey.Keypad1;
            case SDL_SCANCODE_KP_2: return ImGuiKey.Keypad2;
            case SDL_SCANCODE_KP_3: return ImGuiKey.Keypad3;
            case SDL_SCANCODE_KP_4: return ImGuiKey.Keypad4;
            case SDL_SCANCODE_KP_5: return ImGuiKey.Keypad5;
            case SDL_SCANCODE_KP_6: return ImGuiKey.Keypad6;
            case SDL_SCANCODE_KP_7: return ImGuiKey.Keypad7;
            case SDL_SCANCODE_KP_8: return ImGuiKey.Keypad8;
            case SDL_SCANCODE_KP_9: return ImGuiKey.Keypad9;
            case SDL_SCANCODE_KP_PERIOD: return ImGuiKey.KeypadDecimal;
            case SDL_SCANCODE_KP_DIVIDE: return ImGuiKey.KeypadDivide;
            case SDL_SCANCODE_KP_MULTIPLY: return ImGuiKey.KeypadMultiply;
            case SDL_SCANCODE_KP_MINUS: return ImGuiKey.KeypadSubtract;
            case SDL_SCANCODE_KP_PLUS: return ImGuiKey.KeypadAdd;
            case SDL_SCANCODE_KP_ENTER: return ImGuiKey.KeypadEnter;
            case SDL_SCANCODE_KP_EQUALS: return ImGuiKey.KeypadEqual;
            default: break;
        }
        switch (keycode) {
            case SDLK_TAB: return ImGuiKey.Tab;
            case SDLK_LEFT: return ImGuiKey.LeftArrow;
            case SDLK_RIGHT: return ImGuiKey.RightArrow;
            case SDLK_UP: return ImGuiKey.UpArrow;
            case SDLK_DOWN: return ImGuiKey.DownArrow;
            case SDLK_PAGEUP: return ImGuiKey.PageUp;
            case SDLK_PAGEDOWN: return ImGuiKey.PageDown;
            case SDLK_HOME: return ImGuiKey.Home;
            case SDLK_END: return ImGuiKey.End;
            case SDLK_INSERT: return ImGuiKey.Insert;
            case SDLK_DELETE: return ImGuiKey.Delete;
            case SDLK_BACKSPACE: return ImGuiKey.Backspace;
            case SDLK_SPACE: return ImGuiKey.Space;
            case SDLK_RETURN: return ImGuiKey.Enter;
            case SDLK_ESCAPE: return ImGuiKey.Escape;
            case SDLK_COMMA: return ImGuiKey.Comma;
            case SDLK_PERIOD: return ImGuiKey.Period;
            case SDLK_SEMICOLON: return ImGuiKey.Semicolon;
            case SDLK_CAPSLOCK: return ImGuiKey.CapsLock;
            case SDLK_SCROLLLOCK: return ImGuiKey.ScrollLock;
            case SDLK_NUMLOCKCLEAR: return ImGuiKey.NumLock;
            case SDLK_PRINTSCREEN: return ImGuiKey.PrintScreen;
            case SDLK_PAUSE: return ImGuiKey.Pause;
            case SDLK_LCTRL: return ImGuiKey.LeftCtrl;
            case SDLK_LSHIFT: return ImGuiKey.LeftShift;
            case SDLK_LALT: return ImGuiKey.LeftAlt;
            case SDLK_LGUI: return ImGuiKey.LeftSuper;
            case SDLK_RCTRL: return ImGuiKey.RightCtrl;
            case SDLK_RSHIFT: return ImGuiKey.RightShift;
            case SDLK_RALT: return ImGuiKey.RightAlt;
            case SDLK_RGUI: return ImGuiKey.RightSuper;
            case SDLK_APPLICATION: return ImGuiKey.Menu;
            case SDLK_0: return ImGuiKey._0;
            case SDLK_1: return ImGuiKey._1;
            case SDLK_2: return ImGuiKey._2;
            case SDLK_3: return ImGuiKey._3;
            case SDLK_4: return ImGuiKey._4;
            case SDLK_5: return ImGuiKey._5;
            case SDLK_6: return ImGuiKey._6;
            case SDLK_7: return ImGuiKey._7;
            case SDLK_8: return ImGuiKey._8;
            case SDLK_9: return ImGuiKey._9;
            case SDLK_A: return ImGuiKey.A;
            case SDLK_B: return ImGuiKey.B;
            case SDLK_C: return ImGuiKey.C;
            case SDLK_D: return ImGuiKey.D;
            case SDLK_E: return ImGuiKey.E;
            case SDLK_F: return ImGuiKey.F;
            case SDLK_G: return ImGuiKey.G;
            case SDLK_H: return ImGuiKey.H;
            case SDLK_I: return ImGuiKey.I;
            case SDLK_J: return ImGuiKey.J;
            case SDLK_K: return ImGuiKey.K;
            case SDLK_L: return ImGuiKey.L;
            case SDLK_M: return ImGuiKey.M;
            case SDLK_N: return ImGuiKey.N;
            case SDLK_O: return ImGuiKey.O;
            case SDLK_P: return ImGuiKey.P;
            case SDLK_Q: return ImGuiKey.Q;
            case SDLK_R: return ImGuiKey.R;
            case SDLK_S: return ImGuiKey.S;
            case SDLK_T: return ImGuiKey.T;
            case SDLK_U: return ImGuiKey.U;
            case SDLK_V: return ImGuiKey.V;
            case SDLK_W: return ImGuiKey.W;
            case SDLK_X: return ImGuiKey.X;
            case SDLK_Y: return ImGuiKey.Y;
            case SDLK_Z: return ImGuiKey.Z;
            case SDLK_F1: return ImGuiKey.F1;
            case SDLK_F2: return ImGuiKey.F2;
            case SDLK_F3: return ImGuiKey.F3;
            case SDLK_F4: return ImGuiKey.F4;
            case SDLK_F5: return ImGuiKey.F5;
            case SDLK_F6: return ImGuiKey.F6;
            case SDLK_F7: return ImGuiKey.F7;
            case SDLK_F8: return ImGuiKey.F8;
            case SDLK_F9: return ImGuiKey.F9;
            case SDLK_F10: return ImGuiKey.F10;
            case SDLK_F11: return ImGuiKey.F11;
            case SDLK_F12: return ImGuiKey.F12;
            case SDLK_F13: return ImGuiKey.F13;
            case SDLK_F14: return ImGuiKey.F14;
            case SDLK_F15: return ImGuiKey.F15;
            case SDLK_F16: return ImGuiKey.F16;
            case SDLK_F17: return ImGuiKey.F17;
            case SDLK_F18: return ImGuiKey.F18;
            case SDLK_F19: return ImGuiKey.F19;
            case SDLK_F20: return ImGuiKey.F20;
            case SDLK_F21: return ImGuiKey.F21;
            case SDLK_F22: return ImGuiKey.F22;
            case SDLK_F23: return ImGuiKey.F23;
            case SDLK_F24: return ImGuiKey.F24;
            case SDLK_AC_BACK: return ImGuiKey.AppBack;
            case SDLK_AC_FORWARD: return ImGuiKey.AppForward;
            default: break;
        }
        // Fallback to scancode
        switch (scancode) {
            case SDL_SCANCODE_GRAVE: return ImGuiKey.GraveAccent;
            case SDL_SCANCODE_MINUS: return ImGuiKey.Minus;
            case SDL_SCANCODE_EQUALS: return ImGuiKey.Equal;
            case SDL_SCANCODE_LEFTBRACKET: return ImGuiKey.LeftBracket;
            case SDL_SCANCODE_RIGHTBRACKET: return ImGuiKey.RightBracket;
            case SDL_SCANCODE_NONUSBACKSLASH: return ImGuiKey.Oem102;
            case SDL_SCANCODE_BACKSLASH: return ImGuiKey.Backslash;
            case SDL_SCANCODE_SEMICOLON: return ImGuiKey.Semicolon;
            case SDL_SCANCODE_APOSTROPHE: return ImGuiKey.Apostrophe;
            case SDL_SCANCODE_COMMA: return ImGuiKey.Comma;
            case SDL_SCANCODE_PERIOD: return ImGuiKey.Period;
            case SDL_SCANCODE_SLASH: return ImGuiKey.Slash;
            default: break;
        }
        return ImGuiKey.None;
    }

    /**
     * Local mirror of {@code SDL_WINDOW_MINIMIZED} to avoid a static import collision; the constant
     * is just a long bit-flag from {@link org.lwjgl.sdl.SDLVideo}.
     */
    private static final class SDLWindowFlags {
        static final long SDL_WINDOW_MINIMIZED = org.lwjgl.sdl.SDLVideo.SDL_WINDOW_MINIMIZED;

        private SDLWindowFlags() {
        }
    }

    /**
     * @return native {@code SDL_Window*} handle this backend was initialized with
     */
    public final long getWindow() {
        return data.window;
    }
}
