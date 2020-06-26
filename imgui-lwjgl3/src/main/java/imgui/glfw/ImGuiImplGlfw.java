package imgui.glfw;

import static org.lwjgl.glfw.GLFW.GLFW_ARROW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_HRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_IBEAM_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_VRESIZE_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;
import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImVec2;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiNavInput;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

/**
 * This class is a straightforward port of the
 * <a href="https://raw.githubusercontent.com/ocornut/imgui/v1.76/examples/imgui_impl_glfw.cpp">imgui_impl_glfw.cpp</a>.
 * <p>
 * It supports clipboard, gamepad, mouse and keyboard in the same way the original Dear ImGui code does.
 * You can copy-paste this class in your codebase and modify the rendering routine in the way you'd like.
 * <p>
 */
public class ImGuiImplGlfw {

    private static final boolean[] mouseJustPressed = new boolean[ImGuiMouseButton.COUNT];
    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
    private GLFWMouseButtonCallback previousMouseButtonCallback = null;
    private GLFWScrollCallback previousScrollCallback = null;
    private GLFWKeyCallback previousKeyCallback = null;
    private GLFWCharCallback previousCharCallback = null;
    private long windowId;
    private boolean callbacksInstalled;
    private double time = 0.0;

    /**
     * Method to set the {@link GLFWMouseButtonCallback}.
     */
    public void mouseButtonCallback(final long windowId, final int button, final int action, final int mods) {
        if (previousMouseButtonCallback != null) {
            previousMouseButtonCallback.invoke(windowId, button, action, mods);
        }

        if (action == GLFW_PRESS && button >= 0 && button < mouseJustPressed.length) {
            mouseJustPressed[button] = true;
        }
    }

    /**
     * Method to set the {@link GLFWScrollCallback}.
     */
    public void scrollCallback(final long windowId, final double xOffset, final double yOffset) {
        if (previousScrollCallback != null) {
            previousScrollCallback.invoke(windowId, xOffset, yOffset);
        }

        final ImGuiIO io = ImGui.getIO();
        io.setMouseWheelH((float) xOffset);
        io.setMouseWheel((float) yOffset);
    }

    /**
     * Method to set the {@link GLFWKeyCallback}.
     */
    public void keyCallback(final long windowId, final int key, final int scancode, final int action, final int mods) {
        if (previousKeyCallback != null) {
            previousKeyCallback.invoke(windowId, key, scancode, action, mods);
        }

        final ImGuiIO io = ImGui.getIO();
        if (action == GLFW_PRESS) {
            io.setKeysDown(key, true);
        }
        if (action == GLFW_RELEASE) {
            io.setKeysDown(key, false);
        }

        io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
        io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
        io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
        io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
    }

    /**
     * Method to set the {@link GLFWCharCallback}.
     */
    public void charCallback(final long windowId, final int c) {
        if (previousCharCallback != null) {
            previousCharCallback.invoke(windowId, c);
        }

        final ImGuiIO io = ImGui.getIO();
        io.addInputCharacter(c);
    }

    /**
     * Method to do an initialization of the {@link ImGuiImplGlfw} state. It SHOULD be called before calling the {@link ImGuiImplGlfw#newFrame()} method.
     * <p>
     * Method takes two arguments, which should be a valid GLFW window pointer and a boolean indicating whether or not to install callbacks.
     */
    public boolean init(final long windowId, final boolean installCallbacks) {
        this.windowId = windowId;
        time = 0.0;

        final ImGuiIO io = ImGui.getIO();

        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors | ImGuiBackendFlags.HasSetMousePos);
        io.setBackendPlatformName("imgui_java_impl_glfw");

        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
        final int[] keyMap = new int[ImGuiKey.COUNT];
        keyMap[ImGuiKey.Tab] = GLFW_KEY_TAB;
        keyMap[ImGuiKey.LeftArrow] = GLFW_KEY_LEFT;
        keyMap[ImGuiKey.RightArrow] = GLFW_KEY_RIGHT;
        keyMap[ImGuiKey.UpArrow] = GLFW_KEY_UP;
        keyMap[ImGuiKey.DownArrow] = GLFW_KEY_DOWN;
        keyMap[ImGuiKey.PageUp] = GLFW_KEY_PAGE_UP;
        keyMap[ImGuiKey.PageDown] = GLFW_KEY_PAGE_DOWN;
        keyMap[ImGuiKey.Home] = GLFW_KEY_HOME;
        keyMap[ImGuiKey.End] = GLFW_KEY_END;
        keyMap[ImGuiKey.Insert] = GLFW_KEY_INSERT;
        keyMap[ImGuiKey.Delete] = GLFW_KEY_DELETE;
        keyMap[ImGuiKey.Backspace] = GLFW_KEY_BACKSPACE;
        keyMap[ImGuiKey.Space] = GLFW_KEY_SPACE;
        keyMap[ImGuiKey.Enter] = GLFW_KEY_ENTER;
        keyMap[ImGuiKey.Escape] = GLFW_KEY_ESCAPE;
        keyMap[ImGuiKey.KeyPadEnter] = GLFW_KEY_KP_ENTER;
        keyMap[ImGuiKey.A] = GLFW_KEY_A;
        keyMap[ImGuiKey.C] = GLFW_KEY_C;
        keyMap[ImGuiKey.V] = GLFW_KEY_V;
        keyMap[ImGuiKey.X] = GLFW_KEY_X;
        keyMap[ImGuiKey.Y] = GLFW_KEY_Y;
        keyMap[ImGuiKey.Z] = GLFW_KEY_Z;

        io.setKeyMap(keyMap);

        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(windowId);
                return clipboardString != null ? clipboardString : "";
            }
        });

        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String str) {
                glfwSetClipboardString(windowId, str);
            }
        });

        // Mouse cursors mapping. Disable errors whilst setting due to X11.
        final GLFWErrorCallback prevErrorCallback = glfwSetErrorCallback(null);
        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
        mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        glfwSetErrorCallback(prevErrorCallback);

        if (installCallbacks) {
            callbacksInstalled = true;
            previousMouseButtonCallback = glfwSetMouseButtonCallback(windowId, this::mouseButtonCallback);
            previousScrollCallback = glfwSetScrollCallback(windowId, this::scrollCallback);
            previousKeyCallback = glfwSetKeyCallback(windowId, this::keyCallback);
            previousCharCallback = glfwSetCharCallback(windowId, this::charCallback);
        }

        return true;
    }

    /**
     * Method to restore {@link org.lwjgl.glfw.GLFW} to it's state prior to calling method {@link ImGuiImplGlfw#init(long, boolean)}.
     */
    public void dispose() {
        if (callbacksInstalled) {
            glfwSetMouseButtonCallback(windowId, previousMouseButtonCallback);
            glfwSetScrollCallback(windowId, previousScrollCallback);
            glfwSetKeyCallback(windowId, previousKeyCallback);
            glfwSetCharCallback(windowId, previousCharCallback);
            callbacksInstalled = false;
        }

        for (int i = 0; i < ImGuiMouseCursor.COUNT; i++) {
            glfwDestroyCursor(mouseCursors[i]);
        }
    }

    private void updateMousePosAndButtons() {
        final ImGuiIO io = ImGui.getIO();
        for (int i = 0; i < ImGuiMouseButton.COUNT; i++) {
            io.setMouseDown(i, mouseJustPressed[i] || glfwGetMouseButton(windowId, i) != 0);
            mouseJustPressed[i] = false;
        }

        final ImVec2 mousePosBackup = new ImVec2();
        io.getMousePos(mousePosBackup);
        io.setMousePos(-Float.MAX_VALUE, -Float.MAX_VALUE);

        final boolean focused = glfwGetWindowAttrib(windowId, GLFW_FOCUSED) != 0;
        if (focused) {
            if (io.getWantSetMousePos()) {
                glfwSetCursorPos(windowId, mousePosBackup.x, mousePosBackup.y);
            } else {
                final double[] cursorPosX = new double[1];
                final double[] cursorPosY = new double[1];
                glfwGetCursorPos(windowId, cursorPosX, cursorPosY);
                io.setMousePos((float) cursorPosX[0], (float) cursorPosY[0]);
            }
        }
    }

    private void updateMouseCursor() {
        final ImGuiIO io = ImGui.getIO();
        final boolean noCursorChange = (io.getConfigFlags() & ImGuiConfigFlags.NoMouseCursorChange)
            == ImGuiConfigFlags.NoMouseCursorChange;
        final boolean cursorDisabled = glfwGetInputMode(windowId, GLFW_CURSOR) == GLFW_CURSOR_DISABLED;
        if (noCursorChange || cursorDisabled) {
            return;
        }

        final int cursor = ImGui.getMouseCursor();
        if (cursor == ImGuiMouseCursor.None || io.getMouseDrawCursor()) {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        } else {
            glfwSetCursor(windowId, mouseCursors[cursor] != 0 ? mouseCursors[cursor]
                : mouseCursors[ImGuiMouseCursor.Arrow]);
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }

    private void updateGamepads() {
        final ImGuiIO io = ImGui.getIO();

        final float[] navInputs = new float[ImGuiNavInput.COUNT];
        io.setNavInputs(navInputs);

        if ((io.getConfigFlags() & ImGuiConfigFlags.NavEnableGamepad) == 0) {
            return;
        }

        final ByteBuffer buttons = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
        final int buttonsCount = buttons.limit();

        final FloatBuffer axis = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
        final int axisCount = axis.limit();

        mapButton(ImGuiNavInput.Activate, 0, buttons, buttonsCount, io);     // Cross / A
        mapButton(ImGuiNavInput.Cancel, 1, buttons, buttonsCount, io);     // Circle / B
        mapButton(ImGuiNavInput.Menu, 2, buttons, buttonsCount, io);     // Square / X
        mapButton(ImGuiNavInput.Input, 3, buttons, buttonsCount, io);     // Triangle / Y
        mapButton(ImGuiNavInput.DpadLeft, 13, buttons, buttonsCount, io);    // D-Pad Left
        mapButton(ImGuiNavInput.DpadRight, 11, buttons, buttonsCount, io);    // D-Pad Right
        mapButton(ImGuiNavInput.DpadUp, 10, buttons, buttonsCount, io);    // D-Pad Up
        mapButton(ImGuiNavInput.DpadDown, 12, buttons, buttonsCount, io);    // D-Pad Down
        mapButton(ImGuiNavInput.FocusPrev, 4, buttons, buttonsCount, io);     // L1 / LB
        mapButton(ImGuiNavInput.FocusNext, 5, buttons, buttonsCount, io);     // R1 / RB
        mapButton(ImGuiNavInput.TweakSlow, 4, buttons, buttonsCount, io);     // L1 / LB
        mapButton(ImGuiNavInput.TweakFast, 5, buttons, buttonsCount, io);     // R1 / RB
        mapAnalog(ImGuiNavInput.LStickLeft, 0, -0.3f, -0.9f, axis, axisCount, io);
        mapAnalog(ImGuiNavInput.LStickRight, 0, +0.3f, +0.9f, axis, axisCount, io);
        mapAnalog(ImGuiNavInput.LStickUp, 1, +0.3f, +0.9f, axis, axisCount, io);
        mapAnalog(ImGuiNavInput.LStickDown, 1, -0.3f, -0.9f, axis, axisCount, io);

        if (axisCount > 0 && buttonsCount > 0) {
            io.setBackendFlags(io.getBackendFlags() | ImGuiBackendFlags.HasGamepad);
        } else {
            io.setBackendFlags(io.getBackendFlags() & ~ImGuiBackendFlags.HasGamepad);
        }
    }

    private void mapButton(final int navNo, final int buttonNo, final ByteBuffer buttons, final int buttonsCount, final ImGuiIO io) {
        if (buttonsCount > buttonNo && buttons.get(buttonNo) == GLFW_PRESS) {
            io.setNavInputs(navNo, 1.0f);
        }
    }

    private void mapAnalog(
        final int navNo,
        final int axisNo,
        final float v0,
        final float v1,
        final FloatBuffer axis,
        final int axisCount,
        final ImGuiIO io
    ) {
        float v = axisCount > axisNo ? axis.get(axisNo) : v0;
        v = (v - v0) / (v1 - v0);
        if (v > 1.0f) {
            v = 1.0f;
        }
        if (io.getNavInputs(navNo) < v) {
            io.setNavInputs(navNo, v);
        }
    }

    /**
     * Updates {@link ImGuiIO} and {@link org.lwjgl.glfw.GLFW} state.
     */
    public void newFrame() {
        final ImGuiIO io = ImGui.getIO();
        if (!io.getFonts().isBuilt()) {
            throw new IllegalStateException(
                "Font atlas not built! It is generally built by the renderer back-end. Missing call to renderer init() method? e.g. ImGuiImplGl3.init().");
        }

        final int[] width = new int[1];
        final int[] height = new int[1];
        glfwGetWindowSize(windowId, width, height);

        final int[] displayWidth = new int[1];
        final int[] displayHeight = new int[1];
        glfwGetFramebufferSize(windowId, displayWidth, displayHeight);

        io.setDisplaySize((float) width[0], (float) height[0]);
        if ((float) width[0] > 0 && (float) height[0] > 0) {
            io.setDisplayFramebufferScale(
                (float) displayWidth[0] / (float) width[0],
                (float) displayHeight[0] / (float) height[0]
            );
        }

        final double currentTime = glfwGetTime();
        io.setDeltaTime(time > 0.0 ? (float) (currentTime - time) : 1.0f / 60.0f);

        updateMousePosAndButtons();
        updateMouseCursor();
        updateGamepads();
    }
}
