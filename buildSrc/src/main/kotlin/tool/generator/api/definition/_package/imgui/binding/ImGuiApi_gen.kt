package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.node.type.method.ext.JniCast

fun MethodsDsl.genMethods() {
    method("SetNextWindowSizeConstraints") {
        argImVec2("sizeMin")
        argImVec2("sizeMax")
    }

    method("Combo", returnBoolean()) {
        argString("label")
        argIntPtr("currentItem")
        argString("itemsSeparatedByZeros")
        argInt("popupMaxHeightInItems", optional = true)
    }

    method("SaveIniSettingsToMemory") {
        argInt("outIniSize", optional = true, with = listOf(JniCast("(size_t*)&")))
    }

    method("RenderPlatformWindowsDefault")

    method("FindViewportByPlatformHandle", returnStruct("imgui.ImGuiViewport")) {
        argLong("platformHandle", with = listOf(JniCast("(void*)")))
    }
}
