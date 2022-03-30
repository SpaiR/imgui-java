package imgui.backends.glfw;

/**
 * Native binding to the Dear ImGui GLFW backend
 */
public final class ImGuiImplGlfw {
    private ImGuiImplGlfw() {

    }

    /*JNI
    #include "_common.h"
    #include "_glfw.h"
    */

    public static native boolean initForOpenGL(long window, boolean installCallbacks); /*
        return ImGui_ImplGlfw_InitForOpenGL((GLFWwindow*) window, installCallbacks);
    */

    public static native boolean initForVulkan(long window, boolean installCallbacks); /*
        return ImGui_ImplGlfw_InitForVulkan((GLFWwindow*) window, installCallbacks);
    */

    public static native boolean initForOther(long window, boolean installCallbacks); /*
        return ImGui_ImplGlfw_InitForOther((GLFWwindow*) window, installCallbacks);
    */

    public static native void newFrame(); /*
        ImGui_ImplGlfw_NewFrame();
    */

    public static native void shutdown(); /*
        ImGui_ImplGlfw_Shutdown();
    */

    // GLFW callbacks (installer)
    // - When calling Init with 'install_callbacks=true': GLFW callbacks will be installed for you. They will call user's previously installed callbacks, if any.
    // - When calling Init with 'install_callbacks=false': GLFW callbacks won't be installed. You will need to call those function yourself from your own GLFW callbacks.

    //FIXME: Added in current Dear Imgui Master, uncomment once Dear Imgui is updated
    //public static native void installCallbacks(long window); /*
    //    ImGui_ImplGlfw_InstallCallbacks((GLFWwindow*) window);
    //*/

    //FIXME: Added in current Dear Imgui Master, uncomment once Dear Imgui is updated
    //public static native void restoreCallbacks(long window); /*
    //    ImGui_ImplGlfw_RestoreCallbacks((GLFWwindow*) window);
    //*/

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_WindowFocusCallback(GLFWwindow* window, int focused);
    public static native void windowFocusCallback(long window, int focused); /*
        ImGui_ImplGlfw_WindowFocusCallback((GLFWwindow*) window, focused);
    */

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_CursorEnterCallback(GLFWwindow* window, int entered);
    public static native void cursorEnterCallback(long window, int entered); /*
        ImGui_ImplGlfw_CursorEnterCallback((GLFWwindow*) window, entered);
    */

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_MouseButtonCallback(GLFWwindow* window, int button, int action, int mods);
    public static native void mouseButtonCallback(long window, int button, int action, int mods); /*
        ImGui_ImplGlfw_MouseButtonCallback((GLFWwindow*) window, button, action, mods);
    */

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_ScrollCallback(GLFWwindow* window, double xoffset, double yoffset);
    public static native void scrollCallback(long window, double xoffset, double yoffset); /*
        ImGui_ImplGlfw_ScrollCallback((GLFWwindow*) window, xoffset, yoffset);
    */

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_KeyCallback(GLFWwindow* window, int key, int scancode, int action, int mods);
    public static native void keyCallback(long window, int key, int scancode, int action, int mods); /*
        ImGui_ImplGlfw_KeyCallback((GLFWwindow*) window, key, scancode, action, mods);
    */

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_CharCallback(GLFWwindow* window, unsigned int c);
    public static native void charCallback(long window, char c); /*
        ImGui_ImplGlfw_CharCallback((GLFWwindow*) window, (unsigned int) c);
    */

    //IMGUI_IMPL_API void     ImGui_ImplGlfw_MonitorCallback(GLFWmonitor* monitor, int event);
    public static native void monitorCallback(long monitor, int event); /*
        ImGui_ImplGlfw_MonitorCallback((GLFWmonitor*) monitor, event);
    */
}
