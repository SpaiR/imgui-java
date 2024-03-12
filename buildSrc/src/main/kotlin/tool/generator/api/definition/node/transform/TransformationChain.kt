package tool.generator.api.definition.node.transform

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.copy

/**
 * Transformation chains help to modify definitions or add additional definitions if required.
 * It works like: transformation gets a collection of definition nodes and return the new collection. (or the same one)
 * During the transformation process new nodes can be created or old one deleted. Transformations can be very flexible.
 * The idea of them is to provide the way to describe specific logic outside definitions.
 * For example, to handle ImVec2 type properly we need to create new methods and transformations can handle such case with ease.
 * Every new transformation gets a copy of nodes, so actions within one transformation are fully isolated.
 */
class TransformationChain(
    private val nodes: Collection<DefinitionNode>
) {
    fun transform(vararg transformations: Transform): Nodes {
        return transform(transformations.toList())
    }

    fun transform(transformations: Collection<Transform>): Nodes {
        var result = nodes.copy()
        transformations.forEach { transform ->
            result = transform.process(result)
        }
        return result
    }

    @FunctionalInterface
    interface Transform {
        fun process(nodes: Nodes): Nodes
    }
}
