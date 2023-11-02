package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.MethodSignature
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation removes methods with the same signature.
 */
@Suppress("ClassName")
object `remove duplicated signatures` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()
        val processedMethodSignature = mutableSetOf<MethodSignature>()

        for (node in nodes) {
            if (node is MethodDefinitionNode) {
                val nodeMethodSignature = getMethodSignature(node)

                if (processedMethodSignature.contains(nodeMethodSignature)) {
                    continue
                }

                processedMethodSignature += nodeMethodSignature
            }

            result += node
        }

        return result
    }

    private fun getMethodSignature(node: MethodDefinitionNode): MethodSignature {
        val name = node.signature.name
        val args = node.signature.args.map { a ->
            var typeName = a.argType.type.javaClass.simpleName
            // Pointers are arrays.
            if (a.argType.hasFlag(ArgTypeFlag.POINTER) || a.argType.hasFlag(ArgTypeFlag.POINTER_WITH_ARRAY)) {
                typeName += "Array"
            }
            typeName
        }
        return MethodSignature(name, args)
    }
}
