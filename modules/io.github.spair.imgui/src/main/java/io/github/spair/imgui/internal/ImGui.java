package io.github.spair.imgui.internal;

import io.github.spair.imgui.ImVec2;
import io.github.spair.imgui.type.ImFloat;
import io.github.spair.imgui.type.ImInt;

public final class ImGui extends io.github.spair.imgui.ImGui {
    private static final ImGuiDockNode DOCK_NODE = new ImGuiDockNode(0);

    private static final ImGuiWindow IMGUI_CURRENT_WINDOW = new ImGuiWindow(0);

    /*JNI
        #include "_common.h"
        #include <imgui_internal.h>
     */

    // Basic Helpers for widget code

    public static ImVec2 calcItemSize(float sizeX, float sizeY, float defaultW, float defaultH) {
        final ImVec2 value = new ImVec2();
        calcItemSize(sizeX, sizeY, defaultW, defaultH, value);
        return value;
    }

    public static native void calcItemSize(float sizeX, float sizeY, float defaultW, float defaultH, ImVec2 dstImVec2); /*
        Jni::ImVec2Cpy(env, ImGui::CalcItemSize(ImVec2(sizeX, sizeY), defaultW, defaultH), dstImVec2);
    */

    public static native float calcItemSizeX(float sizeX, float sizeY, float defaultW, float defaultH); /*
        return ImGui::CalcItemSize(ImVec2(sizeX, sizeY), defaultW, defaultH).x;
    */

    public static native float calcItemSizeY(float sizeX, float sizeY, float defaultW, float defaultH); /*
        return ImGui::CalcItemSize(ImVec2(sizeX, sizeY), defaultW, defaultH).y;
    */

    public static native void pushItemFlag(int imGuiItemFlags, boolean enabled); /*
        ImGui::PushItemFlag(imGuiItemFlags, enabled);
    */

    public static native void popItemFlag(); /*
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

    public static native void dockBuilderDockWindow(String windowName, int nodeId); /*
        ImGui::DockBuilderDockWindow(windowName, nodeId);
    */

    public static ImGuiDockNode dockBuilderGetNode(final int nodeId) {
        DOCK_NODE.ptr = nDockBuilderGetNode(nodeId);
        return DOCK_NODE;
    }

    private static native long nDockBuilderGetNode(int nodeId); /*
        return (intptr_t)ImGui::DockBuilderGetNode(nodeId);
    */

    public static ImGuiDockNode dockBuilderGetCentralNode(final int nodeId) {
        DOCK_NODE.ptr = nDockBuilderGetCentralNode(nodeId);
        return DOCK_NODE;
    }

    private static native long nDockBuilderGetCentralNode(int nodeId); /*
        return (intptr_t)ImGui::DockBuilderGetCentralNode(nodeId);
    */

    public static native int dockBuilderAddNode(); /*
        return ImGui::DockBuilderAddNode();
    */

    public static native int dockBuilderAddNode(int nodeId); /*
        return ImGui::DockBuilderAddNode(nodeId);
    */

    public static native int dockBuilderAddNode(int nodeId, int flags); /*
        return ImGui::DockBuilderAddNode(nodeId, flags);
    */

    /**
     * Remove node and all its child, undock all windows.
     */
    public static native void dockBuilderRemoveNode(int nodeId); /*
        ImGui::DockBuilderRemoveNode(nodeId);
    */

    public static native void dockBuilderRemoveNodeDockedWindows(int nodeId); /*
        ImGui::DockBuilderRemoveNodeDockedWindows(nodeId);
    */

    public static native void dockBuilderRemoveNodeDockedWindows(int nodeId, boolean clearSettingsRefs); /*
        ImGui::DockBuilderRemoveNodeDockedWindows(nodeId, clearSettingsRefs);
    */

    /**
     * Remove all split/hierarchy. All remaining docked windows will be re-docked to the remaining root node (node_id).
     */
    public static native void dockBuilderRemoveNodeChildNodes(int nodeId); /*
        ImGui::DockBuilderRemoveNodeChildNodes(nodeId);
    */

    public static native void dockBuilderSetNodePos(int nodeId, float posX, float posY); /*
        ImGui::DockBuilderSetNodePos(nodeId, ImVec2(posX, posY));
    */

    public static native void dockBuilderSetNodeSize(int nodeId, float sizeX, float sizeY); /*
        ImGui::DockBuilderSetNodeSize(nodeId, ImVec2(sizeX, sizeY));
    */

    /**
     * Create 2 child nodes in this parent node.
     */
    public static int dockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir, ImInt outIdAtDir, ImInt outIdAtOppositeDir) {
        if (outIdAtDir == null && outIdAtOppositeDir != null) {
            return nDockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, 0, outIdAtOppositeDir.getData());
        } else if (outIdAtDir != null && outIdAtOppositeDir == null) {
            return nDockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, outIdAtDir.getData());
        } else if (outIdAtDir != null) {
            return nDockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, outIdAtDir.getData(), outIdAtOppositeDir.getData());
        } else {
            return nDockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir);
        }
    }

    private static native int nDockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir, int[] outIdAtDir, int[] outIdAtOppositeDir); /*
        return ImGui::DockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, (ImGuiID*)&outIdAtDir[0], (ImGuiID*)&outIdAtOppositeDir[0]);
    */

    private static native int nDockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir); /*
        return ImGui::DockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, NULL, NULL);
    */

    private static native int nDockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir, int[] outIdAtDir); /*
        return ImGui::DockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, (ImGuiID*)&outIdAtDir[0], NULL);
    */

    private static native int nDockBuilderSplitNode(int nodeId, int splitDir, float sizeRatioForNodeAtDir, int o, int[] outIdAtOppositeDir); /*
        return ImGui::DockBuilderSplitNode(nodeId, splitDir, sizeRatioForNodeAtDir, NULL, (ImGuiID*)&outIdAtOppositeDir[0]);
    */

    // TODO DockBuilderCopyDockSpace, DockBuilderCopyNode

    public static native void dockBuilderCopyWindowSettings(String srcName, String dstName); /*
        ImGui::DockBuilderCopyWindowSettings(srcName, dstName);
    */

    public static native void dockBuilderFinish(int nodeId); /*
        ImGui::DockBuilderFinish(nodeId);
    */

    // Widgets low-level behaviors

    public static boolean splitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int imGuiAxis, ImFloat size1, ImFloat size2, float minSize1, float minSize2) {
        return splitterBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, imGuiAxis, size1, size2, minSize1, minSize2, 0, 0);
    }

    public static boolean splitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int imGuiAxis, ImFloat size1, ImFloat size2, float minSize1, float minSize2, float hoverExtend) {
        return splitterBehavior(bbMinX, bbMinY, bbMaxX, bbMaxY, id, imGuiAxis, size1, size2, minSize1, minSize2, hoverExtend, 0);
    }

    public static boolean splitterBehavior(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int imGuiAxis, ImFloat size1, ImFloat size2, float minSize1, float minSize2, float hoverExtend, float hoverVisibilityDelay) {
        return nSplitterBehaviour(bbMinX, bbMinY, bbMaxX, bbMaxY, id, imGuiAxis, size1.getData(), size2.getData(), minSize1, minSize2, hoverExtend, hoverVisibilityDelay);
    }

    private static native boolean nSplitterBehaviour(float bbMinX, float bbMinY, float bbMaxX, float bbMaxY, int id, int imGuiAxis, float[] size1, float[] size2, float minSize1, float minSize2, float hoverExtend, float hoverVisibilityDelay); /*
        return ImGui::SplitterBehavior(ImRect(bbMinX, bbMinY, bbMaxX, bbMaxY), id, (ImGuiAxis)imGuiAxis, &size1[0], &size2[0], minSize1, minSize2, hoverExtend, hoverVisibilityDelay);
    */

    public static ImGuiWindow getCurrentWindow() {
        IMGUI_CURRENT_WINDOW.ptr = nGetCurrentWindow();
        return IMGUI_CURRENT_WINDOW;
    }

    private static native long nGetCurrentWindow(); /*
        return (intptr_t)ImGui::GetCurrentWindow();
    */

    public static ImRect getWindowScrollbarRect(final ImGuiWindow imGuiWindow, int axis) {
        final ImRect imRect = new ImRect();
        nGetWindowScrollbarRect(imGuiWindow.ptr, axis, imRect.min, imRect.max);
        return imRect;
    }

    private static native void nGetWindowScrollbarRect(long windowPtr, int axis, ImVec2 minDstImVec2, ImVec2 maxDstImVec2); /*
        ImRect rect = ImGui::GetWindowScrollbarRect((ImGuiWindow*)windowPtr, static_cast<ImGuiAxis>(axis));
        Jni::ImVec2Cpy(env, &rect.Min, minDstImVec2);
        Jni::ImVec2Cpy(env, &rect.Max, maxDstImVec2);
    */
}
