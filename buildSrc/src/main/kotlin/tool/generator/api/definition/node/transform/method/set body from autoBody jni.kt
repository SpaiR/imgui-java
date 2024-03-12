package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeStruct

@Suppress("ClassName")
abstract class`abstract set body from autoBody jni` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && node.signature.isNative) {
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
        return renderDefaultAutoBody(method)
    }

    private fun renderDefaultAutoBody(method: MethodDefinitionNode): String {
        val returnType = method.returnType.type
        return buildString {
            if (returnType != ReturnTypeGeneral.VOID) {
                append("return ")
            }

            if (returnType == ReturnTypeGeneral.STRING) {
                append("env->NewStringUTF(")
            }
            if (returnType is ReturnTypeStruct) {
                append("($CAST_PTR_JNI)")
                if (method.returnType.hasFlag(ReturnTypeFlag.REF)) {
                    append('&')
                }
            }

            append(renderAutoBodyMethodCall(method))

            if (returnType == ReturnTypeGeneral.STRING) {
                append(')')
            }

            append(';')
        }
    }

    open fun renderAutoBodyMethodCall(method: MethodDefinitionNode): String {
        return buildString {
            append(method.autoBody.thisPointer)
            append(method.autoBody.methodName)
            append('(')
            append(method.autoBody.argsCall.joinToString())
            append(')')
        }
    }
}

@Suppress("ClassName")
object `set body from autoBody jni` : `abstract set body from autoBody jni`()
