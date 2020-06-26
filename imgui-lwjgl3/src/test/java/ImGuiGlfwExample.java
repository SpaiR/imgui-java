import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiFreeType;
import imgui.ImGuiIO;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiMouseCursor;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings("MagicNumber")
public final class ImGuiGlfwExample {
    private long windowPtr; // pointer to the current GLFW window

    // For application window properties
    private final int[] winWidth = new int[1];
    private final int[] winHeight = new int[1];
    private final int[] fbWidth = new int[1];
    private final int[] fbHeight = new int[1];

    // For mouse tracking
    private final double[] mousePosX = new double[1];
    private final double[] mousePosY = new double[1];

    // Mouse cursors provided by GLFW
    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];

    // LWJGJ3 window backend
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    // LWJGL3 renderer (SHOULD be initialized)
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private String glslVersion = null; // We can initialize our renderer with different versions of the GLSL

    // User UI to render
    private final ExampleUi exampleUi = new ExampleUi();

    public void run() throws Exception {
        // IMPORTANT!!
        // This line is critical for Dear ImGui to work.
        ImGui.createContext();

        // Method initializes GLFW backend.
        // This method SHOULD be called after you've setup GLFW.
        // ImGui context should be created as well.
        setupGlfw();
        imGuiGlfw.init(windowPtr, true);

        // Method initializes LWJGL3 renderer.
        // This method SHOULD be called after you've initialized your ImGui configuration (fonts and so on).
        // ImGui context should be created as well.
        setupImGui();
        imGuiGl3.init(glslVersion);

        exampleUi.init();
        loop();

        // You should clean up after yourself in reverse order.
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
    }

    // Initialize GLFW + create an OpenGL context.
    // All code is mostly a copy-paste from the official LWJGL3 "Get Started": https://www.lwjgl.org/guide
    private void setupGlfw() {
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

        decideGlGlslVersions();

        windowPtr = glfwCreateWindow(1280, 768, "Dear ImGui+LWJGL Example", NULL, NULL);

        if (windowPtr == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowPtr, pWidth, pHeight);

            // Get the resolution of the primary monitor
            final GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));

            // Center the window
            glfwSetWindowPos(windowPtr, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(windowPtr); // Make the OpenGL context current
        glfwSwapInterval(GLFW_TRUE); // Enable v-sync
        glfwShowWindow(windowPtr); // Make the window visible

        // IMPORTANT!!
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private void decideGlGlslVersions() {
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        if (isMac) {
            glslVersion = "#version 150";
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);            // Required on Mac
        } else {
            glslVersion = "#version 130";
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        }
    }

    // Initialize Dear ImGui.
    private void setupImGui() {
        // ------------------------------------------------------------
        // Initialize ImGuiIO config
        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null); // We don't want to save .ini file
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard | ImGuiConfigFlags.DockingEnable); // Navigation with keyboard and enabled docking
        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
        io.setBackendPlatformName("imgui_java_impl_glfw");

        // ------------------------------------------------------------
        // Fonts configuration
        // Read: https://raw.githubusercontent.com/ocornut/imgui/master/docs/FONTS.txt

        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

        // Glyphs could be added per-font as well as per config used globally like here
        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesCyrillic());

        // Add a default font, which is 'ProggyClean.ttf, 13px'
        fontAtlas.addFontDefault();

        // Fonts merge example
        fontConfig.setMergeMode(true); // When enabled, all fonts added with this config would be merged with the previously added font
        fontConfig.setPixelSnapH(true);

        fontAtlas.addFontFromMemoryTTF(loadFromResources("basis33.ttf"), 16, fontConfig);

        fontConfig.setMergeMode(false);
        fontConfig.setPixelSnapH(false);

        // Fonts from file/memory example
        // We can add new fonts from the file system
        fontAtlas.addFontFromFileTTF("src/test/resources/Righteous-Regular.ttf", 14, fontConfig);
        fontAtlas.addFontFromFileTTF("src/test/resources/Righteous-Regular.ttf", 16, fontConfig);

        // Or directly from the memory
        fontConfig.setName("Roboto-Regular.ttf, 14px"); // This name will be displayed in Style Editor
        fontAtlas.addFontFromMemoryTTF(loadFromResources("Roboto-Regular.ttf"), 14, fontConfig);
        fontConfig.setName("Roboto-Regular.ttf, 16px"); // We can apply a new config value every time we add a new font
        fontAtlas.addFontFromMemoryTTF(loadFromResources("Roboto-Regular.ttf"), 16, fontConfig);

        fontConfig.destroy(); // After all fonts were added we don't need this config more

        // ------------------------------------------------------------
        // Use freetype instead of stb_truetype to build a fonts texture
        ImGuiFreeType.buildFontAtlas(fontAtlas, ImGuiFreeType.RasterizerFlags.LightHinting);
    }

    // Main application loop
    private void loop() {
        double time = 0; // to track our frame delta value

        // Run the rendering loop until the user has attempted to close the window
        while (!glfwWindowShouldClose(windowPtr)) {
            // Count frame delta value
            final double currentTime = glfwGetTime();
            final double deltaTime = (time > 0) ? (currentTime - time) : 1f / 60f;
            time = currentTime;

            startFrame((float) deltaTime);

            // Any Dear ImGui code SHOULD go between ImGui.newFrame()/ImGui.render() methods
            imGuiGlfw.newFrame();
            ImGui.newFrame();

            exampleUi.render();
            ImGui.render();

            endFrame();
        }
    }

    private void startFrame(final float deltaTime) {
        // Set the clear color and clear the window
        glClearColor(exampleUi.backgroundColor[0], exampleUi.backgroundColor[1], exampleUi.backgroundColor[2], 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        // Get window properties and mouse position
        glfwGetWindowSize(windowPtr, winWidth, winHeight);
        glfwGetFramebufferSize(windowPtr, fbWidth, fbHeight);
        glfwGetCursorPos(windowPtr, mousePosX, mousePosY);

        final float scaleX = (float) fbWidth[0] / winWidth[0];
        final float scaleY = (float) fbHeight[0] / winHeight[0];

        // We SHOULD call those methods to update Dear ImGui state for the current frame
        final ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(fbWidth[0], fbHeight[0]);
        io.setDisplayFramebufferScale(scaleX, scaleY);
        io.setMousePos((float) mousePosX[0] * scaleX, (float) mousePosY[0] * scaleY);
        io.setDeltaTime(deltaTime);

        // Update the mouse cursor
        final int imguiCursor = ImGui.getMouseCursor();
        glfwSetCursor(windowPtr, mouseCursors[imguiCursor]);
        glfwSetInputMode(windowPtr, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    private void endFrame() {
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        imGuiGl3.render(ImGui.getDrawData());

        glfwSwapBuffers(windowPtr);
        glfwPollEvents();
    }

    private byte[] loadFromResources(final String fileName) {
        try (InputStream is = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName));
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            final byte[] data = new byte[16384];

            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            return buffer.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(final String[] args) throws Exception {
        new ImGuiGlfwExample().run();
    }
}
