package tool.generator.api.definition.node.render

import org.reflections.Reflections
import tool.generator.api.definition.Definition
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC2_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC4_JVM
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

interface MethodArgRender {
    fun render(arg: ArgDefinitionNode): String
    fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean
}

private val methodArgRenderLists: List<MethodArgRender> by lazy {
    Reflections(Definition.ROOT_PATH)
        .getSubTypesOf(MethodArgRender::class.java)
        .map { it.getDeclaredConstructor().newInstance() }
}

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
            append(signature.argsList.joinToString { renderArg(method, it) })
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

private fun renderArg(method: MethodDefinitionNode, arg: ArgDefinitionNode): String {
    try {
        return methodArgRenderLists.first { it.isApplicable(method, arg) }.render(arg)
    } catch (e: NoSuchElementException) {
        throw RuntimeException("Unable to find method arg render for method:[$method] arg:[$arg]")
    }
}

private fun renderBody(method: MethodDefinitionNode): String {
    return method.body.lines.joinToString("\n").prependIndent("    ")
}
