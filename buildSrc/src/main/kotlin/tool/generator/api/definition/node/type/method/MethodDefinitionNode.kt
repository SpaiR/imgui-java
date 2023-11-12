package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define a method in general. This node contains a number of sub-nodes.
 * And all of them a basically containers for data defined in [tool.generator.api.definition.node.type.method.ext]
 * package.
 */
data class MethodDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : DefinitionNode {
    interface Content : DefinitionNode

    override fun copy() = MethodDefinitionNode(storage.copy())
}
