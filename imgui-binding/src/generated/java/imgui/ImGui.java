package imgui;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Properties;

public class ImGui {
    private static final String LIB_PATH_PROP = "imgui.library.path";
    private static final String LIB_NAME_PROP = "imgui.library.name";
    private static final String LIB_NAME_DEFAULT = System.getProperty("os.arch").contains("64") ? "imgui-java64" : "imgui-java";
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

        setAssertCallback(new imgui.assertion.ImAssertCallback() {
            @Override
            public void imAssertCallback(final String assertion, final int line, final String file) {
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
    public static native void setAssertCallback(imgui.assertion.ImAssertCallback callback); /*
        Jni::SetAssertionCallback(callback);
    */

    // GENERATED API: BEGIN
    /**
     * Field with generated {@link imgui.binding.ImGuiApi} to use Dear ImGui API.
     * Can be replaced with custom API implementation if required.
     */
    public static imgui.binding.ImGuiApi api = new imgui.binding.ImGuiApi();

    public static imgui.internal.ImGuiContext createContext() {
        return api.createContext();
    }

    public static imgui.internal.ImGuiContext createContext(final imgui.ImFontAtlas sharedFontAtlas) {
        return api.createContext(sharedFontAtlas);
    }

    public static void destroyContext() {
        api.destroyContext();
    }

    public static void destroyContext(final imgui.internal.ImGuiContext ctx) {
        api.destroyContext(ctx);
    }

    public static imgui.internal.ImGuiContext getCurrentContext() {
        return api.getCurrentContext();
    }

    public static void setCurrentContext(final imgui.internal.ImGuiContext ctx) {
        api.setCurrentContext(ctx);
    }

    public static imgui.ImGuiIO getIO() {
        return api.getIO();
    }

    public static imgui.ImGuiStyle getStyle() {
        return api.getStyle();
    }

    public static void newFrame() {
        api.newFrame();
    }

    public static void endFrame() {
        api.endFrame();
    }

    public static void render() {
        api.render();
    }

    public static imgui.ImDrawData getDrawData() {
        return api.getDrawData();
    }

    public static void showDemoWindow() {
        api.showDemoWindow();
    }

    public static void showDemoWindow(final imgui.type.ImBoolean pOpen) {
        api.showDemoWindow(pOpen);
    }

    public static void showMetricsWindow() {
        api.showMetricsWindow();
    }

    public static void showMetricsWindow(final imgui.type.ImBoolean pOpen) {
        api.showMetricsWindow(pOpen);
    }

    public static void showStackToolWindow() {
        api.showStackToolWindow();
    }

    public static void showStackToolWindow(final imgui.type.ImBoolean pOpen) {
        api.showStackToolWindow(pOpen);
    }

    public static void showAboutWindow() {
        api.showAboutWindow();
    }

    public static void showAboutWindow(final imgui.type.ImBoolean pOpen) {
        api.showAboutWindow(pOpen);
    }

    public static void showStyleEditor() {
        api.showStyleEditor();
    }

    public static void showStyleEditor(final imgui.ImGuiStyle ref) {
        api.showStyleEditor(ref);
    }

    public static boolean showStyleSelector(final String label) {
        return api.showStyleSelector(label);
    }

    public static void showFontSelector(final String label) {
        api.showFontSelector(label);
    }

    public static void showUserGuide() {
        api.showUserGuide();
    }

    public static String getVersion() {
        return api.getVersion();
    }

    public static void styleColorsDark() {
        api.styleColorsDark();
    }

    public static void styleColorsDark(final imgui.ImGuiStyle dst) {
        api.styleColorsDark(dst);
    }

    public static void styleColorsLight() {
        api.styleColorsLight();
    }

    public static void styleColorsLight(final imgui.ImGuiStyle dst) {
        api.styleColorsLight(dst);
    }

    public static void styleColorsClassic() {
        api.styleColorsClassic();
    }

    public static void styleColorsClassic(final imgui.ImGuiStyle dst) {
        api.styleColorsClassic(dst);
    }

    public static boolean begin(final String name) {
        return api.begin(name);
    }

    public static boolean begin(final String name, final imgui.type.ImBoolean pOpen) {
        return api.begin(name, pOpen);
    }

    public static boolean begin(final String name, final int flags) {
        return api.begin(name, flags);
    }

    public static boolean begin(final String name, final imgui.type.ImBoolean pOpen, final int flags) {
        return api.begin(name, pOpen, flags);
    }

    public static void end() {
        api.end();
    }

    public static boolean beginChild(final String strId) {
        return api.beginChild(strId);
    }

    public static boolean beginChild(final String strId, final imgui.ImVec2 size) {
        return api.beginChild(strId, size);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY) {
        return api.beginChild(strId, sizeX, sizeY);
    }

    public static boolean beginChild(final String strId, final boolean border) {
        return api.beginChild(strId, border);
    }

    public static boolean beginChild(final String strId, final imgui.ImVec2 size, final boolean border) {
        return api.beginChild(strId, size, border);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border) {
        return api.beginChild(strId, sizeX, sizeY, border);
    }

    public static boolean beginChild(final String strId, final boolean border, final int flags) {
        return api.beginChild(strId, border, flags);
    }

    public static boolean beginChild(final String strId, final imgui.ImVec2 size, final int flags) {
        return api.beginChild(strId, size, flags);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY, final int flags) {
        return api.beginChild(strId, sizeX, sizeY, flags);
    }

    public static boolean beginChild(final String strId, final imgui.ImVec2 size, final boolean border, final int flags) {
        return api.beginChild(strId, size, border, flags);
    }

    public static boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border, final int flags) {
        return api.beginChild(strId, sizeX, sizeY, border, flags);
    }

    public static boolean beginChild(final int id) {
        return api.beginChild(id);
    }

    public static boolean beginChild(final int id, final imgui.ImVec2 size) {
        return api.beginChild(id, size);
    }

    public static boolean beginChild(final int id, final float sizeX, final float sizeY) {
        return api.beginChild(id, sizeX, sizeY);
    }

    public static boolean beginChild(final int id, final boolean border) {
        return api.beginChild(id, border);
    }

    public static boolean beginChild(final int id, final imgui.ImVec2 size, final boolean border) {
        return api.beginChild(id, size, border);
    }

    public static boolean beginChild(final int id, final float sizeX, final float sizeY, final boolean border) {
        return api.beginChild(id, sizeX, sizeY, border);
    }

    public static boolean beginChild(final int id, final boolean border, final int flags) {
        return api.beginChild(id, border, flags);
    }

    public static boolean beginChild(final int id, final imgui.ImVec2 size, final int flags) {
        return api.beginChild(id, size, flags);
    }

    public static boolean beginChild(final int id, final float sizeX, final float sizeY, final int flags) {
        return api.beginChild(id, sizeX, sizeY, flags);
    }

    public static boolean beginChild(final int id, final imgui.ImVec2 size, final boolean border, final int flags) {
        return api.beginChild(id, size, border, flags);
    }

    public static boolean beginChild(final int id, final float sizeX, final float sizeY, final boolean border, final int flags) {
        return api.beginChild(id, sizeX, sizeY, border, flags);
    }

    public static void endChild() {
        api.endChild();
    }

    public static boolean isWindowAppearing() {
        return api.isWindowAppearing();
    }

    public static boolean isWindowCollapsed() {
        return api.isWindowCollapsed();
    }

    public static boolean isWindowFocused() {
        return api.isWindowFocused();
    }

    public static boolean isWindowFocused(final int flags) {
        return api.isWindowFocused(flags);
    }

    public static boolean isWindowHovered() {
        return api.isWindowHovered();
    }

    public static boolean isWindowHovered(final int flags) {
        return api.isWindowHovered(flags);
    }

    public static imgui.ImDrawList getWindowDrawList() {
        return api.getWindowDrawList();
    }

    public static float getWindowDpiScale() {
        return api.getWindowDpiScale();
    }

    public static imgui.ImVec2 getWindowPos() {
        return api.getWindowPos();
    }

    public static void getWindowPos(final imgui.ImVec2 dst) {
        api.getWindowPos(dst);
    }

    public static imgui.ImVec2 getWindowSize() {
        return api.getWindowSize();
    }

    public static void getWindowSize(final imgui.ImVec2 dst) {
        api.getWindowSize(dst);
    }

    public static float getWindowWidth() {
        return api.getWindowWidth();
    }

    public static float getWindowHeight() {
        return api.getWindowHeight();
    }

    public static imgui.ImGuiViewport getWindowViewport() {
        return api.getWindowViewport();
    }

    public static void setNextWindowPos(final imgui.ImVec2 pos) {
        api.setNextWindowPos(pos);
    }

    public static void setNextWindowPos(final float posX, final float posY) {
        api.setNextWindowPos(posX, posY);
    }

    public static void setNextWindowPos(final imgui.ImVec2 pos, final int cond) {
        api.setNextWindowPos(pos, cond);
    }

    public static void setNextWindowPos(final float posX, final float posY, final int cond) {
        api.setNextWindowPos(posX, posY, cond);
    }

    public static void setNextWindowPos(final imgui.ImVec2 pos, final imgui.ImVec2 pivot) {
        api.setNextWindowPos(pos, pivot);
    }

    public static void setNextWindowPos(final float posX, final float posY, final float pivotX, final float pivotY) {
        api.setNextWindowPos(posX, posY, pivotX, pivotY);
    }

    public static void setNextWindowPos(final imgui.ImVec2 pos, final int cond, final imgui.ImVec2 pivot) {
        api.setNextWindowPos(pos, cond, pivot);
    }

    public static void setNextWindowPos(final float posX, final float posY, final int cond, final float pivotX, final float pivotY) {
        api.setNextWindowPos(posX, posY, cond, pivotX, pivotY);
    }

    public static void setNextWindowSize(final imgui.ImVec2 size) {
        api.setNextWindowSize(size);
    }

    public static void setNextWindowSize(final float sizeX, final float sizeY) {
        api.setNextWindowSize(sizeX, sizeY);
    }

    public static void setNextWindowSize(final imgui.ImVec2 size, final int cond) {
        api.setNextWindowSize(size, cond);
    }

    public static void setNextWindowSize(final float sizeX, final float sizeY, final int cond) {
        api.setNextWindowSize(sizeX, sizeY, cond);
    }

    public static void setNextWindowSizeConstraints(final imgui.ImVec2 sizeMin, final imgui.ImVec2 sizeMax) {
        api.setNextWindowSizeConstraints(sizeMin, sizeMax);
    }

    public static void setNextWindowSizeConstraints(final float sizeMinX, final float sizeMinY, final float sizeMaxX, final float sizeMaxY) {
        api.setNextWindowSizeConstraints(sizeMinX, sizeMinY, sizeMaxX, sizeMaxY);
    }

    public static void setNextWindowContentSize(final imgui.ImVec2 size) {
        api.setNextWindowContentSize(size);
    }

    public static void setNextWindowContentSize(final float sizeX, final float sizeY) {
        api.setNextWindowContentSize(sizeX, sizeY);
    }

    public static void setNextWindowCollapsed(final boolean collapsed) {
        api.setNextWindowCollapsed(collapsed);
    }

    public static void setNextWindowCollapsed(final boolean collapsed, final int cond) {
        api.setNextWindowCollapsed(collapsed, cond);
    }

    public static void setNextWindowFocus() {
        api.setNextWindowFocus();
    }

    public static void setNextWindowBgAlpha(final float alpha) {
        api.setNextWindowBgAlpha(alpha);
    }

    public static void setNextWindowViewport(final int viewportId) {
        api.setNextWindowViewport(viewportId);
    }

    public static void setWindowFontScale(final float scale) {
        api.setWindowFontScale(scale);
    }

    public static void setWindowPos(final imgui.ImVec2 pos) {
        api.setWindowPos(pos);
    }

    public static void setWindowPos(final float posX, final float posY) {
        api.setWindowPos(posX, posY);
    }

    public static void setWindowPos(final imgui.ImVec2 pos, final int cond) {
        api.setWindowPos(pos, cond);
    }

    public static void setWindowPos(final float posX, final float posY, final int cond) {
        api.setWindowPos(posX, posY, cond);
    }

    public static void setWindowPos(final String name, final imgui.ImVec2 pos) {
        api.setWindowPos(name, pos);
    }

    public static void setWindowPos(final String name, final float posX, final float posY) {
        api.setWindowPos(name, posX, posY);
    }

    public static void setWindowPos(final String name, final imgui.ImVec2 pos, final int cond) {
        api.setWindowPos(name, pos, cond);
    }

    public static void setWindowPos(final String name, final float posX, final float posY, final int cond) {
        api.setWindowPos(name, posX, posY, cond);
    }

    public static void setWindowSize(final imgui.ImVec2 size) {
        api.setWindowSize(size);
    }

    public static void setWindowSize(final float sizeX, final float sizeY) {
        api.setWindowSize(sizeX, sizeY);
    }

    public static void setWindowSize(final imgui.ImVec2 size, final int cond) {
        api.setWindowSize(size, cond);
    }

    public static void setWindowSize(final float sizeX, final float sizeY, final int cond) {
        api.setWindowSize(sizeX, sizeY, cond);
    }

    public static void setWindowSize(final String name, final imgui.ImVec2 size) {
        api.setWindowSize(name, size);
    }

    public static void setWindowSize(final String name, final float sizeX, final float sizeY) {
        api.setWindowSize(name, sizeX, sizeY);
    }

    public static void setWindowSize(final String name, final imgui.ImVec2 size, final int cond) {
        api.setWindowSize(name, size, cond);
    }

    public static void setWindowSize(final String name, final float sizeX, final float sizeY, final int cond) {
        api.setWindowSize(name, sizeX, sizeY, cond);
    }

    public static void setWindowCollapsed(final boolean collapsed) {
        api.setWindowCollapsed(collapsed);
    }

    public static void setWindowCollapsed(final boolean collapsed, final int cond) {
        api.setWindowCollapsed(collapsed, cond);
    }

    public static void setWindowCollapsed(final String name, final boolean collapsed) {
        api.setWindowCollapsed(name, collapsed);
    }

    public static void setWindowCollapsed(final String name, final boolean collapsed, final int cond) {
        api.setWindowCollapsed(name, collapsed, cond);
    }

    public static void setWindowFocus() {
        api.setWindowFocus();
    }

    public static void setWindowFocus(final String name) {
        api.setWindowFocus(name);
    }

    public static imgui.ImVec2 getContentRegionAvail() {
        return api.getContentRegionAvail();
    }

    public static void getContentRegionAvail(final imgui.ImVec2 dst) {
        api.getContentRegionAvail(dst);
    }

    public static imgui.ImVec2 getContentRegionMax() {
        return api.getContentRegionMax();
    }

    public static void getContentRegionMax(final imgui.ImVec2 dst) {
        api.getContentRegionMax(dst);
    }

    public static imgui.ImVec2 getWindowContentRegionMin() {
        return api.getWindowContentRegionMin();
    }

    public static void getWindowContentRegionMin(final imgui.ImVec2 dst) {
        api.getWindowContentRegionMin(dst);
    }

    public static imgui.ImVec2 getWindowContentRegionMax() {
        return api.getWindowContentRegionMax();
    }

    public static void getWindowContentRegionMax(final imgui.ImVec2 dst) {
        api.getWindowContentRegionMax(dst);
    }

    public static float getScrollX() {
        return api.getScrollX();
    }

    public static float getScrollY() {
        return api.getScrollY();
    }

    public static void setScrollX(final float scrollX) {
        api.setScrollX(scrollX);
    }

    public static void setScrollY(final float scrollY) {
        api.setScrollY(scrollY);
    }

    public static float getScrollMaxX() {
        return api.getScrollMaxX();
    }

    public static float getScrollMaxY() {
        return api.getScrollMaxY();
    }

    public static void setScrollHereX() {
        api.setScrollHereX();
    }

    public static void setScrollHereX(final float centerXRatio) {
        api.setScrollHereX(centerXRatio);
    }

    public static void setScrollHereY() {
        api.setScrollHereY();
    }

    public static void setScrollHereY(final float centerYRatio) {
        api.setScrollHereY(centerYRatio);
    }

    public static void setScrollFromPosX(final float localX) {
        api.setScrollFromPosX(localX);
    }

    public static void setScrollFromPosX(final float localX, final float centerXRatio) {
        api.setScrollFromPosX(localX, centerXRatio);
    }

    public static void setScrollFromPosY(final float localY) {
        api.setScrollFromPosY(localY);
    }

    public static void setScrollFromPosY(final float localY, final float centerYRatio) {
        api.setScrollFromPosY(localY, centerYRatio);
    }

    public static void pushFont(final imgui.ImFont font) {
        api.pushFont(font);
    }

    public static void popFont() {
        api.popFont();
    }

    public static void pushStyleColor(final int idx, final int col) {
        api.pushStyleColor(idx, col);
    }

    public static void pushStyleColor(final int idx, final imgui.ImVec4 col) {
        api.pushStyleColor(idx, col);
    }

    public static void pushStyleColor(final int idx, final float colX, final float colY, final float colZ, final float colW) {
        api.pushStyleColor(idx, colX, colY, colZ, colW);
    }

    public static void popStyleColor() {
        api.popStyleColor();
    }

    public static void popStyleColor(final int count) {
        api.popStyleColor(count);
    }

    public static void pushStyleVar(final int idx, final float val) {
        api.pushStyleVar(idx, val);
    }

    public static void pushStyleVar(final int idx, final imgui.ImVec2 val) {
        api.pushStyleVar(idx, val);
    }

    public static void pushStyleVar(final int idx, final float valX, final float valY) {
        api.pushStyleVar(idx, valX, valY);
    }

    public static void popStyleVar() {
        api.popStyleVar();
    }

    public static void popStyleVar(final int count) {
        api.popStyleVar(count);
    }

    public static void pushAllowKeyboardFocus(final boolean allowKeyboardFocus) {
        api.pushAllowKeyboardFocus(allowKeyboardFocus);
    }

    public static void popAllowKeyboardFocus() {
        api.popAllowKeyboardFocus();
    }

    public static void pushButtonRepeat(final boolean repeat) {
        api.pushButtonRepeat(repeat);
    }

    public static void popButtonRepeat() {
        api.popButtonRepeat();
    }

    public static void pushItemWidth(final float itemWidth) {
        api.pushItemWidth(itemWidth);
    }

    public static void popItemWidth() {
        api.popItemWidth();
    }

    public static void setNextItemWidth(final float itemWidth) {
        api.setNextItemWidth(itemWidth);
    }

    public static float calcItemWidth() {
        return api.calcItemWidth();
    }

    public static void pushTextWrapPos() {
        api.pushTextWrapPos();
    }

    public static void pushTextWrapPos(final float wrapLocalPosX) {
        api.pushTextWrapPos(wrapLocalPosX);
    }

    public static void popTextWrapPos() {
        api.popTextWrapPos();
    }

    public static imgui.ImFont getFont() {
        return api.getFont();
    }

    public static float getFontSize() {
        return api.getFontSize();
    }

    public static imgui.ImVec2 getFontTexUvWhitePixel() {
        return api.getFontTexUvWhitePixel();
    }

    public static void getFontTexUvWhitePixel(final imgui.ImVec2 dst) {
        api.getFontTexUvWhitePixel(dst);
    }

    public static int getColorU32(final int idx) {
        return api.getColorU32(idx);
    }

    public static int getColorU32(final int idx, final float alphaMul) {
        return api.getColorU32(idx, alphaMul);
    }

    public static int getColorU32(final imgui.ImVec4 col) {
        return api.getColorU32(col);
    }

    public static int getColorU32(final float colX, final float colY, final float colZ, final float colW) {
        return api.getColorU32(colX, colY, colZ, colW);
    }

    public static imgui.ImVec4 getStyleColorVec4(final int idx) {
        return api.getStyleColorVec4(idx);
    }

    public static void getStyleColorVec4(final imgui.ImVec4 dst, final int idx) {
        api.getStyleColorVec4(dst, idx);
    }

    public static void separator() {
        api.separator();
    }

    public static void sameLine() {
        api.sameLine();
    }

    public static void sameLine(final float offsetFromStartX) {
        api.sameLine(offsetFromStartX);
    }

    public static void sameLine(final float offsetFromStartX, final float spacing) {
        api.sameLine(offsetFromStartX, spacing);
    }

    public static void newLine() {
        api.newLine();
    }

    public static void spacing() {
        api.spacing();
    }

    public static void dummy(final imgui.ImVec2 size) {
        api.dummy(size);
    }

    public static void dummy(final float sizeX, final float sizeY) {
        api.dummy(sizeX, sizeY);
    }

    public static void indent() {
        api.indent();
    }

    public static void indent(final float indentW) {
        api.indent(indentW);
    }

    public static void unindent() {
        api.unindent();
    }

    public static void unindent(final float indentW) {
        api.unindent(indentW);
    }

    public static void beginGroup() {
        api.beginGroup();
    }

    public static void endGroup() {
        api.endGroup();
    }

    public static imgui.ImVec2 getCursorPos() {
        return api.getCursorPos();
    }

    public static void getCursorPos(final imgui.ImVec2 dst) {
        api.getCursorPos(dst);
    }

    public static float getCursorPosX() {
        return api.getCursorPosX();
    }

    public static float getCursorPosY() {
        return api.getCursorPosY();
    }

    public static void setCursorPos(final imgui.ImVec2 localPos) {
        api.setCursorPos(localPos);
    }

    public static void setCursorPos(final float localPosX, final float localPosY) {
        api.setCursorPos(localPosX, localPosY);
    }

    public static void setCursorPosX(final float localX) {
        api.setCursorPosX(localX);
    }

    public static void setCursorPosY(final float localY) {
        api.setCursorPosY(localY);
    }

    public static imgui.ImVec2 getCursorStartPos() {
        return api.getCursorStartPos();
    }

    public static void getCursorStartPos(final imgui.ImVec2 dst) {
        api.getCursorStartPos(dst);
    }

    public static imgui.ImVec2 getCursorScreenPos() {
        return api.getCursorScreenPos();
    }

    public static void getCursorScreenPos(final imgui.ImVec2 dst) {
        api.getCursorScreenPos(dst);
    }

    public static void setCursorScreenPos(final imgui.ImVec2 pos) {
        api.setCursorScreenPos(pos);
    }

    public static void setCursorScreenPos(final float posX, final float posY) {
        api.setCursorScreenPos(posX, posY);
    }

    public static void alignTextToFramePadding() {
        api.alignTextToFramePadding();
    }

    public static float getTextLineHeight() {
        return api.getTextLineHeight();
    }

    public static float getTextLineHeightWithSpacing() {
        return api.getTextLineHeightWithSpacing();
    }

    public static float getFrameHeight() {
        return api.getFrameHeight();
    }

    public static float getFrameHeightWithSpacing() {
        return api.getFrameHeightWithSpacing();
    }

    public static void pushID(final String strId) {
        api.pushID(strId);
    }

    public static void pushID(final String strIdBegin, final String strIdEnd) {
        api.pushID(strIdBegin, strIdEnd);
    }

    public static void pushID(final int intId) {
        api.pushID(intId);
    }

    public static void popID() {
        api.popID();
    }

    public static int getID(final String strId) {
        return api.getID(strId);
    }

    public static int getID(final String strIdBegin, final String strIdEnd) {
        return api.getID(strIdBegin, strIdEnd);
    }

    public static void textUnformatted(final String text) {
        api.textUnformatted(text);
    }

    public static void textUnformatted(final String text, final String textEnd) {
        api.textUnformatted(text, textEnd);
    }

    public static void text(final String fmt) {
        api.text(fmt);
    }

    public static void textColored(final imgui.ImVec4 col, final String fmt) {
        api.textColored(col, fmt);
    }

    public static void textColored(final float colX, final float colY, final float colZ, final float colW, final String fmt) {
        api.textColored(colX, colY, colZ, colW, fmt);
    }

    public static void textDisabled(final String fmt) {
        api.textDisabled(fmt);
    }

    public static void textWrapped(final String fmt) {
        api.textWrapped(fmt);
    }

    public static void labelText(final String label, final String fmt) {
        api.labelText(label, fmt);
    }

    public static void bulletText(final String fmt) {
        api.bulletText(fmt);
    }

    public static boolean button(final String label) {
        return api.button(label);
    }

    public static boolean button(final String label, final imgui.ImVec2 size) {
        return api.button(label, size);
    }

    public static boolean button(final String label, final float sizeX, final float sizeY) {
        return api.button(label, sizeX, sizeY);
    }

    public static boolean smallButton(final String label) {
        return api.smallButton(label);
    }

    public static boolean invisibleButton(final String strId, final imgui.ImVec2 size) {
        return api.invisibleButton(strId, size);
    }

    public static boolean invisibleButton(final String strId, final float sizeX, final float sizeY) {
        return api.invisibleButton(strId, sizeX, sizeY);
    }

    public static boolean invisibleButton(final String strId, final imgui.ImVec2 size, final int flags) {
        return api.invisibleButton(strId, size, flags);
    }

    public static boolean invisibleButton(final String strId, final float sizeX, final float sizeY, final int flags) {
        return api.invisibleButton(strId, sizeX, sizeY, flags);
    }

    public static boolean arrowButton(final String strId, final int dir) {
        return api.arrowButton(strId, dir);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size) {
        api.image(userTextureId, size);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY) {
        api.image(userTextureId, sizeX, sizeY);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0) {
        api.image(userTextureId, size, uv0);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y) {
        api.image(userTextureId, sizeX, sizeY, uv0X, uv0Y);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1) {
        api.image(userTextureId, size, uv0, uv1);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        api.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 tintCol) {
        api.image(userTextureId, size, uv0, uv1, tintCol);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        api.image(userTextureId, size, uv0, uv1, tintColX, tintColY, tintColZ, tintColW);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 tintCol) {
        api.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintCol);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        api.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 tintCol, final imgui.ImVec4 borderCol) {
        api.image(userTextureId, size, uv0, uv1, tintCol, borderCol);
    }

    public static void image(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float tintColX, final float tintColY, final float tintColZ, final float tintColW, final float borderColX, final float borderColY, final float borderColZ, final float borderColW) {
        api.image(userTextureId, size, uv0, uv1, tintColX, tintColY, tintColZ, tintColW, borderColX, borderColY, borderColZ, borderColW);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 tintCol, final imgui.ImVec4 borderCol) {
        api.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintCol, borderCol);
    }

    public static void image(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float tintColX, final float tintColY, final float tintColZ, final float tintColW, final float borderColX, final float borderColY, final float borderColZ, final float borderColW) {
        api.image(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, tintColX, tintColY, tintColZ, tintColW, borderColX, borderColY, borderColZ, borderColW);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size) {
        return api.imageButton(userTextureId, size);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY) {
        return api.imageButton(userTextureId, sizeX, sizeY);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0) {
        return api.imageButton(userTextureId, size, uv0);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1) {
        return api.imageButton(userTextureId, size, uv0, uv1);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding) {
        return api.imageButton(userTextureId, size, uv0, uv1, framePadding);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 bgCol) {
        return api.imageButton(userTextureId, size, uv0, uv1, bgCol);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return api.imageButton(userTextureId, size, uv0, uv1, bgColX, bgColY, bgColZ, bgColW);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 bgCol) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgCol);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgColX, bgColY, bgColZ, bgColW);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final imgui.ImVec4 bgCol) {
        return api.imageButton(userTextureId, size, uv0, uv1, framePadding, bgCol);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return api.imageButton(userTextureId, size, uv0, uv1, framePadding, bgColX, bgColY, bgColZ, bgColW);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final imgui.ImVec4 bgCol) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgCol);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgColX, bgColY, bgColZ, bgColW);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return api.imageButton(userTextureId, size, uv0, uv1, bgCol, tintCol);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return api.imageButton(userTextureId, size, uv0, uv1, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgCol, tintCol);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return api.imageButton(userTextureId, size, uv0, uv1, framePadding, bgCol, tintCol);
    }

    public static boolean imageButton(final int userTextureId, final imgui.ImVec2 size, final imgui.ImVec2 uv0, final imgui.ImVec2 uv1, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return api.imageButton(userTextureId, size, uv0, uv1, framePadding, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final imgui.ImVec4 bgCol, final imgui.ImVec4 tintCol) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgCol, tintCol);
    }

    public static boolean imageButton(final int userTextureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final int framePadding, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW) {
        return api.imageButton(userTextureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, framePadding, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW);
    }

    public static boolean checkbox(final String label, final boolean v) {
        return api.checkbox(label, v);
    }

    public static boolean checkbox(final String label, final imgui.type.ImBoolean v) {
        return api.checkbox(label, v);
    }

    public static boolean checkboxFlags(final String label, final imgui.type.ImInt flags, final int flagsValue) {
        return api.checkboxFlags(label, flags, flagsValue);
    }

    public static boolean radioButton(final String label, final boolean active) {
        return api.radioButton(label, active);
    }

    public static boolean radioButton(final String label, final imgui.type.ImInt v, final int vButton) {
        return api.radioButton(label, v, vButton);
    }

    public static void progressBar(final float fraction) {
        api.progressBar(fraction);
    }

    public static void progressBar(final float fraction, final imgui.ImVec2 sizeArg) {
        api.progressBar(fraction, sizeArg);
    }

    public static void progressBar(final float fraction, final float sizeArgX, final float sizeArgY) {
        api.progressBar(fraction, sizeArgX, sizeArgY);
    }

    public static void progressBar(final float fraction, final String overlay) {
        api.progressBar(fraction, overlay);
    }

    public static void progressBar(final float fraction, final imgui.ImVec2 sizeArg, final String overlay) {
        api.progressBar(fraction, sizeArg, overlay);
    }

    public static void progressBar(final float fraction, final float sizeArgX, final float sizeArgY, final String overlay) {
        api.progressBar(fraction, sizeArgX, sizeArgY, overlay);
    }

    public static void bullet() {
        api.bullet();
    }

    public static boolean beginCombo(final String label, final String previewValue) {
        return api.beginCombo(label, previewValue);
    }

    public static boolean beginCombo(final String label, final String previewValue, final int flags) {
        return api.beginCombo(label, previewValue, flags);
    }

    public static void endCombo() {
        api.endCombo();
    }

    public static boolean combo(final String label, final imgui.type.ImInt currentItem, final String[] items) {
        return api.combo(label, currentItem, items);
    }

    public static boolean combo(final String label, final imgui.type.ImInt currentItem, final String[] items, final int popupMaxHeightInItems) {
        return api.combo(label, currentItem, items, popupMaxHeightInItems);
    }

    public static boolean combo(final String label, final imgui.type.ImInt currentItem, final String itemsSeparatedByZeros) {
        return api.combo(label, currentItem, itemsSeparatedByZeros);
    }

    public static boolean combo(final String label, final imgui.type.ImInt currentItem, final String itemsSeparatedByZeros, final int popupMaxHeightInItems) {
        return api.combo(label, currentItem, itemsSeparatedByZeros, popupMaxHeightInItems);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value) {
        return api.dragFloat(label, value);
    }

    public static boolean dragFloat(final String label, final float[] value) {
        return api.dragFloat(label, value);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed) {
        return api.dragFloat(label, value, vSpeed);
    }

    public static boolean dragFloat(final String label, final float[] value, final float vSpeed) {
        return api.dragFloat(label, value, vSpeed);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin) {
        return api.dragFloat(label, value, vSpeed, vMin);
    }

    public static boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin) {
        return api.dragFloat(label, value, vSpeed, vMin);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragFloat(final String label, final imgui.type.ImFloat value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragFloat(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return api.dragFloat(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragFloat2(final String label, final float[] value) {
        return api.dragFloat2(label, value);
    }

    public static boolean dragFloat2(final String label, final float[] value, final float vSpeed) {
        return api.dragFloat2(label, value, vSpeed);
    }

    public static boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin) {
        return api.dragFloat2(label, value, vSpeed, vMin);
    }

    public static boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloat2(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloat2(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return api.dragFloat2(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragFloat2(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return api.dragFloat2(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragFloat3(final String label, final float[] value) {
        return api.dragFloat3(label, value);
    }

    public static boolean dragFloat3(final String label, final float[] value, final float vSpeed) {
        return api.dragFloat3(label, value, vSpeed);
    }

    public static boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin) {
        return api.dragFloat3(label, value, vSpeed, vMin);
    }

    public static boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloat3(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloat3(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return api.dragFloat3(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragFloat3(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return api.dragFloat3(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragFloat4(final String label, final float[] value) {
        return api.dragFloat4(label, value);
    }

    public static boolean dragFloat4(final String label, final float[] value, final float vSpeed) {
        return api.dragFloat4(label, value, vSpeed);
    }

    public static boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin) {
        return api.dragFloat4(label, value, vSpeed, vMin);
    }

    public static boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloat4(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloat4(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final int flags) {
        return api.dragFloat4(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragFloat4(final String label, final float[] value, final float vSpeed, final float vMin, final float vMax, final String format, final int flags) {
        return api.dragFloat4(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public static boolean dragFloatRange2(final String label, final imgui.type.ImFloat vCurrentMin, final imgui.type.ImFloat vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax, final int flags) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public static boolean dragFloatRange2(final String label, final float[] vCurrentMin, final float[] vCurrentMax, final float vSpeed, final float vMin, final float vMax, final String format, final String formatMax, final int flags) {
        return api.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value) {
        return api.dragInt(label, value);
    }

    public static boolean dragInt(final String label, final int[] value) {
        return api.dragInt(label, value);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed) {
        return api.dragInt(label, value, vSpeed);
    }

    public static boolean dragInt(final String label, final int[] value, final float vSpeed) {
        return api.dragInt(label, value, vSpeed);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin) {
        return api.dragInt(label, value, vSpeed, vMin);
    }

    public static boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin) {
        return api.dragInt(label, value, vSpeed, vMin);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax) {
        return api.dragInt(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return api.dragInt(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragInt(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragInt(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return api.dragInt(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragInt(final String label, final imgui.type.ImInt value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return api.dragInt(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return api.dragInt(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragInt(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return api.dragInt(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragInt2(final String label, final int[] value) {
        return api.dragInt2(label, value);
    }

    public static boolean dragInt2(final String label, final int[] value, final float vSpeed) {
        return api.dragInt2(label, value, vSpeed);
    }

    public static boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin) {
        return api.dragInt2(label, value, vSpeed, vMin);
    }

    public static boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return api.dragInt2(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragInt2(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return api.dragInt2(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragInt2(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return api.dragInt2(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragInt3(final String label, final int[] value) {
        return api.dragInt3(label, value);
    }

    public static boolean dragInt3(final String label, final int[] value, final float vSpeed) {
        return api.dragInt3(label, value, vSpeed);
    }

    public static boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin) {
        return api.dragInt3(label, value, vSpeed, vMin);
    }

    public static boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return api.dragInt3(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragInt3(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return api.dragInt3(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragInt3(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return api.dragInt3(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragInt4(final String label, final int[] value) {
        return api.dragInt4(label, value);
    }

    public static boolean dragInt4(final String label, final int[] value, final float vSpeed) {
        return api.dragInt4(label, value, vSpeed);
    }

    public static boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin) {
        return api.dragInt4(label, value, vSpeed, vMin);
    }

    public static boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax) {
        return api.dragInt4(label, value, vSpeed, vMin, vMax);
    }

    public static boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragInt4(label, value, vSpeed, vMin, vMax, format);
    }

    public static boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final int flags) {
        return api.dragInt4(label, value, vSpeed, vMin, vMax, flags);
    }

    public static boolean dragInt4(final String label, final int[] value, final float vSpeed, final int vMin, final int vMax, final String format, final int flags) {
        return api.dragInt4(label, value, vSpeed, vMin, vMax, format, flags);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax);
    }

    public static boolean dragIntRange2(final String label, final imgui.type.ImInt vCurrentMin, final imgui.type.ImInt vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax, final int flags) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public static boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed, final int vMin, final int vMax, final String format, final String formatMax, final int flags) {
        return api.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format, formatMax, flags);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImFloat pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final float[] pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin, final float pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin, final float pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImFloat pData, final float vSpeed, final float pMin, final float pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImShort pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final short[] pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin, final short pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin, final short pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImShort pData, final float vSpeed, final short pMin, final short pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImInt pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final int[] pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin, final int pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin, final int pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImInt pData, final float vSpeed, final int pMin, final int pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImLong pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final long[] pData) {
        return api.dragScalar(label, pData);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed) {
        return api.dragScalar(label, pData, vSpeed);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin) {
        return api.dragScalar(label, pData, vSpeed, pMin);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin, final long pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin, final long pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax, final String format) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    public static boolean dragScalar(final String label, final imgui.type.ImLong pData, final float vSpeed, final long pMin, final long pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax, final String format, final int flags) {
        return api.dragScalar(label, pData, vSpeed, pMin, pMax, format, flags);
    }

    public static boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax) {
        return api.sliderFloat(label, value, vMin, vMax);
    }

    public static boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax) {
        return api.sliderFloat(label, value, vMin, vMax);
    }

    public static boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format) {
        return api.sliderFloat(label, value, vMin, vMax, format);
    }

    public static boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return api.sliderFloat(label, value, vMin, vMax, format);
    }

    public static boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax, final int flags) {
        return api.sliderFloat(label, value, vMin, vMax, flags);
    }

    public static boolean sliderFloat(final String label, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format, final int flags) {
        return api.sliderFloat(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return api.sliderFloat(label, value, vMin, vMax, flags);
    }

    public static boolean sliderFloat(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return api.sliderFloat(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax) {
        return api.sliderFloat2(label, value, vMin, vMax);
    }

    public static boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return api.sliderFloat2(label, value, vMin, vMax, format);
    }

    public static boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return api.sliderFloat2(label, value, vMin, vMax, flags);
    }

    public static boolean sliderFloat2(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return api.sliderFloat2(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax) {
        return api.sliderFloat3(label, value, vMin, vMax);
    }

    public static boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return api.sliderFloat3(label, value, vMin, vMax, format);
    }

    public static boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return api.sliderFloat3(label, value, vMin, vMax, flags);
    }

    public static boolean sliderFloat3(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return api.sliderFloat3(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax) {
        return api.sliderFloat4(label, value, vMin, vMax);
    }

    public static boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax, final String format) {
        return api.sliderFloat4(label, value, vMin, vMax, format);
    }

    public static boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax, final int flags) {
        return api.sliderFloat4(label, value, vMin, vMax, flags);
    }

    public static boolean sliderFloat4(final String label, final float[] value, final float vMin, final float vMax, final String format, final int flags) {
        return api.sliderFloat4(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax);
    }

    public static boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax, final String format) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, format);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final String format) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, format);
    }

    public static boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax, final int flags) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, flags);
    }

    public static boolean sliderAngle(final String label, final imgui.type.ImFloat vRad, final float vDegreesMin, final float vDegreesMax, final String format, final int flags) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, format, flags);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final int flags) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, flags);
    }

    public static boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax, final String format, final int flags) {
        return api.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, format, flags);
    }

    public static boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax) {
        return api.sliderInt(label, value, vMin, vMax);
    }

    public static boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax) {
        return api.sliderInt(label, value, vMin, vMax);
    }

    public static boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax, final String format) {
        return api.sliderInt(label, value, vMin, vMax, format);
    }

    public static boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return api.sliderInt(label, value, vMin, vMax, format);
    }

    public static boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax, final int flags) {
        return api.sliderInt(label, value, vMin, vMax, flags);
    }

    public static boolean sliderInt(final String label, final imgui.type.ImInt value, final int vMin, final int vMax, final String format, final int flags) {
        return api.sliderInt(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return api.sliderInt(label, value, vMin, vMax, flags);
    }

    public static boolean sliderInt(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return api.sliderInt(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax) {
        return api.sliderInt2(label, value, vMin, vMax);
    }

    public static boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return api.sliderInt2(label, value, vMin, vMax, format);
    }

    public static boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return api.sliderInt2(label, value, vMin, vMax, flags);
    }

    public static boolean sliderInt2(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return api.sliderInt2(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax) {
        return api.sliderInt3(label, value, vMin, vMax);
    }

    public static boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return api.sliderInt3(label, value, vMin, vMax, format);
    }

    public static boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return api.sliderInt3(label, value, vMin, vMax, flags);
    }

    public static boolean sliderInt3(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return api.sliderInt3(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax) {
        return api.sliderInt4(label, value, vMin, vMax);
    }

    public static boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax, final String format) {
        return api.sliderInt4(label, value, vMin, vMax, format);
    }

    public static boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax, final int flags) {
        return api.sliderInt4(label, value, vMin, vMax, flags);
    }

    public static boolean sliderInt4(final String label, final int[] value, final int vMin, final int vMax, final String format, final int flags) {
        return api.sliderInt4(label, value, vMin, vMax, format, flags);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImFloat pData, final float pMin, final float pMax) {
        return api.sliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format) {
        return api.sliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format, final int flags) {
        return api.sliderScalar(label, pData, pMin, pMax, format, flags);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImShort pData, final short pMin, final short pMax) {
        return api.sliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format) {
        return api.sliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format, final int flags) {
        return api.sliderScalar(label, pData, pMin, pMax, format, flags);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImInt pData, final int pMin, final int pMax) {
        return api.sliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format) {
        return api.sliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format, final int flags) {
        return api.sliderScalar(label, pData, pMin, pMax, format, flags);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImLong pData, final long pMin, final long pMax) {
        return api.sliderScalar(label, pData, pMin, pMax);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format) {
        return api.sliderScalar(label, pData, pMin, pMax, format);
    }

    public static boolean sliderScalar(final String label, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format, final int flags) {
        return api.sliderScalar(label, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax) {
        return api.vSliderFloat(label, size, value, vMin, vMax);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax) {
        return api.vSliderFloat(label, sizeX, sizeY, value, vMin, vMax);
    }

    public static boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format) {
        return api.vSliderFloat(label, size, value, vMin, vMax, format);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format) {
        return api.vSliderFloat(label, sizeX, sizeY, value, vMin, vMax, format);
    }

    public static boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax, final int flags) {
        return api.vSliderFloat(label, size, value, vMin, vMax, flags);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax, final int flags) {
        return api.vSliderFloat(label, sizeX, sizeY, value, vMin, vMax, flags);
    }

    public static boolean vSliderFloat(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format, final int flags) {
        return api.vSliderFloat(label, size, value, vMin, vMax, format, flags);
    }

    public static boolean vSliderFloat(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat value, final float vMin, final float vMax, final String format, final int flags) {
        return api.vSliderFloat(label, sizeX, sizeY, value, vMin, vMax, format, flags);
    }

    public static boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax) {
        return api.vSliderInt(label, size, value, vMin, vMax);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax) {
        return api.vSliderInt(label, sizeX, sizeY, value, vMin, vMax);
    }

    public static boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax, final String format) {
        return api.vSliderInt(label, size, value, vMin, vMax, format);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax, final String format) {
        return api.vSliderInt(label, sizeX, sizeY, value, vMin, vMax, format);
    }

    public static boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax, final int flags) {
        return api.vSliderInt(label, size, value, vMin, vMax, flags);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax, final int flags) {
        return api.vSliderInt(label, sizeX, sizeY, value, vMin, vMax, flags);
    }

    public static boolean vSliderInt(final String label, final imgui.ImVec2 size, final imgui.type.ImInt value, final int vMin, final int vMax, final String format, final int flags) {
        return api.vSliderInt(label, size, value, vMin, vMax, format, flags);
    }

    public static boolean vSliderInt(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt value, final int vMin, final int vMax, final String format, final int flags) {
        return api.vSliderInt(label, sizeX, sizeY, value, vMin, vMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat pData, final float pMin, final float pMax) {
        return api.vSliderScalar(label, size, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat pData, final float pMin, final float pMax) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format, final int flags) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImFloat pData, final float pMin, final float pMax, final String format, final int flags) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImShort pData, final short pMin, final short pMax) {
        return api.vSliderScalar(label, size, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImShort pData, final short pMin, final short pMax) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format, final int flags) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImShort pData, final short pMin, final short pMax, final String format, final int flags) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImInt pData, final int pMin, final int pMax) {
        return api.vSliderScalar(label, size, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt pData, final int pMin, final int pMax) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format, final int flags) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImInt pData, final int pMin, final int pMax, final String format, final int flags) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImLong pData, final long pMin, final long pMax) {
        return api.vSliderScalar(label, size, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImLong pData, final long pMin, final long pMax) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    public static boolean vSliderScalar(final String label, final imgui.ImVec2 size, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format, final int flags) {
        return api.vSliderScalar(label, size, pData, pMin, pMax, format, flags);
    }

    public static boolean vSliderScalar(final String label, final float sizeX, final float sizeY, final imgui.type.ImLong pData, final long pMin, final long pMax, final String format, final int flags) {
        return api.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format, flags);
    }

    public static boolean inputText(final String label, final imgui.type.ImString text) {
        return api.inputText(label, text);
    }

    public static boolean inputText(final String label, final imgui.type.ImString text, final int flags) {
        return api.inputText(label, text, flags);
    }

    public static boolean inputText(final String label, final imgui.type.ImString text, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        return api.inputText(label, text, flags, callback);
    }

    public static boolean inputTextMultiline(final String label, final imgui.type.ImString text) {
        return api.inputTextMultiline(label, text);
    }

    public static boolean inputTextMultiline(final String label, final imgui.type.ImString text, final float width, final float height) {
        return api.inputTextMultiline(label, text, width, height);
    }

    public static boolean inputTextMultiline(final String label, final imgui.type.ImString text, final float width, final float height, final int flags) {
        return api.inputTextMultiline(label, text, width, height, flags);
    }

    public static boolean inputTextMultiline(final String label, final imgui.type.ImString text, final float width, final float height, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        return api.inputTextMultiline(label, text, width, height, flags, callback);
    }

    public static boolean inputTextMultiline(final String label, final imgui.type.ImString text, final int flags) {
        return api.inputTextMultiline(label, text, flags);
    }

    public static boolean inputTextMultiline(final String label, final imgui.type.ImString text, final int flags, final imgui.callback.ImGuiInputTextCallback callback) {
        return api.inputTextMultiline(label, text, flags, callback);
    }

    public static boolean inputFloat(final String label, final imgui.type.ImFloat value) {
        return api.inputFloat(label, value);
    }

    public static boolean inputFloat(final String label, final float[] value) {
        return api.inputFloat(label, value);
    }

    public static boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step) {
        return api.inputFloat(label, value, step);
    }

    public static boolean inputFloat(final String label, final float[] value, final float step) {
        return api.inputFloat(label, value, step);
    }

    public static boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast) {
        return api.inputFloat(label, value, step, stepFast);
    }

    public static boolean inputFloat(final String label, final float[] value, final float step, final float stepFast) {
        return api.inputFloat(label, value, step, stepFast);
    }

    public static boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast, final String format) {
        return api.inputFloat(label, value, step, stepFast, format);
    }

    public static boolean inputFloat(final String label, final float[] value, final float step, final float stepFast, final String format) {
        return api.inputFloat(label, value, step, stepFast, format);
    }

    public static boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast, final int flags) {
        return api.inputFloat(label, value, step, stepFast, flags);
    }

    public static boolean inputFloat(final String label, final imgui.type.ImFloat value, final float step, final float stepFast, final String format, final int flags) {
        return api.inputFloat(label, value, step, stepFast, format, flags);
    }

    public static boolean inputFloat(final String label, final float[] value, final float step, final float stepFast, final int flags) {
        return api.inputFloat(label, value, step, stepFast, flags);
    }

    public static boolean inputFloat(final String label, final float[] value, final float step, final float stepFast, final String format, final int flags) {
        return api.inputFloat(label, value, step, stepFast, format, flags);
    }

    public static boolean inputFloat2(final String label, final float[] value) {
        return api.inputFloat2(label, value);
    }

    public static boolean inputFloat2(final String label, final float[] value, final String format) {
        return api.inputFloat2(label, value, format);
    }

    public static boolean inputFloat2(final String label, final float[] value, final int flags) {
        return api.inputFloat2(label, value, flags);
    }

    public static boolean inputFloat2(final String label, final float[] value, final String format, final int flags) {
        return api.inputFloat2(label, value, format, flags);
    }

    public static boolean inputFloat3(final String label, final float[] value) {
        return api.inputFloat3(label, value);
    }

    public static boolean inputFloat3(final String label, final float[] value, final String format) {
        return api.inputFloat3(label, value, format);
    }

    public static boolean inputFloat3(final String label, final float[] value, final int flags) {
        return api.inputFloat3(label, value, flags);
    }

    public static boolean inputFloat3(final String label, final float[] value, final String format, final int flags) {
        return api.inputFloat3(label, value, format, flags);
    }

    public static boolean inputFloat4(final String label, final float[] value) {
        return api.inputFloat4(label, value);
    }

    public static boolean inputFloat4(final String label, final float[] value, final String format) {
        return api.inputFloat4(label, value, format);
    }

    public static boolean inputFloat4(final String label, final float[] value, final int flags) {
        return api.inputFloat4(label, value, flags);
    }

    public static boolean inputFloat4(final String label, final float[] value, final String format, final int flags) {
        return api.inputFloat4(label, value, format, flags);
    }

    public static boolean inputInt(final String label, final imgui.type.ImInt value) {
        return api.inputInt(label, value);
    }

    public static boolean inputInt(final String label, final int[] value) {
        return api.inputInt(label, value);
    }

    public static boolean inputInt(final String label, final imgui.type.ImInt value, final int step) {
        return api.inputInt(label, value, step);
    }

    public static boolean inputInt(final String label, final int[] value, final int step) {
        return api.inputInt(label, value, step);
    }

    public static boolean inputInt(final String label, final imgui.type.ImInt value, final int step, final int stepFast) {
        return api.inputInt(label, value, step, stepFast);
    }

    public static boolean inputInt(final String label, final int[] value, final int step, final int stepFast) {
        return api.inputInt(label, value, step, stepFast);
    }

    public static boolean inputInt(final String label, final imgui.type.ImInt value, final int step, final int stepFast, final int flags) {
        return api.inputInt(label, value, step, stepFast, flags);
    }

    public static boolean inputInt(final String label, final int[] value, final int step, final int stepFast, final int flags) {
        return api.inputInt(label, value, step, stepFast, flags);
    }

    public static boolean inputInt2(final String label, final int[] value) {
        return api.inputInt2(label, value);
    }

    public static boolean inputInt2(final String label, final int[] value, final int flags) {
        return api.inputInt2(label, value, flags);
    }

    public static boolean inputInt3(final String label, final int[] value) {
        return api.inputInt3(label, value);
    }

    public static boolean inputInt3(final String label, final int[] value, final int flags) {
        return api.inputInt3(label, value, flags);
    }

    public static boolean inputInt4(final String label, final int[] value) {
        return api.inputInt4(label, value);
    }

    public static boolean inputInt4(final String label, final int[] value, final int flags) {
        return api.inputInt4(label, value, flags);
    }

    public static boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast) {
        return api.inputDouble(label, value, step, stepFast);
    }

    public static boolean inputDouble(final String label, final double[] value, final double step, final double stepFast) {
        return api.inputDouble(label, value, step, stepFast);
    }

    public static boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast, final String format) {
        return api.inputDouble(label, value, step, stepFast, format);
    }

    public static boolean inputDouble(final String label, final double[] value, final double step, final double stepFast, final String format) {
        return api.inputDouble(label, value, step, stepFast, format);
    }

    public static boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast, final int flags) {
        return api.inputDouble(label, value, step, stepFast, flags);
    }

    public static boolean inputDouble(final String label, final imgui.type.ImDouble value, final double step, final double stepFast, final String format, final int flags) {
        return api.inputDouble(label, value, step, stepFast, format, flags);
    }

    public static boolean inputDouble(final String label, final double[] value, final double step, final double stepFast, final int flags) {
        return api.inputDouble(label, value, step, stepFast, flags);
    }

    public static boolean inputDouble(final String label, final double[] value, final double step, final double stepFast, final String format, final int flags) {
        return api.inputDouble(label, value, step, stepFast, format, flags);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImFloat pData) {
        return api.inputScalar(label, pData);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep) {
        return api.inputScalar(label, pData, pStep);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep, final float pStepFast) {
        return api.inputScalar(label, pData, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep, final float pStepFast, final String format) {
        return api.inputScalar(label, pData, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImFloat pData, final float pStep, final float pStepFast, final String format, final int flags) {
        return api.inputScalar(label, pData, pStep, pStepFast, format, flags);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImShort pData) {
        return api.inputScalar(label, pData);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep) {
        return api.inputScalar(label, pData, pStep);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep, final short pStepFast) {
        return api.inputScalar(label, pData, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep, final short pStepFast, final String format) {
        return api.inputScalar(label, pData, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImShort pData, final short pStep, final short pStepFast, final String format, final int flags) {
        return api.inputScalar(label, pData, pStep, pStepFast, format, flags);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImInt pData) {
        return api.inputScalar(label, pData);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep) {
        return api.inputScalar(label, pData, pStep);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep, final int pStepFast) {
        return api.inputScalar(label, pData, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep, final int pStepFast, final String format) {
        return api.inputScalar(label, pData, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImInt pData, final int pStep, final int pStepFast, final String format, final int flags) {
        return api.inputScalar(label, pData, pStep, pStepFast, format, flags);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImLong pData) {
        return api.inputScalar(label, pData);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep) {
        return api.inputScalar(label, pData, pStep);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep, final long pStepFast) {
        return api.inputScalar(label, pData, pStep, pStepFast);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep, final long pStepFast, final String format) {
        return api.inputScalar(label, pData, pStep, pStepFast, format);
    }

    public static boolean inputScalar(final String label, final imgui.type.ImLong pData, final long pStep, final long pStepFast, final String format, final int flags) {
        return api.inputScalar(label, pData, pStep, pStepFast, format, flags);
    }

    public static boolean colorEdit3(final String label, final float[] col) {
        return api.colorEdit3(label, col);
    }

    public static boolean colorEdit3(final String label, final float[] col, final int flags) {
        return api.colorEdit3(label, col, flags);
    }

    public static boolean colorEdit4(final String label, final float[] col) {
        return api.colorEdit4(label, col);
    }

    public static boolean colorEdit4(final String label, final float[] col, final int flags) {
        return api.colorEdit4(label, col, flags);
    }

    public static boolean colorPicker3(final String label, final float[] col) {
        return api.colorPicker3(label, col);
    }

    public static boolean colorPicker3(final String label, final float[] col, final int flags) {
        return api.colorPicker3(label, col, flags);
    }

    public static boolean colorPicker4(final String label, final float[] col) {
        return api.colorPicker4(label, col);
    }

    public static boolean colorPicker4(final String label, final float[] col, final int flags) {
        return api.colorPicker4(label, col, flags);
    }

    public static boolean colorPicker4(final String label, final float[] col, final int flags, final imgui.type.ImFloat refCol) {
        return api.colorPicker4(label, col, flags, refCol);
    }

    public static boolean colorButton(final String descId, final imgui.ImVec4 col) {
        return api.colorButton(descId, col);
    }

    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW) {
        return api.colorButton(descId, colX, colY, colZ, colW);
    }

    public static boolean colorButton(final String descId, final imgui.ImVec4 col, final int flags) {
        return api.colorButton(descId, col, flags);
    }

    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int flags) {
        return api.colorButton(descId, colX, colY, colZ, colW, flags);
    }

    public static boolean colorButton(final String descId, final imgui.ImVec4 col, final imgui.ImVec2 size) {
        return api.colorButton(descId, col, size);
    }

    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final imgui.ImVec2 size) {
        return api.colorButton(descId, colX, colY, colZ, colW, size);
    }

    public static boolean colorButton(final String descId, final imgui.ImVec4 col, final float sizeX, final float sizeY) {
        return api.colorButton(descId, col, sizeX, sizeY);
    }

    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final float sizeX, final float sizeY) {
        return api.colorButton(descId, colX, colY, colZ, colW, sizeX, sizeY);
    }

    public static boolean colorButton(final String descId, final imgui.ImVec4 col, final int flags, final imgui.ImVec2 size) {
        return api.colorButton(descId, col, flags, size);
    }

    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int flags, final imgui.ImVec2 size) {
        return api.colorButton(descId, colX, colY, colZ, colW, flags, size);
    }

    public static boolean colorButton(final String descId, final imgui.ImVec4 col, final int flags, final float sizeX, final float sizeY) {
        return api.colorButton(descId, col, flags, sizeX, sizeY);
    }

    public static boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW, final int flags, final float sizeX, final float sizeY) {
        return api.colorButton(descId, colX, colY, colZ, colW, flags, sizeX, sizeY);
    }

    public static void setColorEditOptions(final int flags) {
        api.setColorEditOptions(flags);
    }

    public static boolean treeNode(final String label) {
        return api.treeNode(label);
    }

    public static boolean treeNode(final String strId, final String fmt) {
        return api.treeNode(strId, fmt);
    }

    public static boolean treeNodeEx(final String label) {
        return api.treeNodeEx(label);
    }

    public static boolean treeNodeEx(final String label, final int flags) {
        return api.treeNodeEx(label, flags);
    }

    public static boolean treeNodeEx(final String strId, final int flags, final String fmt) {
        return api.treeNodeEx(strId, flags, fmt);
    }

    public static void treePush(final String strId) {
        api.treePush(strId);
    }

    public static void treePop() {
        api.treePop();
    }

    public static float getTreeNodeToLabelSpacing() {
        return api.getTreeNodeToLabelSpacing();
    }

    public static boolean collapsingHeader(final String label) {
        return api.collapsingHeader(label);
    }

    public static boolean collapsingHeader(final String label, final int flags) {
        return api.collapsingHeader(label, flags);
    }

    public static boolean collapsingHeader(final String label, final imgui.type.ImBoolean pVisible) {
        return api.collapsingHeader(label, pVisible);
    }

    public static boolean collapsingHeader(final String label, final imgui.type.ImBoolean pVisible, final int flags) {
        return api.collapsingHeader(label, pVisible, flags);
    }

    public static void setNextItemOpen(final boolean isOpen) {
        api.setNextItemOpen(isOpen);
    }

    public static void setNextItemOpen(final boolean isOpen, final int cond) {
        api.setNextItemOpen(isOpen, cond);
    }

    public static boolean selectable(final String label) {
        return api.selectable(label);
    }

    public static boolean selectable(final String label, final boolean selected) {
        return api.selectable(label, selected);
    }

    public static boolean selectable(final String label, final int flags) {
        return api.selectable(label, flags);
    }

    public static boolean selectable(final String label, final boolean selected, final int flags) {
        return api.selectable(label, selected, flags);
    }

    public static boolean selectable(final String label, final int flags, final imgui.ImVec2 size) {
        return api.selectable(label, flags, size);
    }

    public static boolean selectable(final String label, final int flags, final float sizeX, final float sizeY) {
        return api.selectable(label, flags, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final boolean selected, final imgui.ImVec2 size) {
        return api.selectable(label, selected, size);
    }

    public static boolean selectable(final String label, final boolean selected, final float sizeX, final float sizeY) {
        return api.selectable(label, selected, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final boolean selected, final int flags, final imgui.ImVec2 size) {
        return api.selectable(label, selected, flags, size);
    }

    public static boolean selectable(final String label, final boolean selected, final int flags, final float sizeX, final float sizeY) {
        return api.selectable(label, selected, flags, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final imgui.type.ImBoolean pSelected) {
        return api.selectable(label, pSelected);
    }

    public static boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final int flags) {
        return api.selectable(label, pSelected, flags);
    }

    public static boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final imgui.ImVec2 size) {
        return api.selectable(label, pSelected, size);
    }

    public static boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final float sizeX, final float sizeY) {
        return api.selectable(label, pSelected, sizeX, sizeY);
    }

    public static boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final int flags, final imgui.ImVec2 size) {
        return api.selectable(label, pSelected, flags, size);
    }

    public static boolean selectable(final String label, final imgui.type.ImBoolean pSelected, final int flags, final float sizeX, final float sizeY) {
        return api.selectable(label, pSelected, flags, sizeX, sizeY);
    }

    public static boolean beginListBox(final String label) {
        return api.beginListBox(label);
    }

    public static boolean beginListBox(final String label, final imgui.ImVec2 size) {
        return api.beginListBox(label, size);
    }

    public static boolean beginListBox(final String label, final float sizeX, final float sizeY) {
        return api.beginListBox(label, sizeX, sizeY);
    }

    public static void endListBox() {
        api.endListBox();
    }

    public static boolean listBox(final String label, final imgui.type.ImInt currentItem, final String[] items) {
        return api.listBox(label, currentItem, items);
    }

    public static boolean listBox(final String label, final imgui.type.ImInt currentItem, final String[] items, final int heightInItems) {
        return api.listBox(label, currentItem, items, heightInItems);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount) {
        api.plotLines(label, values, valuesCount);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset) {
        api.plotLines(label, values, valuesCount, valuesOffset);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin) {
        api.plotLines(label, values, valuesCount, valuesOffset, scaleMin);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax) {
        api.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        api.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        api.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        api.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        api.plotLines(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final int stride) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize, stride);
    }

    public static void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        api.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSize, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final int stride) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final imgui.ImVec2 graphSize, final int stride) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSize, stride);
    }

    public static void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset, final String overlayText, final float scaleMin, final float scaleMax, final float graphSizeX, final float graphSizeY, final int stride) {
        api.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax, graphSizeX, graphSizeY, stride);
    }

    public static void value(final String prefix, final boolean b) {
        api.value(prefix, b);
    }

    public static void value(final String prefix, final int v) {
        api.value(prefix, v);
    }

    public static void value(final String prefix, final float v) {
        api.value(prefix, v);
    }

    public static void value(final String prefix, final float v, final String floatFormat) {
        api.value(prefix, v, floatFormat);
    }

    public static boolean beginMenuBar() {
        return api.beginMenuBar();
    }

    public static void endMenuBar() {
        api.endMenuBar();
    }

    public static boolean beginMainMenuBar() {
        return api.beginMainMenuBar();
    }

    public static void endMainMenuBar() {
        api.endMainMenuBar();
    }

    public static boolean beginMenu(final String label) {
        return api.beginMenu(label);
    }

    public static boolean beginMenu(final String label, final boolean enabled) {
        return api.beginMenu(label, enabled);
    }

    public static void endMenu() {
        api.endMenu();
    }

    public static boolean menuItem(final String label) {
        return api.menuItem(label);
    }

    public static boolean menuItem(final String label, final String shortcut) {
        return api.menuItem(label, shortcut);
    }

    public static boolean menuItem(final String label, final String shortcut, final boolean selected) {
        return api.menuItem(label, shortcut, selected);
    }

    public static boolean menuItem(final String label, final String shortcut, final boolean selected, final boolean enabled) {
        return api.menuItem(label, shortcut, selected, enabled);
    }

    public static boolean menuItem(final String label, final String shortcut, final imgui.type.ImBoolean pSelected) {
        return api.menuItem(label, shortcut, pSelected);
    }

    public static boolean menuItem(final String label, final String shortcut, final imgui.type.ImBoolean pSelected, final boolean enabled) {
        return api.menuItem(label, shortcut, pSelected, enabled);
    }

    public static void beginTooltip() {
        api.beginTooltip();
    }

    public static void endTooltip() {
        api.endTooltip();
    }

    public static void setTooltip(final String fmt) {
        api.setTooltip(fmt);
    }

    public static boolean beginPopup(final String strId) {
        return api.beginPopup(strId);
    }

    public static boolean beginPopup(final String strId, final int flags) {
        return api.beginPopup(strId, flags);
    }

    public static boolean beginPopupModal(final String name) {
        return api.beginPopupModal(name);
    }

    public static boolean beginPopupModal(final String name, final imgui.type.ImBoolean pOpen) {
        return api.beginPopupModal(name, pOpen);
    }

    public static boolean beginPopupModal(final String name, final int flags) {
        return api.beginPopupModal(name, flags);
    }

    public static boolean beginPopupModal(final String name, final imgui.type.ImBoolean pOpen, final int flags) {
        return api.beginPopupModal(name, pOpen, flags);
    }

    public static void endPopup() {
        api.endPopup();
    }

    public static void openPopup(final String strId) {
        api.openPopup(strId);
    }

    public static void openPopup(final String strId, final int popupFlags) {
        api.openPopup(strId, popupFlags);
    }

    public static void openPopup(final int id) {
        api.openPopup(id);
    }

    public static void openPopup(final int id, final int popupFlags) {
        api.openPopup(id, popupFlags);
    }

    public static void openPopupOnItemClick() {
        api.openPopupOnItemClick();
    }

    public static void openPopupOnItemClick(final String strId) {
        api.openPopupOnItemClick(strId);
    }

    public static void openPopupOnItemClick(final int popupFlags) {
        api.openPopupOnItemClick(popupFlags);
    }

    public static void openPopupOnItemClick(final String strId, final int popupFlags) {
        api.openPopupOnItemClick(strId, popupFlags);
    }

    public static void closeCurrentPopup() {
        api.closeCurrentPopup();
    }

    public static boolean beginPopupContextItem() {
        return api.beginPopupContextItem();
    }

    public static boolean beginPopupContextItem(final String strId) {
        return api.beginPopupContextItem(strId);
    }

    public static boolean beginPopupContextItem(final int popupFlags) {
        return api.beginPopupContextItem(popupFlags);
    }

    public static boolean beginPopupContextItem(final String strId, final int popupFlags) {
        return api.beginPopupContextItem(strId, popupFlags);
    }

    public static boolean beginPopupContextWindow() {
        return api.beginPopupContextWindow();
    }

    public static boolean beginPopupContextWindow(final String strId) {
        return api.beginPopupContextWindow(strId);
    }

    public static boolean beginPopupContextWindow(final int popupFlags) {
        return api.beginPopupContextWindow(popupFlags);
    }

    public static boolean beginPopupContextWindow(final String strId, final int popupFlags) {
        return api.beginPopupContextWindow(strId, popupFlags);
    }

    public static boolean beginPopupContextVoid() {
        return api.beginPopupContextVoid();
    }

    public static boolean beginPopupContextVoid(final String strId) {
        return api.beginPopupContextVoid(strId);
    }

    public static boolean beginPopupContextVoid(final int popupFlags) {
        return api.beginPopupContextVoid(popupFlags);
    }

    public static boolean beginPopupContextVoid(final String strId, final int popupFlags) {
        return api.beginPopupContextVoid(strId, popupFlags);
    }

    public static boolean isPopupOpen(final String strId) {
        return api.isPopupOpen(strId);
    }

    public static boolean isPopupOpen(final String strId, final int flags) {
        return api.isPopupOpen(strId, flags);
    }

    public static boolean beginTable(final String strId, final int column) {
        return api.beginTable(strId, column);
    }

    public static boolean beginTable(final String strId, final int column, final int flags) {
        return api.beginTable(strId, column, flags);
    }

    public static boolean beginTable(final String strId, final int column, final int flags, final imgui.ImVec2 outerSize) {
        return api.beginTable(strId, column, flags, outerSize);
    }

    public static boolean beginTable(final String strId, final int column, final int flags, final float outerSizeX, final float outerSizeY) {
        return api.beginTable(strId, column, flags, outerSizeX, outerSizeY);
    }

    public static boolean beginTable(final String strId, final int column, final int flags, final float innerWidth) {
        return api.beginTable(strId, column, flags, innerWidth);
    }

    public static boolean beginTable(final String strId, final int column, final int flags, final imgui.ImVec2 outerSize, final float innerWidth) {
        return api.beginTable(strId, column, flags, outerSize, innerWidth);
    }

    public static boolean beginTable(final String strId, final int column, final int flags, final float outerSizeX, final float outerSizeY, final float innerWidth) {
        return api.beginTable(strId, column, flags, outerSizeX, outerSizeY, innerWidth);
    }

    public static void endTable() {
        api.endTable();
    }

    public static void tableNextRow() {
        api.tableNextRow();
    }

    public static void tableNextRow(final int rowFlags) {
        api.tableNextRow(rowFlags);
    }

    public static void tableNextRow(final float minRowHeight) {
        api.tableNextRow(minRowHeight);
    }

    public static void tableNextRow(final int rowFlags, final float minRowHeight) {
        api.tableNextRow(rowFlags, minRowHeight);
    }

    public static boolean tableNextColumn() {
        return api.tableNextColumn();
    }

    public static boolean tableSetColumnIndex(final int columnN) {
        return api.tableSetColumnIndex(columnN);
    }

    public static void tableSetupColumn(final String label) {
        api.tableSetupColumn(label);
    }

    public static void tableSetupColumn(final String label, final int flags) {
        api.tableSetupColumn(label, flags);
    }

    public static void tableSetupColumn(final String label, final float initWidthOrWeight) {
        api.tableSetupColumn(label, initWidthOrWeight);
    }

    public static void tableSetupColumn(final String label, final int flags, final float initWidthOrWeight) {
        api.tableSetupColumn(label, flags, initWidthOrWeight);
    }

    public static void tableSetupColumn(final String label, final float initWidthOrWeight, final int userId) {
        api.tableSetupColumn(label, initWidthOrWeight, userId);
    }

    public static void tableSetupColumn(final String label, final int flags, final int userId) {
        api.tableSetupColumn(label, flags, userId);
    }

    public static void tableSetupColumn(final String label, final int flags, final float initWidthOrWeight, final int userId) {
        api.tableSetupColumn(label, flags, initWidthOrWeight, userId);
    }

    public static void tableSetupScrollFreeze(final int cols, final int rows) {
        api.tableSetupScrollFreeze(cols, rows);
    }

    public static void tableHeadersRow() {
        api.tableHeadersRow();
    }

    public static void tableHeader(final String label) {
        api.tableHeader(label);
    }

    public static int tableGetColumnCount() {
        return api.tableGetColumnCount();
    }

    public static int tableGetColumnIndex() {
        return api.tableGetColumnIndex();
    }

    public static int tableGetRowIndex() {
        return api.tableGetRowIndex();
    }

    public static String tableGetColumnName() {
        return api.tableGetColumnName();
    }

    public static String tableGetColumnName(final int columnN) {
        return api.tableGetColumnName(columnN);
    }

    public static int tableGetColumnFlags() {
        return api.tableGetColumnFlags();
    }

    public static int tableGetColumnFlags(final int columnN) {
        return api.tableGetColumnFlags(columnN);
    }

    public static void tableSetColumnEnabled(final int columnN, final boolean v) {
        api.tableSetColumnEnabled(columnN, v);
    }

    public static void tableSetBgColor(final int target, final int color) {
        api.tableSetBgColor(target, color);
    }

    public static void tableSetBgColor(final int target, final int color, final int columnN) {
        api.tableSetBgColor(target, color, columnN);
    }

    public static void columns() {
        api.columns();
    }

    public static void columns(final int count) {
        api.columns(count);
    }

    public static void columns(final String id) {
        api.columns(id);
    }

    public static void columns(final int count, final String id) {
        api.columns(count, id);
    }

    public static void columns(final String id, final boolean border) {
        api.columns(id, border);
    }

    public static void columns(final int count, final boolean border) {
        api.columns(count, border);
    }

    public static void columns(final int count, final String id, final boolean border) {
        api.columns(count, id, border);
    }

    public static void nextColumn() {
        api.nextColumn();
    }

    public static int getColumnIndex() {
        return api.getColumnIndex();
    }

    public static float getColumnWidth() {
        return api.getColumnWidth();
    }

    public static float getColumnWidth(final int columnIndex) {
        return api.getColumnWidth(columnIndex);
    }

    public static void setColumnWidth(final int columnIndex, final float width) {
        api.setColumnWidth(columnIndex, width);
    }

    public static float getColumnOffset() {
        return api.getColumnOffset();
    }

    public static float getColumnOffset(final int columnIndex) {
        return api.getColumnOffset(columnIndex);
    }

    public static void setColumnOffset(final int columnIndex, final float offsetX) {
        api.setColumnOffset(columnIndex, offsetX);
    }

    public static int getColumnsCount() {
        return api.getColumnsCount();
    }

    public static boolean beginTabBar(final String strId) {
        return api.beginTabBar(strId);
    }

    public static boolean beginTabBar(final String strId, final int flags) {
        return api.beginTabBar(strId, flags);
    }

    public static void endTabBar() {
        api.endTabBar();
    }

    public static boolean beginTabItem(final String label) {
        return api.beginTabItem(label);
    }

    public static boolean beginTabItem(final String label, final imgui.type.ImBoolean pOpen) {
        return api.beginTabItem(label, pOpen);
    }

    public static boolean beginTabItem(final String label, final int flags) {
        return api.beginTabItem(label, flags);
    }

    public static boolean beginTabItem(final String label, final imgui.type.ImBoolean pOpen, final int flags) {
        return api.beginTabItem(label, pOpen, flags);
    }

    public static void endTabItem() {
        api.endTabItem();
    }

    public static boolean tabItemButton(final String label) {
        return api.tabItemButton(label);
    }

    public static boolean tabItemButton(final String label, final int flags) {
        return api.tabItemButton(label, flags);
    }

    public static void setTabItemClosed(final String tabOrDockedWindowLabel) {
        api.setTabItemClosed(tabOrDockedWindowLabel);
    }

    public static int dockSpace(final int id) {
        return api.dockSpace(id);
    }

    public static int dockSpace(final int id, final imgui.ImVec2 size) {
        return api.dockSpace(id, size);
    }

    public static int dockSpace(final int id, final float sizeX, final float sizeY) {
        return api.dockSpace(id, sizeX, sizeY);
    }

    public static int dockSpace(final int id, final int flags) {
        return api.dockSpace(id, flags);
    }

    public static int dockSpace(final int id, final imgui.ImVec2 size, final int flags) {
        return api.dockSpace(id, size, flags);
    }

    public static int dockSpace(final int id, final float sizeX, final float sizeY, final int flags) {
        return api.dockSpace(id, sizeX, sizeY, flags);
    }

    public static int dockSpace(final int id, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpace(id, flags, windowClass);
    }

    public static int dockSpace(final int id, final imgui.ImVec2 size, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpace(id, size, windowClass);
    }

    public static int dockSpace(final int id, final float sizeX, final float sizeY, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpace(id, sizeX, sizeY, windowClass);
    }

    public static int dockSpace(final int id, final imgui.ImVec2 size, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpace(id, size, flags, windowClass);
    }

    public static int dockSpace(final int id, final float sizeX, final float sizeY, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpace(id, sizeX, sizeY, flags, windowClass);
    }

    public static int dockSpaceOverViewport() {
        return api.dockSpaceOverViewport();
    }

    public static int dockSpaceOverViewport(final imgui.ImGuiViewport viewport) {
        return api.dockSpaceOverViewport(viewport);
    }

    public static int dockSpaceOverViewport(final int flags) {
        return api.dockSpaceOverViewport(flags);
    }

    public static int dockSpaceOverViewport(final imgui.ImGuiViewport viewport, final int flags) {
        return api.dockSpaceOverViewport(viewport, flags);
    }

    public static int dockSpaceOverViewport(final int flags, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpaceOverViewport(flags, windowClass);
    }

    public static int dockSpaceOverViewport(final imgui.ImGuiViewport viewport, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpaceOverViewport(viewport, windowClass);
    }

    public static int dockSpaceOverViewport(final imgui.ImGuiViewport viewport, final int flags, final imgui.ImGuiWindowClass windowClass) {
        return api.dockSpaceOverViewport(viewport, flags, windowClass);
    }

    public static void setNextWindowDockID(final int dockId) {
        api.setNextWindowDockID(dockId);
    }

    public static void setNextWindowDockID(final int dockId, final int cond) {
        api.setNextWindowDockID(dockId, cond);
    }

    public static void setNextWindowClass(final imgui.ImGuiWindowClass windowClass) {
        api.setNextWindowClass(windowClass);
    }

    public static int getWindowDockID() {
        return api.getWindowDockID();
    }

    public static boolean isWindowDocked() {
        return api.isWindowDocked();
    }

    public static void logToTTY() {
        api.logToTTY();
    }

    public static void logToTTY(final int autoOpenDepth) {
        api.logToTTY(autoOpenDepth);
    }

    public static void logToFile() {
        api.logToFile();
    }

    public static void logToFile(final int autoOpenDepth) {
        api.logToFile(autoOpenDepth);
    }

    public static void logToFile(final String filename) {
        api.logToFile(filename);
    }

    public static void logToFile(final int autoOpenDepth, final String filename) {
        api.logToFile(autoOpenDepth, filename);
    }

    public static void logToClipboard() {
        api.logToClipboard();
    }

    public static void logToClipboard(final int autoOpenDepth) {
        api.logToClipboard(autoOpenDepth);
    }

    public static void logFinish() {
        api.logFinish();
    }

    public static void logButtons() {
        api.logButtons();
    }

    public static void logText(final String fmt) {
        api.logText(fmt);
    }

    public static boolean beginDragDropSource() {
        return api.beginDragDropSource();
    }

    public static boolean beginDragDropSource(final int flags) {
        return api.beginDragDropSource(flags);
    }

    public static boolean setDragDropPayload(final String dataType, final Object payload) {
        return api.setDragDropPayload(dataType, payload);
    }

    public static boolean setDragDropPayload(final String dataType, final Object payload, final int cond) {
        return api.setDragDropPayload(dataType, payload, cond);
    }

    public static boolean setDragDropPayload(final Object payload) {
        return api.setDragDropPayload(payload);
    }

    public static boolean setDragDropPayload(final Object payload, final int cond) {
        return api.setDragDropPayload(payload, cond);
    }

    public static void endDragDropSource() {
        api.endDragDropSource();
    }

    public static boolean beginDragDropTarget() {
        return api.beginDragDropTarget();
    }

    public static <T> T acceptDragDropPayload(final String dataType) {
        return api.acceptDragDropPayload(dataType);
    }

    public static <T> T acceptDragDropPayload(final String dataType, final Class<T> aClass) {
        return api.acceptDragDropPayload(dataType, aClass);
    }

    public static <T> T acceptDragDropPayload(final String dataType, final int flags) {
        return api.acceptDragDropPayload(dataType, flags);
    }

    public static <T> T acceptDragDropPayload(final String dataType, final int flags, final Class<T> aClass) {
        return api.acceptDragDropPayload(dataType, flags, aClass);
    }

    public static <T> T acceptDragDropPayload(final Class<T> aClass) {
        return api.acceptDragDropPayload(aClass);
    }

    public static <T> T acceptDragDropPayload(final Class<T> aClass, final int flags) {
        return api.acceptDragDropPayload(aClass, flags);
    }

    public static void endDragDropTarget() {
        api.endDragDropTarget();
    }

    public static <T> T getDragDropPayload() {
        return api.getDragDropPayload();
    }

    public static <T> T getDragDropPayload(final String dataType) {
        return api.getDragDropPayload(dataType);
    }

    public static <T> T getDragDropPayload(final Class<T> aClass) {
        return api.getDragDropPayload(aClass);
    }

    public static void beginDisabled() {
        api.beginDisabled();
    }

    public static void beginDisabled(final boolean disabled) {
        api.beginDisabled(disabled);
    }

    public static void endDisabled() {
        api.endDisabled();
    }

    public static void pushClipRect(final imgui.ImVec2 clipRectMin, final imgui.ImVec2 clipRectMax, final boolean intersectWithCurrentClipRect) {
        api.pushClipRect(clipRectMin, clipRectMax, intersectWithCurrentClipRect);
    }

    public static void pushClipRect(final float clipRectMinX, final float clipRectMinY, final float clipRectMaxX, final float clipRectMaxY, final boolean intersectWithCurrentClipRect) {
        api.pushClipRect(clipRectMinX, clipRectMinY, clipRectMaxX, clipRectMaxY, intersectWithCurrentClipRect);
    }

    public static void popClipRect() {
        api.popClipRect();
    }

    public static void setItemDefaultFocus() {
        api.setItemDefaultFocus();
    }

    public static void setKeyboardFocusHere() {
        api.setKeyboardFocusHere();
    }

    public static void setKeyboardFocusHere(final int offset) {
        api.setKeyboardFocusHere(offset);
    }

    public static boolean isItemHovered() {
        return api.isItemHovered();
    }

    public static boolean isItemHovered(final int flags) {
        return api.isItemHovered(flags);
    }

    public static boolean isItemActive() {
        return api.isItemActive();
    }

    public static boolean isItemFocused() {
        return api.isItemFocused();
    }

    public static boolean isItemClicked() {
        return api.isItemClicked();
    }

    public static boolean isItemClicked(final int mouseButton) {
        return api.isItemClicked(mouseButton);
    }

    public static boolean isItemVisible() {
        return api.isItemVisible();
    }

    public static boolean isItemEdited() {
        return api.isItemEdited();
    }

    public static boolean isItemActivated() {
        return api.isItemActivated();
    }

    public static boolean isItemDeactivated() {
        return api.isItemDeactivated();
    }

    public static boolean isItemDeactivatedAfterEdit() {
        return api.isItemDeactivatedAfterEdit();
    }

    public static boolean isItemToggledOpen() {
        return api.isItemToggledOpen();
    }

    public static boolean isAnyItemHovered() {
        return api.isAnyItemHovered();
    }

    public static boolean isAnyItemActive() {
        return api.isAnyItemActive();
    }

    public static boolean isAnyItemFocused() {
        return api.isAnyItemFocused();
    }

    public static imgui.ImVec2 getItemRectMin() {
        return api.getItemRectMin();
    }

    public static void getItemRectMin(final imgui.ImVec2 dst) {
        api.getItemRectMin(dst);
    }

    public static imgui.ImVec2 getItemRectMax() {
        return api.getItemRectMax();
    }

    public static void getItemRectMax(final imgui.ImVec2 dst) {
        api.getItemRectMax(dst);
    }

    public static imgui.ImVec2 getItemRectSize() {
        return api.getItemRectSize();
    }

    public static void getItemRectSize(final imgui.ImVec2 dst) {
        api.getItemRectSize(dst);
    }

    public static void setItemAllowOverlap() {
        api.setItemAllowOverlap();
    }

    public static imgui.ImGuiViewport getMainViewport() {
        return api.getMainViewport();
    }

    public static boolean isRectVisible(final imgui.ImVec2 size) {
        return api.isRectVisible(size);
    }

    public static boolean isRectVisible(final float sizeX, final float sizeY) {
        return api.isRectVisible(sizeX, sizeY);
    }

    public static boolean isRectVisible(final imgui.ImVec2 rectMin, final imgui.ImVec2 rectMax) {
        return api.isRectVisible(rectMin, rectMax);
    }

    public static boolean isRectVisible(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        return api.isRectVisible(rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    public static double getTime() {
        return api.getTime();
    }

    public static int getFrameCount() {
        return api.getFrameCount();
    }

    public static imgui.ImDrawList getBackgroundDrawList() {
        return api.getBackgroundDrawList();
    }

    public static imgui.ImDrawList getBackgroundDrawList(final imgui.ImGuiViewport viewport) {
        return api.getBackgroundDrawList(viewport);
    }

    public static imgui.ImDrawList getForegroundDrawList() {
        return api.getForegroundDrawList();
    }

    public static imgui.ImDrawList getForegroundDrawList(final imgui.ImGuiViewport viewport) {
        return api.getForegroundDrawList(viewport);
    }

    public static String getStyleColorName(final int idx) {
        return api.getStyleColorName(idx);
    }

    public static void setStateStorage(final imgui.ImGuiStorage storage) {
        api.setStateStorage(storage);
    }

    public static imgui.ImGuiStorage getStateStorage() {
        return api.getStateStorage();
    }

    public static boolean beginChildFrame(final int id, final imgui.ImVec2 size) {
        return api.beginChildFrame(id, size);
    }

    public static boolean beginChildFrame(final int id, final float sizeX, final float sizeY) {
        return api.beginChildFrame(id, sizeX, sizeY);
    }

    public static boolean beginChildFrame(final int id, final imgui.ImVec2 size, final int flags) {
        return api.beginChildFrame(id, size, flags);
    }

    public static boolean beginChildFrame(final int id, final float sizeX, final float sizeY, final int flags) {
        return api.beginChildFrame(id, sizeX, sizeY, flags);
    }

    public static void endChildFrame() {
        api.endChildFrame();
    }

    public static imgui.ImVec2 calcTextSize(final String text) {
        return api.calcTextSize(text);
    }

    public static void calcTextSize(final imgui.ImVec2 dst, final String text) {
        api.calcTextSize(dst, text);
    }

    public static imgui.ImVec2 calcTextSize(final String text, final String textEnd) {
        return api.calcTextSize(text, textEnd);
    }

    public static void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd) {
        api.calcTextSize(dst, text, textEnd);
    }

    public static imgui.ImVec2 calcTextSize(final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        return api.calcTextSize(text, textEnd, hideTextAfterDoubleHash);
    }

    public static void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        api.calcTextSize(dst, text, textEnd, hideTextAfterDoubleHash);
    }

    public static imgui.ImVec2 calcTextSize(final String text, final String textEnd, final float wrapWidth) {
        return api.calcTextSize(text, textEnd, wrapWidth);
    }

    public static void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd, final float wrapWidth) {
        api.calcTextSize(dst, text, textEnd, wrapWidth);
    }

    public static imgui.ImVec2 calcTextSize(final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return api.calcTextSize(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public static void calcTextSize(final imgui.ImVec2 dst, final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        api.calcTextSize(dst, text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public static imgui.ImVec4 colorConvertU32ToFloat4(final int in) {
        return api.colorConvertU32ToFloat4(in);
    }

    public static void colorConvertU32ToFloat4(final imgui.ImVec4 dst, final int in) {
        api.colorConvertU32ToFloat4(dst, in);
    }

    public static int colorConvertFloat4ToU32(final imgui.ImVec4 in) {
        return api.colorConvertFloat4ToU32(in);
    }

    public static int colorConvertFloat4ToU32(final float inX, final float inY, final float inZ, final float inW) {
        return api.colorConvertFloat4ToU32(inX, inY, inZ, inW);
    }

    public static void colorConvertRGBtoHSV(final float[] rgb, final float[] hsv) {
        api.colorConvertRGBtoHSV(rgb, hsv);
    }

    public static void colorConvertHSVtoRGB(final float[] hsv, final float[] rgb) {
        api.colorConvertHSVtoRGB(hsv, rgb);
    }

    public static int getKeyIndex(final int imguiKey) {
        return api.getKeyIndex(imguiKey);
    }

    public static boolean isKeyDown(final int userKeyIndex) {
        return api.isKeyDown(userKeyIndex);
    }

    public static boolean isKeyPressed(final int userKeyIndex) {
        return api.isKeyPressed(userKeyIndex);
    }

    public static boolean isKeyPressed(final int userKeyIndex, final boolean repeat) {
        return api.isKeyPressed(userKeyIndex, repeat);
    }

    public static boolean isKeyReleased(final int userKeyIndex) {
        return api.isKeyReleased(userKeyIndex);
    }

    public static int getKeyPressedAmount(final int keyIndex, final float repeatDelay, final float rate) {
        return api.getKeyPressedAmount(keyIndex, repeatDelay, rate);
    }

    public static void captureKeyboardFromApp() {
        api.captureKeyboardFromApp();
    }

    public static void captureKeyboardFromApp(final boolean wantCaptureKeyboardValue) {
        api.captureKeyboardFromApp(wantCaptureKeyboardValue);
    }

    public static boolean isMouseDown(final int button) {
        return api.isMouseDown(button);
    }

    public static boolean isMouseClicked(final int button) {
        return api.isMouseClicked(button);
    }

    public static boolean isMouseClicked(final int button, final boolean repeat) {
        return api.isMouseClicked(button, repeat);
    }

    public static boolean isMouseReleased(final int button) {
        return api.isMouseReleased(button);
    }

    public static boolean isMouseDoubleClicked(final int button) {
        return api.isMouseDoubleClicked(button);
    }

    public static int getMouseClickedCount(final int button) {
        return api.getMouseClickedCount(button);
    }

    public static boolean isMouseHoveringRect(final imgui.ImVec2 rMin, final imgui.ImVec2 rMax) {
        return api.isMouseHoveringRect(rMin, rMax);
    }

    public static boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        return api.isMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY);
    }

    public static boolean isMouseHoveringRect(final imgui.ImVec2 rMin, final imgui.ImVec2 rMax, final boolean clip) {
        return api.isMouseHoveringRect(rMin, rMax, clip);
    }

    public static boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY, final boolean clip) {
        return api.isMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY, clip);
    }

    public static boolean isMousePosValid(final imgui.ImVec2 mousePos) {
        return api.isMousePosValid(mousePos);
    }

    public static boolean isMousePosValid(final float mousePosX, final float mousePosY) {
        return api.isMousePosValid(mousePosX, mousePosY);
    }

    public static boolean isAnyMouseDown() {
        return api.isAnyMouseDown();
    }

    public static imgui.ImVec2 getMousePos() {
        return api.getMousePos();
    }

    public static void getMousePos(final imgui.ImVec2 dst) {
        api.getMousePos(dst);
    }

    public static imgui.ImVec2 getMousePosOnOpeningCurrentPopup() {
        return api.getMousePosOnOpeningCurrentPopup();
    }

    public static void getMousePosOnOpeningCurrentPopup(final imgui.ImVec2 dst) {
        api.getMousePosOnOpeningCurrentPopup(dst);
    }

    public static boolean isMouseDragging(final int button) {
        return api.isMouseDragging(button);
    }

    public static boolean isMouseDragging(final int button, final float lockThreshold) {
        return api.isMouseDragging(button, lockThreshold);
    }

    public static imgui.ImVec2 getMouseDragDelta() {
        return api.getMouseDragDelta();
    }

    public static void getMouseDragDelta(final imgui.ImVec2 dst) {
        api.getMouseDragDelta(dst);
    }

    public static imgui.ImVec2 getMouseDragDelta(final int button) {
        return api.getMouseDragDelta(button);
    }

    public static void getMouseDragDelta(final imgui.ImVec2 dst, final int button) {
        api.getMouseDragDelta(dst, button);
    }

    public static imgui.ImVec2 getMouseDragDelta(final float lockThreshold) {
        return api.getMouseDragDelta(lockThreshold);
    }

    public static void getMouseDragDelta(final imgui.ImVec2 dst, final float lockThreshold) {
        api.getMouseDragDelta(dst, lockThreshold);
    }

    public static imgui.ImVec2 getMouseDragDelta(final int button, final float lockThreshold) {
        return api.getMouseDragDelta(button, lockThreshold);
    }

    public static void getMouseDragDelta(final imgui.ImVec2 dst, final int button, final float lockThreshold) {
        api.getMouseDragDelta(dst, button, lockThreshold);
    }

    public static void resetMouseDragDelta() {
        api.resetMouseDragDelta();
    }

    public static void resetMouseDragDelta(final int button) {
        api.resetMouseDragDelta(button);
    }

    public static int getMouseCursor() {
        return api.getMouseCursor();
    }

    public static void setMouseCursor(final int cursorType) {
        api.setMouseCursor(cursorType);
    }

    public static void captureMouseFromApp() {
        api.captureMouseFromApp();
    }

    public static void captureMouseFromApp(final boolean wantCaptureMouseValue) {
        api.captureMouseFromApp(wantCaptureMouseValue);
    }

    public static String getClipboardText() {
        return api.getClipboardText();
    }

    public static void setClipboardText(final String text) {
        api.setClipboardText(text);
    }

    public static void loadIniSettingsFromDisk(final String iniFilename) {
        api.loadIniSettingsFromDisk(iniFilename);
    }

    public static void loadIniSettingsFromMemory(final String iniData) {
        api.loadIniSettingsFromMemory(iniData);
    }

    public static void loadIniSettingsFromMemory(final String iniData, final long iniSize) {
        api.loadIniSettingsFromMemory(iniData, iniSize);
    }

    public static void saveIniSettingsToDisk(final String iniFilename) {
        api.saveIniSettingsToDisk(iniFilename);
    }

    public static void saveIniSettingsToMemory() {
        api.saveIniSettingsToMemory();
    }

    public static void saveIniSettingsToMemory(final int outIniSize) {
        api.saveIniSettingsToMemory(outIniSize);
    }

    public static boolean debugCheckVersionAndDataLayout(final String versionStr, final long szIo, final long szStyle, final long szVec2, final long szVec4, final long szDrawvert, final long szDrawidx) {
        return api.debugCheckVersionAndDataLayout(versionStr, szIo, szStyle, szVec2, szVec4, szDrawvert, szDrawidx);
    }

    public static imgui.ImGuiPlatformIO getPlatformIO() {
        return api.getPlatformIO();
    }

    public static void updatePlatformWindows() {
        api.updatePlatformWindows();
    }

    public static void renderPlatformWindowsDefault() {
        api.renderPlatformWindowsDefault();
    }

    public static void destroyPlatformWindows() {
        api.destroyPlatformWindows();
    }

    public static imgui.ImGuiViewport findViewportByID(final int id) {
        return api.findViewportByID(id);
    }

    public static imgui.ImGuiViewport findViewportByPlatformHandle(final long platformHandle) {
        return api.findViewportByPlatformHandle(platformHandle);
    }

    public static int getColorU32i(final int col) {
        return api.getColorU32i(col);
    }

    public static float getWindowPosX() {
        return api.getWindowPosX();
    }

    public static float getWindowPosY() {
        return api.getWindowPosY();
    }

    public static float getWindowSizeX() {
        return api.getWindowSizeX();
    }

    public static float getWindowSizeY() {
        return api.getWindowSizeY();
    }

    public static float getContentRegionAvailX() {
        return api.getContentRegionAvailX();
    }

    public static float getContentRegionAvailY() {
        return api.getContentRegionAvailY();
    }

    public static float getContentRegionMaxX() {
        return api.getContentRegionMaxX();
    }

    public static float getContentRegionMaxY() {
        return api.getContentRegionMaxY();
    }

    public static float getWindowContentRegionMinX() {
        return api.getWindowContentRegionMinX();
    }

    public static float getWindowContentRegionMinY() {
        return api.getWindowContentRegionMinY();
    }

    public static float getWindowContentRegionMaxX() {
        return api.getWindowContentRegionMaxX();
    }

    public static float getWindowContentRegionMaxY() {
        return api.getWindowContentRegionMaxY();
    }

    public static float getFontTexUvWhitePixelX() {
        return api.getFontTexUvWhitePixelX();
    }

    public static float getFontTexUvWhitePixelY() {
        return api.getFontTexUvWhitePixelY();
    }

    public static float getStyleColorVec4X(final int idx) {
        return api.getStyleColorVec4X(idx);
    }

    public static float getStyleColorVec4Y(final int idx) {
        return api.getStyleColorVec4Y(idx);
    }

    public static float getStyleColorVec4Z(final int idx) {
        return api.getStyleColorVec4Z(idx);
    }

    public static float getStyleColorVec4W(final int idx) {
        return api.getStyleColorVec4W(idx);
    }

    public static float getCursorStartPosX() {
        return api.getCursorStartPosX();
    }

    public static float getCursorStartPosY() {
        return api.getCursorStartPosY();
    }

    public static float getCursorScreenPosX() {
        return api.getCursorScreenPosX();
    }

    public static float getCursorScreenPosY() {
        return api.getCursorScreenPosY();
    }

    public static float getItemRectMinX() {
        return api.getItemRectMinX();
    }

    public static float getItemRectMinY() {
        return api.getItemRectMinY();
    }

    public static float getItemRectMaxX() {
        return api.getItemRectMaxX();
    }

    public static float getItemRectMaxY() {
        return api.getItemRectMaxY();
    }

    public static float getItemRectSizeX() {
        return api.getItemRectSizeX();
    }

    public static float getItemRectSizeY() {
        return api.getItemRectSizeY();
    }

    public static float calcTextSizeX(final String text) {
        return api.calcTextSizeX(text);
    }

    public static float calcTextSizeY(final String text) {
        return api.calcTextSizeY(text);
    }

    public static float calcTextSizeX(final String text, final String textEnd) {
        return api.calcTextSizeX(text, textEnd);
    }

    public static float calcTextSizeY(final String text, final String textEnd) {
        return api.calcTextSizeY(text, textEnd);
    }

    public static float calcTextSizeX(final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        return api.calcTextSizeX(text, textEnd, hideTextAfterDoubleHash);
    }

    public static float calcTextSizeY(final String text, final String textEnd, final boolean hideTextAfterDoubleHash) {
        return api.calcTextSizeY(text, textEnd, hideTextAfterDoubleHash);
    }

    public static float calcTextSizeX(final String text, final String textEnd, final float wrapWidth) {
        return api.calcTextSizeX(text, textEnd, wrapWidth);
    }

    public static float calcTextSizeY(final String text, final String textEnd, final float wrapWidth) {
        return api.calcTextSizeY(text, textEnd, wrapWidth);
    }

    public static float calcTextSizeX(final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return api.calcTextSizeX(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public static float calcTextSizeY(final String text, final String textEnd, final boolean hideTextAfterDoubleHash, final float wrapWidth) {
        return api.calcTextSizeY(text, textEnd, hideTextAfterDoubleHash, wrapWidth);
    }

    public static float colorConvertU32ToFloat4X(final int in) {
        return api.colorConvertU32ToFloat4X(in);
    }

    public static float colorConvertU32ToFloat4Y(final int in) {
        return api.colorConvertU32ToFloat4Y(in);
    }

    public static float colorConvertU32ToFloat4Z(final int in) {
        return api.colorConvertU32ToFloat4Z(in);
    }

    public static float colorConvertU32ToFloat4W(final int in) {
        return api.colorConvertU32ToFloat4W(in);
    }

    public static float getMousePosX() {
        return api.getMousePosX();
    }

    public static float getMousePosY() {
        return api.getMousePosY();
    }

    public static float getMousePosOnOpeningCurrentPopupX() {
        return api.getMousePosOnOpeningCurrentPopupX();
    }

    public static float getMousePosOnOpeningCurrentPopupY() {
        return api.getMousePosOnOpeningCurrentPopupY();
    }

    public static float getMouseDragDeltaX() {
        return api.getMouseDragDeltaX();
    }

    public static float getMouseDragDeltaY() {
        return api.getMouseDragDeltaY();
    }

    public static float getMouseDragDeltaX(final int button) {
        return api.getMouseDragDeltaX(button);
    }

    public static float getMouseDragDeltaY(final int button) {
        return api.getMouseDragDeltaY(button);
    }

    public static float getMouseDragDeltaX(final float lockThreshold) {
        return api.getMouseDragDeltaX(lockThreshold);
    }

    public static float getMouseDragDeltaY(final float lockThreshold) {
        return api.getMouseDragDeltaY(lockThreshold);
    }

    public static float getMouseDragDeltaX(final int button, final float lockThreshold) {
        return api.getMouseDragDeltaX(button, lockThreshold);
    }

    public static float getMouseDragDeltaY(final int button, final float lockThreshold) {
        return api.getMouseDragDeltaY(button, lockThreshold);
    }
    // GENERATED API: END
}
