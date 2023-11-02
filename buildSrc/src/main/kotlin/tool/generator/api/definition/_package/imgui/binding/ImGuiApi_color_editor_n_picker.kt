package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genColorEditorAndPicker() {
    method("ColorEdit3", returnBoolean()) {
        argString("label")
        argFloatArr("col")
        argInt("flags", optional = true)
    }
    method("ColorEdit4", returnBoolean()) {
        argString("label")
        argFloatArr("col")
        argInt("flags", optional = true)
    }
    method("ColorPicker3", returnBoolean()) {
        argString("label")
        argFloatArr("col")
        argInt("flags", optional = true)
    }
    method("ColorPicker4", returnBoolean()) {
        argString("label")
        argFloatArr("col")
        argInt("flags", optional = true)
        argFloatPtr("refCol", optional = true)
    }
}
