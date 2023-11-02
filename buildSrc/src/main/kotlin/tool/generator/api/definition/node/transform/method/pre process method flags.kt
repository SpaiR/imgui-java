package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ReturnTypeFlag
import tool.generator.api.definition.node.type.method.ext.name
import tool.generator.api.definition.node.type.method.ext.returnType
import tool.generator.api.definition.node.type.method.ext.signature

/**
 * Transformation adds to the method [ArgsDefinitionNode] node if absent.
 * Mostly to ensure we have a proper node structure.
 */
@Suppress("ClassName")
open class `pre process method flags`(
    val staticReturnMethods: Set<String> = emptySet(),
) : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        nodes.forEach {
            if (it is MethodDefinitionNode) {
                if (staticReturnMethods.contains(it.signature.name)) {
                    it.returnType.container.add(ReturnTypeFlag.STATIC)
                }
            }
        }
        return nodes
    }
}
