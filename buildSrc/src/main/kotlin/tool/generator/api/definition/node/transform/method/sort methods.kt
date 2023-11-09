package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.isNative
import tool.generator.api.definition.node.type.method.ext.name
import tool.generator.api.definition.node.type.method.ext.signature

/**
 * Transformation to sort methods according to the provided order.
 */
@Suppress("ClassName")
class `sort methods`(
    private val sortOrder: Map<String, Int>
) : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()
        val jvmSortFunctions = mutableListOf<MethodDefinitionNode>()
        val jniSortFunctions = mutableListOf<MethodDefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode) {
                if (node.signature.isNative) {
                    jniSortFunctions += node
                } else {
                    jvmSortFunctions += node
                }
            } else {
                result += node
            }
        }

        if (jvmSortFunctions.isEmpty() && jniSortFunctions.isEmpty()) {
            return result
        }

        jvmSortFunctions.sortBy {
            // If the function is unknown, then we place it in the end.
            sortOrder[it.signature.name.toLowerCase()] ?: Int.MAX_VALUE
        }
        jniSortFunctions.sortBy {
            // If the function is unknown, then we place it in the end.
            sortOrder[it.signature.name.substringAfter(METHOD_JNI_PREFIX).toLowerCase()] ?: Int.MAX_VALUE
        }

        result += jvmSortFunctions
        result += jniSortFunctions

        return result

    }
}
