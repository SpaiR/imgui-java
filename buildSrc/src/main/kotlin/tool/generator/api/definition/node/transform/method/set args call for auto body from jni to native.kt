package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeNull
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypePrimitive
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeStruct
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeVec

/**
 * Transformation creates argsCall for JNI auto-body.
 * @see [argsCall]
 */
@Suppress("ClassName")
object `set args call for auto body from jni to native` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            result += node

            if (node is MethodDefinitionNode && node.signature.isNative) {
                node.autoBody.argsCall = node.signature.argsList.map(::createArgCall)
            }
        }

        return result
    }

    private fun createArgCall(arg: ArgDefinitionNode): String {
        val argType = arg.argType.type

        var argCall = if (arg.argType.hasFlag(ArgTypeFlag.POINTER) || arg.argType.hasFlag(ArgTypeFlag.ARRAY)) {
            "&${arg.name}[0]"
        } else if (argType is ArgTypeStruct) {
            "(${argType.value.substringAfterLast('.')}*)${arg.name}"
        } else if (argType == ArgTypeVec.V2) {
            "ImVec2(${arg.name}X, ${arg.name}Y)"
        } else if (argType == ArgTypeVec.V4) {
            "ImVec4(${arg.name}X, ${arg.name}Y, ${arg.name}Z, ${arg.name}W)"
        } else if (argType is ArgTypeNull) {
            if (arg.hasDefaultJniValue) {
                arg.defaultJniValue
            } else {
                TYPE_NULL_JNI
            }
        } else {
            arg.name
        }

        getDefaultJniCast(arg)?.let {
            argCall = "($it)$argCall"
        }

        if (arg.hasJniCast) {
            argCall = arg.jniCast + argCall
        }

        return argCall
    }

    /**
     * Default JNI cast needed to ensure native code works with types it expects.
     */
    private fun getDefaultJniCast(arg: ArgDefinitionNode): String? {
        if (arg.argType.type == ArgTypePrimitive.BOOL && !arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            return "bool"
        }
        return null
    }
}
