package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation removes [ArgType.NULL] from everywhere except jni auto-body.
 */
@Suppress("ClassName")
object `remove null argument except jni auto body` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode) {
                node.signature.container.find(ArgsDefinitionNode::class)?.let { argsNode ->
                    val newArgs = argsNode.args.filterNot { it.argType.type == ArgType.Null }
                    argsNode.container.clear()
                    argsNode.container.addAll(newArgs)
                    if (!node.signature.isNative) {
                        node.autoBody.argsCall = node.autoBody.argsCall.filterNot { it == TYPE_NULL_JNI }
                    }
                }
            }
        }

        return result
    }
}
