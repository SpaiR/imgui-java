package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genDragSliders() {
    method("DragFloat", returnBoolean()) {
        argString("label")
        argFloatPtr("value", withArray = true)
        argFloat("vSpeed", optional = true)
        argFloat("vMin", optional = true)
        argFloat("vMax", optional = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("DragFloat2", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argFloat("vSpeed", optional = true)
        argFloat("vMin", optional = true)
        argFloat("vMax", optional = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("DragFloat3", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argFloat("vSpeed", optional = true)
        argFloat("vMin", optional = true)
        argFloat("vMax", optional = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("DragFloat4", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argFloat("vSpeed", optional = true)
        argFloat("vMin", optional = true)
        argFloat("vMax", optional = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("DragFloatRange2", returnBoolean()) {
        argString("label")
        argFloatPtr("vCurrentMin", withArray = true)
        argFloatPtr("vCurrentMax", withArray = true)
        argFloat("vSpeed", optional = true)
        argFloat("vMin", optional = true)
        argFloat("vMax", optional = true)
        argString("format", optional = true)
        argString("formatMax", optional = true)
        argInt("flags", optional = true)
    }
    method("DragInt", returnBoolean()) {
        argString("label")
        argIntPtr("value", withArray = true)
        argFloat("vSpeed", optional = true)
        argInt("vMin", optional = true)
        argInt("vMax", optional = true)
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("DragInt2", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argFloat("vSpeed", optional = true)
        argInt("vMin", optional = true)
        argInt("vMax", optional = true)
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("DragInt3", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argFloat("vSpeed", optional = true)
        argInt("vMin", optional = true)
        argInt("vMax", optional = true)
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("DragInt4", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argFloat("vSpeed", optional = true)
        argInt("vMin", optional = true)
        argInt("vMax", optional = true)
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("DragIntRange2", returnBoolean()) {
        argString("label")
        argIntPtr("vCurrentMin", withArray = true)
        argIntPtr("vCurrentMax", withArray = true)
        argFloat("vSpeed", optional = true)
        argInt("vMin", optional = true)
        argInt("vMax", optional = true)
        argString("format", optional = true)
        argString("formatMax", optional = true)
        argInt("flags", optional = true)
    }
    method("DragScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_Float")
        argFloatPtr("pData", withArray = true)
        argFloat("vSpeed", optional = true)
        argFloat("pMin", optional = true, jniCast = "&")
        argFloat("pMax", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("DragScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S16")
        argShortPtr("pData", withArray = true)
        argFloat("vSpeed", optional = true)
        argShort("pMin", optional = true, jniCast = "&")
        argShort("pMax", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("DragScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S32")
        argIntPtr("pData", withArray = true)
        argFloat("vSpeed", optional = true)
        argInt("pMin", optional = true, jniCast = "&")
        argInt("pMax", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("DragScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S64")
        argLongPtr("pData", withArray = true)
        argFloat("vSpeed", optional = true)
        argLong("pMin", optional = true, jniCast = "&")
        argLong("pMax", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
}
