package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;

public final class ImNodesStyle extends ImGuiStruct {
    public ImNodesStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_imnodes.h"
        #define THIS ((ImNodesStyle*)STRUCT_PTR)
     */

    public float getGridSpacing() {
        return nGetGridSpacing();
    }

    public void setGridSpacing(final float value) {
        nSetGridSpacing(value);
    }

    private native float nGetGridSpacing(); /*
        return THIS->GridSpacing;
    */

    private native void nSetGridSpacing(float value); /*
        THIS->GridSpacing = value;
    */

    public float getNodeCornerRounding() {
        return nGetNodeCornerRounding();
    }

    public void setNodeCornerRounding(final float value) {
        nSetNodeCornerRounding(value);
    }

    private native float nGetNodeCornerRounding(); /*
        return THIS->NodeCornerRounding;
    */

    private native void nSetNodeCornerRounding(float value); /*
        THIS->NodeCornerRounding = value;
    */

    public ImVec2 getNodePadding() {
        final ImVec2 dst = new ImVec2();
        nGetNodePadding(dst);
        return dst;
    }

    public float getNodePaddingX() {
        return nGetNodePaddingX();
    }

    public float getNodePaddingY() {
        return nGetNodePaddingY();
    }

    public void getNodePadding(final ImVec2 dst) {
        nGetNodePadding(dst);
    }

    public void setNodePadding(final ImVec2 value) {
        nSetNodePadding(value.x, value.y);
    }

    public void setNodePadding(final float valueX, final float valueY) {
        nSetNodePadding(valueX, valueY);
    }

    private native void nGetNodePadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->NodePadding, dst);
    */

    private native float nGetNodePaddingX(); /*
        return THIS->NodePadding.x;
    */

    private native float nGetNodePaddingY(); /*
        return THIS->NodePadding.y;
    */

    private native void nSetNodePadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->NodePadding = value;
    */

    public float getNodeBorderThickness() {
        return nGetNodeBorderThickness();
    }

    public void setNodeBorderThickness(final float value) {
        nSetNodeBorderThickness(value);
    }

    private native float nGetNodeBorderThickness(); /*
        return THIS->NodeBorderThickness;
    */

    private native void nSetNodeBorderThickness(float value); /*
        THIS->NodeBorderThickness = value;
    */

    public float getLinkThickness() {
        return nGetLinkThickness();
    }

    public void setLinkThickness(final float value) {
        nSetLinkThickness(value);
    }

    private native float nGetLinkThickness(); /*
        return THIS->LinkThickness;
    */

    private native void nSetLinkThickness(float value); /*
        THIS->LinkThickness = value;
    */

    public float getLinkLineSegmentsPerLength() {
        return nGetLinkLineSegmentsPerLength();
    }

    public void setLinkLineSegmentsPerLength(final float value) {
        nSetLinkLineSegmentsPerLength(value);
    }

    private native float nGetLinkLineSegmentsPerLength(); /*
        return THIS->LinkLineSegmentsPerLength;
    */

    private native void nSetLinkLineSegmentsPerLength(float value); /*
        THIS->LinkLineSegmentsPerLength = value;
    */

    public float getLinkHoverDistance() {
        return nGetLinkHoverDistance();
    }

    public void setLinkHoverDistance(final float value) {
        nSetLinkHoverDistance(value);
    }

    private native float nGetLinkHoverDistance(); /*
        return THIS->LinkHoverDistance;
    */

    private native void nSetLinkHoverDistance(float value); /*
        THIS->LinkHoverDistance = value;
    */

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    public float getPinCircleRadius() {
        return nGetPinCircleRadius();
    }

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    public void setPinCircleRadius(final float value) {
        nSetPinCircleRadius(value);
    }

    private native float nGetPinCircleRadius(); /*
        return THIS->PinCircleRadius;
    */

    private native void nSetPinCircleRadius(float value); /*
        THIS->PinCircleRadius = value;
    */

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    public float getPinQuadSideLength() {
        return nGetPinQuadSideLength();
    }

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    public void setPinQuadSideLength(final float value) {
        nSetPinQuadSideLength(value);
    }

    private native float nGetPinQuadSideLength(); /*
        return THIS->PinQuadSideLength;
    */

    private native void nSetPinQuadSideLength(float value); /*
        THIS->PinQuadSideLength = value;
    */

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    public float getPinTriangleSideLength() {
        return nGetPinTriangleSideLength();
    }

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    public void setPinTriangleSideLength(final float value) {
        nSetPinTriangleSideLength(value);
    }

    private native float nGetPinTriangleSideLength(); /*
        return THIS->PinTriangleSideLength;
    */

    private native void nSetPinTriangleSideLength(float value); /*
        THIS->PinTriangleSideLength = value;
    */

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    public float getPinLineThickness() {
        return nGetPinLineThickness();
    }

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    public void setPinLineThickness(final float value) {
        nSetPinLineThickness(value);
    }

    private native float nGetPinLineThickness(); /*
        return THIS->PinLineThickness;
    */

    private native void nSetPinLineThickness(float value); /*
        THIS->PinLineThickness = value;
    */

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    public float getPinHoverRadius() {
        return nGetPinHoverRadius();
    }

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    public void setPinHoverRadius(final float value) {
        nSetPinHoverRadius(value);
    }

    private native float nGetPinHoverRadius(); /*
        return THIS->PinHoverRadius;
    */

    private native void nSetPinHoverRadius(float value); /*
        THIS->PinHoverRadius = value;
    */

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    public float getPinOffset() {
        return nGetPinOffset();
    }

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    public void setPinOffset(final float value) {
        nSetPinOffset(value);
    }

    private native float nGetPinOffset(); /*
        return THIS->PinOffset;
    */

    private native void nSetPinOffset(float value); /*
        THIS->PinOffset = value;
    */

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public ImVec2 getMiniMapPadding() {
        final ImVec2 dst = new ImVec2();
        nGetMiniMapPadding(dst);
        return dst;
    }

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public float getMiniMapPaddingX() {
        return nGetMiniMapPaddingX();
    }

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public float getMiniMapPaddingY() {
        return nGetMiniMapPaddingY();
    }

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public void getMiniMapPadding(final ImVec2 dst) {
        nGetMiniMapPadding(dst);
    }

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public void setMiniMapPadding(final ImVec2 value) {
        nSetMiniMapPadding(value.x, value.y);
    }

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public void setMiniMapPadding(final float valueX, final float valueY) {
        nSetMiniMapPadding(valueX, valueY);
    }

    private native void nGetMiniMapPadding(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MiniMapPadding, dst);
    */

    private native float nGetMiniMapPaddingX(); /*
        return THIS->MiniMapPadding.x;
    */

    private native float nGetMiniMapPaddingY(); /*
        return THIS->MiniMapPadding.y;
    */

    private native void nSetMiniMapPadding(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MiniMapPadding = value;
    */

    /**
     * Mini-map offset from the screen side.
     */
    public ImVec2 getMiniMapOffset() {
        final ImVec2 dst = new ImVec2();
        nGetMiniMapOffset(dst);
        return dst;
    }

    /**
     * Mini-map offset from the screen side.
     */
    public float getMiniMapOffsetX() {
        return nGetMiniMapOffsetX();
    }

    /**
     * Mini-map offset from the screen side.
     */
    public float getMiniMapOffsetY() {
        return nGetMiniMapOffsetY();
    }

    /**
     * Mini-map offset from the screen side.
     */
    public void getMiniMapOffset(final ImVec2 dst) {
        nGetMiniMapOffset(dst);
    }

    /**
     * Mini-map offset from the screen side.
     */
    public void setMiniMapOffset(final ImVec2 value) {
        nSetMiniMapOffset(value.x, value.y);
    }

    /**
     * Mini-map offset from the screen side.
     */
    public void setMiniMapOffset(final float valueX, final float valueY) {
        nSetMiniMapOffset(valueX, valueY);
    }

    private native void nGetMiniMapOffset(ImVec2 dst); /*
        Jni::ImVec2Cpy(env, THIS->MiniMapOffset, dst);
    */

    private native float nGetMiniMapOffsetX(); /*
        return THIS->MiniMapOffset.x;
    */

    private native float nGetMiniMapOffsetY(); /*
        return THIS->MiniMapOffset.y;
    */

    private native void nSetMiniMapOffset(float valueX, float valueY); /*MANUAL
        ImVec2 value = ImVec2(valueX, valueY);
        THIS->MiniMapOffset = value;
    */

    /**
     * By default, ImNodesStyleFlags_NodeOutline and ImNodesStyleFlags_Gridlines are enabled.
     */
    public int getFlags() {
        return nGetFlags();
    }

    /**
     * By default, ImNodesStyleFlags_NodeOutline and ImNodesStyleFlags_Gridlines are enabled.
     */
    public void setFlags(final int value) {
        nSetFlags(value);
    }

    /**
     * By default, ImNodesStyleFlags_NodeOutline and ImNodesStyleFlags_Gridlines are enabled.
     */
    public void addFlags(final int flags) {
        setFlags(getFlags() | flags);
    }

    /**
     * By default, ImNodesStyleFlags_NodeOutline and ImNodesStyleFlags_Gridlines are enabled.
     */
    public void removeFlags(final int flags) {
        setFlags(getFlags() & ~(flags));
    }

    /**
     * By default, ImNodesStyleFlags_NodeOutline and ImNodesStyleFlags_Gridlines are enabled.
     */
    public boolean hasFlags(final int flags) {
        return (getFlags() & flags) != 0;
    }

    private native int nGetFlags(); /*
        return THIS->Flags;
    */

    private native void nSetFlags(int value); /*
        THIS->Flags = value;
    */

    public int[] getColors() {
        return nGetColors();
    }

    public int getColors(final int idx) {
        return nGetColors(idx);
    }

    public void setColors(final int[] value) {
        nSetColors(value);
    }

    public void setColors(final int idx, final int value) {
        nSetColors(idx, value);
    }

    private native int[] nGetColors(); /*
        jint jBuf[ImNodesCol_COUNT];
        for (int i = 0; i < ImNodesCol_COUNT; i++)
            jBuf[i] = THIS->Colors[i];
        jintArray result = env->NewIntArray(ImNodesCol_COUNT);
        env->SetIntArrayRegion(result, 0, ImNodesCol_COUNT, jBuf);
        return result;
    */

    private native int nGetColors(int idx); /*
        return THIS->Colors[idx];
    */

    private native void nSetColors(int[] value); /*
        for (int i = 0; i < ImNodesCol_COUNT; i++)
            THIS->Colors[i] = value[i];
    */

    private native void nSetColors(int idx, int value); /*
        THIS->Colors[idx] = value;
    */

    /*JNI
        #undef THIS
     */
}
