package tool.generator.api.definition.node.type.method.ext.arg

import tool.generator.api.definition.node.render.MethodArgRender
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

class ArgTypeStruct(val value: String) : ArgType {
    override fun copy() = this
}

class MethodArgStructRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        var result = (arg.argType.type as ArgTypeStruct).value
        if (arg.hasFlag(ArgFlag.FINAL)) {
            result = "final $result"
        }
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (method.signature.isNative) {
            return false
        }
        return arg.argType.type is ArgTypeStruct
    }
}

class MethodArgStructJniRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        return "long ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        if (!method.signature.isNative) {
            return false
        }
        return arg.argType.type is ArgTypeStruct
    }
}
