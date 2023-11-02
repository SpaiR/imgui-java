package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.genDataPlotting() {
    method("PlotLines") {
        argString("label")
        argFloatArr("values")
        argInt("valuesCount")
        argInt("valuesOffset", optional = true, default = "0")
        argString("overlayText", optional = true, default = "NULL")
        argFloat("scaleMin", optional = true)
        argFloat("scaleMax", optional = true)
        argImVec2("graphSize", optional = true, default = "ImVec2(0, 0)")
        argInt("stride", optional = true)
    }
    method("PlotHistogram") {
        argString("label")
        argFloatArr("values")
        argInt("valuesCount")
        argInt("valuesOffset", default = "0")
        argString("overlayText", default = "NULL")
        argFloat("scaleMin", optional = true)
        argFloat("scaleMax", optional = true)
        argImVec2("graphSize", default = "ImVec2(0, 0)")
        argInt("stride", optional = true)
    }
}
