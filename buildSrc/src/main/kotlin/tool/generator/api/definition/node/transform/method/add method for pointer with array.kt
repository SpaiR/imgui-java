package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation creates additional JVM method,
 * when one or multiple arguments have [ArgTypeFlag.POINTER_WITH_ARRAY] flag.
 * Created method will be the same, but instead of args with flag it will have appropriate arrays.
 */
@Suppress("ClassName")
object `add method for pointer with array` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && !node.signature.isNative) {
                val args = node.signature.args
                var newMethod: MethodDefinitionNode? = null

                args.forEachIndexed { index, arg ->
                    if (arg.argType.hasFlag(ArgTypeFlag.POINTER_WITH_ARRAY)) {
                        if (newMethod == null) {
                            newMethod = node.copy()
                        }

                        newMethod!!.let {
                            val newArgs = it.signature.args.toMutableList()
                            val newArg = newArgs[index]
                            newArg.argType.type = when (newArg.argType.type) {
                                is ArgType.Short -> ArgType.ShortArray
                                is ArgType.Int -> ArgType.IntArray
                                is ArgType.Float -> ArgType.FloatArray
                                is ArgType.Long -> ArgType.LongArray
                                is ArgType.Double -> ArgType.DoubleArray
                                else -> error("Can't fine array type argType: ${newArg.argType}")
                            }
                            newArg.argType.container.remove(ArgTypeFlag.POINTER)
                            newArg.argType.container.remove(ArgTypeFlag.POINTER_WITH_ARRAY)
                            newArgs[index] = newArg

                            it.signature.container.clear(ArgsDefinitionNode::class)
                            it.signature.container.add(ArgsDefinitionNode().apply {
                                container.addAll(newArgs)
                            })
                        }
                    }
                }

                if (newMethod != null) {
                    result += newMethod!!
                }
            }
        }

        return result
    }
}
