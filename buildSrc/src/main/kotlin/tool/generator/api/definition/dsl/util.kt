package tool.generator.api.definition.dsl

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

fun ArgsDsl.argInt(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    jniCast: String? = null
) {
    arg {
        type {
            asInt()
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

fun ArgsDsl.argIntPtr(name: String, optional: Boolean = false, default: String? = null, withArray: Boolean = false) {
    arg {
        type {
            asInt()
            flagPointer()
            if (withArray) {
                flatPointerWithArray()
            }
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argIntArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asIntArray()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argFloat(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    arg {
        type {
            asFloat()
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

fun ArgsDsl.argFloatPtr(name: String, optional: Boolean = false, default: String? = null, withArray: Boolean = false) {
    arg {
        type {
            asFloat()
            flagPointer()
            if (withArray) {
                flatPointerWithArray()
            }
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argFloatArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asFloatArray()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argShort(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    arg {
        type {
            asShort()
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

fun ArgsDsl.argShortPtr(name: String, optional: Boolean = false, default: String? = null, withArray: Boolean = false) {
    arg {
        type {
            asShort()
            flagPointer()
            if (withArray) {
                flatPointerWithArray()
            }
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argByteArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asByteArray()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argLong(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    arg {
        type {
            asLong()
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

fun ArgsDsl.argLongPtr(name: String, optional: Boolean = false, default: String? = null, withArray: Boolean = false) {
    arg {
        type {
            asLong()
            flagPointer()
            if (withArray) {
                flatPointerWithArray()
            }
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argDouble(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    jniCast: String? = null,
) {
    arg {
        type {
            asDouble()
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

fun ArgsDsl.argDoublePtr(name: String, optional: Boolean = false, default: String? = null, withArray: Boolean = false) {
    arg {
        type {
            asDouble()
            flagPointer()
            if (withArray) {
                flatPointerWithArray()
            }
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argObject(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asRaw()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argGenericClass(
    name: String,
    genericLiteral: String = "T",
    optional: Boolean = false,
    default: String? = null
) {
    arg {
        type {
            asGenericClass(genericLiteral)
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argString(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asString()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argStringArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asStringArray()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
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

fun ArgsDsl.argStruct(type: String, name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asStruct(type)
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun ArgsDsl.argImVec2(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asVec2()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
    }
}

fun DefineDsl.initConstructor(className: String) {
    line("""
        public $className() {
            imgui.ImGui.init();
        }
    """.trimIndent())
}
