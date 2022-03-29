package imgui.backends.glfw;

public final class ImGuiImplGlfwNative {
    private ImGuiImplGlfwNative() {

    }

    /*JNI
    #include "_common.h"
    #include "_glfw.h"
    */

    public static native void newFrame(); /*
        ImGui_ImplGlfw_NewFrame();
    */

    public static native void shutdown(); /*
        ImGui_ImplGlfw_Shutdown();
    */
}
