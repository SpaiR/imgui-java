package imgui.extension.nodeditor;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.TypeArray;

@BindingSource
public final class NodeEditorStyle extends ImGuiStruct {
    public NodeEditorStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_nodeeditor.h"
        #define THIS ((ax::NodeEditor::Style*)STRUCT_PTR)
     */

    @BindingField
    public ImVec4 NodePadding;

    @BindingField
    public float NodeRounding;

    @BindingField
    public float NodeBorderWidth;

    @BindingField
    public float HoveredNodeBorderWidth;

    @BindingField
    public float HoverNodeBorderOffset;

    @BindingField
    public float SelectedNodeBorderWidth;

    @BindingField
    public float SelectedNodeBorderOffset;

    @BindingField
    public float PinRounding;

    @BindingField
    public float PinBorderWidth;

    @BindingField
    public float LinkStrength;

    @BindingField
    public ImVec2 SourceDirection;

    @BindingField
    public ImVec2 TargetDirection;

    @BindingField
    public float ScrollDuration;

    @BindingField
    public float FlowMarkerDistance;

    @BindingField
    public float FlowSpeed;

    @BindingField
    public float FlowDuration;

    @BindingField
    public ImVec2 PivotAlignment;

    @BindingField
    public ImVec2 PivotSize;

    @BindingField
    public ImVec2 PivotScale;

    @BindingField
    public float PinCorners;

    @BindingField
    public float PinRadius;

    @BindingField
    public float PinArrowSize;

    @BindingField
    public float PinArrowWidth;

    @BindingField
    public float GroupRounding;

    @BindingField
    public float GroupBorderWidth;

    @BindingField
    public float HighlightConnectedLinks;

    @BindingField
    public float SnapLinkToPinDir;

    @BindingField
    @TypeArray(type = "ImVec4", size = "ax::NodeEditor::StyleColor_Count")
    public ImVec4[] Colors;

    /*JNI
        #undef THIS
     */
}
