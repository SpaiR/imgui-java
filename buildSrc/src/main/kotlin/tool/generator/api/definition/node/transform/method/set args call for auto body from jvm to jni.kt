package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation creates argsCall for JVM auto-body.
 * @see [argsCall]
 */
@Suppress("ClassName")
object `set args call for auto body from jvm to jni` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && !node.signature.isNative) {
                node.autoBody.argsCall = node.signature.argsList.map(::createArgCall)
            }
        }

        return result
    }

    private fun createArgCall(arg: ArgDefinitionNode): String {
        if (arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            return "${arg.name}.getData()"
        }

        return when (arg.argType.type) {
            is ArgType.Struct -> "${arg.name}.$FIELD_PTR_JVM"
            is ArgType.Vec2 -> "${arg.name}X, ${arg.name}Y"
            is ArgType.Vec4 -> "${arg.name}X, ${arg.name}Y, ${arg.name}Z, ${arg.name}W"
            is ArgType.Null -> TYPE_NULL_JNI
            else -> arg.name
        }
    }
}
