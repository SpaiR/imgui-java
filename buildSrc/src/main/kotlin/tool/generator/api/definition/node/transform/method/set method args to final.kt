package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation transforms JVM methods args to final.
 */
@Suppress("ClassName")
object `set method args to final` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode && !node.signature.isNative) {
                transform(node)
            }
            result += node
        }

        return result
    }

    private fun transform(method: MethodDefinitionNode) {
        method.signature.argsList.forEach { arg ->
            arg.addFlag(ArgFlag.FINAL)
        }
    }
}
