package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ReturnType
import tool.generator.api.definition.node.type.method.ext.ReturnTypeFlag
import tool.generator.api.definition.node.type.method.ext.addFlag
import tool.generator.api.definition.node.type.method.ext.type

@DefinitionDsl
class ReturnTypeDsl {
    val data = ReturnTypeDefinitionNode()

    fun type(type: ReturnType) {
        data.type = type
    }

    fun asVoid() {
        type(ReturnType.Void)
    }

    fun asString() {
        type(ReturnType.String)
    }

    fun asInt() {
        type(ReturnType.Int)
    }

    fun asDouble() {
        type(ReturnType.Double)
    }

    fun asFloat() {
        type(ReturnType.Float)
    }

    fun asBoolean() {
        type(ReturnType.Boolean)
    }

    fun asVec2() {
        type(ReturnType.Vec2)
    }

    fun asVec4() {
        type(ReturnType.Vec4)
    }

    fun asStruct(name: String) {
        type(ReturnType.Struct(name))
    }

    fun asGenericLiteral(name: String = "T") {
        type(ReturnType.GenericLiteral(name))
    }

    fun flagStatic() {
        data.addFlag(ReturnTypeFlag.STATIC)
    }

    fun flagRef() {
        data.addFlag(ReturnTypeFlag.REF)
    }
}
