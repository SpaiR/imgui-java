package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CollectionContent
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode

/**
 * Returns a collection of [ArgDefinitionNode] nodes.
 */
var ArgsDefinitionNode.args: Collection<ArgDefinitionNode>
    get() = storage.get<CollectionContent<ArgDefinitionNode>>("args")?.value?.toList() ?: emptyList()
    set(value) = storage.put("args", CollectionContent(value.toList()))
