package imgui;

/**
 * [ALPHA] Rarely used / very advanced uses only. Use with SetNextWindowClass() and DockSpace() functions.
 * Important: the content of this class is still highly WIP and likely to change and be refactored
 * before we stabilize Docking features. Please be mindful if using this.
 * Provide hints:
 * - To the platform back-end via altered viewport flags (enable/disable OS decoration, OS task bar icons, etc.)
 * - To the platform back-end for OS level parent/child relationships of viewport.
 * - To the docking system for various options and filtering.
 */
public final class ImGuiWindowClass implements ImDestroyable {
    final long ptr;

    /**
     * This class will create a native structure.
     * Call {@link #destroy()} method to manually free used memory.
     */
    public ImGuiWindowClass() {
        ImGui.touch();
        ptr = nCreate();
    }

    ImGuiWindowClass(final long ptr) {
        this.ptr = ptr;
    }

    @Override
    public void destroy() {
        nDestroy(ptr);
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>

        jfieldID imGuiWindowClassPtrID;

        #define IMGUI_WINDOW_CLASS ((ImGuiWindowClass*)env->GetLongField(object, imGuiWindowClassPtrID))
     */

    static native void nInit(); /*
        jclass jImGuiWindowClassClass = env->FindClass("imgui/ImGuiWindowClass");
        imGuiWindowClassPtrID = env->GetFieldID(jImGuiWindowClassClass, "ptr", "J");
    */

    private native long nCreate(); /*
        ImGuiWindowClass* imGuiWindowClass = new ImGuiWindowClass();
        return (intptr_t)imGuiWindowClass;
    */

    private native void nDestroy(long ptr); /*
        delete (ImGuiWindowClass*)ptr;
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
     * Hint for the platform back-end. If non-zero, the platform back-end can create a parent<>child relationship between the platform windows.
     * Not conforming back-ends are free to e.g. parent every viewport to the main viewport or not.
     */
    public native int getParentViewportId(); /*
        return IMGUI_WINDOW_CLASS->ParentViewportId;
    */

    /**
     * Hint for the platform back-end. If non-zero, the platform back-end can create a parent<>child relationship between the platform windows.
     * Not conforming back-ends are free to e.g. parent every viewport to the main viewport or not.
     */
    public native void setParentViewportId(int parentViewportId); /*
        IMGUI_WINDOW_CLASS->ParentViewportId = parentViewportId;
    */

    // TODO: ViewportFlagsOverrideSet, ViewportFlagsOverrideClear

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
