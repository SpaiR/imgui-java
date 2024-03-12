package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define the content of the auto-body.
 * It works like a "virtual" container which data is used when `AUTO_BODY_MARKER` is rendered.
 */
data class AutoBodyDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : MethodDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = AutoBodyDefinitionNode(storage.copy())
}
