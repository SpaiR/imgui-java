package imgui;

import imgui.assertion.ImAssertCallback;
import imgui.callback.ImGuiInputTextCallback;
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
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Properties;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ImGui {
    private static final String LIB_PATH_PROP = "imgui.library.path";
    private static final String LIB_NAME_PROP = "imgui.library.name";
    private static final String LIB_NAME_DEFAULT = "imgui-java64";
    private static final String LIB_TMP_DIR_PREFIX = "imgui-java-natives";

    static {
        final String libPath = System.getProperty(LIB_PATH_PROP);
        final String libName = System.getProperty(LIB_NAME_PROP, LIB_NAME_DEFAULT);
        final String fullLibName = resolveFullLibName();

        if (libPath != null) {
            System.load(Paths.get(libPath).resolve(fullLibName).toAbsolutePath().toString());
        } else {
            try {
                System.loadLibrary(libName);
            } catch (Exception | Error e) {
                final String extractedLibAbsPath = tryLoadFromClasspath(fullLibName);
                if (extractedLibAbsPath != null) {
                    System.load(extractedLibAbsPath);
                } else {
                    throw e;
                }
            }
        }

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

        return System.getProperty(LIB_NAME_PROP, libPrefix + LIB_NAME_DEFAULT + libSuffix);
    }

    // This method tries to unpack the library binary from classpath into the temp dir.
    private static String tryLoadFromClasspath(final String fullLibName) {
        try (InputStream is = ImGui.class.getClassLoader().getResourceAsStream("io/imgui/java/native-bin/" + fullLibName)) {
            if (is == null) {
                return null;
            }

            final String version = getVersionString().orElse("undefined");
            final Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve(LIB_TMP_DIR_PREFIX).resolve(version);
            if (!Files.exists(tmpDir)) {
                Files.createDirectories(tmpDir);
            }

            final Path libBin = tmpDir.resolve(fullLibName);
            try {
                Files.copy(is, libBin, StandardCopyOption.REPLACE_EXISTING);
            } catch (AccessDeniedException e) {
                if (!Files.exists(libBin)) {
                    throw e;
                }
            }

            return libBin.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Optional<String> getVersionString() {
        final Properties properties = new Properties();
        try (InputStream is = ImGui.class.getResourceAsStream("/imgui/imgui-java.properties")) {
            if (is != null) {
                properties.load(is);
                return Optional.of(properties.get("imgui.java.version").toString());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return Optional.empty();
    }

    /**
     * For internal usage.
     * Method is used to initiate static instantiation (loading of the native libraries etc.).
     * Otherwise, native libraries will be loaded on demand and natively mapped objects won't work.
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

    /**
     * Set a custom assertion callback for ImGui assertions.
     * Take note: Any custom assertion callback SHOULD NOT throw any exception.
     * After any callback the application will be terminated, any attempt to bypass this behavior
     * will result in a EXCEPTION_ACCESS_VIOLATION from within the native layer.
     *
     * @param callback The custom ImGui assertion callback
     */
    public static native void setAssertCallback(ImAssertCallback callback); /*
        Jni::SetAssertionCallback(callback);
    */

    // Context creation and access
    // Each context create its own ImFontAtlas by default.
    // You may instance one yourself and pass it to CreateContext() to share a font atlas between imgui contexts.
    // None of those functions is reliant on the current context.

    public static ImGuiContext createContext() {
        return new ImGuiContext(nCreateContext());
    }

    public static ImGuiContext createContext(final ImFontAtlas sharedFontAtlas) {
        return new ImGuiContext(nCreateContext(sharedFontAtlas.ptr));
    }

    private static native long nCreateContext(); /*
        return (uintptr_t)ImGui::CreateContext();
    */

    private static native long nCreateContext(long sharedFontAtlas); /*
        return (uintptr_t)ImGui::CreateContext(reinterpret_cast<ImFontAtlas*>(sharedFontAtlas));
    */

    public static void destroyContext() {
        nDestroyContext();
    }

    public static void destroyContext(final ImGuiContext ctx) {
        nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext(); /*
        ImGui::DestroyContext();
    */

    private static native void nDestroyContext(long ctx); /*
        ImGui::DestroyContext(reinterpret_cast<ImGuiContext*>(ctx));
    */

    public static ImGuiContext getCurrentContext() {
        return new ImGuiContext(nGetCurrentContext());
    }

    private static native long nGetCurrentContext(); /*
        return (uintptr_t)ImGui::GetCurrentContext();
    */

    public static void setCurrentContext(final ImGuiContext ctx) {
        nSetCurrentContext(ctx.ptr);
    }

    private static native void nSetCurrentContext(long ctx); /*
        ImGui::SetCurrentContext(reinterpret_cast<ImGuiContext*>(ctx));
    */

    // Main

    private static final ImGuiIO _GETIO_1 = new ImGuiIO(0);

    /**
     * Access the IO structure (mouse/keyboard/gamepad inputs, time, various configuration options/flags).
     */
    public static ImGuiIO getIO() {
        _GETIO_1.ptr = nGetIO();
        return _GETIO_1;
    }

    private static native long nGetIO(); /*
        return (uintptr_t)&ImGui::GetIO();
    */

    private static final ImGuiStyle _GETSTYLE_1 = new ImGuiStyle(0);

    /**
     * Access the Style structure (colors, sizes). Always use PushStyleCol(), PushStyleVar() to modify style mid-frame!
     */
    public static ImGuiStyle getStyle() {
        _GETSTYLE_1.ptr = nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle(); /*
        return (uintptr_t)&ImGui::GetStyle();
    */

    /**
     * Start a new Dear ImGui frame, you can submit any command from this point until Render()/EndFrame().
     */
    public static void newFrame() {
        nNewFrame();
    }

    private static native void nNewFrame(); /*
        ImGui::NewFrame();
    */

    /**
     * Ends the Dear ImGui frame. automatically called by Render(). If you don't need to render data (skipping rendering) you may call EndFrame() without
     * Render()... but you'll have wasted CPU already! If you don't need to render, better to not create any windows and not call NewFrame() at all!
     */
    public static void endFrame() {
        nEndFrame();
    }

    private static native void nEndFrame(); /*
        ImGui::EndFrame();
    */

    /**
     * Ends the Dear ImGui frame, finalize the draw data. You can then get call GetDrawData().
     */
    public static void render() {
        nRender();
    }

    private static native void nRender(); /*
        ImGui::Render();
    */

    private static final ImDrawData _GETDRAWDATA_1 = new ImDrawData(0);

    /**
     * Valid after Render() and until the next call to NewFrame(). this is what you have to render.
     */
    public static ImDrawData getDrawData() {
        _GETDRAWDATA_1.ptr = nGetDrawData();
        return _GETDRAWDATA_1;
    }

    private static native long nGetDrawData(); /*
        return (uintptr_t)ImGui::GetDrawData();
    */

    // Demo, Debug, Information

    /**
     * Create Demo window. Demonstrate most ImGui features. Call this to learn about the library!
     */
    public static void showDemoWindow() {
        nShowDemoWindow();
    }

    /**
     * Create Demo window. Demonstrate most ImGui features. Call this to learn about the library!
     */
    public static void showDemoWindow(final ImBoolean pOpen) {
        nShowDemoWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowDemoWindow(); /*
        ImGui::ShowDemoWindow();
    */

    private static native void nShowDemoWindow(boolean[] pOpen); /*MANUAL
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        ImGui::ShowDemoWindow((pOpen != NULL ? &pOpen[0] : NULL));
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
    */

    /**
     * Create Metrics/Debugger window. display Dear ImGui internals: windows, draw commands, various internal state, etc.
     */
    public static void showMetricsWindow() {
        nShowMetricsWindow();
    }

    /**
     * Create Metrics/Debugger window. display Dear ImGui internals: windows, draw commands, various internal state, etc.
     */
    public static void showMetricsWindow(final ImBoolean pOpen) {
        nShowMetricsWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowMetricsWindow(); /*
        ImGui::ShowMetricsWindow();
    */

    private static native void nShowMetricsWindow(boolean[] pOpen); /*MANUAL
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        ImGui::ShowMetricsWindow((pOpen != NULL ? &pOpen[0] : NULL));
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
    */

    /**
     * Create Stack Tool window. hover items with mouse to query information about the source of their unique ID.
     */
    public static void showStackToolWindow() {
        nShowStackToolWindow();
    }

    /**
     * Create Stack Tool window. hover items with mouse to query information about the source of their unique ID.
     */
    public static void showStackToolWindow(final ImBoolean pOpen) {
        nShowStackToolWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowStackToolWindow(); /*
        ImGui::ShowStackToolWindow();
    */

    private static native void nShowStackToolWindow(boolean[] pOpen); /*MANUAL
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        ImGui::ShowStackToolWindow((pOpen != NULL ? &pOpen[0] : NULL));
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
    */

    /**
     * Create About window. display Dear ImGui version, credits and build/system information.
     */
    public static void showAboutWindow() {
        nShowAboutWindow();
    }

    /**
     * Create About window. display Dear ImGui version, credits and build/system information.
     */
    public static void showAboutWindow(final ImBoolean pOpen) {
        nShowAboutWindow(pOpen != null ? pOpen.getData() : null);
    }

    private static native void nShowAboutWindow(); /*
        ImGui::ShowAboutWindow();
    */

    private static native void nShowAboutWindow(boolean[] pOpen); /*MANUAL
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        ImGui::ShowAboutWindow((pOpen != NULL ? &pOpen[0] : NULL));
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
    */

    /**
     * Add style editor block (not a window).
     * You can pass in a reference ImGuiStyle structure to compare to, revert to and save to (else it uses the default style)
     */
    public static void showStyleEditor() {
        nShowStyleEditor();
    }

    /**
     * Add style editor block (not a window).
     * You can pass in a reference ImGuiStyle structure to compare to, revert to and save to (else it uses the default style)
     */
    public static void showStyleEditor(final ImGuiStyle ref) {
        nShowStyleEditor(ref.ptr);
    }

    private static native void nShowStyleEditor(); /*
        ImGui::ShowStyleEditor();
    */

    private static native void nShowStyleEditor(long ref); /*
        ImGui::ShowStyleEditor(reinterpret_cast<ImGuiStyle*>(ref));
    */

    /**
     * Add style selector block (not a window), essentially a combo listing the default styles.
     */
    public static boolean showStyleSelector(final String label) {
        return nShowStyleSelector(label);
    }

    private static native boolean nShowStyleSelector(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::ShowStyleSelector(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Add font selector block (not a window), essentially a combo listing the loaded fonts.
     */
    public static void showFontSelector(final String label) {
        nShowFontSelector(label);
    }

    private static native void nShowFontSelector(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::ShowFontSelector(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    /**
     * Add basic help/info block (not a window): how to manipulate ImGui as a end-user (mouse/keyboard controls).
     */
    public static void showUserGuide() {
        nShowUserGuide();
    }

    private static native void nShowUserGuide(); /*
        ImGui::ShowUserGuide();
    */

    /**
     * Get the compiled version string e.g. "1.80 WIP" (essentially the value for IMGUI_VERSION from the compiled version of imgui.cpp)
     */
    public static String getVersion() {
        return nGetVersion();
    }

    private static native String nGetVersion(); /*
        return env->NewStringUTF(ImGui::GetVersion());
    */

    // Styles

    /**
     * New, recommended style (default)
     */
    public static void styleColorsDark() {
        nStyleColorsDark();
    }

    /**
     * New, recommended style (default)
     */
    public static void styleColorsDark(final ImGuiStyle style) {
        nStyleColorsDark(style.ptr);
    }

    private static native void nStyleColorsDark(); /*
        ImGui::StyleColorsDark();
    */

    private static native void nStyleColorsDark(long style); /*
        ImGui::StyleColorsDark(reinterpret_cast<ImGuiStyle*>(style));
    */

    /**
     * Best used with borders and a custom, thicker font
     */
    public static void styleColorsLight() {
        nStyleColorsLight();
    }

    /**
     * Best used with borders and a custom, thicker font
     */
    public static void styleColorsLight(final ImGuiStyle style) {
        nStyleColorsLight(style.ptr);
    }

    private static native void nStyleColorsLight(); /*
        ImGui::StyleColorsLight();
    */

    private static native void nStyleColorsLight(long style); /*
        ImGui::StyleColorsLight(reinterpret_cast<ImGuiStyle*>(style));
    */

    /**
     * Classic imgui style
     */
    public static void styleColorsClassic() {
        nStyleColorsClassic();
    }

    /**
     * Classic imgui style
     */
    public static void styleColorsClassic(final ImGuiStyle style) {
        nStyleColorsClassic(style.ptr);
    }

    private static native void nStyleColorsClassic(); /*
        ImGui::StyleColorsClassic();
    */

    private static native void nStyleColorsClassic(long style); /*
        ImGui::StyleColorsClassic(reinterpret_cast<ImGuiStyle*>(style));
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

    public static boolean begin(final String title) {
        return nBegin(title);
    }

    public static boolean begin(final String title, final ImBoolean pOpen) {
        return nBegin(title, pOpen != null ? pOpen.getData() : null);
    }

    public static boolean begin(final String title, final ImBoolean pOpen, final int imGuiWindowFlags) {
        return nBegin(title, pOpen != null ? pOpen.getData() : null, imGuiWindowFlags);
    }

    public static boolean begin(final String title, final int imGuiWindowFlags) {
        return nBegin(title, imGuiWindowFlags);
    }

    private static native boolean nBegin(String obj_title); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        auto _result = ImGui::Begin(title);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
        return _result;
    */

    private static native boolean nBegin(String obj_title, boolean[] obj_pOpen); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        auto _result = ImGui::Begin(title, (pOpen != NULL ? &pOpen[0] : NULL));
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
        return _result;
    */

    private static native boolean nBegin(String obj_title, boolean[] obj_pOpen, int imGuiWindowFlags); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        auto _result = ImGui::Begin(title, (pOpen != NULL ? &pOpen[0] : NULL), imGuiWindowFlags);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
        return _result;
    */

    private static native boolean nBegin(String obj_title, int imGuiWindowFlags); /*MANUAL
        auto title = obj_title == NULL ? NULL : (char*)env->GetStringUTFChars(obj_title, JNI_FALSE);
        auto _result = ImGui::Begin(title, NULL, imGuiWindowFlags);
        if (title != NULL) env->ReleaseStringUTFChars(obj_title, title);
        return _result;
    */

    public static void end() {
        nEnd();
    }

    private static native void nEnd(); /*
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

    public static boolean beginChild(final String strId) {
        return nBeginChild(strId);
    }

    public static boolean beginChild(final String strId, final ImVec2 size) {
        return nBeginChild(strId, size.x, size.y);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY) {
        return nBeginChild(strId, sizeX, sizeY);
    }

    public static boolean beginChild(final String strId, final ImVec2 size, final boolean border) {
        return nBeginChild(strId, size.x, size.y, border);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border) {
        return nBeginChild(strId, sizeX, sizeY, border);
    }

    public static boolean beginChild(final String strId, final ImVec2 size, final boolean border, final int imGuiWindowFlags) {
        return nBeginChild(strId, size.x, size.y, border, imGuiWindowFlags);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border, final int imGuiWindowFlags) {
        return nBeginChild(strId, sizeX, sizeY, border, imGuiWindowFlags);
    }

    public static boolean beginChild(final String strId, final boolean border, final int imGuiWindowFlags) {
        return nBeginChild(strId, border, imGuiWindowFlags);
    }

    public static boolean beginChild(final String strId, final int imGuiWindowFlags) {
        return nBeginChild(strId, imGuiWindowFlags);
    }

    public static boolean beginChild(final String strId, final ImVec2 size, final int imGuiWindowFlags) {
        return nBeginChild(strId, size.x, size.y, imGuiWindowFlags);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY, final int imGuiWindowFlags) {
        return nBeginChild(strId, sizeX, sizeY, imGuiWindowFlags);
    }

    private static native boolean nBeginChild(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginChild(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginChild(String obj_strId, float sizeX, float sizeY); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(strId, size);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginChild(String obj_strId, float sizeX, float sizeY, boolean border); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(strId, size, border);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginChild(String obj_strId, float sizeX, float sizeY, boolean border, int imGuiWindowFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(strId, size, border, imGuiWindowFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginChild(String obj_strId, boolean border, int imGuiWindowFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginChild(strId, ImVec2(0,0), border, imGuiWindowFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginChild(String obj_strId, int imGuiWindowFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginChild(strId, ImVec2(0,0), false, imGuiWindowFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginChild(String obj_strId, float sizeX, float sizeY, int imGuiWindowFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(strId, size, false, imGuiWindowFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    public static boolean beginChild(final int imGuiID) {
        return nBeginChild(imGuiID);
    }

    public static boolean beginChild(final int imGuiID, final ImVec2 size) {
        return nBeginChild(imGuiID, size.x, size.y);
    }

    public static boolean beginChild(final int imGuiID, final float sizeX, final float sizeY) {
        return nBeginChild(imGuiID, sizeX, sizeY);
    }

    public static boolean beginChild(final int imGuiID, final ImVec2 size, final boolean border) {
        return nBeginChild(imGuiID, size.x, size.y, border);
    }

    public static boolean beginChild(final int imGuiID, final float sizeX, final float sizeY, final boolean border) {
        return nBeginChild(imGuiID, sizeX, sizeY, border);
    }

    public static boolean beginChild(final int imGuiID, final ImVec2 size, final boolean border, final int imGuiWindowFlags) {
        return nBeginChild(imGuiID, size.x, size.y, border, imGuiWindowFlags);
    }

    public static boolean beginChild(final int imGuiID, final float sizeX, final float sizeY, final boolean border, final int imGuiWindowFlags) {
        return nBeginChild(imGuiID, sizeX, sizeY, border, imGuiWindowFlags);
    }

    public static boolean beginChild(final int imGuiID, final boolean border, final int imGuiWindowFlags) {
        return nBeginChild(imGuiID, border, imGuiWindowFlags);
    }

    public static boolean beginChild(final int imGuiID, final int imGuiWindowFlags) {
        return nBeginChild(imGuiID, imGuiWindowFlags);
    }

    public static boolean beginChild(final int imGuiID, final ImVec2 size, final int imGuiWindowFlags) {
        return nBeginChild(imGuiID, size.x, size.y, imGuiWindowFlags);
    }

    public static boolean beginChild(final int imGuiID, final float sizeX, final float sizeY, final int imGuiWindowFlags) {
        return nBeginChild(imGuiID, sizeX, sizeY, imGuiWindowFlags);
    }

    private static native boolean nBeginChild(int imGuiID); /*
        return ImGui::BeginChild(imGuiID);
    */

    private static native boolean nBeginChild(int imGuiID, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(imGuiID, size);
        return _result;
    */

    private static native boolean nBeginChild(int imGuiID, float sizeX, float sizeY, boolean border); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(imGuiID, size, border);
        return _result;
    */

    private static native boolean nBeginChild(int imGuiID, float sizeX, float sizeY, boolean border, int imGuiWindowFlags); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(imGuiID, size, border, imGuiWindowFlags);
        return _result;
    */

    private static native boolean nBeginChild(int imGuiID, boolean border, int imGuiWindowFlags); /*
        return ImGui::BeginChild(imGuiID, ImVec2(0,0), border, imGuiWindowFlags);
    */

    private static native boolean nBeginChild(int imGuiID, int imGuiWindowFlags); /*
        return ImGui::BeginChild(imGuiID, ImVec2(0,0), false, imGuiWindowFlags);
    */

    private static native boolean nBeginChild(int imGuiID, float sizeX, float sizeY, int imGuiWindowFlags); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChild(imGuiID, size, false, imGuiWindowFlags);
        return _result;
    */

    public static void endChild() {
        nEndChild();
    }

    private static native void nEndChild(); /*
        ImGui::EndChild();
    */

    // Windows Utilities
    // - "current window" = the window we are appending into while inside a Begin()/End() block. "next window" = next window we will Begin() into.

    public static boolean isWindowAppearing() {
        return nIsWindowAppearing();
    }

    private static native boolean nIsWindowAppearing(); /*
        return ImGui::IsWindowAppearing();
    */

    public static boolean isWindowCollapsed() {
        return nIsWindowCollapsed();
    }

    private static native boolean nIsWindowCollapsed(); /*
        return ImGui::IsWindowCollapsed();
    */

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    public static boolean isWindowFocused() {
        return nIsWindowFocused();
    }

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    public static boolean isWindowFocused(final int imGuiFocusedFlags) {
        return nIsWindowFocused(imGuiFocusedFlags);
    }

    private static native boolean nIsWindowFocused(); /*
        return ImGui::IsWindowFocused();
    */

    private static native boolean nIsWindowFocused(int imGuiFocusedFlags); /*
        return ImGui::IsWindowFocused(imGuiFocusedFlags);
    */

    /**
     * Is current window hovered (and typically: not blocked by a popup/modal)? see flags for options.
     * NB: If you are trying to check whether your mouse should be dispatched to imgui or to your app,
     * you should use the 'io.WantCaptureMouse' boolean for that! Please read the FAQ!
     */
    public static boolean isWindowHovered() {
        return nIsWindowHovered();
    }

    /**
     * Is current window hovered (and typically: not blocked by a popup/modal)? see flags for options.
     * NB: If you are trying to check whether your mouse should be dispatched to imgui or to your app,
     * you should use the 'io.WantCaptureMouse' boolean for that! Please read the FAQ!
     */
    public static boolean isWindowHovered(final int imGuiHoveredFlags) {
        return nIsWindowHovered(imGuiHoveredFlags);
    }

    private static native boolean nIsWindowHovered(); /*
        return ImGui::IsWindowHovered();
    */

    private static native boolean nIsWindowHovered(int imGuiHoveredFlags); /*
        return ImGui::IsWindowHovered(imGuiHoveredFlags);
    */

    /**
     * Get draw list associated to the current window, to append your own drawing primitives
     */
    public static ImDrawList getWindowDrawList() {
        return new ImDrawList(nGetWindowDrawList());
    }

    private static native long nGetWindowDrawList(); /*
        return (uintptr_t)ImGui::GetWindowDrawList();
    */

    /**
     * Get DPI scale currently associated to the current window's viewport.
     */
    public static float getWindowDpiScale() {
        return nGetWindowDpiScale();
    }

    private static native float nGetWindowDpiScale(); /*
        return ImGui::GetWindowDpiScale();
    */

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static ImVec2 getWindowPos() {
        final ImVec2 dst = new ImVec2();
        nGetWindowPos(dst);
        return dst;
    }

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static float getWindowPosX() {
        return nGetWindowPosX();
    }

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static float getWindowPosY() {
        return nGetWindowPosY();
    }

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public static void getWindowPos(final ImVec2 dst) {
        nGetWindowPos(dst);
    }

    private static native void nGetWindowPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowPos(), dst);
    */

    private static native float nGetWindowPosX(); /*
        return ImGui::GetWindowPos().x;
    */

    private static native float nGetWindowPosY(); /*
        return ImGui::GetWindowPos().y;
    */

    /**
     * Get current window size
     */
    public static ImVec2 getWindowSize() {
        final ImVec2 dst = new ImVec2();
        nGetWindowSize(dst);
        return dst;
    }

    /**
     * Get current window size
     */
    public static float getWindowSizeX() {
        return nGetWindowSizeX();
    }

    /**
     * Get current window size
     */
    public static float getWindowSizeY() {
        return nGetWindowSizeY();
    }

    /**
     * Get current window size
     */
    public static void getWindowSize(final ImVec2 dst) {
        nGetWindowSize(dst);
    }

    private static native void nGetWindowSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowSize(), dst);
    */

    private static native float nGetWindowSizeX(); /*
        return ImGui::GetWindowSize().x;
    */

    private static native float nGetWindowSizeY(); /*
        return ImGui::GetWindowSize().y;
    */

    /**
     * Get current window width (shortcut for GetWindowSize().x)
     */
    public static float getWindowWidth() {
        return nGetWindowWidth();
    }

    private static native float nGetWindowWidth(); /*
        return ImGui::GetWindowWidth();
    */

    /**
     * Get current window height (shortcut for GetWindowSize().y)
     */
    public static float getWindowHeight() {
        return nGetWindowHeight();
    }

    private static native float nGetWindowHeight(); /*
        return ImGui::GetWindowHeight();
    */

    /**
     * Get viewport currently associated to the current window.
     */
    public static ImGuiViewport getWindowViewport() {
        return new ImGuiViewport(nGetWindowViewport());
    }

    private static native long nGetWindowViewport(); /*
        return (uintptr_t)ImGui::GetWindowViewport();
    */

    // Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final ImVec2 pos) {
        nSetNextWindowPos(pos.x, pos.y);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final float posX, final float posY) {
        nSetNextWindowPos(posX, posY);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final ImVec2 pos, final int cond) {
        nSetNextWindowPos(pos.x, pos.y, cond);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final float posX, final float posY, final int cond) {
        nSetNextWindowPos(posX, posY, cond);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final ImVec2 pos, final int cond, final ImVec2 pivot) {
        nSetNextWindowPos(pos.x, pos.y, cond, pivot.x, pivot.y);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final float posX, final float posY, final int cond, final float pivotX, final float pivotY) {
        nSetNextWindowPos(posX, posY, cond, pivotX, pivotY);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final ImVec2 pos, final ImVec2 pivot) {
        nSetNextWindowPos(pos.x, pos.y, pivot.x, pivot.y);
    }

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static void setNextWindowPos(final float posX, final float posY, final float pivotX, final float pivotY) {
        nSetNextWindowPos(posX, posY, pivotX, pivotY);
    }

    private static native void nSetNextWindowPos(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetNextWindowPos(pos);
    */

    private static native void nSetNextWindowPos(float posX, float posY, int cond); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetNextWindowPos(pos, cond);
    */

    private static native void nSetNextWindowPos(float posX, float posY, int cond, float pivotX, float pivotY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImVec2 pivot = ImVec2(pivotX, pivotY);
        ImGui::SetNextWindowPos(pos, cond, pivot);
    */

    private static native void nSetNextWindowPos(float posX, float posY, float pivotX, float pivotY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImVec2 pivot = ImVec2(pivotX, pivotY);
        ImGui::SetNextWindowPos(pos, ImGuiCond_None, pivot);
    */

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static void setNextWindowSize(final ImVec2 size) {
        nSetNextWindowSize(size.x, size.y);
    }

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static void setNextWindowSize(final float sizeX, final float sizeY) {
        nSetNextWindowSize(sizeX, sizeY);
    }

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static void setNextWindowSize(final ImVec2 size, final int cond) {
        nSetNextWindowSize(size.x, size.y, cond);
    }

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static void setNextWindowSize(final float sizeX, final float sizeY, final int cond) {
        nSetNextWindowSize(sizeX, sizeY, cond);
    }

    private static native void nSetNextWindowSize(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetNextWindowSize(size);
    */

    private static native void nSetNextWindowSize(float sizeX, float sizeY, int cond); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetNextWindowSize(size, cond);
    */

    /**
     * Set next window size limits. use -1,-1 on either X/Y axis to preserve the current size. Sizes will be rounded down.
     */
    public static void setNextWindowSizeConstraints(final ImVec2 sizeMin, final ImVec2 sizeMax) {
        nSetNextWindowSizeConstraints(sizeMin.x, sizeMin.y, sizeMax.x, sizeMax.y);
    }

    /**
     * Set next window size limits. use -1,-1 on either X/Y axis to preserve the current size. Sizes will be rounded down.
     */
    public static void setNextWindowSizeConstraints(final float sizeMinX, final float sizeMinY, final float sizeMaxX, final float sizeMaxY) {
        nSetNextWindowSizeConstraints(sizeMinX, sizeMinY, sizeMaxX, sizeMaxY);
    }

    private static native void nSetNextWindowSizeConstraints(float sizeMinX, float sizeMinY, float sizeMaxX, float sizeMaxY); /*MANUAL
        ImVec2 sizeMin = ImVec2(sizeMinX, sizeMinY);
        ImVec2 sizeMax = ImVec2(sizeMaxX, sizeMaxY);
        ImGui::SetNextWindowSizeConstraints(sizeMin, sizeMax);
    */

    /**
     * Set next window content size (~ scrollable client area, which enforce the range of scrollbars).
     * Not including window decorations (title bar, menu bar, etc.) nor WindowPadding. set an axis to 0.0f to leave it automatic. call before Begin()
     */
    public static void setNextWindowContentSize(final ImVec2 size) {
        nSetNextWindowContentSize(size.x, size.y);
    }

    /**
     * Set next window content size (~ scrollable client area, which enforce the range of scrollbars).
     * Not including window decorations (title bar, menu bar, etc.) nor WindowPadding. set an axis to 0.0f to leave it automatic. call before Begin()
     */
    public static void setNextWindowContentSize(final float sizeX, final float sizeY) {
        nSetNextWindowContentSize(sizeX, sizeY);
    }

    private static native void nSetNextWindowContentSize(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetNextWindowContentSize(size);
    */

    /**
     * Set next window collapsed state. call before Begin()
     */
    public static void setNextWindowCollapsed(final boolean collapsed) {
        nSetNextWindowCollapsed(collapsed);
    }

    /**
     * Set next window collapsed state. call before Begin()
     */
    public static void setNextWindowCollapsed(final boolean collapsed, final int cond) {
        nSetNextWindowCollapsed(collapsed, cond);
    }

    private static native void nSetNextWindowCollapsed(boolean collapsed); /*
        ImGui::SetNextWindowCollapsed(collapsed);
    */

    private static native void nSetNextWindowCollapsed(boolean collapsed, int cond); /*
        ImGui::SetNextWindowCollapsed(collapsed, cond);
    */

    /**
     * Set next window to be focused / top-most. call before Begin()
     */
    public static void setNextWindowFocus() {
        nSetNextWindowFocus();
    }

    private static native void nSetNextWindowFocus(); /*
        ImGui::SetNextWindowFocus();
    */

    /**
     * Set next window background color alpha. helper to easily override the Alpha component of ImGuiCol_WindowBg/ChildBg/PopupBg.
     * You may also use ImGuiWindowFlags_NoBackground.
     */
    public static void setNextWindowBgAlpha(final float alpha) {
        nSetNextWindowBgAlpha(alpha);
    }

    private static native void nSetNextWindowBgAlpha(float alpha); /*
        ImGui::SetNextWindowBgAlpha(alpha);
    */

    /**
     * Set next window viewport.
     */
    public static void setNextWindowViewport(final int viewportId) {
        nSetNextWindowViewport(viewportId);
    }

    private static native void nSetNextWindowViewport(int viewportId); /*
        ImGui::SetNextWindowViewport(viewportId);
    */

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static void setWindowPos(final ImVec2 pos) {
        nSetWindowPos(pos.x, pos.y);
    }

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static void setWindowPos(final float posX, final float posY) {
        nSetWindowPos(posX, posY);
    }

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static void setWindowPos(final ImVec2 pos, final int cond) {
        nSetWindowPos(pos.x, pos.y, cond);
    }

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static void setWindowPos(final float posX, final float posY, final int cond) {
        nSetWindowPos(posX, posY, cond);
    }

    private static native void nSetWindowPos(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetWindowPos(pos);
    */

    private static native void nSetWindowPos(float posX, float posY, int cond); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetWindowPos(pos, cond);
    */

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static void setWindowSize(final ImVec2 size) {
        nSetWindowSize(size.x, size.y);
    }

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static void setWindowSize(final float sizeX, final float sizeY) {
        nSetWindowSize(sizeX, sizeY);
    }

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static void setWindowSize(final ImVec2 size, final int cond) {
        nSetWindowSize(size.x, size.y, cond);
    }

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static void setWindowSize(final float sizeX, final float sizeY, final int cond) {
        nSetWindowSize(sizeX, sizeY, cond);
    }

    private static native void nSetWindowSize(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowSize(size);
    */

    private static native void nSetWindowSize(float sizeX, float sizeY, int cond); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowSize(size, cond);
    */

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    public static void setWindowCollapsed(final boolean collapsed) {
        nSetWindowCollapsed(collapsed);
    }

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    public static void setWindowCollapsed(final boolean collapsed, final int cond) {
        nSetWindowCollapsed(collapsed, cond);
    }

    private static native void nSetWindowCollapsed(boolean collapsed); /*
        ImGui::SetWindowCollapsed(collapsed);
    */

    private static native void nSetWindowCollapsed(boolean collapsed, int cond); /*
        ImGui::SetWindowCollapsed(collapsed, cond);
    */

    /**
     * (not recommended) set current window to be focused / top-most. prefer using SetNextWindowFocus().
     */
    public static void setWindowFocus() {
        nSetWindowFocus();
    }

    private static native void nSetWindowFocus(); /*
        ImGui::SetWindowFocus();
    */

    /**
     * Set font scale. Adjust IO.FontGlobalScale if you want to scale all windows.
     * This is an old API! For correct scaling, prefer to reload font + rebuild ImFontAtlas + call style.ScaleAllSizes().
     */
    public static void setWindowFontScale(final float scale) {
        nSetWindowFontScale(scale);
    }

    private static native void nSetWindowFontScale(float scale); /*
        ImGui::SetWindowFontScale(scale);
    */

    /**
     * Set named window position.
     */
    public static void setWindowPos(final String name, final ImVec2 pos) {
        nSetWindowPos(name, pos.x, pos.y);
    }

    /**
     * Set named window position.
     */
    public static void setWindowPos(final String name, final float posX, final float posY) {
        nSetWindowPos(name, posX, posY);
    }

    /**
     * Set named window position.
     */
    public static void setWindowPos(final String name, final ImVec2 pos, final int cond) {
        nSetWindowPos(name, pos.x, pos.y, cond);
    }

    /**
     * Set named window position.
     */
    public static void setWindowPos(final String name, final float posX, final float posY, final int cond) {
        nSetWindowPos(name, posX, posY, cond);
    }

    private static native void nSetWindowPos(String name, float posX, float posY); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetWindowPos(name, pos);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    private static native void nSetWindowPos(String name, float posX, float posY, int cond); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetWindowPos(name, pos, cond);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static void setWindowSize(final String name, final ImVec2 size) {
        nSetWindowSize(name, size.x, size.y);
    }

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static void setWindowSize(final String name, final float sizeX, final float sizeY) {
        nSetWindowSize(name, sizeX, sizeY);
    }

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static void setWindowSize(final String name, final ImVec2 size, final int cond) {
        nSetWindowSize(name, size.x, size.y, cond);
    }

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static void setWindowSize(final String name, final float sizeX, final float sizeY, final int cond) {
        nSetWindowSize(name, sizeX, sizeY, cond);
    }

    private static native void nSetWindowSize(String name, float sizeX, float sizeY); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowSize(name, size);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    private static native void nSetWindowSize(String name, float sizeX, float sizeY, int cond); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowSize(name, size, cond);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    /**
     * Set named window collapsed state
     */
    public static void setWindowCollapsed(final String name, final boolean collapsed) {
        nSetWindowCollapsed(name, collapsed);
    }

    /**
     * Set named window collapsed state
     */
    public static void setWindowCollapsed(final String name, final boolean collapsed, final int cond) {
        nSetWindowCollapsed(name, collapsed, cond);
    }

    private static native void nSetWindowCollapsed(String name, boolean collapsed); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImGui::SetWindowCollapsed((const char *)name, (bool)collapsed);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    private static native void nSetWindowCollapsed(String name, boolean collapsed, int cond); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImGui::SetWindowCollapsed((const char *)name, (bool)collapsed, cond);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    /**
     * Set named window to be focused / top-most. Use NULL to remove focus.
     */
    public static void setWindowFocus(final String name) {
        nSetWindowFocus(name);
    }

    private static native void nSetWindowFocus(String name); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        ImGui::SetWindowFocus(name);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
    */

    // Content region
    // - Retrieve available space from a given point. GetContentRegionAvail() is frequently useful.
    // - Those functions are bound to be redesigned (they are confusing, incomplete and the Min/Max return values are in local window coordinates which increases confusion)

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static ImVec2 getContentRegionAvail() {
        final ImVec2 dst = new ImVec2();
        nGetContentRegionAvail(dst);
        return dst;
    }

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static float getContentRegionAvailX() {
        return nGetContentRegionAvailX();
    }

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static float getContentRegionAvailY() {
        return nGetContentRegionAvailY();
    }

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static void getContentRegionAvail(final ImVec2 dst) {
        nGetContentRegionAvail(dst);
    }

    private static native void nGetContentRegionAvail(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionAvail(), dst);
    */

    private static native float nGetContentRegionAvailX(); /*
        return ImGui::GetContentRegionAvail().x;
    */

    private static native float nGetContentRegionAvailY(); /*
        return ImGui::GetContentRegionAvail().y;
    */

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static ImVec2 getContentRegionMax() {
        final ImVec2 dst = new ImVec2();
        nGetContentRegionMax(dst);
        return dst;
    }

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static float getContentRegionMaxX() {
        return nGetContentRegionMaxX();
    }

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static float getContentRegionMaxY() {
        return nGetContentRegionMaxY();
    }

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static void getContentRegionMax(final ImVec2 dst) {
        nGetContentRegionMax(dst);
    }

    private static native void nGetContentRegionMax(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionMax(), dst);
    */

    private static native float nGetContentRegionMaxX(); /*
        return ImGui::GetContentRegionMax().x;
    */

    private static native float nGetContentRegionMaxY(); /*
        return ImGui::GetContentRegionMax().y;
    */

    /**
     * Content boundaries min for the full window (roughly (0,0)-Scroll), in window coordinates
     */
    public static ImVec2 getWindowContentRegionMin() {
        final ImVec2 dst = new ImVec2();
        nGetWindowContentRegionMin(dst);
        return dst;
    }

    /**
     * Content boundaries min for the full window (roughly (0,0)-Scroll), in window coordinates
     */
    public static float getWindowContentRegionMinX() {
        return nGetWindowContentRegionMinX();
    }

    /**
     * Content boundaries min for the full window (roughly (0,0)-Scroll), in window coordinates
     */
    public static float getWindowContentRegionMinY() {
        return nGetWindowContentRegionMinY();
    }

    /**
     * Content boundaries min for the full window (roughly (0,0)-Scroll), in window coordinates
     */
    public static void getWindowContentRegionMin(final ImVec2 dst) {
        nGetWindowContentRegionMin(dst);
    }

    private static native void nGetWindowContentRegionMin(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowContentRegionMin(), dst);
    */

    private static native float nGetWindowContentRegionMinX(); /*
        return ImGui::GetWindowContentRegionMin().x;
    */

    private static native float nGetWindowContentRegionMinY(); /*
        return ImGui::GetWindowContentRegionMin().y;
    */

    /**
     * Content boundaries max for the full window (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static ImVec2 getWindowContentRegionMax() {
        final ImVec2 dst = new ImVec2();
        nGetWindowContentRegionMax(dst);
        return dst;
    }

    /**
     * Content boundaries max for the full window (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static float getWindowContentRegionMaxX() {
        return nGetWindowContentRegionMaxX();
    }

    /**
     * Content boundaries max for the full window (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static float getWindowContentRegionMaxY() {
        return nGetWindowContentRegionMaxY();
    }

    /**
     * Content boundaries max for the full window (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static void getWindowContentRegionMax(final ImVec2 dst) {
        nGetWindowContentRegionMax(dst);
    }

    private static native void nGetWindowContentRegionMax(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowContentRegionMax(), dst);
    */

    private static native float nGetWindowContentRegionMaxX(); /*
        return ImGui::GetWindowContentRegionMax().x;
    */

    private static native float nGetWindowContentRegionMaxY(); /*
        return ImGui::GetWindowContentRegionMax().y;
    */

    // Windows Scrolling

    /**
     * Get scrolling amount [0 .. GetScrollMaxX()]
     */
    public static float getScrollX() {
        return nGetScrollX();
    }

    private static native float nGetScrollX(); /*
        return ImGui::GetScrollX();
    */

    /**
     * Get scrolling amount [0 .. GetScrollMaxY()]
     */
    public static float getScrollY() {
        return nGetScrollY();
    }

    private static native float nGetScrollY(); /*
        return ImGui::GetScrollY();
    */

    /**
     * Set scrolling amount [0 .. GetScrollMaxX()]
     */
    public static void setScrollX(final float scrollX) {
        nSetScrollX(scrollX);
    }

    private static native void nSetScrollX(float scrollX); /*
        ImGui::SetScrollX(scrollX);
    */

    /**
     * Set scrolling amount [0..GetScrollMaxY()]
     */
    public static void setScrollY(final float scrollY) {
        nSetScrollY(scrollY);
    }

    private static native void nSetScrollY(float scrollY); /*
        ImGui::SetScrollY(scrollY);
    */

    /**
     * Get maximum scrolling amount ~~ ContentSize.x - WindowSize.x - DecorationsSize.x
     */
    public static float getScrollMaxX() {
        return nGetScrollMaxX();
    }

    private static native float nGetScrollMaxX(); /*
        return ImGui::GetScrollMaxX();
    */

    /**
     * Get maximum scrolling amount ~~ ContentSize.y - WindowSize.y - DecorationsSize.y
     */
    public static float getScrollMaxY() {
        return nGetScrollMaxY();
    }

    private static native float nGetScrollMaxY(); /*
        return ImGui::GetScrollMaxY();
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static void setScrollHereX() {
        nSetScrollHereX();
    }

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static void setScrollHereX(final float centerXRatio) {
        nSetScrollHereX(centerXRatio);
    }

    private static native void nSetScrollHereX(); /*
        ImGui::SetScrollHereX();
    */

    private static native void nSetScrollHereX(float centerXRatio); /*
        ImGui::SetScrollHereX(centerXRatio);
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static void setScrollHereY() {
        nSetScrollHereY();
    }

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static void setScrollHereY(final float centerYRatio) {
        nSetScrollHereY(centerYRatio);
    }

    private static native void nSetScrollHereY(); /*
        ImGui::SetScrollHereY();
    */

    private static native void nSetScrollHereY(float centerYRatio); /*
        ImGui::SetScrollHereY(centerYRatio);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static void setScrollFromPosX(final float localX) {
        nSetScrollFromPosX(localX);
    }

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static void setScrollFromPosX(final float localX, final float centerXRatio) {
        nSetScrollFromPosX(localX, centerXRatio);
    }

    private static native void nSetScrollFromPosX(float localX); /*
        ImGui::SetScrollFromPosX(localX);
    */

    private static native void nSetScrollFromPosX(float localX, float centerXRatio); /*
        ImGui::SetScrollFromPosX(localX, centerXRatio);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static void setScrollFromPosY(final float localY) {
        nSetScrollFromPosY(localY);
    }

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static void setScrollFromPosY(final float localY, final float centerYRatio) {
        nSetScrollFromPosY(localY, centerYRatio);
    }

    private static native void nSetScrollFromPosY(float localY); /*
        ImGui::SetScrollFromPosY(localY);
    */

    private static native void nSetScrollFromPosY(float localY, float centerYRatio); /*
        ImGui::SetScrollFromPosY(localY, centerYRatio);
    */

    // Parameters stacks (shared)

    public static void pushFont(final ImFont font) {
        nPushFont(font.ptr);
    }

    private static native void nPushFont(long font); /*
        ImGui::PushFont(reinterpret_cast<ImFont*>(font));
    */

    public static void popFont() {
        nPopFont();
    }

    private static native void nPopFont(); /*
        ImGui::PopFont();
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
    public static void pushStyleColor(final int imGuiCol, final ImVec4 col) {
        nPushStyleColor(imGuiCol, col.x, col.y, col.z, col.w);
    }

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    public static void pushStyleColor(final int imGuiCol, final float colX, final float colY, final float colZ, final float colW) {
        nPushStyleColor(imGuiCol, colX, colY, colZ, colW);
    }

    private static native void nPushStyleColor(int imGuiCol, float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImGui::PushStyleColor(imGuiCol, col);
    */

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    public static void pushStyleColor(final int imGuiCol, final int col) {
        nPushStyleColor(imGuiCol, col);
    }

    private static native void nPushStyleColor(int imGuiCol, int col); /*
        ImGui::PushStyleColor(imGuiCol, col);
    */

    public static void popStyleColor() {
        nPopStyleColor();
    }

    public static void popStyleColor(final int count) {
        nPopStyleColor(count);
    }

    private static native void nPopStyleColor(); /*
        ImGui::PopStyleColor();
    */

    private static native void nPopStyleColor(int count); /*
        ImGui::PopStyleColor(count);
    */

    /**
     * Modify a style float variable. always use this if you modify the style after NewFrame().
     */
    public static void pushStyleVar(final int imGuiStyleVar, final float val) {
        nPushStyleVar(imGuiStyleVar, val);
    }

    private static native void nPushStyleVar(int imGuiStyleVar, float val); /*
        ImGui::PushStyleVar(imGuiStyleVar, val);
    */

    /**
     * Modify a style ImVec2 variable. always use this if you modify the style after NewFrame().
     */
    public static void pushStyleVar(final int imGuiStyleVar, final ImVec2 val) {
        nPushStyleVar(imGuiStyleVar, val.x, val.y);
    }

    /**
     * Modify a style ImVec2 variable. always use this if you modify the style after NewFrame().
     */
    public static void pushStyleVar(final int imGuiStyleVar, final float valX, final float valY) {
        nPushStyleVar(imGuiStyleVar, valX, valY);
    }

    private static native void nPushStyleVar(int imGuiStyleVar, float valX, float valY); /*MANUAL
        ImVec2 val = ImVec2(valX, valY);
        ImGui::PushStyleVar(imGuiStyleVar, val);
    */

    public static void popStyleVar() {
        nPopStyleVar();
    }

    public static void popStyleVar(final int count) {
        nPopStyleVar(count);
    }

    private static native void nPopStyleVar(); /*
        ImGui::PopStyleVar();
    */

    private static native void nPopStyleVar(int count); /*
        ImGui::PopStyleVar(count);
    */

    /**
     * Allow focusing using TAB/Shift-TAB, enabled by default but you can disable it for certain widgets
     */
    public static void pushAllowKeyboardFocus(final boolean allowKeyboardFocus) {
        nPushAllowKeyboardFocus(allowKeyboardFocus);
    }

    private static native void nPushAllowKeyboardFocus(boolean allowKeyboardFocus); /*
        ImGui::PushAllowKeyboardFocus(allowKeyboardFocus);
    */

    public static void popAllowKeyboardFocus() {
        nPopAllowKeyboardFocus();
    }

    private static native void nPopAllowKeyboardFocus(); /*
        ImGui::PopAllowKeyboardFocus();
    */

    /**
     * In 'repeat' mode, Button*() functions return repeated true in a typematic manner (using io.KeyRepeatDelay/io.KeyRepeatRate setting).
     * Note that you can call IsItemActive() after any Button() to tell if the button is held in the current frame.
     */
    public static void pushButtonRepeat(final boolean repeat) {
        nPushButtonRepeat(repeat);
    }

    private static native void nPushButtonRepeat(boolean repeat); /*
        ImGui::PushButtonRepeat(repeat);
    */

    public static void popButtonRepeat() {
        nPopButtonRepeat();
    }

    private static native void nPopButtonRepeat(); /*
        ImGui::PopButtonRepeat();
    */

    // Parameters stacks (current window)

    /**
     * Push width of items for common large "item+label" widgets. {@code > 0.0f}: width in pixels,
     * {@code <0.0f} align xx pixels to the right of window (so -1.0f always align width to the right side).
     */
    public static void pushItemWidth(final float itemWidth) {
        nPushItemWidth(itemWidth);
    }

    private static native void nPushItemWidth(float itemWidth); /*
        ImGui::PushItemWidth(itemWidth);
    */

    public static void popItemWidth() {
        nPopItemWidth();
    }

    private static native void nPopItemWidth(); /*
        ImGui::PopItemWidth();
    */

    /**
     * Set width of the _next_ common large "item+label" widget. {@code > 0.0f}: width in pixels,
     * {@code <0.0f} align xx pixels to the right of window (so -1.0f always align width to the right side)
     */
    public static void setNextItemWidth(final float itemWidth) {
        nSetNextItemWidth(itemWidth);
    }

    private static native void nSetNextItemWidth(float itemWidth); /*
        ImGui::SetNextItemWidth(itemWidth);
    */

    /**
     * Width of item given pushed settings and current cursor position. NOT necessarily the width of last item unlike most 'Item' functions.
     */
    public static float calcItemWidth() {
        return nCalcItemWidth();
    }

    private static native float nCalcItemWidth(); /*
        return ImGui::CalcItemWidth();
    */

    /**
     * Push Word-wrapping positions for Text*() commands. {@code < 0.0f}: no wrapping; 0.0f: wrap to end of window (or column); {@code > 0.0f}: wrap at
     * 'wrap_posX' position in window local space
     */
    public static void pushTextWrapPos() {
        nPushTextWrapPos();
    }

    /**
     * Push Word-wrapping positions for Text*() commands. {@code < 0.0f}: no wrapping; 0.0f: wrap to end of window (or column); {@code > 0.0f}: wrap at
     * 'wrap_posX' position in window local space
     */
    public static void pushTextWrapPos(final float wrapLocalPosX) {
        nPushTextWrapPos(wrapLocalPosX);
    }

    private static native void nPushTextWrapPos(); /*
        ImGui::PushTextWrapPos();
    */

    private static native void nPushTextWrapPos(float wrapLocalPosX); /*
        ImGui::PushTextWrapPos(wrapLocalPosX);
    */

    public static void popTextWrapPos() {
        nPopTextWrapPos();
    }

    private static native void nPopTextWrapPos(); /*
        ImGui::PopTextWrapPos();
    */

    // Style read access

    private static final ImFont _GETFONT_1 = new ImFont(0);

    /**
     * Get current font.
     */
    public static ImFont getFont() {
        _GETFONT_1.ptr = nGetFont();
        return _GETFONT_1;
    }

    private static native long nGetFont(); /*
        return (uintptr_t)ImGui::GetFont();
    */

    /**
     * Get current font size (= height in pixels) of current font with current scale applied
     */
    public static int getFontSize() {
        return nGetFontSize();
    }

    private static native int nGetFontSize(); /*
        return ImGui::GetFontSize();
    */

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static ImVec2 getFontTexUvWhitePixel() {
        final ImVec2 dst = new ImVec2();
        nGetFontTexUvWhitePixel(dst);
        return dst;
    }

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static float getFontTexUvWhitePixelX() {
        return nGetFontTexUvWhitePixelX();
    }

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static float getFontTexUvWhitePixelY() {
        return nGetFontTexUvWhitePixelY();
    }

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static void getFontTexUvWhitePixel(final ImVec2 dst) {
        nGetFontTexUvWhitePixel(dst);
    }

    private static native void nGetFontTexUvWhitePixel(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetFontTexUvWhitePixel(), dst);
    */

    private static native float nGetFontTexUvWhitePixelX(); /*
        return ImGui::GetFontTexUvWhitePixel().x;
    */

    private static native float nGetFontTexUvWhitePixelY(); /*
        return ImGui::GetFontTexUvWhitePixel().y;
    */

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier, packed as a 32-bit value suitable for ImDrawList.
     */
    public static int getColorU32(final int idx) {
        return nGetColorU32(idx);
    }

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier, packed as a 32-bit value suitable for ImDrawList.
     */
    public static int getColorU32(final int idx, final float alphaMul) {
        return nGetColorU32(idx, alphaMul);
    }

    private static native int nGetColorU32(int idx); /*
        return ImGui::GetColorU32(static_cast<ImGuiCol>(idx));
    */

    private static native int nGetColorU32(int idx, float alphaMul); /*
        return ImGui::GetColorU32(static_cast<ImGuiCol>(idx), alphaMul);
    */

    /**
     * Retrieve given color with style alpha applied, packed as a 32-bit value suitable for ImDrawList.
     */
    public static int getColorU32(final ImVec4 col) {
        return nGetColorU32(col.x, col.y, col.z, col.w);
    }

    /**
     * Retrieve given color with style alpha applied, packed as a 32-bit value suitable for ImDrawList.
     */
    public static int getColorU32(final float colX, final float colY, final float colZ, final float colW) {
        return nGetColorU32(colX, colY, colZ, colW);
    }

    private static native int nGetColorU32(float colX, float colY, float colZ, float colW); /*MANUAL
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        auto _result = ImGui::GetColorU32(col);
        return _result;
    */

    /**
     * Retrieve given color with style alpha applied, packed as a 32-bit value suitable for ImDrawList.
     */
    public static int getColorU32i(final int col) {
        return nGetColorU32i(col);
    }

    private static native int nGetColorU32i(int col); /*
        return ImGui::GetColorU32(static_cast<ImU32>(col));
    */

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static ImVec4 getStyleColorVec4(final int imGuiColIdx) {
        final ImVec4 dst = new ImVec4();
        nGetStyleColorVec4(dst, imGuiColIdx);
        return dst;
    }

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static float getStyleColorVec4X(final int imGuiColIdx) {
        return nGetStyleColorVec4X(imGuiColIdx);
    }

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static float getStyleColorVec4Y(final int imGuiColIdx) {
        return nGetStyleColorVec4Y(imGuiColIdx);
    }

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static float getStyleColorVec4Z(final int imGuiColIdx) {
        return nGetStyleColorVec4Z(imGuiColIdx);
    }

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static float getStyleColorVec4W(final int imGuiColIdx) {
        return nGetStyleColorVec4W(imGuiColIdx);
    }

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static void getStyleColorVec4(final ImVec4 dst, final int imGuiColIdx) {
        nGetStyleColorVec4(dst, imGuiColIdx);
    }

    private static native void nGetStyleColorVec4(ImVec4 dst, int imGuiColIdx); /*
        Jni::ImVec4Cpy(env, ImGui::GetStyleColorVec4(imGuiColIdx), dst);
    */

    private static native float nGetStyleColorVec4X(int imGuiColIdx); /*
        return ImGui::GetStyleColorVec4(imGuiColIdx).x;
    */

    private static native float nGetStyleColorVec4Y(int imGuiColIdx); /*
        return ImGui::GetStyleColorVec4(imGuiColIdx).y;
    */

    private static native float nGetStyleColorVec4Z(int imGuiColIdx); /*
        return ImGui::GetStyleColorVec4(imGuiColIdx).z;
    */

    private static native float nGetStyleColorVec4W(int imGuiColIdx); /*
        return ImGui::GetStyleColorVec4(imGuiColIdx).w;
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
    public static void separator() {
        nSeparator();
    }

    private static native void nSeparator(); /*
        ImGui::Separator();
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static void sameLine() {
        nSameLine();
    }

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static void sameLine(final float offsetFromStartX) {
        nSameLine(offsetFromStartX);
    }

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static void sameLine(final float offsetFromStartX, final float spacing) {
        nSameLine(offsetFromStartX, spacing);
    }

    private static native void nSameLine(); /*
        ImGui::SameLine();
    */

    private static native void nSameLine(float offsetFromStartX); /*
        ImGui::SameLine(offsetFromStartX);
    */

    private static native void nSameLine(float offsetFromStartX, float spacing); /*
        ImGui::SameLine(offsetFromStartX, spacing);
    */

    /**
     * Undo a SameLine() or force a new line when in an horizontal-layout context.
     */
    public static void newLine() {
        nNewLine();
    }

    private static native void nNewLine(); /*
        ImGui::NewLine();
    */

    /**
     * Add vertical spacing.
     */
    public static void spacing() {
        nSpacing();
    }

    private static native void nSpacing(); /*
        ImGui::Spacing();
    */

    /**
     * Add a dummy item of given size. unlike InvisibleButton(), Dummy() won't take the mouse click or be navigable into.
     */
    public static void dummy(final ImVec2 size) {
        nDummy(size.x, size.y);
    }

    /**
     * Add a dummy item of given size. unlike InvisibleButton(), Dummy() won't take the mouse click or be navigable into.
     */
    public static void dummy(final float sizeX, final float sizeY) {
        nDummy(sizeX, sizeY);
    }

    private static native void nDummy(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::Dummy(size);
    */

    /**
     * Move content position toward the right, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static void indent() {
        nIndent();
    }

    /**
     * Move content position toward the right, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static void indent(final float indentW) {
        nIndent(indentW);
    }

    private static native void nIndent(); /*
        ImGui::Indent();
    */

    private static native void nIndent(float indentW); /*
        ImGui::Indent(indentW);
    */

    /**
     * Move content position back to the left, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static void unindent() {
        nUnindent();
    }

    /**
     * Move content position back to the left, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    public static void unindent(final float indentW) {
        nUnindent(indentW);
    }

    private static native void nUnindent(); /*
        ImGui::Unindent();
    */

    private static native void nUnindent(float indentW); /*
        ImGui::Unindent(indentW);
    */

    /**
     * Lock horizontal starting position
     */
    public static void beginGroup() {
        nBeginGroup();
    }

    private static native void nBeginGroup(); /*
        ImGui::BeginGroup();
    */

    /**
     * Unlock horizontal starting position + capture the whole group bounding box into one "item" (so you can use IsItemHovered() or layout primitives such as SameLine() on whole group, etc.)
     */
    public static void endGroup() {
        nEndGroup();
    }

    private static native void nEndGroup(); /*
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
        final ImVec2 dst = new ImVec2();
        nGetCursorPos(dst);
        return dst;
    }

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static float getCursorPosX() {
        return nGetCursorPosX();
    }

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static float getCursorPosY() {
        return nGetCursorPosY();
    }

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static void getCursorPos(final ImVec2 dst) {
        nGetCursorPos(dst);
    }

    private static native void nGetCursorPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorPos(), dst);
    */

    private static native float nGetCursorPosX(); /*
        return ImGui::GetCursorPos().x;
    */

    private static native float nGetCursorPosY(); /*
        return ImGui::GetCursorPos().y;
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static void setCursorPos(final ImVec2 pos) {
        nSetCursorPos(pos.x, pos.y);
    }

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static void setCursorPos(final float posX, final float posY) {
        nSetCursorPos(posX, posY);
    }

    private static native void nSetCursorPos(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetCursorPos(pos);
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static void setCursorPosX(final float x) {
        nSetCursorPosX(x);
    }

    private static native void nSetCursorPosX(float x); /*
        ImGui::SetCursorPosX(x);
    */

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static void setCursorPosY(final float y) {
        nSetCursorPosY(y);
    }

    private static native void nSetCursorPosY(float y); /*
        ImGui::SetCursorPosY(y);
    */

    /**
     * Initial cursor position in window coordinates
     */
    public static ImVec2 getCursorStartPos() {
        final ImVec2 dst = new ImVec2();
        nGetCursorStartPos(dst);
        return dst;
    }

    /**
     * Initial cursor position in window coordinates
     */
    public static float getCursorStartPosX() {
        return nGetCursorStartPosX();
    }

    /**
     * Initial cursor position in window coordinates
     */
    public static float getCursorStartPosY() {
        return nGetCursorStartPosY();
    }

    /**
     * Initial cursor position in window coordinates
     */
    public static void getCursorStartPos(final ImVec2 dst) {
        nGetCursorStartPos(dst);
    }

    private static native void nGetCursorStartPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorStartPos(), dst);
    */

    private static native float nGetCursorStartPosX(); /*
        return ImGui::GetCursorStartPos().x;
    */

    private static native float nGetCursorStartPosY(); /*
        return ImGui::GetCursorStartPos().y;
    */

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static ImVec2 getCursorScreenPos() {
        final ImVec2 dst = new ImVec2();
        nGetCursorScreenPos(dst);
        return dst;
    }

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static float getCursorScreenPosX() {
        return nGetCursorScreenPosX();
    }

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static float getCursorScreenPosY() {
        return nGetCursorScreenPosY();
    }

    /**
     * Cursor position in absolute coordinates (useful to work with ImDrawList API).
     * Generally top-left == GetMainViewport().Pos == (0,0) in single viewport mode,
     * and bottom-right == GetMainViewport().Pos+Size == io.DisplaySize in single-viewport mode.
     */
    public static void getCursorScreenPos(final ImVec2 dst) {
        nGetCursorScreenPos(dst);
    }

    private static native void nGetCursorScreenPos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorScreenPos(), dst);
    */

    private static native float nGetCursorScreenPosX(); /*
        return ImGui::GetCursorScreenPos().x;
    */

    private static native float nGetCursorScreenPosY(); /*
        return ImGui::GetCursorScreenPos().y;
    */

    /**
     * Cursor position in absolute coordinates.
     */
    public static void setCursorScreenPos(final ImVec2 pos) {
        nSetCursorScreenPos(pos.x, pos.y);
    }

    /**
     * Cursor position in absolute coordinates.
     */
    public static void setCursorScreenPos(final float posX, final float posY) {
        nSetCursorScreenPos(posX, posY);
    }

    private static native void nSetCursorScreenPos(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetCursorScreenPos(pos);
    */

    /**
     * Vertically align upcoming text baseline to FramePadding.y so that it will align properly to regularly framed items (call if you have text on a line before a framed item)
     */
    public static void alignTextToFramePadding() {
        nAlignTextToFramePadding();
    }

    private static native void nAlignTextToFramePadding(); /*
        ImGui::AlignTextToFramePadding();
    */

    /**
     * ~ FontSize
     */
    public static float getTextLineHeight() {
        return nGetTextLineHeight();
    }

    private static native float nGetTextLineHeight(); /*
        return ImGui::GetTextLineHeight();
    */

    /**
     * ~ FontSize + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of text)
     */
    public static float getTextLineHeightWithSpacing() {
        return nGetTextLineHeightWithSpacing();
    }

    private static native float nGetTextLineHeightWithSpacing(); /*
        return ImGui::GetTextLineHeightWithSpacing();
    */

    /**
     * ~ FontSize + style.FramePadding.y * 2
     */
    public static float getFrameHeight() {
        return nGetFrameHeight();
    }

    private static native float nGetFrameHeight(); /*
        return ImGui::GetFrameHeight();
    */

    /**
     * ~ FontSize + style.FramePadding.y * 2 + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of framed widgets)
     */
    public static float getFrameHeightWithSpacing() {
        return nGetFrameHeightWithSpacing();
    }

    private static native float nGetFrameHeightWithSpacing(); /*
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
    public static void pushID(final String strId) {
        nPushID(strId);
    }

    private static native void nPushID(String strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImGui::PushID(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
    */

    /**
     * Push string into the ID stack (will hash string).
     */
    public static void pushID(final String strIdBegin, final String strIdEnd) {
        nPushID(strIdBegin, strIdEnd);
    }

    private static native void nPushID(String strIdBegin, String strIdEnd); /*MANUAL
        auto strIdBegin = obj_strIdBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strIdBegin, JNI_FALSE);
        auto strIdEnd = obj_strIdEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strIdEnd, JNI_FALSE);
        ImGui::PushID(strIdBegin, strIdEnd);
        if (strIdBegin != NULL) env->ReleaseStringUTFChars(obj_strIdBegin, strIdBegin);
        if (strIdEnd != NULL) env->ReleaseStringUTFChars(obj_strIdEnd, strIdEnd);
    */

    /**
     * Push pointer into the ID stack (will hash pointer).
     */
    public static void pushID(final long ptrId) {
        nPushID(ptrId);
    }

    private static native void nPushID(long ptrId); /*
        ImGui::PushID((void*)ptrId);
    */

    /**
     * Push integer into the ID stack (will hash integer).
     */
    public static void pushID(final int intId) {
        nPushID(intId);
    }

    private static native void nPushID(int intId); /*
        ImGui::PushID(intId);
    */

    /**
     * Pop from the ID stack.
     */
    public static void popID() {
        nPopID();
    }

    private static native void nPopID(); /*
        ImGui::PopID();
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static int getID(final String strId) {
        return nGetID(strId);
    }

    private static native int nGetID(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::GetID(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static int getID(final String strIdBegin, final String strIdEnd) {
        return nGetID(strIdBegin, strIdEnd);
    }

    private static native int nGetID(String obj_strIdBegin, String obj_strIdEnd); /*MANUAL
        auto strIdBegin = obj_strIdBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strIdBegin, JNI_FALSE);
        auto strIdEnd = obj_strIdEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strIdEnd, JNI_FALSE);
        auto _result = ImGui::GetID(strIdBegin, strIdEnd);
        if (strIdBegin != NULL) env->ReleaseStringUTFChars(obj_strIdBegin, strIdBegin);
        if (strIdEnd != NULL) env->ReleaseStringUTFChars(obj_strIdEnd, strIdEnd);
        return _result;
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static int getID(final long ptrId) {
        return nGetID(ptrId);
    }

    private static native int nGetID(long ptrId); /*
        return ImGui::GetID((void*)ptrId);
    */

    // Widgets: Text

    /**
     * Raw text without formatting. Roughly equivalent to Text("%s", text) but:
     * A) doesn't require null terminated string if 'textEnd' is specified,
     * B) it's faster, no memory copy is done, no buffer size limits, recommended for long chunks of text.
     */
    public static void textUnformatted(final String text) {
        nTextUnformatted(text);
    }

    /**
     * Raw text without formatting. Roughly equivalent to Text("%s", text) but:
     * A) doesn't require null terminated string if 'textEnd' is specified,
     * B) it's faster, no memory copy is done, no buffer size limits, recommended for long chunks of text.
     */
    public static void textUnformatted(final String text, final String textEnd) {
        nTextUnformatted(text, textEnd);
    }

    private static native void nTextUnformatted(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::TextUnformatted(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native void nTextUnformatted(String text, String textEnd); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto textEnd = obj_textEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_textEnd, JNI_FALSE);
        ImGui::TextUnformatted(text, textEnd);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        if (textEnd != NULL) env->ReleaseStringUTFChars(obj_textEnd, textEnd);
    */

    /**
     * Formatted text
     */
    public static void text(final String text) {
        nText(text);
    }

    private static native void nText(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::Text(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    public static void textColored(final ImVec4 col, final String text) {
        nTextColored(col.x, col.y, col.z, col.w, text);
    }

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    public static void textColored(final float colX, final float colY, final float colZ, final float colW, final String text) {
        nTextColored(colX, colY, colZ, colW, text);
    }

    private static native void nTextColored(float colX, float colY, float colZ, float colW, String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImGui::TextColored(col, text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
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
    public static void textDisabled(final String text) {
        nTextDisabled(text);
    }

    private static native void nTextDisabled(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::TextDisabled(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    /**
     * Shortcut for PushTextWrapPos(0.0f); Text(fmt, ...); PopTextWrapPos();.
     * Note that this won't work on an auto-resizing window if there's no other widgets to extend the window width,
     * yoy may need to set a size using SetNextWindowSize().
     */
    public static void textWrapped(final String text) {
        nTextWrapped(text);
    }

    private static native void nTextWrapped(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::TextWrapped(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    /**
     * Display text+label aligned the same way as value+label widgets
     */
    public static void labelText(final String label, final String text) {
        nLabelText(label, text);
    }

    private static native void nLabelText(String label, String text); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::LabelText(label, text, NULL);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    /**
     * Shortcut for Bullet()+Text()
     */
    public static void bulletText(final String text) {
        nBulletText(text);
    }

    private static native void nBulletText(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::BulletText(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    // Widgets: Main
    // - Most widgets return true when the value has been changed or when pressed/selected
    // - You may also use one of the many IsItemXXX functions (e.g. IsItemActive, IsItemHovered, etc.) to query widget state.

    /**
     * Button
     */
    public static boolean button(final String label) {
        return nButton(label);
    }

    /**
     * Button
     */
    public static boolean button(final String label, final ImVec2 size) {
        return nButton(label, size.x, size.y);
    }

    /**
     * Button
     */
    public static boolean button(final String label, final float sizeX, final float sizeY) {
        return nButton(label, sizeX, sizeY);
    }

    private static native boolean nButton(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::Button(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nButton(String obj_label, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Button(label, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Button with FramePadding=(0,0) to easily embed within text
     */
    public static boolean smallButton(final String label) {
        return nSmallButton(label);
    }

    private static native boolean nSmallButton(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::SmallButton(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static boolean invisibleButton(final String strId, final ImVec2 size) {
        return nInvisibleButton(strId, size.x, size.y);
    }

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static boolean invisibleButton(final String strId, final float sizeX, final float sizeY) {
        return nInvisibleButton(strId, sizeX, sizeY);
    }

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static boolean invisibleButton(final String strId, final ImVec2 size, final int imGuiButtonFlags) {
        return nInvisibleButton(strId, size.x, size.y, imGuiButtonFlags);
    }

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static boolean invisibleButton(final String strId, final float sizeX, final float sizeY, final int imGuiButtonFlags) {
        return nInvisibleButton(strId, sizeX, sizeY, imGuiButtonFlags);
    }

    private static native boolean nInvisibleButton(String obj_strId, float sizeX, float sizeY); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::InvisibleButton(strId, size);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nInvisibleButton(String obj_strId, float sizeX, float sizeY, int imGuiButtonFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::InvisibleButton(strId, size, imGuiButtonFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    /**
     * Square button with an arrow shape
     */
    public static boolean arrowButton(final String strId, final int dir) {
        return nArrowButton(strId, dir);
    }

    private static native boolean nArrowButton(String obj_strId, int dir); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::ArrowButton(strId, dir);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    public static void image(final long userTextureId, final ImVec2 size) {
        nImage(userTextureId, size.x, size.y);
    }

    public static void image(final long userTextureId, final float sizeX, final float sizeY) {
        nImage(userTextureId, sizeX, sizeY);
    }

    public static void image(final long userTextureId, final ImVec2 size, final ImVec2 uv0) {
        nImage(userTextureId, size.x, size.y, uv0.x, uv0.y);
    }

    public static void image(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y) {
        nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y);
    }

    public static void image(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1) {
        nImage(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    public static void image(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    public static void image(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final ImVec4 tintCol) {
        nImage(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    public static void image(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW);
    }

    public static void image(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final ImVec4 tintCol, final ImVec4 borderCol) {
        nImage(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, tintCol.x, tintCol.y, tintCol.z, tintCol.w, borderCol.x, borderCol.y, borderCol.z, borderCol.w);
    }

    public static void image(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW, final float borderColX, final float borderColY, final float borderColZ, final float borderColW) {
        nImage(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW, borderColX, borderColY, borderColZ, borderColW);
    }

    private static native void nImage(long userTextureId, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::Image((ImTextureID)(uintptr_t)userTextureId, size);
    */

    private static native void nImage(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImGui::Image((ImTextureID)(uintptr_t)userTextureId, size, uv0);
    */

    private static native void nImage(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImGui::Image((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1);
    */

    private static native void nImage(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 tintCol = ImVec4(tintColX, tintColY, tintColZ, tintColW);
        ImGui::Image((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1, tintCol);
    */

    private static native void nImage(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float tintColX, float tintColY, float tintColZ, float tintColW, float borderColX, float borderColY, float borderColZ, float borderColW); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 tintCol = ImVec4(tintColX, tintColY, tintColZ, tintColW);
        ImVec4 borderCol = ImVec4(borderColX, borderColY, borderColZ, borderColW);
        ImGui::Image((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1, tintCol, borderCol);
    */

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size) {
        return nImageButton(userTextureId, size.x, size.y);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY) {
        return nImageButton(userTextureId, sizeX, sizeY);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size, final ImVec2 uv0) {
        return nImageButton(userTextureId, size.x, size.y, uv0.x, uv0.y);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y) {
        return nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1) {
        return nImageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        return nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final int framePadding) {
        return nImageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, framePadding);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding) {
        return nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final int framePadding, final ImVec4 bgCol) {
        return nImageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, framePadding, bgCol.x, bgCol.y, bgCol.z, bgCol.w);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgColX, bgColY, bgColZ, bgColW);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final int framePadding, final ImVec4 bgCol, final ImVec4 tintCol) {
        return nImageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, framePadding, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final ImVec4 bgCol, final ImVec4 tintCol) {
        return nImageButton(userTextureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w);
    }

    /**
     * {@code <0} framePadding uses default frame padding settings. 0 for no padding
     */
    public static boolean imageButton(final long userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return nImageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size);
        return _result;
    */

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size, uv0);
        return _result;
    */

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1);
        return _result;
    */

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1, framePadding);
        return _result;
    */

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding, float bgColX, float bgColY, float bgColZ, float bgColW); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 bgCol = ImVec4(bgColX, bgColY, bgColZ, bgColW);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1, framePadding, bgCol);
        return _result;
    */

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, int framePadding, float bgColX, float bgColY, float bgColZ, float bgColW, float tintColX, float tintColY, float tintColZ, float tintColW); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 bgCol = ImVec4(bgColX, bgColY, bgColZ, bgColW);
        ImVec4 tintCol = ImVec4(tintColX, tintColY, tintColZ, tintColW);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1, framePadding, bgCol, tintCol);
        return _result;
    */

    private static native boolean nImageButton(long userTextureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float bgColX, float bgColY, float bgColZ, float bgColW, float tintColX, float tintColY, float tintColZ, float tintColW); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 bgCol = ImVec4(bgColX, bgColY, bgColZ, bgColW);
        ImVec4 tintCol = ImVec4(tintColX, tintColY, tintColZ, tintColW);
        auto _result = ImGui::ImageButton((ImTextureID)(uintptr_t)userTextureId, size, uv0, uv1, -1, bgCol, tintCol);
        return _result;
    */

    public static boolean checkbox(String label, boolean active) {
        return nCheckbox(label, active);
    }

    private static native boolean nCheckbox(String label, boolean active); /*
        bool flag = (bool)active;
        return ImGui::Checkbox(label, &flag);
    */

    public static boolean checkbox(final String label, final ImBoolean data) {
        return nCheckbox(label, data != null ? data.getData() : null);
    }

    private static native boolean nCheckbox(String obj_label, boolean[] obj_data); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto data = obj_data == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_data, JNI_FALSE);
        auto _result = ImGui::Checkbox(label, (data != NULL ? &data[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (data != NULL) env->ReleasePrimitiveArrayCritical(obj_data, data, JNI_FALSE);
        return _result;
    */

    public static boolean checkboxFlags(final String label, final ImInt flags, final int flagsValue) {
        return nCheckboxFlags(label, flags != null ? flags.getData() : null, flagsValue);
    }

    private static native boolean nCheckboxFlags(String obj_label, int[] obj_flags, int flagsValue); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto flags = obj_flags == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_flags, JNI_FALSE);
        auto _result = ImGui::CheckboxFlags(label, (flags != NULL ? &flags[0] : NULL), flagsValue);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (flags != NULL) env->ReleasePrimitiveArrayCritical(obj_flags, flags, JNI_FALSE);
        return _result;
    */

    /**
     * Use with e.g. if (RadioButton("one", my_value==1)) { my_value = 1; }
     */
    public static boolean radioButton(final String label, final boolean active) {
        return nRadioButton(label, active);
    }

    private static native boolean nRadioButton(String obj_label, boolean active); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::RadioButton(label, active);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Shortcut to handle the above pattern when value is an integer
     */
    public static boolean radioButton(final String label, final ImInt v, final int vButton) {
        return nRadioButton(label, v != null ? v.getData() : null, vButton);
    }

    private static native boolean nRadioButton(String obj_label, int[] obj_v, int vButton); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::RadioButton(label, (v != NULL ? &v[0] : NULL), vButton);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static void progressBar(final float fraction) {
        nProgressBar(fraction);
    }

    public static void progressBar(final float fraction, final ImVec2 size) {
        nProgressBar(fraction, size.x, size.y);
    }

    public static void progressBar(final float fraction, final float sizeX, final float sizeY) {
        nProgressBar(fraction, sizeX, sizeY);
    }

    public static void progressBar(final float fraction, final ImVec2 size, final String overlay) {
        nProgressBar(fraction, size.x, size.y, overlay);
    }

    public static void progressBar(final float fraction, final float sizeX, final float sizeY, final String overlay) {
        nProgressBar(fraction, sizeX, sizeY, overlay);
    }

    public static void progressBar(final float fraction, final String overlay) {
        nProgressBar(fraction, overlay);
    }

    private static native void nProgressBar(float fraction); /*
        ImGui::ProgressBar(fraction);
    */

    private static native void nProgressBar(float fraction, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::ProgressBar(fraction, size);
    */

    private static native void nProgressBar(float fraction, float sizeX, float sizeY, String overlay); /*MANUAL
        auto overlay = obj_overlay == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlay, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::ProgressBar(fraction, size, overlay);
        if (overlay != NULL) env->ReleaseStringUTFChars(obj_overlay, overlay);
    */

    private static native void nProgressBar(float fraction, String overlay); /*MANUAL
        auto overlay = obj_overlay == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlay, JNI_FALSE);
        ImGui::ProgressBar(fraction, ImVec2(-FLT_MIN,0), overlay);
        if (overlay != NULL) env->ReleaseStringUTFChars(obj_overlay, overlay);
    */

    /**
     * Draw a small circle + keep the cursor on the same line. advance cursor x position by GetTreeNodeToLabelSpacing(), same distance that TreeNode() uses
     */
    public static void bullet() {
        nBullet();
    }

    private static native void nBullet(); /*
        ImGui::Bullet();
    */

    // Widgets: Combo Box
    // - The BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
    // - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

    public static boolean beginCombo(final String label, final String previewValue) {
        return nBeginCombo(label, previewValue);
    }

    public static boolean beginCombo(final String label, final String previewValue, final int imGuiComboFlags) {
        return nBeginCombo(label, previewValue, imGuiComboFlags);
    }

    private static native boolean nBeginCombo(String obj_label, String obj_previewValue); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto previewValue = obj_previewValue == NULL ? NULL : (char*)env->GetStringUTFChars(obj_previewValue, JNI_FALSE);
        auto _result = ImGui::BeginCombo(label, previewValue);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (previewValue != NULL) env->ReleaseStringUTFChars(obj_previewValue, previewValue);
        return _result;
    */

    private static native boolean nBeginCombo(String obj_label, String obj_previewValue, int imGuiComboFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto previewValue = obj_previewValue == NULL ? NULL : (char*)env->GetStringUTFChars(obj_previewValue, JNI_FALSE);
        auto _result = ImGui::BeginCombo(label, previewValue, imGuiComboFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (previewValue != NULL) env->ReleaseStringUTFChars(obj_previewValue, previewValue);
        return _result;
    */

    /**
     * Only call EndCombo() if BeginCombo() returns true!
     */
    public static void endCombo() {
        nEndCombo();
    }

    private static native void nEndCombo(); /*
        ImGui::EndCombo();
    */

    public static boolean combo(final String label, final ImInt currentItem, final String[] items) {
        return nCombo(label, currentItem != null ? currentItem.getData() : null, items, items.length);
    }

    public static boolean combo(final String label, final ImInt currentItem, final String[] items, final int popupMaxHeightInItems) {
        return nCombo(label, currentItem != null ? currentItem.getData() : null, items, items.length, popupMaxHeightInItems);
    }

    private static native boolean nCombo(String obj_label, int[] obj_currentItem, String[] obj_items, int itemsCount); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto currentItem = obj_currentItem == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_currentItem, JNI_FALSE);
        const char* items[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            items[i] = rawStr;
        };
        auto _result = ImGui::Combo(label, (currentItem != NULL ? &currentItem[0] : NULL), items, itemsCount);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (currentItem != NULL) env->ReleasePrimitiveArrayCritical(obj_currentItem, currentItem, JNI_FALSE);
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            env->ReleaseStringUTFChars(str, items[i]);
        };
        return _result;
    */

    private static native boolean nCombo(String obj_label, int[] obj_currentItem, String[] obj_items, int itemsCount, int popupMaxHeightInItems); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto currentItem = obj_currentItem == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_currentItem, JNI_FALSE);
        const char* items[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            items[i] = rawStr;
        };
        auto _result = ImGui::Combo(label, (currentItem != NULL ? &currentItem[0] : NULL), items, itemsCount, popupMaxHeightInItems);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (currentItem != NULL) env->ReleasePrimitiveArrayCritical(obj_currentItem, currentItem, JNI_FALSE);
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            env->ReleaseStringUTFChars(str, items[i]);
        };
        return _result;
    */

    /**
     * Separate items with \0 within a string, end item-list with \0\0. e.g. "One\0Two\0Three\0"
     */
    public static boolean combo(final String label, final ImInt currentItem, final String itemsSeparatedByZeros) {
        return nCombo(label, currentItem != null ? currentItem.getData() : null, itemsSeparatedByZeros);
    }

    /**
     * Separate items with \0 within a string, end item-list with \0\0. e.g. "One\0Two\0Three\0"
     */
    public static boolean combo(final String label, final ImInt currentItem, final String itemsSeparatedByZeros, final int popupMaxHeightInItems) {
        return nCombo(label, currentItem != null ? currentItem.getData() : null, itemsSeparatedByZeros, popupMaxHeightInItems);
    }

    private static native boolean nCombo(String obj_label, int[] obj_currentItem, String obj_itemsSeparatedByZeros); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto currentItem = obj_currentItem == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_currentItem, JNI_FALSE);
        auto itemsSeparatedByZeros = obj_itemsSeparatedByZeros == NULL ? NULL : (char*)env->GetStringUTFChars(obj_itemsSeparatedByZeros, JNI_FALSE);
        auto _result = ImGui::Combo(label, (currentItem != NULL ? &currentItem[0] : NULL), itemsSeparatedByZeros);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (currentItem != NULL) env->ReleasePrimitiveArrayCritical(obj_currentItem, currentItem, JNI_FALSE);
        if (itemsSeparatedByZeros != NULL) env->ReleaseStringUTFChars(obj_itemsSeparatedByZeros, itemsSeparatedByZeros);
        return _result;
    */

    private static native boolean nCombo(String obj_label, int[] obj_currentItem, String obj_itemsSeparatedByZeros, int popupMaxHeightInItems); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto currentItem = obj_currentItem == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_currentItem, JNI_FALSE);
        auto itemsSeparatedByZeros = obj_itemsSeparatedByZeros == NULL ? NULL : (char*)env->GetStringUTFChars(obj_itemsSeparatedByZeros, JNI_FALSE);
        auto _result = ImGui::Combo(label, (currentItem != NULL ? &currentItem[0] : NULL), itemsSeparatedByZeros, popupMaxHeightInItems);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (currentItem != NULL) env->ReleasePrimitiveArrayCritical(obj_currentItem, currentItem, JNI_FALSE);
        if (itemsSeparatedByZeros != NULL) env->ReleaseStringUTFChars(obj_itemsSeparatedByZeros, itemsSeparatedByZeros);
        return _result;
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

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v) {
        return nDragFloat(label, v);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v, final float vSpeed) {
        return nDragFloat(label, v, vSpeed);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin) {
        return nDragFloat(label, v, vSpeed, vMin);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax) {
        return nDragFloat(label, v, vSpeed, vMin, vMax);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format) {
        return nDragFloat(label, v, vSpeed, vMin, vMax, format);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nDragFloat(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nDragFloat(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragFloat(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat(String obj_label, float[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat(String obj_label, float[] obj_v, float vSpeed, float vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat(label, &v[0], vSpeed, vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragFloat2(final String label, final float[] v) {
        return nDragFloat2(label, v);
    }

    public static boolean dragFloat2(final String label, final float[] v, final float vSpeed) {
        return nDragFloat2(label, v, vSpeed);
    }

    public static boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin) {
        return nDragFloat2(label, v, vSpeed, vMin);
    }

    public static boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax) {
        return nDragFloat2(label, v, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format) {
        return nDragFloat2(label, v, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nDragFloat2(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nDragFloat2(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragFloat2(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat2(String obj_label, float[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat2(String obj_label, float[] obj_v, float vSpeed, float vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat2(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat2(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat2(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat2(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat2(label, &v[0], vSpeed, vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragFloat3(final String label, final float[] v) {
        return nDragFloat3(label, v);
    }

    public static boolean dragFloat3(final String label, final float[] v, final float vSpeed) {
        return nDragFloat3(label, v, vSpeed);
    }

    public static boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin) {
        return nDragFloat3(label, v, vSpeed, vMin);
    }

    public static boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax) {
        return nDragFloat3(label, v, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format) {
        return nDragFloat3(label, v, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nDragFloat3(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nDragFloat3(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragFloat3(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat3(String obj_label, float[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat3(String obj_label, float[] obj_v, float vSpeed, float vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat3(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat3(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat3(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat3(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat3(label, &v[0], vSpeed, vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragFloat4(final String label, final float[] v) {
        return nDragFloat4(label, v);
    }

    public static boolean dragFloat4(final String label, final float[] v, final float vSpeed) {
        return nDragFloat4(label, v, vSpeed);
    }

    public static boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin) {
        return nDragFloat4(label, v, vSpeed, vMin);
    }

    public static boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax) {
        return nDragFloat4(label, v, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format) {
        return nDragFloat4(label, v, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nDragFloat4(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nDragFloat4(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragFloat4(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat4(String obj_label, float[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat4(String obj_label, float[] obj_v, float vSpeed, float vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat4(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloat4(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat4(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloat4(String obj_label, float[] obj_v, float vSpeed, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragFloat4(label, &v[0], vSpeed, vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed) {
        return nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin) {
        return nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax) {
        return nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format) {
        return nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax) {
        return nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax, final int imGuiSliderFlags) {
        return nDragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, imGuiSliderFlags);
    }

    private static native boolean nDragFloatRange2(String obj_label, float[] obj_vCurrentMin, float[] obj_vCurrentMax, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloatRange2(String obj_label, float[] obj_vCurrentMin, float[] obj_vCurrentMax, float vSpeed, float vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloatRange2(String obj_label, float[] obj_vCurrentMin, float[] obj_vCurrentMax, float vSpeed, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragFloatRange2(String obj_label, float[] obj_vCurrentMin, float[] obj_vCurrentMax, float vSpeed, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragFloatRange2(String obj_label, float[] obj_vCurrentMin, float[] obj_vCurrentMax, float vSpeed, float vMin, float vMax, String obj_format, String obj_formatMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto formatMax = obj_formatMax == NULL ? NULL : (char*)env->GetStringUTFChars(obj_formatMax, JNI_FALSE);
        auto _result = ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        if (formatMax != NULL) env->ReleaseStringUTFChars(obj_formatMax, formatMax);
        return _result;
    */

    private static native boolean nDragFloatRange2(String obj_label, float[] obj_vCurrentMin, float[] obj_vCurrentMax, float vSpeed, float vMin, float vMax, String obj_format, String obj_formatMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto formatMax = obj_formatMax == NULL ? NULL : (char*)env->GetStringUTFChars(obj_formatMax, JNI_FALSE);
        auto _result = ImGui::DragFloatRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        if (formatMax != NULL) env->ReleaseStringUTFChars(obj_formatMax, formatMax);
        return _result;
    */

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v) {
        return nDragInt(label, v);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v, final float vSpeed) {
        return nDragInt(label, v, vSpeed);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin) {
        return nDragInt(label, v, vSpeed, vMin);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax) {
        return nDragInt(label, v, vSpeed, vMin, vMax);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format) {
        return nDragInt(label, v, vSpeed, vMin, vMax, format);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nDragInt(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    public static boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nDragInt(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragInt(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt(String obj_label, int[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt(String obj_label, int[] obj_v, float vSpeed, int vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt(label, &v[0], vSpeed, vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragInt2(final String label, final int[] v) {
        return nDragInt2(label, v);
    }

    public static boolean dragInt2(final String label, final int[] v, final float vSpeed) {
        return nDragInt2(label, v, vSpeed);
    }

    public static boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin) {
        return nDragInt2(label, v, vSpeed, vMin);
    }

    public static boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax) {
        return nDragInt2(label, v, vSpeed, vMin, vMax);
    }

    public static boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format) {
        return nDragInt2(label, v, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nDragInt2(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nDragInt2(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragInt2(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt2(String obj_label, int[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt2(String obj_label, int[] obj_v, float vSpeed, int vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt2(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt2(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt2(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt2(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt2(label, &v[0], vSpeed, vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragInt3(final String label, final int[] v) {
        return nDragInt3(label, v);
    }

    public static boolean dragInt3(final String label, final int[] v, final float vSpeed) {
        return nDragInt3(label, v, vSpeed);
    }

    public static boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin) {
        return nDragInt3(label, v, vSpeed, vMin);
    }

    public static boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax) {
        return nDragInt3(label, v, vSpeed, vMin, vMax);
    }

    public static boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format) {
        return nDragInt3(label, v, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nDragInt3(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nDragInt3(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragInt3(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt3(String obj_label, int[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt3(String obj_label, int[] obj_v, float vSpeed, int vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt3(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt3(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt3(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt3(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt3(label, &v[0], vSpeed, vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragInt4(final String label, final int[] v) {
        return nDragInt4(label, v);
    }

    public static boolean dragInt4(final String label, final int[] v, final float vSpeed) {
        return nDragInt4(label, v, vSpeed);
    }

    public static boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin) {
        return nDragInt4(label, v, vSpeed, vMin);
    }

    public static boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax) {
        return nDragInt4(label, v, vSpeed, vMin, vMax);
    }

    public static boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format) {
        return nDragInt4(label, v, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nDragInt4(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nDragInt4(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nDragInt4(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt4(String obj_label, int[] obj_v, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt4(String obj_label, int[] obj_v, float vSpeed, int vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt4(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragInt4(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt4(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0], vSpeed, vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragInt4(String obj_label, int[] obj_v, float vSpeed, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::DragInt4(label, &v[0], vSpeed, vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax, final int imGuiSliderFlags) {
        return nDragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, imGuiSliderFlags);
    }

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax, float vSpeed, int vMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax, float vSpeed, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax, float vSpeed, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax, float vSpeed, int vMin, int vMax, String obj_format, String obj_formatMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto formatMax = obj_formatMax == NULL ? NULL : (char*)env->GetStringUTFChars(obj_formatMax, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        if (formatMax != NULL) env->ReleaseStringUTFChars(obj_formatMax, formatMax);
        return _result;
    */

    private static native boolean nDragIntRange2(String obj_label, int[] obj_vCurrentMin, int[] obj_vCurrentMax, float vSpeed, int vMin, int vMax, String obj_format, String obj_formatMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vCurrentMin = obj_vCurrentMin == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMin, JNI_FALSE);
        auto vCurrentMax = obj_vCurrentMax == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_vCurrentMax, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto formatMax = obj_formatMax == NULL ? NULL : (char*)env->GetStringUTFChars(obj_formatMax, JNI_FALSE);
        auto _result = ImGui::DragIntRange2(label, &vCurrentMin[0], &vCurrentMax[0], vSpeed, vMin, vMax, format, formatMax, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vCurrentMin != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMin, vCurrentMin, JNI_FALSE);
        if (vCurrentMax != NULL) env->ReleasePrimitiveArrayCritical(obj_vCurrentMax, vCurrentMax, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        if (formatMax != NULL) env->ReleaseStringUTFChars(obj_formatMax, formatMax);
        return _result;
    */

    public static boolean dragScalar(final String label, final short[] pData) {
        return nDragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed) {
        return nDragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin) {
        return nDragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax, final String format) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String obj_label, short[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, short[] obj_pData, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, short[] obj_pData, float vSpeed, short pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, short[] obj_pData, float vSpeed, short pMin, short pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, short[] obj_pData, float vSpeed, short pMin, short pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, short[] obj_pData, float vSpeed, short pMin, short pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S16, &pData[0], vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalar(final String label, final int[] pData) {
        return nDragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed) {
        return nDragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin) {
        return nDragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax, final String format) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String obj_label, int[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, int[] obj_pData, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, int[] obj_pData, float vSpeed, int pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, int[] obj_pData, float vSpeed, int pMin, int pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, int[] obj_pData, float vSpeed, int pMin, int pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, int[] obj_pData, float vSpeed, int pMin, int pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S32, &pData[0], vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalar(final String label, final long[] pData) {
        return nDragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed) {
        return nDragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin) {
        return nDragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax, final String format) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String obj_label, long[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, long[] obj_pData, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, long[] obj_pData, float vSpeed, long pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, long[] obj_pData, float vSpeed, long pMin, long pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, long[] obj_pData, float vSpeed, long pMin, long pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, long[] obj_pData, float vSpeed, long pMin, long pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_S64, &pData[0], vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalar(final String label, final float[] pData) {
        return nDragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed) {
        return nDragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin) {
        return nDragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax, final String format) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String obj_label, float[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, float[] obj_pData, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, float[] obj_pData, float vSpeed, float pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, float[] obj_pData, float vSpeed, float pMin, float pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, float[] obj_pData, float vSpeed, float pMin, float pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, float[] obj_pData, float vSpeed, float pMin, float pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Float, &pData[0], vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalar(final String label, final double[] pData) {
        return nDragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final double[] pData, final float vSpeed) {
        return nDragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final double[] pData, final float vSpeed, final double pMin) {
        return nDragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final double[] pData, final float vSpeed, final double pMin, final double pMax) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final double[] pData, final float vSpeed, final double pMin, final double pMax, final String format) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final double[] pData, final float vSpeed, final double pMin, final double pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalar(String obj_label, double[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Double, &pData[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, double[] obj_pData, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Double, &pData[0], vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, double[] obj_pData, float vSpeed, double pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Double, &pData[0], vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, double[] obj_pData, float vSpeed, double pMin, double pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Double, &pData[0], vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, double[] obj_pData, float vSpeed, double pMin, double pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Double, &pData[0], vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalar(String obj_label, double[] obj_pData, float vSpeed, double pMin, double pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalar(label, ImGuiDataType_Double, &pData[0], vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalarN(final String label, final short[] pData, final int components) {
        return nDragScalarN(label, pData, components);
    }

    public static boolean dragScalarN(final String label, final short[] pData, final int components, final float vSpeed) {
        return nDragScalarN(label, pData, components, vSpeed);
    }

    public static boolean dragScalarN(final String label, final short[] pData, final int components, final float vSpeed, final short pMin) {
        return nDragScalarN(label, pData, components, vSpeed, pMin);
    }

    public static boolean dragScalarN(final String label, final short[] pData, final int components, final float vSpeed, final short pMin, final short pMax) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    public static boolean dragScalarN(final String label, final short[] pData, final int components, final float vSpeed, final short pMin, final short pMax, final String format) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalarN(final String label, final short[] pData, final int components, final float vSpeed, final short pMin, final short pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String obj_label, short[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S16, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, short[] obj_pData, int components, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S16, &pData[0], components, vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, short[] obj_pData, int components, float vSpeed, short pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S16, &pData[0], components, vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, short[] obj_pData, int components, float vSpeed, short pMin, short pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S16, &pData[0], components, vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, short[] obj_pData, int components, float vSpeed, short pMin, short pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S16, &pData[0], components, vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, short[] obj_pData, int components, float vSpeed, short pMin, short pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S16, &pData[0], components, vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalarN(final String label, final int[] pData, final int components) {
        return nDragScalarN(label, pData, components);
    }

    public static boolean dragScalarN(final String label, final int[] pData, final int components, final float vSpeed) {
        return nDragScalarN(label, pData, components, vSpeed);
    }

    public static boolean dragScalarN(final String label, final int[] pData, final int components, final float vSpeed, final int pMin) {
        return nDragScalarN(label, pData, components, vSpeed, pMin);
    }

    public static boolean dragScalarN(final String label, final int[] pData, final int components, final float vSpeed, final int pMin, final int pMax) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    public static boolean dragScalarN(final String label, final int[] pData, final int components, final float vSpeed, final int pMin, final int pMax, final String format) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalarN(final String label, final int[] pData, final int components, final float vSpeed, final int pMin, final int pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String obj_label, int[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S32, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, int[] obj_pData, int components, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S32, &pData[0], components, vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, int[] obj_pData, int components, float vSpeed, int pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S32, &pData[0], components, vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, int[] obj_pData, int components, float vSpeed, int pMin, int pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S32, &pData[0], components, vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, int[] obj_pData, int components, float vSpeed, int pMin, int pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S32, &pData[0], components, vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, int[] obj_pData, int components, float vSpeed, int pMin, int pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S32, &pData[0], components, vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalarN(final String label, final long[] pData, final int components) {
        return nDragScalarN(label, pData, components);
    }

    public static boolean dragScalarN(final String label, final long[] pData, final int components, final float vSpeed) {
        return nDragScalarN(label, pData, components, vSpeed);
    }

    public static boolean dragScalarN(final String label, final long[] pData, final int components, final float vSpeed, final long pMin) {
        return nDragScalarN(label, pData, components, vSpeed, pMin);
    }

    public static boolean dragScalarN(final String label, final long[] pData, final int components, final float vSpeed, final long pMin, final long pMax) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    public static boolean dragScalarN(final String label, final long[] pData, final int components, final float vSpeed, final long pMin, final long pMax, final String format) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalarN(final String label, final long[] pData, final int components, final float vSpeed, final long pMin, final long pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String obj_label, long[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S64, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, long[] obj_pData, int components, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S64, &pData[0], components, vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, long[] obj_pData, int components, float vSpeed, long pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S64, &pData[0], components, vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, long[] obj_pData, int components, float vSpeed, long pMin, long pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S64, &pData[0], components, vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, long[] obj_pData, int components, float vSpeed, long pMin, long pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S64, &pData[0], components, vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, long[] obj_pData, int components, float vSpeed, long pMin, long pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_S64, &pData[0], components, vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalarN(final String label, final float[] pData, final int components) {
        return nDragScalarN(label, pData, components);
    }

    public static boolean dragScalarN(final String label, final float[] pData, final int components, final float vSpeed) {
        return nDragScalarN(label, pData, components, vSpeed);
    }

    public static boolean dragScalarN(final String label, final float[] pData, final int components, final float vSpeed, final float pMin) {
        return nDragScalarN(label, pData, components, vSpeed, pMin);
    }

    public static boolean dragScalarN(final String label, final float[] pData, final int components, final float vSpeed, final float pMin, final float pMax) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    public static boolean dragScalarN(final String label, final float[] pData, final int components, final float vSpeed, final float pMin, final float pMax, final String format) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalarN(final String label, final float[] pData, final int components, final float vSpeed, final float pMin, final float pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String obj_label, float[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Float, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, float[] obj_pData, int components, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Float, &pData[0], components, vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, float[] obj_pData, int components, float vSpeed, float pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Float, &pData[0], components, vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, float[] obj_pData, int components, float vSpeed, float pMin, float pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Float, &pData[0], components, vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, float[] obj_pData, int components, float vSpeed, float pMin, float pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Float, &pData[0], components, vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, float[] obj_pData, int components, float vSpeed, float pMin, float pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Float, &pData[0], components, vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean dragScalarN(final String label, final double[] pData, final int components) {
        return nDragScalarN(label, pData, components);
    }

    public static boolean dragScalarN(final String label, final double[] pData, final int components, final float vSpeed) {
        return nDragScalarN(label, pData, components, vSpeed);
    }

    public static boolean dragScalarN(final String label, final double[] pData, final int components, final float vSpeed, final double pMin) {
        return nDragScalarN(label, pData, components, vSpeed, pMin);
    }

    public static boolean dragScalarN(final String label, final double[] pData, final int components, final float vSpeed, final double pMin, final double pMax) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    public static boolean dragScalarN(final String label, final double[] pData, final int components, final float vSpeed, final double pMin, final double pMax, final String format) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalarN(final String label, final double[] pData, final int components, final float vSpeed, final double pMin, final double pMax, final String format, final int imGuiSliderFlags) {
        return nDragScalarN(label, pData, components, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nDragScalarN(String obj_label, double[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Double, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, double[] obj_pData, int components, float vSpeed); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Double, &pData[0], components, vSpeed);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, double[] obj_pData, int components, float vSpeed, double pMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Double, &pData[0], components, vSpeed, &pMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, double[] obj_pData, int components, float vSpeed, double pMin, double pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Double, &pData[0], components, vSpeed, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, double[] obj_pData, int components, float vSpeed, double pMin, double pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Double, &pData[0], components, vSpeed, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nDragScalarN(String obj_label, double[] obj_pData, int components, float vSpeed, double pMin, double pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::DragScalarN(label, ImGuiDataType_Double, &pData[0], components, vSpeed, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
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
    public static boolean sliderFloat(final String label, final float[] v, final float vMin, final float vMax) {
        return nSliderFloat(label, v, vMin, vMax);
    }

    /**
     * Adjust format to decorate the value with a prefix or a suffix for in-slider labels or unit display.
     */
    public static boolean sliderFloat(final String label, final float[] v, final float vMin, final float vMax, final String format) {
        return nSliderFloat(label, v, vMin, vMax, format);
    }

    /**
     * Adjust format to decorate the value with a prefix or a suffix for in-slider labels or unit display.
     */
    public static boolean sliderFloat(final String label, final float[] v, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nSliderFloat(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    /**
     * Adjust format to decorate the value with a prefix or a suffix for in-slider labels or unit display.
     */
    public static boolean sliderFloat(final String label, final float[] v, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nSliderFloat(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderFloat(String obj_label, float[] obj_v, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderFloat(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat(String obj_label, float[] obj_v, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat(label, &v[0], vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderFloat2(final String label, final float[] v, final float vMin, final float vMax) {
        return nSliderFloat2(label, v, vMin, vMax);
    }

    public static boolean sliderFloat2(final String label, final float[] v, final float vMin, final float vMax, final String format) {
        return nSliderFloat2(label, v, vMin, vMax, format);
    }

    public static boolean sliderFloat2(final String label, final float[] v, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nSliderFloat2(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderFloat2(final String label, final float[] v, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nSliderFloat2(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderFloat2(String obj_label, float[] obj_v, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat2(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderFloat2(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat2(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat2(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat2(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat2(String obj_label, float[] obj_v, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat2(label, &v[0], vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderFloat3(final String label, final float[] v, final float vMin, final float vMax) {
        return nSliderFloat3(label, v, vMin, vMax);
    }

    public static boolean sliderFloat3(final String label, final float[] v, final float vMin, final float vMax, final String format) {
        return nSliderFloat3(label, v, vMin, vMax, format);
    }

    public static boolean sliderFloat3(final String label, final float[] v, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nSliderFloat3(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderFloat3(final String label, final float[] v, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nSliderFloat3(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderFloat3(String obj_label, float[] obj_v, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat3(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderFloat3(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat3(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat3(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat3(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat3(String obj_label, float[] obj_v, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat3(label, &v[0], vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderFloat4(final String label, final float[] v, final float vMin, final float vMax) {
        return nSliderFloat4(label, v, vMin, vMax);
    }

    public static boolean sliderFloat4(final String label, final float[] v, final float vMin, final float vMax, final String format) {
        return nSliderFloat4(label, v, vMin, vMax, format);
    }

    public static boolean sliderFloat4(final String label, final float[] v, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nSliderFloat4(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderFloat4(final String label, final float[] v, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nSliderFloat4(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderFloat4(String obj_label, float[] obj_v, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat4(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderFloat4(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat4(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat4(String obj_label, float[] obj_v, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderFloat4(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderFloat4(String obj_label, float[] obj_v, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderFloat4(label, &v[0], vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderAngle(final String label, final float[] vRad) {
        return nSliderAngle(label, vRad);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin) {
        return nSliderAngle(label, vRad, vDegreesMin);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax) {
        return nSliderAngle(label, vRad, vDegreesMin, vDegreesMax);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final String format) {
        return nSliderAngle(label, vRad, vDegreesMin, vDegreesMax, format);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final String format, final int imGuiSliderFlags) {
        return nSliderAngle(label, vRad, vDegreesMin, vDegreesMax, format, imGuiSliderFlags);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final int imGuiSliderFlags) {
        return nSliderAngle(label, vRad, vDegreesMin, vDegreesMax, imGuiSliderFlags);
    }

    private static native boolean nSliderAngle(String obj_label, float[] obj_vRad); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vRad = obj_vRad == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vRad, JNI_FALSE);
        auto _result = ImGui::SliderAngle(label, &vRad[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vRad != NULL) env->ReleasePrimitiveArrayCritical(obj_vRad, vRad, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderAngle(String obj_label, float[] obj_vRad, float vDegreesMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vRad = obj_vRad == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vRad, JNI_FALSE);
        auto _result = ImGui::SliderAngle(label, &vRad[0], vDegreesMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vRad != NULL) env->ReleasePrimitiveArrayCritical(obj_vRad, vRad, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderAngle(String obj_label, float[] obj_vRad, float vDegreesMin, float vDegreesMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vRad = obj_vRad == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vRad, JNI_FALSE);
        auto _result = ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vRad != NULL) env->ReleasePrimitiveArrayCritical(obj_vRad, vRad, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderAngle(String obj_label, float[] obj_vRad, float vDegreesMin, float vDegreesMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vRad = obj_vRad == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vRad, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vRad != NULL) env->ReleasePrimitiveArrayCritical(obj_vRad, vRad, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderAngle(String obj_label, float[] obj_vRad, float vDegreesMin, float vDegreesMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vRad = obj_vRad == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vRad, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vRad != NULL) env->ReleasePrimitiveArrayCritical(obj_vRad, vRad, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderAngle(String obj_label, float[] obj_vRad, float vDegreesMin, float vDegreesMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto vRad = obj_vRad == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_vRad, JNI_FALSE);
        auto _result = ImGui::SliderAngle(label, &vRad[0], vDegreesMin, vDegreesMax, "%.0f deg", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (vRad != NULL) env->ReleasePrimitiveArrayCritical(obj_vRad, vRad, JNI_FALSE);
        return _result;
    */

    public static boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax) {
        return nSliderInt(label, v, vMin, vMax);
    }

    public static boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax, final String format) {
        return nSliderInt(label, v, vMin, vMax, format);
    }

    public static boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nSliderInt(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nSliderInt(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderInt(String obj_label, int[] obj_v, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderInt(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt(String obj_label, int[] obj_v, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt(label, &v[0], vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax) {
        return nSliderInt2(label, v, vMin, vMax);
    }

    public static boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax, final String format) {
        return nSliderInt2(label, v, vMin, vMax, format);
    }

    public static boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nSliderInt2(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nSliderInt2(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderInt2(String obj_label, int[] obj_v, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt2(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderInt2(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt2(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt2(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt2(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt2(String obj_label, int[] obj_v, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt2(label, &v[0], vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax) {
        return nSliderInt3(label, v, vMin, vMax);
    }

    public static boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax, final String format) {
        return nSliderInt3(label, v, vMin, vMax, format);
    }

    public static boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nSliderInt3(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nSliderInt3(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderInt3(String obj_label, int[] obj_v, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt3(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderInt3(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt3(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt3(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt3(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt3(String obj_label, int[] obj_v, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt3(label, &v[0], vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax) {
        return nSliderInt4(label, v, vMin, vMax);
    }

    public static boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax, final String format) {
        return nSliderInt4(label, v, vMin, vMax, format);
    }

    public static boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nSliderInt4(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nSliderInt4(label, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nSliderInt4(String obj_label, int[] obj_v, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt4(label, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderInt4(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt4(label, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt4(String obj_label, int[] obj_v, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderInt4(label, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderInt4(String obj_label, int[] obj_v, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::SliderInt4(label, &v[0], vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean sliderScalar(final String label, final short[] pData, final short pMin, final short pMax) {
        return nSliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final short[] pData, final short pMin, final short pMax, final String format) {
        return nSliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final short[] pData, final short pMin, final short pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String obj_label, short[] obj_pData, short pMin, short pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S16, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, short[] obj_pData, short pMin, short pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S16, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, short[] obj_pData, short pMin, short pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S16, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalar(final String label, final int[] pData, final int pMin, final int pMax) {
        return nSliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final int[] pData, final int pMin, final int pMax, final String format) {
        return nSliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final int[] pData, final int pMin, final int pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String obj_label, int[] obj_pData, int pMin, int pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S32, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, int[] obj_pData, int pMin, int pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S32, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, int[] obj_pData, int pMin, int pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S32, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalar(final String label, final long[] pData, final long pMin, final long pMax) {
        return nSliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final long[] pData, final long pMin, final long pMax, final String format) {
        return nSliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final long[] pData, final long pMin, final long pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String obj_label, long[] obj_pData, long pMin, long pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S64, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, long[] obj_pData, long pMin, long pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S64, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, long[] obj_pData, long pMin, long pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_S64, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalar(final String label, final float[] pData, final float pMin, final float pMax) {
        return nSliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final float[] pData, final float pMin, final float pMax, final String format) {
        return nSliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final float[] pData, final float pMin, final float pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String obj_label, float[] obj_pData, float pMin, float pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_Float, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, float[] obj_pData, float pMin, float pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_Float, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, float[] obj_pData, float pMin, float pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_Float, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalar(final String label, final double[] pData, final double pMin, final double pMax) {
        return nSliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final double[] pData, final double pMin, final double pMax, final String format) {
        return nSliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final double[] pData, final double pMin, final double pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalar(String obj_label, double[] obj_pData, double pMin, double pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_Double, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, double[] obj_pData, double pMin, double pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_Double, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalar(String obj_label, double[] obj_pData, double pMin, double pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalar(label, ImGuiDataType_Double, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalarN(final String label, final short[] pData, final int components, final short pMin, final short pMax) {
        return nSliderScalarN(label, pData, components, pMin, pMax);
    }

    public static boolean sliderScalarN(final String label, final short[] pData, final int components, final short pMin, final short pMax, final String format) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format);
    }

    public static boolean sliderScalarN(final String label, final short[] pData, final int components, final short pMin, final short pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String obj_label, short[] obj_pData, int components, short pMin, short pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S16, &pData[0], components, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, short[] obj_pData, int components, short pMin, short pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S16, &pData[0], components, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, short[] obj_pData, int components, short pMin, short pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S16, &pData[0], components, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalarN(final String label, final int[] pData, final int components, final int pMin, final int pMax) {
        return nSliderScalarN(label, pData, components, pMin, pMax);
    }

    public static boolean sliderScalarN(final String label, final int[] pData, final int components, final int pMin, final int pMax, final String format) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format);
    }

    public static boolean sliderScalarN(final String label, final int[] pData, final int components, final int pMin, final int pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String obj_label, int[] obj_pData, int components, int pMin, int pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S32, &pData[0], components, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, int[] obj_pData, int components, int pMin, int pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S32, &pData[0], components, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, int[] obj_pData, int components, int pMin, int pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S32, &pData[0], components, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalarN(final String label, final long[] pData, final int components, final long pMin, final long pMax) {
        return nSliderScalarN(label, pData, components, pMin, pMax);
    }

    public static boolean sliderScalarN(final String label, final long[] pData, final int components, final long pMin, final long pMax, final String format) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format);
    }

    public static boolean sliderScalarN(final String label, final long[] pData, final int components, final long pMin, final long pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String obj_label, long[] obj_pData, int components, long pMin, long pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S64, &pData[0], components, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, long[] obj_pData, int components, long pMin, long pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S64, &pData[0], components, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, long[] obj_pData, int components, long pMin, long pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_S64, &pData[0], components, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalarN(final String label, final float[] pData, final int components, final float pMin, final float pMax) {
        return nSliderScalarN(label, pData, components, pMin, pMax);
    }

    public static boolean sliderScalarN(final String label, final float[] pData, final int components, final float pMin, final float pMax, final String format) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format);
    }

    public static boolean sliderScalarN(final String label, final float[] pData, final int components, final float pMin, final float pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String obj_label, float[] obj_pData, int components, float pMin, float pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_Float, &pData[0], components, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, float[] obj_pData, int components, float pMin, float pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_Float, &pData[0], components, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, float[] obj_pData, int components, float pMin, float pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_Float, &pData[0], components, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean sliderScalarN(final String label, final double[] pData, final int components, final double pMin, final double pMax) {
        return nSliderScalarN(label, pData, components, pMin, pMax);
    }

    public static boolean sliderScalarN(final String label, final double[] pData, final int components, final double pMin, final double pMax, final String format) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format);
    }

    public static boolean sliderScalarN(final String label, final double[] pData, final int components, final double pMin, final double pMax, final String format, final int imGuiSliderFlags) {
        return nSliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nSliderScalarN(String obj_label, double[] obj_pData, int components, double pMin, double pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_Double, &pData[0], components, &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, double[] obj_pData, int components, double pMin, double pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_Double, &pData[0], components, &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nSliderScalarN(String obj_label, double[] obj_pData, int components, double pMin, double pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::SliderScalarN(label, ImGuiDataType_Double, &pData[0], components, &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean vSliderFloat(final String label, final ImVec2 size, final float[] v, final float vMin, final float vMax) {
        return nVSliderFloat(label, size.x, size.y, v, vMin, vMax);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final float[] v, final float vMin, final float vMax) {
        return nVSliderFloat(label, sizeX, sizeY, v, vMin, vMax);
    }

    public static boolean vSliderFloat(final String label, final ImVec2 size, final float[] v, final float vMin, final float vMax, final String format) {
        return nVSliderFloat(label, size.x, size.y, v, vMin, vMax, format);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final float[] v, final float vMin, final float vMax, final String format) {
        return nVSliderFloat(label, sizeX, sizeY, v, vMin, vMax, format);
    }

    public static boolean vSliderFloat(final String label, final ImVec2 size, final float[] v, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nVSliderFloat(label, size.x, size.y, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final float[] v, final float vMin, final float vMax, final String format, final int imGuiSliderFlags) {
        return nVSliderFloat(label, sizeX, sizeY, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderFloat(final String label, final ImVec2 size, final float[] v, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nVSliderFloat(label, size.x, size.y, v, vMin, vMax, imGuiSliderFlags);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final float[] v, final float vMin, final float vMax, final int imGuiSliderFlags) {
        return nVSliderFloat(label, sizeX, sizeY, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nVSliderFloat(String obj_label, float sizeX, float sizeY, float[] obj_v, float vMin, float vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderFloat(label, size, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderFloat(String obj_label, float sizeX, float sizeY, float[] obj_v, float vMin, float vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderFloat(label, size, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderFloat(String obj_label, float sizeX, float sizeY, float[] obj_v, float vMin, float vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderFloat(label, size, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderFloat(String obj_label, float sizeX, float sizeY, float[] obj_v, float vMin, float vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderFloat(label, size, &v[0], vMin, vMax, "%.3f", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean vSliderInt(final String label, final ImVec2 size, final int[] v, final int vMin, final int vMax) {
        return nVSliderInt(label, size.x, size.y, v, vMin, vMax);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final int[] v, final int vMin, final int vMax) {
        return nVSliderInt(label, sizeX, sizeY, v, vMin, vMax);
    }

    public static boolean vSliderInt(final String label, final ImVec2 size, final int[] v, final int vMin, final int vMax, final String format) {
        return nVSliderInt(label, size.x, size.y, v, vMin, vMax, format);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final int[] v, final int vMin, final int vMax, final String format) {
        return nVSliderInt(label, sizeX, sizeY, v, vMin, vMax, format);
    }

    public static boolean vSliderInt(final String label, final ImVec2 size, final int[] v, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nVSliderInt(label, size.x, size.y, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final int[] v, final int vMin, final int vMax, final String format, final int imGuiSliderFlags) {
        return nVSliderInt(label, sizeX, sizeY, v, vMin, vMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderInt(final String label, final ImVec2 size, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nVSliderInt(label, size.x, size.y, v, vMin, vMax, imGuiSliderFlags);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags) {
        return nVSliderInt(label, sizeX, sizeY, v, vMin, vMax, imGuiSliderFlags);
    }

    private static native boolean nVSliderInt(String obj_label, float sizeX, float sizeY, int[] obj_v, int vMin, int vMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderInt(label, size, &v[0], vMin, vMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderInt(String obj_label, float sizeX, float sizeY, int[] obj_v, int vMin, int vMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderInt(label, size, &v[0], vMin, vMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderInt(String obj_label, float sizeX, float sizeY, int[] obj_v, int vMin, int vMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderInt(label, size, &v[0], vMin, vMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderInt(String obj_label, float sizeX, float sizeY, int[] obj_v, int vMin, int vMax, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderInt(label, size, &v[0], vMin, vMax, "%d", imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean vSliderScalar(final String label, final ImVec2 size, final short[] pData, final short pMin, final short pMax) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final short[] pData, final short pMin, final short pMax) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final short[] pData, final short pMin, final short pMax, final String format) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final short[] pData, final short pMin, final short pMax, final String format) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final short[] pData, final short pMin, final short pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final short[] pData, final short pMin, final short pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, short[] obj_pData, short pMin, short pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S16, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, short[] obj_pData, short pMin, short pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S16, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, short[] obj_pData, short pMin, short pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S16, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean vSliderScalar(final String label, final ImVec2 size, final int[] pData, final int pMin, final int pMax) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final int[] pData, final int pMin, final int pMax) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final int[] pData, final int pMin, final int pMax, final String format) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final int[] pData, final int pMin, final int pMax, final String format) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final int[] pData, final int pMin, final int pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final int[] pData, final int pMin, final int pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, int[] obj_pData, int pMin, int pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S32, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, int[] obj_pData, int pMin, int pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S32, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, int[] obj_pData, int pMin, int pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S32, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean vSliderScalar(final String label, final ImVec2 size, final long[] pData, final long pMin, final long pMax) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final long[] pData, final long pMin, final long pMax) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final long[] pData, final long pMin, final long pMax, final String format) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final long[] pData, final long pMin, final long pMax, final String format) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final long[] pData, final long pMin, final long pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final long[] pData, final long pMin, final long pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, long[] obj_pData, long pMin, long pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S64, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, long[] obj_pData, long pMin, long pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S64, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, long[] obj_pData, long pMin, long pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_S64, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean vSliderScalar(final String label, final ImVec2 size, final float[] pData, final float pMin, final float pMax) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final float[] pData, final float pMin, final float pMax) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final float[] pData, final float pMin, final float pMax, final String format) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final float[] pData, final float pMin, final float pMax, final String format) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final float[] pData, final float pMin, final float pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final float[] pData, final float pMin, final float pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, float[] obj_pData, float pMin, float pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_Float, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, float[] obj_pData, float pMin, float pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_Float, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, float[] obj_pData, float pMin, float pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_Float, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean vSliderScalar(final String label, final ImVec2 size, final double[] pData, final double pMin, final double pMax) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final double[] pData, final double pMin, final double pMax) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final double[] pData, final double pMin, final double pMax, final String format) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final double[] pData, final double pMin, final double pMax, final String format) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final ImVec2 size, final double[] pData, final double pMin, final double pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, size.x, size.y, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final double[] pData, final double pMin, final double pMax, final String format, final int imGuiSliderFlags) {
        return nVSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, double[] obj_pData, double pMin, double pMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_Double, &pData[0], &pMin, &pMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, double[] obj_pData, double pMin, double pMax, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_Double, &pData[0], &pMin, &pMax, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nVSliderScalar(String obj_label, float sizeX, float sizeY, double[] obj_pData, double pMin, double pMax, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::VSliderScalar(label, size, ImGuiDataType_Double, &pData[0], &pMin, &pMax, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    // Widgets: Input with Keyboard
    // - If you want to use InputText() with std::string or any custom dynamic string type, see misc/cpp/imgui_stdlib.h and comments in imgui_demo.cpp.
    // - Most of the ImGuiInputTextFlags flags are only useful for InputText() and not for InputFloatX, InputIntX, InputDouble etc.

    /*JNI
        jmethodID jImStringResizeInternalMID;
        jmethodID jInputTextCallbackMID;

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
            jobject* handler;
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

            if (userData->handler != NULL) {
                JNIEnv* env = userData->env;
                env->CallObjectMethod(*userData->handler, jInputTextCallbackMID, data);
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

        jclass jCallback = env->FindClass("imgui/callback/ImGuiInputTextCallback");
        jInputTextCallbackMID = env->GetMethodID(jCallback, "accept", "(J)V");
    */

    public static boolean inputText(final String label, final ImString text) {
        return preInputText(false, label, null, text);
    }

    public static boolean inputText(final String label, final ImString text, final int imGuiInputTextFlags) {
        return preInputText(false, label, null, text, 0, 0, imGuiInputTextFlags);
    }

    public static boolean inputText(final String label, final ImString text, final int imGuiInputTextFlags, final ImGuiInputTextCallback callback) {
        return preInputText(false, label, null, text, 0, 0, imGuiInputTextFlags, callback);
    }

    public static boolean inputTextMultiline(final String label, final ImString text) {
        return preInputText(true, label, null, text);
    }

    public static boolean inputTextMultiline(final String label, final ImString text, final float width, final float height) {
        return preInputText(true, label, null, text, width, height);
    }

    public static boolean inputTextMultiline(final String label, final ImString text, final int imGuiInputTextFlags) {
        return preInputText(true, label, null, text, 0, 0, imGuiInputTextFlags);
    }

    public static boolean inputTextMultiline(final String label, final ImString text, final int imGuiInputTextFlags, final ImGuiInputTextCallback callback) {
        return preInputText(true, label, null, text, 0, 0, imGuiInputTextFlags, callback);
    }

    public static boolean inputTextMultiline(final String label, final ImString text, final float width, final float height, final int imGuiInputTextFlags) {
        return preInputText(true, label, null, text, width, height, imGuiInputTextFlags);
    }

    public static boolean inputTextMultiline(final String label, final ImString text, final float width, final float height, final int imGuiInputTextFlags, final ImGuiInputTextCallback callback) {
        return preInputText(true, label, null, text, width, height, imGuiInputTextFlags, callback);
    }

    public static boolean inputTextWithHint(final String label, final String hint, final ImString text) {
        return preInputText(false, label, hint, text);
    }

    public static boolean inputTextWithHint(final String label, final String hint, final ImString text, final int imGuiInputTextFlags) {
        return preInputText(false, label, hint, text, 0, 0, imGuiInputTextFlags);
    }

    public static boolean inputTextWithHint(final String label, final String hint, final ImString text, final int imGuiInputTextFlags, final ImGuiInputTextCallback callback) {
        return preInputText(false, label, hint, text, 0, 0, imGuiInputTextFlags, callback);
    }

    private static boolean preInputText(final boolean multiline, final String label, final String hint, final ImString text) {
        return preInputText(multiline, label, hint, text, 0, 0);
    }

    private static boolean preInputText(final boolean multiline, final String label, final String hint, final ImString text, final float width, final float height) {
        return preInputText(multiline, label, hint, text, width, height, ImGuiInputTextFlags.None);
    }

    private static boolean preInputText(final boolean multiline, final String label, final String hint, final ImString text, final float width, final float height, final int flags) {
        return preInputText(multiline, label, hint, text, width, height, flags, null);
    }

    private static boolean preInputText(final boolean multiline, final String label, final String hint, final ImString text, final float width, final float height, final int flagsV, final ImGuiInputTextCallback callback) {
        final ImString.InputData inputData = text.inputData;
        int flags = flagsV;

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

        return nInputText(multiline, hint != null, label, hintLabel, text, text.getData(), text.getData().length, width, height, flags, inputData, inputData.allowedChars, callback);
    }

    private static native boolean nInputText(boolean multiline, boolean hint, String label, String hintLabel, ImString imString, byte[] buf, int maxSize, float width, float height, int flags, ImString.InputData textInputData, String allowedChars, ImGuiInputTextCallback callback); /*
        InputTextCallbackUserData userData;
        userData.imString = &imString;
        userData.maxSize = maxSize;
        userData.jResizedBuf = NULL;
        userData.resizedBuf = NULL;
        userData.textInputData = &textInputData;
        userData.env = env;
        userData.allowedChars = allowedChars;
        userData.handler = callback != NULL ? &callback : NULL;

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

    public static boolean inputFloat(final String label, final ImFloat v) {
        return nInputFloat(label, v != null ? v.getData() : null);
    }

    public static boolean inputFloat(final String label, final ImFloat v, final float step) {
        return nInputFloat(label, v != null ? v.getData() : null, step);
    }

    public static boolean inputFloat(final String label, final ImFloat v, final float step, final float stepFast) {
        return nInputFloat(label, v != null ? v.getData() : null, step, stepFast);
    }

    public static boolean inputFloat(final String label, final ImFloat v, final float step, final float stepFast, final String format) {
        return nInputFloat(label, v != null ? v.getData() : null, step, stepFast, format);
    }

    public static boolean inputFloat(final String label, final ImFloat v, final float step, final float stepFast, final String format, final int imGuiInputTextFlags) {
        return nInputFloat(label, v != null ? v.getData() : null, step, stepFast, format, imGuiInputTextFlags);
    }

    public static boolean inputFloat(final String label, final ImFloat v, final float step, final float stepFast, final int imGuiInputTextFlags) {
        return nInputFloat(label, v != null ? v.getData() : null, step, stepFast, imGuiInputTextFlags);
    }

    private static native boolean nInputFloat(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat(label, (v != NULL ? &v[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputFloat(String obj_label, float[] obj_v, float step); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat(label, (v != NULL ? &v[0] : NULL), step);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputFloat(String obj_label, float[] obj_v, float step, float stepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat(label, (v != NULL ? &v[0] : NULL), step, stepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputFloat(String obj_label, float[] obj_v, float step, float stepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat(label, (v != NULL ? &v[0] : NULL), step, stepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat(String obj_label, float[] obj_v, float step, float stepFast, String obj_format, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat(label, (v != NULL ? &v[0] : NULL), step, stepFast, format, imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat(String obj_label, float[] obj_v, float step, float stepFast, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat(label, (v != NULL ? &v[0] : NULL), step, stepFast, "%.3f", imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputFloat2(final String label, final float[] v) {
        return nInputFloat2(label, v);
    }

    public static boolean inputFloat2(final String label, final float[] v, final String format) {
        return nInputFloat2(label, v, format);
    }

    public static boolean inputFloat2(final String label, final float[] v, final String format, final int imGuiInputTextFlags) {
        return nInputFloat2(label, v, format, imGuiInputTextFlags);
    }

    public static boolean inputFloat2(final String label, final float[] v, final int imGuiInputTextFlags) {
        return nInputFloat2(label, v, imGuiInputTextFlags);
    }

    private static native boolean nInputFloat2(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat2(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputFloat2(String obj_label, float[] obj_v, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat2(label, &v[0], format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat2(String obj_label, float[] obj_v, String obj_format, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat2(label, &v[0], format, imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat2(String obj_label, float[] obj_v, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat2(label, &v[0], "%.3f", imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputFloat3(final String label, final float[] v) {
        return nInputFloat3(label, v);
    }

    public static boolean inputFloat3(final String label, final float[] v, final String format) {
        return nInputFloat3(label, v, format);
    }

    public static boolean inputFloat3(final String label, final float[] v, final String format, final int imGuiInputTextFlags) {
        return nInputFloat3(label, v, format, imGuiInputTextFlags);
    }

    public static boolean inputFloat3(final String label, final float[] v, final int imGuiInputTextFlags) {
        return nInputFloat3(label, v, imGuiInputTextFlags);
    }

    private static native boolean nInputFloat3(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat3(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputFloat3(String obj_label, float[] obj_v, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat3(label, &v[0], format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat3(String obj_label, float[] obj_v, String obj_format, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat3(label, &v[0], format, imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat3(String obj_label, float[] obj_v, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat3(label, &v[0], "%.3f", imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputFloat4(final String label, final float[] v) {
        return nInputFloat4(label, v);
    }

    public static boolean inputFloat4(final String label, final float[] v, final String format) {
        return nInputFloat4(label, v, format);
    }

    public static boolean inputFloat4(final String label, final float[] v, final String format, final int imGuiInputTextFlags) {
        return nInputFloat4(label, v, format, imGuiInputTextFlags);
    }

    public static boolean inputFloat4(final String label, final float[] v, final int imGuiInputTextFlags) {
        return nInputFloat4(label, v, imGuiInputTextFlags);
    }

    private static native boolean nInputFloat4(String obj_label, float[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat4(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputFloat4(String obj_label, float[] obj_v, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat4(label, &v[0], format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat4(String obj_label, float[] obj_v, String obj_format, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputFloat4(label, &v[0], format, imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputFloat4(String obj_label, float[] obj_v, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputFloat4(label, &v[0], "%.3f", imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputInt(final String label, final ImInt v) {
        return nInputInt(label, v != null ? v.getData() : null);
    }

    public static boolean inputInt(final String label, final ImInt v, final int step) {
        return nInputInt(label, v != null ? v.getData() : null, step);
    }

    public static boolean inputInt(final String label, final ImInt v, final int step, final int stepFast) {
        return nInputInt(label, v != null ? v.getData() : null, step, stepFast);
    }

    public static boolean inputInt(final String label, final ImInt v, final int step, final int stepFast, final int imGuiInputTextFlags) {
        return nInputInt(label, v != null ? v.getData() : null, step, stepFast, imGuiInputTextFlags);
    }

    private static native boolean nInputInt(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt(label, (v != NULL ? &v[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputInt(String obj_label, int[] obj_v, int step); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt(label, (v != NULL ? &v[0] : NULL), step);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputInt(String obj_label, int[] obj_v, int step, int stepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt(label, (v != NULL ? &v[0] : NULL), step, stepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputInt(String obj_label, int[] obj_v, int step, int stepFast, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt(label, (v != NULL ? &v[0] : NULL), step, stepFast, imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputInt2(final String label, final int[] v) {
        return nInputInt2(label, v);
    }

    public static boolean inputInt2(final String label, final int[] v, final int imGuiInputTextFlags) {
        return nInputInt2(label, v, imGuiInputTextFlags);
    }

    private static native boolean nInputInt2(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt2(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputInt2(String obj_label, int[] obj_v, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt2(label, &v[0], imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputInt3(final String label, final int[] v) {
        return nInputInt3(label, v);
    }

    public static boolean inputInt3(final String label, final int[] v, final int imGuiInputTextFlags) {
        return nInputInt3(label, v, imGuiInputTextFlags);
    }

    private static native boolean nInputInt3(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt3(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputInt3(String obj_label, int[] obj_v, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt3(label, &v[0], imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputInt4(final String label, final int[] v) {
        return nInputInt4(label, v);
    }

    public static boolean inputInt4(final String label, final int[] v, final int imGuiInputTextFlags) {
        return nInputInt4(label, v, imGuiInputTextFlags);
    }

    private static native boolean nInputInt4(String obj_label, int[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt4(label, &v[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputInt4(String obj_label, int[] obj_v, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputInt4(label, &v[0], imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputDouble(final String label, final ImDouble v) {
        return nInputDouble(label, v != null ? v.getData() : null);
    }

    public static boolean inputDouble(final String label, final ImDouble v, final double step) {
        return nInputDouble(label, v != null ? v.getData() : null, step);
    }

    public static boolean inputDouble(final String label, final ImDouble v, final double step, final double stepFast) {
        return nInputDouble(label, v != null ? v.getData() : null, step, stepFast);
    }

    public static boolean inputDouble(final String label, final ImDouble v, final double step, final double stepFast, final String format) {
        return nInputDouble(label, v != null ? v.getData() : null, step, stepFast, format);
    }

    public static boolean inputDouble(final String label, final ImDouble v, final double step, final double stepFast, final String format, final int imGuiInputTextFlags) {
        return nInputDouble(label, v != null ? v.getData() : null, step, stepFast, format, imGuiInputTextFlags);
    }

    public static boolean inputDouble(final String label, final ImDouble v, final double step, final double stepFast, final int imGuiInputTextFlags) {
        return nInputDouble(label, v != null ? v.getData() : null, step, stepFast, imGuiInputTextFlags);
    }

    private static native boolean nInputDouble(String obj_label, double[] obj_v); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputDouble(label, (v != NULL ? &v[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputDouble(String obj_label, double[] obj_v, double step); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputDouble(label, (v != NULL ? &v[0] : NULL), step);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputDouble(String obj_label, double[] obj_v, double step, double stepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputDouble(label, (v != NULL ? &v[0] : NULL), step, stepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputDouble(String obj_label, double[] obj_v, double step, double stepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputDouble(label, (v != NULL ? &v[0] : NULL), step, stepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputDouble(String obj_label, double[] obj_v, double step, double stepFast, String obj_format, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputDouble(label, (v != NULL ? &v[0] : NULL), step, stepFast, format, imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputDouble(String obj_label, double[] obj_v, double step, double stepFast, int imGuiInputTextFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto v = obj_v == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_v, JNI_FALSE);
        auto _result = ImGui::InputDouble(label, (v != NULL ? &v[0] : NULL), step, stepFast, "%.6f", imGuiInputTextFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (v != NULL) env->ReleasePrimitiveArrayCritical(obj_v, v, JNI_FALSE);
        return _result;
    */

    public static boolean inputScalar(final String label, final ImShort pData) {
        return nInputScalar(label, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final ImShort pData, final short pStep) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final ImShort pData, final short pStep, final short pStepFast) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final ImShort pData, final short pStep, final short pStepFast, final String format) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final ImShort pData, final short pStep, final short pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, short[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S16, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, short[] obj_pData, short pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S16, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, short[] obj_pData, short pStep, short pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S16, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, short[] obj_pData, short pStep, short pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S16, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, short[] obj_pData, short pStep, short pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S16, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final ImInt pData) {
        return nInputScalar(label, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final ImInt pData, final int pStep) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final ImInt pData, final int pStep, final int pStepFast) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final ImInt pData, final int pStep, final int pStepFast, final String format) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final ImInt pData, final int pStep, final int pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, int[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S32, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int[] obj_pData, int pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S32, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int[] obj_pData, int pStep, int pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S32, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int[] obj_pData, int pStep, int pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S32, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int[] obj_pData, int pStep, int pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S32, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final ImLong pData) {
        return nInputScalar(label, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final ImLong pData, final long pStep) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final ImLong pData, final long pStep, final long pStepFast) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final ImLong pData, final long pStep, final long pStepFast, final String format) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final ImLong pData, final long pStep, final long pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, long[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S64, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, long[] obj_pData, long pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S64, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, long[] obj_pData, long pStep, long pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S64, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, long[] obj_pData, long pStep, long pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S64, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, long[] obj_pData, long pStep, long pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_S64, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final ImFloat pData) {
        return nInputScalar(label, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final ImFloat pData, final float pStep) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final ImFloat pData, final float pStep, final float pStepFast) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final ImFloat pData, final float pStep, final float pStepFast, final String format) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final ImFloat pData, final float pStep, final float pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, float[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Float, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, float[] obj_pData, float pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Float, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, float[] obj_pData, float pStep, float pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Float, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, float[] obj_pData, float pStep, float pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Float, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, float[] obj_pData, float pStep, float pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Float, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final ImDouble pData) {
        return nInputScalar(label, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final ImDouble pData, final double pStep) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final ImDouble pData, final double pStep, final double pStepFast) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final ImDouble pData, final double pStep, final double pStepFast, final String format) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final ImDouble pData, final double pStep, final double pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, double[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Double, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, double[] obj_pData, double pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Double, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, double[] obj_pData, double pStep, double pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Double, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, double[] obj_pData, double pStep, double pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Double, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, double[] obj_pData, double pStep, double pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, ImGuiDataType_Double, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final int dataType, final ImShort pData) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImShort pData, final short pStep) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImShort pData, final short pStep, final short pStepFast) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImShort pData, final short pStep, final short pStepFast, final String format) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImShort pData, final short pStep, final short pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, int dataType, short[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, short[] obj_pData, short pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, short[] obj_pData, short pStep, short pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, short[] obj_pData, short pStep, short pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, short[] obj_pData, short pStep, short pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final int dataType, final ImInt pData) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImInt pData, final int pStep) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImInt pData, final int pStep, final int pStepFast) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImInt pData, final int pStep, final int pStepFast, final String format) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImInt pData, final int pStep, final int pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, int dataType, int[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, int[] obj_pData, int pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, int[] obj_pData, int pStep, int pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, int[] obj_pData, int pStep, int pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, int[] obj_pData, int pStep, int pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final int dataType, final ImLong pData) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImLong pData, final long pStep) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImLong pData, final long pStep, final long pStepFast) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImLong pData, final long pStep, final long pStepFast, final String format) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImLong pData, final long pStep, final long pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, int dataType, long[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, long[] obj_pData, long pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, long[] obj_pData, long pStep, long pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, long[] obj_pData, long pStep, long pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, long[] obj_pData, long pStep, long pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final int dataType, final ImFloat pData) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImFloat pData, final float pStep) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImFloat pData, final float pStep, final float pStepFast) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImFloat pData, final float pStep, final float pStepFast, final String format) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImFloat pData, final float pStep, final float pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, int dataType, float[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, float[] obj_pData, float pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, float[] obj_pData, float pStep, float pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, float[] obj_pData, float pStep, float pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, float[] obj_pData, float pStep, float pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalar(final String label, final int dataType, final ImDouble pData) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImDouble pData, final double pStep) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImDouble pData, final double pStep, final double pStepFast) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImDouble pData, final double pStep, final double pStepFast, final String format) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final int dataType, final ImDouble pData, final double pStep, final double pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalar(label, dataType, pData != null ? pData.getData() : null, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalar(String obj_label, int dataType, double[] obj_pData); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, double[] obj_pData, double pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, double[] obj_pData, double pStep, double pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, double[] obj_pData, double pStep, double pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalar(String obj_label, int dataType, double[] obj_pData, double pStep, double pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalar(label, dataType, (pData != NULL ? &pData[0] : NULL), &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final short[] pData, final int components) {
        return nInputScalarN(label, pData, components);
    }

    public static boolean inputScalarN(final String label, final short[] pData, final int components, final short pStep) {
        return nInputScalarN(label, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final short[] pData, final int components, final short pStep, final short pStepFast) {
        return nInputScalarN(label, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final short[] pData, final int components, final short pStep, final short pStepFast, final String format) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final short[] pData, final int components, final short pStep, final short pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, short[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S16, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, short[] obj_pData, int components, short pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S16, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, short[] obj_pData, int components, short pStep, short pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S16, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, short[] obj_pData, int components, short pStep, short pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S16, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, short[] obj_pData, int components, short pStep, short pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S16, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final int[] pData, final int components) {
        return nInputScalarN(label, pData, components);
    }

    public static boolean inputScalarN(final String label, final int[] pData, final int components, final int pStep) {
        return nInputScalarN(label, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final int[] pData, final int components, final int pStep, final int pStepFast) {
        return nInputScalarN(label, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final int[] pData, final int components, final int pStep, final int pStepFast, final String format) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final int[] pData, final int components, final int pStep, final int pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, int[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S32, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int[] obj_pData, int components, int pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S32, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int[] obj_pData, int components, int pStep, int pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S32, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int[] obj_pData, int components, int pStep, int pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S32, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int[] obj_pData, int components, int pStep, int pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S32, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final long[] pData, final int components) {
        return nInputScalarN(label, pData, components);
    }

    public static boolean inputScalarN(final String label, final long[] pData, final int components, final long pStep) {
        return nInputScalarN(label, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final long[] pData, final int components, final long pStep, final long pStepFast) {
        return nInputScalarN(label, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final long[] pData, final int components, final long pStep, final long pStepFast, final String format) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final long[] pData, final int components, final long pStep, final long pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, long[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S64, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, long[] obj_pData, int components, long pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S64, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, long[] obj_pData, int components, long pStep, long pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S64, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, long[] obj_pData, int components, long pStep, long pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S64, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, long[] obj_pData, int components, long pStep, long pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_S64, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final float[] pData, final int components) {
        return nInputScalarN(label, pData, components);
    }

    public static boolean inputScalarN(final String label, final float[] pData, final int components, final float pStep) {
        return nInputScalarN(label, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final float[] pData, final int components, final float pStep, final float pStepFast) {
        return nInputScalarN(label, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final float[] pData, final int components, final float pStep, final float pStepFast, final String format) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final float[] pData, final int components, final float pStep, final float pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, float[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Float, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, float[] obj_pData, int components, float pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Float, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, float[] obj_pData, int components, float pStep, float pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Float, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, float[] obj_pData, int components, float pStep, float pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Float, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, float[] obj_pData, int components, float pStep, float pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Float, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final double[] pData, final int components) {
        return nInputScalarN(label, pData, components);
    }

    public static boolean inputScalarN(final String label, final double[] pData, final int components, final double pStep) {
        return nInputScalarN(label, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final double[] pData, final int components, final double pStep, final double pStepFast) {
        return nInputScalarN(label, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final double[] pData, final int components, final double pStep, final double pStepFast, final String format) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final double[] pData, final int components, final double pStep, final double pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, double[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Double, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, double[] obj_pData, int components, double pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Double, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, double[] obj_pData, int components, double pStep, double pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Double, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, double[] obj_pData, int components, double pStep, double pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Double, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, double[] obj_pData, int components, double pStep, double pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, ImGuiDataType_Double, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final int dataType, final short[] pData, final int components) {
        return nInputScalarN(label, dataType, pData, components);
    }

    public static boolean inputScalarN(final String label, final int dataType, final short[] pData, final int components, final short pStep) {
        return nInputScalarN(label, dataType, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final int dataType, final short[] pData, final int components, final short pStep, final short pStepFast) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final int dataType, final short[] pData, final int components, final short pStep, final short pStepFast, final String format) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final int dataType, final short[] pData, final int components, final short pStep, final short pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, int dataType, short[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, short[] obj_pData, int components, short pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, short[] obj_pData, int components, short pStep, short pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, short[] obj_pData, int components, short pStep, short pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, short[] obj_pData, int components, short pStep, short pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (short*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final int dataType, final int[] pData, final int components) {
        return nInputScalarN(label, dataType, pData, components);
    }

    public static boolean inputScalarN(final String label, final int dataType, final int[] pData, final int components, final int pStep) {
        return nInputScalarN(label, dataType, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final int dataType, final int[] pData, final int components, final int pStep, final int pStepFast) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final int dataType, final int[] pData, final int components, final int pStep, final int pStepFast, final String format) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final int dataType, final int[] pData, final int components, final int pStep, final int pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, int dataType, int[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, int[] obj_pData, int components, int pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, int[] obj_pData, int components, int pStep, int pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, int[] obj_pData, int components, int pStep, int pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, int[] obj_pData, int components, int pStep, int pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final int dataType, final long[] pData, final int components) {
        return nInputScalarN(label, dataType, pData, components);
    }

    public static boolean inputScalarN(final String label, final int dataType, final long[] pData, final int components, final long pStep) {
        return nInputScalarN(label, dataType, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final int dataType, final long[] pData, final int components, final long pStep, final long pStepFast) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final int dataType, final long[] pData, final int components, final long pStep, final long pStepFast, final String format) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final int dataType, final long[] pData, final int components, final long pStep, final long pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, int dataType, long[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, long[] obj_pData, int components, long pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, long[] obj_pData, int components, long pStep, long pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, long[] obj_pData, int components, long pStep, long pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, long[] obj_pData, int components, long pStep, long pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final int dataType, final float[] pData, final int components) {
        return nInputScalarN(label, dataType, pData, components);
    }

    public static boolean inputScalarN(final String label, final int dataType, final float[] pData, final int components, final float pStep) {
        return nInputScalarN(label, dataType, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final int dataType, final float[] pData, final int components, final float pStep, final float pStepFast) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final int dataType, final float[] pData, final int components, final float pStep, final float pStepFast, final String format) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final int dataType, final float[] pData, final int components, final float pStep, final float pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, int dataType, float[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, float[] obj_pData, int components, float pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, float[] obj_pData, int components, float pStep, float pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, float[] obj_pData, int components, float pStep, float pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, float[] obj_pData, int components, float pStep, float pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    public static boolean inputScalarN(final String label, final int dataType, final double[] pData, final int components) {
        return nInputScalarN(label, dataType, pData, components);
    }

    public static boolean inputScalarN(final String label, final int dataType, final double[] pData, final int components, final double pStep) {
        return nInputScalarN(label, dataType, pData, components, pStep);
    }

    public static boolean inputScalarN(final String label, final int dataType, final double[] pData, final int components, final double pStep, final double pStepFast) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    public static boolean inputScalarN(final String label, final int dataType, final double[] pData, final int components, final double pStep, final double pStepFast, final String format) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    public static boolean inputScalarN(final String label, final int dataType, final double[] pData, final int components, final double pStep, final double pStepFast, final String format, final int imGuiSliderFlags) {
        return nInputScalarN(label, dataType, pData, components, pStep, pStepFast, format, imGuiSliderFlags);
    }

    private static native boolean nInputScalarN(String obj_label, int dataType, double[] obj_pData, int components); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, double[] obj_pData, int components, double pStep); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, double[] obj_pData, int components, double pStep, double pStepFast); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, double[] obj_pData, int components, double pStep, double pStepFast, String obj_format); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    private static native boolean nInputScalarN(String obj_label, int dataType, double[] obj_pData, int components, double pStep, double pStepFast, String obj_format, int imGuiSliderFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pData = obj_pData == NULL ? NULL : (double*)env->GetPrimitiveArrayCritical(obj_pData, JNI_FALSE);
        auto format = obj_format == NULL ? NULL : (char*)env->GetStringUTFChars(obj_format, JNI_FALSE);
        auto _result = ImGui::InputScalarN(label, dataType, &pData[0], components, &pStep, &pStepFast, format, imGuiSliderFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pData != NULL) env->ReleasePrimitiveArrayCritical(obj_pData, pData, JNI_FALSE);
        if (format != NULL) env->ReleaseStringUTFChars(obj_format, format);
        return _result;
    */

    // Widgets: Color Editor/Picker (tip: the ColorEdit* functions have a little color square that can be left-clicked to open a picker, and right-clicked to open an option menu.)
    // - Note that in C++ a 'float v[X]' function argument is the _same_ as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible.
    // - You can pass the address of a first float element out of a contiguous structure, e.g. &myvector.x

    public static boolean colorEdit3(final String label, final float[] col) {
        return nColorEdit3(label, col);
    }

    public static boolean colorEdit3(final String label, final float[] col, final int imGuiColorEditFlags) {
        return nColorEdit3(label, col, imGuiColorEditFlags);
    }

    private static native boolean nColorEdit3(String obj_label, float[] obj_col); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorEdit3(label, &col[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorEdit3(String obj_label, float[] obj_col, int imGuiColorEditFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorEdit3(label, &col[0], imGuiColorEditFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    public static boolean colorEdit4(final String label, final float[] col) {
        return nColorEdit4(label, col);
    }

    public static boolean colorEdit4(final String label, final float[] col, final int imGuiColorEditFlags) {
        return nColorEdit4(label, col, imGuiColorEditFlags);
    }

    private static native boolean nColorEdit4(String obj_label, float[] obj_col); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorEdit4(label, &col[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorEdit4(String obj_label, float[] obj_col, int imGuiColorEditFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorEdit4(label, &col[0], imGuiColorEditFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    public static boolean colorPicker3(final String label, final float[] col) {
        return nColorPicker3(label, col);
    }

    public static boolean colorPicker3(final String label, final float[] col, final int imGuiColorEditFlags) {
        return nColorPicker3(label, col, imGuiColorEditFlags);
    }

    private static native boolean nColorPicker3(String obj_label, float[] obj_col); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorPicker3(label, &col[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorPicker3(String obj_label, float[] obj_col, int imGuiColorEditFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorPicker3(label, &col[0], imGuiColorEditFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    public static boolean colorPicker4(final String label, final float[] col) {
        return nColorPicker4(label, col);
    }

    public static boolean colorPicker4(final String label, final float[] col, final int imGuiColorEditFlags) {
        return nColorPicker4(label, col, imGuiColorEditFlags);
    }

    public static boolean colorPicker4(final String label, final float[] col, final int imGuiColorEditFlags, final float[] refCol) {
        return nColorPicker4(label, col, imGuiColorEditFlags, refCol);
    }

    public static boolean colorPicker4(final String label, final float[] col, final float[] refCol) {
        return nColorPicker4(label, col, refCol);
    }

    private static native boolean nColorPicker4(String obj_label, float[] obj_col); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorPicker4(label, &col[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorPicker4(String obj_label, float[] obj_col, int imGuiColorEditFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorPicker4(label, &col[0], imGuiColorEditFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorPicker4(String obj_label, float[] obj_col, int imGuiColorEditFlags, float[] obj_refCol); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto refCol = obj_refCol == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_refCol, JNI_FALSE);
        auto _result = ImGui::ColorPicker4(label, &col[0], imGuiColorEditFlags, &refCol[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        if (refCol != NULL) env->ReleasePrimitiveArrayCritical(obj_refCol, refCol, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorPicker4(String obj_label, float[] obj_col, float[] obj_refCol); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto refCol = obj_refCol == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_refCol, JNI_FALSE);
        auto _result = ImGui::ColorPicker4(label, &col[0], 0, &refCol[0]);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        if (refCol != NULL) env->ReleasePrimitiveArrayCritical(obj_refCol, refCol, JNI_FALSE);
        return _result;
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final ImVec4 col) {
        return nColorButton(descId, col.x, col.y, col.z, col.w);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW) {
        return nColorButton(descId, colX, colY, colZ, colW);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final ImVec4 col, final int imGuiColorEditFlags) {
        return nColorButton(descId, col.x, col.y, col.z, col.w, imGuiColorEditFlags);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int imGuiColorEditFlags) {
        return nColorButton(descId, colX, colY, colZ, colW, imGuiColorEditFlags);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final ImVec4 col, final int imGuiColorEditFlags, final ImVec2 size) {
        return nColorButton(descId, col.x, col.y, col.z, col.w, imGuiColorEditFlags, size.x, size.y);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int imGuiColorEditFlags, final float sizeX, final float sizeY) {
        return nColorButton(descId, colX, colY, colZ, colW, imGuiColorEditFlags, sizeX, sizeY);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final ImVec4 col, final ImVec2 size) {
        return nColorButton(descId, col.x, col.y, col.z, col.w, size.x, size.y);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final float sizeX, final float sizeY) {
        return nColorButton(descId, colX, colY, colZ, colW, sizeX, sizeY);
    }

    private static native boolean nColorButton(String obj_descId, float colX, float colY, float colZ, float colW); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        auto _result = ImGui::ColorButton(descId, col);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        return _result;
    */

    private static native boolean nColorButton(String obj_descId, float colX, float colY, float colZ, float colW, int imGuiColorEditFlags); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        auto _result = ImGui::ColorButton(descId, col, imGuiColorEditFlags);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        return _result;
    */

    private static native boolean nColorButton(String obj_descId, float colX, float colY, float colZ, float colW, int imGuiColorEditFlags, float sizeX, float sizeY); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ColorButton(descId, col, imGuiColorEditFlags, size);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        return _result;
    */

    private static native boolean nColorButton(String obj_descId, float colX, float colY, float colZ, float colW, float sizeX, float sizeY); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        ImVec4 col = ImVec4(colX, colY, colZ, colW);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ColorButton(descId, col, 0, size);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        return _result;
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    public static boolean colorButton(final String descId, final float[] col) {
        return nColorButton(descId, col);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    public static boolean colorButton(final String descId, final float[] col, final int imGuiColorEditFlags) {
        return nColorButton(descId, col, imGuiColorEditFlags);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    public static boolean colorButton(final String descId, final float[] col, final int imGuiColorEditFlags, final ImVec2 size) {
        return nColorButton(descId, col, imGuiColorEditFlags, size.x, size.y);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    public static boolean colorButton(final String descId, final float[] col, final int imGuiColorEditFlags, final float sizeX, final float sizeY) {
        return nColorButton(descId, col, imGuiColorEditFlags, sizeX, sizeY);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    public static boolean colorButton(final String descId, final float[] col, final ImVec2 size) {
        return nColorButton(descId, col, size.x, size.y);
    }

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    public static boolean colorButton(final String descId, final float[] col, final float sizeX, final float sizeY) {
        return nColorButton(descId, col, sizeX, sizeY);
    }

    private static native boolean nColorButton(String obj_descId, float[] obj_col); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]));
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorButton(String obj_descId, float[] obj_col, int imGuiColorEditFlags); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        auto _result = ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]), imGuiColorEditFlags);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorButton(String obj_descId, float[] obj_col, int imGuiColorEditFlags, float sizeX, float sizeY); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]), imGuiColorEditFlags, size);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    private static native boolean nColorButton(String obj_descId, float[] obj_col, float sizeX, float sizeY); /*MANUAL
        auto descId = obj_descId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_descId, JNI_FALSE);
        auto col = obj_col == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_col, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ColorButton(descId, ImVec4(col[0], col[1], col[2], col[3]), 0, size);
        if (descId != NULL) env->ReleaseStringUTFChars(obj_descId, descId);
        if (col != NULL) env->ReleasePrimitiveArrayCritical(obj_col, col, JNI_FALSE);
        return _result;
    */

    /**
     * Initialize current options (generally on application startup) if you want to select a default format,
     * picker type, etc. User will be able to change many settings, unless you pass the _NoOptions flag to your calls.
     */
    public static void setColorEditOptions(final int imGuiColorEditFlags) {
        nSetColorEditOptions(imGuiColorEditFlags);
    }

    private static native void nSetColorEditOptions(int imGuiColorEditFlags); /*
        ImGui::SetColorEditOptions(imGuiColorEditFlags);
    */

    // Widgets: Trees
    // - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

    public static boolean treeNode(final String label) {
        return nTreeNode(label);
    }

    private static native boolean nTreeNode(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNode(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Helper variation to easily decorelate the id from the displayed string.
     * Read the FAQ about why and how to use ID. to align arbitrary text at the same level as a TreeNode() you can use Bullet().
     */
    public static boolean treeNode(final String strId, final String label) {
        return nTreeNode(strId, label);
    }

    private static native boolean nTreeNode(String obj_strId, String obj_label); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNode(strId, label, NULL);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    public static boolean treeNode(final long ptrId, final String label) {
        return nTreeNode(ptrId, label);
    }

    private static native boolean nTreeNode(long ptrId, String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNode((void*)ptrId, label, NULL);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    public static boolean treeNodeEx(final String label) {
        return nTreeNodeEx(label);
    }

    public static boolean treeNodeEx(final String label, final int flags) {
        return nTreeNodeEx(label, flags);
    }

    private static native boolean nTreeNodeEx(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNodeEx(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nTreeNodeEx(String obj_label, int flags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNodeEx(label, flags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    public static boolean treeNodeEx(final String strId, final int flags, final String label) {
        return nTreeNodeEx(strId, flags, label);
    }

    private static native boolean nTreeNodeEx(String obj_strId, int flags, String obj_label); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNodeEx(strId, flags, label, NULL);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    public static boolean treeNodeEx(final long ptrId, final int flags, final String label) {
        return nTreeNodeEx(ptrId, flags, label);
    }

    private static native boolean nTreeNodeEx(long ptrId, int flags, String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TreeNodeEx((void*)ptrId, flags, label, NULL);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * ~ Indent()+PushId(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    public static void treePush(final String strId) {
        nTreePush(strId);
    }

    private static native void nTreePush(String strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImGui::TreePush(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
    */

    /**
     * ~ Indent()+PushId(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    public static void treePush() {
        nTreePush();
    }

    /**
     * ~ Indent()+PushId(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    public static void treePush(final long ptrId) {
        nTreePush(ptrId);
    }

    private static native void nTreePush(); /*
        ImGui::TreePush();
    */

    private static native void nTreePush(long ptrId); /*
        ImGui::TreePush((void*)ptrId);
    */

    /**
     * ~ Unindent()+PopId()
     */
    public static void treePop() {
        nTreePop();
    }

    private static native void nTreePop(); /*
        ImGui::TreePop();
    */

    /**
     * Horizontal distance preceding label when using TreeNode*() or Bullet() == (g.FontSize + style.FramePadding.x*2) for a regular unframed TreeNode
     */
    public static float getTreeNodeToLabelSpacing() {
        return nGetTreeNodeToLabelSpacing();
    }

    private static native float nGetTreeNodeToLabelSpacing(); /*
        return ImGui::GetTreeNodeToLabelSpacing();
    */

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    public static boolean collapsingHeader(final String label) {
        return nCollapsingHeader(label);
    }

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    public static boolean collapsingHeader(final String label, final int imGuiTreeNodeFlags) {
        return nCollapsingHeader(label, imGuiTreeNodeFlags);
    }

    private static native boolean nCollapsingHeader(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::CollapsingHeader(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nCollapsingHeader(String obj_label, int imGuiTreeNodeFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::CollapsingHeader(label, imGuiTreeNodeFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * When 'p_visible != NULL': if '*p_visible==true' display an additional small close button on upper right of the header which will set the bool
     * to false when clicked, if '*p_visible==false' don't display the header.
     */
    public static boolean collapsingHeader(final String label, final ImBoolean pVisible) {
        return nCollapsingHeader(label, pVisible != null ? pVisible.getData() : null);
    }

    /**
     * When 'p_visible != NULL': if '*p_visible==true' display an additional small close button on upper right of the header which will set the bool
     * to false when clicked, if '*p_visible==false' don't display the header.
     */
    public static boolean collapsingHeader(final String label, final ImBoolean pVisible, final int imGuiTreeNodeFlags) {
        return nCollapsingHeader(label, pVisible != null ? pVisible.getData() : null, imGuiTreeNodeFlags);
    }

    private static native boolean nCollapsingHeader(String obj_label, boolean[] obj_pVisible); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pVisible = obj_pVisible == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pVisible, JNI_FALSE);
        auto _result = ImGui::CollapsingHeader(label, (pVisible != NULL ? &pVisible[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pVisible != NULL) env->ReleasePrimitiveArrayCritical(obj_pVisible, pVisible, JNI_FALSE);
        return _result;
    */

    private static native boolean nCollapsingHeader(String obj_label, boolean[] obj_pVisible, int imGuiTreeNodeFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pVisible = obj_pVisible == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pVisible, JNI_FALSE);
        auto _result = ImGui::CollapsingHeader(label, (pVisible != NULL ? &pVisible[0] : NULL), imGuiTreeNodeFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pVisible != NULL) env->ReleasePrimitiveArrayCritical(obj_pVisible, pVisible, JNI_FALSE);
        return _result;
    */

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    public static void setNextItemOpen(final boolean isOpen) {
        nSetNextItemOpen(isOpen);
    }

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    public static void setNextItemOpen(final boolean isOpen, final int cond) {
        nSetNextItemOpen(isOpen, cond);
    }

    private static native void nSetNextItemOpen(boolean isOpen); /*
        ImGui::SetNextItemOpen(isOpen);
    */

    private static native void nSetNextItemOpen(boolean isOpen, int cond); /*
        ImGui::SetNextItemOpen(isOpen, cond);
    */

    // Widgets: Selectables
    // - A selectable highlights when hovered, and can display another color when selected.
    // - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

    public static boolean selectable(final String label) {
        return nSelectable(label);
    }

    public static boolean selectable(final String label, final boolean selected) {
        return nSelectable(label, selected);
    }

    public static boolean selectable(final String label, final boolean selected, final int imGuiSelectableFlags) {
        return nSelectable(label, selected, imGuiSelectableFlags);
    }

    public static boolean selectable(final String label, final boolean selected, final int imGuiSelectableFlags, final ImVec2 size) {
        return nSelectable(label, selected, imGuiSelectableFlags, size.x, size.y);
    }

    public static boolean selectable(final String label, final boolean selected, final int imGuiSelectableFlags, final float sizeX, final float sizeY) {
        return nSelectable(label, selected, imGuiSelectableFlags, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final int imGuiSelectableFlags, final ImVec2 size) {
        return nSelectable(label, imGuiSelectableFlags, size.x, size.y);
    }

    public static boolean selectable(final String label, final int imGuiSelectableFlags, final float sizeX, final float sizeY) {
        return nSelectable(label, imGuiSelectableFlags, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final ImVec2 size) {
        return nSelectable(label, size.x, size.y);
    }

    public static boolean selectable(final String label, final float sizeX, final float sizeY) {
        return nSelectable(label, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final boolean selected, final ImVec2 size) {
        return nSelectable(label, selected, size.x, size.y);
    }

    public static boolean selectable(final String label, final boolean selected, final float sizeX, final float sizeY) {
        return nSelectable(label, selected, sizeX, sizeY);
    }

    private static native boolean nSelectable(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::Selectable(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean selected); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::Selectable(label, selected);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean selected, int imGuiSelectableFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::Selectable(label, selected, imGuiSelectableFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean selected, int imGuiSelectableFlags, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Selectable(label, selected, imGuiSelectableFlags, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, int imGuiSelectableFlags, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Selectable(label, false, imGuiSelectableFlags, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Selectable(label, false, 0, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean selected, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Selectable(label, selected, 0, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    public static boolean selectable(final String label, final ImBoolean pSelected) {
        return nSelectable(label, pSelected != null ? pSelected.getData() : null);
    }

    public static boolean selectable(final String label, final ImBoolean pSelected, final int imGuiSelectableFlags) {
        return nSelectable(label, pSelected != null ? pSelected.getData() : null, imGuiSelectableFlags);
    }

    public static boolean selectable(final String label, final ImBoolean pSelected, final int imGuiSelectableFlags, final ImVec2 size) {
        return nSelectable(label, pSelected != null ? pSelected.getData() : null, imGuiSelectableFlags, size.x, size.y);
    }

    public static boolean selectable(final String label, final ImBoolean pSelected, final int imGuiSelectableFlags, final float sizeX, final float sizeY) {
        return nSelectable(label, pSelected != null ? pSelected.getData() : null, imGuiSelectableFlags, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final ImBoolean pSelected, final ImVec2 size) {
        return nSelectable(label, pSelected != null ? pSelected.getData() : null, size.x, size.y);
    }

    public static boolean selectable(final String label, final ImBoolean pSelected, final float sizeX, final float sizeY) {
        return nSelectable(label, pSelected != null ? pSelected.getData() : null, sizeX, sizeY);
    }

    private static native boolean nSelectable(String obj_label, boolean[] obj_pSelected); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pSelected = obj_pSelected == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pSelected, JNI_FALSE);
        auto _result = ImGui::Selectable(label, (pSelected != NULL ? &pSelected[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pSelected != NULL) env->ReleasePrimitiveArrayCritical(obj_pSelected, pSelected, JNI_FALSE);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean[] obj_pSelected, int imGuiSelectableFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pSelected = obj_pSelected == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pSelected, JNI_FALSE);
        auto _result = ImGui::Selectable(label, (pSelected != NULL ? &pSelected[0] : NULL), imGuiSelectableFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pSelected != NULL) env->ReleasePrimitiveArrayCritical(obj_pSelected, pSelected, JNI_FALSE);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean[] obj_pSelected, int imGuiSelectableFlags, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pSelected = obj_pSelected == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pSelected, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Selectable(label, (pSelected != NULL ? &pSelected[0] : NULL), imGuiSelectableFlags, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pSelected != NULL) env->ReleasePrimitiveArrayCritical(obj_pSelected, pSelected, JNI_FALSE);
        return _result;
    */

    private static native boolean nSelectable(String obj_label, boolean[] obj_pSelected, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pSelected = obj_pSelected == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pSelected, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::Selectable(label, (pSelected != NULL ? &pSelected[0] : NULL), 0, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pSelected != NULL) env->ReleasePrimitiveArrayCritical(obj_pSelected, pSelected, JNI_FALSE);
        return _result;
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
    public static boolean beginListBox(final String label) {
        return nBeginListBox(label);
    }

    /**
     * Open a framed scrolling region.
     */
    public static boolean beginListBox(final String label, final ImVec2 size) {
        return nBeginListBox(label, size.x, size.y);
    }

    /**
     * Open a framed scrolling region.
     */
    public static boolean beginListBox(final String label, final float sizeX, final float sizeY) {
        return nBeginListBox(label, sizeX, sizeY);
    }

    private static native boolean nBeginListBox(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::BeginListBox(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nBeginListBox(String obj_label, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginListBox(label, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Only call EndListBox() if BeginListBox() returned true!
     */
    public static void endListBox() {
        nEndListBox();
    }

    private static native void nEndListBox(); /*
        ImGui::EndListBox();
    */

    public static void listBox(final String label, final ImInt currentItem, final String[] items) {
        nListBox(label, currentItem != null ? currentItem.getData() : null, items, items.length);
    }

    public static void listBox(final String label, final ImInt currentItem, final String[] items, final int heightInItems) {
        nListBox(label, currentItem != null ? currentItem.getData() : null, items, items.length, heightInItems);
    }

    private static native void nListBox(String label, int[] currentItem, String[] obj_items, int itemsCount); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto currentItem = obj_currentItem == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_currentItem, JNI_FALSE);
        const char* items[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            items[i] = rawStr;
        };
        ImGui::ListBox(label, (currentItem != NULL ? &currentItem[0] : NULL), items, itemsCount);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (currentItem != NULL) env->ReleasePrimitiveArrayCritical(obj_currentItem, currentItem, JNI_FALSE);
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            env->ReleaseStringUTFChars(str, items[i]);
        };
    */

    private static native void nListBox(String label, int[] currentItem, String[] obj_items, int itemsCount, int heightInItems); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto currentItem = obj_currentItem == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_currentItem, JNI_FALSE);
        const char* items[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
            items[i] = rawStr;
        };
        ImGui::ListBox(label, (currentItem != NULL ? &currentItem[0] : NULL), items, itemsCount, heightInItems);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (currentItem != NULL) env->ReleasePrimitiveArrayCritical(obj_currentItem, currentItem, JNI_FALSE);
        for (int i = 0; i < itemsCount; i++) {
            const jstring str = (jstring)env->GetObjectArrayElement(obj_items, i);
            env->ReleaseStringUTFChars(str, items[i]);
        };
    */

    // Widgets: Data Plotting
    // - Consider using ImPlot (https://github.com/epezent/implot)

    public static void plotLines(final String label, final float[] values, final int valuesCount) {
        nPlotLines(label, values, valuesCount);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset) {
        nPlotLines(label, values, valuesCount, valuesOffset);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final ImVec2 graphSize) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final String overlayText, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotLines(label, values, valuesCount, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotLines(label, values, valuesCount, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotLines(label, values, valuesCount, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotLines(label, values, valuesCount, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final int stride) {
        nPlotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, stride);
    }

    private static native void nPlotLines(String label, float[] values, int valuesCount); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImGui::PlotLines(label, &values[0], valuesCount);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotLines(label, &values[0], valuesCount, 0, overlayText, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotLines(label, &values[0], valuesCount, 0, NULL, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotLines(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotLines(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(0,0), stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    public static void plotHistogram(final String label, final float[] values, final int valuesCount) {
        nPlotHistogram(label, values, valuesCount);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset) {
        nPlotHistogram(label, values, valuesCount, valuesOffset);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final ImVec2 graphSize) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final String overlayText, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotHistogram(label, values, valuesCount, overlayText, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotHistogram(label, values, valuesCount, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotHistogram(label, values, valuesCount, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotHistogram(label, values, valuesCount, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final ImVec2 graphSize, final int stride) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize.x, graphSize.y, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final int stride) {
        nPlotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, stride);
    }

    private static native void nPlotHistogram(String label, float[] values, int valuesCount); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImGui::PlotHistogram(label, &values[0], valuesCount);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, String overlayText, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotHistogram(label, &values[0], valuesCount, 0, overlayText, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotHistogram(label, &values[0], valuesCount, 0, NULL, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, float scaleMin, float scaleMax, float graphSizeX, float graphSizeY, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        ImVec2 graphSize = ImVec2(graphSizeX, graphSizeY);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, NULL, scaleMin, scaleMax, graphSize, stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
    */

    private static native void nPlotHistogram(String label, float[] values, int valuesCount, int valuesOffset, String overlayText, float scaleMin, float scaleMax, int stride); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto values = obj_values == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_values, JNI_FALSE);
        auto overlayText = obj_overlayText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_overlayText, JNI_FALSE);
        ImGui::PlotHistogram(label, &values[0], valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, ImVec2(0,0), stride);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (values != NULL) env->ReleasePrimitiveArrayCritical(obj_values, values, JNI_FALSE);
        if (overlayText != NULL) env->ReleaseStringUTFChars(obj_overlayText, overlayText);
    */

    // Widgets: Value() Helpers.
    // - Those are merely shortcut to calling Text() with a format string. Output single value in "name: value" format (tip: freely declare more in your code to handle your types. you can add functions to the ImGui namespace)

    public static void value(final String prefix, final Number value) {
        nValue(prefix, value.toString());
    }

    public static void value(final String prefix, final float value, String floatFormat) {
        nValue(prefix, String.format(floatFormat, value));
    }

    private static native void nValue(String prefix, String value); /*
        ImGui::Value(prefix, value);
    */

    // Widgets: Menus
    // - Use BeginMenuBar() on a window ImGuiWindowFlags_MenuBar to append to its menu bar.
    // - Use BeginMainMenuBar() to create a menu bar at the top of the screen and append to it.
    // - Use BeginMenu() to create a menu. You can call BeginMenu() multiple time with the same identifier to append more items to it.

    /**
     * Append to menu-bar of current window (requires ImGuiWindowFlags_MenuBar flag set on parent window).
     */
    public static boolean beginMenuBar() {
        return nBeginMenuBar();
    }

    private static native boolean nBeginMenuBar(); /*
        return ImGui::BeginMenuBar();
    */

    /**
     * Only call EndMenuBar() if BeginMenuBar() returns true!
     */
    public static void endMenuBar() {
        nEndMenuBar();
    }

    private static native void nEndMenuBar(); /*
        ImGui::EndMenuBar();
    */

    /**
     * Create and append to a full screen menu-bar.
     */
    public static boolean beginMainMenuBar() {
        return nBeginMainMenuBar();
    }

    private static native boolean nBeginMainMenuBar(); /*
        return ImGui::BeginMainMenuBar();
    */

    /**
     * Only call EndMainMenuBar() if BeginMainMenuBar() returns true!
     */
    public static void endMainMenuBar() {
        nEndMainMenuBar();
    }

    private static native void nEndMainMenuBar(); /*
        ImGui::EndMainMenuBar();
    */

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    public static boolean beginMenu(final String label) {
        return nBeginMenu(label);
    }

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    public static boolean beginMenu(final String label, final boolean enabled) {
        return nBeginMenu(label, enabled);
    }

    private static native boolean nBeginMenu(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::BeginMenu(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nBeginMenu(String obj_label, boolean enabled); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::BeginMenu(label, enabled);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Only call EndMenu() if BeginMenu() returns true!
     */
    public static void endMenu() {
        nEndMenu();
    }

    private static native void nEndMenu(); /*
        ImGui::EndMenu();
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(final String label) {
        return nMenuItem(label);
    }

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(final String label, final boolean selected) {
        return nMenuItem(label, selected);
    }

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(final String label, final boolean selected, final boolean enabled) {
        return nMenuItem(label, selected, enabled);
    }

    private static native boolean nMenuItem(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, NULL);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nMenuItem(String obj_label, boolean selected); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, NULL, selected);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nMenuItem(String obj_label, boolean selected, boolean enabled); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, NULL, selected, enabled);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(final String label, final String shortcut) {
        return nMenuItem(label, shortcut);
    }

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(final String label, final String shortcut, final boolean selected) {
        return nMenuItem(label, shortcut, selected);
    }

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static boolean menuItem(final String label, final String shortcut, final boolean selected, final boolean enabled) {
        return nMenuItem(label, shortcut, selected, enabled);
    }

    private static native boolean nMenuItem(String obj_label, String obj_shortcut); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto shortcut = obj_shortcut == NULL ? NULL : (char*)env->GetStringUTFChars(obj_shortcut, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, shortcut);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (shortcut != NULL) env->ReleaseStringUTFChars(obj_shortcut, shortcut);
        return _result;
    */

    private static native boolean nMenuItem(String obj_label, String obj_shortcut, boolean selected); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto shortcut = obj_shortcut == NULL ? NULL : (char*)env->GetStringUTFChars(obj_shortcut, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, shortcut, selected);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (shortcut != NULL) env->ReleaseStringUTFChars(obj_shortcut, shortcut);
        return _result;
    */

    private static native boolean nMenuItem(String obj_label, String obj_shortcut, boolean selected, boolean enabled); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto shortcut = obj_shortcut == NULL ? NULL : (char*)env->GetStringUTFChars(obj_shortcut, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, shortcut, selected, enabled);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (shortcut != NULL) env->ReleaseStringUTFChars(obj_shortcut, shortcut);
        return _result;
    */

    /**
     * Return true when activated + toggle (*pSelected) if pSelected != NULL
     */
    public static boolean menuItem(final String label, final String shortcut, final ImBoolean pSelected) {
        return nMenuItem(label, shortcut, pSelected != null ? pSelected.getData() : null);
    }

    /**
     * Return true when activated + toggle (*pSelected) if pSelected != NULL
     */
    public static boolean menuItem(final String label, final String shortcut, final ImBoolean pSelected, final boolean enabled) {
        return nMenuItem(label, shortcut, pSelected != null ? pSelected.getData() : null, enabled);
    }

    private static native boolean nMenuItem(String obj_label, String obj_shortcut, boolean[] obj_pSelected); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto shortcut = obj_shortcut == NULL ? NULL : (char*)env->GetStringUTFChars(obj_shortcut, JNI_FALSE);
        auto pSelected = obj_pSelected == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pSelected, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, shortcut, (pSelected != NULL ? &pSelected[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (shortcut != NULL) env->ReleaseStringUTFChars(obj_shortcut, shortcut);
        if (pSelected != NULL) env->ReleasePrimitiveArrayCritical(obj_pSelected, pSelected, JNI_FALSE);
        return _result;
    */

    private static native boolean nMenuItem(String obj_label, String obj_shortcut, boolean[] obj_pSelected, boolean enabled); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto shortcut = obj_shortcut == NULL ? NULL : (char*)env->GetStringUTFChars(obj_shortcut, JNI_FALSE);
        auto pSelected = obj_pSelected == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pSelected, JNI_FALSE);
        auto _result = ImGui::MenuItem(label, shortcut, (pSelected != NULL ? &pSelected[0] : NULL), enabled);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (shortcut != NULL) env->ReleaseStringUTFChars(obj_shortcut, shortcut);
        if (pSelected != NULL) env->ReleasePrimitiveArrayCritical(obj_pSelected, pSelected, JNI_FALSE);
        return _result;
    */

    // Tooltips
    // - Tooltip are windows following the mouse. They do not take focus away.

    /**
     * Begin/append a tooltip window. to create full-featured tooltip (with any kind of items).
     */
    public static void beginTooltip() {
        nBeginTooltip();
    }

    private static native void nBeginTooltip(); /*
        ImGui::BeginTooltip();
    */

    public static void endTooltip() {
        nEndTooltip();
    }

    private static native void nEndTooltip(); /*
        ImGui::EndTooltip();
    */

    /**
     * Set a text-only tooltip, typically use with ImGui::IsItemHovered(). override any previous call to SetTooltip().
     */
    public static void setTooltip(final String text) {
        nSetTooltip(text);
    }

    private static native void nSetTooltip(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::SetTooltip(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
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
    public static boolean beginPopup(final String strId) {
        return nBeginPopup(strId);
    }

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopup(final String strId, final int imGuiWindowFlags) {
        return nBeginPopup(strId, imGuiWindowFlags);
    }

    private static native boolean nBeginPopup(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopup(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopup(String obj_strId, int imGuiWindowFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopup(strId, imGuiWindowFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(final String name) {
        return nBeginPopupModal(name);
    }

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(final String name, final ImBoolean pOpen) {
        return nBeginPopupModal(name, pOpen != null ? pOpen.getData() : null);
    }

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(final String name, final ImBoolean pOpen, final int imGuiWindowFlags) {
        return nBeginPopupModal(name, pOpen != null ? pOpen.getData() : null, imGuiWindowFlags);
    }

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    public static boolean beginPopupModal(final String name, final int imGuiWindowFlags) {
        return nBeginPopupModal(name, imGuiWindowFlags);
    }

    private static native boolean nBeginPopupModal(String obj_name); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto _result = ImGui::BeginPopupModal(name);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        return _result;
    */

    private static native boolean nBeginPopupModal(String obj_name, boolean[] obj_pOpen); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        auto _result = ImGui::BeginPopupModal(name, (pOpen != NULL ? &pOpen[0] : NULL));
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
        return _result;
    */

    private static native boolean nBeginPopupModal(String obj_name, boolean[] obj_pOpen, int imGuiWindowFlags); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        auto _result = ImGui::BeginPopupModal(name, (pOpen != NULL ? &pOpen[0] : NULL), imGuiWindowFlags);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
        return _result;
    */

    private static native boolean nBeginPopupModal(String obj_name, int imGuiWindowFlags); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto _result = ImGui::BeginPopupModal(name, NULL, imGuiWindowFlags);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        return _result;
    */

    /**
     * Only call EndPopup() if BeginPopupXXX() returns true!
     */
    public static void endPopup() {
        nEndPopup();
    }

    private static native void nEndPopup(); /*
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
    public static void openPopup(final String strId) {
        nOpenPopup(strId);
    }

    /**
     * Call to mark popup as open (don't call every frame!).
     */
    public static void openPopup(final String strId, final int imGuiPopupFlags) {
        nOpenPopup(strId, imGuiPopupFlags);
    }

    private static native void nOpenPopup(String strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImGui::OpenPopup(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
    */

    private static native void nOpenPopup(String strId, int imGuiPopupFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImGui::OpenPopup(strId, imGuiPopupFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
    */

    /**
     * Id overload to facilitate calling from nested stacks.
     */
    public static void openPopup(final int id) {
        nOpenPopup(id);
    }

    /**
     * Id overload to facilitate calling from nested stacks.
     */
    public static void openPopup(final int id, final int imGuiPopupFlags) {
        nOpenPopup(id, imGuiPopupFlags);
    }

    private static native void nOpenPopup(int id); /*
        ImGui::OpenPopup((ImGuiID)id);
    */

    private static native void nOpenPopup(int id, int imGuiPopupFlags); /*
        ImGui::OpenPopup((ImGuiID)id, imGuiPopupFlags);
    */

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static void openPopupOnItemClick() {
        nOpenPopupOnItemClick();
    }

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static void openPopupOnItemClick(final String strId) {
        nOpenPopupOnItemClick(strId);
    }

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static void openPopupOnItemClick(final String strId, final int imGuiPopupFlags) {
        nOpenPopupOnItemClick(strId, imGuiPopupFlags);
    }

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    public static void openPopupOnItemClick(final int imGuiPopupFlags) {
        nOpenPopupOnItemClick(imGuiPopupFlags);
    }

    private static native void nOpenPopupOnItemClick(); /*
        ImGui::OpenPopupOnItemClick();
    */

    private static native void nOpenPopupOnItemClick(String strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImGui::OpenPopupOnItemClick(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
    */

    private static native void nOpenPopupOnItemClick(String strId, int imGuiPopupFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImGui::OpenPopupOnItemClick(strId, imGuiPopupFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
    */

    private static native void nOpenPopupOnItemClick(int imGuiPopupFlags); /*
        ImGui::OpenPopupOnItemClick(NULL, imGuiPopupFlags);
    */

    /**
     * Manually close the popup we have begin-ed into.
     */
    public static void closeCurrentPopup() {
        nCloseCurrentPopup();
    }

    private static native void nCloseCurrentPopup(); /*
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
    public static boolean beginPopupContextItem() {
        return nBeginPopupContextItem();
    }

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static boolean beginPopupContextItem(final String strId) {
        return nBeginPopupContextItem(strId);
    }

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static boolean beginPopupContextItem(final String strId, final int imGuiPopupFlags) {
        return nBeginPopupContextItem(strId, imGuiPopupFlags);
    }

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static boolean beginPopupContextItem(final int imGuiPopupFlags) {
        return nBeginPopupContextItem(imGuiPopupFlags);
    }

    private static native boolean nBeginPopupContextItem(); /*
        return ImGui::BeginPopupContextItem();
    */

    private static native boolean nBeginPopupContextItem(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopupContextItem(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopupContextItem(String obj_strId, int imGuiPopupFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopupContextItem(strId, imGuiPopupFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopupContextItem(int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextItem(NULL, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked on current window.
     */
    public static boolean beginPopupContextWindow() {
        return nBeginPopupContextWindow();
    }

    /**
     * Open+begin popup when clicked on current window.
     */
    public static boolean beginPopupContextWindow(final String strId) {
        return nBeginPopupContextWindow(strId);
    }

    /**
     * Open+begin popup when clicked on current window.
     */
    public static boolean beginPopupContextWindow(final String strId, final int imGuiPopupFlags) {
        return nBeginPopupContextWindow(strId, imGuiPopupFlags);
    }

    /**
     * Open+begin popup when clicked on current window.
     */
    public static boolean beginPopupContextWindow(final int imGuiPopupFlags) {
        return nBeginPopupContextWindow(imGuiPopupFlags);
    }

    private static native boolean nBeginPopupContextWindow(); /*
        return ImGui::BeginPopupContextWindow();
    */

    private static native boolean nBeginPopupContextWindow(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopupContextWindow(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopupContextWindow(String obj_strId, int imGuiPopupFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopupContextWindow(strId, imGuiPopupFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopupContextWindow(int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextWindow(NULL, imGuiPopupFlags);
    */

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static boolean beginPopupContextVoid() {
        return nBeginPopupContextVoid();
    }

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static boolean beginPopupContextVoid(final String strId) {
        return nBeginPopupContextVoid(strId);
    }

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static boolean beginPopupContextVoid(final String strId, final int imGuiPopupFlags) {
        return nBeginPopupContextVoid(strId, imGuiPopupFlags);
    }

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    public static boolean beginPopupContextVoid(final int imGuiPopupFlags) {
        return nBeginPopupContextVoid(imGuiPopupFlags);
    }

    private static native boolean nBeginPopupContextVoid(); /*
        return ImGui::BeginPopupContextVoid();
    */

    private static native boolean nBeginPopupContextVoid(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopupContextVoid(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopupContextVoid(String obj_strId, int imGuiPopupFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginPopupContextVoid(strId, imGuiPopupFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginPopupContextVoid(int imGuiPopupFlags); /*
        return ImGui::BeginPopupContextVoid(NULL, imGuiPopupFlags);
    */

    // Popups: test function
    //  - IsPopupOpen(): return true if the popup is open at the current BeginPopup() level of the popup stack.
    //  - IsPopupOpen() with ImGuiPopupFlags_AnyPopupId: return true if any popup is open at the current BeginPopup() level of the popup stack.
    //  - IsPopupOpen() with ImGuiPopupFlags_AnyPopupId + ImGuiPopupFlags_AnyPopupLevel: return true if any popup is open.

    /**
     * Return true if the popup is open.
     */
    public static boolean isPopupOpen(final String strId) {
        return nIsPopupOpen(strId);
    }

    /**
     * Return true if the popup is open.
     */
    public static boolean isPopupOpen(final String strId, final int imGuiPopupFlags) {
        return nIsPopupOpen(strId, imGuiPopupFlags);
    }

    private static native boolean nIsPopupOpen(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::IsPopupOpen(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nIsPopupOpen(String obj_strId, int imGuiPopupFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::IsPopupOpen(strId, imGuiPopupFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
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

    public static boolean beginTable(final String id, final int column) {
        return nBeginTable(id, column);
    }

    public static boolean beginTable(final String id, final int column, final int imGuiTableFlags) {
        return nBeginTable(id, column, imGuiTableFlags);
    }

    public static boolean beginTable(final String id, final int column, final int imGuiTableFlags, final ImVec2 outerSize) {
        return nBeginTable(id, column, imGuiTableFlags, outerSize.x, outerSize.y);
    }

    public static boolean beginTable(final String id, final int column, final int imGuiTableFlags, final float outerSizeX, final float outerSizeY) {
        return nBeginTable(id, column, imGuiTableFlags, outerSizeX, outerSizeY);
    }

    public static boolean beginTable(final String id, final int column, final int imGuiTableFlags, final ImVec2 outerSize, final float innerWidth) {
        return nBeginTable(id, column, imGuiTableFlags, outerSize.x, outerSize.y, innerWidth);
    }

    public static boolean beginTable(final String id, final int column, final int imGuiTableFlags, final float outerSizeX, final float outerSizeY, final float innerWidth) {
        return nBeginTable(id, column, imGuiTableFlags, outerSizeX, outerSizeY, innerWidth);
    }

    public static boolean beginTable(final String id, final int column, final ImVec2 outerSize, final float innerWidth) {
        return nBeginTable(id, column, outerSize.x, outerSize.y, innerWidth);
    }

    public static boolean beginTable(final String id, final int column, final float outerSizeX, final float outerSizeY, final float innerWidth) {
        return nBeginTable(id, column, outerSizeX, outerSizeY, innerWidth);
    }

    public static boolean beginTable(final String id, final int column, final float innerWidth) {
        return nBeginTable(id, column, innerWidth);
    }

    public static boolean beginTable(final String id, final int column, final int imGuiTableFlags, final float innerWidth) {
        return nBeginTable(id, column, imGuiTableFlags, innerWidth);
    }

    private static native boolean nBeginTable(String obj_id, int column); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto _result = ImGui::BeginTable(id, column);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    private static native boolean nBeginTable(String obj_id, int column, int imGuiTableFlags); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto _result = ImGui::BeginTable(id, column, imGuiTableFlags);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    private static native boolean nBeginTable(String obj_id, int column, int imGuiTableFlags, float outerSizeX, float outerSizeY); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImVec2 outerSize = ImVec2(outerSizeX, outerSizeY);
        auto _result = ImGui::BeginTable(id, column, imGuiTableFlags, outerSize);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    private static native boolean nBeginTable(String obj_id, int column, int imGuiTableFlags, float outerSizeX, float outerSizeY, float innerWidth); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImVec2 outerSize = ImVec2(outerSizeX, outerSizeY);
        auto _result = ImGui::BeginTable(id, column, imGuiTableFlags, outerSize, innerWidth);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    private static native boolean nBeginTable(String obj_id, int column, float outerSizeX, float outerSizeY, float innerWidth); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImVec2 outerSize = ImVec2(outerSizeX, outerSizeY);
        auto _result = ImGui::BeginTable(id, column, 0, outerSize, innerWidth);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    private static native boolean nBeginTable(String obj_id, int column, float innerWidth); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto _result = ImGui::BeginTable(id, column, 0, ImVec2(0,0), innerWidth);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    private static native boolean nBeginTable(String obj_id, int column, int imGuiTableFlags, float innerWidth); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        auto _result = ImGui::BeginTable(id, column, imGuiTableFlags, ImVec2(0,0), innerWidth);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
        return _result;
    */

    /**
     * Only call EndTable() if BeginTable() returns true!
     */
    public static void endTable() {
        nEndTable();
    }

    private static native void nEndTable(); /*
        ImGui::EndTable();
    */

    /**
     * Append into the first cell of a new row.
     */
    public static void tableNextRow() {
        nTableNextRow();
    }

    /**
     * Append into the first cell of a new row.
     */
    public static void tableNextRow(final int imGuiTableRowFlags) {
        nTableNextRow(imGuiTableRowFlags);
    }

    /**
     * Append into the first cell of a new row.
     */
    public static void tableNextRow(final int imGuiTableRowFlags, final float minRowHeight) {
        nTableNextRow(imGuiTableRowFlags, minRowHeight);
    }

    /**
     * Append into the first cell of a new row.
     */
    public static void tableNextRow(final float minRowHeight) {
        nTableNextRow(minRowHeight);
    }

    private static native void nTableNextRow(); /*
        ImGui::TableNextRow();
    */

    private static native void nTableNextRow(int imGuiTableRowFlags); /*
        ImGui::TableNextRow(imGuiTableRowFlags);
    */

    private static native void nTableNextRow(int imGuiTableRowFlags, float minRowHeight); /*
        ImGui::TableNextRow(imGuiTableRowFlags, minRowHeight);
    */

    private static native void nTableNextRow(float minRowHeight); /*
        ImGui::TableNextRow(0, minRowHeight);
    */

    /**
     * Append into the next column (or first column of next row if currently in last column). Return true when column is visible.
     */
    public static boolean tableNextColumn() {
        return nTableNextColumn();
    }

    private static native boolean nTableNextColumn(); /*
        return ImGui::TableNextColumn();
    */

    /**
     * Append into the specified column. Return true when column is visible.
     */
    public static boolean tableSetColumnIndex(final int columnN) {
        return nTableSetColumnIndex(columnN);
    }

    private static native boolean nTableSetColumnIndex(int columnN); /*
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

    public static void tableSetupColumn(final String label) {
        nTableSetupColumn(label);
    }

    public static void tableSetupColumn(final String label, final int imGuiTableColumnFlags) {
        nTableSetupColumn(label, imGuiTableColumnFlags);
    }

    public static void tableSetupColumn(final String label, final int imGuiTableColumnFlags, final float initWidthOrWeight) {
        nTableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight);
    }

    public static void tableSetupColumn(final String label, final int imGuiTableColumnFlags, final float initWidthOrWeight, final int userId) {
        nTableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight, userId);
    }

    public static void tableSetupColumn(final String label, final float initWidthOrWeight, final int userId) {
        nTableSetupColumn(label, initWidthOrWeight, userId);
    }

    public static void tableSetupColumn(final String label, final int imGuiTableColumnFlags, final int userId) {
        nTableSetupColumn(label, imGuiTableColumnFlags, userId);
    }

    private static native void nTableSetupColumn(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableSetupColumn(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nTableSetupColumn(String label, int imGuiTableColumnFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nTableSetupColumn(String label, int imGuiTableColumnFlags, float initWidthOrWeight); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nTableSetupColumn(String label, int imGuiTableColumnFlags, float initWidthOrWeight, int userId); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight, userId);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nTableSetupColumn(String label, float initWidthOrWeight, int userId); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableSetupColumn(label, 0, initWidthOrWeight, userId);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    private static native void nTableSetupColumn(String label, int imGuiTableColumnFlags, int userId); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableSetupColumn(label, imGuiTableColumnFlags, 0.0f, userId);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    /**
     * Lock columns/rows so they stay visible when scrolled.
     */
    public static void tableSetupScrollFreeze(final int cols, final int rows) {
        nTableSetupScrollFreeze(cols, rows);
    }

    private static native void nTableSetupScrollFreeze(int cols, int rows); /*
        ImGui::TableSetupScrollFreeze(cols, rows);
    */

    /**
     * Submit all headers cells based on data provided to TableSetupColumn() + submit context menu
     */
    public static void tableHeadersRow() {
        nTableHeadersRow();
    }

    private static native void nTableHeadersRow(); /*
        ImGui::TableHeadersRow();
    */

    /**
     * Submit one header cell manually (rarely used)
     */
    public static void tableHeader(final String label) {
        nTableHeader(label);
    }

    private static native void nTableHeader(String label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImGui::TableHeader(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
    */

    // Tables: Sorting
    // - Call TableGetSortSpecs() to retrieve latest sort specs for the table. NULL when not sorting.
    // - When 'SpecsDirty == true' you should sort your data. It will be true when sorting specs have changed
    //   since last call, or the first time. Make sure to set 'SpecsDirty = false' after sorting, else you may
    //   wastefully sort your data every frame!
    // - Lifetime: don't hold on this pointer over multiple frames or past any subsequent call to BeginTable().

    public static ImGuiTableSortSpecs tableGetSortSpecs() {
        return new ImGuiTableSortSpecs(nTableGetSortSpecs());
    }

    private static native long nTableGetSortSpecs(); /*
        return (uintptr_t)ImGui::TableGetSortSpecs();
    */

    // Tables: Miscellaneous functions
    // - Functions args 'int column_n' treat the default value of -1 as the same as passing the current column index.

    /**
     * Return number of columns (value passed to BeginTable).
     */
    public static int tableGetColumnCount() {
        return nTableGetColumnCount();
    }

    private static native int nTableGetColumnCount(); /*
        return ImGui::TableGetColumnCount();
    */

    /**
     * Return current column index.
     */
    public static int tableGetColumnIndex() {
        return nTableGetColumnIndex();
    }

    private static native int nTableGetColumnIndex(); /*
        return ImGui::TableGetColumnIndex();
    */

    /**
     * Return current row index.
     */
    public static int tableGetRowIndex() {
        return nTableGetRowIndex();
    }

    private static native int nTableGetRowIndex(); /*
        return ImGui::TableGetRowIndex();
    */

    /**
     * Return "" if column didn't have a name declared by TableSetupColumn(). Pass -1 to use current column.
     */
    public static String tableGetColumnName() {
        return nTableGetColumnName();
    }

    /**
     * Return "" if column didn't have a name declared by TableSetupColumn(). Pass -1 to use current column.
     */
    public static String tableGetColumnName(final int columnN) {
        return nTableGetColumnName(columnN);
    }

    private static native String nTableGetColumnName(); /*
        return env->NewStringUTF(ImGui::TableGetColumnName());
    */

    private static native String nTableGetColumnName(int columnN); /*
        return env->NewStringUTF(ImGui::TableGetColumnName(columnN));
    */

    /**
     * Return column flags, so you can query their Enabled/Visible/Sorted/Hovered status flags. Pass -1 to use current column.
     */
    public static int tableGetColumnFlags() {
        return nTableGetColumnFlags();
    }

    /**
     * Return column flags, so you can query their Enabled/Visible/Sorted/Hovered status flags. Pass -1 to use current column.
     */
    public static int tableGetColumnFlags(final int columnN) {
        return nTableGetColumnFlags(columnN);
    }

    private static native int nTableGetColumnFlags(); /*
        return ImGui::TableGetColumnFlags();
    */

    private static native int nTableGetColumnFlags(int columnN); /*
        return ImGui::TableGetColumnFlags(columnN);
    */

    /**
     * change user accessible enabled/disabled state of a column. Set to false to hide the column.
     * User can use the context menu to change this themselves (right-click in headers, or right-click in columns body with ImGuiTableFlags_ContextMenuInBody)
     */
    public static void tableSetColumnEnabled(final int columnN, final boolean value) {
        nTableSetColumnEnabled(columnN, value);
    }

    private static native void nTableSetColumnEnabled(int columnN, boolean value); /*
        ImGui::TableSetColumnEnabled(columnN, value);
    */

    /**
     * Change the color of a cell, row, or column. See ImGuiTableBgTarget_ flags for details.
     */
    public static void tableSetBgColor(final int imGuiTableBgTarget, final int color) {
        nTableSetBgColor(imGuiTableBgTarget, color);
    }

    /**
     * Change the color of a cell, row, or column. See ImGuiTableBgTarget_ flags for details.
     */
    public static void tableSetBgColor(final int imGuiTableBgTarget, final int color, final int columnN) {
        nTableSetBgColor(imGuiTableBgTarget, color, columnN);
    }

    private static native void nTableSetBgColor(int imGuiTableBgTarget, int color); /*
        ImGui::TableSetBgColor(imGuiTableBgTarget, color);
    */

    private static native void nTableSetBgColor(int imGuiTableBgTarget, int color, int columnN); /*
        ImGui::TableSetBgColor(imGuiTableBgTarget, color, columnN);
    */

    // Legacy Columns API (2020: prefer using Tables!)
    // Columns
    // - You can also use SameLine(posX) to mimic simplified columns.

    public static void columns() {
        nColumns();
    }

    public static void columns(final int count) {
        nColumns(count);
    }

    public static void columns(final int count, final String id) {
        nColumns(count, id);
    }

    public static void columns(final int count, final String id, final boolean border) {
        nColumns(count, id, border);
    }

    public static void columns(final String id, final boolean border) {
        nColumns(id, border);
    }

    public static void columns(final boolean border) {
        nColumns(border);
    }

    public static void columns(final int count, final boolean border) {
        nColumns(count, border);
    }

    private static native void nColumns(); /*
        ImGui::Columns();
    */

    private static native void nColumns(int count); /*
        ImGui::Columns(count);
    */

    private static native void nColumns(int count, String id); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImGui::Columns(count, id);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
    */

    private static native void nColumns(int count, String id, boolean border); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImGui::Columns(count, id, border);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
    */

    private static native void nColumns(String id, boolean border); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImGui::Columns(1, id, border);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
    */

    private static native void nColumns(boolean border); /*
        ImGui::Columns(1, NULL, border);
    */

    private static native void nColumns(int count, boolean border); /*
        ImGui::Columns(count, NULL, border);
    */

    /**
     * Next column, defaults to current row or next row if the current row is finished
     */
    public static void nextColumn() {
        nNextColumn();
    }

    private static native void nNextColumn(); /*
        ImGui::NextColumn();
    */

    /**
     * Get current column index
     */
    public static int getColumnIndex() {
        return nGetColumnIndex();
    }

    private static native int nGetColumnIndex(); /*
        return ImGui::GetColumnIndex();
    */

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    public static float getColumnWidth() {
        return nGetColumnWidth();
    }

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    public static float getColumnWidth(final int columnIndex) {
        return nGetColumnWidth(columnIndex);
    }

    private static native float nGetColumnWidth(); /*
        return ImGui::GetColumnWidth();
    */

    private static native float nGetColumnWidth(int columnIndex); /*
        return ImGui::GetColumnWidth(columnIndex);
    */

    /**
     * Set column width (in pixels). pass -1 to use current column
     */
    public static void setColumnWidth(final int columnIndex, final float width) {
        nSetColumnWidth(columnIndex, width);
    }

    private static native void nSetColumnWidth(int columnIndex, float width); /*
        ImGui::SetColumnWidth(columnIndex, width);
    */

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    public static float getColumnOffset() {
        return nGetColumnOffset();
    }

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    public static float getColumnOffset(final int columnIndex) {
        return nGetColumnOffset(columnIndex);
    }

    private static native float nGetColumnOffset(); /*
        return ImGui::GetColumnOffset();
    */

    private static native float nGetColumnOffset(int columnIndex); /*
        return ImGui::GetColumnOffset(columnIndex);
    */

    /**
     * Set position of column line (in pixels, from the left side of the contents region). pass -1 to use current column
     */
    public static void setColumnOffset(final int columnIndex, final float offsetX) {
        nSetColumnOffset(columnIndex, offsetX);
    }

    private static native void nSetColumnOffset(int columnIndex, float offsetX); /*
        ImGui::SetColumnOffset(columnIndex, offsetX);
    */

    public static int getColumnsCount() {
        return nGetColumnsCount();
    }

    private static native int nGetColumnsCount(); /*
        return ImGui::GetColumnsCount();
    */

    // Tab Bars, Tabs
    // Note: Tabs are automatically created by the docking system. Use this to create tab bars/tabs yourself without docking being involved.

    /**
     * Create and append into a TabBar
     */
    public static boolean beginTabBar(final String strId) {
        return nBeginTabBar(strId);
    }

    /**
     * Create and append into a TabBar
     */
    public static boolean beginTabBar(final String strId, final int imGuiTabBarFlags) {
        return nBeginTabBar(strId, imGuiTabBarFlags);
    }

    private static native boolean nBeginTabBar(String obj_strId); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginTabBar(strId);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nBeginTabBar(String obj_strId, int imGuiTabBarFlags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        auto _result = ImGui::BeginTabBar(strId, imGuiTabBarFlags);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    /**
     * Only call EndTabBar() if BeginTabBar() returns true!
     */
    public static void endTabBar() {
        nEndTabBar();
    }

    private static native void nEndTabBar(); /*
        ImGui::EndTabBar();
    */

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(final String label) {
        return nBeginTabItem(label);
    }

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(final String label, final ImBoolean pOpen) {
        return nBeginTabItem(label, pOpen != null ? pOpen.getData() : null);
    }

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(final String label, final ImBoolean pOpen, final int imGuiTabItemFlags) {
        return nBeginTabItem(label, pOpen != null ? pOpen.getData() : null, imGuiTabItemFlags);
    }

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean beginTabItem(final String label, final int imGuiTabItemFlags) {
        return nBeginTabItem(label, imGuiTabItemFlags);
    }

    private static native boolean nBeginTabItem(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::BeginTabItem(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nBeginTabItem(String obj_label, boolean[] obj_pOpen); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        auto _result = ImGui::BeginTabItem(label, (pOpen != NULL ? &pOpen[0] : NULL));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
        return _result;
    */

    private static native boolean nBeginTabItem(String obj_label, boolean[] obj_pOpen, int imGuiTabItemFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto pOpen = obj_pOpen == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_pOpen, JNI_FALSE);
        auto _result = ImGui::BeginTabItem(label, (pOpen != NULL ? &pOpen[0] : NULL), imGuiTabItemFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (pOpen != NULL) env->ReleasePrimitiveArrayCritical(obj_pOpen, pOpen, JNI_FALSE);
        return _result;
    */

    private static native boolean nBeginTabItem(String obj_label, int imGuiTabItemFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::BeginTabItem(label, NULL, imGuiTabItemFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Only call EndTabItem() if BeginTabItem() returns true!
     */
    public static void endTabItem() {
        nEndTabItem();
    }

    private static native void nEndTabItem(); /*
        ImGui::EndTabItem();
    */

    /**
     * Create a Tab behaving like a button. return true when clicked. cannot be selected in the tab bar.
     */
    public static boolean tabItemButton(final String label) {
        return nTabItemButton(label);
    }

    /**
     * Create a Tab behaving like a button. return true when clicked. cannot be selected in the tab bar.
     */
    public static boolean tabItemButton(final String label, final int imGuiTabItemFlags) {
        return nTabItemButton(label, imGuiTabItemFlags);
    }

    private static native boolean nTabItemButton(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TabItemButton(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nTabItemButton(String obj_label, int imGuiTabItemFlags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::TabItemButton(label, imGuiTabItemFlags);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    /**
     * Notify TabBar or Docking system of a closed tab/window ahead (useful to reduce visual flicker on reorderable tab bars).
     * For tab-bar: call after BeginTabBar() and before Tab submissions. Otherwise call with a window name.
     */
    public static void setTabItemClosed(final String tabOrDockedWindowLabel) {
        nSetTabItemClosed(tabOrDockedWindowLabel);
    }

    private static native void nSetTabItemClosed(String tabOrDockedWindowLabel); /*MANUAL
        auto tabOrDockedWindowLabel = obj_tabOrDockedWindowLabel == NULL ? NULL : (char*)env->GetStringUTFChars(obj_tabOrDockedWindowLabel, JNI_FALSE);
        ImGui::SetTabItemClosed(tabOrDockedWindowLabel);
        if (tabOrDockedWindowLabel != NULL) env->ReleaseStringUTFChars(obj_tabOrDockedWindowLabel, tabOrDockedWindowLabel);
    */

    // Docking
    // [BETA API] Enable with io.ConfigFlags |= ImGuiConfigFlags_DockingEnable.
    // Note: You can use most Docking facilities without calling any API. You DO NOT need to call DockSpace() to use Docking!
    // - To dock windows: if io.ConfigDockingWithShift == false (default) drag window from their title bar.
    // - To dock windows: if io.ConfigDockingWithShift == true: hold SHIFT anywhere while moving windows.
    // About DockSpace:
    // - Use DockSpace() to create an explicit dock node _within_ an existing window. See Docking demo for details.
    // - DockSpace() needs to be submitted _before_ any window they can host. If you use a dockspace, submit it early in your app.

    public static int dockSpace(final int imGuiID) {
        return nDockSpace(imGuiID);
    }

    public static int dockSpace(final int imGuiID, final ImVec2 size) {
        return nDockSpace(imGuiID, size.x, size.y);
    }

    public static int dockSpace(final int imGuiID, final float sizeX, final float sizeY) {
        return nDockSpace(imGuiID, sizeX, sizeY);
    }

    public static int dockSpace(final int imGuiID, final ImVec2 size, final int imGuiDockNodeFlags) {
        return nDockSpace(imGuiID, size.x, size.y, imGuiDockNodeFlags);
    }

    public static int dockSpace(final int imGuiID, final float sizeX, final float sizeY, final int imGuiDockNodeFlags) {
        return nDockSpace(imGuiID, sizeX, sizeY, imGuiDockNodeFlags);
    }

    public static int dockSpace(final int imGuiID, final ImVec2 size, final int imGuiDockNodeFlags, final ImGuiWindowClass windowClass) {
        return nDockSpace(imGuiID, size.x, size.y, imGuiDockNodeFlags, windowClass.ptr);
    }

    public static int dockSpace(final int imGuiID, final float sizeX, final float sizeY, final int imGuiDockNodeFlags, final ImGuiWindowClass windowClass) {
        return nDockSpace(imGuiID, sizeX, sizeY, imGuiDockNodeFlags, windowClass.ptr);
    }

    public static int dockSpace(final int imGuiID, final int imGuiDockNodeFlags, final ImGuiWindowClass windowClass) {
        return nDockSpace(imGuiID, imGuiDockNodeFlags, windowClass.ptr);
    }

    public static int dockSpace(final int imGuiID, final ImGuiWindowClass windowClass) {
        return nDockSpace(imGuiID, windowClass.ptr);
    }

    public static int dockSpace(final int imGuiID, final ImVec2 size, final ImGuiWindowClass windowClass) {
        return nDockSpace(imGuiID, size.x, size.y, windowClass.ptr);
    }

    public static int dockSpace(final int imGuiID, final float sizeX, final float sizeY, final ImGuiWindowClass windowClass) {
        return nDockSpace(imGuiID, sizeX, sizeY, windowClass.ptr);
    }

    private static native int nDockSpace(int imGuiID); /*
        return ImGui::DockSpace(imGuiID);
    */

    private static native int nDockSpace(int imGuiID, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::DockSpace(imGuiID, size);
        return _result;
    */

    private static native int nDockSpace(int imGuiID, float sizeX, float sizeY, int imGuiDockNodeFlags); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::DockSpace(imGuiID, size, imGuiDockNodeFlags);
        return _result;
    */

    private static native int nDockSpace(int imGuiID, float sizeX, float sizeY, int imGuiDockNodeFlags, long windowClass); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::DockSpace(imGuiID, size, imGuiDockNodeFlags, reinterpret_cast<ImGuiWindowClass*>(windowClass));
        return _result;
    */

    private static native int nDockSpace(int imGuiID, int imGuiDockNodeFlags, long windowClass); /*
        return ImGui::DockSpace(imGuiID, ImVec2(0,0), imGuiDockNodeFlags, reinterpret_cast<ImGuiWindowClass*>(windowClass));
    */

    private static native int nDockSpace(int imGuiID, long windowClass); /*
        return ImGui::DockSpace(imGuiID, ImVec2(0,0), 0, reinterpret_cast<ImGuiWindowClass*>(windowClass));
    */

    private static native int nDockSpace(int imGuiID, float sizeX, float sizeY, long windowClass); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::DockSpace(imGuiID, size, 0, reinterpret_cast<ImGuiWindowClass*>(windowClass));
        return _result;
    */

    public static int dockSpaceOverViewport() {
        return nDockSpaceOverViewport();
    }

    public static int dockSpaceOverViewport(final ImGuiViewport viewport) {
        return nDockSpaceOverViewport(viewport.ptr);
    }

    public static int dockSpaceOverViewport(final ImGuiViewport viewport, final int imGuiDockNodeFlags) {
        return nDockSpaceOverViewport(viewport.ptr, imGuiDockNodeFlags);
    }

    public static int dockSpaceOverViewport(final ImGuiViewport viewport, final int imGuiDockNodeFlags, final ImGuiWindowClass windowClass) {
        return nDockSpaceOverViewport(viewport.ptr, imGuiDockNodeFlags, windowClass.ptr);
    }

    public static int dockSpaceOverViewport(final ImGuiViewport viewport, final ImGuiWindowClass windowClass) {
        return nDockSpaceOverViewport(viewport.ptr, windowClass.ptr);
    }

    private static native int nDockSpaceOverViewport(); /*
        return ImGui::DockSpaceOverViewport();
    */

    private static native int nDockSpaceOverViewport(long viewport); /*
        return ImGui::DockSpaceOverViewport(reinterpret_cast<ImGuiViewport*>(viewport));
    */

    private static native int nDockSpaceOverViewport(long viewport, int imGuiDockNodeFlags); /*
        return ImGui::DockSpaceOverViewport(reinterpret_cast<ImGuiViewport*>(viewport), imGuiDockNodeFlags);
    */

    private static native int nDockSpaceOverViewport(long viewport, int imGuiDockNodeFlags, long windowClass); /*
        return ImGui::DockSpaceOverViewport(reinterpret_cast<ImGuiViewport*>(viewport), imGuiDockNodeFlags, reinterpret_cast<ImGuiWindowClass*>(windowClass));
    */

    private static native int nDockSpaceOverViewport(long viewport, long windowClass); /*
        return ImGui::DockSpaceOverViewport(reinterpret_cast<ImGuiViewport*>(viewport), 0, reinterpret_cast<ImGuiWindowClass*>(windowClass));
    */

    /**
     * Set next window dock id
     */
    public static void setNextWindowDockID(final int dockId) {
        nSetNextWindowDockID(dockId);
    }

    /**
     * Set next window dock id
     */
    public static void setNextWindowDockID(final int dockId, final int imGuiCond) {
        nSetNextWindowDockID(dockId, imGuiCond);
    }

    private static native void nSetNextWindowDockID(int dockId); /*
        ImGui::SetNextWindowDockID(dockId);
    */

    private static native void nSetNextWindowDockID(int dockId, int imGuiCond); /*
        ImGui::SetNextWindowDockID(dockId, imGuiCond);
    */

    /**
     * set next window class (rare/advanced uses: provide hints to the platform backend via altered viewport flags and parent/child info)
     */
    public static void setNextWindowClass(final ImGuiWindowClass windowClass) {
        nSetNextWindowClass(windowClass.ptr);
    }

    private static native void nSetNextWindowClass(long windowClass); /*
        ImGui::SetNextWindowClass(reinterpret_cast<ImGuiWindowClass*>(windowClass));
    */

    public static int getWindowDockID() {
        return nGetWindowDockID();
    }

    private static native int nGetWindowDockID(); /*
        return ImGui::GetWindowDockID();
    */

    /**
     * Is current window docked into another window?
     */
    public static boolean isWindowDocked() {
        return nIsWindowDocked();
    }

    private static native boolean nIsWindowDocked(); /*
        return ImGui::IsWindowDocked();
    */

    // Logging/Capture
    // - All text output from the interface can be captured into tty/file/clipboard. By default, tree nodes are automatically opened during logging.

    /**
     * Start logging to tty (stdout)
     */
    public static void logToTTY() {
        nLogToTTY();
    }

    /**
     * Start logging to tty (stdout)
     */
    public static void logToTTY(final int autoOpenDepth) {
        nLogToTTY(autoOpenDepth);
    }

    private static native void nLogToTTY(); /*
        ImGui::LogToTTY();
    */

    private static native void nLogToTTY(int autoOpenDepth); /*
        ImGui::LogToTTY(autoOpenDepth);
    */

    /**
     * Start logging to file
     */
    public static void logToFile() {
        nLogToFile();
    }

    /**
     * Start logging to file
     */
    public static void logToFile(final int autoOpenDepth) {
        nLogToFile(autoOpenDepth);
    }

    /**
     * Start logging to file
     */
    public static void logToFile(final int autoOpenDepth, final String filename) {
        nLogToFile(autoOpenDepth, filename);
    }

    /**
     * Start logging to file
     */
    public static void logToFile(final String filename) {
        nLogToFile(filename);
    }

    private static native void nLogToFile(); /*
        ImGui::LogToFile();
    */

    private static native void nLogToFile(int autoOpenDepth); /*
        ImGui::LogToFile(autoOpenDepth);
    */

    private static native void nLogToFile(int autoOpenDepth, String filename); /*MANUAL
        auto filename = obj_filename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_filename, JNI_FALSE);
        ImGui::LogToFile(autoOpenDepth, filename);
        if (filename != NULL) env->ReleaseStringUTFChars(obj_filename, filename);
    */

    private static native void nLogToFile(String filename); /*MANUAL
        auto filename = obj_filename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_filename, JNI_FALSE);
        ImGui::LogToFile(-1, filename);
        if (filename != NULL) env->ReleaseStringUTFChars(obj_filename, filename);
    */

    /**
     * Start logging to OS clipboard
     */
    public static void logToClipboard() {
        nLogToClipboard();
    }

    /**
     * Start logging to OS clipboard
     */
    public static void logToClipboard(final int autoOpenDepth) {
        nLogToClipboard(autoOpenDepth);
    }

    private static native void nLogToClipboard(); /*
        ImGui::LogToClipboard();
    */

    private static native void nLogToClipboard(int autoOpenDepth); /*
        ImGui::LogToClipboard(autoOpenDepth);
    */

    /**
     * Stop logging (close file, etc.)
     */
    public static void logFinish() {
        nLogFinish();
    }

    private static native void nLogFinish(); /*
        ImGui::LogFinish();
    */

    /**
     * Helper to display buttons for logging to tty/file/clipboard
     */
    public static void logButtons() {
        nLogButtons();
    }

    private static native void nLogButtons(); /*
        ImGui::LogButtons();
    */

    /**
     * Pass text data straight to log (without being displayed)
     */
    public static void logText(final String text) {
        nLogText(text);
    }

    private static native void nLogText(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::LogText(text, NULL);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    // Drag and Drop
    // - If you stop calling BeginDragDropSource() the payload is preserved however it won't have a preview tooltip (we currently display a fallback "..." tooltip as replacement)

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    public static boolean beginDragDropSource() {
        return nBeginDragDropSource();
    }

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    public static boolean beginDragDropSource(final int imGuiDragDropFlags) {
        return nBeginDragDropSource(imGuiDragDropFlags);
    }

    private static native boolean nBeginDragDropSource(); /*
        return ImGui::BeginDragDropSource();
    */

    private static native boolean nBeginDragDropSource(int imGuiDragDropFlags); /*
        return ImGui::BeginDragDropSource(imGuiDragDropFlags);
    */

    private static WeakReference<Object> payloadRef = null;
    private static final byte[] PAYLOAD_PLACEHOLDER_DATA = new byte[1];

    /**
     * Type is a user defined string of maximum 32 characters. Strings starting with '_' are reserved for dear imgui internal types.
     * <p>
     * BINDING NOTICE:
     * Method adopted for Java, so objects are used instead of raw bytes.
     * Binding stores a reference to the object in a form of {@link WeakReference}.
     */
    public static boolean setDragDropPayload(final String dataType, final Object payload) {
        return setDragDropPayload(dataType, payload, 0);
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
        return setDragDropPayload(payload, 0);
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
    public static void endDragDropSource() {
        nEndDragDropSource();
    }

    private static native void nEndDragDropSource(); /*
        ImGui::EndDragDropSource();
    */

    /**
     * Call after submitting an item that may receive a payload. If this returns true, you can call AcceptDragDropPayload() + EndDragDropTarget()
     */
    public static boolean beginDragDropTarget() {
        return nBeginDragDropTarget();
    }

    private static native boolean nBeginDragDropTarget(); /*
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
    public static void endDragDropTarget() {
        nEndDragDropTarget();
    }

    private static native void nEndDragDropTarget(); /*
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
        const ImGuiPayload* payload = ImGui::GetDragDropPayload();
        return payload != NULL && payload->Data != NULL;
    */

    private static native boolean nHasDragDropPayload(String dataType); /*
        const ImGuiPayload* payload = ImGui::GetDragDropPayload();
        return payload != NULL && payload->IsDataType(dataType);
    */

    /**
     * Disable all user interactions and dim items visuals (applying style.DisabledAlpha over current colors)
     * BeginDisabled(false) essentially does nothing useful but is provided to facilitate use of boolean expressions.
     * If you can avoid calling BeginDisabled(False)/EndDisabled() best to avoid it.
     */
    public static void beginDisabled() {
        nBeginDisabled();
    }

    /**
     * Disable all user interactions and dim items visuals (applying style.DisabledAlpha over current colors)
     * BeginDisabled(false) essentially does nothing useful but is provided to facilitate use of boolean expressions.
     * If you can avoid calling BeginDisabled(False)/EndDisabled() best to avoid it.
     */
    public static void beginDisabled(final boolean disabled) {
        nBeginDisabled(disabled);
    }

    private static native void nBeginDisabled(); /*
        ImGui::BeginDisabled();
    */

    private static native void nBeginDisabled(boolean disabled); /*
        ImGui::BeginDisabled(disabled);
    */

    public static void endDisabled() {
        nEndDisabled();
    }

    private static native void nEndDisabled(); /*
        ImGui::EndDisabled();
    */

    // Clipping
    // - Mouse hovering is affected by ImGui::PushClipRect() calls, unlike direct calls to ImDrawList::PushClipRect() which are render only.

    public static void pushClipRect(final ImVec2 clipRectMin, final ImVec2 clipRectMax, final boolean intersectWithCurrentClipRect) {
        nPushClipRect(clipRectMin.x, clipRectMin.y, clipRectMax.x, clipRectMax.y, intersectWithCurrentClipRect);
    }

    public static void pushClipRect(final float clipRectMinX, final float clipRectMinY, final float clipRectMaxX, final float clipRectMaxY, final boolean intersectWithCurrentClipRect) {
        nPushClipRect(clipRectMinX, clipRectMinY, clipRectMaxX, clipRectMaxY, intersectWithCurrentClipRect);
    }

    private static native void nPushClipRect(float clipRectMinX, float clipRectMinY, float clipRectMaxX, float clipRectMaxY, boolean intersectWithCurrentClipRect); /*MANUAL
        ImVec2 clipRectMin = ImVec2(clipRectMinX, clipRectMinY);
        ImVec2 clipRectMax = ImVec2(clipRectMaxX, clipRectMaxY);
        ImGui::PushClipRect(clipRectMin, clipRectMax, intersectWithCurrentClipRect);
    */

    public static void popClipRect() {
        nPopClipRect();
    }

    private static native void nPopClipRect(); /*
        ImGui::PopClipRect();
    */

    // Focus, Activation
    // - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

    /**
     * Make last item the default focused item of a window.
     */
    public static void setItemDefaultFocus() {
        nSetItemDefaultFocus();
    }

    private static native void nSetItemDefaultFocus(); /*
        ImGui::SetItemDefaultFocus();
    */

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    public static void setKeyboardFocusHere() {
        nSetKeyboardFocusHere();
    }

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    public static void setKeyboardFocusHere(final int offset) {
        nSetKeyboardFocusHere(offset);
    }

    private static native void nSetKeyboardFocusHere(); /*
        ImGui::SetKeyboardFocusHere();
    */

    private static native void nSetKeyboardFocusHere(int offset); /*
        ImGui::SetKeyboardFocusHere(offset);
    */

    // Item/Widgets Utilities
    // - Most of the functions are referring to the last/previous item we submitted.
    // - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    public static boolean isItemHovered() {
        return nIsItemHovered();
    }

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    public static boolean isItemHovered(final int imGuiHoveredFlags) {
        return nIsItemHovered(imGuiHoveredFlags);
    }

    private static native boolean nIsItemHovered(); /*
        return ImGui::IsItemHovered();
    */

    private static native boolean nIsItemHovered(int imGuiHoveredFlags); /*
        return ImGui::IsItemHovered(imGuiHoveredFlags);
    */

    /**
     * Is the last item active? (e.g. button being held, text field being edited.
     * This will continuously return true while holding mouse button on an item.
     * Items that don't interact will always return false)
     */
    public static boolean isItemActive() {
        return nIsItemActive();
    }

    private static native boolean nIsItemActive(); /*
        return ImGui::IsItemActive();
    */

    /**
     * Is the last item focused for keyboard/gamepad navigation?
     */
    public static boolean isItemFocused() {
        return nIsItemFocused();
    }

    private static native boolean nIsItemFocused(); /*
        return ImGui::IsItemFocused();
    */

    /**
     * Is the last item clicked? (e.g. button/node just clicked on) == {@code IsMouseClicked(mouseButton) && IsItemHovered()}
     */
    public static boolean isItemClicked() {
        return nIsItemClicked();
    }

    /**
     * Is the last item clicked? (e.g. button/node just clicked on) == {@code IsMouseClicked(mouseButton) && IsItemHovered()}
     */
    public static boolean isItemClicked(final int mouseButton) {
        return nIsItemClicked(mouseButton);
    }

    private static native boolean nIsItemClicked(); /*
        return ImGui::IsItemClicked();
    */

    private static native boolean nIsItemClicked(int mouseButton); /*
        return ImGui::IsItemClicked(mouseButton);
    */

    /**
     * Is the last item visible? (items may be out of sight because of clipping/scrolling)
     */
    public static boolean isItemVisible() {
        return nIsItemVisible();
    }

    private static native boolean nIsItemVisible(); /*
        return ImGui::IsItemVisible();
    */

    /**
     * Did the last item modify its underlying value this frame? or was pressed? This is generally the same as the "bool" return value of many widgets.
     */
    public static boolean isItemEdited() {
        return nIsItemEdited();
    }

    private static native boolean nIsItemEdited(); /*
        return ImGui::IsItemEdited();
    */

    /**
     * Was the last item just made active (item was previously inactive).
     */
    public static boolean isItemActivated() {
        return nIsItemActivated();
    }

    private static native boolean nIsItemActivated(); /*
        return ImGui::IsItemActivated();
    */

    /**
     * Was the last item just made inactive (item was previously active). Useful for Undo/Redo patterns with widgets that requires continuous editing.
     */
    public static boolean isItemDeactivated() {
        return nIsItemDeactivated();
    }

    private static native boolean nIsItemDeactivated(); /*
        return ImGui::IsItemDeactivated();
    */

    /**
     * Was the last item just made inactive and made a value change when it was active? (e.g. Slider/Drag moved).
     * Useful for Undo/Redo patterns with widgets that requires continuous editing.
     * Note that you may get false positives (some widgets such as Combo()/ListBox()/Selectable() will return true even when clicking an already selected item).
     */
    public static boolean isItemDeactivatedAfterEdit() {
        return nIsItemDeactivatedAfterEdit();
    }

    private static native boolean nIsItemDeactivatedAfterEdit(); /*
        return ImGui::IsItemDeactivatedAfterEdit();
    */

    /**
     * Was the last item open state toggled? set by TreeNode().
     */
    public static boolean isItemToggledOpen() {
        return nIsItemToggledOpen();
    }

    private static native boolean nIsItemToggledOpen(); /*
        return ImGui::IsItemToggledOpen();
    */

    /**
     * Is any item hovered?
     */
    public static boolean isAnyItemHovered() {
        return nIsAnyItemHovered();
    }

    private static native boolean nIsAnyItemHovered(); /*
        return ImGui::IsAnyItemHovered();
    */

    /**
     * Is any item active?
     */
    public static boolean isAnyItemActive() {
        return nIsAnyItemActive();
    }

    private static native boolean nIsAnyItemActive(); /*
        return ImGui::IsAnyItemActive();
    */
    /**
     * Is any item focused?
     */
    public static boolean isAnyItemFocused() {
        return nIsAnyItemFocused();
    }

    private static native boolean nIsAnyItemFocused(); /*
        return ImGui::IsAnyItemFocused();
    */

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static ImVec2 getItemRectMin() {
        final ImVec2 dst = new ImVec2();
        nGetItemRectMin(dst);
        return dst;
    }

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static float getItemRectMinX() {
        return nGetItemRectMinX();
    }

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static float getItemRectMinY() {
        return nGetItemRectMinY();
    }

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static void getItemRectMin(final ImVec2 dst) {
        nGetItemRectMin(dst);
    }

    private static native void nGetItemRectMin(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectMin(), dst);
    */

    private static native float nGetItemRectMinX(); /*
        return ImGui::GetItemRectMin().x;
    */

    private static native float nGetItemRectMinY(); /*
        return ImGui::GetItemRectMin().y;
    */

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static ImVec2 getItemRectMax() {
        final ImVec2 dst = new ImVec2();
        nGetItemRectMax(dst);
        return dst;
    }

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static float getItemRectMaxX() {
        return nGetItemRectMaxX();
    }

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static float getItemRectMaxY() {
        return nGetItemRectMaxY();
    }

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static void getItemRectMax(final ImVec2 dst) {
        nGetItemRectMax(dst);
    }

    private static native void nGetItemRectMax(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectMax(), dst);
    */

    private static native float nGetItemRectMaxX(); /*
        return ImGui::GetItemRectMax().x;
    */

    private static native float nGetItemRectMaxY(); /*
        return ImGui::GetItemRectMax().y;
    */

    /**
     * Get size of last item
     */
    public static ImVec2 getItemRectSize() {
        final ImVec2 dst = new ImVec2();
        nGetItemRectSize(dst);
        return dst;
    }

    /**
     * Get size of last item
     */
    public static float getItemRectSizeX() {
        return nGetItemRectSizeX();
    }

    /**
     * Get size of last item
     */
    public static float getItemRectSizeY() {
        return nGetItemRectSizeY();
    }

    /**
     * Get size of last item
     */
    public static void getItemRectSize(final ImVec2 dst) {
        nGetItemRectSize(dst);
    }

    private static native void nGetItemRectSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectSize(), dst);
    */

    private static native float nGetItemRectSizeX(); /*
        return ImGui::GetItemRectSize().x;
    */

    private static native float nGetItemRectSizeY(); /*
        return ImGui::GetItemRectSize().y;
    */

    /**
     * Allow last item to be overlapped by a subsequent item. sometimes useful with invisible buttons, selectables, etc. to catch unused area.
     */
    public static void setItemAllowOverlap() {
        nSetItemAllowOverlap();
    }

    private static native void nSetItemAllowOverlap(); /*
        ImGui::SetItemAllowOverlap();
    */

    // Viewports
    // - Currently represents the Platform Window created by the application which is hosting our Dear ImGui windows.
    // - In 'docking' branch with multi-viewport enabled, we extend this concept to have multiple active viewports.
    // - In the future we will extend this concept further to also represent Platform Monitor and support a "no main platform window" operation mode.

    private static final ImGuiViewport _GETMAINVIEWPORT_1 = new ImGuiViewport(0);

    /**
     * Return primary/default viewport.
     */
    public static ImGuiViewport getMainViewport() {
        _GETMAINVIEWPORT_1.ptr = nGetMainViewport();
        return _GETMAINVIEWPORT_1;
    }

    private static native long nGetMainViewport(); /*
        return (uintptr_t)ImGui::GetMainViewport();
    */

    // Miscellaneous Utilities

    /**
     * Test if rectangle (of given size, starting from cursor position) is visible / not clipped.
     */
    public static boolean isRectVisible(final ImVec2 size) {
        return nIsRectVisible(size.x, size.y);
    }

    /**
     * Test if rectangle (of given size, starting from cursor position) is visible / not clipped.
     */
    public static boolean isRectVisible(final float sizeX, final float sizeY) {
        return nIsRectVisible(sizeX, sizeY);
    }

    private static native boolean nIsRectVisible(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::IsRectVisible(size);
        return _result;
    */

    /**
     * Test if rectangle (in screen space) is visible / not clipped. to perform coarse clipping on user's side.
     */
    public static boolean isRectVisible(final ImVec2 rectMin, final ImVec2 rectMax) {
        return nIsRectVisible(rectMin.x, rectMin.y, rectMax.x, rectMax.y);
    }

    /**
     * Test if rectangle (in screen space) is visible / not clipped. to perform coarse clipping on user's side.
     */
    public static boolean isRectVisible(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        return nIsRectVisible(rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    private static native boolean nIsRectVisible(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*MANUAL
        ImVec2 rectMin = ImVec2(rectMinX, rectMinY);
        ImVec2 rectMax = ImVec2(rectMaxX, rectMaxY);
        auto _result = ImGui::IsRectVisible(rectMin, rectMax);
        return _result;
    */

    /**
     * Get global imgui time. incremented by io.DeltaTime every frame.
     */
    public static double getTime() {
        return nGetTime();
    }

    private static native double nGetTime(); /*
        return ImGui::GetTime();
    */

    /**
     * Get global imgui frame count. incremented by 1 every frame.
     */
    public static int getFrameCount() {
        return nGetFrameCount();
    }

    private static native int nGetFrameCount(); /*
        return ImGui::GetFrameCount();
    */

    /**
     * Get background draw list for the viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getBackgroundDrawList() {
        return new ImDrawList(nGetBackgroundDrawList());
    }

    /**
     * Get background draw list for the viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getBackgroundDrawList(final ImGuiViewport viewport) {
        return new ImDrawList(nGetBackgroundDrawList(viewport.ptr));
    }

    private static native long nGetBackgroundDrawList(); /*
        return (uintptr_t)ImGui::GetBackgroundDrawList();
    */

    private static native long nGetBackgroundDrawList(long viewport); /*
        return (uintptr_t)ImGui::GetBackgroundDrawList(reinterpret_cast<ImGuiViewport*>(viewport));
    */

    /**
     * Get foreground draw list for the viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getForegroundDrawList() {
        return new ImDrawList(nGetForegroundDrawList());
    }

    /**
     * Get foreground draw list for the viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList getForegroundDrawList(final ImGuiViewport viewport) {
        return new ImDrawList(nGetForegroundDrawList(viewport.ptr));
    }

    private static native long nGetForegroundDrawList(); /*
        return (uintptr_t)ImGui::GetForegroundDrawList();
    */

    private static native long nGetForegroundDrawList(long viewport); /*
        return (uintptr_t)ImGui::GetForegroundDrawList(reinterpret_cast<ImGuiViewport*>(viewport));
    */

    // TODO GetDrawListSharedData

    /**
     * Get a string corresponding to the enum value (for display, saving, etc.).
     */
    public static String getStyleColorName(final int imGuiColIdx) {
        return nGetStyleColorName(imGuiColIdx);
    }

    private static native String nGetStyleColorName(int imGuiColIdx); /*
        return env->NewStringUTF(ImGui::GetStyleColorName(imGuiColIdx));
    */

    /**
     * Replace current window storage with our own (if you want to manipulate it yourself, typically clear subsection of it).
     */
    public static void setStateStorage(final ImGuiStorage storage) {
        nSetStateStorage(storage.ptr);
    }

    private static native void nSetStateStorage(long storage); /*
        ImGui::SetStateStorage(reinterpret_cast<ImGuiStorage*>(storage));
    */

    public static ImGuiStorage getStateStorage() {
        return new ImGuiStorage(nGetStateStorage());
    }

    private static native long nGetStateStorage(); /*
        return (uintptr_t)ImGui::GetStateStorage();
    */

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static boolean beginChildFrame(final int id, final ImVec2 size) {
        return nBeginChildFrame(id, size.x, size.y);
    }

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static boolean beginChildFrame(final int id, final float sizeX, final float sizeY) {
        return nBeginChildFrame(id, sizeX, sizeY);
    }

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static boolean beginChildFrame(final int id, final ImVec2 size, final int imGuiWindowFlags) {
        return nBeginChildFrame(id, size.x, size.y, imGuiWindowFlags);
    }

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static boolean beginChildFrame(final int id, final float sizeX, final float sizeY, final int imGuiWindowFlags) {
        return nBeginChildFrame(id, sizeX, sizeY, imGuiWindowFlags);
    }

    private static native boolean nBeginChildFrame(int id, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChildFrame(id, size);
        return _result;
    */

    private static native boolean nBeginChildFrame(int id, float sizeX, float sizeY, int imGuiWindowFlags); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::BeginChildFrame(id, size, imGuiWindowFlags);
        return _result;
    */

    /**
     * Always call EndChildFrame() regardless of BeginChildFrame() return values (which indicates a collapsed/clipped window)
     */
    public static void endChildFrame() {
        nEndChildFrame();
    }

    private static native void nEndChildFrame(); /*
        ImGui::EndChildFrame();
    */

    // Text Utilities

    public static ImVec2 calcTextSize(final String text) {
        final ImVec2 dst = new ImVec2();
        nCalcTextSize(dst, text);
        return dst;
    }

    public static float calcTextSizeX(final String text) {
        return nCalcTextSizeX(text);
    }

    public static float calcTextSizeY(final String text) {
        return nCalcTextSizeY(text);
    }

    public static void calcTextSize(final ImVec2 dst, final String text) {
        nCalcTextSize(dst, text);
    }

    public static ImVec2 calcTextSize(final String text, final boolean hideTextAfterDoubleHash) {
        final ImVec2 dst = new ImVec2();
        nCalcTextSize(dst, text, hideTextAfterDoubleHash);
        return dst;
    }

    public static float calcTextSizeX(final String text, final boolean hideTextAfterDoubleHash) {
        return nCalcTextSizeX(text, hideTextAfterDoubleHash);
    }

    public static float calcTextSizeY(final String text, final boolean hideTextAfterDoubleHash) {
        return nCalcTextSizeY(text, hideTextAfterDoubleHash);
    }

    public static void calcTextSize(final ImVec2 dst, final String text, final boolean hideTextAfterDoubleHash) {
        nCalcTextSize(dst, text, hideTextAfterDoubleHash);
    }

    public static ImVec2 calcTextSize(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        final ImVec2 dst = new ImVec2();
        nCalcTextSize(dst, text, hideTextAfterDoubleHash, wrapWidth);
        return dst;
    }

    public static float calcTextSizeX(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return nCalcTextSizeX(text, hideTextAfterDoubleHash, wrapWidth);
    }

    public static float calcTextSizeY(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return nCalcTextSizeY(text, hideTextAfterDoubleHash, wrapWidth);
    }

    public static void calcTextSize(final ImVec2 dst, final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        nCalcTextSize(dst, text, hideTextAfterDoubleHash, wrapWidth);
    }

    public static ImVec2 calcTextSize(final String text, final float wrapWidth) {
        final ImVec2 dst = new ImVec2();
        nCalcTextSize(dst, text, wrapWidth);
        return dst;
    }

    public static float calcTextSizeX(final String text, final float wrapWidth) {
        return nCalcTextSizeX(text, wrapWidth);
    }

    public static float calcTextSizeY(final String text, final float wrapWidth) {
        return nCalcTextSizeY(text, wrapWidth);
    }

    public static void calcTextSize(final ImVec2 dst, final String text, final float wrapWidth) {
        nCalcTextSize(dst, text, wrapWidth);
    }

    private static native void nCalcTextSize(ImVec2 dst, String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        Jni::ImVec2Cpy(env, ImGui::CalcTextSize(text, NULL), dst);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native float nCalcTextSizeX(String obj_text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL).x;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native float nCalcTextSizeY(String obj_text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL).y;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native void nCalcTextSize(ImVec2 dst, String text, boolean hideTextAfterDoubleHash); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        Jni::ImVec2Cpy(env, ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash), dst);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native float nCalcTextSizeX(String obj_text, boolean hideTextAfterDoubleHash); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash).x;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native float nCalcTextSizeY(String obj_text, boolean hideTextAfterDoubleHash); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash).y;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native void nCalcTextSize(ImVec2 dst, String text, boolean hideTextAfterDoubleHash, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        Jni::ImVec2Cpy(env, ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash, wrapWidth), dst);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native float nCalcTextSizeX(String obj_text, boolean hideTextAfterDoubleHash, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash, wrapWidth).x;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native float nCalcTextSizeY(String obj_text, boolean hideTextAfterDoubleHash, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL, hideTextAfterDoubleHash, wrapWidth).y;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native void nCalcTextSize(ImVec2 dst, String text, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        Jni::ImVec2Cpy(env, ImGui::CalcTextSize(text, NULL, false, wrapWidth), dst);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    private static native float nCalcTextSizeX(String obj_text, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL, false, wrapWidth).x;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    private static native float nCalcTextSizeY(String obj_text, float wrapWidth); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        auto _result = ImGui::CalcTextSize(text, NULL, false, wrapWidth).y;
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
        return _result;
    */

    // Color Utilities

    public static ImVec4 colorConvertU32ToFloat4(final int in) {
        final ImVec4 dst = new ImVec4();
        nColorConvertU32ToFloat4(dst, in);
        return dst;
    }

    public static float colorConvertU32ToFloat4X(final int in) {
        return nColorConvertU32ToFloat4X(in);
    }

    public static float colorConvertU32ToFloat4Y(final int in) {
        return nColorConvertU32ToFloat4Y(in);
    }

    public static float colorConvertU32ToFloat4Z(final int in) {
        return nColorConvertU32ToFloat4Z(in);
    }

    public static float colorConvertU32ToFloat4W(final int in) {
        return nColorConvertU32ToFloat4W(in);
    }

    public static void colorConvertU32ToFloat4(final ImVec4 dst, final int in) {
        nColorConvertU32ToFloat4(dst, in);
    }

    private static native void nColorConvertU32ToFloat4(ImVec4 dst, int in); /*
        Jni::ImVec4Cpy(env, ImGui::ColorConvertU32ToFloat4(in), dst);
    */

    private static native float nColorConvertU32ToFloat4X(int in); /*
        return ImGui::ColorConvertU32ToFloat4(in).x;
    */

    private static native float nColorConvertU32ToFloat4Y(int in); /*
        return ImGui::ColorConvertU32ToFloat4(in).y;
    */

    private static native float nColorConvertU32ToFloat4Z(int in); /*
        return ImGui::ColorConvertU32ToFloat4(in).z;
    */

    private static native float nColorConvertU32ToFloat4W(int in); /*
        return ImGui::ColorConvertU32ToFloat4(in).w;
    */

    public static int colorConvertFloat4ToU32(final ImVec4 in) {
        return nColorConvertFloat4ToU32(in.x, in.y, in.z, in.w);
    }

    public static int colorConvertFloat4ToU32(final float inX, final float inY, final float inZ, final float inW) {
        return nColorConvertFloat4ToU32(inX, inY, inZ, inW);
    }

    private static native int nColorConvertFloat4ToU32(float inX, float inY, float inZ, float inW); /*MANUAL
        ImVec4 in = ImVec4(inX, inY, inZ, inW);
        auto _result = ImGui::ColorConvertFloat4ToU32(in);
        return _result;
    */

    public static void colorConvertRGBtoHSV(float[] rgb, float[] hsv){
        nColorConvertRGBtoHSV(rgb, hsv);
    }

    public static native void nColorConvertRGBtoHSV(float[] rgb, float[] hsv); /*
        ImGui::ColorConvertRGBtoHSV(rgb[0], rgb[1], rgb[2], hsv[0], hsv[1], hsv[2]);
    */

    public static void colorConvertHSVtoRGB(float[] hsv, float[] rgb) {
        nColorConvertHSVtoRGB(hsv, rgb);
    }

    public static native void nColorConvertHSVtoRGB(float[] hsv, float[] rgb); /*
        ImGui::ColorConvertHSVtoRGB(hsv[0], hsv[1], hsv[2], rgb[0], rgb[1], rgb[2]);
    */

    // Inputs Utilities: Keyboard
    // Without IMGUI_DISABLE_OBSOLETE_KEYIO: (legacy support)
    //   - For 'ImGuiKey key' you can still use your legacy native/user indices according to how your backend/engine stored them in io.KeysDown[].
    // With IMGUI_DISABLE_OBSOLETE_KEYIO: (this is the way forward)
    //   - Any use of 'ImGuiKey' will assert when key < 512 will be passed, previously reserved as native/user keys indices
    //   - GetKeyIndex() is pass-through and therefore deprecated (gone if IMGUI_DISABLE_OBSOLETE_KEYIO is defined)

    /**
     * Map ImGuiKey_* values into user's key index. == io.KeyMap[key]
     */
    @Deprecated
    public static int getKeyIndex(final int key) {
        return nGetKeyIndex(key);
    }

    private static native int nGetKeyIndex(int key); /*
        return ImGui::GetKeyIndex(static_cast<ImGuiKey>(key));
    */

    /**
     * Is key being held. == io.KeysDown[user_key_index].
     */
    public static boolean isKeyDown(final int key) {
        return nIsKeyDown(key);
    }

    private static native boolean nIsKeyDown(int key); /*
        return ImGui::IsKeyDown(static_cast<ImGuiKey>(key));
    */

    /**
     * Was key pressed (went from !Down to Down)? if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    public static boolean isKeyPressed(final int key) {
        return nIsKeyPressed(key);
    }

    /**
     * Was key pressed (went from !Down to Down)? if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    public static boolean isKeyPressed(final int key, final boolean repeat) {
        return nIsKeyPressed(key, repeat);
    }

    private static native boolean nIsKeyPressed(int key); /*
        return ImGui::IsKeyPressed(static_cast<ImGuiKey>(key));
    */

    private static native boolean nIsKeyPressed(int key, boolean repeat); /*
        return ImGui::IsKeyPressed(static_cast<ImGuiKey>(key), repeat);
    */

    /**
     * Was key released (went from Down to !Down)
     */
    public static boolean isKeyReleased(final int key) {
        return nIsKeyReleased(key);
    }

    private static native boolean nIsKeyReleased(int key); /*
        return ImGui::IsKeyReleased(static_cast<ImGuiKey>(key));
    */

    /**
     * Uses provided repeat rate/delay.
     * Return a count, most often 0 or 1 but might be {@code >1} if RepeatRate is small enough that {@code DeltaTime > RepeatRate}
     */
    public static boolean getKeyPressedAmount(final int key, final float repeatDelay, final float rate) {
        return nGetKeyPressedAmount(key, repeatDelay, rate);
    }

    private static native boolean nGetKeyPressedAmount(int key, float repeatDelay, float rate); /*
        return ImGui::GetKeyPressedAmount(static_cast<ImGuiKey>(key), repeatDelay, rate);
    */

    /**
     * [DEBUG] returns English name of the key. Those names a provided for debugging purpose and are not meant to be saved persistently not compared.
     */
    public static String getKeyName(final int key) {
        return nGetKeyName(key);
    }

    private static native String nGetKeyName(int key); /*
        return env->NewStringUTF(ImGui::GetKeyName(static_cast<ImGuiKey>(key)));
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureKeyboard flag next frame (said flag is entirely left for your application to handle).
     * e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = wantCaptureKeyboardValue"; after the next NewFrame() call.
     */
    public static void captureKeyboardFromApp() {
        nCaptureKeyboardFromApp();
    }

    /**
     * Attention: misleading name! manually override io.WantCaptureKeyboard flag next frame (said flag is entirely left for your application to handle).
     * e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = wantCaptureKeyboardValue"; after the next NewFrame() call.
     */
    public static void captureKeyboardFromApp(final boolean wantCaptureKeyboardValue) {
        nCaptureKeyboardFromApp(wantCaptureKeyboardValue);
    }

    private static native void nCaptureKeyboardFromApp(); /*
        ImGui::CaptureKeyboardFromApp();
    */

    private static native void nCaptureKeyboardFromApp(boolean wantCaptureKeyboardValue); /*
        ImGui::CaptureKeyboardFromApp(wantCaptureKeyboardValue);
    */

    // Inputs Utilities: Mouse
    // - To refer to a mouse button, you may use named enums in your code e.g. ImGuiMouseButton_Left, ImGuiMouseButton_Right.
    // - You can also use regular integer: it is forever guaranteed that 0=Left, 1=Right, 2=Middle.
    // - Dragging operations are only reported after mouse has moved a certain distance away from the initial clicking position (see 'lock_threshold' and 'io.MouseDraggingThreshold')

    /**
     * Is mouse button held (0=left, 1=right, 2=middle)
     */
    public static boolean isMouseDown(final int button) {
        return nIsMouseDown(button);
    }

    private static native boolean nIsMouseDown(int button); /*
        return ImGui::IsMouseDown(button);
    */

    /**
     * Did mouse button clicked (went from !Down to Down) (0=left, 1=right, 2=middle)
     */
    public static boolean isMouseClicked(final int button) {
        return nIsMouseClicked(button);
    }

    /**
     * Did mouse button clicked (went from !Down to Down) (0=left, 1=right, 2=middle)
     */
    public static boolean isMouseClicked(final int button, final boolean repeat) {
        return nIsMouseClicked(button, repeat);
    }

    private static native boolean nIsMouseClicked(int button); /*
        return ImGui::IsMouseClicked(button);
    */

    private static native boolean nIsMouseClicked(int button, boolean repeat); /*
        return ImGui::IsMouseClicked(button, repeat);
    */

    /**
     * Did mouse button released (went from Down to !Down)
     */
    public static boolean isMouseReleased(final int button) {
        return nIsMouseReleased(button);
    }

    private static native boolean nIsMouseReleased(int button); /*
        return ImGui::IsMouseReleased(button);
    */

    /**
     * did mouse button double-clicked? (note that a double-click will also report IsMouseClicked() == true).
     */
    public static boolean isMouseDoubleClicked(final int button) {
        return nIsMouseDoubleClicked(button);
    }

    private static native boolean nIsMouseDoubleClicked(int button); /*
        return ImGui::IsMouseDoubleClicked(button);
    */

    /**
     * Return the number of successive mouse-clicks at the time where a click happen (otherwise 0).
     */
    public static int getMouseClickedCount(final int button) {
        return nGetMouseClickedCount(button);
    }

    private static native int nGetMouseClickedCount(int button); /*
        return ImGui::GetMouseClickedCount(button);
    */

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static boolean isMouseHoveringRect(final ImVec2 rMin, final ImVec2 rMax) {
        return nIsMouseHoveringRect(rMin.x, rMin.y, rMax.x, rMax.y);
    }

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        return nIsMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY);
    }

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static boolean isMouseHoveringRect(final ImVec2 rMin, final ImVec2 rMax, final boolean clip) {
        return nIsMouseHoveringRect(rMin.x, rMin.y, rMax.x, rMax.y, clip);
    }

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY, final boolean clip) {
        return nIsMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY, clip);
    }

    private static native boolean nIsMouseHoveringRect(float rMinX, float rMinY, float rMaxX, float rMaxY); /*MANUAL
        ImVec2 rMin = ImVec2(rMinX, rMinY);
        ImVec2 rMax = ImVec2(rMaxX, rMaxY);
        auto _result = ImGui::IsMouseHoveringRect(rMin, rMax);
        return _result;
    */

    private static native boolean nIsMouseHoveringRect(float rMinX, float rMinY, float rMaxX, float rMaxY, boolean clip); /*MANUAL
        ImVec2 rMin = ImVec2(rMinX, rMinY);
        ImVec2 rMax = ImVec2(rMaxX, rMaxY);
        auto _result = ImGui::IsMouseHoveringRect(rMin, rMax, clip);
        return _result;
    */

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static boolean isMousePosValid() {
        return nIsMousePosValid();
    }

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static boolean isMousePosValid(final ImVec2 mousePos) {
        return nIsMousePosValid(mousePos.x, mousePos.y);
    }

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static boolean isMousePosValid(final float mousePosX, final float mousePosY) {
        return nIsMousePosValid(mousePosX, mousePosY);
    }

    private static native boolean nIsMousePosValid(); /*
        return ImGui::IsMousePosValid();
    */

    private static native boolean nIsMousePosValid(float mousePosX, float mousePosY); /*MANUAL
        ImVec2 mousePos = ImVec2(mousePosX, mousePosY);
        auto _result = ImGui::IsMousePosValid(&mousePos);
        return _result;
    */

    /**
     * Is any mouse button held
     */
    public static boolean isAnyMouseDown() {
        return nIsAnyMouseDown();
    }

    private static native boolean nIsAnyMouseDown(); /*
        return ImGui::IsAnyMouseDown();
    */

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static ImVec2 getMousePos() {
        final ImVec2 dst = new ImVec2();
        nGetMousePos(dst);
        return dst;
    }

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static float getMousePosX() {
        return nGetMousePosX();
    }

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static float getMousePosY() {
        return nGetMousePosY();
    }

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static void getMousePos(final ImVec2 dst) {
        nGetMousePos(dst);
    }

    private static native void nGetMousePos(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetMousePos(), dst);
    */

    private static native float nGetMousePosX(); /*
        return ImGui::GetMousePos().x;
    */

    private static native float nGetMousePosY(); /*
        return ImGui::GetMousePos().y;
    */

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static ImVec2 getMousePosOnOpeningCurrentPopup() {
        final ImVec2 dst = new ImVec2();
        nGetMousePosOnOpeningCurrentPopup(dst);
        return dst;
    }

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static float getMousePosOnOpeningCurrentPopupX() {
        return nGetMousePosOnOpeningCurrentPopupX();
    }

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static float getMousePosOnOpeningCurrentPopupY() {
        return nGetMousePosOnOpeningCurrentPopupY();
    }

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static void getMousePosOnOpeningCurrentPopup(final ImVec2 dst) {
        nGetMousePosOnOpeningCurrentPopup(dst);
    }

    private static native void nGetMousePosOnOpeningCurrentPopup(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetMousePosOnOpeningCurrentPopup(), dst);
    */

    private static native float nGetMousePosOnOpeningCurrentPopupX(); /*
        return ImGui::GetMousePosOnOpeningCurrentPopup().x;
    */

    private static native float nGetMousePosOnOpeningCurrentPopupY(); /*
        return ImGui::GetMousePosOnOpeningCurrentPopup().y;
    */

    /**
     * Is mouse dragging. if lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold
     */
    public static boolean isMouseDragging(final int button) {
        return nIsMouseDragging(button);
    }

    /**
     * Is mouse dragging. if lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold
     */
    public static boolean isMouseDragging(final int button, final float lockThreshold) {
        return nIsMouseDragging(button, lockThreshold);
    }

    private static native boolean nIsMouseDragging(int button); /*
        return ImGui::IsMouseDragging(button);
    */

    private static native boolean nIsMouseDragging(int button, float lockThreshold); /*
        return ImGui::IsMouseDragging(button, lockThreshold);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static ImVec2 getMouseDragDelta() {
        final ImVec2 dst = new ImVec2();
        nGetMouseDragDelta(dst);
        return dst;
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static float getMouseDragDeltaX() {
        return nGetMouseDragDeltaX();
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static float getMouseDragDeltaY() {
        return nGetMouseDragDeltaY();
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static void getMouseDragDelta(final ImVec2 dst) {
        nGetMouseDragDelta(dst);
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static ImVec2 getMouseDragDelta(final int button) {
        final ImVec2 dst = new ImVec2();
        nGetMouseDragDelta(dst, button);
        return dst;
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static float getMouseDragDeltaX(final int button) {
        return nGetMouseDragDeltaX(button);
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static float getMouseDragDeltaY(final int button) {
        return nGetMouseDragDeltaY(button);
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static void getMouseDragDelta(final ImVec2 dst, final int button) {
        nGetMouseDragDelta(dst, button);
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static ImVec2 getMouseDragDelta(final int button, final float lockThreshold) {
        final ImVec2 dst = new ImVec2();
        nGetMouseDragDelta(dst, button, lockThreshold);
        return dst;
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static float getMouseDragDeltaX(final int button, final float lockThreshold) {
        return nGetMouseDragDeltaX(button, lockThreshold);
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static float getMouseDragDeltaY(final int button, final float lockThreshold) {
        return nGetMouseDragDeltaY(button, lockThreshold);
    }

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lockThreshold {@code < -1.0f} uses io.MouseDraggingThreshold.
     */
    public static void getMouseDragDelta(final ImVec2 dst, final int button, final float lockThreshold) {
        nGetMouseDragDelta(dst, button, lockThreshold);
    }

    private static native void nGetMouseDragDelta(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(), dst);
    */

    private static native float nGetMouseDragDeltaX(); /*
        return ImGui::GetMouseDragDelta().x;
    */

    private static native float nGetMouseDragDeltaY(); /*
        return ImGui::GetMouseDragDelta().y;
    */

    private static native void nGetMouseDragDelta(ImVec2 dst, int button); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(button), dst);
    */

    private static native float nGetMouseDragDeltaX(int button); /*
        return ImGui::GetMouseDragDelta(button).x;
    */

    private static native float nGetMouseDragDeltaY(int button); /*
        return ImGui::GetMouseDragDelta(button).y;
    */

    private static native void nGetMouseDragDelta(ImVec2 dst, int button, float lockThreshold); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(button, lockThreshold), dst);
    */

    private static native float nGetMouseDragDeltaX(int button, float lockThreshold); /*
        return ImGui::GetMouseDragDelta(button, lockThreshold).x;
    */

    private static native float nGetMouseDragDeltaY(int button, float lockThreshold); /*
        return ImGui::GetMouseDragDelta(button, lockThreshold).y;
    */

    public static void resetMouseDragDelta() {
        nResetMouseDragDelta();
    }

    public static void resetMouseDragDelta(final int button) {
        nResetMouseDragDelta(button);
    }

    private static native void nResetMouseDragDelta(); /*
        ImGui::ResetMouseDragDelta();
    */

    private static native void nResetMouseDragDelta(int button); /*
        ImGui::ResetMouseDragDelta(button);
    */

    /**
     * get desired cursor type, reset in ImGui::NewFrame(), this is updated during the frame. valid before Render().
     * If you use software rendering by setting io.MouseDrawCursor ImGui will render those for you
     */
    public static int getMouseCursor() {
        return nGetMouseCursor();
    }

    private static native int nGetMouseCursor(); /*
        return ImGui::GetMouseCursor();
    */

    /**
     * Set desired cursor type
     */
    public static void setMouseCursor(final int type) {
        nSetMouseCursor(type);
    }

    private static native void nSetMouseCursor(int type); /*
        ImGui::SetMouseCursor(type);
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureMouse flag next frame (said flag is entirely left for your application to handle).
     * This is equivalent to setting "io.WantCaptureMouse = wantCaptureMouseValue;" after the next NewFrame() call.
     */
    public static void captureMouseFromApp() {
        nCaptureMouseFromApp();
    }

    /**
     * Attention: misleading name! manually override io.WantCaptureMouse flag next frame (said flag is entirely left for your application to handle).
     * This is equivalent to setting "io.WantCaptureMouse = wantCaptureMouseValue;" after the next NewFrame() call.
     */
    public static void captureMouseFromApp(final boolean wantCaptureMouseValue) {
        nCaptureMouseFromApp(wantCaptureMouseValue);
    }

    private static native void nCaptureMouseFromApp(); /*
        ImGui::CaptureMouseFromApp();
    */

    private static native void nCaptureMouseFromApp(boolean wantCaptureMouseValue); /*
        ImGui::CaptureMouseFromApp(wantCaptureMouseValue);
    */

    // Clipboard Utilities
    // - Also see the LogToClipboard() function to capture GUI into clipboard, or easily output text data to the clipboard.

    public static String getClipboardText() {
        return nGetClipboardText();
    }

    private static native String nGetClipboardText(); /*
        return env->NewStringUTF(ImGui::GetClipboardText());
    */

    public static void setClipboardText(final String text) {
        nSetClipboardText(text);
    }

    private static native void nSetClipboardText(String text); /*MANUAL
        auto text = obj_text == NULL ? NULL : (char*)env->GetStringUTFChars(obj_text, JNI_FALSE);
        ImGui::SetClipboardText(text);
        if (text != NULL) env->ReleaseStringUTFChars(obj_text, text);
    */

    // Settings/.Ini Utilities
    // - The disk functions are automatically called if io.IniFilename != NULL (default is "imgui.ini").
    // - Set io.IniFilename to NULL to load/save manually. Read io.WantSaveIniSettings description about handling .ini saving manually.

    /**
     * Call after CreateContext() and before the first call to NewFrame(). NewFrame() automatically calls LoadIniSettingsFromDisk(io.IniFilename).
     */
    public static void loadIniSettingsFromDisk(final String iniFilename) {
        nLoadIniSettingsFromDisk(iniFilename);
    }

    private static native void nLoadIniSettingsFromDisk(String iniFilename); /*MANUAL
        auto iniFilename = obj_iniFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniFilename, JNI_FALSE);
        ImGui::LoadIniSettingsFromDisk(iniFilename);
        if (iniFilename != NULL) env->ReleaseStringUTFChars(obj_iniFilename, iniFilename);
    */

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    public static void loadIniSettingsFromMemory(final String iniData) {
        nLoadIniSettingsFromMemory(iniData);
    }

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    public static void loadIniSettingsFromMemory(final String iniData, final int iniSize) {
        nLoadIniSettingsFromMemory(iniData, iniSize);
    }

    private static native void nLoadIniSettingsFromMemory(String iniData); /*MANUAL
        auto iniData = obj_iniData == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniData, JNI_FALSE);
        ImGui::LoadIniSettingsFromMemory(iniData);
        if (iniData != NULL) env->ReleaseStringUTFChars(obj_iniData, iniData);
    */

    private static native void nLoadIniSettingsFromMemory(String iniData, int iniSize); /*MANUAL
        auto iniData = obj_iniData == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniData, JNI_FALSE);
        ImGui::LoadIniSettingsFromMemory(iniData, iniSize);
        if (iniData != NULL) env->ReleaseStringUTFChars(obj_iniData, iniData);
    */

    /**
     * This is automatically called (if io.IniFilename is not empty) a few seconds after any modification that should be reflected in the .ini file (and also by DestroyContext).
     */
    public static void saveIniSettingsToDisk(final String iniFilename) {
        nSaveIniSettingsToDisk(iniFilename);
    }

    private static native void nSaveIniSettingsToDisk(String iniFilename); /*MANUAL
        auto iniFilename = obj_iniFilename == NULL ? NULL : (char*)env->GetStringUTFChars(obj_iniFilename, JNI_FALSE);
        ImGui::SaveIniSettingsToDisk(iniFilename);
        if (iniFilename != NULL) env->ReleaseStringUTFChars(obj_iniFilename, iniFilename);
    */

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    public static String saveIniSettingsToMemory() {
        return nSaveIniSettingsToMemory();
    }

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    public static String saveIniSettingsToMemory(final long outIniSize) {
        return nSaveIniSettingsToMemory(outIniSize);
    }

    private static native String nSaveIniSettingsToMemory(); /*
        return env->NewStringUTF(ImGui::SaveIniSettingsToMemory());
    */

    private static native String nSaveIniSettingsToMemory(long outIniSize); /*
        return env->NewStringUTF(ImGui::SaveIniSettingsToMemory((size_t*)&outIniSize));
    */

    // Debug Utilities
    // - This is used by the IMGUI_CHECKVERSION() macro.

    public static boolean debugCheckVersionAndDataLayout(final String versionStr, final int szIo, final int szStyle, final int szVec2, final int szVec4, final int szDrawVert, final int szDrawIdx) {
        return nDebugCheckVersionAndDataLayout(versionStr, szIo, szStyle, szVec2, szVec4, szDrawVert, szDrawIdx);
    }

    private static native boolean nDebugCheckVersionAndDataLayout(String obj_versionStr, int szIo, int szStyle, int szVec2, int szVec4, int szDrawVert, int szDrawIdx); /*MANUAL
        auto versionStr = obj_versionStr == NULL ? NULL : (char*)env->GetStringUTFChars(obj_versionStr, JNI_FALSE);
        auto _result = ImGui::DebugCheckVersionAndDataLayout(versionStr, szIo, szStyle, szVec2, szVec4, szDrawVert, szDrawIdx);
        if (versionStr != NULL) env->ReleaseStringUTFChars(obj_versionStr, versionStr);
        return _result;
    */

    // (Optional) Platform/OS interface for multi-viewport support
    // Read comments around the ImGuiPlatformIO structure for more details.
    // Note: You may use GetWindowViewport() to get the current viewport of the current window.

    private static final ImGuiPlatformIO _GETPLATFORMIO_1 = new ImGuiPlatformIO(0);

    /**
     * Platform/renderer functions, for backend to setup + viewports list.
     */
    public static ImGuiPlatformIO getPlatformIO() {
        _GETPLATFORMIO_1.ptr = nGetPlatformIO();
        return _GETPLATFORMIO_1;
    }

    private static native long nGetPlatformIO(); /*
        return (uintptr_t)&ImGui::GetPlatformIO();
    */

    /**
     * Call in main loop. Will call CreateWindow/ResizeWindow/etc. Platform functions for each secondary viewport, and DestroyWindow for each inactive viewport.
     */
    public static void updatePlatformWindows() {
        nUpdatePlatformWindows();
    }

    private static native void nUpdatePlatformWindows(); /*
        ImGui::UpdatePlatformWindows();
    */

    /**
     * Call in main loop. will call RenderWindow/SwapBuffers platform functions for each secondary viewport which doesn't have the ImGuiViewportFlags_Minimized flag set.
     * May be reimplemented by user for custom rendering needs.
     */
    public static void renderPlatformWindowsDefault() {
        nRenderPlatformWindowsDefault();
    }

    private static native void nRenderPlatformWindowsDefault(); /*
        ImGui::RenderPlatformWindowsDefault();
    */

    /**
     * Call DestroyWindow platform functions for all viewports.
     * Call from backend Shutdown() if you need to close platform windows before imgui shutdown.
     * Otherwise will be called by DestroyContext().
     */
    public static void destroyPlatformWindows() {
        nDestroyPlatformWindows();
    }

    private static native void nDestroyPlatformWindows(); /*
        ImGui::DestroyPlatformWindows();
    */

    /**
     * This is a helper for backends.
     */
    public static ImGuiViewport findViewportByID(final int imGuiID) {
        return new ImGuiViewport(nFindViewportByID(imGuiID));
    }

    private static native long nFindViewportByID(int imGuiID); /*
        return (uintptr_t)ImGui::FindViewportByID(imGuiID);
    */

    /**
     * This is a helper for backends. The type platform_handle is decided by the backend (e.g. HWND, MyWindow*, GLFWwindow* etc.)
     */
    public static ImGuiViewport findViewportByPlatformHandle(final long platformHandle) {
        return new ImGuiViewport(nFindViewportByPlatformHandle(platformHandle));
    }

    private static native long nFindViewportByPlatformHandle(long platformHandle); /*
        return (uintptr_t)ImGui::FindViewportByPlatformHandle((void*)platformHandle);
    */
}
