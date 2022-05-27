package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation creates additional method to handle default jni arguments.
 * @see [defaultJniValue]
 */
@Suppress("ClassName")
object `add methods for default jni args` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode) {
                val args = node.signature.args
                args.forEachIndexed { index, arg ->
                    if (arg.hasDefaultJniValue && index + 1 < args.size) {
                        val firstPart = args.toList().subList(0, index)
                        val secondPart = args.toList().subList(index + 1, args.size)
                        val newArgs = firstPart + secondPart

                        val newMethod = node.copy()

                        newMethod.signature.container.get(ArgsDefinitionNode::class).let { argsNode ->
                            argsNode.container.clear(ArgDefinitionNode::class)
                            newArgs.forEach(argsNode.container::add)

                            val callList = newMethod.autoBody.argsCall.toMutableList()

                            if (node.signature.isNative) {
                                callList[index] = arg.defaultJniValue
                            } else {
                                callList.removeAt(index)
                            }

                            newMethod.autoBody.argsCall = callList
                        }

                        result += newMethod
                    }
                }
            }

            result += node
        }

        return result
    }
}
