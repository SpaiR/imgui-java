package tool.generator.api.definition.node.transform.struct

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.autoBody
import tool.generator.api.definition.node.type.method.ext.isNative
import tool.generator.api.definition.node.type.method.ext.methodName
import tool.generator.api.definition.node.type.method.ext.signature

/**
 * Transformation removes `Get` and `Set` prefixes from autoBody content.
 */
@Suppress("ClassName")
object `remove get and set from autoBody jni` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && node.signature.isNative) {
                node.autoBody.methodName = node.autoBody.methodName.removePrefix("Get").removePrefix("Set")
            }
        }

        return result
    }
}
