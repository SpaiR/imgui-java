package imgui;

import imgui.assertion.ImAssertCallback;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiDragDropFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImLong;
import imgui.type.ImShort;
import imgui.type.ImString;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImGui {
    private static final String LIB_PATH_PROP = "imgui.library.path";
    private static final String LIB_NAME_PROP = "imgui.library.name";
    private static final String LIB_NAME_BASE = "imgui-java";
    private static final boolean is64Bit = System.getProperty("os.arch").contains("64") || System.getProperty("os.arch").startsWith("armv8");
    private static final boolean isARM = System.getProperty("os.arch").equals("arm") || System.getProperty("os.arch").startsWith("aarch64");

    private static final String LIB_TMP_DIR_PREFIX = "imgui-java-natives_" + System.currentTimeMillis();

    private static final ImGuiContext IMGUI_CONTEXT;
    private static final ImGuiIO IMGUI_IO;
    private static final ImDrawList WINDOW_DRAW_LIST;
    private static final ImDrawList BACKGROUND_DRAW_LIST;
    private static final ImDrawList FOREGROUND_DRAW_LIST;
    private static final ImGuiStorage IMGUI_STORAGE;
    private static final ImGuiViewport WINDOW_VIEWPORT;
    private static final ImGuiViewport FIND_VIEWPORT;

    private static final ImDrawData DRAW_DATA;
    private static final ImFont FONT;
    private static final ImGuiStyle STYLE;
    private static final ImGuiViewport MAIN_VIEWPORT;
    private static final ImGuiPlatformIO PLATFORM_IO;

    static {
        final String libPath = System.getProperty(LIB_PATH_PROP);
        String genLibName = LIB_NAME_BASE;

        if (isARM) {
            genLibName += "arm";
        }
        if (is64Bit) {
            genLibName += "64";
        }

        final String libName = System.getProperty(LIB_NAME_PROP, genLibName);
        final String fullLibName = resolveFullLibName();

        final String extractedLibAbsPath = tryLoadFromClasspath(fullLibName);

        if (libPath != null) {
            System.load(Paths.get(libPath).resolve(fullLibName).toFile().getAbsolutePath());
        } else if (extractedLibAbsPath != null) {
            System.load(extractedLibAbsPath);
        } else {
            System.loadLibrary(libName);
        }

        IMGUI_CONTEXT = new ImGuiContext(0);
        IMGUI_IO = new ImGuiIO(0);
        WINDOW_DRAW_LIST = new ImDrawList(0);
        BACKGROUND_DRAW_LIST = new ImDrawList(0);
        FOREGROUND_DRAW_LIST = new ImDrawList(0);
        IMGUI_STORAGE = new ImGuiStorage(0);
        WINDOW_VIEWPORT = new ImGuiViewport(0);
        FIND_VIEWPORT = new ImGuiViewport(0);

        DRAW_DATA = new ImDrawData(0);
        FONT = new ImFont(0);
        STYLE = new ImGuiStyle(0);
        MAIN_VIEWPORT = new ImGuiViewport(0);
        PLATFORM_IO = new ImGuiPlatformIO(0);

        nInitJni();
        ImFontAtlas.nInit();
        ImGuiPlatformIO.init();
        nInitInputTextData();
        setAssertCallback(new ImAssertCallback() {
            @Override
            public void imAssertCallback(String assertion, int line, String file) {
                System.err.println("Dear ImGui Assertion Failed: " + assertion);
                System.err.println("Assertion Located At: " + file + ":" + line);
                Thread.dumpStack();
            }
        });
    }

    private static String resolveFullLibName() {
        final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        final String libPrefix;
        final String libSuffix;

        if (isWin) {
            libPrefix = "";
            libSuffix = ".dll";
        } else if (isMac) {
            libPrefix = "lib";
            libSuffix = ".dylib";
        } else {
            libPrefix = "lib";
            libSuffix = ".so";
        }

        String genLibName = LIB_NAME_BASE;

        if (isARM) {
            genLibName += "arm";
        }
        if (is64Bit) {
            genLibName += "64";
        }


        return System.getProperty(LIB_NAME_PROP, libPrefix + genLibName + libSuffix);
    }

    // This method tries to unpack the library binary from classpath into the temp dir.
    private static String tryLoadFromClasspath(final String fullLibName) {
        try (InputStream is = ImGui.class.getClassLoader().getResourceAsStream("io/imgui/java/native-bin/" + fullLibName)) {
            if (is == null) {
                return null;
            }

            final Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve(LIB_TMP_DIR_PREFIX);
            tmpDir.toFile().mkdirs();

            final Path libBin = tmpDir.resolve(fullLibName);
            Files.copy(is, libBin, StandardCopyOption.REPLACE_EXISTING);
            libBin.toFile().deleteOnExit();

            return libBin.toFile().getAbsolutePath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * For internal usage.
     * Method is used to initiate static instantiation (loading of the native libraries etc.).
     * Otherwise native libraries will be loaded on demand and natively mapped objects won't work.
     */
    public static void init() {
    }

    /*JNI
        #include "_common.h"
     */

    private static native void nInitJni(); /*
        Jni::InitJvm(env);
        Jni::InitCommon(env);
        Jni::InitAssertion(env);
        Jni::InitCallbacks(env);
        Jni::InitBindingStruct(env);
    */

    // Context creation and access
    // Each context create its own ImFontAtlas by default.
    // You may instance one yourself and pass it to CreateContext() to share a font atlas between imgui contexts.
    // None of those functions is reliant on the current context.
    //
    // BINDING NOTICE: Getting of the current context is not implemented since it's a part of internal API which is not exposed here.

    public static ImGuiContext createContext() {
        IMGUI_CONTEXT.ptr = nCreateContext();
        return IMGUI_CONTEXT;
    }

    private static native long nCreateContext(); /*
        return (intptr_t)ImGui::CreateContext();
    */

    public static ImGuiContext createContext(ImFontAtlas sharedFontAtlas) {
        IMGUI_CONTEXT.ptr = nCreateContext(sharedFontAtlas.ptr);
        return IMGUI_CONTEXT;
    }

    private static native long nCreateContext(long sharedFontAtlasPtr); /*
        return (intptr_t)ImGui::CreateContext((ImFontAtlas*)sharedFontAtlasPtr);
    */

    public static native void destroyContext(); /*
        ImGui::DestroyContext();
    */

    public static void destroyContext(ImGuiContext ctx) {
        nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext(long ptr); /*
        ImGui::DestroyContext((ImGuiContext*) ptr);
    */

    public static ImGuiContext getCurrentContext() {
        IMGUI_CONTEXT.ptr = nGetCurrentContext();
        return IMGUI_CONTEXT;
    }

    private static native long nGetCurrentContext(); /*
        return (intptr_t)ImGui::GetCurrentContext();
    */

    public static void setCurrentContext(ImGuiContext ctx) {
        nSetCurrentContext(ctx.ptr);
    }

    private static native void nSetCurrentContext(long ptr); /*
        ImGui::SetCurrentContext((ImGuiContext*) ptr);
    */

    /**
     * Set a custom assertion callback for ImGui assertions.
     * Take note: Any custom assertion callback SHOULD NOT throw any exception.
     * After any callback the application will be terminated, any attempt to bypass this behavior
     * will result in a EXCEPTION_ACCESS_VIOLATION from within the native layer.
     * @param callback The custom ImGui assertion callback
     */
    public static native void setAssertCallback(ImAssertCallback callback); /*
        Jni::SetAssertionCallback(callback);
    */

    // Main

    /**
     * Access the IO structure (mouse/keyboard/gamepad inputs, time, various configuration options/flags).
     */
    public static ImGuiIO getIO() {
        IMGUI_IO.ptr = nGetIO();
        return IMGUI_IO;
    }

    private static native long nGetIO(); /*
        return (intptr_t)&ImGui::GetIO();
    */

    /**
     * Access the Style structure (colors, sizes). Always use PushStyleCol(), PushStyleVar() to modify style mid-frame!
     */
    public static ImGuiStyle getStyle() {
        STYLE.ptr = nGetStyle();
        return STYLE;
    }

    private static native long nGetStyle(); /*
        return (intptr_t)&ImGui::GetStyle();
    */

    /**
     * Start a new Dear ImGui frame, you can submit any command from this point until Render()/EndFrame().
     */
    public static native void newFrame(); /*
        ImGui::NewFrame();
    */

    /**
     * Ends the Dear ImGui frame. automatically called by Render(). If you don't need to render data (skipping rendering) you may call EndFrame() without
     * Render()... but you'll have wasted CPU already! If you don't need to render, better to not create any windows and not call NewFrame() at all!
     */
    public static native void endFrame(); /*
        ImGui::EndFrame();
    */

    /**
     * Ends the Dear ImGui frame, finalize the draw data. You can then get call GetDrawData().
     */
    public static native void render(); /*
        ImGui::Render();
    */

    /**
     * Valid after Render() and until the next call to NewFrame(). this is what you have to render.
     */
    public static ImDrawData getDrawData() {
        DRAW_DATA.ptr = nGetDrawData();
        return DRAW_DATA;
    }

    private static native long nGetDrawData(); /*
        return (intptr_t)ImGui::GetDrawData();
    */

    // Demo, Debug, Information

    /**
     * Create Demo window. Demonstrate most ImGui features. Call this to learn about the library!
     */
    public static native void showDemoWindow(); /*
        ImGui::ShowDemoWindow();
    */

    public static void showDemoWindow(ImBoolean pOpen) {
        nShowDemoWindow(pOpen.getData());
    }

    private static native void nShowDemoWindow(boolean[] pOpen); /*
        ImGui::ShowDemoWindow(&pOpen[0]);
    */

    /**
     * Create Metrics/Debugger window. display Dear ImGui internals: windows, draw commands, various internal state, etc.
     */
    public static void showMetricsWindow(ImBoolean pOpen) {
        nShowMetricsWindow(pOpen.getData());
    }

    /**
     * Create Metrics/Debugger window. display Dear ImGui internals: windows, draw commands, various internal state, etc.
     */
    public static native void showMetricsWindow(); /*
        ImGui::ShowMetricsWindow();
    */

    private static native void nShowMetricsWindow(boolean[] pOpen); /*
        ImGui::ShowMetricsWindow(&pOpen[0]);
    */

    /**
     * Create Stack Tool window. hover items with mouse to query information about the source of their unique ID.
     */
    public static void showStackToolWindow(ImBoolean pOpen) {
        nShowMetricsWindow(pOpen.getData());
    }

    /**
     * Create Stack Tool window. hover items with mouse to query information about the source of their unique ID.
     */
    public static native void showStackToolWindow(); /*
        ImGui::ShowStackToolWindow();
    */

    private static native void nShowStackToolWindow(boolean[] pOpen); /*
        ImGui::ShowStackToolWindow(&pOpen[0]);
    */

    /**
     * Create About window. display Dear ImGui version, credits and build/system information.
     */
    public static native void showAboutWindow(); /*
        ImGui::ShowAboutWindow();
    */

    public static void showAboutWindow(ImBoolean pOpen) {
        nShowAboutWindow(pOpen.getData());
    }

    private static native void nShowAboutWindow(boolean[] pOpen); /*
        ImGui::ShowAboutWindow(&pOpen[0]);
    */

    /**
     * Add style editor block (not a window).
     * You can pass in a reference ImGuiStyle structure to compare to, revert to and save to (else it uses the default style)
     */
    public static native void showStyleEditor(); /*
        ImGui::ShowStyleEditor();
    */

    public static void showStyleEditor(ImGuiStyle ref) {
        nShowStyleEditor(ref.ptr);
    }

    private static native void nShowStyleEditor(long ref); /*
        ImGui::ShowStyleEditor((ImGuiStyle*)ref);
    */

    /**
     * Add style selector block (not a window), essentially a combo listing the default styles.
     */
    public static native boolean showStyleSelector(String label); /*
        return ImGui::ShowStyleSelector(label);
    */

    /**
     * Add font selector block (not a window), essentially a combo listing the loaded fonts.
     */
    public static native void showFontSelector(String label); /*
        ImGui::ShowFontSelector(label);
    */

    /**
     * Add basic help/info block (not a window): how to manipulate ImGui as a end-user (mouse/keyboard controls).
     */
    public static native void showUserGuide(); /*
        ImGui::ShowUserGuide();
    */

    /**
     * Get the compiled version string e.g. "1.80 WIP" (essentially the value for IMGUI_VERSION from the compiled version of imgui.cpp)
     */
    public static native String getVersion(); /*
        return env->NewStringUTF(ImGui::GetVersion());
    */

    // Styles

    /**
     * New, recommended style (default)
     */
    public static native void styleColorsDark(); /*
        ImGui::StyleColorsDark();
    */

    public static void styleColorsDark(ImGuiStyle style) {
        nStyleColorsDark(style.ptr);
    }

    private static native void nStyleColorsDark(long ptr); /*
        ImGui::StyleColorsDark((ImGuiStyle*)ptr);
    */

    /**
     * Best used with borders and a custom, thicker font
     */
    public static native void styleColorsLight(); /*
        ImGui::StyleColorsLight();
    */

    public static void styleColorsLight(ImGuiStyle style) {
        nStyleColorsLight(style.ptr);
    }

    private static native void nStyleColorsLight(long ptr); /*
        ImGui::StyleColorsLight((ImGuiStyle*)ptr);
    */

    /**
     * Classic imgui style
     */
    public static native void styleColorsClassic(); /*
        ImGui::StyleColorsClassic();
    */

    public static void styleColorsClassic(ImGuiStyle style) {
        nStyleColorsClassic(style.ptr);
    }

    private static native void nStyleColorsClassic(long ptr); /*
        ImGui::StyleColorsClassic((ImGuiStyle*)ptr);
    */

    // Windows
    // - Begin() = push window to the stack and start appending to it. End() = pop window from the stack.
    // - You may append multiple times to the same window during the same frame.
    // - Passing 'bool* pOpen != NULL' shows a window-closing widget in the upper-right corner of the window,
    //   which clicking will set the boolean to false when clicked.
    // - You may append multiple times to the same window during the same frame by calling Begin()/End() pairs multiple times.
    //   Some information such as 'flags' or 'p_open' will only be considered by the first call to Begin().
    // - Begin() return false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting
    //   anything to the window. Always call a matching End() for each Begin() call, regardless of its return value!
    //   [Important: due to legacy reason, this is inconsistent with most other functions such as BeginMenu/EndMenu,
    //    BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding BeginXXX function
    //    returned true. Begin and BeginChild are the only odd ones out. Will be fixed in a future update.]
    // - Note that the bottom of window stack always contains a window called "Debug".

    public static native boolean begin(String title); /*
        return ImGui::Begin(title);
    */

    public static boolean begin(String title, ImBoolean pOpen) {
        return nBegin(title, pOpen.getData(), 0);
    }

    public static boolean begin(String title, int imGuiWindowFlags) {
        return nBegin(title, imGuiWindowFlags);
    }

    public static boolean begin(String title, ImBoolean pOpen, int imGuiWindowFlags) {
        return nBegin(title, pOpen.getData(), imGuiWindowFlags);
    }

    private static native boolean nBegin(String title, int imGuiWindowFlags); /*
        return ImGui::Begin(title, NULL, imGuiWindowFlags);
    */

    private static native boolean nBegin(String title, boolean[] pOpen, int imGuiWindowFlags); /*
        return ImGui::Begin(title, &pOpen[0], imGuiWindowFlags);
    */

    public static native void end(); /*
        ImGui::End();
    */

    // Child Windows
    // - Use child windows to begin into a self-contained independent scrolling/clipping regions within a host window. Child windows can embed their own child.
    // - For each independent axis of 'size': ==0.0f: use remaining host window size / >0.0f: fixed size / <0.0f: use remaining window size minus abs(size) / Each axis can use a different mode, e.g. ImVec2(0,400).
    // - BeginChild() returns false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting anything to the window.
    //   Always call a matching EndChild() for each BeginChild() call, regardless of its return value.
    //   [Important: due to legacy reason, this is inconsistent with most other functions such as BeginMenu/EndMenu,
    //    BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding BeginXXX function
    //    returned true. Begin and BeginChild are the only odd ones out. Will be fixed in a future update.]

    public static native boolean beginChild(String strId); /*
        return ImGui::BeginChild(strId);
    */

    public static native boolean beginChild(String strId, float width, float height); /*
        return ImGui::BeginChild(strId, ImVec2(width, height));
    */

    public static native boolean beginChild(String strId, float width, float height, boolean border); /*
        return ImGui::BeginChild(strId, ImVec2(width, height), border);
    */

    public static native boolean beginChild(String strId, float width, float height, boolean border, int imGuiWindowFlags); /*
        return ImGui::BeginChild(strId, ImVec2(width, height), border, imGuiWindowFlags);
    */

    public static native boolean beginChild(int imGuiID); /*
        return ImGui::BeginChild(imGuiID);
    */

    public static native boolean beginChild(int imGuiID, float width, float height, boolean border); /*
        return ImGui::BeginChild(imGuiID, ImVec2(width, height), border);
    */

    public static native boolean beginChild(int imGuiID, float width, float height, boolean border, int imGuiWindowFlags); /*
        return ImGui::BeginChild(imGuiID, ImVec2(width, height), border, imGuiWindowFlags);
    */

    public static native void endChild(); /*
        ImGui::EndChild();
    */

    // Windows Utilities
    // - "current window" = the window we are appending into while inside a Begin()/End() block. "next window" = next window we will Begin() into.

    public static native boolean isWindowAppearing(); /*
        return ImGui::IsWindowAppearing();
    */

    public static native boolean isWindowCollapsed(); /*
        return ImGui::IsWindowCollapsed();
    */

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    public static native boolean isWindowFocused(); /*
        return ImGui::IsWindowFocused();
    */

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    public static native boolean isWindowFocused(int imGuiFocusedFlags); /*
        return ImGui::IsWindowFocused(imGuiFocusedFlags);
    */

    /**
     * Is current window hovered (and typically: not blocked by a popup/modal)? see flags for options.
     * NB: If you are trying to check whether your mouse should be dispatched to imgui or to your app,
     * you should use the 'io.WantCaptureMouse' boolean for that! Please read the FAQ!
     */
    public static native boolean isWindowHovered(); /*
        return ImGui::IsWindowHovered();
    */

    /**
     * Is current window hovered (and typically: not blocked by a popup/modal)? see flags for options.
     * NB: If you are trying to check whether your mouse should be dispatched to imgui or to your app,
     * you should use the 'io.WantCaptureMouse' boolean for that! Please read the FAQ!
     */
    public static native boolean isWindowHovered(int imGuiHoveredFlags); /*
        return ImGui::IsWindowHovered(imGuiHoveredFlags);
    */

    /**
     * Get draw list associated to the current window, to append your own drawing primitives
     */
    public static ImDrawList getWindowDrawList() {
        WINDOW_DRAW_LIST.ptr = nGetWindowDrawList();
        return WINDOW_DRAW_LIST;
    }

    private static native long nGetWindowDrawList(); /*
        return (intptr_t)ImGui::GetWindowDrawList();
    */

    /**
     * Get DPI scale currently associated to the current window's viewport.
     */
    public static native float getWindowDpiScale(); /*
        return ImGui::GetWindowDpiScale();
    */

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static ImVec2 getWindowPos() {
        final ImVec2 value = new ImVec2();
        getWindowPos(value);
        return value;
    }

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static native void getWindowPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowPos(), dstImVec2);
    */

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static native float getWindowPosX(); /*
        return ImGui::GetWindowPos().x;
    */

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static native float getWindowPosY(); /*
        return ImGui::GetWindowPos().y;
    */

    /**
     * Get current window size
     */
    public static ImVec2 getWindowSize() {
        final ImVec2 value = new ImVec2();
        getWindowSize(value);
        return value;
    }

    /**
     * Get current window size
     */
    public static native void getWindowSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowSize(), dstImVec2);
    */

    /**
     * Get current window size
     */
    public static native float getWindowSizeX(); /*
        return ImGui::GetWindowSize().x;
    */

    /**
     * Get current window size
     */
    public static native float getWindowSizeY(); /*
        return ImGui::GetWindowSize().y;
    */

    /**
     * Get current window width (shortcut for GetWindowSize().x)
     */
    public static native float getWindowWidth(); /*
        return ImGui::GetWindowWidth();
    */

    /**
     * Get current window height (shortcut for GetWindowSize().y)
     */
    public static native float getWindowHeight(); /*
        return ImGui::GetWindowHeight();
     */

    /**
     * Get viewport currently associated to the current window.
     */
    public static ImGuiViewport getWindowViewport() {
        WINDOW_VIEWPORT.ptr = nGetWindowViewport();
        return WINDOW_VIEWPORT;
    }

    private static native long nGetWindowViewport(); /*
        return (intptr_t)ImGui::GetWindowViewport();
    */

    // Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static native void setNextWindowPos(float x, float y); /*
        ImGui::SetNextWindowPos(ImVec2(x, y));
     */

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static native void setNextWindowPos(float x, float y, int imGuiCond); /*
        ImGui::SetNextWindowPos(ImVec2(x, y), imGuiCond);
     */

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static native void setNextWindowPos(float x, float y, int imGuiCond, float pivotX, float pivotY); /*
        ImGui::SetNextWindowPos(ImVec2(x, y), imGuiCond, ImVec2(pivotX, pivotY));
     */

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static native void setNextWindowSize(float width, float height); /*
        ImGui::SetNextWindowSize(ImVec2(width, height));
    */

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static native void setNextWindowSize(float width, float height, int imGuiCond); /*
        ImGui::SetNextWindowSize(ImVec2(width, height), imGuiCond);
    */

    /**
     * Set next window size limits. use -1,-1 on either X/Y axis to preserve the current size. Sizes will be rounded down.
     */
    public static native void setNextWindowSizeConstraints(float minWidth, float minHeight, float maxWidth, float maxHeight); /*
        ImGui::SetNextWindowSizeConstraints(ImVec2(minWidth, minHeight), ImVec2(maxWidth, maxHeight));
    */

    /**
     * Set next window content size (~ scrollable client area, which enforce the range of scrollbars).
     * Not including window decorations (title bar, menu bar, etc.) nor WindowPadding. set an axis to 0.0f to leave it automatic. call before Begin()
     */
    public static native void setNextWindowContentSize(float width, float height); /*
        ImGui::SetNextWindowContentSize(ImVec2(width, height));
    */

    /**
     * Set next window collapsed state. call before Begin()
     */
    public static native void setNextWindowCollapsed(boolean collapsed); /*
        ImGui::SetNextWindowCollapsed(collapsed);
    */

    /**
     * Set next window collapsed state. call before Begin()
     */
    public static native void setNextWindowCollapsed(boolean collapsed, int imGuiCond); /*
        ImGui::SetNextWindowCollapsed(collapsed, imGuiCond);
    */

    /**
     * Set next window to be focused / top-most. call before Begin()
     */
    public static native void setNextWindowFocus(); /*
        ImGui::SetNextWindowFocus();
    */

    /**
     * Set next window background color alpha. helper to easily override the Alpha component of ImGuiCol_WindowBg/ChildBg/PopupBg.
     * You may also use ImGuiWindowFlags_NoBackground.
     */
    public static native void setNextWindowBgAlpha(float alpha); /*
        ImGui::SetNextWindowBgAlpha(alpha);
    */

    /**
     * Set next window viewport.
     */
    public static native void setNextWindowViewport(int viewportId); /*
        ImGui::SetNextWindowViewport(viewportId);
    */

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static native void setWindowPos(float x, float y); /*
        ImGui::SetWindowPos(ImVec2(x, y));
    */

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static native void setWindowPos(float x, float y, int imGuiCond); /*
        ImGui::SetWindowPos(ImVec2(x, y), imGuiCond);
    */

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static native void setWindowSize(float width, float height); /*
        ImGui::SetWindowSize(ImVec2(width, height));
    */

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static native void setWindowSize(float width, float height, int imGuiCond); /*
        ImGui::SetWindowSize(ImVec2(width, height), imGuiCond);
    */

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    public static native void setWindowCollapsed(boolean collapsed); /*
        ImGui::SetWindowCollapsed(collapsed);
    */

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    public static native void setWindowCollapsed(boolean collapsed, int imGuiCond); /*
        ImGui::SetWindowCollapsed(collapsed, imGuiCond);
    */

    /**
     * (not recommended) set current window to be focused / top-most. prefer using SetNextWindowFocus().
     */
    public static native void setWindowFocus(); /*
        ImGui::SetWindowFocus();
    */

    /**
     * Set font scale. Adjust IO.FontGlobalScale if you want to scale all windows.
     * This is an old API! For correct scaling, prefer to reload font + rebuild ImFontAtlas + call style.ScaleAllSizes().
     */
    public native void setWindowFontScale(float scale); /*
        ImGui::SetWindowFontScale(scale);
    */

    /**
     * Set named window position.
     */
    public static native void setWindowPos(String name, float x, float y); /*
        ImGui::SetWindowPos(name, ImVec2(x, y));
    */

    /**
     * Set named window position.
     */
    public static native void setWindowPos(String name, float x, float y, int imGuiCond); /*
        ImGui::SetWindowPos(name, ImVec2(x, y), imGuiCond);
    */

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static native void setWindowSize(String name, float x, float y); /*
        ImGui::SetWindowSize(name, ImVec2(x, y));
    */

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static native void setWindowSize(String name, float x, float y, int imGuiCond); /*
        ImGui::SetWindowSize(name, ImVec2(x, y), imGuiCond);
    */

    /**
     * Set named window collapsed state
     */
    public static native void setWindowCollapsed(String name, boolean collapsed); /*
        ImGui::SetWindowCollapsed(name, collapsed, 0);
    */

    /**
     * Set named window collapsed state
     */
    public static native void setWindowCollapsed(String name, boolean collapsed, int imGuiCond); /*
        ImGui::SetWindowCollapsed(name, collapsed, imGuiCond);
    */

    /**
     * Set named window to be focused / top-most. Use NULL to remove focus.
     */
    public static native void setWindowFocus(String name); /*MANUAL
        if (obj_name == NULL)
            ImGui::SetWindowFocus(NULL);
        else {
            char* name = (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
            ImGui::SetWindowFocus(name);
            env->ReleaseStringUTFChars(obj_name, name);
        }
    */

    // Content region
    // - Retrieve available space from a given point. GetContentRegionAvail() is frequently useful.
    // - Those functions are bound to be redesigned (they are confusing, incomplete and the Min/Max return values are in local window coordinates which increases confusion)

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static ImVec2 getContentRegionAvail() {
        final ImVec2 value = new ImVec2();
        getContentRegionAvail(value);
        return value;
    }

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static native void getContentRegionAvail(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionAvail(), dstImVec2);
    */

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static native float getContentRegionAvailX(); /*
        return ImGui::GetContentRegionAvail().x;
    */

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static native float getContentRegionAvailY(); /*
        return ImGui::GetContentRegionAvail().y;
    */

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static ImVec2 getContentRegionMax() {
        final ImVec2 value = new ImVec2();
        getContentRegionMax(value);
        return value;
    }

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static native void getContentRegionMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionMax(), dstImVec2);
    */

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static native float getContentRegionMaxX(); /*
        return ImGui::GetContentRegionMax().x;
    */

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static native float getContentRegionMaxY(); /*
        return ImGui::GetContentRegionMax().y;
    */

    /**
     * Content boundaries max (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static ImVec2 getWindowContentRegionMin() {
        final ImVec2 value = new ImVec2();
        getWindowContentRegionMin(value);
        return value;
    }

    /**
     * Content boundaries max (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static native void getWindowContentRegionMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowContentRegionMin(), dstImVec2);
    */

    /**
     * Content boundaries max (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static native float getWindowContentRegionMinX(); /*
        return ImGui::GetWindowContentRegionMin().x;
    */

    /**
     * Content boundaries max (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static native float getWindowContentRegionMinY(); /*
        return ImGui::GetWindowContentRegionMin().y;
    */

    public static ImVec2 getWindowContentRegionMax() {
        final ImVec2 value = new ImVec2();
        getWindowContentRegionMax(value);
        return value;
    }

    public static native void getWindowContentRegionMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowContentRegionMax(), dstImVec2);
    */

    public static native float getWindowContentRegionMaxX(); /*
        return ImGui::GetWindowContentRegionMax().x;
    */

    public static native float getWindowContentRegionMaxY(); /*
        return ImGui::GetWindowContentRegionMax().y;
    */

    // Windows Scrolling

    /**
     * Get scrolling amount [0 .. GetScrollMaxX()]
     */
    public static native float getScrollX(); /*
        return ImGui::GetScrollX();
    */

    /**
     * Get scrolling amount [0 .. GetScrollMaxY()]
     */
    public static native float getScrollY(); /*
        return ImGui::GetScrollY();
    */

    /**
     * Set scrolling amount [0 .. GetScrollMaxX()]
     */
    public static native void setScrollX(float scrollX); /*
        ImGui::SetScrollX(scrollX);
    */

    /**
     * Set scrolling amount [0..GetScrollMaxY()]
     */
    public static native void setScrollY(float scrollY); /*
        ImGui::SetScrollY(scrollY);
    */

    /**
     * Get maximum scrolling amount ~~ ContentSize.x - WindowSize.x - DecorationsSize.x
     */
    public static native float getScrollMaxX(); /*
        return ImGui::GetScrollMaxX();
    */

    /**
     * Get maximum scrolling amount ~~ ContentSize.y - WindowSize.y - DecorationsSize.y
     */
    public static native float getScrollMaxY(); /*
        return ImGui::GetScrollMaxY();
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void setScrollHereX(); /*
        ImGui::SetScrollHereX();
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void setScrollHereX(float centerXRatio); /*
        ImGui::SetScrollHereX(centerXRatio);
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void setScrollHereY(); /*
        ImGui::SetScrollHereY();
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void setScrollHereY(float centerYRatio); /*
        ImGui::SetScrollHereY(centerYRatio);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void setScrollFromPosX(float localX); /*
        ImGui::SetScrollFromPosX(localX);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void setScrollFromPosX(float localX, float centerXRatio); /*
        ImGui::SetScrollFromPosX(localX, centerXRatio);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void setScrollFromPosY(float localY); /*
        ImGui::SetScrollFromPosY(localY);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void setScrollFromPosY(float localY, float centerYRatio); /*
        ImGui::SetScrollFromPosY(localY, centerYRatio);
    */

    // Parameters stacks (shared)

    public static void pushFont(ImFont font) {
        nPushFont(font.ptr);
    }

    private static native void nPushFont(long fontPtr); /*
        ImGui::PushFont((ImFont*)fontPtr);
    */

    public static native void popFont(); /*
        ImGui::PopFont();
    */

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    public static native void pushStyleColor(int imGuiCol, float r, float g, float b, float a); /*
        ImGui::PushStyleColor(imGuiCol, (ImU32)ImColor((float)r, (float)g, (float)b, (float)a));
    */

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    public static native void pushStyleColor(int imGuiCol, int r, int g, int b, int a); /*
        ImGui::PushStyleColor(imGuiCol, (ImU32)ImColor((int)r, (int)g, (int)b, (int)a));
    */

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    public static native void pushStyleColor(int imGuiCol, int col); /*
        ImGui::PushStyleColor(imGuiCol, col);
    */

    public static native void popStyleColor(); /*
        ImGui::PopStyleColor();
    */

    public static native void popStyleColor(int count); /*
        ImGui::PopStyleColor(count);
    */

    /**
     * Modify a style float variable. always use this if you modify the style after NewFrame().
     */
    public static native void pushStyleVar(int imGuiStyleVar, float val); /*
        ImGui::PushStyleVar(imGuiStyleVar, val);
    */

    /**
     * Modify a style ImVec2 variable. always use this if you modify the style after NewFrame().
     */
    public static native void pushStyleVar(int imGuiStyleVar, float valX, float valY); /*
        ImGui::PushStyleVar(imGuiStyleVar, ImVec2(valX, valY));
    */

    public static native void popStyleVar(); /*
        ImGui::PopStyleVar();
    */

    public static native void popStyleVar(int count); /*
        ImGui::PopStyleVar(count);
    */

    /**
     * Allow focusing using TAB/Shift-TAB, enabled by default but you can disable it for certain widgets
     */
    public static native void pushAllowKeyboardFocus(boolean allowKeyboardFocus); /*
        ImGui::PushAllowKeyboardFocus(allowKeyboardFocus);
    */

    public static native void popAllowKeyboardFocus(); /*
        ImGui::PopAllowKeyboardFocus();
    */

    /**
     * In 'repeat' mode, Button*() functions return repeated true in a typematic manner (using io.KeyRepeatDelay/io.KeyRepeatRate setting).
     * Note that you can call IsItemActive() after any Button() to tell if the button is held in the current frame.
     */
    public static native void pushButtonRepeat(boolean repeat); /*
        ImGui::PushButtonRepeat(repeat);
    */

    public static native void popButtonRepeat(); /*
        ImGui::PopButtonRepeat();
    */

    // Parameters stacks (current window)

    /**
     * Push width of items for common large "item+label" widgets. {@code > 0.0f}: width in pixels,
     * {@code <0.0f} align xx pixels to the right of window (so -1.0f always align width to the right side).
     */
    public static native void pushItemWidth(float itemWidth); /*
        ImGui::PushItemWidth(itemWidth);
    */

    public static native void popItemWidth(); /*
        ImGui::PopItemWidth();
    */

    /**
     * Set width of the _next_ common large "item+label" widget. {@code > 0.0f}: width in pixels,
     * {@code <0.0f} align xx pixels to the right of window (so -1.0f always align width to the right side)
     */
    public static native void setNextItemWidth(float itemWidth); /*
        ImGui::SetNextItemWidth(itemWidth);
    */

    /**
     * Width of item given pushed settings and current cursor position. NOT necessarily the width of last item unlike most 'Item' functions.
     */
    public static native float calcItemWidth(); /*
        return ImGui::CalcItemWidth();
    */

    /**
     * Push Word-wrapping positions for Text*() commands. {@code < 0.0f}: no wrapping; 0.0f: wrap to end of window (or column); {@code > 0.0f}: wrap at
     * 'wrap_posX' position in window local space
     */
    public static native void pushTextWrapPos(); /*
        ImGui::PushTextWrapPos();
    */

    /**
     * Push Word-wrapping positions for Text*() commands. {@code < 0.0f}: no wrapping; 0.0f: wrap to end of window (or column); {@code > 0.0f}: wrap at
     * 'wrap_posX' position in window local space
     */
    public static native void pushTextWrapPos(float wrapLocalPosX); /*
        ImGui::PushTextWrapPos(wrapLocalPosX);
    */

    public static native void popTextWrapPos(); /*
        ImGui::PopTextWrapPos();
    */

    // Style read access

    /**
     * Get current font.
     */
    public static ImFont getFont() {
        FONT.ptr = nGetFont();
        return FONT;
    }

    private static native long nGetFont(); /*
        return (intptr_t)ImGui::GetFont();
    */

    /**
     * Get current font size (= height in pixels) of current font with current scale applied
     */
    public static native int getFontSize(); /*
        return ImGui::GetFontSize();
    */

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static ImVec2 getFontTexUvWhitePixel() {
        final ImVec2 value = new ImVec2();
        getFontTexUvWhitePixel(value);
        return value;
    }

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static native void getFontTexUvWhitePixel(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetFontTexUvWhitePixel(), dstImVec2);
    */

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static native float getFontTexUvWhitePixelX(); /*
        return ImGui::GetFontTexUvWhitePixel().x;
    */

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static native float getFontTexUvWhitePixelY(); /*
        return ImGui::GetFontTexUvWhitePixel().y;
    */

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier
     */
    public static native int getColorU32(int imGuiCol); /*
        return ImGui::GetColorU32((ImGuiCol)imGuiCol);
    */

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier
     */
    public static native int getColorU32(int imGuiCol, float alphaMul); /*
        return ImGui::GetColorU32(imGuiCol, alphaMul);
    */

    /**
     * Retrieve given color with style alpha applied
     */
    public static native int getColorU32(float r, float g, float b, float a); /*
        return ImGui::GetColorU32(ImVec4(r, g, b, a));
    */

    /**
     * Retrieve given color with style alpha applied
     * <p>
     * BINDING NOTICE: Since {@link #getColorU32(int)} has the same signature, this specific method has an 'i' suffix.
     */
    public static native int getColorU32i(int col); /*
        return ImGui::GetColorU32((ImU32)col);
    */

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public ImVec4 getStyleColorVec4(final int imGuiStyleVar) {
        final ImVec4 value = new ImVec4();
        getStyleColorVec4(imGuiStyleVar, value);
        return value;
    }

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static native void getStyleColorVec4(int imGuiStyleVar, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, ImGui::GetStyleColorVec4(imGuiStyleVar), dstImVec4);
    */

    // Cursor / Layout
    // - By "cursor" we mean the current output position.
    // - The typical widget behavior is to output themselves at the current cursor position, then move the cursor one line down.
    // - You can call SameLine() between widgets to undo the last carriage return and output at the right of the preceding widget.
    // - Attention! We currently have inconsistencies between window-local and absolute positions we will aim to fix with future API:
    //    Window-local coordinates:   SameLine(), GetCursorPos(), SetCursorPos(), GetCursorStartPos(), GetContentRegionMax(), GetWindowContentRegion*(), PushTextWrapPos()
    //    Absolute coordinate:        GetCursorScreenPos(), SetCursorScreenPos(), all ImDrawList:: functions.

    /**
     * Separator, generally horizontal. inside a menu bar or in horizontal layout mode, this becomes a vertical separator.
     */
    public static native void separator(); /*
        ImGui::Separator();
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static native void sameLine(); /*
        ImGui::SameLine();
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static native void sameLine(float offsetFromStartX); /*
        ImGui::SameLine(offsetFromStartX);
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static native void sameLine(float offsetFromStartX, float spacing); /*
        ImGui::SameLine(offsetFromStartX, spacing);
    */

    /**
     * Undo a SameLine() or force a new line when in an horizontal-layout context.
     */
    public static native void newLine(); /*
        ImGui::NewLine();
    */

    /**
     * Add vertical spacing.
     */
    public static native void spacing(); /*
        ImGui::Spacing();
    */

    /**
     * Add a dummy item of given size. unlike InvisibleButton(), Dummy() won't take the mouse click or be navigable into.
     */
    public static native void dummy(float width, float height); /*
        ImGui::Dummy(ImVec2(width, height));
    */

    /**
     * Move content position toward the right, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static native void indent(); /*
        ImGui::Indent();
    */

    /**
     * Move content position toward the right, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static native void indent(float indentW); /*
        ImGui::Indent(indentW);
    */

    /**
     * Move content position back to the left, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static native void unindent(); /*
        ImGui::Unindent();
    */

    /**
     * Move content position back to the left, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static native void unindent(float indentW); /*
        ImGui::Unindent(indentW);
    */

    /**
     * Lock horizontal starting position
     */
    public static native void beginGroup(); /*
        ImGui::BeginGroup();
    */

    /**
     * Unlock horizontal starting position + capture the whole group bounding box into one "item" (so you can use IsItemHovered() or layout primitives such as SameLine() on whole group, etc.)
     */
    public static native void endGroup(); /*
        ImGui::EndGroup();
    */

    // (some functions are using window-relative coordinates, such as: GetCursorPos, GetCursorStartPos, GetContentRegionMax, GetWindowContentRegion* etc.
    //  other functions such as GetCursorScreenPos or everything in ImDrawList::
    //  are using the main, absolute coordinate system.
    //  GetWindowPos() + GetCursorPos() == GetCursorScreenPos() etc.)

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static ImVec2 getCursorPos() {
        final ImVec2 value = new ImVec2();
        getCursorPos(value);
        return value;
    }

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native void getCursorPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorPos(), dstImVec2);
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native float getCursorPosX(); /*
        return ImGui::GetCursorPosX();
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native float getCursorPosY(); /*
        return ImGui::GetCursorPosY();
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native void setCursorPos(float x, float y); /*
        ImGui::SetCursorPos(ImVec2(x, y));
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native void setCursorPosX(float x); /*
        ImGui::SetCursorPosX(x);
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native void setCursorPosY(float y); /*
        ImGui::SetCursorPosY(y);
    */

    /**
     * Initial cursor position in window coordinates
     */
    public static ImVec2 getCursorStartPos() {
        final ImVec2 value = new ImVec2();
        getCursorStartPos(value);
        return value;
    }

    /**
     * Initial cursor position in window coordinates
     */
    public static native void getCursorStartPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorStartPos(), dstImVec2);
    */

    /**
     * Initial cursor position in window coordinates
     */
    public static native float getCursorStartPosX(); /*
        return ImGui::GetCursorStartPos().x;
    */

    /**
     * Initial cursor position in window coordinates
     */
    public static native float getCursorStartPosY(); /*
        return ImGui::GetCursorStartPos().y;
    */

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static ImVec2 getCursorScreenPos() {
        final ImVec2 value = new ImVec2();
        getCursorScreenPos(value);
        return value;
    }

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static native void getCursorScreenPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorScreenPos(), dstImVec2);
     */

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static native float getCursorScreenPosX(); /*
        return ImGui::GetCursorScreenPos().x;
    */

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static native float getCursorScreenPosY(); /*
        return ImGui::GetCursorScreenPos().y;
    */

    /**
     * Cursor position in absolute coordinates.
     */    public static native void setCursorScreenPos(float x, float y); /*
        ImGui::SetCursorScreenPos(ImVec2(x, y));
    */

    /**
     * Vertically align upcoming text baseline to FramePadding.y so that it will align properly to regularly framed items (call if you have text on a line before a framed item)
     */
    public static native void alignTextToFramePadding(); /*
        ImGui::AlignTextToFramePadding();
    */

    /**
     * ~ FontSize
     */
    public static native float getTextLineHeight(); /*
        return ImGui::GetTextLineHeight();
    */

    /**
     * ~ FontSize + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of text)
     */
    public static native float getTextLineHeightWithSpacing(); /*
        return ImGui::GetTextLineHeightWithSpacing();
    */

    /**
     * ~ FontSize + style.FramePadding.y * 2
     */
    public static native float getFrameHeight(); /*
        return ImGui::GetFrameHeight();
    */

    /**
     * ~ FontSize + style.FramePadding.y * 2 + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of framed widgets)
     */
    public static native float getFrameHeightWithSpacing(); /*
        return ImGui::GetFrameHeightWithSpacing();
    */

    // ID stack/scopes
    // - Read the FAQ for more details about how ID are handled in dear imgui. If you are creating widgets in a loop you most
    //   likely want to push a unique identifier (e.g. object pointer, loop index) to uniquely differentiate them.
    // - The resulting ID are hashes of the entire stack.
    // - You can also use the "Label##foobar" syntax within widget label to distinguish them from each others.
    // - In this header file we use the "label"/"name" terminology to denote a string that will be displayed and used as an ID,
    //   whereas "strId" denote a string that is only used as an ID and not normally displayed.

    /**
     * Push string into the ID stack (will hash string).
     */
    public static native void pushID(String strId); /*
        ImGui::PushID(strId);
    */

    /**
     * Push string into the ID stack (will hash string).
     */
    public static native void pushID(String strIdBegin, String strIdEnd); /*
        ImGui::PushID(strIdBegin, strIdEnd);
    */

    /**
     * Push pointer into the ID stack (will hash pointer).
     */
    public static native void pushID(long ptrId); /*
        ImGui::PushID((void*)ptrId);
    */

    /**
     * Push integer into the ID stack (will hash integer).
     */
    public static native void pushID(int intId); /*
        ImGui::PushID(intId);
    */

    /**
     * Pop from the ID stack.
     */
    public static native void popID(); /*
        ImGui::PopID();
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static native int getID(String strId); /*
        return ImGui::GetID(strId);
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static native int getID(String strIdBegin, String strIdEnd); /*
        return ImGui::GetID(strIdBegin, strIdEnd);
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static native int getID(long ptrId); /*
        return ImGui::GetID((void*)ptrId);
    */

    // Widgets: Text

    /**
     * Raw text without formatting. Roughly equivalent to Text("%s", text) but:
     * A) doesn't require null terminated string if 'textEnd' is specified,
     * B) it's faster, no memory copy is done, no buffer size limits, recommended for long chunks of text.
     */
    public static native void textUnformatted(String text); /*
        ImGui::TextUnformatted(text);
    */

    /**
     * Formatted text
     * <p>
     * BINDING NOTICE: Since all text formatting could be done on Java side, this call is equal to {@link ImGui#textUnformatted(String)}.
     */
    public static native void text(String text); /*
        ImGui::TextUnformatted(text);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    public static native void textColored(float r, float g, float b, float a, String text); /*
        ImGui::TextColored(ImColor((float)r, (float)g, (float)b, (float)a), text, NULL);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    public static native void textColored(int r, int g, int b, int a, String text); /*
        ImGui::TextColored(ImColor((int)r, (int)g, (int)b, (int)a), text, NULL);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    public static native void textColored(int col, String text); /*
        ImGui::TextColored(ImColor(col), text, NULL);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, style.Colors[ImGuiCol_TextDisabled]); Text(fmt, ...); PopStyleColor();
     */
    public static native void textDisabled(String text); /*
        ImGui::TextDisabled(text, NULL);
    */

    /**
     * Shortcut for PushTextWrapPos(0.0f); Text(fmt, ...); PopTextWrapPos();.
     * Note that this won't work on an auto-resizing window if there's no other widgets to extend the window width,
     * yoy may need to set a size using SetNextWindowSize().
     */
    public static native void textWrapped(String text); /*
        ImGui::TextWrapped(text, NULL);
    */

    /**
     * Display text+label aligned the same way as value+label widgets
     */
    public static native void labelText(String label, String text); /*
        ImGui::LabelText(label, text, NULL);
    */

    /**
     * Shortcut for Bullet()+Text()
     */
    public static native void bulletText(String text); /*
        ImGui::BulletText(text, NULL);
    */

    // Widgets: Main
    // - Most widgets return true when the value has been changed or when pressed/selected
    // - You may also use one of the many IsItemXXX functions (e.g. IsItemActive, IsItemHovered, etc.) to query widget state.

    /**
     * Button
     */
    public static native boolean button(String label); /*
        return ImGui::Button(label);
    */

    /**
     * Button
     */
    public static native boolean button(String label, float width, float height); /*
        return ImGui::Button(label, ImVec2(width, height));
    */

    /**
     * Button with FramePadding=(0,0) to easily embed within text
     */
    public static native boolean smallButton(String label); /*
        return ImGui::SmallButton(label);
    */

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static native boolean invisibleButton(String strId, float width, float height); /*
        return ImGui::InvisibleButton(strId, ImVec2(width, height));
    */

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static native boolean invisibleButton(String strId, float width, float height, int imGuiButtonFlags); /*
        return ImGui::InvisibleButton(strId, ImVec2(width, height), imGuiButtonFlags);
    */

    /**
     * Square button with an arrow shape
     */
    public static native boolean arrowButton(String strId, int dir); /*
        return ImGui::ArrowButton(strId, dir);
    */

    public static native void image(int textureID, float sizeX, float sizeY); /*
        ImGui::Image((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY));
    */

    public static native void image(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y); /*
        ImGui::Image((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y));
    */

    public static native void image(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*
        ImGui::Image((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y));
    */

    public static native void image(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColorR, float tintColorG, float tintColorB, float tintColorA); /*
        ImGui::Image((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y), ImVec4(tintColorR, tintColorG, tintColorB, tintColorA));
    */

    public static native void image(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColorR, float tintColorG, float tintColorB, float tintColorA, float borderR, float borderG, float borderB, float borderA); /*
        ImGui::Image((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y), ImVec4(tintColorR, tintColorG, tintColorB, tintColorA), ImVec4(borderR, borderG, borderB, borderA));
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static native boolean imageButton(int textureID, float sizeX, float sizeY); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY));
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static native boolean imageButton(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y));
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static native boolean imageButton(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y));
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static native boolean imageButton(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y), framePadding);
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static native boolean imageButton(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding, float bgColorR, float bgColorG, float bgColorB, float bgColorA); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y), framePadding, ImVec4(bgColorR, bgColorG, bgColorB, bgColorA));
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static native boolean imageButton(int textureID, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding, float bgColorR, float bgColorG, float bgColorB, float bgColorA, float tintR, float tintG, float tintB, float tintA); /*
        return ImGui::ImageButton((ImTextureID)(intptr_t)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0X, uv0Y), ImVec2(uv1X, uv1Y), framePadding, ImVec4(bgColorR, bgColorG, bgColorB, bgColorA), ImVec4(tintR, tintG, tintB, tintA));
    */

    public static native boolean checkbox(String label, boolean active); /*
        bool flag = (bool)active;
        return ImGui::Checkbox(label, &flag);
    */

    public static boolean checkbox(String label, ImBoolean active) {
        return nCheckbox(label, active.getData());
    }

    private static native boolean nCheckbox(String label, boolean[] data); /*
        return ImGui::Checkbox(label, &data[0]);
    */

    public static boolean checkboxFlags(String label, ImInt v, int flagsValue) {
        return nCheckboxFlags(label, v.getData(), flagsValue);
    }

    private static native boolean nCheckboxFlags(String label, int[] data, int flagsValue); /*
        return ImGui::CheckboxFlags(label, (unsigned int*)&data[0], flagsValue);
    */

    /**
     * Use with e.g. if (RadioButton("one", my_value==1)) { my_value = 1; }
     */
    public static native boolean radioButton(String label, boolean active); /*
        return ImGui::RadioButton(label, active);
    */

    /**
     * Shortcut to handle the above pattern when value is an integer
     */
    public static boolean radioButton(String label, ImInt v, int vButton) {
        return nRadioButton(label, v.getData(), vButton);
    }

    private static native boolean nRadioButton(String label, int[] data, int vButton); /*
        return ImGui::RadioButton(label, &data[0], vButton);
    */

    public static native void progressBar(float fraction); /*
        ImGui::ProgressBar(fraction);
    */

    public static native void progressBar(float fraction, float sizeArgX, float sizeArgY); /*
        ImGui::ProgressBar(fraction, ImVec2(sizeArgX, sizeArgY));
    */

    public static native void progressBar(float fraction, float sizeArgX, float sizeArgY, String overlay); /*
        ImGui::ProgressBar(fraction, ImVec2(sizeArgX, sizeArgY), overlay);
    */

    /**
     * Draw a small circle + keep the cursor on the same line. advance cursor x position by GetTreeNodeToLabelSpacing(), same distance that TreeNode() uses
     */
    public static native void bullet(); /*
        ImGui::Bullet();
    */

    // Widgets: Combo Box
    // - The BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
    // - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

    public static native boolean beginCombo(String label, String previewValue); /*
        return ImGui::BeginCombo(label, previewValue);
     */

    public static native boolean beginCombo(String label, String previewValue, int imGuiComboFlags); /*
        return ImGui::BeginCombo(label, previewValue, imGuiComboFlags);
    */

    /**
     * Only call EndCombo() if BeginCombo() returns true!
     */
    public static native void endCombo(); /*
        ImGui::EndCombo();
    */

    public static boolean combo(String label, ImInt currentItem, String[] items) {
        return nCombo(label, currentItem.getData(), items, items.length, -1);
    }

    public static boolean combo(String label, ImInt currentItem, String[] items, int popupMaxHeightInItems) {
        return nCombo(label, currentItem.getData(), items, items.length, popupMaxHeightInItems);
    }

    private static native boolean nCombo(String label, int[] currentItem, String[] items, int itemsCount, int popupMaxHeightInItems); /*
        const char* listboxItems[itemsCount];

        for (int i = 0; i < itemsCount; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(items, i);
            const char* rawString = env->GetStringUTFChars(string, JNI_FALSE);
            listboxItems[i] = rawString;
        }

        bool flag = ImGui::Combo(label, &currentItem[0], listboxItems, itemsCount, popupMaxHeightInItems);

        for (int i = 0; i< itemsCount; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(items, i);
            env->ReleaseStringUTFChars(string, listboxItems[i]);
        }

        return flag;
    */

    /**
     * Separate items with \0 within a string, end item-list with \0\0. e.g. "One\0Two\0Three\0"
     */
    public static boolean combo(String label, ImInt currentItem, String itemsSeparatedByZeros) {
        return nCombo(label, currentItem.getData(), itemsSeparatedByZeros, -1);
    }

    public static boolean combo(String label, ImInt currentItem, String itemsSeparatedByZeros, int popupMaxHeightInItems) {
        return nCombo(label, currentItem.getData(), itemsSeparatedByZeros, popupMaxHeightInItems);
    }

    private static native boolean nCombo(String label, int[] currentItem, String itemsSeparatedByZeros, int popupMaxHeightInItems); /*
        return ImGui::Combo(label, &currentItem[0], itemsSeparatedByZeros, popupMaxHeightInItems);
    */

    // Widgets: Drag Sliders
    // - CTRL+Click on any drag box to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - For all the Float2/Float3/Float4/Int2/Int3/Int4 versions of every functions, note that a 'float v[X]' function argument is the same as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible. You can pass address of your first element out of a contiguous set, e.g. &myvector.x
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Format string may also be set to NULL or use the default format ("%f" or "%d").
    // - Speed are per-pixel of mouse movement (vSpeed=0.2f: mouse needs to move by 5 pixels to increase value by 1). For gamepad/keyboard navigation, minimum speed is Max(vSpeed, minimum_step_at_given_precision).
    // - Use vMin < vMax to clamp edits to given limits. Note that CTRL+Click manual input can override those limits.
    // - Use v_max = FLT_MAX / INT_MAX etc to avoid clamping to a maximum, same with v_min = -FLT_MAX / INT_MIN to avoid clamping to a minimum.
    // - Use vMin > vMax to lock edits.
    // - We use the same sets of flags for DragXXX() and SliderXXX() functions as the features are the same and it makes it easier to swap them.
    // - Legacy: Pre-1.78 there are DragXXX() function signatures that takes a final `float power=1.0f' argument instead of the `ImGuiSliderFlags flags=0' argument.
    //   If you get a warning converting a float to ImGuiSliderFlags, read https://github.com/ocornut/imgui/issues/3361

    public static native boolean dragFloat(String label, float[] v); /*
        return ImGui::DragFloat(label, &v[0]);
    */

    public static native boolean dragFloat(String label, float[] v, float vSpeed); /*
        return ImGui::DragFloat(label, &v[0], vSpeed);
    */

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static native boolean dragFloat(String label, float[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax);
    */

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static native boolean dragFloat(String label, float[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax, format);
    */

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static native boolean dragFloat(String label, float[] v, float vSpeed, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean dragFloat2(String label, float[] v); /*
        return ImGui::DragFloat2(label, v);
    */

    public static native boolean dragFloat2(String label, float[] v, float vSpeed); /*
        return ImGui::DragFloat2(label, v, vSpeed);
    */

    public static native boolean dragFloat2(String label, float[] v, float vSpeed, float vMin); /*
        return ImGui::DragFloat2(label, v, vSpeed, vMin);
    */

    public static native boolean dragFloat2(String label, float[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat2(label, v, vSpeed, vMin, vMax);
    */

    public static native boolean dragFloat2(String label, float[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat2(label, v, vSpeed, vMin, vMax, format);
    */

    public static native boolean dragFloat2(String label, float[] v, float vSpeed, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragFloat2(label, v, vSpeed, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean dragFloat3(String label, float[] v); /*
        return ImGui::DragFloat3(label, v);
    */

    public static native boolean dragFloat3(String label, float[] v, float vSpeed); /*
        return ImGui::DragFloat3(label, v, vSpeed);
    */

    public static native boolean dragFloat3(String label, float[] v, float vSpeed, float vMin); /*
        return ImGui::DragFloat3(label, v, vSpeed, vMin);
    */

    public static native boolean dragFloat3(String label, float[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat3(label, v, vSpeed, vMin, vMax);
    */

    public static native boolean dragFloat3(String label, float[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat3(label, v, vSpeed, vMin, vMax, format);
    */

    public static native boolean dragFloat3(String label, float[] v, float vSpeed, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragFloat3(label, v, vSpeed, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean dragFloat4(String label, float[] v); /*
        return ImGui::DragFloat4(label, v);
    */

    public static native boolean dragFloat4(String label, float[] v, float vSpeed); /*
        return ImGui::DragFloat4(label, v, vSpeed);
    */

    public static native boolean dragFloat4(String label, float[] v, float vSpeed, float vMin); /*
        return ImGui::DragFloat4(label, v, vSpeed, vMin);
    */

    public static native boolean dragFloat4(String label, float[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloat4(label, v, vSpeed, vMin, vMax);
    */

    public static native boolean dragFloat4(String label, float[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloat4(label, v, vSpeed, vMin, vMax, format);
    */

    public static native boolean dragFloat4(String label, float[] v, float vSpeed, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragFloat4(label, v, vSpeed, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0]);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax, String format, String formatMax); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax);
    */

    public static native boolean dragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, float vMin, float vMax, String format, String formatMax, int imGuiSliderFlags); /*
        return ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean dragInt(String label, int[] v); /*
        return ImGui::DragInt(label, &v[0]);
    */

    public static native boolean dragInt(String label, int[] v, float vSpeed); /*
        return ImGui::DragInt(label, &v[0], vSpeed);
    */

    public static native boolean dragInt(String label, int[] v, float vSpeed, float vMin); /*
        return ImGui::DragInt(label, &v[0], vSpeed, vMin);
    */

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static native boolean dragInt(String label, int[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragInt(label, &v[0], vSpeed, vMin, vMax);
    */

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static native boolean dragInt(String label, int[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragInt(label, &v[0], vSpeed, vMin, vMax, format);
    */

    public static native boolean dragInt2(String label, int[] v); /*
        return ImGui::DragInt2(label, v);
    */

    public static native boolean dragInt2(String label, int[] v, float vSpeed); /*
        return ImGui::DragInt2(label, v, vSpeed);
    */

    public static native boolean dragInt2(String label, int[] v, float vSpeed, float vMin); /*
        return ImGui::DragInt2(label, v, vSpeed, vMin);
    */

    public static native boolean dragInt2(String label, int[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragInt2(label, v, vSpeed, vMin, vMax);
    */

    public static native boolean dragInt2(String label, int[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragInt2(label, v, vSpeed, vMin, vMax, format);
    */

    public static native boolean dragInt3(String label, int[] v); /*
        return ImGui::DragInt3(label, v);
    */

    public static native boolean dragInt3(String label, int[] v, float vSpeed); /*
        return ImGui::DragInt3(label, v, vSpeed);
    */

    public static native boolean dragInt3(String label, int[] v, float vSpeed, float vMin); /*
        return ImGui::DragInt3(label, v, vSpeed, vMin);
    */

    public static native boolean dragInt3(String label, int[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragInt3(label, v, vSpeed, vMin, vMax);
    */

    public static native boolean dragInt3(String label, int[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragInt3(label, v, vSpeed, vMin, vMax, format);
    */

    public static native boolean dragInt4(String label, int[] v); /*
        return ImGui::DragInt4(label, v);
    */

    public static native boolean dragInt4(String label, int[] v, float vSpeed); /*
        return ImGui::DragInt4(label, v, vSpeed);
    */

    public static native boolean dragInt4(String label, int[] v, float vSpeed, float vMin); /*
        return ImGui::DragInt4(label, v, vSpeed, vMin);
    */

    public static native boolean dragInt4(String label, int[] v, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragInt4(label, v, vSpeed, vMin, vMax);
    */

    public static native boolean dragInt4(String label, int[] v, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragInt4(label, v, vSpeed, vMin, vMax, format);
    */

    public static native boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0]);
    */

    public static native boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed);
    */

    public static native boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, float vMin); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin);
    */

    public static native boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, float vMin, float vMax); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax);
    */

    public static native boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, float vMin, float vMax, String format); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format);
    */

    public static native boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed, float vMin, float vMax, String format, String formatMax); /*
        return ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax);
    */

    public static boolean dragScalar(String label, int dataType, ImInt pData, float vSpeed) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed);
    }

    private static native boolean nDragScalar(String label, int dataType, int[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed);
    */

    public static boolean dragScalar(String label, int dataType, ImInt pData, float vSpeed, int pMin) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin);
    }

    private static native boolean nDragScalar(String label, int dataType, int[] pData, float vSpeed, int pMin); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin);
    */

    public static boolean dragScalar(String label, int dataType, ImInt pData, float vSpeed, int pMin, int pMax) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalar(String label, int dataType, int[] pData, float vSpeed, int pMin, int pMax); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalar(String label, int dataType, ImInt pData, float vSpeed, int pMin, int pMax, String format) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalar(String label, int dataType, ImInt pData, float vSpeed, int pMin, int pMax, String format, int imGuiSliderFlags) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String label, int dataType, int[] pData, float vSpeed, int pMin, int pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalar(String label, int dataType, ImFloat pData, float vSpeed) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed);
    }

    private static native boolean nDragScalar(String label, int dataType, float[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed);
    */

    public static boolean dragScalar(String label, int dataType, ImFloat pData, float vSpeed, float pMin) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin);
    }

    private static native boolean nDragScalar(String label, int dataType, float[] pData, float vSpeed, float pMin); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin);
    */

    public static boolean dragScalar(String label, int dataType, ImFloat pData, float vSpeed, float pMin, float pMax) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalar(String label, int dataType, float[] pData, float vSpeed, float pMin, float pMax); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalar(String label, int dataType, ImFloat pData, float vSpeed, float pMin, float pMax, String format) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalar(String label, int dataType, ImFloat pData, float vSpeed, float pMin, float pMax, String format, int imGuiSliderFlags) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String label, int dataType, float[] pData, float vSpeed, float pMin, float pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalar(String label, int dataType, ImDouble pData, float vSpeed) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed);
    }

    private static native boolean nDragScalar(String label, int dataType, double[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed);
    */

    public static boolean dragScalar(String label, int dataType, ImDouble pData, float vSpeed, double pMin) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin);
    }

    private static native boolean nDragScalar(String label, int dataType, double[] pData, float vSpeed, double pMin); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin);
    */

    public static boolean dragScalar(String label, int dataType, ImDouble pData, float vSpeed, double pMin, double pMax) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalar(String label, int dataType, double[] pData, float vSpeed, double pMin, double pMax); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalar(String label, int dataType, ImDouble pData, float vSpeed, double pMin, double pMax, String format) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalar(String label, int dataType, ImDouble pData, float vSpeed, double pMin, double pMax, String format, int imGuiSliderFlags) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String label, int dataType, double[] pData, float vSpeed, double pMin, double pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalar(String label, int dataType, ImLong pData, float vSpeed) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed);
    }

    private static native boolean nDragScalar(String label, int dataType, long[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed);
    */

    public static boolean dragScalar(String label, int dataType, ImLong pData, float vSpeed, long pMin) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin);
    }

    private static native boolean nDragScalar(String label, int dataType, long[] pData, float vSpeed, long pMin); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin);
    */

    public static boolean dragScalar(String label, int dataType, ImLong pData, float vSpeed, long pMin, long pMax) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalar(String label, int dataType, long[] pData, float vSpeed, long pMin, long pMax); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalar(String label, int dataType, ImLong pData, float vSpeed, long pMin, long pMax, String format) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalar(String label, int dataType, ImLong pData, float vSpeed, long pMin, long pMax, String format, int imGuiSliderFlags) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String label, int dataType, long[] pData, float vSpeed, long pMin, long pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalar(String label, int dataType, ImShort pData, float vSpeed) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed);
    }

    private static native boolean nDragScalar(String label, int dataType, short[] pData, float vSpeed); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed);
    */

    public static boolean dragScalar(String label, int dataType, ImShort pData, float vSpeed, short pMin) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin);
    }

    private static native boolean nDragScalar(String label, int dataType, short[] pData, float vSpeed, short pMin); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin);
    */

    public static boolean dragScalar(String label, int dataType, ImShort pData, float vSpeed, short pMin, short pMax) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalar(String label, int dataType, short[] pData, float vSpeed, short pMin, short pMax); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalar(String label, int dataType, ImShort pData, float vSpeed, short pMin, short pMax, String format) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalar(String label, int dataType, ImShort pData, float vSpeed, short pMin, short pMax, String format, int imGuiSliderFlags) {
        return nDragScalar(label, dataType, pData.getData(), vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String label, int dataType, short[] pData, float vSpeed, short pMin, short pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalar(label, dataType, &pData[0], vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalarN(String label, int dataType, ImInt pData, int components, float vSpeed) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed);
    }

    private static native boolean nDragScalarN(String label, int dataType, int[] pData, int components, float vSpeed); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed);
    */

    public static boolean dragScalarN(String label, int dataType, ImInt pData, int components, float vSpeed, int pMin) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin);
    }

    private static native boolean nDragScalarN(String label, int dataType, int[] pData, int components, float vSpeed, int pMin); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin);
    */

    public static boolean dragScalarN(String label, int dataType, ImInt pData, int components, float vSpeed, int pMin, int pMax) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalarN(String label, int dataType, int[] pData, int components, float vSpeed, int pMin, int pMax); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalarN(String label, int dataType, ImInt pData, int components, float vSpeed, int pMin, int pMax, String format) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalarN(String label, int dataType, ImInt pData, int components, float vSpeed, int pMin, int pMax, String format, int imGuiSliderFlags) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String label, int dataType, int[] pData, int components, float vSpeed, int pMin, int pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalarN(String label, int dataType, ImFloat pData, int components, float vSpeed) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed);
    }

    private static native boolean nDragScalarN(String label, int dataType, float[] pData, int components, float vSpeed); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed);
    */

    public static boolean dragScalarN(String label, int dataType, ImFloat pData, int components, float vSpeed, float pMin) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin);
    }

    private static native boolean nDragScalarN(String label, int dataType, float[] pData, int components, float vSpeed, float pMin); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin);
    */

    public static boolean dragScalarN(String label, int dataType, ImFloat pData, int components, float vSpeed, float pMin, float pMax) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalarN(String label, int dataType, float[] pData, int components, float vSpeed, float pMin, float pMax); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalarN(String label, int dataType, ImFloat pData, int components, float vSpeed, float pMin, float pMax, String format) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalarN(String label, int dataType, ImFloat pData, int components, float vSpeed, float pMin, float pMax, String format, int imGuiSliderFlags) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String label, int dataType, float[] pData, int components, float vSpeed, float pMin, float pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalarN(String label, int dataType, ImDouble pData, int components, float vSpeed) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed);
    }

    private static native boolean nDragScalarN(String label, int dataType, double[] pData, int components, float vSpeed); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed);
    */

    public static boolean dragScalarN(String label, int dataType, ImDouble pData, int components, float vSpeed, double pMin) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin);
    }

    private static native boolean nDragScalarN(String label, int dataType, double[] pData, int components, float vSpeed, double pMin); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin);
    */

    public static boolean dragScalarN(String label, int dataType, ImDouble pData, int components, float vSpeed, double pMin, double pMax) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalarN(String label, int dataType, double[] pData, int components, float vSpeed, double pMin, double pMax); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalarN(String label, int dataType, ImDouble pData, int components, float vSpeed, double pMin, double pMax, String format) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalarN(String label, int dataType, ImDouble pData, int components, float vSpeed, double pMin, double pMax, String format, int imGuiSliderFlags) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String label, int dataType, double[] pData, int components, float vSpeed, double pMin, double pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalarN(String label, int dataType, ImLong pData, int components, float vSpeed) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed);
    }

    private static native boolean nDragScalarN(String label, int dataType, long[] pData, int components, float vSpeed); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed);
    */

    public static boolean dragScalarN(String label, int dataType, ImLong pData, int components, float vSpeed, long pMin) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin);
    }

    private static native boolean nDragScalarN(String label, int dataType, long[] pData, int components, float vSpeed, long pMin); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin);
    */

    public static boolean dragScalarN(String label, int dataType, ImLong pData, int components, float vSpeed, long pMin, long pMax) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalarN(String label, int dataType, long[] pData, int components, float vSpeed, long pMin, long pMax); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalarN(String label, int dataType, ImLong pData, int components, float vSpeed, long pMin, long pMax, String format) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalarN(String label, int dataType, ImLong pData, int components, float vSpeed, long pMin, long pMax, String format, int imGuiSliderFlags) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String label, int dataType, long[] pData, int components, float vSpeed, long pMin, long pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean dragScalarN(String label, int dataType, ImShort pData, int components, float vSpeed) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed);
    }

    private static native boolean nDragScalarN(String label, int dataType, short[] pData, int components, float vSpeed); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed);
    */

    public static boolean dragScalarN(String label, int dataType, ImShort pData, int components, float vSpeed, short pMin) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin);
    }

    private static native boolean nDragScalarN(String label, int dataType, short[] pData, int components, float vSpeed, short pMin); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin);
    */

    public static boolean dragScalarN(String label, int dataType, ImShort pData, int components, float vSpeed, short pMin, short pMax) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax);
    }

    private static native boolean nDragScalarN(String label, int dataType, short[] pData, int components, float vSpeed, short pMin, short pMax); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax);
    */

    public static boolean dragScalarN(String label, int dataType, ImShort pData, int components, float vSpeed, short pMin, short pMax, String format) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, 0);
    }

    public static boolean dragScalarN(String label, int dataType, ImShort pData, int components, float vSpeed, short pMin, short pMax, String format, int imGuiSliderFlags) {
        return nDragScalarN(label, dataType, pData.getData(), components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String label, int dataType, short[] pData, int components, float vSpeed, short pMin, short pMax, String format, int imGuiSliderFlags); /*
        return ImGui::DragScalarN(label, dataType, &pData[0], components, vSpeed, &pMin, &pMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    // Widgets: Regular Sliders
    // - CTRL+Click on any slider to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Format string may also be set to NULL or use the default format ("%f" or "%d").
    // - Legacy: Pre-1.78 there are SliderXXX() function signatures that takes a final `float power=1.0f' argument instead of the `ImGuiSliderFlags flags=0' argument.
    //   If you get a warning converting a float to ImGuiSliderFlags, read https://github.com/ocornut/imgui/issues/3361

    /**
     * Adjust format to decorate the value with a prefix or a suffix for in-slider labels or unit display.
     */
    public static native boolean sliderFloat(String label, float[] v, float vMin, float vMax); /*
        return ImGui::SliderFloat(label, &v[0],vMin, vMax);
    */

    public static native boolean sliderFloat(String label, float[] v, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat(label, &v[0], vMin, vMax, format);
    */

    public static native boolean sliderFloat(String label, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderFloat(label, &v[0], vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean sliderFloat2(String label, float[] v, float vMin, float vMax); /*
        return ImGui::SliderFloat2(label, v, vMin, vMax);
    */

    public static native boolean sliderFloat2(String label, float[] v, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat2(label, v, vMin, vMax, format);
    */

    public static native boolean sliderFloat2(String label, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderFloat2(label, v, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean sliderFloat3(String label, float[] v, float vMin, float vMax); /*
        return ImGui::SliderFloat3(label, v, vMin, vMax);
    */

    public static native boolean sliderFloat3(String label, float[] v, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat3(label, v, vMin, vMax, format);
    */

    public static native boolean sliderFloat3(String label, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderFloat3(label, v, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean sliderFloat4(String label, float[] v, float vMin, float vMax); /*
        return ImGui::SliderFloat4(label, v, vMin, vMax);
    */

    public static native boolean sliderFloat4(String label, float[] v, float vMin, float vMax, String format); /*
        return ImGui::SliderFloat4(label, v, vMin, vMax, format);
    */

    public static native boolean sliderFloat4(String label, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderFloat4(label, v, vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean sliderAngle(String label, float[] vRad); /*
        return ImGui::SliderAngle(label, &vRad[0]);
    */

    public static native boolean sliderAngle(String label, float[] vRad, float vDegreesMin); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin);
    */

    public static native boolean sliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax);
    */

    public static native boolean sliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax, String format); /*
        return ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, format);
    */

    public static native boolean sliderInt(String label, int[] v, int vMin, int vMax); /*
        return ImGui::SliderInt(label, &v[0], vMin, vMax);
    */

    public static native boolean sliderInt(String label, int[] v, int vMin, int vMax, String format); /*
        return ImGui::SliderInt(label, &v[0], vMin, vMax, format);
    */

    public static native boolean sliderInt2(String label, int[] v, int vMin, int vMax); /*
        return ImGui::SliderInt2(label, v, vMin, vMax);
    */

    public static native boolean sliderInt2(String label, int[] v, int vMin, int vMax, String format); /*
        return ImGui::SliderInt2(label, v, vMin, vMax, format);
    */

    public static native boolean sliderInt3(String label, int[] v, int vMin, int vMax); /*
        return ImGui::SliderInt3(label, v, vMin, vMax);
    */

    public static native boolean sliderInt3(String label, int[] v, int vMin, int vMax, String format); /*
        return ImGui::SliderInt3(label, v, vMin, vMax, format);
    */

    public static native boolean sliderInt4(String label, int[] v, int vMin, int vMax); /*
        return ImGui::SliderInt4(label, v, vMin, vMax);
    */

    public static native boolean sliderInt4(String label, int[] v, int vMin, int vMax, String format); /*
        return ImGui::SliderInt4(label, v, vMin, vMax, format);
    */

    public static boolean sliderScalar(String label, int dataType, ImInt v, int vMin, int vMax) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalar(String label, int dataType, int[] v, int vMin, int vMax); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax);
    */

    public static boolean sliderScalar(String label, int dataType, ImInt v, int vMin, int vMax, String format) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalar(String label, int dataType, ImInt v, int vMin, int vMax, String format, int imGuiSliderFlags) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String label, int dataType, int[] v, int vMin, int vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalar(String label, int dataType, ImFloat v, float vMin, float vMax) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalar(String label, int dataType, float[] v, float vMin, float vMax); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax);
    */

    public static boolean sliderScalar(String label, int dataType, ImFloat v, float vMin, float vMax, String format) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalar(String label, int dataType, ImFloat v, float vMin, float vMax, String format, int imGuiSliderFlags) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String label, int dataType, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalar(String label, int dataType, ImLong v, long vMin, long vMax) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalar(String label, int dataType, long[] v, long vMin, long vMax); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax);
    */

    public static boolean sliderScalar(String label, int dataType, ImLong v, long vMin, long vMax, String format) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalar(String label, int dataType, ImLong v, long vMin, long vMax, String format, int imGuiSliderFlags) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String label, int dataType, long[] v, long vMin, long vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalar(String label, int dataType, ImDouble v, double vMin, double vMax) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalar(String label, int dataType, double[] v, double vMin, double vMax); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax);
    */

    public static boolean sliderScalar(String label, int dataType, ImDouble v, double vMin, double vMax, String format) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalar(String label, int dataType, ImDouble v, double vMin, double vMax, String format, int imGuiSliderFlags) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String label, int dataType, double[] v, double vMin, double vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalar(String label, int dataType, ImShort v, short vMin, short vMax) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalar(String label, int dataType, short[] v, short vMin, short vMax); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax);
    */

    public static boolean sliderScalar(String label, int dataType, ImShort v, short vMin, short vMax, String format) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalar(String label, int dataType, ImShort v, short vMin, short vMax, String format, int imGuiSliderFlags) {
        return nSliderScalar(label, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String label, int dataType, short[] v, short vMin, short vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalar(label, dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImInt v, int vMin, int vMax) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, int[] v, int vMin, int vMax); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImInt v, int vMin, int vMax, String format) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalarN(String label, int dataType, int components, ImInt v, int vMin, int vMax, String format, int imGuiSliderFlags) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, int[] v, int vMin, int vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImFloat v, float vMin, float vMax) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, float[] v, float vMin, float vMax); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImFloat v, float vMin, float vMax, String format) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalarN(String label, int dataType, int components, ImFloat v, float vMin, float vMax, String format, int imGuiSliderFlags) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImLong v, long vMin, long vMax) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, long[] v, long vMin, long vMax); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImLong v, long vMin, long vMax, String format) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalarN(String label, int dataType, int components, ImLong v, long vMin, long vMax, String format, int imGuiSliderFlags) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, long[] v, long vMin, long vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImDouble v, double vMin, double vMax) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, double[] v, double vMin, double vMax); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImDouble v, double vMin, double vMax, String format) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalarN(String label, int dataType, int components, ImDouble v, double vMin, double vMax, String format, int imGuiSliderFlags) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, double[] v, double vMin, double vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImShort v, short vMin, short vMax) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, short[] v, short vMin, short vMax); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax);
    */

    public static boolean sliderScalarN(String label, int dataType, int components, ImShort v, short vMin, short vMax, String format) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean sliderScalarN(String label, int dataType, int components, ImShort v, short vMin, short vMax, String format, int imGuiSliderFlags) {
        return nSliderScalarN(label, dataType, components, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String label, int dataType, int components, short[] v, short vMin, short vMax, String format, int imGuiSliderFlags); /*
        return ImGui::SliderScalarN(label, dataType, &v[0], components, &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean vSliderFloat(String label, float sizeX, float sizeY, float[] v, float vMin, float vMax); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], vMin, vMax);
    */

    public static native boolean vSliderFloat(String label, float sizeX, float sizeY, float[] v, float vMin, float vMax, String format); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], vMin, vMax);
    */

    public static native boolean vSliderFloat(String label, float sizeX, float sizeY, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], vMin, vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static native boolean vSliderInt(String label, float sizeX, float sizeY, int[] v, int vMin, int vMax); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], vMin, vMax);
    */

    public static native boolean vSliderInt(String label, float sizeX, float sizeY, int[] v, int vMin, int vMax, String format); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], vMin, vMax, format);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImInt v, int vMin, int vMax) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, int[] v, int vMin, int vMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImInt v, int vMin, int vMax, String format) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImInt v, int vMin, int vMax, String format, int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, int[] v, int vMin, int vMax, String format, int imGuiSliderFlags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImFloat v, float vMin, float vMax) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, float[] v, float vMin, float vMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImFloat v, float vMin, float vMax, String format) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImFloat v, float vMin, float vMax, String format, int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, float[] v, float vMin, float vMax, String format, int imGuiSliderFlags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImLong v, long vMin, long vMax) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, long[] v, long vMin, long vMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImLong v, long vMin, long vMax, String format) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImLong v, long vMin, long vMax, String format, int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, long[] v, long vMin, long vMax, String format, int imGuiSliderFlags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImDouble v, double vMin, double vMax) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, double[] v, double vMin, double vMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImDouble v, double vMin, double vMax, String format) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImDouble v, double vMin, double vMax, String format, int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, double[] v, double vMin, double vMax, String format, int imGuiSliderFlags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImShort v, short vMin, short vMax) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, short[] v, short vMin, short vMax); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax);
    */

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImShort v, short vMin, short vMax, String format) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, 0);
    }

    public static boolean vSliderScalar(String label, float sizeX, float sizeY, int dataType, ImShort v, short vMin, short vMax, String format, int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, dataType, v.getData(), vMin, vMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int dataType, short[] v, short vMin, short vMax, String format, int imGuiSliderFlags); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), dataType, &v[0], &vMin, &vMax, format, (ImGuiSliderFlags)imGuiSliderFlags);
    */

    // Widgets: Input with Keyboard
    // - If you want to use InputText() with std::string or any custom dynamic string type, see misc/cpp/imgui_stdlib.h and comments in imgui_demo.cpp.
    // - Most of the ImGuiInputTextFlags flags are only useful for InputText() and not for InputFloatX, InputIntX, InputDouble etc.

    /*JNI
        jmethodID jImStringResizeInternalMID;

        jfieldID inputDataSizeID;
        jfieldID inputDataIsDirtyID;
        jfieldID inputDataIsResizedID;

        struct InputTextCallbackUserData {
            JNIEnv* env;
            jobject* imString;
            int maxSize;
            jbyteArray jResizedBuf;
            char* resizedBuf;
            jobject* textInputData;
            char* allowedChars;
        };

        static int TextEditCallbackStub(ImGuiInputTextCallbackData* data) {
            InputTextCallbackUserData* userData = (InputTextCallbackUserData*)data->UserData;

            if (data->EventFlag == ImGuiInputTextFlags_CallbackCharFilter) {
                int allowedCharLength = strlen(userData->allowedChars);
                if(allowedCharLength > 0) {
                    bool found = false;
                    for(int i = 0; i < allowedCharLength; i++) {
                        if(userData->allowedChars[i] == data->EventChar) {
                            found = true;
                            break;
                        }
                    }
                    return found ? 0 : 1;
                }
            } else if (data->EventFlag == ImGuiInputTextFlags_CallbackResize) {
                int newSize = data->BufTextLen;
                if (newSize >= userData->maxSize) {
                    JNIEnv* env = userData->env;

                    jbyteArray newBufArr = (jbyteArray)env->CallObjectMethod(*userData->imString, jImStringResizeInternalMID, newSize);
                    char* newBuf = (char*)env->GetPrimitiveArrayCritical(newBufArr, 0);

                    data->Buf = newBuf;

                    userData->jResizedBuf = newBufArr;
                    userData->resizedBuf = newBuf;
                }
            }

            return 0;
        }
    */

    private static native void nInitInputTextData(); /*
        jclass jInputDataClass = env->FindClass("imgui/type/ImString$InputData");
        inputDataSizeID = env->GetFieldID(jInputDataClass, "size", "I");
        inputDataIsDirtyID = env->GetFieldID(jInputDataClass, "isDirty", "Z");
        inputDataIsResizedID = env->GetFieldID(jInputDataClass, "isResized", "Z");

        jclass jImString = env->FindClass("imgui/type/ImString");
        jImStringResizeInternalMID = env->GetMethodID(jImString, "resizeInternal", "(I)[B");
    */

    public static boolean inputText(String label, ImString text) {
        return preInputText(false, label, null, text, 0, 0, ImGuiInputTextFlags.None);
    }

    public static boolean inputText(String label, ImString text, int imGuiInputTextFlags) {
        return preInputText(false, label, null, text, 0, 0, imGuiInputTextFlags);
    }

    public static boolean inputTextMultiline(String label, ImString text) {
        return preInputText(true, label, null, text, 0, 0, ImGuiInputTextFlags.None);
    }

    public static boolean inputTextMultiline(String label, ImString text, float width, float height) {
        return preInputText(true, label, null, text, width, height, ImGuiInputTextFlags.None);
    }

    public static boolean inputTextMultiline(String label, ImString text, int imGuiInputTextFlags) {
        return preInputText(true, label, null, text, 0, 0, imGuiInputTextFlags);
    }

    public static boolean inputTextMultiline(String label, ImString text, float width, float height, int imGuiInputTextFlags) {
        return preInputText(true, label, null, text, width, height, imGuiInputTextFlags);
    }

    public static boolean inputTextWithHint(String label, String hint, ImString text) {
        return preInputText(false, label, hint, text, 0, 0, ImGuiInputTextFlags.None);
    }

    public static boolean inputTextWithHint(String label, String hint, ImString text, int imGuiInputTextFlags) {
        return preInputText(false, label, hint, text, 0, 0, imGuiInputTextFlags);
    }

    private static boolean preInputText(boolean multiline, String label, String hint, ImString text, float width, float height, int flags) {
        final ImString.InputData inputData = text.inputData;

        if (inputData.isResizable) {
            flags |= ImGuiInputTextFlags.CallbackResize;
        }

        if (!inputData.allowedChars.isEmpty()) {
            flags |= ImGuiInputTextFlags.CallbackCharFilter;
        }

        String hintLabel = hint;
        if (hintLabel == null) {
            hintLabel = "";
        }

        return nInputText(multiline, hint != null, label, hintLabel, text, text.getData(), text.getData().length, width, height, flags, inputData, inputData.allowedChars);
    }

    private static native boolean nInputText(boolean multiline, boolean hint, String label, String hintLabel, ImString imString, byte[] buf, int maxSize, float width, float height, int flags, ImString.InputData textInputData, String allowedChars); /*
        InputTextCallbackUserData userData;
        userData.imString = &imString;
        userData.maxSize = maxSize;
        userData.jResizedBuf = NULL;
        userData.resizedBuf = NULL;
        userData.textInputData = &textInputData;
        userData.env = env;
        userData.allowedChars = allowedChars;

        bool valueChanged;

        if (multiline) {
            valueChanged = ImGui::InputTextMultiline(label, buf, maxSize, ImVec2(width, height), flags, &TextEditCallbackStub, &userData);
        } else if (hint) {
            valueChanged = ImGui::InputTextWithHint(label, hintLabel, buf, maxSize, flags, &TextEditCallbackStub, &userData);
        } else {
            valueChanged = ImGui::InputText(label, buf, maxSize, flags, &TextEditCallbackStub, &userData);
        }

        if (valueChanged) {
            int size;

            if (userData.jResizedBuf != NULL) {
                size = strlen(userData.resizedBuf);
                env->ReleasePrimitiveArrayCritical(userData.jResizedBuf, userData.resizedBuf, 0);
            } else {
                size = strlen(buf);
            }

            env->SetIntField(textInputData, inputDataSizeID, size);
            env->SetBooleanField(textInputData, inputDataIsDirtyID, true);
        }

        return valueChanged;
    */

    public static boolean inputFloat(String label, ImFloat v) {
        return nInputFloat(label, v.getData(), 0, 0, "%.3f", ImGuiInputTextFlags.None);
    }

    public static boolean inputFloat(String label, ImFloat v, float step) {
        return nInputFloat(label, v.getData(), step, 0, "%.3f", ImGuiInputTextFlags.None);
    }

    public static boolean inputFloat(String label, ImFloat v, float step, float stepFast) {
        return nInputFloat(label, v.getData(), step, stepFast, "%.3f", ImGuiInputTextFlags.None);
    }

    public static boolean inputFloat(String label, ImFloat v, float step, float stepFast, String format) {
        return nInputFloat(label, v.getData(), step, stepFast, format, ImGuiInputTextFlags.None);
    }

    public static boolean inputFloat(String label, ImFloat v, float step, float stepFast, String format, int imGuiInputTextFlags) {
        return nInputFloat(label, v.getData(), step, stepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputFloat(String label, float[] v, float step, float stepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat(label, &v[0], step, stepFast, format, imGuiInputTextFlags);
    */

    public static native boolean inputFloat2(String label, float[] v); /*
        return ImGui::InputFloat2(label, v);
    */

    public static native boolean inputFloat2(String label, float[] v, String format); /*
        return ImGui::InputFloat2(label, v, format);
    */

    public static native boolean inputFloat2(String label, float[] v, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat2(label, v, format, imGuiInputTextFlags);
    */

    public static native boolean inputFloat3(String label, float[] v); /*
        return ImGui::InputFloat3(label, v);
    */

    public static native boolean inputFloat3(String label, float[] v, String format); /*
        return ImGui::InputFloat3(label, v, format);
    */

    public static native boolean inputFloat3(String label, float[] v, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat3(label, v, format, imGuiInputTextFlags);
    */

    public static native boolean inputFloat4(String label, float[] v); /*
        return ImGui::InputFloat4(label, v);
    */

    public static native boolean inputFloat4(String label, float[] v, String format); /*
        return ImGui::InputFloat4(label, v, format);
    */

    public static native boolean inputFloat4(String label, float[] v, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat4(label, v, format, imGuiInputTextFlags);
    */

    public static boolean inputInt(String label, ImInt v) {
        return nInputInt(label, v.getData(), 1, 100, ImGuiInputTextFlags.None);
    }

    public static boolean inputInt(String label, ImInt v, int step) {
        return nInputInt(label, v.getData(), step, 100, ImGuiInputTextFlags.None);
    }

    public static boolean inputInt(String label, ImInt v, int step, int stepFast) {
        return nInputInt(label, v.getData(), step, stepFast, ImGuiInputTextFlags.None);
    }

    public static boolean inputInt(String label, ImInt v, int step, int stepFast, int imGuiInputTextFlags) {
        return nInputInt(label, v.getData(), step, stepFast, imGuiInputTextFlags);
    }

    private static native boolean nInputInt(String label, int[] v, int step, int stepFast, int imGuiInputTextFlags); /*
        return ImGui::InputInt(label, &v[0], step, stepFast, imGuiInputTextFlags);
    */

    public static native boolean inputInt2(String label, int[] v); /*
        return ImGui::InputInt2(label, v);
    */

    public static native boolean inputInt2(String label, int[] v, int imGuiInputTextFlags); /*
        return ImGui::InputInt2(label, v, imGuiInputTextFlags);
    */

    public static native boolean inputInt3(String label, int[] v); /*
        return ImGui::InputInt3(label, v);
    */

    public static native boolean inputInt3(String label, int[] v, int imGuiInputTextFlags); /*
        return ImGui::InputInt3(label, v, imGuiInputTextFlags);
    */

    public static native boolean inputInt4(String label, int[] v); /*
        return ImGui::InputInt4(label, v);
    */

    public static native boolean inputInt4(String label, int[] v, int imGuiInputTextFlags); /*
        return ImGui::InputInt4(label, v, imGuiInputTextFlags);
    */

    public static boolean inputDouble(String label, ImDouble v) {
        return nInputDouble(label, v.getData(), 0, 0, "%.6f", ImGuiInputTextFlags.None);
    }

    public static boolean inputDouble(String label, ImDouble v, double step) {
        return nInputDouble(label, v.getData(), step, 0, "%.6f", ImGuiInputTextFlags.None);
    }

    public static boolean inputDouble(String label, ImDouble v, double step, double stepFast) {
        return nInputDouble(label, v.getData(), step, stepFast, "%.6f", ImGuiInputTextFlags.None);
    }

    public static boolean inputDouble(String label, ImDouble v, double step, double stepFast, String format) {
        return nInputDouble(label, v.getData(), step, stepFast, format, ImGuiInputTextFlags.None);
    }

    public static boolean inputDouble(String label, ImDouble v, double step, double stepFast, String format, int imGuiInputTextFlags) {
        return nInputDouble(label, v.getData(), step, stepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputDouble(String label, double[] v, double step, double stepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputDouble(label, &v[0], step, stepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalar(String label, int dataType, ImInt pData) {
        return nInputScalar(label, dataType, pData.getData());
    }

    private static native boolean nInputScalar(String label, int dataType, int[] pData); /*
        return ImGui::InputScalar(label, dataType, &pData[0]);
    */

    public static boolean inputScalar(String label, int dataType, ImInt pData, int pStep) {
        return nInputScalar(label, dataType, pData.getData(), pStep);
    }

    private static native boolean nInputScalar(String label, int dataType, int[] pData, int pStep); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep);
    */

    public static boolean inputScalar(String label, int dataType, ImInt pData, int pStep, int pStepFast) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast);
    }

    private static native boolean nInputScalar(String label, int dataType, int[] pData, int pStep, int pStepFast); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast);
    */

    public static boolean inputScalar(String label, int dataType, ImInt pData, int pStep, int pStepFast, String format) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format);
    }

    private static native boolean nInputScalar(String label, int dataType, int[] pData, int pStep, int pStepFast, String format); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format);
    */

    public static boolean inputScalar(String label, int dataType, ImInt pData, int pStep, int pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int dataType, int[] pData, int pStep, int pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalar(String label, int dataType, ImFloat pData) {
        return nInputScalar(label, dataType, pData.getData());
    }

    private static native boolean nInputScalar(String label, int dataType, float[] pData); /*
        return ImGui::InputScalar(label, dataType, &pData[0]);
    */

    public static boolean inputScalar(String label, int dataType, ImFloat pData, float pStep) {
        return nInputScalar(label, dataType, pData.getData(), pStep);
    }

    private static native boolean nInputScalar(String label, int dataType, float[] pData, float pStep); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep);
    */

    public static boolean inputScalar(String label, int dataType, ImFloat pData, float pStep, float pStepFast) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast);
    }

    private static native boolean nInputScalar(String label, int dataType, float[] pData, float pStep, float pStepFast); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast);
    */

    public static boolean inputScalar(String label, int dataType, ImFloat pData, float pStep, float pStepFast, String format) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format);
    }

    private static native boolean nInputScalar(String label, int dataType, float[] pData, float pStep, float pStepFast, String format); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format);
    */

    public static boolean inputScalar(String label, int dataType, ImFloat pData, float pStep, float pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int dataType, float[] pData, float pStep, float pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalar(String label, int dataType, ImLong pData) {
        return nInputScalar(label, dataType, pData.getData());
    }

    private static native boolean nInputScalar(String label, int dataType, long[] pData); /*
        return ImGui::InputScalar(label, dataType, &pData[0]);
    */

    public static boolean inputScalar(String label, int dataType, ImLong pData, long pStep) {
        return nInputScalar(label, dataType, pData.getData(), pStep);
    }

    private static native boolean nInputScalar(String label, int dataType, long[] pData, long pStep); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep);
    */

    public static boolean inputScalar(String label, int dataType, ImLong pData, long pStep, long pStepFast) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast);
    }

    private static native boolean nInputScalar(String label, int dataType, long[] pData, long pStep, long pStepFast); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast);
    */

    public static boolean inputScalar(String label, int dataType, ImLong pData, long pStep, long pStepFast, String format) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format);
    }

    private static native boolean nInputScalar(String label, int dataType, long[] pData, long pStep, long pStepFast, String format); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format);
    */

    public static boolean inputScalar(String label, int dataType, ImLong pData, long pStep, long pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int dataType, long[] pData, long pStep, long pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalar(String label, int dataType, ImDouble pData) {
        return nInputScalar(label, dataType, pData.getData());
    }

    private static native boolean nInputScalar(String label, int dataType, double[] pData); /*
        return ImGui::InputScalar(label, dataType, &pData[0]);
    */

    public static boolean inputScalar(String label, int dataType, ImDouble pData, double pStep) {
        return nInputScalar(label, dataType, pData.getData(), pStep);
    }

    private static native boolean nInputScalar(String label, int dataType, double[] pData, double pStep); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep);
    */

    public static boolean inputScalar(String label, int dataType, ImDouble pData, double pStep, double pStepFast) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast);
    }

    private static native boolean nInputScalar(String label, int dataType, double[] pData, double pStep, double pStepFast); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast);
    */

    public static boolean inputScalar(String label, int dataType, ImDouble pData, double pStep, double pStepFast, String format) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format);
    }

    private static native boolean nInputScalar(String label, int dataType, double[] pData, double pStep, double pStepFast, String format); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format);
    */

    public static boolean inputScalar(String label, int dataType, ImDouble pData, double pStep, double pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int dataType, double[] pData, double pStep, double pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalar(String label, int dataType, ImShort pData) {
        return nInputScalar(label, dataType, pData.getData());
    }

    private static native boolean nInputScalar(String label, int dataType, short[] pData); /*
        return ImGui::InputScalar(label, dataType, &pData[0]);
    */

    public static boolean inputScalar(String label, int dataType, ImShort pData, short pStep) {
        return nInputScalar(label, dataType, pData.getData(), pStep);
    }

    private static native boolean nInputScalar(String label, int dataType, short[] pData, short pStep); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep);
    */

    public static boolean inputScalar(String label, int dataType, ImShort pData, short pStep, short pStepFast) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast);
    }

    private static native boolean nInputScalar(String label, int dataType, short[] pData, short pStep, short pStepFast); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast);
    */

    public static boolean inputScalar(String label, int dataType, ImShort pData, short pStep, short pStepFast, String format) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format);
    }

    private static native boolean nInputScalar(String label, int dataType, short[] pData, short pStep, short pStepFast, String format); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format);
    */

    public static boolean inputScalar(String label, int dataType, ImShort pData, short pStep, short pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, dataType, pData.getData(), pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int dataType, short[] pData, short pStep, short pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, dataType, &pData[0], &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalarN(String label, int dataType, ImInt pData, int components) {
        return nInputScalarN(label, dataType, pData.getData(), components);
    }

    private static native boolean nInputScalarN(String label, int dataType, int[] pData, int components); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components);
    */

    public static boolean inputScalarN(String label, int dataType, ImInt pData, int components, int pStep) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep);
    }

    private static native boolean nInputScalarN(String label, int dataType, int[] pData, int components, int pStep); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
    */

    public static boolean inputScalarN(String label, int dataType, ImInt pData, int components, int pStep, int pStepFast) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast);
    }

    private static native boolean nInputScalarN(String label, int dataType, int[] pData, int components, int pStep, int pStepFast); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
    */

    public static boolean inputScalarN(String label, int dataType, ImInt pData, int components, int pStep, int pStepFast, String format) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format);
    }

    private static native boolean nInputScalarN(String label, int dataType, int[] pData, int components, int pStep, int pStepFast, String format); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
    */

    public static boolean inputScalarN(String label, int dataType, ImInt pData, int components, int pStep, int pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int dataType, int[] pData, int components, int pStep, int pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalarN(String label, int dataType, ImFloat pData, int components) {
        return nInputScalarN(label, dataType, pData.getData(), components);
    }

    private static native boolean nInputScalarN(String label, int dataType, float[] pData, int components); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components);
    */

    public static boolean inputScalarN(String label, int dataType, ImFloat pData, int components, float pStep) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep);
    }

    private static native boolean nInputScalarN(String label, int dataType, float[] pData, int components, float pStep); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
    */

    public static boolean inputScalarN(String label, int dataType, ImFloat pData, int components, float pStep, float pStepFast) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast);
    }

    private static native boolean nInputScalarN(String label, int dataType, float[] pData, int components, float pStep, float pStepFast); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
    */

    public static boolean inputScalarN(String label, int dataType, ImFloat pData, int components, float pStep, float pStepFast, String format) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format);
    }

    private static native boolean nInputScalarN(String label, int dataType, float[] pData, int components, float pStep, float pStepFast, String format); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
    */

    public static boolean inputScalarN(String label, int dataType, ImFloat pData, int components, float pStep, float pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int dataType, float[] pData, int components, float pStep, float pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalarN(String label, int dataType, ImLong pData, int components) {
        return nInputScalarN(label, dataType, pData.getData(), components);
    }

    private static native boolean nInputScalarN(String label, int dataType, long[] pData, int components); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components);
    */

    public static boolean inputScalarN(String label, int dataType, ImLong pData, int components, long pStep) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep);
    }

    private static native boolean nInputScalarN(String label, int dataType, long[] pData, int components, long pStep); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
    */

    public static boolean inputScalarN(String label, int dataType, ImLong pData, int components, long pStep, long pStepFast) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast);
    }

    private static native boolean nInputScalarN(String label, int dataType, long[] pData, int components, long pStep, long pStepFast); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
    */

    public static boolean inputScalarN(String label, int dataType, ImLong pData, int components, long pStep, long pStepFast, String format) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format);
    }

    private static native boolean nInputScalarN(String label, int dataType, long[] pData, int components, long pStep, long pStepFast, String format); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
    */

    public static boolean inputScalarN(String label, int dataType, ImLong pData, int components, long pStep, long pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int dataType, long[] pData, int components, long pStep, long pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalarN(String label, int dataType, ImDouble pData, int components) {
        return nInputScalarN(label, dataType, pData.getData(), components);
    }

    private static native boolean nInputScalarN(String label, int dataType, double[] pData, int components); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components);
    */

    public static boolean inputScalarN(String label, int dataType, ImDouble pData, int components, double pStep) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep);
    }

    private static native boolean nInputScalarN(String label, int dataType, double[] pData, int components, double pStep); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
    */

    public static boolean inputScalarN(String label, int dataType, ImDouble pData, int components, double pStep, double pStepFast) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast);
    }

    private static native boolean nInputScalarN(String label, int dataType, double[] pData, int components, double pStep, double pStepFast); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
    */

    public static boolean inputScalarN(String label, int dataType, ImDouble pData, int components, double pStep, double pStepFast, String format) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format);
    }

    private static native boolean nInputScalarN(String label, int dataType, double[] pData, int components, double pStep, double pStepFast, String format); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
    */

    public static boolean inputScalarN(String label, int dataType, ImDouble pData, int components, double pStep, double pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int dataType, double[] pData, int components, double pStep, double pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    public static boolean inputScalarN(String label, int dataType, ImShort pData, int components) {
        return nInputScalarN(label, dataType, pData.getData(), components);
    }

    private static native boolean nInputScalarN(String label, int dataType, short[] pData, int components); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components);
    */

    public static boolean inputScalarN(String label, int dataType, ImShort pData, int components, short pStep) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep);
    }

    private static native boolean nInputScalarN(String label, int dataType, short[] pData, int components, short pStep); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
    */

    public static boolean inputScalarN(String label, int dataType, ImShort pData, int components, short pStep, short pStepFast) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast);
    }

    private static native boolean nInputScalarN(String label, int dataType, short[] pData, int components, short pStep, short pStepFast); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
    */

    public static boolean inputScalarN(String label, int dataType, ImShort pData, int components, short pStep, short pStepFast, String format) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format);
    }

    private static native boolean nInputScalarN(String label, int dataType, short[] pData, int components, short pStep, short pStepFast, String format); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
    */

    public static boolean inputScalarN(String label, int dataType, ImShort pData, int components, short pStep, short pStepFast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, dataType, pData.getData(), components, pStep, pStepFast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int dataType, short[] pData, int components, short pStep, short pStepFast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiInputTextFlags);
    */

    // Widgets: Color Editor/Picker (tip: the ColorEdit* functions have a little color square that can be left-clicked to open a picker, and right-clicked to open an option menu.)
    // - Note that in C++ a 'float v[X]' function argument is the _same_ as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible.
    // - You can pass the address of a first float element out of a contiguous structure, e.g. &myvector.x

    public static native boolean colorEdit3(String label, float[] col); /*
        return ImGui::ColorEdit3(label, col);
    */

    public static native boolean colorEdit3(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorEdit3(label, col, imGuiColorEditFlags);
    */

    public static native boolean colorEdit4(String label, float[] col); /*
        return ImGui::ColorEdit4(label, col);
    */

    public static native boolean colorEdit4(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorEdit4(label, col, imGuiColorEditFlags);
    */

    public static native boolean colorPicker3(String label, float[] col); /*
        return ImGui::ColorPicker3(label, col);
    */

    public static native boolean colorPicker3(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorPicker3(label, col, imGuiColorEditFlags);
    */

    public static native boolean colorPicker4(String label, float[] col); /*
        return ImGui::ColorPicker4(label, col);
    */

    public static native boolean colorPicker4(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorPicker4(label, col, imGuiColorEditFlags);
    */

    public static native boolean colorPicker4(String label, float[] col, int imGuiColorEditFlags, float refCol); /*
        return ImGui::ColorPicker4(label, col, imGuiColorEditFlags, &refCol);
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static native boolean colorButton(String descId, float[] col); /*
        return ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]));
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static native boolean colorButton(String descId, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]), imGuiColorEditFlags);
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static native boolean colorButton(String descId, float[] col, int imGuiColorEditFlags, float width, float height); /*
        return ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]), imGuiColorEditFlags, ImVec2(width, height));
    */

    /**
     * Initialize current options (generally on application startup) if you want to select a default format,
     * picker type, etc. User will be able to change many settings, unless you pass the _NoOptions flag to your calls.
     */
    public static native void setColorEditOptions(int imGuiColorEditFlags); /*
        ImGui::SetColorEditOptions(imGuiColorEditFlags);
    */

    // Widgets: Trees
    // - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

    public static native boolean treeNode(String label); /*
        return ImGui::TreeNode(label);
    */

    /**
     * Helper variation to easily decorelate the id from the displayed string.
     * Read the FAQ about why and how to use ID. to align arbitrary text at the same level as a TreeNode() you can use Bullet().
     */
    public static native boolean treeNode(String strId, String label); /*
        return ImGui::TreeNode(strId, label, NULL);
    */

    public static native boolean treeNode(long ptrId, String label); /*
        return ImGui::TreeNode((void*)ptrId, label, NULL);
    */

    public static native boolean treeNodeEx(String label); /*
        return ImGui::TreeNodeEx(label);
    */

    public static native boolean treeNodeEx(String label, int imGuiTreeNodeFlags); /*
        return ImGui::TreeNodeEx(label, imGuiTreeNodeFlags);
    */

    public static native boolean treeNodeEx(String strId, int imGuiTreeNodeFlags, String label); /*
        return ImGui::TreeNodeEx(strId, imGuiTreeNodeFlags, label, NULL);
    */

    public static native boolean treeNodeEx(long ptrId, int imGuiTreeNodeFlags, String label); /*
        return ImGui::TreeNodeEx((void*)ptrId, imGuiTreeNodeFlags, label, NULL);
    */

    /**
     * ~ Indent()+PushId(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    public static native void treePush(); /*
        ImGui::TreePush();
    */

    public static native void treePush(String strId); /*
        ImGui::TreePush(strId);
    */

    public static native void treePush(long ptrId); /*
        ImGui::TreePush((void*)ptrId);
    */

    /**
     * ~ Unindent()+PopId()
     */
    public static native void treePop(); /*
        ImGui::TreePop();
    */

    /**
     * Horizontal distance preceding label when using TreeNode*() or Bullet() == (g.FontSize + style.FramePadding.x*2) for a regular unframed TreeNode
     */
    public static native float getTreeNodeToLabelSpacing(); /*
        return ImGui::GetTreeNodeToLabelSpacing();
    */

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    public static native boolean collapsingHeader(String label); /*
        return ImGui::CollapsingHeader(label);
    */

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    public static native boolean collapsingHeader(String label, int imGuiTreeNodeFlags); /*
        return ImGui::CollapsingHeader(label, imGuiTreeNodeFlags);
    */

    /**
     * When 'pVisible' isn't NULL, display an additional small close button on upper right of the header
     * which will set the bool to false when clicked, if '*pVisible==false' don't display the header.
     */
    public static boolean collapsingHeader(String label, ImBoolean pVisible) {
        return nCollapsingHeader(label, pVisible.getData(), 0);
    }

    /**
     * When 'pVisible' isn't NULL, display an additional small close button on upper right of the header
     * which will set the bool to false when clicked, if '*pVisible==false' don't display the header.
     */
    public static boolean collapsingHeader(String label, ImBoolean pVisible, int imGuiTreeNodeFlags) {
        return nCollapsingHeader(label, pVisible.getData(), imGuiTreeNodeFlags);
    }

    private static native boolean nCollapsingHeader(String label, boolean[] pVisible, int imGuiTreeNodeFlags); /*
        return ImGui::CollapsingHeader(label, &pVisible[0], imGuiTreeNodeFlags);
    */

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    public static native void setNextItemOpen(boolean isOpen); /*
        ImGui::SetNextItemOpen(isOpen);
    */

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    public static native void setNextItemOpen(boolean isOpen, int cond); /*
        ImGui::SetNextItemOpen(isOpen, cond);
    */

    // Widgets: Selectables
    // - A selectable highlights when hovered, and can display another color when selected.
    // - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

    public static native boolean selectable(String label); /*
        return ImGui::Selectable(label);
    */

    public static native boolean selectable(String label, boolean selected); /*
        return ImGui::Selectable(label, selected);
    */

    public static native boolean selectable(String label, boolean selected, int imGuiSelectableFlags); /*
        return ImGui::Selectable(label, selected, imGuiSelectableFlags);
    */

    public static native boolean selectable(String label, boolean selected, int imGuiSelectableFlags, float sizeX, float sizeY); /*
        return ImGui::Selectable(label, selected, imGuiSelectableFlags, ImVec2(sizeX, sizeY));
    */

    public static boolean selectable(String label, ImBoolean selected) {
        return nSelectable(label, selected.getData(), 0, 0, 0);
    }

    public static boolean selectable(String label, ImBoolean selected, int imGuiSelectableFlags) {
        return nSelectable(label, selected.getData(), imGuiSelectableFlags, 0, 0);
    }

    public static boolean selectable(String label, ImBoolean selected, int imGuiSelectableFlags, float sizeX, float sizeY) {
        return nSelectable(label, selected.getData(), imGuiSelectableFlags, sizeX, sizeY);
    }

    private static native boolean nSelectable(String label, boolean[] selected, int imGuiSelectableFlags, float sizeX, float sizeY); /*
        return ImGui::Selectable(label,  &selected[0], imGuiSelectableFlags, ImVec2(sizeX, sizeY));
    */

    // Widgets: List Boxes
    // - This is essentially a thin wrapper to using BeginChild/EndChild with some stylistic changes.
    // - The BeginListBox()/EndListBox() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() or any items.
    // - The simplified/old ListBox() api are helpers over BeginListBox()/EndListBox() which are kept available for convenience purpose. This is analoguous to how Combos are created.
    // - Choose frame width:   size.x > 0.0f: custom  /  size.x < 0.0f or -FLT_MIN: right-align   /  size.x = 0.0f (default): use current ItemWidth
    // - Choose frame height:  size.y > 0.0f: custom  /  size.y < 0.0f or -FLT_MIN: bottom-align  /  size.y = 0.0f (default): arbitrary default height which can fit ~7 items

    /**
     * Open a framed scrolling region.
     */
    public static native boolean beginListBox(String label); /*
        return ImGui::BeginListBox(label);
    */

    /**
     * Open a framed scrolling region.
     */
    public static native boolean beginListBox(String label, float sizeX, float sizeY); /*
        return ImGui::BeginListBox(label, ImVec2(sizeX, sizeY));
    */

    /**
     * Only call EndListBox() if BeginListBox() returned true!
     */
    public static native void endListBox(); /*
        ImGui::EndListBox();
    */

    public static void listBox(String label, ImInt currentItem, String[] items) {
        nListBox(label, currentItem.getData(), items, items.length, -1);
    }

    public static void listBox(String label, ImInt currentItem, String[] items, int heightInItems) {
        nListBox(label, currentItem.getData(), items, items.length, heightInItems);
    }

    private static native boolean nListBox(String label, int[] currentItem, String[] items, int itemsCount, int heightInItems); /*
        const char* listboxItems[itemsCount];

        for (int i = 0; i < itemsCount; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(items, i);
            const char* rawString = env->GetStringUTFChars(string, JNI_FALSE);
            listboxItems[i] = rawString;
        }

        bool flag = ImGui::ListBox(label, &currentItem[0], listboxItems, itemsCount, heightInItems);

        for (int i = 0; i< itemsCount; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(items, i);
            env->ReleaseStringUTFChars(string, listboxItems[i]);
        }

        return flag;
    */

    // Widgets: Data Plotting
    // - Consider using ImPlot (https://github.com/epezent/implot)

    public static native void plotLines(String label, float[] values, int valuesCount); /*
        ImGui::PlotLines(label, &values[0], valuesCount);
    */

    public static native void plotLines(String label, float[] values, int valuesCount, int valuesOffset); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset);
    */

    public static native void plotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText);
    */

    public static native void plotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin);
    */

    public static native void plotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    */

    public static native void plotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphWidth, float graphHeight); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphWidth, graphHeight));
    */

    public static native void plotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphWidth, float graphHeight, int stride); /*
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphWidth, graphHeight), stride);
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount);
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset);
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText);
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin);
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphWidth, float graphHeight); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphWidth, graphHeight));
    */

    public static native void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphWidth, float graphHeight, int stride); /*
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(graphWidth, graphHeight), stride);
    */

    // Widgets: Value() Helpers.
    // - Those are merely shortcut to calling Text() with a format string. Output single value in "name: value" format (tip: freely declare more in your code to handle your types. you can add functions to the ImGui namespace)

    public static native void value(String prefix, boolean b); /*
        ImGui::Value(prefix, b);
    */

    public static native void value(String prefix, int v); /*
        ImGui::Value(prefix, (int)v);
    */

    public static native void value(String prefix, long v); /*
        ImGui::Value(prefix, (unsigned int)v);
    */

    public static native void value(String prefix, float f); /*
        ImGui::Value(prefix, f);
    */

    public static native void value(String prefix, float f, String floatFormat); /*
        ImGui::Value(prefix, f, floatFormat);
    */

    // Widgets: Menus
    // - Use BeginMenuBar() on a window ImGuiWindowFlags_MenuBar to append to its menu bar.
    // - Use BeginMainMenuBar() to create a menu bar at the top of the screen and append to it.
    // - Use BeginMenu() to create a menu. You can call BeginMenu() multiple time with the same identifier to append more items to it.

    /**
     * Append to menu-bar of current window (requires ImGuiWindowFlags_MenuBar flag set on parent window).
     */
    public static native boolean beginMenuBar(); /*
        return ImGui::BeginMenuBar();
    */

    /**
     * Only call EndMenuBar() if BeginMenuBar() returns true!
     */
    public static native void endMenuBar(); /*
        ImGui::EndMenuBar();
    */

    /**
     * Create and append to a full screen menu-bar.
     */
    public static native boolean beginMainMenuBar(); /*
        return ImGui::BeginMainMenuBar();
    */

    /**
     * Only call EndMainMenuBar() if BeginMainMenuBar() returns true!
     */
    public static native void endMainMenuBar(); /*
        ImGui::EndMainMenuBar();
    */

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    public static native boolean beginMenu(String label); /*
        return ImGui::BeginMenu(label);
    */

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    public static native boolean beginMenu(String label, boolean enabled); /*
        return ImGui::BeginMenu(label, enabled);
    */

    /**
     * Only call EndMenu() if BeginMenu() returns true!
     */
    public static native void endMenu(); /*
        ImGui::EndMenu();
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static native boolean menuItem(String label); /*
        return ImGui::MenuItem(label);
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(String label, String shortcut) {
        return nMenuItem(label, shortcut, false, true);
    }

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(String label, String shortcut, boolean selected) {
        return nMenuItem(label, shortcut, selected, true);
    }

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(String label, String shortcut, boolean selected, boolean enabled) {
        return nMenuItem(label, shortcut, selected, enabled);
    }

    /**
     * Return true when activated + toggle (*pSelected) if pSelected != NULL
     */
    public static boolean menuItem(String label, String shortcut, ImBoolean pSelected) {
        return nMenuItem(label, shortcut, pSelected.getData(), true);
    }

    /**
     * Return true when activated + toggle (*pSelected) if pSelected != NULL
     */
    public static boolean menuItem(String label, String shortcut, ImBoolean pSelected, boolean enabled) {
        return nMenuItem(label, shortcut, pSelected.getData(), enabled);
    }

    /**
     * Return true when activated
     */
    private static native boolean nMenuItem(String labelObj, String shortcutObj, boolean selected, boolean enabled); /*MANUAL
        char* label = (char*)env->GetStringUTFChars(labelObj, JNI_FALSE);
        char* shortcut = NULL;
        if (shortcutObj != NULL)
            shortcut = (char*)env->GetStringUTFChars(shortcutObj, JNI_FALSE);

        jboolean result = ImGui::MenuItem(label, shortcut, selected, enabled);

        if (shortcutObj != NULL)
            env->ReleaseStringUTFChars(shortcutObj, shortcut);
        env->ReleaseStringUTFChars(labelObj, label);

        return result;
    */

    /**
     * Return true when activated + toggle (*pSelected) if pSelected != NULL
     */
    private static native boolean nMenuItem(String labelObj, String shortcutObj, boolean[] pSelectedObj, boolean enabled); /*MANUAL
        char* label = (char*)env->GetStringUTFChars(labelObj, JNI_FALSE);
        char* shortcut = NULL;
        if (shortcutObj != NULL)
            shortcut = (char*)env->GetStringUTFChars(shortcutObj, JNI_FALSE);
        bool* pSelected = (bool*)env->GetPrimitiveArrayCritical(pSelectedObj, JNI_FALSE);

        jboolean result = ImGui::MenuItem(label, shortcut, &pSelected[0], enabled);

        env->ReleasePrimitiveArrayCritical(pSelectedObj, pSelected, 0);
        if (shortcutObj != NULL)
            env->ReleaseStringUTFChars(shortcutObj, shortcut);
        env->ReleaseStringUTFChars(labelObj, label);

        return result;
    */

    // Tooltips
    // - Tooltip are windows following the mouse. They do not take focus away.

    /**
     * Begin/append a tooltip window. to create full-featured tooltip (with any kind of items).
     */
    public static native void beginTooltip(); /*
        ImGui::BeginTooltip();
    */

    public static native void endTooltip(); /*
        ImGui::EndTooltip();
    */

    /**
     * Set a text-only tooltip, typically use with ImGui::IsItemHovered(). override any previous call to SetTooltip().
     */
    public static native void setTooltip(String text); /*
        ImGui::SetTooltip(text, NULL);
    */

    // Popups, Modals
    //  - They block normal mouse hovering detection (and therefore most mouse interactions) behind them.
    //  - If not modal: they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    //  - Their visibility state (~bool) is held internally instead of being held by the programmer as we are used to with regular Begin*() calls.
    //  - The 3 properties above are related: we need to retain popup visibility state in the library because popups may be closed as any time.
    //  - You can bypass the hovering restriction by using ImGuiHoveredFlags_AllowWhenBlockedByPopup when calling IsItemHovered() or IsWindowHovered().
    //  - IMPORTANT: Popup identifiers are relative to the current ID stack, so OpenPopup and BeginPopup generally needs to be at the same level of the stack.
    //    This is sometimes leading to confusing mistakes. May rework this in the future.
    // Popups: begin/end functions
    //  - BeginPopup(): query popup state, if open start appending into the window. Call EndPopup() afterwards. ImGuiWindowFlags are forwarded to the window.
    //  - BeginPopupModal(): block every interactions behind the window, cannot be closed by user, add a dimming background, has a title bar.

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static native boolean beginPopup(String strId); /*
        return ImGui::BeginPopup(strId);
    */

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static native boolean beginPopup(String strId, int imGuiWindowFlags); /*
        return ImGui::BeginPopup(strId, imGuiWindowFlags);
    */

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static native boolean beginPopupModal(String name); /*
        return ImGui::BeginPopupModal(name);
    */

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(String name, ImBoolean pOpen) {
        return nBeginPopupModal(name, pOpen.getData(), 0);
    }

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(String name, int imGuiWindowFlags) {
        return nBeginPopupModal(name, imGuiWindowFlags);
    }

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(String name, ImBoolean pOpen, int imGuiWindowFlags) {
        return nBeginPopupModal(name, pOpen.getData(), imGuiWindowFlags);
    }

    private static native boolean nBeginPopupModal(String name, int imGuiWindowFlags); /*
        return ImGui::BeginPopupModal(name, NULL, imGuiWindowFlags);
    */

    private static native boolean nBeginPopupModal(String name, boolean[] pOpen, int imGuiWindowFlags); /*
        return ImGui::BeginPopupModal(name, &pOpen[0], imGuiWindowFlags);
    */

    /**
     * Only call EndPopup() if BeginPopupXXX() returns true!
     */
    public static native void endPopup(); /*
        ImGui::EndPopup();
    */

    // Popups: open/close functions
    //  - OpenPopup(): set popup state to open. ImGuiPopupFlags are available for opening options.
    //  - If not modal: they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    //  - CloseCurrentPopup(): use inside the BeginPopup()/EndPopup() scope to close manually.
    //  - CloseCurrentPopup() is called by default by Selectable()/MenuItem() when activated (FIXME: need some options).
    //  - Use ImGuiPopupFlags_NoOpenOverExistingPopup to avoid opening a popup if there's already one at the same level. This is equivalent to e.g. testing for !IsAnyPopupOpen() prior to OpenPopup().

    /**
     * Call to mark popup as open (don't call every frame!).
     */
    public static native void openPopup(String strId); /*
        ImGui::OpenPopup(strId);
    */

    /**
     * Call to mark popup as open (don't call every frame!).
     */
    public static native void openPopup(String strId, int imGuiPopupFlags); /*
        ImGui::OpenPopup(strId, imGuiPopupFlags);
    */

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static native void openPopupOnItemClick(); /*
        ImGui::OpenPopupOnItemClick();
    */

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static native void openPopupOnItemClick(String strId); /*
        ImGui::OpenPopupOnItemClick(strId);
    */

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static native void openPopupOnItemClick(int imGuiPopupFlags); /*
        ImGui::OpenPopupOnItemClick(NULL, imGuiPopupFlags);
    */

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static native void openPopupOnItemClick(String strId, int imGuiPopupFlags); /*
        ImGui::OpenPopupOnItemClick(strId, imGuiPopupFlags);
    */

    /**
     * Manually close the popup we have begin-ed into.
     */
    public static native void closeCurrentPopup(); /*
        ImGui::CloseCurrentPopup();
    */

    // Popups: open+begin combined functions helpers
    //  - Helpers to do OpenPopup+BeginPopup where the Open action is triggered by e.g. hovering an item and right-clicking.
    //  - They are convenient to easily create context menus, hence the name.
    //  - IMPORTANT: Notice that BeginPopupContextXXX takes ImGuiPopupFlags just like OpenPopup() and unlike BeginPopup(). For full consistency, we may add ImGuiWindowFlags to the BeginPopupContextXXX functions in the future.
    //  - IMPORTANT: we exceptionally default their flags to 1 (== ImGuiPopupFlags_MouseButtonRight) for backward compatibility with older API taking 'int mouse_button = 1' parameter, so if you add other flags remember to re-add the ImGuiPopupFlags_MouseButtonRight.

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean beginPopupContextItem(); /*
        return ImGui::BeginPopupContextItem();
    */

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean beginPopupContextItem(String strId); /*
        return ImGui::BeginPopupContextItem(strId);
    */

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean beginPopupContextItem(int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextItem(NULL, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean beginPopupContextItem(String strId, int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextItem(strId, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked on current window.
     */
    public static native boolean beginPopupContextWindow(); /*
        return ImGui::BeginPopupContextWindow();
    */

    /**
     * Open+begin popup when clicked on current window.
     */
    public static native boolean beginPopupContextWindow(String strId); /*
        return ImGui::BeginPopupContextWindow(strId);
    */

    /**
     * Open+begin popup when clicked on current window.
     */
    public static native boolean beginPopupContextWindow(int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextWindow(NULL, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked on current window.
     */
    public static native boolean beginPopupContextWindow(String strId, int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextWindow(strId, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static native boolean beginPopupContextVoid(); /*
        return ImGui::BeginPopupContextVoid();
     */

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static native boolean beginPopupContextVoid(String strId); /*
        return ImGui::BeginPopupContextVoid(strId);
    */

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static native boolean beginPopupContextVoid(int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextVoid(NULL, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static native boolean beginPopupContextVoid(String strId, int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextVoid(strId, imGuiPopupFlags);
    */

    // Popups: test function
    //  - IsPopupOpen(): return true if the popup is open at the current BeginPopup() level of the popup stack.
    //  - IsPopupOpen() with ImGuiPopupFlags_AnyPopupId: return true if any popup is open at the current BeginPopup() level of the popup stack.
    //  - IsPopupOpen() with ImGuiPopupFlags_AnyPopupId + ImGuiPopupFlags_AnyPopupLevel: return true if any popup is open.

    /**
     * Return true if the popup is open.
     */
    public static native boolean isPopupOpen(String strId); /*
        return ImGui::IsPopupOpen(strId);
    */

    /**
     * Return true if the popup is open.
     */
    public static native boolean isPopupOpen(String strId, int imGuiPopupFlags); /*
        return ImGui::IsPopupOpen(strId, imGuiPopupFlags);
    */

    // Tables
    // [BETA API] API may evolve slightly! If you use this, please update to the next version when it comes out!
    // - Full-featured replacement for old Columns API.
    // - See Demo->Tables for demo code.
    // - See top of imgui_tables.cpp for general commentary.
    // - See ImGuiTableFlags_ and ImGuiTableColumnFlags_ enums for a description of available flags.
    // The typical call flow is:
    // - 1. Call BeginTable().
    // - 2. Optionally call TableSetupColumn() to submit column name/flags/defaults.
    // - 3. Optionally call TableSetupScrollFreeze() to request scroll freezing of columns/rows.
    // - 4. Optionally call TableHeadersRow() to submit a header row. Names are pulled from TableSetupColumn() data.
    // - 5. Populate contents:
    //    - In most situations you can use TableNextRow() + TableSetColumnIndex(N) to start appending into a column.
    //    - If you are using tables as a sort of grid, where every columns is holding the same type of contents,
    //      you may prefer using TableNextColumn() instead of TableNextRow() + TableSetColumnIndex().
    //      TableNextColumn() will automatically wrap-around into the next row if needed.
    //    - IMPORTANT: Comparatively to the old Columns() API, we need to call TableNextColumn() for the first column!
    //    - Summary of possible call flow:
    //        --------------------------------------------------------------------------------------------------------
    //        TableNextRow() -> TableSetColumnIndex(0) -> Text("Hello 0") -> TableSetColumnIndex(1) -> Text("Hello 1")  // OK
    //        TableNextRow() -> TableNextColumn()      -> Text("Hello 0") -> TableNextColumn()      -> Text("Hello 1")  // OK
    //                          TableNextColumn()      -> Text("Hello 0") -> TableNextColumn()      -> Text("Hello 1")  // OK: TableNextColumn() automatically gets to next row!
    //        TableNextRow()                           -> Text("Hello 0")                                               // Not OK! Missing TableSetColumnIndex() or TableNextColumn()! Text will not appear!
    //        --------------------------------------------------------------------------------------------------------
    // - 5. Call EndTable()

    public static native boolean beginTable(String id, int column); /*
        return ImGui::BeginTable(id, column);
    */

    public static native boolean beginTable(String id, int column, int imGuiTableFlags); /*
        return ImGui::BeginTable(id, column, imGuiTableFlags);
    */

    public static native boolean beginTable(String id, int column, int imGuiTableFlags, float outerSizeX, float outerSizeY); /*
        return ImGui::BeginTable(id, column, imGuiTableFlags, ImVec2(outerSizeX, outerSizeY));
    */

    public static native boolean beginTable(String id, int column, int imGuiTableFlags, float outerSizeX, float outerSizeY, float innerWidth); /*
        return ImGui::BeginTable(id, column, imGuiTableFlags, ImVec2(outerSizeX, outerSizeY), innerWidth);
    */

    /**
     * Only call EndTable() if BeginTable() returns true!
     */
    public static native void endTable(); /*
        ImGui::EndTable();
    */

    /**
     * Append into the first cell of a new row.
     */
    public static native void tableNextRow(); /*
        ImGui::TableNextRow();
    */

    /**
     * Append into the first cell of a new row.
     */
    public static native void tableNextRow(int imGuiTableRowFlags); /*
        ImGui::TableNextRow(imGuiTableRowFlags);
    */

    /**
     * Append into the first cell of a new row.
     */
    public static native void tableNextRow(int imGuiTableRowFlags, float minRowHeight); /*
        ImGui::TableNextRow(imGuiTableRowFlags, minRowHeight);
    */

    /**
     * Append into the next column (or first column of next row if currently in last column). Return true when column is visible.
     */
    public static native boolean tableNextColumn(); /*
        return ImGui::TableNextColumn();
    */

    /**
     * Append into the specified column. Return true when column is visible.
     */
    public static native boolean tableSetColumnIndex(int columnN); /*
        return ImGui::TableSetColumnIndex(columnN);
    */

    // Tables: Headers & Columns declaration
    // - Use TableSetupColumn() to specify label, resizing policy, default width/weight, id, various other flags etc.
    // - Use TableHeadersRow() to create a header row and automatically submit a TableHeader() for each column.
    //   Headers are required to perform: reordering, sorting, and opening the context menu.
    //   The context menu can also be made available in columns body using ImGuiTableFlags_ContextMenuInBody.
    // - You may manually submit headers using TableNextRow() + TableHeader() calls, but this is only useful in
    //   some advanced use cases (e.g. adding custom widgets in header row).
    // - Use TableSetupScrollFreeze() to lock columns/rows so they stay visible when scrolled.

    public static native void tableSetupColumn(String label); /*
        ImGui::TableSetupColumn(label);
    */

    public static native void tableSetupColumn(String label, int imGuiTableColumnFlags); /*
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags);
    */

    public static native void tableSetupColumn(String label, int imGuiTableColumnFlags, float initWidthOrWeight); /*
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight);
    */

    public static native void tableSetupColumn(String label, int imGuiTableColumnFlags, float initWidthOrWeight, int userId); /*
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight, userId);
    */

    /**
     * Lock columns/rows so they stay visible when scrolled.
     */
    public static native void tableSetupScrollFreeze(int cols, int rows); /*
        ImGui::TableSetupScrollFreeze(cols, rows);
    */

    /**
     * Submit all headers cells based on data provided to TableSetupColumn() + submit context menu
     */
    public static native void tableHeadersRow(); /*
        ImGui::TableHeadersRow();
    */

    /**
     * Submit one header cell manually (rarely used)
     */
    public static native void tableHeader(String label); /*
        ImGui::TableHeader(label);
    */

    // Tables: Sorting
    // - Call TableGetSortSpecs() to retrieve latest sort specs for the table. NULL when not sorting.
    // - When 'SpecsDirty == true' you should sort your data. It will be true when sorting specs have changed
    //   since last call, or the first time. Make sure to set 'SpecsDirty = false' after sorting, else you may
    //   wastefully sort your data every frame!
    // - Lifetime: don't hold on this pointer over multiple frames or past any subsequent call to BeginTable().

    // TODO: TableGetSortSpecs()

    // Tables: Miscellaneous functions
    // - Functions args 'int column_n' treat the default value of -1 as the same as passing the current column index.

    /**
     * Return number of columns (value passed to BeginTable).
     */
    public static native int tableGetColumnCount(); /*
        return ImGui::TableGetColumnCount();
    */

    /**
     * Return current column index.
     */
    public static native int tableGetColumnIndex(); /*
        return ImGui::TableGetColumnIndex();
    */

    /**
     * Return current row index.
     */
    public static native int tableGetRowIndex(); /*
        return ImGui::TableGetRowIndex();
    */

    /**
     * Return "" if column didn't have a name declared by TableSetupColumn(). Pass -1 to use current column.
     */
    public static native String tableGetColumnName(); /*
        return env->NewStringUTF(ImGui::TableGetColumnName());
    */

    /**
     * Return "" if column didn't have a name declared by TableSetupColumn(). Pass -1 to use current column.
     */
    public static native String tableGetColumnName(int columnN); /*
        return env->NewStringUTF(ImGui::TableGetColumnName(columnN));
    */

    /**
     * Return column flags so you can query their Enabled/Visible/Sorted/Hovered status flags. Pass -1 to use current column.
     */
    public static native int tableGetColumnFlags(); /*
        return ImGui::TableGetColumnFlags();
    */

    /**
     * Return column flags so you can query their Enabled/Visible/Sorted/Hovered status flags. Pass -1 to use current column.
     */
    public static native int tableGetColumnFlags(int columnN); /*
        return ImGui::TableGetColumnFlags(columnN);
    */

    /**
     * Change the color of a cell, row, or column. See ImGuiTableBgTarget_ flags for details.
     */
    public static native void tableSetBgColor(int imGuiTableBgTarget, int color); /*
        ImGui::TableSetBgColor(imGuiTableBgTarget, color);
    */

    /**
     * Change the color of a cell, row, or column. See ImGuiTableBgTarget_ flags for details.
     */
    public static native void tableSetBgColor(int imGuiTableBgTarget, int color, int columnN); /*
        ImGui::TableSetBgColor(imGuiTableBgTarget, color, columnN);
    */

    // Legacy Columns API (2020: prefer using Tables!)
    // Columns
    // - You can also use SameLine(posX) to mimic simplified columns.

    public static native void columns(); /*
        ImGui::Columns();
    */

    public static native void columns(int count); /*
        ImGui::Columns(count);
    */

    public static native void columns(int count, String id); /*
        ImGui::Columns(count, id);
    */

    public static native void columns(int count, String id, boolean border); /*
        ImGui::Columns(count, id, border);
    */

    /**
     * Next column, defaults to current row or next row if the current row is finished
     */
    public static native void nextColumn(); /*
        ImGui::NextColumn();
    */

    /**
     * Get current column index
     */
    public static native int getColumnIndex(); /*
        return ImGui::GetColumnIndex();
     */

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    public static native float getColumnWidth(); /*
        return ImGui::GetColumnWidth();
    */

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    public static native float getColumnWidth(int columnIndex); /*
        return ImGui::GetColumnWidth(columnIndex);
    */

    /**
     * Set column width (in pixels). pass -1 to use current column
     */
    public static native void setColumnWidth(int columnIndex, float width); /*
        ImGui::SetColumnWidth(columnIndex, width);
    */

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    public static native float getColumnOffset(); /*
        return ImGui::GetColumnOffset();
    */

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    public static native float getColumnOffset(int columnIndex); /*
        return ImGui::GetColumnOffset(columnIndex);
    */

    /**
     * Set position of column line (in pixels, from the left side of the contents region). pass -1 to use current column
     */
    public static native void setColumnOffset(int columnIndex, float offsetX); /*
        ImGui::SetColumnOffset(columnIndex, offsetX);
    */

    public static native int getColumnsCount(); /*
        return ImGui::GetColumnsCount();
    */

    // Tab Bars, Tabs
    // Note: Tabs are automatically created by the docking system. Use this to create tab bars/tabs yourself without docking being involved.

    /**
     * Create and append into a TabBar
     */
    public static native boolean beginTabBar(String strId); /*
        return ImGui::BeginTabBar(strId);
    */

    /**
     * Create and append into a TabBar
     */
    public static native boolean beginTabBar(String strId, int imGuiTabBarFlags); /*
        return ImGui::BeginTabBar(strId, imGuiTabBarFlags);
    */

    /**
     * Only call EndTabBar() if BeginTabBar() returns true!
     */
    public static native void endTabBar(); /*
        ImGui::EndTabBar();
    */

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static native boolean beginTabItem(String label); /*
        return ImGui::BeginTabItem(label);
    */

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(String label, ImBoolean pOpen) {
        return nBeginTabItem(label, pOpen.getData(), 0);
    }

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(String label, int imGuiTabItemFlags) {
        return nBeginTabItem(label, imGuiTabItemFlags);
    }

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(String label, ImBoolean pOpen, int imGuiTabItemFlags) {
        return nBeginTabItem(label, pOpen.getData(), imGuiTabItemFlags);
    }

    private static native boolean nBeginTabItem(String label, int imGuiTabItemFlags); /*
        return ImGui::BeginTabItem(label, NULL, imGuiTabItemFlags);
    */

    private static native boolean nBeginTabItem(String label, boolean[] pOpen, int imGuiTabItemFlags); /*
        return ImGui::BeginTabItem(label, &pOpen[0], imGuiTabItemFlags);
    */

    /**
     * Only call EndTabItem() if BeginTabItem() returns true!
     */
    public static native void endTabItem(); /*
        ImGui::EndTabItem();
    */

    /**
     * Create a Tab behaving like a button. return true when clicked. cannot be selected in the tab bar.
     */
    public static native boolean tabItemButton(String label); /*
        return ImGui::TabItemButton(label);
    */

    /**
     * Create a Tab behaving like a button. return true when clicked. cannot be selected in the tab bar.
     */
    public static native boolean tabItemButton(String label, int imGuiTabItemFlags); /*
        return ImGui::TabItemButton(label, imGuiTabItemFlags);
    */

    /**
     * Notify TabBar or Docking system of a closed tab/window ahead (useful to reduce visual flicker on reorderable tab bars).
     * For tab-bar: call after BeginTabBar() and before Tab submissions. Otherwise call with a window name.
     */
    public static native void setTabItemClosed(String tabOrDockedWindowLabel); /*
        ImGui::SetTabItemClosed(tabOrDockedWindowLabel);
    */

    // Docking
    // [BETA API] Enable with io.ConfigFlags |= ImGuiConfigFlags_DockingEnable.
    // Note: You can use most Docking facilities without calling any API. You DO NOT need to call DockSpace() to use Docking!
    // - To dock windows: if io.ConfigDockingWithShift == false (default) drag window from their title bar.
    // - To dock windows: if io.ConfigDockingWithShift == true: hold SHIFT anywhere while moving windows.
    // About DockSpace:
    // - Use DockSpace() to create an explicit dock node _within_ an existing window. See Docking demo for details.
    // - DockSpace() needs to be submitted _before_ any window they can host. If you use a dockspace, submit it early in your app.

    public static int dockSpace(int imGuiID) {
        return nDockSpace(imGuiID, 0, 0, 0, 0);
    }

    public static int dockSpace(int imGuiID, float sizeX, float sizeY) {
        return nDockSpace(imGuiID, sizeX, sizeY, 0, 0);
    }

    public static int dockSpace(int imGuiID, float sizeX, float sizeY, int imGuiDockNodeFlags) {
        return nDockSpace(imGuiID, sizeX, sizeY, imGuiDockNodeFlags, 0);
    }

    public static int dockSpace(int imGuiID, float sizeX, float sizeY, int imGuiDockNodeFlags, ImGuiWindowClass imGuiWindowClass) {
        return nDockSpace(imGuiID, sizeX, sizeY, imGuiDockNodeFlags, imGuiWindowClass.ptr);
    }

    private static native int nDockSpace(int imGuiID, float sizeX, float sizeY, int imGuiDockNodeFlags, long windowClassPtr); /*
        return ImGui::DockSpace(imGuiID, ImVec2(sizeX, sizeY), imGuiDockNodeFlags, windowClassPtr != 0 ? (ImGuiWindowClass*)windowClassPtr : NULL);
    */

    public static int dockSpaceOverViewport() {
        return nDockSpaceOverViewport(0, 0, 0);
    }

    public static int dockSpaceOverViewport(ImGuiViewport viewport) {
        return nDockSpaceOverViewport(viewport.ptr, 0, 0);
    }

    public static int dockSpaceOverViewport(ImGuiViewport viewport, int imGuiDockNodeFlags) {
        return nDockSpaceOverViewport(viewport.ptr, imGuiDockNodeFlags, 0);
    }

    public static int dockSpaceOverViewport(ImGuiViewport viewport, int imGuiDockNodeFlags, ImGuiWindowClass windowClass) {
        return nDockSpaceOverViewport(viewport.ptr, imGuiDockNodeFlags, windowClass.ptr);
    }

    private static native int nDockSpaceOverViewport(long viewportPtr, int imGuiDockNodeFlags, long windowClassPtr); /*
        return ImGui::DockSpaceOverViewport(viewportPtr != 0 ? (ImGuiViewport*)viewportPtr : NULL, imGuiDockNodeFlags, windowClassPtr != 0 ? (ImGuiWindowClass*)windowClassPtr : NULL);
    */

    /**
     * Set next window dock id
     */
    public static native void setNextWindowDockID(int dockId); /*
        ImGui::SetNextWindowDockID(dockId);
    */

    /**
     * Set next window dock id
     */
    public static native void setNextWindowDockID(int dockId, int imGuiCond); /*
        ImGui::SetNextWindowDockID(dockId, imGuiCond);
    */

    /**
     * set next window class (rare/advanced uses: provide hints to the platform backend via altered viewport flags and parent/child info)
     */
    public static void setNextWindowClass(ImGuiWindowClass windowClass) {
        nSetNextWindowClass(windowClass.ptr);
    }

    private static native void nSetNextWindowClass(long windowClassPtr); /*
        ImGui::SetNextWindowClass((ImGuiWindowClass*)windowClassPtr);
    */

    public static native int getWindowDockID(); /*
        return ImGui::GetWindowDockID();
    */

    /**
     * Is current window docked into another window?
     */
    public static native boolean isWindowDocked(); /*
        return ImGui::IsWindowDocked();
    */

    // Logging/Capture
    // - All text output from the interface can be captured into tty/file/clipboard. By default, tree nodes are automatically opened during logging.

    /**
     * Start logging to tty (stdout)
     */
    public static native void logToTTY(); /*
        ImGui::LogToTTY();
    */

    /**
     * Start logging to tty (stdout)
     */
    public static native void logToTTY(int autoOpenDepth); /*
        ImGui::LogToTTY(autoOpenDepth);
    */

    /**
     * Start logging to file
     */
    public static native void logToFile(); /*
        ImGui::LogToFile();
    */

    /**
     * Start logging to file
     */
    public static native void logToFile(int autoOpenDepth); /*
        ImGui::LogToFile(autoOpenDepth);
    */

    /**
     * Start logging to file
     */
    public static native void logToFile(int autoOpenDepth, String filename); /*
        ImGui::LogToFile(autoOpenDepth, filename);
    */

    /**
     * Start logging to OS clipboard
     */
    public static native void logToClipboard(); /*
        ImGui::LogToClipboard();
    */


    /**
     * Start logging to OS clipboard
     */
    public static native void logToClipboard(int autoOpenDepth); /*
        ImGui::LogToClipboard(autoOpenDepth);
    */

    /**
     * Stop logging (close file, etc.)
     */
    public static native void logFinish(); /*
        ImGui::LogFinish();
    */

    /**
     * Helper to display buttons for logging to tty/file/clipboard
     */
    public static native void logButtons(); /*
        ImGui::LogButtons();
    */

    /**
     * Pass text data straight to log (without being displayed)
     */
    public static native void logText(String text); /*
        ImGui::LogText(text, NULL);
    */

    // Drag and Drop
    // - If you stop calling BeginDragDropSource() the payload is preserved however it won't have a preview tooltip (we currently display a fallback "..." tooltip as replacement)

    private static WeakReference<Object> payloadRef = null;
    private static final byte[] PAYLOAD_PLACEHOLDER_DATA = new byte[1];

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    public static native boolean beginDragDropSource(); /*
        return ImGui::BeginDragDropSource();
    */

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    public static native boolean beginDragDropSource(int imGuiDragDropFlags); /*
        return ImGui::BeginDragDropSource(imGuiDragDropFlags);
    */

    /**
     * Type is a user defined string of maximum 32 characters. Strings starting with '_' are reserved for dear imgui internal types.
     * <p>
     * BINDING NOTICE:
     * Method adopted for Java, so objects are used instead of raw bytes.
     * Binding stores a reference to the object in a form of {@link WeakReference}.
     */
    public static boolean setDragDropPayload(final String dataType, final Object payload) {
        return setDragDropPayload(dataType, payload, ImGuiCond.None);
    }

    /**
     * Type is a user defined string of maximum 32 characters. Strings starting with '_' are reserved for dear imgui internal types.
     * <p>
     * BINDING NOTICE:
     * Method adopted for Java, so objects are used instead of raw bytes.
     * Binding stores a reference to the object in a form of {@link WeakReference}.
     */
    public static boolean setDragDropPayload(final String dataType, final Object payload, final int imGuiCond) {
        if (payloadRef == null || payloadRef.get() != payload) {
            payloadRef = new WeakReference<>(payload);
        }
        return nSetDragDropPayload(dataType, PAYLOAD_PLACEHOLDER_DATA, 1, imGuiCond);
    }

    /**
     * Binding alternative for {@link #setDragDropPayload(String, Object)}, which uses payload class as a unique identifier.
     */
    public static boolean setDragDropPayload(final Object payload) {
        return setDragDropPayload(payload, ImGuiCond.None);
    }

    /**
     * Binding alternative for {@link #setDragDropPayload(String, Object, int)}, which uses payload class as a unique identifier.
     */
    public static boolean setDragDropPayload(final Object payload, final int imGuiCond) {
        return setDragDropPayload(String.valueOf(payload.getClass().hashCode()), payload, imGuiCond);
    }

    private static native boolean nSetDragDropPayload(String dataType, byte[] data, int sz, int imGuiCond); /*
        return ImGui::SetDragDropPayload(dataType, &data[0], sz, imGuiCond);
    */

    /**
     * Only call EndDragDropSource() if BeginDragDropSource() returns true!
     */
    public static native void endDragDropSource(); /*
        ImGui::EndDragDropSource();
    */

    /**
     * Call after submitting an item that may receive a payload. If this returns true, you can call AcceptDragDropPayload() + EndDragDropTarget()
     */
    public static native boolean beginDragDropTarget(); /*
        return ImGui::BeginDragDropTarget();
    */

    /**
     * Accept contents of a given type. If ImGuiDragDropFlags_AcceptBeforeDelivery is set you can peek into the payload before the mouse button is released.
     */
    public static <T> T acceptDragDropPayload(final String dataType) {
        return acceptDragDropPayload(dataType, ImGuiDragDropFlags.None);
    }

    /**
     * Type safe alternative for {@link #acceptDragDropPayload(String)}, since it checks assignability of the accepted class.
     */
    public static <T> T acceptDragDropPayload(final String dataType, final Class<T> aClass) {
        return acceptDragDropPayload(dataType, ImGuiDragDropFlags.None, aClass);
    }

    /**
     * Accept contents of a given type. If ImGuiDragDropFlags_AcceptBeforeDelivery is set you can peek into the payload before the mouse button is released.
     */
    public static <T> T acceptDragDropPayload(final String dataType, final int imGuiDragDropFlags) {
        return acceptDragDropPayload(dataType, imGuiDragDropFlags, null);
    }

    /**
     * Type safe alternative for {@link #acceptDragDropPayload(String, int)}, since it checks assignability of the accepted class.
     */
    @SuppressWarnings("unchecked")
    public static <T> T acceptDragDropPayload(final String dataType, final int imGuiDragDropFlags, final Class<T> aClass) {
        if (payloadRef != null && nAcceptDragDropPayload(dataType, imGuiDragDropFlags)) {
            final Object rawPayload = payloadRef.get();
            if (rawPayload != null) {
                if (aClass == null || rawPayload.getClass().isAssignableFrom(aClass)) {
                    return (T) rawPayload;
                }
            }
        }
        return null;
    }

    /**
     * Binding alternative for {@link #acceptDragDropPayload(String)}, which uses payload class as a unique identifier.
     */
    public static <T> T acceptDragDropPayload(final Class<T> aClass) {
        return acceptDragDropPayload(String.valueOf(aClass.hashCode()), ImGuiDragDropFlags.None, aClass);
    }

    /**
     * Binding alternative for {@link #acceptDragDropPayload(String, int)}, which uses payload class as a unique identifier.
     */
    public static <T> T acceptDragDropPayload(final Class<T> aClass, final int imGuiDragDropFlags) {
        return acceptDragDropPayload(String.valueOf(aClass.hashCode()), imGuiDragDropFlags, aClass);
    }

    private static native boolean nAcceptDragDropPayload(String dataType, int imGuiDragDropFlags); /*
        return ImGui::AcceptDragDropPayload(dataType, imGuiDragDropFlags) != NULL;
    */

    /**
     * Only call EndDragDropTarget() if BeginDragDropTarget() returns true!
     */
    public static native void endDragDropTarget(); /*
        ImGui::EndDragDropTarget();
    */

    /**
     * Peek directly into the current payload from anywhere. May return NULL.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDragDropPayload() {
        if (payloadRef != null && nHasDragDropPayload()) {
            final Object rawPayload = payloadRef.get();
            if (rawPayload != null) {
                return (T) rawPayload;
            }
        }
        return null;
    }

    /**
     * Peek directly into the current payload from anywhere. May return NULL. Checks if payload has the same type as provided.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDragDropPayload(final String dataType) {
        if (payloadRef != null && nHasDragDropPayload(dataType)) {
            final Object rawPayload = payloadRef.get();
            if (rawPayload != null) {
                return (T) rawPayload;
            }
        }
        return null;
    }

    /**
     * Binding alternative for {@link #getDragDropPayload(String)}, which uses payload class as a unique identifier.
     */
    public static <T> T getDragDropPayload(final Class<T> aClass) {
        return getDragDropPayload(String.valueOf(aClass.hashCode()));
    }

    private static native boolean nHasDragDropPayload(); /*
        return ImGui::GetDragDropPayload()->Data != NULL;
    */

    private static native boolean nHasDragDropPayload(String dataType); /*
        return ImGui::GetDragDropPayload()->IsDataType(dataType);
    */

    /**
     * Disable all user interactions and dim items visuals (applying style.DisabledAlpha over current colors)
     * BeginDisabled(false) essentially does nothing useful but is provided to facilitate use of boolean expressions.
     * If you can avoid calling BeginDisabled(False)/EndDisabled() best to avoid it.
     */
    public static void beginDisabled() {
        beginDisabled(true);
    }

    /**
     * Disable all user interactions and dim items visuals (applying style.DisabledAlpha over current colors)
     * BeginDisabled(false) essentially does nothing useful but is provided to facilitate use of boolean expressions.
     * If you can avoid calling BeginDisabled(False)/EndDisabled() best to avoid it.
     */
    public static native void beginDisabled(boolean disabled); /*
        ImGui::BeginDisabled(disabled);
    */

    public static native void endDisabled(); /*
        ImGui::EndDisabled();
    */

    // Clipping
    // - Mouse hovering is affected by ImGui::PushClipRect() calls, unlike direct calls to ImDrawList::PushClipRect() which are render only.

    public static native void pushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY, boolean intersectWithCurrentClipRect); /*
        ImGui::PushClipRect(ImVec2(clipRectMinX, clipRectMinY), ImVec2(clipRectMaxX, clipRectMaxY), intersectWithCurrentClipRect);
    */

    public static native void popClipRect(); /*
        ImGui::PopClipRect();
    */

    // Focus, Activation
    // - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

    /**
     * Make last item the default focused item of a window.
     */
    public static native void setItemDefaultFocus(); /*
        ImGui::SetItemDefaultFocus();
    */

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    public static native void setKeyboardFocusHere(); /*
        ImGui::SetKeyboardFocusHere();
    */

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    public static native void setKeyboardFocusHere(int offset); /*
        ImGui::SetKeyboardFocusHere(offset);
    */

    // Item/Widgets Utilities
    // - Most of the functions are referring to the last/previous item we submitted.
    // - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    public static native boolean isItemHovered(); /*
        return ImGui::IsItemHovered();
    */

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    public static native boolean isItemHovered(int imGuiHoveredFlags); /*
        return ImGui::IsItemHovered(imGuiHoveredFlags);
    */

    /**
     * Is the last item active? (e.g. button being held, text field being edited.
     * This will continuously return true while holding mouse button on an item.
     * Items that don't interact will always return false)
     */
    public static native boolean isItemActive(); /*
        return ImGui::IsItemActive();
    */

    /**
     * Is the last item focused for keyboard/gamepad navigation?
     */
    public static native boolean isItemFocused(); /*
        return ImGui::IsItemFocused();
    */

    /**
     * Is the last item clicked? (e.g. button/node just clicked on) == {@code IsMouseClicked(mouseButton) && IsItemHovered()}
     */
    public static native boolean isItemClicked(); /*
        return ImGui::IsItemClicked();
    */

    /**
     * Is the last item clicked? (e.g. button/node just clicked on) == {@code IsMouseClicked(mouseButton) && IsItemHovered()}
     */
    public static native boolean isItemClicked(int mouseButton); /*
        return ImGui::IsItemClicked(mouseButton);
    */

    /**
     * Is the last item visible? (items may be out of sight because of clipping/scrolling)
     */
    public static native boolean isItemVisible(); /*
        return ImGui::IsItemVisible();
    */

    /**
     * Did the last item modify its underlying value this frame? or was pressed? This is generally the same as the "bool" return value of many widgets.
     */
    public static native boolean isItemEdited(); /*
        return ImGui::IsItemEdited();
    */

    /**
     * Was the last item just made active (item was previously inactive).
     */
    public static native boolean isItemActivated(); /*
        return ImGui::IsItemActivated();
    */

    /**
     * Was the last item just made inactive (item was previously active). Useful for Undo/Redo patterns with widgets that requires continuous editing.
     */
    public static native boolean isItemDeactivated(); /*
        return ImGui::IsItemDeactivated();
    */

    /**
     * Was the last item just made inactive and made a value change when it was active? (e.g. Slider/Drag moved).
     * Useful for Undo/Redo patterns with widgets that requires continuous editing.
     * Note that you may get false positives (some widgets such as Combo()/ListBox()/Selectable() will return true even when clicking an already selected item).
     */
    public static native boolean isItemDeactivatedAfterEdit(); /*
        return ImGui::IsItemDeactivatedAfterEdit();
    */

    /**
     * Was the last item open state toggled? set by TreeNode().
     */
    public static native boolean isItemToggledOpen(); /*
        return ImGui::IsItemToggledOpen();
    */

    /**
     * Is any item hovered?
     */
    public static native boolean isAnyItemHovered(); /*
        return ImGui::IsAnyItemHovered();
    */

    /**
     * Is any item active?
     */
    public static native boolean isAnyItemActive(); /*
        return ImGui::IsAnyItemActive();
    */

    /**
     * Is any item focused?
     */
    public static native boolean isAnyItemFocused(); /*
        return ImGui::IsAnyItemFocused();
    */

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static ImVec2 getItemRectMin() {
        final ImVec2 value = new ImVec2();
        getItemRectMin(value);
        return value;
    }

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static native void getItemRectMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectMin(), dstImVec2);
    */

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static native float getItemRectMinX(); /*
        return ImGui::GetItemRectMin().x;
    */

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static native float getItemRectMinY(); /*
        return ImGui::GetItemRectMin().y;
    */

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static ImVec2 getItemRectMax() {
        final ImVec2 value = new ImVec2();
        getItemRectMax(value);
        return value;
    }

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static native void getItemRectMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectMax(), dstImVec2);
    */

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static native float getItemRectMaxX(); /*
        return ImGui::GetItemRectMax().x;
    */

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static native float getItemRectMaxY(); /*
        return ImGui::GetItemRectMax().y;
    */

    /**
     * Get size of last item
     */
    public static ImVec2 getItemRectSize() {
        final ImVec2 value = new ImVec2();
        getItemRectSize(value);
        return value;
    }

    /**
     * Get size of last item
     */
    public static native void getItemRectSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectSize(), dstImVec2);
    */

    /**
     * Get size of last item
     */
    public static native float getItemRectSizeX(); /*
        return ImGui::GetItemRectSize().x;
    */

    /**
     * Get size of last item
     */
    public static native float getItemRectSizeY(); /*
        return ImGui::GetItemRectSize().y;
    */

    /**
     * Allow last item to be overlapped by a subsequent item. sometimes useful with invisible buttons, selectables, etc. to catch unused area.
     */
    public static native void setItemAllowOverlap(); /*
        ImGui::SetItemAllowOverlap();
    */

    // Viewports
    // - Currently represents the Platform Window created by the application which is hosting our Dear ImGui windows.
    // - In 'docking' branch with multi-viewport enabled, we extend this concept to have multiple active viewports.
    // - In the future we will extend this concept further to also represent Platform Monitor and support a "no main platform window" operation mode.

    /**
     * Return primary/default viewport.
     */
    public static ImGuiViewport getMainViewport() {
        MAIN_VIEWPORT.ptr = nGetMainViewport();
        return MAIN_VIEWPORT;
    }

    private static native long nGetMainViewport(); /*
        return (intptr_t)ImGui::GetMainViewport();
    */

    // Miscellaneous Utilities

    /**
     * Test if rectangle (of given size, starting from cursor position) is visible / not clipped.
     */
    public static native boolean isRectVisible(float width, float height); /*
        return ImGui::IsRectVisible(ImVec2(width, height));
    */

    /**
     * Test if rectangle (in screen space) is visible / not clipped. to perform coarse clipping on user's side.
     */
    public static native boolean isRectVisible(float minX, float minY, float maxX, float maxY); /*
        return ImGui::IsRectVisible(ImVec2(minX, minY), ImVec2(maxX, maxY));
    */

    /**
     * Get global imgui time. incremented by io.DeltaTime every frame.
     */
    public static native double getTime(); /*
        return ImGui::GetTime();
    */

    /**
     * Get global imgui frame count. incremented by 1 every frame.
     */
    public static native int getFrameCount(); /*
        return ImGui::GetFrameCount();
    */

    /**
     * Get background draw list for the viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getBackgroundDrawList() {
        BACKGROUND_DRAW_LIST.ptr = nGetBackgroundDrawList();
        return BACKGROUND_DRAW_LIST;
    }

    private static native long nGetBackgroundDrawList(); /*
        return (intptr_t)ImGui::GetBackgroundDrawList();
    */

    /**
     * Get foreground draw list for the viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getForegroundDrawList() {
        FOREGROUND_DRAW_LIST.ptr = nGetForegroundDrawList();
        return FOREGROUND_DRAW_LIST;
    }

    private static native long nGetForegroundDrawList(); /*
        return (intptr_t)ImGui::GetForegroundDrawList();
    */

    /**
     * Get background draw list for the given viewport.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getBackgroundDrawList(ImGuiViewport viewport) {
        BACKGROUND_DRAW_LIST.ptr = nGetBackgroundDrawList(viewport.ptr);
        return BACKGROUND_DRAW_LIST;
    }

    private static native long nGetBackgroundDrawList(long viewportPtr); /*
        return (intptr_t)ImGui::GetBackgroundDrawList((ImGuiViewport*)viewportPtr);
    */

    /**
     * Get foreground draw list for the given viewport.
     * This draw list will be the last rendered one. Useful to quickly draw shapes/text over dear imgui contents.
     */
    public static ImDrawList getForegroundDrawList(ImGuiViewport viewport) {
        BACKGROUND_DRAW_LIST.ptr = nGetForegroundDrawList(viewport.ptr);
        return BACKGROUND_DRAW_LIST;
    }

    private static native long nGetForegroundDrawList(long viewportPtr); /*
        return (intptr_t)ImGui::GetBackgroundDrawList((ImGuiViewport*)viewportPtr);
    */

    // TODO GetDrawListSharedData

    /**
     * Get a string corresponding to the enum value (for display, saving, etc.).
     */
    public static native String getStyleColorName(int imGuiCol); /*
        return env->NewStringUTF(ImGui::GetStyleColorName(imGuiCol));
    */

    /**
     * Replace current window storage with our own (if you want to manipulate it yourself, typically clear subsection of it).
     */
    public static void setStateStorage(final ImGuiStorage storage) {
        nSetStateStorage(storage.ptr);
    }

    private static native void nSetStateStorage(long imGuiStoragePtr); /*
        ImGui::SetStateStorage((ImGuiStorage*)imGuiStoragePtr);
    */

    public static ImGuiStorage getStateStorage() {
        IMGUI_STORAGE.ptr = nGetStateStorage();
        return IMGUI_STORAGE;
    }

    private static native long nGetStateStorage(); /*
        return (intptr_t)ImGui::GetStateStorage();
    */

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static native boolean beginChildFrame(int id, float width, float height); /*
        return ImGui::BeginChildFrame(id, ImVec2(width, height));
    */

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static native boolean beginChildFrame(int id, float width, float height, int imGuiWindowFlags); /*
        return ImGui::BeginChildFrame(id, ImVec2(width, height), imGuiWindowFlags);
    */

    /**
     * Always call EndChildFrame() regardless of BeginChildFrame() return values (which indicates a collapsed/clipped window)
     */
    public static native void endChildFrame(); /*
        ImGui::EndChildFrame();
    */

    // Text Utilities

    public final ImVec2 calcTextSize(final String text) {
        final ImVec2 value = new ImVec2();
        calcTextSize(value, text);
        return value;
    }

    public final ImVec2 calcTextSize(final String text, final boolean hideTextAfterDoubleHash) {
        final ImVec2 value = new ImVec2();
        calcTextSize(value, text, hideTextAfterDoubleHash);
        return value;
    }

    public final ImVec2 calcTextSize(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        final ImVec2 value = new ImVec2();
        calcTextSize(value, text, hideTextAfterDoubleHash, wrapWidth);
        return value;
    }

    public static native void calcTextSize(ImVec2 dstImVec2, String text); /*
        ImVec2 src = ImGui::CalcTextSize(text);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    public static native void calcTextSize(ImVec2 dstImVec2, String text, boolean hideTextAfterDoubleHash); /*
        ImVec2 src = ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    public static native void calcTextSize(ImVec2 dstImVec2, String text, float wrapWidth); /*
        ImVec2 src = ImGui::CalcTextSize(text, NULL, false, wrapWidth);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    public static native void calcTextSize(ImVec2 dstImVec2, String text, boolean hideTextAfterDoubleHash, float wrapWidth); /*
        ImVec2 src = ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash, wrapWidth);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    // Color Utilities

    public final ImVec4 colorConvertU32ToFloat4(final int in) {
        final ImVec4 value = new ImVec4();
        colorConvertU32ToFloat4(in, value);
        return value;
    }

    public static native void colorConvertU32ToFloat4(int in, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, ImGui::ColorConvertU32ToFloat4(in), dstImVec4);
    */

    public static native int colorConvertFloat4ToU32(float r, float g, float b, float a); /*
        return ImGui::ColorConvertFloat4ToU32(ImVec4(r, g, b, a));
    */

    public static native void colorConvertRGBtoHSV(float[] rgb, float[] hsv); /*
        ImGui::ColorConvertRGBtoHSV(rgb[0], rgb[1], rgb[2], hsv[0], hsv[1], hsv[2]);
    */

    public static native void colorConvertHSVtoRGB(float[] hsv, float[] rgb); /*
        ImGui::ColorConvertHSVtoRGB(hsv[0], hsv[1], hsv[2], rgb[0], rgb[1], rgb[2]);
    */

    // Inputs Utilities: Keyboard
    // - For 'int user_key_index' you can use your own indices/enums according to how your backend/engine stored them in io.KeysDown[].
    // - We don't know the meaning of those value. You can use GetKeyIndex() to map a ImGuiKey_ value into the user index.

    /**
     * Map ImGuiKey_* values into user's key index. == io.KeyMap[key]
     */
    public static native int getKeyIndex(int imguiKey); /*
        return ImGui::GetKeyIndex(imguiKey);
    */

    /**
     * Is key being held. == io.KeysDown[user_key_index].
     */
    public static native boolean isKeyDown(int userKeyIndex); /*
        return ImGui::IsKeyDown(userKeyIndex);
    */

    /**
     * Was key pressed (went from !Down to Down)? if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    public static native boolean isKeyPressed(int userKeyIndex); /*
        return ImGui::IsKeyPressed(userKeyIndex);
    */

    /**
     * Was key pressed (went from !Down to Down)? if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    public static native boolean isKeyPressed(int userKeyIndex, boolean repeat); /*
        return ImGui::IsKeyPressed(userKeyIndex, repeat);
    */

    /**
     * Was key released (went from Down to !Down)..
     */
    public static native boolean isKeyReleased(int userKeyIndex); /*
        return ImGui::IsKeyReleased(userKeyIndex);
    */

    /**
     * Uses provided repeat rate/delay.
     * Return a count, most often 0 or 1 but might be {@code >1} if RepeatRate is small enough that {@code DeltaTime > RepeatRate}
     */
    public static native boolean getKeyPressedAmount(int keyIndex, float repeatDelay, float rate); /*
        return ImGui::GetKeyPressedAmount(keyIndex, repeatDelay, rate);
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureKeyboard flag next frame (said flag is entirely left for your application to handle).
     * e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = wantCaptureKeyboardValue"; after the next NewFrame() call.
     */
    public static native void captureKeyboardFromApp(); /*
        ImGui::CaptureKeyboardFromApp();
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureKeyboard flag next frame (said flag is entirely left for your application to handle).
     * e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = wantCaptureKeyboardValue"; after the next NewFrame() call.
     */
    public static native void captureKeyboardFromApp(boolean wantCaptureKeyboardValue); /*
        ImGui::CaptureKeyboardFromApp(wantCaptureKeyboardValue);
    */

    // Inputs Utilities: Mouse
    // - To refer to a mouse button, you may use named enums in your code e.g. ImGuiMouseButton_Left, ImGuiMouseButton_Right.
    // - You can also use regular integer: it is forever guaranteed that 0=Left, 1=Right, 2=Middle.
    // - Dragging operations are only reported after mouse has moved a certain distance away from the initial clicking position (see 'lock_threshold' and 'io.MouseDraggingThreshold')

    /**
     * Is mouse button held (0=left, 1=right, 2=middle)
     */
    public static native boolean isMouseDown(int button); /*
        return ImGui::IsMouseDown(button);
    */

    /**
     * Is any mouse button held
     */
    public static native boolean isAnyMouseDown(); /*
        return ImGui::IsAnyMouseDown();
    */

    /**
     * Did mouse button clicked (went from !Down to Down) (0=left, 1=right, 2=middle)
     */
    public static native boolean isMouseClicked(int button); /*
        return ImGui::IsMouseClicked(button);
    */

    public static native boolean isMouseClicked(int button, boolean repeat); /*
        return ImGui::IsMouseClicked(button, repeat);
    */

    /**
     * did mouse button double-clicked? (note that a double-click will also report IsMouseClicked() == true).
     */
    public static native boolean isMouseDoubleClicked(int button); /*
        return ImGui::IsMouseDoubleClicked(button);
    */

    /**
     * Return the number of successive mouse-clicks at the time where a click happen (otherwise 0).
     */
    public static native int getMouseClickedCount(int button); /*
        return ImGui::GetMouseClickedCount(button);
    */

    /**
     * Did mouse button released (went from Down to !Down)
     */
    public static native boolean isMouseReleased(int button); /*
        return ImGui::IsMouseReleased(button);
    */

    /**
     * Is mouse dragging. if lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold
     */
    public static native boolean isMouseDragging(int button); /*
        return ImGui::IsMouseDragging(button);
    */

    /**
     * Is mouse dragging. if lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold
     */
    public static native boolean isMouseDragging(int button, float lockThreshold); /*
        return ImGui::IsMouseDragging(button, lockThreshold);
    */

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static native boolean isMouseHoveringRect(float minX, float minY, float maxX, float maxY); /*
        return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY));
    */

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static native boolean isMouseHoveringRect(float minX, float minY, float maxX, float maxY, boolean clip); /*
        return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY), clip);
    */

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static native boolean isMousePosValid(); /*
        return ImGui::IsMousePosValid();
    */

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static native boolean isMousePosValid(float mousePosX, float mousePosY); /*
        ImVec2 pos = ImVec2(mousePosX, mousePosY);
        return ImGui::IsMousePosValid(&pos);
    */

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static ImVec2 getMousePos() {
        final ImVec2 value = new ImVec2();
        getMousePos(value);
        return value;
    }

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static native void getMousePos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetMousePos(), dstImVec2);
    */

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static native float getMousePosX(); /*
        return ImGui::GetMousePos().x;
    */

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static native float getMousePosY(); /*
        return ImGui::GetMousePos().y;
    */

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static ImVec2 getMousePosOnOpeningCurrentPopup() {
        final ImVec2 value = new ImVec2();
        getMousePosOnOpeningCurrentPopup(value);
        return value;
    }

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static native void getMousePosOnOpeningCurrentPopup(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetMousePosOnOpeningCurrentPopup(), dstImVec2);
    */

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static native float getMousePosOnOpeningCurrentPopupX(); /*
        return ImGui::GetMousePosOnOpeningCurrentPopup().x;
    */

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static native float getMousePosOnOpeningCurrentPopupY(); /*
        return ImGui::GetMousePosOnOpeningCurrentPopup().y;
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static ImVec2 getMouseDragDelta() {
        final ImVec2 value = new ImVec2();
        getMouseDragDelta(value);
        return value;
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native void getMouseDragDelta(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(), dstImVec2);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native float getMouseDragDeltaX(); /*
        return ImGui::GetMouseDragDelta().x;
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native float getMouseDragDeltaY(); /*
        return ImGui::GetMouseDragDelta().y;
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static ImVec2 getMouseDragDelta(final int button) {
        final ImVec2 value = new ImVec2();
        getMouseDragDelta(value, button);
        return value;
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native void getMouseDragDelta(ImVec2 dstImVec2, int button); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(button), dstImVec2);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native float getMouseDragDeltaX(int button); /*
        return ImGui::GetMouseDragDelta(button).x;
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native float getMouseDragDeltaY(int button); /*
        return ImGui::GetMouseDragDelta(button).y;
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static ImVec2 getMouseDragDelta(final int button, final float lockThreshold) {
        final ImVec2 value = new ImVec2();
        getMouseDragDelta(value, button, lockThreshold);
        return value;
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native void getMouseDragDelta(ImVec2 dstImVec2, int button, float lockThreshold); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(button, lockThreshold), dstImVec2);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native float getMouseDragDeltaX(int button, float lockThreshold); /*
        return ImGui::GetMouseDragDelta(button, lockThreshold).x;
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static native float getMouseDragDeltaY(int button, float lockThreshold); /*
        return ImGui::GetMouseDragDelta(button, lockThreshold).y;
    */

    public static native void resetMouseDragDelta(); /*
        ImGui::ResetMouseDragDelta();
    */

    public static native void resetMouseDragDelta(int button); /*
        ImGui::ResetMouseDragDelta(button);
    */

    /**
     * Get desired cursor type, reset in ImGui::NewFrame(), this is updated during the frame. valid before Render().
     * If you use software rendering by setting io.MouseDrawCursor ImGui will render those for you
     */
    public static native int getMouseCursor(); /*
        return ImGui::GetMouseCursor();
    */

    /**
     * Set desired cursor type
     */
    public static native void setMouseCursor(int type); /*
        ImGui::SetMouseCursor(type);
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureMouse flag next frame (said flag is entirely left for your application to handle).
     * This is equivalent to setting "io.WantCaptureMouse = wantCaptureMouseValue;" after the next NewFrame() call.
     */
    public static native void captureMouseFromApp(); /*
        ImGui::CaptureMouseFromApp();
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureMouse flag next frame (said flag is entirely left for your application to handle).
     * This is equivalent to setting "io.WantCaptureMouse = wantCaptureMouseValue;" after the next NewFrame() call.
     */
    public static native void captureMouseFromApp(boolean wantCaptureMouseValue); /*
        ImGui::CaptureMouseFromApp(wantCaptureMouseValue);
    */

    // Clipboard Utilities
    // - Also see the LogToClipboard() function to capture GUI into clipboard, or easily output text data to the clipboard.

    public static native String getClipboardText(); /*
        return env->NewStringUTF(ImGui::GetClipboardText());
    */

    public static native void setClipboardText(String text); /*
        ImGui::SetClipboardText(text);
    */

    // Settings/.Ini Utilities
    // - The disk functions are automatically called if io.IniFilename != NULL (default is "imgui.ini").
    // - Set io.IniFilename to NULL to load/save manually. Read io.WantSaveIniSettings description about handling .ini saving manually.

    /**
     * Call after CreateContext() and before the first call to NewFrame(). NewFrame() automatically calls LoadIniSettingsFromDisk(io.IniFilename).
     */
    public static native void loadIniSettingsFromDisk(String iniFilename); /*
        ImGui::LoadIniSettingsFromDisk(iniFilename);
    */

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    public static native void loadIniSettingsFromMemory(String iniData); /*
        ImGui::LoadIniSettingsFromMemory(iniData);
    */

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    public static native void loadIniSettingsFromMemory(String iniData, int iniSize); /*
        ImGui::LoadIniSettingsFromMemory(iniData, iniSize);
    */

    /**
     * This is automatically called (if io.IniFilename is not empty) a few seconds after any modification that should be reflected in the .ini file (and also by DestroyContext).
     */
    public static native void saveIniSettingsToDisk(String iniFilename); /*
        ImGui::SaveIniSettingsToDisk(iniFilename);
    */

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    public static native String saveIniSettingsToMemory(); /*
        return env->NewStringUTF(ImGui::SaveIniSettingsToMemory());
    */

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    public static native String saveIniSettingsToMemory(long outIniSize); /*
        return env->NewStringUTF(ImGui::SaveIniSettingsToMemory((size_t*)&outIniSize));
    */

    // (Optional) Platform/OS interface for multi-viewport support
    // Read comments around the ImGuiPlatformIO structure for more details.
    // Note: You may use GetWindowViewport() to get the current viewport of the current window.

    /**
     * Platform/renderer functions, for backend to setup + viewports list.
     */
    public static ImGuiPlatformIO getPlatformIO() {
        PLATFORM_IO.ptr = nGetPlatformIO();
        return PLATFORM_IO;
    }

    private static native long nGetPlatformIO(); /*
        return (intptr_t)&ImGui::GetPlatformIO();
    */

    /**
     * Call in main loop. Will call CreateWindow/ResizeWindow/etc. Platform functions for each secondary viewport, and DestroyWindow for each inactive viewport.
     */
    public static native void updatePlatformWindows(); /*
        ImGui::UpdatePlatformWindows();
    */

    /**
     *  Call in main loop. will call RenderWindow/SwapBuffers platform functions for each secondary viewport which doesn't have the ImGuiViewportFlags_Minimized flag set.
     *  May be reimplemented by user for custom rendering needs.
     */
    public static native void renderPlatformWindowsDefault(); /*
        ImGui::RenderPlatformWindowsDefault();
    */

    /**
     * Call DestroyWindow platform functions for all viewports.
     * Call from backend Shutdown() if you need to close platform windows before imgui shutdown.
     * Otherwise will be called by DestroyContext().
     */
    public static native void destroyPlatformWindows(); /*
        ImGui::DestroyPlatformWindows();
    */

    /**
     * This is a helper for backends.
     */
    public static ImGuiViewport findViewportByID(int imGuiID) {
        FIND_VIEWPORT.ptr = nFindViewportByID(imGuiID);
        return FIND_VIEWPORT;
    }

    private static native long nFindViewportByID(int imGuiID); /*
        return (intptr_t)ImGui::FindViewportByID(imGuiID);
    */

    /**
     * This is a helper for backends. The type platform_handle is decided by the backend (e.g. HWND, MyWindow*, GLFWwindow* etc.)
     */
    public static ImGuiViewport findViewportByPlatformHandle(long platformHandle) {
        FIND_VIEWPORT.ptr = nFindViewportByPlatformHandle(platformHandle);
        return FIND_VIEWPORT;
    }

    private static native long nFindViewportByPlatformHandle(long platformHandle); /*
        return (intptr_t)ImGui::FindViewportByPlatformHandle((void*)platformHandle);
    */
}
