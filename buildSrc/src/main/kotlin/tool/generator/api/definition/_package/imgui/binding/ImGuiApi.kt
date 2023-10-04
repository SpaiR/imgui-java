package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.Definition
import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.node.transform.method.*
import tool.generator.api.definition.node.type.method.ext.JniCast

class ImGuiApi : Definition {
    companion object {
        private const val THIS_POINTER_IMGUI = "ImGui::"
    }

    @Suppress("ClassName")
    object `set auto body this pointer to imgui` : `set this pointer`(THIS_POINTER_IMGUI)

    override fun getNodes() = defines(
        define {
            jniBlock("#include \"_common.h\"")
        },
        define({
            transformation {
                chain(
                    `add return type node`,
                    `add auto body node`,
                    `add args node`,
                    `set auto body method name from original name`,
                )
            }
            transformation {
                chain(
                    `set method public jvm`,
                    `set auto body this pointer jvm`,
                )
                chain(
                    `set method private jni`,
                    `set auto body this pointer to imgui`,
                )
            }
            transformation {
                chain(
                    `add methods for optional args`,
                    `add method for pointer with array`,
                )
            }
            transformation {
                chain(
                    `set args call for auto body from jvm to jni`,
                    `set args call for auto body from jni to native`,
                )
            }
            transformation {
                chain(
                    `remove null argument except jni auto body`,
                    `add methods for default jni args`,
                )
            }
            transformation {
                chain(
                    `handle vec2 arg`,
                    `handle vec2 return`,
                )
            }
            transformation {
                chain(
                    `handle vec4 arg`,
                    `handle vec4 return`,
                )
            }
            transformation {
                chain(`add static struct field`) // should be the last in the chain
            }
        }) {
            methods {
                // Context creation and access
                method("CreateContext", returnStruct("imgui.internal.ImGuiContext")) {
                    argStruct("imgui.ImFontAtlas", "sharedFontAtlas", optional = true)
                }
                method("DestroyContext") {
                    argStruct("imgui.internal.ImGuiContext", "ctx", optional = true)
                }
                method("GetCurrentContext", returnStruct("imgui.internal.ImGuiContext", static = true))
                method("SetCurrentContext") {
                    argStruct("imgui.internal.ImGuiContext", "ctx")
                }

                // Main
                method("GetIO", returnStruct("imgui.ImGuiIO", static = true, isRef = true))
                method("GetStyle", returnStruct("imgui.ImGuiStyle", static = true, isRef = true))
                method("NewFrame")
                method("EndFrame")
                method("Render")
                method("GetDrawData", returnStruct("imgui.ImDrawData", static = true))

                // Demo, Debug, Information
                method("ShowDemoWindow") { argBooleanPtr("open", optional = true) }
                method("ShowMetricsWindow") { argBooleanPtr("open", optional = true) }
                method("ShowStackToolWindow") { argBooleanPtr("open", optional = true) }
                method("ShowStyleEditor") { argStruct("imgui.ImGuiStyle", "ref", optional = true) }
                method("ShowStyleSelector", returnBoolean()) { argString("label") }
                method("ShowFontSelector") { argString("label") }
                method("ShowUserGuide")
                method("GetVersion", returnString())

                // Styles
                method("StyleColorsDark") { argStruct("imgui.ImGuiStyle", "dst", optional = true) }
                method("StyleColorsLight") { argStruct("imgui.ImGuiStyle", "dst", optional = true) }
                method("StyleColorsClassic") { argStruct("imgui.ImGuiStyle", "dst", optional = true) }

                // Windows
                method("Begin", returnBoolean()) {
                    argString("name")
                    argBooleanPtr("pOpen", optional = true, default = "NULL")
                    argInt("flags", optional = true)
                }
                method("End")

                // Child Windows
                method("BeginChild", returnBoolean()) {
                    argString("id")
                    argImVec2("size", optional = true, default = "ImVec2(0, 0)")
                    argBoolean("border", optional = true, default = "false")
                    argInt("flags", optional = true)
                }
                method("BeginChild", returnBoolean()) {
                    argInt("id")
                    argImVec2("size", optional = true, default = "ImVec2(0, 0)")
                    argBoolean("border", optional = true, default = "false")
                    argInt("flags", optional = true)
                }
                method("EndChild")

                // Windows Utilities
                method("IsWindowAppearing", returnBoolean())
                method("IsWindowCollapsed", returnBoolean())
                method("IsWindowFocused", returnBoolean()) { argInt("flags", optional = true) }
                method("IsWindowHovered", returnBoolean()) { argInt("flags", optional = true) }
                method("GetWindowDrawList", returnStruct("imgui.ImDrawList"))
                method("GetWindowDpiScale", returnFloat())
                method("GetWindowPos", returnImVec2())
                method("GetWindowSize", returnImVec2())
                method("GetWindowWidth", returnFloat())
                method("GetWindowHeight", returnFloat())
                method("GetWindowViewport", returnStruct("imgui.ImGuiViewport"))

                // Window manipulation
                method("SetNextWindowPos") {
                    argImVec2("pos")
                    argInt("cond", optional = true)
                    argImVec2("pivot", optional = true)
                }
                method("SetNextWindowSize") {
                    argImVec2("size")
                    argInt("cond", optional = true)
                }
                method("SetNextWindowSizeConstraints") {
                    argImVec2("sizeMin")
                    argImVec2("sizeMax")
                }
                method("SetNextWindowContentSize") { argImVec2("size") }
                method("SetNextWindowCollapsed") {
                    argBoolean("collapsed")
                    argInt("cond", optional = true)
                }
                method("SetNextWindowFocus")
                method("SetNextWindowBgAlpha") { argFloat("alpha") }
                method("SetNextWindowViewport") { argInt("viewportId") }
                method("SetWindowPos") {
                    argImVec2("pos")
                    argInt("cond", optional = true)
                }
                method("SetWindowSize") {
                    argImVec2("size")
                    argInt("cond", optional = true)
                }
                method("SetWindowCollapsed") {
                    argBoolean("collapsed")
                    argInt("cond", default = "ImGuiCond_None")
                }
                method("SetWindowCollapsed") {
                    argString("name")
                    argBoolean("collapsed")
                    argInt("cond", default = "ImGuiCond_None")
                }
                method("SetWindowFocus")
                method("SetWindowFontScale") { argFloat("scale") }
                method("SetWindowPos") {
                    argString("name")
                    argImVec2("pos")
                    argInt("cond", optional = true)
                }
                method("SetWindowSize") {
                    argString("name")
                    argImVec2("size")
                    argInt("cond", optional = true)
                }
                method("SetWindowFocus") {
                    argString("name")
                }

                // Content region
                method("GetContentRegionAvail", returnImVec2())
                method("GetContentRegionMax", returnImVec2())
                method("GetWindowContentRegionMin", returnImVec2())
                method("GetWindowContentRegionMax", returnImVec2())

                // Windows Scrolling
                method("GetScrollX", returnFloat())
                method("GetScrollY", returnFloat())
                method("SetScrollX") { argFloat("scrollX") }
                method("SetScrollY") { argFloat("scrollY") }
                method("GetScrollMaxX", returnFloat())
                method("GetScrollMaxY", returnFloat())
                method("SetScrollHereX") { argFloat("centerRatioX", optional = true) }
                method("SetScrollHereY") { argFloat("centerRatioY", optional = true) }
                method("SetScrollFromPosX") {
                    argFloat("localX")
                    argFloat("centerRatioX", optional = true)
                }
                method("SetScrollFromPosY") {
                    argFloat("localY")
                    argFloat("centerRatioY", optional = true)
                }

                // Parameters stacks (shared)
                method("PushFont") { argStruct("imgui.ImFont", "font") }
                method("PopFont")
                method("PushStyleColor") {
                    argInt("idx")
                    argInt("col")
                }
                method("PushStyleColor") {
                    argInt("idx")
                    argImVec4("col")
                }
                method("PopStyleColor") { argInt("count", optional = true) }
                method("PushStyleVar") {
                    argInt("idx")
                    argInt("col")
                }
                method("PushStyleVar") {
                    argInt("idx")
                    argImVec2("val")
                }
                method("PopStyleVar") { argInt("count", optional = true) }
                method("PushAllowKeyboardFocus") { argBoolean("allowKeyboardFocus") }
                method("PopAllowKeyboardFocus")
                method("PushButtonRepeat") { argBoolean("repeat") }
                method("PopButtonRepeat")

                // Parameters stacks (current window)
                method("PushItemWidth") { argFloat("itemWidth") }
                method("PopItemWidth")
                method("SetNextItemWidth") { argFloat("itemWidth") }
                method("CalcItemWidth", returnFloat())
                method("PushTextWrapPos") { argFloat("wrapLocalPosX", optional = true) }
                method("PopTextWrapPos")

                // Style read access
                method("GetFont", returnStruct("imgui.ImFont"))
                method("GetFontSize", returnFloat())
                method("GetFontTexUvWhitePixel", returnImVec2())
                method("GetColorU32", returnInt()) {
                    argInt("idx")
                    argFloat("alphaMul")
                }
                method("GetColorU32", returnInt()) { argImVec4("col") }
                method("GetStyleColorVec4", returnImVec4()) { argInt("idx") }

                // Cursor / Layout
                method("Separator")
                method("SameLine") {
                    argFloat("offsetFromStart", optional = true)
                    argFloat("spacing", optional = true)
                }
                method("NewLine")
                method("Spacing")
                method("Dummy") { argImVec2("size") }
                method("Indent") { argFloat("indentW", optional = true) }
                method("Unindent") { argFloat("indentW", optional = true) }
                method("BeginGroup")
                method("EndGroup")
                method("GetCursorPos", returnImVec2())
                method("SetCursorPos") { argImVec2("localPos") }
                method("SetCursorPosX") { argFloat("localX") }
                method("SetCursorPosY") { argFloat("localY") }
                method("GetCursorStartPos", returnImVec2())
                method("GetCursorScreenPos", returnImVec2())
                method("SetCursorScreenPos") { argImVec2("localPos") }
                method("AlignTextToFramePadding")
                method("GetTextLineHeight", returnFloat())
                method("GetTextLineHeightWithSpacing", returnFloat())
                method("GetFrameHeight", returnFloat())
                method("GetFrameHeightWithSpacing", returnFloat())

                // ID stack/scopes
                method("PushID") { argString("id") }
                method("PushID") { argInt("id") }
                method("PopID")
                method("GetID", returnInt()) { argString("id") }

                // Widgets: Text
                method("TextUnformatted") { argString("text") }
                method("Text") {
                    argString("text")
                    argNull()
                }
                method("TextColored") {
                    argImVec4("col")
                    argString("text")
                    argNull()
                }
                method("TextDisabled") {
                    argString("text")
                    argNull()
                }
                method("TextWrapped") {
                    argString("text")
                    argNull()
                }
                method("LabelText") {
                    argString("text")
                    argNull()
                }
                method("BulletText") {
                    argString("text")
                    argNull()
                }

                // Widgets: Main
                method("Button", returnBoolean()) {
                    argString("label")
                    argImVec2("size", optional = true)
                }
                method("SmallButton", returnBoolean()) { argString("label") }
                method("InvisibleButton", returnBoolean()) {
                    argString("id")
                    argImVec2("size")
                    argInt("flags", optional = true)
                }
                method("ArrowButton", returnBoolean()) {
                    argString("id")
                    argInt("dir")
                }
                method("Image") {
                    argInt("userTextureId", with = listOf(JniCast("(ImTextureID)($CAST_PTR_JNI)")))
                    argImVec2("size")
                    argImVec2("uv0", optional = true)
                    argImVec2("uv1", optional = true)
                    argImVec4("tintCol", optional = true)
                    argImVec4("borderCol", optional = true)
                }
                method("ImageButton", returnBoolean()) {
                    argInt("userTextureId", with = listOf(JniCast("(ImTextureID)($CAST_PTR_JNI)")))
                    argImVec2("size")
                    argImVec2("uv0", optional = true)
                    argImVec2("uv1", optional = true)
                    argFloat("framePadding", optional = true)
                    argImVec4("bgCol", optional = true)
                    argImVec4("tintCol", optional = true)
                }
                method("Checkbox", returnBoolean()) {
                    argString("label")
                    argBooleanPtr("v")
                }
                method("CheckboxFlags", returnBoolean()) {
                    argString("label")
                    argIntPtr("flags")
                    argInt("flagsValue")
                }
                method("RadioButton", returnBoolean()) {
                    argString("label")
                    argBoolean("active")
                }
                method("RadioButton", returnBoolean()) {
                    argString("label")
                    argIntPtr("v")
                    argInt("vButton")
                }
                method("ProgressBar") {
                    argFloat("fraction")
                    argImVec2("sizeArg", optional = true, default = "ImVec2(-FLT_MIN, 0)")
                    argString("overlay", optional = true)
                }
                method("Bullet")

                // Widgets: Combo Box
                method("BeginCombo", returnBoolean()) {
                    argString("label")
                    argString("previewValue")
                    argInt("flags", optional = true)
                }
                method("EndCombo")
                method("Combo", returnBoolean()) {
                    argString("label")
                    argIntPtr("currentItem")
                    argString("itemsSeparatedByZeros")
                    argInt("popupMaxHeightInItems", optional = true)
                }

                // Widgets: Drag Sliders
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
                    argFloat("pMin", optional = true, with = listOf(JniCast("&")))
                    argFloat("pMax", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("DragScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S16")
                    argShortPtr("pData", withArray = true)
                    argFloat("vSpeed", optional = true)
                    argShort("pMin", optional = true, with = listOf(JniCast("&")))
                    argShort("pMax", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("DragScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S32")
                    argIntPtr("pData", withArray = true)
                    argFloat("vSpeed", optional = true)
                    argInt("pMin", optional = true, with = listOf(JniCast("&")))
                    argInt("pMax", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("DragScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S64")
                    argLongPtr("pData", withArray = true)
                    argFloat("vSpeed", optional = true)
                    argLong("pMin", optional = true, with = listOf(JniCast("&")))
                    argLong("pMax", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }

                // Widgets: Regular Sliders
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
                    argFloat("pMin", with = listOf(JniCast("&")))
                    argFloat("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("SliderScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S16")
                    argShortPtr("pData")
                    argShort("pMin", with = listOf(JniCast("&")))
                    argShort("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("SliderScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S32")
                    argIntPtr("pData")
                    argInt("pMin", with = listOf(JniCast("&")))
                    argInt("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("SliderScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S64")
                    argLongPtr("pData")
                    argLong("pMin", with = listOf(JniCast("&")))
                    argLong("pMax", with = listOf(JniCast("&")))
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
                    argFloat("pMin", with = listOf(JniCast("&")))
                    argFloat("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("VSliderScalar", returnBoolean()) {
                    argString("label")
                    argImVec2("size")
                    argDefault("ImGuiDataType_S16")
                    argShortPtr("pData")
                    argShort("pMin", with = listOf(JniCast("&")))
                    argShort("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("VSliderScalar", returnBoolean()) {
                    argString("label")
                    argImVec2("size")
                    argDefault("ImGuiDataType_S32")
                    argIntPtr("pData")
                    argInt("pMin", with = listOf(JniCast("&")))
                    argInt("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("VSliderScalar", returnBoolean()) {
                    argString("label")
                    argImVec2("size")
                    argDefault("ImGuiDataType_S64")
                    argLongPtr("pData")
                    argLong("pMin", with = listOf(JniCast("&")))
                    argLong("pMax", with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }

                // Widgets: Input with Keyboard
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
                    argFloat("pStep", optional = true, with = listOf(JniCast("&")))
                    argFloat("pStepFast", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("InputScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S16")
                    argShortPtr("pData")
                    argShort("pStep", optional = true, with = listOf(JniCast("&")))
                    argShort("pStepFast", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("InputScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S32")
                    argIntPtr("pData")
                    argInt("pStep", optional = true, with = listOf(JniCast("&")))
                    argInt("pStepFast", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }
                method("InputScalar", returnBoolean()) {
                    argString("label")
                    argDefault("ImGuiDataType_S64")
                    argLongPtr("pData")
                    argLong("pStep", optional = true, with = listOf(JniCast("&")))
                    argLong("pStepFast", optional = true, with = listOf(JniCast("&")))
                    argString("format", optional = true)
                    argInt("flags", optional = true)
                }

                // Widgets: Color Editor/Picker
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
                method("ColorButton", returnBoolean()) {
                    argString("descId")
                    argImVec4("col")
                    argInt("flags", optional = true, default = "ImGuiColorEditFlags_None")
                    argImVec2("size", optional = true)
                }
                method("SetColorEditOptions") {
                    argInt("flags")
                }

                // Widgets: Trees
                method("TreeNode", returnBoolean()) {
                    argString("label")
                }
                method("TreeNodeEx", returnBoolean()) {
                    argString("label")
                    argInt("flags", optional = true)
                }
                method("TreePush") {
                    argString("strId")
                }
                method("TreePop")
                method("GetTreeNodeToLabelSpacing", returnFloat())
                method("CollapsingHeader", returnBoolean()) {
                    argString("label")
                    argInt("flags", optional = true)
                }
                method("CollapsingHeader", returnBoolean()) {
                    argString("label")
                    argBooleanPtr("pVisible")
                    argInt("flags", optional = true)
                }
                method("SetNextItemOpen") {
                    argBoolean("isOpen")
                    argInt("cont", optional = true)
                }

                // Widgets: Selectables
                method("Selectable", returnBoolean()) {
                    argString("label")
                    argBoolean("selected", optional = true, default = "false")
                    argInt("flags", optional = true, default = "ImGuiSelectableFlags_None")
                    argImVec2("size", optional = true)
                }
                method("Selectable", returnBoolean()) {
                    argString("label")
                    argBooleanPtr("selected")
                    argInt("flags", optional = true, default = "ImGuiSelectableFlags_None")
                    argImVec2("size", optional = true)
                }

                // Widgets: List Boxes
                method("BeginListBox", returnBoolean()) {
                    argString("label")
                    argImVec2("size", optional = true)
                }
                method("EndListBox")

                // Widgets: Data Plotting
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

                // Widgets: Value() Helpers.
                method("Value") {
                    argString("prefix")
                    argBoolean("value", with = listOf(JniCast("(bool)")))
                }
                method("Value") {
                    argString("prefix")
                    argInt("value", with = listOf(JniCast("(int)")))
                }
                method("Value") {
                    argString("prefix")
                    argLong("value", with = listOf(JniCast("(unsigned int)")))
                }
                method("Value") {
                    argString("prefix")
                    argFloat("value", with = listOf(JniCast("(float)")))
                    argString("floatFormat", optional = true)
                }

                // Widgets: Menus
                method("BeginMenuBar", returnBoolean())
                method("EndMenuBar")
                method("BeginMainMenuBar", returnBoolean())
                method("EndMainMenuBar")
                method("BeginMenu", returnBoolean()) {
                    argString("label")
                    argBoolean("enabled", optional = true)
                }
                method("EndMenu")
                method("MenuItem", returnBoolean()) {
                    argString("label")
                    argString("shortcut", optional = true, default = "NULL")
                    argBoolean("selected", optional = true)
                    argBoolean("enabled", optional = true)
                }
                method("MenuItem", returnBoolean()) {
                    argString("label")
                    argString("shortcut", optional = true, default = "NULL")
                    argBooleanPtr("selected")
                    argBoolean("enabled", optional = true)
                }

                // Tooltips
                method("BeginTooltip")
                method("EndTooltip")
                method("SetTooltip") {
                    argString("tooltip")
                    argNull()
                }

                // Popups, Modals
                method("BeginPopup", returnBoolean()) {
                    argString("strId")
                    argInt("flags", optional = true)
                }
                method("BeginPopupModal", returnBoolean()) {
                    argString("name")
                    argBooleanPtr("pOpen", optional = true, default = "NULL")
                    argInt("flags", optional = true)
                }
                method("EndPopup")

                // Popups: open/close functions
                method("OpenPopup") {
                    argString("strId")
                    argInt("popupFlags", optional = true)
                }
                method("OpenPopup") {
                    argInt("id")
                    argInt("popupFlags", optional = true)
                }
                method("OpenPopupOnItemClick") {
                    argString("strId", optional = true, default = "NULL")
                    argInt("popupFlags", optional = true)
                }
                method("CloseCurrentPopup")

                // Popups: open+begin combined functions helpers
                method("BeginPopupContextItem", returnBoolean()) {
                    argString("strId", optional = true, default = "NULL")
                    argInt("popupFlags", optional = true)
                }
                method("BeginPopupContextWindow", returnBoolean()) {
                    argString("strId", optional = true, default = "NULL")
                    argInt("popupFlags", optional = true)
                }
                method("BeginPopupContextVoid", returnBoolean()) {
                    argString("strId", optional = true, default = "NULL")
                    argInt("popupFlags", optional = true)
                }

                // Popups: query functions
                method("IsPopupOpen", returnBoolean()) {
                    argString("strId")
                    argInt("flags", optional = true)
                }

                // Tables
                method("BeginTable", returnBoolean()) {
                    argString("strId")
                    argInt("column")
                    argInt("flags", optional = true, default = "ImGuiTableFlags_None")
                    argImVec2("outerSize", optional = true, default = "ImVec2(0, 0)")
                    argFloat("innerWidth", optional = true)
                }
                method("EndTable")
                method("TableNextRow") {
                    argInt("rowFlags", optional = true, default = "ImGuiTableRowFlags_None")
                    argFloat("minRowHeight", optional = true)
                }
                method("TableNextColumn", returnBoolean())
                method("TableSetColumnIndex", returnBoolean()) {
                    argInt("columnN")
                }

                // Tables: Headers & Columns declaration
                method("TableSetupColumn") {
                    argString("label")
                    argInt("flags", optional = true, default = "ImGuiTableColumnFlags_None")
                    argFloat("initWidthOrWeight", optional = true, default = "0.0f")
                    argInt("userId", optional = true)
                }
                method("TableSetupScrollFreeze") {
                    argInt("cols")
                    argInt("rows")
                }
                method("TableHeadersRow")
                method("TableHeader") {
                    argString("label")
                }

                // Tables: Sorting
                // TODO: TableGetSortSpecs

                // Tables: Miscellaneous functions
                method("TableGetColumnCount", returnInt())
                method("TableGetColumnIndex", returnInt())
                method("TableGetRowIndex", returnInt())
                method("TableGetColumnName", returnString()) {
                    argInt("columnN", optional = true)
                }
                method("TableGetColumnFlags", returnInt()) {
                    argInt("columnN", optional = true)
                }
                method("TableSetColumnEnabled") {
                    argInt("columnN")
                    argBoolean("value")
                }
                method("TableSetBgColor") {
                    argInt("target")
                    argInt("color")
                    argInt("columnN", optional = true)
                }

                // Legacy Columns API (prefer using Tables!)
                method("Columns") {
                    argInt("count", optional = true, default = "1")
                    argString("id", optional = true, default = "NULL")
                    argBoolean("border", optional = true)
                }
                method("NextColumn")
                method("GetColumnIndex", returnInt())
                method("GetColumnWidth", returnFloat()) {
                    argInt("columnIndex", optional = true)
                }
                method("SetColumnWidth") {
                    argInt("columnIndex")
                    argFloat("width")
                }
                method("GetColumnOffset", returnFloat()) {
                    argInt("columnIndex", optional = true)
                }
                method("SetColumnOffset") {
                    argInt("columnIndex")
                    argFloat("offsetX")
                }
                method("GetColumnsCount", returnInt())

                // Tab Bars, Tabs
                method("BeginTabBar", returnBoolean()) {
                    argString("strId")
                    argInt("flags", optional = true)
                }
                method("EndTabBar")
                method("BeginTabItem", returnBoolean()) {
                    argString("label")
                    argBooleanPtr("pOpen", optional = true, default = "NULL")
                    argInt("flags", optional = true)
                }
                method("EndTabItem")
                method("TabItemButton", returnBoolean()) {
                    argString("label")
                    argInt("flags", optional = true)
                }
                method("SetTabItemClosed") {
                    argString("tabOrDockedWindowLabel")
                }

                // Docking
                method("DockSpace", returnInt()) {
                    argInt("id")
                    argImVec2("size", optional = true, default = "ImVec2(0, 0)")
                    argInt("flags", optional = true, default = "ImGuiDockNodeFlags_None")
                    argStruct("imgui.ImGuiWindowClass", "windowClass", optional = true)
                }
                method("DockSpaceOverViewport", returnInt()) {
                    argStruct("imgui.ImGuiViewport", "viewport", optional = true, default = "NULL")
                    argInt("flags", optional = true, default = "ImGuiDockNodeFlags_None")
                    argStruct("imgui.ImGuiWindowClass", "windowClass", optional = true)
                }
                method("SetNextWindowDockID") {
                    argInt("dockId")
                    argInt("cond", optional = true)
                }
                method("SetNextWindowClass") {
                    argStruct("imgui.ImGuiWindowClass", "windowClass")
                }
                method("GetWindowDockID", returnInt())
                method("IsWindowDocked", returnBoolean())

                // Logging/Capture
                method("LogToTTY") {
                    argInt("autoOpenDepth", optional = true)
                }
                method("LogToFile") {
                    argInt("autoOpenDepth", optional = true, default = "-1")
                    argString("filename", optional = true)
                }
                method("LogToClipboard") {
                    argInt("autoOpenDepth", optional = true)
                }
                method("LogFinish")
                method("LogButtons")
                method("LogText") {
                    argString("text")
                    argNull()
                }

                // Drag and Drop
                method("BeginDragDropSource", returnBoolean()) {
                    argInt("flags", optional = true)
                }
                method("EndDragDropSource")
                method("BeginDragDropTarget", returnBoolean())
                method("EndDragDropTarget")

                // Disabling [BETA API]
                method("BeginDisabled") {
                    argBoolean("disabled", optional = true)
                }
                method("EndDisabled")

                // Clipping
                method("PushClipRect") {
                    argImVec2("clipRectMin")
                    argImVec2("clipRectMax")
                    argBoolean("intersectWithCurrentClipRect")
                }
                method("PopClipRect")

                // Focus, Activation
                method("SetItemDefaultFocus")
                method("SetKeyboardFocusHere") {
                    argInt("offset", optional = true)
                }

                // Item/Widgets Utilities and Query Functions
                method("IsItemHovered", returnBoolean()) {
                    argInt("flags", optional = true)
                }
                method("IsItemActive", returnBoolean())
                method("IsItemFocused", returnBoolean())
                method("IsItemClicked", returnBoolean()) {
                    argInt("mouseButton", optional = true)
                }
                method("IsItemVisible", returnBoolean())
                method("IsItemEdited", returnBoolean())
                method("IsItemActivated", returnBoolean())
                method("IsItemDeactivated", returnBoolean())
                method("IsItemDeactivatedAfterEdit", returnBoolean())
                method("IsItemToggledOpen", returnBoolean())
                method("IsAnyItemHovered", returnBoolean())
                method("IsAnyItemActive", returnBoolean())
                method("IsAnyItemFocused", returnBoolean())
                method("GetItemRectMin", returnImVec2())
                method("GetItemRectMax", returnImVec2())
                method("GetItemRectSize", returnImVec2())
                method("SetItemAllowOverlap")

                // Viewports
                method("GetMainViewport", returnStruct("imgui.ImGuiViewport", static = true))

                // Miscellaneous Utilities
                method("IsRectVisible", returnBoolean()) {
                    argImVec2("size")
                }
                method("IsRectVisible", returnBoolean()) {
                    argImVec2("rectMin")
                    argImVec2("rectMax")
                }
                method("GetTime", returnDouble())
                method("GetFrameCount", returnInt())
                method("GetBackgroundDrawList", returnStruct("imgui.ImDrawList"))
                method("GetForegroundDrawList", returnStruct("imgui.ImDrawList"))
                method("GetBackgroundDrawList", returnStruct("imgui.ImDrawList")) {
                    argStruct("imgui.ImGuiViewport", "viewport")
                }
                method("GetForegroundDrawList", returnStruct("imgui.ImDrawList")) {
                    argStruct("imgui.ImGuiViewport", "viewport")
                }
                // TODO: GetDrawListSharedData
                method("GetStyleColorName", returnString()) {
                    argInt("idx")
                }
                method("SetStateStorage") {
                    argStruct("imgui.ImGuiStorage", "storage")
                }
                method("GetStateStorage", returnStruct("imgui.ImGuiStorage"))
                method("BeginChildFrame", returnBoolean()) {
                    argInt("id")
                    argImVec2("size")
                    argInt("flags", optional = true)
                }
                method("EndChildFrame")

                // Text Utilities
                method("CalcTextSize", returnImVec2()) {
                    argString("text")
                    argString("textEnd", optional = true, default = "NULL")
                    argBoolean("hideTextAfterDoubleHas", optional = true, default = "false")
                    argFloat("wrapWidth", optional = true)
                }

                // Color Utilities
                method("ColorConvertU32ToFloat4", returnImVec4()) {
                    argInt("in")
                }
                method("ColorConvertFloat4ToU32", returnInt()) {
                    argImVec4("in")
                }

                // Inputs Utilities: Keyboard
                method("GetKeyIndex", returnInt()) {
                    argInt("key")
                }
                method("IsKeyDown", returnBoolean()) {
                    argInt("userKeyIndex")
                }
                method("IsKeyPressed", returnBoolean()) {
                    argInt("userKeyIndex")
                    argBoolean("repeat", optional = true)
                }
                method("IsKeyReleased", returnBoolean()) {
                    argInt("userKeyIndex")
                }
                method("GetKeyPressedAmount", returnInt()) {
                    argInt("keyIndex")
                    argFloat("repeatDelay")
                    argFloat("rate")
                }
                method("CaptureKeyboardFromApp") {
                    argBoolean("value", optional = true)
                }

                // Inputs Utilities: Mouse
                method("IsMouseDown", returnBoolean()) {
                    argInt("button")
                }
                method("IsMouseClicked", returnBoolean()) {
                    argInt("button")
                    argBoolean("repeat", optional = true)
                }
                method("IsMouseReleased", returnBoolean()) {
                    argInt("button")
                }
                method("IsMouseDoubleClicked", returnBoolean()) {
                    argInt("button")
                }
                method("GetMouseClickedCount", returnInt()) {
                    argInt("button")
                }
                method("IsMouseHoveringRect", returnBoolean()) {
                    argImVec2("rMin")
                    argImVec2("rMax")
                    argBoolean("clip", optional = true)
                }
                method("IsAnyMouseDown", returnBoolean())
                method("GetMousePos", returnImVec2())
                method("GetMousePosOnOpeningCurrentPopup", returnImVec2())
                method("IsMouseDragging", returnBoolean()) {
                    argInt("button")
                    argFloat("lockThreshold", optional = true)
                }

                method("GetMouseDragDelta", returnImVec2()) {
                    argInt("button", optional = true, default = "ImGuiMouseButton_Left")
                    argFloat("lockThreshold", optional = true)
                }

                method("ResetMouseDragDelta") {
                    argInt("button", optional = true)
                }
                method("GetMouseCursor", returnInt())
                method("SetMouseCursor") {
                    argInt("cursorType")
                }
                method("CaptureMouseFromApp") {
                    argBoolean("value", optional = true)
                }

                // Clipboard Utilities
                method("GetClipboardText", returnString())
                method("SetClipboardText") {
                    argString("text")
                }

                // Settings/.Ini Utilities
                method("LoadIniSettingsFromDisk") {
                    argString("iniFilename")
                }
                method("LoadIniSettingsFromMemory") {
                    argString("iniData")
                    argInt("iniSize", optional = true)
                }
                method("SaveIniSettingsToDisk") {
                    argString("iniFilename")
                }
                method("SaveIniSettingsToMemory") {
                    argInt("outIniSize", optional = true, with = listOf(JniCast("(size_t*)&")))
                }

                // Debug Utilities
                method("DebugCheckVersionAndDataLayout", returnBoolean()) {
                    argString("versionStr")
                    argInt("szIO")
                    argInt("szStyle")
                    argInt("szVec2")
                    argInt("szVec4")
                    argInt("szDrawVert")
                    argInt("szDrawIdx")
                }

                // (Optional) Platform/OS interface for multi-viewport support
                method("GetPlatformIO", returnStruct("imgui.ImGuiPlatformIO", static = true, isRef = true))
                method("UpdatePlatformWindows")
                method("RenderPlatformWindowsDefault")
                method("DestroyPlatformWindows")
                method("FindViewportByID", returnStruct("imgui.ImGuiViewport")) {
                    argInt("id")
                }
                method("FindViewportByPlatformHandle", returnStruct("imgui.ImGuiViewport")) {
                    argLong("platformHandle", with = listOf(JniCast("(void*)")))
                }
            }
        },
    )
}
