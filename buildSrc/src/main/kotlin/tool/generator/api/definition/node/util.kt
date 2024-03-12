package tool.generator.api.definition.node

typealias Nodes = Collection<DefinitionNode>

fun Nodes.copy(): Nodes {
    return map(DefinitionNode::copy).map { it as DefinitionNode }
}

data class MethodSignature(val name: String, val argTypes: Collection<String>)
