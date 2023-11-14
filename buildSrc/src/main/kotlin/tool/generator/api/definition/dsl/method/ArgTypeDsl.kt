package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ArgType
import tool.generator.api.definition.node.type.method.ext.ArgTypeFlag
import tool.generator.api.definition.node.type.method.ext.addFlag
import tool.generator.api.definition.node.type.method.ext.arg.*
import tool.generator.api.definition.node.type.method.ext.type

@DefinitionDsl
class ArgTypeDsl {
    val data = ArgTypeDefinitionNode()

    fun type(type: ArgType) {
        data.type = type
    }

    fun asObject() {
        type(ArgTypeCommonInstance("Object"))
    }

    fun asString() {
        type(ArgTypeString)
    }

    fun asNull() {
        type(ArgTypeNull)
    }

    fun asBoolean() {
        type(ArgTypePrimitive.BOOL)
    }

    fun asByte() {
        type(ArgTypePrimitive.BYTE)
    }

    fun asShort() {
        type(ArgTypePrimitive.SHORT)
    }

    fun asInt() {
        type(ArgTypePrimitive.INT)
    }

    fun asFloat() {
        type(ArgTypePrimitive.FLOAT)
    }

    fun asDouble() {
        type(ArgTypePrimitive.DOUBLE)
    }

    fun asLong() {
        type(ArgTypePrimitive.LONG)
    }

    fun asStruct(name: String) {
        type(ArgTypeStruct(name))
    }

    fun asGenericClass(genericLiteral: String = "T") {
        type(ArgTypeCommonInstance("Class<$genericLiteral>"))
    }

    fun asVec2() {
        type(ArgTypeVec.V2)
    }

    fun asVec4() {
        type(ArgTypeVec.V4)
    }

    fun flagPointer() {
        data.addFlag(ArgTypeFlag.POINTER)
    }

    fun flagArray() {
        data.addFlag(ArgTypeFlag.ARRAY)
    }
}
