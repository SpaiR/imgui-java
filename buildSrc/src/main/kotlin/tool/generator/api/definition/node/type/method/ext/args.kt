package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode

/**
 * Returns a collection of [ArgDefinitionNode] nodes.
 */
val ArgsDefinitionNode.args: Collection<ArgDefinitionNode>
    get() = container.getAll(ArgDefinitionNode::class)
