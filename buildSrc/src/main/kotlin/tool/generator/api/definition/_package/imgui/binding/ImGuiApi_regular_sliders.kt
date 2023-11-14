package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genRegularSliders() {
    method("SliderFloat", returnBoolean()) {
        argString("label")
        argFloat("value", isPointer = true, isArray = true)
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderFloat2", returnBoolean()) {
        argString("label")
        argFloat("value", isArray = true)
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderFloat3", returnBoolean()) {
        argString("label")
        argFloat("value", isArray = true)
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderFloat4", returnBoolean()) {
        argString("label")
        argFloat("value", isArray = true)
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderAngle", returnBoolean()) {
        argString("label")
        argFloat("vRad", isPointer = true, isArray = true)
        argFloat("vDegreesMin")
        argFloat("vDegreesMax")
        argString("format", optional = true, default = "\"%.0f deg\"")
        argInt("flags", optional = true)
    }
    method("SliderInt", returnBoolean()) {
        argString("label")
        argInt("value", isPointer = true, isArray = true)
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderInt2", returnBoolean()) {
        argString("label")
        argInt("value", isArray = true)
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderInt3", returnBoolean()) {
        argString("label")
        argInt("value", isArray = true)
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderInt4", returnBoolean()) {
        argString("label")
        argInt("value", isArray = true)
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_Float")
        argFloat("pData", isPointer = true)
        argFloat("pMin", jniCast = "&")
        argFloat("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S16")
        argShort("pData", isPointer = true)
        argShort("pMin", jniCast = "&")
        argShort("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S32")
        argInt("pData", isPointer = true)
        argInt("pMin", jniCast = "&")
        argInt("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S64")
        argLong("pData", isPointer = true)
        argLong("pMin", jniCast = "&")
        argLong("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderFloat", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argFloat("value", isPointer = true)
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("VSliderInt", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argInt("value", isPointer = true)
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_Float")
        argFloat("pData", isPointer = true)
        argFloat("pMin", jniCast = "&")
        argFloat("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_S16")
        argShort("pData", isPointer = true)
        argShort("pMin", jniCast = "&")
        argShort("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_S32")
        argInt("pData", isPointer = true)
        argInt("pMin", jniCast = "&")
        argInt("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_S64")
        argLong("pData", isPointer = true)
        argLong("pMin", jniCast = "&")
        argLong("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
}
