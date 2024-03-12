package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation creates additional JVM method,
 * when one or multiple arguments have [ArgTypeFlag.ARRAY] and [ArgTypeFlag.POINTER] flags.
 * Created method will be the same, but instead of args with flag it will have appropriate arrays.
 */
@Suppress("ClassName")
object `add method for pointer with array` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode && !node.signature.isNative) {
                // We make a copy of the initial node to avoid unwanted modifications.
                val initialNode = node.copy()
                val initialArgs = initialNode.signature.args.args

                val newMethod = node.copy()
                val newArgs = newMethod.signature.argsList.toMutableList()

                var hasNewMethodToAdd = false
                initialArgs.forEachIndexed { index, arg ->
                    if (arg.argType.hasFlag(ArgTypeFlag.POINTER) && arg.argType.hasFlag(ArgTypeFlag.ARRAY)) {
                        hasNewMethodToAdd = true

                        // Remove ARRAY flag so the method will be generated with a pointer arg only.
                        arg.argType.removeFlag(ArgTypeFlag.ARRAY)

                        // New method will be generated with an argument as an array.
                        val newArg = newArgs[index]
                        newArgs[index] = newArg
                        newArg.argType.removeFlag(ArgTypeFlag.POINTER)
                    }
                }

                newMethod.signature.args.args = newArgs

                // Add the initial node to the result list.
                result += initialNode

                // If we have args where we need to remove ARRAY flag - there is a method to be added.
                if (hasNewMethodToAdd) {
                    result += newMethod
                }
            } else {
                result += node
            }
        }

        return result
    }
}
