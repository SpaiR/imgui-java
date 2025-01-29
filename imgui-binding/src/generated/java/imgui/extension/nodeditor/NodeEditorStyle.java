package imgui.extension.nodeditor;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;

public final class NodeEditorStyle extends ImGuiStruct {
    public NodeEditorStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_nodeeditor.h"
        #define THIS ((ax::NodeEditor::Style*)STRUCT_PTR)
     */

    public ImVec4 getNodePadding() {
        final ImVec4 dst = new ImVec4();
        nGetNodePadding(dst);
        return dst;
    }

    public float getNodePaddingX() {
        return nGetNodePaddingX();
    }

    public float getNodePaddingY() {
        return nGetNodePaddingY();
    }

    public float getNodePaddingZ() {
        return nGetNodePaddingZ();
    }

    public float getNodePaddingW() {
        return nGetNodePaddingW();
    }

    public void getNodePadding(final ImVec4 dst) {
        nGetNodePadding(dst);
    }

    public void setNodePadding(final ImVec4 value) {
        nSetNodePadding(value.x, value.y, value.z, value.w);
    }

    public void setNodePadding(final float valueX, final float valueY, final float valueZ, final float valueW) {
        nSetNodePadding(valueX, valueY, valueZ, valueW);
    }

    private native void nGetNodePadding(ImVec4 dst); /*
        Jni::ImVec4Cpy(env, THIS->NodePadding, dst);
    */

    private native float nGetNodePaddingX(); /*
        return THIS->NodePadding.x;
    */

    private native float nGetNodePaddingY(); /*
        return THIS->NodePadding.y;
    */

    private native float nGetNodePaddingZ(); /*
        return THIS->NodePadding.z;
    */

    private native float nGetNodePaddingW(); /*
        return THIS->NodePadding.w;
    */

    private native void nSetNodePadding(float valueX, float valueY, float valueZ, float valueW); /*MANUAL
        ImVec4 value = ImVec4(valueX, valueY, valueZ, valueW);
        THIS->NodePadding = value;
    */

    public float getNodeRounding() {
        return nGetNodeRounding();
    }

    public void setNodeRounding(final float value) {
        nSetNodeRounding(value);
    }

    private native float nGetNodeRounding(); /*
        return THIS->NodeRounding;
    */

    private native void nSetNodeRounding(float value); /*
        THIS->NodeRounding = value;
    */

    public float getNodeBorderWidth() {
        return nGetNodeBorderWidth();
    }

    public void setNodeBorderWidth(final float value) {
        nSetNodeBorderWidth(value);
    }

    private native float nGetNodeBorderWidth(); /*
        return THIS->NodeBorderWidth;
    */

    private native void nSetNodeBorderWidth(float value); /*
        THIS->NodeBorderWidth = value;
    */

    public float getHoveredNodeBorderWidth() {
        return nGetHoveredNodeBorderWidth();
    }

    public void setHoveredNodeBorderWidth(final float value) {
        nSetHoveredNodeBorderWidth(value);
    }

    private native float nGetHoveredNodeBorderWidth(); /*
        return THIS->HoveredNodeBorderWidth;
    */

    private native void nSetHoveredNodeBorderWidth(float value); /*
        THIS->HoveredNodeBorderWidth = value;
    */

    public float getHoverNodeBorderOffset() {
        return nGetHoverNodeBorderOffset();
    }

    public void setHoverNodeBorderOffset(final float value) {
        nSetHoverNodeBorderOffset(value);
    }

    private native float nGetHoverNodeBorderOffset(); /*
        return THIS->HoverNodeBorderOffset;
    */

    private native void nSetHoverNodeBorderOffset(float value); /*
        THIS->HoverNodeBorderOffset = value;
    */

    public float getSelectedNodeBorderWidth() {
        return nGetSelectedNodeBorderWidth();
    }

    public void setSelectedNodeBorderWidth(final float value) {
        nSetSelectedNodeBorderWidth(value);
    }

    private native float nGetSelectedNodeBorderWidth(); /*
        return THIS->SelectedNodeBorderWidth;
    */

    private native void nSetSelectedNodeBorderWidth(float value); /*
        THIS->SelectedNodeBorderWidth = value;
    */

    public float getSelectedNodeBorderOffset() {
        return nGetSelectedNodeBorderOffset();
    }

    public void setSelectedNodeBorderOffset(final float value) {
        nSetSelectedNodeBorderOffset(value);
    }

    private native float nGetSelectedNodeBorderOffset(); /*
        return THIS->SelectedNodeBorderOffset;
    */

    private native void nSetSelectedNodeBorderOffset(float value); /*
        THIS->SelectedNodeBorderOffset = value;
    */

    public float getPinRounding() {
        return nGetPinRounding();
    }

    public void setPinRounding(final float value) {
        nSetPinRounding(value);
    }

    private native float nGetPinRounding(); /*
        return THIS->PinRounding;
    */

    private native void nSetPinRounding(float value); /*
        THIS->PinRounding = value;
    */

    public float getPinBorderWidth() {
        return nGetPinBorderWidth();
    }

    public void setPinBorderWidth(final float value) {
        nSetPinBorderWidth(value);
    }

    private native float nGetPinBorderWidth(); /*
        return THIS->PinBorderWidth;
    */

    private native void nSetPinBorderWidth(float value); /*
        THIS->PinBorderWidth = value;
    */

    public float getLinkStrength() {
        return nGetLinkStrength();
    }

    public void setLinkStrength(final float value) {
        nSetLinkStrength(value);
    }

    private native float nGetLinkStrength(); /*
        return THIS->LinkStrength;
    */

    private native void nSetLinkStrength(float value); /*
        THIS->LinkStrength = value;
    */

    public ImVec2 getSourceDirection() {
        final ImVec2 dst = new ImVec2();
        nGetSourceDirection(dst);
        return dst;
    }

    public float getSourceDirectionX() {
        return nGetSourceDirectionX();
    }

    public float getSourceDirectionY() {
        return nGetSourceDirectionY();
    }

    public void getSourceDirection(final ImVec2 dst) {
        nGetSourceDirection(dst);
    }

    public void setSourceDirection(final ImVec2 value) {
        nSetSourceDirection(value.x, value.y);
    }

    public void setSourceDirection(final float valueX, final float valueY) {
        nSetSourceDirection(valueX, valueY);
    }

    private native void nGetSourceDirection(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->SourceDirection, dst);
    */

    private native float nGetSourceDirectionX(); /*
        return THIS->SourceDirection.x;
    */

    private native float nGetSourceDirectionY(); /*
        return THIS->SourceDirection.y;
    */

    private native void nSetSourceDirection(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->SourceDirection = value;
    */

    public ImVec2 getTargetDirection() {
        final ImVec2 dst = new ImVec2();
        nGetTargetDirection(dst);
        return dst;
    }

    public float getTargetDirectionX() {
        return nGetTargetDirectionX();
    }

    public float getTargetDirectionY() {
        return nGetTargetDirectionY();
    }

    public void getTargetDirection(final ImVec2 dst) {
        nGetTargetDirection(dst);
    }

    public void setTargetDirection(final ImVec2 value) {
        nSetTargetDirection(value.x, value.y);
    }

    public void setTargetDirection(final float valueX, final float valueY) {
        nSetTargetDirection(valueX, valueY);
    }

    private native void nGetTargetDirection(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->TargetDirection, dst);
    */

    private native float nGetTargetDirectionX(); /*
        return THIS->TargetDirection.x;
    */

    private native float nGetTargetDirectionY(); /*
        return THIS->TargetDirection.y;
    */

    private native void nSetTargetDirection(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->TargetDirection = value;
    */

    public float getScrollDuration() {
        return nGetScrollDuration();
    }

    public void setScrollDuration(final float value) {
        nSetScrollDuration(value);
    }

    private native float nGetScrollDuration(); /*
        return THIS->ScrollDuration;
    */

    private native void nSetScrollDuration(float value); /*
        THIS->ScrollDuration = value;
    */

    public float getFlowMarkerDistance() {
        return nGetFlowMarkerDistance();
    }

    public void setFlowMarkerDistance(final float value) {
        nSetFlowMarkerDistance(value);
    }

    private native float nGetFlowMarkerDistance(); /*
        return THIS->FlowMarkerDistance;
    */

    private native void nSetFlowMarkerDistance(float value); /*
        THIS->FlowMarkerDistance = value;
    */

    public float getFlowSpeed() {
        return nGetFlowSpeed();
    }

    public void setFlowSpeed(final float value) {
        nSetFlowSpeed(value);
    }

    private native float nGetFlowSpeed(); /*
        return THIS->FlowSpeed;
    */

    private native void nSetFlowSpeed(float value); /*
        THIS->FlowSpeed = value;
    */

    public float getFlowDuration() {
        return nGetFlowDuration();
    }

    public void setFlowDuration(final float value) {
        nSetFlowDuration(value);
    }

    private native float nGetFlowDuration(); /*
        return THIS->FlowDuration;
    */

    private native void nSetFlowDuration(float value); /*
        THIS->FlowDuration = value;
    */

    public ImVec2 getPivotAlignment() {
        final ImVec2 dst = new ImVec2();
        nGetPivotAlignment(dst);
        return dst;
    }

    public float getPivotAlignmentX() {
        return nGetPivotAlignmentX();
    }

    public float getPivotAlignmentY() {
        return nGetPivotAlignmentY();
    }

    public void getPivotAlignment(final ImVec2 dst) {
        nGetPivotAlignment(dst);
    }

    public void setPivotAlignment(final ImVec2 value) {
        nSetPivotAlignment(value.x, value.y);
    }

    public void setPivotAlignment(final float valueX, final float valueY) {
        nSetPivotAlignment(valueX, valueY);
    }

    private native void nGetPivotAlignment(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->PivotAlignment, dst);
    */

    private native float nGetPivotAlignmentX(); /*
        return THIS->PivotAlignment.x;
    */

    private native float nGetPivotAlignmentY(); /*
        return THIS->PivotAlignment.y;
    */

    private native void nSetPivotAlignment(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->PivotAlignment = value;
    */

    public ImVec2 getPivotSize() {
        final ImVec2 dst = new ImVec2();
        nGetPivotSize(dst);
        return dst;
    }

    public float getPivotSizeX() {
        return nGetPivotSizeX();
    }

    public float getPivotSizeY() {
        return nGetPivotSizeY();
    }

    public void getPivotSize(final ImVec2 dst) {
        nGetPivotSize(dst);
    }

    public void setPivotSize(final ImVec2 value) {
        nSetPivotSize(value.x, value.y);
    }

    public void setPivotSize(final float valueX, final float valueY) {
        nSetPivotSize(valueX, valueY);
    }

    private native void nGetPivotSize(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->PivotSize, dst);
    */

    private native float nGetPivotSizeX(); /*
        return THIS->PivotSize.x;
    */

    private native float nGetPivotSizeY(); /*
        return THIS->PivotSize.y;
    */

    private native void nSetPivotSize(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->PivotSize = value;
    */

    public ImVec2 getPivotScale() {
        final ImVec2 dst = new ImVec2();
        nGetPivotScale(dst);
        return dst;
    }

    public float getPivotScaleX() {
        return nGetPivotScaleX();
    }

    public float getPivotScaleY() {
        return nGetPivotScaleY();
    }

    public void getPivotScale(final ImVec2 dst) {
        nGetPivotScale(dst);
    }

    public void setPivotScale(final ImVec2 value) {
        nSetPivotScale(value.x, value.y);
    }

    public void setPivotScale(final float valueX, final float valueY) {
        nSetPivotScale(valueX, valueY);
    }

    private native void nGetPivotScale(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->PivotScale, dst);
    */

    private native float nGetPivotScaleX(); /*
        return THIS->PivotScale.x;
    */

    private native float nGetPivotScaleY(); /*
        return THIS->PivotScale.y;
    */

    private native void nSetPivotScale(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->PivotScale = value;
    */

    public float getPinCorners() {
        return nGetPinCorners();
    }

    public void setPinCorners(final float value) {
        nSetPinCorners(value);
    }

    private native float nGetPinCorners(); /*
        return THIS->PinCorners;
    */

    private native void nSetPinCorners(float value); /*
        THIS->PinCorners = value;
    */

    public float getPinRadius() {
        return nGetPinRadius();
    }

    public void setPinRadius(final float value) {
        nSetPinRadius(value);
    }

    private native float nGetPinRadius(); /*
        return THIS->PinRadius;
    */

    private native void nSetPinRadius(float value); /*
        THIS->PinRadius = value;
    */

    public float getPinArrowSize() {
        return nGetPinArrowSize();
    }

    public void setPinArrowSize(final float value) {
        nSetPinArrowSize(value);
    }

    private native float nGetPinArrowSize(); /*
        return THIS->PinArrowSize;
    */

    private native void nSetPinArrowSize(float value); /*
        THIS->PinArrowSize = value;
    */

    public float getPinArrowWidth() {
        return nGetPinArrowWidth();
    }

    public void setPinArrowWidth(final float value) {
        nSetPinArrowWidth(value);
    }

    private native float nGetPinArrowWidth(); /*
        return THIS->PinArrowWidth;
    */

    private native void nSetPinArrowWidth(float value); /*
        THIS->PinArrowWidth = value;
    */

    public float getGroupRounding() {
        return nGetGroupRounding();
    }

    public void setGroupRounding(final float value) {
        nSetGroupRounding(value);
    }

    private native float nGetGroupRounding(); /*
        return THIS->GroupRounding;
    */

    private native void nSetGroupRounding(float value); /*
        THIS->GroupRounding = value;
    */

    public float getGroupBorderWidth() {
        return nGetGroupBorderWidth();
    }

    public void setGroupBorderWidth(final float value) {
        nSetGroupBorderWidth(value);
    }

    private native float nGetGroupBorderWidth(); /*
        return THIS->GroupBorderWidth;
    */

    private native void nSetGroupBorderWidth(float value); /*
        THIS->GroupBorderWidth = value;
    */

    public float getHighlightConnectedLinks() {
        return nGetHighlightConnectedLinks();
    }

    public void setHighlightConnectedLinks(final float value) {
        nSetHighlightConnectedLinks(value);
    }

    private native float nGetHighlightConnectedLinks(); /*
        return THIS->HighlightConnectedLinks;
    */

    private native void nSetHighlightConnectedLinks(float value); /*
        THIS->HighlightConnectedLinks = value;
    */

    public float getSnapLinkToPinDir() {
        return nGetSnapLinkToPinDir();
    }

    public void setSnapLinkToPinDir(final float value) {
        nSetSnapLinkToPinDir(value);
    }

    private native float nGetSnapLinkToPinDir(); /*
        return THIS->SnapLinkToPinDir;
    */

    private native void nSetSnapLinkToPinDir(float value); /*
        THIS->SnapLinkToPinDir = value;
    */

    public ImVec4[] getColors() {
        return nGetColors();
    }

    public void setColors(final ImVec4[] value) {
        nSetColors(value);
    }

    private native ImVec4[] nGetColors(); /*
        return Jni::NewImVec4Array(env, THIS->Colors, ax::NodeEditor::StyleColor_Count);
    */

    private native void nSetColors(ImVec4[] value); /*
        Jni::ImVec4ArrayCpy(env, value, THIS->Colors, ax::NodeEditor::StyleColor_Count);
    */

    /*JNI
        #undef THIS
     */
}
