package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.Definition
import tool.generator.api.definition.dsl.decl.containsMethodName
import tool.generator.api.definition.dsl.decl.containsMethodSignature
import tool.generator.api.definition.dsl.decl.convertFunctionDeclsToDsl
import tool.generator.api.definition.dsl.define
import tool.generator.api.definition.dsl.defines
import tool.generator.api.definition.dsl.initConstructor
import tool.generator.api.definition.node.MethodSignature
import tool.generator.api.definition.node.transform.method.`pre process method flags`
import tool.generator.api.definition.node.transform.method.`remove duplicated signatures`
import tool.generator.api.definition.node.transform.method.`set method public jvm`
import tool.generator.api.definition.node.transform.method.`sort methods`
import tool.generator.api.definition.node.transform.template.methodsTransformationsTemplate
import tool.generator.ast.AstFunctionDecl
import tool.generator.ast.AstNamespaceDecl
import tool.generator.ast.AstParmVarDecl
import tool.generator.ast.parseAstResource

class ImGuiApi : Definition {
    companion object {
        private const val THIS_POINTER_IMGUI = "ImGui::"
        private const val IMGUI_NAMESPACE = "ImGui"

        /**
         * Methods which return value is a static (global) object.
         * They should always return the same Java instance with the same native pointer.
         */
        private val staticReturnMethods: Set<String> = setOf(
            "GetCurrentContext",
            "GetIO",
            "GetStyle",
            "GetDrawData",
            "GetMainViewport",
            "GetPlatformIO",
        )

        /**
         * Methods with a specific signature. They need a manual handling.
         */
        private val ignoredMethodsSignatures = setOf(
            MethodSignature("PushID", setOf("const void *")),
            MethodSignature("GetID", setOf("const void *")),
            MethodSignature("TreeNode", setOf("const void *", "const char *", AstParmVarDecl.FORMAT_ATTR_NAME)),
            MethodSignature("TreeNodeEx", setOf("const void *", "ImGuiTreeNodeFlags", "const char *", AstParmVarDecl.FORMAT_ATTR_NAME)),
            MethodSignature("TreePush", setOf("const void *")),
            MethodSignature("GetColorU32", setOf("ImU32")),
        )

        /**
         * Methods mostly defined manually due to their specific signature or behaviour.
         */
        private val ignoredMethodNames: Set<String> = setOf(
            // Defined in `ImGuiApi_manual`
            "Combo",
            "ListBox",
            "IsMousePosValid",
            "SetDragDropPayload", "AcceptDragDropPayload", "GetDragDropPayload",
            "ColorConvertRGBtoHSV", "ColorConvertHSVtoRGB",

            // Defined in `ImGuiApi_gen`
            "SetNextWindowSizeConstraints",
            "SaveIniSettingsToMemory",
            "RenderPlatformWindowsDefault",
            "FindViewportByPlatformHandle",

            // Defined in `ImGuiApi_drag_sliders`
            "DragFloat", "DragFloat2", "DragFloat3", "DragFloat4", "DragFloatRange2",
            "DragInt", "DragInt2", "DragInt3", "DragInt4", "DragIntRange2",
            "DragScalar",

            // Defined in `ImGuiApi_regular_sliders`
            "SliderFloat", "SliderFloat2", "SliderFloat3", "SliderFloat4", "SliderAngle",
            "SliderInt", "SliderInt2", "SliderInt3", "SliderInt4",
            "SliderScalar", "VSliderFloat", "VSliderInt", "VSliderScalar",

            // Defined in `ImGuiApi_inputs`
            "InputFloat", "InputFloat2", "InputFloat3", "InputFloat4",
            "InputInt", "InputInt2", "InputInt3", "InputInt4",
            "InputDouble", "InputScalar",

            // Defined in `ImGuiApi_color_editor_n_picker`
            "ColorEdit3", "ColorEdit4", "ColorPicker3", "ColorPicker4",

            // Defined in `ImGuiApi_data_plotting`
            "PlotLines", "PlotHistogram",

            // Ignored
            "TextV", "TextColoredV", "TextDisabledV", "TextWrappedV",
            "LabelTextV", "BulletTextV",
            "DragScalarN", "SliderScalarN", "InputScalarN",
            "TreeNodeV", "TreeNodeExV",
            "SetTooltipV", "LogTextV",
            "SetAllocatorFunctions", "GetAllocatorFunctions",
            "MemAlloc", "MemFree",

            // FIXME
            "InputText",
            "InputTextMultiline",
            "InputTextWithHint",

            // TODO
            "TableGetSortSpecs",
            "GetDrawListSharedData",
        )
    }

    private fun getAllAstImGuiFunctions(): Collection<AstFunctionDecl> {
        return parseAstResource("ast-imgui.json").decls
            .filterIsInstance<AstNamespaceDecl>()
            .first { it.name == IMGUI_NAMESPACE }.decls
            .filterIsInstance<AstFunctionDecl>()
    }

    private fun getAstImGuiFunctions(): Collection<AstFunctionDecl> {
        return getAllAstImGuiFunctions()
            .filterNot(containsMethodName(ignoredMethodNames))
            .filterNot(containsMethodSignature(ignoredMethodsSignatures))
    }

    override fun getNodes() = defines(
        define {
            initConstructor(ImGuiApi::class.java.simpleName)
            jniBlock("#include \"_common.h\"")
        },
        define({
            transformation {
                chain(
                    `remove duplicated signatures`,
                    `sort methods`(getAllAstImGuiFunctions().associate { it.name.toLowerCase() to it.offset })
                )
            }
        }, defines(
            define({
                transformation {
                    chain(`set method public jvm`)
                }
            }) {
                // Add them without transforms.
                manualMethods()
            },
            define({
                transformation {
                    chain(
                        `pre process method flags`(
                            staticReturnMethods = staticReturnMethods
                        ),
                    )
                }
                methodsTransformationsTemplate(
                    jniAutoBodyThisPointer = THIS_POINTER_IMGUI
                )
            }) {
                methods {
                    genDragSliders()
                    genRegularSliders()
                    genInputs()
                    genColorEditorAndPicker()
                    genDataPlotting()
                    genMethods()
                    convertFunctionDeclsToDsl(getAstImGuiFunctions())
                }
            },
        )),
    )
}
