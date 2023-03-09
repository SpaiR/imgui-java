package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.extension.imnodes.flag.ImNodesPinShape;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

/**
 * Bindings for Imnodes (https://github.com/Nelarius/imnodes/)
 * Original library author - Johann Muszynski (https://github.com/Nelarius)
 * <p>
 * Refer to the library's Github page for examples and support
 */
public final class ImNodes {
    private static final ImNodesStyle STYLE = new ImNodesStyle(0);

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

    public static ImNodesContext editorContextCreate() {
        return new ImNodesContext();
    }

    public static void editorContextFree(final ImNodesContext context) {
        context.destroy();
    }

    public static void editorContextSet(final ImNodesContext context) {
        nEditorContextSet(context.ptr);
    }

    private static native void nEditorContextSet(long ptr); /*
        ImNodes::EditorContextSet((ImNodesEditorContext*)ptr);
    */

    /**
     * Initialize the node editor system.
     */
    public static native void createContext(); /*
        ImNodes::CreateContext();
    */

    public static native void destroyContext(); /*
        ImNodes::DestroyContext();
    */

    /**
     * Returns the global style struct. See the struct declaration for default values.
     */
    public static ImNodesStyle getStyle() {
        STYLE.ptr = nGetStyle();
        return STYLE;
    }

    private static native long nGetStyle(); /*
        return (intptr_t)&ImNodes::GetStyle();
    */

    // Style presets matching the dear imgui styles of the same name.

    public static native void styleColorsDark(); /*
        ImNodes::StyleColorsDark();
    */

    public static native void styleColorsClassic(); /*
        ImNodes::StyleColorsClassic();
    */

    public static native void styleColorsLight(); /*
        ImNodes::StyleColorsLight();
    */

    /**
     * Use PushColorStyle and PopColorStyle to modify ImNodesColorStyle mid-frame.
     */
    public static native void pushColorStyle(int imNodesStyleColor, int color); /*
        ImNodes::PushColorStyle((ImNodesCol)imNodesStyleColor, color);
    */

    public static native void popColorStyle(); /*
        ImNodes::PopColorStyle();
    */

    public static native void pushStyleVar(int imNodesStyleVar, float value); /*
        ImNodes::PushStyleVar((ImNodesStyleVar)imNodesStyleVar, value);
    */

    public static native void pushStyleVar(int imNodesStyleVar, float x, float y); /*
        ImNodes::PushStyleVar((ImNodesStyleVar)imNodesStyleVar, ImVec2(x, y));
    */

    public static native void popStyleVar(); /*
        ImNodes::PopStyleVar();
    */

    /**
     * The top-level function call. Call this before calling BeginNode/EndNode. Calling this function
     * will result the node editor grid workspace being rendered.
     */
    public static native void beginNodeEditor(); /*
        ImNodes::BeginNodeEditor();
    */

    public static native void endNodeEditor(); /*
        ImNodes::EndNodeEditor();
    */

    public static native void beginNode(int node); /*
        ImNodes::BeginNode(node);
    */

    public static native void endNode(); /*
        ImNodes::EndNode();
    */

    /**
     * Render a link between attributes.
     * The attributes ids used here must match the ids used in Begin(Input|Output)Attribute function
     * calls. The order of source and target doesn't make a difference for rendering the link.
     */
    public static native void link(int id, int source, int target); /*
        ImNodes::Link(id, source, target);
    */

    /**
     * Place your node title bar content (such as the node title, using ImGui::Text) between the
     * following function calls. These functions have to be called before adding any attributes, or the
     * layout of the node will be incorrect.
     */
    public static native void beginNodeTitleBar(); /*
        ImNodes::BeginNodeTitleBar();
    */

    public static native void endNodeTitleBar(); /*
        ImNodes::EndNodeTitleBar();
    */

    // Attributes are ImGui UI elements embedded within the node. Attributes can have pin shapes
    // rendered next to them. Links are created between pins.
    //
    // The activity status of an attribute can be checked via the IsAttributeActive() and
    // IsAnyAttributeActive() function calls. This is one easy way of checking for any changes made to
    // an attribute's drag float UI, for instance.
    //
    // Each attribute id must be unique.

    /**
     * Create a static attribute block. A static attribute has no pin, and therefore can't be linked to
     * anything. However, you can still use IsAttributeActive() and IsAnyAttributeActive() to check for
     * attribute activity.
     */
    public static native void beginStaticAttribute(int id); /*
        ImNodes::BeginStaticAttribute(id);
    */

    public static native void endStaticAttribute(); /*
        ImNodes::EndStaticAttribute();
    */

    /**
     * Create an input attribute block. The pin is rendered on left side.
     */
    public static void beginInputAttribute(final int id) {
        beginInputAttribute(id, ImNodesPinShape.CircleFilled);
    }

    public static native void beginInputAttribute(int id, int imNodesPinShape); /*
        ImNodes::BeginInputAttribute(id, (ImNodesPinShape)imNodesPinShape);
    */

    public static native void endInputAttribute(); /*
        ImNodes::EndInputAttribute();
    */

    /**
     * Create an output attribute block. The pin is rendered on the right side.
     */
    public static void beginOutputAttribute(final int id) {
        beginOutputAttribute(id, ImNodesPinShape.CircleFilled);
    }

    public static native void beginOutputAttribute(int id, int imNodesPinShape); /*
        ImNodes::BeginOutputAttribute(id, (ImNodesPinShape)imNodesPinShape);
    */

    /**
     * Push a single AttributeFlags value. By default, only AttributeFlags_None is set.
     */
    public static native void pushAttributeFlag(int imNodesAttributeFlags); /*
        ImNodes::PushAttributeFlag((ImNodesAttributeFlags)imNodesAttributeFlags);
    */

    public static native void endOutputAttribute(); /*
        ImNodes::EndOutputAttribute();
    */

    /**
     * Returns true if the current node editor canvas is being hovered over by the mouse, and is not
     * blocked by any other windows.
     */
    public static native boolean isEditorHovered(); /*
        return ImNodes::IsEditorHovered();
    */

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
     * Binding notice: getActiveAttribute() returns id the active attribute. If there is no active attribute -1 will be returned.
     * <p>
     * This method implemented instead of the original bool ImNodes::IsAnyAttributeActive(int* attribute_id) for convenience.
     */
    public static native int getActiveAttribute(); /*
        int i;
        return ImNodes::IsAnyAttributeActive(&i) ? i : -1;
    */

    /**
     * Was the previous attribute active? This will continuously return true while the left mouse button
     * is being pressed over the UI content of the attribute.
     */
    public static native boolean isAttributeActive(); /*
        return ImNodes::IsAttributeActive();
    */

    // Use the following functions to query a change of state for an existing link, or new link. Call
    // these after EndNodeEditor().

    /**
     * Did the user start dragging a new link from a pin?
     */
    public static boolean isLinkStarted(final ImInt startAtAttributeId) {
        return nIsLinkStarted(startAtAttributeId.getData());
    }

    private static native boolean nIsLinkStarted(int[] data); /*
        return ImNodes::IsLinkStarted(&data[0]);
    */

    /**
     * Did the user drop the dragged link before attaching it to a pin?
     * There are two different kinds of situations to consider when handling this event:
     * 1) a link which is created at a pin and then dropped
     * 2) an existing link which is detached from a pin and then dropped
     * Use the including_detached_links flag to control whether this function triggers when the user
     * detaches a link and drops it.
     */
    public static boolean isLinkDropped(final ImInt startedAtAttributeId, final boolean includingDetachedLinks) {
        return nIsLinkDropped(startedAtAttributeId.getData(), includingDetachedLinks);
    }

    private static native boolean nIsLinkDropped(int[] data, boolean includingDetachedLinks); /*
        return ImNodes::IsLinkDropped(&data[0], includingDetachedLinks);
    */

    /**
     * Did the user finish creating a new link?
     */
    public static boolean isLinkCreated(final ImInt startedAtAttributeId, final ImInt endedAtAttributeId) {
        return nIsLinkCreated(startedAtAttributeId.getData(), endedAtAttributeId.getData());
    }

    private static native boolean nIsLinkCreated(int[] sourceAttribute, int[] targetAttribute); /*
        return ImNodes::IsLinkCreated(&sourceAttribute[0], &targetAttribute[0]);
    */

    public static boolean isLinkCreated(final ImInt startedAtNodeId, final ImInt startedAtAttributeId,
                                        final ImInt endedAtNodeId, final ImInt endedAtAttributeId,
                                        final ImBoolean createdFromSnap) {
        return nIsLinkCreated(startedAtNodeId.getData(), startedAtAttributeId.getData(), endedAtNodeId.getData(), endedAtAttributeId.getData(), createdFromSnap.getData());
    }

    private static native boolean nIsLinkCreated(int[] startedAtNodeId, int[] startedAtAttributeId, int[] endedAtNodeId, int[] endedAtAttributeId, boolean[] createdFromSnap); /*
        return ImNodes::IsLinkCreated(&startedAtNodeId[0], &startedAtAttributeId[0], &endedAtNodeId[0], &endedAtAttributeId[0], &createdFromSnap[0]);
    */

    /**
     * Was an existing link detached from a pin by the user? The detached link's id is assigned to the
     * output argument link_id.
     */
    public static boolean isLinkDestroyed(final ImInt linkId) {
        return nIsLinkDestroyed(linkId.getData());
    }

    private static native boolean nIsLinkDestroyed(int[] linkId); /*
        return ImNodes::IsLinkDestroyed(&linkId[0]);
    */

    /**
     * Use The following two functions to query the number of selected nodes or links in the current
     * editor. Use after calling EndNodeEditor().
     */
    public static native int numSelectedNodes(); /*
        return ImNodes::NumSelectedNodes();
    */

    public static native int numSelectedLinks(); /*
        return ImNodes::NumSelectedLinks();
    */

    /**
     * Get the selected node/link ids. The pointer argument should point to an integer array with at
     * least as many elements as the respective NumSelectedNodes/NumSelectedLinks function call
     * returned.
     */
    public static native void getSelectedNodes(int[] nodeIds); /*
        ImNodes::GetSelectedNodes(&nodeIds[0]);
    */

    public static native void getSelectedLinks(int[] linkIds); /*
        ImNodes::GetSelectedLinks(&linkIds[0]);
    */

    /**
     * Clears the list of selected nodes/links. Useful if you want to delete a selected node or link.
     */
    public static native void clearNodeSelection(); /*
        ImNodes::ClearNodeSelection();
    */

    public static native void clearNodeSelection(int node); /*
        ImNodes::ClearNodeSelection(node);
    */

    public static native void clearLinkSelection(); /*
        ImNodes::ClearLinkSelection();
    */

    public static native void clearLinkSelection(int link); /*
        ImNodes::ClearLinkSelection(link);
    */

    /**
     * Manually select a node or link.
     */
    public static native void selectNode(int node); /*
        ImNodes::SelectNode(node);
    */

    public static native void selectLink(int link); /*
        ImNodes::SelectLink(link);
    */

     /**
     * Check if a a specified node/link is selected.
     */
    public static native boolean isNodeSelected(int node); /*
        return ImNodes::IsNodeSelected(node);
    */

    public static native boolean isLinkSelected(int link); /*
        return ImNodes::IsLinkSelected(link);
    */

    /**
     * Enable or disable the ability to click and drag a specific node.
     */
    public static native void setNodeDraggable(int node, boolean isDraggable); /*
        ImNodes::SetNodeDraggable(node, isDraggable);
    */

    public static native void getNodeDimensions(int node, ImVec2 result); /*
        ImVec2 dst = ImNodes::GetNodeDimensions(node);
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native float getNodeDimensionsX(int node); /*
        return ImNodes::GetNodeDimensions(node).x;
    */

    public static native float getNodeDimensionsY(int node); /*
        return ImNodes::GetNodeDimensions(node).y;
    */


    // The node's position can be expressed in three coordinate systems:
    // * screen space coordinates, -- the origin is the upper left corner of the window.
    // * editor space coordinates -- the origin is the upper left corner of the node editor window
    // * grid space coordinates, -- the origin is the upper left corner of the node editor window,
    // translated by the current editor panning vector (see EditorContextGetPanning() and
    // EditorContextResetPanning())
    // Use the following functions to get and set the node's coordinates in these coordinate systems.

    public static native void setNodeScreenSpacePos(int node, float x, float y); /*
        ImNodes::SetNodeScreenSpacePos(node, ImVec2(x, y));
    */

    public static native void setNodeEditorSpacePos(int node, float x, float y); /*
        ImNodes::SetNodeEditorSpacePos(node, ImVec2(x, y));
    */

    public static native void setNodeGridSpacePos(int node, float x, float y); /*
        ImNodes::SetNodeGridSpacePos(node, ImVec2(x, y));
    */

    public static native void getNodeScreenSpacePos(int node, ImVec2 result); /*
        ImVec2 dst = ImNodes::GetNodeScreenSpacePos(node);
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native float getNodeScreenSpacePosX(int node); /*
        return ImNodes::GetNodeScreenSpacePos(node).x;
    */

    public static native float getNodeScreenSpacePosY(int node); /*
        return ImNodes::GetNodeScreenSpacePos(node).y;
    */

    public static native void getNodeEditorSpacePos(int node, ImVec2 result); /*
        ImVec2 dst = ImNodes::GetNodeEditorSpacePos(node);
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native float getNodeEditorSpacePosX(int node); /*
        return ImNodes::GetNodeEditorSpacePos(node).x;
    */

    public static native float getNodeEditorSpacePosY(int node); /*
        return ImNodes::GetNodeEditorSpacePos(node).y;
    */

    public static native void getNodeGridSpacePos(int node, ImVec2 dst); /*
        ImVec2 result = ImNodes::GetNodeGridSpacePos(node);
        Jni::ImVec2Cpy(env, &result, dst);
    */

    public static native float getNodeGridSpacePosX(int node); /*
        return ImNodes::GetNodeGridSpacePos(node).x;
    */

    public static native float getNodeGridSpacePosY(int node); /*
        return ImNodes::GetNodeGridSpacePos(node).y;
    */

    public static native void editorResetPanning(float x, float y); /*
        ImNodes::EditorContextResetPanning(ImVec2(x, y));
    */

    public static native void editorContextGetPanning(ImVec2 result); /*
        ImVec2 dst = ImNodes::EditorContextGetPanning();
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native void editorMoveToNode(int node); /*
        ImNodes::EditorContextMoveToNode(node);
    */

    // Use the following functions to write the editor context's state to a string, or directly to a
    // file. The editor context is serialized in the INI file format.

    public static native String saveCurrentEditorStateToIniString(); /*
        return env->NewStringUTF(ImNodes::SaveCurrentEditorStateToIniString(NULL));
    */

    public static String saveEditorStateToIniString(final ImNodesContext context) {
        return nSaveEditorStateToIniString(context.ptr);
    }

    private static native String nSaveEditorStateToIniString(long context); /*
        return env->NewStringUTF(ImNodes::SaveEditorStateToIniString((ImNodesEditorContext*)context, NULL));
    */

    public static native void loadCurrentEditorStateFromIniString(String data, int dataSize); /*
        ImNodes::LoadCurrentEditorStateFromIniString(data, dataSize);
    */

    public static void loadEditorStateFromIniString(final ImNodesContext context, final String data, final int dataSize) {
        nLoadEditorStateFromIniString(context.ptr, data, dataSize);
    }

    private static native void nLoadEditorStateFromIniString(long context, String data, int dataSize); /*
        ImNodes::LoadEditorStateFromIniString((ImNodesEditorContext*)context, data, dataSize);
    */

    public static native void saveCurrentEditorStateToIniFile(String fileName); /*
        ImNodes::SaveCurrentEditorStateToIniFile(fileName);
    */

    public static void saveEditorStateToIniFile(final ImNodesContext context, final String fileName) {
        nSaveEditorStateToIniFile(context.ptr, fileName);
    }

    private static native void nSaveEditorStateToIniFile(long context, String fileName); /*
        ImNodes::SaveEditorStateToIniFile((ImNodesEditorContext*)context, fileName);
    */

    public static native void loadCurrentEditorStateFromIniFile(String fileName); /*
        ImNodes::LoadCurrentEditorStateFromIniFile(fileName);
    */

    public static void loadEditorStateFromIniFile(final ImNodesContext context, final String fileName) {
        nLoadEditorStateFromIniFile(context.ptr, fileName);
    }

    private static native void nLoadEditorStateFromIniFile(long context, String fileName); /*
        ImNodes::LoadEditorStateFromIniFile((ImNodesEditorContext*)context, fileName);
    */

    public static native void miniMap(float miniMapSizeFraction, int miniMapLocation); /*
        ImNodes::MiniMap(miniMapSizeFraction, (ImNodesMiniMapLocation)miniMapLocation);
    */
}
