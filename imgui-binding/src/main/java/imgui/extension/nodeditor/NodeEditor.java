package imgui.extension.nodeditor;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.type.ImLong;

/**
 * Bindings for Imgui Node Editor (https://github.com/thedmd/imgui-node-editor)
 * Original library author - Michał Cichoń (https://github.com/thedmd)
 * <p>
 * An implementation of node editor with ImGui-like API.
 * Project purpose is to serve as a basis for more complex solutions like blueprint editors.
 * <p>
 * Refer to the library's Github page for examples and support
 * <p>
 * Before you can start drawing editor you have to create ImNodeEditorContext
 * (either by invoking constructor directly or using ImNodeEditor.createEditor()) and switch
 * current editor via ImNodeEditor.setCurrentEditor(NodeEditorContext)
 * <p>
 * Binding notice: instead of special types for ids of nodes, links and pins which used in native library
 * we use longs to reduce boilerplate and garbage production
 */
@BindingSource(callPtr = "ax::NodeEditor")
public final class NodeEditor {
    private NodeEditor() {
    }

    /*JNI
        #include "_nodeeditor.h"
        #define NodeEditorConfig ax::NodeEditor::Config
        #define NodeEditorStyle ax::NodeEditor::Style
        #define NodeEditorContext ax::NodeEditor::EditorContext
     */

    @BindingMethod
    public static native void SetCurrentEditor(NodeEditorContext ctx);

    @BindingMethod
    public static native NodeEditorContext GetCurrentEditor();

    @BindingMethod
    public static native NodeEditorContext CreateEditor(@OptArg NodeEditorConfig config);

    @BindingMethod
    public static native void DestroyEditor(NodeEditorContext ctx);

    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native NodeEditorStyle GetStyle();

    @BindingMethod
    public static native String GetStyleColorName(@ArgValue(staticCast = "ax::NodeEditor::StyleColor") int colorIndex);

    @BindingMethod
    public static native void PushStyleColor(@ArgValue(staticCast = "ax::NodeEditor::StyleColor") int colorIndex, ImVec4 color);

    @BindingMethod
    public static native void PopStyleColor(@OptArg int count);

    @BindingMethod
    public static native void PushStyleVar(@ArgValue(staticCast = "ax::NodeEditor::StyleVar")int varIndex, float value);

    @BindingMethod
    public static native void PushStyleVar(@ArgValue(staticCast = "ax::NodeEditor::StyleVar")int varIndex, ImVec2 value);

    @BindingMethod
    public static native void PushStyleVar(@ArgValue(staticCast = "ax::NodeEditor::StyleVar")int varIndex, ImVec4 value);

    @BindingMethod
    public static native void PopStyleVar(@OptArg int count);

    @BindingMethod
    public static native void Begin(String id, @OptArg ImVec2 size);

    @BindingMethod
    public static native void End();

    @BindingMethod
    public static native void BeginNode(long id);

    @BindingMethod
    public static native void BeginPin(long id, @ArgValue(staticCast = "ax::NodeEditor::PinKind") int kind);

    @BindingMethod
    public static native void PinRect(ImVec2 a, ImVec2 b);

    @BindingMethod
    public static native void PinPivotRect(ImVec2 a, ImVec2 b);

    @BindingMethod
    public static native void PinPivotSize(ImVec2 size);

    @BindingMethod
    public static native void PinPivotScale(ImVec2 scale);

    @BindingMethod
    public static native void PinPivotAlignment(ImVec2 alignment);

    @BindingMethod
    public static native void EndPin();

    @BindingMethod
    public static native void Group(ImVec2 size);

    @BindingMethod
    public static native void EndNode();

    @BindingMethod
    public static native boolean BeginGroupHint(long nodeId);

    @BindingMethod
    public static native ImVec2 GetGroupMin();

    @BindingMethod
    public static native ImVec2 GetGroupMax();

    @BindingMethod
    @ReturnValue(isStatic = true)
    public static native ImDrawList GetHintForegroundDrawList();

    @BindingMethod
    @ReturnValue(isStatic = true)
    public static native ImDrawList GetHintBackgroundDrawList();

    @BindingMethod
    public static native void EndGroupHint();

    @BindingMethod
    @ReturnValue(isStatic = true)
    public static native ImDrawList GetNodeBackgroundDrawList(long nodeId);

    @BindingMethod
    public static native boolean Link(long id, long startPinId, long endPinId, @OptArg(callValue = "ImVec4(1,1,1,1)") ImVec4 color, @OptArg float thickness);

    @BindingMethod
    public static native void Flow(long linkId, @OptArg @ArgValue(staticCast = "ax::NodeEditor::FlowDirection") int direction);

    @BindingMethod
    public static native boolean BeginCreate(@OptArg(callValue = "ImVec4(1,1,1,1)") ImVec4 color, @OptArg float thickness);

    @BindingMethod
    public static native boolean QueryNewLink(@ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong startId, @ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong endId);

    @BindingMethod
    public static native boolean QueryNewLink(@ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong startId, @ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong endId, ImVec4 color, @OptArg float thickness);

    @BindingMethod
    public static native boolean QueryNewNode(@ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong pinId);

    @BindingMethod
    public static native boolean QueryNewNode(@ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong pinId, ImVec4 color, @OptArg float thickness);

    @BindingMethod
    public static native boolean AcceptNewItem();

    @BindingMethod
    public static native boolean AcceptNewItem(ImVec4 color, @OptArg float thickness);

    @BindingMethod
    public static native void RejectNewItem();

    @BindingMethod
    public static native void RejectNewItem(ImVec4 color, @OptArg float thickness);

    @BindingMethod
    public static native void EndCreate();

    @BindingMethod
    public static native boolean BeginDelete();

    @BindingMethod
    public static native boolean QueryDeletedLink(@ArgValue(reinterpretCast = "ax::NodeEditor::LinkId*") ImLong linkId, @OptArg @ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong startId, @OptArg @ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong endId);

    @BindingMethod
    public static native boolean QueryDeletedNode(@ArgValue(reinterpretCast = "ax::NodeEditor::NodeId*") ImLong nodeId);

    @BindingMethod
    public static native boolean AcceptDeletedItem(@OptArg boolean deleteDependencies);

    @BindingMethod
    public static native void RejectDeletedItem();

    @BindingMethod
    public static native void EndDelete();

    @BindingMethod
    public static native void SetNodePosition(long nodeId, ImVec2 editorPosition);

    @BindingMethod
    public static native void SetGroupSize(long nodeId, ImVec2 size);

    @BindingMethod
    public static native ImVec2 GetNodePosition(long nodeId);

    @BindingMethod
    public static native ImVec2 GetNodeSize(long nodeId);

    @BindingMethod
    public static native void CenterNodeOnScreen(long nodeId);

    @BindingMethod
    public static native void SetNodeZPosition(long nodeId, float z);

    @BindingMethod
    public static native float GetNodeZPosition(long nodeId);

    @BindingMethod
    public static native void RestoreNodeState(long nodeId);

    @BindingMethod
    public static native void Suspend();

    @BindingMethod
    public static native void Resume();

    @BindingMethod
    public static native boolean IsSuspended();

    @BindingMethod
    public static native boolean IsActive();

    @BindingMethod
    public static native boolean HasSelectionChanged();

    @BindingMethod
    public static native int GetSelectedObjectCount();

    @BindingMethod
    public static native int GetSelectedNodes(@ArgValue(reinterpretCast = "ax::NodeEditor::NodeId*") long[] nodes, int size);

    @BindingMethod
    public static native int GetSelectedLinks(@ArgValue(reinterpretCast = "ax::NodeEditor::LinkId*") long[] links, int size);

    @BindingMethod
    public static native boolean IsNodeSelected(long nodeId);

    @BindingMethod
    public static native boolean IsLinkSelected(long linkId);

    @BindingMethod
    public static native void ClearSelection();

    @BindingMethod
    public static native void SelectNode(long nodeId, @OptArg boolean append);

    @BindingMethod
    public static native void SelectLink(long linkId, @OptArg boolean append);

    @BindingMethod
    public static native void DeselectNode(long nodeId);

    @BindingMethod
    public static native void DeselectLink(long linkId);

    @BindingMethod
    public static native boolean DeleteNode(long nodeId);

    @BindingMethod
    public static native boolean DeleteLink(long linkId);

    @BindingMethod(callName = "HasAnyLinks")
    public static native boolean HasAnyLinksNode(@ArgValue(staticCast = "ax::NodeEditor::NodeId") long nodeId);

    @BindingMethod(callName = "HasAnyLinks")
    public static native boolean HasAnyLinksPin(@ArgValue(staticCast = "ax::NodeEditor::PinId") long pinId);

    @BindingMethod(callName = "BreakLinks")
    public static native int BreakLinksNode(@ArgValue(staticCast = "ax::NodeEditor::NodeId") long nodeId);

    @BindingMethod(callName = "BreakLinks")
    public static native int BreakLinksPin(@ArgValue(staticCast = "ax::NodeEditor::PinId") long pinId);

    @BindingMethod
    public static native void NavigateToContent(@OptArg float duration);

    @BindingMethod
    public static native void NavigateToSelection(@OptArg(callValue = "false") boolean zoomIn, @OptArg float duration);

    @BindingMethod
    public static native boolean ShowNodeContextMenu(@ArgValue(reinterpretCast = "ax::NodeEditor::NodeId*") ImLong nodeId);

    @BindingMethod
    public static native boolean ShowPinContextMenu(@ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong pinId);

    @BindingMethod
    public static native boolean ShowLinkContextMenu(@ArgValue(reinterpretCast = "ax::NodeEditor::LinkId*") ImLong linkId);

    public static native long getNodeWithContextMenu(); /*
        ax::NodeEditor::NodeId id;
        return ax::NodeEditor::ShowNodeContextMenu(&id) ? (uintptr_t)id : -1;
    */

    public static native long getPinWithContextMenu(); /*
        ax::NodeEditor::PinId id;
        return ax::NodeEditor::ShowPinContextMenu(&id) ? (uintptr_t)id : -1;
    */

    public static native long getLinkWithContextMenu(); /*
        ax::NodeEditor::LinkId id;
        return ax::NodeEditor::ShowLinkContextMenu(&id) ? (uintptr_t)id : -1;
    */

    @BindingMethod
    public static native boolean ShowBackgroundContextMenu();

    @BindingMethod
    public static native void EnableShortcuts(boolean enable);

    @BindingMethod
    public static native boolean AreShortcutsEnabled();

    @BindingMethod
    public static native boolean BeginShortcut();

    @BindingMethod
    public static native boolean AcceptCut();

    @BindingMethod
    public static native boolean AcceptCopy();

    @BindingMethod
    public static native boolean AcceptPaste();

    @BindingMethod
    public static native boolean AcceptDuplicate();

    @BindingMethod
    public static native boolean AcceptCreateNode();

    @BindingMethod
    public static native int GetActionContextSize();

    @BindingMethod
    public static native int GetActionContextNodes(@ArgValue(reinterpretCast = "ax::NodeEditor::NodeId*") long[] nodes, int size);

    @BindingMethod
    public static native int GetActionContextLinks(@ArgValue(reinterpretCast = "ax::NodeEditor::LinkId*") long[] links, int size);

    @BindingMethod
    public static native void EndShortcut();

    @BindingMethod
    public static native float GetCurrentZoom();

    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetHoveredNode();

    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetHoveredPin();

    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetHoveredLink();

    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetDoubleClickedNode();

    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetDoubleClickedPin();

    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetDoubleClickedLink();

    @BindingMethod
    public static native boolean IsBackgroundClicked();

    @BindingMethod
    public static native boolean IsBackgroundDoubleClicked();

    @BindingMethod
    public static native boolean GetLinkPins(@ArgValue(staticCast = "ax::NodeEditor::LinkId") long linkId, @ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong startPinId, @ArgValue(reinterpretCast = "ax::NodeEditor::PinId*") ImLong endPinId);

    @BindingMethod
    public static native boolean PinHadAnyLinks(@ArgValue(staticCast = "ax::NodeEditor::PinId") long pinId);

    @BindingMethod
    public static native ImVec2 GetScreenSize();

    @BindingMethod
    public static native ImVec2 ScreenToCanvas(ImVec2 pos);

    @BindingMethod
    public static native ImVec2 CanvasToScreen(ImVec2 pos);

    @BindingMethod
    public static native int GetNodeCount();

    @BindingMethod
    public static native int GetOrderedNodeIds(@ArgValue(reinterpretCast = "ax::NodeEditor::NodeId*") long[] nodes, int size);

    /*JNI
        #undef NodeEditorConfig
        #undef NodeEditorStyle
        #undef NodeEditorContext
     */
}
