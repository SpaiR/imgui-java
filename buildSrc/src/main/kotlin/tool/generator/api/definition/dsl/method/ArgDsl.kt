package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ArgFlag
import tool.generator.api.definition.node.type.method.ext.defaultJniValue
import tool.generator.api.definition.node.type.method.ext.name

@DefinitionDsl
class ArgDsl {
    val data = ArgDefinitionNode()

    fun type(type: ArgTypeDefinitionNode) {
        data.container.add(type)
    }

    fun type(action: ArgTypeDsl.() -> Unit) {
        type(ArgTypeDsl().apply(action).data)
    }

    fun name(value: String) {
        data.name = value
    }

    fun addFlag(flag: ArgFlag) {
        data.container.add(flag)
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
