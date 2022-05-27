package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.SignatureDefinitionNode

/**
 * Transformation adds to the method [ArgsDefinitionNode] node if absent.
 * Mostly to ensure we have a proper node structure.
 */
@Suppress("ClassName")
object `add args node` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode) {
                node.container.get(SignatureDefinitionNode::class).let {
                    if (!it.container.contains(ArgsDefinitionNode::class)) {
                        it.container.add(ArgsDefinitionNode())
                    }
                }
            }
        }

        return result
    }
}
