package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation handles methods with ImVec2 as argument type.
 */
@Suppress("ClassName")
object `handle vec2 arg` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        for (node in nodes) {
            if (node is MethodDefinitionNode) {
                if (node.signature.args.find { it.argType.type is ArgType.Vec2 } != null) {
                    if (!node.signature.isNative) {
                        result += createJvmSplitArgCallMethod(node)
                    }
                    result += createSplitArgTypeMethod(node)
                    continue
                }
            }
            result += node
        }

        return result
    }

    /**
     * Transforms:
     *
     * ```java
     * void method(/*args*/) {
     *     nMethod(vec); // vec as ImVec2
     * }
     * ```
     * to
     * ```java
     * void method(/*args*/) {
     *     nMethod(vecX, vecY); // vecX and vecY as float
     * }
     * ```
     */
    private fun createJvmSplitArgCallMethod(node: MethodDefinitionNode): MethodDefinitionNode {
        val method = node.copy()

        method.body.lines = listOf(buildString {
            if (method.returnType.type !is ReturnType.Void) {
                append("return ")
            }

            append(THIS_PTR_CALL_JVM)
            append(method.signature.name)
            append('(')
            append(method.signature.args.joinToString(transform = ::renderArg))
            append(')')
            append(';')
        })

        return method
    }

    private fun renderArg(arg: ArgDefinitionNode): String {
        return when (arg.argType.type) {
            is ArgType.Vec2 -> "${arg.name}.x, ${arg.name}.y"
            is ArgType.Vec4 -> "${arg.name}X, ${arg.name}Y, ${arg.name}Z, ${arg.name}W"
            else -> arg.name
        }
    }

    /**
     * Transforms:
     * ```java
     * void method(ImVec2 vec);
     * ```
     * to
     * ```java
     * void method(float vecX, float vecY);
     * ```
     */
    private fun createSplitArgTypeMethod(node: MethodDefinitionNode): MethodDefinitionNode {
        val method = node.copy()

        val args = method.signature.args.toMutableList()
        val newArgs = mutableListOf<ArgDefinitionNode>()

        args.forEach { arg ->
            if (arg.argType.type is ArgType.Vec2) {
                val floatArg = arg.copy().apply {
                    argType.type = ArgType.Float
                }
                newArgs += floatArg.copy().apply {
                    name += "X"
                }
                newArgs += floatArg.copy().apply {
                    name += "Y"
                }
            } else {
                newArgs += arg
            }
        }

        method.signature.container.get(ArgsDefinitionNode::class).let {
            it.container.clear()
            newArgs.forEach(it.container::add)
        }

        return method
    }
}
