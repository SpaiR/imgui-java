package tool.generator.api.definition.node.type.field.ext

import tool.generator.api.definition.node.type.method.ext.ArgType
import tool.generator.api.definition.node.type.method.ext.ReturnType
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypePrimitive
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeString
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeStruct
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeVec
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeStruct
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeVec

interface FieldType {
    fun toArgType(): ArgType

    fun toReturnType(): ReturnType
}

enum class FieldTypeConst : FieldType {
    BOOL,
    BYTE,
    SHORT,
    INT,
    FLOAT,
    DOUBLE,
    LONG,
    STRING,
    VEC2,
    VEC4;

    override fun toArgType(): ArgType {
        return when (this) {
            BOOL -> ArgTypePrimitive.BOOL
            BYTE -> ArgTypePrimitive.BYTE
            SHORT -> ArgTypePrimitive.SHORT
            INT -> ArgTypePrimitive.INT
            FLOAT -> ArgTypePrimitive.FLOAT
            DOUBLE -> ArgTypePrimitive.DOUBLE
            LONG -> ArgTypePrimitive.LONG
            STRING -> ArgTypeString
            VEC2 -> ArgTypeVec.V2
            VEC4 -> ArgTypeVec.V4
        }
    }

    override fun toReturnType(): ReturnType {
        return when (this) {
            BOOL -> ReturnTypeGeneral.BOOL
            BYTE -> TODO()
            SHORT -> TODO()
            INT -> ReturnTypeGeneral.INT
            FLOAT -> ReturnTypeGeneral.FLOAT
            DOUBLE -> ReturnTypeGeneral.DOUBLE
            LONG -> ReturnTypeGeneral.LONG
            STRING -> ReturnTypeGeneral.STRING
            VEC2 -> ReturnTypeVec.V2
            VEC4 -> ReturnTypeVec.V4
        }
    }
}

data class FieldTypeStruct(val value: String) : FieldType {
    override fun toArgType(): ArgType {
        return ArgTypeStruct(value)
    }

    override fun toReturnType(): ReturnType {
        return ReturnTypeStruct(value)
    }
}
