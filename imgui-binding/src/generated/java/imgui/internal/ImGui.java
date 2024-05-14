package imgui.internal;

import imgui.ImVec2;
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

    // Basic Helpers for widget code

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

    public static void pushItemFlag(final int imGuiItemFlags, final boolean enabled) {
        nPushItemFlag(imGuiItemFlags, enabled);
    }

    private static native void nPushItemFlag(int imGuiItemFlags, boolean enabled); /*
        ImGui::PushItemFlag(imGuiItemFlags, enabled);
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
        return (intptr_t)ImGui::DockBuilderGetNode(nodeId);
    */

    public static ImGuiDockNode dockBuilderGetCentralNode(final int nodeId) {
        return new ImGuiDockNode(nDockBuilderGetCentralNode(nodeId));
    }

    private static native long nDockBuilderGetCentralNode(int nodeId); /*
        return (intptr_t)ImGui::DockBuilderGetCentralNode(nodeId);
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

    // Widgets low-level behaviors

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

    public static ImGuiWindow getCurrentWindow() {
        return new ImGuiWindow(nGetCurrentWindow());
    }

    private static native long nGetCurrentWindow(); /*
        return (intptr_t)ImGui::GetCurrentWindow();
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
}
