package imgui.lwjgl3.glfw;

public final class ImGuiImplGlfwNative {
    private ImGuiImplGlfwNative() {
    }

    /*JNI
        #if defined(_WIN32)
            #include <windows.h>
        #endif
     */

    /**
     *  GLFW hack: Hide icon from task bar
     * Applied only for windows
     *
     * Internal Use Only.
     * FIXME: Once Java Module System has been implemented, this function should only be exported
     *  to the imgui.lwjgl module.
     */
    public static native void win32hideFromTaskBar(long hwndPtr); /*
        #if defined(_WIN32)
            HWND hwnd = (HWND)hwndPtr;
            LONG ex_style = ::GetWindowLong(hwnd, GWL_EXSTYLE);
            ex_style &= ~WS_EX_APPWINDOW;
            ex_style |= WS_EX_TOOLWINDOW;
            ::SetWindowLong(hwnd, GWL_EXSTYLE, ex_style);
        #endif
    */
}
