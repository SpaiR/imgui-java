package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

/**
 * Bindings for Imnodes (https://github.com/Nelarius/imnodes/)
 * Original library author - Johann Muszynski (https://github.com/Nelarius)
 * <p>
 * Refer to the library's Github page for examples and support
 */
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

    public static void setImGuiContext(final ImGuiContext ctx) {
        nSetImGuiContext(ctx.ptr);
    }

    private static native void nSetImGuiContext(long ctx); /*
        ImNodes::SetImGuiContext(reinterpret_cast<ImGuiContext*>(ctx));
    */

    public static ImNodesContext createContext() {
        return new ImNodesContext(nCreateContext());
    }

    private static native long nCreateContext(); /*
        return (uintptr_t)ImNodes::CreateContext();
    */

    public static void destroyContext() {
        nDestroyContext();
    }

    public static void destroyContext(final ImNodesContext ctx) {
        nDestroyContext(ctx.ptr);
    }

    private static native void nDestroyContext(); /*
        ImNodes::DestroyContext();
    */

    private static native void nDestroyContext(long ctx); /*
        ImNodes::DestroyContext(reinterpret_cast<ImNodesContext*>(ctx));
    */

    public static ImNodesContext getCurrentContext() {
        return new ImNodesContext(nGetCurrentContext());
    }

    private static native long nGetCurrentContext(); /*
        return (uintptr_t)ImNodes::GetCurrentContext();
    */

    public static void setCurrentContext(final ImNodesContext ctx) {
        nSetCurrentContext(ctx.ptr);
    }

    private static native void nSetCurrentContext(long ctx); /*
        ImNodes::SetCurrentContext(reinterpret_cast<ImNodesContext*>(ctx));
    */

    public static ImNodesEditorContext editorContextCreate() {
        return new ImNodesEditorContext(nEditorContextCreate());
    }

    private static native long nEditorContextCreate(); /*
        return (uintptr_t)ImNodes::EditorContextCreate();
    */

    public static void editorContextFree(final ImNodesEditorContext context) {
        nEditorContextFree(context.ptr);
    }

    private static native void nEditorContextFree(long context); /*
        ImNodes::EditorContextFree(reinterpret_cast<ImNodesEditorContext*>(context));
    */

    public static void editorContextSet(final ImNodesEditorContext context) {
        nEditorContextSet(context.ptr);
    }

    private static native void nEditorContextSet(long context); /*
        ImNodes::EditorContextSet(reinterpret_cast<ImNodesEditorContext*>(context));
    */

    public static ImVec2 editorContextGetPanning() {
        final ImVec2 dst = new ImVec2();
        nEditorContextGetPanning(dst);
        return dst;
    }

    public static float editorContextGetPanningX() {
        return nEditorContextGetPanningX();
    }

    public static float editorContextGetPanningY() {
        return nEditorContextGetPanningY();
    }

    public static void editorContextGetPanning(final ImVec2 dst) {
        nEditorContextGetPanning(dst);
    }

    private static native void nEditorContextGetPanning(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ImNodes::EditorContextGetPanning(), dst);
    */

    private static native float nEditorContextGetPanningX(); /*
        return ImNodes::EditorContextGetPanning().x;
    */

    private static native float nEditorContextGetPanningY(); /*
        return ImNodes::EditorContextGetPanning().y;
    */

    public static void editorContextResetPanning(final ImVec2 pos) {
        nEditorContextResetPanning(pos.x, pos.y);
    }

    public static void editorContextResetPanning(final float posX, final float posY) {
        nEditorContextResetPanning(posX, posY);
    }

    private static native void nEditorContextResetPanning(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        ImNodes::EditorContextResetPanning(pos);
    */

    public static void editorContextMoveToNode(final int nodeId) {
        nEditorContextMoveToNode(nodeId);
    }

    private static native void nEditorContextMoveToNode(int nodeId); /*
        ImNodes::EditorContextMoveToNode(nodeId);
    */

    private static final ImNodesIO _GETIO_1 = new ImNodesIO(0);

    public static ImNodesIO getIO() {
        _GETIO_1.ptr = nGetIO();
        return _GETIO_1;
    }

    private static native long nGetIO(); /*
        return (uintptr_t)&ImNodes::GetIO();
    */

    private static final ImNodesStyle _GETSTYLE_1 = new ImNodesStyle(0);

    /**
     * Returns the global style struct. See the struct declaration for default values.
     */
    public static ImNodesStyle getStyle() {
        _GETSTYLE_1.ptr = nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle(); /*
        return (uintptr_t)&ImNodes::GetStyle();
    */

    // Style presets matching the dear imgui styles of the same name.

    public static void styleColorsDark() {
        nStyleColorsDark();
    }

    public static void styleColorsDark(final ImNodesStyle style) {
        nStyleColorsDark(style.ptr);
    }

    private static native void nStyleColorsDark(); /*
        ImNodes::StyleColorsDark();
    */

    private static native void nStyleColorsDark(long style); /*
        ImNodes::StyleColorsDark(reinterpret_cast<ImNodesStyle*>(style));
    */

    public static void styleColorsClassic() {
        nStyleColorsClassic();
    }

    public static void styleColorsClassic(final ImNodesStyle style) {
        nStyleColorsClassic(style.ptr);
    }

    private static native void nStyleColorsClassic(); /*
        ImNodes::StyleColorsClassic();
    */

    private static native void nStyleColorsClassic(long style); /*
        ImNodes::StyleColorsClassic(reinterpret_cast<ImNodesStyle*>(style));
    */

    public static void styleColorsLight() {
        nStyleColorsLight();
    }

    public static void styleColorsLight(final ImNodesStyle style) {
        nStyleColorsLight(style.ptr);
    }

    private static native void nStyleColorsLight(); /*
        ImNodes::StyleColorsLight();
    */

    private static native void nStyleColorsLight(long style); /*
        ImNodes::StyleColorsLight(reinterpret_cast<ImNodesStyle*>(style));
    */

    /**
     * The top-level function call. Call this before calling BeginNode/EndNode. Calling this function
     * will result the node editor grid workspace being rendered.
     */
    public static void beginNodeEditor() {
        nBeginNodeEditor();
    }

    private static native void nBeginNodeEditor(); /*
        ImNodes::BeginNodeEditor();
    */

    public static void endNodeEditor() {
        nEndNodeEditor();
    }

    private static native void nEndNodeEditor(); /*
        ImNodes::EndNodeEditor();
    */

    /**
     * Add a navigable minimap to the editor; call before EndNodeEditor after all nodes and links have been specified
     * // TODO: add callback
     */
    public static void miniMap() {
        nMiniMap();
    }

    /**
     * Add a navigable minimap to the editor; call before EndNodeEditor after all nodes and links have been specified
     * // TODO: add callback
     */
    public static void miniMap(final float miniMapSizeFraction) {
        nMiniMap(miniMapSizeFraction);
    }

    /**
     * Add a navigable minimap to the editor; call before EndNodeEditor after all nodes and links have been specified
     * // TODO: add callback
     */
    public static void miniMap(final float miniMapSizeFraction, final int miniMapLocation) {
        nMiniMap(miniMapSizeFraction, miniMapLocation);
    }

    private static native void nMiniMap(); /*
        ImNodes::MiniMap();
    */

    private static native void nMiniMap(float miniMapSizeFraction); /*
        ImNodes::MiniMap(miniMapSizeFraction);
    */

    private static native void nMiniMap(float miniMapSizeFraction, int miniMapLocation); /*
        ImNodes::MiniMap(miniMapSizeFraction, static_cast<ImNodesMiniMapLocation>(miniMapLocation));
    */

    /**
     * Use PushColorStyle and PopColorStyle to modify ImNodesColorStyle mid-frame.
     */
    public static void pushColorStyle(final int item, final int color) {
        nPushColorStyle(item, color);
    }

    private static native void nPushColorStyle(int item, int color); /*
        ImNodes::PushColorStyle(static_cast<ImNodesCol>(item), color);
    */

    public static void popColorStyle() {
        nPopColorStyle();
    }

    private static native void nPopColorStyle(); /*
        ImNodes::PopColorStyle();
    */

    public static void pushStyleVar(final int styleItem, final float value) {
        nPushStyleVar(styleItem, value);
    }

    private static native void nPushStyleVar(int styleItem, float value); /*
        ImNodes::PushStyleVar(static_cast<ImNodesStyleVar>(styleItem), value);
    */

    public static void pushStyleVar(final int styleItem, final ImVec2 value) {
        nPushStyleVar(styleItem, value.x, value.y);
    }

    public static void pushStyleVar(final int styleItem, final float valueX, final float valueY) {
        nPushStyleVar(styleItem, valueX, valueY);
    }

    private static native void nPushStyleVar(int styleItem, float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        ImNodes::PushStyleVar(static_cast<ImNodesStyleVar>(styleItem), value);
    */

    public static void popStyleVar() {
        nPopStyleVar();
    }

    private static native void nPopStyleVar(); /*
        ImNodes::PopStyleVar();
    */

    public static void beginNode(final int node) {
        nBeginNode(node);
    }

    private static native void nBeginNode(int node); /*
        ImNodes::BeginNode(node);
    */

    public static void endNode() {
        nEndNode();
    }

    private static native void nEndNode(); /*
        ImNodes::EndNode();
    */

    public static ImVec2 getNodeDimensions(final int id) {
        final ImVec2 dst = new ImVec2();
        nGetNodeDimensions(dst, id);
        return dst;
    }

    public static float getNodeDimensionsX(final int id) {
        return nGetNodeDimensionsX(id);
    }

    public static float getNodeDimensionsY(final int id) {
        return nGetNodeDimensionsY(id);
    }

    public static void getNodeDimensions(final ImVec2 dst, final int id) {
        nGetNodeDimensions(dst, id);
    }

    private static native void nGetNodeDimensions(ImVec2 dst, int id); /*
        Jni::ImVec2Cpy(env, ImNodes::GetNodeDimensions(id), dst);
    */

    private static native float nGetNodeDimensionsX(int id); /*
        return ImNodes::GetNodeDimensions(id).x;
    */

    private static native float nGetNodeDimensionsY(int id); /*
        return ImNodes::GetNodeDimensions(id).y;
    */

    /**
     * Place your node title bar content (such as the node title, using ImGui::Text) between the
     * following function calls. These functions have to be called before adding any attributes, or the
     * layout of the node will be incorrect.
     */
    public static void beginNodeTitleBar() {
        nBeginNodeTitleBar();
    }

    private static native void nBeginNodeTitleBar(); /*
        ImNodes::BeginNodeTitleBar();
    */

    public static void endNodeTitleBar() {
        nEndNodeTitleBar();
    }

    private static native void nEndNodeTitleBar(); /*
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
     * Create an input attribute block. The pin is rendered on left side.
     */
    public static void beginInputAttribute(final int id) {
        nBeginInputAttribute(id);
    }

    /**
     * Create an input attribute block. The pin is rendered on left side.
     */
    public static void beginInputAttribute(final int id, final int shape) {
        nBeginInputAttribute(id, shape);
    }

    private static native void nBeginInputAttribute(int id); /*
        ImNodes::BeginInputAttribute(id);
    */

    private static native void nBeginInputAttribute(int id, int shape); /*
        ImNodes::BeginInputAttribute(id, static_cast<ImNodesPinShape>(shape));
    */ /*
        ImNodes::BeginInputAttribute(id, (ImNodesPinShape)imNodesPinShape);
    */

    public static void endInputAttribute() {
        nEndInputAttribute();
    }

    private static native void nEndInputAttribute(); /*
        ImNodes::EndInputAttribute();
    */

    /**
     * Create an output attribute block. The pin is rendered on the right side.
     */
    public static void beginOutputAttribute(final int id) {
        nBeginOutputAttribute(id);
    }

    /**
     * Create an output attribute block. The pin is rendered on the right side.
     */
    public static void beginOutputAttribute(final int id, final int shape) {
        nBeginOutputAttribute(id, shape);
    }

    private static native void nBeginOutputAttribute(int id); /*
        ImNodes::BeginOutputAttribute(id);
    */

    private static native void nBeginOutputAttribute(int id, int shape); /*
        ImNodes::BeginOutputAttribute(id, static_cast<ImNodesPinShape>(shape));
    */

    public static void endOutputAttribute() {
        nEndOutputAttribute();
    }

    private static native void nEndOutputAttribute(); /*
        ImNodes::EndOutputAttribute();
    */

    /**
     * Create a static attribute block. A static attribute has no pin, and therefore can't be linked to
     * anything. However, you can still use IsAttributeActive() and IsAnyAttributeActive() to check for
     * attribute activity.
     */
    public static void beginStaticAttribute(final int id) {
        nBeginStaticAttribute(id);
    }

    private static native void nBeginStaticAttribute(int id); /*
        ImNodes::BeginStaticAttribute(id);
    */

    public static void endStaticAttribute() {
        nEndStaticAttribute();
    }

    private static native void nEndStaticAttribute(); /*
        ImNodes::EndStaticAttribute();
    */

    /**
     * Push a single AttributeFlags value. By default, only AttributeFlags_None is set.
     */
    public static void pushAttributeFlag(final int flag) {
        nPushAttributeFlag(flag);
    }

    private static native void nPushAttributeFlag(int flag); /*
        ImNodes::PushAttributeFlag(static_cast<ImNodesAttributeFlags>(flag));
    */

    public static void popAttributeFlag() {
        nPopAttributeFlag();
    }

    private static native void nPopAttributeFlag(); /*
        ImNodes::PopAttributeFlag();
    */

    /**
     * Render a link between attributes.
     * The attributes ids used here must match the ids used in Begin(Input|Output)Attribute function
     * calls. The order of source and target doesn't make a difference for rendering the link.
     */
    public static void link(final int id, final int source, final int target) {
        nLink(id, source, target);
    }

    private static native void nLink(int id, int source, int target); /*
        ImNodes::Link(id, source, target);
    */

    /**
     * Enable or disable the ability to click and drag a specific node.
     */
    public static void setNodeDraggable(final int nodeId, final boolean draggable) {
        nSetNodeDraggable(nodeId, draggable);
    }

    private static native void nSetNodeDraggable(int nodeId, boolean draggable); /*
        ImNodes::SetNodeDraggable(nodeId, draggable);
    */

    // The node's position can be expressed in three coordinate systems:
    // * screen space coordinates, -- the origin is the upper left corner of the window.
    // * editor space coordinates -- the origin is the upper left corner of the node editor window
    // * grid space coordinates, -- the origin is the upper left corner of the node editor window,
    // translated by the current editor panning vector (see EditorContextGetPanning() and
    // EditorContextResetPanning())
    // Use the following functions to get and set the node's coordinates in these coordinate systems.

    public static void setNodeScreenSpacePos(final int nodeId, final ImVec2 screenSpacePos) {
        nSetNodeScreenSpacePos(nodeId, screenSpacePos.x, screenSpacePos.y);
    }

    public static void setNodeScreenSpacePos(final int nodeId, final float screenSpacePosX, final float screenSpacePosY) {
        nSetNodeScreenSpacePos(nodeId, screenSpacePosX, screenSpacePosY);
    }

    private static native void nSetNodeScreenSpacePos(int nodeId, float screenSpacePosX, float screenSpacePosY); /*MANUAL
        ImVec2 screenSpacePos = ImVec2(screenSpacePosX, screenSpacePosY);
        ImNodes::SetNodeScreenSpacePos(nodeId, screenSpacePos);
    */

    public static void setNodeEditorSpacePos(final int nodeId, final ImVec2 editorSpacePos) {
        nSetNodeEditorSpacePos(nodeId, editorSpacePos.x, editorSpacePos.y);
    }

    public static void setNodeEditorSpacePos(final int nodeId, final float editorSpacePosX, final float editorSpacePosY) {
        nSetNodeEditorSpacePos(nodeId, editorSpacePosX, editorSpacePosY);
    }

    private static native void nSetNodeEditorSpacePos(int nodeId, float editorSpacePosX, float editorSpacePosY); /*MANUAL
        ImVec2 editorSpacePos = ImVec2(editorSpacePosX, editorSpacePosY);
        ImNodes::SetNodeEditorSpacePos(nodeId, editorSpacePos);
    */

    public static void setNodeGridSpacePos(final int nodeId, final ImVec2 gridPos) {
        nSetNodeGridSpacePos(nodeId, gridPos.x, gridPos.y);
    }

    public static void setNodeGridSpacePos(final int nodeId, final float gridPosX, final float gridPosY) {
        nSetNodeGridSpacePos(nodeId, gridPosX, gridPosY);
    }

    private static native void nSetNodeGridSpacePos(int nodeId, float gridPosX, float gridPosY); /*MANUAL
        ImVec2 gridPos = ImVec2(gridPosX, gridPosY);
        ImNodes::SetNodeGridSpacePos(nodeId, gridPos);
    */
    
    public static ImVec2 getNodeScreenSpacePos(final int nodeId) {
        final ImVec2 dst = new ImVec2();
        nGetNodeScreenSpacePos(dst, nodeId);
        return dst;
    }

    public static float getNodeScreenSpacePosX(final int nodeId) {
        return nGetNodeScreenSpacePosX(nodeId);
    }

    public static float getNodeScreenSpacePosY(final int nodeId) {
        return nGetNodeScreenSpacePosY(nodeId);
    }

    public static void getNodeScreenSpacePos(final ImVec2 dst, final int nodeId) {
        nGetNodeScreenSpacePos(dst, nodeId);
    }

    private static native void nGetNodeScreenSpacePos(ImVec2 dst, int nodeId); /*
        Jni::ImVec2Cpy(env, ImNodes::GetNodeScreenSpacePos(nodeId), dst);
    */

    private static native float nGetNodeScreenSpacePosX(int nodeId); /*
        return ImNodes::GetNodeScreenSpacePos(nodeId).x;
    */

    private static native float nGetNodeScreenSpacePosY(int nodeId); /*
        return ImNodes::GetNodeScreenSpacePos(nodeId).y;
    */

    public static ImVec2 getNodeEditorSpacePos(final int nodeId) {
        final ImVec2 dst = new ImVec2();
        nGetNodeEditorSpacePos(dst, nodeId);
        return dst;
    }

    public static float getNodeEditorSpacePosX(final int nodeId) {
        return nGetNodeEditorSpacePosX(nodeId);
    }

    public static float getNodeEditorSpacePosY(final int nodeId) {
        return nGetNodeEditorSpacePosY(nodeId);
    }

    public static void getNodeEditorSpacePos(final ImVec2 dst, final int nodeId) {
        nGetNodeEditorSpacePos(dst, nodeId);
    }

    private static native void nGetNodeEditorSpacePos(ImVec2 dst, int nodeId); /*
        Jni::ImVec2Cpy(env, ImNodes::GetNodeEditorSpacePos(nodeId), dst);
    */

    private static native float nGetNodeEditorSpacePosX(int nodeId); /*
        return ImNodes::GetNodeEditorSpacePos(nodeId).x;
    */

    private static native float nGetNodeEditorSpacePosY(int nodeId); /*
        return ImNodes::GetNodeEditorSpacePos(nodeId).y;
    */

    public static ImVec2 getNodeGridSpacePos(final int nodeId) {
        final ImVec2 dst = new ImVec2();
        nGetNodeGridSpacePos(dst, nodeId);
        return dst;
    }

    public static float getNodeGridSpacePosX(final int nodeId) {
        return nGetNodeGridSpacePosX(nodeId);
    }

    public static float getNodeGridSpacePosY(final int nodeId) {
        return nGetNodeGridSpacePosY(nodeId);
    }

    public static void getNodeGridSpacePos(final ImVec2 dst, final int nodeId) {
        nGetNodeGridSpacePos(dst, nodeId);
    }

    private static native void nGetNodeGridSpacePos(ImVec2 dst, int nodeId); /*
        Jni::ImVec2Cpy(env, ImNodes::GetNodeGridSpacePos(nodeId), dst);
    */

    private static native float nGetNodeGridSpacePosX(int nodeId); /*
        return ImNodes::GetNodeGridSpacePos(nodeId).x;
    */

    private static native float nGetNodeGridSpacePosY(int nodeId); /*
        return ImNodes::GetNodeGridSpacePos(nodeId).y;
    */

    /**
     * Enable or disable grid snapping.
     */
    public static void snapNodeToGrid(final int nodeId) {
        nSnapNodeToGrid(nodeId);
    }

    private static native void nSnapNodeToGrid(int nodeId); /*
        ImNodes::SnapNodeToGrid(nodeId);
    */

    /**
     * Returns true if the current node editor canvas is being hovered over by the mouse, and is not
     * blocked by any other windows.
     */
    public static boolean isEditorHovered() {
        return nIsEditorHovered();
    }

    private static native boolean nIsEditorHovered(); /*
        return ImNodes::IsEditorHovered();
    */

    // The following functions return true if a UI element is being hovered over by the mouse cursor.
    // Assigns the id of the UI element being hovered over to the function argument. Use these functions
    // after EndNodeEditor() has been called.

    public static boolean isNodeHovered(final ImInt nodeId) {
        return nIsNodeHovered(nodeId != null ? nodeId.getData() : null);
    }

    private static native boolean nIsNodeHovered(int[] obj_nodeId); /*MANUAL
        auto nodeId = obj_nodeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_nodeId, JNI_FALSE);
        auto _result = ImNodes::IsNodeHovered((nodeId != NULL ? &nodeId[0] : NULL));
        if (nodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_nodeId, nodeId, JNI_FALSE);
        return _result;
    */

    public static boolean isLinkHovered(final ImInt linkId) {
        return nIsLinkHovered(linkId != null ? linkId.getData() : null);
    }

    private static native boolean nIsLinkHovered(int[] obj_linkId); /*MANUAL
        auto linkId = obj_linkId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_linkId, JNI_FALSE);
        auto _result = ImNodes::IsLinkHovered((linkId != NULL ? &linkId[0] : NULL));
        if (linkId != NULL) env->ReleasePrimitiveArrayCritical(obj_linkId, linkId, JNI_FALSE);
        return _result;
    */

    public static boolean isPinHovered(final ImInt attributeId) {
        return nIsPinHovered(attributeId != null ? attributeId.getData() : null);
    }

    private static native boolean nIsPinHovered(int[] obj_attributeId); /*MANUAL
        auto attributeId = obj_attributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_attributeId, JNI_FALSE);
        auto _result = ImNodes::IsPinHovered((attributeId != NULL ? &attributeId[0] : NULL));
        if (attributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_attributeId, attributeId, JNI_FALSE);
        return _result;
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
     * Use The following two functions to query the number of selected nodes or links in the current
     * editor. Use after calling EndNodeEditor().
     */
    public static int numSelectedNodes() {
        return nNumSelectedNodes();
    }

    private static native int nNumSelectedNodes(); /*
        return ImNodes::NumSelectedNodes();
    */

    public static int numSelectedLinks() {
        return nNumSelectedLinks();
    }

    private static native int nNumSelectedLinks(); /*
        return ImNodes::NumSelectedLinks();
    */

    /**
     * Get the selected node/link ids. The pointer argument should point to an integer array with at
     * least as many elements as the respective NumSelectedNodes/NumSelectedLinks function call
     * returned.
     */
    public static void getSelectedNodes(final int[] nodeIds) {
        nGetSelectedNodes(nodeIds);
    }

    private static native void nGetSelectedNodes(int[] nodeIds); /*MANUAL
        auto nodeIds = obj_nodeIds == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_nodeIds, JNI_FALSE);
        ImNodes::GetSelectedNodes(&nodeIds[0]);
        if (nodeIds != NULL) env->ReleasePrimitiveArrayCritical(obj_nodeIds, nodeIds, JNI_FALSE);
    */

    /**
     * Get the selected node/link ids. The pointer argument should point to an integer array with at
     * least as many elements as the respective NumSelectedNodes/NumSelectedLinks function call
     * returned.
     */
    public static void getSelectedLinks(final int[] linkIds) {
        nGetSelectedLinks(linkIds);
    }

    private static native void nGetSelectedLinks(int[] linkIds); /*MANUAL
        auto linkIds = obj_linkIds == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_linkIds, JNI_FALSE);
        ImNodes::GetSelectedLinks(&linkIds[0]);
        if (linkIds != NULL) env->ReleasePrimitiveArrayCritical(obj_linkIds, linkIds, JNI_FALSE);
    */

    /**
     * Clears the list of selected nodes/links. Useful if you want to delete a selected node or link.
     */
    public static void clearNodeSelection() {
        nClearNodeSelection();
    }

    private static native void nClearNodeSelection(); /*
        ImNodes::ClearNodeSelection();
    */

    /**
     * Clears the list of selected nodes/links. Useful if you want to delete a selected node or link.
     */
    public static void clearLinkSelection() {
        nClearLinkSelection();
    }

    private static native void nClearLinkSelection(); /*
        ImNodes::ClearLinkSelection();
    */

    /**
     * Manually select a node or link.
     */
    public static void selectNode(final int nodeId) {
        nSelectNode(nodeId);
    }

    private static native void nSelectNode(int nodeId); /*
        ImNodes::SelectNode(nodeId);
    */

    public static void clearNodeSelection(final int nodeId) {
        nClearNodeSelection(nodeId);
    }

    private static native void nClearNodeSelection(int nodeId); /*
        ImNodes::ClearNodeSelection(nodeId);
    */

    public static boolean isNodeSelected(final int nodeId) {
        return nIsNodeSelected(nodeId);
    }

    private static native boolean nIsNodeSelected(int nodeId); /*
        return ImNodes::IsNodeSelected(nodeId);
    */

    public static void selectLink(final int linkId) {
        nSelectLink(linkId);
    }

    private static native void nSelectLink(int linkId); /*
        ImNodes::SelectLink(linkId);
    */

    public static void clearLinkSelection(final int linkId) {
        nClearLinkSelection(linkId);
    }

    private static native void nClearLinkSelection(int linkId); /*
        ImNodes::ClearLinkSelection(linkId);
    */

    public static boolean isLinkSelected(final int linkId) {
        return nIsLinkSelected(linkId);
    }

    private static native boolean nIsLinkSelected(int linkId); /*
        return ImNodes::IsLinkSelected(linkId);
    */

    /**
     * Was the previous attribute active? This will continuously return true while the left mouse button
     * is being pressed over the UI content of the attribute.
     */
    public static boolean isAttributeActive() {
        return nIsAttributeActive();
    }

    private static native boolean nIsAttributeActive(); /*
        return ImNodes::IsAttributeActive();
    */

    public static boolean isAnyAttributeActive() {
        return nIsAnyAttributeActive();
    }

    public static boolean isAnyAttributeActive(final ImInt attributeId) {
        return nIsAnyAttributeActive(attributeId != null ? attributeId.getData() : null);
    }

    private static native boolean nIsAnyAttributeActive(); /*
        return ImNodes::IsAnyAttributeActive();
    */

    private static native boolean nIsAnyAttributeActive(int[] obj_attributeId); /*MANUAL
        auto attributeId = obj_attributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_attributeId, JNI_FALSE);
        auto _result = ImNodes::IsAnyAttributeActive((attributeId != NULL ? &attributeId[0] : NULL));
        if (attributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_attributeId, attributeId, JNI_FALSE);
        return _result;
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

    // Use the following functions to query a change of state for an existing link, or new link. Call
    // these after EndNodeEditor().

    /**
     * Did the user start dragging a new link from a pin?
     */
    public static boolean isLinkStarted(final ImInt startedAtAttributeId) {
        return nIsLinkStarted(startedAtAttributeId != null ? startedAtAttributeId.getData() : null);
    }

    private static native boolean nIsLinkStarted(int[] obj_startedAtAttributeId); /*MANUAL
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto _result = ImNodes::IsLinkStarted((startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL));
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        return _result;
    */

    /**
     * Did the user drop the dragged link before attaching it to a pin?
     * There are two different kinds of situations to consider when handling this event:
     * 1) a link which is created at a pin and then dropped
     * 2) an existing link which is detached from a pin and then dropped
     * Use the including_detached_links flag to control whether this function triggers when the user
     * detaches a link and drops it.
     */
    public static boolean isLinkDropped() {
        return nIsLinkDropped();
    }

    /**
     * Did the user drop the dragged link before attaching it to a pin?
     * There are two different kinds of situations to consider when handling this event:
     * 1) a link which is created at a pin and then dropped
     * 2) an existing link which is detached from a pin and then dropped
     * Use the including_detached_links flag to control whether this function triggers when the user
     * detaches a link and drops it.
     */
    public static boolean isLinkDropped(final ImInt startedAtAttributeId) {
        return nIsLinkDropped(startedAtAttributeId != null ? startedAtAttributeId.getData() : null);
    }

    /**
     * Did the user drop the dragged link before attaching it to a pin?
     * There are two different kinds of situations to consider when handling this event:
     * 1) a link which is created at a pin and then dropped
     * 2) an existing link which is detached from a pin and then dropped
     * Use the including_detached_links flag to control whether this function triggers when the user
     * detaches a link and drops it.
     */
    public static boolean isLinkDropped(final ImInt startedAtAttributeId, final boolean includingDetachedLinks) {
        return nIsLinkDropped(startedAtAttributeId != null ? startedAtAttributeId.getData() : null, includingDetachedLinks);
    }

    /**
     * Did the user drop the dragged link before attaching it to a pin?
     * There are two different kinds of situations to consider when handling this event:
     * 1) a link which is created at a pin and then dropped
     * 2) an existing link which is detached from a pin and then dropped
     * Use the including_detached_links flag to control whether this function triggers when the user
     * detaches a link and drops it.
     */
    public static boolean isLinkDropped(final boolean includingDetachedLinks) {
        return nIsLinkDropped(includingDetachedLinks);
    }

    private static native boolean nIsLinkDropped(); /*
        return ImNodes::IsLinkDropped();
    */

    private static native boolean nIsLinkDropped(int[] obj_startedAtAttributeId); /*MANUAL
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto _result = ImNodes::IsLinkDropped((startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL));
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        return _result;
    */

    private static native boolean nIsLinkDropped(int[] obj_startedAtAttributeId, boolean includingDetachedLinks); /*MANUAL
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto _result = ImNodes::IsLinkDropped((startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL), includingDetachedLinks);
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        return _result;
    */

    private static native boolean nIsLinkDropped(boolean includingDetachedLinks); /*
        return ImNodes::IsLinkDropped(NULL, includingDetachedLinks);
    */

    /**
     * Did the user finish creating a new link?
     */
    public static boolean isLinkCreated(final ImInt startedAtAttributeId, final ImInt endedAtAttributeId) {
        return nIsLinkCreated(startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null);
    }

    /**
     * Did the user finish creating a new link?
     */
    public static boolean isLinkCreated(final ImInt startedAtAttributeId, final ImInt endedAtAttributeId, final ImBoolean createdFromSnap) {
        return nIsLinkCreated(startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null, createdFromSnap != null ? createdFromSnap.getData() : null);
    }

    private static native boolean nIsLinkCreated(int[] obj_startedAtAttributeId, int[] obj_endedAtAttributeId); /*MANUAL
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto endedAtAttributeId = obj_endedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_endedAtAttributeId, JNI_FALSE);
        auto _result = ImNodes::IsLinkCreated((startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL), (endedAtAttributeId != NULL ? &endedAtAttributeId[0] : NULL));
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        if (endedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_endedAtAttributeId, endedAtAttributeId, JNI_FALSE);
        return _result;
    */

    private static native boolean nIsLinkCreated(int[] obj_startedAtAttributeId, int[] obj_endedAtAttributeId, boolean[] obj_createdFromSnap); /*MANUAL
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto endedAtAttributeId = obj_endedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_endedAtAttributeId, JNI_FALSE);
        auto createdFromSnap = obj_createdFromSnap == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_createdFromSnap, JNI_FALSE);
        auto _result = ImNodes::IsLinkCreated((startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL), (endedAtAttributeId != NULL ? &endedAtAttributeId[0] : NULL), (createdFromSnap != NULL ? &createdFromSnap[0] : NULL));
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        if (endedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_endedAtAttributeId, endedAtAttributeId, JNI_FALSE);
        if (createdFromSnap != NULL) env->ReleasePrimitiveArrayCritical(obj_createdFromSnap, createdFromSnap, JNI_FALSE);
        return _result;
    */

    /**
     * Did the user finish creating a new link?
     */
    public static boolean isLinkCreated(final ImInt startedAtNodeId, final ImInt startedAtAttributeId, final ImInt endedAtNodeId, final ImInt endedAtAttributeId) {
        return nIsLinkCreated(startedAtNodeId != null ? startedAtNodeId.getData() : null, startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtNodeId != null ? endedAtNodeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null);
    }

    /**
     * Did the user finish creating a new link?
     */
    public static boolean isLinkCreated(final ImInt startedAtNodeId, final ImInt startedAtAttributeId, final ImInt endedAtNodeId, final ImInt endedAtAttributeId, final ImBoolean createdFromSnap) {
        return nIsLinkCreated(startedAtNodeId != null ? startedAtNodeId.getData() : null, startedAtAttributeId != null ? startedAtAttributeId.getData() : null, endedAtNodeId != null ? endedAtNodeId.getData() : null, endedAtAttributeId != null ? endedAtAttributeId.getData() : null, createdFromSnap != null ? createdFromSnap.getData() : null);
    }

    private static native boolean nIsLinkCreated(int[] obj_startedAtNodeId, int[] obj_startedAtAttributeId, int[] obj_endedAtNodeId, int[] obj_endedAtAttributeId); /*MANUAL
        auto startedAtNodeId = obj_startedAtNodeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtNodeId, JNI_FALSE);
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto endedAtNodeId = obj_endedAtNodeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_endedAtNodeId, JNI_FALSE);
        auto endedAtAttributeId = obj_endedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_endedAtAttributeId, JNI_FALSE);
        auto _result = ImNodes::IsLinkCreated((startedAtNodeId != NULL ? &startedAtNodeId[0] : NULL), (startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL), (endedAtNodeId != NULL ? &endedAtNodeId[0] : NULL), (endedAtAttributeId != NULL ? &endedAtAttributeId[0] : NULL));
        if (startedAtNodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtNodeId, startedAtNodeId, JNI_FALSE);
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        if (endedAtNodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_endedAtNodeId, endedAtNodeId, JNI_FALSE);
        if (endedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_endedAtAttributeId, endedAtAttributeId, JNI_FALSE);
        return _result;
    */

    private static native boolean nIsLinkCreated(int[] obj_startedAtNodeId, int[] obj_startedAtAttributeId, int[] obj_endedAtNodeId, int[] obj_endedAtAttributeId, boolean[] obj_createdFromSnap); /*MANUAL
        auto startedAtNodeId = obj_startedAtNodeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtNodeId, JNI_FALSE);
        auto startedAtAttributeId = obj_startedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_startedAtAttributeId, JNI_FALSE);
        auto endedAtNodeId = obj_endedAtNodeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_endedAtNodeId, JNI_FALSE);
        auto endedAtAttributeId = obj_endedAtAttributeId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_endedAtAttributeId, JNI_FALSE);
        auto createdFromSnap = obj_createdFromSnap == NULL ? NULL : (bool*)env->GetPrimitiveArrayCritical(obj_createdFromSnap, JNI_FALSE);
        auto _result = ImNodes::IsLinkCreated((startedAtNodeId != NULL ? &startedAtNodeId[0] : NULL), (startedAtAttributeId != NULL ? &startedAtAttributeId[0] : NULL), (endedAtNodeId != NULL ? &endedAtNodeId[0] : NULL), (endedAtAttributeId != NULL ? &endedAtAttributeId[0] : NULL), (createdFromSnap != NULL ? &createdFromSnap[0] : NULL));
        if (startedAtNodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtNodeId, startedAtNodeId, JNI_FALSE);
        if (startedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_startedAtAttributeId, startedAtAttributeId, JNI_FALSE);
        if (endedAtNodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_endedAtNodeId, endedAtNodeId, JNI_FALSE);
        if (endedAtAttributeId != NULL) env->ReleasePrimitiveArrayCritical(obj_endedAtAttributeId, endedAtAttributeId, JNI_FALSE);
        if (createdFromSnap != NULL) env->ReleasePrimitiveArrayCritical(obj_createdFromSnap, createdFromSnap, JNI_FALSE);
        return _result;
    */

    /**
     * Was an existing link detached from a pin by the user? The detached link's id is assigned to the
     * output argument link_id.
     */
    public static boolean isLinkDestroyed(final ImInt linkId) {
        return nIsLinkDestroyed(linkId != null ? linkId.getData() : null);
    }

    private static native boolean nIsLinkDestroyed(int[] obj_linkId); /*MANUAL
        auto linkId = obj_linkId == NULL ? NULL : (int*)env->GetPrimitiveArrayCritical(obj_linkId, JNI_FALSE);
        auto _result = ImNodes::IsLinkDestroyed((linkId != NULL ? &linkId[0] : NULL));
        if (linkId != NULL) env->ReleasePrimitiveArrayCritical(obj_linkId, linkId, JNI_FALSE);
        return _result;
    */

    // Use the following functions to write the editor context's state to a string, or directly to a
    // file. The editor context is serialized in the INI file format.

    public static String saveCurrentEditorStateToIniString() {
        return nSaveCurrentEditorStateToIniString();
    }

    private static native String nSaveCurrentEditorStateToIniString(); /*
        return env->NewStringUTF(ImNodes::SaveCurrentEditorStateToIniString());
    */

    public static String saveEditorStateToIniString(final ImNodesEditorContext editor) {
        return nSaveEditorStateToIniString(editor.ptr);
    }

    private static native String nSaveEditorStateToIniString(long editor); /*
        return env->NewStringUTF(ImNodes::SaveEditorStateToIniString(reinterpret_cast<ImNodesEditorContext*>(editor)));
    */

    public static void loadCurrentEditorStateFromIniString(final String data, final int dataSize) {
        nLoadCurrentEditorStateFromIniString(data, dataSize);
    }

    private static native void nLoadCurrentEditorStateFromIniString(String data, int dataSize); /*MANUAL
        auto data = obj_data == NULL ? NULL : (char*)env->GetStringUTFChars(obj_data, JNI_FALSE);
        ImNodes::LoadCurrentEditorStateFromIniString(data, dataSize);
        if (data != NULL) env->ReleaseStringUTFChars(obj_data, data);
    */

    public static void loadEditorStateFromIniString(final ImNodesEditorContext editor, final String data, final int dataSize) {
        nLoadEditorStateFromIniString(editor.ptr, data, dataSize);
    }

    private static native void nLoadEditorStateFromIniString(long editor, String data, int dataSize); /*MANUAL
        auto data = obj_data == NULL ? NULL : (char*)env->GetStringUTFChars(obj_data, JNI_FALSE);
        ImNodes::LoadEditorStateFromIniString(reinterpret_cast<ImNodesEditorContext*>(editor), data, dataSize);
        if (data != NULL) env->ReleaseStringUTFChars(obj_data, data);
    */

    public static void saveCurrentEditorStateToIniFile(final String fileName) {
        nSaveCurrentEditorStateToIniFile(fileName);
    }

    private static native void nSaveCurrentEditorStateToIniFile(String fileName); /*MANUAL
        auto fileName = obj_fileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fileName, JNI_FALSE);
        ImNodes::SaveCurrentEditorStateToIniFile(fileName);
        if (fileName != NULL) env->ReleaseStringUTFChars(obj_fileName, fileName);
    */

    public static void saveEditorStateToIniFile(final ImNodesEditorContext editor, final String fileName) {
        nSaveEditorStateToIniFile(editor.ptr, fileName);
    }

    private static native void nSaveEditorStateToIniFile(long editor, String fileName); /*MANUAL
        auto fileName = obj_fileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fileName, JNI_FALSE);
        ImNodes::SaveEditorStateToIniFile(reinterpret_cast<ImNodesEditorContext*>(editor), fileName);
        if (fileName != NULL) env->ReleaseStringUTFChars(obj_fileName, fileName);
    */

    public static void loadCurrentEditorStateFromIniFile(final String fileName) {
        nLoadCurrentEditorStateFromIniFile(fileName);
    }

    private static native void nLoadCurrentEditorStateFromIniFile(String fileName); /*MANUAL
        auto fileName = obj_fileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fileName, JNI_FALSE);
        ImNodes::LoadCurrentEditorStateFromIniFile(fileName);
        if (fileName != NULL) env->ReleaseStringUTFChars(obj_fileName, fileName);
    */

    public static void loadEditorStateFromIniFile(final ImNodesEditorContext editor, final String fileName) {
        nLoadEditorStateFromIniFile(editor.ptr, fileName);
    }

    private static native void nLoadEditorStateFromIniFile(long editor, String fileName); /*MANUAL
        auto fileName = obj_fileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_fileName, JNI_FALSE);
        ImNodes::LoadEditorStateFromIniFile(reinterpret_cast<ImNodesEditorContext*>(editor), fileName);
        if (fileName != NULL) env->ReleaseStringUTFChars(obj_fileName, fileName);
    */
}
