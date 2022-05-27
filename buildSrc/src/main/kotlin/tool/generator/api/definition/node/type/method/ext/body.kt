package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.ListContent
import tool.generator.api.definition.node.container.StringContent
import tool.generator.api.definition.node.type.method.BodyDefinitionNode

/**
 * Collection of lines at the body.
 */
var BodyDefinitionNode.lines: Collection<String>
    get() = storage.get<ListContent<StringContent>>("lines")?.value?.map { it.value } ?: emptyList()
    set(value) = storage.put("lines", ListContent(value.map { StringContent(it) }))

fun BodyDefinitionNode.addLine(line: String) {
    lines += listOf(line)
}
