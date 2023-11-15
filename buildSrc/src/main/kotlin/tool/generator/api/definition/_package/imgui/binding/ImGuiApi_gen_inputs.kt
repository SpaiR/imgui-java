package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genInputs() {
    method("InputFloat", returnBoolean()) {
        argString("label")
        argFloat("value", isPointer = true, isArray = true)
        argFloat("step", optional = true)
        argFloat("stepFast", optional = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputFloat2", returnBoolean()) {
        argString("label")
        argFloat("value", isArray = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputFloat3", returnBoolean()) {
        argString("label")
        argFloat("value", isArray = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputFloat4", returnBoolean()) {
        argString("label")
        argFloat("value", isArray = true)
        argString("format", optional = true, default = "\"%.3f\"")
        argInt("flags", optional = true)
    }
    method("InputInt", returnBoolean()) {
        argString("label")
        argInt("value", isPointer = true, isArray = true)
        argInt("step", optional = true)
        argInt("stepFast", optional = true)
        argInt("flags", optional = true)
    }
    method("InputInt2", returnBoolean()) {
        argString("label")
        argInt("value", isArray = true)
        argInt("flags", optional = true)
    }
    method("InputInt3", returnBoolean()) {
        argString("label")
        argInt("value", isArray = true)
        argInt("flags", optional = true)
    }
    method("InputInt4", returnBoolean()) {
        argString("label")
        argInt("value", isArray = true)
        argInt("flags", optional = true)
    }
    method("InputDouble", returnBoolean()) {
        argString("label")
        argDouble("value", isPointer = true, isArray = true)
        argDouble("step")
        argDouble("stepFast")
        argString("format", optional = true, default = "\"%.6f\"")
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_Float")
        argFloat("pData", isPointer = true)
        argFloat("pStep", optional = true, jniCast = "&")
        argFloat("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S16")
        argShort("pData", isPointer = true)
        argShort("pStep", optional = true, jniCast = "&")
        argShort("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S32")
        argInt("pData", isPointer = true)
        argInt("pStep", optional = true, jniCast = "&")
        argInt("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
    method("InputScalar", returnBoolean()) {
        argString("label")
        argDefault("ImGuiDataType_S64")
        argLong("pData", isPointer = true)
        argLong("pStep", optional = true, jniCast = "&")
        argLong("pStepFast", optional = true, jniCast = "&")
        argString("format", optional = true)
        argInt("flags", optional = true)
    }
}
