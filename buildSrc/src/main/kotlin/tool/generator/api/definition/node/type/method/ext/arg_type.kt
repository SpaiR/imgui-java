package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CloneableContent
import tool.generator.api.definition.node.container.CollectionContent
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode

/**
 * Field to store [ArgType].
 */
var ArgTypeDefinitionNode.type: ArgType
    get() = storage.value("type")
    set(value) = storage.put("type", value)

interface ArgType : CloneableContent

val ArgTypeDefinitionNode.flgas: Collection<ArgTypeFlag>
    get() = storage.get<CollectionContent<ArgTypeFlag>>("flags")?.value ?: emptyList()

fun ArgTypeDefinitionNode.hasFlag(flag: ArgTypeFlag): Boolean {
    return storage.get<CollectionContent<ArgTypeFlag>>("flags")?.value?.contains(flag) ?: false
}

fun ArgTypeDefinitionNode.addFlag(flag: ArgTypeFlag) {
    val flags = (storage.get<CollectionContent<ArgTypeFlag>>("flags")?.value ?: emptyList()).toMutableSet()
    flags += flag
    storage.put("flags", CollectionContent(flags.toList()))
}

fun ArgTypeDefinitionNode.removeFlag(flag: ArgTypeFlag) {
    val flags = (storage.get<CollectionContent<ArgTypeFlag>>("flags")?.value ?: emptyList()).toMutableSet()
    flags -= flag
    storage.put("flags", CollectionContent(flags.toList()))
}

enum class ArgTypeFlag : ArgTypeDefinitionNode.Content {
    /**
     * Marker, that the argument type is a pointer.
     * Result in specific argument rendering, like using `ImBoolean` instead of `boolean`.
     */
    POINTER,
    ARRAY;

    override fun copy() = this
}
