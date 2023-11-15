package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ReturnType
import tool.generator.api.definition.node.type.method.ext.ReturnTypeFlag
import tool.generator.api.definition.node.type.method.ext.addFlag
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGenericLiteral
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeStruct
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeVec
import tool.generator.api.definition.node.type.method.ext.type

@DefinitionDsl
class ReturnTypeDsl {
    val data = ReturnTypeDefinitionNode()

    fun type(type: ReturnType) {
        data.type = type
    }

    fun asVoid() {
        type(ReturnTypeGeneral.VOID)
    }

    fun asString() {
        type(ReturnTypeGeneral.STRING)
    }

    fun asBoolean() {
        type(ReturnTypeGeneral.BOOL)
    }

    fun asInt() {
        type(ReturnTypeGeneral.INT)
    }

    fun asDouble() {
        type(ReturnTypeGeneral.DOUBLE)
    }

    fun asFloat() {
        type(ReturnTypeGeneral.FLOAT)
    }

    fun asVec2() {
        type(ReturnTypeVec.V2)
    }

    fun asVec4() {
        type(ReturnTypeVec.V4)
    }

    fun asStruct(name: String) {
        type(ReturnTypeStruct(name))
    }

    fun asGenericLiteral(literal: String = "T") {
        type(ReturnTypeGenericLiteral(literal))
    }

    fun flagStatic() {
        data.addFlag(ReturnTypeFlag.STATIC)
    }

    fun flagRef() {
        data.addFlag(ReturnTypeFlag.REF)
    }
}
