package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genMethods() {
    method("SetNextWindowSizeConstraints") {
        argImVec2("sizeMin")
        argImVec2("sizeMax")
    }

    method("Combo", returnBoolean()) {
        argString("label")
        argInt("currentItem", isPointer = true)
        argString("itemsSeparatedByZeros")
        argInt("popupMaxHeightInItems", optional = true)
    }

    method("SaveIniSettingsToMemory") {
        argInt("outIniSize", optional = true, jniCast = "(size_t*)&")
    }

    method("RenderPlatformWindowsDefault")

    method("FindViewportByPlatformHandle", returnStruct("imgui.ImGuiViewport")) {
        argLong("platformHandle", jniCast = "(void*)")
    }

    method("IsMousePosValid", returnBoolean()) {
        argImVec2("mousePos")
    }
}
