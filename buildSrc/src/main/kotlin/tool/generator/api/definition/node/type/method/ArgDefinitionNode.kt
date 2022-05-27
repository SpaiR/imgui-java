package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage
import tool.generator.api.definition.node.container.DefinitionNodeContainer

/**
 * Definition node to define an argument content.
 */
class ArgDefinitionNode(
    val container: DefinitionNodeContainer<Content> = DefinitionNodeContainer(),
    val storage: ContentStorage = ContentStorage(),
) : ArgsDefinitionNode.Content {
    interface Content : DefinitionNode

    override fun copy() = ArgDefinitionNode(container.copy(), storage.copy())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArgDefinitionNode

        if (container != other.container) return false
        if (storage != other.storage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = container.hashCode()
        result = 31 * result + storage.hashCode()
        return result
    }

    override fun toString(): String {
        return "ArgDefinitionNode(container=$container, storage=$storage)"
    }
}
