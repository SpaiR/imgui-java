package imgui.internal;

import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImGuiPlatformMonitor;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public final class ImGui extends imgui.ImGui {
    /*JNI
        #include "_common.h"
        #include "_internal.h"
     */

    static {
        nInit();
    }

    public static void init() {
    }

    private static native void nInit(); /*
        Jni::InitInternal(env);
    */

    // Windows
    // We should always have a CurrentWindow in the stack (there is an implicit "Debug" window)
    // If this ever crash because g.CurrentWindow is NULL it means that either
    // - ImGui::NewFrame() has never been called, which is illegal.
    // - You are calling ImGui functions after ImGui::EndFrame()/ImGui::Render() and before the next ImGui::NewFrame(), which is also illegal.

    public static ImGuiWindow getCurrentWindowRead() {
        return new ImGuiWindow(nGetCurrentWindowRead());
    }

    private static native long nGetCurrentWindowRead(); /*
        return (uintptr_t)ImGui::GetCurrentWindowRead();
    */

    public static ImGuiWindow getCurrentWindow() {
        return new ImGuiWindow(nGetCurrentWindow());
    }

    private static native long nGetCurrentWindow(); /*
        return (uintptr_t)ImGui::GetCurrentWindow();
    */

    public static ImGuiWindow findWindowByID(final int id) {
        return new ImGuiWindow(nFindWindowByID(id));
    }

    private static native long nFindWindowByID(int id); /*
        return (uintptr_t)ImGui::FindWindowByID(id);
    */

    public static ImGuiWindow findWindowByName(final String name) {
        return new ImGuiWindow(nFindWindowByName(name));
    }

    private static native long nFindWindowByName(String obj_name); /*MANUAL
        auto name = obj_name == NULL ? NULL : (char*)env->GetStringUTFChars(obj_name, JNI_FALSE);
        auto _result = (uintptr_t)ImGui::FindWindowByName(name);
        if (name != NULL) env->ReleaseStringUTFChars(obj_name, name);
        return _result;
    */

    public static void updateWindowParentAndRootLinks(final ImGuiWindow window, final int flags, final ImGuiWindow parentWindow) {
        nUpdateWindowParentAndRootLinks(window.ptr, flags, parentWindow.ptr);
    }

    private static native void nUpdateWindowParentAndRootLinks(long window, int flags, long parentWindow); /*
        ImGui::UpdateWindowParentAndRootLinks(reinterpret_cast<ImGuiWindow*>(window), flags, reinterpret_cast<ImGuiWindow*>(parentWindow));
    */

    public static ImVec2 calcWindowNextAutoFitSize(final ImGuiWindow window) {
        final ImVec2 dst = new ImVec2();
        nCalcWindowNextAutoFitSize(dst, window.ptr);
        return dst;
    }

    public static float calcWindowNextAutoFitSizeX(final ImGuiWindow window) {
        return nCalcWindowNextAutoFitSizeX(window.ptr);
    }

    public static float calcWindowNextAutoFitSizeY(final ImGuiWindow window) {
        return nCalcWindowNextAutoFitSizeY(window.ptr);
    }

    public static void calcWindowNextAutoFitSize(final ImVec2 dst, final ImGuiWindow window) {
        nCalcWindowNextAutoFitSize(dst, window.ptr);
    }

    private static native void nCalcWindowNextAutoFitSize(ImVec2 dst, long window); /*
        Jni::ImVec2Cpy(env, ImGui::CalcWindowNextAutoFitSize(reinterpret_cast<ImGuiWindow*>(window)), dst);
    */

    private static native float nCalcWindowNextAutoFitSizeX(long window); /*
        return ImGui::CalcWindowNextAutoFitSize(reinterpret_cast<ImGuiWindow*>(window)).x;
    */

    private static native float nCalcWindowNextAutoFitSizeY(long window); /*
        return ImGui::CalcWindowNextAutoFitSize(reinterpret_cast<ImGuiWindow*>(window)).y;
    */

    public static boolean isWindowChildOf(final ImGuiWindow window, final ImGuiWindow potentialParent, final boolean popupHierarchy, final boolean dockHierarchy) {
        return nIsWindowChildOf(window.ptr, potentialParent.ptr, popupHierarchy, dockHierarchy);
    }

    private static native boolean nIsWindowChildOf(long window, long potentialParent, boolean popupHierarchy, boolean dockHierarchy); /*
        return ImGui::IsWindowChildOf(reinterpret_cast<ImGuiWindow*>(window), reinterpret_cast<ImGuiWindow*>(potentialParent), popupHierarchy, dockHierarchy);
    */

    public static boolean isWindowWithinBeginStackOf(final ImGuiWindow window, final ImGuiWindow potentialParent) {
        return nIsWindowWithinBeginStackOf(window.ptr, potentialParent.ptr);
    }

    private static native boolean nIsWindowWithinBeginStackOf(long window, long potentialParent); /*
        return ImGui::IsWindowWithinBeginStackOf(reinterpret_cast<ImGuiWindow*>(window), reinterpret_cast<ImGuiWindow*>(potentialParent));
    */

    public static boolean isWindowAbove(final ImGuiWindow potentialAbove, final ImGuiWindow potentialBelow) {
        return nIsWindowAbove(potentialAbove.ptr, potentialBelow.ptr);
    }

    private static native boolean nIsWindowAbove(long potentialAbove, long potentialBelow); /*
        return ImGui::IsWindowAbove(reinterpret_cast<ImGuiWindow*>(potentialAbove), reinterpret_cast<ImGuiWindow*>(potentialBelow));
    */

    public static boolean isWindowNavFocusable(final ImGuiWindow window) {
        return nIsWindowNavFocusable(window.ptr);
    }

    private static native boolean nIsWindowNavFocusable(long window); /*
        return ImGui::IsWindowNavFocusable(reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void setWindowPos(final ImGuiWindow window, final ImVec2 pos) {
        nSetWindowPos(window.ptr, pos.x, pos.y);
    }

    public static void setWindowPos(final ImGuiWindow window, final float posX, final float posY) {
        nSetWindowPos(window.ptr, posX, posY);
    }

    public static void setWindowPos(final ImGuiWindow window, final ImVec2 pos, final int cond) {
        nSetWindowPos(window.ptr, pos.x, pos.y, cond);
    }

    public static void setWindowPos(final ImGuiWindow window, final float posX, final float posY, final int cond) {
        nSetWindowPos(window.ptr, posX, posY, cond);
    }

    private static native void nSetWindowPos(long window, float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetWindowPos(reinterpret_cast<ImGuiWindow*>(window), pos);
    */

    private static native void nSetWindowPos(long window, float posX, float posY, int cond); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::SetWindowPos(reinterpret_cast<ImGuiWindow*>(window), pos, cond);
    */

    public static void setWindowSize(final ImGuiWindow window, final ImVec2 size) {
        nSetWindowSize(window.ptr, size.x, size.y);
    }

    public static void setWindowSize(final ImGuiWindow window, final float sizeX, final float sizeY) {
        nSetWindowSize(window.ptr, sizeX, sizeY);
    }

    public static void setWindowSize(final ImGuiWindow window, final ImVec2 size, final int cond) {
        nSetWindowSize(window.ptr, size.x, size.y, cond);
    }

    public static void setWindowSize(final ImGuiWindow window, final float sizeX, final float sizeY, final int cond) {
        nSetWindowSize(window.ptr, sizeX, sizeY, cond);
    }

    private static native void nSetWindowSize(long window, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowSize(reinterpret_cast<ImGuiWindow*>(window), size);
    */

    private static native void nSetWindowSize(long window, float sizeX, float sizeY, int cond); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowSize(reinterpret_cast<ImGuiWindow*>(window), size, cond);
    */

    public static void setWindowCollapsed(final ImGuiWindow window, final boolean collapsed) {
        nSetWindowCollapsed(window.ptr, collapsed);
    }

    public static void setWindowCollapsed(final ImGuiWindow window, final boolean collapsed, final int cond) {
        nSetWindowCollapsed(window.ptr, collapsed, cond);
    }

    private static native void nSetWindowCollapsed(long window, boolean collapsed); /*
        ImGui::SetWindowCollapsed(reinterpret_cast<ImGuiWindow*>(window), static_cast<bool>(collapsed));
    */

    private static native void nSetWindowCollapsed(long window, boolean collapsed, int cond); /*
        ImGui::SetWindowCollapsed(reinterpret_cast<ImGuiWindow*>(window), static_cast<bool>(collapsed), cond);
    */

    public static void setWindowHitTestHole(final ImGuiWindow window, final ImVec2 pos, final ImVec2 size) {
        nSetWindowHitTestHole(window.ptr, pos.x, pos.y, size.x, size.y);
    }

    public static void setWindowHitTestHole(final ImGuiWindow window, final float posX, final float posY, final float sizeX, final float sizeY) {
        nSetWindowHitTestHole(window.ptr, posX, posY, sizeX, sizeY);
    }

    private static native void nSetWindowHitTestHole(long window, float posX, float posY, float sizeX, float sizeY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::SetWindowHitTestHole(reinterpret_cast<ImGuiWindow*>(window), pos, size);
    */

    public static ImRect windowRectAbsToRel(final ImGuiWindow window, final ImRect r) {
        final ImRect dst = new ImRect();
        nWindowRectAbsToRel(dst, window.ptr, r.min.x, r.min.y, r.max.x, r.max.y);
        return dst;
    }

    public static ImRect windowRectAbsToRel(final ImGuiWindow window, final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        final ImRect dst = new ImRect();
        nWindowRectAbsToRel(dst, window.ptr, rMinX, rMinY, rMaxX, rMaxY);
        return dst;
    }

    public static void windowRectAbsToRel(final ImRect dst, final ImGuiWindow window, final ImRect r) {
        nWindowRectAbsToRel(dst, window.ptr, r.min.x, r.min.y, r.max.x, r.max.y);
    }

    public static void windowRectAbsToRel(final ImRect dst, final ImGuiWindow window, final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        nWindowRectAbsToRel(dst, window.ptr, rMinX, rMinY, rMaxX, rMaxY);
    }

    private static native void nWindowRectAbsToRel(ImRect dst, long window, float rMinX, float rMinY, float rMaxX, float rMaxY); /*
        Jni::ImRectCpy(env, ImGui::WindowRectAbsToRel(reinterpret_cast<ImGuiWindow*>(window), ImRect(rMinX, rMinY, rMaxX, rMaxY)), dst);
    */

    public static ImRect windowRectRelToAbs(final ImGuiWindow window, final ImRect r) {
        final ImRect dst = new ImRect();
        nWindowRectRelToAbs(dst, window.ptr, r.min.x, r.min.y, r.max.x, r.max.y);
        return dst;
    }

    public static ImRect windowRectRelToAbs(final ImGuiWindow window, final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        final ImRect dst = new ImRect();
        nWindowRectRelToAbs(dst, window.ptr, rMinX, rMinY, rMaxX, rMaxY);
        return dst;
    }

    public static void windowRectRelToAbs(final ImRect dst, final ImGuiWindow window, final ImRect r) {
        nWindowRectRelToAbs(dst, window.ptr, r.min.x, r.min.y, r.max.x, r.max.y);
    }

    public static void windowRectRelToAbs(final ImRect dst, final ImGuiWindow window, final float rMinX, final float rMinY, final float rMaxX, final float rMaxY) {
        nWindowRectRelToAbs(dst, window.ptr, rMinX, rMinY, rMaxX, rMaxY);
    }

    private static native void nWindowRectRelToAbs(ImRect dst, long window, float rMinX, float rMinY, float rMaxX, float rMaxY); /*
        Jni::ImRectCpy(env, ImGui::WindowRectRelToAbs(reinterpret_cast<ImGuiWindow*>(window), ImRect(rMinX, rMinY, rMaxX, rMaxY)), dst);
    */


    // Windows: Display Order and Focus Order

    public static void focusWindow(final ImGuiWindow window) {
        nFocusWindow(window.ptr);
    }

    public static void focusWindow(final ImGuiWindow window, final int flags) {
        nFocusWindow(window.ptr, flags);
    }

    private static native void nFocusWindow(long window); /*
        ImGui::FocusWindow(reinterpret_cast<ImGuiWindow*>(window));
    */

    private static native void nFocusWindow(long window, int flags); /*
        ImGui::FocusWindow(reinterpret_cast<ImGuiWindow*>(window), static_cast<ImGuiFocusRequestFlags>(flags));
    */

    public static void focusTopMostWindowUnderOne(final ImGuiWindow underThisWindow, final ImGuiWindow ignoreWindow, final ImGuiViewport filterViewport, final int flags) {
        nFocusTopMostWindowUnderOne(underThisWindow.ptr, ignoreWindow.ptr, filterViewport.ptr, flags);
    }

    private static native void nFocusTopMostWindowUnderOne(long underThisWindow, long ignoreWindow, long filterViewport, int flags); /*
        ImGui::FocusTopMostWindowUnderOne(reinterpret_cast<ImGuiWindow*>(underThisWindow), reinterpret_cast<ImGuiWindow*>(ignoreWindow), reinterpret_cast<ImGuiViewport*>(filterViewport), static_cast<ImGuiFocusRequestFlags>(flags));
    */

    public static void bringWindowToFocusFront(final ImGuiWindow window) {
        nBringWindowToFocusFront(window.ptr);
    }

    private static native void nBringWindowToFocusFront(long window); /*
        ImGui::BringWindowToFocusFront(reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void bringWindowToDisplayFront(final ImGuiWindow window) {
        nBringWindowToDisplayFront(window.ptr);
    }

    private static native void nBringWindowToDisplayFront(long window); /*
        ImGui::BringWindowToDisplayFront(reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void bringWindowToDisplayBack(final ImGuiWindow window) {
        nBringWindowToDisplayBack(window.ptr);
    }

    private static native void nBringWindowToDisplayBack(long window); /*
        ImGui::BringWindowToDisplayBack(reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void bringWindowToDisplayBehind(final ImGuiWindow window, final ImGuiWindow aboveWindow) {
        nBringWindowToDisplayBehind(window.ptr, aboveWindow.ptr);
    }

    private static native void nBringWindowToDisplayBehind(long window, long aboveWindow); /*
        ImGui::BringWindowToDisplayBehind(reinterpret_cast<ImGuiWindow*>(window), reinterpret_cast<ImGuiWindow*>(aboveWindow));
    */

    public static int findWindowDisplayIndex(final ImGuiWindow window) {
        return nFindWindowDisplayIndex(window.ptr);
    }

    private static native int nFindWindowDisplayIndex(long window); /*
        return ImGui::FindWindowDisplayIndex(reinterpret_cast<ImGuiWindow*>(window));
    */

    public static ImGuiWindow findBottomMostVisibleWindowWithinBeginStack(final ImGuiWindow window) {
        return new ImGuiWindow(nFindBottomMostVisibleWindowWithinBeginStack(window.ptr));
    }

    private static native long nFindBottomMostVisibleWindowWithinBeginStack(long window); /*
        return (uintptr_t)ImGui::FindBottomMostVisibleWindowWithinBeginStack(reinterpret_cast<ImGuiWindow*>(window));
    */

    // Fonts, drawing

    public static void setCurrentFont(final ImFont font) {
        nSetCurrentFont(font.ptr);
    }

    private static native void nSetCurrentFont(long font); /*
        ImGui::SetCurrentFont(reinterpret_cast<ImFont*>(font));
    */

    public static ImFont getDefaultFont() {
        return new ImFont(nGetDefaultFont());
    }

    private static native long nGetDefaultFont(); /*
        return (uintptr_t)ImGui::GetDefaultFont();
    */

    public static ImDrawList getForegroundDrawList(final ImGuiWindow window) {
        return new ImDrawList(nGetForegroundDrawList(window.ptr));
    }

    private static native long nGetForegroundDrawList(long window); /*
        return (uintptr_t)ImGui::GetForegroundDrawList(reinterpret_cast<ImGuiWindow*>(window));
    */

    // Init

    public static void initialize() {
        nInitialize();
    }

    private static native void nInitialize(); /*
        ImGui::Initialize();
    */

    public static void shutdown() {
        nShutdown();
    }

    private static native void nShutdown(); /*
        ImGui::Shutdown();
    */

    // NewFrame

    public static void updateInputEvents(final boolean trickleFastInputs) {
        nUpdateInputEvents(trickleFastInputs);
    }

    private static native void nUpdateInputEvents(boolean trickleFastInputs); /*
        ImGui::UpdateInputEvents(trickleFastInputs);
    */

    public static void updateHoveredWindowAndCaptureFlags() {
        nUpdateHoveredWindowAndCaptureFlags();
    }

    private static native void nUpdateHoveredWindowAndCaptureFlags(); /*
        ImGui::UpdateHoveredWindowAndCaptureFlags();
    */

    public static void startMouseMovingWindow(final ImGuiWindow window) {
        nStartMouseMovingWindow(window.ptr);
    }

    private static native void nStartMouseMovingWindow(long window); /*
        ImGui::StartMouseMovingWindow(reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void startMouseMovingWindowOrNode(final ImGuiWindow window, final ImGuiDockNode node, final boolean undockFloatingNode) {
        nStartMouseMovingWindowOrNode(window.ptr, node.ptr, undockFloatingNode);
    }

    private static native void nStartMouseMovingWindowOrNode(long window, long node, boolean undockFloatingNode); /*
        ImGui::StartMouseMovingWindowOrNode(reinterpret_cast<ImGuiWindow*>(window), reinterpret_cast<ImGuiDockNode*>(node), undockFloatingNode);
    */

    public static void updateMouseMovingWindowNewFrame() {
        nUpdateMouseMovingWindowNewFrame();
    }

    private static native void nUpdateMouseMovingWindowNewFrame(); /*
        ImGui::UpdateMouseMovingWindowNewFrame();
    */

    public static void updateMouseMovingWindowEndFrame() {
        nUpdateMouseMovingWindowEndFrame();
    }

    private static native void nUpdateMouseMovingWindowEndFrame(); /*
        ImGui::UpdateMouseMovingWindowEndFrame();
    */

    // TODO: Generic context hooks

    // Viewports

    public static ImGuiPlatformMonitor getViewportPlatformMonitor(final ImGuiViewport viewport) {
        return new ImGuiPlatformMonitor(nGetViewportPlatformMonitor(viewport.ptr));
    }

    private static native long nGetViewportPlatformMonitor(long viewport); /*
        return (uintptr_t)ImGui::GetViewportPlatformMonitor(reinterpret_cast<ImGuiViewport*>(viewport));
    */

    // TODO: Settings

    // Scrolling

    public static void setNextWindowScroll(final ImVec2 scroll) {
        nSetNextWindowScroll(scroll.x, scroll.y);
    }

    public static void setNextWindowScroll(final float scrollX, final float scrollY) {
        nSetNextWindowScroll(scrollX, scrollY);
    }

    private static native void nSetNextWindowScroll(float scrollX, float scrollY); /*MANUAL
        ImVec2 scroll = ImVec2(scrollX, scrollY);
        ImGui::SetNextWindowScroll(scroll);
    */

    public static void setScrollX(final ImGuiWindow window, final float scrollX) {
        nSetScrollX(window.ptr, scrollX);
    }

    private static native void nSetScrollX(long window, float scrollX); /*
        ImGui::SetScrollX(reinterpret_cast<ImGuiWindow*>(window), scrollX);
    */

    public static void setScrollY(final ImGuiWindow window, final float scrollY) {
        nSetScrollY(window.ptr, scrollY);
    }

    private static native void nSetScrollY(long window, float scrollY); /*
        ImGui::SetScrollY(reinterpret_cast<ImGuiWindow*>(window), scrollY);
    */

    public static void setScrollFromPosX(final ImGuiWindow window, final float localX, final float centerXRatio) {
        nSetScrollFromPosX(window.ptr, localX, centerXRatio);
    }

    private static native void nSetScrollFromPosX(long window, float localX, float centerXRatio); /*
        ImGui::SetScrollFromPosX(reinterpret_cast<ImGuiWindow*>(window), localX, centerXRatio);
    */

    public static void setScrollFromPosY(final ImGuiWindow window, final float localY, final float centerYRatio) {
        nSetScrollFromPosY(window.ptr, localY, centerYRatio);
    }

    private static native void nSetScrollFromPosY(long window, float localY, float centerYRatio); /*
        ImGui::SetScrollFromPosY(reinterpret_cast<ImGuiWindow*>(window), localY, centerYRatio);
    */

    // Early work-in-progress API (ScrollToItem() will become public)

    public static void scrollToItem() {
        nScrollToItem();
    }

    public static void scrollToItem(final int flags) {
        nScrollToItem(flags);
    }

    private static native void nScrollToItem(); /*
        ImGui::ScrollToItem();
    */

    private static native void nScrollToItem(int flags); /*
        ImGui::ScrollToItem(flags);
    */

    public static void scrollToRect(final ImGuiWindow window, final ImRect rect) {
        nScrollToRect(window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y);
    }

    public static void scrollToRect(final ImGuiWindow window, final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        nScrollToRect(window.ptr, rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    public static void scrollToRect(final ImGuiWindow window, final ImRect rect, final int flags) {
        nScrollToRect(window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y, flags);
    }

    public static void scrollToRect(final ImGuiWindow window, final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY, final int flags) {
        nScrollToRect(window.ptr, rectMinX, rectMinY, rectMaxX, rectMaxY, flags);
    }

    private static native void nScrollToRect(long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        ImGui::ScrollToRect(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY));
    */

    private static native void nScrollToRect(long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, int flags); /*
        ImGui::ScrollToRect(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY), flags);
    */

    public static ImVec2 scrollToRectEx(final ImGuiWindow window, final ImRect rect) {
        final ImVec2 dst = new ImVec2();
        nScrollToRectEx(dst, window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y);
        return dst;
    }

    public static ImVec2 scrollToRectEx(final ImGuiWindow window, final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        final ImVec2 dst = new ImVec2();
        nScrollToRectEx(dst, window.ptr, rectMinX, rectMinY, rectMaxX, rectMaxY);
        return dst;
    }

    public static float scrollToRectExX(final ImGuiWindow window, final ImRect rect) {
        return nScrollToRectExX(window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y);
    }

    public static float scrollToRectExY(final ImGuiWindow window, final ImRect rect) {
        return nScrollToRectExY(window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y);
    }

    public static void scrollToRectEx(final ImVec2 dst, final ImGuiWindow window, final ImRect rect) {
        nScrollToRectEx(dst, window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y);
    }

    public static void scrollToRectEx(final ImVec2 dst, final ImGuiWindow window, final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY) {
        nScrollToRectEx(dst, window.ptr, rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    public static ImVec2 scrollToRectEx(final ImGuiWindow window, final ImRect rect, final int flags) {
        final ImVec2 dst = new ImVec2();
        nScrollToRectEx(dst, window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y, flags);
        return dst;
    }

    public static ImVec2 scrollToRectEx(final ImGuiWindow window, final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY, final int flags) {
        final ImVec2 dst = new ImVec2();
        nScrollToRectEx(dst, window.ptr, rectMinX, rectMinY, rectMaxX, rectMaxY, flags);
        return dst;
    }

    public static float scrollToRectExX(final ImGuiWindow window, final ImRect rect, final int flags) {
        return nScrollToRectExX(window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y, flags);
    }

    public static float scrollToRectExY(final ImGuiWindow window, final ImRect rect, final int flags) {
        return nScrollToRectExY(window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y, flags);
    }

    public static void scrollToRectEx(final ImVec2 dst, final ImGuiWindow window, final ImRect rect, final int flags) {
        nScrollToRectEx(dst, window.ptr, rect.min.x, rect.min.y, rect.max.x, rect.max.y, flags);
    }

    public static void scrollToRectEx(final ImVec2 dst, final ImGuiWindow window, final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY, final int flags) {
        nScrollToRectEx(dst, window.ptr, rectMinX, rectMinY, rectMaxX, rectMaxY, flags);
    }

    private static native void nScrollToRectEx(ImVec2 dst, long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        Jni::ImVec2Cpy(env, ImGui::ScrollToRectEx(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY)), dst);
    */

    private static native float nScrollToRectExX(long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        return ImGui::ScrollToRectEx(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY)).x;
    */

    private static native float nScrollToRectExY(long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY); /*
        return ImGui::ScrollToRectEx(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY)).y;
    */

    private static native void nScrollToRectEx(ImVec2 dst, long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, int flags); /*
        Jni::ImVec2Cpy(env, ImGui::ScrollToRectEx(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY), flags), dst);
    */

    private static native float nScrollToRectExX(long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, int flags); /*
        return ImGui::ScrollToRectEx(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY), flags).x;
    */

    private static native float nScrollToRectExY(long window, float rectMinX, float rectMinY, float rectMaxX, float rectMaxY, int flags); /*
        return ImGui::ScrollToRectEx(reinterpret_cast<ImGuiWindow*>(window), ImRect(rectMinX, rectMinY, rectMaxX, rectMaxY), flags).y;
    */

    // Basic Accessors

    public static int getItemStatusFlags() {
        return nGetItemStatusFlags();
    }

    private static native int nGetItemStatusFlags(); /*
        return ImGui::GetItemStatusFlags();
    */

    public static int getItemFlags() {
        return nGetItemFlags();
    }

    private static native int nGetItemFlags(); /*
        return ImGui::GetItemFlags();
    */

    public static int getActiveID() {
        return nGetActiveID();
    }

    private static native int nGetActiveID(); /*
        return ImGui::GetActiveID();
    */

    public static int getFocusID() {
        return nGetFocusID();
    }

    private static native int nGetFocusID(); /*
        return ImGui::GetFocusID();
    */

    public static void setActiveID(final int id, final ImGuiWindow window) {
        nSetActiveID(id, window.ptr);
    }

    private static native void nSetActiveID(int id, long window); /*
        ImGui::SetActiveID(id, reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void setFocusID(final int id, final ImGuiWindow window) {
        nSetFocusID(id, window.ptr);
    }

    private static native void nSetFocusID(int id, long window); /*
        ImGui::SetFocusID(id, reinterpret_cast<ImGuiWindow*>(window));
    */

    public static void clearActiveID() {
        nClearActiveID();
    }

    private static native void nClearActiveID(); /*
        ImGui::ClearActiveID();
    */

    public static int getHoveredID() {
        return nGetHoveredID();
    }

    private static native int nGetHoveredID(); /*
        return ImGui::GetHoveredID();
    */

    public static void setHoveredID(final int id) {
        nSetHoveredID(id);
    }

    private static native void nSetHoveredID(int id); /*
        ImGui::SetHoveredID(id);
    */

    public static void keepAliveID(final int id) {
        nKeepAliveID(id);
    }

    private static native void nKeepAliveID(int id); /*
        ImGui::KeepAliveID(id);
    */

    public static void markItemEdited(final int id) {
        nMarkItemEdited(id);
    }

    private static native void nMarkItemEdited(int id); /*
        ImGui::MarkItemEdited(id);
    */

    public static void pushOverrideID(final int id) {
        nPushOverrideID(id);
    }

    private static native void nPushOverrideID(int id); /*
        ImGui::PushOverrideID(id);
    */

    public static int getIDWithSeed(final String strIdBegin, final String strIdEnd, final int seed) {
        return nGetIDWithSeed(strIdBegin, strIdEnd, seed);
    }

    private static native int nGetIDWithSeed(String obj_strIdBegin, String obj_strIdEnd, int seed); /*MANUAL
        auto strIdBegin = obj_strIdBegin == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strIdBegin, JNI_FALSE);
        auto strIdEnd = obj_strIdEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strIdEnd, JNI_FALSE);
        auto _result = ImGui::GetIDWithSeed(strIdBegin, strIdEnd, seed);
        if (strIdBegin != NULL) env->ReleaseStringUTFChars(obj_strIdBegin, strIdBegin);
        if (strIdEnd != NULL) env->ReleaseStringUTFChars(obj_strIdEnd, strIdEnd);
        return _result;
    */

    // Basic Helpers for widget code

    public static void itemSize(final ImVec2 size) {
        nItemSize(size.x, size.y);
    }

    public static void itemSize(final float sizeX, final float sizeY) {
        nItemSize(sizeX, sizeY);
    }

    public static void itemSize(final ImVec2 size, final float textBaselineY) {
        nItemSize(size.x, size.y, textBaselineY);
    }

    public static void itemSize(final float sizeX, final float sizeY, final float textBaselineY) {
        nItemSize(sizeX, sizeY, textBaselineY);
    }

    private static native void nItemSize(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::ItemSize(size);
    */

    private static native void nItemSize(float sizeX, float sizeY, float textBaselineY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::ItemSize(size, textBaselineY);
    */

    public static void itemSize(final ImRect bb) {
        nItemSize(bb.min.x, bb.min.y, bb.max.x, bb.max.y);
    }

    public static void itemSize(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY) {
        nItemSize(bbMinX, bbMinY, bbMaxX, bbMaxY);
    }

    public static void itemSize(final ImRect bb, final float textBaselineY) {
        nItemSize(bb.min.x, bb.min.y, bb.max.x, bb.max.y, textBaselineY);
    }

    public static void itemSize(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final float textBaselineY) {
        nItemSize(bbMinX, bbMinY, bbMaxX, bbMaxY, textBaselineY);
    }

    private static native void nItemSize(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY); /*
        ImGui::ItemSize(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY));
    */

    private static native void nItemSize(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, float textBaselineY); /*
        ImGui::ItemSize(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), textBaselineY);
    */

    // TODO: ItemAdd

    public static boolean itemHoverable(final ImRect bb, final int id, final int itemFlags) {
        return nItemHoverable(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, itemFlags);
    }

    public static boolean itemHoverable(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final int itemFlags) {
        return nItemHoverable(bbMinX, bbMinY, bbMaxX, bbMaxY, id, itemFlags);
    }

    private static native boolean nItemHoverable(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int itemFlags); /*
        return ImGui::ItemHoverable(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id, static_cast<ImGuiItemFlags>(itemFlags));
    */

    public static boolean isWindowContentHoverable(final ImGuiWindow window, final int flags) {
        return nIsWindowContentHoverable(window.ptr, flags);
    }

    private static native boolean nIsWindowContentHoverable(long window, int flags); /*
        return ImGui::IsWindowContentHoverable(reinterpret_cast<ImGuiWindow*>(window), static_cast<ImGuiHoveredFlags>(flags));
    */

    public static boolean isClippedEx(final ImRect bb, final int id) {
        return nIsClippedEx(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id);
    }

    public static boolean isClippedEx(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id) {
        return nIsClippedEx(bbMinX, bbMinY, bbMaxX, bbMaxY, id);
    }

    private static native boolean nIsClippedEx(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id); /*
        return ImGui::IsClippedEx(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id);
    */

    public static void setLastItemData(final int itemId, final int inFlags, final int statusFlags, final ImRect itemRect) {
        nSetLastItemData(itemId, inFlags, statusFlags, itemRect.min.x, itemRect.min.y, itemRect.max.x, itemRect.max.y);
    }

    public static void setLastItemData(final int itemId, final int inFlags, final int statusFlags, final float itemRectMinX, final float itemRectMinY, final float itemRectMaxX, final float itemRectMaxY) {
        nSetLastItemData(itemId, inFlags, statusFlags, itemRectMinX, itemRectMinY, itemRectMaxX, itemRectMaxY);
    }

    private static native void nSetLastItemData(int itemId, int inFlags, int statusFlags, float itemRectMinX, float itemRectMinY, float itemRectMaxX, float itemRectMaxY); /*
        ImGui::SetLastItemData(itemId, inFlags, statusFlags, ImRect(itemRectMinX, itemRectMinY, itemRectMaxX, itemRectMaxY));
    */

    public static ImVec2 calcItemSize(final ImVec2 size, final float defaultW, final float defaultH) {
        final ImVec2 dst = new ImVec2();
        nCalcItemSize(dst, size.x, size.y, defaultW, defaultH);
        return dst;
    }

    public static ImVec2 calcItemSize(final float sizeX, final float sizeY, final float defaultW, final float defaultH) {
        final ImVec2 dst = new ImVec2();
        nCalcItemSize(dst, sizeX, sizeY, defaultW, defaultH);
        return dst;
    }

    public static float calcItemSizeX(final ImVec2 size, final float defaultW, final float defaultH) {
        return nCalcItemSizeX(size.x, size.y, defaultW, defaultH);
    }

    public static float calcItemSizeY(final ImVec2 size, final float defaultW, final float defaultH) {
        return nCalcItemSizeY(size.x, size.y, defaultW, defaultH);
    }

    public static void calcItemSize(final ImVec2 dst, final ImVec2 size, final float defaultW, final float defaultH) {
        nCalcItemSize(dst, size.x, size.y, defaultW, defaultH);
    }

    public static void calcItemSize(final ImVec2 dst, final float sizeX, final float sizeY, final float defaultW, final float defaultH) {
        nCalcItemSize(dst, sizeX, sizeY, defaultW, defaultH);
    }

    private static native void nCalcItemSize(ImVec2 dst, float sizeX, float sizeY, float defaultW, float defaultH); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        Jni::ImVec2Cpy(env, ImGui::CalcItemSize(size, defaultW, defaultH), dst);
    */

    private static native float nCalcItemSizeX(float sizeX, float sizeY, float defaultW, float defaultH); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::CalcItemSize(size, defaultW, defaultH).x;
        return _result;
    */

    private static native float nCalcItemSizeY(float sizeX, float sizeY, float defaultW, float defaultH); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::CalcItemSize(size, defaultW, defaultH).y;
        return _result;
    */

    public static float calcWrapWidthForPos(final ImVec2 pos, final float wrapPosX) {
        return nCalcWrapWidthForPos(pos.x, pos.y, wrapPosX);
    }

    public static float calcWrapWidthForPos(final float posX, final float posY, final float wrapPosX) {
        return nCalcWrapWidthForPos(posX, posY, wrapPosX);
    }

    private static native float nCalcWrapWidthForPos(float posX, float posY, float wrapPosX); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ImGui::CalcWrapWidthForPos(pos, wrapPosX);
        return _result;
    */

    public static void pushMultiItemsWidths(final int components, final float widthFull) {
        nPushMultiItemsWidths(components, widthFull);
    }

    private static native void nPushMultiItemsWidths(int components, float widthFull); /*
        ImGui::PushMultiItemsWidths(components, widthFull);
    */

    public static boolean isItemToggledSelection() {
        return nIsItemToggledSelection();
    }

    private static native boolean nIsItemToggledSelection(); /*
        return ImGui::IsItemToggledSelection();
    */

    public static ImVec2 getContentRegionMaxAbs() {
        final ImVec2 dst = new ImVec2();
        nGetContentRegionMaxAbs(dst);
        return dst;
    }

    public static float getContentRegionMaxAbsX() {
        return nGetContentRegionMaxAbsX();
    }

    public static float getContentRegionMaxAbsY() {
        return nGetContentRegionMaxAbsY();
    }

    public static void getContentRegionMaxAbs(final ImVec2 dst) {
        nGetContentRegionMaxAbs(dst);
    }

    private static native void nGetContentRegionMaxAbs(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImGui::GetContentRegionMaxAbs(), dst);
    */

    private static native float nGetContentRegionMaxAbsX(); /*
        return ImGui::GetContentRegionMaxAbs().x;
    */

    private static native float nGetContentRegionMaxAbsY(); /*
        return ImGui::GetContentRegionMaxAbs().y;
    */

    // TODO: ShrinkWidths

    // Parameter stacks

    public static void pushItemFlag(final int option, final boolean enabled) {
        nPushItemFlag(option, enabled);
    }

    private static native void nPushItemFlag(int option, boolean enabled); /*
        ImGui::PushItemFlag(option, enabled);
    */

    public static void popItemFlag() {
        nPopItemFlag();
    }

    private static native void nPopItemFlag(); /*
        ImGui::PopItemFlag();
    */

    // Docking - Builder function needs to be generally called before the node is used/submitted.
    // - The DockBuilderXXX functions are designed to _eventually_ become a public API, but it is too early to expose it and guarantee stability.
    // - Do not hold on ImGuiDockNode* pointers! They may be invalidated by any split/merge/remove operation and every frame.
    // - To create a DockSpace() node, make sure to set the ImGuiDockNodeFlags_DockSpace flag when calling DockBuilderAddNode().
    //   You can create dockspace nodes (attached to a window) _or_ floating nodes (carry its own window) with this API.
    // - DockBuilderSplitNode() create 2 child nodes within 1 node. The initial node becomes a parent node.
    // - If you intend to split the node immediately after creation using DockBuilderSplitNode(), make sure
    //   to call DockBuilderSetNodeSize() beforehand. If you don't, the resulting split sizes may not be reliable.
    // - Call DockBuilderFinish() after you are done.

    public static void dockBuilderDockWindow(final String windowName, final int nodeId) {
        nDockBuilderDockWindow(windowName, nodeId);
    }

    private static native void nDockBuilderDockWindow(String windowName, int nodeId); /*MANUAL
        auto windowName = obj_windowName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_windowName, JNI_FALSE);
        ImGui::DockBuilderDockWindow(windowName, nodeId);
        if (windowName != NULL) env->ReleaseStringUTFChars(obj_windowName, windowName);
    */

    public static ImGuiDockNode dockBuilderGetNode(final int nodeId) {
        return new ImGuiDockNode(nDockBuilderGetNode(nodeId));
    }

    private static native long nDockBuilderGetNode(int nodeId); /*
        return (uintptr_t)ImGui::DockBuilderGetNode(nodeId);
    */

    public static ImGuiDockNode dockBuilderGetCentralNode(final int nodeId) {
        return new ImGuiDockNode(nDockBuilderGetCentralNode(nodeId));
    }

    private static native long nDockBuilderGetCentralNode(int nodeId); /*
        return (uintptr_t)ImGui::DockBuilderGetCentralNode(nodeId);
    */

    public static int dockBuilderAddNode() {
        return nDockBuilderAddNode();
    }

    public static int dockBuilderAddNode(final int nodeId) {
        return nDockBuilderAddNode(nodeId);
    }

    public static int dockBuilderAddNode(final int nodeId, final int flags) {
        return nDockBuilderAddNode(nodeId, flags);
    }

    private static native int nDockBuilderAddNode(); /*
        return ImGui::DockBuilderAddNode();
    */

    private static native int nDockBuilderAddNode(int nodeId); /*
        return ImGui::DockBuilderAddNode(nodeId);
    */

    private static native int nDockBuilderAddNode(int nodeId, int flags); /*
        return ImGui::DockBuilderAddNode(nodeId, flags);
    */

    /**
     * Remove node and all its child, undock all windows.
     */
    public static void dockBuilderRemoveNode(final int nodeId) {
        nDockBuilderRemoveNode(nodeId);
    }

    private static native void nDockBuilderRemoveNode(int nodeId); /*
        ImGui::DockBuilderRemoveNode(nodeId);
    */

    public static void dockBuilderRemoveNodeDockedWindows(final int nodeId) {
        nDockBuilderRemoveNodeDockedWindows(nodeId);
    }

    public static void dockBuilderRemoveNodeDockedWindows(final int nodeId, final boolean clearSettingsRefs) {
        nDockBuilderRemoveNodeDockedWindows(nodeId, clearSettingsRefs);
    }

    private static native void nDockBuilderRemoveNodeDockedWindows(int nodeId); /*
        ImGui::DockBuilderRemoveNodeDockedWindows(nodeId);
    */

    private static native void nDockBuilderRemoveNodeDockedWindows(int nodeId, boolean clearSettingsRefs); /*
        ImGui::DockBuilderRemoveNodeDockedWindows(nodeId, clearSettingsRefs);
    */

    /**
     * Remove all split/hierarchy. All remaining docked windows will be re-docked to the remaining root node (node_id).
     */
    public static void dockBuilderRemoveNodeChildNodes(final int nodeId) {
        nDockBuilderRemoveNodeChildNodes(nodeId);
    }

    private static native void nDockBuilderRemoveNodeChildNodes(int nodeId); /*
        ImGui::DockBuilderRemoveNodeChildNodes(nodeId);
    */

    public static void dockBuilderSetNodePos(final int nodeId, final ImVec2 pos) {
        nDockBuilderSetNodePos(nodeId, pos.x, pos.y);
    }

    public static void dockBuilderSetNodePos(final int nodeId, final float posX, final float posY) {
        nDockBuilderSetNodePos(nodeId, posX, posY);
    }

    private static native void nDockBuilderSetNodePos(int nodeId, float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImGui::DockBuilderSetNodePos(nodeId, pos);
    */

    public static void dockBuilderSetNodeSize(final int nodeId, final ImVec2 size) {
        nDockBuilderSetNodeSize(nodeId, size.x, size.y);
    }

    public static void dockBuilderSetNodeSize(final int nodeId, final float sizeX, final float sizeY) {
        nDockBuilderSetNodeSize(nodeId, sizeX, sizeY);
    }

    private static native void nDockBuilderSetNodeSize(int nodeId, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImGui::DockBuilderSetNodeSize(nodeId, size);
    */

    /**
     * Create 2 child nodes in this parent node.
     */
    public static int dockBuilderSplitNode(final int nodeId, final int splitDir, final float sizeRatioForNodeAtDir, final ImInt outIdAtDir, final ImInt outIdAtOppositeDir) {
        return nDockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, outIdAtDir != null ? outIdAtDir.getData() : null, outIdAtOppositeDir != null ? outIdAtOppositeDir.getData() : null);
    }

    private static native int nDockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir, int[] obj_outIdAtDir, int[] obj_outIdAtOppositeDir); /*MANUAL
        auto outIdAtDir = obj_outIdAtDir == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_outIdAtDir, JNI_FALSE);
        auto outIdAtOppositeDir = obj_outIdAtOppositeDir == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_outIdAtOppositeDir, JNI_FALSE);
        auto _result = ImGui::DockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, reinterpret_cast<ImGuiID*>((outIdAtDir != NULL ? &outIdAtDir[0] : NULL)), reinterpret_cast<ImGuiID*>((outIdAtOppositeDir != NULL ? &outIdAtOppositeDir[0] : NULL)));
        if (outIdAtDir != NULL) env->ReleasePrimitiveArrayCritical(obj_outIdAtDir, outIdAtDir, JNI_FALSE);
        if (outIdAtOppositeDir != NULL) env->ReleasePrimitiveArrayCritical(obj_outIdAtOppositeDir, outIdAtOppositeDir, JNI_FALSE);
        return _result;
    */

    // TODO DockBuilderCopyDockSpace, DockBuilderCopyNode

    public static void dockBuilderCopyWindowSettings(final String srcName, final String dstName) {
        nDockBuilderCopyWindowSettings(srcName, dstName);
    }

    private static native void nDockBuilderCopyWindowSettings(String srcName, String dstName); /*MANUAL
        auto srcName = obj_srcName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_srcName, JNI_FALSE);
        auto dstName = obj_dstName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_dstName, JNI_FALSE);
        ImGui::DockBuilderCopyWindowSettings(srcName, dstName);
        if (srcName != NULL) env->ReleaseStringUTFChars(obj_srcName, srcName);
        if (dstName != NULL) env->ReleaseStringUTFChars(obj_dstName, dstName);
    */

    public static void dockBuilderFinish(final int nodeId) {
        nDockBuilderFinish(nodeId);
    }

    private static native void nDockBuilderFinish(int nodeId); /*
        ImGui::DockBuilderFinish(nodeId);
    */

    // Tables: Candidates for public API

    public static void tableOpenContextMenu() {
        nTableOpenContextMenu();
    }

    public static void tableOpenContextMenu(final int columnN) {
        nTableOpenContextMenu(columnN);
    }

    private static native void nTableOpenContextMenu(); /*
        ImGui::TableOpenContextMenu();
    */

    private static native void nTableOpenContextMenu(int columnN); /*
        ImGui::TableOpenContextMenu(columnN);
    */

    public static void tableSetColumnWidth(final int columnN, final float width) {
        nTableSetColumnWidth(columnN, width);
    }

    private static native void nTableSetColumnWidth(int columnN, float width); /*
        ImGui::TableSetColumnWidth(columnN, width);
    */

    public static void tableSetColumnSortDirection(final int columnN, final int sortDirection, final boolean appendToSortSpecs) {
        nTableSetColumnSortDirection(columnN, sortDirection, appendToSortSpecs);
    }

    private static native void nTableSetColumnSortDirection(int columnN, int sortDirection, boolean appendToSortSpecs); /*
        ImGui::TableSetColumnSortDirection(columnN, static_cast<ImGuiSortDirection>(sortDirection), appendToSortSpecs);
    */

    /**
     * May use {@code (TableGetColumnFlags() & ImGuiTableColumnFlags_IsHovered)} instead. Return hovered column.
     * Return -1 when table is not hovered. return columns_count if the unused space at the right of visible columns is hovered.
     */
    public static int tableGetHoveredColumn() {
        return nTableGetHoveredColumn();
    }

    private static native int nTableGetHoveredColumn(); /*
        return ImGui::TableGetHoveredColumn();
    */

    /**
     * Retrieve *PREVIOUS FRAME* hovered row. This difference with TableGetHoveredColumn() is the reason why this is not public yet.
     */
    public static int tableGetHoveredRow() {
        return nTableGetHoveredRow();
    }

    private static native int nTableGetHoveredRow(); /*
        return ImGui::TableGetHoveredRow();
    */

    public static float tableGetHeaderRowHeight() {
        return nTableGetHeaderRowHeight();
    }

    private static native float nTableGetHeaderRowHeight(); /*
        return ImGui::TableGetHeaderRowHeight();
    */

    public static void tablePushBackgroundChannel() {
        nTablePushBackgroundChannel();
    }

    private static native void nTablePushBackgroundChannel(); /*
        ImGui::TablePushBackgroundChannel();
    */

    public static void tablePopBackgroundChannel() {
        nTablePopBackgroundChannel();
    }

    private static native void nTablePopBackgroundChannel(); /*
        ImGui::TablePopBackgroundChannel();
    */

    // Widgets

    public static void textEx(final String beginText) {
        nTextEx(beginText);
    }

    public static void textEx(final String beginText, final String endText) {
        nTextEx(beginText, endText);
    }

    public static void textEx(final String beginText, final String endText, final int flags) {
        nTextEx(beginText, endText, flags);
    }

    private static native void nTextEx(String beginText); /*MANUAL
        auto beginText = obj_beginText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_beginText, JNI_FALSE);
        ImGui::TextEx(beginText);
        if (beginText != NULL) env->ReleaseStringUTFChars(obj_beginText, beginText);
    */

    private static native void nTextEx(String beginText, String endText); /*MANUAL
        auto beginText = obj_beginText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_beginText, JNI_FALSE);
        auto endText = obj_endText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_endText, JNI_FALSE);
        ImGui::TextEx(beginText, endText);
        if (beginText != NULL) env->ReleaseStringUTFChars(obj_beginText, beginText);
        if (endText != NULL) env->ReleaseStringUTFChars(obj_endText, endText);
    */

    private static native void nTextEx(String beginText, String endText, int flags); /*MANUAL
        auto beginText = obj_beginText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_beginText, JNI_FALSE);
        auto endText = obj_endText == NULL ? NULL : (char*)env->GetStringUTFChars(obj_endText, JNI_FALSE);
        ImGui::TextEx(beginText, endText, static_cast<ImGuiTextFlags>(flags));
        if (beginText != NULL) env->ReleaseStringUTFChars(obj_beginText, beginText);
        if (endText != NULL) env->ReleaseStringUTFChars(obj_endText, endText);
    */

    public static boolean buttonEx(final String label) {
        return nButtonEx(label);
    }

    public static boolean buttonEx(final String label, final ImVec2 size) {
        return nButtonEx(label, size.x, size.y);
    }

    public static boolean buttonEx(final String label, final float sizeX, final float sizeY) {
        return nButtonEx(label, sizeX, sizeY);
    }

    public static boolean buttonEx(final String label, final ImVec2 size, final int flags) {
        return nButtonEx(label, size.x, size.y, flags);
    }

    public static boolean buttonEx(final String label, final float sizeX, final float sizeY, final int flags) {
        return nButtonEx(label, sizeX, sizeY, flags);
    }

    public static boolean buttonEx(final String label, final int flags) {
        return nButtonEx(label, flags);
    }

    private static native boolean nButtonEx(String obj_label); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::ButtonEx(label);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nButtonEx(String obj_label, float sizeX, float sizeY); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ButtonEx(label, size);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nButtonEx(String obj_label, float sizeX, float sizeY, int flags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ButtonEx(label, size, static_cast<ImGuiButtonFlags>(flags));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    private static native boolean nButtonEx(String obj_label, int flags); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto _result = ImGui::ButtonEx(label, ImVec2(0,0), static_cast<ImGuiButtonFlags>(flags));
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        return _result;
    */

    public static boolean arrowButtonEx(final String strId, final int dir, final ImVec2 size) {
        return nArrowButtonEx(strId, dir, size.x, size.y);
    }

    public static boolean arrowButtonEx(final String strId, final int dir, final float sizeX, final float sizeY) {
        return nArrowButtonEx(strId, dir, sizeX, sizeY);
    }

    public static boolean arrowButtonEx(final String strId, final int dir, final ImVec2 size, final int flags) {
        return nArrowButtonEx(strId, dir, size.x, size.y, flags);
    }

    public static boolean arrowButtonEx(final String strId, final int dir, final float sizeX, final float sizeY, final int flags) {
        return nArrowButtonEx(strId, dir, sizeX, sizeY, flags);
    }

    private static native boolean nArrowButtonEx(String obj_strId, int dir, float sizeX, float sizeY); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ArrowButtonEx(strId, static_cast<ImGuiDir>(dir), size);
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    private static native boolean nArrowButtonEx(String obj_strId, int dir, float sizeX, float sizeY, int flags); /*MANUAL
        auto strId = obj_strId == NULL ? NULL : (char*)env->GetStringUTFChars(obj_strId, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        auto _result = ImGui::ArrowButtonEx(strId, static_cast<ImGuiDir>(dir), size, static_cast<ImGuiButtonFlags>(flags));
        if (strId != NULL) env->ReleaseStringUTFChars(obj_strId, strId);
        return _result;
    */

    public static boolean imageButtonEx(final int id, final long textureId, final ImVec2 size, final ImVec2 uv0, final ImVec2 uv1, final ImVec4 bgCol, final ImVec4 tintCol, final int flags) {
        return nImageButtonEx(id, textureId, size.x, size.y, uv0.x, uv0.y, uv1.x, uv1.y, bgCol.x, bgCol.y, bgCol.z, bgCol.w, tintCol.x, tintCol.y, tintCol.z, tintCol.w, flags);
    }

    public static boolean imageButtonEx(final int id, final long textureId, final float sizeX, final float sizeY, final float uv0X, final float uv0Y, final float uv1X, final float uv1Y, final float bgColX, final float bgColY, final float bgColZ, final float bgColW, final float tintColX, final float tintColY, final float tintColZ, final float tintColW, final int flags) {
        return nImageButtonEx(id, textureId, sizeX, sizeY, uv0X, uv0Y, uv1X, uv1Y, bgColX, bgColY, bgColZ, bgColW, tintColX, tintColY, tintColZ, tintColW, flags);
    }

    private static native boolean nImageButtonEx(int id, long textureId, float sizeX, float sizeY, float uv0X, float uv0Y, float uv1X, float uv1Y, float bgColX, float bgColY, float bgColZ, float bgColW, float tintColX, float tintColY, float tintColZ, float tintColW, int flags); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ImVec2 uv0 = ImVec2(uv0X, uv0Y);
        ImVec2 uv1 = ImVec2(uv1X, uv1Y);
        ImVec4 bgCol = ImVec4(bgColX, bgColY, bgColZ, bgColW);
        ImVec4 tintCol = ImVec4(tintColX, tintColY, tintColZ, tintColW);
        auto _result = ImGui::ImageButtonEx((ImGuiID)id, (ImTextureID)(uintptr_t)textureId, size, uv0, uv1, bgCol, tintCol, static_cast<ImGuiButtonFlags>(flags));
        return _result;
    */

    public static void separatorEx(final int flags) {
        nSeparatorEx(flags);
    }

    public static void separatorEx(final int flags, final float thickness) {
        nSeparatorEx(flags, thickness);
    }

    private static native void nSeparatorEx(int flags); /*
        ImGui::SeparatorEx(static_cast<ImGuiSeparatorFlags>(flags));
    */

    private static native void nSeparatorEx(int flags, float thickness); /*
        ImGui::SeparatorEx(static_cast<ImGuiSeparatorFlags>(flags), thickness);
    */

    public static void separatorTextEx(final int id, final String label, final String labelEnd, final float extraWidth) {
        nSeparatorTextEx(id, label, labelEnd, extraWidth);
    }

    private static native void nSeparatorTextEx(int id, String label, String labelEnd, float extraWidth); /*MANUAL
        auto label = obj_label == NULL ? NULL : (char*)env->GetStringUTFChars(obj_label, JNI_FALSE);
        auto labelEnd = obj_labelEnd == NULL ? NULL : (char*)env->GetStringUTFChars(obj_labelEnd, JNI_FALSE);
        ImGui::SeparatorTextEx((ImGuiID)id, label, labelEnd, extraWidth);
        if (label != NULL) env->ReleaseStringUTFChars(obj_label, label);
        if (labelEnd != NULL) env->ReleaseStringUTFChars(obj_labelEnd, labelEnd);
    */

    // Widgets: Window Decorations

    public static boolean closeButton(final int id, final ImVec2 pos) {
        return nCloseButton(id, pos.x, pos.y);
    }

    public static boolean closeButton(final int id, final float posX, final float posY) {
        return nCloseButton(id, posX, posY);
    }

    private static native boolean nCloseButton(int id, float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ImGui::CloseButton((ImGuiID)id, pos);
        return _result;
    */

    public static boolean collapseButton(final int id, final ImVec2 pos, final ImGuiDockNode dockNode) {
        return nCollapseButton(id, pos.x, pos.y, dockNode.ptr);
    }

    public static boolean collapseButton(final int id, final float posX, final float posY, final ImGuiDockNode dockNode) {
        return nCollapseButton(id, posX, posY, dockNode.ptr);
    }

    private static native boolean nCollapseButton(int id, float posX, float posY, long dockNode); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ImGui::CollapseButton((ImGuiID)id, pos, reinterpret_cast<ImGuiDockNode*>(dockNode));
        return _result;
    */

    public static void scrollbar(final int axis) {
        nScrollbar(axis);
    }

    private static native void nScrollbar(int axis); /*
        ImGui::Scrollbar(static_cast<ImGuiAxis>(axis));
    */

    public static ImRect getWindowScrollbarRect(final ImGuiWindow imGuiWindow, final int axis) {
        final ImRect dst = new ImRect();
        nGetWindowScrollbarRect(dst, imGuiWindow.ptr, axis);
        return dst;
    }

    public static void getWindowScrollbarRect(final ImRect dst, final ImGuiWindow imGuiWindow, final int axis) {
        nGetWindowScrollbarRect(dst, imGuiWindow.ptr, axis);
    }

    private static native void nGetWindowScrollbarRect(ImRect dst, long imGuiWindow, int axis); /*
        Jni::ImRectCpy(env, ImGui::GetWindowScrollbarRect(reinterpret_cast<ImGuiWindow*>(imGuiWindow), static_cast<ImGuiAxis>(axis)), dst);
    */

    public static int getWindowScrollbarID(final ImGuiWindow window, final int axis) {
        return nGetWindowScrollbarID(window.ptr, axis);
    }

    private static native int nGetWindowScrollbarID(long window, int axis); /*
        return ImGui::GetWindowScrollbarID(reinterpret_cast<ImGuiWindow*>(window), static_cast<ImGuiAxis>(axis));
    */

    public static int getWindowResizeCornerID(final ImGuiWindow window, final int n) {
        return nGetWindowResizeCornerID(window.ptr, n);
    }

    private static native int nGetWindowResizeCornerID(long window, int n); /*
        return ImGui::GetWindowResizeCornerID(reinterpret_cast<ImGuiWindow*>(window), n);
    */

    public static int getWindowResizeBorderID(final ImGuiWindow window, final int dir) {
        return nGetWindowResizeBorderID(window.ptr, dir);
    }

    private static native int nGetWindowResizeBorderID(long window, int dir); /*
        return ImGui::GetWindowResizeBorderID(reinterpret_cast<ImGuiWindow*>(window), static_cast<ImGuiDir>(dir));
    */

    // Widgets low-level behaviors

    public static boolean buttonBehavior(final ImRect bb, final int id, final ImBoolean outHovered, final ImBoolean outHeld) {
        return nButtonBehavior(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, outHovered != null ? outHovered.getData() : null, outHeld != null ? outHeld.getData() : null);
    }

    public static boolean buttonBehavior(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final ImBoolean outHovered, final ImBoolean outHeld) {
        return nButtonBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, outHovered != null ? outHovered.getData() : null, outHeld != null ? outHeld.getData() : null);
    }

    public static boolean buttonBehavior(final ImRect bb, final int id, final ImBoolean outHovered, final ImBoolean outHeld, final int imGuiButtonFlags) {
        return nButtonBehavior(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, outHovered != null ? outHovered.getData() : null, outHeld != null ? outHeld.getData() : null, imGuiButtonFlags);
    }

    public static boolean buttonBehavior(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final ImBoolean outHovered, final ImBoolean outHeld, final int imGuiButtonFlags) {
        return nButtonBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, outHovered != null ? outHovered.getData() : null, outHeld != null ? outHeld.getData() : null, imGuiButtonFlags);
    }

    private static native boolean nButtonBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, boolean[] obj_outHovered, boolean[] obj_outHeld); /*MANUAL
        auto outHovered = obj_outHovered == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_outHovered, JNI_FALSE);
        auto outHeld = obj_outHeld == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_outHeld, JNI_FALSE);
        auto _result = ImGui::ButtonBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), (ImGuiID)id, (outHovered != NULL ? &outHovered[0] : NULL), (outHeld != NULL ? &outHeld[0] : NULL));
        if (outHovered != NULL) env->ReleasePrimitiveArrayCritical(obj_outHovered, outHovered, JNI_FALSE);
        if (outHeld != NULL) env->ReleasePrimitiveArrayCritical(obj_outHeld, outHeld, JNI_FALSE);
        return _result;
    */

    private static native boolean nButtonBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, boolean[] obj_outHovered, boolean[] obj_outHeld, int imGuiButtonFlags); /*MANUAL
        auto outHovered = obj_outHovered == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_outHovered, JNI_FALSE);
        auto outHeld = obj_outHeld == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_outHeld, JNI_FALSE);
        auto _result = ImGui::ButtonBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), (ImGuiID)id, (outHovered != NULL ? &outHovered[0] : NULL), (outHeld != NULL ? &outHeld[0] : NULL), static_cast<ImGuiButtonFlags>(imGuiButtonFlags));
        if (outHovered != NULL) env->ReleasePrimitiveArrayCritical(obj_outHovered, outHovered, JNI_FALSE);
        if (outHeld != NULL) env->ReleasePrimitiveArrayCritical(obj_outHeld, outHeld, JNI_FALSE);
        return _result;
    */

    public static boolean splitterBehavior(final ImRect bb, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2) {
        return nSplitterBehavior(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2);
    }

    public static boolean splitterBehavior(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2) {
        return nSplitterBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2);
    }

    public static boolean splitterBehavior(final ImRect bb, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2, final float hoverExtend) {
        return nSplitterBehavior(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2, hoverExtend);
    }

    public static boolean splitterBehavior(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2, final float hoverExtend) {
        return nSplitterBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2, hoverExtend);
    }

    public static boolean splitterBehavior(final ImRect bb, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2, final float hoverExtend, final float hoverVisibilityDelay) {
        return nSplitterBehavior(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2, hoverExtend, hoverVisibilityDelay);
    }

    public static boolean splitterBehavior(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2, final float hoverExtend, final float hoverVisibilityDelay) {
        return nSplitterBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2, hoverExtend, hoverVisibilityDelay);
    }

    public static boolean splitterBehavior(final ImRect bb, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2, final float hoverExtend, final float hoverVisibilityDelay, final int bgCol) {
        return nSplitterBehavior(bb.min.x, bb.min.y, bb.max.x, bb.max.y, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2, hoverExtend, hoverVisibilityDelay, bgCol);
    }

    public static boolean splitterBehavior(final float bbMinX, final float bbMinY, final float bbMaxX, final float bbMaxY, final int id, final int axis, final ImFloat size1, final ImFloat size2, final float minSize1, final float minSize2, final float hoverExtend, final float hoverVisibilityDelay, final int bgCol) {
        return nSplitterBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, axis, size1 != null ? size1.getData() : null, size2 != null ? size2.getData() : null, minSize1, minSize2, hoverExtend, hoverVisibilityDelay, bgCol);
    }

    private static native boolean nSplitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int axis, float[] obj_size1, float[] obj_size2, float minSize1, float minSize2); /*MANUAL
        auto size1 = obj_size1 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size1, JNI_FALSE);
        auto size2 = obj_size2 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size2, JNI_FALSE);
        auto _result = ImGui::SplitterBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id, static_cast<ImGuiAxis>(axis), (size1 != NULL ? &size1[0] : NULL), (size2 != NULL ? &size2[0] : NULL), minSize1, minSize2);
        if (size1 != NULL) env->ReleasePrimitiveArrayCritical(obj_size1, size1, JNI_FALSE);
        if (size2 != NULL) env->ReleasePrimitiveArrayCritical(obj_size2, size2, JNI_FALSE);
        return _result;
    */

    private static native boolean nSplitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int axis, float[] obj_size1, float[] obj_size2, float minSize1, float minSize2, float hoverExtend); /*MANUAL
        auto size1 = obj_size1 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size1, JNI_FALSE);
        auto size2 = obj_size2 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size2, JNI_FALSE);
        auto _result = ImGui::SplitterBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id, static_cast<ImGuiAxis>(axis), (size1 != NULL ? &size1[0] : NULL), (size2 != NULL ? &size2[0] : NULL), minSize1, minSize2, hoverExtend);
        if (size1 != NULL) env->ReleasePrimitiveArrayCritical(obj_size1, size1, JNI_FALSE);
        if (size2 != NULL) env->ReleasePrimitiveArrayCritical(obj_size2, size2, JNI_FALSE);
        return _result;
    */

    private static native boolean nSplitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int axis, float[] obj_size1, float[] obj_size2, float minSize1, float minSize2, float hoverExtend, float hoverVisibilityDelay); /*MANUAL
        auto size1 = obj_size1 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size1, JNI_FALSE);
        auto size2 = obj_size2 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size2, JNI_FALSE);
        auto _result = ImGui::SplitterBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id, static_cast<ImGuiAxis>(axis), (size1 != NULL ? &size1[0] : NULL), (size2 != NULL ? &size2[0] : NULL), minSize1, minSize2, hoverExtend, hoverVisibilityDelay);
        if (size1 != NULL) env->ReleasePrimitiveArrayCritical(obj_size1, size1, JNI_FALSE);
        if (size2 != NULL) env->ReleasePrimitiveArrayCritical(obj_size2, size2, JNI_FALSE);
        return _result;
    */

    private static native boolean nSplitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int axis, float[] obj_size1, float[] obj_size2, float minSize1, float minSize2, float hoverExtend, float hoverVisibilityDelay, int bgCol); /*MANUAL
        auto size1 = obj_size1 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size1, JNI_FALSE);
        auto size2 = obj_size2 == NULL ? NULL : (float*)env->GetPrimitiveArrayCritical(obj_size2, JNI_FALSE);
        auto _result = ImGui::SplitterBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id, static_cast<ImGuiAxis>(axis), (size1 != NULL ? &size1[0] : NULL), (size2 != NULL ? &size2[0] : NULL), minSize1, minSize2, hoverExtend, hoverVisibilityDelay, bgCol);
        if (size1 != NULL) env->ReleasePrimitiveArrayCritical(obj_size1, size1, JNI_FALSE);
        if (size2 != NULL) env->ReleasePrimitiveArrayCritical(obj_size2, size2, JNI_FALSE);
        return _result;
    */

}
