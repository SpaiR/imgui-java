package imgui.app;

import imgui.ImGui;
import imgui.bgfx.ImGuiImplBGFX;
import imgui.flag.ImGuiConfigFlags;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.BufferUtils;
import org.lwjgl.bgfx.BGFXInit;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.bgfx.BGFX.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.Configuration.GLFW_LIBRARY_NAME;
import static org.lwjgl.system.MemoryStack.stackPush;

public abstract class BGFXWindow {
    private final ImGuiImplBGFX imGuiBGFX = new ImGuiImplBGFX();
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

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
        initWindow(config);
        initImGui(config);
        imGuiGlfw.init(handle, true);
        imGuiBGFX.init();
    }

    /**
     * Method to dispose all used application resources and destroy its window.
     */
    protected void dispose() {
        imGuiBGFX.dispose();
        imGuiGlfw.dispose();
        disposeImGui();
        disposeWindow();
    }

    /**
     * Method to create and initialize GLFW window.
     *
     * @param config configuration object with basic window information
     */
    protected void initWindow(final Configuration config) {
        if (Platform.get() == Platform.MACOSX) {
            GLFW_LIBRARY_NAME.set("glfw_async");
        }

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);

        handle = glfwCreateWindow(config.getWidth(), config.getHeight(), config.getTitle(), MemoryUtil.NULL, MemoryUtil.NULL);

        if (handle == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        try (MemoryStack stack = stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            GLFW.glfwGetWindowSize(handle, pWidth, pHeight);
            final GLFWVidMode vidmode = Objects.requireNonNull(GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()));
            GLFW.glfwSetWindowPos(handle, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        try (MemoryStack stack = stackPush()) {
            BGFXInit init = BGFXInit.malloc(stack);

            bgfx_init_ctor(init);
            init.resolution(it -> it
                .width(config.getWidth())
                .height(config.getHeight())
                .reset(BGFX_RESET_VSYNC));
            switch (Platform.get()) {
                case LINUX:
                    init.platformData()
                        .ndt(GLFWNativeX11.glfwGetX11Display())
                        .nwh(GLFWNativeX11.glfwGetX11Window(handle));
                    break;
                case MACOSX:
                    init.platformData()
                        .nwh(GLFWNativeCocoa.glfwGetCocoaWindow(handle));
                    break;
                case WINDOWS:
                    init.platformData()
                        .nwh(GLFWNativeWin32.glfwGetWin32Window(handle));
                    break;
            }

            if (!bgfx_init(init)) {
                throw new RuntimeException("Error initializing bgfx renderer");
            }
        }

        if (config.isFullScreen()) {
            GLFW.glfwMaximizeWindow(handle);
        } else {
            GLFW.glfwShowWindow(handle);
        }

        clearBuffer();
        renderBuffer();

        GLFW.glfwSetWindowSizeCallback(handle, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(final long window, final int width, final int height) {
                bgfx_reset(width, height, BGFX_RESET_NONE, BGFX_TEXTURE_FORMAT_COUNT);
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
     * Method used to clear the OpenGL buffer.
     */
    private void clearBuffer() {
        int color = Math.round(colorBg.getRed() * 255);
        color = (color << 8) + Math.round(colorBg.getGreen() * 255);
        color = (color << 8) + Math.round(colorBg.getBlue() * 255);
        color = (color << 8) + Math.round(colorBg.getAlpha() * 255);
        bgfx_set_view_clear(0, BGFX_CLEAR_COLOR | BGFX_CLEAR_DEPTH,  color, 1.0f, 0);
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(getHandle(), w, h);
        int width = w.get(0);
        int height = h.get(0);
        bgfx_set_view_rect(0, 0, 0, width, height);
    }

    /**
     * Method called at the beginning of the main cycle.
     * It clears OpenGL buffer and starts an ImGui frame.
     */
    protected void startFrame() {
        clearBuffer();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    /**
     * Method called in the end of the main cycle.
     * It renders ImGui and swaps GLFW buffers to show an updated frame.
     */
    protected void endFrame() {
        ImGui.render();
        imGuiBGFX.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }

        renderBuffer();
    }

    /**
     * Method to render the OpenGL buffer and poll window events.
     */
    private void renderBuffer() {
        bgfx_frame(false);
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
        bgfx_shutdown();
        Callbacks.glfwFreeCallbacks(handle);
        GLFW.glfwDestroyWindow(handle);
        GLFW.glfwTerminate();
        Objects.requireNonNull(GLFW.glfwSetErrorCallback(null)).free();
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
