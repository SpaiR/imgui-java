package imgui.extension.imnodes;

import imgui.binding.ImGuiStruct;

public final class ImNodesStyle extends ImGuiStruct {
    public ImNodesStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_imnodes.h"

        #define IMNODES_STYLE ((imnodes::Style*)STRUCT_PTR)
     */

    public native float getGridSpacing(); /*
       return IMNODES_STYLE->grid_spacing;
    */

    public native void setGridSpacing(float gridSpacing); /*
       IMNODES_STYLE->grid_spacing = gridSpacing;
    */

    public native float getNodeCornerRounding(); /*
       return IMNODES_STYLE->node_corner_rounding;
    */

    public native void setNodeCornerRounding(float nodeCornerRounding); /*
       IMNODES_STYLE->node_corner_rounding = nodeCornerRounding;
    */

    public native float getNodePaddingHorizontal(); /*
       return IMNODES_STYLE->node_padding_horizontal;
    */

    public native void setNodePaddingHorizontal(float nodePaddingHorizontal); /*
       IMNODES_STYLE->node_padding_horizontal = nodePaddingHorizontal;
    */

    public native float getNodePaddingVertical(); /*
       return IMNODES_STYLE->node_padding_vertical;
    */

    public native void setNodePaddingVertical(float nodePaddingVertical); /*
       IMNODES_STYLE->node_padding_vertical = nodePaddingVertical;
    */

    public native float getNodeBorderThickness(); /*
       return IMNODES_STYLE->node_border_thickness;
    */

    public native void setNodeBorderThickness(float nodeBorderThickness); /*
       IMNODES_STYLE->node_border_thickness = nodeBorderThickness;
    */

    public native float getLinkThickness(); /*
       return IMNODES_STYLE->link_thickness;
    */

    public native void setLinkThickness(float linkThickness); /*
       IMNODES_STYLE->link_thickness = linkThickness;
    */

    public native float getLinkLineSegmentsPerLength(); /*
       return IMNODES_STYLE->link_line_segments_per_length;
    */

    public native void setLinkLineSegmentsPerLength(float linkLineSegmentsPerLength); /*
       IMNODES_STYLE->link_line_segments_per_length = linkLineSegmentsPerLength;
    */

    public native float getLinkHoverDistance(); /*
       return IMNODES_STYLE->link_hover_distance;
    */

    public native void setLinkHoverDistance(float linkHoverDistance); /*
       IMNODES_STYLE->link_hover_distance = linkHoverDistance;
    */

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    public native float getPinCircleRadius(); /*
       return IMNODES_STYLE->pin_circle_radius;
    */

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    public native void setPinCircleRadius(float pinCircleRadius); /*
       IMNODES_STYLE->pin_circle_radius = pinCircleRadius;
    */

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    public native float getPinQuadSideLength(); /*
       return IMNODES_STYLE->pin_quad_side_length;
    */

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    public native void setPinQuadSideLength(float pinQuadSideLength); /*
       IMNODES_STYLE->pin_quad_side_length = pinQuadSideLength;
    */

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    public native float getPinTriangleSideLength(); /*
       return IMNODES_STYLE->pin_triangle_side_length;
    */

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    public native void setPinTriangleSideLength(float pinTriangleSideLength); /*
       IMNODES_STYLE->pin_triangle_side_length = pinTriangleSideLength;
    */

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    public native float getPinLineThickness(); /*
       return IMNODES_STYLE->pin_line_thickness;
    */

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    public native void setPinLineThickness(float pinLineThickness); /*
       IMNODES_STYLE->pin_line_thickness = pinLineThickness;
    */

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    public native float getPinHoverRadius(); /*
       return IMNODES_STYLE->pin_hover_radius;
    */

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    public native void setPinHoverRadius(float pinHoverRadius); /*
       IMNODES_STYLE->pin_hover_radius = pinHoverRadius;
    */

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    public native float getPinOffset(); /*
       return IMNODES_STYLE->pin_offset;
    */

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    public native void setPinOffset(float pinOffset); /*
       IMNODES_STYLE->pin_offset = pinOffset;
    */

    public native int getFlags(); /*
       return IMNODES_STYLE->flags;
    */

    public native void setFlags(int imNodesStyleFlags); /*
       IMNODES_STYLE->flags = (imnodes::StyleFlags)imNodesStyleFlags;
    */
}
