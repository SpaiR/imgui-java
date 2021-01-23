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
    private static ImNodesStyle style;

    private ImNodes() {
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include <imnodes.h>
        #include "jni_common.h"
     */

    // An editor context corresponds to a set of nodes in a single workspace (created with a single
    // Begin/EndNodeEditor pair)
    //
    // By default, the library creates an editor context behind the scenes, so using any of the imnodes
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
        imnodes::EditorContextSet((imnodes::EditorContext*)ptr);
    */

    /**
     * Initialize the node editor system.
     */
    public static native void initialize(); /*
        imnodes::Initialize();
    */

    public static native void shutdown(); /*
        imnodes::Shutdown();
    */

    /**
     * Returns the global style struct. See the struct declaration for default values.
     */
    public static ImNodesStyle getStyle() {
        if (style == null) {
            style = new ImNodesStyle(nGetStyle());
        }
        return style;
    }

    private static native long nGetStyle(); /*
        return (intptr_t)&imnodes::GetStyle();
    */

    // Style presets matching the dear imgui styles of the same name.

    public static native void styleColorsDark(); /*
        imnodes::StyleColorsDark();
    */

    public static native void styleColorsClassic(); /*
        imnodes::StyleColorsClassic();
    */

    public static native void styleColorsLight(); /*
        imnodes::StyleColorsLight();
    */

    /**
     * Use PushColorStyle and PopColorStyle to modify ImNodesColorStyle mid-frame.
     */
    public static native void pushColorStyle(int imNodesStyleColor, int color); /*
        imnodes::PushColorStyle((imnodes::ColorStyle)imNodesStyleColor, color);
    */

    public static native void popColorStyle(); /*
        imnodes::PopColorStyle();
    */

    public static native void pushStyleVar(int imNodesStyleVar, float value); /*
        imnodes::PushStyleVar((imnodes::StyleVar)imNodesStyleVar, value);
    */

    public static native void popStyleVar(); /*
        imnodes::PopStyleVar();
    */

    /**
     * The top-level function call. Call this before calling BeginNode/EndNode. Calling this function
     * will result the node editor grid workspace being rendered.
     */
    public static native void beginNodeEditor(); /*
        imnodes::BeginNodeEditor();
    */

    public static native void endNodeEditor(); /*
        imnodes::EndNodeEditor();
    */

    public static native void beginNode(int node); /*
        imnodes::BeginNode(node);
    */

    public static native void endNode(); /*
        imnodes::EndNode();
    */

    /**
     * Render a link between attributes.
     * The attributes ids used here must match the ids used in Begin(Input|Output)Attribute function
     * calls. The order of source and target doesn't make a difference for rendering the link.
     */
    public static native void link(int id, int source, int target); /*
        imnodes::Link(id, source, target);
    */

    /**
     * Place your node title bar content (such as the node title, using ImGui::Text) between the
     * following function calls. These functions have to be called before adding any attributes, or the
     * layout of the node will be incorrect.
     */
    public static native void beginNodeTitleBar(); /*
        imnodes::BeginNodeTitleBar();
    */

    public static native void endNodeTitleBar(); /*
        imnodes::EndNodeTitleBar();
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
        imnodes::BeginStaticAttribute(id);
    */

    public static native void endStaticAttribute(); /*
        imnodes::EndStaticAttribute();
    */

    /**
     * Create an input attribute block. The pin is rendered on left side.
     */
    public static void beginInputAttribute(final int id) {
        beginInputAttribute(id, ImNodesPinShape.CircleFilled);
    }

    public static native void beginInputAttribute(int id, int imNodesPinShape); /*
        imnodes::BeginInputAttribute(id, (imnodes::PinShape)imNodesPinShape);
    */

    public static native void endInputAttribute(); /*
        imnodes::EndInputAttribute();
    */

    /**
     * Create an output attribute block. The pin is rendered on the right side.
     */
    public static void beginOutputAttribute(final int id) {
        beginOutputAttribute(id, ImNodesPinShape.CircleFilled);
    }

    public static native void beginOutputAttribute(int id, int imNodesPinShape); /*
        imnodes::BeginOutputAttribute(id, (imnodes::PinShape)imNodesPinShape);
    */

    /**
     * Push a single AttributeFlags value. By default, only AttributeFlags_None is set.
     */
    public static native void pushAttributeFlag(int imNodesAttributeFlags); /*
        imnodes::PushAttributeFlag((imnodes::AttributeFlags)imNodesAttributeFlags);
    */

    public static native void endOutputAttribute(); /*
        imnodes::EndOutputAttribute();
    */

    /**
     * Returns true if the current node editor canvas is being hovered over by the mouse, and is not
     * blocked by any other windows.
     */
    public static native boolean isEditorHovered(); /*
        return imnodes::IsEditorHovered();
    */

    // Binding notice: getHoveredNode(), getHoveredLink() and getHoveredPin()
    // return id of the hovered object. If there is no such object -1 will be returned.
    // Use these functions after endNodeEditor() has been called.
    //
    // These methods implemented instead of the original bool imnodes::IsNodeHovered(int* node_id),
    // bool imnodes::IsLinkHovered(int* link_id) and bool imnodes::IsPinHovered(int* attribute_id) for convenience.

    /**
     * @return id of the hovered node or -1 if there is no such object
     */
    public static native int getHoveredNode(); /*
        int i;
        return imnodes::IsNodeHovered(&i) ? i : -1;
    */

    /**
     * @return id of the hovered link or -1 if there is no such object
     */
    public static native int getHoveredLink(); /*
        int i;
        return imnodes::IsLinkHovered(&i) ? i : -1;
    */

    /**
     * @return id of the hovered pin or -1 if there is no such object
     */
    public static native int getHoveredPin(); /*
        int i;
        return imnodes::IsPinHovered(&i) ? i : -1;
    */

    /**
     * Binding notice: getActiveAttribute() returns id the active attribute. If there is no active attribute -1 will be returned.
     * <p>
     * This method implemented instead of the original bool imnodes::IsAnyAttributeActive(int* attribute_id) for convenience.
     */
    public static native int getActiveAttribute(); /*
        int i;
        return imnodes::IsAnyAttributeActive(&i) ? i : -1;
    */

    /**
     * Was the previous attribute active? This will continuously return true while the left mouse button
     * is being pressed over the UI content of the attribute.
     */
    public static native boolean isAttributeActive(); /*
        return imnodes::IsAttributeActive();
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
        return imnodes::IsLinkStarted(&data[0]);
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
        return imnodes::IsLinkDropped(&data[0], includingDetachedLinks);
    */

    /**
     * Did the user finish creating a new link?
     */
    public static boolean isLinkCreated(final ImInt startedAtAttributeId, final ImInt endedAtAttributeId) {
        return nIsLinkCreated(startedAtAttributeId.getData(), endedAtAttributeId.getData());
    }

    private static native boolean nIsLinkCreated(int[] sourceAttribute, int[] targetAttribute); /*
        return imnodes::IsLinkCreated(&sourceAttribute[0], &targetAttribute[0]);
    */

    public static boolean isLinkCreated(final ImInt startedAtNodeId, final ImInt startedAtAttributeId,
                                        final ImInt endedAtNodeId, final ImInt endedAtAttributeId,
                                        final ImBoolean createdFromSnap) {
        return nIsLinkCreated(startedAtNodeId.getData(), startedAtAttributeId.getData(), endedAtNodeId.getData(), endedAtAttributeId.getData(), createdFromSnap.getData());
    }

    private static native boolean nIsLinkCreated(int[] startedAtNodeId, int[] startedAtAttributeId, int[] endedAtNodeId, int[] endedAtAttributeId, boolean[] createdFromSnap); /*
        return imnodes::IsLinkCreated(&startedAtNodeId[0], &startedAtAttributeId[0], &endedAtNodeId[0], &endedAtAttributeId[0], &createdFromSnap[0]);
    */

    /**
     * Was an existing link detached from a pin by the user? The detached link's id is assigned to the
     * output argument link_id.
     */
    public static boolean isLinkDestroyed(final ImInt linkId) {
        return nIsLinkDestroyed(linkId.getData());
    }

    private static native boolean nIsLinkDestroyed(int[] linkId); /*
        return imnodes::IsLinkDestroyed(&linkId[0]);
    */

    /**
     * Use The following two functions to query the number of selected nodes or links in the current
     * editor. Use after calling EndNodeEditor().
     */
    public static native int numSelectedNodes(); /*
        return imnodes::NumSelectedNodes();
    */

    public static native int numSelectedLinks(); /*
        return imnodes::NumSelectedLinks();
    */

    /**
     * Get the selected node/link ids. The pointer argument should point to an integer array with at
     * least as many elements as the respective NumSelectedNodes/NumSelectedLinks function call
     * returned.
     */
    public static native void getSelectedNodes(int[] nodeIds); /*
        imnodes::GetSelectedNodes(&nodeIds[0]);
    */

    public static native void getSelectedLinks(int[] linkIds); /*
        imnodes::GetSelectedLinks(&linkIds[0]);
    */

    /**
     * Clears the list of selected nodes/links. Useful if you want to delete a selected node or link.
     */
    public static native void clearNodeSelection(); /*
        imnodes::ClearNodeSelection();
    */

    public static native void clearLinkSelection(); /*
        imnodes::ClearLinkSelection();
    */

    /**
     * Enable or disable the ability to click and drag a specific node.
     */
    public static native void setNodeDraggable(int node, boolean isDraggable); /*
        imnodes::SetNodeDraggable(node, isDraggable);
    */

    public static native void getNodeDimensions(int node, ImVec2 result); /*
        ImVec2 dst = imnodes::GetNodeDimensions(node);
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native float getNodeDimensionsX(int node); /*
        return imnodes::GetNodeDimensions(node).x;
    */

    public static native float getNodeDimensionsY(int node); /*
        return imnodes::GetNodeDimensions(node).y;
    */


    // The node's position can be expressed in three coordinate systems:
    // * screen space coordinates, -- the origin is the upper left corner of the window.
    // * editor space coordinates -- the origin is the upper left corner of the node editor window
    // * grid space coordinates, -- the origin is the upper left corner of the node editor window,
    // translated by the current editor panning vector (see EditorContextGetPanning() and
    // EditorContextResetPanning())
    // Use the following functions to get and set the node's coordinates in these coordinate systems.

    public static native void setNodeScreenSpacePos(int node, float x, float y); /*
        imnodes::SetNodeScreenSpacePos(node, ImVec2(x, y));
    */

    public static native void setNodeEditorSpacePos(int node, float x, float y); /*
        imnodes::SetNodeEditorSpacePos(node, ImVec2(x, y));
    */

    public static native void setNodeGridSpacePos(int node, float x, float y); /*
        imnodes::SetNodeGridSpacePos(node, ImVec2(x, y));
    */

    public static native void getNodeScreenSpacePos(int node, ImVec2 result); /*
        ImVec2 dst = imnodes::GetNodeScreenSpacePos(node);
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native float getNodeScreenSpacePosX(int node); /*
        return imnodes::GetNodeScreenSpacePos(node).x;
    */

    public static native float getNodeScreenSpacePosY(int node); /*
        return imnodes::GetNodeScreenSpacePos(node).y;
    */

    public static native void getNodeEditorSpacePos(int node, ImVec2 result); /*
        ImVec2 dst = imnodes::GetNodeEditorSpacePos(node);
        Jni::ImVec2Cpy(env, &dst, result);
    */

    public static native float getNodeEditorSpacePosX(int node); /*
        return imnodes::GetNodeEditorSpacePos(node).x;
    */

    public static native float getNodeEditorSpacePosY(int node); /*
        return imnodes::GetNodeEditorSpacePos(node).y;
    */

    public static native void getNodeGridSpacePos(int node, ImVec2 dst); /*
        ImVec2 result = imnodes::GetNodeGridSpacePos(node);
        Jni::ImVec2Cpy(env, &result, dst);
    */

    public static native float getNodeGridSpacePosX(int node); /*
        return imnodes::GetNodeGridSpacePos(node).x;
    */

    public static native float getNodeGridSpacePosY(int node); /*
        return imnodes::GetNodeGridSpacePos(node).y;
    */

    public static native void editorResetPanning(float x, float y); /*
        imnodes::EditorContextResetPanning(ImVec2(x, y));
    */

    public static native void editorMoveToNode(int node); /*
        imnodes::EditorContextMoveToNode(node);
    */

    // Use the following functions to write the editor context's state to a string, or directly to a
    // file. The editor context is serialized in the INI file format.

    public static native String saveCurrentEditorStateToIniString(); /*
        return env->NewStringUTF(imnodes::SaveCurrentEditorStateToIniString(NULL));
    */

    public static String saveEditorStateToIniString(final ImNodesContext context) {
        return nSaveEditorStateToIniString(context.ptr);
    }

    private static native String nSaveEditorStateToIniString(long context); /*
        return env->NewStringUTF(imnodes::SaveEditorStateToIniString((imnodes::EditorContext*)context, NULL));
    */

    public static native void loadCurrentEditorStateFromIniString(String data, int dataSize); /*
        imnodes::LoadCurrentEditorStateFromIniString(data, dataSize);
    */

    public static void loadEditorStateFromIniString(final ImNodesContext context, final String data, final int dataSize) {
        nLoadEditorStateFromIniString(context.ptr, data, dataSize);
    }

    private static native void nLoadEditorStateFromIniString(long context, String data, int dataSize); /*
        imnodes::LoadEditorStateFromIniString((imnodes::EditorContext*)context, data, dataSize);
    */

    public static native void saveCurrentEditorStateToIniFile(String fileName); /*
        imnodes::SaveCurrentEditorStateToIniFile(fileName);
    */

    public static void saveEditorStateToIniFile(final ImNodesContext context, final String fileName) {
        nSaveEditorStateToIniFile(context.ptr, fileName);
    }

    private static native void nSaveEditorStateToIniFile(long context, String fileName); /*
        imnodes::SaveEditorStateToIniFile((imnodes::EditorContext*)context, fileName);
    */

    public static native void loadCurrentEditorStateFromIniFile(String fileName); /*
        imnodes::LoadCurrentEditorStateFromIniFile(fileName);
    */

    public static void loadEditorStateFromIniFile(final ImNodesContext context, final String fileName) {
        nLoadEditorStateFromIniFile(context.ptr, fileName);
    }

    private static native void nLoadEditorStateFromIniFile(long context, String fileName); /*
        imnodes::LoadEditorStateFromIniFile((imnodes::EditorContext*)context, fileName);
    */
}
