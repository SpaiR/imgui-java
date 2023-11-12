package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genInputs() {
    method("InputFloat", returnBoolean()) {
        argString("label")
        argFloatPtr("value", withArray = true)
        argFloat("step", optional = true)
        argFloat("stepFast", optional = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputFloat2", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputFloat3", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputFloat4", returnBoolean()) {
        argString("label")
        argFloatArr("value")
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputInt", returnBoolean()) {
        argString("label")
        argIntPtr("value", withArray = true)
        argInt("step", optional = true)
        argInt("stepFast", optional = true)
        argInt("flags", optional = true)
    }
    method("InputInt2", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argInt("flags", optional = true)
    }
    method("InputInt3", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argInt("flags", optional = true)
    }
    method("InputInt4", returnBoolean()) {
        argString("label")
        argIntArr("value")
        argInt("flags", optional = true)
    }
    method("InputDouble", returnBoolean()) {
        argString("label")
        argDoublePtr("value", withArray = true)
        argDouble("step")
        argDouble("stepFast")
        argString("format", optional = true, default = "\"%.6f\"")
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_Float")
        argFloatPtr("pData")
        argFloat("pStep", optional = true, jniCast = "&")
        argFloat("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S16")
        argShortPtr("pData")
        argShort("pStep", optional = true, jniCast = "&")
        argShort("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S32")
        argIntPtr("pData")
        argInt("pStep", optional = true, jniCast = "&")
        argInt("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S64")
        argLongPtr("pData")
        argLong("pStep", optional = true, jniCast = "&")
        argLong("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
}
