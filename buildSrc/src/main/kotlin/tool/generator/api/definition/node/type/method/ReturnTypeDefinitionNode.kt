package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define a type which should be returned by the method.
 */
data class ReturnTypeDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : MethodDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = ReturnTypeDefinitionNode(storage.copy())
}
