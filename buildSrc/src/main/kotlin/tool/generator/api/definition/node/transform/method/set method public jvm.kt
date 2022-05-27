
package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation transforms methods to JVM form.
 * 1. Makes them public
 * 2. Tweaks the name
 * 3. Makes arguments final
 */
@Suppress("ClassName")
object `set method public jvm` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode) {
                transform(node)
            }
            result += node
        }

        return result
    }

    private fun transform(method: MethodDefinitionNode) {
        method.signature.visibility = SignatureVisibility.PUBLIC
        method.signature.name = method.signature.name.decapitalize()
        method.signature.args.forEach { arg ->
            arg.container.add(ArgFlag.FINAL)
        }
    }
}
