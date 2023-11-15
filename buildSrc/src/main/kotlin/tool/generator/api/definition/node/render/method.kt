package tool.generator.api.definition.node.render

import tool.generator.api.definition.Definition
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

interface MethodArgRender {
    fun render(arg: ArgDefinitionNode): String
    fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean
}

interface MethodReturnRender {
    fun render(returnType: ReturnTypeDefinitionNode): String
    fun isApplicable(method: MethodDefinitionNode, returnType: ReturnTypeDefinitionNode): Boolean
}

private val methodArgRenderLists: List<MethodArgRender> by lazy {
    Definition.ROOT_REFLECTION
        .getSubTypesOf(MethodArgRender::class.java)
        .map { it.getDeclaredConstructor().newInstance() }
}

private val methodReturnRenderLists: List<MethodReturnRender> by lazy {
    Definition.ROOT_REFLECTION
        .getSubTypesOf(MethodReturnRender::class.java)
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
            append(renderReturn(method))

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

private fun renderReturn(method: MethodDefinitionNode): String {
    try {
        return methodReturnRenderLists.first { it.isApplicable(method, method.returnType) }.render(method.returnType)
    } catch (e: NoSuchElementException) {
        throw RuntimeException("Unable to render return:[${method.returnType}] for method:[$method]")
    }
}

private fun renderArg(method: MethodDefinitionNode, arg: ArgDefinitionNode): String {
    try {
        return methodArgRenderLists.first { it.isApplicable(method, arg) }.render(arg)
    } catch (e: NoSuchElementException) {
        throw RuntimeException("Unable to render arg:[$arg] for method:[$method]")
    }
}

private fun renderBody(method: MethodDefinitionNode): String {
    return method.body.lines.joinToString("\n").prependIndent("    ")
}
