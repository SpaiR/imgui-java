package imgui.app;

import imgui.ImGui;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.Objects;

/**
 * Low-level abstraction, which creates application window and starts the main loop.
 * It's recommended to use {@link Application}, but this class could be extended directly as well.
 * When extended, life-cycle methods should be called manually.
 */
public abstract class Window {

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private Backend backend;
    private Backend customBackend;

    /**
     * Pointer to the native GLFW window.
     */
    protected long handle;

    /**
     * Background color of the window.
     */
    protected final Color colorBg = new Color(.5f, .5f, .5f, 1);

    /**
     * Method to initialize application.
     *
     * @param config configuration object with basic window information
     */
    protected void init(final Configuration config) {
        if (config.getBackendType() == BackendType.OPENGL) {
            backend = new ImGuiGlBackend();
        } else if (config.getBackendType() == BackendType.VULKAN) {
            backend = new ImGuiVkBackend();
        } else if (config.getBackendType() == BackendType.CUSTOM) {
            if (customBackend != null) {
                backend = customBackend;
            } else {
                throw new RuntimeException("No custom rendering backend provided");
            }
        }

        initWindow(config);
        initImGui(config);
        imGuiGlfw.init(handle, true);
        backend.init(colorBg);
    }

    /**
     * Method to dispose all used application resources and destroy its window.
     */
    protected void dispose() {
        imGuiGlfw.dispose();
        backend.destroy();
        disposeImGui();
        disposeWindow();
    }

    /**
     * Method to create and initialize GLFW window.
     *
     * @param config configuration object with basic window information
     */
    protected void initWindow(final Configuration config) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

        backend.preCreateWindow();

        handle = GLFW.glfwCreateWindow(config.getWidth(), config.getHeight(), config.getTitle(), MemoryUtil.NULL, MemoryUtil.NULL);

        if (handle == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            GLFW.glfwGetWindowSize(handle, pWidth, pHeight);
            final GLFWVidMode vidmode = Objects.requireNonNull(GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()));
            GLFW.glfwSetWindowPos(handle, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        backend.postCreateWindow(handle);

        if (config.isFullScreen()) {
            GLFW.glfwMaximizeWindow(handle);
        } else {
            GLFW.glfwShowWindow(handle);
        }

        GLFW.glfwSetWindowSizeCallback(handle, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(final long window, final int width, final int height) {
                backend.resize(window, width, height);
                runFrame();
            }
        });
    }


    /**
     * Method to initialize Dear ImGui context. Could be overridden to do custom Dear ImGui setup before application start.
     *
     * @param config configuration object with basic window information
     */
    protected void initImGui(final Configuration config) {
        ImGui.createContext();
    }

    /**
     * Method called every frame, before calling {@link #process()} method.
     */
    protected void preProcess() {
    }

    /**
     * Method called every frame, after calling {@link #process()} method.
     */
    protected void postProcess() {
    }

    /**
     * Main application loop.
     */
    protected void run() {
        while (!GLFW.glfwWindowShouldClose(handle)) {
            runFrame();
        }
    }

    /**
     * Method used to run the next frame.
     */
    protected void runFrame() {
        startFrame();
        preProcess();
        process();
        postProcess();
        endFrame();
    }

    /**
     * Method to be overridden by user to provide main application logic.
     */
    public abstract void process();

    /**
     * Method called at the beginning of the main cycle.
     * Starts an ImGui frame, then hands off to the backend to begin the frame
     */
    protected void startFrame() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();
        backend.begin();
    }

    /**
     * Method called in the end of the main cycle.
     * It renders ImGui and swaps GLFW buffers to show an updated frame.
     */
    protected void endFrame() {
        ImGui.render();
        backend.end();

        GLFW.glfwPollEvents();
    }

    /**
     * Method to destroy Dear ImGui context.
     */
    protected void disposeImGui() {
        ImGui.destroyContext();
    }

    /**
     * Method to destroy GLFW window.
     */
    protected void disposeWindow() {
        Callbacks.glfwFreeCallbacks(handle);
        GLFW.glfwDestroyWindow(handle);
        GLFW.glfwTerminate();
        Objects.requireNonNull(GLFW.glfwSetErrorCallback(null)).free();
    }

    /**
     * Get the backend being used to render on the window
     *
     * @return The current backend
     */
    protected Backend getBackend() {
        return backend;
    }

    /**
     * Use a custom backend for rendering
     * Must be called before {@link #init(Configuration)}
     *
     * @param backend The custom backend to use
     */
    public void setBackend(Backend backend) {
        customBackend = backend;
    }

    /**
     * @return pointer to the native GLFW window
     */
    public final long getHandle() {
        return handle;
    }

    /**
     * @return {@link Color} instance, which represents background color for the window
     */
    public final Color getColorBg() {
        return colorBg;
    }
}
