import imgui.ImGui;
import imgui.ImGuiString;
import imgui.enums.ImGuiCond;
import imgui.enums.ImGuiInputTextFlags;
import imgui.enums.ImGuiKey;
import imgui.enums.ImGuiMouseCursor;
import imgui.enums.ImGuiTreeNodeFlags;
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

    private long window; // current GLFW window ID

    // Those are used to track window size properties
    private final int[] winWidth = new int[1];
    private final int[] winHeight = new int[1];
    private final int[] fbWidth = new int[1];
    private final int[] fbHeight = new int[1];

    // For mouse tracking
    private final double[] mousePosX = new double[1];
    private final double[] mousePosY = new double[1];

    // TODO move this into ImGuiIO.KeysDown
    private boolean ctrlKeyDown;
    private boolean shiftKeyDown;
    private boolean altKeyDown;
    private boolean superKeyDown;

    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT.getValue()];

    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    // Local app variables go here
    private final ImGuiString imguiDemoLink = new ImGuiString("https://raw.githubusercontent.com/ocornut/imgui/v1.74/imgui_demo.cpp");
    private int clickCount = 0;

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
        // It loads native library, creates ImGui context and does other necessary stuff.
        ImGui.init();

        // ImGui provides three different color schemas for styling. We will use the classic one here.
        ImGui.StyleColorsClassic();
        // ImGui.StyleColorsDark(); // This is a default style for ImGui
        // ImGui.StyleColorsLight();

        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
        final int[] keys = new int[ImGuiKey.COUNT.code];
        keys[ImGuiKey.Tab.code] = GLFW_KEY_TAB;
        keys[ImGuiKey.LeftArrow.code] = GLFW_KEY_LEFT;
        keys[ImGuiKey.RightArrow.code] = GLFW_KEY_RIGHT;
        keys[ImGuiKey.UpArrow.code] = GLFW_KEY_UP;
        keys[ImGuiKey.DownArrow.code] = GLFW_KEY_DOWN;
        keys[ImGuiKey.PageUp.code] = GLFW_KEY_PAGE_UP;
        keys[ImGuiKey.PageDown.code] = GLFW_KEY_PAGE_DOWN;
        keys[ImGuiKey.Home.code] = GLFW_KEY_HOME;
        keys[ImGuiKey.End.code] = GLFW_KEY_END;
        keys[ImGuiKey.Insert.code] = GLFW_KEY_INSERT;
        keys[ImGuiKey.Delete.code] = GLFW_KEY_DELETE;
        keys[ImGuiKey.Backspace.code] = GLFW_KEY_BACKSPACE;
        keys[ImGuiKey.Space.code] = GLFW_KEY_SPACE;
        keys[ImGuiKey.Enter.code] = GLFW_KEY_ENTER;
        keys[ImGuiKey.Escape.code] = GLFW_KEY_ESCAPE;
        keys[ImGuiKey.KeyPadEnter.code] = GLFW_KEY_KP_ENTER;
        keys[ImGuiKey.A.code] = GLFW_KEY_A;
        keys[ImGuiKey.C.code] = GLFW_KEY_C;
        keys[ImGuiKey.V.code] = GLFW_KEY_V;
        keys[ImGuiKey.X.code] = GLFW_KEY_X;
        keys[ImGuiKey.Y.code] = GLFW_KEY_Y;
        keys[ImGuiKey.Z.code] = GLFW_KEY_Z;
        ImGui.initKeyMap(keys);

        // Mouse cursors mapping
        mouseCursors[ImGuiMouseCursor.Arrow.getValue()] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput.getValue()] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeAll.getValue()] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS.getValue()] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW.getValue()] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNESW.getValue()] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNWSE.getValue()] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand.getValue()] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);

        // Here goes GLFW callbacks to update user input stuff in ImGui
        glfwSetKeyCallback(window, (w, key, scancode, action, mods) -> {
            final boolean isKeyDown = action == GLFW_PRESS;

            if (key == GLFW_KEY_LEFT_CONTROL || key == GLFW_KEY_RIGHT_CONTROL) {
                ctrlKeyDown = isKeyDown;
            } else if (key == GLFW_KEY_LEFT_SHIFT || key == GLFW_KEY_RIGHT_SHIFT) {
                shiftKeyDown = isKeyDown;
            } else if (key == GLFW_KEY_LEFT_ALT || key == GLFW_KEY_RIGHT_ALT) {
                altKeyDown = isKeyDown;
            } else if (key == GLFW_KEY_LEFT_SUPER || key == GLFW_KEY_RIGHT_SUPER) {
                superKeyDown = isKeyDown;
            }

            ImGui.UpdateKey(key, isKeyDown, action == GLFW_RELEASE, ctrlKeyDown, shiftKeyDown, altKeyDown, superKeyDown);
        });

        glfwSetCharCallback(window, (w, c) -> {
            if (c != GLFW_KEY_DELETE) {
                ImGui.UpdateKeyTyped(c);
            }
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            final boolean mouseDown0 = button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE;
            final boolean mouseDown1 = button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE;
            final boolean mouseDown2 = button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE;
            final boolean mouseDown3 = button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE;
            final boolean mouseDown4 = button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE;
            final boolean mouseDown5 = button == GLFW_MOUSE_BUTTON_6 && action != GLFW_RELEASE;
            ImGui.UpdateMouseState(mouseDown0, mouseDown1, mouseDown2, mouseDown3, mouseDown4, mouseDown5);

            if (!ImGui.GetIO().WantCaptureMouse && mouseDown1) {
                ImGui.SetWindowFocus(null);
            }
        });

        glfwSetScrollCallback(window, (w, xOffset, yOffset) -> ImGui.UpdateScroll((float) xOffset, (float) yOffset));

        // Initialize renderer itself
        imGuiGl3.init();
    }

    private void loop() {
        double time = 0; // to track our frame delta value

        // Set the clear color
        glClearColor(0.5f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close the window
        while (!glfwWindowShouldClose(window)) {
            // Count frame delta value
            final double currentTime = glfwGetTime();
            final double deltaTime = (time > 0) ? (currentTime - time) : 1f / 60f;
            time = currentTime;

            // Get window size properties and mouse position
            glfwGetWindowSize(window, winWidth, winHeight);
            glfwGetFramebufferSize(window, fbWidth, fbHeight);
            glfwGetCursorPos(window, mousePosX, mousePosY);

            // IMPORTANT!!
            // We SHOULD call those methods to update ImGui state for current frame
            ImGui.UpdateImGui();
            ImGui.UpdateDisplaySize(winWidth[0], winHeight[0], fbWidth[0], fbHeight[0]);
            ImGui.UpdateMousePos((float) mousePosX[0], (float) mousePosY[0]);
            ImGui.UpdateDeltaTime((float) deltaTime);

            // Update mouse cursor
            final ImGuiMouseCursor imguiCursor = ImGui.GetMouseCursor();
            glfwSetCursor(window, mouseCursors[imguiCursor.getValue()]);
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

            // Render itself starts here
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // IMPORTANT!!
            // Any ImGui code SHOULD go between NewFrame()/Render() methods
            ImGui.NewFrame();

            ImGui.SetNextWindowSize(600, 200, ImGuiCond.Once);
            ImGui.SetNextWindowPos(10, 10, ImGuiCond.Once);

            ImGui.Begin("Custom window");
            ImGui.Text("Hello from Java!");
            if (ImGui.Button("Click")) {
                clickCount++;
            }
            if (ImGui.IsItemHovered()) {
                ImGui.SetMouseCursor(ImGuiMouseCursor.Hand);
            }
            ImGui.SameLine();
            ImGui.Text("Count: " + clickCount);
            ImGui.Separator();

            ImGui.BeginChild("##custom_window_child", 200, 50);
            if (ImGui.TreeNode("Simple..")) {
                ImGui.TreeNodeEx("..tree", ImGuiTreeNodeFlags.Leaf.or(ImGuiTreeNodeFlags.Bullet).or(ImGuiTreeNodeFlags.NoTreePushOnOpen));
                ImGui.TreePop();
            }
            ImGui.EndChild();

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
            ImGui.End();

            ImGui.ShowDemoWindow();
            ImGui.Render();

            imGuiGl3.render(ImGui.GetDrawData()); // render DrawData from ImGui into our OpenGL context

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();
        }
    }

    // If you want to clean a room after yourself - do it by yourself
    private void destroyImGui() {
        imGuiGl3.dispose();
        ImGui.destroy();
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
