package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation creates additional method to handle optional arguments.
 *
 * Transforms:
 * ```java
 * void foo(int x, int y); // where `y` is optional
 * ```
 * to
 * ```java
 * void foo(int x);
 *
 * void foo(int x, int y);
 * ```
 */
@Suppress("ClassName")
object `add methods for optional args` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode) {
                val args = node.signature.argsList

                args.forEachIndexed { index, arg ->
                    if (arg.hasFlag(ArgFlag.OPTIONAL) && isPossibleToCreateOptionalArgs(args, index, arg)) {
                        val newMethod = node.copy()
                        val newArgs = ArgsDefinitionNode()

                        if (index != 0) {
                            newArgs.args = args.toList().subList(0, index)
                        }

                        newMethod.signature.args = newArgs
                        result += newMethod
                    }
                }
            }

            result += node
        }

        return result
    }

    /**
     * It's impossible to create methods with optional arguments when neighboring arguments are of the same type.
     */
    private fun isPossibleToCreateOptionalArgs(
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
