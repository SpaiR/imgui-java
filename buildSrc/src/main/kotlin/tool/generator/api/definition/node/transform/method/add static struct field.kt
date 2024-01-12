package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.LineDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeStruct

/**
 * Transformation which creates an additional node with a static field.
 * The field itself is used for return types marked with a static flag.
 */
@Suppress("ClassName")
object `add static struct field` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode) {
                val returnType = node.returnType.type
                val isStatic = node.returnType.hasFlag(ReturnTypeFlag.STATIC)
                if (returnType is ReturnTypeStruct && isStatic && !node.signature.isNative) {
                    // Generates field like:
                    // private static final $ReturnType $generatedName = new $ReturnType(0);
                    result += LineDefinitionNode(
                        buildString {
                            append("private static final ")
                            append(returnType.type)
                            append(' ')
                            append(getUniqueMethodFieldName(node))
                            append(" = new ")
                            append(returnType.type)
                            append("(0);")
                        }
                    )
                }
            }

            result += node
        }

        return result
    }
}
