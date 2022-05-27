package tool.generator.api.definition.node.render

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.type.JniBlockDefinitionNode
import tool.generator.api.definition.node.type.LineDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode

class NodeRenderer {
    fun render(definitionNode: DefinitionNode): String {
        return when (definitionNode) {
            is LineDefinitionNode -> definitionNode.value
            is JniBlockDefinitionNode -> renderJniBlock(definitionNode)
            is MethodDefinitionNode -> renderMethod(definitionNode)
            else -> error("Invalid node type to render: $definitionNode")
        }
    }
}
