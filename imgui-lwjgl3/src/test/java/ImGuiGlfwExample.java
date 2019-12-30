import imgui.ImBool;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImString;
import imgui.enums.ImGuiBackendFlags;
import imgui.enums.ImGuiColorEditFlags;
import imgui.enums.ImGuiCond;
import imgui.enums.ImGuiConfigFlags;
import imgui.enums.ImGuiInputTextFlags;
import imgui.enums.ImGuiKey;
import imgui.enums.ImGuiMouseCursor;
import imgui.gl3.ImGuiImplGl3;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings("MagicNumber")
public final class ImGuiGlfwExample {
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;

    private long window; // current GLFW window pointer

    // Those are used to track window size properties
    private final int[] winWidth = new int[1];
    private final int[] winHeight = new int[1];
    private final int[] fbWidth = new int[1];
    private final int[] fbHeight = new int[1];

    // For mouse tracking
    private final double[] mousePosX = new double[1];
    private final double[] mousePosY = new double[1];

    // Mouse cursors provided by GLFW
    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];

    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    // Local app variables go here
    private final ImString imguiDemoLink = new ImString("https://raw.githubusercontent.com/ocornut/imgui/v1.74/imgui_demo.cpp", 100);
    private float[] backgroundColor = new float[]{0.5f, 0, 0};
    private int clickCount = 0;
    private final byte[] testPayload = "Test Payload".getBytes();
    private String dropTargetText = "Drop Here";

    private final ImBool showDemoWindow = new ImBool();

    public void run() {
        initGlfw();
        initImGui();
        loop();
        destroyImGui();
        destroyGlfw();
    }

    // Method initializes GLFW window. All code is mostly a copy-paste from the official LWJGL3 website.
    private void initGlfw() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be maximized
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE); // the window will be decorated

        // Create the window
        window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, "ImGui+GLFW+LWJGL Example", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            final GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));

            // Center the window
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(window); // Make the OpenGL context current
        glfwSwapInterval(GLFW_TRUE); // Enable v-sync
        glfwShowWindow(window); // Make the window visible

        // IMPORTANT!!
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    // Here we will initialize ImGui stuff.
    private void initImGui() {
        // IMPORTANT!!
        // This line is critical for ImGui to work.
        ImGui.CreateContext();

        // ImGui provides three different color schemas for styling. We will use the classic one here.
        ImGui.StyleColorsClassic();
        // ImGui.StyleColorsDark(); // This is a default style for ImGui
        // ImGui.StyleColorsLight();

        // Initialize ImGuiIO config
        final ImGuiIO io = ImGui.GetIO();

        io.setIniFilename(null);
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors);
        io.setBackendPlatformName("imgui_java_impl_glfw");
        io.setBackendRendererName("imgui_java_impl_lwjgl");

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

        // Mouse cursors mapping
        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);

        // Here goes GLFW callbacks to update user input stuff in ImGui
        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                io.setKeysDown(key, true);
            } else if (action == GLFW_RELEASE) {
                io.setKeysDown(key, false);
            }

            io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
        });

        glfwSetCharCallback(window, (w, c) -> {
            if (c != GLFW_KEY_DELETE) {
                io.AddInputCharacter(c);
            }
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            final boolean[] mouseDown = new boolean[5];

            mouseDown[0] = button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE;
            mouseDown[1] = button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE;
            mouseDown[2] = button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE;
            mouseDown[3] = button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE;
            mouseDown[4] = button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE;

            io.setMouseDown(mouseDown);

            if (!io.getWantCaptureMouse() && mouseDown[1]) {
                ImGui.SetWindowFocus(null);
            }
        });

        glfwSetScrollCallback(window, (w, xOffset, yOffset) -> {
            io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
            io.setMouseWheel(io.getMouseWheel() + (float) yOffset);
        });

        // Initialize renderer itself
        imGuiGl3.init();
    }

    private void loop() {
        double time = 0; // to track our frame delta value

        // Run the rendering loop until the user has attempted to close the window
        while (!glfwWindowShouldClose(window)) {
            // Count frame delta value
            final double currentTime = glfwGetTime();
            final double deltaTime = (time > 0) ? (currentTime - time) : 1f / 60f;
            time = currentTime;

            // Set the clear color
            glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 0.0f);

            // Get window size properties and mouse position
            glfwGetWindowSize(window, winWidth, winHeight);
            glfwGetFramebufferSize(window, fbWidth, fbHeight);
            glfwGetCursorPos(window, mousePosX, mousePosY);

            // IMPORTANT!!
            // We SHOULD call those methods to update ImGui state for current frame
            final ImGuiIO io = ImGui.GetIO();
            io.setDisplaySize(winWidth[0], winHeight[0]);
            io.setDisplayFramebufferScale((float) fbWidth[0] / winWidth[0], (float) fbHeight[0] / winHeight[0]);
            io.setMousePos((float) mousePosX[0], (float) mousePosY[0]);
            io.setDeltaTime((float) deltaTime);

            // Update mouse cursor
            final int imguiCursor = ImGui.GetMouseCursor();
            glfwSetCursor(window, mouseCursors[imguiCursor]);
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

            // Render itself starts here
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // IMPORTANT!!
            // Any ImGui code SHOULD go between NewFrame()/Render() methods
            ImGui.NewFrame();
            showUi();
            ImGui.End();

            if (showDemoWindow.get()) {
                ImGui.ShowDemoWindow(showDemoWindow);
            }

            ImGui.Render();

            imGuiGl3.render(ImGui.GetDrawData()); // render DrawData from ImGui into our OpenGL context

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }
    }

    private void showUi() {
        ImGui.SetNextWindowSize(600, 210, ImGuiCond.Once);
        ImGui.SetNextWindowPos(10, 10, ImGuiCond.Once);

        ImGui.Begin("Custom window");
        ImGui.Text("Hello from Java!");
        ImGui.Button("Drag me");
        if (ImGui.BeginDragDropSource()) {
            ImGui.SetDragDropPayload("payload_type", testPayload, testPayload.length);
            ImGui.Text("Drag started");
            ImGui.EndDragDropSource();
        }
        ImGui.SameLine();
        ImGui.Text(dropTargetText);
        if (ImGui.BeginDragDropTarget()) {
            final byte[] payload = ImGui.AcceptDragDropPayload("payload_type");
            if (payload != null) {
                dropTargetText = new String(payload);
            }
            ImGui.EndDragDropTarget();
        }

        ImGui.AlignTextToFramePadding();
        ImGui.Text("Background color:");
        ImGui.SameLine();
        ImGui.ColorEdit3("##click_counter_col", backgroundColor, ImGuiColorEditFlags.NoInputs | ImGuiColorEditFlags.NoDragDrop);
        if (ImGui.Button("Click")) {
            clickCount++;
        }
        if (ImGui.IsItemHovered()) {
            ImGui.SetMouseCursor(ImGuiMouseCursor.Hand);
        }
        ImGui.SameLine();
        ImGui.Text("Count: " + clickCount);
        ImGui.Checkbox("Show demo window", showDemoWindow);
        ImGui.NewLine();

        ImGui.Separator();
        ImGui.Text("Consider to look the original ImGui demo: ");
        ImGui.SetNextItemWidth(500);
        ImGui.InputText("##input_to_copy_link", imguiDemoLink, ImGuiInputTextFlags.ReadOnly);
        if (ImGui.IsItemHovered()) {
            ImGui.SetMouseCursor(ImGuiMouseCursor.TextInput);
        }
        ImGui.SameLine();
        ImGui.Text("(?)");
        if (ImGui.IsItemHovered()) {
            ImGui.SetTooltip("You can copy and paste this link to browser");
        }
    }

    // If you want to clean a room after yourself - do it by yourself
    private void destroyImGui() {
        imGuiGl3.dispose();
        ImGui.DestroyContext();
    }

    private void destroyGlfw() {
        for (long mouseCursor : mouseCursors) {
            glfwDestroyCursor(mouseCursor);
        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static void main(final String[] args) {
        new ImGuiGlfwExample().run();
    }
}
