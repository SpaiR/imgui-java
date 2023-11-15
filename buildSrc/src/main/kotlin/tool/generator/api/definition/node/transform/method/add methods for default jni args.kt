package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
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
                val args = node.signature.argsList
                args.forEachIndexed { index, arg ->
                    if (arg.hasDefaultJniValue && index + 1 < args.size && isPossibleToCreateDefaultArgs(args, index, arg)) {
                        val firstPart = args.toList().subList(0, index)
                        val secondPart = args.toList().subList(index + 1, args.size)
                        val newArgs = firstPart + secondPart

                        val newMethod = node.copy()

                        newMethod.signature.args.let { argsNode ->
                            argsNode.args = newArgs

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

    /**
     * It's impossible to create methods with default arguments when neighboring arguments are of the same type.
     */
    private fun isPossibleToCreateDefaultArgs(
        args: Collection<ArgDefinitionNode>,
        index: Int,
        arg: ArgDefinitionNode
    ): Boolean {
        if (index - 1 >= 0) {
            val prevArg = args.toList()[index - 1]
            if (prevArg.argType.type == arg.argType.type && prevArg.argType.flgas == arg.argType.flgas) {
                return false
            }
        }
        if (index + 1 < args.size) {
            val nextArg = args.toList()[index + 1]
            if (nextArg.argType.type == arg.argType.type && nextArg.argType.flgas == arg.argType.flgas) {
                return false
            }
        }
        return true
    }

}
