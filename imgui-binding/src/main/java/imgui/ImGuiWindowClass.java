package imgui;

import imgui.binding.ImGuiStructDestroyable;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;

/**
 * [ALPHA] Rarely used / very advanced uses only. Use with SetNextWindowClass() and DockSpace() functions.
 * Important: the content of this class is still highly WIP and likely to change and be refactored
 * before we stabilize Docking features. Please be mindful if using this.
 * Provide hints:
 * - To the platform backend via altered viewport flags (enable/disable OS decoration, OS task bar icons, etc.)
 * - To the platform backend for OS level parent/child relationships of viewport.
 * - To the docking system for various options and filtering.
 */
@BindingSource
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
    @BindingField
    public int ClassId;

    /**
     * Hint for the platform backend. If non-zero, the platform backend can create a parent{@code <>}child relationship between the platform windows.
     * Not conforming backends are free to e.g. parent every viewport to the main viewport or not.
     */
    @BindingField
    public int ParentViewportId;

    /**
     * ID of parent window for shortcut focus route evaluation, e.g. Shortcut() call from Parent Window will succeed when this window is focused.
     */
    @BindingField
    public int FocusRouteParentWindowId;

    /**
     * Viewport flags to set when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    @BindingField(isFlag = true)
    public int ViewportFlagsOverrideSet;

    /**
     * Viewport flags to clear when a window of this class owns a viewport.
     * This allows you to enforce OS decoration or task bar icon, override the defaults on a per-window basis.
     */
    @BindingField(isFlag = true)
    public int ViewportFlagsOverrideClear;

    /**
     * [EXPERIMENTAL] TabItem flags to set when a window of this class gets submitted into a dock node tab bar.
     * May use with ImGuiTabItemFlags_Leading or ImGuiTabItemFlags_Trailing.
     */
    @BindingField(isFlag = true)
    public int TabItemFlagsOverrideSet;

    /**
     * [EXPERIMENTAL] Dock node flags to set when a window of this class is hosted by a dock node (it doesn't have to be selected!)
     */
    @BindingField(isFlag = true)
    public int DockNodeFlagsOverrideSet;

    /**
     * Set to true to enforce single floating windows of this class always having their own docking node
     * (equivalent of setting the global io.ConfigDockingAlwaysTabBar)
     */
    @BindingField
    public boolean DockingAlwaysTabBar;

    /**
     * Set to true to allow windows of this class to be docked/merged with an unclassed window.
     */
    @BindingField
    public boolean DockingAllowUnclassed;

    /*JNI
        #undef THIS
     */
}
