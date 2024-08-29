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
        super();
    }

    public ImGuiWindowClass(final long ptr) {
        super(ptr);
    }

    @Override
    protected long create() {
        return nCreate();
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiWindowClass*)STRUCT_PTR)
     */

    private native long nCreate(); /*
        return (uintptr_t)(new ImGuiWindowClass());
    */

    /**
     * User data. 0 = Default class (unclassed). Windows of different classes cannot be docked with each others.
     */
    public int getClassId() {
        return nGetClassId();
    }

    /**
     * User data. 0 = Default class (unclassed). Windows of different classes cannot be docked with each others.
     */
    public void setClassId(final int value) {
        nSetClassId(value);
    }

    private native int nGetClassId(); /*
        return THIS->ClassId;
    */

    private native void nSetClassId(int value); /*
        THIS->ClassId = value;
    */

    /**
     * Hint for the platform backend. If non-zero, the platform backend can create a parent{@code <>}child relationship between the platform windows.
     * Not conforming backends are free to e.g. parent every viewport to the main viewport or not.
     */
    public int getParentViewportId() {
        return nGetParentViewportId();
    }

    /**
     * Hint for the platform backend. If non-zero, the platform backend can create a parent{@code <>}child relationship between the platform windows.
     * Not conforming backends are free to e.g. parent every viewport to the main viewport or not.
     */
    public void setParentViewportId(final int value) {
        nSetParentViewportId(value);
    }

    private native int nGetParentViewportId(); /*
        return THIS->ParentViewportId;
    */

    private native void nSetParentViewportId(int value); /*
        THIS->ParentViewportId = value;
    */

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public int getViewportFlagsOverrideSet() {
        return nGetViewportFlagsOverrideSet();
    }

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public void setViewportFlagsOverrideSet(final int value) {
        nSetViewportFlagsOverrideSet(value);
    }

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public void addViewportFlagsOverrideSet(final int flags) {
        setViewportFlagsOverrideSet(getViewportFlagsOverrideSet() | flags);
    }

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public void removeViewportFlagsOverrideSet(final int flags) {
        setViewportFlagsOverrideSet(getViewportFlagsOverrideSet() & ~(flags));
    }

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public boolean hasViewportFlagsOverrideSet(final int flags) {
        return (getViewportFlagsOverrideSet() & flags) != 0;
    }

    private native int nGetViewportFlagsOverrideSet(); /*
        return THIS->ViewportFlagsOverrideSet;
    */

    private native void nSetViewportFlagsOverrideSet(int value); /*
        THIS->ViewportFlagsOverrideSet = value;
    */

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public int getViewportFlagsOverrideClear() {
        return nGetViewportFlagsOverrideClear();
    }

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public void setViewportFlagsOverrideClear(final int value) {
        nSetViewportFlagsOverrideClear(value);
    }

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public void addViewportFlagsOverrideClear(final int flags) {
        setViewportFlagsOverrideClear(getViewportFlagsOverrideClear() | flags);
    }

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public void removeViewportFlagsOverrideClear(final int flags) {
        setViewportFlagsOverrideClear(getViewportFlagsOverrideClear() & ~(flags));
    }

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    public boolean hasViewportFlagsOverrideClear(final int flags) {
        return (getViewportFlagsOverrideClear() & flags) != 0;
    }

    private native int nGetViewportFlagsOverrideClear(); /*
        return THIS->ViewportFlagsOverrideClear;
    */

    private native void nSetViewportFlagsOverrideClear(int value); /*
        THIS->ViewportFlagsOverrideClear = value;
    */

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public int getTabItemFlagsOverrideSet() {
        return nGetTabItemFlagsOverrideSet();
    }

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public void setTabItemFlagsOverrideSet(final int value) {
        nSetTabItemFlagsOverrideSet(value);
    }

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public void addTabItemFlagsOverrideSet(final int flags) {
        setTabItemFlagsOverrideSet(getTabItemFlagsOverrideSet() | flags);
    }

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public void removeTabItemFlagsOverrideSet(final int flags) {
        setTabItemFlagsOverrideSet(getTabItemFlagsOverrideSet() & ~(flags));
    }

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    public boolean hasTabItemFlagsOverrideSet(final int flags) {
        return (getTabItemFlagsOverrideSet() & flags) != 0;
    }

    private native int nGetTabItemFlagsOverrideSet(); /*
        return THIS->TabItemFlagsOverrideSet;
    */

    private native void nSetTabItemFlagsOverrideSet(int value); /*
        THIS->TabItemFlagsOverrideSet = value;
    */

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public int getDockNodeFlagsOverrideSet() {
        return nGetDockNodeFlagsOverrideSet();
    }

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    public void setDockNodeFlagsOverrideSet(final int value) {
        nSetDockNodeFlagsOverrideSet(value);
    }

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

    private native int nGetDockNodeFlagsOverrideSet(); /*
        return THIS->DockNodeFlagsOverrideSet;
    */

    private native void nSetDockNodeFlagsOverrideSet(int value); /*
        THIS->DockNodeFlagsOverrideSet = value;
    */

    /**
     * Set to true to enforce single floating windows of this class always having their own docking node
     * (equivalent of setting the global io.ConfigDockingAlwaysTabBar)
     */
    public boolean getDockingAlwaysTabBar() {
        return nGetDockingAlwaysTabBar();
    }

    /**
     * Set to true to enforce single floating windows of this class always having their own docking node
     * (equivalent of setting the global io.ConfigDockingAlwaysTabBar)
     */
    public void setDockingAlwaysTabBar(final boolean value) {
        nSetDockingAlwaysTabBar(value);
    }

    private native boolean nGetDockingAlwaysTabBar(); /*
        return THIS->DockingAlwaysTabBar;
    */

    private native void nSetDockingAlwaysTabBar(boolean value); /*
        THIS->DockingAlwaysTabBar = value;
    */

    /**
     * Set to true to allow windows of this class to be docked/merged with an unclassed window.
     */
    public boolean getDockingAllowUnclassed() {
        return nGetDockingAllowUnclassed();
    }

    /**
     * Set to true to allow windows of this class to be docked/merged with an unclassed window.
     */
    public void setDockingAllowUnclassed(final boolean value) {
        nSetDockingAllowUnclassed(value);
    }

    private native boolean nGetDockingAllowUnclassed(); /*
        return THIS->DockingAllowUnclassed;
    */

    private native void nSetDockingAllowUnclassed(boolean value); /*
        THIS->DockingAllowUnclassed = value;
    */

    /*JNI
        #undef THIS
     */
}
