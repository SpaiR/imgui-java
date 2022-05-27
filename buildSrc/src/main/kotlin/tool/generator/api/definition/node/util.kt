package tool.generator.api.definition.node

typealias Nodes = Collection<DefinitionNode>

fun Nodes.copy(): Nodes {
    return map(DefinitionNode::copy)
}
