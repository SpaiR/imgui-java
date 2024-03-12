package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeNull
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeStruct
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeVec

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
            ArgTypeVec.V2 -> "${arg.name}X, ${arg.name}Y"
            ArgTypeVec.V4 -> "${arg.name}X, ${arg.name}Y, ${arg.name}Z, ${arg.name}W"
            is ArgTypeStruct -> "${arg.name}.$FIELD_PTR_JVM"
            is ArgTypeNull -> TYPE_NULL_JNI
            else -> arg.name
        }
    }
}
