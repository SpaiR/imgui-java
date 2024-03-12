package tool.generator.api.definition.dsl.struct

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.transform.method.MARKER_AUTO_BODY
import tool.generator.api.definition.node.type.method.*
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.field.ext.FieldType

@DefinitionDsl
class FieldDsl {
    /**
     * Struct fields accessed though special accessor methods.
     */
    val data: MutableList<MethodDefinitionNode> = mutableListOf()

    private lateinit var fieldType: FieldType
    private lateinit var fieldName: String

    fun type(type: FieldType) {
        this.fieldType = type
    }

    fun name(name: String) {
        this.fieldName = name
    }

    fun withGetterAndSetter() {
        withGetter()
        withSetter()
    }

    fun withGetter() {
        data += MethodDefinitionNode().apply {
            signature.name = "Get${fieldName.capitalize()}"
            body.lines = listOf(MARKER_AUTO_BODY)
            returnType = ReturnTypeDefinitionNode().apply {
                type = fieldType.toReturnType()
            }
        }
    }

    fun withSetter() {
        data += MethodDefinitionNode().apply {
            signature.name = "Set${fieldName.capitalize()}"
            signature.args = ArgsDefinitionNode().apply {
                args = listOf(ArgDefinitionNode().apply {
                    argType = ArgTypeDefinitionNode().apply {
                        name = "value"
                        type = fieldType.toArgType()
                    }
                })
            }
            body.lines = listOf(MARKER_AUTO_BODY)
        }
    }
}
