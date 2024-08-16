package imgui.glfw;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewport;
import imgui.callback.ImPlatformFuncViewportFloat;
import imgui.callback.ImPlatformFuncViewportImVec2;
import imgui.callback.ImPlatformFuncViewportString;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiViewportFlags;
import imgui.lwjgl3.glfw.ImGuiImplGlfwNative;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.system.Callback;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_FLOATING;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUS_ON_SHOW;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_LEFT_TRIGGER;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_LEFT_X;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_LEFT_Y;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_RIGHT_X;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_RIGHT_Y;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_A;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_B;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_BACK;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_DPAD_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_DPAD_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_DPAD_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_DPAD_UP;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_LEFT_BUMPER;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_LEFT_THUMB;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_RIGHT_THUMB;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_START;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_X;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_BUTTON_Y;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HOVERED;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_ICONIFIED;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSLASH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_CAPS_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_EQUAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F10;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F12;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DIVIDE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_EQUAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_MULTIPLY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_MENU;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_MINUS;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_NUM_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAUSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PRINT_SCREEN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SCROLL_LOCK;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SEMICOLON;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SLASH;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_PASSTHROUGH;
import static org.lwjgl.glfw.GLFW.GLFW_NOT_ALLOWED_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZE_ALL_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZE_NESW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZE_NWSE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwFocusWindow;
import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetGamepadState;
import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwGetKeyName;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorContentScale;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMonitorWorkarea;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwGetVersion;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMonitorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowOpacity;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * This class is a straightforward port of the
 * <a href="https://raw.githubusercontent.com/ocornut/imgui/1ee252772ae9c0a971d06257bb5c89f628fa696a/backends/imgui_impl_glfw.cpp">imgui_impl_glfw.cpp</a>.
 * <p>
 * It supports clipboard, gamepad, mouse and keyboard in the same way the original Dear ImGui code does. You can copy-paste this class in your codebase and
 * modify the rendering routine in the way you'd like.
 */
@SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:NeedBraces", "checkstyle:LocalVariableName", "checkstyle:FinalLocalVariable", "checkstyle:ParameterName", "checkstyle:EmptyBlock", "checkstyle:AvoidNestedBlocks"})
public class ImGuiImplGlfw {
    protected static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    protected static final boolean IS_WINDOWS = OS.contains("win");
    protected static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");

    /**
     * Data class to store implementation specific fields.
     * Same as {@code ImGui_ImplGlfw_Data}.
     */
    protected static class Data {
        protected long window = -1;
        protected double time = 0.0;
        protected long mouseWindow = -1;
        protected long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
        protected ImVec2 lastValidMousePos = new ImVec2();
        protected long[] keyOwnerWindows = new long[GLFW_KEY_LAST];
        protected boolean installedCallbacks = false;
        protected boolean wantUpdateMonitors = true;

        // Chain GLFW callbacks: our callbacks will call the user's previously installed callbacks, if any.
        protected GLFWWindowFocusCallback prevUserCallbackWindowFocus = null;
        protected GLFWCursorPosCallback prevUserCallbackCursorPos = null;
        protected GLFWCursorEnterCallback prevUserCallbackCursorEnter = null;
        protected GLFWMouseButtonCallback prevUserCallbackMousebutton = null;
        protected GLFWScrollCallback prevUserCallbackScroll = null;
        protected GLFWKeyCallback prevUserCallbackKey = null;
        protected GLFWCharCallback prevUserCallbackChar = null;
        protected GLFWMonitorCallback prevUserCallbackMonitor = null;
    }

    /**
     * Internal class to store containers for frequently used arrays.
     * This class helps minimize the number of object allocations on the JVM side,
     * thereby improving performance and reducing garbage collection overhead.
     */
    private static final class Properties {
        private final int[] windowW = new int[1];
        private final int[] windowH = new int[1];
        private final int[] windowX = new int[1];
        private final int[] windowY = new int[1];
        private final int[] displayW = new int[1];
        private final int[] displayH = new int[1];

        // For mouse tracking
        private final ImVec2 mousePosPrev = new ImVec2();
        private final double[] mouseX = new double[1];
        private final double[] mouseY = new double[1];

        // Monitor properties
        private final int[] monitorX = new int[1];
        private final int[] monitorY = new int[1];
        private final int[] monitorWorkAreaX = new int[1];
        private final int[] monitorWorkAreaY = new int[1];
        private final int[] monitorWorkAreaWidth = new int[1];
        private final int[] monitorWorkAreaHeight = new int[1];
        private final float[] monitorContentScaleX = new float[1];
        private final float[] monitorContentScaleY = new float[1];

        // For char translation
        private final String charNames = "`-=[]\\,;'./";
        private final int[] charKeys = {
            GLFW_KEY_GRAVE_ACCENT, GLFW_KEY_MINUS, GLFW_KEY_EQUAL, GLFW_KEY_LEFT_BRACKET,
            GLFW_KEY_RIGHT_BRACKET, GLFW_KEY_BACKSLASH, GLFW_KEY_COMMA, GLFW_KEY_SEMICOLON,
            GLFW_KEY_APOSTROPHE, GLFW_KEY_PERIOD, GLFW_KEY_SLASH
        };
    }

    protected Data data = null;
    private final Properties props = new Properties();

    // Some features may be available only from a specific version
    private boolean glfwHawWindowTopmost;
    private boolean glfwHasWindowHovered;
    private boolean glfwHasWindowAlpha;
    private boolean glfwHasPerMonitorDpi;
    // private boolean glfwHasVulkan; TODO: I want to believe...
    private boolean glfwHasFocusWindow;
    private boolean glfwHasFocusOnShow;
    private boolean glfwHasMonitorWorkArea;
    private boolean glfwHasOsxWindowPosFix;
    private boolean glfwHasNewCursors;
    private boolean glfwHasMousePassthrough;
    private boolean glfwHasGamepadApi;
    private boolean glfwHasGetKeyName;

    protected ImStrSupplier getClipboardTextFn() {
        return new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(data.window);
                return clipboardString != null ? clipboardString : "";
            }
        };
    }

    protected ImStrConsumer setClipboardTextFn() {
        return new ImStrConsumer() {
            @Override
            public void accept(final String text) {
                glfwSetClipboardString(data.window, text);
            }
        };
    }

    protected int glfwKeyToImGuiKey(final int glfwKey) {
        switch (glfwKey) {
            case GLFW_KEY_TAB:
                return ImGuiKey.Tab;
            case GLFW_KEY_LEFT:
                return ImGuiKey.LeftArrow;
            case GLFW_KEY_RIGHT:
                return ImGuiKey.RightArrow;
            case GLFW_KEY_UP:
                return ImGuiKey.UpArrow;
            case GLFW_KEY_DOWN:
                return ImGuiKey.DownArrow;
            case GLFW_KEY_PAGE_UP:
                return ImGuiKey.PageUp;
            case GLFW_KEY_PAGE_DOWN:
                return ImGuiKey.PageDown;
            case GLFW_KEY_HOME:
                return ImGuiKey.Home;
            case GLFW_KEY_END:
                return ImGuiKey.End;
            case GLFW_KEY_INSERT:
                return ImGuiKey.Insert;
            case GLFW_KEY_DELETE:
                return ImGuiKey.Delete;
            case GLFW_KEY_BACKSPACE:
                return ImGuiKey.Backspace;
            case GLFW_KEY_SPACE:
                return ImGuiKey.Space;
            case GLFW_KEY_ENTER:
                return ImGuiKey.Enter;
            case GLFW_KEY_ESCAPE:
                return ImGuiKey.Escape;
            case GLFW_KEY_APOSTROPHE:
                return ImGuiKey.Apostrophe;
            case GLFW_KEY_COMMA:
                return ImGuiKey.Comma;
            case GLFW_KEY_MINUS:
                return ImGuiKey.Minus;
            case GLFW_KEY_PERIOD:
                return ImGuiKey.Period;
            case GLFW_KEY_SLASH:
                return ImGuiKey.Slash;
            case GLFW_KEY_SEMICOLON:
                return ImGuiKey.Semicolon;
            case GLFW_KEY_EQUAL:
                return ImGuiKey.Equal;
            case GLFW_KEY_LEFT_BRACKET:
                return ImGuiKey.LeftBracket;
            case GLFW_KEY_BACKSLASH:
                return ImGuiKey.Backslash;
            case GLFW_KEY_RIGHT_BRACKET:
                return ImGuiKey.RightBracket;
            case GLFW_KEY_GRAVE_ACCENT:
                return ImGuiKey.GraveAccent;
            case GLFW_KEY_CAPS_LOCK:
                return ImGuiKey.CapsLock;
            case GLFW_KEY_SCROLL_LOCK:
                return ImGuiKey.ScrollLock;
            case GLFW_KEY_NUM_LOCK:
                return ImGuiKey.NumLock;
            case GLFW_KEY_PRINT_SCREEN:
                return ImGuiKey.PrintScreen;
            case GLFW_KEY_PAUSE:
                return ImGuiKey.Pause;
            case GLFW_KEY_KP_0:
                return ImGuiKey.Keypad0;
            case GLFW_KEY_KP_1:
                return ImGuiKey.Keypad1;
            case GLFW_KEY_KP_2:
                return ImGuiKey.Keypad2;
            case GLFW_KEY_KP_3:
                return ImGuiKey.Keypad3;
            case GLFW_KEY_KP_4:
                return ImGuiKey.Keypad4;
            case GLFW_KEY_KP_5:
                return ImGuiKey.Keypad5;
            case GLFW_KEY_KP_6:
                return ImGuiKey.Keypad6;
            case GLFW_KEY_KP_7:
                return ImGuiKey.Keypad7;
            case GLFW_KEY_KP_8:
                return ImGuiKey.Keypad8;
            case GLFW_KEY_KP_9:
                return ImGuiKey.Keypad9;
            case GLFW_KEY_KP_DECIMAL:
                return ImGuiKey.KeypadDecimal;
            case GLFW_KEY_KP_DIVIDE:
                return ImGuiKey.KeypadDivide;
            case GLFW_KEY_KP_MULTIPLY:
                return ImGuiKey.KeypadMultiply;
            case GLFW_KEY_KP_SUBTRACT:
                return ImGuiKey.KeypadSubtract;
            case GLFW_KEY_KP_ADD:
                return ImGuiKey.KeypadAdd;
            case GLFW_KEY_KP_ENTER:
                return ImGuiKey.KeypadEnter;
            case GLFW_KEY_KP_EQUAL:
                return ImGuiKey.KeypadEqual;
            case GLFW_KEY_LEFT_SHIFT:
                return ImGuiKey.LeftShift;
            case GLFW_KEY_LEFT_CONTROL:
                return ImGuiKey.LeftCtrl;
            case GLFW_KEY_LEFT_ALT:
                return ImGuiKey.LeftAlt;
            case GLFW_KEY_LEFT_SUPER:
                return ImGuiKey.LeftSuper;
            case GLFW_KEY_RIGHT_SHIFT:
                return ImGuiKey.RightShift;
            case GLFW_KEY_RIGHT_CONTROL:
                return ImGuiKey.RightCtrl;
            case GLFW_KEY_RIGHT_ALT:
                return ImGuiKey.RightAlt;
            case GLFW_KEY_RIGHT_SUPER:
                return ImGuiKey.RightSuper;
            case GLFW_KEY_MENU:
                return ImGuiKey.Menu;
            case GLFW_KEY_0:
                return ImGuiKey._0;
            case GLFW_KEY_1:
                return ImGuiKey._1;
            case GLFW_KEY_2:
                return ImGuiKey._2;
            case GLFW_KEY_3:
                return ImGuiKey._3;
            case GLFW_KEY_4:
                return ImGuiKey._4;
            case GLFW_KEY_5:
                return ImGuiKey._5;
            case GLFW_KEY_6:
                return ImGuiKey._6;
            case GLFW_KEY_7:
                return ImGuiKey._7;
            case GLFW_KEY_8:
                return ImGuiKey._8;
            case GLFW_KEY_9:
                return ImGuiKey._9;
            case GLFW_KEY_A:
                return ImGuiKey.A;
            case GLFW_KEY_B:
                return ImGuiKey.B;
            case GLFW_KEY_C:
                return ImGuiKey.C;
            case GLFW_KEY_D:
                return ImGuiKey.D;
            case GLFW_KEY_E:
                return ImGuiKey.E;
            case GLFW_KEY_F:
                return ImGuiKey.F;
            case GLFW_KEY_G:
                return ImGuiKey.G;
            case GLFW_KEY_H:
                return ImGuiKey.H;
            case GLFW_KEY_I:
                return ImGuiKey.I;
            case GLFW_KEY_J:
                return ImGuiKey.J;
            case GLFW_KEY_K:
                return ImGuiKey.K;
            case GLFW_KEY_L:
                return ImGuiKey.L;
            case GLFW_KEY_M:
                return ImGuiKey.M;
            case GLFW_KEY_N:
                return ImGuiKey.N;
            case GLFW_KEY_O:
                return ImGuiKey.O;
            case GLFW_KEY_P:
                return ImGuiKey.P;
            case GLFW_KEY_Q:
                return ImGuiKey.Q;
            case GLFW_KEY_R:
                return ImGuiKey.R;
            case GLFW_KEY_S:
                return ImGuiKey.S;
            case GLFW_KEY_T:
                return ImGuiKey.T;
            case GLFW_KEY_U:
                return ImGuiKey.U;
            case GLFW_KEY_V:
                return ImGuiKey.V;
            case GLFW_KEY_W:
                return ImGuiKey.W;
            case GLFW_KEY_X:
                return ImGuiKey.X;
            case GLFW_KEY_Y:
                return ImGuiKey.Y;
            case GLFW_KEY_Z:
                return ImGuiKey.Z;
            case GLFW_KEY_F1:
                return ImGuiKey.F1;
            case GLFW_KEY_F2:
                return ImGuiKey.F2;
            case GLFW_KEY_F3:
                return ImGuiKey.F3;
            case GLFW_KEY_F4:
                return ImGuiKey.F4;
            case GLFW_KEY_F5:
                return ImGuiKey.F5;
            case GLFW_KEY_F6:
                return ImGuiKey.F6;
            case GLFW_KEY_F7:
                return ImGuiKey.F7;
            case GLFW_KEY_F8:
                return ImGuiKey.F8;
            case GLFW_KEY_F9:
                return ImGuiKey.F9;
            case GLFW_KEY_F10:
                return ImGuiKey.F10;
            case GLFW_KEY_F11:
                return ImGuiKey.F11;
            case GLFW_KEY_F12:
                return ImGuiKey.F12;
            default:
                return ImGuiKey.None;
        }
    }

    protected void updateKeyModifiers(final int mods) {
        final ImGuiIO io = ImGui.getIO();
        io.addKeyEvent(ImGuiKey.ModCtrl, (mods & GLFW_MOD_CONTROL) != 0);
        io.addKeyEvent(ImGuiKey.ModShift, (mods & GLFW_MOD_SHIFT) != 0);
        io.addKeyEvent(ImGuiKey.ModAlt, (mods & GLFW_MOD_ALT) != 0);
        io.addKeyEvent(ImGuiKey.ModSuper, (mods & GLFW_MOD_SUPER) != 0);
    }

    public void mouseButtonCallback(final long window, final int button, final int action, final int mods) {
        if (data.prevUserCallbackMousebutton != null && window == data.window) {
            data.prevUserCallbackMousebutton.invoke(window, button, action, mods);
        }

        updateKeyModifiers(mods);

        final ImGuiIO io = ImGui.getIO();
        if (button >= 0 && button < ImGuiMouseButton.COUNT) {
            io.addMouseButtonEvent(button, action == GLFW_PRESS);
        }
    }

    public void scrollCallback(final long window, final double xOffset, final double yOffset) {
        if (data.prevUserCallbackScroll != null && window == data.window) {
            data.prevUserCallbackScroll.invoke(window, xOffset, yOffset);
        }

        final ImGuiIO io = ImGui.getIO();
        io.addMouseWheelEvent((float) xOffset, (float) yOffset);
    }

    protected int translateUntranslatedKey(final int key, final int scancode) {
        if (!glfwHasGetKeyName) {
            return key;
        }

        // GLFW 3.1+ attempts to "untranslate" keys, which goes the opposite of what every other framework does, making using lettered shortcuts difficult.
        // (It had reasons to do so: namely GLFW is/was more likely to be used for WASD-type game controls rather than lettered shortcuts, but IHMO the 3.1 change could have been done differently)
        // See https://github.com/glfw/glfw/issues/1502 for details.
        // Adding a workaround to undo this (so our keys are translated->untranslated->translated, likely a lossy process).
        // This won't cover edge cases but this is at least going to cover common cases.
        if (key >= GLFW_KEY_KP_0 && key <= GLFW_KEY_KP_EQUAL) {
            return key;
        }

        int resultKey = key;
        final String keyName = glfwGetKeyName(key, scancode);

        if (keyName != null && keyName.length() > 2 && keyName.charAt(0) != 0 && keyName.charAt(1) == 0) {
            if (keyName.charAt(0) >= '0' && keyName.charAt(0) <= '9') {
                resultKey = GLFW_KEY_0 + (keyName.charAt(0) - '0');
            } else if (keyName.charAt(0) >= 'A' && keyName.charAt(0) <= 'Z') {
                resultKey = GLFW_KEY_A + (keyName.charAt(0) - 'A');
            } else {
                final int index = props.charNames.indexOf(keyName.charAt(0));
                if (index != -1) {
                    resultKey = props.charKeys[index];
                }
            }
        }

        return resultKey;
    }

    public void keyCallback(final long window, final int keycode, final int scancode, final int action, final int mods) {
        if (data.prevUserCallbackKey != null && window == data.window) {
            data.prevUserCallbackKey.invoke(window, keycode, scancode, action, mods);
        }

        if (action != GLFW_PRESS && action != GLFW_RELEASE) {
            return;
        }

        updateKeyModifiers(mods);

        if (keycode >= 0 && keycode < data.keyOwnerWindows.length) {
            data.keyOwnerWindows[keycode] = (action == GLFW_PRESS) ? window : -1;
        }

        final int key = translateUntranslatedKey(keycode, scancode);

        final ImGuiIO io = ImGui.getIO();
        final int imguiKey = glfwKeyToImGuiKey(key);
        io.addKeyEvent(imguiKey, (action == GLFW_PRESS));
        io.setKeyEventNativeData(imguiKey, key, scancode); // To support legacy indexing (<1.87 user code)
    }

    public void windowFocusCallback(final long window, final boolean focused) {
        if (data.prevUserCallbackWindowFocus != null && window == data.window) {
            data.prevUserCallbackWindowFocus.invoke(window, focused);
        }

        ImGui.getIO().addFocusEvent(focused);
    }

    public void cursorPosCallback(final long window, final double x, final double y) {
        if (data.prevUserCallbackCursorPos != null && window == data.window) {
            data.prevUserCallbackCursorPos.invoke(window, x, y);
        }

        float posX = (float) x;
        float posY = (float) y;

        final ImGuiIO io = ImGui.getIO();

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            glfwGetWindowPos(window, props.windowX, props.windowY);
            posX += props.windowX[0];
            posY += props.windowY[0];
        }

        io.addMousePosEvent(posX, posY);
        data.lastValidMousePos.set(posX, posY);
    }

    // Workaround: X11 seems to send spurious Leave/Enter events which would make us lose our position,
    // so we back it up and restore on Leave/Enter (see https://github.com/ocornut/imgui/issues/4984)
    public void cursorEnterCallback(final long window, final boolean entered) {
        if (data.prevUserCallbackCursorEnter != null && window == data.window) {
            data.prevUserCallbackCursorEnter.invoke(window, entered);
        }

        final ImGuiIO io = ImGui.getIO();

        if (entered) {
            data.mouseWindow = window;
            io.addMousePosEvent(data.lastValidMousePos.x, data.lastValidMousePos.y);
        } else if (data.mouseWindow == window) {
            io.getMousePos(data.lastValidMousePos);
            data.mouseWindow = -1;
            io.addMousePosEvent(Float.MIN_VALUE, Float.MIN_VALUE);
        }
    }

    public void charCallback(final long window, final int c) {
        if (data.prevUserCallbackChar != null && window == data.window) {
            data.prevUserCallbackChar.invoke(window, c);
        }

        ImGui.getIO().addInputCharacter(c);
    }

    public void monitorCallback(final long window, final int event) {
        data.wantUpdateMonitors = true;
    }

    public void installCallbacks(final long window) {
        data.prevUserCallbackWindowFocus = glfwSetWindowFocusCallback(window, this::windowFocusCallback);
        data.prevUserCallbackCursorEnter = glfwSetCursorEnterCallback(window, this::cursorEnterCallback);
        data.prevUserCallbackCursorPos = glfwSetCursorPosCallback(window, this::cursorPosCallback);
        data.prevUserCallbackMousebutton = glfwSetMouseButtonCallback(window, this::mouseButtonCallback);
        data.prevUserCallbackScroll = glfwSetScrollCallback(window, this::scrollCallback);
        data.prevUserCallbackKey = glfwSetKeyCallback(window, this::keyCallback);
        data.prevUserCallbackChar = glfwSetCharCallback(window, this::charCallback);
        data.prevUserCallbackMonitor = glfwSetMonitorCallback(this::monitorCallback);
        data.installedCallbacks = true;
    }

    protected void freeCallback(final Callback cb) {
        if (cb != null) {
            cb.free();
        }
    }

    public void restoreCallbacks(final long window) {
        freeCallback(glfwSetWindowFocusCallback(window, data.prevUserCallbackWindowFocus));
        freeCallback(glfwSetCursorEnterCallback(window, data.prevUserCallbackCursorEnter));
        freeCallback(glfwSetCursorPosCallback(window, data.prevUserCallbackCursorPos));
        freeCallback(glfwSetMouseButtonCallback(window, data.prevUserCallbackMousebutton));
        freeCallback(glfwSetScrollCallback(window, data.prevUserCallbackScroll));
        freeCallback(glfwSetKeyCallback(window, data.prevUserCallbackKey));
        freeCallback(glfwSetCharCallback(window, data.prevUserCallbackChar));
        freeCallback(glfwSetMonitorCallback(data.prevUserCallbackMonitor));
        data.installedCallbacks = false;
        data.prevUserCallbackWindowFocus = null;
        data.prevUserCallbackCursorEnter = null;
        data.prevUserCallbackCursorPos = null;
        data.prevUserCallbackMousebutton = null;
        data.prevUserCallbackScroll = null;
        data.prevUserCallbackKey = null;
        data.prevUserCallbackChar = null;
        data.prevUserCallbackMonitor = null;
    }

    protected Data newData() {
        return new Data();
    }

    public boolean init(final long window, final boolean installCallbacks) {
        // On the Dear ImGui backend side version resolving is done with the usage of defines.
        {
            final int[] major = new int[1];
            final int[] minor = new int[1];
            final int[] rev = new int[1];
            glfwGetVersion(major, minor, rev);

            final int version = major[0] * 1000 + minor[0] * 100 + rev[0] * 10;

            glfwHawWindowTopmost = version >= 3200;
            glfwHasWindowHovered = version >= 3300;
            glfwHasWindowAlpha = version >= 3300;
            glfwHasPerMonitorDpi = version >= 3300;
            // glfwHasVulkan = version >= 3200; TODO: I want to believe...
            glfwHasFocusWindow = version >= 3200;
            glfwHasFocusOnShow = version >= 3300;
            glfwHasMonitorWorkArea = version >= 3300;
            glfwHasOsxWindowPosFix = version >= 3310;
            glfwHasNewCursors = version >= 3400;
            glfwHasMousePassthrough = version >= 3400;
            glfwHasGamepadApi = version >= 3300;
            glfwHasGetKeyName = version >= 3200;
        }

        final ImGuiIO io = ImGui.getIO();

        io.setBackendPlatformName("imgui-java_impl_glfw");
        io.addBackendFlags(ImGuiBackendFlags.HasMouseCursors | ImGuiBackendFlags.HasSetMousePos | ImGuiBackendFlags.PlatformHasViewports);
        if (glfwHasMousePassthrough || (glfwHasWindowHovered && IS_WINDOWS)) {
            io.addBackendFlags(ImGuiBackendFlags.HasMouseHoveredViewport);
        }

        data = newData();
        data.window = window;
        data.time = 0.0;
        data.wantUpdateMonitors = true;

        io.setGetClipboardTextFn(getClipboardTextFn());
        io.setSetClipboardTextFn(setClipboardTextFn());

        // Create mouse cursors
        // (By design, on X11 cursors are user configurable and some cursors may be missing. When a cursor doesn't exist,
        // GLFW will emit an error which will often be printed by the app, so we temporarily disable error reporting.
        // Missing cursors will return NULL and our _UpdateMouseCursor() function will use the Arrow cursor instead.)
        final GLFWErrorCallback prevErrorCallback = glfwSetErrorCallback(null);
        data.mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        data.mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        data.mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        data.mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        data.mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
        if (glfwHasNewCursors) {
            data.mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_RESIZE_ALL_CURSOR);
            data.mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_RESIZE_NESW_CURSOR);
            data.mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_RESIZE_NWSE_CURSOR);
            data.mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_NOT_ALLOWED_CURSOR);
        } else {
            data.mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
            data.mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
            data.mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
            data.mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        }
        glfwSetErrorCallback(prevErrorCallback);

        // Chain GLFW callbacks: our callbacks will call the user's previously installed callbacks, if any.
        if (installCallbacks) {
            installCallbacks(window);
        }

        // Update monitors the first time (note: monitor callback are broken in GLFW 3.2 and earlier, see github.com/glfw/glfw/issues/784)
        updateMonitors();
        glfwSetMonitorCallback(this::monitorCallback);

        // Our mouse update function expect PlatformHandle to be filled for the main viewport
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        mainViewport.setPlatformHandle(window);
        if (IS_WINDOWS) {
            mainViewport.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(window));
        }
        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            initPlatformInterface();
        }

        return true;
    }

    public void shutdown() {
        final ImGuiIO io = ImGui.getIO();

        shutdownPlatformInterface();

        if (data.installedCallbacks) {
            restoreCallbacks(data.window);
        }

        for (int cursorN = 0; cursorN < ImGuiMouseCursor.COUNT; cursorN++) {
            glfwDestroyCursor(data.mouseCursors[cursorN]);
        }

        io.setBackendPlatformName(null);
        data = null;
    }

    protected void updateMouseData() {
        final ImGuiIO io = ImGui.getIO();
        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();

        int mouseViewportId = 0;
        io.getMousePos(props.mousePosPrev);

        for (int n = 0; n < platformIO.getViewportsSize(); n++) {
            final ImGuiViewport viewport = platformIO.getViewports(n);
            final long window = viewport.getPlatformHandle();
            final boolean isWindowFocused = glfwGetWindowAttrib(window, GLFW_FOCUSED) != 0;

            if (isWindowFocused) {
                // (Optional) Set OS mouse position from Dear ImGui if requested (rarely used, only when ImGuiConfigFlags_NavEnableSetMousePos is enabled by user)
                // When multi-viewports are enabled, all Dear ImGui positions are same as OS positions.
                if (io.getWantSetMousePos()) {
                    glfwSetCursorPos(window, props.mousePosPrev.x - viewport.getPosX(), props.mousePosPrev.y - viewport.getPosY());
                }

                // (Optional) Fallback to provide mouse position when focused (ImGui_ImplGlfw_CursorPosCallback already provides this when hovered or captured)
                if (data.mouseWindow == -1) {
                    glfwGetCursorPos(window, props.mouseX, props.mouseY);
                    double mouseX = props.mouseX[0];
                    double mouseY = props.mouseY[0];
                    if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                        // Single viewport mode: mouse position in client window coordinates (io.MousePos is (0,0) when the mouse is on the upper-left corner of the app window)
                        // Multi-viewport mode: mouse position in OS absolute coordinates (io.MousePos is (0,0) when the mouse is on the upper-left of the primary monitor)
                        glfwGetWindowPos(window, props.windowX, props.windowY);
                        mouseX += props.windowX[0];
                        mouseY += props.windowY[0];
                    }
                    data.lastValidMousePos.set((float) mouseX, (float) mouseY);
                    io.addMousePosEvent((float) mouseX, (float) mouseY);
                }
            }

            // (Optional) When using multiple viewports: call io.AddMouseViewportEvent() with the viewport the OS mouse cursor is hovering.
            // If ImGuiBackendFlags_HasMouseHoveredViewport is not set by the backend, Dear imGui will ignore this field and infer the information using its flawed heuristic.
            // - [X] GLFW >= 3.3 backend ON WINDOWS ONLY does correctly ignore viewports with the _NoInputs flag.
            // - [!] GLFW <= 3.2 backend CANNOT correctly ignore viewports with the _NoInputs flag, and CANNOT reported Hovered Viewport because of mouse capture.
            //       Some backend are not able to handle that correctly. If a backend report an hovered viewport that has the _NoInputs flag (e.g. when dragging a window
            //       for docking, the viewport has the _NoInputs flag in order to allow us to find the viewport under), then Dear ImGui is forced to ignore the value reported
            //       by the backend, and use its flawed heuristic to guess the viewport behind.
            // - [X] GLFW backend correctly reports this regardless of another viewport behind focused and dragged from (we need this to find a useful drag and drop target).
            // FIXME: This is currently only correct on Win32. See what we do below with the WM_NCHITTEST, missing an equivalent for other systems.
            // See https://github.com/glfw/glfw/issues/1236 if you want to help in making this a GLFW feature.

            if (glfwHasMousePassthrough || (glfwHasWindowHovered && IS_WINDOWS)) {
                boolean windowNoInput = viewport.hasFlags(ImGuiViewportFlags.NoInputs);
                if (glfwHasMousePassthrough) {
                    glfwSetWindowAttrib(window, GLFW_MOUSE_PASSTHROUGH, windowNoInput ? GLFW_TRUE : GLFW_FALSE);
                }
                if (glfwGetWindowAttrib(window, GLFW_HOVERED) == GLFW_TRUE && !windowNoInput) {
                    mouseViewportId = viewport.getID();
                }
            }
            // else
            // We cannot use bd->MouseWindow maintained from CursorEnter/Leave callbacks, because it is locked to the window capturing mouse.
        }

        if (io.hasBackendFlags(ImGuiBackendFlags.HasMouseHoveredViewport)) {
            io.addMouseViewportEvent(mouseViewportId);
        }
    }

    protected void updateMouseCursor() {
        final ImGuiIO io = ImGui.getIO();

        if (io.hasConfigFlags(ImGuiConfigFlags.NoMouseCursorChange) || glfwGetInputMode(data.window, GLFW_CURSOR) == GLFW_CURSOR_DISABLED) {
            return;
        }

        final int imguiCursor = ImGui.getMouseCursor();
        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();

        for (int n = 0; n < platformIO.getViewportsSize(); n++) {
            final long windowPtr = platformIO.getViewports(n).getPlatformHandle();

            if (imguiCursor == ImGuiMouseCursor.None || io.getMouseDrawCursor()) {
                // Hide OS mouse cursor if imgui is drawing it or if it wants no cursor
                glfwSetInputMode(windowPtr, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            } else {
                // Show OS mouse cursor
                // FIXME-PLATFORM: Unfocused windows seems to fail changing the mouse cursor with GLFW 3.2, but 3.3 works here.
                glfwSetCursor(windowPtr, data.mouseCursors[imguiCursor] != 0 ? data.mouseCursors[imguiCursor] : data.mouseCursors[ImGuiMouseCursor.Arrow]);
                glfwSetInputMode(windowPtr, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
        }
    }

    @FunctionalInterface
    private interface MapButton {
        void run(int keyNo, int buttonNo, int _unused);
    }

    @FunctionalInterface
    private interface MapAnalog {
        void run(int keyNo, int axisNo, int _unused, float v0, float v1);
    }

    @SuppressWarnings("ManualMinMaxCalculation")
    private float saturate(final float v) {
        return v < 0.0f ? 0.0f : v > 1.0f ? 1.0f : v;
    }

    protected void updateGamepads() {
        final ImGuiIO io = ImGui.getIO();

        if (!io.hasConfigFlags(ImGuiConfigFlags.NavEnableGamepad)) {
            return;
        }

        io.removeBackendFlags(ImGuiBackendFlags.HasGamepad);

        final MapButton mapButton;
        final MapAnalog mapAnalog;

        if (glfwHasGamepadApi) {
            try (GLFWGamepadState gamepad = GLFWGamepadState.create()) {
                if (!glfwGetGamepadState(GLFW_JOYSTICK_1, gamepad)) {
                    return;
                }
                mapButton = (keyNo, buttonNo, _unused) -> io.addKeyEvent(keyNo, gamepad.buttons(buttonNo) != 0);
                mapAnalog = (keyNo, axisNo, _unused, v0, v1) -> {
                    float v = gamepad.axes(axisNo);
                    v = (v - v0) / (v1 - v0);
                    io.addKeyAnalogEvent(keyNo, v > 0.10f, saturate(v));
                };
            }
        } else {
            final FloatBuffer axes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
            final ByteBuffer buttons = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
            if (axes == null || axes.limit() == 0 || buttons == null || buttons.limit() == 0) {
                return;
            }
            mapButton = (keyNo, buttonNo, _unused) -> io.addKeyEvent(keyNo, (buttons.limit() > buttonNo && buttons.get(buttonNo) == GLFW_PRESS));
            mapAnalog = (keyNo, axisNo, _unused, v0, v1) -> {
                float v = (axes.limit() > axisNo) ? axes.get(axisNo) : v0;
                v = (v - v0) / (v1 - v0);
                io.addKeyAnalogEvent(keyNo, v > 0.10f, saturate(v));
            };
        }

        io.addBackendFlags(ImGuiBackendFlags.HasGamepad);
        mapButton.run(ImGuiKey.GamepadStart, GLFW_GAMEPAD_BUTTON_START, 7);
        mapButton.run(ImGuiKey.GamepadBack, GLFW_GAMEPAD_BUTTON_BACK, 6);
        mapButton.run(ImGuiKey.GamepadFaceDown, GLFW_GAMEPAD_BUTTON_A, 0);     // Xbox A, PS Cross
        mapButton.run(ImGuiKey.GamepadFaceRight, GLFW_GAMEPAD_BUTTON_B, 1);     // Xbox B, PS Circle
        mapButton.run(ImGuiKey.GamepadFaceLeft, GLFW_GAMEPAD_BUTTON_X, 2);     // Xbox X, PS Square
        mapButton.run(ImGuiKey.GamepadFaceUp, GLFW_GAMEPAD_BUTTON_Y, 3);     // Xbox Y, PS Triangle
        mapButton.run(ImGuiKey.GamepadDpadLeft, GLFW_GAMEPAD_BUTTON_DPAD_LEFT, 13);
        mapButton.run(ImGuiKey.GamepadDpadRight, GLFW_GAMEPAD_BUTTON_DPAD_RIGHT, 11);
        mapButton.run(ImGuiKey.GamepadDpadUp, GLFW_GAMEPAD_BUTTON_DPAD_UP, 10);
        mapButton.run(ImGuiKey.GamepadDpadDown, GLFW_GAMEPAD_BUTTON_DPAD_DOWN, 12);
        mapButton.run(ImGuiKey.GamepadL1, GLFW_GAMEPAD_BUTTON_LEFT_BUMPER, 4);
        mapButton.run(ImGuiKey.GamepadR1, GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER, 5);
        mapAnalog.run(ImGuiKey.GamepadL2, GLFW_GAMEPAD_AXIS_LEFT_TRIGGER, 4, -0.75f, +1.0f);
        mapAnalog.run(ImGuiKey.GamepadR2, GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER, 5, -0.75f, +1.0f);
        mapButton.run(ImGuiKey.GamepadL3, GLFW_GAMEPAD_BUTTON_LEFT_THUMB, 8);
        mapButton.run(ImGuiKey.GamepadR3, GLFW_GAMEPAD_BUTTON_RIGHT_THUMB, 9);
        mapAnalog.run(ImGuiKey.GamepadLStickLeft, GLFW_GAMEPAD_AXIS_LEFT_X, 0, -0.25f, -1.0f);
        mapAnalog.run(ImGuiKey.GamepadLStickRight, GLFW_GAMEPAD_AXIS_LEFT_X, 0, +0.25f, +1.0f);
        mapAnalog.run(ImGuiKey.GamepadLStickUp, GLFW_GAMEPAD_AXIS_LEFT_Y, 1, -0.25f, -1.0f);
        mapAnalog.run(ImGuiKey.GamepadLStickDown, GLFW_GAMEPAD_AXIS_LEFT_Y, 1, +0.25f, +1.0f);
        mapAnalog.run(ImGuiKey.GamepadRStickLeft, GLFW_GAMEPAD_AXIS_RIGHT_X, 2, -0.25f, -1.0f);
        mapAnalog.run(ImGuiKey.GamepadRStickRight, GLFW_GAMEPAD_AXIS_RIGHT_X, 2, +0.25f, +1.0f);
        mapAnalog.run(ImGuiKey.GamepadRStickUp, GLFW_GAMEPAD_AXIS_RIGHT_Y, 3, -0.25f, -1.0f);
        mapAnalog.run(ImGuiKey.GamepadRStickDown, GLFW_GAMEPAD_AXIS_RIGHT_Y, 3, +0.25f, +1.0f);
    }

    protected void updateMonitors() {
        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();
        final PointerBuffer monitors = glfwGetMonitors();
        if (monitors == null) {
            System.err.println("Unable to get monitors!");
            return;
        }

        platformIO.resizeMonitors(0);

        for (int n = 0; n < monitors.limit(); n++) {
            final long monitor = monitors.get(n);

            final GLFWVidMode vidMode = glfwGetVideoMode(monitor);
            if (vidMode == null) {
                System.err.println("Unable to get video mode!");
                return;
            }

            glfwGetMonitorPos(monitor, props.monitorX, props.monitorY);

            final float mainPosX = props.monitorX[0];
            final float mainPosY = props.monitorY[0];
            final float mainSizeX = vidMode.width();
            final float mainSizeY = vidMode.height();

            float workPosX = 0;
            float workPosY = 0;
            float workSizeX = 0;
            float workSizeY = 0;

            // Workaround a small GLFW issue reporting zero on monitor changes: https://github.com/glfw/glfw/pull/1761
            if (glfwHasMonitorWorkArea) {
                glfwGetMonitorWorkarea(monitor, props.monitorWorkAreaX, props.monitorWorkAreaY, props.monitorWorkAreaWidth, props.monitorWorkAreaHeight);
                if (props.monitorWorkAreaWidth[0] > 0 && props.monitorWorkAreaHeight[0] > 0) {
                    workPosX = props.monitorWorkAreaX[0];
                    workPosY = props.monitorWorkAreaY[0];
                    workSizeX = props.monitorWorkAreaWidth[0];
                    workSizeY = props.monitorWorkAreaHeight[0];
                }
            }

            float dpiScale = 0;

            // Warning: the validity of monitor DPI information on Windows depends on the application DPI awareness settings,
            // which generally needs to be set in the manifest or at runtime.
            if (glfwHasPerMonitorDpi) {
                glfwGetMonitorContentScale(monitor, props.monitorContentScaleX, props.monitorContentScaleY);
                dpiScale = props.monitorContentScaleX[0];
            }

            platformIO.pushMonitors(mainPosX, mainPosY, mainSizeX, mainSizeY, workPosX, workPosY, workSizeX, workSizeY, dpiScale);
        }

        data.wantUpdateMonitors = false;
    }

    public void newFrame() {
        final ImGuiIO io = ImGui.getIO();

        // Setup display size (every frame to accommodate for window resizing)
        glfwGetWindowSize(data.window, props.windowW, props.windowH);
        glfwGetFramebufferSize(data.window, props.displayW, props.displayH);
        io.setDisplaySize((float) props.windowW[0], (float) props.windowH[0]);
        if (props.windowW[0] > 0 && props.windowH[0] > 0) {
            final float scaleX = (float) props.displayW[0] / props.windowW[0];
            final float scaleY = (float) props.displayH[0] / props.windowH[0];
            io.setDisplayFramebufferScale(scaleX, scaleY);
        }

        if (data.wantUpdateMonitors) {
            updateMonitors();
        }

        // Setup time step
        final double currentTime = glfwGetTime();
        io.setDeltaTime(data.time > 0.0 ? (float) (currentTime - data.time) : 1.0f / 60.0f);
        data.time = currentTime;

        updateMouseData();
        updateMouseCursor();

        // Update game controllers (if enabled and available)
        updateGamepads();
    }

    //--------------------------------------------------------------------------------------------------------
    // MULTI-VIEWPORT / PLATFORM INTERFACE SUPPORT
    // This is an _advanced_ and _optional_ feature, allowing the backend to create and handle multiple viewports simultaneously.
    // If you are new to dear imgui or creating a new binding for dear imgui, it is recommended that you completely ignore this section first..
    //--------------------------------------------------------------------------------------------------------

    private static final class ViewportData {
        long window = -1;
        boolean windowOwned = false;
        int ignoreWindowPosEventFrame = -1;
        int ignoreWindowSizeEventFrame = -1;
    }

    private void windowCloseCallback(final long windowId) {
        final ImGuiViewport vp = ImGui.findViewportByPlatformHandle(windowId);
        if (vp.isValidPtr()) {
            vp.setPlatformRequestClose(true);
        }
    }

    // GLFW may dispatch window pos/size events after calling glfwSetWindowPos()/glfwSetWindowSize().
    // However: depending on the platform the callback may be invoked at different time:
    // - on Windows it appears to be called within the glfwSetWindowPos()/glfwSetWindowSize() call
    // - on Linux it is queued and invoked during glfwPollEvents()
    // Because the event doesn't always fire on glfwSetWindowXXX() we use a frame counter tag to only
    // ignore recent glfwSetWindowXXX() calls.
    private void windowPosCallback(final long windowId, final int xPos, final int yPos) {
        final ImGuiViewport vp = ImGui.findViewportByPlatformHandle(windowId);
        if (vp.isNotValidPtr()) {
            return;
        }

        final ViewportData vd = (ViewportData) vp.getPlatformUserData();
        if (vd != null) {
            final boolean ignoreEvent = (ImGui.getFrameCount() <= vd.ignoreWindowPosEventFrame + 1);
            if (ignoreEvent) {
                return;
            }
        }

        vp.setPlatformRequestMove(true);
    }

    private void windowSizeCallback(final long windowId, final int width, final int height) {
        final ImGuiViewport vp = ImGui.findViewportByPlatformHandle(windowId);
        if (vp.isNotValidPtr()) {
            return;
        }

        final ViewportData vd = (ViewportData) vp.getPlatformUserData();
        if (vd != null) {
            final boolean ignoreEvent = (ImGui.getFrameCount() <= vd.ignoreWindowSizeEventFrame + 1);
            if (ignoreEvent) {
                return;
            }
        }

        vp.setPlatformRequestResize(true);
    }

    private final class CreateWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            final ViewportData vd = new ViewportData();
            vp.setPlatformUserData(vd);

            // GLFW 3.2 unfortunately always set focus on glfwCreateWindow() if GLFW_VISIBLE is set, regardless of GLFW_FOCUSED
            // With GLFW 3.3, the hint GLFW_FOCUS_ON_SHOW fixes this problem
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            glfwWindowHint(GLFW_FOCUSED, GLFW_FALSE);
            if (glfwHasFocusOnShow) {
                glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_FALSE);
            }
            glfwWindowHint(GLFW_DECORATED, vp.hasFlags(ImGuiViewportFlags.NoDecoration) ? GLFW_FALSE : GLFW_TRUE);
            if (glfwHawWindowTopmost) {
                glfwWindowHint(GLFW_FLOATING, vp.hasFlags(ImGuiViewportFlags.TopMost) ? GLFW_TRUE : GLFW_FALSE);
            }

            vd.window = glfwCreateWindow((int) vp.getSizeX(), (int) vp.getSizeY(), "No Title Yet", NULL, data.window);
            vd.windowOwned = true;

            vp.setPlatformHandle(vd.window);

            if (IS_WINDOWS) {
                vp.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(vd.window));
            }

            glfwSetWindowPos(vd.window, (int) vp.getPosX(), (int) vp.getPosY());

            // Install GLFW callbacks for secondary viewports
            glfwSetWindowFocusCallback(vd.window, ImGuiImplGlfw.this::windowFocusCallback);
            glfwSetCursorEnterCallback(vd.window, ImGuiImplGlfw.this::cursorEnterCallback);
            glfwSetCursorPosCallback(vd.window, ImGuiImplGlfw.this::cursorPosCallback);
            glfwSetMouseButtonCallback(vd.window, ImGuiImplGlfw.this::mouseButtonCallback);
            glfwSetScrollCallback(vd.window, ImGuiImplGlfw.this::scrollCallback);
            glfwSetKeyCallback(vd.window, ImGuiImplGlfw.this::keyCallback);
            glfwSetCharCallback(vd.window, ImGuiImplGlfw.this::charCallback);
            glfwSetWindowCloseCallback(vd.window, ImGuiImplGlfw.this::windowCloseCallback);
            glfwSetWindowPosCallback(vd.window, ImGuiImplGlfw.this::windowPosCallback);
            glfwSetWindowSizeCallback(vd.window, ImGuiImplGlfw.this::windowSizeCallback);

            glfwMakeContextCurrent(vd.window);
            glfwSwapInterval(0);
        }
    }

    private final class DestroyWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();

            if (vd != null && vd.windowOwned) {
                if (!glfwHasMousePassthrough && glfwHasWindowHovered && IS_WINDOWS) {
                    // TODO: RemovePropA
                }

                // Release any keys that were pressed in the window being destroyed and are still held down,
                // because we will not receive any release events after window is destroyed.
                for (int i = 0; i < data.keyOwnerWindows.length; i++) {
                    if (data.keyOwnerWindows[i] == vd.window) {
                        keyCallback(vd.window, i, 0, GLFW_RELEASE, 0); // Later params are only used for main viewport, on which this function is never called.
                    }
                }

                Callbacks.glfwFreeCallbacks(vd.window);
                glfwDestroyWindow(vd.window);

                vd.window = -1;
            }


            vp.setPlatformHandle(-1);
            vp.setPlatformUserData(null);
        }
    }

    private static final class ShowWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd == null) {
                return;
            }

            if (IS_WINDOWS && vp.hasFlags(ImGuiViewportFlags.NoTaskBarIcon)) {
                ImGuiImplGlfwNative.win32hideFromTaskBar(vp.getPlatformHandleRaw());
            }

            glfwShowWindow(vd.window);
        }
    }

    private static final class GetWindowPosFunction extends ImPlatformFuncViewportSuppImVec2 {
        private final int[] posX = new int[1];
        private final int[] posY = new int[1];

        @Override
        public void get(final ImGuiViewport vp, final ImVec2 dst) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd == null) {
                return;
            }
            posX[0] = 0;
            posY[0] = 0;
            glfwGetWindowPos(vd.window, posX, posY);
            dst.set(posX[0], posY[0]);
        }
    }

    private static final class SetWindowPosFunction extends ImPlatformFuncViewportImVec2 {
        @Override
        public void accept(final ImGuiViewport vp, final ImVec2 value) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd == null) {
                return;
            }
            vd.ignoreWindowPosEventFrame = ImGui.getFrameCount();
            glfwSetWindowPos(vd.window, (int) value.x, (int) value.y);
        }
    }

    private static final class GetWindowSizeFunction extends ImPlatformFuncViewportSuppImVec2 {
        private final int[] width = new int[1];
        private final int[] height = new int[1];

        @Override
        public void get(final ImGuiViewport vp, final ImVec2 dst) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd == null) {
                return;
            }
            width[0] = 0;
            height[0] = 0;
            glfwGetWindowSize(vd.window, width, height);
            dst.x = width[0];
            dst.y = height[0];
        }
    }

    private final class SetWindowSizeFunction extends ImPlatformFuncViewportImVec2 {
        private final int[] x = new int[1];
        private final int[] y = new int[1];
        private final int[] width = new int[1];
        private final int[] height = new int[1];

        @Override
        public void accept(final ImGuiViewport vp, final ImVec2 value) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd == null) {
                return;
            }
            if (IS_APPLE && !glfwHasOsxWindowPosFix) {
                // Native OS windows are positioned from the bottom-left corner on macOS, whereas on other platforms they are
                // positioned from the upper-left corner. GLFW makes an effort to convert macOS style coordinates, however it
                // doesn't handle it when changing size. We are manually moving the window in order for changes of size to be based
                // on the upper-left corner.
                x[0] = 0;
                y[0] = 0;
                width[0] = 0;
                height[0] = 0;
                glfwGetWindowPos(vd.window, x, y);
                glfwGetWindowSize(vd.window, width, height);
                glfwSetWindowPos(vd.window, x[0], y[0] - height[0] + (int) value.y);
            }
            vd.ignoreWindowSizeEventFrame = ImGui.getFrameCount();
            glfwSetWindowSize(vd.window, (int) value.x, (int) value.y);
        }
    }

    private static final class SetWindowTitleFunction extends ImPlatformFuncViewportString {
        @Override
        public void accept(final ImGuiViewport vp, final String value) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd != null) {
                glfwSetWindowTitle(vd.window, value);
            }
        }
    }

    private final class SetWindowFocusFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            if (glfwHasFocusWindow) {
                final ViewportData vd = (ViewportData) vp.getPlatformUserData();
                if (vd != null) {
                    glfwFocusWindow(vd.window);
                }
            }
        }
    }

    private static final class GetWindowFocusFunction extends ImPlatformFuncViewportSuppBoolean {
        @Override
        public boolean get(final ImGuiViewport vp) {
            final ViewportData data = (ViewportData) vp.getPlatformUserData();
            return glfwGetWindowAttrib(data.window, GLFW_FOCUSED) != 0;
        }
    }

    private static final class GetWindowMinimizedFunction extends ImPlatformFuncViewportSuppBoolean {
        @Override
        public boolean get(final ImGuiViewport vp) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd != null) {
                return glfwGetWindowAttrib(vd.window, GLFW_ICONIFIED) != GLFW_FALSE;
            }
            return false;
        }
    }

    private final class SetWindowAlphaFunction extends ImPlatformFuncViewportFloat {
        @Override
        public void accept(final ImGuiViewport vp, final float value) {
            if (glfwHasWindowAlpha) {
                final ViewportData vd = (ViewportData) vp.getPlatformUserData();
                if (vd != null) {
                    glfwSetWindowOpacity(vd.window, value);
                }
            }
        }
    }

    private static final class RenderWindowFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd != null) {
                glfwMakeContextCurrent(vd.window);
            }
        }
    }

    private static final class SwapBuffersFunction extends ImPlatformFuncViewport {
        @Override
        public void accept(final ImGuiViewport vp) {
            final ViewportData vd = (ViewportData) vp.getPlatformUserData();
            if (vd != null) {
                glfwMakeContextCurrent(vd.window);
                glfwSwapBuffers(vd.window);
            }
        }
    }

    protected void initPlatformInterface() {
        final ImGuiPlatformIO platformIO = ImGui.getPlatformIO();

        // Register platform interface (will be coupled with a renderer interface)
        platformIO.setPlatformCreateWindow(new CreateWindowFunction());
        platformIO.setPlatformDestroyWindow(new DestroyWindowFunction());
        platformIO.setPlatformShowWindow(new ShowWindowFunction());
        platformIO.setPlatformGetWindowPos(new GetWindowPosFunction());
        platformIO.setPlatformSetWindowPos(new SetWindowPosFunction());
        platformIO.setPlatformGetWindowSize(new GetWindowSizeFunction());
        platformIO.setPlatformSetWindowSize(new SetWindowSizeFunction());
        platformIO.setPlatformSetWindowTitle(new SetWindowTitleFunction());
        platformIO.setPlatformSetWindowFocus(new SetWindowFocusFunction());
        platformIO.setPlatformGetWindowFocus(new GetWindowFocusFunction());
        platformIO.setPlatformGetWindowMinimized(new GetWindowMinimizedFunction());
        platformIO.setPlatformSetWindowAlpha(new SetWindowAlphaFunction());
        platformIO.setPlatformRenderWindow(new RenderWindowFunction());
        platformIO.setPlatformSwapBuffers(new SwapBuffersFunction());

        // Register main window handle (which is owned by the main application, not by us)
        // This is mostly for simplicity and consistency, so that our code (e.g. mouse handling etc.) can use same logic for main and secondary viewports.
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        final ViewportData vd = new ViewportData();
        vd.window = data.window;
        vd.windowOwned = false;
        mainViewport.setPlatformUserData(vd);
        mainViewport.setPlatformHandle(data.window);
    }

    protected void shutdownPlatformInterface() {
        ImGui.destroyPlatformWindows();
    }
}
