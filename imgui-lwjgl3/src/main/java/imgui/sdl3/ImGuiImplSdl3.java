package imgui.sdl3;

import static org.lwjgl.sdl.SDLClipboard.SDL_GetClipboardText;
import static org.lwjgl.sdl.SDLClipboard.SDL_HasClipboardText;
import static org.lwjgl.sdl.SDLClipboard.SDL_SetClipboardText;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_GAMEPAD_ADDED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_GAMEPAD_REMOVED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_KEY_DOWN;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_KEY_UP;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_BUTTON_DOWN;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_BUTTON_UP;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_MOTION;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_MOUSE_WHEEL;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_TEXT_INPUT;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_FOCUS_GAINED;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_FOCUS_LOST;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_MOUSE_ENTER;
import static org.lwjgl.sdl.SDLEvents.SDL_EVENT_WINDOW_MOUSE_LEAVE;
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
import static org.lwjgl.sdl.SDLKeyboard.SDL_GetKeyboardFocus;
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
import static org.lwjgl.sdl.SDLMouse.SDL_GetGlobalMouseState;
import static org.lwjgl.sdl.SDLMouse.SDL_GetMouseFocus;
import static org.lwjgl.sdl.SDLMouse.SDL_GetWindowRelativeMouseMode;
import static org.lwjgl.sdl.SDLMouse.SDL_HideCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_DEFAULT;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_EW_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_MOVE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NESW_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NOT_ALLOWED;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NS_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_NWSE_RESIZE;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_POINTER;
import static org.lwjgl.sdl.SDLMouse.SDL_SYSTEM_CURSOR_TEXT;
import static org.lwjgl.sdl.SDLMouse.SDL_SetCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_ShowCursor;
import static org.lwjgl.sdl.SDLMouse.SDL_WarpMouseInWindow;
import static org.lwjgl.sdl.SDLProperties.SDL_GetPointerProperty;
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
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_PERIOD;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_RIGHTBRACKET;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_SEMICOLON;
import static org.lwjgl.sdl.SDLScancode.SDL_SCANCODE_SLASH;
import static org.lwjgl.sdl.SDLTimer.SDL_GetPerformanceCounter;
import static org.lwjgl.sdl.SDLTimer.SDL_GetPerformanceFrequency;
import static org.lwjgl.sdl.SDLTouch.SDL_TOUCH_MOUSEID;
import static org.lwjgl.sdl.SDLVideo.SDL_GetCurrentVideoDriver;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowDisplayScale;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowFlags;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowID;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowPosition;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowProperties;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowSize;
import static org.lwjgl.sdl.SDLVideo.SDL_GetWindowSizeInPixels;
import static org.lwjgl.sdl.SDLVideo.SDL_PROP_WINDOW_COCOA_WINDOW_POINTER;
import static org.lwjgl.sdl.SDLVideo.SDL_PROP_WINDOW_WIN32_HWND_POINTER;
import static org.lwjgl.sdl.SDLVideo.SDL_WINDOW_MINIMIZED;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.sdl.SDL_Event;
import org.lwjgl.system.MemoryStack;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiMouseSource;

@SuppressWarnings({ "checkstyle:DesignForExtension" })
public class ImGuiImplSdl3 {
    protected static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_WINDOWS = OS.contains("win");
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");

    private static class SDL3Data {
        public long window = -1;
        public long windowID = -1;
        public long time = 0;
        public String clipboardTextData;

        // mouse handling
        public int mouseButtonsDown;
        public long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
        public long mouseLastCursor;
        public int mousePendingLeaveFrame;
        public boolean mouseCanUseGlobalState;
        public MouseCaptureMode mouseCaptureMode;

        // gamepad handling
        public List<Long> gamepads;
        public GamepadMode gamepadMode;
        public boolean wantUpdateGamepadsList;
    };

    private enum MouseCaptureMode {
        Enabled, EnabledAfterDrag, Disabled
    }

    private enum GamepadMode {
        AutoFirst, AutoAll, Manual
    }

    private SDL3Data data;

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
            case SDL_EVENT_WINDOW_MOUSE_ENTER:
                if (getViewportForWindowID(event.window().windowID()) == null) {
                    return false;
                }
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
        data = new SDL3Data();
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
            data.mousePendingLeaveFrame = 0;
            io.addMousePosEvent(-Float.MAX_VALUE, -Float.MAX_VALUE);
        }

        updateMouseData();
        updateMouseCursor();

        // Update game controllers (if enabled and available)
        updateGamepads();
    }
}
