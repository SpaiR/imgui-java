package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.TypeArray;

@BindingSource
public final class ImNodesStyle extends ImGuiStruct {
    public ImNodesStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_imnodes.h"
        #define THIS ((ImNodesStyle*)STRUCT_PTR)
     */

    @BindingField
    public float GridSpacing;

    @BindingField
    public float NodeCornerRounding;

    @BindingField
    public ImVec2 NodePadding;

    @BindingField
    public float NodeBorderThickness;

    @BindingField
    public float LinkThickness;

    @BindingField
    public float LinkLineSegmentsPerLength;

    @BindingField
    public float LinkHoverDistance;

    /**
     * The circle radius used when the pin shape is either PinShape_Circle or PinShape_CircleFilled.
     */
    @BindingField
    public float PinCircleRadius;

    /**
     * The quad side length used when the shape is either PinShape_Quad or PinShape_QuadFilled.
     */
    @BindingField
    public float PinQuadSideLength;

    /**
     * The equilateral triangle side length used when the pin shape is either PinShape_Triangle or PinShape_TriangleFilled.
     */
    @BindingField
    public float PinTriangleSideLength;

    /**
     * The thickness of the line used when the pin shape is not filled.
     */
    @BindingField
    public float PinLineThickness;

    /**
     * The radius from the pin's center position inside of which it is detected as being hovered over.
     */
    @BindingField
    public float PinHoverRadius;

    /**
     * Offsets the pins' positions from the edge of the node to the outside of the node.
     */
    @BindingField
    public float PinOffset;

    /**
     * Mini-map padding size between mini-map edge and mini-map content.
     */
    @BindingField
    public ImVec2 MiniMapPadding;

    /**
     * Mini-map offset from the screen side.
     */
    @BindingField
    public ImVec2 MiniMapOffset;

    /**
     * By default, ImNodesStyleFlags_NodeOutline and ImNodesStyleFlags_Gridlines are enabled.
     */
    @BindingField(isFlag = true)
    public int Flags;

    @BindingField
    @TypeArray(type = "int", size = "ImNodesCol_COUNT")
    public int[] Colors;

    /*JNI
        #undef THIS
     */
}
