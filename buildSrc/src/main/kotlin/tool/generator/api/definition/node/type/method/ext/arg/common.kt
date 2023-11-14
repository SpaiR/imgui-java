package tool.generator.api.definition.node.type.method.ext.arg

import tool.generator.api.definition.node.render.MethodArgRender
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

interface ArgTypeCommon : ArgType {
    fun value(): String
}

open class ArgTypeCommonInstance(val value: String) : ArgTypeCommon {
    override fun copy() = this

    override fun value() = value
}

class MethodArgCommonRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        var result = (arg.argType.type as ArgTypeCommon).value()
        if (arg.hasFlag(ArgFlag.FINAL)) {
            result = "final $result"
        }
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (arg.argType.type !is ArgTypeCommon) {
            return false
        }
        if (method.signature.isNative) {
            return false;
        }
        return true
    }
}

class MethodArgCommonJniRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        val result = (arg.argType.type as ArgTypeCommon).value()
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (arg.argType.type !is ArgTypeCommon) {
            return false
        }
        if (!method.signature.isNative) {
            return false;
        }
        return true
    }
}

