package imgui.extension.nodeditor;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;





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

public final class NodeEditor {
    private NodeEditor() {
    }

    /*JNI
        #include "_nodeeditor.h"
        #define NodeEditorConfig ax::NodeEditor::Config
        #define NodeEditorStyle ax::NodeEditor::Style
        #define NodeEditorContext ax::NodeEditor::EditorContext
     */

    public static void setCurrentEditor(final NodeEditorContext ctx) {
        nSetCurrentEditor(ctx.ptr);
    }

    private static native void nSetCurrentEditor(long ctx); /*
        ax::NodeEditor::SetCurrentEditor(reinterpret_cast<NodeEditorContext*>(ctx));
    */

    public static NodeEditorContext getCurrentEditor() {
        return new NodeEditorContext(nGetCurrentEditor());
    }

    private static native long nGetCurrentEditor(); /*
        return (uintptr_t)ax::NodeEditor::GetCurrentEditor();
    */

    public static NodeEditorContext createEditor() {
        return new NodeEditorContext(nCreateEditor());
    }

    public static NodeEditorContext createEditor(final NodeEditorConfig config) {
        return new NodeEditorContext(nCreateEditor(config.ptr));
    }

    private static native long nCreateEditor(); /*
        return (uintptr_t)ax::NodeEditor::CreateEditor();
    */

    private static native long nCreateEditor(long config); /*
        return (uintptr_t)ax::NodeEditor::CreateEditor(reinterpret_cast<NodeEditorConfig*>(config));
    */

    public static void destroyEditor(final NodeEditorContext ctx) {
        nDestroyEditor(ctx.ptr);
    }

    private static native void nDestroyEditor(long ctx); /*
        ax::NodeEditor::DestroyEditor(reinterpret_cast<NodeEditorContext*>(ctx));
    */

    private static final NodeEditorStyle _GETSTYLE_1 = new NodeEditorStyle(0);

    public static NodeEditorStyle getStyle() {
        _GETSTYLE_1.ptr = nGetStyle();
        return _GETSTYLE_1;
    }

    private static native long nGetStyle(); /*
        return (uintptr_t)&ax::NodeEditor::GetStyle();
    */

    public static String getStyleColorName(final int colorIndex) {
        return nGetStyleColorName(colorIndex);
    }

    private static native String nGetStyleColorName(int colorIndex); /*
        return env->NewStringUTF(ax::NodeEditor::GetStyleColorName(static_cast<ax::NodeEditor::StyleColor>(colorIndex)));
    */

    public static void pushStyleColor(final int colorIndex, final ImVec4 color) {
        nPushStyleColor(colorIndex, color.x, color.y, color.z, color.w);
    }

    public static void pushStyleColor(final int colorIndex, final float colorX, final float colorY, final float colorZ, final float colorW) {
        nPushStyleColor(colorIndex, colorX, colorY, colorZ, colorW);
    }

    private static native void nPushStyleColor(int colorIndex, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        ax::NodeEditor::PushStyleColor(static_cast<ax::NodeEditor::StyleColor>(colorIndex), color);
    */

    public static void popStyleColor() {
        nPopStyleColor();
    }

    public static void popStyleColor(final int count) {
        nPopStyleColor(count);
    }

    private static native void nPopStyleColor(); /*
        ax::NodeEditor::PopStyleColor();
    */

    private static native void nPopStyleColor(int count); /*
        ax::NodeEditor::PopStyleColor(count);
    */

    public static void pushStyleVar(final int varIndex, final float value) {
        nPushStyleVar(varIndex, value);
    }

    private static native void nPushStyleVar(int varIndex, float value); /*
        ax::NodeEditor::PushStyleVar(static_cast<ax::NodeEditor::StyleVar>(varIndex), value);
    */

    public static void pushStyleVar(final int varIndex, final ImVec2 value) {
        nPushStyleVar(varIndex, value.x, value.y);
    }

    public static void pushStyleVar(final int varIndex, final float valueX, final float valueY) {
        nPushStyleVar(varIndex, valueX, valueY);
    }

    private static native void nPushStyleVar(int varIndex, float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        ax::NodeEditor::PushStyleVar(static_cast<ax::NodeEditor::StyleVar>(varIndex), value);
    */

    public static void pushStyleVar(final int varIndex, final ImVec4 value) {
        nPushStyleVar(varIndex, value.x, value.y, value.z, value.w);
    }

    public static void pushStyleVar(final int varIndex, final float valueX, final float valueY, final float valueZ, final float valueW) {
        nPushStyleVar(varIndex, valueX, valueY, valueZ, valueW);
    }

    private static native void nPushStyleVar(int varIndex, float valueX, float valueY, float valueZ, float valueW); /*MANUAL
        ImVec4 value = ImVec4(valueX, valueY, valueZ, valueW);
        ax::NodeEditor::PushStyleVar(static_cast<ax::NodeEditor::StyleVar>(varIndex), value);
    */

    public static void popStyleVar() {
        nPopStyleVar();
    }

    public static void popStyleVar(final int count) {
        nPopStyleVar(count);
    }

    private static native void nPopStyleVar(); /*
        ax::NodeEditor::PopStyleVar();
    */

    private static native void nPopStyleVar(int count); /*
        ax::NodeEditor::PopStyleVar(count);
    */

    public static void begin(final String id) {
        nBegin(id);
    }

    public static void begin(final String id, final ImVec2 size) {
        nBegin(id, size.x, size.y);
    }

    public static void begin(final String id, final float sizeX, final float sizeY) {
        nBegin(id, sizeX, sizeY);
    }

    private static native void nBegin(String id); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ax::NodeEditor::Begin(id);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
    */

    private static native void nBegin(String id, float sizeX, float sizeY); /*MANUAL
        auto id = obj_id == NULL ? NULL : (char*)env->GetStringUTFChars(obj_id, JNI_FALSE);
        ImVec2 size = ImVec2(sizeX, sizeY);
        ax::NodeEditor::Begin(id, size);
        if (id != NULL) env->ReleaseStringUTFChars(obj_id, id);
    */

    public static void end() {
        nEnd();
    }

    private static native void nEnd(); /*
        ax::NodeEditor::End();
    */

    public static void beginNode(final long id) {
        nBeginNode(id);
    }

    private static native void nBeginNode(long id); /*
        ax::NodeEditor::BeginNode(id);
    */

    public static void beginPin(final long id, final int kind) {
        nBeginPin(id, kind);
    }

    private static native void nBeginPin(long id, int kind); /*
        ax::NodeEditor::BeginPin(id, static_cast<ax::NodeEditor::PinKind>(kind));
    */

    public static void pinRect(final ImVec2 a, final ImVec2 b) {
        nPinRect(a.x, a.y, b.x, b.y);
    }

    public static void pinRect(final float aX, final float aY, final float bX, final float bY) {
        nPinRect(aX, aY, bX, bY);
    }

    private static native void nPinRect(float aX, float aY, float bX, float bY); /*MANUAL
        ImVec2 a = ImVec2(aX, aY);
        ImVec2 b = ImVec2(bX, bY);
        ax::NodeEditor::PinRect(a, b);
    */

    public static void pinPivotRect(final ImVec2 a, final ImVec2 b) {
        nPinPivotRect(a.x, a.y, b.x, b.y);
    }

    public static void pinPivotRect(final float aX, final float aY, final float bX, final float bY) {
        nPinPivotRect(aX, aY, bX, bY);
    }

    private static native void nPinPivotRect(float aX, float aY, float bX, float bY); /*MANUAL
        ImVec2 a = ImVec2(aX, aY);
        ImVec2 b = ImVec2(bX, bY);
        ax::NodeEditor::PinPivotRect(a, b);
    */

    public static void pinPivotSize(final ImVec2 size) {
        nPinPivotSize(size.x, size.y);
    }

    public static void pinPivotSize(final float sizeX, final float sizeY) {
        nPinPivotSize(sizeX, sizeY);
    }

    private static native void nPinPivotSize(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ax::NodeEditor::PinPivotSize(size);
    */

    public static void pinPivotScale(final ImVec2 scale) {
        nPinPivotScale(scale.x, scale.y);
    }

    public static void pinPivotScale(final float scaleX, final float scaleY) {
        nPinPivotScale(scaleX, scaleY);
    }

    private static native void nPinPivotScale(float scaleX, float scaleY); /*MANUAL
        ImVec2 scale = ImVec2(scaleX, scaleY);
        ax::NodeEditor::PinPivotScale(scale);
    */

    public static void pinPivotAlignment(final ImVec2 alignment) {
        nPinPivotAlignment(alignment.x, alignment.y);
    }

    public static void pinPivotAlignment(final float alignmentX, final float alignmentY) {
        nPinPivotAlignment(alignmentX, alignmentY);
    }

    private static native void nPinPivotAlignment(float alignmentX, float alignmentY); /*MANUAL
        ImVec2 alignment = ImVec2(alignmentX, alignmentY);
        ax::NodeEditor::PinPivotAlignment(alignment);
    */

    public static void endPin() {
        nEndPin();
    }

    private static native void nEndPin(); /*
        ax::NodeEditor::EndPin();
    */

    public static void group(final ImVec2 size) {
        nGroup(size.x, size.y);
    }

    public static void group(final float sizeX, final float sizeY) {
        nGroup(sizeX, sizeY);
    }

    private static native void nGroup(float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ax::NodeEditor::Group(size);
    */

    public static void endNode() {
        nEndNode();
    }

    private static native void nEndNode(); /*
        ax::NodeEditor::EndNode();
    */

    public static boolean beginGroupHint(final long nodeId) {
        return nBeginGroupHint(nodeId);
    }

    private static native boolean nBeginGroupHint(long nodeId); /*
        return ax::NodeEditor::BeginGroupHint(nodeId);
    */

    public static ImVec2 getGroupMin() {
        final ImVec2 dst = new ImVec2();
        nGetGroupMin(dst);
        return dst;
    }

    public static float getGroupMinX() {
        return nGetGroupMinX();
    }

    public static float getGroupMinY() {
        return nGetGroupMinY();
    }

    public static void getGroupMin(final ImVec2 dst) {
        nGetGroupMin(dst);
    }

    private static native void nGetGroupMin(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ax::NodeEditor::GetGroupMin(), dst);
    */

    private static native float nGetGroupMinX(); /*
        return ax::NodeEditor::GetGroupMin().x;
    */

    private static native float nGetGroupMinY(); /*
        return ax::NodeEditor::GetGroupMin().y;
    */

    public static ImVec2 getGroupMax() {
        final ImVec2 dst = new ImVec2();
        nGetGroupMax(dst);
        return dst;
    }

    public static float getGroupMaxX() {
        return nGetGroupMaxX();
    }

    public static float getGroupMaxY() {
        return nGetGroupMaxY();
    }

    public static void getGroupMax(final ImVec2 dst) {
        nGetGroupMax(dst);
    }

    private static native void nGetGroupMax(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ax::NodeEditor::GetGroupMax(), dst);
    */

    private static native float nGetGroupMaxX(); /*
        return ax::NodeEditor::GetGroupMax().x;
    */

    private static native float nGetGroupMaxY(); /*
        return ax::NodeEditor::GetGroupMax().y;
    */

    private static final ImDrawList _GETHINTFOREGROUNDDRAWLIST_1 = new ImDrawList(0);

    public static ImDrawList getHintForegroundDrawList() {
        _GETHINTFOREGROUNDDRAWLIST_1.ptr = nGetHintForegroundDrawList();
        return _GETHINTFOREGROUNDDRAWLIST_1;
    }

    private static native long nGetHintForegroundDrawList(); /*
        return (uintptr_t)ax::NodeEditor::GetHintForegroundDrawList();
    */

    private static final ImDrawList _GETHINTBACKGROUNDDRAWLIST_1 = new ImDrawList(0);

    public static ImDrawList getHintBackgroundDrawList() {
        _GETHINTBACKGROUNDDRAWLIST_1.ptr = nGetHintBackgroundDrawList();
        return _GETHINTBACKGROUNDDRAWLIST_1;
    }

    private static native long nGetHintBackgroundDrawList(); /*
        return (uintptr_t)ax::NodeEditor::GetHintBackgroundDrawList();
    */

    public static void endGroupHint() {
        nEndGroupHint();
    }

    private static native void nEndGroupHint(); /*
        ax::NodeEditor::EndGroupHint();
    */

    private static final ImDrawList _GETNODEBACKGROUNDDRAWLIST_1040171299 = new ImDrawList(0);

    public static ImDrawList getNodeBackgroundDrawList(final long nodeId) {
        _GETNODEBACKGROUNDDRAWLIST_1040171299.ptr = nGetNodeBackgroundDrawList(nodeId);
        return _GETNODEBACKGROUNDDRAWLIST_1040171299;
    }

    private static native long nGetNodeBackgroundDrawList(long nodeId); /*
        return (uintptr_t)ax::NodeEditor::GetNodeBackgroundDrawList(nodeId);
    */

    public static boolean link(final long id, final long startPinId, final long endPinId) {
        return nLink(id, startPinId, endPinId);
    }

    public static boolean link(final long id, final long startPinId, final long endPinId, final ImVec4 color) {
        return nLink(id, startPinId, endPinId, color.x, color.y, color.z, color.w);
    }

    public static boolean link(final long id, final long startPinId, final long endPinId, final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nLink(id, startPinId, endPinId, colorX, colorY, colorZ, colorW);
    }

    public static boolean link(final long id, final long startPinId, final long endPinId, final ImVec4 color, final float thickness) {
        return nLink(id, startPinId, endPinId, color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean link(final long id, final long startPinId, final long endPinId, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nLink(id, startPinId, endPinId, colorX, colorY, colorZ, colorW, thickness);
    }

    public static boolean link(final long id, final long startPinId, final long endPinId, final float thickness) {
        return nLink(id, startPinId, endPinId, thickness);
    }

    private static native boolean nLink(long id, long startPinId, long endPinId); /*
        return ax::NodeEditor::Link(id, startPinId, endPinId);
    */

    private static native boolean nLink(long id, long startPinId, long endPinId, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::Link(id, startPinId, endPinId, color);
        return _result;
    */

    private static native boolean nLink(long id, long startPinId, long endPinId, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::Link(id, startPinId, endPinId, color, thickness);
        return _result;
    */

    private static native boolean nLink(long id, long startPinId, long endPinId, float thickness); /*
        return ax::NodeEditor::Link(id, startPinId, endPinId, ImVec4(1,1,1,1), thickness);
    */

    public static void flow(final long linkId) {
        nFlow(linkId);
    }

    public static void flow(final long linkId, final int direction) {
        nFlow(linkId, direction);
    }

    private static native void nFlow(long linkId); /*
        ax::NodeEditor::Flow(linkId);
    */

    private static native void nFlow(long linkId, int direction); /*
        ax::NodeEditor::Flow(linkId, static_cast<ax::NodeEditor::FlowDirection>(direction));
    */

    public static boolean beginCreate() {
        return nBeginCreate();
    }

    public static boolean beginCreate(final ImVec4 color) {
        return nBeginCreate(color.x, color.y, color.z, color.w);
    }

    public static boolean beginCreate(final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nBeginCreate(colorX, colorY, colorZ, colorW);
    }

    public static boolean beginCreate(final ImVec4 color, final float thickness) {
        return nBeginCreate(color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean beginCreate(final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nBeginCreate(colorX, colorY, colorZ, colorW, thickness);
    }

    public static boolean beginCreate(final float thickness) {
        return nBeginCreate(thickness);
    }

    private static native boolean nBeginCreate(); /*
        return ax::NodeEditor::BeginCreate();
    */

    private static native boolean nBeginCreate(float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::BeginCreate(color);
        return _result;
    */

    private static native boolean nBeginCreate(float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::BeginCreate(color, thickness);
        return _result;
    */

    private static native boolean nBeginCreate(float thickness); /*
        return ax::NodeEditor::BeginCreate(ImVec4(1,1,1,1), thickness);
    */

    public static boolean queryNewLink(final ImLong startId, final ImLong endId) {
        return nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null);
    }

    private static native boolean nQueryNewLink(long[] obj_startId, long[] obj_endId); /*MANUAL
        auto startId = obj_startId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_startId, JNI_FALSE);
        auto endId = obj_endId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_endId, JNI_FALSE);
        auto _result = ax::NodeEditor::QueryNewLink(reinterpret_cast<ax::NodeEditor::PinId*>((startId != NULL ? &startId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((endId != NULL ? &endId[0] : NULL)));
        if (startId != NULL) env->ReleasePrimitiveArrayCritical(obj_startId, startId, JNI_FALSE);
        if (endId != NULL) env->ReleasePrimitiveArrayCritical(obj_endId, endId, JNI_FALSE);
        return _result;
    */

    public static boolean queryNewLink(final ImLong startId, final ImLong endId, final ImVec4 color) {
        return nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, color.x, color.y, color.z, color.w);
    }

    public static boolean queryNewLink(final ImLong startId, final ImLong endId, final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, colorX, colorY, colorZ, colorW);
    }

    public static boolean queryNewLink(final ImLong startId, final ImLong endId, final ImVec4 color, final float thickness) {
        return nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean queryNewLink(final ImLong startId, final ImLong endId, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nQueryNewLink(startId != null ? startId.getData() : null, endId != null ? endId.getData() : null, colorX, colorY, colorZ, colorW, thickness);
    }

    private static native boolean nQueryNewLink(long[] obj_startId, long[] obj_endId, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        auto startId = obj_startId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_startId, JNI_FALSE);
        auto endId = obj_endId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_endId, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::QueryNewLink(reinterpret_cast<ax::NodeEditor::PinId*>((startId != NULL ? &startId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((endId != NULL ? &endId[0] : NULL)), color);
        if (startId != NULL) env->ReleasePrimitiveArrayCritical(obj_startId, startId, JNI_FALSE);
        if (endId != NULL) env->ReleasePrimitiveArrayCritical(obj_endId, endId, JNI_FALSE);
        return _result;
    */

    private static native boolean nQueryNewLink(long[] obj_startId, long[] obj_endId, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        auto startId = obj_startId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_startId, JNI_FALSE);
        auto endId = obj_endId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_endId, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::QueryNewLink(reinterpret_cast<ax::NodeEditor::PinId*>((startId != NULL ? &startId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((endId != NULL ? &endId[0] : NULL)), color, thickness);
        if (startId != NULL) env->ReleasePrimitiveArrayCritical(obj_startId, startId, JNI_FALSE);
        if (endId != NULL) env->ReleasePrimitiveArrayCritical(obj_endId, endId, JNI_FALSE);
        return _result;
    */

    public static boolean queryNewNode(final ImLong pinId) {
        return nQueryNewNode(pinId != null ? pinId.getData() : null);
    }

    private static native boolean nQueryNewNode(long[] obj_pinId); /*MANUAL
        auto pinId = obj_pinId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pinId, JNI_FALSE);
        auto _result = ax::NodeEditor::QueryNewNode(reinterpret_cast<ax::NodeEditor::PinId*>((pinId != NULL ? &pinId[0] : NULL)));
        if (pinId != NULL) env->ReleasePrimitiveArrayCritical(obj_pinId, pinId, JNI_FALSE);
        return _result;
    */

    public static boolean queryNewNode(final ImLong pinId, final ImVec4 color) {
        return nQueryNewNode(pinId != null ? pinId.getData() : null, color.x, color.y, color.z, color.w);
    }

    public static boolean queryNewNode(final ImLong pinId, final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nQueryNewNode(pinId != null ? pinId.getData() : null, colorX, colorY, colorZ, colorW);
    }

    public static boolean queryNewNode(final ImLong pinId, final ImVec4 color, final float thickness) {
        return nQueryNewNode(pinId != null ? pinId.getData() : null, color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean queryNewNode(final ImLong pinId, final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nQueryNewNode(pinId != null ? pinId.getData() : null, colorX, colorY, colorZ, colorW, thickness);
    }

    private static native boolean nQueryNewNode(long[] obj_pinId, float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        auto pinId = obj_pinId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pinId, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::QueryNewNode(reinterpret_cast<ax::NodeEditor::PinId*>((pinId != NULL ? &pinId[0] : NULL)), color);
        if (pinId != NULL) env->ReleasePrimitiveArrayCritical(obj_pinId, pinId, JNI_FALSE);
        return _result;
    */

    private static native boolean nQueryNewNode(long[] obj_pinId, float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        auto pinId = obj_pinId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pinId, JNI_FALSE);
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::QueryNewNode(reinterpret_cast<ax::NodeEditor::PinId*>((pinId != NULL ? &pinId[0] : NULL)), color, thickness);
        if (pinId != NULL) env->ReleasePrimitiveArrayCritical(obj_pinId, pinId, JNI_FALSE);
        return _result;
    */

    public static boolean acceptNewItem() {
        return nAcceptNewItem();
    }

    private static native boolean nAcceptNewItem(); /*
        return ax::NodeEditor::AcceptNewItem();
    */

    public static boolean acceptNewItem(final ImVec4 color) {
        return nAcceptNewItem(color.x, color.y, color.z, color.w);
    }

    public static boolean acceptNewItem(final float colorX, final float colorY, final float colorZ, final float colorW) {
        return nAcceptNewItem(colorX, colorY, colorZ, colorW);
    }

    public static boolean acceptNewItem(final ImVec4 color, final float thickness) {
        return nAcceptNewItem(color.x, color.y, color.z, color.w, thickness);
    }

    public static boolean acceptNewItem(final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        return nAcceptNewItem(colorX, colorY, colorZ, colorW, thickness);
    }

    private static native boolean nAcceptNewItem(float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::AcceptNewItem(color);
        return _result;
    */

    private static native boolean nAcceptNewItem(float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        auto _result = ax::NodeEditor::AcceptNewItem(color, thickness);
        return _result;
    */

    public static void rejectNewItem() {
        nRejectNewItem();
    }

    private static native void nRejectNewItem(); /*
        ax::NodeEditor::RejectNewItem();
    */

    public static void rejectNewItem(final ImVec4 color) {
        nRejectNewItem(color.x, color.y, color.z, color.w);
    }

    public static void rejectNewItem(final float colorX, final float colorY, final float colorZ, final float colorW) {
        nRejectNewItem(colorX, colorY, colorZ, colorW);
    }

    public static void rejectNewItem(final ImVec4 color, final float thickness) {
        nRejectNewItem(color.x, color.y, color.z, color.w, thickness);
    }

    public static void rejectNewItem(final float colorX, final float colorY, final float colorZ, final float colorW, final float thickness) {
        nRejectNewItem(colorX, colorY, colorZ, colorW, thickness);
    }

    private static native void nRejectNewItem(float colorX, float colorY, float colorZ, float colorW); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        ax::NodeEditor::RejectNewItem(color);
    */

    private static native void nRejectNewItem(float colorX, float colorY, float colorZ, float colorW, float thickness); /*MANUAL
        ImVec4 color = ImVec4(colorX, colorY, colorZ, colorW);
        ax::NodeEditor::RejectNewItem(color, thickness);
    */

    public static void endCreate() {
        nEndCreate();
    }

    private static native void nEndCreate(); /*
        ax::NodeEditor::EndCreate();
    */

    public static boolean beginDelete() {
        return nBeginDelete();
    }

    private static native boolean nBeginDelete(); /*
        return ax::NodeEditor::BeginDelete();
    */

    public static boolean queryDeletedLink(final ImLong linkId) {
        return nQueryDeletedLink(linkId != null ? linkId.getData() : null);
    }

    public static boolean queryDeletedLink(final ImLong linkId, final ImLong startId) {
        return nQueryDeletedLink(linkId != null ? linkId.getData() : null, startId != null ? startId.getData() : null);
    }

    public static boolean queryDeletedLink(final ImLong linkId, final ImLong startId, final ImLong endId) {
        return nQueryDeletedLink(linkId != null ? linkId.getData() : null, startId != null ? startId.getData() : null, endId != null ? endId.getData() : null);
    }

    private static native boolean nQueryDeletedLink(long[] obj_linkId); /*MANUAL
        auto linkId = obj_linkId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_linkId, JNI_FALSE);
        auto _result = ax::NodeEditor::QueryDeletedLink(reinterpret_cast<ax::NodeEditor::LinkId*>((linkId != NULL ? &linkId[0] : NULL)));
        if (linkId != NULL) env->ReleasePrimitiveArrayCritical(obj_linkId, linkId, JNI_FALSE);
        return _result;
    */

    private static native boolean nQueryDeletedLink(long[] obj_linkId, long[] obj_startId); /*MANUAL
        auto linkId = obj_linkId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_linkId, JNI_FALSE);
        auto startId = obj_startId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_startId, JNI_FALSE);
        auto _result = ax::NodeEditor::QueryDeletedLink(reinterpret_cast<ax::NodeEditor::LinkId*>((linkId != NULL ? &linkId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((startId != NULL ? &startId[0] : NULL)));
        if (linkId != NULL) env->ReleasePrimitiveArrayCritical(obj_linkId, linkId, JNI_FALSE);
        if (startId != NULL) env->ReleasePrimitiveArrayCritical(obj_startId, startId, JNI_FALSE);
        return _result;
    */

    private static native boolean nQueryDeletedLink(long[] obj_linkId, long[] obj_startId, long[] obj_endId); /*MANUAL
        auto linkId = obj_linkId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_linkId, JNI_FALSE);
        auto startId = obj_startId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_startId, JNI_FALSE);
        auto endId = obj_endId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_endId, JNI_FALSE);
        auto _result = ax::NodeEditor::QueryDeletedLink(reinterpret_cast<ax::NodeEditor::LinkId*>((linkId != NULL ? &linkId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((startId != NULL ? &startId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((endId != NULL ? &endId[0] : NULL)));
        if (linkId != NULL) env->ReleasePrimitiveArrayCritical(obj_linkId, linkId, JNI_FALSE);
        if (startId != NULL) env->ReleasePrimitiveArrayCritical(obj_startId, startId, JNI_FALSE);
        if (endId != NULL) env->ReleasePrimitiveArrayCritical(obj_endId, endId, JNI_FALSE);
        return _result;
    */

    public static boolean queryDeletedNode(final ImLong nodeId) {
        return nQueryDeletedNode(nodeId != null ? nodeId.getData() : null);
    }

    private static native boolean nQueryDeletedNode(long[] obj_nodeId); /*MANUAL
        auto nodeId = obj_nodeId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_nodeId, JNI_FALSE);
        auto _result = ax::NodeEditor::QueryDeletedNode(reinterpret_cast<ax::NodeEditor::NodeId*>((nodeId != NULL ? &nodeId[0] : NULL)));
        if (nodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_nodeId, nodeId, JNI_FALSE);
        return _result;
    */

    public static boolean acceptDeletedItem() {
        return nAcceptDeletedItem();
    }

    public static boolean acceptDeletedItem(final boolean deleteDependencies) {
        return nAcceptDeletedItem(deleteDependencies);
    }

    private static native boolean nAcceptDeletedItem(); /*
        return ax::NodeEditor::AcceptDeletedItem();
    */

    private static native boolean nAcceptDeletedItem(boolean deleteDependencies); /*
        return ax::NodeEditor::AcceptDeletedItem(deleteDependencies);
    */

    public static void rejectDeletedItem() {
        nRejectDeletedItem();
    }

    private static native void nRejectDeletedItem(); /*
        ax::NodeEditor::RejectDeletedItem();
    */

    public static void endDelete() {
        nEndDelete();
    }

    private static native void nEndDelete(); /*
        ax::NodeEditor::EndDelete();
    */

    public static void setNodePosition(final long nodeId, final ImVec2 editorPosition) {
        nSetNodePosition(nodeId, editorPosition.x, editorPosition.y);
    }

    public static void setNodePosition(final long nodeId, final float editorPositionX, final float editorPositionY) {
        nSetNodePosition(nodeId, editorPositionX, editorPositionY);
    }

    private static native void nSetNodePosition(long nodeId, float editorPositionX, float editorPositionY); /*MANUAL
        ImVec2 editorPosition = ImVec2(editorPositionX, editorPositionY);
        ax::NodeEditor::SetNodePosition(nodeId, editorPosition);
    */

    public static void setGroupSize(final long nodeId, final ImVec2 size) {
        nSetGroupSize(nodeId, size.x, size.y);
    }

    public static void setGroupSize(final long nodeId, final float sizeX, final float sizeY) {
        nSetGroupSize(nodeId, sizeX, sizeY);
    }

    private static native void nSetGroupSize(long nodeId, float sizeX, float sizeY); /*MANUAL
        ImVec2 size = ImVec2(sizeX, sizeY);
        ax::NodeEditor::SetGroupSize(nodeId, size);
    */

    public static ImVec2 getNodePosition(final long nodeId) {
        final ImVec2 dst = new ImVec2();
        nGetNodePosition(dst, nodeId);
        return dst;
    }

    public static float getNodePositionX(final long nodeId) {
        return nGetNodePositionX(nodeId);
    }

    public static float getNodePositionY(final long nodeId) {
        return nGetNodePositionY(nodeId);
    }

    public static void getNodePosition(final ImVec2 dst, final long nodeId) {
        nGetNodePosition(dst, nodeId);
    }

    private static native void nGetNodePosition(ImVec2 dst, long nodeId); /*
        Jni::ImVec2Cpy(env, ax::NodeEditor::GetNodePosition(nodeId), dst);
    */

    private static native float nGetNodePositionX(long nodeId); /*
        return ax::NodeEditor::GetNodePosition(nodeId).x;
    */

    private static native float nGetNodePositionY(long nodeId); /*
        return ax::NodeEditor::GetNodePosition(nodeId).y;
    */

    public static ImVec2 getNodeSize(final long nodeId) {
        final ImVec2 dst = new ImVec2();
        nGetNodeSize(dst, nodeId);
        return dst;
    }

    public static float getNodeSizeX(final long nodeId) {
        return nGetNodeSizeX(nodeId);
    }

    public static float getNodeSizeY(final long nodeId) {
        return nGetNodeSizeY(nodeId);
    }

    public static void getNodeSize(final ImVec2 dst, final long nodeId) {
        nGetNodeSize(dst, nodeId);
    }

    private static native void nGetNodeSize(ImVec2 dst, long nodeId); /*
        Jni::ImVec2Cpy(env, ax::NodeEditor::GetNodeSize(nodeId), dst);
    */

    private static native float nGetNodeSizeX(long nodeId); /*
        return ax::NodeEditor::GetNodeSize(nodeId).x;
    */

    private static native float nGetNodeSizeY(long nodeId); /*
        return ax::NodeEditor::GetNodeSize(nodeId).y;
    */

    public static void centerNodeOnScreen(final long nodeId) {
        nCenterNodeOnScreen(nodeId);
    }

    private static native void nCenterNodeOnScreen(long nodeId); /*
        ax::NodeEditor::CenterNodeOnScreen(nodeId);
    */

    public static void setNodeZPosition(final long nodeId, final float z) {
        nSetNodeZPosition(nodeId, z);
    }

    private static native void nSetNodeZPosition(long nodeId, float z); /*
        ax::NodeEditor::SetNodeZPosition(nodeId, z);
    */

    public static float getNodeZPosition(final long nodeId) {
        return nGetNodeZPosition(nodeId);
    }

    private static native float nGetNodeZPosition(long nodeId); /*
        return ax::NodeEditor::GetNodeZPosition(nodeId);
    */

    public static void restoreNodeState(final long nodeId) {
        nRestoreNodeState(nodeId);
    }

    private static native void nRestoreNodeState(long nodeId); /*
        ax::NodeEditor::RestoreNodeState(nodeId);
    */

    public static void suspend() {
        nSuspend();
    }

    private static native void nSuspend(); /*
        ax::NodeEditor::Suspend();
    */

    public static void resume() {
        nResume();
    }

    private static native void nResume(); /*
        ax::NodeEditor::Resume();
    */

    public static boolean isSuspended() {
        return nIsSuspended();
    }

    private static native boolean nIsSuspended(); /*
        return ax::NodeEditor::IsSuspended();
    */

    public static boolean isActive() {
        return nIsActive();
    }

    private static native boolean nIsActive(); /*
        return ax::NodeEditor::IsActive();
    */

    public static boolean hasSelectionChanged() {
        return nHasSelectionChanged();
    }

    private static native boolean nHasSelectionChanged(); /*
        return ax::NodeEditor::HasSelectionChanged();
    */

    public static int getSelectedObjectCount() {
        return nGetSelectedObjectCount();
    }

    private static native int nGetSelectedObjectCount(); /*
        return ax::NodeEditor::GetSelectedObjectCount();
    */

    public static int getSelectedNodes(final long[] nodes, final int size) {
        return nGetSelectedNodes(nodes, size);
    }

    private static native int nGetSelectedNodes(long[] obj_nodes, int size); /*MANUAL
        auto nodes = obj_nodes == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_nodes, JNI_FALSE);
        auto _result = ax::NodeEditor::GetSelectedNodes(reinterpret_cast<ax::NodeEditor::NodeId*>(&nodes[0]), size);
        if (nodes != NULL) env->ReleasePrimitiveArrayCritical(obj_nodes, nodes, JNI_FALSE);
        return _result;
    */

    public static int getSelectedLinks(final long[] links, final int size) {
        return nGetSelectedLinks(links, size);
    }

    private static native int nGetSelectedLinks(long[] obj_links, int size); /*MANUAL
        auto links = obj_links == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_links, JNI_FALSE);
        auto _result = ax::NodeEditor::GetSelectedLinks(reinterpret_cast<ax::NodeEditor::LinkId*>(&links[0]), size);
        if (links != NULL) env->ReleasePrimitiveArrayCritical(obj_links, links, JNI_FALSE);
        return _result;
    */

    public static boolean isNodeSelected(final long nodeId) {
        return nIsNodeSelected(nodeId);
    }

    private static native boolean nIsNodeSelected(long nodeId); /*
        return ax::NodeEditor::IsNodeSelected(nodeId);
    */

    public static boolean isLinkSelected(final long linkId) {
        return nIsLinkSelected(linkId);
    }

    private static native boolean nIsLinkSelected(long linkId); /*
        return ax::NodeEditor::IsLinkSelected(linkId);
    */

    public static void clearSelection() {
        nClearSelection();
    }

    private static native void nClearSelection(); /*
        ax::NodeEditor::ClearSelection();
    */

    public static void selectNode(final long nodeId) {
        nSelectNode(nodeId);
    }

    public static void selectNode(final long nodeId, final boolean append) {
        nSelectNode(nodeId, append);
    }

    private static native void nSelectNode(long nodeId); /*
        ax::NodeEditor::SelectNode(nodeId);
    */

    private static native void nSelectNode(long nodeId, boolean append); /*
        ax::NodeEditor::SelectNode(nodeId, append);
    */

    public static void selectLink(final long linkId) {
        nSelectLink(linkId);
    }

    public static void selectLink(final long linkId, final boolean append) {
        nSelectLink(linkId, append);
    }

    private static native void nSelectLink(long linkId); /*
        ax::NodeEditor::SelectLink(linkId);
    */

    private static native void nSelectLink(long linkId, boolean append); /*
        ax::NodeEditor::SelectLink(linkId, append);
    */

    public static void deselectNode(final long nodeId) {
        nDeselectNode(nodeId);
    }

    private static native void nDeselectNode(long nodeId); /*
        ax::NodeEditor::DeselectNode(nodeId);
    */

    public static void deselectLink(final long linkId) {
        nDeselectLink(linkId);
    }

    private static native void nDeselectLink(long linkId); /*
        ax::NodeEditor::DeselectLink(linkId);
    */

    public static boolean deleteNode(final long nodeId) {
        return nDeleteNode(nodeId);
    }

    private static native boolean nDeleteNode(long nodeId); /*
        return ax::NodeEditor::DeleteNode(nodeId);
    */

    public static boolean deleteLink(final long linkId) {
        return nDeleteLink(linkId);
    }

    private static native boolean nDeleteLink(long linkId); /*
        return ax::NodeEditor::DeleteLink(linkId);
    */

    public static boolean hasAnyLinksNode(final long nodeId) {
        return nHasAnyLinksNode(nodeId);
    }

    private static native boolean nHasAnyLinksNode(long nodeId); /*
        return ax::NodeEditor::HasAnyLinks(static_cast<ax::NodeEditor::NodeId>(nodeId));
    */

    public static boolean hasAnyLinksPin(final long pinId) {
        return nHasAnyLinksPin(pinId);
    }

    private static native boolean nHasAnyLinksPin(long pinId); /*
        return ax::NodeEditor::HasAnyLinks(static_cast<ax::NodeEditor::PinId>(pinId));
    */

    public static int breakLinksNode(final long nodeId) {
        return nBreakLinksNode(nodeId);
    }

    private static native int nBreakLinksNode(long nodeId); /*
        return ax::NodeEditor::BreakLinks(static_cast<ax::NodeEditor::NodeId>(nodeId));
    */

    public static int breakLinksPin(final long pinId) {
        return nBreakLinksPin(pinId);
    }

    private static native int nBreakLinksPin(long pinId); /*
        return ax::NodeEditor::BreakLinks(static_cast<ax::NodeEditor::PinId>(pinId));
    */

    public static void navigateToContent() {
        nNavigateToContent();
    }

    public static void navigateToContent(final float duration) {
        nNavigateToContent(duration);
    }

    private static native void nNavigateToContent(); /*
        ax::NodeEditor::NavigateToContent();
    */

    private static native void nNavigateToContent(float duration); /*
        ax::NodeEditor::NavigateToContent(duration);
    */

    public static void navigateToSelection() {
        nNavigateToSelection();
    }

    public static void navigateToSelection(final boolean zoomIn) {
        nNavigateToSelection(zoomIn);
    }

    public static void navigateToSelection(final boolean zoomIn, final float duration) {
        nNavigateToSelection(zoomIn, duration);
    }

    public static void navigateToSelection(final float duration) {
        nNavigateToSelection(duration);
    }

    private static native void nNavigateToSelection(); /*
        ax::NodeEditor::NavigateToSelection();
    */

    private static native void nNavigateToSelection(boolean zoomIn); /*
        ax::NodeEditor::NavigateToSelection(zoomIn);
    */

    private static native void nNavigateToSelection(boolean zoomIn, float duration); /*
        ax::NodeEditor::NavigateToSelection(zoomIn, duration);
    */

    private static native void nNavigateToSelection(float duration); /*
        ax::NodeEditor::NavigateToSelection(false, duration);
    */

    public static boolean showNodeContextMenu(final ImLong nodeId) {
        return nShowNodeContextMenu(nodeId != null ? nodeId.getData() : null);
    }

    private static native boolean nShowNodeContextMenu(long[] obj_nodeId); /*MANUAL
        auto nodeId = obj_nodeId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_nodeId, JNI_FALSE);
        auto _result = ax::NodeEditor::ShowNodeContextMenu(reinterpret_cast<ax::NodeEditor::NodeId*>((nodeId != NULL ? &nodeId[0] : NULL)));
        if (nodeId != NULL) env->ReleasePrimitiveArrayCritical(obj_nodeId, nodeId, JNI_FALSE);
        return _result;
    */

    public static boolean showPinContextMenu(final ImLong pinId) {
        return nShowPinContextMenu(pinId != null ? pinId.getData() : null);
    }

    private static native boolean nShowPinContextMenu(long[] obj_pinId); /*MANUAL
        auto pinId = obj_pinId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_pinId, JNI_FALSE);
        auto _result = ax::NodeEditor::ShowPinContextMenu(reinterpret_cast<ax::NodeEditor::PinId*>((pinId != NULL ? &pinId[0] : NULL)));
        if (pinId != NULL) env->ReleasePrimitiveArrayCritical(obj_pinId, pinId, JNI_FALSE);
        return _result;
    */

    public static boolean showLinkContextMenu(final ImLong linkId) {
        return nShowLinkContextMenu(linkId != null ? linkId.getData() : null);
    }

    private static native boolean nShowLinkContextMenu(long[] obj_linkId); /*MANUAL
        auto linkId = obj_linkId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_linkId, JNI_FALSE);
        auto _result = ax::NodeEditor::ShowLinkContextMenu(reinterpret_cast<ax::NodeEditor::LinkId*>((linkId != NULL ? &linkId[0] : NULL)));
        if (linkId != NULL) env->ReleasePrimitiveArrayCritical(obj_linkId, linkId, JNI_FALSE);
        return _result;
    */

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

    public static boolean showBackgroundContextMenu() {
        return nShowBackgroundContextMenu();
    }

    private static native boolean nShowBackgroundContextMenu(); /*
        return ax::NodeEditor::ShowBackgroundContextMenu();
    */

    public static void enableShortcuts(final boolean enable) {
        nEnableShortcuts(enable);
    }

    private static native void nEnableShortcuts(boolean enable); /*
        ax::NodeEditor::EnableShortcuts(enable);
    */

    public static boolean areShortcutsEnabled() {
        return nAreShortcutsEnabled();
    }

    private static native boolean nAreShortcutsEnabled(); /*
        return ax::NodeEditor::AreShortcutsEnabled();
    */

    public static boolean beginShortcut() {
        return nBeginShortcut();
    }

    private static native boolean nBeginShortcut(); /*
        return ax::NodeEditor::BeginShortcut();
    */

    public static boolean acceptCut() {
        return nAcceptCut();
    }

    private static native boolean nAcceptCut(); /*
        return ax::NodeEditor::AcceptCut();
    */

    public static boolean acceptCopy() {
        return nAcceptCopy();
    }

    private static native boolean nAcceptCopy(); /*
        return ax::NodeEditor::AcceptCopy();
    */

    public static boolean acceptPaste() {
        return nAcceptPaste();
    }

    private static native boolean nAcceptPaste(); /*
        return ax::NodeEditor::AcceptPaste();
    */

    public static boolean acceptDuplicate() {
        return nAcceptDuplicate();
    }

    private static native boolean nAcceptDuplicate(); /*
        return ax::NodeEditor::AcceptDuplicate();
    */

    public static boolean acceptCreateNode() {
        return nAcceptCreateNode();
    }

    private static native boolean nAcceptCreateNode(); /*
        return ax::NodeEditor::AcceptCreateNode();
    */

    public static int getActionContextSize() {
        return nGetActionContextSize();
    }

    private static native int nGetActionContextSize(); /*
        return ax::NodeEditor::GetActionContextSize();
    */

    public static int getActionContextNodes(final long[] nodes, final int size) {
        return nGetActionContextNodes(nodes, size);
    }

    private static native int nGetActionContextNodes(long[] obj_nodes, int size); /*MANUAL
        auto nodes = obj_nodes == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_nodes, JNI_FALSE);
        auto _result = ax::NodeEditor::GetActionContextNodes(reinterpret_cast<ax::NodeEditor::NodeId*>(&nodes[0]), size);
        if (nodes != NULL) env->ReleasePrimitiveArrayCritical(obj_nodes, nodes, JNI_FALSE);
        return _result;
    */

    public static int getActionContextLinks(final long[] links, final int size) {
        return nGetActionContextLinks(links, size);
    }

    private static native int nGetActionContextLinks(long[] obj_links, int size); /*MANUAL
        auto links = obj_links == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_links, JNI_FALSE);
        auto _result = ax::NodeEditor::GetActionContextLinks(reinterpret_cast<ax::NodeEditor::LinkId*>(&links[0]), size);
        if (links != NULL) env->ReleasePrimitiveArrayCritical(obj_links, links, JNI_FALSE);
        return _result;
    */

    public static void endShortcut() {
        nEndShortcut();
    }

    private static native void nEndShortcut(); /*
        ax::NodeEditor::EndShortcut();
    */

    public static float getCurrentZoom() {
        return nGetCurrentZoom();
    }

    private static native float nGetCurrentZoom(); /*
        return ax::NodeEditor::GetCurrentZoom();
    */

    public static long getHoveredNode() {
        return nGetHoveredNode();
    }

    private static native long nGetHoveredNode(); /*
        return (uintptr_t)ax::NodeEditor::GetHoveredNode();
    */

    public static long getHoveredPin() {
        return nGetHoveredPin();
    }

    private static native long nGetHoveredPin(); /*
        return (uintptr_t)ax::NodeEditor::GetHoveredPin();
    */

    public static long getHoveredLink() {
        return nGetHoveredLink();
    }

    private static native long nGetHoveredLink(); /*
        return (uintptr_t)ax::NodeEditor::GetHoveredLink();
    */

    public static long getDoubleClickedNode() {
        return nGetDoubleClickedNode();
    }

    private static native long nGetDoubleClickedNode(); /*
        return (uintptr_t)ax::NodeEditor::GetDoubleClickedNode();
    */

    public static long getDoubleClickedPin() {
        return nGetDoubleClickedPin();
    }

    private static native long nGetDoubleClickedPin(); /*
        return (uintptr_t)ax::NodeEditor::GetDoubleClickedPin();
    */

    public static long getDoubleClickedLink() {
        return nGetDoubleClickedLink();
    }

    private static native long nGetDoubleClickedLink(); /*
        return (uintptr_t)ax::NodeEditor::GetDoubleClickedLink();
    */

    public static boolean isBackgroundClicked() {
        return nIsBackgroundClicked();
    }

    private static native boolean nIsBackgroundClicked(); /*
        return ax::NodeEditor::IsBackgroundClicked();
    */

    public static boolean isBackgroundDoubleClicked() {
        return nIsBackgroundDoubleClicked();
    }

    private static native boolean nIsBackgroundDoubleClicked(); /*
        return ax::NodeEditor::IsBackgroundDoubleClicked();
    */

    public static boolean getLinkPins(final long linkId, final ImLong startPinId, final ImLong endPinId) {
        return nGetLinkPins(linkId, startPinId != null ? startPinId.getData() : null, endPinId != null ? endPinId.getData() : null);
    }

    private static native boolean nGetLinkPins(long linkId, long[] obj_startPinId, long[] obj_endPinId); /*MANUAL
        auto startPinId = obj_startPinId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_startPinId, JNI_FALSE);
        auto endPinId = obj_endPinId == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_endPinId, JNI_FALSE);
        auto _result = ax::NodeEditor::GetLinkPins(static_cast<ax::NodeEditor::LinkId>(linkId), reinterpret_cast<ax::NodeEditor::PinId*>((startPinId != NULL ? &startPinId[0] : NULL)), reinterpret_cast<ax::NodeEditor::PinId*>((endPinId != NULL ? &endPinId[0] : NULL)));
        if (startPinId != NULL) env->ReleasePrimitiveArrayCritical(obj_startPinId, startPinId, JNI_FALSE);
        if (endPinId != NULL) env->ReleasePrimitiveArrayCritical(obj_endPinId, endPinId, JNI_FALSE);
        return _result;
    */

    public static boolean pinHadAnyLinks(final long pinId) {
        return nPinHadAnyLinks(pinId);
    }

    private static native boolean nPinHadAnyLinks(long pinId); /*
        return ax::NodeEditor::PinHadAnyLinks(static_cast<ax::NodeEditor::PinId>(pinId));
    */

    public static ImVec2 getScreenSize() {
        final ImVec2 dst = new ImVec2();
        nGetScreenSize(dst);
        return dst;
    }

    public static float getScreenSizeX() {
        return nGetScreenSizeX();
    }

    public static float getScreenSizeY() {
        return nGetScreenSizeY();
    }

    public static void getScreenSize(final ImVec2 dst) {
        nGetScreenSize(dst);
    }

    private static native void nGetScreenSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, ax::NodeEditor::GetScreenSize(), dst);
    */

    private static native float nGetScreenSizeX(); /*
        return ax::NodeEditor::GetScreenSize().x;
    */

    private static native float nGetScreenSizeY(); /*
        return ax::NodeEditor::GetScreenSize().y;
    */

    public static ImVec2 screenToCanvas(final ImVec2 pos) {
        final ImVec2 dst = new ImVec2();
        nScreenToCanvas(dst, pos.x, pos.y);
        return dst;
    }

    public static ImVec2 screenToCanvas(final float posX, final float posY) {
        final ImVec2 dst = new ImVec2();
        nScreenToCanvas(dst, posX, posY);
        return dst;
    }

    public static float screenToCanvasX(final ImVec2 pos) {
        return nScreenToCanvasX(pos.x, pos.y);
    }

    public static float screenToCanvasY(final ImVec2 pos) {
        return nScreenToCanvasY(pos.x, pos.y);
    }

    public static void screenToCanvas(final ImVec2 dst, final ImVec2 pos) {
        nScreenToCanvas(dst, pos.x, pos.y);
    }

    public static void screenToCanvas(final ImVec2 dst, final float posX, final float posY) {
        nScreenToCanvas(dst, posX, posY);
    }

    private static native void nScreenToCanvas(ImVec2 dst, float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        Jni::ImVec2Cpy(env, ax::NodeEditor::ScreenToCanvas(pos), dst);
    */

    private static native float nScreenToCanvasX(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ax::NodeEditor::ScreenToCanvas(pos).x;
        return _result;
    */

    private static native float nScreenToCanvasY(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ax::NodeEditor::ScreenToCanvas(pos).y;
        return _result;
    */

    public static ImVec2 canvasToScreen(final ImVec2 pos) {
        final ImVec2 dst = new ImVec2();
        nCanvasToScreen(dst, pos.x, pos.y);
        return dst;
    }

    public static ImVec2 canvasToScreen(final float posX, final float posY) {
        final ImVec2 dst = new ImVec2();
        nCanvasToScreen(dst, posX, posY);
        return dst;
    }

    public static float canvasToScreenX(final ImVec2 pos) {
        return nCanvasToScreenX(pos.x, pos.y);
    }

    public static float canvasToScreenY(final ImVec2 pos) {
        return nCanvasToScreenY(pos.x, pos.y);
    }

    public static void canvasToScreen(final ImVec2 dst, final ImVec2 pos) {
        nCanvasToScreen(dst, pos.x, pos.y);
    }

    public static void canvasToScreen(final ImVec2 dst, final float posX, final float posY) {
        nCanvasToScreen(dst, posX, posY);
    }

    private static native void nCanvasToScreen(ImVec2 dst, float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        Jni::ImVec2Cpy(env, ax::NodeEditor::CanvasToScreen(pos), dst);
    */

    private static native float nCanvasToScreenX(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ax::NodeEditor::CanvasToScreen(pos).x;
        return _result;
    */

    private static native float nCanvasToScreenY(float posX, float posY); /*MANUAL
        ImVec2 pos = ImVec2(posX, posY);
        auto _result = ax::NodeEditor::CanvasToScreen(pos).y;
        return _result;
    */

    public static int getNodeCount() {
        return nGetNodeCount();
    }

    private static native int nGetNodeCount(); /*
        return ax::NodeEditor::GetNodeCount();
    */

    public static int getOrderedNodeIds(final long[] nodes, final int size) {
        return nGetOrderedNodeIds(nodes, size);
    }

    private static native int nGetOrderedNodeIds(long[] obj_nodes, int size); /*MANUAL
        auto nodes = obj_nodes == NULL ? NULL : (long*)env->GetPrimitiveArrayCritical(obj_nodes, JNI_FALSE);
        auto _result = ax::NodeEditor::GetOrderedNodeIds(reinterpret_cast<ax::NodeEditor::NodeId*>(&nodes[0]), size);
        if (nodes != NULL) env->ReleasePrimitiveArrayCritical(obj_nodes, nodes, JNI_FALSE);
        return _result;
    */

    /*JNI
        #undef NodeEditorConfig
        #undef NodeEditorStyle
        #undef NodeEditorContext
     */
}
