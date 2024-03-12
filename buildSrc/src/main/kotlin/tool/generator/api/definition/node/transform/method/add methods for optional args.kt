package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
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
                    if (arg.hasFlag(ArgFlag.OPTIONAL)) {
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
}
