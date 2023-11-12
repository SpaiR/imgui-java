package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define an argument type.
 */
data class ArgTypeDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : ArgDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = ArgTypeDefinitionNode(storage.copy())
}
