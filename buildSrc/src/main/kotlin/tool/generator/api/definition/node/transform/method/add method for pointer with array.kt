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
                val args = node.signature.argsList
                var newMethod: MethodDefinitionNode? = null

                args.forEachIndexed { index, arg ->
                    if (arg.argType.hasFlag(ArgTypeFlag.POINTER) && arg.argType.hasFlag(ArgTypeFlag.ARRAY)) {
                        if (newMethod == null) {
                            newMethod = node.copy()
                        }

                        // Remove ARRAY flag so the method will be generated with a pointer arg.
                        arg.argType.removeFlag(ArgTypeFlag.ARRAY)

                        newMethod!!.let {
                            val newArgs = it.signature.argsList.toMutableList()
                            val newArg = newArgs[index]
                            newArgs[index] = newArg

                            // New method will be generated with an argument as an array.
                            newArg.argType.removeFlag(ArgTypeFlag.POINTER)

                            it.signature.args = ArgsDefinitionNode().apply {
                                this.args = newArgs
                            }
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
