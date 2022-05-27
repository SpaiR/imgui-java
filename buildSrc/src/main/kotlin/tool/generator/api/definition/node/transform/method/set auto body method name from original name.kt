package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.autoBody
import tool.generator.api.definition.node.type.method.ext.methodName
import tool.generator.api.definition.node.type.method.ext.name
import tool.generator.api.definition.node.type.method.ext.signature

/**
 * Transformations sets methodName for auto-body with the name from the method signature.
 * This helps to persist initial method name, since during transformation chain it can be changed.
 */
@Suppress("ClassName")
object `set auto body method name from original name` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode) {
                node.autoBody.methodName = node.signature.name
            }
        }

        return result
    }
}
