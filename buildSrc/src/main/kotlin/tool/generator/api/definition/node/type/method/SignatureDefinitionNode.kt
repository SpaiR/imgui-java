package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage

/**
 * Definition node to define method signature.
 */
data class SignatureDefinitionNode(
    val storage: ContentStorage = ContentStorage(),
) : MethodDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = SignatureDefinitionNode(storage.copy())
}
