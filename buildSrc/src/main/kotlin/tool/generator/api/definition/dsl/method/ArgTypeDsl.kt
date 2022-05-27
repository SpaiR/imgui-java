package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ArgType
import tool.generator.api.definition.node.type.method.ext.ArgTypeFlag
import tool.generator.api.definition.node.type.method.ext.type

@DefinitionDsl
class ArgTypeDsl {
    val data = ArgTypeDefinitionNode()

    fun type(type: ArgType) {
        data.type = type
    }

    fun asString() {
        type(ArgType.String)
    }

    fun asNull() {
        type(ArgType.Null)
    }

    fun asInt() {
        type(ArgType.Int)
    }

    fun asIntArray() {
        type(ArgType.IntArray)
    }

    fun asFloat() {
        type(ArgType.Float)
    }

    fun asFloatArray() {
        type(ArgType.FloatArray)
    }

    fun asShort() {
        type(ArgType.Short)
    }

    fun asShortArray() {
        type(ArgType.ShortArray)
    }

    fun asLong() {
        type(ArgType.Long)
    }

    fun asLongArray() {
        type(ArgType.LongArray)
    }

    fun asDouble() {
        type(ArgType.Double)
    }

    fun asDoubleArray() {
        type(ArgType.DoubleArray)
    }

    fun asBoolean() {
        type(ArgType.Boolean)
    }

    fun asStruct(name: String) {
        type(ArgType.Struct(name))
    }

    fun asVec2() {
        type(ArgType.Vec2)
    }

    fun asVec4() {
        type(ArgType.Vec4)
    }

    fun flagPointer() {
        data.container.add(ArgTypeFlag.POINTER)
    }

    fun flatPointerWithArray() {
        data.container.add(ArgTypeFlag.POINTER_WITH_ARRAY)
    }
}
