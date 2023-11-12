package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genRegularSliders() {
    method("SliderFloat", returnBoolean()) {
        argString("label")
        argFloatPtr("value", withArray = true)
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderFloat2", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderFloat3", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderFloat4", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("SliderAngle", returnBoolean()) {
        argString("label")
        argFloatPtr("vRad", withArray = true)
        argFloat("vDegreesMin")
        argFloat("vDegreesMax")
        argString("format", optional = true, default = "\"%.0f deg\"")
        argInt("flags", optional = true)
    }
    method("SliderInt", returnBoolean()) {
        argString("label")
        argIntPtr("value", withArray = true)
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderInt2", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderInt3", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderInt4", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_Float")
        argFloatPtr("pData")
        argFloat("pMin", jniCast = "&")
        argFloat("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S16")
        argShortPtr("pData")
        argShort("pMin", jniCast = "&")
        argShort("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S32")
        argIntPtr("pData")
        argInt("pMin", jniCast = "&")
        argInt("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("SliderScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S64")
        argLongPtr("pData")
        argLong("pMin", jniCast = "&")
        argLong("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderFloat", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argFloatPtr("value")
        argFloat("vMin")
        argFloat("vMax")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("VSliderInt", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argIntPtr("value")
        argInt("vMin")
        argInt("vMax")
        argString("format", optional = true, default = "\"%d\"")
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_Float")
        argFloatPtr("pData")
        argFloat("pMin", jniCast = "&")
        argFloat("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_S16")
        argShortPtr("pData")
        argShort("pMin", jniCast = "&")
        argShort("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_S32")
        argIntPtr("pData")
        argInt("pMin", jniCast = "&")
        argInt("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("VSliderScalar", returnBoolean()) {
        argString("label")
        argImVec2("size")
        argDefault("ImGuiDataType_S64")
        argLongPtr("pData")
        argLong("pMin", jniCast = "&")
        argLong("pMax", jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
}
