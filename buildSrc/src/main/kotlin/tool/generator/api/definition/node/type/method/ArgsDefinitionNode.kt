package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Container for all method arguments.
 */
data class ArgsDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : SignatureDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = ArgsDefinitionNode(storage.copy())
}
