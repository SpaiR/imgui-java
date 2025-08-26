package imgui;

import imgui.assertion.ImAssertCallback;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.ArgVariant;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.callback.ImGuiInputTextCallback;
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

@BindingSource
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

    @BindingMethod
    public static native ImGuiContext CreateContext(@OptArg ImFontAtlas sharedFontAtlas);

    @BindingMethod
    public static native void DestroyContext(@OptArg ImGuiContext ctx);

    @BindingMethod
    public static native ImGuiContext GetCurrentContext();

    @BindingMethod
    public static native void SetCurrentContext(ImGuiContext ctx);

    // Main

    /**
     * Access the IO structure (mouse/keyboard/gamepad inputs, time, various configuration options/flags).
     */
    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native ImGuiIO GetIO();

    /**
     * Access the Style structure (colors, sizes). Always use PushStyleColor(), PushStyleVar() to modify style mid-frame!
     */
    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native ImGuiStyle GetStyle();

    /**
     * Start a new Dear ImGui frame, you can submit any command from this point until Render()/EndFrame().
     */
    @BindingMethod
    public static native void NewFrame();

    /**
     * Ends the Dear ImGui frame. automatically called by Render(). If you don't need to render data (skipping rendering) you may call EndFrame() without
     * Render()... but you'll have wasted CPU already! If you don't need to render, better to not create any windows and not call NewFrame() at all!
     */
    @BindingMethod
    public static native void EndFrame();

    /**
     * Ends the Dear ImGui frame, finalize the draw data. You can then get call GetDrawData().
     */
    @BindingMethod
    public static native void Render();

    /**
     * Valid after Render() and until the next call to NewFrame(). this is what you have to render.
     */
    @BindingMethod
    @ReturnValue(isStatic = true)
    public static native ImDrawData GetDrawData();

    // Demo, Debug, Information

    /**
     * Create Demo window. Demonstrate most ImGui features. Call this to learn about the library!
     */
    @BindingMethod
    public static native void ShowDemoWindow(@OptArg ImBoolean pOpen);

    /**
     * Create Metrics/Debugger window. display Dear ImGui internals: windows, draw commands, various internal state, etc.
     */
    @BindingMethod
    public static native void ShowMetricsWindow(@OptArg ImBoolean pOpen);

    /**
     * Create Debug Log window. display a simplified log of important dear imgui events.
     */
    @BindingMethod
    public static native void ShowDebugLogWindow(@OptArg ImBoolean pOpen);

    /**
     * Create Stack Tool window. hover items with mouse to query information about the source of their unique ID.
     */
    @BindingMethod
    public static native void ShowIDStackToolWindow(@OptArg ImBoolean pOpen);

    /**
     * Create About window. display Dear ImGui version, credits and build/system information.
     */
    @BindingMethod
    public static native void ShowAboutWindow(@OptArg ImBoolean pOpen);

    /**
     * Add style editor block (not a window).
     * You can pass in a reference ImGuiStyle structure to compare to, revert to and save to (else it uses the default style)
     */
    @BindingMethod
    public static native void ShowStyleEditor(@OptArg ImGuiStyle ref);

    /**
     * Add style selector block (not a window), essentially a combo listing the default styles.
     */
    @BindingMethod
    public static native boolean ShowStyleSelector(String label);

    /**
     * Add font selector block (not a window), essentially a combo listing the loaded fonts.
     */
    @BindingMethod
    public static native void ShowFontSelector(String label);

    /**
     * Add basic help/info block (not a window): how to manipulate ImGui as an end-user (mouse/keyboard controls).
     */
    @BindingMethod
    public static native void ShowUserGuide();

    /**
     * Get the compiled version string e.g. "1.80 WIP" (essentially the value for IMGUI_VERSION from the compiled version of imgui.cpp)
     */
    @BindingMethod
    public static native String GetVersion();

    // Styles

    /**
     * New, recommended style (default)
     */
    @BindingMethod
    public static native void StyleColorsDark(@OptArg ImGuiStyle style);

    /**
     * Best used with borders and a custom, thicker font
     */
    @BindingMethod
    public static native void StyleColorsLight(@OptArg ImGuiStyle style);

    /**
     * Classic imgui style
     */
    @BindingMethod
    public static native void StyleColorsClassic(@OptArg ImGuiStyle style);

    // Windows
    // - Begin() = push window to the stack and start appending to it. End() = pop window from the stack.
    // - Passing 'bool* p_open != NULL' shows a window-closing widget in the upper-right corner of the window,
    //   which clicking will set the boolean to false when clicked.
    // - You may append multiple times to the same window during the same frame by calling Begin()/End() pairs multiple times.
    //   Some information such as 'flags' or 'p_open' will only be considered by the first call to Begin().
    // - Begin() return false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting
    //   anything to the window. Always call a matching End() for each Begin() call, regardless of its return value!
    //   [Important: due to legacy reason, Begin/End and BeginChild/EndChild are inconsistent with all other functions
    //    such as BeginMenu/EndMenu, BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding
    //    BeginXXX function returned true. Begin and BeginChild are the only odd ones out. Will be fixed in a future update.]
    // - Note that the bottom of window stack always contains a window called "Debug".

    @BindingMethod
    public static native boolean Begin(String title, @OptArg(callValue = "NULL") ImBoolean pOpen, @OptArg int imGuiWindowFlags);

    @BindingMethod
    public static native void End();

    // Child Windows
    // - Use child windows to begin into a self-contained independent scrolling/clipping regions within a host window. Child windows can embed their own child.
    // - Before 1.90 (November 2023), the "ImGuiChildFlags child_flags = 0" parameter was "bool border = false".
    //   This API is backward compatible with old code, as we guarantee that ImGuiChildFlags_Border == true.
    //   Consider updating your old code:
    //      BeginChild("Name", size, false)   -> Begin("Name", size, 0); or Begin("Name", size, ImGuiChildFlags_None);
    //      BeginChild("Name", size, true)    -> Begin("Name", size, ImGuiChildFlags_Border);
    // - Manual sizing (each axis can use a different setting e.g. ImVec2(0.0f, 400.0f)):
    //     == 0.0f: use remaining parent window size for this axis.
    //      > 0.0f: use specified size for this axis.
    //      < 0.0f: right/bottom-align to specified distance from available content boundaries.
    // - Specifying ImGuiChildFlags_AutoResizeX or ImGuiChildFlags_AutoResizeY makes the sizing automatic based on child contents.
    //   Combining both ImGuiChildFlags_AutoResizeX _and_ ImGuiChildFlags_AutoResizeY defeats purpose of a scrolling region and is NOT recommended.
    // - BeginChild() returns false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting
    //   anything to the window. Always call a matching EndChild() for each BeginChild() call, regardless of its return value.
    //   [Important: due to legacy reason, Begin/End and BeginChild/EndChild are inconsistent with all other functions
    //    such as BeginMenu/EndMenu, BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding
    //    BeginXXX function returned true. Begin and BeginChild are the only odd ones out. Will be fixed in a future update.]

    @BindingMethod
    public static native boolean BeginChild(String strId, @OptArg(callValue = "ImVec2(0,0)") ImVec2 size, @OptArg(callValue = "0") int childFlags, @OptArg int windowFlags);

    @BindingMethod
    public static native boolean BeginChild(int id, @OptArg(callValue = "ImVec2(0,0)") ImVec2 size, @OptArg(callValue = "0") int childFlags, @OptArg int windowFlags);

    @BindingMethod
    public static native boolean BeginChild(String strId, ImVec2 size, @ArgValue(staticCast = "ImGuiChildFlags") boolean border, @OptArg int windowFlags);

    /**
     * @deprecated Use {@link #beginChild(String, ImVec2, int, int)} instead.
     */
    @Deprecated
    @BindingMethod
    public static native boolean BeginChild(int id, ImVec2 size, @ArgValue(staticCast = "ImGuiChildFlags") boolean border, @OptArg int windowFlags);

    @BindingMethod
    public static native void EndChild();

    // Windows Utilities
    // - "current window" = the window we are appending into while inside a Begin()/End() block. "next window" = next window we will Begin() into.

    @BindingMethod
    public static native boolean IsWindowAppearing();

    @BindingMethod
    public static native boolean IsWindowCollapsed();

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    @BindingMethod
    public static native boolean IsWindowFocused(@OptArg int imGuiFocusedFlags);

    /**
     * Is current window hovered and hoverable (e.g. not blocked by a popup/modal)? See ImGuiHoveredFlags_ for options.
     * IMPORTANT: If you are trying to check whether your mouse should be dispatched to Dear ImGui or to your underlying app,
     * you should not use this function! Use the 'io.WantCaptureMouse' boolean for that!
     * Refer to FAQ entry "How can I tell whether to dispatch mouse/keyboard to Dear ImGui or my application?" for details.
     */
    @BindingMethod
    public static native boolean IsWindowHovered(@OptArg int imGuiHoveredFlags);

    /**
     * Get draw list associated to the current window, to append your own drawing primitives
     */
    @BindingMethod
    public static native ImDrawList GetWindowDrawList();

    /**
     * Get DPI scale currently associated to the current window's viewport.
     */
    @BindingMethod
    public static native float GetWindowDpiScale();

    /**
     * Get current window position in screen space (note: it is unlikely you need to use this.
     * Consider using current layout pos instead, GetCursorScreenPos())
     */
    @BindingMethod
    public static native ImVec2 GetWindowPos();

    /**
     * Get current window size (note: it is unlikely you need to use this. Consider using GetCursorScreenPos() and e.g. GetContentRegionAvail() instead)
     */
    @BindingMethod
    public static native ImVec2 GetWindowSize();

    /**
     * Get current window width (shortcut for GetWindowSize().x)
     */
    @BindingMethod
    public static native float GetWindowWidth();

    /**
     * Get current window height (shortcut for GetWindowSize().y)
     */
    @BindingMethod
    public static native float GetWindowHeight();

    /**
     * Get viewport currently associated to the current window.
     */
    @BindingMethod
    public static native ImGuiViewport GetWindowViewport();

    // Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    @BindingMethod
    public static native void SetNextWindowPos(ImVec2 pos, @OptArg(callValue = "ImGuiCond_None") int cond, @OptArg ImVec2 pivot);

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    @BindingMethod
    public static native void SetNextWindowSize(ImVec2 size, @OptArg int cond);

    /**
     * Set next window size limits. use 0.0f or FLT_MAX if you don't want limits. Use -1 for both min and max of same axis to preserve current size (which itself is a constraint).
     * Use callback to apply non-trivial programmatic constraints.
     */
    @BindingMethod
    public static native void SetNextWindowSizeConstraints(ImVec2 sizeMin, ImVec2 sizeMax);

    /**
     * Set next window content size (~ scrollable client area, which enforce the range of scrollbars).
     * Not including window decorations (title bar, menu bar, etc.) nor WindowPadding. set an axis to 0.0f to leave it automatic. call before Begin()
     */
    @BindingMethod
    public static native void SetNextWindowContentSize(ImVec2 size);

    /**
     * Set next window collapsed state. call before Begin()
     */
    @BindingMethod
    public static native void SetNextWindowCollapsed(boolean collapsed, @OptArg int cond);

    /**
     * Set next window to be focused / top-most. call before Begin()
     */
    @BindingMethod
    public static native void SetNextWindowFocus();

    /**
     * Set next window scrolling value (use {@code < 0.0f} to not affect a given axis).
     */
    @BindingMethod
    public static native void SetNextWindowScroll(ImVec2 scroll);

    /**
     * Set next window background color alpha. helper to easily override the Alpha component of ImGuiCol_WindowBg/ChildBg/PopupBg.
     * You may also use ImGuiWindowFlags_NoBackground.
     */
    @BindingMethod
    public static native void SetNextWindowBgAlpha(float alpha);

    /**
     * Set next window viewport.
     */
    @BindingMethod
    public static native void SetNextWindowViewport(int viewportId);

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    @BindingMethod
    public static native void SetWindowPos(ImVec2 pos, @OptArg int cond);

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    @BindingMethod
    public static native void SetWindowSize(ImVec2 size, @OptArg int cond);

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    @BindingMethod
    public static native void SetWindowCollapsed(boolean collapsed, @OptArg int cond);

    /**
     * (not recommended) set current window to be focused / top-most. prefer using SetNextWindowFocus().
     */
    @BindingMethod
    public static native void SetWindowFocus();

    /**
     * Set font scale. Adjust IO.FontGlobalScale if you want to scale all windows.
     * This is an old API! For correct scaling, prefer to reload font + rebuild ImFontAtlas + call style.ScaleAllSizes().
     */
    @BindingMethod
    public static native void SetWindowFontScale(float scale);

    /**
     * Set named window position.
     */
    @BindingMethod
    public static native void SetWindowPos(String name, ImVec2 pos, @OptArg int cond);

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    @BindingMethod
    public static native void SetWindowSize(String name, ImVec2 size, @OptArg int cond);

    /**
     * Set named window collapsed state
     */
    @BindingMethod
    public static native void SetWindowCollapsed(@ArgValue(callPrefix = "(const char *)") String name, @ArgValue(callPrefix = "(bool)") boolean collapsed, @OptArg int cond);

    /**
     * Set named window to be focused / top-most. Use NULL to remove focus.
     */
    @BindingMethod
    public static native void SetWindowFocus(String name);

    // Content region
    // - Retrieve available space from a given point. GetContentRegionAvail() is frequently useful.
    // - Those functions are bound to be redesigned (they are confusing, incomplete and the Min/Max return values are in local window coordinates which increases confusion)

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    @BindingMethod
    public static native ImVec2 GetContentRegionAvail();

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    @BindingMethod
    public static native ImVec2 GetContentRegionMax();

    /**
     * Content boundaries min for the full window (roughly (0,0)-Scroll), in window coordinates
     */
    @BindingMethod
    public static native ImVec2 GetWindowContentRegionMin();

    /**
     * Content boundaries max for the full window (roughly (0,0)+Size-Scroll) where Size can be overridden with SetNextWindowContentSize(), in window coordinates
     */
    @BindingMethod
    public static native ImVec2 GetWindowContentRegionMax();

    // Windows Scrolling
    // - Any change of Scroll will be applied at the beginning of next frame in the first call to Begin().
    // - You may instead use SetNextWindowScroll() prior to calling Begin() to avoid this delay, as an alternative to using SetScrollX()/SetScrollY().

    /**
     * Get scrolling amount [0 .. GetScrollMaxX()]
     */
    @BindingMethod
    public static native float GetScrollX();

    /**
     * Get scrolling amount [0 .. GetScrollMaxY()]
     */
    @BindingMethod
    public static native float GetScrollY();

    /**
     * Set scrolling amount [0 .. GetScrollMaxX()]
     */
    @BindingMethod
    public static native void SetScrollX(float scrollX);

    /**
     * Set scrolling amount [0..GetScrollMaxY()]
     */
    @BindingMethod
    public static native void SetScrollY(float scrollY);

    /**
     * Get maximum scrolling amount ~~ ContentSize.x - WindowSize.x - DecorationsSize.x
     */
    @BindingMethod
    public static native float GetScrollMaxX();

    /**
     * Get maximum scrolling amount ~~ ContentSize.y - WindowSize.y - DecorationsSize.y
     */
    @BindingMethod
    public static native float GetScrollMaxY();

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    @BindingMethod
    public static native void SetScrollHereX(@OptArg float centerXRatio);

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    @BindingMethod
    public static native void SetScrollHereY(@OptArg float centerYRatio);

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    @BindingMethod
    public static native void SetScrollFromPosX(float localX, @OptArg float centerXRatio);

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    @BindingMethod
    public static native void SetScrollFromPosY(float localY, @OptArg float centerYRatio);

    // Parameters stacks (shared)

    @BindingMethod
    public static native void PushFont(ImFont font);

    @BindingMethod
    public static native void PopFont();

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    public static native void pushStyleColor(int imGuiCol, int r, int g, int b, int a); /*
        ImGui::PushStyleColor(imGuiCol, (ImU32)ImColor((int)r, (int)g, (int)b, (int)a));
    */

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    @BindingMethod
    public static native void PushStyleColor(final int imGuiCol, final ImVec4 col);

    /**
     * Modify a style color. always use this if you modify the style after NewFrame().
     */
    @BindingMethod
    public static native void PushStyleColor(int imGuiCol, int col);

    @BindingMethod
    public static native void PopStyleColor(@OptArg int count);

    /**
     * Modify a style float variable. always use this if you modify the style after NewFrame().
     */
    @BindingMethod
    public static native void PushStyleVar(int imGuiStyleVar, float val);

    /**
     * Modify a style ImVec2 variable. always use this if you modify the style after NewFrame().
     */
    @BindingMethod
    public static native void PushStyleVar(int imGuiStyleVar, ImVec2 val);

    @BindingMethod
    public static native void PopStyleVar(@OptArg int count);

    /**
     * Tab stop enable. Allow focusing using TAB/Shift-TAB, enabled by default but you can disable it for certain widgets
     */
    @BindingMethod
    public static native void PushTabStop(boolean tabStop);

    @BindingMethod
    public static native void PopTabStop();

    /**
     * In 'repeat' mode, Button*() functions return repeated true in a typematic manner (using io.KeyRepeatDelay/io.KeyRepeatRate setting).
     * Note that you can call IsItemActive() after any Button() to tell if the button is held in the current frame.
     */
    @BindingMethod
    public static native void PushButtonRepeat(boolean repeat);

    @BindingMethod
    public static native void PopButtonRepeat();

    // Parameters stacks (current window)

    /**
     * Push width of items for common large "item+label" widgets. {@code > 0.0f}: width in pixels,
     * {@code <0.0f} align xx pixels to the right of window (so -1.0f always align width to the right side).
     */
    @BindingMethod
    public static native void PushItemWidth(float itemWidth);

    @BindingMethod
    public static native void PopItemWidth();

    /**
     * Set width of the _next_ common large "item+label" widget. {@code > 0.0f}: width in pixels,
     * {@code <0.0f} align xx pixels to the right of window (so -1.0f always align width to the right side)
     */
    @BindingMethod
    public static native void SetNextItemWidth(float itemWidth);

    /**
     * Width of item given pushed settings and current cursor position. NOT necessarily the width of last item unlike most 'Item' functions.
     */
    @BindingMethod
    public static native float CalcItemWidth();

    /**
     * Push Word-wrapping positions for Text*() commands. {@code < 0.0f}: no wrapping; 0.0f: wrap to end of window (or column); {@code > 0.0f}: wrap at
     * 'wrap_posX' position in window local space
     */
    @BindingMethod
    public static native void PushTextWrapPos(@OptArg float wrapLocalPosX);

    @BindingMethod
    public static native void PopTextWrapPos();

    // Style read access
    // - Use the ShowStyleEditor() function to interactively see/edit the colors.

    /**
     * Get current font.
     */
    @BindingMethod
    @ReturnValue(isStatic = true)
    public static native ImFont GetFont();

    /**
     * Get current font size (= height in pixels) of current font with current scale applied
     */
    @BindingMethod
    public static native int GetFontSize();

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    @BindingMethod
    public static native ImVec2 GetFontTexUvWhitePixel();

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier, packed as a 32-bit value suitable for ImDrawList.
     */
    @BindingMethod
    public static native int GetColorU32(@ArgValue(staticCast = "ImGuiCol") int idx, @OptArg float alphaMul);

    /**
     * Retrieve given color with style alpha applied, packed as a 32-bit value suitable for ImDrawList.
     */
    @BindingMethod
    public static native int GetColorU32(ImVec4 col);

    /**
     * Retrieve given color with style alpha applied, packed as a 32-bit value suitable for ImDrawList.
     */
    @BindingMethod(callName = "GetColorU32")
    public static native int GetColorU32i(@ArgValue(staticCast = "ImU32") int col, @OptArg float alphaMul);

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    @BindingMethod
    public static native ImVec4 GetStyleColorVec4(int imGuiColIdx);

    // Layout cursor positioning
    // - By "cursor" we mean the current output position.
    // - The typical widget behavior is to output themselves at the current cursor position, then move the cursor one line down.
    // - You can call SameLine() between widgets to undo the last carriage return and output at the right of the preceding widget.
    // - Attention! We currently have inconsistencies between window-local and absolute positions we will aim to fix with future API:
    //    - Absolute coordinate:        GetCursorScreenPos(), SetCursorScreenPos(), all ImDrawList:: functions. -> this is the preferred way forward.
    //    - Window-local coordinates:   SameLine(), GetCursorPos(), SetCursorPos(), GetCursorStartPos(), GetContentRegionMax(), GetWindowContentRegion*(), PushTextWrapPos()
    // - GetCursorScreenPos() = GetCursorPos() + GetWindowPos(). GetWindowPos() is almost only ever useful to convert from window-local to absolute coordinates.

    /**
     * Cursor position in absolute coordinates (prefer using this, also more useful to work with ImDrawList API).
     */
    @BindingMethod
    public static native ImVec2 GetCursorScreenPos();

    /**
     * Cursor position in absolute coordinates.
     */
    @BindingMethod
    public static native void SetCursorScreenPos(ImVec2 pos);

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    @BindingMethod
    public static native ImVec2 GetCursorPos();

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    @BindingMethod
    public static native void SetCursorPos(ImVec2 localPos);

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    @BindingMethod
    public static native void SetCursorPosX(float localX);

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    @BindingMethod
    public static native void SetCursorPosY(float localY);

    /**
     * Initial cursor position in window coordinates
     */
    @BindingMethod
    public static native ImVec2 GetCursorStartPos();

    // Other layout functions

    /**
     * Separator, generally horizontal. inside a menu bar or in horizontal layout mode, this becomes a vertical separator.
     */
    @BindingMethod
    public static native void Separator();

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    @BindingMethod
    public static native void SameLine(@OptArg float offsetFromStartX, @OptArg float spacing);

    /**
     * Undo a SameLine() or force a new line when in a horizontal-layout context.
     */
    @BindingMethod
    public static native void NewLine();

    /**
     * Add vertical spacing.
     */
    @BindingMethod
    public static native void Spacing();

    /**
     * Add a dummy item of given size. unlike InvisibleButton(), Dummy() won't take the mouse click or be navigable into.
     */
    @BindingMethod
    public static native void Dummy(ImVec2 size);

    /**
     * Move content position toward the right, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    @BindingMethod
    public static native void Indent(@OptArg float indentW);

    /**
     * Move content position back to the left, by indent_w, or style.IndentSpacing if indent_w {@code <= 0}.
     */
    @BindingMethod
    public static native void Unindent(@OptArg float indentW);

    /**
     * Lock horizontal starting position
     */
    @BindingMethod
    public static native void BeginGroup();

    /**
     * Unlock horizontal starting position + capture the whole group bounding box into one "item" (so you can use IsItemHovered() or layout primitives such as SameLine() on whole group, etc.)
     */
    @BindingMethod
    public static native void EndGroup();

    // (some functions are using window-relative coordinates, such as: GetCursorPos, GetCursorStartPos, GetContentRegionMax, GetWindowContentRegion* etc.
    //  other functions such as GetCursorScreenPos or everything in ImDrawList::
    //  are using the main, absolute coordinate system.
    //  GetWindowPos() + GetCursorPos() == GetCursorScreenPos() etc.)

    /**
     * Vertically align upcoming text baseline to FramePadding.y so that it will align properly to regularly framed items (call if you have text on a line before a framed item)
     */
    @BindingMethod
    public static native void AlignTextToFramePadding();

    /**
     * ~ FontSize
     */
    @BindingMethod
    public static native float GetTextLineHeight();

    /**
     * ~ FontSize + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of text)
     */
    @BindingMethod
    public static native float GetTextLineHeightWithSpacing();

    /**
     * ~ FontSize + style.FramePadding.y * 2
     */
    @BindingMethod
    public static native float GetFrameHeight();

    /**
     * ~ FontSize + style.FramePadding.y * 2 + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of framed widgets)
     */
    @BindingMethod
    public static native float GetFrameHeightWithSpacing();

    // ID stack/scopes
    // Read the FAQ (docs/FAQ.md or http://dearimgui.com/faq) for more details about how ID are handled in dear imgui.
    // - Those questions are answered and impacted by understanding of the ID stack system:
    //   - "Q: Why is my widget not reacting when I click on it?"
    //   - "Q: How can I have widgets with an empty label?"
    //   - "Q: How can I have multiple widgets with the same label?"
    // - Short version: ID are hashes of the entire ID stack. If you are creating widgets in a loop you most likely
    //   want to push a unique identifier (e.g. object pointer, loop index) to uniquely differentiate them.
    // - You can also use the "Label##foobar" syntax within widget label to distinguish them from each others.
    // - In this header file we use the "label"/"name" terminology to denote a string that will be displayed + used as an ID,
    //   whereas "str_id" denote a string that is only used as an ID and not normally displayed.

    /**
     * Push string into the ID stack (will hash string).
     */
    @BindingMethod
    public static native void PushID(String strId);

    /**
     * Push string into the ID stack (will hash string).
     */
    @BindingMethod
    public static native void PushID(String strIdBegin, String strIdEnd);

    /**
     * Push pointer into the ID stack (will hash pointer).
     */
    @BindingMethod
    public static native void PushID(@ArgValue(callPrefix = "(void*)") long ptrId);

    /**
     * Push integer into the ID stack (will hash integer).
     */
    @BindingMethod
    public static native void PushID(int intId);

    /**
     * Pop from the ID stack.
     */
    @BindingMethod
    public static native void PopID();

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    @BindingMethod
    public static native int GetID(String strId);

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    @BindingMethod
    public static native int GetID(String strIdBegin, String strIdEnd);

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    @BindingMethod
    public static native int GetID(@ArgValue(callPrefix = "(void*)") long ptrId);

    // Widgets: Text

    /**
     * Raw text without formatting. Roughly equivalent to Text("%s", text) but:
     * A) doesn't require null terminated string if 'textEnd' is specified,
     * B) it's faster, no memory copy is done, no buffer size limits, recommended for long chunks of text.
     */
    @BindingMethod
    public static native void TextUnformatted(String text, @OptArg String textEnd);

    /**
     * Formatted text
     */
    @BindingMethod
    public static native void Text(String text, Void NULL);

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    @BindingMethod
    public static native void TextColored(ImVec4 col, String text, Void NULL);

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
    @BindingMethod
    public static native void TextDisabled(String text, Void NULL);

    /**
     * Shortcut for PushTextWrapPos(0.0f); Text(fmt, ...); PopTextWrapPos();.
     * Note that this won't work on an auto-resizing window if there's no other widgets to extend the window width,
     * yoy may need to set a size using SetNextWindowSize().
     */
    @BindingMethod
    public static native void TextWrapped(String text, Void NULL);

    /**
     * Display text+label aligned the same way as value+label widgets
     */
    @BindingMethod
    public static native void LabelText(String label, String text, Void NULL);

    /**
     * Shortcut for Bullet()+Text()
     */
    @BindingMethod
    public static native void BulletText(String text, Void NULL);

    /**
     * Currently: formatted text with an horizontal line
     */
    @BindingMethod
    public static native void SeparatorText(String label);

    // Widgets: Main
    // - Most widgets return true when the value has been changed or when pressed/selected
    // - You may also use one of the many IsItemXXX functions (e.g. IsItemActive, IsItemHovered, etc.) to query widget state.

    /**
     * Button
     */
    @BindingMethod
    public static native boolean Button(String label, @OptArg ImVec2 size);

    /**
     * Button with (FramePadding.y == 0) to easily embed within text
     */
    @BindingMethod
    public static native boolean SmallButton(String label);

    /**
     * Flexible button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    @BindingMethod
    public static native boolean InvisibleButton(String strId, ImVec2 size, @OptArg int imGuiButtonFlags);

    /**
     * Square button with an arrow shape
     */
    @BindingMethod
    public static native boolean ArrowButton(String strId, @ArgValue(staticCast = "ImGuiDir") int dir);

    public static boolean checkbox(String label, boolean active) {
        return nCheckbox(label, active);
    }

    private static native boolean nCheckbox(String label, boolean active); /*
        bool flag = (bool)active;
        return ImGui::Checkbox(label, &flag);
    */

    @BindingMethod
    public static native boolean Checkbox(String label, ImBoolean data);

    @BindingMethod
    public static native boolean CheckboxFlags(String label, ImInt flags, int flagsValue);

    /**
     * Use with e.g. if (RadioButton("one", my_value==1)) { my_value = 1; }
     */
    @BindingMethod
    public static native boolean RadioButton(String label, boolean active);

    /**
     * Shortcut to handle the above pattern when value is an integer
     */
    @BindingMethod
    public static native boolean RadioButton(String label, ImInt v, int vButton);

    @BindingMethod
    public static native void ProgressBar(float fraction, @OptArg(callValue = "ImVec2(-FLT_MIN,0)") ImVec2 size, @OptArg String overlay);

    /**
     * Draw a small circle + keep the cursor on the same line. advance cursor x position by GetTreeNodeToLabelSpacing(), same distance that TreeNode() uses
     */
    @BindingMethod
    public static native void Bullet();

    // Widgets: Images
    // - Read about ImTextureID here: https://github.com/ocornut/imgui/wiki/Image-Loading-and-Displaying-Examples

    @BindingMethod
    public static native void Image(@ArgValue(callPrefix = "(ImTextureID)(uintptr_t)") long userTextureId, ImVec2 imageSize, @OptArg ImVec2 uv0, @OptArg ImVec2 uv1, @OptArg ImVec4 tintCol, @OptArg ImVec4 borderCol);

    @BindingMethod
    public static native boolean ImageButton(String strId, @ArgValue(callPrefix = "(ImTextureID)(uintptr_t)") long userTextureId, ImVec2 imageSize, @OptArg ImVec2 uv0, @OptArg ImVec2 uv1, @OptArg ImVec4 bgCol, @OptArg ImVec4 tintCol);


    // Widgets: Combo Box (Dropdown)
    // - The BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
    // - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

    @BindingMethod
    public static native boolean BeginCombo(String label, String previewValue, @OptArg int imGuiComboFlags);

    /**
     * Only call EndCombo() if BeginCombo() returns true!
     */
    @BindingMethod
    public static native void EndCombo();

    @BindingMethod
    public static native boolean Combo(String label, ImInt currentItem, String[] items, Void itemsCount, @OptArg int popupMaxHeightInItems);

    /**
     * Separate items with \0 within a string, end item-list with \0\0. e.g. "One\0Two\0Three\0"
     */
    @BindingMethod
    public static native boolean Combo(String label, ImInt currentItem, String itemsSeparatedByZeros, @OptArg int popupMaxHeightInItems);

    // Widgets: Drag Sliders
    // - CTRL+Click on any drag box to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - For all the Float2/Float3/Float4/Int2/Int3/Int4 versions of every function, note that a 'float v[X]' function argument is the same as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible. You can pass address of your first element out of a contiguous set, e.g. &myvector.x
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Format string may also be set to NULL or use the default format ("%f" or "%d").
    // - Speed are per-pixel of mouse movement (vSpeed=0.2f: mouse needs to move by 5 pixels to increase value by 1). For gamepad/keyboard navigation, minimum speed is Max(vSpeed, minimum_step_at_given_precision).
    // - Use vMin < vMax to clamp edits to given limits. Note that CTRL+Click manual input can override those limits.
    // - Use v_max = FLT_MAX / INT_MAX etc to avoid clamping to a maximum, same with v_min = -FLT_MAX / INT_MIN to avoid clamping to a minimum.
    // - Use vMin > vMax to lock edits.
    // - We use the same sets of flags for DragXXX() and SliderXXX() functions as the features are the same and it makes it easier to swap them.
    // - Legacy: Pre-1.78 there are DragXXX() function signatures that take a final `float power=1.0f' argument instead of the `ImGuiSliderFlags flags=0' argument.
    //   If you get a warning converting a float to ImGuiSliderFlags, read https://github.com/ocornut/imgui/issues/3361

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    @BindingMethod
    public static native boolean DragFloat(String label, float[] v, @OptArg float vSpeed, @OptArg float vMin, @OptArg float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragFloat2(String label, float[] v, @OptArg float vSpeed, @OptArg float vMin, @OptArg float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragFloat3(String label, float[] v, @OptArg float vSpeed, @OptArg float vMin, @OptArg float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragFloat4(String label, float[] v, @OptArg float vSpeed, @OptArg float vMin, @OptArg float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragFloatRange2(String label, float[] vCurrentMin, float[] vCurrentMax, float vSpeed, @OptArg float vMin, @OptArg float vMax, @OptArg String format, @OptArg String formatMax, @OptArg int imGuiSliderFlags);

    /**
     * If {@code vMin >= vMax} we have no bound
     */
    @BindingMethod
    public static native boolean DragInt(String label, int[] v, @OptArg float vSpeed, @OptArg int vMin, @OptArg int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragInt2(String label, int[] v, @OptArg float vSpeed, @OptArg int vMin, @OptArg int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragInt3(String label, int[] v, @OptArg float vSpeed, @OptArg int vMin, @OptArg int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragInt4(String label, int[] v, @OptArg float vSpeed, @OptArg int vMin, @OptArg int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, @OptArg float vSpeed, @OptArg int vMin, @OptArg int vMax, @OptArg String format, @OptArg String formatMax, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalar(String label, Void ImGuiDataType_S16, short[] pData, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") short pMin, @OptArg @ArgValue(callPrefix = "&") short pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalar(String label, Void ImGuiDataType_S32, int[] pData, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") int pMin, @OptArg @ArgValue(callPrefix = "&") int pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalar(String label, Void ImGuiDataType_S64, long[] pData, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") long pMin, @OptArg @ArgValue(callPrefix = "&") long pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalar(String label, Void ImGuiDataType_Float, float[] pData, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") float pMin, @OptArg @ArgValue(callPrefix = "&") float pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalar(String label, Void ImGuiDataType_Double, double[] pData, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") double pMin, @OptArg @ArgValue(callPrefix = "&") double pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalarN(String label, Void ImGuiDataType_S16, short[] pData, int components, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") short pMin, @OptArg @ArgValue(callPrefix = "&") short pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalarN(String label, Void ImGuiDataType_S32, int[] pData, int components, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") int pMin, @OptArg @ArgValue(callPrefix = "&") int pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalarN(String label, Void ImGuiDataType_S64, long[] pData, int components, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") long pMin, @OptArg @ArgValue(callPrefix = "&") long pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalarN(String label, Void ImGuiDataType_Float, float[] pData, int components, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") float pMin, @OptArg @ArgValue(callPrefix = "&") float pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean DragScalarN(String label, Void ImGuiDataType_Double, double[] pData, int components, @OptArg float vSpeed, @OptArg @ArgValue(callPrefix = "&") double pMin, @OptArg @ArgValue(callPrefix = "&") double pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    // Widgets: Regular Sliders
    // - CTRL+Click on any slider to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Format string may also be set to NULL or use the default format ("%f" or "%d").
    // - Legacy: Pre-1.78 there are SliderXXX() function signatures that take a final `float power=1.0f' argument instead of the `ImGuiSliderFlags flags=0' argument.
    //   If you get a warning converting a float to ImGuiSliderFlags, read https://github.com/ocornut/imgui/issues/3361

    /**
     * Adjust format to decorate the value with a prefix or a suffix for in-slider labels or unit display.
     */
    @BindingMethod
    public static native boolean SliderFloat(String label, float[] v, float vMin, float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderFloat2(String label, float[] v, float vMin, float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderFloat3(String label, float[] v, float vMin, float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderFloat4(String label, float[] v, float vMin, float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderAngle(String label, float[] vRad, @OptArg float vDegreesMin, @OptArg float vDegreesMax, @OptArg(callValue = "\"%.0f deg\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderInt(String label, int[] v, int vMin, int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderInt2(String label, int[] v, int vMin, int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderInt3(String label, int[] v, int vMin, int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderInt4(String label, int[] v, int vMin, int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalar(String label, Void ImGuiDataType_S16, short[] pData, @ArgValue(callPrefix = "&") short pMin, @ArgValue(callPrefix = "&") short pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalar(String label, Void ImGuiDataType_S32, int[] pData, @ArgValue(callPrefix = "&") int pMin, @ArgValue(callPrefix = "&") int pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalar(String label, Void ImGuiDataType_S64, long[] pData, @ArgValue(callPrefix = "&") long pMin, @ArgValue(callPrefix = "&") long pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalar(String label, Void ImGuiDataType_Float, float[] pData, @ArgValue(callPrefix = "&") float pMin, @ArgValue(callPrefix = "&") float pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalar(String label, Void ImGuiDataType_Double, double[] pData, @ArgValue(callPrefix = "&") double pMin, @ArgValue(callPrefix = "&") double pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalarN(String label, Void ImGuiDataType_S16, short[] pData, int components, @ArgValue(callPrefix = "&") short pMin, @ArgValue(callPrefix = "&") short pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalarN(String label, Void ImGuiDataType_S32, int[] pData, int components, @ArgValue(callPrefix = "&") int pMin, @ArgValue(callPrefix = "&") int pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalarN(String label, Void ImGuiDataType_S64, long[] pData, int components, @ArgValue(callPrefix = "&") long pMin, @ArgValue(callPrefix = "&") long pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalarN(String label, Void ImGuiDataType_Float, float[] pData, int components, @ArgValue(callPrefix = "&") float pMin, @ArgValue(callPrefix = "&") float pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean SliderScalarN(String label, Void ImGuiDataType_Double, double[] pData, int components, @ArgValue(callPrefix = "&") double pMin, @ArgValue(callPrefix = "&") double pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderFloat(String label, ImVec2 size, float[] v, float vMin, float vMax, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderInt(String label, ImVec2 size, int[] v, int vMin, int vMax, @OptArg(callValue = "\"%d\"") String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderScalar(String label, ImVec2 size, Void ImGuiDataType_S16, short[] pData, @ArgValue(callPrefix = "&") short pMin, @ArgValue(callPrefix = "&") short pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderScalar(String label, ImVec2 size, Void ImGuiDataType_S32, int[] pData, @ArgValue(callPrefix = "&") int pMin, @ArgValue(callPrefix = "&") int pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderScalar(String label, ImVec2 size, Void ImGuiDataType_S64, long[] pData, @ArgValue(callPrefix = "&") long pMin, @ArgValue(callPrefix = "&") long pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderScalar(String label, ImVec2 size, Void ImGuiDataType_Float, float[] pData, @ArgValue(callPrefix = "&") float pMin, @ArgValue(callPrefix = "&") float pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean VSliderScalar(String label, ImVec2 size, Void ImGuiDataType_Double, double[] pData, @ArgValue(callPrefix = "&") double pMin, @ArgValue(callPrefix = "&") double pMax, @OptArg String format, @OptArg int imGuiSliderFlags);

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
        return preInputText(multiline, label, hint, text, width, height, 0/*ImGuiInputTextFlags.None*/);
    }

    private static boolean preInputText(final boolean multiline, final String label, final String hint, final ImString text, final float width, final float height, final int flags) {
        return preInputText(multiline, label, hint, text, width, height, flags, null);
    }

    private static boolean preInputText(final boolean multiline, final String label, final String hint, final ImString text, final float width, final float height, final int flags, final ImGuiInputTextCallback callback) {
        final ImString.InputData inputData = text.inputData;

        String hintLabel = hint;
        if (hintLabel == null) {
            hintLabel = "";
        }

        return nInputText(
            multiline,
            hint != null,
            label,
            hintLabel,
            text,
            text.getData(),
            text.getData().length,
            width,
            height,
            flags,
            inputData,
            inputData.allowedChars,
            inputData.isResizable,
            callback
        );
    }

    private static native boolean nInputText(
        boolean multiline,
        boolean hint,
        String label,
        String hintLabel,
        ImString imString,
        byte[] buf,
        int maxSize,
        float width,
        float height,
        int flagsV,
        ImString.InputData textInputData,
        String allowedChars,
        boolean isResizable,
        ImGuiInputTextCallback callback
    ); /*
        InputTextCallbackUserData userData;
        userData.imString = &imString;
        userData.maxSize = maxSize;
        userData.jResizedBuf = NULL;
        userData.resizedBuf = NULL;
        userData.textInputData = &textInputData;
        userData.env = env;
        userData.allowedChars = allowedChars;
        userData.handler = callback != NULL ? &callback : NULL;

        int flags = flagsV;
        if (isResizable) {
            flags |= ImGuiInputTextFlags_CallbackResize;
        }
        if (strlen(allowedChars) > 0) {
            flags |= ImGuiInputTextFlags_CallbackCharFilter;
        }

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

    @BindingMethod
    public static native boolean InputFloat(String label, ImFloat v, @OptArg float step, @OptArg float stepFast, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputFloat2(String label, float[] v, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputFloat3(String label, float[] v, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputFloat4(String label, float[] v, @OptArg(callValue = "\"%.3f\"") String format, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputInt(String label, ImInt v, @OptArg int step, @OptArg int stepFast, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputInt2(String label, int[] v, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputInt3(String label, int[] v, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputInt4(String label, int[] v, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputDouble(String label, ImDouble v, @OptArg double step, @OptArg double stepFast, @OptArg(callValue = "\"%.6f\"") String format, @OptArg int imGuiInputTextFlags);

    @BindingMethod
    public static native boolean InputScalar(String label,
                                             @ArgVariant(name = {"ImGuiDataType_S16", "ImGuiDataType_S32", "ImGuiDataType_S64", "ImGuiDataType_Float", "ImGuiDataType_Double"}) Void __,
                                             @ArgVariant(type = {"ImShort", "ImInt", "ImLong", "ImFloat", "ImDouble"}) Void pData,
                                             @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStep,
                                             @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStepFast,
                                             @OptArg String format,
                                             @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean InputScalar(String label,
                                             int dataType,
                                             @ArgVariant(type = {"ImShort", "ImInt", "ImLong", "ImFloat", "ImDouble"}) Void pData,
                                             @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStep,
                                             @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStepFast,
                                             @OptArg String format,
                                             @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean InputScalarN(String label,
                                              @ArgVariant(name = {"ImGuiDataType_S16", "ImGuiDataType_S32", "ImGuiDataType_S64", "ImGuiDataType_Float", "ImGuiDataType_Double"}) Void __,
                                              @ArgVariant(type = {"short[]", "int[]", "long[]", "float[]", "double[]"}) Void pData,
                                              int components,
                                              @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStep,
                                              @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStepFast,
                                              @OptArg String format,
                                              @OptArg int imGuiSliderFlags);

    @BindingMethod
    public static native boolean InputScalarN(String label,
                                              int dataType,
                                              @ArgVariant(type = {"short[]", "int[]", "long[]", "float[]", "double[]"}) Void pData,
                                              int components,
                                              @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStep,
                                              @ArgVariant(type = {"short", "int", "long", "float", "double"}) @OptArg @ArgValue(callPrefix = "&") Void pStepFast,
                                              @OptArg String format,
                                              @OptArg int imGuiSliderFlags);

    // Widgets: Color Editor/Picker (tip: the ColorEdit* functions have a little color square that can be left-clicked to open a picker, and right-clicked to open an option menu.)
    // - Note that in C++ a 'float v[X]' function argument is the _same_ as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible.
    // - You can pass the address of a first float element out of a contiguous structure, e.g. &myvector.x

    @BindingMethod
    public static native boolean ColorEdit3(String label, float[] col, @OptArg int imGuiColorEditFlags);

    @BindingMethod
    public static native boolean ColorEdit4(String label, float[] col, @OptArg int imGuiColorEditFlags);

    @BindingMethod
    public static native boolean ColorPicker3(String label, float[] col, @OptArg int imGuiColorEditFlags);

    @BindingMethod
    public static native boolean ColorPicker4(String label, float[] col, @OptArg(callValue = "0") int imGuiColorEditFlags, @OptArg float[] refCol);

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    @BindingMethod
    public static native boolean ColorButton(String descId, ImVec4 col, @OptArg(callValue = "0") int imGuiColorEditFlags, @OptArg ImVec2 size);

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     *
     * @deprecated use {@link #colorButton(String, ImVec4)} or {@link #colorButton(String, float, float, float, float)} instead
     */
    @Deprecated
    @BindingMethod
    public static native boolean ColorButton(String descId, @ArgValue(callValue = "ImVec4(col[0], col[1], col[2], col[3])") float[] col, @OptArg(callValue = "0") int imGuiColorEditFlags, @OptArg ImVec2 size);

    /**
     * Initialize current options (generally on application startup) if you want to select a default format,
     * picker type, etc. User will be able to change many settings, unless you pass the _NoOptions flag to your calls.
     */
    @BindingMethod
    public static native void SetColorEditOptions(int imGuiColorEditFlags);

    // Widgets: Trees
    // - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

    @BindingMethod
    public static native boolean TreeNode(String label);

    /**
     * Helper variation to easily decorelate the id from the displayed string.
     * Read the FAQ about why and how to use ID. to align arbitrary text at the same level as a TreeNode() you can use Bullet().
     */
    @BindingMethod
    public static native boolean TreeNode(String strId, String label, Void NULL);

    @BindingMethod
    public static native boolean TreeNode(@ArgValue(callPrefix = "(void*)") long ptrId, String label, Void NULL);

    @BindingMethod
    public static native boolean TreeNodeEx(String label, @OptArg int flags);

    @BindingMethod
    public static native boolean TreeNodeEx(String strId, int flags, String label, Void NULL);

    @BindingMethod
    public static native boolean TreeNodeEx(@ArgValue(callPrefix = "(void*)") long ptrId, int flags, String label, Void NULL);

    /**
     * ~ Indent()+PushID(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    @BindingMethod
    public static native void TreePush(String strId);

    /**
     * ~ Indent()+PushID(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    @BindingMethod
    public static native void TreePush(@ArgValue(callPrefix = "(void*)") long ptrId);

    /**
     * ~ Unindent()+PopID()
     */
    @BindingMethod
    public static native void TreePop();

    /**
     * Horizontal distance preceding label when using TreeNode*() or Bullet() == (g.FontSize + style.FramePadding.x*2) for a regular unframed TreeNode
     */
    @BindingMethod
    public static native float GetTreeNodeToLabelSpacing();

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    @BindingMethod
    public static native boolean CollapsingHeader(String label, @OptArg int imGuiTreeNodeFlags);

    /**
     * When 'p_visible != NULL': if '*p_visible==true' display an additional small close button on upper right of the header which will set the bool
     * to false when clicked, if '*p_visible==false' don't display the header.
     */
    @BindingMethod
    public static native boolean CollapsingHeader(String label, ImBoolean pVisible, @OptArg int imGuiTreeNodeFlags);

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    @BindingMethod
    public static native void SetNextItemOpen(boolean isOpen, @OptArg int cond);

    // Widgets: Selectables
    // - A selectable highlights when hovered, and can display another color when selected.
    // - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

    @BindingMethod
    public static native boolean Selectable(String label, @OptArg(callValue = "false") boolean selected, @OptArg(callValue = "0") int imGuiSelectableFlags, @OptArg ImVec2 size);

    @BindingMethod
    public static native boolean Selectable(String label, ImBoolean pSelected, @OptArg(callValue = "0") int imGuiSelectableFlags, @OptArg ImVec2 size);

    // Widgets: List Boxes
    // - This is essentially a thin wrapper to using BeginChild/EndChild with the ImGuiChildFlags_FrameStyle flag for stylistic changes + displaying a label.
    // - You can submit contents and manage your selection state however you want it, by creating e.g. Selectable() or any other items.
    // - The simplified/old ListBox() api are helpers over BeginListBox()/EndListBox() which are kept available for convenience purpose. This is analoguous to how Combos are created.
    // - Choose frame width:   size.x > 0.0f: custom  /  size.x < 0.0f or -FLT_MIN: right-align   /  size.x = 0.0f (default): use current ItemWidth
    // - Choose frame height:  size.y > 0.0f: custom  /  size.y < 0.0f or -FLT_MIN: bottom-align  /  size.y = 0.0f (default): arbitrary default height which can fit ~7 items

    /**
     * Open a framed scrolling region.
     */
    @BindingMethod
    public static native boolean BeginListBox(String label, @OptArg ImVec2 size);

    /**
     * Only call EndListBox() if BeginListBox() returned true!
     */
    @BindingMethod
    public static native void EndListBox();

    @BindingMethod
    public static native void ListBox(String label, ImInt currentItem, String[] items, Void itemsCount, @OptArg int heightInItems);

    // Widgets: Data Plotting
    // - Consider using ImPlot (https://github.com/epezent/implot)

    @BindingMethod
    public static native void PlotLines(String label, float[] values, int valuesCount, @OptArg(callValue = "0") int valuesOffset, @OptArg(callValue = "NULL") String overlayText, @OptArg float scaleMin, @OptArg float scaleMax, @OptArg(callValue = "ImVec2(0,0)") ImVec2 graphSize, @OptArg int stride);

    @BindingMethod
    public static native void PlotHistogram(String label, float[] values, int valuesCount, @OptArg(callValue = "0") int valuesOffset, @OptArg(callValue = "NULL") String overlayText, @OptArg float scaleMin, @OptArg float scaleMax, @OptArg(callValue = "ImVec2(0,0)") ImVec2 graphSize, @OptArg int stride);

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
    @BindingMethod
    public static native boolean BeginMenuBar();

    /**
     * Only call EndMenuBar() if BeginMenuBar() returns true!
     */
    @BindingMethod
    public static native void EndMenuBar();

    /**
     * Create and append to a full screen menu-bar.
     */
    @BindingMethod
    public static native boolean BeginMainMenuBar();

    /**
     * Only call EndMainMenuBar() if BeginMainMenuBar() returns true!
     */
    @BindingMethod
    public static native void EndMainMenuBar();

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    @BindingMethod
    public static native boolean BeginMenu(String label, @OptArg boolean enabled);

    /**
     * Only call EndMenu() if BeginMenu() returns true!
     */
    @BindingMethod
    public static native void EndMenu();

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    @BindingMethod
    public static native boolean MenuItem(String label, Void NULL, @OptArg boolean selected, @OptArg boolean enabled);

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    @BindingMethod
    public static native boolean MenuItem(String label, String shortcut, @OptArg boolean selected, @OptArg boolean enabled);

    /**
     * Return true when activated + toggle (*pSelected) if pSelected != NULL
     */
    @BindingMethod
    public static native boolean MenuItem(String label, String shortcut, ImBoolean pSelected, @OptArg boolean enabled);

    // Tooltips
    // - Tooltips are windows following the mouse. They do not take focus away.
    // - A tooltip window can contain items of any types.
    // - SetTooltip() is more or less a shortcut for the 'if (BeginTooltip()) { Text(...); EndTooltip(); }' idiom (with a subtlety that it discard any previously submitted tooltip)

    /**
     * Begin/append a tooltip window.
     */
    @BindingMethod
    public static native void BeginTooltip();

    /**
     * Only call EndTooltip() if BeginTooltip()/BeginItemTooltip() returns true!
     */
    @BindingMethod
    public static native void EndTooltip();

    /**
     * Set a text-only tooltip. Often used after a ImGui::IsItemHovered() check. Override any previous call to SetTooltip().
     */
    @BindingMethod
    public static native void SetTooltip(String text, Void NULL);

    // Tooltips: helpers for showing a tooltip when hovering an item
    // - BeginItemTooltip() is a shortcut for the 'if (IsItemHovered(ImGuiHoveredFlags_Tooltip) && BeginTooltip())' idiom.
    // - SetItemTooltip() is a shortcut for the 'if (IsItemHovered(ImGuiHoveredFlags_Tooltip)) { SetTooltip(...); }' idiom.
    // - Where 'ImGuiHoveredFlags_Tooltip' itself is a shortcut to use 'style.HoverFlagsForTooltipMouse' or 'style.HoverFlagsForTooltipNav' depending on active input type. For mouse it defaults to 'ImGuiHoveredFlags_Stationary | ImGuiHoveredFlags_DelayShort'.

    /**
     * Begin/append a tooltip window if preceding item was hovered.
     */
    @BindingMethod
    public static native boolean BeginItemTooltip();

    /**
     * Set a text-only tooltip if preceding item was hovered. override any previous call to SetTooltip().
     */
    @BindingMethod
    public static native void SetItemTooltip(String text, Void NULL);

    // Popups, Modals
    //  - They block normal mouse hovering detection (and therefore most mouse interactions) behind them.
    //  - If not modal: they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    //  - Their visibility state (~bool) is held internally instead of being held by the programmer as we are used to with regular Begin*() calls.
    //  - The 3 properties above are related: we need to retain popup visibility state in the library because popups may be closed as any time.
    //  - You can bypass the hovering restriction by using ImGuiHoveredFlags_AllowWhenBlockedByPopup when calling IsItemHovered() or IsWindowHovered().
    //  - IMPORTANT: Popup identifiers are relative to the current ID stack, so OpenPopup and BeginPopup generally needs to be at the same level of the stack.
    //    This is sometimes leading to confusing mistakes. May rework this in the future.
    //  - BeginPopup(): query popup state, if open start appending into the window. Call EndPopup() afterwards if returned true. ImGuiWindowFlags are forwarded to the window.
    //  - BeginPopupModal(): block every interaction behind the window, cannot be closed by user, add a dimming background, has a title bar.

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    @BindingMethod
    public static native boolean BeginPopup(String strId, @OptArg int imGuiWindowFlags);

    /**
     * Return true if the popup is open, and you can start outputting to it.
     */
    @BindingMethod
    public static native boolean BeginPopupModal(String name, @OptArg(callValue = "NULL") ImBoolean pOpen, @OptArg int imGuiWindowFlags);

    /**
     * Only call EndPopup() if BeginPopupXXX() returns true!
     */
    @BindingMethod
    public static native void EndPopup();

    // Popups: open/close functions
    //  - OpenPopup(): set popup state to open. ImGuiPopupFlags are available for opening options.
    //  - If not modal: they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    //  - CloseCurrentPopup(): use inside the BeginPopup()/EndPopup() scope to close manually.
    //  - CloseCurrentPopup() is called by default by Selectable()/MenuItem() when activated (FIXME: need some options).
    //  - Use ImGuiPopupFlags_NoOpenOverExistingPopup to avoid opening a popup if there's already one at the same level. This is equivalent to e.g. testing for !IsAnyPopupOpen() prior to OpenPopup().

    /**
     * Call to mark popup as open (don't call every frame!).
     */
    @BindingMethod
    public static native void OpenPopup(String strId, @OptArg int imGuiPopupFlags);

    /**
     * Id overload to facilitate calling from nested stacks.
     */
    @BindingMethod
    public static native void OpenPopup(@ArgValue(callPrefix = "(ImGuiID)") int id, @OptArg int imGuiPopupFlags);

    /**
     * Helper to open popup when clicked on last item. return true when just opened. (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors)
     */
    @BindingMethod
    public static native void OpenPopupOnItemClick(@OptArg(callValue = "NULL") String strId, @OptArg int imGuiPopupFlags);

    /**
     * Manually close the popup we have begin-ed into.
     */
    @BindingMethod
    public static native void CloseCurrentPopup();

    // Popups: open+begin combined functions helpers
    //  - Helpers to do OpenPopup+BeginPopup where the Open action is triggered by e.g. hovering an item and right-clicking.
    //  - They are convenient to easily create context menus, hence the name.
    //  - IMPORTANT: Notice that BeginPopupContextXXX takes ImGuiPopupFlags just like OpenPopup() and unlike BeginPopup(). For full consistency, we may add ImGuiWindowFlags to the BeginPopupContextXXX functions in the future.
    //  - IMPORTANT: we exceptionally default their flags to 1 (== ImGuiPopupFlags_MouseButtonRight) for backward compatibility with older API taking 'int mouse_button = 1' parameter, so if you add other flags remember to re-add the ImGuiPopupFlags_MouseButtonRight.

    /**
     * Open+begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    @BindingMethod
    public static native boolean BeginPopupContextItem(@OptArg(callValue = "NULL") String strId, @OptArg int imGuiPopupFlags);

    /**
     * Open+begin popup when clicked on current window.
     */
    @BindingMethod
    public static native boolean BeginPopupContextWindow(@OptArg(callValue = "NULL") String strId, @OptArg int imGuiPopupFlags);

    /**
     * Open+begin popup when clicked in void (where there are no windows).
     */
    @BindingMethod
    public static native boolean BeginPopupContextVoid(@OptArg(callValue = "NULL") String strId, @OptArg int imGuiPopupFlags);

    // Popups: test function
    //  - IsPopupOpen(): return true if the popup is open at the current BeginPopup() level of the popup stack.
    //  - IsPopupOpen() with ImGuiPopupFlags_AnyPopupId: return true if any popup is open at the current BeginPopup() level of the popup stack.
    //  - IsPopupOpen() with ImGuiPopupFlags_AnyPopupId + ImGuiPopupFlags_AnyPopupLevel: return true if any popup is open.

    /**
     * Return true if the popup is open.
     */
    @BindingMethod
    public static native boolean IsPopupOpen(String strId, @OptArg int imGuiPopupFlags);

    // Tables
    // - Full-featured replacement for old Columns API.
    // - See Demo->Tables for demo code. See top of imgui_tables.cpp for general commentary.
    // - See ImGuiTableFlags_ and ImGuiTableColumnFlags_ enums for a description of available flags.
    // The typical call flow is:
    // - 1. Call BeginTable(), early out if returning false.
    // - 2. Optionally call TableSetupColumn() to submit column name/flags/defaults.
    // - 3. Optionally call TableSetupScrollFreeze() to request scroll freezing of columns/rows.
    // - 4. Optionally call TableHeadersRow() to submit a header row. Names are pulled from TableSetupColumn() data.
    // - 5. Populate contents:
    //    - In most situations you can use TableNextRow() + TableSetColumnIndex(N) to start appending into a column.
    //    - If you are using tables as a sort of grid, where every column is holding the same type of contents,
    //      you may prefer using TableNextColumn() instead of TableNextRow() + TableSetColumnIndex().
    //      TableNextColumn() will automatically wrap-around into the next row if needed.
    //    - IMPORTANT: Comparatively to the old Columns() API, we need to call TableNextColumn() for the first column!
    //    - Summary of possible call flow:
    //        - TableNextRow() -> TableSetColumnIndex(0) -> Text("Hello 0") -> TableSetColumnIndex(1) -> Text("Hello 1")  // OK
    //        - TableNextRow() -> TableNextColumn()      -> Text("Hello 0") -> TableNextColumn()      -> Text("Hello 1")  // OK
    //        -                   TableNextColumn()      -> Text("Hello 0") -> TableNextColumn()      -> Text("Hello 1")  // OK: TableNextColumn() automatically gets to next row!
    //        - TableNextRow()                           -> Text("Hello 0")                                               // Not OK! Missing TableSetColumnIndex() or TableNextColumn()! Text will not appear!
    // - 5. Call EndTable()

    @BindingMethod
    public static native boolean BeginTable(String id, int columns, @OptArg(callValue = "0") int imGuiTableFlags, @OptArg(callValue = "ImVec2(0,0)") ImVec2 outerSize, @OptArg float innerWidth);

    /**
     * Only call EndTable() if BeginTable() returns true!
     */
    @BindingMethod
    public static native void EndTable();

    /**
     * Append into the first cell of a new row.
     */
    @BindingMethod
    public static native void TableNextRow(@OptArg(callValue = "0") int imGuiTableRowFlags, @OptArg float minRowHeight);

    /**
     * Append into the next column (or first column of next row if currently in last column). Return true when column is visible.
     */
    @BindingMethod
    public static native boolean TableNextColumn();

    /**
     * Append into the specified column. Return true when column is visible.
     */
    @BindingMethod
    public static native boolean TableSetColumnIndex(int columnN);

    // Tables: Headers & Columns declaration
    // - Use TableSetupColumn() to specify label, resizing policy, default width/weight, id, various other flags etc.
    // - Use TableHeadersRow() to create a header row and automatically submit a TableHeader() for each column.
    //   Headers are required to perform: reordering, sorting, and opening the context menu.
    //   The context menu can also be made available in columns body using ImGuiTableFlags_ContextMenuInBody.
    // - You may manually submit headers using TableNextRow() + TableHeader() calls, but this is only useful in
    //   some advanced use cases (e.g. adding custom widgets in header row).
    // - Use TableSetupScrollFreeze() to lock columns/rows so they stay visible when scrolled.

    @BindingMethod
    public static native void TableSetupColumn(String label, @OptArg(callValue = "0") int imGuiTableColumnFlags, @OptArg(callValue = "0.0f") float initWidthOrWeight, @OptArg int userId);

    /**
     * Lock columns/rows so they stay visible when scrolled.
     */
    @BindingMethod
    public static native void TableSetupScrollFreeze(int cols, int rows);

    /**
     * Submit one header cell manually (rarely used)
     */
    @BindingMethod
    public static native void TableHeader(String label);

    /**
     * Submit all headers cells based on data provided to TableSetupColumn() + submit context menu
     */
    @BindingMethod
    public static native void TableHeadersRow();

    /**
     * Submit a row with angled headers for every column with the ImGuiTableColumnFlags_AngledHeader flag. MUST BE FIRST ROW.
     */
    @BindingMethod
    public static native void TableAngledHeadersRow();

    // Tables: Sorting & Miscellaneous functions
    // - Sorting: call TableGetSortSpecs() to retrieve latest sort specs for the table. NULL when not sorting.
    //   When 'sort_specs->SpecsDirty == true' you should sort your data. It will be true when sorting specs have
    //   changed since last call, or the first time. Make sure to set 'SpecsDirty = false' after sorting,
    //   else you may wastefully sort your data every frame!
    // - Functions args 'int column_n' treat the default value of -1 as the same as passing the current column index.

    /**
     * Get latest sort specs for the table (NULL if not sorting).
     * Lifetime: don't hold on this pointer over multiple frames or past any subsequent call to BeginTable().
     */
    @BindingMethod
    public static native ImGuiTableSortSpecs TableGetSortSpecs();

    // Tables: Miscellaneous functions
    // - Functions args 'int column_n' treat the default value of -1 as the same as passing the current column index.

    /**
     * Return number of columns (value passed to BeginTable).
     */
    @BindingMethod
    public static native int TableGetColumnCount();

    /**
     * Return current column index.
     */
    @BindingMethod
    public static native int TableGetColumnIndex();

    /**
     * Return current row index.
     */
    @BindingMethod
    public static native int TableGetRowIndex();

    /**
     * Return "" if column didn't have a name declared by TableSetupColumn(). Pass -1 to use current column.
     */
    @BindingMethod
    public static native String TableGetColumnName(@OptArg int columnN);

    /**
     * Return column flags, so you can query their Enabled/Visible/Sorted/Hovered status flags. Pass -1 to use current column.
     */
    @BindingMethod
    public static native int TableGetColumnFlags(@OptArg int columnN);

    /**
     * change user accessible enabled/disabled state of a column. Set to false to hide the column.
     * User can use the context menu to change this themselves (right-click in headers, or right-click in columns body with ImGuiTableFlags_ContextMenuInBody)
     */
    @BindingMethod
    public static native void TableSetColumnEnabled(int columnN, boolean value);

    /**
     * Return hovered column. return -1 when table is not hovered. return columns_count if the unused space at the right of visible columns is hovered. Can also use (TableGetColumnFlags() & ImGuiTableColumnFlags_IsHovered) instead.
     */
    @BindingMethod
    public static native int TableGetHoveredColumn();

    /**
     * Change the color of a cell, row, or column. See ImGuiTableBgTarget_ flags for details.
     */
    @BindingMethod
    public static native void TableSetBgColor(int imGuiTableBgTarget, int color, @OptArg int columnN);

    // Legacy Columns API (2020: prefer using Tables!)
    // Columns
    // - You can also use SameLine(posX) to mimic simplified columns.

    @BindingMethod
    public static native void Columns(@OptArg(callValue = "1") int count, @OptArg(callValue = "NULL") String id, @OptArg boolean border);

    /**
     * Next column, defaults to current row or next row if the current row is finished
     */
    @BindingMethod
    public static native void NextColumn();

    /**
     * Get current column index
     */
    @BindingMethod
    public static native int GetColumnIndex();

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    @BindingMethod
    public static native float GetColumnWidth(@OptArg int columnIndex);

    /**
     * Set column width (in pixels). pass -1 to use current column
     */
    @BindingMethod
    public static native void SetColumnWidth(int columnIndex, float width);

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    @BindingMethod
    public static native float GetColumnOffset(@OptArg int columnIndex);

    /**
     * Set position of column line (in pixels, from the left side of the contents region). pass -1 to use current column
     */
    @BindingMethod
    public static native void SetColumnOffset(int columnIndex, float offsetX);

    @BindingMethod
    public static native int GetColumnsCount();

    // Tab Bars, Tabs
    // - Note: Tabs are automatically created by the docking system (when in 'docking' branch). Use this to create tab bars/tabs yourself.

    /**
     * Create and append into a TabBar
     */
    @BindingMethod
    public static native boolean BeginTabBar(String strId, @OptArg int imGuiTabBarFlags);

    /**
     * Only call EndTabBar() if BeginTabBar() returns true!
     */
    @BindingMethod
    public static native void EndTabBar();

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    @BindingMethod
    public static native boolean BeginTabItem(String label, @OptArg(callValue = "NULL") ImBoolean pOpen, @OptArg int imGuiTabItemFlags);

    /**
     * Only call EndTabItem() if BeginTabItem() returns true!
     */
    @BindingMethod
    public static native void EndTabItem();

    /**
     * Create a Tab behaving like a button. return true when clicked. cannot be selected in the tab bar.
     */
    @BindingMethod
    public static native boolean TabItemButton(String label, @OptArg int imGuiTabItemFlags);

    /**
     * Notify TabBar or Docking system of a closed tab/window ahead (useful to reduce visual flicker on reorderable tab bars).
     * For tab-bar: call after BeginTabBar() and before Tab submissions. Otherwise call with a window name.
     */
    @BindingMethod
    public static native void SetTabItemClosed(String tabOrDockedWindowLabel);

    // Docking
    // [BETA API] Enable with io.ConfigFlags |= ImGuiConfigFlags_DockingEnable.
    // Note: You can use most Docking facilities without calling any API. You DO NOT need to call DockSpace() to use Docking!
    // - Drag from window title bar or their tab to dock/undock. Hold SHIFT to disable docking.
    // - Drag from window menu button (upper-left button) to undock an entire node (all windows).
    // - When io.ConfigDockingWithShift == true, you instead need to hold SHIFT to enable docking.
    // About dockspaces:
    // - Use DockSpaceOverViewport() to create a window covering the screen or a specific viewport + a dockspace inside it.
    //   This is often used with ImGuiDockNodeFlags_PassthruCentralNode to make it transparent.
    // - Use DockSpace() to create an explicit dock node _within_ an existing window. See Docking demo for details.
    // - Important: Dockspaces need to be submitted _before_ any window they can host. Submit it early in your frame!
    // - Important: Dockspaces need to be kept alive if hidden, otherwise windows docked into it will be undocked.
    //   e.g. if you have multiple tabs with a dockspace inside each tab: submit the non-visible dockspaces with ImGuiDockNodeFlags_KeepAliveOnly.

    @BindingMethod
    public static native int DockSpace(int dockspaceId, @OptArg(callValue = "ImVec2(0,0)") ImVec2 size, @OptArg(callValue = "0") int imGuiDockNodeFlags, @OptArg ImGuiWindowClass windowClass);

    @BindingMethod
    public static native int DockSpaceOverViewport(@OptArg(callValue = "0") int dockspaceId, @OptArg ImGuiViewport viewport, @OptArg(callValue = "0") int imGuiDockNodeFlags, @OptArg ImGuiWindowClass windowClass);

    /**
     * Set next window dock id
     */
    @BindingMethod
    public static native void SetNextWindowDockID(int dockId, @OptArg int imGuiCond);

    /**
     * set next window class (rare/advanced uses: provide hints to the platform backend via altered viewport flags and parent/child info)
     */
    @BindingMethod
    public static native void SetNextWindowClass(ImGuiWindowClass windowClass);

    @BindingMethod
    public static native int GetWindowDockID();

    /**
     * Is current window docked into another window?
     */
    @BindingMethod
    public static native boolean IsWindowDocked();

    // Logging/Capture
    // - All text output from the interface can be captured into tty/file/clipboard. By default, tree nodes are automatically opened during logging.

    /**
     * Start logging to tty (stdout)
     */
    @BindingMethod
    public static native void LogToTTY(@OptArg int autoOpenDepth);

    /**
     * Start logging to file
     */
    @BindingMethod
    public static native void LogToFile(@OptArg(callValue = "-1") int autoOpenDepth, @OptArg String filename);

    /**
     * Start logging to OS clipboard
     */
    @BindingMethod
    public static native void LogToClipboard(@OptArg int autoOpenDepth);

    /**
     * Stop logging (close file, etc.)
     */
    @BindingMethod
    public static native void LogFinish();

    /**
     * Helper to display buttons for logging to tty/file/clipboard
     */
    @BindingMethod
    public static native void LogButtons();

    /**
     * Pass text data straight to log (without being displayed)
     */
    @BindingMethod
    public static native void LogText(String text, Void NULL);

    // Drag and Drop
    // - If you stop calling BeginDragDropSource() the payload is preserved however it won't have a preview tooltip (we currently display a fallback "..." tooltip as replacement)

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    @BindingMethod
    public static native boolean BeginDragDropSource(@OptArg int imGuiDragDropFlags);

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
    @BindingMethod
    public static native void EndDragDropSource();

    /**
     * Call after submitting an item that may receive a payload. If this returns true, you can call AcceptDragDropPayload() + EndDragDropTarget()
     */
    @BindingMethod
    public static native boolean BeginDragDropTarget();

    /**
     * Accept contents of a given type. If ImGuiDragDropFlags_AcceptBeforeDelivery is set you can peek into the payload before the mouse button is released.
     */
    public static <T> T acceptDragDropPayload(final String dataType) {
        return acceptDragDropPayload(dataType, 0/*ImGuiDragDropFlags.None*/);
    }

    /**
     * Type safe alternative for {@link #acceptDragDropPayload(String)}, since it checks assignability of the accepted class.
     */
    public static <T> T acceptDragDropPayload(final String dataType, final Class<T> aClass) {
        return acceptDragDropPayload(dataType, 0/*ImGuiDragDropFlags.None*/, aClass);
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
        return acceptDragDropPayload(String.valueOf(aClass.hashCode()), 0/*ImGuiDragDropFlags.None*/, aClass);
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
    @BindingMethod
    public static native void EndDragDropTarget();

    /**
     * Peek directly into the current payload from anywhere. returns NULL when drag and drop is finished or inactive. use ImGuiPayload::IsDataType() to test for the payload type.
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

    // Disabling [BETA API]
    // - Disable all user interactions and dim items visuals (applying style.DisabledAlpha over current colors)
    // - Those can be nested but it cannot be used to enable an already disabled section (a single BeginDisabled(true) in the stack is enough to keep everything disabled)
    // - Tooltips windows by exception are opted out of disabling.
    // - BeginDisabled(false) essentially does nothing useful but is provided to facilitate use of boolean expressions. If you can avoid calling BeginDisabled(False)/EndDisabled() best to avoid it.

    /**
     * Disable all user interactions and dim items visuals (applying style.DisabledAlpha over current colors)
     * BeginDisabled(false) essentially does nothing useful but is provided to facilitate use of boolean expressions.
     * If you can avoid calling BeginDisabled(False)/EndDisabled() best to avoid it.
     */
    @BindingMethod
    public static native void BeginDisabled(@OptArg boolean disabled);

    @BindingMethod
    public static native void EndDisabled();

    // Clipping
    // - Mouse hovering is affected by ImGui::PushClipRect() calls, unlike direct calls to ImDrawList::PushClipRect() which are render only.

    @BindingMethod
    public static native void PushClipRect(ImVec2 clipRectMin, ImVec2 clipRectMax, boolean intersectWithCurrentClipRect);

    @BindingMethod
    public static native void PopClipRect();

    // Focus, Activation
    // - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

    /**
     * Make last item the default focused item of a window.
     */
    @BindingMethod
    public static native void SetItemDefaultFocus();

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    @BindingMethod
    public static native void SetKeyboardFocusHere(@OptArg int offset);

    // Overlapping mode

    /**
     * Allow next item to be overlapped by a subsequent item.
     * Useful with invisible buttons, selectable, treenode covering an area where subsequent items may need to be added.
     * Note that both Selectable() and TreeNode() have dedicated flags doing this.
     */
    @BindingMethod
    public static native void SetNextItemAllowOverlap();

    // Item/Widgets Utilities
    // - Most of the functions are referring to the last/previous item we submitted.
    // - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    @BindingMethod
    public static native boolean IsItemHovered(@OptArg int imGuiHoveredFlags);

    /**
     * Is the last item active? (e.g. button being held, text field being edited.
     * This will continuously return true while holding mouse button on an item.
     * Items that don't interact will always return false)
     */
    @BindingMethod
    public static native boolean IsItemActive();

    /**
     * Is the last item focused for keyboard/gamepad navigation?
     */
    @BindingMethod
    public static native boolean IsItemFocused();

    /**
     * Is the last item hovered and mouse clicked on? (**)  == {@code IsMouseClicked(mouseButton) && IsItemHovered()}
     * Important. (**) this is NOT equivalent to the behavior of e.g. Button(). Read comments in function definition.
     */
    @BindingMethod
    public static native boolean IsItemClicked(@OptArg int mouseButton);

    /**
     * Is the last item visible? (items may be out of sight because of clipping/scrolling)
     */
    @BindingMethod
    public static native boolean IsItemVisible();

    /**
     * Did the last item modify its underlying value this frame? or was pressed? This is generally the same as the "bool" return value of many widgets.
     */
    @BindingMethod
    public static native boolean IsItemEdited();

    /**
     * Was the last item just made active (item was previously inactive).
     */
    @BindingMethod
    public static native boolean IsItemActivated();

    /**
     * Was the last item just made inactive (item was previously active). Useful for Undo/Redo patterns with widgets that require continuous editing.
     */
    @BindingMethod
    public static native boolean IsItemDeactivated();

    /**
     * Was the last item just made inactive and made a value change when it was active? (e.g. Slider/Drag moved).
     * Useful for Undo/Redo patterns with widgets that require continuous editing.
     * Note that you may get false positives (some widgets such as Combo()/ListBox()/Selectable() will return true even when clicking an already selected item).
     */
    @BindingMethod
    public static native boolean IsItemDeactivatedAfterEdit();

    /**
     * Was the last item open state toggled? set by TreeNode().
     */
    @BindingMethod
    public static native boolean IsItemToggledOpen();

    /**
     * Is any item hovered?
     */
    @BindingMethod
    public static native boolean IsAnyItemHovered();

    /**
     * Is any item active?
     */
    @BindingMethod
    public static native boolean IsAnyItemActive();
    /**
     * Is any item focused?
     */
    @BindingMethod
    public static native boolean IsAnyItemFocused();

    /**
     * Get ID of last item (~~ often same ImGui::GetID(label) beforehand)
     */
    @BindingMethod
    public static native int GetItemID();

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    @BindingMethod
    public static native ImVec2 GetItemRectMin();

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    @BindingMethod
    public static native ImVec2 GetItemRectMax();

    /**
     * Get size of last item
     */
    @BindingMethod
    public static native ImVec2 GetItemRectSize();

    // Viewports
    // - Currently represents the Platform Window created by the application which is hosting our Dear ImGui windows.
    // - In 'docking' branch with multi-viewport enabled, we extend this concept to have multiple active viewports.
    // - In the future we will extend this concept further to also represent Platform Monitor and support a "no main platform window" operation mode.

    /**
     * Return primary/default viewport.
     */
    @BindingMethod
    @ReturnValue(isStatic = true)
    public static native ImGuiViewport GetMainViewport();

    // Background/Foreground Draw Lists

    /**
     * Get background draw list for the given viewport or viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    @BindingMethod
    public static native ImDrawList GetBackgroundDrawList(@OptArg ImGuiViewport viewport);

    /**
     * Get foreground draw list for the given viewport or viewport associated to the current window.
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    @BindingMethod
    public static native ImDrawList GetForegroundDrawList(@OptArg ImGuiViewport viewport);

    // Miscellaneous Utilities

    /**
     * Test if rectangle (of given size, starting from cursor position) is visible / not clipped.
     */
    @BindingMethod
    public static native boolean IsRectVisible(ImVec2 size);

    /**
     * Test if rectangle (in screen space) is visible / not clipped. to perform coarse clipping on user's side.
     */
    @BindingMethod
    public static native boolean IsRectVisible(ImVec2 rectMin, ImVec2 rectMax);

    /**
     * Get global imgui time. incremented by io.DeltaTime every frame.
     */
    @BindingMethod
    public static native double GetTime();

    /**
     * Get global imgui frame count. incremented by 1 every frame.
     */
    @BindingMethod
    public static native int GetFrameCount();



    // TODO GetDrawListSharedData

    /**
     * Get a string corresponding to the enum value (for display, saving, etc.).
     */
    @BindingMethod
    public static native String GetStyleColorName(int imGuiColIdx);

    /**
     * Replace current window storage with our own (if you want to manipulate it yourself, typically clear subsection of it).
     */
    @BindingMethod
    public static native void SetStateStorage(final ImGuiStorage storage);

    @BindingMethod
    public static native ImGuiStorage GetStateStorage();

    // Text Utilities

    @BindingMethod
    public static native ImVec2 CalcTextSize(String text, Void NULL, @OptArg(callValue = "false") boolean hideTextAfterDoubleHash, @OptArg float wrapWidth);

    // Color Utilities

    @BindingMethod
    public static native ImVec4 ColorConvertU32ToFloat4(final int in);

    @BindingMethod
    public static native int ColorConvertFloat4ToU32(ImVec4 in);

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

    // Inputs Utilities: Keyboard/Mouse/Gamepad
    // - the ImGuiKey enum contains all possible keyboard, mouse and gamepad inputs (e.g. ImGuiKey_A, ImGuiKey_MouseLeft, ImGuiKey_GamepadDpadUp...).
    // - before v1.87, we used ImGuiKey to carry native/user indices as defined by each backends. About use of those legacy ImGuiKey values:
    //  - without IMGUI_DISABLE_OBSOLETE_KEYIO (legacy support): you can still use your legacy native/user indices (< 512) according to how your backend/engine stored them in io.KeysDown[], but need to cast them to ImGuiKey.
    //  - with    IMGUI_DISABLE_OBSOLETE_KEYIO (this is the way forward): any use of ImGuiKey will assert with key < 512. GetKeyIndex() is pass-through and therefore deprecated (gone if IMGUI_DISABLE_OBSOLETE_KEYIO is defined).

    /**
     * Is key being held. == io.KeysDown[user_key_index].
     */
    @BindingMethod
    public static native boolean IsKeyDown(@ArgValue(staticCast = "ImGuiKey") int key);

    /**
     * Was key pressed (went from !Down to Down)? if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    @BindingMethod
    public static native boolean IsKeyPressed(@ArgValue(staticCast = "ImGuiKey") int key, @OptArg boolean repeat);

    /**
     * Was key released (went from Down to !Down)
     */
    @BindingMethod
    public static native boolean IsKeyReleased(@ArgValue(staticCast = "ImGuiKey") int key);

    /**
     * Was key chord (mods + key) pressed, e.g. you can pass 'ImGuiMod_Ctrl | ImGuiKey_S' as a key-chord.
     * This doesn't do any routing or focus check, please consider using Shortcut() function instead.
     */
    @BindingMethod
    public static native boolean IsKeyChordPressed(@ArgValue(staticCast = "ImGuiKeyChord") int keyChord);

    /**
     * Uses provided repeat rate/delay.
     * Return a count, most often 0 or 1 but might be {@code >1} if RepeatRate is small enough that {@code DeltaTime > RepeatRate}
     */
    @BindingMethod
    public static native boolean GetKeyPressedAmount(@ArgValue(staticCast = "ImGuiKey") int key, float repeatDelay, float rate);

    /**
     * [DEBUG] returns English name of the key. Those names a provided for debugging purpose and are not meant to be saved persistently not compared.
     */
    @BindingMethod
    public static native String GetKeyName(@ArgValue(staticCast = "ImGuiKey") int key);

    /**
     * Override io.WantCaptureKeyboard flag next frame (said flag is left for your application to handle,
     * typically when true it instructs your app to ignore inputs). e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = want_capture_keyboard"; after the next NewFrame() call.
     */
    @BindingMethod
    public static native void SetNextFrameWantCaptureKeyboard(boolean wantCaptureKeyboard);

    // Inputs Utilities: Shortcut Testing & Routing [BETA]
    // - ImGuiKeyChord = a ImGuiKey + optional ImGuiMod_Alt/ImGuiMod_Ctrl/ImGuiMod_Shift/ImGuiMod_Super.
    //       ImGuiKey_C                          // Accepted by functions taking ImGuiKey or ImGuiKeyChord arguments)
    //       ImGuiMod_Ctrl | ImGuiKey_C          // Accepted by functions taking ImGuiKeyChord arguments)
    //   only ImGuiMod_XXX values are legal to combine with an ImGuiKey. You CANNOT combine two ImGuiKey values.
    // - The general idea is that several callers may register interest in a shortcut, and only one owner gets it.
    //      Parent   -> call Shortcut(Ctrl+S)    // When Parent is focused, Parent gets the shortcut.
    //        Child1 -> call Shortcut(Ctrl+S)    // When Child1 is focused, Child1 gets the shortcut (Child1 overrides Parent shortcuts)
    //        Child2 -> no call                  // When Child2 is focused, Parent gets the shortcut.
    //   The whole system is order independent, so if Child1 makes its calls before Parent, results will be identical.
    //   This is an important property as it facilitate working with foreign code or larger codebase.
    // - To understand the difference:
    //   - IsKeyChordPressed() compares mods and call IsKeyPressed() -> function has no side-effect.
    //   - Shortcut() submits a route, routes are resolved, if it currently can be routed it calls IsKeyChordPressed() -> function has (desirable) side-effects as it can prevents another call from getting the route.
    // - Visualize registered routes in 'Metrics/Debugger->Inputs'.

    @BindingMethod
    public static native boolean Shortcut(@ArgValue(staticCast = "ImGuiKeyChord") int keyChord, @OptArg int flags);

    @BindingMethod
    public static native void SetNextItemShortcut(@ArgValue(staticCast = "ImGuiKeyChord") int keyChord, @OptArg int flags);

    // Inputs Utilities: Mouse specific
    // - To refer to a mouse button, you may use named enums in your code e.g. ImGuiMouseButton_Left, ImGuiMouseButton_Right.
    // - You can also use regular integer: it is forever guaranteed that 0=Left, 1=Right, 2=Middle.
    // - Dragging operations are only reported after mouse has moved a certain distance away from the initial clicking position (see 'lock_threshold' and 'io.MouseDraggingThreshold')

    /**
     * Is mouse button held (0=left, 1=right, 2=middle)
     */
    @BindingMethod
    public static native boolean IsMouseDown(int button);

    /**
     * Did mouse button clicked (went from !Down to Down) (0=left, 1=right, 2=middle)
     */
    @BindingMethod
    public static native boolean IsMouseClicked(int button, @OptArg boolean repeat);

    /**
     * Did mouse button released (went from Down to !Down)
     */
    @BindingMethod
    public static native boolean IsMouseReleased(int button);

    /**
     * did mouse button double-clicked? (note that a double-click will also report IsMouseClicked() == true).
     */
    @BindingMethod
    public static native boolean IsMouseDoubleClicked(int button);

    /**
     * Return the number of successive mouse-clicks at the time where a click happen (otherwise 0).
     */
    @BindingMethod
    public static native int GetMouseClickedCount(int button);

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    @BindingMethod
    public static native boolean IsMouseHoveringRect(ImVec2 rMin, ImVec2 rMax, @OptArg boolean clip);

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    @BindingMethod
    public static native boolean IsMousePosValid(@OptArg @ArgValue(callPrefix = "&") final ImVec2 mousePos);

    /**
     * Is any mouse button held
     */
    @BindingMethod
    public static native boolean IsAnyMouseDown();

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    @BindingMethod
    public static native ImVec2 GetMousePos();

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    @BindingMethod
    public static native ImVec2 GetMousePosOnOpeningCurrentPopup();

    /**
     * Is mouse dragging? (uses io.MouseDraggingThreshold if lock_threshold < 0.0f)
     */
    @BindingMethod
    public static native boolean IsMouseDragging(int button, @OptArg float lockThreshold);

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once (uses io.MouseDraggingThreshold if lock_threshold < 0.0f)
     */
    @BindingMethod
    public static native ImVec2 GetMouseDragDelta(@OptArg final int button, @OptArg final float lockThreshold);

    @BindingMethod
    public static native void ResetMouseDragDelta(@OptArg int button);

    /**
     * Get desired mouse cursor shape. Important: reset in ImGui::NewFrame(), this is updated during the frame. valid before Render().
     * If you use software rendering by setting io.MouseDrawCursor ImGui will render those for you
     */
    @BindingMethod
    public static native int GetMouseCursor();

    /**
     * Set desired mouse cursor shape
     */
    @BindingMethod
    public static native void SetMouseCursor(int type);

    /**
     *  Override io.WantCaptureMouse flag next frame (said flag is left for your application to handle,
     *  typical when true it instucts your app to ignore inputs).
     *  This is equivalent to setting "io.WantCaptureMouse = want_capture_mouse;" after the next NewFrame() call.
     */
    @BindingMethod
    public static native void SetNextFrameWantCaptureMouse(boolean wantCaptureMouse);

    // Clipboard Utilities
    // - Also see the LogToClipboard() function to capture GUI into clipboard, or easily output text data to the clipboard.

    @BindingMethod
    public static native String GetClipboardText();

    @BindingMethod
    public static native void SetClipboardText(String text);

    // Settings/.Ini Utilities
    // - The disk functions are automatically called if io.IniFilename != NULL (default is "imgui.ini").
    // - Set io.IniFilename to NULL to load/save manually. Read io.WantSaveIniSettings description about handling .ini saving manually.

    /**
     * Call after CreateContext() and before the first call to NewFrame(). NewFrame() automatically calls LoadIniSettingsFromDisk(io.IniFilename).
     */
    @BindingMethod
    public static native void LoadIniSettingsFromDisk(String iniFilename);

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    @BindingMethod
    public static native void LoadIniSettingsFromMemory(String iniData, @OptArg int iniSize);

    /**
     * This is automatically called (if io.IniFilename is not empty) a few seconds after any modification that should be reflected in the .ini file (and also by DestroyContext).
     */
    @BindingMethod
    public static native void SaveIniSettingsToDisk(String iniFilename);

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    @BindingMethod
    public static native String SaveIniSettingsToMemory(@OptArg @ArgValue(callPrefix = "(size_t*)&") long outIniSize);

    // Debug Utilities
    // - Your main debugging friend is the ShowMetricsWindow() function, which is also accessible from Demo->Tools->Metrics Debugger

    @BindingMethod
    public static native void DebugTextEncoding(String text);

    @BindingMethod
    public static native void DebugFlashStyleColor(@ArgValue(staticCast = "ImGuiCol") int idx);

    @BindingMethod
    public static native void DebugStartItemPicker();

    @BindingMethod
    public static native boolean DebugCheckVersionAndDataLayout(String versionStr, int szIo, int szStyle, int szVec2, int szVec4, int szDrawVert, int szDrawIdx);

    // (Optional) Platform/OS interface for multi-viewport support
    // Read comments around the ImGuiPlatformIO structure for more details.
    // Note: You may use GetWindowViewport() to get the current viewport of the current window.

    /**
     * Platform/renderer functions, for backend to setup + viewports list.
     */
    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native ImGuiPlatformIO GetPlatformIO();

    /**
     * Call in main loop. Will call CreateWindow/ResizeWindow/etc. Platform functions for each secondary viewport, and DestroyWindow for each inactive viewport.
     */
    @BindingMethod
    public static native void UpdatePlatformWindows();

    /**
     * Call in main loop. will call RenderWindow/SwapBuffers platform functions for each secondary viewport which doesn't have the ImGuiViewportFlags_Minimized flag set.
     * May be reimplemented by user for custom rendering needs.
     */
    @BindingMethod
    public static native void RenderPlatformWindowsDefault();

    /**
     * Call DestroyWindow platform functions for all viewports.
     * Call from backend Shutdown() if you need to close platform windows before imgui shutdown.
     * Otherwise will be called by DestroyContext().
     */
    @BindingMethod
    public static native void DestroyPlatformWindows();

    /**
     * This is a helper for backends.
     */
    @BindingMethod
    public static native ImGuiViewport FindViewportByID(int imGuiID);

    /**
     * This is a helper for backends. The type platform_handle is decided by the backend (e.g. HWND, MyWindow*, GLFWwindow* etc.)
     */
    @BindingMethod
    public static native ImGuiViewport FindViewportByPlatformHandle(@ArgValue(callPrefix = "(void*)") long platformHandle);
}
