package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

@DefinitionDsl
class ArgDsl {
    val data = ArgDefinitionNode()

    fun type(type: ArgTypeDefinitionNode) {
        data.argType = type
    }

    fun type(action: ArgTypeDsl.() -> Unit) {
        type(ArgTypeDsl().apply(action).data)
    }

    fun name(value: String) {
        data.name = value
    }

    fun addFlag(flag: ArgFlag) {
        data.addFlag(flag)
    }

    fun final() {
        addFlag(ArgFlag.FINAL)
    }

    fun optional() {
        addFlag(ArgFlag.OPTIONAL)
    }

    fun defaultJniValue(value: String) {
        data.defaultJniValue = value
    }
}
