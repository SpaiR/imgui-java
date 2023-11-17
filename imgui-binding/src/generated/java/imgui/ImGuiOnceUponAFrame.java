package imgui;

/**
 * Helper: Execute a block of code at maximum once a frame.
 * Convenient if you want to quickly create an UI within deep-nested code that runs multiple times every frame.
 * Usage: static ImGuiOnceUponAFrame oaf; if (oaf) ImGui::Text("This will be called only once per frame");
 * <p>
 * BINDING NOTICE:
 * Java example: if (ImGuiOnceUponAFrame.get()) ImGui.text("This will be called only once per frame");
 */
public final class ImGuiOnceUponAFrame {
    private ImGuiOnceUponAFrame() {
    }

    /*JNI
        #include "_common.h"
     */

    public static native boolean get(); /*
        ImGuiOnceUponAFrame oaf;
        return (bool)oaf;
    */
}
