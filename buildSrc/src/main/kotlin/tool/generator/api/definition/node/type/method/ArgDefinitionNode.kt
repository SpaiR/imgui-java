package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define an argument content.
 */
data class ArgDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : ArgsDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = ArgDefinitionNode(storage.copy())
}
