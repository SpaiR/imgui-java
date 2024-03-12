package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.MethodDefinitionNode

@DefinitionDsl
class MethodsDsl {
    val data: MutableList<MethodDefinitionNode> = mutableListOf()

    fun method(action: MethodDsl.() -> Unit) {
        data += MethodDsl().apply(action).data
    }
}
