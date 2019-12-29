package imgui;

import imgui.enums.ImGuiInputTextFlags;

import java.nio.ByteBuffer;

public final class ImGui {
    private final static String LIB_NAME_PROP = "imgui.library.name";
    private final static String LIB_NAME_DEFAULT = "imgui-java";

    private static final ImDrawData DRAW_DATA = new ImDrawData(100_000, 100_000, 1000);
    private static final ImGuiIO IMGUI_IO = new ImGuiIO();
    private static final ImGuiStyle IMGUI_STYLE = new ImGuiStyle();
    private static final ImDrawList IM_DRAW_LIST = new ImDrawList(ImDrawList.TYPE_DEFAULT);
    private static final ImDrawList IM_DRAW_LIST_BACKGROUND = new ImDrawList(ImDrawList.TYPE_BACKGROUND);
    private static final ImDrawList IM_DRAW_LIST_FOREGROUND = new ImDrawList(ImDrawList.TYPE_FOREGROUND);

    static {
        System.loadLibrary(System.getProperty(LIB_NAME_PROP, LIB_NAME_DEFAULT));
        initJniCommon();
        ImDrawList.nInit();
        ImDrawData.nInit();
        nInitInputTextData();
    }

    private ImGui() {
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include "jni_common.h"
     */

    private native static void initJniCommon(); /*
        Jni::InitCommon(env);
    */

    // Context creation and access
    // Each context create its own ImFontAtlas by default.
    // You may instance one yourself and pass it to CreateContext() to share a font atlas between imgui contexts.
    // None of those functions is reliant on the current context.
    //
    // BINDING NOTICE: Getting of the current context is not implemented since it's a part of internal API which is not exposed here.

    public native static void CreateContext(); /*
        ImGui::CreateContext();
    */

    // public static void CreateContext(ImFontAtlas sharedFontAtlas) TODO create context with fonts

    public native static void DestroyContext(); /*
        ImGui::DestroyContext();
    */

    // Main

    /**
     * Access the IO structure (mouse/keyboard/gamepad inputs, time, various configuration options/flags).
     */
    public static ImGuiIO GetIO() {
        return IMGUI_IO;
    }

    /**
     * Access the Style structure (colors, sizes). Always use PushStyleCol(), PushStyleVar() to modify style mid-frame.
     */
    public static ImGuiStyle GetStyle() {
        return IMGUI_STYLE;
    }

    /**
     * Start a new Dear ImGui frame, you can submit any command from this point until Render()/EndFrame().
     */
    public native static void NewFrame(); /*
        ImGui::NewFrame();
    */

    /**
     * Ends the Dear ImGui frame. automatically called by Render(), you likely don't need to call that yourself directly.
     * If you don't need to render data (skipping rendering) you may call EndFrame() but you'll have wasted CPU already!
     * If you don't need to render, better to not create any imgui windows and not call NewFrame() at all!
     */
    public native static void EndFrame(); /*
        ImGui::EndFrame();
    */

    /**
     * Ends the Dear ImGui frame, finalize the draw data.
     * You can get call GetDrawData() to obtain it and run your rendering function.
     */
    public native static void Render(); /*
        ImGui::Render();
    */

    /**
     * Valid after Render() and until the next call to NewFrame(). this is what you have to render.
     */
    public static ImDrawData GetDrawData() {
        ByteBuffer cmdByteBuffer = DRAW_DATA.cmdByteBuffer;
        ByteBuffer vByteBuffer = DRAW_DATA.vByteBuffer;
        ByteBuffer iByteBuffer = DRAW_DATA.iByteBuffer;
        vByteBuffer.position(0);
        iByteBuffer.position(0);
        cmdByteBuffer.position(0);
        DRAW_DATA.nFillDrawData(iByteBuffer, vByteBuffer, cmdByteBuffer);
        return DRAW_DATA;
    }

    // Demo, Debug, Information

    /**
     * Create Demo window (previously called ShowTestWindow). demonstrate most ImGui features.
     * Call this to learn about the library!
     */
    public native static void ShowDemoWindow(); /*
        ImGui::ShowDemoWindow();
    */

    public static void ShowDemoWindow(ImBool pOpen) {
        nShowDemoWindow(pOpen.data);
    }

    private native static void nShowDemoWindow(boolean[] pOpen); /*
        ImGui::ShowDemoWindow(&pOpen[0]);
    */

    /**
     * Create About window. display Dear ImGui version, credits and build/system information.
     */
    public native static void ShowAboutWindow(); /*
        ImGui::ShowAboutWindow();
    */

    public static void ShowAboutWindow(ImBool pOpen) {
        nShowAboutWindow(pOpen.data);
    }

    private native static void nShowAboutWindow(boolean[] pOpen); /*
        ImGui::ShowAboutWindow(&pOpen[0]);
    */

    /**
     * Create Metrics/Debug window.
     * Display Dear ImGui internals: draw commands (with individual draw calls and vertices), window list, basic internal state, etc.
     */
    public native static void ShowMetricsWindow(); /*
        ImGui::ShowMetricsWindow();
    */

    public static void ShowMetricsWindow(ImBool pOpen) {
        nShowMetricsWindow(pOpen.data);
    }

    private native static void nShowMetricsWindow(boolean[] pOpen); /*
        ImGui::ShowMetricsWindow(&pOpen[0]);
    */

    /**
     * Add style editor block (not a window).
     * You can pass in a reference ImGuiStyle structure to compare to, revert to and save to (else it uses the default style)
     */
    public native static void ShowStyleEditor(); /*
        ImGui::ShowStyleEditor();
    */

    /**
     * Add style selector block (not a window), essentially a combo listing the default styles.
     */
    public native static boolean ShowStyleSelector(String label); /*
        return ImGui::ShowStyleSelector(label);
    */

    /**
     * Add font selector block (not a window), essentially a combo listing the loaded fonts.
     */
    public native static void ShowFontSelector(String label); /*
        ImGui::ShowFontSelector(label);
    */

    /**
     * Add basic help/info block (not a window): how to manipulate ImGui as a end-user (mouse/keyboard controls).
     */
    public native static void ShowUserGuide(); /*
        ImGui::ShowUserGuide();
    */

    /**
     * Get the compiled version string e.g. "1.23" (essentially the compiled value for IMGUI_VERSION)
     */
    public native static String GetVersion(); /*
        return env->NewStringUTF(ImGui::GetVersion());
    */

    // Styles

    /**
     * New, recommended style (default)
     */
    public native static void StyleColorsDark(); /*
        ImGui::StyleColorsDark();
    */

    /**
     * Classic imgui style
     */
    public native static void StyleColorsClassic(); /*
        ImGui::StyleColorsClassic();
    */

    /**
     * Best used with borders and a custom, thicker font
     */
    public native static void StyleColorsLight(); /*
        ImGui::StyleColorsLight();
    */

    // Windows
    // - Begin() = push window to the stack and start appending to it. End() = pop window from the stack.
    // - You may append multiple times to the same window during the same frame.
    // - Passing 'bool* pOpen != NULL' shows a window-closing widget in the upper-right corner of the window,
    //   which clicking will set the boolean to false when clicked.
    // - Begin() return false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting
    //   anything to the window. Always call a matching End() for each Begin() call, regardless of its return value!
    //   [Important: due to legacy reason, this is inconsistent with most other functions such as BeginMenu/EndMenu,
    //    BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding BeginXXX function
    //    returned true. Begin and BeginChild are the only odd ones out. Will be fixed in a future update.]
    // - Note that the bottom of window stack always contains a window called "Debug".

    public native static boolean Begin(String title); /*
        return ImGui::Begin(title);
    */

    public static boolean Begin(String title, ImBool pOpen) {
        return nBegin(title, pOpen.data, 0);
    }

    public static boolean Begin(String title, ImBool pOpen, int imGuiWindowFlags) {
        return nBegin(title, pOpen.data, imGuiWindowFlags);
    }

    private native static boolean nBegin(String title, boolean[] pOpen, int imGuiWindowFlags); /*
        return ImGui::Begin(title, &pOpen[0], imGuiWindowFlags);
    */

    public native static void End(); /*
        ImGui::End();
    */

    // Child Windows
    // - Use child windows to begin into a self-contained independent scrolling/clipping regions within a host window. Child windows can embed their own child.
    // - For each independent axis of 'size': ==0.0f: use remaining host window size / >0.0f: fixed size / <0.0f: use remaining window size minus abs(size) / Each axis can use a different mode, e.g. ImVec2(0,400).
    // - BeginChild() returns false to indicate the window is collapsed or fully clipped, so you may early out and omit submitting anything to the window.
    //   Always call a matching EndChild() for each BeginChild() call, regardless of its return value [this is due to legacy reason and is inconsistent with most other functions such as BeginMenu/EndMenu, BeginPopup/EndPopup, etc. where the EndXXX call should only be called if the corresponding BeginXXX function returned true.]

    public native static boolean BeginChild(String strId); /*
        return ImGui::BeginChild(strId);
    */

    public static native boolean BeginChild(String str_id, float width, float height); /*
        return ImGui::BeginChild(str_id, ImVec2(width, height));
    */

    public static native boolean BeginChild(String str_id, float width, float height, boolean border); /*
        return ImGui::BeginChild(str_id, ImVec2(width, height), border);
    */

    public static native boolean BeginChild(String str_id, float width, float height, boolean border, int imGuiWindowFlags); /*
        return ImGui::BeginChild(str_id, ImVec2(width, height), border, imGuiWindowFlags);
    */

    public static native boolean BeginChild(int imGuiID); /*
        return ImGui::BeginChild(imGuiID);
    */

    public static native boolean BeginChild(int imGuiID, float width, float height, boolean border); /*
        return ImGui::BeginChild(imGuiID, ImVec2(width, height), border);
    */

    public static native boolean BeginChild(int imGuiID, float width, float height, boolean border, int imGuiWindowFlags); /*
        return ImGui::BeginChild(imGuiID, ImVec2(width, height), border, imGuiWindowFlags);
    */

    public static native void EndChild(); /*
        ImGui::EndChild();
    */

    // Windows Utilities
    // - "current window" = the window we are appending into while inside a Begin()/End() block. "next window" = next window we will Begin() into.

    public static native boolean IsWindowAppearing(); /*
        return ImGui::IsWindowAppearing();
    */

    public static native boolean IsWindowCollapsed(); /*
        return ImGui::IsWindowCollapsed();
    */

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    public static native boolean IsWindowFocused(); /*
        return ImGui::IsWindowFocused();
    */

    /**
     * Is current window focused? or its root/child, depending on flags. see flags for options.
     */
    public static native boolean IsWindowFocused(int imGuiFocusedFlags); /*
        return ImGui::IsWindowFocused(imGuiFocusedFlags);
    */

    /**
     * Is current window hovered (and typically: not blocked by a popup/modal)? see flags for options.
     * NB: If you are trying to check whether your mouse should be dispatched to imgui or to your app,
     * you should use the 'io.WantCaptureMouse' boolean for that! Please read the FAQ!
     */
    public static native boolean IsWindowHovered(); /*
        return ImGui::IsWindowHovered();
    */

    /**
     * Is current window hovered (and typically: not blocked by a popup/modal)? see flags for options.
     * NB: If you are trying to check whether your mouse should be dispatched to imgui or to your app,
     * you should use the 'io.WantCaptureMouse' boolean for that! Please read the FAQ!
     */
    public static native boolean IsWindowHovered(int imGuiHoveredFlags); /*
        return ImGui::IsWindowHovered(imGuiHoveredFlags);
    */

    /**
     * Get draw list associated to the current window, to append your own drawing primitives
     */
    public static ImDrawList GetWindowDrawList() {
        return IM_DRAW_LIST;
    }

    /**
     * Get current window position in screen space (useful if you want to do your own drawing via the DrawList API)
     */
    public native static void GetWindowPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowPos(), dstImVec2);
    */

    /**
     * Get current window size
     */
    public static native void GetWindowSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowSize(), dstImVec2);
    */

    /**
     * Get current window width (shortcut for GetWindowSize().x)
     */
    public static native float GetWindowWidth(); /*
        return ImGui::GetWindowWidth();
    */

    /**
     * Get current window height (shortcut for GetWindowSize().y)
     */
    public static native float GetWindowHeight(); /*
        return ImGui::GetWindowHeight();
     */

    // Prefer using SetNextXXX functions (before Begin) rather that SetXXX functions (after Begin).

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static native void SetNextWindowPos(float x, float y); /*
        ImGui::SetNextWindowPos(ImVec2(x, y));
     */

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static native void SetNextWindowPos(float x, float y, int imGuiCond); /*
        ImGui::SetNextWindowPos(ImVec2(x, y), imGuiCond);
     */

    /**
     * Set next window position. call before Begin(). use pivot=(0.5f,0.5f) to center on given point, etc.
     */
    public static native void SetNextWindowPos(float x, float y, int imGuiCond, float pivotX, float pivotY); /*
        ImGui::SetNextWindowPos(ImVec2(x, y), imGuiCond, ImVec2(pivotX, pivotY));
     */

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static native void SetNextWindowSize(float width, float height); /*
        ImGui::SetNextWindowSize(ImVec2(width, height));
    */

    /**
     * Set next window size. set axis to 0.0f to force an auto-fit on this axis. call before Begin()
     */
    public static native void SetNextWindowSize(float width, float height, int imGuiCond); /*
        ImGui::SetNextWindowSize(ImVec2(width, height), imGuiCond);
    */

    /**
     * Set next window size limits. use -1,-1 on either X/Y axis to preserve the current size. Sizes will be rounded down.
     */
    public static native void SetNextWindowSizeConstraints(float minWidth, float minHeight, float maxWidth, float maxHeight); /*
        ImGui::SetNextWindowSizeConstraints(ImVec2(minWidth, minHeight), ImVec2(maxWidth, maxHeight));
    */

    /**
     * Set next window content size (~ scrollable client area, which enforce the range of scrollbars).
     * Not including window decorations (title bar, menu bar, etc.) nor WindowPadding. set an axis to 0.0f to leave it automatic. call before Begin()
     */
    public static native void SetNextWindowContentSize(float width, float height); /*
        ImGui::SetNextWindowContentSize(ImVec2(width, height));
    */

    /**
     * Set next window collapsed state. call before Begin()
     */
    public static native void SetNextWindowCollapsed(boolean collapsed); /*
        ImGui::SetNextWindowCollapsed(collapsed);
    */

    /**
     * Set next window collapsed state. call before Begin()
     */
    public static native void SetNextWindowCollapsed(boolean collapsed, int imGuiCond); /*
        ImGui::SetNextWindowCollapsed(collapsed, imGuiCond);
    */

    /**
     * Set next window to be focused / top-most. call before Begin()
     */
    public static native void SetNextWindowFocus(); /*
        ImGui::SetNextWindowFocus();
    */

    /**
     * Set next window background color alpha. helper to easily modify ImGuiCol_WindowBg/ChildBg/PopupBg.
     * You may also use ImGuiWindowFlags_NoBackground.
     */
    public static native void SetNextWindowBgAlpha(float alpha); /*
        ImGui::SetNextWindowBgAlpha(alpha);
    */

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static native void SetWindowPos(float x, float y); /*
        ImGui::SetWindowPos(ImVec2(x, y));
    */

    /**
     * (not recommended) set current window position - call within Begin()/End().
     * Prefer using SetNextWindowPos(), as this may incur tearing and side-effects.
     */
    public static native void SetWindowPos(float x, float y, int imGuiCond); /*
        ImGui::SetWindowPos(ImVec2(x, y), imGuiCond);
    */

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static native void SetWindowSize(float width, float height); /*
        ImGui::SetWindowSize(ImVec2(width, height));
    */

    /**
     * (not recommended) set current window size - call within Begin()/End(). set to ImVec2(0,0) to force an auto-fit.
     * Prefer using SetNextWindowSize(), as this may incur tearing and minor side-effects.
     */
    public static native void SetWindowSize(float width, float height, int imGuiCond); /*
        ImGui::SetWindowSize(ImVec2(width, height), imGuiCond);
    */

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    public static native void SetWindowCollapsed(boolean collapsed); /*
        ImGui::SetWindowCollapsed(collapsed);
    */

    /**
     * (not recommended) set current window collapsed state. prefer using SetNextWindowCollapsed().
     */
    public static native void SetWindowCollapsed(boolean collapsed, int imGuiCond); /*
        ImGui::SetWindowCollapsed(collapsed, imGuiCond);
    */

    /**
     * (not recommended) set current window to be focused / top-most. prefer using SetNextWindowFocus().
     */
    public static native void SetWindowFocus(); /*
        ImGui::SetWindowFocus();
    */

    /**
     * Set font scale. Adjust IO.FontGlobalScale if you want to scale all windows.
     * This is an old API! For correct scaling, prefer to reload font + rebuild ImFontAtlas + call style.ScaleAllSizes().
     */
    public native void SetWindowFontScale(float scale); /*
        ImGui::SetWindowFontScale(scale);
    */

    /**
     * Set named window position.
     */
    public static native void SetWindowPos(String name, float x, float y); /*
        ImGui::SetWindowPos(name, ImVec2(x, y));
    */

    /**
     * Set named window position.
     */
    public static native void SetWindowPos(String name, float x, float y, int imGuiCond); /*
        ImGui::SetWindowPos(name, ImVec2(x, y), imGuiCond);
    */

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static native void SetWindowSize(String name, float x, float y); /*
        ImGui::SetWindowSize(name, ImVec2(x, y));
    */

    /**
     * Set named window size. set axis to 0.0f to force an auto-fit on this axis.
     */
    public static native void SetWindowSize(String name, float x, float y, int imGuiCond); /*
        ImGui::SetWindowSize(name, ImVec2(x, y), imGuiCond);
    */

    /**
     * Set named window collapsed state
     */
    public static native void SetWindowCollapsed(String name, boolean collapsed); /*
        ImGui::SetWindowCollapsed(name, collapsed);
    */

    /**
     * Set named window collapsed state
     */
    public static native void SetWindowCollapsed(String name, boolean collapsed, int imGuiCond); /*
        ImGui::SetWindowCollapsed(name, collapsed, imGuiCond);
    */

    /**
     * Set named window to be focused / top-most. Use NULL to remove focus.
     */
    public static native void SetWindowFocus(String name); /*MANUAL
        if (obj_name == NULL)
            ImGui::SetWindowFocus(NULL);
        else {
            char* name = (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
            ImGui::SetWindowFocus(name);
            env->ReleaseStringUTFChars(obj_name, name);
        }
    */

    // Content region
    // - Those functions are bound to be redesigned soon (they are confusing, incomplete and return values in local window coordinates which increases confusion)

    /**
     * Current content boundaries (typically window boundaries including scrolling, or current column boundaries), in windows coordinates
     */
    public static native void GetContentRegionMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionMax(), dstImVec2);
    */

    /**
     * == GetContentRegionMax() - GetCursorPos()
     */
    public static native void GetContentRegionAvail(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionAvail(), dstImVec2);
    */

    /**
     * Content boundaries min (roughly (0,0)-Scroll), in window coordinates
     */
    public static native float GetContentRegionAvailWidth(); /*
        return ImGui::GetContentRegionAvailWidth();
    */

    /**
     * Content boundaries max (roughly (0,0)+Size-Scroll) where Size can be override with SetNextWindowContentSize(), in window coordinates
     */
    public static native void GetWindowContentRegionMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowContentRegionMin(), dstImVec2);
    */

    public static native void GetWindowContentRegionMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetWindowContentRegionMax(), dstImVec2);
    */

    // Windows Scrolling

    /**
     * Get scrolling amount [0..GetScrollMaxX()]
     */
    public static native float GetScrollX(); /*
        return ImGui::GetScrollX();
    */

    /**
     * Get scrolling amount [0..GetScrollMaxY()]
     */
    public static native float GetScrollY(); /*
        return ImGui::GetScrollY();
    */

    /**
     * Get maximum scrolling amount ~~ ContentSize.X - WindowSize.X
     */
    public static native float GetScrollMaxX(); /*
        return ImGui::GetScrollMaxX();
    */

    /**
     * Get maximum scrolling amount ~~ ContentSize.Y - WindowSize.Y
     */
    public static native float GetScrollMaxY(); /*
        return ImGui::GetScrollMaxY();
    */

    /**
     * Set scrolling amount [0..GetScrollMaxX()]
     */
    public static native void SetScrollX(float scrollX); /*
        ImGui::SetScrollX(scrollX);
    */

    /**
     * Set scrolling amount [0..GetScrollMaxY()]
     */
    public static native void SetScrollY(float scrollY); /*
        ImGui::SetScrollY(scrollY);
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void SetScrollHereX(); /*
        ImGui::SetScrollHereX();
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_x_ratio=0.0: left, 0.5: center, 1.0: right.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void SetScrollHereX(float centerXRatio); /*
        ImGui::SetScrollHereX(centerXRatio);
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void SetScrollHereY(); /*
        ImGui::SetScrollHereY();
    */

    /**
     * Adjust scrolling amount to make current cursor position visible. center_y_ratio=0.0: top, 0.5: center, 1.0: bottom.
     * When using to make a "default/current item" visible, consider using SetItemDefaultFocus() instead.
     */
    public static native void SetScrollHereY(float centerYRatio); /*
        ImGui::SetScrollHereY(centerYRatio);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void SetScrollFromPosX(float localX); /*
        ImGui::SetScrollFromPosX(localX);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void SetScrollFromPosX(float localX, float centerXRatio); /*
        ImGui::SetScrollFromPosX(localX, centerXRatio);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void SetScrollFromPosY(float localY); /*
        ImGui::SetScrollFromPosY(localY);
    */

    /**
     * Adjust scrolling amount to make given position visible. Generally GetCursorStartPos() + offset to compute a valid position.
     */
    public static native void SetScrollFromPosY(float localY, float centerYRatio); /*
        ImGui::SetScrollFromPosY(localY, centerYRatio);
    */

    // Parameters stacks (shared)

    // TODO void PushFont(ImFont* font);

    public static native void PopFont(); /*
        ImGui::PopFont();
    */

    public static native void PushStyleColor(int imGuiCol, long col); /*
        ImGui::PushStyleColor(imGuiCol, col);
    */

    public static native void PushStyleColor(int imGuiCol, float r, float g, float b, float a); /*
        ImGui::PushStyleColor(imGuiCol, ImVec4(r, g, b, a));
    */

    public static native void PopStyleColor(); /*
        ImGui::PopStyleColor();
    */

    public static native void PopStyleColor(int count); /*
        ImGui::PopStyleColor(count);
    */

    public static native void PushStyleVar(int imGuiStyleVar, float val); /*
        ImGui::PushStyleVar(imGuiStyleVar, val);
    */

    public static native void PushStyleVar(int imGuiStyleVar, float val_x, float val_y); /*
        ImGui::PushStyleVar(imGuiStyleVar, ImVec2(val_x, val_y));
    */

    public static native void PopStyleVar(); /*
        ImGui::PopStyleVar();
    */

    public static native void PopStyleVar(int count); /*
        ImGui::PopStyleVar(count);
    */

    /**
     * Retrieve style color as stored in ImGuiStyle structure. use to feed back into PushStyleColor(),
     * otherwise use GetColorU32() to get style color with style alpha baked in.
     */
    public static native void GetStyleColorVec4(int imGuiStyleVar, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, ImGui::GetStyleColorVec4(imGuiStyleVar), dstImVec4);
    */

    // TODO ImFont* GetFont();

    /**
     * Get current font size (= height in pixels) of current font with current scale applied
     */
    public static native int GetFontSize(); /*
        return ImGui::GetFontSize();
    */

    /**
     * Get UV coordinate for a while pixel, useful to draw custom shapes via the ImDrawList API
     */
    public static native void GetFontTexUvWhitePixel(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetFontTexUvWhitePixel(), dstImVec2);
    */

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier
     */
    public static native long GetColorU32(int imGuiCol); /*
        return ImGui::GetColorU32((ImGuiCol)imGuiCol);
    */

    /**
     * Retrieve given style color with style alpha applied and optional extra alpha multiplier
     */
    public static native long GetColorU32(int imGuiCol, float alpha_mul); /*
        return ImGui::GetColorU32(imGuiCol, alpha_mul);
    */

    /**
     * Retrieve given color with style alpha applied
     */
    public static native long GetColorU32(float col_r, float col_g, float col_b, float col_a); /*
        return ImGui::GetColorU32(ImVec4(col_r, col_g, col_b, col_a));
    */

    /**
     * Retrieve given color with style alpha applied
     */
    public static native long GetColorU32(long col); /*
        return ImGui::GetColorU32((ImU32)col);
    */

    // Parameters stacks (current window)

    /**
     * Set width of items for common large "item+label" widgets. >0.0f: width in pixels,
     * <0.0f align xx pixels to the right of window (so -1.0f always align width to the right side). 0.0f = default to ~2/3 of windows width,
     */
    public static native void PushItemWidth(float item_width); /*
        ImGui::PushItemWidth(item_width);
    */

    public static native void PopItemWidth(); /*
        ImGui::PopItemWidth();
    */

    /**
     * Set width of the _next_ common large "item+label" widget. >0.0f: width in pixels,
     * <0.0f align xx pixels to the right of window (so -1.0f always align width to the right side)
     */
    public static native void SetNextItemWidth(float item_width); /*
        ImGui::SetNextItemWidth(item_width);
    */

    /**
     * Width of item given pushed settings and current cursor position. NOT necessarily the width of last item unlike most 'Item' functions.
     */
    public static native float CalcItemWidth(); /*
        return ImGui::CalcItemWidth();
    */

    /**
     * Word-wrapping for Text*() commands. < 0.0f: no wrapping; 0.0f: wrap to end of window (or column); > 0.0f: wrap at 'wrap_pos_x'
     * position in window local space
     */
    public static native void PushTextWrapPos(); /*
        ImGui::PushTextWrapPos();
    */

    /**
     * Word-wrapping for Text*() commands. < 0.0f: no wrapping; 0.0f: wrap to end of window (or column); > 0.0f: wrap at 'wrap_pos_x'
     * position in window local space
     */
    public static native void PushTextWrapPos(float wrap_local_pos_x); /*
        ImGui::PushTextWrapPos(wrap_local_pos_x);
    */

    public static native void PopTextWrapPos(); /*
        ImGui::PopTextWrapPos();
    */

    /**
     * Allow focusing using TAB/Shift-TAB, enabled by default but you can disable it for certain widgets
     */
    public static native void PushAllowKeyboardFocus(boolean allow_keyboard_focus); /*
        ImGui::PushAllowKeyboardFocus(allow_keyboard_focus);
    */

    public static native void PopAllowKeyboardFocus(); /*
        ImGui::PopAllowKeyboardFocus();
    */

    /**
     * In 'repeat' mode, Button*() functions return repeated true in a typematic manner (using io.KeyRepeatDelay/io.KeyRepeatRate setting).
     * Note that you can call IsItemActive() after any Button() to tell if the button is held in the current frame.
     */
    public static native void PushButtonRepeat(boolean repeat); /*
        ImGui::PushButtonRepeat(repeat);
    */

    public static native void PopButtonRepeat(); /*
        ImGui::PopButtonRepeat();
    */

    // Cursor / Layout
    // - By "cursor" we mean the current output position.
    // - The typical widget behavior is to output themselves at the current cursor position, then move the cursor one line down.
    // - You can call SameLine() between widgets to undo the last carriage return and output at the right of the preceeding widget.

    /**
     * Separator, generally horizontal. inside a menu bar or in horizontal layout mode, this becomes a vertical separator.
     */
    public static native void Separator(); /*
        ImGui::Separator();
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static native void SameLine(); /*
        ImGui::SameLine();
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static native void SameLine(float offsetFromStartX); /*
        ImGui::SameLine(offsetFromStartX);
    */

    /**
     * Call between widgets or groups to layout them horizontally. X position given in window coordinates.
     */
    public static native void SameLine(float offsetFromStartX, float spacing); /*
        ImGui::SameLine(offsetFromStartX, spacing);
    */

    /**
     * Undo a SameLine() or force a new line when in an horizontal-layout context.
     */
    public static native void NewLine(); /*
        ImGui::NewLine();
    */

    /**
     * Add vertical spacing.
     */
    public static native void Spacing(); /*
        ImGui::Spacing();
    */

    /**
     * Add a dummy item of given size. unlike InvisibleButton(), Dummy() won't take the mouse click or be navigable into.
     */
    public static native void Dummy(float width, float height); /*
        ImGui::Dummy(ImVec2(width, height));
    */

    /**
     * Move content position toward the right, by style.IndentSpacing or indent_w if != 0
     */
    public static native void Indent(); /*
        ImGui::Indent();
    */

    /**
     * Move content position toward the right, by style.IndentSpacing or indent_w if != 0
     */
    public static native void Indent(float indentW); /*
        ImGui::Indent(indentW);
    */

    /**
     * Move content position back to the left, by style.IndentSpacing or indent_w if != 0
     */
    public static native void Unindent(); /*
        ImGui::Unindent();
    */

    /**
     * Move content position back to the left, by style.IndentSpacing or indent_w if != 0
     */
    public static native void Unindent(float indentW); /*
        ImGui::Unindent(indentW);
    */

    /**
     * Lock horizontal starting position
     */
    public static native void BeginGroup(); /*
        ImGui::BeginGroup();
    */

    /**
     * Unlock horizontal starting position + capture the whole group bounding box into one "item" (so you can use IsItemHovered() or layout primitives such as SameLine() on whole group, etc.)
     */
    public static native void EndGroup(); /*
        ImGui::EndGroup();
    */

    // (some functions are using window-relative coordinates, such as: GetCursorPos, GetCursorStartPos, GetContentRegionMax, GetWindowContentRegion* etc.
    //  other functions such as GetCursorScreenPos or everything in ImDrawList::
    //  are using the main, absolute coordinate system.
    //  GetWindowPos() + GetCursorPos() == GetCursorScreenPos() etc.)

    /**
     * Cursor position in window coordinates (relative to window position)
     */
    public static native void GetCursorPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorPos(), dstImVec2);
    */

    public static native float GetCursorPosX(); /*
        return ImGui::GetCursorPosX();
    */

    public static native float GetCursorPosY(); /*
        return ImGui::GetCursorPosY();
    */

    public static native void SetCursorPos(float x, float y); /*
        ImGui::SetCursorPos(ImVec2(x, y));
    */

    public static native void SetCursorPosX(float x); /*
        ImGui::SetCursorPosX(x);
    */

    public static native void SetCursorPosY(float y); /*
        ImGui::SetCursorPosY(y);
    */

    /**
     * Initial cursor position in window coordinates
     */
    public static native void GetCursorStartPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorStartPos(), dstImVec2);
    */

    /**
     * Cursor position in absolute screen coordinates [0..io.DisplaySize] (useful to work with ImDrawList API)
     */
    public static native void GetCursorScreenPos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetCursorScreenPos(), dstImVec2);
     */

    /**
     * Cursor position in absolute screen coordinates [0..io.DisplaySize]
     */
    public static native void SetCursorScreenPos(float x, float y); /*
        ImGui::SetCursorScreenPos(ImVec2(x, y));
    */

    /**
     * Vertically align upcoming text baseline to FramePadding.y so that it will align properly to regularly framed items (call if you have text on a line before a framed item)
     */
    public static native void AlignTextToFramePadding(); /*
        ImGui::AlignTextToFramePadding();
    */

    /**
     * ~ FontSize
     */
    public static native float GetTextLineHeight(); /*
        return ImGui::GetTextLineHeight();
    */

    /**
     * ~ FontSize + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of text)
     */
    public static native float GetTextLineHeightWithSpacing(); /*
        return ImGui::GetTextLineHeightWithSpacing();
    */

    /**
     * ~ FontSize + style.FramePadding.y * 2
     */
    public static native float GetFrameHeight(); /*
        return ImGui::GetFrameHeight();
    */

    /**
     * ~ FontSize + style.FramePadding.y * 2 + style.ItemSpacing.y (distance in pixels between 2 consecutive lines of framed widgets)
     */
    public static native float GetFrameHeightWithSpacing(); /*
        return ImGui::GetFrameHeightWithSpacing();
    */

    // ID stack/scopes
    // - Read the FAQ for more details about how ID are handled in dear imgui. If you are creating widgets in a loop you most
    //   likely want to push a unique identifier (e.g. object pointer, loop index) to uniquely differentiate them.
    // - The resulting ID are hashes of the entire stack.
    // - You can also use the "Label##foobar" syntax within widget label to distinguish them from each others.
    // - In this header file we use the "label"/"name" terminology to denote a string that will be displayed and used as an ID,
    //   whereas "str_id" denote a string that is only used as an ID and not normally displayed.

    /**
     * Push string into the ID stack (will hash string).
     */
    public static native void PushID(String str_id); /*
        ImGui::PushID(str_id);
    */

    /**
     * Push string into the ID stack (will hash string).
     */
    public static native void PushID(String str_id_begin, String str_id_end); /*
        ImGui::PushID(str_id_begin, str_id_end);
    */

    /**
     * Push pointer into the ID stack (will hash pointer).
     */
    public static native void PushID(long ptr_id); /*
        ImGui::PushID((void*)ptr_id);
    */

    /**
     * Push integer into the ID stack (will hash integer).
     */
    public static native void PushID(int int_id); /*
        ImGui::PushID(int_id);
    */

    /**
     * Pop from the ID stack.
     */
    public static native void PopID(); /*
        ImGui::PopID();
    */

    /**
     * Calculate unique ID (hash of whole ID stack + given parameter). e.g. if you want to query into ImGuiStorage yourself
     */
    public static native long GetID(String str_id); /*
        return ImGui::GetID(str_id);
    */

    public static native long GetID(String str_id_begin, String str_id_end); /*
        return ImGui::GetID(str_id_begin, str_id_end);
    */

    public static native long GetID(long ptr_id); /*
        return ImGui::GetID((void*)ptr_id);
    */

    // Widgets: Text

    /**
     * Raw text without formatting. Roughly equivalent to Text("%s", text) but:
     * A) doesn't require null terminated string if 'text_end' is specified,
     * B) it's faster, no memory copy is done, no buffer size limits, recommended for long chunks of text.
     */
    public static native void TextUnformatted(String text); /*
        ImGui::TextUnformatted(text);
    */

    /**
     * Raw text without formatting. Roughly equivalent to Text("%s", text) but:
     * A) doesn't require null terminated string if 'text_end' is specified,
     * B) it's faster, no memory copy is done, no buffer size limits, recommended for long chunks of text.
     */
    public static native void TextUnformatted(String text, String text_end); /*
        ImGui::TextUnformatted(text, text_end);
    */

    /**
     * Formatted text
     * BINDING NOTICE: Since all text formatting could be done on Java side, this call is equal to {@link ImGui#TextUnformatted(String)}.
     */
    public static native void Text(String text); /*
        ImGui::TextUnformatted(text);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, col); Text(fmt, ...); PopStyleColor();
     */
    public static native void TextColored(float r, float g, float b, float a, String text); /*
        ImGui::TextColored(ImVec4(r, g, b, a), text);
    */

    /**
     * Shortcut for PushStyleColor(ImGuiCol_Text, style.Colors[ImGuiCol_TextDisabled]); Text(fmt, ...); PopStyleColor();
     */
    public static native void TextDisabled(String text); /*
        ImGui::TextDisabled(text);
    */

    /**
     * Shortcut for PushTextWrapPos(0.0f); Text(fmt, ...); PopTextWrapPos();.
     * Note that this won't work on an auto-resizing window if there's no other widgets to extend the window width,
     * yoy may need to set a size using SetNextWindowSize().
     */
    public static native void TextWrapped(String text); /*
        ImGui::TextWrapped(text);
    */

    /**
     * Display text+label aligned the same way as value+label widgets
     */
    public static native void LabelText(String label, String text); /*
        ImGui::LabelText(label, text);
    */

    /**
     * Shortcut for Bullet()+Text()
     */
    public static native void BulletText(String text); /*
        ImGui::BulletText(text);
    */

    // Widgets: Main
    // - Most widgets return true when the value has been changed or when pressed/selected
    // - You may also use one of the many IsItemXXX functions (e.g. IsItemActive, IsItemHovered, etc.) to query widget state.

    /**
     * Button
     */
    public static native boolean Button(String label); /*
        return ImGui::Button(label);
    */

    /**
     * Button
     */
    public static native boolean Button(String label, float width, float height); /*
        return ImGui::Button(label, ImVec2(width, height));
    */

    /**
     * Button with FramePadding=(0,0) to easily embed within text
     */
    public static native boolean SmallButton(String label); /*
        return ImGui::SmallButton(label);
    */

    /**
     * Button behavior without the visuals, frequently useful to build custom behaviors using the public api (along with IsItemActive, IsItemHovered, etc.)
     */
    public static native boolean InvisibleButton(String strId, float width, float height); /*
        return ImGui::InvisibleButton(strId, ImVec2(width, height));
    */

    /**
     * Square button with an arrow shape
     */
    public static native boolean ArrowButton(String strId, int dir); /*
        return ImGui::ArrowButton(strId, dir);
    */

    public static native void Image(int textureID, float sizeX, float sizeY); /*
        ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY));
    */

    public static native void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y); /*
        ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y));
    */

    public static native void Image(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, float tint_color_r, float tint_color_g, float tint_color_b, float tint_color_a, float border_col_r, float border_col_g, float border_col_b, float border_col_a); /*
        ImGui::Image((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), ImVec4(tint_color_r, tint_color_g, tint_color_b, tint_color_a), ImVec4(border_col_r, border_col_g, border_col_b, border_col_a));
    */

    /**
     * <0 frame_padding uses default frame padding settings. 0 for no padding
     */
    public static native boolean ImageButton(int textureID, float sizeX, float sizeY); /*
        return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY));
    */

    /**
     * <0 frame_padding uses default frame padding settings. 0 for no padding
     */
    public static native boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding); /*
        return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), frame_padding);
    */

    /**
     * <0 frame_padding uses default frame padding settings. 0 for no padding
     */
    public static native boolean ImageButton(int textureID, float sizeX, float sizeY, float uv0_x, float uv0_y, float uv1_x, float uv1_y, int frame_padding, float bg_color_r, float bg_color_g, float bg_color_b, float bg_color_a, float tint_col_r, float tint_col_g, float tint_col_b, float tint_col_a); /*
        return ImGui::ImageButton((ImTextureID)textureID, ImVec2(sizeX, sizeY), ImVec2(uv0_x, uv0_y), ImVec2(uv1_x, uv1_y), frame_padding, ImVec4(bg_color_r, bg_color_g, bg_color_b, bg_color_a), ImVec4(tint_col_r, tint_col_g, tint_col_b, tint_col_a));
    */

    public static boolean Checkbox(String label, ImBool active) {
        return nCheckbox(label, active.data);
    }

    private static native boolean nCheckbox(String label, boolean[] data); /*
        return ImGui::Checkbox(label, &data[0]);
    */

    public static boolean CheckboxFlags(String label, ImInt v, int flagsValue) {
        return nCheckboxFlags(label, v.data, flagsValue);
    }

    private static native boolean nCheckboxFlags(String label, int[] data, int flagsValue); /*
        return ImGui::CheckboxFlags(label, (unsigned int*)&data[0], flagsValue);
    */

    /**
     * Use with e.g. if (RadioButton("one", my_value==1)) { my_value = 1; }
     */
    public static native boolean RadioButton(String label, boolean active); /*
        return ImGui::RadioButton(label, active);
    */

    /**
     * Shortcut to handle the above pattern when value is an integer
     */
    public static boolean RadioButton(String label, ImInt v, int v_button) {
        return nRadioButton(label, v.data, v_button);
    }

    private static native boolean nRadioButton(String label, int[] data, int v_button); /*
        return ImGui::RadioButton(label, &data[0], v_button);
    */

    public static native void ProgressBar(float fraction); /*
        ImGui::ProgressBar(fraction);
    */

    public static native void ProgressBar(float fraction, float size_arg_x, float size_arg_y); /*
        ImGui::ProgressBar(fraction, ImVec2(size_arg_x, size_arg_y));
    */

    public static native void ProgressBar(float fraction, float size_arg_x, float size_arg_y, String overlay); /*
        ImGui::ProgressBar(fraction, ImVec2(size_arg_x, size_arg_y), overlay);
    */

    /**
     * Draw a small circle and keep the cursor on the same line. advance cursor x position by GetTreeNodeToLabelSpacing(), same distance that TreeNode() uses
     */
    public static native void Bullet(); /*
        ImGui::Bullet();
    */

    // Widgets: Combo Box
    // - The BeginCombo()/EndCombo() api allows you to manage your contents and selection state however you want it, by creating e.g. Selectable() items.
    // - The old Combo() api are helpers over BeginCombo()/EndCombo() which are kept available for convenience purpose.

    public static native  boolean BeginCombo(String label, String preview_value); /*
        return ImGui::BeginCombo(label, preview_value);
     */

    public static native  boolean BeginCombo(String label, String preview_value, int imGuiComboFlags); /*
        return ImGui::BeginCombo(label, preview_value, imGuiComboFlags);
    */

    /**
     * Only call EndCombo() if BeginCombo() returns true!
     */
    public static native  void EndCombo(); /*
        ImGui::EndCombo();
    */

    public static boolean Combo(String label, ImInt current_item, String[] items, int items_count) {
        return nCombo(label, current_item.data, items, items_count, -1);
    }

    public static boolean Combo(String label, ImInt current_item, String[] items, int items_count, int popup_max_height_in_items) {
        return nCombo(label, current_item.data, items, items_count, popup_max_height_in_items);
    }

    private static native  boolean nCombo(String label, int[] current_item, String[] items, int items_count, int popup_max_height_in_items); /*
        const char* listbox_items[items_count];
        for(int i = 0; i < items_count; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(items, i);
            const char* rawString = env->GetStringUTFChars(string, 0);
            listbox_items[i] = rawString;
        }
        return ImGui::Combo(label, &current_item[0], listbox_items, items_count, popup_max_height_in_items);
    */

    /**
     * Separate items with \0 within a string, end item-list with \0\0. e.g. "One\0Two\0Three\0"
     */
    public static boolean Combo(String label, ImInt current_item, String items_separated_by_zeros) {
        return nCombo(label, current_item.data, items_separated_by_zeros, -1);
    }

    public static boolean Combo(String label, ImInt current_item, String items_separated_by_zeros, int popup_max_height_in_items) {
        return nCombo(label, current_item.data, items_separated_by_zeros, popup_max_height_in_items);
    }

    private static native boolean nCombo(String label, int[] current_item, String items_separated_by_zeros, int popup_max_height_in_items); /*
        return ImGui::Combo(label, &current_item[0], items_separated_by_zeros, popup_max_height_in_items);
    */

    // Widgets: Drags
    // - CTRL+Click on any drag box to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - For all the Float2/Float3/Float4/Int2/Int3/Int4 versions of every functions, note that a 'float v[X]' function argument is the same as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible. You can pass address of your first element out of a contiguous set, e.g. &myvector.x
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.
    // - Speed are per-pixel of mouse movement (v_speed=0.2f: mouse needs to move by 5 pixels to increase value by 1). For gamepad/keyboard navigation, minimum speed is Max(v_speed, minimum_step_at_given_precision).
    // - Use v_min < v_max to clamp edits to given limits. Note that CTRL+Click manual input can override those limits.
    // - Use v_min > v_max to lock edits.

    public static native boolean DragFloat(String label, float[] v); /*
        return ImGui::DragFloat(label, &v[0]);
    */

    public static native boolean DragFloat(String label, float[] v, float v_speed); /*
        return ImGui::DragFloat(label, &v[0], v_speed);
    */

    /**
     * If v_min >= v_max we have no bound
     */
    public static native boolean DragFloat(String label, float[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max);
    */

    /**
     * If v_min >= v_max we have no bound
     */
    public static native boolean DragFloat(String label, float[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max, format);
    */

    /**
     * If v_min >= v_max we have no bound
     */
    public static native boolean DragFloat(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power); /*
        return ImGui::DragFloat(label, &v[0], v_speed, v_min, v_max, format, power);
    */

    public static native boolean DragFloat2(String label, float[] v); /*
        return ImGui::DragFloat2(label, v);
    */

    public static native boolean DragFloat2(String label, float[] v, float v_speed); /*
        return ImGui::DragFloat2(label, v, v_speed);
    */

    public static native boolean DragFloat2(String label, float[] v, float v_speed, float v_min); /*
        return ImGui::DragFloat2(label, v, v_speed, v_min);
    */

    public static native boolean DragFloat2(String label, float[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragFloat2(label, v, v_speed, v_min, v_max);
    */

    public static native boolean DragFloat2(String label, float[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragFloat2(label, v, v_speed, v_min, v_max, format);
    */

    public static native boolean DragFloat2(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power); /*
        return ImGui::DragFloat2(label, v, v_speed, v_min, v_max, format, power);
    */

    public static native boolean DragFloat3(String label, float[] v); /*
        return ImGui::DragFloat3(label, v);
    */

    public static native boolean DragFloat3(String label, float[] v, float v_speed); /*
        return ImGui::DragFloat3(label, v, v_speed);
    */

    public static native boolean DragFloat3(String label, float[] v, float v_speed, float v_min); /*
        return ImGui::DragFloat3(label, v, v_speed, v_min);
    */

    public static native boolean DragFloat3(String label, float[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragFloat3(label, v, v_speed, v_min, v_max);
    */

    public static native boolean DragFloat3(String label, float[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragFloat3(label, v, v_speed, v_min, v_max, format);
    */

    public static native boolean DragFloat3(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power); /*
        return ImGui::DragFloat3(label, v, v_speed, v_min, v_max, format, power);
    */

    public static native boolean DragFloat4(String label, float[] v); /*
        return ImGui::DragFloat4(label, v);
    */

    public static native boolean DragFloat4(String label, float[] v, float v_speed); /*
        return ImGui::DragFloat4(label, v, v_speed);
    */

    public static native boolean DragFloat4(String label, float[] v, float v_speed, float v_min); /*
        return ImGui::DragFloat4(label, v, v_speed, v_min);
    */

    public static native boolean DragFloat4(String label, float[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragFloat4(label, v, v_speed, v_min, v_max);
    */

    public static native boolean DragFloat4(String label, float[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragFloat4(label, v, v_speed, v_min, v_max, format);
    */

    public static native boolean DragFloat4(String label, float[] v, float v_speed, float v_min, float v_max, String format, float power); /*
        return ImGui::DragFloat4(label, v, v_speed, v_min, v_max, format, power);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0]);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed, float v_min); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed, float v_min, float v_max); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max);
    */

    public static native boolean DragFloatRange2(String label, float[] v_current_min, float[] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max, float power); /*
        return ImGui::DragFloatRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max, power);
    */

    public static native boolean DragInt(String label, int[] v); /*
        return ImGui::DragInt(label, &v[0]);
    */

    public static native boolean DragInt(String label, int[] v, float v_speed); /*
        return ImGui::DragInt(label, &v[0], v_speed);
    */

    public static native boolean DragInt(String label, int[] v, float v_speed, float v_min); /*
        return ImGui::DragInt(label, &v[0], v_speed, v_min);
    */

    /**
     * If v_min >= v_max we have no bound
     */
    public static native boolean DragInt(String label, int[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragInt(label, &v[0], v_speed, v_min, v_max);
    */

    /**
     * If v_min >= v_max we have no bound
     */
    public static native boolean DragInt(String label, int[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragInt(label, &v[0], v_speed, v_min, v_max, format);
    */

    public static native boolean DragInt2(String label, int[] v); /*
        return ImGui::DragInt2(label, v);
    */

    public static native boolean DragInt2(String label, int[] v, float v_speed); /*
        return ImGui::DragInt2(label, v, v_speed);
    */

    public static native boolean DragInt2(String label, int[] v, float v_speed, float v_min); /*
        return ImGui::DragInt2(label, v, v_speed, v_min);
    */

    public static native boolean DragInt2(String label, int[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragInt2(label, v, v_speed, v_min, v_max);
    */

    public static native boolean DragInt2(String label, int[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragInt2(label, v, v_speed, v_min, v_max, format);
    */

    public static native boolean DragInt3(String label, int[] v); /*
        return ImGui::DragInt2(label, v);
    */

    public static native boolean DragInt3(String label, int[] v, float v_speed); /*
        return ImGui::DragInt2(label, v, v_speed);
    */

    public static native boolean DragInt3(String label, int[] v, float v_speed, float v_min); /*
        return ImGui::DragInt2(label, v, v_speed, v_min);
    */

    public static native boolean DragInt3(String label, int[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragInt2(label, v, v_speed, v_min, v_max);
    */

    public static native boolean DragInt3(String label, int[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragInt2(label, v, v_speed, v_min, v_max, format);
    */

    public static native boolean DragInt4(String label, int[] v); /*
        return ImGui::DragInt4(label, v);
    */

    public static native boolean DragInt4(String label, int[] v, float v_speed); /*
        return ImGui::DragInt4(label, v, v_speed);
    */

    public static native boolean DragInt4(String label, int[] v, float v_speed, float v_min); /*
        return ImGui::DragInt4(label, v, v_speed, v_min);
    */

    public static native boolean DragInt4(String label, int[] v, float v_speed, float v_min, float v_max); /*
        return ImGui::DragInt4(label, v, v_speed, v_min, v_max);
    */

    public static native boolean DragInt4(String label, int[] v, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragInt4(label, v, v_speed, v_min, v_max, format);
    */

    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max); /*
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0]);
    */

    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max, float v_speed); /*
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed);
    */

    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max, float v_speed, float v_min); /*
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min);
    */

    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max, float v_speed, float v_min, float v_max); /*
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max);
    */

    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max, float v_speed, float v_min, float v_max, String format); /*
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format);
    */

    public static native boolean DragIntRange2(String label, int[] v_current_min, int[] v_current_max, float v_speed, float v_min, float v_max, String format, String format_max); /*
        return ImGui::DragIntRange2(label, &v_current_min[0], &v_current_max[0], v_speed, v_min, v_max, format, format_max);
    */

    public static boolean DragScalar(String label, int data_type, ImInt p_data, float v_speed) {
        return nDragScalar(label, data_type, p_data.data, v_speed);
    }

    private static native boolean nDragScalar(String label, int data_type, int[] p_data, float v_speed); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed);
    */

    public static boolean DragScalar(String label, int data_type, ImInt p_data, float v_speed, int p_min) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min);
    }

    private static native boolean nDragScalar(String label, int data_type, int[] p_data, float v_speed, int p_min); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min);
    */

    public static boolean DragScalar(String label, int data_type, ImInt p_data, float v_speed, int p_min, int p_max) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalar(String label, int data_type, int[] p_data, float v_speed, int p_min, int p_max); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max);
    */

    public static boolean DragScalar(String label, int data_type, ImInt p_data, float v_speed, int p_min, int p_max, String format) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalar(String label, int data_type, ImInt p_data, float v_speed, int p_min, int p_max, String format, float power) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalar(String label, int data_type, int[] p_data, float v_speed, int p_min, int p_max, String format, float power); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalar(String label, int data_type, ImFloat p_data, float v_speed) {
        return nDragScalar(label, data_type, p_data.data, v_speed);
    }

    private static native boolean nDragScalar(String label, int data_type, float[] p_data, float v_speed); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed);
    */

    public static boolean DragScalar(String label, int data_type, ImFloat p_data, float v_speed, float p_min) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min);
    }

    private static native boolean nDragScalar(String label, int data_type, float[] p_data, float v_speed, float p_min); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min);
    */

    public static boolean DragScalar(String label, int data_type, ImFloat p_data, float v_speed, float p_min, float p_max) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalar(String label, int data_type, float[] p_data, float v_speed, float p_min, float p_max); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max);
    */

    public static boolean DragScalar(String label, int data_type, ImFloat p_data, float v_speed, float p_min, float p_max, String format) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalar(String label, int data_type, ImFloat p_data, float v_speed, float p_min, float p_max, String format, float power) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalar(String label, int data_type, float[] p_data, float v_speed, float p_min, float p_max, String format, float power); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalar(String label, int data_type, ImDouble p_data, float v_speed) {
        return nDragScalar(label, data_type, p_data.data, v_speed);
    }

    private static native boolean nDragScalar(String label, int data_type, double[] p_data, float v_speed); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed);
    */

    public static boolean DragScalar(String label, int data_type, ImDouble p_data, float v_speed, double p_min) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min);
    }

    private static native boolean nDragScalar(String label, int data_type, double[] p_data, float v_speed, double p_min); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min);
    */

    public static boolean DragScalar(String label, int data_type, ImDouble p_data, float v_speed, double p_min, double p_max) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalar(String label, int data_type, double[] p_data, float v_speed, double p_min, double p_max); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max);
    */

    public static boolean DragScalar(String label, int data_type, ImDouble p_data, float v_speed, double p_min, double p_max, String format) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalar(String label, int data_type, ImDouble p_data, float v_speed, double p_min, double p_max, String format, float power) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalar(String label, int data_type, double[] p_data, float v_speed, double p_min, double p_max, String format, float power); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalar(String label, int data_type, ImLong p_data, float v_speed) {
        return nDragScalar(label, data_type, p_data.data, v_speed);
    }

    private static native boolean nDragScalar(String label, int data_type, long[] p_data, float v_speed); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed);
    */

    public static boolean DragScalar(String label, int data_type, ImLong p_data, float v_speed, long p_min) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min);
    }

    private static native boolean nDragScalar(String label, int data_type, long[] p_data, float v_speed, long p_min); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min);
    */

    public static boolean DragScalar(String label, int data_type, ImLong p_data, float v_speed, long p_min, long p_max) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalar(String label, int data_type, long[] p_data, float v_speed, long p_min, long p_max); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max);
    */

    public static boolean DragScalar(String label, int data_type, ImLong p_data, float v_speed, long p_min, long p_max, String format) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalar(String label, int data_type, ImLong p_data, float v_speed, long p_min, long p_max, String format, float power) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalar(String label, int data_type, long[] p_data, float v_speed, long p_min, long p_max, String format, float power); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalar(String label, int data_type, ImShort p_data, float v_speed) {
        return nDragScalar(label, data_type, p_data.data, v_speed);
    }

    private static native boolean nDragScalar(String label, int data_type, short[] p_data, float v_speed); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed);
    */

    public static boolean DragScalar(String label, int data_type, ImShort p_data, float v_speed, short p_min) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min);
    }

    private static native boolean nDragScalar(String label, int data_type, short[] p_data, float v_speed, short p_min); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min);
    */

    public static boolean DragScalar(String label, int data_type, ImShort p_data, float v_speed, short p_min, short p_max) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalar(String label, int data_type, short[] p_data, float v_speed, short p_min, short p_max); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max);
    */

    public static boolean DragScalar(String label, int data_type, ImShort p_data, float v_speed, short p_min, short p_max, String format) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalar(String label, int data_type, ImShort p_data, float v_speed, short p_min, short p_max, String format, float power) {
        return nDragScalar(label, data_type, p_data.data, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalar(String label, int data_type, short[] p_data, float v_speed, short p_min, short p_max, String format, float power); /*
        return ImGui::DragScalar(label, data_type, &p_data[0], v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalarN(String label, int data_type, ImInt p_data, int components, float v_speed) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed);
    }

    private static native boolean nDragScalarN(String label, int data_type, int[] p_data, int components, float v_speed); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed);
    */

    public static boolean DragScalarN(String label, int data_type, ImInt p_data, int components, float v_speed, int p_min) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min);
    }

    private static native boolean nDragScalarN(String label, int data_type, int[] p_data, int components, float v_speed, int p_min); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min);
    */

    public static boolean DragScalarN(String label, int data_type, ImInt p_data, int components, float v_speed, int p_min, int p_max) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalarN(String label, int data_type, int[] p_data, int components, float v_speed, int p_min, int p_max); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max);
    */

    public static boolean DragScalarN(String label, int data_type, ImInt p_data, int components, float v_speed, int p_min, int p_max, String format) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalarN(String label, int data_type, ImInt p_data, int components, float v_speed, int p_min, int p_max, String format, float power) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalarN(String label, int data_type, int[] p_data, int components, float v_speed, int p_min, int p_max, String format, float power); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalarN(String label, int data_type, ImFloat p_data, int components, float v_speed) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed);
    }

    private static native boolean nDragScalarN(String label, int data_type, float[] p_data, int components, float v_speed); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed);
    */

    public static boolean DragScalarN(String label, int data_type, ImFloat p_data, int components, float v_speed, float p_min) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min);
    }

    private static native boolean nDragScalarN(String label, int data_type, float[] p_data, int components, float v_speed, float p_min); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min);
    */

    public static boolean DragScalarN(String label, int data_type, ImFloat p_data, int components, float v_speed, float p_min, float p_max) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalarN(String label, int data_type, float[] p_data, int components, float v_speed, float p_min, float p_max); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max);
    */

    public static boolean DragScalarN(String label, int data_type, ImFloat p_data, int components, float v_speed, float p_min, float p_max, String format) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalarN(String label, int data_type, ImFloat p_data, int components, float v_speed, float p_min, float p_max, String format, float power) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalarN(String label, int data_type, float[] p_data, int components, float v_speed, float p_min, float p_max, String format, float power); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalarN(String label, int data_type, ImDouble p_data, int components, float v_speed) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed);
    }

    private static native boolean nDragScalarN(String label, int data_type, double[] p_data, int components, float v_speed); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed);
    */

    public static boolean DragScalarN(String label, int data_type, ImDouble p_data, int components, float v_speed, double p_min) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min);
    }

    private static native boolean nDragScalarN(String label, int data_type, double[] p_data, int components, float v_speed, double p_min); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min);
    */

    public static boolean DragScalarN(String label, int data_type, ImDouble p_data, int components, float v_speed, double p_min, double p_max) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalarN(String label, int data_type, double[] p_data, int components, float v_speed, double p_min, double p_max); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max);
    */

    public static boolean DragScalarN(String label, int data_type, ImDouble p_data, int components, float v_speed, double p_min, double p_max, String format) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalarN(String label, int data_type, ImDouble p_data, int components, float v_speed, double p_min, double p_max, String format, float power) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalarN(String label, int data_type, double[] p_data, int components, float v_speed, double p_min, double p_max, String format, float power); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalarN(String label, int data_type, ImLong p_data, int components, float v_speed) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed);
    }

    private static native boolean nDragScalarN(String label, int data_type, long[] p_data, int components, float v_speed); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed);
    */

    public static boolean DragScalarN(String label, int data_type, ImLong p_data, int components, float v_speed, long p_min) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min);
    }

    private static native boolean nDragScalarN(String label, int data_type, long[] p_data, int components, float v_speed, long p_min); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min);
    */

    public static boolean DragScalarN(String label, int data_type, ImLong p_data, int components, float v_speed, long p_min, long p_max) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalarN(String label, int data_type, long[] p_data, int components, float v_speed, long p_min, long p_max); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max);
    */

    public static boolean DragScalarN(String label, int data_type, ImLong p_data, int components, float v_speed, long p_min, long p_max, String format) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalarN(String label, int data_type, ImLong p_data, int components, float v_speed, long p_min, long p_max, String format, float power) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalarN(String label, int data_type, long[] p_data, int components, float v_speed, long p_min, long p_max, String format, float power); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max, format, power);
    */

    public static boolean DragScalarN(String label, int data_type, ImShort p_data, int components, float v_speed) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed);
    }

    private static native boolean nDragScalarN(String label, int data_type, short[] p_data, int components, float v_speed); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed);
    */

    public static boolean DragScalarN(String label, int data_type, ImShort p_data, int components, float v_speed, short p_min) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min);
    }

    private static native boolean nDragScalarN(String label, int data_type, short[] p_data, int components, float v_speed, short p_min); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min);
    */

    public static boolean DragScalarN(String label, int data_type, ImShort p_data, int components, float v_speed, short p_min, short p_max) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max);
    }

    private static native boolean nDragScalarN(String label, int data_type, short[] p_data, int components, float v_speed, short p_min, short p_max); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max);
    */

    public static boolean DragScalarN(String label, int data_type, ImShort p_data, int components, float v_speed, short p_min, short p_max, String format) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, 1.0f);
    }

    public static boolean DragScalarN(String label, int data_type, ImShort p_data, int components, float v_speed, short p_min, short p_max, String format, float power) {
        return nDragScalarN(label, data_type, p_data.data, components, v_speed, p_min, p_max, format, power);
    }

    private static native boolean nDragScalarN(String label, int data_type, short[] p_data, int components, float v_speed, short p_min, short p_max, String format, float power); /*
        return ImGui::DragScalarN(label, data_type, &p_data[0], components, v_speed, &p_min, &p_max, format, power);
    */

    // Widgets: Sliders
    // - CTRL+Click on any slider to turn them into an input box. Manually input values aren't clamped and can go off-bounds.
    // - Adjust format string to decorate the value with a prefix, a suffix, or adapt the editing and display precision e.g. "%.3f" -> 1.234; "%5.2f secs" -> 01.23 secs; "Biscuit: %.0f" -> Biscuit: 1; etc.

    /**
     * Adjust format to decorate the value with a prefix or a suffix for in-slider labels or unit display. Use power!=1.0 for power curve sliders
     */
    public static native boolean SliderFloat(String label, float[] v, float v_min, float v_max); /*
        return ImGui::SliderFloat(label, &v[0],v_min, v_max);
    */

    public static native boolean SliderFloat(String label, float[] v, float v_min, float v_max, String format); /*
        return ImGui::SliderFloat(label, &v[0], v_min, v_max, format);
    */

    public static native boolean SliderFloat(String label, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::SliderFloat(label, &v[0], v_min, v_max, format, power);
    */

    public static native boolean SliderFloat2(String label, float[] v, float v_min, float v_max); /*
        return ImGui::SliderFloat2(label, v, v_min, v_max);
    */

    public static native boolean SliderFloat2(String label, float[] v, float v_min, float v_max, String format); /*
        return ImGui::SliderFloat2(label, v, v_min, v_max, format);
    */

    public static native boolean SliderFloat2(String label, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::SliderFloat2(label, v, v_min, v_max, format, power);
    */

    public static native boolean SliderFloat3(String label, float[] v, float v_min, float v_max); /*
        return ImGui::SliderFloat3(label, v, v_min, v_max);
    */

    public static native boolean SliderFloat3(String label, float[] v, float v_min, float v_max, String format); /*
        return ImGui::SliderFloat3(label, v, v_min, v_max, format);
    */

    public static native boolean SliderFloat3(String label, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::SliderFloat3(label, v, v_min, v_max, format, power);
    */

    public static native boolean SliderFloat4(String label, float[] v, float v_min, float v_max); /*
        return ImGui::SliderFloat4(label, v, v_min, v_max);
    */

    public static native boolean SliderFloat4(String label, float[] v, float v_min, float v_max, String format); /*
        return ImGui::SliderFloat4(label, v, v_min, v_max, format);
    */

    public static native boolean SliderFloat4(String label, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::SliderFloat4(label, v, v_min, v_max, format, power);
    */

    public static native boolean SliderAngle(String label, float[] v_rad); /*
        return ImGui::SliderAngle(label, &v_rad[0]);
    */

    public static native boolean SliderAngle(String label, float[] v_rad, float v_degrees_min); /*
        return ImGui::SliderAngle(label, &v_rad[0], v_degrees_min);
    */

    public static native boolean SliderAngle(String label, float[] v_rad, float v_degrees_min, float v_degrees_max); /*
        return ImGui::SliderAngle(label, &v_rad[0], v_degrees_min, v_degrees_max);
    */

    public static native boolean SliderAngle(String label, float[] v_rad, float v_degrees_min, float v_degrees_max, String format); /*
        return ImGui::SliderAngle(label, &v_rad[0], v_degrees_min, v_degrees_max, format);
    */

    public static native boolean SliderInt(String label, int[] v, int v_min, int v_max); /*
        return ImGui::SliderInt(label, &v[0], v_min, v_max);
    */

    public static native boolean SliderInt(String label, int[] v, int v_min, int v_max, String format); /*
        return ImGui::SliderInt(label, &v[0], v_min, v_max, format);
    */

    public static native boolean SliderInt2(String label, int[] v, int v_min, int v_max); /*
        return ImGui::SliderInt2(label, v, v_min, v_max);
    */

    public static native boolean SliderInt2(String label, int[] v, int v_min, int v_max, String format); /*
        return ImGui::SliderInt2(label, v, v_min, v_max, format);
    */

    public static native boolean SliderInt3(String label, int[] v, int v_min, int v_max); /*
        return ImGui::SliderInt3(label, v, v_min, v_max);
    */

    public static native boolean SliderInt3(String label, int[] v, int v_min, int v_max, String format); /*
        return ImGui::SliderInt3(label, v, v_min, v_max, format);
    */

    public static native boolean SliderInt4(String label, int[] v, int v_min, int v_max); /*
        return ImGui::SliderInt4(label, v, v_min, v_max);
    */

    public static native boolean SliderInt4(String label, int[] v, int v_min, int v_max, String format); /*
        return ImGui::SliderInt4(label, v, v_min, v_max, format);
    */

    public static boolean SliderScalar(String label, int data_type, ImInt v, int v_min, int v_max) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalar(String label, int data_type, int[] v, int v_min, int v_max); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */

    public static boolean SliderScalar(String label, int data_type, ImInt v, int v_min, int v_max, String format) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalar(String label, int data_type, ImInt v, int v_min, int v_max, String format, float power) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalar(String label, int data_type, int[] v, int v_min, int v_max, String format, float power); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean SliderScalar(String label, int data_type, ImFloat v, float v_min, float v_max) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalar(String label, int data_type, float[] v, float v_min, float v_max); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */

    public static boolean SliderScalar(String label, int data_type, ImFloat v, float v_min, float v_max, String format) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalar(String label, int data_type, ImFloat v, float v_min, float v_max, String format, float power) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalar(String label, int data_type, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean SliderScalar(String label, int data_type, ImLong v, long v_min, long v_max) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalar(String label, int data_type, long[] v, long v_min, long v_max); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */

    public static boolean SliderScalar(String label, int data_type, ImLong v, long v_min, long v_max, String format) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalar(String label, int data_type, ImLong v, long v_min, long v_max, String format, float power) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalar(String label, int data_type, long[] v, long v_min, long v_max, String format, float power); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean SliderScalar(String label, int data_type, ImDouble v, double v_min, double v_max) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalar(String label, int data_type, double[] v, double v_min, double v_max); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */

    public static boolean SliderScalar(String label, int data_type, ImDouble v, double v_min, double v_max, String format) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalar(String label, int data_type, ImDouble v, double v_min, double v_max, String format, float power) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalar(String label, int data_type, double[] v, double v_min, double v_max, String format, float power); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean SliderScalar(String label, int data_type, ImShort v, short v_min, short v_max) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalar(String label, int data_type, short[] v, short v_min, short v_max); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max);
    */

    public static boolean SliderScalar(String label, int data_type, ImShort v, short v_min, short v_max, String format) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalar(String label, int data_type, ImShort v, short v_min, short v_max, String format, float power) {
        return nSliderScalar(label, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalar(String label, int data_type, short[] v, short v_min, short v_max, String format, float power); /*
        return ImGui::SliderScalar(label, data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImInt v, int v_min, int v_max) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, int[] v, int v_min, int v_max); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImInt v, int v_min, int v_max, String format) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalarN(String label, int data_type, int components, ImInt v, int v_min, int v_max, String format, float power) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, int[] v, int v_min, int v_max, String format, float power); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max, format, power);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImFloat v, float v_min, float v_max) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, float[] v, float v_min, float v_max); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImFloat v, float v_min, float v_max, String format) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalarN(String label, int data_type, int components, ImFloat v, float v_min, float v_max, String format, float power) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max, format, power);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImLong v, long v_min, long v_max) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, long[] v, long v_min, long v_max); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImLong v, long v_min, long v_max, String format) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalarN(String label, int data_type, int components, ImLong v, long v_min, long v_max, String format, float power) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, long[] v, long v_min, long v_max, String format, float power); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max, format, power);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImDouble v, double v_min, double v_max) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, double[] v, double v_min, double v_max); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImDouble v, double v_min, double v_max, String format) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalarN(String label, int data_type, int components, ImDouble v, double v_min, double v_max, String format, float power) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, double[] v, double v_min, double v_max, String format, float power); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max, format, power);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImShort v, short v_min, short v_max) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, short[] v, short v_min, short v_max); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max);
    */

    public static boolean SliderScalarN(String label, int data_type, int components, ImShort v, short v_min, short v_max, String format) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean SliderScalarN(String label, int data_type, int components, ImShort v, short v_min, short v_max, String format, float power) {
        return nSliderScalarN(label, data_type, components, v.data, v_min, v_max, format, power);
    }

    private static native boolean nSliderScalarN(String label, int data_type, int components, short[] v, short v_min, short v_max, String format, float power); /*
        return ImGui::SliderScalarN(label, data_type, &v[0], components, &v_min, &v_max, format, power);
    */

    public static native boolean VSliderFloat(String label, float sizeX, float sizeY, float[] v, float v_min, float v_max); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
    */

    public static native boolean VSliderFloat(String label, float sizeX, float sizeY, float[] v, float v_min, float v_max, String format); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
    */

    public static native boolean VSliderFloat(String label, float sizeX, float sizeY, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::VSliderFloat(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max, format, power);
    */

    public static native boolean VSliderInt(String label, float sizeX, float sizeY, int[] v, int v_min, int v_max); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max);
    */

    public static native boolean VSliderInt(String label, float sizeX, float sizeY, int[] v, int v_min, int v_max, String format); /*
        return ImGui::VSliderInt(label, ImVec2(sizeX, sizeY), &v[0], v_min, v_max, format);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImInt v, int v_min, int v_max) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, int[] v, int v_min, int v_max); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImInt v, int v_min, int v_max, String format) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImInt v, int v_min, int v_max, String format, float power) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, int[] v, int v_min, int v_max, String format, float power); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImFloat v, float v_min, float v_max) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, float[] v, float v_min, float v_max); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImFloat v, float v_min, float v_max, String format) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImFloat v, float v_min, float v_max, String format, float power) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, float[] v, float v_min, float v_max, String format, float power); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImLong v, long v_min, long v_max) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, long[] v, long v_min, long v_max); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImLong v, long v_min, long v_max, String format) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImLong v, long v_min, long v_max, String format, float power) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, long[] v, long v_min, long v_max, String format, float power); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImDouble v, double v_min, double v_max) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, double[] v, double v_min, double v_max); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImDouble v, double v_min, double v_max, String format) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImDouble v, double v_min, double v_max, String format, float power) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, double[] v, double v_min, double v_max, String format, float power); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImShort v, short v_min, short v_max) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, short[] v, short v_min, short v_max); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max);
    */

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImShort v, short v_min, short v_max, String format) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, 1.0f);
    }

    public static boolean VSliderScalar(String label, float sizeX, float sizeY, int data_type, ImShort v, short v_min, short v_max, String format, float power) {
        return nVSliderScalar(label, sizeX, sizeY, data_type, v.data, v_min, v_max, format, power);
    }

    private static native boolean nVSliderScalar(String label, float sizeX, float sizeY, int data_type, short[] v, short v_min, short v_max, String format, float power); /*
        return ImGui::VSliderScalar(label, ImVec2(sizeX, sizeY), data_type, &v[0], &v_min, &v_max, format, power);
    */

    // Widgets: Input with Keyboard
    // - If you want to use InputText() with a dynamic string type such as std::string or your own, see misc/cpp/imgui_stdlib.h
    // - Most of the ImGuiInputTextFlags flags are only useful for InputText() and not for InputFloatX, InputIntX, InputDouble etc.

    /*JNI
        jfieldID imTextInputDataSizeID;
        jfieldID imTextInputDataIsDirtyID;

        struct InputTextCallback_UserData {
            jobject* textInputData;
            JNIEnv* env;
            int maxChar;
            char* allowedChar;
            int allowedCharLength;
            int maxSize;
            int curSize;
        };

        static int TextEditCallbackStub(ImGuiInputTextCallbackData* data) {
            InputTextCallback_UserData* userData = (InputTextCallback_UserData*)data->UserData;

            if (data->EventFlag == ImGuiInputTextFlags_CallbackCharFilter) {
                if(userData->allowedCharLength > 0) {
                    bool found = false;
                    for(int i = 0; i < userData->allowedCharLength; i++) {
                        if(userData->allowedChar[i] == data->EventChar) {
                            found = true;
                            break;
                        }
                    }
                    return found ? 0 : 1;
                }
            }

            return 0;
        }
    */

    private static native void nInitInputTextData(); /*
        jclass jImInputTextDataClass = env->FindClass("imgui/ImGuiInputTextData");
        imTextInputDataSizeID = env->GetFieldID(jImInputTextDataClass, "size", "I");
        imTextInputDataIsDirtyID = env->GetFieldID(jImInputTextDataClass, "isDirty", "Z");
    */

    public static boolean InputText(String label, ImString text) {
        return nInputText(label, text.data, text.data.length, 0, text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    public static boolean InputText(String label, ImString text, int imGuiInputTextFlags) {
        return nInputText(label, text.data, text.data.length, imGuiInputTextFlags, text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    private static native boolean nInputText(String label, byte[] buff, int maxSize, int flags, ImGuiInputTextData textInputData, int maxChar, String allowedChar, int allowedCharLength); /*
        int size = (int)strlen(buff);

        InputTextCallback_UserData cb_user_data;
        cb_user_data.textInputData = &textInputData;
        cb_user_data.env = env;
        cb_user_data.curSize = size;
        cb_user_data.maxSize = maxSize;
        cb_user_data.maxChar = maxChar;
        cb_user_data.allowedChar = allowedChar;
        cb_user_data.allowedCharLength = allowedCharLength;

        char tempArray[maxSize];

        memset(tempArray, 0, sizeof(tempArray));
        memcpy(tempArray, buff, size);

        if(maxChar >= 0 && maxChar < maxSize) {
            maxSize = maxChar;
        }

        bool flag = ImGui::InputText(label, tempArray, maxSize, flags | ImGuiInputTextFlags_CallbackCharFilter, &TextEditCallbackStub, &cb_user_data);

        if (flag) {
            size = (int)strlen(tempArray);
            env->SetIntField(textInputData, imTextInputDataSizeID, size);
            env->SetBooleanField(textInputData, imTextInputDataIsDirtyID, true);
            memset(buff, 0, maxSize);
            memcpy(buff, tempArray, size);
        }

        return flag;
    */

    public static boolean InputTextMultiline(String label, ImString text) {
        return nInputTextMultiline(label, text.data, text.data.length, 0, 0, 0, text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    public static boolean InputTextMultiline(String label, ImString text, float width, float height) {
        return nInputTextMultiline(label, text.data, text.data.length, width, height, 0, text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    public static boolean InputTextMultiline(String label, ImString text, float width, float height, int imGuiInputTextFlags) {
        return nInputTextMultiline(label, text.data, text.data.length, width, height, imGuiInputTextFlags, text.inputData, text.inputData.maxChar, text.inputData.allowedChar, text.inputData.allowedChar.length());
    }

    private static native boolean nInputTextMultiline(String label, byte[] buff, int maxSize, float width, float height, int flags, ImGuiInputTextData textInputData, int maxChar, String allowedChar, int allowedCharLength); /*
        int size = (int)strlen(buff);

        InputTextCallback_UserData cb_user_data;
        cb_user_data.textInputData = &textInputData;
        cb_user_data.env = env;
        cb_user_data.curSize = size;
        cb_user_data.maxSize = maxSize;
        cb_user_data.maxChar = maxChar;
        cb_user_data.allowedChar = allowedChar;
        cb_user_data.allowedCharLength = allowedCharLength;

        char tempArray[maxSize];

        memset(tempArray, 0, sizeof(tempArray));
        memcpy(tempArray, buff, size);

        if(maxChar >= 0 && maxChar < maxSize) {
            maxSize = maxChar;
        }

        bool flag = ImGui::InputTextMultiline(label, tempArray, maxSize, ImVec2(width, height), flags | ImGuiInputTextFlags_CallbackCharFilter, &TextEditCallbackStub, &cb_user_data);

        if (flag) {
            size = (int)strlen(tempArray);
            env->SetIntField(textInputData, imTextInputDataSizeID, size);
            env->SetBooleanField(textInputData, imTextInputDataIsDirtyID, true);
            memset(buff, 0, maxSize);
            memcpy(buff, tempArray, size);
        }

        return flag;
    */

    public static boolean InputFloat(String label, ImFloat v) {
        return nInputFloat(label, v.data, 0, 0, "%.3f", 0);
    }

    public static boolean InputFloat(String label, ImFloat v, float step) {
        return nInputFloat(label, v.data, step, 0, "%.3f", 0);
    }

    public static boolean InputFloat(String label, ImFloat v, float step, float step_fast) {
        return nInputFloat(label, v.data, step, step_fast, "%.3f", 0);
    }

    public static boolean InputFloat(String label, ImFloat v, float step, float step_fast, String format) {
        return nInputFloat(label, v.data, step, step_fast, format, 0);
    }

    public static boolean InputFloat(String label, ImFloat v, float step, float step_fast, String format, int imGuiInputTextFlags) {
        return nInputFloat(label, v.data, step, step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputFloat(String label, float[] v, float step, float step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat(label, &v[0], step, step_fast, format, imGuiInputTextFlags);
    */

    public static native boolean InputFloat2(String label, float[] v); /*
        return ImGui::InputFloat2(label, v, "%.3f", 0);
    */

    public static native boolean InputFloat2(String label, float[] v, String format); /*
        return ImGui::InputFloat2(label, v, format, 0);
    */

    public static native boolean InputFloat2(String label, float[] v, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat2(label, v, format, imGuiInputTextFlags);
    */

    public static native boolean InputFloat3(String label, float[] v); /*
        return ImGui::InputFloat2(label, v, "%.3f", 0);
    */

    public static native boolean InputFloat3(String label, float[] v, String format); /*
        return ImGui::InputFloat2(label, v, format, 0);
    */

    public static native boolean InputFloat3(String label, float[] v, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat2(label, v, format, imGuiInputTextFlags);
    */

    public static native boolean InputFloat4(String label, float[] v); /*
        return ImGui::InputFloat2(label, v, "%.3f", 0);
    */

    public static native boolean InputFloat4(String label, float[] v, String format); /*
        return ImGui::InputFloat2(label, v, format, 0);
    */

    public static native boolean InputFloat4(String label, float[] v, String format, int imGuiInputTextFlags); /*
        return ImGui::InputFloat2(label, v, format, imGuiInputTextFlags);
    */

    public static boolean InputInt(String label, ImInt v) {
        return nInputInt(label, v.data, 1, 100, 0);
    }

    public static boolean InputInt(String label, ImInt v, int step) {
        return nInputInt(label, v.data, step, 100, 0);
    }

    public static boolean InputInt(String label, ImInt v, int step, int step_fast) {
        return nInputInt(label, v.data, step, step_fast, 0);
    }

    public static boolean InputInt(String label, ImInt v, int step, int step_fast, int imGuiInputTextFlags) {
        return nInputInt(label, v.data, step, step_fast, imGuiInputTextFlags);
    }

    private static native boolean nInputInt(String label, int[] v, int step, int step_fast, int imGuiInputTextFlags); /*
        return ImGui::InputInt(label, &v[0], step, step_fast, imGuiInputTextFlags);
    */

    public static native boolean InputInt2(String label, int[] v); /*
        return ImGui::InputInt2(label, v, 0);
    */

    public static native boolean InputInt2(String label, int[] v, int imGuiInputTextFlags); /*
        return ImGui::InputInt2(label, v, imGuiInputTextFlags);
    */

    public static native boolean InputInt3(String label, int[] v); /*
        return ImGui::InputInt2(label, v, 0);
    */

    public static native boolean InputInt3(String label, int[] v, int imGuiInputTextFlags); /*
        return ImGui::InputInt2(label, v, imGuiInputTextFlags);
    */

    public static native boolean InputInt4(String label, int[] v); /*
        return ImGui::InputInt2(label, v, 0);
    */

    public static native boolean InputInt4(String label, int[] v, int imGuiInputTextFlags); /*
        return ImGui::InputInt2(label, v, imGuiInputTextFlags);
    */

    public static boolean InputDouble(String label, ImDouble v) {
        return nInputDouble(label, v.data, 0, 0, "%.6f", 0);
    }

    public static boolean InputDouble(String label, ImDouble v, double step) {
        return nInputDouble(label, v.data, step, 0, "%.6f", 0);
    }

    public static boolean InputDouble(String label, ImDouble v, double step, double step_fast) {
        return nInputDouble(label, v.data, step, step_fast, "%.6f", 0);
    }

    public static boolean InputDouble(String label, ImDouble v, double step, double step_fast, String format) {
        return nInputDouble(label, v.data, step, step_fast, format, 0);
    }

    public static boolean InputDouble(String label, ImDouble v, double step, double step_fast, String format, int imGuiInputTextFlags) {
        return nInputDouble(label, v.data, step, step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputDouble(String label, double[] v, double step, double step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputDouble(label, &v[0], step, step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalar(String label, int data_type, ImInt p_data) {
        return nInputScalar(label, data_type, p_data.data);
    }

    private static native boolean nInputScalar(String label, int data_type, int[] p_data); /*
        return ImGui::InputScalar(label, data_type, &p_data[0]);
    */

    public static boolean InputScalar(String label, int data_type, ImInt p_data, int p_step) {
        return nInputScalar(label, data_type, p_data.data, p_step);
    }

    private static native boolean nInputScalar(String label, int data_type, int[] p_data, int p_step); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step);
    */

    public static boolean InputScalar(String label, int data_type, ImInt p_data, int p_step, int p_step_fast) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast);
    }

    private static native boolean nInputScalar(String label, int data_type, int[] p_data, int p_step, int p_step_fast); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast);
    */

    public static boolean InputScalar(String label, int data_type, ImInt p_data, int p_step, int p_step_fast, String format) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalar(String label, int data_type, int[] p_data, int p_step, int p_step_fast, String format); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format);
    */

    public static boolean InputScalar(String label, int data_type, ImInt p_data, int p_step, int p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int data_type, int[] p_data, int p_step, int p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalar(String label, int data_type, ImFloat p_data) {
        return nInputScalar(label, data_type, p_data.data);
    }

    private static native boolean nInputScalar(String label, int data_type, float[] p_data); /*
        return ImGui::InputScalar(label, data_type, &p_data[0]);
    */

    public static boolean InputScalar(String label, int data_type, ImFloat p_data, float p_step) {
        return nInputScalar(label, data_type, p_data.data, p_step);
    }

    private static native boolean nInputScalar(String label, int data_type, float[] p_data, float p_step); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step);
    */

    public static boolean InputScalar(String label, int data_type, ImFloat p_data, float p_step, float p_step_fast) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast);
    }

    private static native boolean nInputScalar(String label, int data_type, float[] p_data, float p_step, float p_step_fast); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast);
    */

    public static boolean InputScalar(String label, int data_type, ImFloat p_data, float p_step, float p_step_fast, String format) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalar(String label, int data_type, float[] p_data, float p_step, float p_step_fast, String format); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format);
    */

    public static boolean InputScalar(String label, int data_type, ImFloat p_data, float p_step, float p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int data_type, float[] p_data, float p_step, float p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalar(String label, int data_type, ImLong p_data) {
        return nInputScalar(label, data_type, p_data.data);
    }

    private static native boolean nInputScalar(String label, int data_type, long[] p_data); /*
        return ImGui::InputScalar(label, data_type, &p_data[0]);
    */

    public static boolean InputScalar(String label, int data_type, ImLong p_data, long p_step) {
        return nInputScalar(label, data_type, p_data.data, p_step);
    }

    private static native boolean nInputScalar(String label, int data_type, long[] p_data, long p_step); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step);
    */

    public static boolean InputScalar(String label, int data_type, ImLong p_data, long p_step, long p_step_fast) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast);
    }

    private static native boolean nInputScalar(String label, int data_type, long[] p_data, long p_step, long p_step_fast); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast);
    */

    public static boolean InputScalar(String label, int data_type, ImLong p_data, long p_step, long p_step_fast, String format) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalar(String label, int data_type, long[] p_data, long p_step, long p_step_fast, String format); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format);
    */

    public static boolean InputScalar(String label, int data_type, ImLong p_data, long p_step, long p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int data_type, long[] p_data, long p_step, long p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalar(String label, int data_type, ImDouble p_data) {
        return nInputScalar(label, data_type, p_data.data);
    }

    private static native boolean nInputScalar(String label, int data_type, double[] p_data); /*
        return ImGui::InputScalar(label, data_type, &p_data[0]);
    */

    public static boolean InputScalar(String label, int data_type, ImDouble p_data, double p_step) {
        return nInputScalar(label, data_type, p_data.data, p_step);
    }

    private static native boolean nInputScalar(String label, int data_type, double[] p_data, double p_step); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step);
    */

    public static boolean InputScalar(String label, int data_type, ImDouble p_data, double p_step, double p_step_fast) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast);
    }

    private static native boolean nInputScalar(String label, int data_type, double[] p_data, double p_step, double p_step_fast); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast);
    */

    public static boolean InputScalar(String label, int data_type, ImDouble p_data, double p_step, double p_step_fast, String format) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalar(String label, int data_type, double[] p_data, double p_step, double p_step_fast, String format); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format);
    */

    public static boolean InputScalar(String label, int data_type, ImDouble p_data, double p_step, double p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int data_type, double[] p_data, double p_step, double p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalar(String label, int data_type, ImShort p_data) {
        return nInputScalar(label, data_type, p_data.data);
    }

    private static native boolean nInputScalar(String label, int data_type, short[] p_data); /*
        return ImGui::InputScalar(label, data_type, &p_data[0]);
    */

    public static boolean InputScalar(String label, int data_type, ImShort p_data, short p_step) {
        return nInputScalar(label, data_type, p_data.data, p_step);
    }

    private static native boolean nInputScalar(String label, int data_type, short[] p_data, short p_step); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step);
    */

    public static boolean InputScalar(String label, int data_type, ImShort p_data, short p_step, short p_step_fast) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast);
    }

    private static native boolean nInputScalar(String label, int data_type, short[] p_data, short p_step, short p_step_fast); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast);
    */

    public static boolean InputScalar(String label, int data_type, ImShort p_data, short p_step, short p_step_fast, String format) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalar(String label, int data_type, short[] p_data, short p_step, short p_step_fast, String format); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format);
    */

    public static boolean InputScalar(String label, int data_type, ImShort p_data, short p_step, short p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalar(label, data_type, p_data.data, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalar(String label, int data_type, short[] p_data, short p_step, short p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalar(label, data_type, &p_data[0], &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalarN(String label, int data_type, ImInt p_data, int components) {
        return nInputScalarN(label, data_type, p_data.data, components);
    }

    private static native boolean nInputScalarN(String label, int data_type, int[] p_data, int components); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components);
    */

    public static boolean InputScalarN(String label, int data_type, ImInt p_data, int components, int p_step) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step);
    }

    private static native boolean nInputScalarN(String label, int data_type, int[] p_data, int components, int p_step); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step);
    */

    public static boolean InputScalarN(String label, int data_type, ImInt p_data, int components, int p_step, int p_step_fast) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast);
    }

    private static native boolean nInputScalarN(String label, int data_type, int[] p_data, int components, int p_step, int p_step_fast); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast);
    */

    public static boolean InputScalarN(String label, int data_type, ImInt p_data, int components, int p_step, int p_step_fast, String format) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalarN(String label, int data_type, int[] p_data, int components, int p_step, int p_step_fast, String format); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format);
    */

    public static boolean InputScalarN(String label, int data_type, ImInt p_data, int components, int p_step, int p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int data_type, int[] p_data, int components, int p_step, int p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalarN(String label, int data_type, ImFloat p_data, int components) {
        return nInputScalarN(label, data_type, p_data.data, components);
    }

    private static native boolean nInputScalarN(String label, int data_type, float[] p_data, int components); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components);
    */

    public static boolean InputScalarN(String label, int data_type, ImFloat p_data, int components, float p_step) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step);
    }

    private static native boolean nInputScalarN(String label, int data_type, float[] p_data, int components, float p_step); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step);
    */

    public static boolean InputScalarN(String label, int data_type, ImFloat p_data, int components, float p_step, float p_step_fast) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast);
    }

    private static native boolean nInputScalarN(String label, int data_type, float[] p_data, int components, float p_step, float p_step_fast); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast);
    */

    public static boolean InputScalarN(String label, int data_type, ImFloat p_data, int components, float p_step, float p_step_fast, String format) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalarN(String label, int data_type, float[] p_data, int components, float p_step, float p_step_fast, String format); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format);
    */

    public static boolean InputScalarN(String label, int data_type, ImFloat p_data, int components, float p_step, float p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int data_type, float[] p_data, int components, float p_step, float p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalarN(String label, int data_type, ImLong p_data, int components) {
        return nInputScalarN(label, data_type, p_data.data, components);
    }

    private static native boolean nInputScalarN(String label, int data_type, long[] p_data, int components); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components);
    */

    public static boolean InputScalarN(String label, int data_type, ImLong p_data, int components, long p_step) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step);
    }

    private static native boolean nInputScalarN(String label, int data_type, long[] p_data, int components, long p_step); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step);
    */

    public static boolean InputScalarN(String label, int data_type, ImLong p_data, int components, long p_step, long p_step_fast) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast);
    }

    private static native boolean nInputScalarN(String label, int data_type, long[] p_data, int components, long p_step, long p_step_fast); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast);
    */

    public static boolean InputScalarN(String label, int data_type, ImLong p_data, int components, long p_step, long p_step_fast, String format) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalarN(String label, int data_type, long[] p_data, int components, long p_step, long p_step_fast, String format); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format);
    */

    public static boolean InputScalarN(String label, int data_type, ImLong p_data, int components, long p_step, long p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int data_type, long[] p_data, int components, long p_step, long p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalarN(String label, int data_type, ImDouble p_data, int components) {
        return nInputScalarN(label, data_type, p_data.data, components);
    }

    private static native boolean nInputScalarN(String label, int data_type, double[] p_data, int components); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components);
    */

    public static boolean InputScalarN(String label, int data_type, ImDouble p_data, int components, double p_step) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step);
    }

    private static native boolean nInputScalarN(String label, int data_type, double[] p_data, int components, double p_step); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step);
    */

    public static boolean InputScalarN(String label, int data_type, ImDouble p_data, int components, double p_step, double p_step_fast) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast);
    }

    private static native boolean nInputScalarN(String label, int data_type, double[] p_data, int components, double p_step, double p_step_fast); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast);
    */

    public static boolean InputScalarN(String label, int data_type, ImDouble p_data, int components, double p_step, double p_step_fast, String format) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalarN(String label, int data_type, double[] p_data, int components, double p_step, double p_step_fast, String format); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format);
    */

    public static boolean InputScalarN(String label, int data_type, ImDouble p_data, int components, double p_step, double p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int data_type, double[] p_data, int components, double p_step, double p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    public static boolean InputScalarN(String label, int data_type, ImShort p_data, int components) {
        return nInputScalarN(label, data_type, p_data.data, components);
    }

    private static native boolean nInputScalarN(String label, int data_type, short[] p_data, int components); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components);
    */

    public static boolean InputScalarN(String label, int data_type, ImShort p_data, int components, short p_step) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step);
    }

    private static native boolean nInputScalarN(String label, int data_type, short[] p_data, int components, short p_step); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step);
    */

    public static boolean InputScalarN(String label, int data_type, ImShort p_data, int components, short p_step, short p_step_fast) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast);
    }

    private static native boolean nInputScalarN(String label, int data_type, short[] p_data, int components, short p_step, short p_step_fast); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast);
    */

    public static boolean InputScalarN(String label, int data_type, ImShort p_data, int components, short p_step, short p_step_fast, String format) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format);
    }

    private static native boolean nInputScalarN(String label, int data_type, short[] p_data, int components, short p_step, short p_step_fast, String format); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format);
    */

    public static boolean InputScalarN(String label, int data_type, ImShort p_data, int components, short p_step, short p_step_fast, String format, int imGuiInputTextFlags) {
        return nInputScalarN(label, data_type, p_data.data, components, p_step, p_step_fast, format, imGuiInputTextFlags);
    }

    private static native boolean nInputScalarN(String label, int data_type, short[] p_data, int components, short p_step, short p_step_fast, String format, int imGuiInputTextFlags); /*
        return ImGui::InputScalarN(label, data_type, &p_data[0], components, &p_step, &p_step_fast, format, imGuiInputTextFlags);
    */

    // Widgets: Color Editor/Picker (tip: the ColorEdit* functions have a little colored preview square that can be left-clicked to open a picker, and right-clicked to open an option menu.)
    // - Note that in C++ a 'float v[X]' function argument is the _same_ as 'float* v', the array syntax is just a way to document the number of elements that are expected to be accessible.
    // - You can pass the address of a first float element out of a contiguous structure, e.g. &myvector.x

    public native static boolean ColorEdit3(String label, float[] col); /*
        return ImGui::ColorEdit3(label, col);
    */

    public native static boolean ColorEdit3(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorEdit3(label, col, imGuiColorEditFlags);
    */

    public native static boolean ColorEdit4(String label, float[] col); /*
        return ImGui::ColorEdit4(label, col);
    */

    public native static boolean ColorEdit4(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorEdit4(label, col, imGuiColorEditFlags);
    */

    public native static boolean ColorPicker3(String label, float[] col); /*
        return ImGui::ColorPicker3(label, col);
    */

    public native static boolean ColorPicker3(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorPicker3(label, col, imGuiColorEditFlags);
    */

    public native static boolean ColorPicker4(String label, float[] col); /*
        return ImGui::ColorPicker4(label, col);
    */

    public native static boolean ColorPicker4(String label, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorPicker4(label, col, imGuiColorEditFlags);
    */

    public native static boolean ColorPicker4(String label, float[] col, int imGuiColorEditFlags, float ref_col); /*
        return ImGui::ColorPicker4(label, col, imGuiColorEditFlags, &ref_col);
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public native static boolean ColorButton(String desc_id, float[] col); /*
        return ImGui::ColorButton(desc_id, ImVec4(col[0], col[1], col[2], col[3]));
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public native static boolean ColorButton(String desc_id, float[] col, int imGuiColorEditFlags); /*
        return ImGui::ColorButton(desc_id, ImVec4(col[0], col[1], col[2], col[3]), imGuiColorEditFlags);
    */

    /**
     * Display a colored square/button, hover for details, return true when pressed.
     */
    public native static boolean ColorButton(String desc_id, float[] col, int imGuiColorEditFlags, float width, float height); /*
        return ImGui::ColorButton(desc_id, ImVec4(col[0], col[1], col[2], col[3]), imGuiColorEditFlags, ImVec2(width, height));
    */

    /**
     * Initialize current options (generally on application startup) if you want to select a default format,
     * picker type, etc. User will be able to change many settings, unless you pass the _NoOptions flag to your calls.
     */
    public native static void SetColorEditOptions(int imGuiColorEditFlags); /*
        ImGui::SetColorEditOptions(imGuiColorEditFlags);
    */

    // Widgets: Trees
    // - TreeNode functions return true when the node is open, in which case you need to also call TreePop() when you are finished displaying the tree node contents.

    public static native boolean TreeNode(String label); /*
        return ImGui::TreeNode(label);
    */

    /**
     * Helper variation to easily decorelate the id from the displayed string.
     * Read the FAQ about why and how to use ID. to align arbitrary text at the same level as a TreeNode() you can use Bullet().
     */
    public static native boolean TreeNode(String str_id, String label); /*
        return ImGui::TreeNode(str_id, label);
    */

    public static native boolean TreeNode(long ptr_id, String label); /*
        return ImGui::TreeNode((void*)ptr_id, label);
    */

    public static native boolean TreeNodeEx(String label); /*
        return ImGui::TreeNodeEx(label);
    */

    public static native boolean TreeNodeEx(String label, int imGuiTreeNodeFlags); /*
        return ImGui::TreeNodeEx(label, imGuiTreeNodeFlags);
    */

    public static native boolean TreeNodeEx(String str_id, int imGuiTreeNodeFlags, String label); /*
        return ImGui::TreeNodeEx(str_id, imGuiTreeNodeFlags, label);
    */

    public static native boolean TreeNodeEx(int ptr_id, int imGuiTreeNodeFlags, String label); /*
        return ImGui::TreeNodeEx((void*)(intptr_t)ptr_id, imGuiTreeNodeFlags, label);
    */

    /**
     * ~ Indent()+PushId(). Already called by TreeNode() when returning true, but you can call TreePush/TreePop yourself if desired.
     */
    public static native void TreePush(); /*
        ImGui::TreePush();
    */

    public static native void TreePush(String str_id); /*
        ImGui::TreePush(str_id);
    */

    public static native void TreePush(long ptr_id); /*
        ImGui::TreePush((void*)(intptr_t)ptr_id);
    */

    /**
     * ~ Unindent()+PopId()
     */
    public static native void TreePop(); /*
        ImGui::TreePop();
    */

    public static native void TreeAdvanceToLabelPos(); /*
        ImGui::TreeAdvanceToLabelPos();
    */

    /**
     * Horizontal distance preceding label when using TreeNode*() or Bullet() == (g.FontSize + style.FramePadding.x*2) for a regular unframed TreeNode
     */
    public static native float GetTreeNodeToLabelSpacing(); /*
        return ImGui::GetTreeNodeToLabelSpacing();
    */

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    public static native boolean CollapsingHeader(String label); /*
        return ImGui::CollapsingHeader(label);
    */

    /**
     * If returning 'true' the header is open. doesn't indent nor push on ID stack. user doesn't have to call TreePop().
     */
    public static native boolean CollapsingHeader(String label, int imGuiTreeNodeFlags); /*
        return ImGui::CollapsingHeader(label, imGuiTreeNodeFlags);
    */

    /**
     * When 'p_open' isn't NULL, display an additional small close button on upper right of the header
     */
    public static boolean CollapsingHeader(String label, ImBool p_open) {
        return nCollapsingHeader(label, p_open.data, 0);
    }

    /**
     * When 'p_open' isn't NULL, display an additional small close button on upper right of the header
     */
    public static boolean CollapsingHeader(String label, ImBool p_open, int imGuiTreeNodeFlags) {
        return nCollapsingHeader(label, p_open.data, imGuiTreeNodeFlags);
    }

    private static native boolean nCollapsingHeader(String label, boolean[] p_open, int imGuiTreeNodeFlags); /*
        return ImGui::CollapsingHeader(label, p_open, imGuiTreeNodeFlags);
    */

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    public static native void SetNextItemOpen(boolean is_open); /*
        ImGui::SetNextItemOpen(is_open);
    */

    /**
     * Set next TreeNode/CollapsingHeader open state.
     */
    public static native void SetNextItemOpen(boolean is_open, int cond); /*
        ImGui::SetNextItemOpen(is_open, cond);
    */

    // Widgets: Selectables
    // - A selectable highlights when hovered, and can display another color when selected.
    // - Neighbors selectable extend their highlight bounds in order to leave no gap between them.

    public static native boolean Selectable(String label); /*
        return ImGui::Selectable(label);
    */

    public static native boolean Selectable(String label, boolean selected); /*
        return ImGui::Selectable(label, selected);
    */

    public static native boolean Selectable(String label, boolean selected, int imGuiSelectableFlags); /*
        return ImGui::Selectable(label, selected, imGuiSelectableFlags);
    */

    public static native boolean Selectable(String label, boolean selected, int imGuiSelectableFlags, float sizeX, float sizeY); /*
        return ImGui::Selectable(label, selected, imGuiSelectableFlags, ImVec2(sizeX, sizeY));
    */

    public static boolean Selectable(String label, ImBool selected) {
        return nSelectable(label, selected.data, 0, 0, 0);
    }

    public static boolean Selectable(String label, ImBool selected, int imGuiSelectableFlags) {
        return nSelectable(label, selected.data, imGuiSelectableFlags, 0, 0);
    }

    public static boolean Selectable(String label, ImBool selected, int imGuiSelectableFlags, float sizeX, float sizeY) {
        return nSelectable(label, selected.data, imGuiSelectableFlags, sizeX, sizeY);
    }

    private static native boolean nSelectable(String label, boolean[] selected, int imGuiSelectableFlags, float sizeX, float sizeY); /*
        return ImGui::Selectable(label,  &selected[0], imGuiSelectableFlags, ImVec2(sizeX, sizeY));
    */

    // Widgets: List Boxes
    // - FIXME: To be consistent with all the newer API, ListBoxHeader/ListBoxFooter should in reality be called BeginListBox/EndListBox. Will rename them.

    public static void ListBox(String label, ImInt current_item, String[] items, int items_count) {
        nListBox(label, current_item.data, items, items_count, -1);
    }

    public static void ListBox(String label, ImInt current_item, String[] items, int items_count, int height_in_items) {
        nListBox(label, current_item.data, items, items_count, height_in_items);
    }

    private static native boolean nListBox(String label, int[] current_item, String [] items, int items_count, int height_in_items); /*
        const char* listbox_items[items_count];

        for(int i = 0; i < items_count; i++) {
            jstring string = (jstring)env->GetObjectArrayElement(items, i);
            const char *rawString = env->GetStringUTFChars(string, 0);
            listbox_items[i] = rawString;
        }

        return ImGui::ListBox(label, &current_item[0], listbox_items, items_count, height_in_items);
    */

    /**
     * Use if you want to reimplement ListBox() will custom data or interactions.
     * If the function return true, you can output elements then call ListBoxFooter() afterwards.
     */
    public static native boolean ListBoxHeader(String label); /*
        return ImGui::ListBoxHeader(label);
    */

    /**
     * Use if you want to reimplement ListBox() will custom data or interactions.
     * If the function return true, you can output elements then call ListBoxFooter() afterwards.
     */
    public static native boolean ListBoxHeader(String label, float sizeX, float sizeY); /*
        return ImGui::ListBoxHeader(label, ImVec2(sizeX, sizeY));
    */

    public static native boolean ListBoxHeader(String label, int items_count); /*
        return ImGui::ListBoxHeader(label, items_count);
    */

    public static native boolean ListBoxHeader(String label, int items_count, int height_in_items); /*
        return ImGui::ListBoxHeader(label, items_count, height_in_items);
    */

    /**
     * Terminate the scrolling region. Only call ListBoxFooter() if ListBoxHeader() returned true!
     */
    public static native void ListBoxFooter(); /*
        ImGui::ListBoxFooter();
    */

    // Widgets: Data Plotting

    public static native void PlotLines(String label, float[] values, int values_count); /*
        ImGui::PlotLines(label, &values[0], values_count);
    */

    public static native void PlotLines(String label, float[] values, int values_count, int values_offset); /*
        ImGui::PlotLines(label, &values[0], values_count, values_offset);
    */

    public static native void PlotLines(String label, float[] values, int values_count, int values_offset, String overlay_text); /*
        ImGui::PlotLines(label, &values[0], values_count, values_offset, overlay_text);
    */

    public static native void PlotLines(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min); /*
        ImGui::PlotLines(label, &values[0], values_count, values_offset, overlay_text, scale_min);
    */

    public static native void PlotLines(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min, float scale_max); /*
        ImGui::PlotLines(label, &values[0], values_count, values_offset, overlay_text, scale_min, scale_max);
    */

    public static native void PlotLines(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min, float scale_max, float graph_width, float graph_height); /*
        ImGui::PlotLines(label, &values[0], values_count, values_offset, overlay_text, scale_min, scale_max, ImVec2(graph_width, graph_height));
    */

    public static native void PlotLines(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min, float scale_max, float graph_width, float graph_height, int stride); /*
        ImGui::PlotLines(label, &values[0], values_count, values_offset, overlay_text, scale_min, scale_max, ImVec2(graph_width, graph_height), stride);
    */

    public static native void PlotHistogram(String label, float[] values, int values_count); /*
        ImGui::PlotHistogram(label, &values[0], values_count);
    */

    public static native void PlotHistogram(String label, float[] values, int values_count, int values_offset); /*
        ImGui::PlotHistogram(label, &values[0], values_count, values_offset);
    */

    public static native void PlotHistogram(String label, float[] values, int values_count, int values_offset, String overlay_text); /*
        ImGui::PlotHistogram(label, &values[0], values_count, values_offset, overlay_text);
    */

    public static native void PlotHistogram(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min); /*
        ImGui::PlotHistogram(label, &values[0], values_count, values_offset, overlay_text, scale_min);
    */

    public static native void PlotHistogram(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min, float scale_max); /*
        ImGui::PlotHistogram(label, &values[0], values_count, values_offset, overlay_text, scale_min, scale_max);
    */

    public static native void PlotHistogram(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min, float scale_max, float graph_width, float graph_height); /*
        ImGui::PlotHistogram(label, &values[0], values_count, values_offset, overlay_text, scale_min, scale_max, ImVec2(graph_width, graph_height));
    */

    public static native void PlotHistogram(String label, float[] values, int values_count, int values_offset, String overlay_text, float scale_min, float scale_max, float graph_width, float graph_height, int stride); /*
        ImGui::PlotHistogram(label, &values[0], values_count, values_offset, overlay_text, scale_min, scale_max, ImVec2(graph_width, graph_height), stride);
    */

    // Widgets: Value() Helpers.
    // - Those are merely shortcut to calling Text() with a format string. Output single value in "name: value" format (tip: freely declare more in your code to handle your types. you can add functions to the ImGui namespace)

    public static native void Value(String prefix, boolean b); /*
        ImGui::Value(prefix, b);
    */

    public static native void Value(String prefix, int v); /*
        ImGui::Value(prefix, (int)v);
    */

    public static native void Value(String prefix, long v); /*
        ImGui::Value(prefix, (unsigned int)v);
    */

    public static native void Value(String prefix, float f); /*
        ImGui::Value(prefix, f);
    */

    public static native void Value(String prefix, float f, String float_format); /*
        ImGui::Value(prefix, f, float_format);
    */

    // Widgets: Menus
    // - Use BeginMenuBar() on a window ImGuiWindowFlags_MenuBar to append to its menu bar.
    // - Use BeginMainMenuBar() to create a menu bar at the top of the screen.

    /**
     * Append to menu-bar of current window (requires ImGuiWindowFlags_MenuBar flag set on parent window).
     */
    public static native boolean BeginMenuBar(); /*
        return ImGui::BeginMenuBar();
    */

    /**
     * Only call EndMenuBar() if BeginMenuBar() returns true!
     */
    public static native void EndMenuBar(); /*
        ImGui::EndMenuBar();
    */

    /**
     * Create and append to a full screen menu-bar.
     */
    public static native boolean BeginMainMenuBar(); /*
        return ImGui::BeginMainMenuBar();
    */

    /**
     * Only call EndMainMenuBar() if BeginMainMenuBar() returns true!
     */
    public static native void EndMainMenuBar(); /*
        ImGui::EndMainMenuBar();
    */

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    public static native boolean BeginMenu(String label); /*
        return ImGui::BeginMenu(label);
    */

    /**
     * Create a sub-menu entry. only call EndMenu() if this returns true!
     */
    public static native boolean BeginMenu(String label, boolean enabled); /*
        return ImGui::BeginMenu(label, enabled);
    */

    /**
     * Only call EndMenu() if BeginMenu() returns true!
     */
    public static native void EndMenu(); /*
        ImGui::EndMenu();
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static native boolean MenuItem(String label); /*
        return ImGui::MenuItem(label);
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static native boolean MenuItem(String label, String shortcut); /*
        return ImGui::MenuItem(label, shortcut);
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static native boolean MenuItem(String label, String shortcut, boolean selected); /*
        return ImGui::MenuItem(label, shortcut, selected);
    */

    /**
     * Return true when activated. shortcuts are displayed for convenience but not processed by ImGui at the moment
     */
    public static native boolean MenuItem(String label, String shortcut, boolean selected, boolean enabled); /*
        return ImGui::MenuItem(label, shortcut, selected, enabled);
    */

    /**
     * Return true when activated + toggle (*p_selected) if p_selected != NULL
     */
    public static boolean MenuItem(String label, String shortcut, ImBool p_selected) {
        return nMenuItem(label, shortcut, p_selected.data, true);
    }

    /**
     * Return true when activated + toggle (*p_selected) if p_selected != NULL
     */
    public static boolean MenuItem(String label, String shortcut, ImBool p_selected, boolean enabled) {
        return nMenuItem(label, shortcut, p_selected.data, enabled);
    }

    /**
     * Return true when activated + toggle (*p_selected) if p_selected != NULL
     */
    private static native boolean nMenuItem(String label, String shortcut, boolean[] p_selected, boolean enabled); /*
        return ImGui::MenuItem(label, shortcut, &p_selected[0], enabled);
    */

    // Tooltips
    // - Tooltip are windows following the mouse which do not take focus away.

    /**
     * Begin/append a tooltip window. to create full-featured tooltip (with any kind of items).
     */
    public static native void BeginTooltip(); /*
        ImGui::BeginTooltip();
    */

    public static native void EndTooltip(); /*
        ImGui::EndTooltip();
    */

    /**
     * Set a text-only tooltip, typically use with ImGui::IsItemHovered(). override any previous call to SetTooltip().
     */
    public static native void SetTooltip(String text); /*
        ImGui::SetTooltip(text);
    */

    // Popups, Modals
    // The properties of popups windows are:
    // - They block normal mouse hovering detection outside them. (*)
    // - Unless modal, they can be closed by clicking anywhere outside them, or by pressing ESCAPE.
    // - Their visibility state (~bool) is held internally by imgui instead of being held by the programmer as we are used to with regular Begin() calls.
    //   User can manipulate the visibility state by calling OpenPopup().
    // (*) You can use IsItemHovered(ImGuiHoveredFlags_AllowWhenBlockedByPopup) to bypass it and detect hovering even when normally blocked by a popup.
    // Those three properties are connected. The library needs to hold their visibility state because it can close popups at any time.

    /**
     * Call to mark popup as open (don't call every frame!).
     * Popups are closed when user click outside, or if CloseCurrentPopup() is called within a BeginPopup()/EndPopup() block.
     * By default, Selectable()/MenuItem() are calling CloseCurrentPopup().
     * Popup identifiers are relative to the current ID-stack (so OpenPopup and BeginPopup needs to be at the same level).
     */
    public static native void OpenPopup(String str_id); /*
        ImGui::OpenPopup(str_id);
    */

    /**
     * Return true if the popup is open, and you can start outputting to it. only call EndPopup() if BeginPopup() returns true!
     */
    public static native boolean BeginPopup(String str_id); /*
        return ImGui::BeginPopup(str_id);
    */

    /**
     * Return true if the popup is open, and you can start outputting to it. only call EndPopup() if BeginPopup() returns true!
     */
    public static native boolean BeginPopup(String str_id, int imGuiWindowFlags); /*
        return ImGui::BeginPopup(str_id, imGuiWindowFlags);
    */

    /**
     * helper to open and begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean BeginPopupContextItem(); /*
        return ImGui::BeginPopupContextItem();
    */

    /**
     * helper to open and begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean BeginPopupContextItem(String str_id); /*
        return ImGui::BeginPopupContextItem(str_id);
    */

    /**
     * helper to open and begin popup when clicked on last item. if you can pass a NULL str_id only if the previous item had an id.
     * If you want to use that on a non-interactive item such as Text() you need to pass in an explicit ID here. read comments in .cpp!
     */
    public static native boolean BeginPopupContextItem(String str_id, int mouse_button); /*
        return ImGui::BeginPopupContextItem(str_id, mouse_button);
    */

    /**
     * Helper to open and begin popup when clicked on current window.
     */
    public static native boolean BeginPopupContextWindow(); /*
        return ImGui::BeginPopupContextWindow();
    */

    /**
     * Helper to open and begin popup when clicked on current window.
     */
    public static native boolean BeginPopupContextWindow(String str_id); /*
        return ImGui::BeginPopupContextWindow(str_id);
    */

    /**
     * Helper to open and begin popup when clicked on current window.
     */
    public static native boolean BeginPopupContextWindow(String str_id, int mouse_button); /*
        return ImGui::BeginPopupContextWindow(str_id, mouse_button);
    */

    /**
     * Helper to open and begin popup when clicked on current window.
     */
    public static native boolean BeginPopupContextWindow(String str_id, int mouse_button, boolean also_over_items); /*
        return ImGui::BeginPopupContextWindow(str_id, mouse_button, also_over_items);
    */

    /**
     * Helper to open and begin popup when clicked in void (where there are no imgui windows).
     */
    public static native boolean BeginPopupContextVoid(); /*
        return ImGui::BeginPopupContextVoid();
     */

    /**
     * Helper to open and begin popup when clicked in void (where there are no imgui windows).
     */
    public static native boolean BeginPopupContextVoid(String str_id); /*
        return ImGui::BeginPopupContextVoid(str_id);
    */

    /**
     * Helper to open and begin popup when clicked in void (where there are no imgui windows).
     */
    public static native boolean BeginPopupContextVoid(String str_id, int mouse_button); /*
        return ImGui::BeginPopupContextVoid(str_id, mouse_button);
    */

    /**
     * Modal dialog (regular window with title bar, block interactions behind the modal window, can't close the modal window by clicking outside)
     */
    public static native boolean BeginPopupModal(String name); /*
        return ImGui::BeginPopupModal(name);
    */

    /**
     * Modal dialog (regular window with title bar, block interactions behind the modal window, can't close the modal window by clicking outside)
     */
    public static boolean BeginPopupModal(String name, ImBool p_open) {
        return nBeginPopupModal(name, p_open.data, 0);
    }

    /**
     * Modal dialog (regular window with title bar, block interactions behind the modal window, can't close the modal window by clicking outside)
     */
    public static boolean BeginPopupModal(String name, ImBool p_open, int imGuiWindowFlags) {
        return nBeginPopupModal(name, p_open.data, imGuiWindowFlags);
    }

    private static native boolean nBeginPopupModal(String name, boolean[] p_open, int imGuiWindowFlags); /*
        return ImGui::BeginPopupModal(name, &p_open[0], imGuiWindowFlags);
    */

    /**
     * only call EndPopup() if BeginPopupXXX() returns true!
     */
    public static native void EndPopup(); /*
        ImGui::EndPopup();
    */

    /**
     * Helper to open popup when clicked on last item (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors).
     * return true when just opened.
     */
    public static native boolean OpenPopupOnItemClick(); /*
        return ImGui::OpenPopupOnItemClick();
    */

    /**
     * Helper to open popup when clicked on last item (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors).
     * return true when just opened.
     */
    public static native boolean OpenPopupOnItemClick(String str_id); /*
        return ImGui::OpenPopupOnItemClick(str_id);
    */

    /**
     * Helper to open popup when clicked on last item (note: actually triggers on the mouse _released_ event to be consistent with popup behaviors).
     * return true when just opened.
     */
    public static native boolean OpenPopupOnItemClick(String str_id, int mouse_button); /*
        return ImGui::OpenPopupOnItemClick(str_id, mouse_button);
    */

    /**
     * Return true if the popup is open at the current begin-ed level of the popup stack.
     */
    public static native boolean IsPopupOpen(String str_id); /*
        return ImGui::IsPopupOpen(str_id);
    */

    /**
     * Close the popup we have begin-ed into. clicking on a MenuItem or Selectable automatically close the current popup.
     */
    public static native void CloseCurrentPopup(); /*
        ImGui::CloseCurrentPopup();
    */

    // Columns
    // - You can also use SameLine(pos_x) to mimic simplified columns.
    // - The columns API is work-in-progress and rather lacking (columns are arguably the worst part of dear imgui at the moment!)
    // - By end of the 2019 we will expose a new 'Table' api which will replace columns.

    public static native void Columns(); /*
        ImGui::Columns();
    */

    public static native void Columns(int count); /*
        ImGui::Columns(count);
    */

    public static native void Columns(int count, String id); /*
        ImGui::Columns(count, id);
    */

    public static native void Columns(int count, String id, boolean border); /*
        ImGui::Columns(count, id, border);
    */

    /**
     * Next column, defaults to current row or next row if the current row is finished
     */
    public static native void NextColumn(); /*
        ImGui::NextColumn();
    */

    /**
     * Get current column index
     */
    public static native int GetColumnIndex(); /*
        return ImGui::GetColumnIndex();
     */

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    public static native float GetColumnWidth(); /*
        return ImGui::GetColumnWidth();
    */

    /**
     * Get column width (in pixels). pass -1 to use current column
     */
    public static native float GetColumnWidth(int column_index); /*
        return ImGui::GetColumnWidth(column_index);
    */

    /**
     * Set column width (in pixels). pass -1 to use current column
     */
    public static native void SetColumnWidth(int column_index, float width); /*
        ImGui::SetColumnWidth(column_index, width);
    */

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    public static native float GetColumnOffset(); /*
        return ImGui::GetColumnOffset();
    */

    /**
     * Get position of column line (in pixels, from the left side of the contents region). pass -1 to use current column, otherwise 0..GetColumnsCount() inclusive. column 0 is typically 0.0f
     */
    public static native float GetColumnOffset(int column_index); /*
        return ImGui::GetColumnOffset(column_index);
    */

    /**
     * Set position of column line (in pixels, from the left side of the contents region). pass -1 to use current column
     */
    public static native void SetColumnOffset(int column_index, float offset_x); /*
        ImGui::SetColumnOffset(column_index, offset_x);
    */

    public static native int GetColumnsCount(); /*
        return ImGui::GetColumnsCount();
    */

    // Tab Bars, Tabs

    /**
     * Create and append into a TabBar
     */
    public static native boolean BeginTabBar(String str_id); /*
        return ImGui::BeginTabBar(str_id);
    */

    /**
     * Create and append into a TabBar
     */
    public static native boolean BeginTabBar(String str_id, int imGuiTabBarFlags); /*
        return ImGui::BeginTabBar(str_id, imGuiTabBarFlags);
    */

    /**
     * Only call EndTabBar() if BeginTabBar() returns true!
     */
    public static native void EndTabBar(); /*
        ImGui::EndTabBar();
    */

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static native boolean BeginTabItem(String label); /*
        return ImGui::BeginTabItem(label);
    */

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean BeginTabItem(String label, ImBool p_open) {
        return nBeginTabItem(label, p_open.data, 0);
    }

    /**
     * Create a Tab. Returns true if the Tab is selected.
     */
    public static boolean BeginTabItem(String label, ImBool p_open, int imGuiTabBarFlags) {
        return nBeginTabItem(label, p_open.data, imGuiTabBarFlags);
    }

    private static native boolean nBeginTabItem(String label, boolean[] p_open, int imGuiTabBarFlags); /*
        return ImGui::BeginTabItem(label, &p_open[0], imGuiTabBarFlags);
    */

    /**
     * Only call EndTabItem() if BeginTabItem() returns true!
     */
    public static native void EndTabItem(); /*
        ImGui::EndTabItem();
    */

    /**
     * Notify TabBar or Docking system of a closed tab/window ahead (useful to reduce visual flicker on reorderable tab bars).
     * For tab-bar: call after BeginTabBar() and before Tab submissions. Otherwise call with a window name.
     */
    public static native void SetTabItemClosed(String tab_or_docked_window_label); /*
        ImGui::SetTabItemClosed(tab_or_docked_window_label);
    */

    // Logging/Capture
    // - All text output from the interface can be captured into tty/file/clipboard. By default, tree nodes are automatically opened during logging.

    /**
     * Start logging to tty (stdout)
     */
    public static native void LogToTTY(); /*
        ImGui::LogToTTY();
    */

    /**
     * Start logging to tty (stdout)
     */
    public static native void LogToTTY(int auto_open_depth); /*
        ImGui::LogToTTY(auto_open_depth);
    */

    /**
     * Start logging to file
     */
    public static native void LogToFile(); /*
        ImGui::LogToFile();
    */

    /**
     * Start logging to file
     */
    public static native void LogToFile(int auto_open_depth); /*
        ImGui::LogToFile(auto_open_depth);
    */

    /**
     * Start logging to file
     */
    public static native void LogToFile(int auto_open_depth, String filename); /*
        ImGui::LogToFile(auto_open_depth, filename);
    */

    /**
     * Start logging to OS clipboard
     */
    public static native void LogToClipboard(); /*
        ImGui::LogToClipboard();
    */


    /**
     * Start logging to OS clipboard
     */
    public static native void LogToClipboard(int auto_open_depth); /*
        ImGui::LogToClipboard(auto_open_depth);
    */

    /**
     * Stop logging (close file, etc.)
     */
    public static native void LogFinish(); /*
        ImGui::LogFinish();
    */

    /**
     * Helper to display buttons for logging to tty/file/clipboard
     */
    public static native void LogButtons(); /*
        ImGui::LogButtons();
    */

    /**
     * Pass text data straight to log (without being displayed)
     */
    public static native void LogText(String text); /*
        ImGui::LogText(text);
    */

    // Drag and Drop
    // [BETA API] API may evolve!

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    public static native boolean BeginDragDropSource(); /*
        return ImGui::BeginDragDropSource();
    */

    /**
     * Call when the current item is active. If this return true, you can call SetDragDropPayload() + EndDragDropSource()
     */
    public static native boolean BeginDragDropSource(int imGuiDragDropFlags); /*
        return ImGui::BeginDragDropSource(imGuiDragDropFlags);
    */

    /**
     * Type is a user defined string of maximum 32 characters. Strings starting with '_' are reserved for dear imgui internal types.
     * Data is copied and held by imgui.
     */
    public static native boolean SetDragDropPayload(String type, byte[] data, int sz); /*
        return ImGui::SetDragDropPayload(type, &data[0], sz);
    */

    /**
     * Type is a user defined string of maximum 32 characters. Strings starting with '_' are reserved for dear imgui internal types.
     * Data is copied and held by imgui.
     */
    public static native boolean SetDragDropPayload(String type, byte[] data, int sz, int imGuiCond); /*
        return ImGui::SetDragDropPayload(type, &data[0], sz, imGuiCond);
    */

    /**
     * Only call EndDragDropSource() if BeginDragDropSource() returns true!
     */
    public static native void EndDragDropSource(); /*
        ImGui::EndDragDropSource();
    */

    /**
     * Call after submitting an item that may receive a payload. If this returns true, you can call AcceptDragDropPayload() + EndDragDropTarget()
     */
    public static native boolean BeginDragDropTarget(); /*
        return ImGui::BeginDragDropTarget();
    */

    /**
     * Accept contents of a given type. If ImGuiDragDropFlags_AcceptBeforeDelivery is set you can peek into the payload before the mouse button is released.
     */
    public static byte[] AcceptDragDropPayload(String type) {
        return nAcceptDragDropPayload(type, 0);
    }

    /**
     * Accept contents of a given type. If ImGuiDragDropFlags_AcceptBeforeDelivery is set you can peek into the payload before the mouse button is released.
     */
    public static byte[] AcceptDragDropPayload(String type, int imGuiDragDropFlags) {
        return nAcceptDragDropPayload(type, imGuiDragDropFlags);
    }

    private static native byte[] nAcceptDragDropPayload(String type, int imGuiDragDropFlags); /*
        if (const ImGuiPayload* payload = ImGui::AcceptDragDropPayload(type, imGuiDragDropFlags)) {
            jbyteArray array = env->NewByteArray(payload->DataSize);
            env->SetByteArrayRegion(array, 0, payload->DataSize, (jbyte*)payload->Data);
            return array;
        }
        return NULL;
    */

    /**
     * Only call EndDragDropTarget() if BeginDragDropTarget() returns true!
     */
    public static native void EndDragDropTarget(); /*
        ImGui::EndDragDropTarget();
    */

    /**
     * Peek directly into the current payload from anywhere. May return NULL. use ImGuiPayload::IsDataType() to test for the payload type.
     * TODO implement ImGuiPayload class
     */
    public static native byte[] GetDragDropPayload(); /*
        if (const ImGuiPayload* payload = ImGui::GetDragDropPayload()) {
            jbyteArray array = env->NewByteArray(payload->DataSize);
            env->SetByteArrayRegion(array, 0, payload->DataSize, (jbyte*)payload->Data);
            return array;
        }
        return NULL;
    */

    // Clipping

    public static native void PushClipRect(float clip_rect_min_x, float clip_rect_min_y, float clip_rect_max_x, float clip_rect_max_y, boolean intersect_with_current_clip_rect); /*
        ImGui::PushClipRect(ImVec2(clip_rect_min_x, clip_rect_min_y), ImVec2(clip_rect_max_x, clip_rect_max_y), intersect_with_current_clip_rect);
    */

    public static native void PopClipRect(); /*
        ImGui::PopClipRect();
    */

    // Focus, Activation
    // - Prefer using "SetItemDefaultFocus()" over "if (IsWindowAppearing()) SetScrollHereY()" when applicable to signify "this is the default item"

    /**
     * Make last item the default focused item of a window.
     */
    public static native void SetItemDefaultFocus(); /*
        ImGui::SetItemDefaultFocus();
    */

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    public static native void SetKeyboardFocusHere(); /*
        ImGui::SetKeyboardFocusHere();
    */

    /**
     * Focus keyboard on the next widget. Use positive 'offset' to access sub components of a multiple component widget. Use -1 to access previous widget.
     */
    public static native void SetKeyboardFocusHere(int offset); /*
        ImGui::SetKeyboardFocusHere(offset);
    */

    // Item/Widgets Utilities
    // - Most of the functions are referring to the last/previous item we submitted.
    // - See Demo Window under "Widgets->Querying Status" for an interactive visualization of most of those functions.

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    public static native boolean IsItemHovered(); /*
        return ImGui::IsItemHovered();
    */

    /**
     * Is the last item hovered? (and usable, aka not blocked by a popup, etc.). See ImGuiHoveredFlags for more options.
     */
    public static native boolean IsItemHovered(int imGuiHoveredFlags); /*
        return ImGui::IsItemHovered(imGuiHoveredFlags);
    */

    /**
     * Is the last item active? (e.g. button being held, text field being edited.
     * This will continuously return true while holding mouse button on an item.
     * Items that don't interact will always return false)
     */
    public static native boolean IsItemActive(); /*
        return ImGui::IsItemActive();
    */

    /**
     * Is the last item focused for keyboard/gamepad navigation?
     */
    public static native boolean IsItemFocused(); /*
        return ImGui::IsItemFocused();
    */

    /**
     * Is the last item clicked? (e.g. button/node just clicked on) == IsMouseClicked(mouse_button) && IsItemHovered()
     */
    public static native boolean IsItemClicked(); /*
        return ImGui::IsItemClicked();
    */

    /**
     * Is the last item clicked? (e.g. button/node just clicked on) == IsMouseClicked(mouse_button) && IsItemHovered()
     */
    public static native boolean IsItemClicked(int mouse_button); /*
        return ImGui::IsItemClicked(mouse_button);
    */

    /**
     * Is the last item visible? (items may be out of sight because of clipping/scrolling)
     */
    public static native boolean IsItemVisible(); /*
        return ImGui::IsItemVisible();
    */

    /**
     * Did the last item modify its underlying value this frame? or was pressed? This is generally the same as the "bool" return value of many widgets.
     */
    public static native boolean IsItemEdited(); /*
        return ImGui::IsItemEdited();
    */

    /**
     * Was the last item just made active (item was previously inactive).
     */
    public static native boolean IsItemActivated(); /*
        return ImGui::IsItemActivated();
    */

    /**
     * Was the last item just made inactive (item was previously active). Useful for Undo/Redo patterns with widgets that requires continuous editing.
     */
    public static native boolean IsItemDeactivated(); /*
        return ImGui::IsItemDeactivated();
    */

    /**
     * Was the last item just made inactive and made a value change when it was active? (e.g. Slider/Drag moved).
     * Useful for Undo/Redo patterns with widgets that requires continuous editing.
     * Note that you may get false positives (some widgets such as Combo()/ListBox()/Selectable() will return true even when clicking an already selected item).
     */
    public static native boolean IsItemDeactivatedAfterEdit(); /*
        return ImGui::IsItemDeactivatedAfterEdit();
    */

    /**
     * Was the last item open state toggled? set by TreeNode().
     */
    public static native boolean IsItemToggledOpen(); /*
        return ImGui::IsItemToggledOpen();
    */

    /**
     * Is any item hovered?
     */
    public static native boolean IsAnyItemHovered(); /*
        return ImGui::IsAnyItemHovered();
    */

    /**
     * Is any item active?
     */
    public static native boolean IsAnyItemActive(); /*
        return ImGui::IsAnyItemActive();
    */

    /**
     * Is any item focused?
     */
    public static native boolean IsAnyItemFocused(); /*
        return ImGui::IsAnyItemFocused();
    */

    /**
     * Get upper-left bounding rectangle of the last item (screen space)
     */
    public static native void GetItemRectMin(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectMin(), dstImVec2);
    */

    /**
     * Get lower-right bounding rectangle of the last item (screen space)
     */
    public static native void GetItemRectMax(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectMax(), dstImVec2);
    */

    /**
     * Get size of last item
     */
    public static native void GetItemRectSize(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetItemRectSize(), dstImVec2);
    */

    /**
     * Allow last item to be overlapped by a subsequent item. sometimes useful with invisible buttons, selectables, etc. to catch unused area.
     */
    public static native void SetItemAllowOverlap(); /*
        ImGui::SetItemAllowOverlap();
    */

    // Miscellaneous Utilities

    /**
     * Test if rectangle (of given size, starting from cursor position) is visible / not clipped.
     */
    public static native boolean IsRectVisible(float width, float height); /*
        return ImGui::IsRectVisible(ImVec2(width, height));
    */

    /**
     * Test if rectangle (in screen space) is visible / not clipped. to perform coarse clipping on user's side.
     */
    public static native boolean IsRectVisible(float min_x, float min_y, float max_x, float max_y); /*
        return ImGui::IsRectVisible(ImVec2(min_x, min_y), ImVec2(max_x, max_y));
    */

    /**
     * Get global imgui time. incremented by io.DeltaTime every frame.
     */
    public static native double GetTime(); /*
        return ImGui::GetTime();
    */

    /**
     * Get global imgui frame count. incremented by 1 every frame.
     */
    public static native int GetFrameCount(); /*
        return ImGui::GetFrameCount();
    */

    /**
     * This draw list will be the first rendering one. Useful to quickly draw shapes/text behind dear imgui contents.
     */
    public static ImDrawList GetBackgroundDrawList() {
        return IM_DRAW_LIST_BACKGROUND;
    }

    /**
     * This draw list will be the last rendered one. Useful to quickly draw shapes/text over dear imgui contents.
     */
    public static ImDrawList GetForegroundDrawList() {
        return IM_DRAW_LIST_FOREGROUND;
    }

    // TODO GetDrawListSharedData

    /**
     * Get a string corresponding to the enum value (for display, saving, etc.).
     */
    public static native String GetStyleColorName(int imGuiCol); /*
        return env->NewStringUTF(ImGui::GetStyleColorName(imGuiCol));
    */

    // TODO SetStateStorage, GetStateStorage

    public static native void CalcTextSize(ImVec2 dstImVec2, String text); /*
        ImVec2 src = ImGui::CalcTextSize(text);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    public static native void CalcTextSize(ImVec2 dstImVec2, String text, String text_end); /*
        ImVec2 src = ImGui::CalcTextSize(text, text_end);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    public static native void CalcTextSize(ImVec2 dstImVec2, String text, String text_end, boolean hide_text_after_double_has); /*
        ImVec2 src = ImGui::CalcTextSize(text, text_end, hide_text_after_double_has);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    public static native void CalcTextSize(ImVec2 dstImVec2, String text, String text_end, boolean hide_text_after_double_has, float wrap_width); /*
        ImVec2 src = ImGui::CalcTextSize(text, text_end, hide_text_after_double_has, wrap_width);
        Jni::ImVec2Cpy(env, src, dstImVec2);
    */

    /**
     * Calculate coarse clipping for large list of evenly sized items. Prefer using the ImGuiListClipper higher-level helper if you can.
     */
    public static native void CalcListClipping(int item_count, float items_height, int[] out_items_display_start, int[] out_items_display_end); /*
        ImGui::CalcListClipping(item_count, items_height, &out_items_display_start[0], &out_items_display_end[0]);
    */

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static native boolean BeginChildFrame(int id, float width, float height); /*
        return ImGui::BeginChildFrame(id, ImVec2(width, height));
    */

    /**
     * Helper to create a child window / scrolling region that looks like a normal widget frame
     */
    public static native boolean BeginChildFrame(int id, float width, float height, int imGuiWindowFlags); /*
        return ImGui::BeginChildFrame(id, ImVec2(width, height), imGuiWindowFlags);
    */

    /**
     * Always call EndChildFrame() regardless of BeginChildFrame() return values (which indicates a collapsed/clipped window)
     */
    public static native void EndChildFrame(); /*
        ImGui::EndChildFrame();
    */

    // Color Utilities

    public static native void ColorConvertU32ToFloat4(long in, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, ImGui::ColorConvertU32ToFloat4(in), dstImVec4);
    */

    public static native long ColorConvertFloat4ToU32(float r, float g, float b, float a); /*
        return ImGui::ColorConvertFloat4ToU32(ImVec4(r, g, b, a));
    */

    public static native void ColorConvertRGBtoHSV(float[] rgb, float[] hsv); /*
        ImGui::ColorConvertRGBtoHSV(rgb[0], rgb[1], rgb[2], hsv[0], hsv[1], hsv[2]);
    */

    public static native void ColorConvertHSVtoRGB(float[] hsv, float[] rgb); /*
        ImGui::ColorConvertHSVtoRGB(hsv[0], hsv[1], hsv[2], rgb[0], rgb[1], rgb[2]);
    */

    // Inputs Utilities

    /**
     * Map ImGuiKey_* values into user's key index. == io.KeyMap[key]
     */
    public static native int GetKeyIndex(int imgui_key); /*
        return ImGui::GetKeyIndex(imgui_key);
    */

    /**
     * Is key being held. == io.KeysDown[user_key_index]. note that imgui doesn't know the semantic of each entry of io.KeysDown[].
     * Use your own indices/enums according to how your backend/engine stored them into io.KeysDown[]!
     */
    public static native boolean IsKeyDown(int user_key_index); /*
        return ImGui::IsKeyDown(user_key_index);
    */

    /**
     * Was key pressed (went from !Down to Down). if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    public static native boolean IsKeyPressed(int user_key_index); /*
        return ImGui::IsKeyPressed(user_key_index);
    */

    /**
     * Was key pressed (went from !Down to Down). if repeat=true, uses io.KeyRepeatDelay / KeyRepeatRate
     */
    public static native boolean IsKeyPressed(int user_key_index, boolean repeat); /*
        return ImGui::IsKeyPressed(user_key_index, repeat);
    */

    /**
     * Was key released (went from Down to !Down)..
     */
    public static native boolean IsKeyReleased(int user_key_index); /*
        return ImGui::IsKeyReleased(user_key_index);
    */

    /**
     * uUses provided repeat rate/delay. return a count, most often 0 or 1 but might be >1 if RepeatRate is small enough that DeltaTime > RepeatRate
     */
    public static native boolean GetKeyPressedAmount(int key_index, float repeat_delay, float rate); /*
        return ImGui::GetKeyPressedAmount(key_index, repeat_delay, rate);
    */

    /**
     * Is mouse button held (0=left, 1=right, 2=middle)
     */
    public static native boolean IsMouseDown(int button); /*
        return ImGui::IsMouseDown(button);
    */

    /**
     * Is any mouse button held
     */
    public static native boolean IsAnyMouseDown(); /*
        return ImGui::IsAnyMouseDown();
    */

    /**
     * Did mouse button clicked (went from !Down to Down) (0=left, 1=right, 2=middle)
     */
    public static native boolean IsMouseClicked(int button); /*
        return ImGui::IsMouseClicked(button);
    */

    public static native boolean IsMouseClicked(int button, boolean repeat); /*
        return ImGui::IsMouseClicked(button, repeat);
    */

    /**
     * Did mouse button double-clicked. a double-click returns false in IsMouseClicked(). uses io.MouseDoubleClickTime.
     */
    public static native boolean IsMouseDoubleClicked(int button); /*
        return ImGui::IsMouseDoubleClicked(button);
    */

    /**
     * Did mouse button released (went from Down to !Down)
     */
    public static native boolean IsMouseReleased(int button); /*
        return ImGui::IsMouseReleased(button);
    */

    /**
     * Is mouse dragging. if lock_threshold < -1.0f uses io.MouseDraggingThreshold
     */
    public static native boolean IsMouseDragging(); /*
        return ImGui::IsMouseDragging();
    */

    /**
     * Is mouse dragging. if lock_threshold < -1.0f uses io.MouseDraggingThreshold
     */
    public static native boolean IsMouseDragging(int button); /*
        return ImGui::IsMouseDragging(button);
    */

    /**
     * Is mouse dragging. if lock_threshold < -1.0f uses io.MouseDraggingThreshold
     */
    public static native boolean IsMouseDragging(int button, float lock_threshold); /*
        return ImGui::IsMouseDragging(button, lock_threshold);
    */

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static native boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY); /*
        return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY));
    */

    /**
     * Is mouse hovering given bounding rect (in screen space). clipped by current clipping settings, but disregarding of other consideration of focus/window ordering/popup-block.
     */
    public static native boolean IsMouseHoveringRect(float minX, float minY, float maxX, float maxY, boolean clip); /*
        return ImGui::IsMouseHoveringRect(ImVec2(minX, minY), ImVec2(maxX, maxY), clip);
    */

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static native boolean IsMousePosValid(); /*
        return ImGui::IsMousePosValid();
    */

    /**
     * By convention we use (-FLT_MAX,-FLT_MAX) to denote that there is no mouse
     */
    public static native boolean IsMousePosValid(float mouse_pos_x, float mouse_pos_y); /*
        ImVec2 pos = ImVec2(mouse_pos_x, mouse_pos_y);
        return ImGui::IsMousePosValid(&pos);
    */

    /**
     * Shortcut to ImGui::GetIO().MousePos provided by user, to be consistent with other calls
     */
    public static native void GetMousePos(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetMousePos(), dstImVec2);
    */

    /**
     * Retrieve backup of mouse position at the time of opening popup we have BeginPopup() into
     */
    public static native void GetMousePosOnOpeningCurrentPopup(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetMousePosOnOpeningCurrentPopup(), dstImVec2);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lock_threshold < -1.0f uses io.MouseDraggingThreshold.
     */
    public static native void GetMouseDragDelta(ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(), dstImVec2);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lock_threshold < -1.0f uses io.MouseDraggingThreshold.
     */
    public static native void GetMouseDragDelta(ImVec2 dstImVec2, int button); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(button), dstImVec2);
    */

    /**
     * Return the delta from the initial clicking position while the mouse button is pressed or was just released.
     * This is locked and return 0.0f until the mouse moves past a distance threshold at least once. If lock_threshold < -1.0f uses io.MouseDraggingThreshold.
     */
    public static native void GetMouseDragDelta(ImVec2 dstImVec2, int button, float lock_threshold); /*
        Jni::ImVec2Cpy(env, ImGui::GetMouseDragDelta(button, lock_threshold), dstImVec2);
    */

    public static native void ResetMouseDragDelta(); /*
        ImGui::ResetMouseDragDelta();
    */

    public static native void ResetMouseDragDelta(int button); /*
        ImGui::ResetMouseDragDelta(button);
    */

    /**
     * Get desired cursor type, reset in ImGui::NewFrame(), this is updated during the frame. valid before Render().
     * If you use software rendering by setting io.MouseDrawCursor ImGui will render those for you
     */
    public static native int GetMouseCursor(); /*
        return ImGui::GetMouseCursor();
    */

    /**
     * Set desired cursor type
     */
    public static native void SetMouseCursor(int type); /*
        ImGui::SetMouseCursor(type);
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureKeyboard flag next frame (said flag is entirely left for your application to handle).
     * e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = want_capture_keyboard_value"; after the next NewFrame() call.
     */
    public static native void CaptureKeyboardFromApp(); /*
        ImGui::CaptureKeyboardFromApp();
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureKeyboard flag next frame (said flag is entirely left for your application to handle).
     * e.g. force capture keyboard when your widget is being hovered.
     * This is equivalent to setting "io.WantCaptureKeyboard = want_capture_keyboard_value"; after the next NewFrame() call.
     */
    public static native void CaptureKeyboardFromApp(boolean want_capture_keyboard_value); /*
        ImGui::CaptureKeyboardFromApp(want_capture_keyboard_value);
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureMouse flag next frame (said flag is entirely left for your application to handle).
     * This is equivalent to setting "io.WantCaptureMouse = want_capture_mouse_value;" after the next NewFrame() call.
     */
    public static native void CaptureMouseFromApp(); /*
        ImGui::CaptureMouseFromApp();
    */

    /**
     * Attention: misleading name! manually override io.WantCaptureMouse flag next frame (said flag is entirely left for your application to handle).
     * This is equivalent to setting "io.WantCaptureMouse = want_capture_mouse_value;" after the next NewFrame() call.
     */
    public static native void CaptureMouseFromApp(boolean want_capture_mouse_value); /*
        ImGui::CaptureMouseFromApp(want_capture_mouse_value);
    */

    // Settings/.Ini Utilities
    // - The disk functions are automatically called if io.IniFilename != NULL (default is "imgui.ini").
    // - Set io.IniFilename to NULL to load/save manually. Read io.WantSaveIniSettings description about handling .ini saving manually.

    /**
     * Call after CreateContext() and before the first call to NewFrame(). NewFrame() automatically calls LoadIniSettingsFromDisk(io.IniFilename).
     */
    public static native void LoadIniSettingsFromDisk(String ini_filename); /*
        ImGui::LoadIniSettingsFromDisk(ini_filename);
    */

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    public static native void LoadIniSettingsFromMemory(String ini_data); /*
        ImGui::LoadIniSettingsFromMemory(ini_data);
    */

    /**
     * Call after CreateContext() and before the first call to NewFrame() to provide .ini data from your own data source.
     */
    public static native void LoadIniSettingsFromMemory(String ini_data, int ini_size); /*
        ImGui::LoadIniSettingsFromMemory(ini_data, ini_size);
    */

    /**
     * This is automatically called (if io.IniFilename is not empty) a few seconds after any modification that should be reflected in the .ini file (and also by DestroyContext).
     */
    public static native void SaveIniSettingsToDisk(String ini_filename); /*
        ImGui::SaveIniSettingsToDisk(ini_filename);
    */

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    public static native String SaveIniSettingsToMemory(); /*
        return env->NewStringUTF(ImGui::SaveIniSettingsToMemory());
    */

    /**
     * Return a zero-terminated string with the .ini data which you can save by your own mean.
     * Call when io.WantSaveIniSettings is set, then save data by your own mean and clear io.WantSaveIniSettings.
     */
    public static native String SaveIniSettingsToMemory(long out_ini_size); /*
        return env->NewStringUTF(ImGui::SaveIniSettingsToMemory((size_t*)&out_ini_size));
    */
}
