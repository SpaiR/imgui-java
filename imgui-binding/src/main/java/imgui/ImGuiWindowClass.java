package imgui;

import imgui.binding.ImGuiStructDestroyable;

/**
 * [ALPHA] Rarely used / very advanced uses only. Use with SetNextWindowClass() and DockSpace() functions.
 * Important: the content of this class is still highly WIP and likely to change and be refactored
 * before we stabilize Docking features. Please be mindful if using this.
 * Provide hints:
 * - To the platform backend via altered viewport flags (enable/disable OS decoration, OS task bar icons, etc.)
 * - To the platform backend for OS level parent/child relationships of viewport.
 * - To the docking system for various options and filtering.
 */
public final class ImGuiWindowClass extends ImGuiStructDestroyable {
    public ImGuiWindowClass() {
    }

    public ImGuiWindowClass(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include "jni_binding_struct.h"

        #define IMGUI_WINDOW_CLASS ((ImGuiWindowClass*)STRUCT_PTR)
     */

    @Override
    protected long create() {
        return nCreate();
    }

    private native long nCreate(); /*
        return (intptr_t)(new ImGuiWindowClass());
    */

    /**
     * User data. 0 = Default class (unclassed). Windows of different classes cannot be docked with each others.
     */
    public native int geClassId(); /*
        return IMGUI_WINDOW_CLASS->ClassId;
    */

    /**
     * User data. 0 = Default class (unclassed). Windows of different classes cannot be docked with each others.
     */
    public native void setClassId(int classId); /*
        IMGUI_WINDOW_CLASS->ClassId = classId;
    */

    /**
     * Hint for the platform backend. If non-zero, the platform backend can create a parent{@code <>}child relationship between the platform windows.
     * Not conforming backends are free to e.g. parent every viewport to the main viewport or not.
     */
    public native int getParentViewportId(); /*
        return IMGUI_WINDOW_CLASS->ParentViewportId;
    */

    /**
     * Hint for the platform backend. If non-zero, the platform backend can create a parent{@code <>}child relationship between the platform windows.
     * Not conforming backends are free to e.g. parent every viewport to the main viewport or not.
     */
    public native void setParentViewportId(int parentViewportId); /*
        IMGUI_WINDOW_CLASS->ParentViewportId = parentViewportId;
    */

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public native int getViewportFlagsOverrideSet(); /*
        return IMGUI_WINDOW_CLASS->ViewportFlagsOverrideSet;
    */

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public native void setViewportFlagsOverrideSet(int viewportFlagsOverrideSet); /*
        IMGUI_WINDOW_CLASS->ViewportFlagsOverrideSet = viewportFlagsOverrideSet;
    */

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public native int getViewportFlagsOverrideClear(); /*
        return IMGUI_WINDOW_CLASS->ViewportFlagsOverrideClear;
    */

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public native void setViewportFlagsOverrideClear(int viewportFlagsOverrideClear); /*
        IMGUI_WINDOW_CLASS->ViewportFlagsOverrideClear = viewportFlagsOverrideClear;
    */

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public native int getTabItemFlagsOverrideSet(); /*
        return IMGUI_WINDOW_CLASS->TabItemFlagsOverrideSet;
    */

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public native void setTabItemFlagsOverrideSet(int tabItemFlagsOverrideSet); /*
        IMGUI_WINDOW_CLASS->TabItemFlagsOverrideSet = tabItemFlagsOverrideSet;
    */

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public native int getDockNodeFlagsOverrideSet(); /*
        return IMGUI_WINDOW_CLASS->DockNodeFlagsOverrideSet;
    */

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public native void setDockNodeFlagsOverrideSet(int dockNodeFlagsOverrideSet); /*
        IMGUI_WINDOW_CLASS->DockNodeFlagsOverrideSet = dockNodeFlagsOverrideSet;
    */

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public void addDockNodeFlagsOverrideSet(final int flags) {
        setDockNodeFlagsOverrideSet(getDockNodeFlagsOverrideSet() | flags);
    }

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public void removeDockNodeFlagsOverrideSet(final int flags) {
        setDockNodeFlagsOverrideSet(getDockNodeFlagsOverrideSet() & ~(flags));
    }

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public boolean hasDockNodeFlagsOverrideSet(final int flags) {
        return (getDockNodeFlagsOverrideSet() & flags) != 0;
    }

    /**
     * [EXPERIMENTAL]
     */
    public native int getDockNodeFlagsOverrideClear(); /*
        return IMGUI_WINDOW_CLASS->DockNodeFlagsOverrideClear;
    */

    /**
     * [EXPERIMENTAL]
     */
    public native void setDockNodeFlagsOverrideClear(int dockNodeFlagsOverrideClear); /*
        IMGUI_WINDOW_CLASS->DockNodeFlagsOverrideClear = dockNodeFlagsOverrideClear;
    */

    /**
     * [EXPERIMENTAL]
     */
    public void addDockNodeFlagsOverrideClear(final int flags) {
        setDockNodeFlagsOverrideClear(getDockNodeFlagsOverrideClear() | flags);
    }

    /**
     * [EXPERIMENTAL]
     */
    public void removeDockNodeFlagsOverrideClear(final int flags) {
        setDockNodeFlagsOverrideClear(getDockNodeFlagsOverrideClear() & ~(flags));
    }

    /**
     * [EXPERIMENTAL]
     */
    public boolean hasDockNodeFlagsOverrideClear(final int flags) {
        return (getDockNodeFlagsOverrideClear() & flags) != 0;
    }

    /**
     * Set to true to enforce single floating windows of this class always having their own docking node
     * (equivalent of setting the global io.ConfigDockingAlwaysTabBar)
     */
    public native boolean getDockingAlwaysTabBar(); /*
        return IMGUI_WINDOW_CLASS->DockingAlwaysTabBar;
    */

    /**
     * Set to true to enforce single floating windows of this class always having their own docking node
     * (equivalent of setting the global io.ConfigDockingAlwaysTabBar)
     */
    public native void setDockingAlwaysTabBar(boolean dockingAlwaysTabBar); /*
        IMGUI_WINDOW_CLASS->DockingAlwaysTabBar = dockingAlwaysTabBar;
    */

    /**
     * Set to true to allow windows of this class to be docked/merged with an unclassed window.
     */
    public native boolean getDockingAllowUnclassed(); /*
        return IMGUI_WINDOW_CLASS->DockingAllowUnclassed;
    */

    /**
     * Set to true to allow windows of this class to be docked/merged with an unclassed window.
     */
    public native void setDockingAllowUnclassed(boolean dockingAllowUnclassed); /*
        IMGUI_WINDOW_CLASS->DockingAllowUnclassed = dockingAllowUnclassed;
    */
}
