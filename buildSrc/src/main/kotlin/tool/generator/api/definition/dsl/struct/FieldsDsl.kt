package tool.generator.api.definition.dsl.struct

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.MethodDefinitionNode

@DefinitionDsl
class FieldsDsl {
    val data: MutableList<MethodDefinitionNode> = mutableListOf()

    fun field(action: FieldDsl.() -> Unit) {
        data += FieldDsl().apply(action).data
    }
}
