package imgui.extension.nodeditor;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;

public final class NodeEditorStyle extends ImGuiStruct {
    public NodeEditorStyle(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include <stdint.h>
        #include <imgui.h>
        #include <imgui_node_editor.h>
        #include <imgui_node_editor_internal.h>
        #include "jni_common.h"
        #include "jni_binding_struct.h"

        namespace ed = ax::NodeEditor;

        #define IM_NODE_EDITOR_STYLE ((ed::Style*)STRUCT_PTR)
     */

    public native void getNodePadding(ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, IM_NODE_EDITOR_STYLE->NodePadding, dstImVec4);
    */

    public native void setNodePadding(float x, float y, float z, float w); /*
        IM_NODE_EDITOR_STYLE->NodePadding.x = x;
        IM_NODE_EDITOR_STYLE->NodePadding.y = y;
        IM_NODE_EDITOR_STYLE->NodePadding.z = z;
        IM_NODE_EDITOR_STYLE->NodePadding.w = w;
    */

    public native float getNodeRounding(); /*
       return IM_NODE_EDITOR_STYLE->NodeRounding;
    */

    public native void setNodeRounding(float nodeRounding); /*
       IM_NODE_EDITOR_STYLE->NodeRounding = nodeRounding;
    */

    public native float getNodeBorderWidth(); /*
       return IM_NODE_EDITOR_STYLE->NodeBorderWidth;
    */

    public native void setNodeBorderWidth(float nodeBorderWidth); /*
       IM_NODE_EDITOR_STYLE->NodeBorderWidth = nodeBorderWidth;
    */

    public native float getHoveredNodeBorderWidth(); /*
       return IM_NODE_EDITOR_STYLE->HoveredNodeBorderWidth;
    */

    public native void setHoveredNodeBorderWidth(float hoveredNodeBorderWidth); /*
       IM_NODE_EDITOR_STYLE->HoveredNodeBorderWidth = hoveredNodeBorderWidth;
    */

    public native float getSelectedNodeBorderWidth(); /*
       return IM_NODE_EDITOR_STYLE->SelectedNodeBorderWidth;
    */

    public native void setSelectedNodeBorderWidth(float selectedNodeBorderWidth); /*
       IM_NODE_EDITOR_STYLE->SelectedNodeBorderWidth = selectedNodeBorderWidth;
    */

    public native float getPinRounding(); /*
       return IM_NODE_EDITOR_STYLE->PinRounding;
    */

    public native void setPinRounding(float pinRounding); /*
       IM_NODE_EDITOR_STYLE->PinRounding = pinRounding;
    */

    public native float getPinBorderWidth(); /*
       return IM_NODE_EDITOR_STYLE->PinBorderWidth;
    */

    public native void setPinBorderWidth(float pinBorderWidth); /*
       IM_NODE_EDITOR_STYLE->PinBorderWidth = pinBorderWidth;
    */

    public native float getLinkStrength(); /*
       return IM_NODE_EDITOR_STYLE->LinkStrength;
    */

    public native void setLinkStrength(float linkStrength); /*
       IM_NODE_EDITOR_STYLE->LinkStrength = linkStrength;
    */

    public native void getSourceDirection(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IM_NODE_EDITOR_STYLE->SourceDirection, dstImVec2);
    */

    public native float getSourceDirectionX(); /*
       return IM_NODE_EDITOR_STYLE->SourceDirection.x;
    */

    public native float getSourceDirectionY(); /*
       return IM_NODE_EDITOR_STYLE->SourceDirection.y;
    */

    public native void setSourceDirection(float x, float y); /*
       IM_NODE_EDITOR_STYLE->SourceDirection.x = x;
       IM_NODE_EDITOR_STYLE->SourceDirection.y = y;
    */

    public native void getTargetDirection(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IM_NODE_EDITOR_STYLE->TargetDirection, dstImVec2);
    */


    public native float getTargetDirectionX(); /*
       return IM_NODE_EDITOR_STYLE->TargetDirection.x;
    */

    public native float getTargetDirectionY(); /*
       return IM_NODE_EDITOR_STYLE->TargetDirection.y;
    */

    public native void setTargetDirection(float x, float y); /*
       IM_NODE_EDITOR_STYLE->TargetDirection.x = x;
       IM_NODE_EDITOR_STYLE->TargetDirection.y = y;
    */

    public native float getScrollDuration(); /*
       return IM_NODE_EDITOR_STYLE->ScrollDuration;
    */

    public native void setScrollDuration(float scrollDuration); /*
       IM_NODE_EDITOR_STYLE->ScrollDuration = scrollDuration;
    */

    public native float getFlowMarkerDistance(); /*
       return IM_NODE_EDITOR_STYLE->FlowMarkerDistance;
    */

    public native void setFlowMarkerDistance(float flowMarkerDistance); /*
       IM_NODE_EDITOR_STYLE->FlowMarkerDistance = flowMarkerDistance;
    */

    public native float getFlowSpeed(); /*
       return IM_NODE_EDITOR_STYLE->FlowSpeed;
    */

    public native void setFlowSpeed(float flowSpeed); /*
       IM_NODE_EDITOR_STYLE->FlowSpeed = flowSpeed;
    */

    public native float getFlowDuration(); /*
       return IM_NODE_EDITOR_STYLE->FlowDuration;
    */

    public native void setFlowDuration(float flowDuration); /*
       IM_NODE_EDITOR_STYLE->FlowDuration = flowDuration;
    */

    public native void getPivotAlignment(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IM_NODE_EDITOR_STYLE->PivotAlignment, dstImVec2);
    */

    public native float getPivotAlignmentX(); /*
       return IM_NODE_EDITOR_STYLE->PivotAlignment.x;
    */

    public native float getPivotAlignmentY(); /*
       return IM_NODE_EDITOR_STYLE->PivotAlignment.y;
    */

    public native void setPivotAlignment(float x, float y); /*
       IM_NODE_EDITOR_STYLE->PivotAlignment.x = x;
       IM_NODE_EDITOR_STYLE->PivotAlignment.y = y;
    */

    public native void getPivotSize(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IM_NODE_EDITOR_STYLE->PivotSize, dstImVec2);
    */

    public native float getPivotSizeX(); /*
       return IM_NODE_EDITOR_STYLE->PivotSize.x;
    */

    public native float getPivotSizeY(); /*
       return IM_NODE_EDITOR_STYLE->PivotSize.y;
    */

    public native void setPivotSize(float x, float y); /*
       IM_NODE_EDITOR_STYLE->PivotSize.x = x;
       IM_NODE_EDITOR_STYLE->PivotSize.y = y;
    */

    public native void getPivotScale(ImVec2 dstImVec2); /*
       Jni::ImVec2Cpy(env, &IM_NODE_EDITOR_STYLE->PivotScale, dstImVec2);
    */

    public native float getPivotScaleX(); /*
       return IM_NODE_EDITOR_STYLE->PivotScale.x;
    */

    public native float getPivotScaleY(); /*
       return IM_NODE_EDITOR_STYLE->PivotScale.y;
    */

    public native void setPivotScale(float x, float y); /*
       IM_NODE_EDITOR_STYLE->PivotScale.x = x;
       IM_NODE_EDITOR_STYLE->PivotScale.y = y;
    */

    public native float getPinCorners(); /*
       return IM_NODE_EDITOR_STYLE->PinCorners;
    */

    public native void setPinCorners(float pinCorners); /*
       IM_NODE_EDITOR_STYLE->PinCorners = pinCorners;
    */

    public native float getPinRadius(); /*
       return IM_NODE_EDITOR_STYLE->PinRadius;
    */

    public native void setPinRadius(float pinRadius); /*
       IM_NODE_EDITOR_STYLE->PinRadius = pinRadius;
    */

    public native float getPinArrowSize(); /*
       return IM_NODE_EDITOR_STYLE->PinArrowSize;
    */

    public native void setPinArrowSize(float pinArrowSize); /*
       IM_NODE_EDITOR_STYLE->PinArrowSize = pinArrowSize;
    */

    public native float getPinArrowWidth(); /*
       return IM_NODE_EDITOR_STYLE->PinArrowWidth;
    */

    public native void setPinArrowWidth(float pinArrowWidth); /*
       IM_NODE_EDITOR_STYLE->PinArrowWidth = pinArrowWidth;
    */

    public native float getGroupRounding(); /*
       return IM_NODE_EDITOR_STYLE->GroupRounding;
    */

    public native void setGroupRounding(float groupRounding); /*
       IM_NODE_EDITOR_STYLE->GroupRounding = groupRounding;
    */

    public native float getGroupBorderWidth(); /*
       return IM_NODE_EDITOR_STYLE->GroupBorderWidth;
    */

    public native void setGroupBorderWidth(float groupBorderWidth); /*
       IM_NODE_EDITOR_STYLE->GroupBorderWidth = groupBorderWidth;
    */

    /**
     * BINDING NOTICE: colors is a 2d array with sizes: [StyleColor_Count][4]
     */
    public native void setColors(float[][] colors); /*
        for (int i = 0; i < ed::StyleColor_Count; i++) {
            jfloatArray jColors = (jfloatArray)env->GetObjectArrayElement(colors, i);
            jfloat* jColor = env->GetFloatArrayElements(jColors, 0);

            IM_NODE_EDITOR_STYLE->Colors[i].x = jColor[0];
            IM_NODE_EDITOR_STYLE->Colors[i].y = jColor[1];
            IM_NODE_EDITOR_STYLE->Colors[i].z = jColor[2];
            IM_NODE_EDITOR_STYLE->Colors[i].w = jColor[3];

            env->ReleaseFloatArrayElements(jColors, jColor, 0);
            env->DeleteLocalRef(jColors);
        }
    */

    public native void getColor(int styleColor, ImVec4 dstImVec4); /*
        Jni::ImVec4Cpy(env, IM_NODE_EDITOR_STYLE->Colors[styleColor], dstImVec4);
    */

    public native void setColor(int styleColor, float r, float g, float b, float a); /*
        IM_NODE_EDITOR_STYLE->Colors[styleColor] = ImColor((float)r, (float)g, (float)b, (float)a);
    */

    public native void setColor(int styleColor, int r, int g, int b, int a); /*
        IM_NODE_EDITOR_STYLE->Colors[styleColor] = ImColor((int)r, (int)g, (int)b, (int)a);
    */

    public native void setColor(int styleColor, int col); /*
        IM_NODE_EDITOR_STYLE->Colors[styleColor] = ImColor(col);
    */
}
