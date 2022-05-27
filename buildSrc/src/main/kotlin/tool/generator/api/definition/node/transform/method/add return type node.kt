package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ReturnType
import tool.generator.api.definition.node.type.method.ext.type

/**
 * Transformation adds to the method [ReturnTypeDefinitionNode] node if absent.
 * Mostly to ensure we have a proper node structure.
 */
@Suppress("ClassName")
object `add return type node` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && !node.container.contains(ReturnTypeDefinitionNode::class)) {
                node.container.add(ReturnTypeDefinitionNode().apply {
                    type = ReturnType.Void
                })
            }
        }

        return result
    }
}
