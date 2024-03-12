package tool.generator.api.definition.node.type.method.ext.arg

import tool.generator.api.definition.node.render.MethodArgRender
import tool.generator.api.definition.node.transform.method.TYPE_IM_STRING
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

object ArgTypeString : ArgType {
    override fun copy() = this
}

class MethodArgStringRender : MethodArgRender {
    override fun render(arg: ArgDefinitionNode): String {
        var result = if (arg.argType.hasFlag(ArgTypeFlag.ARRAY)) {
            "String[]"
        } else if (arg.argType.hasFlag(ArgTypeFlag.POINTER)) {
            TYPE_IM_STRING
        } else {
            "String"
        }
        if (arg.hasFlag(ArgFlag.FINAL)) {
            result = "final $result"
        }
        return "$result ${arg.name}"
    }

    override fun isApplicable(method: MethodDefinitionNode, arg: ArgDefinitionNode): Boolean {
        return arg.argType.type is ArgTypeString
    }
}
