package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CollectionContent
import tool.generator.api.definition.node.container.StringContent
import tool.generator.api.definition.node.type.method.BodyDefinitionNode

/**
 * Collection of lines at the body.
 */
var BodyDefinitionNode.lines: Collection<String>
    get() = storage.get<CollectionContent<StringContent>>("lines")?.value?.map { it.value } ?: emptyList()
    set(value) = storage.put("lines", CollectionContent(value.map { StringContent(it) }))

fun BodyDefinitionNode.addLine(line: String) {
    lines += listOf(line)
}
