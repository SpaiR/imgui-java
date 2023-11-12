package tool.generator.api.definition.node.render

import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC2_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC4_JVM
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

fun renderMethod(method: MethodDefinitionNode): String {
    return buildString {
        method.signature.let { signature ->
            append(signature.visibility.toString().toLowerCase())

            if (signature.isStatic) {
                append(" static")
            }
            if (signature.isFinal) {
                append(" final")
            }
            if (signature.isNative) {
                append(" native")
            }

            append(' ')
            append(renderReturnTypeInSignature(method))

            append(' ')
            append(signature.name)

            append("(")
            append(signature.argsList.joinToString {
                buildString {
                    if (it.hasFlag(ArgFlag.FINAL)) {
                        append("final ")
                    }

                    append(renderArgType(method.signature.isNative, it))
                    append(" ")
                    append(it.name)
                }
            })
            append(")")

            if (signature.isNative) {
                appendLine("; /*")
            } else {
                appendLine(" {")
            }
        }

        appendLine(renderBody(method).trimEnd())

        if (method.signature.isNative) {
            append("*/")
        } else {
            append("}")
        }
    }
}

private fun renderReturnTypeInSignature(method: MethodDefinitionNode): String {
    val returnType = method.returnType.type
    return if (returnType is ReturnType.Void) {
        "void"
    } else if (returnType is ReturnType.Struct) {
        if (method.signature.isNative) {
            "long"
        } else {
            returnType.name
        }
    } else if (returnType is ReturnType.GenericLiteral) {
        "<${returnType.name}> ${returnType.name}"
    } else if (returnType is ReturnType.Vec2) {
        TYPE_IM_VEC2_JVM
    } else if (returnType is ReturnType.Vec4) {
        TYPE_IM_VEC4_JVM
    } else if (returnType is ReturnType.String) {
        "String"
    } else {
        returnType.javaClass.simpleName.toLowerCase()
    }
}

private fun renderArgType(isNative: Boolean, arg: ArgDefinitionNode): String {
    val argType = arg.argType.type

    if (argType is ArgType.Struct) {
        return if (isNative) {
            "long"
        } else {
            argType.name
        }
    }

    if (argType is ArgType.GenericClass) {
        return "Class<${argType.name}>"
    }

    // There can only be a definition.
    argType as ArgType.Definition

    return if (isNative) {
        if (arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            argType.jniPointerName
        } else {
            argType.jniName
        }
    } else {
        if (arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            argType.jvmPointerName
        } else {
            argType.jvmName
        }
    }
}

private fun renderBody(method: MethodDefinitionNode): String {
    return method.body.lines.joinToString("\n").prependIndent("    ")
}
