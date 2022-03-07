package imgui.app;

import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL32;

public class ImGuiGlBackend implements Backend {

    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private long windowHandle;
    private Color clearColor;
    private String glslVersion = null;

    public ImGuiGlBackend() {

    }

    @Override
    public void init(long windowHandle, Color clearColor) {
        this.windowHandle = windowHandle;
        this.clearColor = clearColor;
        decideGlGlslVersions();
        imGuiGl3.init(glslVersion);
        clearBuffer();
    }

    private void decideGlGlslVersions() {
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        if (isMac) {
            glslVersion = "#version 150";
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);          // Required on Mac
        } else {
            glslVersion = "#version 130";
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);
        }
    }

    @Override
    public void begin() {
        clearBuffer();
    }

    /**
     * Method used to clear the OpenGL buffer.
     */
    private void clearBuffer() {
        GL32.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
        GL32.glClear(GL32.GL_COLOR_BUFFER_BIT | GL32.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void end() {
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    @Override
    public void destroy() {
        imGuiGl3.dispose();
    }
}
