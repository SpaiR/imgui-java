package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode

@DefinitionDsl
class ArgsDsl {
    val data = ArgsDefinitionNode()

    fun arg(action: ArgDsl.() -> Unit) {
        data.container.add(ArgDsl().apply(action).data)
    }
}
