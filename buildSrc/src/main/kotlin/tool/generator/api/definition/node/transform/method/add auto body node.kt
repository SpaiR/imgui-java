package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.AutoBodyDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode

/**
 * Transformation adds to the method [AutoBodyDefinitionNode] node if absent.
 * Mostly to ensure we have a proper node structure.
 */
@Suppress("ClassName")
object `add auto body node` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && !node.container.contains(AutoBodyDefinitionNode::class)) {
                node.container.add(AutoBodyDefinitionNode())
            }
        }

        return result
    }
}
