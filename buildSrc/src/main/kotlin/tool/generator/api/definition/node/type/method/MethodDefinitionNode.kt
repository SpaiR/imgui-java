package tool.generator.api.definition.node.type.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.container.ContentStorage
import tool.generator.api.definition.node.container.DefinitionNodeContainer

/**
 * Definition node to define a method in general. This node contains a number of sub-nodes.
 * And all of them a basically containers for data defined in [tool.generator.api.definition.node.type.method.ext]
 * package.
 */
class MethodDefinitionNode(
    val container: DefinitionNodeContainer<Content> = DefinitionNodeContainer(),
    val storage: ContentStorage = ContentStorage(),
) : DefinitionNode {
    interface Content : DefinitionNode

    override fun copy() = MethodDefinitionNode(container.copy(), storage.copy())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MethodDefinitionNode

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
        return "MethodDefinitionNode(container=$container, storage=$storage)"
    }
}
