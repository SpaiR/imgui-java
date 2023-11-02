package tool.generator.api.definition.dsl

import tool.generator.api.definition.dsl.method.ArgsDsl
import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.dsl.method.ReturnTypeDsl
import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode

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
            data.container.add(returnType)
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

fun returnImVec2(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asVec2()
    }.data
}

fun returnImVec4(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asVec4()
    }.data
}

fun returnBoolean(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asBoolean()
    }.data
}

fun returnFloat(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asFloat()
    }.data
}

fun returnInt(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asInt()
    }.data
}

fun returnDouble(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asDouble()
    }.data
}

fun returnString(): ReturnTypeDefinitionNode {
    return ReturnTypeDsl().apply {
        asString()
    }.data
}

/*
    Argument types
 */

fun ArgsDsl.argBoolean(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    with: Collection<ArgDefinitionNode.Content> = emptyList()
) {
    arg {
        type {
            asBoolean()
        }
        name(name)
        if (optional) {
            optional()
        }
        if (default != null) {
            defaultJniValue(default)
        }
        if (with.isNotEmpty()) {
            data.container.addAll(with)
        }
    }
}

fun ArgsDsl.argBooleanPtr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asBoolean()
            flagPointer()
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

fun ArgsDsl.argInt(
    name: String,
    optional: Boolean = false,
    default: String? = null,
    with: Collection<ArgDefinitionNode.Content> = emptyList()
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
        if (with.isNotEmpty()) {
            data.container.addAll(with)
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
    with: Collection<ArgDefinitionNode.Content> = emptyList()
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
        if (with.isNotEmpty()) {
            data.container.addAll(with)
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
    with: Collection<ArgDefinitionNode.Content> = emptyList()
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
        if (with.isNotEmpty()) {
            data.container.addAll(with)
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

fun ArgsDsl.argShortArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asShortArray()
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
    with: Collection<ArgDefinitionNode.Content> = emptyList()
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
        if (with.isNotEmpty()) {
            data.container.addAll(with)
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

fun ArgsDsl.argLongArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asLongArray()
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
    with: Collection<ArgDefinitionNode.Content> = emptyList()
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
        if (with.isNotEmpty()) {
            data.container.addAll(with)
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

fun ArgsDsl.argDoubleArr(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asDoubleArray()
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

fun ArgsDsl.argImVec4(name: String, optional: Boolean = false, default: String? = null) {
    arg {
        type {
            asVec4()
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
