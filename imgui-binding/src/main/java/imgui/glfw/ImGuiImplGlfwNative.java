package imgui.glfw;

final class ImGuiImplGlfwNative {
    private ImGuiImplGlfwNative() {
    }

    /*JNI
        #if defined(_WIN32)
            #include <windows.h>
        #endif
     */

    // GLFW hack: Hide icon from task bar
    // Applied only for windows
    static native void win32hideFromTaskBar(long hwndPtr); /*
        #if defined(_WIN32)
            HWND hwnd = (HWND)hwndPtr;
            LONG ex_style = ::GetWindowLong(hwnd, GWL_EXSTYLE);
            ex_style &= ~WS_EX_APPWINDOW;
            ex_style |= WS_EX_TOOLWINDOW;
            ::SetWindowLong(hwnd, GWL_EXSTYLE, ex_style);
        #endif
    */
}
