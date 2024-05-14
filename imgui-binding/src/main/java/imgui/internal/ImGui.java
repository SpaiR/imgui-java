package imgui.internal;

import imgui.ImVec2;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.type.ImFloat;
import imgui.type.ImInt;

@BindingSource
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

    // Basic Helpers for widget code

    @BindingMethod
    public static native ImVec2 CalcItemSize(ImVec2 size, float defaultW, float defaultH);

    @BindingMethod
    public static native void PushItemFlag(int imGuiItemFlags, boolean enabled);

    @BindingMethod
    public static native void PopItemFlag();

    // Docking - Builder function needs to be generally called before the node is used/submitted.
    // - The DockBuilderXXX functions are designed to _eventually_ become a public API, but it is too early to expose it and guarantee stability.
    // - Do not hold on ImGuiDockNode* pointers! They may be invalidated by any split/merge/remove operation and every frame.
    // - To create a DockSpace() node, make sure to set the ImGuiDockNodeFlags_DockSpace flag when calling DockBuilderAddNode().
    //   You can create dockspace nodes (attached to a window) _or_ floating nodes (carry its own window) with this API.
    // - DockBuilderSplitNode() create 2 child nodes within 1 node. The initial node becomes a parent node.
    // - If you intend to split the node immediately after creation using DockBuilderSplitNode(), make sure
    //   to call DockBuilderSetNodeSize() beforehand. If you don't, the resulting split sizes may not be reliable.
    // - Call DockBuilderFinish() after you are done.

    @BindingMethod
    public static native void DockBuilderDockWindow(String windowName, int nodeId);

    @BindingMethod
    public static native ImGuiDockNode DockBuilderGetNode(final int nodeId);

    @BindingMethod
    public static native ImGuiDockNode DockBuilderGetCentralNode(final int nodeId);

    @BindingMethod
    public static native int DockBuilderAddNode(@OptArg int nodeId, @OptArg int flags);

    /**
     * Remove node and all its child, undock all windows.
     */
    @BindingMethod
    public static native void DockBuilderRemoveNode(int nodeId);

    @BindingMethod
    public static native void DockBuilderRemoveNodeDockedWindows(int nodeId, @OptArg boolean clearSettingsRefs);

    /**
     * Remove all split/hierarchy. All remaining docked windows will be re-docked to the remaining root node (node_id).
     */
    @BindingMethod
    public static native void DockBuilderRemoveNodeChildNodes(int nodeId);

    @BindingMethod
    public static native void DockBuilderSetNodePos(int nodeId, ImVec2 pos);

    @BindingMethod
    public static native void DockBuilderSetNodeSize(int nodeId, ImVec2 size);

    /**
     * Create 2 child nodes in this parent node.
     */
    @BindingMethod
    public static native int DockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir, @ArgValue(reinterpretCast = "ImGuiID*") ImInt outIdAtDir, @ArgValue(reinterpretCast = "ImGuiID*") ImInt outIdAtOppositeDir);

    // TODO DockBuilderCopyDockSpace, DockBuilderCopyNode

    @BindingMethod
    public static native void DockBuilderCopyWindowSettings(String srcName, String dstName);

    @BindingMethod
    public static native void DockBuilderFinish(int nodeId);

    // Widgets low-level behaviors

    @BindingMethod
    public static native boolean SplitterBehavior(ImRect bb, int id, @ArgValue(staticCast = "ImGuiAxis") int axis, ImFloat size1, ImFloat size2, float minSize1, float minSize2, @OptArg float hoverExtend, @OptArg float hoverVisibilityDelay, @OptArg int bgCol);

    @BindingMethod
    public static native ImGuiWindow GetCurrentWindow();

    @BindingMethod
    public static native ImRect GetWindowScrollbarRect(ImGuiWindow imGuiWindow, @ArgValue(staticCast = "ImGuiAxis") int axis);
}
