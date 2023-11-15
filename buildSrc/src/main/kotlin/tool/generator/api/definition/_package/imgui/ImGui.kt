package tool.generator.api.definition._package.imgui

import tool.generator.api.definition.Definition
import tool.generator.api.definition._package.imgui.binding.ImGuiApi
import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral

class ImGui : Definition {
    companion object {
        private const val API_FIELD_NAME = "api"
    }

    override fun getNodes(): Collection<DefinitionNode> {
        return emptyList() //TODO: remove to generate nodes for ImGui
//        return defines(
//            define {
//                line("""
//                    /**
//                     * Field with generated {@link ${getApiClass()}} to use Dear ImGui API.
//                     * Can be replaced with custom API implementation if required.
//                     */
//                    public static ${getApiClass()} $API_FIELD_NAME = new ${getApiClass()}();
//                """.trimIndent())
//            },
//            collectImGuiApiMethods()
//        )
    }

    private fun getApiClass(): String {
        return ImGuiApi::class.java.name.removePrefix(Definition.PACKAGE_PATH)
    }

    private fun collectImGuiApiMethods(): Collection<DefinitionNode> {
        return ImGuiApi().getNodes()
            .filterIsInstance(MethodDefinitionNode::class.java)
            .filterNot { it.signature.isNative }
            .map { method ->
                method.signature.isStatic = true
                method.body.lines = listOf(
                    buildString {
                        if (method.returnType.type != ReturnTypeGeneral.VOID) {
                            append("return ")
                        }

                        append(API_FIELD_NAME)
                        append('.')
                        append(method.signature.name)
                        append('(')
                        append(method.signature.argsList.joinToString { it.name })
                        append(')')
                        append(';')
                    }
                )

                method
            }
    }
}
