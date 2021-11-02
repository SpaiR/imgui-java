package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImNodesStyle extends ImGuiStruct {
    public ImNodesStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_imnodes.h"

        #define IMNODES_STYLE ((ImNodesStyle*)STRUCT_PTR)
     */

    public native float getGridSpacing(); /*
       return IMNODES_STYLE->GridSpacing;
    */

    public native void setGridSpacing(float gridSpacing); /*
       IMNODES_STYLE->GridSpacing = gridSpacing;
    */

    public native float getNodeCornerRounding(); /*
       return IMNODES_STYLE->NodeCornerRounding;
    */

    public native void setNodeCornerRounding(float nodeCornerRounding); /*
       IMNODES_STYLE->NodeCornerRounding = nodeCornerRounding;
    */

    public native void getNodePadding(ImVec2 result); /*
       Jni::ImVec2Cpy(env, &IMNODES_STYLE->NodePadding, result);
    */

    public native void setNodePadding(float x, float y); /*
       IMNODES_STYLE->NodePadding = ImVec2(x, y);
    */

    public native float getNodeBorderThickness(); /*
       return IMNODES_STYLE->NodeBorderThickness;
    */

    public native void setNodeBorderThickness(float nodeBorderThickness); /*
       IMNODES_STYLE->NodeBorderThickness = nodeBorderThickness;
    */

    public native float getLinkThickness(); /*
       return IMNODES_STYLE->LinkThickness;
    */

    public native void setLinkThickness(float linkThickness); /*
       IMNODES_STYLE->LinkThickness = linkThickness;
    */

    public native float getLinkLineSegmentsPerLength(); /*
       return IMNODES_STYLE->LinkLineSegmentsPerLength;
    */

    public native void setLinkLineSegmentsPerLength(float linkLineSegmentsPerLength); /*
       IMNODES_STYLE->LinkLineSegmentsPerLength = linkLineSegmentsPerLength;
    */

    public native float getLinkHoverDistance(); /*
       return IMNODES_STYLE->LinkHoverDistance;
    */

    public native void setLinkHoverDistance(float linkHoverDistance); /*
       IMNODES_STYLE->LinkHoverDistance = linkHoverDistance;
    */

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    public native float getPinCircleRadius(); /*
       return IMNODES_STYLE->PinCircleRadius;
    */

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    public native void setPinCircleRadius(float pinCircleRadius); /*
       IMNODES_STYLE->PinCircleRadius = pinCircleRadius;
    */

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    public native float getPinQuadSideLength(); /*
       return IMNODES_STYLE->PinQuadSideLength;
    */

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    public native void setPinQuadSideLength(float pinQuadSideLength); /*
       IMNODES_STYLE->PinQuadSideLength = pinQuadSideLength;
    */

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    public native float getPinTriangleSideLength(); /*
       return IMNODES_STYLE->PinTriangleSideLength;
    */

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    public native void setPinTriangleSideLength(float pinTriangleSideLength); /*
       IMNODES_STYLE->PinTriangleSideLength = pinTriangleSideLength;
    */

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    public native float getPinLineThickness(); /*
       return IMNODES_STYLE->PinLineThickness;
    */

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    public native void setPinLineThickness(float pinLineThickness); /*
       IMNODES_STYLE->PinLineThickness = pinLineThickness;
    */

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    public native float getPinHoverRadius(); /*
       return IMNODES_STYLE->PinHoverRadius;
    */

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    public native void setPinHoverRadius(float pinHoverRadius); /*
       IMNODES_STYLE->PinHoverRadius = pinHoverRadius;
    */

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    public native float getPinOffset(); /*
       return IMNODES_STYLE->PinOffset;
    */

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    public native void setPinOffset(float pinOffset); /*
       IMNODES_STYLE->PinOffset = pinOffset;
    */

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public native void getMiniMapPadding(ImVec2 result); /*
       Jni::ImVec2Cpy(env, &IMNODES_STYLE->MiniMapPadding, result);
    */

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    public native void setMiniMapPadding(float x, float y); /*
       IMNODES_STYLE->MiniMapPadding = ImVec2(x, y);
    */

    /**
     * Mini-map offset from the screen side.
     */
    public native ImVec2 getMiniMapOffset(ImVec2 result); /*
       Jni::ImVec2Cpy(env, &IMNODES_STYLE->MiniMapOffset, result);
    */

    /**
     * Mini-map offset from the screen side.
     */
    public native void setMiniMapOffset(float x, float y); /*
       IMNODES_STYLE->MiniMapOffset = ImVec2(x, y);
    */
    
    public native int getFlags(); /*
       return IMNODES_STYLE->Flags;
    */

    public native void setFlags(int imNodesStyleFlags); /*
       IMNODES_STYLE->Flags = (ImNodesStyleFlags)imNodesStyleFlags;
    */
}
