package imgui.extension.nodeditor;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.type.ImLong;

/**
 * Bindings for Imgui Node Editor (https://github.com/thedmd/imgui-node-editor)
 * Original library author - Michał Cichoń (https://github.com/thedmd)
 *
 * An implementation of node editor with ImGui-like API.
 * Project purpose is to serve as a basis for more complex solutions like blueprint editors.
 *
 * Refer to the library's Github page for examples and support
 *
 * Before you can start drawing editor you have to create ImNodeEditorContext
 * (either by invoking constructor directly or using ImNodeEditor.createEditor()) and switch
 * current editor via ImNodeEditor.setCurrentEditor(NodeEditorContext)
 *
 * Binding notice: instead of special types for ids of nodes, links and pins which used in native library
 * we use longs to reduce boilerplate and garbage production
 */
public final class NodeEditor {
    private static final ImDrawList HINT_FOREGROUND_DRAW_LIST = new ImDrawList(0);
    private static final ImDrawList HINT_BACKGROUND_DRAW_LIST = new ImDrawList(0);
    private static final ImDrawList NODE_BACKGROUND_DRAW_LIST = new ImDrawList(0);

    private static NodeEditorStyle style;

    private NodeEditor() {
    }

    /*JNI
        #include <imgui.h>
        #include <imgui_node_editor.h>
        #include <imgui_node_editor_internal.h>
        #include "jni_common.h"
        #include "jni_binding_struct.h"

        namespace ed = ax::NodeEditor;
     */

    /**
     * This method exists for api consistency and you can just call NodeEditorContext manually
     */
    public static NodeEditorContext createEditor() {
        return new NodeEditorContext();
    }

    public static NodeEditorContext createEditor(final NodeEditorConfig config) {
        return new NodeEditorContext(config);
    }

    /**
     * This method exists for api consistency and you can just call NodeEditorContext.destroy() manually
     */
    public static void destroyEditor(final NodeEditorContext editorContext) {
        editorContext.destroy();
    }

    public static void setCurrentEditor(final NodeEditorContext editorContext) {
        nSetCurrentEditor(editorContext.ptr);
    }

    private static native void nSetCurrentEditor(long editor); /*
        ed::SetCurrentEditor((ed::EditorContext*)editor);
    */

    public static native void begin(String title); /*
        ed::Begin(title);
    */

    public static native void beginNode(long id); /*
        ed::BeginNode(id);
    */

    public static native void group(float w, float h); /*
        ed::Group(ImVec2(w, h));
    */

    public static native boolean beginGroupHint(long id); /*
        return ed::BeginGroupHint(id);
    */


    public static native void beginPin(long pin, int imNodeEditorPinKind); /*
        ed::BeginPin(pin, (ed::PinKind)imNodeEditorPinKind);
    */

    public static native void endGroupHint(); /*
        ed::EndGroupHint();
    */

    public static native void endPin(); /*
        ed::EndPin();
    */

    public static native void endNode(); /*
        ed::EndNode();
    */

    public static native void end(); /*
        ed::End();
    */

    public static native float getScreenSizeX(); /*
        return ed::GetScreenSize().x;
    */

    public static native float getScreenSizeY(); /*
        return ed::GetScreenSize().y;
    */

    public static native float toCanvasX(float screenSpacePosX); /*
        return ed::ScreenToCanvas(ImVec2(screenSpacePosX, 0.0f)).x;
    */

    public static native float toCanvasY(float screenSpacePosY); /*
        return ed::ScreenToCanvas(ImVec2(0.0f, screenSpacePosY)).y;
    */

    public static native float toScreenX(float canvasSpacePosX); /*
        return ed::CanvasToScreen(ImVec2(canvasSpacePosX, 0.0f)).x;
    */

    public static native float toScreenY(float canvasSpacePosY); /*
        return ed::CanvasToScreen(ImVec2(0.0f, canvasSpacePosY)).y;
    */

    public static NodeEditorStyle getStyle() {
        if (style == null) {
            style = new NodeEditorStyle(nGetStyle());
        }
        return style;
    }

    private static native long nGetStyle(); /*
        return (intptr_t)&ed::GetStyle();
    */

    public static native String getStyleColorName(int imNodeEditorStyleColor); /*
        return env->NewStringUTF(ed::GetStyleColorName((ed::StyleColor)imNodeEditorStyleColor));
    */

    public static native void pushStyleColor(int imNodeEditorStyleColor, float r, float g, float b, float a); /*
        ed::PushStyleColor((ed::StyleColor)imNodeEditorStyleColor, ImVec4(r, g, b, a));
    */

    public static native void popStyleColor(int count); /*
        ed::PopStyleColor(count);
    */

    public static native void pushStyleVar(int imNodeEditorStyleVar, float v); /*
        ed::PushStyleVar((ed::StyleVar)imNodeEditorStyleVar, v);
    */

    public static native void pushStyleVar(int imNodeEditorStyleVar, float x, float y); /*
        ed::PushStyleVar((ed::StyleVar)imNodeEditorStyleVar, ImVec2(x, y));
    */

    public static native void pushStyleVar(int imNodeEditorStyleVar, float r, float g, float b, float a); /*
        ed::PushStyleVar((ed::StyleVar)imNodeEditorStyleVar, ImVec4(r, g, b, a));
    */

    public static native void popStyleVar(int count); /*
        ed::PopStyleVar(count);
    */

    public static native float getGroupMinX(); /*
        return ed::GetGroupMin().x;
    */

    public static native float getGroupMinY(); /*
        return ed::GetGroupMin().y;
    */

    public static native float getGroupMaxX(); /*
        return ed::GetGroupMax().x;
    */

    public static native float getGroupMaxY(); /*
        return ed::GetGroupMax().y;
    */

    /**
     *  Binding notice: both getHintForegroundDrawList(), getHintBackgroundDrawList() and getNodeBackgroundDrawList(long)
     *  return singleton objects which shouldn't be used outside of the scope of current hint/node
     */

    public static ImDrawList getHintForegroundDrawList() {
        HINT_FOREGROUND_DRAW_LIST.ptr = nGetHintForegroundDrawList();
        return HINT_FOREGROUND_DRAW_LIST;
    }

    public static ImDrawList getHintBackgroundDrawList() {
        HINT_BACKGROUND_DRAW_LIST.ptr = nGetHintBackgroundDrawList();
        return HINT_BACKGROUND_DRAW_LIST;
    }

    public static ImDrawList getNodeBackgroundDrawList(final long nodeId) {
        final long ptr = nGetNodeBackgroundDrawList(nodeId);
        if (ptr == 0) {
            return null;
        } else {
            NODE_BACKGROUND_DRAW_LIST.ptr = ptr;
            return NODE_BACKGROUND_DRAW_LIST;
        }
    }

    private static native long nGetHintForegroundDrawList(); /*
        return (intptr_t)(uintptr_t)ed::GetHintForegroundDrawList();
    */

    private static native long nGetHintBackgroundDrawList(); /*
        return (intptr_t)(uintptr_t)ed::GetHintBackgroundDrawList();
    */

    private static native long nGetNodeBackgroundDrawList(long nodeId); /*
        return (intptr_t)(uintptr_t)ed::GetNodeBackgroundDrawList(nodeId);
    */

    public static native long getDoubleClickedNode(); /*
        return (intptr_t)(uintptr_t)ed::GetDoubleClickedNode();
    */

    public static native long getDoubleClickedPin(); /*
        return (intptr_t)(uintptr_t)ed::GetDoubleClickedPin();
    */

    public static native long getDoubleClickedLink(); /*
        return (intptr_t)(uintptr_t)ed::GetDoubleClickedLink();
    */

    public static native boolean isBackgroundClicked(); /*
        return ed::IsBackgroundClicked();
    */

    public static native boolean isBackgroundDoubleClicked(); /*
        return ed::IsBackgroundDoubleClicked();
    */

    public static native boolean pinHadAnyLinks(long pinId); /*
        return ed::PinHadAnyLinks(pinId);
    */

    public static native float getCurrentZoom(); /*
        return ed::GetCurrentZoom();
    */

    public static native void pinRect(float x, float y, float w, float h); /*
        ed::PinRect(ImVec2(x, y), ImVec2(w, h));
    */

    public static native void pinPivotRect(float x, float y, float w, float h); /*
        ed::PinPivotRect(ImVec2(x, y), ImVec2(w, h));
    */

    public static native void pinPivotSize(float w, float h); /*
        ed::PinPivotSize(ImVec2(w, h));
    */

    public static native void pinPivotScale(float w, float h); /*
        ed::PinPivotScale(ImVec2(w, h));
    */

    public static native void pinPivotAlignment(float x, float y); /*
        ed::PinPivotAlignment(ImVec2(x, y));
    */

    public static boolean showNodeContextMenu(final ImLong nodeId) {
        return nShowNodeContextMenu(nodeId.getData());
    }

    private static native boolean nShowNodeContextMenu(long[] nodeId); /*
        return ed::ShowNodeContextMenu((ed::NodeId*)&nodeId[0]);
    */

    public static boolean showPinContextMenu(final ImLong pinId) {
        return nShowPinContextMenu(pinId.getData());
    }

    private static native boolean nShowPinContextMenu(long[] pinId); /*
        return ed::ShowPinContextMenu((ed::PinId*)&pinId[0]);
    */

    public static boolean showLinkContextMenu(final ImLong linkId) {
        return nShowLinkContextMenu(linkId.getData());
    }

    private static native boolean nShowLinkContextMenu(long[] linkId); /*
        return ed::ShowLinkContextMenu((ed::LinkId*)&linkId[0]);
    */

    /**
     * Binding notice: getNodeWithContextMenu(), getPinWithContextMenu() and getLinkWithContextMenu()
     * return id of the object for which context menu should be shown. If there is no such object -1 will be returned.
     * <p>
     * These methods implemented as convenient alternative to showNodeContextMenu(ImLong), showPinContextMenu(ImLong) and
     * showLinkContextMenu(ImLong)
     */

    public static native long getNodeWithContextMenu(); /*
        ed::NodeId id;
        return ed::ShowNodeContextMenu(&id) ? (intptr_t)(uintptr_t)id : -1;
    */

    public static native long getPinWithContextMenu(); /*
        ed::PinId id;
        return ed::ShowPinContextMenu(&id) ? (intptr_t)(uintptr_t)id : -1;
    */

    public static native long getLinkWithContextMenu(); /*
        ed::LinkId id;
        return ed::ShowLinkContextMenu(&id) ? (intptr_t)(uintptr_t)id : -1;
    */

    public static native boolean showBackgroundContextMenu(); /*
        return ed::ShowBackgroundContextMenu();
    */

    public static native void restoreNodeState(long node); /*
        ed::RestoreNodeState(node);
    */

    public static native void suspend(); /*
        ed::Suspend();
    */

    public static native void resume(); /*
        ed::Resume();
    */

    public static native boolean isSuspended(); /*
        return ed::IsSuspended();
    */

    public static native boolean isActive(); /*
        return ed::IsActive();
    */

    public static native void setNodePosition(long node, float x, float y); /*
        ed::SetNodePosition(node, ImVec2(x, y));
    */

    public static void link(final long id, final long startPinId, final long endPinId) {
        link(id, startPinId, endPinId, 1, 1, 1, 1, 1);
    }

    public static native void link(long id, long startPinId, long endPinId, float r, float g, float b, float a,
                                   float thickness); /*
        ed::Link(id, startPinId, endPinId, ImVec4(r, g, b, a), thickness);
    */

    public static native void flow(long linkId); /*
        ed::Flow(linkId);
    */

    public static boolean beginCreate() {
        return beginCreate(1, 1, 1, 1, 1);
    }

    public static native boolean beginCreate(float r, float g, float b, float a, float thickness); /*
        return ed::BeginCreate(ImVec4(r, g, b, a), thickness);
    */

    public static boolean queryNewLink(final ImLong startId, final ImLong endId) {
        return nQueryNewLink(startId.getData(), endId.getData(), 1, 1, 1, 1, 1);
    }

    public static boolean queryNewLink(final ImLong startId, final ImLong endId, final float r, final float g, final float b, final float a, final float thickness) {
        return nQueryNewLink(startId.getData(), endId.getData(), r, g, b, a, thickness);
    }

    private static native boolean nQueryNewLink(long[] startId, long[] endId, float r, float g, float b, float a, float thickness); /*
        return ed::QueryNewLink((ed::PinId*)&startId[0], (ed::PinId*)&endId[0], ImVec4(r, g, b, a), thickness);
    */

    public static boolean acceptNewItem() {
        return acceptNewItem(1, 1, 1, 1, 1);
    }

    public static native boolean acceptNewItem(float r, float g, float b, float a, float thickness); /*
        return ed::AcceptNewItem(ImVec4(r, g, b, a), thickness);
    */

    public static void rejectNewItem() {
        rejectNewItem(1, 1, 1, 1, 1);
    }

    public static native void rejectNewItem(float r, float g, float b, float a, float thickness); /*
        ed::RejectNewItem(ImVec4(r, g, b, a), thickness);
    */

    public static native void endCreate(); /*
        ed::EndCreate();
    */

    public static native boolean beginDelete(); /*
        return ed::BeginDelete();
    */

    public static boolean queryDeletedLink(final ImLong linkId, final ImLong startId, final ImLong endId) {
        return nQueryDeletedLink(linkId.getData(), startId.getData(), endId.getData());
    }

    private static native boolean nQueryDeletedLink(long[] linkId, long[] startId, long[] endId); /*
        return ed::QueryDeletedLink((ed::LinkId*)&linkId[0], (ed::PinId*)&startId[0], (ed::PinId*)&endId[0]);
    */

    public static boolean queryDeletedNode(final ImLong nodeId) {
        return nQueryDeletedNode(nodeId.getData());
    }

    private static native boolean nQueryDeletedNode(long[] nodeId); /*
        return ed::QueryDeletedNode((ed::NodeId*)&nodeId[0]);
    */

    public static native boolean acceptDeletedItem(); /*
        return ed::AcceptDeletedItem();
    */

    public static native void rejectDeletedItem(); /*
        ed::RejectDeletedItem();
    */

    public static native void endDelete(); /*
        ed::EndDelete();
    */

    public static native void navigateToContent(float duration); /*
        ed::NavigateToContent(duration);
    */

    public static native void navigateToSelection(boolean zoomIn, float duration); /*
        ed::NavigateToSelection(zoomIn, duration);
    */

    public static native void getNodePosition(long node, ImVec2 dst); /*
        ImVec2 result = ed::GetNodePosition(node);
        Jni::ImVec2Cpy(env, &result, dst);
    */

    public static native float getNodePositionX(long node); /*
        return ed::GetNodePosition(node).x;
    */

    public static native float getNodePositionY(long node); /*
        return ed::GetNodePosition(node).y;
    */

    public static native float getNodeSizeX(long node); /*
        return ed::GetNodeSize(node).x;
    */

    public static native float getNodeSizeY(long node); /*
        return ed::GetNodeSize(node).y;
    */

    public static native void centerNodeOnScreen(long node); /*
        ed::CenterNodeOnScreen(node);
    */

    public static native boolean hasSelectionChanged(); /*
        return ed::HasSelectionChanged();
    */

    public static native int getSelectedObjectCount(); /*
        return ed::GetSelectedObjectCount();
    */

    public static native int getSelectedNodes(long[] nodes, int size); /*
        return ed::GetSelectedNodes((ed::NodeId*)&nodes[0], size);
    */

    public static native int getSelectedLinks(long[] links, int size); /*
        return ed::GetSelectedLinks((ed::LinkId*)&links[0], size);
    */

    public static native void clearSelection(); /*
        return ed::ClearSelection();
    */

    public static native void selectNode(long id, boolean append); /*
        ed::SelectNode(id, append);
    */

    public static native void selectLink(long id, boolean append); /*
        ed::SelectLink(id, append);
    */

    public static native void deselectNode(long id); /*
        ed::DeselectNode(id);
    */

    public static native void deselectLink(long id); /*
        ed::DeselectLink(id);
    */

    public static native boolean deleteNode(long nodeId); /*
        return ed::DeleteNode(nodeId);
    */

    public static native boolean deleteLink(long linkId); /*
        return ed::DeleteLink(linkId);
    */

    public static native void enableShortcuts(boolean enable); /*
        ed::EnableShortcuts(enable);
    */

    public static native boolean areShortcutsEnabled(); /*
        return ed::AreShortcutsEnabled();
    */

    public static native boolean beginShortcut(); /*
        return ed::BeginShortcut();
    */

    public static native boolean acceptCut(); /*
        return ed::AcceptCut();
    */

    public static native boolean acceptCopy(); /*
        return ed::AcceptCopy();
    */

    public static native boolean acceptPaste(); /*
        return ed::AcceptPaste();
    */

    public static native boolean acceptDuplicate(); /*
        return ed::AcceptDuplicate();
    */

    public static native boolean acceptCreateNode(); /*
        return ed::AcceptCreateNode();
    */

    public static native int getActionContextSize(); /*
        return ed::GetActionContextSize();
    */

    public static native int getActionContextNodes(long[] nodes, int size); /*
        return ed::GetActionContextNodes((ed::NodeId*)&nodes[0], size);
    */

    public static native int getActionContextLinks(long[] links, int size); /*
        return ed::GetActionContextLinks((ed::LinkId*)&links[0], size);
    */

    public static native void endShortcut(); /*
        ed::EndShortcut();
    */
}
