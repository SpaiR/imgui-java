package tool.generator.api.definition.dsl

import tool.generator.api.definition.dsl.method.ArgTypeDsl
import tool.generator.api.definition.dsl.method.ArgsDsl
import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.dsl.method.ReturnTypeDsl
import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.jniCast
import tool.generator.api.definition.node.type.method.ext.returnType

@DslMarker
annotation class DefinitionDsl

fun defines(vararg nodes: Nodes): Nodes {
    return mutableListOf<DefinitionNode>().apply {
        addAll(nodes.flatMap { it })
    }
}

fun MethodsDsl.method(
    name: String,
    returnType: ReturnTypeDefinitionNode? = null,
    argsDsl: (ArgsDsl.() -> Unit)? = null
) {
    method {
        signature {
            name(name)
            if (argsDsl != null) {
                args(argsDsl)
            }
        }
        body {
            autoBody()
        }
        if (returnType != null) {
            data.returnType = returnType
        }
    }
}

/*
    Return types
 */

fun returnStruct(name: String, static: Boolean = false, isRef: Boolean = false): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asStruct(name)
        if (static) {
            flagStatic()
        }
        if (isRef) {
            flagRef()
        }
    }.data
}

fun returnBoolean(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asBoolean()
    }.data
}

/*
    Argument types
 */

private fun ArgsDsl.rawArg(
    asFun: ArgTypeDsl.() -> Unit,
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    arg {
        type {
            asFun()
            if (isPointer) {
                flagPointer()
            }
            if (isArray) {
                flagArray()
            }
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
        if (jniCast != null) {
            data.jniCast = jniCast
        }
    }
}

fun ArgsDsl.argBoolean(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asBoolean() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argShort(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asShort() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argByte(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asByte() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argInt(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asInt() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argFloat(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asFloat() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argLong(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asLong() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argDouble(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    rawArg(
        { asDouble() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
        jniCast = jniCast,
    )
}

fun ArgsDsl.argObject(
    name: String,
    optional: Boolean = false,
    default: String? = null,
) {
    argObject("Object", name, optional, default)
}

fun ArgsDsl.argObject(
    type: String,
    name: String,
    optional: Boolean = false,
    default: String? = null,
) {
    rawArg(
        { asObject(type) },
        name,
        optional = optional,
        default = default,
    )
}

fun ArgsDsl.argGenericClass(
    name: String,
    literal: String = "T",
    optional: Boolean = false,
    default: String? = null,
) {
    rawArg(
        { asGenericClass(literal) },
        name,
        optional = optional,
        default = default,
    )
}

fun ArgsDsl.argString(
    name: String,
    optional: Boolean = false,
    isPointer: Boolean = false,
    isArray: Boolean = false,
    default: String? = null,
) {
    rawArg(
        { asString() },
        name,
        optional = optional,
        isPointer = isPointer,
        isArray = isArray,
        default = default,
    )
}

fun ArgsDsl.argDefault(default: String) {
    argNull(default)
}

fun ArgsDsl.argNull(default: String? = null) {
    arg {
        type {
            asNull()
        }
        name("# GENERATED NAME #")
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argStruct(
    type: String,
    name: String,
    optional: Boolean = false,
    default: String? = null,
) {
    rawArg(
        { asStruct(type) },
        name,
        optional = optional,
        default = default,
    )
}

fun ArgsDsl.argImVec2(
    name: String,
    optional: Boolean = false,
    default: String? = null,
) {
    rawArg(
        { asVec2() },
        name,
        optional = optional,
        default = default,
    )
}

fun ArgsDsl.argImVec4(
    name: String,
    optional: Boolean = false,
    default: String? = null,
) {
    rawArg(
        { asVec4() },
        name,
        optional = optional,
        default = default,
    )
}

fun DefineDsl.initConstructor(className: String) {
    line(
        """
        public $className() {
            imgui.ImGui.init();
        }
    """.trimIndent()
    )
}
