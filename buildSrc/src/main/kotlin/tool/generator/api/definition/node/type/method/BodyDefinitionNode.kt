package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define the content of the method body. Basically a collection of string lines.
 */
data class BodyDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : MethodDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = BodyDefinitionNode(storage.copy())
}
