package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeStruct

@Suppress("ClassName")
object `set body from autoBody` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode) {
                val lines = node.body.lines.toMutableList()
                lines.toList().forEachIndexed { index, line ->
                    if (line == MARKER_AUTO_BODY) {
                        lines[index] = renderAutoBody(node)
                    }
                }
                node.body.lines = lines
            }
        }

        return result
    }

    private fun renderAutoBody(method: MethodDefinitionNode): String {
        val isStruct = method.returnType.type is ReturnTypeStruct
        val isStatic = method.returnType.hasFlag(ReturnTypeFlag.STATIC)
        return if (isStruct && isStatic && !method.signature.isNative) {
            renderAutoBodyForJvmWithStaticStructField(method)
        } else {
            renderDefaultAutoBody(method)
        }
    }

    private fun renderAutoBodyForJvmWithStaticStructField(method: MethodDefinitionNode): String {
        return buildString {
            val fieldName = getStaticStructFieldName(method)
            append(fieldName)
            append('.')
            append(FIELD_PTR_JVM)
            append(" = ")
            append(renderAutoBodyMethodCall(method))
            append(';')
            appendLine()
            append("return ")
            append(fieldName)
            append(';')
        }
    }

    private fun renderDefaultAutoBody(method: MethodDefinitionNode): String {
        val returnType = method.returnType.type
        return buildString {
            if (returnType != ReturnTypeGeneral.VOID) {
                append("return ")
            }

            if (returnType == ReturnTypeGeneral.STRING && method.signature.isNative) {
                append("env->NewStringUTF(")
            }
            if (returnType is ReturnTypeStruct) {
                if (method.signature.isNative) {
                    append("($CAST_PTR_JNI)")
                    if (method.returnType.hasFlag(ReturnTypeFlag.REF)) {
                        append('&')
                    }
                } else {
                    append("new ${returnType.type}(")
                }
            }

            append(renderAutoBodyMethodCall(method))

            if (returnType == ReturnTypeGeneral.STRING && method.signature.isNative) {
                append(')')
            }
            if (returnType is ReturnTypeStruct && !method.signature.isNative) {
                append(')')
            }

            append(';')
        }
    }

    private fun renderAutoBodyMethodCall(method: MethodDefinitionNode): String {
        return buildString {
            append(method.autoBody.thisPointer)

            if (!method.signature.isNative) {
                append(METHOD_JNI_PREFIX)
            }

            append(method.autoBody.methodName)
            append('(')
            append(method.autoBody.argsCall.joinToString())
            append(')')
        }
    }
}
