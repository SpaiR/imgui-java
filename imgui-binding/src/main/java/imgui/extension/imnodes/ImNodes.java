package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

/**
 * Bindings for Imnodes (https://github.com/Nelarius/imnodes/)
 * Original library author - Johann Muszynski (https://github.com/Nelarius)
 * <p>
 * Refer to the library's Github page for examples and support
 */
@BindingSource
public final class ImNodes {
    private ImNodes() {
    }

    /*JNI
        #include "_imnodes.h"
     */

    // An editor context corresponds to a set of nodes in a single workspace (created with a single
    // Begin/EndNodeEditor pair)
    //
    // By default, the library creates an editor context behind the scenes, so using any of the ImNodes
    // functions doesn't require you to explicitly create a context.

    @BindingMethod
    public static native void SetImGuiContext(ImGuiContext ctx);

    @BindingMethod
    public static native ImNodesContext CreateContext();

    @BindingMethod
    public static native void DestroyContext(@OptArg ImNodesContext ctx);

    @BindingMethod
    public static native ImNodesContext GetCurrentContext();

    @BindingMethod
    public static native void SetCurrentContext(ImNodesContext ctx);

    @BindingMethod
    public static native ImNodesEditorContext EditorContextCreate();

    @BindingMethod
    public static native void EditorContextFree(ImNodesEditorContext context);

    @BindingMethod
    public static native void EditorContextSet(ImNodesEditorContext context);

    @BindingMethod
    public static native ImVec2 EditorContextGetPanning();

    @BindingMethod
    public static native void EditorContextResetPanning(ImVec2 pos);

    @BindingMethod
    public static native void EditorContextMoveToNode(int nodeId);

    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native ImNodesIO GetIO();

    /**
     * Returns the global style struct. See the struct declaration for default values.
     */
    @BindingMethod
    @ReturnValue(isStatic = true, callPrefix = "&")
    public static native ImNodesStyle GetStyle();

    // Style presets matching the dear imgui styles of the same name.

    @BindingMethod
    public static native void StyleColorsDark(@OptArg ImNodesStyle style);

    @BindingMethod
    public static native void StyleColorsClassic(@OptArg ImNodesStyle style);

    @BindingMethod
    public static native void StyleColorsLight(@OptArg ImNodesStyle style);

    /**
     * The top-level function call. Call this before calling BeginNode/EndNode. Calling this function
     * will result the node editor grid workspace being rendered.
     */
    @BindingMethod
    public static native void BeginNodeEditor();

    @BindingMethod
    public static native void EndNodeEditor();

    /**
     * Add a navigable minimap to the editor; call before EndNodeEditor after all nodes and links have been specified
     * // TODO: add callback
     */
    @BindingMethod
    public static native void MiniMap(@OptArg float miniMapSizeFraction, @OptArg @ArgValue(staticCast = "ImNodesMiniMapLocation") int miniMapLocation);

    /**
     * Use PushColorStyle and PopColorStyle to modify ImNodesColorStyle mid-frame.
     */
    @BindingMethod
    public static native void PushColorStyle(@ArgValue(staticCast = "ImNodesCol") int item, int color);

    @BindingMethod
    public static native void PopColorStyle();

    @BindingMethod
    public static native void PushStyleVar(@ArgValue(staticCast = "ImNodesStyleVar") int styleItem, float value);

    @BindingMethod
    public static native void PushStyleVar(@ArgValue(staticCast = "ImNodesStyleVar") int styleItem, ImVec2 value);

    @BindingMethod
    public static native void PopStyleVar();

    @BindingMethod
    public static native void BeginNode(int node);

    @BindingMethod
    public static native void EndNode();

    @BindingMethod
    public static native ImVec2 GetNodeDimensions(int id);

    /**
     * Place your node title bar content (such as the node title, using ImGui::Text) between the
     * following function calls. These functions have to be called before adding any attributes, or the
     * layout of the node will be incorrect.
     */
    @BindingMethod
    public static native void BeginNodeTitleBar();

    @BindingMethod
    public static native void EndNodeTitleBar();

    // Attributes are ImGui UI elements embedded within the node. Attributes can have pin shapes
    // rendered next to them. Links are created between pins.
    //
    // The activity status of an attribute can be checked via the IsAttributeActive() and
    // IsAnyAttributeActive() function calls. This is one easy way of checking for any changes made to
    // an attribute's drag float UI, for instance.
    //
    // Each attribute id must be unique.

    /**
     * Create an input attribute block. The pin is rendered on left side.
     */
    @BindingMethod
    public static native void BeginInputAttribute(int id, @OptArg @ArgValue(staticCast = "ImNodesPinShape") int shape); /*
        ImNodes::BeginInputAttribute(id, (ImNodesPinShape)imNodesPinShape);
    */

    @BindingMethod
    public static native void EndInputAttribute();

    /**
     * Create an output attribute block. The pin is rendered on the right side.
     */
    @BindingMethod
    public static native void BeginOutputAttribute(int id, @OptArg @ArgValue(staticCast = "ImNodesPinShape") int shape);

    @BindingMethod
    public static native void EndOutputAttribute();

    /**
     * Create a static attribute block. A static attribute has no pin, and therefore can't be linked to
     * anything. However, you can still use IsAttributeActive() and IsAnyAttributeActive() to check for
     * attribute activity.
     */
    @BindingMethod
    public static native void BeginStaticAttribute(int id);

    @BindingMethod
    public static native void EndStaticAttribute();

    /**
     * Push a single AttributeFlags value. By default, only AttributeFlags_None is set.
     */
    @BindingMethod
    public static native void PushAttributeFlag(@ArgValue(staticCast = "ImNodesAttributeFlags") int flag);

    @BindingMethod
    public static native void PopAttributeFlag();

    /**
     * Render a link between attributes.
     * The attributes ids used here must match the ids used in Begin(Input|Output)Attribute function
     * calls. The order of source and target doesn't make a difference for rendering the link.
     */
    @BindingMethod
    public static native void Link(int id, int source, int target);

    /**
     * Enable or disable the ability to click and drag a specific node.
     */
    @BindingMethod
    public static native void SetNodeDraggable(int nodeId, boolean draggable);

    // The node's position can be expressed in three coordinate systems:
    // * screen space coordinates, -- the origin is the upper left corner of the window.
    // * editor space coordinates -- the origin is the upper left corner of the node editor window
    // * grid space coordinates, -- the origin is the upper left corner of the node editor window,
    // translated by the current editor panning vector (see EditorContextGetPanning() and
    // EditorContextResetPanning())
    // Use the following functions to get and set the node's coordinates in these coordinate systems.

    @BindingMethod
    public static native void SetNodeScreenSpacePos(int nodeId, ImVec2 screenSpacePos);

    @BindingMethod
    public static native void SetNodeEditorSpacePos(int nodeId, ImVec2 editorSpacePos);

    @BindingMethod
    public static native void SetNodeGridSpacePos(int nodeId, ImVec2 gridPos);
    
    @BindingMethod
    public static native ImVec2 GetNodeScreenSpacePos(int nodeId);

    @BindingMethod
    public static native ImVec2 GetNodeEditorSpacePos(int nodeId);

    @BindingMethod
    public static native ImVec2 GetNodeGridSpacePos(int nodeId);

    /**
     * Enable or disable grid snapping.
     */
    @BindingMethod
    public static native void SnapNodeToGrid(int nodeId);

    /**
     * Returns true if the current node editor canvas is being hovered over by the mouse, and is not
     * blocked by any other windows.
     */
    @BindingMethod
    public static native boolean IsEditorHovered();

    // The following functions return true if a UI element is being hovered over by the mouse cursor.
    // Assigns the id of the UI element being hovered over to the function argument. Use these functions
    // after EndNodeEditor() has been called.

    @BindingMethod
    public static native boolean IsNodeHovered(ImInt nodeId);

    @BindingMethod
    public static native boolean IsLinkHovered(ImInt linkId);

    @BindingMethod
    public static native boolean IsPinHovered(ImInt attributeId);

    // Binding notice: getHoveredNode(), getHoveredLink() and getHoveredPin()
    // return id of the hovered object. If there is no such object -1 will be returned.
    // Use these functions after endNodeEditor() has been called.
    //
    // These methods implemented instead of the original bool ImNodes::IsNodeHovered(int* node_id),
    // bool ImNodes::IsLinkHovered(int* link_id) and bool ImNodes::IsPinHovered(int* attribute_id) for convenience.

    /**
     * @return id of the hovered node or -1 if there is no such object
     */
    public static native int getHoveredNode(); /*
        int i;
        return ImNodes::IsNodeHovered(&i) ? i : -1;
    */

    /**
     * @return id of the hovered link or -1 if there is no such object
     */
    public static native int getHoveredLink(); /*
        int i;
        return ImNodes::IsLinkHovered(&i) ? i : -1;
    */

    /**
     * @return id of the hovered pin or -1 if there is no such object
     */
    public static native int getHoveredPin(); /*
        int i;
        return ImNodes::IsPinHovered(&i) ? i : -1;
    */

    /**
     * Use The following two functions to query the number of selected nodes or links in the current
     * editor. Use after calling EndNodeEditor().
     */
    @BindingMethod
    public static native int NumSelectedNodes();

    @BindingMethod
    public static native int NumSelectedLinks();

    /**
     * Get the selected node/link ids. The pointer argument should point to an integer array with at
     * least as many elements as the respective NumSelectedNodes/NumSelectedLinks function call
     * returned.
     */
    @BindingMethod
    public static native void GetSelectedNodes(int[] nodeIds);

    /**
     * Get the selected node/link ids. The pointer argument should point to an integer array with at
     * least as many elements as the respective NumSelectedNodes/NumSelectedLinks function call
     * returned.
     */
    @BindingMethod
    public static native void GetSelectedLinks(int[] linkIds);

    /**
     * Clears the list of selected nodes/links. Useful if you want to delete a selected node or link.
     */
    @BindingMethod
    public static native void ClearNodeSelection();

    /**
     * Clears the list of selected nodes/links. Useful if you want to delete a selected node or link.
     */
    @BindingMethod
    public static native void ClearLinkSelection();

    /**
     * Manually select a node or link.
     */
    @BindingMethod
    public static native void SelectNode(int nodeId);

    @BindingMethod
    public static native void ClearNodeSelection(int nodeId);

    @BindingMethod
    public static native boolean IsNodeSelected(int nodeId);

    @BindingMethod
    public static native void SelectLink(int linkId);

    @BindingMethod
    public static native void ClearLinkSelection(int linkId);

    @BindingMethod
    public static native boolean IsLinkSelected(int linkId);

    /**
     * Was the previous attribute active? This will continuously return true while the left mouse button
     * is being pressed over the UI content of the attribute.
     */
    @BindingMethod
    public static native boolean IsAttributeActive();

    @BindingMethod
    public static native boolean IsAnyAttributeActive(@OptArg ImInt attributeId);

    /**
     * Binding notice: getActiveAttribute() returns id the active attribute. If there is no active attribute -1 will be returned.
     * <p>
     * This method implemented instead of the original bool ImNodes::IsAnyAttributeActive(int* attribute_id) for convenience.
     */
    public static native int getActiveAttribute(); /*
        int i;
        return ImNodes::IsAnyAttributeActive(&i) ? i : -1;
    */

    // Use the following functions to query a change of state for an existing link, or new link. Call
    // these after EndNodeEditor().

    /**
     * Did the user start dragging a new link from a pin?
     */
    @BindingMethod
    public static native boolean IsLinkStarted(ImInt startedAtAttributeId);

    /**
     * Did the user drop the dragged link before attaching it to a pin?
     * There are two different kinds of situations to consider when handling this event:
     * 1) a link which is created at a pin and then dropped
     * 2) an existing link which is detached from a pin and then dropped
     * Use the including_detached_links flag to control whether this function triggers when the user
     * detaches a link and drops it.
     */
    @BindingMethod
    public static native boolean IsLinkDropped(@OptArg(callValue = "NULL") ImInt startedAtAttributeId, @OptArg boolean includingDetachedLinks);

    /**
     * Did the user finish creating a new link?
     */
    @BindingMethod
    public static native boolean IsLinkCreated(ImInt startedAtAttributeId, ImInt endedAtAttributeId, @OptArg ImBoolean createdFromSnap);

    /**
     * Did the user finish creating a new link?
     */
    @BindingMethod
    public static native boolean IsLinkCreated(ImInt startedAtNodeId, ImInt startedAtAttributeId, ImInt endedAtNodeId, ImInt endedAtAttributeId, @OptArg ImBoolean createdFromSnap);

    /**
     * Was an existing link detached from a pin by the user? The detached link's id is assigned to the
     * output argument link_id.
     */
    @BindingMethod
    public static native boolean IsLinkDestroyed(ImInt linkId);

    // Use the following functions to write the editor context's state to a string, or directly to a
    // file. The editor context is serialized in the INI file format.

    @BindingMethod
    public static native String SaveCurrentEditorStateToIniString();

    @BindingMethod
    public static native String SaveEditorStateToIniString(ImNodesEditorContext editor);

    @BindingMethod
    public static native void LoadCurrentEditorStateFromIniString(String data, int dataSize);

    @BindingMethod
    public static native void LoadEditorStateFromIniString(ImNodesEditorContext editor, String data, int dataSize);

    @BindingMethod
    public static native void SaveCurrentEditorStateToIniFile(String fileName);

    @BindingMethod
    public static native void SaveEditorStateToIniFile(ImNodesEditorContext editor, String fileName);

    @BindingMethod
    public static native void LoadCurrentEditorStateFromIniFile(String fileName);

    @BindingMethod
    public static native void LoadEditorStateFromIniFile(ImNodesEditorContext editor, String fileName);
}
