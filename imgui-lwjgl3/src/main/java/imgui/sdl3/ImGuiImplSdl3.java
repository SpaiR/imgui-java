package imgui.sdl3;

// this is the dumbest check i have ever seen
// without star imports, there is around 250 lines of imports
// why do java people do this to themselves
// CHECKSTYLE:OFF AvoidStarImport
import static org.lwjgl.sdl.SDLClipboard.SDL_GetClipboardText;
import static org.lwjgl.sdl.SDLClipboard.SDL_HasClipboardText;
import static org.lwjgl.sdl.SDLClipboard.SDL_SetClipboardText;
import static org.lwjgl.sdl.SDLEvents.*;
import static org.lwjgl.sdl.SDLGamepad.*;
import static org.lwjgl.sdl.SDLHints.SDL_HINT_MOUSE_AUTO_CAPTURE;
import static org.lwjgl.sdl.SDLHints.SDL_HINT_MOUSE_FOCUS_CLICKTHROUGH;
import static org.lwjgl.sdl.SDLHints.SDL_SetHint;
import static org.lwjgl.sdl.SDLKeyboard.SDL_GetKeyboardFocus;
import static org.lwjgl.sdl.SDLKeycode.*;
import static org.lwjgl.sdl.SDLMouse.*;
import static org.lwjgl.sdl.SDLProperties.SDL_GetPointerProperty;
import static org.lwjgl.sdl.SDLScancode.*;
import static org.lwjgl.sdl.SDLTimer.SDL_GetPerformanceCounter;
import static org.lwjgl.sdl.SDLTimer.SDL_GetPerformanceFrequency;
import static org.lwjgl.sdl.SDLTouch.SDL_TOUCH_MOUSEID;
import static org.lwjgl.sdl.SDLVideo.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import imgui.*;
import imgui.callback.*;
import imgui.flag.*;
import imgui.lwjgl3.glfw.ImGuiImplGlfwNative;
import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.sdl.SDL_Rect;
import org.lwjgl.system.MemoryStack;
// CHECKSTYLE:ON AvoidStarImport

@SuppressWarnings({ "checkstyle:DesignForExtension" })
public class ImGuiImplSdl3 {
    protected static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_WINDOWS = OS.contains("win");
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");

    private static class Data {
        long window = -1;
        long windowID = -1;
        long time = 0;
        boolean useVulkan;
        String clipboardTextData;

        // mouse handling
        long mouseWindowID;
        int mouseButtonsDown;
        long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
        long mouseLastCursor;
        int mousePendingLeaveFrame;
        boolean mouseCanUseGlobalState;
        MouseCaptureMode mouseCaptureMode;

        // gamepad handling
        List<Long> gamepads;
        GamepadMode gamepadMode;
        boolean wantUpdateGamepadsList;

        boolean wantUpdateMonitors;
    }

    private static class ViewportData {
        long window;
        long windowID;
        boolean windowOwned;
        long glContext;
    }

    private enum MouseCaptureMode {
        Enabled, EnabledAfterDrag, Disabled
    }

    private enum GamepadMode {
        AutoFirst, AutoAll, Manual
    }

    private Data data;

    private ImStrSupplier getClipboardText() {
        return new ImStrSupplier() {
            @Override
            public String get() {
                if (SDL_HasClipboardText()) {
                    data.clipboardTextData = SDL_GetClipboardText();
                } else {
                    data.clipboardTextData = null;
                }
                return data.clipboardTextData;
            }
        };
    }

    private ImStrConsumer setClipboardText() {
        return new ImStrConsumer() {
            @Override
            public void accept(final String text) {
                SDL_SetClipboardText(text);
            }
        };
    }

    private ImGuiViewport getViewportForWindowID(final long windowId) {
        return (windowId == data.windowID) ? ImGui.getMainViewport() : null;
    }

    private int keyEventToImGuiKey(final int keycode, final int scancode) {
        // Keypad doesn't have individual key values in SDL3
        switch (scancode) {
            case SDL_SCANCODE_KP_0:
                return ImGuiKey.Keypad0;
            case SDL_SCANCODE_KP_1:
                return ImGuiKey.Keypad1;
            case SDL_SCANCODE_KP_2:
                return ImGuiKey.Keypad2;
            case SDL_SCANCODE_KP_3:
                return ImGuiKey.Keypad3;
            case SDL_SCANCODE_KP_4:
                return ImGuiKey.Keypad4;
            case SDL_SCANCODE_KP_5:
                return ImGuiKey.Keypad5;
            case SDL_SCANCODE_KP_6:
                return ImGuiKey.Keypad6;
            case SDL_SCANCODE_KP_7:
                return ImGuiKey.Keypad7;
            case SDL_SCANCODE_KP_8:
                return ImGuiKey.Keypad8;
            case SDL_SCANCODE_KP_9:
                return ImGuiKey.Keypad9;
            case SDL_SCANCODE_KP_PERIOD:
                return ImGuiKey.KeypadDecimal;
            case SDL_SCANCODE_KP_DIVIDE:
                return ImGuiKey.KeypadDivide;
            case SDL_SCANCODE_KP_MULTIPLY:
                return ImGuiKey.KeypadMultiply;
            case SDL_SCANCODE_KP_MINUS:
                return ImGuiKey.KeypadSubtract;
            case SDL_SCANCODE_KP_PLUS:
                return ImGuiKey.KeypadAdd;
            case SDL_SCANCODE_KP_ENTER:
                return ImGuiKey.KeypadEnter;
            case SDL_SCANCODE_KP_EQUALS:
                return ImGuiKey.KeypadEqual;
            default:
                break;
        }
        switch (keycode) {
            case SDLK_TAB:
                return ImGuiKey.Tab;
            case SDLK_LEFT:
                return ImGuiKey.LeftArrow;
            case SDLK_RIGHT:
                return ImGuiKey.RightArrow;
            case SDLK_UP:
                return ImGuiKey.UpArrow;
            case SDLK_DOWN:
                return ImGuiKey.DownArrow;
            case SDLK_PAGEUP:
                return ImGuiKey.PageUp;
            case SDLK_PAGEDOWN:
                return ImGuiKey.PageDown;
            case SDLK_HOME:
                return ImGuiKey.Home;
            case SDLK_END:
                return ImGuiKey.End;
            case SDLK_INSERT:
                return ImGuiKey.Insert;
            case SDLK_DELETE:
                return ImGuiKey.Delete;
            case SDLK_BACKSPACE:
                return ImGuiKey.Backspace;
            case SDLK_SPACE:
                return ImGuiKey.Space;
            case SDLK_RETURN:
                return ImGuiKey.Enter;
            case SDLK_ESCAPE:
                return ImGuiKey.Escape;
            // case SDLK_APOSTROPHE: return ImGuiKey.Apostrophe;
            case SDLK_COMMA:
                return ImGuiKey.Comma;
            // case SDLK_MINUS: return ImGuiKey.Minus;
            case SDLK_PERIOD:
                return ImGuiKey.Period;
            // case SDLK_SLASH: return ImGuiKey.Slash;
            case SDLK_SEMICOLON:
                return ImGuiKey.Semicolon;
            // case SDLK_EQUALS: return ImGuiKey.Equal;
            // case SDLK_LEFTBRACKET: return ImGuiKey.LeftBracket;
            // case SDLK_BACKSLASH: return ImGuiKey.Backslash;
            // case SDLK_RIGHTBRACKET: return ImGuiKey.RightBracket;
            // case SDLK_GRAVE: return ImGuiKey.GraveAccent;
            case SDLK_CAPSLOCK:
                return ImGuiKey.CapsLock;
            case SDLK_SCROLLLOCK:
                return ImGuiKey.ScrollLock;
            case SDLK_NUMLOCKCLEAR:
                return ImGuiKey.NumLock;
            case SDLK_PRINTSCREEN:
                return ImGuiKey.PrintScreen;
            case SDLK_PAUSE:
                return ImGuiKey.Pause;
            case SDLK_LCTRL:
                return ImGuiKey.LeftCtrl;
            case SDLK_LSHIFT:
                return ImGuiKey.LeftShift;
            case SDLK_LALT:
                return ImGuiKey.LeftAlt;
            case SDLK_LGUI:
                return ImGuiKey.LeftSuper;
            case SDLK_RCTRL:
                return ImGuiKey.RightCtrl;
            case SDLK_RSHIFT:
                return ImGuiKey.RightShift;
            case SDLK_RALT:
                return ImGuiKey.RightAlt;
            case SDLK_RGUI:
                return ImGuiKey.RightSuper;
            case SDLK_APPLICATION:
                return ImGuiKey.Menu;
            case SDLK_0:
                return ImGuiKey._0;
            case SDLK_1:
                return ImGuiKey._1;
            case SDLK_2:
                return ImGuiKey._2;
            case SDLK_3:
                return ImGuiKey._3;
            case SDLK_4:
                return ImGuiKey._4;
            case SDLK_5:
                return ImGuiKey._5;
            case SDLK_6:
                return ImGuiKey._6;
            case SDLK_7:
                return ImGuiKey._7;
            case SDLK_8:
                return ImGuiKey._8;
            case SDLK_9:
                return ImGuiKey._9;
            case SDLK_A:
                return ImGuiKey.A;
            case SDLK_B:
                return ImGuiKey.B;
            case SDLK_C:
                return ImGuiKey.C;
            case SDLK_D:
                return ImGuiKey.D;
            case SDLK_E:
                return ImGuiKey.E;
            case SDLK_F:
                return ImGuiKey.F;
            case SDLK_G:
                return ImGuiKey.G;
            case SDLK_H:
                return ImGuiKey.H;
            case SDLK_I:
                return ImGuiKey.I;
            case SDLK_J:
                return ImGuiKey.J;
            case SDLK_K:
                return ImGuiKey.K;
            case SDLK_L:
                return ImGuiKey.L;
            case SDLK_M:
                return ImGuiKey.M;
            case SDLK_N:
                return ImGuiKey.N;
            case SDLK_O:
                return ImGuiKey.O;
            case SDLK_P:
                return ImGuiKey.P;
            case SDLK_Q:
                return ImGuiKey.Q;
            case SDLK_R:
                return ImGuiKey.R;
            case SDLK_S:
                return ImGuiKey.S;
            case SDLK_T:
                return ImGuiKey.T;
            case SDLK_U:
                return ImGuiKey.U;
            case SDLK_V:
                return ImGuiKey.V;
            case SDLK_W:
                return ImGuiKey.W;
            case SDLK_X:
                return ImGuiKey.X;
            case SDLK_Y:
                return ImGuiKey.Y;
            case SDLK_Z:
                return ImGuiKey.Z;
            case SDLK_F1:
                return ImGuiKey.F1;
            case SDLK_F2:
                return ImGuiKey.F2;
            case SDLK_F3:
                return ImGuiKey.F3;
            case SDLK_F4:
                return ImGuiKey.F4;
            case SDLK_F5:
                return ImGuiKey.F5;
            case SDLK_F6:
                return ImGuiKey.F6;
            case SDLK_F7:
                return ImGuiKey.F7;
            case SDLK_F8:
                return ImGuiKey.F8;
            case SDLK_F9:
                return ImGuiKey.F9;
            case SDLK_F10:
                return ImGuiKey.F10;
            case SDLK_F11:
                return ImGuiKey.F11;
            case SDLK_F12:
                return ImGuiKey.F12;
            case SDLK_F13:
                return ImGuiKey.F13;
            case SDLK_F14:
                return ImGuiKey.F14;
            case SDLK_F15:
                return ImGuiKey.F15;
            case SDLK_F16:
                return ImGuiKey.F16;
            case SDLK_F17:
                return ImGuiKey.F17;
            case SDLK_F18:
                return ImGuiKey.F18;
            case SDLK_F19:
                return ImGuiKey.F19;
            case SDLK_F20:
                return ImGuiKey.F20;
            case SDLK_F21:
                return ImGuiKey.F21;
            case SDLK_F22:
                return ImGuiKey.F22;
            case SDLK_F23:
                return ImGuiKey.F23;
            case SDLK_F24:
                return ImGuiKey.F24;
            case SDLK_AC_BACK:
                return ImGuiKey.AppBack;
            case SDLK_AC_FORWARD:
                return ImGuiKey.AppForward;
            default:
                break;
        }

        // Fallback to scancode
        switch (scancode) {
            case SDL_SCANCODE_GRAVE:
                return ImGuiKey.GraveAccent;
            case SDL_SCANCODE_MINUS:
                return ImGuiKey.Minus;
            case SDL_SCANCODE_EQUALS:
                return ImGuiKey.Equal;
            case SDL_SCANCODE_LEFTBRACKET:
                return ImGuiKey.LeftBracket;
            case SDL_SCANCODE_RIGHTBRACKET:
                return ImGuiKey.RightBracket;
            case SDL_SCANCODE_BACKSLASH:
                return ImGuiKey.Backslash;
            case SDL_SCANCODE_SEMICOLON:
                return ImGuiKey.Semicolon;
            case SDL_SCANCODE_APOSTROPHE:
                return ImGuiKey.Apostrophe;
            case SDL_SCANCODE_COMMA:
                return ImGuiKey.Comma;
            case SDL_SCANCODE_PERIOD:
                return ImGuiKey.Period;
            case SDL_SCANCODE_SLASH:
                return ImGuiKey.Slash;
            default:
                break;
        }
        return ImGuiKey.None;
    }

    private void updateKeyModifiers(final int sdlKeyMods) {
        final ImGuiIO io = ImGui.getIO();
        io.addKeyEvent(ImGuiKey.ModCtrl, (sdlKeyMods & SDL_KMOD_CTRL) != 0);
        io.addKeyEvent(ImGuiKey.ModShift, (sdlKeyMods & SDL_KMOD_SHIFT) != 0);
        io.addKeyEvent(ImGuiKey.ModAlt, (sdlKeyMods & SDL_KMOD_ALT) != 0);
        io.addKeyEvent(ImGuiKey.ModSuper, (sdlKeyMods & SDL_KMOD_GUI) != 0);
    }

    public boolean processEvent(final SDL_Event event) {
        if (data == null) {
            // "Context or backend not initialized! Did you call ImGui_ImplSDL3_Init()?"
            return false;
        }
        final ImGuiIO io = ImGui.getIO();

        switch (event.type()) {
            case SDL_EVENT_MOUSE_MOTION:
                if (getViewportForWindowID(event.motion().windowID()) == null) {
                    return false;
                }
                final ImVec2 mousePos = new ImVec2(event.motion().x(), event.motion().y());
                io.addMouseSourceEvent(event.motion().which() == SDL_TOUCH_MOUSEID ? ImGuiMouseSource.TouchScreen
                        : ImGuiMouseSource.Mouse);
                io.addMousePosEvent(mousePos.x, mousePos.y);
                return true;
            case SDL_EVENT_MOUSE_WHEEL:
                if (getViewportForWindowID(event.wheel().windowID()) == null) {
                    return false;
                }
                final float wheelX = -event.wheel().x();
                final float wheelY = event.wheel().y();
                io.addMouseSourceEvent(event.wheel().which() == SDL_TOUCH_MOUSEID ? ImGuiMouseSource.TouchScreen
                        : ImGuiMouseSource.Mouse);
                io.addMouseWheelEvent(wheelX, wheelY);
                return true;
            case SDL_EVENT_MOUSE_BUTTON_DOWN:
            case SDL_EVENT_MOUSE_BUTTON_UP:
                if (getViewportForWindowID(event.button().windowID()) == null) {
                    return false;
                }
                int mouseButton = -1;
                if (event.button().button() == SDL_BUTTON_LEFT) {
                    mouseButton = 0;
                }
                if (event.button().button() == SDL_BUTTON_RIGHT) {
                    mouseButton = 1;
                }
                if (event.button().button() == SDL_BUTTON_MIDDLE) {
                    mouseButton = 2;
                }
                if (event.button().button() == SDL_BUTTON_X1) {
                    mouseButton = 3;
                }
                if (event.button().button() == SDL_BUTTON_X2) {
                    mouseButton = 4;
                }
                if (mouseButton == -1) {
                    break;
                }
                io.addMouseSourceEvent(event.button().which() == SDL_TOUCH_MOUSEID ? ImGuiMouseSource.TouchScreen
                        : ImGuiMouseSource.Mouse);
                io.addMouseButtonEvent(mouseButton, (event.type() == SDL_EVENT_MOUSE_BUTTON_DOWN));
                data.mouseButtonsDown = (event.type() == SDL_EVENT_MOUSE_BUTTON_DOWN)
                        ? (data.mouseButtonsDown | (1 << mouseButton))
                        : (data.mouseButtonsDown & ~(1 << mouseButton));
                return true;
            case SDL_EVENT_TEXT_INPUT:
                if (getViewportForWindowID(event.text().windowID()) == null) {
                    return false;
                }
                io.addInputCharactersUTF8(event.text().text().toString());
                return true;
            case SDL_EVENT_KEY_DOWN:
            case SDL_EVENT_KEY_UP:
                if (getViewportForWindowID(event.key().windowID()) == null) {
                    return false;
                }
                updateKeyModifiers(event.key().mod());
                final int key = keyEventToImGuiKey(event.key().key(), event.key().scancode());
                io.addKeyEvent(key, (event.type() == SDL_EVENT_KEY_DOWN));
                io.setKeyEventNativeData(key, event.key().key(), event.key().scancode(), event.key().scancode());
                return true;
            case SDL_EVENT_DISPLAY_ORIENTATION:
            case SDL_EVENT_DISPLAY_ADDED:
            case SDL_EVENT_DISPLAY_REMOVED:
            case SDL_EVENT_DISPLAY_MOVED:
            case SDL_EVENT_DISPLAY_CONTENT_SCALE_CHANGED:
                data.wantUpdateMonitors = true;
                return true;
            case SDL_EVENT_WINDOW_MOUSE_ENTER:
                if (getViewportForWindowID(event.window().windowID()) == null) {
                    return false;
                }
                data.mouseWindowID = event.window().windowID();
                data.mousePendingLeaveFrame = 0;
                return true;
            // - In some cases, when detaching a window from main viewport SDL may send
            // SDL_WINDOWEVENT_ENTER one frame too late,
            // causing SDL_WINDOWEVENT_LEAVE on previous frame to interrupt drag operation
            // by clear mouse position. This is why
            // we delay process the SDL_WINDOWEVENT_LEAVE events by one frame. See issue
            // #5012 for details.
            case SDL_EVENT_WINDOW_MOUSE_LEAVE:
                if (getViewportForWindowID(event.window().windowID()) == null) {
                    return false;
                }
                data.mousePendingLeaveFrame = ImGui.getFrameCount() + 1;
                return true;
            case SDL_EVENT_WINDOW_FOCUS_GAINED:
            case SDL_EVENT_WINDOW_FOCUS_LOST:
                if (getViewportForWindowID(event.window().windowID()) == null) {
                    return false;
                }
                io.addFocusEvent(event.type() == SDL_EVENT_WINDOW_FOCUS_GAINED);
                return true;
            case SDL_EVENT_GAMEPAD_ADDED:
            case SDL_EVENT_GAMEPAD_REMOVED:
                data.wantUpdateGamepadsList = true;
                return true;
            default:
                break;
        }
        return false;
    }

    private void setupPlatformHandles(final ImGuiViewport viewport, final long window) {
        viewport.setPlatformHandle(SDL_GetWindowID(window));
        viewport.setPlatformHandleRaw(0);
        if (IS_WINDOWS) {
            viewport.setPlatformHandleRaw(
                    SDL_GetPointerProperty(SDL_GetWindowProperties(window), SDL_PROP_WINDOW_WIN32_HWND_POINTER, 0));
        } else if (IS_APPLE) {
            viewport.setPlatformHandleRaw(SDL_GetPointerProperty(SDL_GetWindowProperties(window),
                    SDL_PROP_WINDOW_COCOA_WINDOW_POINTER, 0));
        }
    }

    public boolean init(final long window) {
        final ImGuiIO io = ImGui.getIO();
        data = new Data();
        data.gamepads = new ArrayList<>();

        // Setup backend capabilities flags
        io.setBackendPlatformName("imgui_impl_sdl3");
        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors | ImGuiBackendFlags.HasSetMousePos);

        data.window = window;
        data.windowID = SDL_GetWindowID(window);

        // Check and store if we are on a SDL backend that supports
        // SDL_GetGlobalMouseState() and SDL_CaptureMouse()
        // ("wayland" and "rpi" don't support it, but we chose to use a white-list
        // instead of a black-list)
        data.mouseCanUseGlobalState = false;
        data.mouseCaptureMode = MouseCaptureMode.Disabled;
        // #if SDL_HAS_CAPTURE_AND_GLOBAL_MOUSE
        final String sdlBackend = SDL_GetCurrentVideoDriver();
        final String[] captureAndGlobalStateWhitelist = {"windows", "cocoa", "x11", "DIVE", "VMAN"};
        for (String item : captureAndGlobalStateWhitelist) {
            assert sdlBackend != null;
            if (sdlBackend.equals(item)) {
                data.mouseCanUseGlobalState = true;
                data.mouseCaptureMode = item.equals("x11") ? MouseCaptureMode.EnabledAfterDrag
                        : MouseCaptureMode.Enabled;
            }
        }

        io.setSetClipboardTextFn(setClipboardText());
        io.setGetClipboardTextFn(getClipboardText());

        // OpenInShellFn seems to not be available

        // Gamepad handling
        data.gamepadMode = GamepadMode.AutoFirst;
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
        /*
         * those two seem to be missing...
         * data.mouseCursors[ImGuiMouseCursor.Wait] =
         * SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_WAIT);
         * data.mouseCursors[ImGuiMouseCursor.Progress] =
         * SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_PROGRESS);
         */
        data.mouseCursors[ImGuiMouseCursor.NotAllowed] = SDL_CreateSystemCursor(SDL_SYSTEM_CURSOR_NOT_ALLOWED);

        // Set platform dependent data in viewport
        // Our mouse update function expect PlatformHandle to be filled for the main
        // viewport
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        setupPlatformHandles(mainViewport, window);

        // From 2.0.5: Set SDL hint to receive mouse click events on window focus,
        // otherwise SDL doesn't emit the event.
        // Without this, when clicking to gain focus, our widgets wouldn't activate even
        // though they showed as hovered.
        // (This is unfortunately a global SDL setting, so enabling it might have a
        // side-effect on your application.
        // It is unlikely to make a difference, but if your app absolutely needs to
        // ignore the initial on-focus click:
        // you can ignore SDL_EVENT_MOUSE_BUTTON_DOWN events coming right after a
        // SDL_EVENT_WINDOW_FOCUS_GAINED)
        SDL_SetHint(SDL_HINT_MOUSE_FOCUS_CLICKTHROUGH, "1");

        // From 2.0.22: Disable auto-capture, this is preventing drag and drop across
        // multiple windows (see #5710)
        SDL_SetHint(SDL_HINT_MOUSE_AUTO_CAPTURE, "0");

        return true;
    }

    public void shutdown() {
        if (data == null) {
            return;
        }
        final ImGuiIO io = ImGui.getIO();

        for (int cursorN = 0; cursorN < ImGuiMouseCursor.COUNT; cursorN++) {
            SDL_DestroyCursor(data.mouseCursors[cursorN]);
        }
        closeGamepads();

        io.setBackendPlatformName(null);
        io.removeBackendFlags(
                ImGuiBackendFlags.HasMouseCursors | ImGuiBackendFlags.HasSetMousePos | ImGuiBackendFlags.HasGamepad);
        data = null;
    }

    public void setMouseCaptureMode(final MouseCaptureMode mode) {
        if (mode == MouseCaptureMode.Disabled && data.mouseCaptureMode != MouseCaptureMode.Disabled) {
            SDL_CaptureMouse(false);
        }
        data.mouseCaptureMode = mode;
    }

    private void updateMouseData() {
        final ImGuiIO io = ImGui.getIO();

        // We forward mouse input when hovered or captured (via SDL_EVENT_MOUSE_MOTION)
        // or when focused (below)
        // - SDL_CaptureMouse() let the OS know e.g. that our drags can extend outside
        // of parent boundaries (we want updated position) and shouldn't trigger other
        // operations outside.
        // - Debuggers under Linux tends to leave captured mouse on break, which may be
        // very inconvenient, so to mitigate the issue on X11 we we wait until mouse has
        // moved to begin capture.
        if (data.mouseCaptureMode == MouseCaptureMode.Enabled) {
            SDL_CaptureMouse(data.mouseButtonsDown != 0);
        } else if (data.mouseCaptureMode == MouseCaptureMode.EnabledAfterDrag) {
            boolean wantCapture = false;
            for (int buttonN = 0; buttonN < ImGuiMouseButton.COUNT && !wantCapture; buttonN++) {
                if (ImGui.isMouseDragging(buttonN, 1.0f)) {
                    wantCapture = true;
                }
            }
            SDL_CaptureMouse(wantCapture);
        }

        final long focusedWindow = SDL_GetKeyboardFocus();
        final boolean isAppFocused = (data.window == focusedWindow);
        if (isAppFocused) {
            // (Optional) Set OS mouse position from Dear ImGui if requested (rarely used,
            // only when io.ConfigNavMoveSetMousePos is enabled by user)
            if (io.getWantSetMousePos()) {
                SDL_WarpMouseInWindow(data.window, io.getMousePos().x, io.getMousePos().y);
            }

            // (Optional) Fallback to provide unclamped mouse position when focused but not
            // hovered (SDL_EVENT_MOUSE_MOTION already provides this when hovered or
            // captured)
            // Note that SDL_GetGlobalMouseState() is in theory slow on X11, but this only
            // runs on rather specific cases. If a problem we may provide a way to opt-out
            // this feature.
            final long hoveredWindow = SDL_GetMouseFocus();
            final boolean isRelativeMouseMode = SDL_GetWindowRelativeMouseMode(data.window);
            if (hoveredWindow == 0 && data.mouseCanUseGlobalState && data.mouseButtonsDown == 0
                    && !isRelativeMouseMode) {
                // Single-viewport mode: mouse position in client window coordinates
                // (io.MousePos is (0,0) when the mouse is on the upper-left corner of the app
                // window)
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    final FloatBuffer mouseX = stack.mallocFloat(1);
                    final FloatBuffer mouseY = stack.mallocFloat(1);
                    final IntBuffer windowX = stack.mallocInt(1);
                    final IntBuffer windowY = stack.mallocInt(1);
                    SDL_GetGlobalMouseState(mouseX, mouseY);
                    SDL_GetWindowPosition(focusedWindow, windowX, windowY);
                    final ImVec2 mouse = new ImVec2(mouseX.get(0), mouseY.get(0));
                    final ImVec2 window = new ImVec2(windowX.get(0), windowY.get(0));
                    mouse.x -= window.x;
                    mouse.y -= window.y;
                    io.addMousePosEvent(mouse.x, mouse.y);
                }
            }
        }

        if (io.hasBackendFlags(ImGuiBackendFlags.HasMouseHoveredViewport)) {
            int mouseViewportID = 0;
            final long sdlMouseWindow = SDL_GetWindowFromID((int) data.mouseWindowID);
            if (sdlMouseWindow != 0) {
                final ImGuiViewport mouseViewport = ImGui.findViewportByPlatformHandle(sdlMouseWindow);
                if (mouseViewport != null) {
                    mouseViewportID = mouseViewport.getID();
                }
            }
            io.addMouseViewportEvent(mouseViewportID);
        }
    }

    private void updateMouseCursor() {
        final ImGuiIO io = ImGui.getIO();
        if ((io.getConfigFlags() & ImGuiConfigFlags.NoMouseCursorChange) != 0) {
            return;
        }

        final int imguiCursor = ImGui.getMouseCursor();
        if (io.getMouseDrawCursor() || imguiCursor == ImGuiMouseCursor.None) {
            // Hide OS mouse cursor if imgui is drawing it or if it wants no cursor
            SDL_HideCursor();
        } else {
            // Show OS mouse cursor
            final long expectedCursor = data.mouseCursors[imguiCursor] != 0 ? data.mouseCursors[imguiCursor]
                    : data.mouseCursors[ImGuiMouseCursor.Arrow];
            if (data.mouseLastCursor != expectedCursor) {
                SDL_SetCursor(expectedCursor); // SDL function doesn't have an early out (see #6113)
                data.mouseLastCursor = expectedCursor;
            }
            SDL_ShowCursor();
        }
    }

    private void closeGamepads() {
        if (data.gamepadMode != GamepadMode.Manual) {
            for (long gamepad : data.gamepads) {
                SDL_CloseGamepad(gamepad);
            }
        }
        data.gamepads.clear();
    }

    private void updateGamepadButton(final ImGuiIO io, final int key, final int buttonNo) {
        boolean mergedValue = false;
        for (long gamepad : data.gamepads) {
            mergedValue = SDL_GetGamepadButton(gamepad, buttonNo);
        }
        io.addKeyEvent(key, mergedValue);
    }

    private float saturate(final float v) {
        return v < 0.0f ? 0.0f : Math.min(v, 1.0f);
    }

    private void updateGamepadAnalog(final ImGuiIO io, final int key, final int axisNo, final float v0,
            final float v1) {
        float mergedValue = 0.0f;
        for (long gamepad : data.gamepads) {
            final float vn = saturate((float) (SDL_GetGamepadAxis(gamepad, axisNo) - v0) / (float) (v1 - v0));
            if (mergedValue < vn) {
                mergedValue = vn;
            }
        }
        io.addKeyAnalogEvent(key, mergedValue > 0.1f, mergedValue);
    }

    private void updateGamepads() {
        final ImGuiIO io = ImGui.getIO();

        // Update list of gamepads to use
        if (data.wantUpdateGamepadsList && data.gamepadMode != GamepadMode.Manual) {
            closeGamepads();
            final IntBuffer sdlGamepads = SDL_GetGamepads();
            if (sdlGamepads == null) {
                return;
            }
            for (int n = 0; n < sdlGamepads.capacity(); n++) {
                final long gamepad = SDL_OpenGamepad(sdlGamepads.get(n));
                if (gamepad != 0) {
                    data.gamepads.add(gamepad);
                    if (data.gamepadMode == GamepadMode.AutoFirst) {
                        break;
                    }
                }
            }
            data.wantUpdateGamepadsList = false;
        }

        io.removeBackendFlags(ImGuiBackendFlags.HasGamepad);
        if (data.gamepads.isEmpty()) {
            io.addKeyEvent(ImGuiKey.GamepadStart, false);
            io.addKeyEvent(ImGuiKey.GamepadBack, false);
            io.addKeyEvent(ImGuiKey.GamepadFaceLeft, false);
            io.addKeyEvent(ImGuiKey.GamepadFaceRight, false);
            io.addKeyEvent(ImGuiKey.GamepadFaceUp, false);
            io.addKeyEvent(ImGuiKey.GamepadFaceDown, false);
            io.addKeyEvent(ImGuiKey.GamepadDpadLeft, false);
            io.addKeyEvent(ImGuiKey.GamepadDpadRight, false);
            io.addKeyEvent(ImGuiKey.GamepadDpadUp, false);
            io.addKeyEvent(ImGuiKey.GamepadDpadDown, false);
            io.addKeyEvent(ImGuiKey.GamepadL1, false);
            io.addKeyEvent(ImGuiKey.GamepadR1, false);
            io.addKeyEvent(ImGuiKey.GamepadL2, false);
            io.addKeyEvent(ImGuiKey.GamepadR2, false);
            io.addKeyEvent(ImGuiKey.GamepadL3, false);
            io.addKeyEvent(ImGuiKey.GamepadR3, false);
            io.addKeyEvent(ImGuiKey.GamepadLStickLeft, false);
            io.addKeyEvent(ImGuiKey.GamepadLStickRight, false);
            io.addKeyEvent(ImGuiKey.GamepadLStickUp, false);
            io.addKeyEvent(ImGuiKey.GamepadLStickDown, false);
            io.addKeyEvent(ImGuiKey.GamepadRStickLeft, false);
            io.addKeyEvent(ImGuiKey.GamepadRStickRight, false);
            io.addKeyEvent(ImGuiKey.GamepadRStickUp, false);
            io.addKeyEvent(ImGuiKey.GamepadRStickDown, false);
            return;
        }
        io.addBackendFlags(ImGuiBackendFlags.HasGamepad);

        // Update gamepad inputs
        final int thumbDeadZone = 8000; // SDL_gamepad.h suggests using this value.
        updateGamepadButton(io, ImGuiKey.GamepadStart, SDL_GAMEPAD_BUTTON_START);
        updateGamepadButton(io, ImGuiKey.GamepadBack, SDL_GAMEPAD_BUTTON_BACK);
        updateGamepadButton(io, ImGuiKey.GamepadFaceLeft, SDL_GAMEPAD_BUTTON_WEST); // Xbox X, PS Square
        updateGamepadButton(io, ImGuiKey.GamepadFaceRight, SDL_GAMEPAD_BUTTON_EAST); // Xbox B, PS Circle
        updateGamepadButton(io, ImGuiKey.GamepadFaceUp, SDL_GAMEPAD_BUTTON_NORTH); // Xbox Y, PS Triangle
        updateGamepadButton(io, ImGuiKey.GamepadFaceDown, SDL_GAMEPAD_BUTTON_SOUTH); // Xbox A, PS Cross
        updateGamepadButton(io, ImGuiKey.GamepadDpadLeft, SDL_GAMEPAD_BUTTON_DPAD_LEFT);
        updateGamepadButton(io, ImGuiKey.GamepadDpadRight, SDL_GAMEPAD_BUTTON_DPAD_RIGHT);
        updateGamepadButton(io, ImGuiKey.GamepadDpadUp, SDL_GAMEPAD_BUTTON_DPAD_UP);
        updateGamepadButton(io, ImGuiKey.GamepadDpadDown, SDL_GAMEPAD_BUTTON_DPAD_DOWN);
        updateGamepadButton(io, ImGuiKey.GamepadL1, SDL_GAMEPAD_BUTTON_LEFT_SHOULDER);
        updateGamepadButton(io, ImGuiKey.GamepadR1, SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER);
        updateGamepadAnalog(io, ImGuiKey.GamepadL2, SDL_GAMEPAD_AXIS_LEFT_TRIGGER, 0.0f, 32767);
        updateGamepadAnalog(io, ImGuiKey.GamepadR2, SDL_GAMEPAD_AXIS_RIGHT_TRIGGER, 0.0f, 32767);
        updateGamepadButton(io, ImGuiKey.GamepadL3, SDL_GAMEPAD_BUTTON_LEFT_STICK);
        updateGamepadButton(io, ImGuiKey.GamepadR3, SDL_GAMEPAD_BUTTON_RIGHT_STICK);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickLeft, SDL_GAMEPAD_AXIS_LEFTX, -thumbDeadZone, -32768);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickRight, SDL_GAMEPAD_AXIS_LEFTX, +thumbDeadZone, +32767);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickUp, SDL_GAMEPAD_AXIS_LEFTY, -thumbDeadZone, -32768);
        updateGamepadAnalog(io, ImGuiKey.GamepadLStickDown, SDL_GAMEPAD_AXIS_LEFTY, +thumbDeadZone, +32767);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickLeft, SDL_GAMEPAD_AXIS_RIGHTX, -thumbDeadZone, -32768);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickRight, SDL_GAMEPAD_AXIS_RIGHTX, +thumbDeadZone, +32767);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickUp, SDL_GAMEPAD_AXIS_RIGHTY, -thumbDeadZone, -32768);
        updateGamepadAnalog(io, ImGuiKey.GamepadRStickDown, SDL_GAMEPAD_AXIS_RIGHTY, +thumbDeadZone, +32767);
    }

    // slight api change, no out parameters in java
    private ImVec4 getWindowSizeAndFramebufferScale(final long window) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer w = stack.mallocInt(1);
            final IntBuffer h = stack.mallocInt(1);
            SDL_GetWindowSize(window, w, h);
            if ((SDL_GetWindowFlags(window) & SDL_WINDOW_MINIMIZED) != 0) {
                w.put(0, 0);
                h.put(0, 0);
            }

            float fbScaleX = 0.0f;
            float fbScaleY = 0.0f;
            if (IS_APPLE) {
                fbScaleX = SDL_GetWindowDisplayScale(window);
                fbScaleY = fbScaleX;
            } else {
                final IntBuffer displayW = stack.mallocInt(1);
                final IntBuffer displayH = stack.mallocInt(1);
                SDL_GetWindowSizeInPixels(window, displayW, displayH);
                fbScaleX = (w.get(0) > 0) ? (float) displayW.get(0) / (float) w.get(0) : 1.0f;
                fbScaleY = (h.get(0) > 0) ? (float) displayH.get(0) / (float) h.get(0) : 1.0f;
            }

            return new ImVec4(w.get(0), h.get(0), fbScaleX, fbScaleY);
        }
    }

    void updateMonitors() {
        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        platformIO.resizeMonitors(0);
        data.wantUpdateMonitors = false;

        final IntBuffer displays = SDL_GetDisplays();
        for (int n = 0; n < displays.capacity(); n++) {
            // Warning: the validity of monitor DPI information on Windows depends on the
            // application DPI awareness settings, which generally needs to be set in the
            // manifest or at runtime.
            final int displayID = displays.get(n);
            final SDL_Rect disp = null;
            SDL_GetDisplayBounds(displayID, disp);
            final SDL_Rect usable = null;
            SDL_GetDisplayUsableBounds(displayID, usable);

            final float mainPosX = disp.x();
            final float mainPosY = disp.y();
            final float workPosX = disp.x();
            final float workPosY = disp.y();

            final float mainSizeX = disp.w();
            final float mainSizeY = disp.h();
            final float workSizeX = disp.w();
            final float workSizeY = disp.h();

            final float dpiScale = SDL_GetDisplayContentScale(displayID);

            platformIO.pushMonitors(displayID, mainPosX, mainPosY, mainSizeX, mainSizeY, workPosX, workPosY,
                    workSizeX, workSizeY, dpiScale);
        }
    }

    public void newFrame() {
        if (data == null) {
            return;
        }
        final ImGuiIO io = ImGui.getIO();

        // Setup main viewport size (every frame to accommodate for window resizing)
        final ImVec4 sizeScale = getWindowSizeAndFramebufferScale(data.window);
        final ImVec2 size = new ImVec2(sizeScale.x, sizeScale.y);
        final ImVec2 scale = new ImVec2(sizeScale.z, sizeScale.w);
        io.setDisplaySize(size);
        io.setDisplayFramebufferScale(scale);

        if (data.wantUpdateMonitors) {
            updateMonitors();
        }

        // Setup time step (we could also use SDL_GetTicksNS() available since SDL3)
        // (Accept SDL_GetPerformanceCounter() not returning a monotonically increasing
        // value. Happens in VMs and Emscripten, see #6189, #6114, #3644)
        final long frequency = SDL_GetPerformanceFrequency();
        long currentTime = SDL_GetPerformanceCounter();
        if (currentTime <= data.time) {
            currentTime = data.time + 1;
        }
        io.setDeltaTime(
                data.time > 0 ? (float) ((double) (currentTime - data.time) / frequency) : (float) (1.0f / 60.0f));
        data.time = currentTime;

        if (data.mousePendingLeaveFrame != 0 && data.mousePendingLeaveFrame >= ImGui.getFrameCount()
                && data.mouseButtonsDown == 0) {
            data.mouseWindowID = 0;
            data.mousePendingLeaveFrame = 0;
            io.addMousePosEvent(-Float.MAX_VALUE, -Float.MAX_VALUE);
        }

        updateMouseData();
        updateMouseCursor();

        // Update game controllers (if enabled and available)
        updateGamepads();
    }

    private class CreateWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport viewport) {
            final ViewportData vd = new ViewportData();
            viewport.setPlatformUserData(vd);

            final ImGuiViewport mainViewport = ImGui.getMainViewport();
            final ViewportData mainViewportData = (ViewportData) mainViewport.getPlatformUserData();

            // Share GL resources with main context
            final boolean useOpenGL = (mainViewportData.glContext != 0);
            long backupContext = 0;
            if (useOpenGL) {
                backupContext = SDL_GL_GetCurrentContext();
                SDL_GL_SetAttribute(SDL_GL_SHARE_WITH_CURRENT_CONTEXT, 1);
                SDL_GL_MakeCurrent(mainViewportData.window, mainViewportData.glContext);
            }

            int sdlFlags = 0;
            sdlFlags |= useOpenGL ? SDL_WINDOW_OPENGL : (data.useVulkan ? SDL_WINDOW_VULKAN : 0);
            sdlFlags |= SDL_GetWindowFlags(data.window);
            sdlFlags |= ((viewport.getFlags() & ImGuiViewportFlags.NoDecoration) != 0) ? SDL_WINDOW_BORDERLESS : 0;
            sdlFlags |= ((viewport.getFlags() & ImGuiViewportFlags.NoDecoration) != 0) ? 0 : SDL_WINDOW_RESIZABLE;
            if (!IS_WINDOWS) {
                // See SDL hack in ImGui_ImplSDL3_ShowWindow().
                sdlFlags |= ((viewport.getFlags() & ImGuiViewportFlags.NoTaskBarIcon) != 0) ? SDL_WINDOW_UTILITY : 0;
            }
            sdlFlags |= ((viewport.getFlags() & ImGuiViewportFlags.TopMost) != 0) ? SDL_WINDOW_ALWAYS_ON_TOP : 0;
            vd.window = SDL_CreateWindow("No Title Yet", (int) viewport.getSize().x, (int) viewport.getSize().y,
                    sdlFlags);
            SDL_SetWindowPosition(vd.window, (int) viewport.getPos().x, (int) viewport.getPos().y);
            vd.windowOwned = true;
            if (useOpenGL) {
                vd.glContext = SDL_GL_CreateContext(vd.window);
                SDL_GL_SetSwapInterval(0);
            }
            if (useOpenGL && backupContext != 0) {
                SDL_GL_MakeCurrent(vd.window, backupContext);
            }

            setupPlatformHandles(viewport, vd.window);
        }
    }

    private static class DestroyWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            if (vd != null) {
                if (vd.glContext != 0 && vd.windowOwned) {
                    SDL_GL_DestroyContext(vd.glContext);
                }
                if (vd.window != 0 && vd.windowOwned) {
                    SDL_DestroyWindow(vd.window);
                }
                vd.glContext = 0;
                vd.window = 0;
            }
            viewport.setPlatformUserData(null);
            viewport.setPlatformHandle(0);
        }
    }

    private static class ShowWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            if (IS_WINDOWS) {
                ImGuiImplGlfwNative.win32hideFromTaskBar(viewport.getPlatformHandleRaw());
            }

            SDL_ShowWindow(vd.window);
        }
    }

    private static class GetWindowPosFunction extends ImPlatformFuncViewportSuppImVec2 {
        public void get(final ImGuiViewport viewport, final ImVec2 dst) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                final IntBuffer x = stack.mallocInt(1);
                final IntBuffer y = stack.mallocInt(1);
                SDL_GetWindowPosition(vd.window, x, y);

                dst.x = x.get(0);
                dst.y = y.get(0);
            }
        }
    }

    private static class SetWindowPosFunction extends ImPlatformFuncViewportImVec2 {
        @Override
        public void accept(final ImGuiViewport viewport, final ImVec2 pos) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            SDL_SetWindowPosition(vd.window, (int) pos.x, (int) pos.y);
        }
    }

    private static class GetWindowSizeFunction extends ImPlatformFuncViewportSuppImVec2 {
        @Override
        public void get(final ImGuiViewport viewport, final ImVec2 dst) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            try (MemoryStack stack = MemoryStack.stackPush()) {
                final IntBuffer w = stack.mallocInt(1);
                final IntBuffer h = stack.mallocInt(1);
                SDL_GetWindowSize(vd.window, w, h);

                dst.x = w.get(0);
                dst.y = h.get(0);
            }
        }

    }

    private static class SetWindowSizeFunction extends ImPlatformFuncViewportImVec2 {
        public void accept(final ImGuiViewport viewport, final ImVec2 size) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            SDL_SetWindowSize(vd.window, (int) size.x, (int) size.y);
        }
    }

    private static class SetWindowTitleFunction extends ImPlatformFuncViewportString {
        @Override
        public void accept(final ImGuiViewport viewport, final String title) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            SDL_SetWindowTitle(vd.window, title);
        }
    }

    private static class SetWindowAlphaFunction extends ImPlatformFuncViewportFloat {
        @Override
        public void accept(final ImGuiViewport viewport, final float alpha) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            SDL_SetWindowOpacity(vd.window, alpha);
        }
    }

    private static class SetWindowFocusFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            SDL_RaiseWindow(vd.window);
        }
    }

    private static class GetWindowFocusFunction extends ImPlatformFuncViewportSuppBoolean {
        @Override
        public boolean get(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            return (SDL_GetWindowFlags(vd.window) & SDL_WINDOW_INPUT_FOCUS) != 0;
        }
    }

    private static class GetWindowMinimizedFunction extends ImPlatformFuncViewportSuppBoolean {
        @Override
        public boolean get(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            return (SDL_GetWindowFlags(vd.window) & SDL_WINDOW_MINIMIZED) != 0;
        }
    }

    private static class RenderWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            if (vd.glContext != 0) {
                SDL_GL_MakeCurrent(vd.window, vd.glContext);
            }
        }
    }

    private static class SwapBuffersFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport viewport) {
            final ViewportData vd = (ViewportData) viewport.getPlatformUserData();
            if (vd.glContext != 0) {
                SDL_GL_MakeCurrent(vd.window, vd.glContext);
                SDL_GL_SwapWindow(vd.window);
            }
        }
    }

    void initPlatformInterface(final long window, final long sdlGlContext) {
        // Register platform interface (will be coupled with a renderer interface)
        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        platformIO.setPlatformCreateWindow(new CreateWindowFunction());
        platformIO.setPlatformDestroyWindow(new DestroyWindowFunction());
        platformIO.setPlatformShowWindow(new ShowWindowFunction());
        platformIO.setPlatformSetWindowPos(new SetWindowPosFunction());
        platformIO.setPlatformGetWindowPos(new GetWindowPosFunction());
        platformIO.setPlatformSetWindowSize(new SetWindowSizeFunction());
        platformIO.setPlatformGetWindowSize(new GetWindowSizeFunction());
        platformIO.setPlatformSetWindowFocus(new SetWindowFocusFunction());
        platformIO.setPlatformGetWindowFocus(new GetWindowFocusFunction());
        platformIO.setPlatformGetWindowMinimized(new GetWindowMinimizedFunction());
        platformIO.setPlatformSetWindowTitle(new SetWindowTitleFunction());
        platformIO.setPlatformRenderWindow(new RenderWindowFunction());
        platformIO.setPlatformSwapBuffers(new SwapBuffersFunction());
        platformIO.setPlatformSetWindowAlpha(new SetWindowAlphaFunction());

        // Register main window handle (which is owned by the main application, not by
        // us)
        // This is mostly for simplicity and consistency, so that our code (e.g. mouse
        // handling etc.) can use same logic for main and secondary viewports.
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        final ViewportData vd = new ViewportData();
        vd.window = window;
        vd.windowID = SDL_GetWindowID(window);
        vd.windowOwned = false;
        vd.glContext = sdlGlContext;
        mainViewport.setPlatformUserData(vd);
        mainViewport.setPlatformHandle(vd.window);
    }

    void shutdownPlatformInterface() {
        ImGui.destroyPlatformWindows();
    }
}
