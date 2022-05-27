package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.autoBody
import tool.generator.api.definition.node.type.method.ext.thisPointer

/**
 * Transformation sets [thisPointer] for auto-body to [THIS_PTR_CALL_JVM].
 */
@Suppress("ClassName")
object `set auto body this pointer jvm` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode) {
                node.autoBody.thisPointer = THIS_PTR_CALL_JVM
            }
        }

        return result
    }
}
