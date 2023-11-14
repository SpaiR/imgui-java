package tool.generator.api.definition.node.type.method.ext.arg

import tool.generator.api.definition.node.render.MethodArgRender
import tool.generator.api.definition.node.transform.method.*
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

enum class ArgTypePrimitive : ArgType {
    BOOL,
    BYTE,
    SHORT,
    INT,
    FLOAT,
    DOUBLE,
    LONG;

    override fun copy() = this
}

class MethodArgPrimitiveRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        var result = if (arg.argType.hasFlag(ArgTypeFlag.ARRAY)) {
            primitive2array(arg.argType.type as ArgTypePrimitive)
        } else {
            primitive2type(arg.argType.type as ArgTypePrimitive)
        }
        if (arg.hasFlag(ArgFlag.FINAL)) {
            result = "final $result"
        }
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (arg.argType.type !is ArgTypePrimitive) {
            return false
        }
        if (arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            return false
        }
        return true
    }
}

class MethodArgPrimitivePointerJvmRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        var result = when (arg.argType.type as ArgTypePrimitive) {
            ArgTypePrimitive.BOOL -> TYPE_IM_BOOL
            ArgTypePrimitive.BYTE -> TYPE_IM_BYTE
            ArgTypePrimitive.SHORT -> TYPE_IM_SHORT
            ArgTypePrimitive.INT -> TYPE_IM_INT
            ArgTypePrimitive.FLOAT -> TYPE_IM_FLOAT
            ArgTypePrimitive.DOUBLE -> TYPE_IM_DOUBLE
            ArgTypePrimitive.LONG -> TYPE_IM_LONG
        }
        if (arg.hasFlag(ArgFlag.FINAL)) {
            result = "final $result"
        }
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (method.signature.isNative) {
            return false
        }
        if (arg.argType.type !is ArgTypePrimitive) {
            return false
        }
        if (!arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            return false
        }
        return true
    }
}

class MethodArgPrimitivePointerJniRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        val result = primitive2array(arg.argType.type as ArgTypePrimitive)
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (!method.signature.isNative) {
            return false
        }
        if (arg.argType.type !is ArgTypePrimitive) {
            return false
        }
        if (!arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            return false
        }
        return true
    }
}

private fun primitive2type(number: ArgTypePrimitive): String {
    return when (number) {
        ArgTypePrimitive.BOOL -> "boolean"
        ArgTypePrimitive.BYTE -> "byte"
        ArgTypePrimitive.SHORT -> "short"
        ArgTypePrimitive.INT -> "int"
        ArgTypePrimitive.FLOAT -> "float"
        ArgTypePrimitive.DOUBLE -> "double"
        ArgTypePrimitive.LONG -> "long"
    }
}

private fun primitive2array(number: ArgTypePrimitive): String {
    return when (number) {
        ArgTypePrimitive.BOOL -> "boolean[]"
        ArgTypePrimitive.BYTE -> "byte[]"
        ArgTypePrimitive.SHORT -> "short[]"
        ArgTypePrimitive.INT -> "int[]"
        ArgTypePrimitive.FLOAT -> "float[]"
        ArgTypePrimitive.DOUBLE -> "double[]"
        ArgTypePrimitive.LONG -> "long[]"
    }
}
