package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.ext.args

@DefinitionDsl
class ArgsDsl {
    val data = ArgsDefinitionNode()

    fun arg(action: ArgDsl.() -> Unit) {
        data.args += ArgDsl().apply(action).data
    }
}
