package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CloneableContent
import tool.generator.api.definition.node.container.CollectionContent
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral

/**
 * The type defined by the class.
 */
var ReturnTypeDefinitionNode.type: ReturnType
    get() = storage.getOrPut("type", ReturnTypeGeneral.VOID)
    set(value) = storage.put("type", value)

interface ReturnType : CloneableContent

fun ReturnTypeDefinitionNode.hasFlag(flag: ReturnTypeFlag): Boolean {
    return storage.get<CollectionContent<ReturnTypeFlag>>("flags")?.value?.contains(flag) ?: false
}

fun ReturnTypeDefinitionNode.addFlag(flag: ReturnTypeFlag) {
    val flags = (storage.get<CollectionContent<ReturnTypeFlag>>("flags")?.value ?: emptyList()).toMutableSet()
    flags += flag
    storage.put("flags", CollectionContent(flags.toList()))
}

fun ReturnTypeDefinitionNode.removeFlag(flag: ReturnTypeFlag) {
    val flags = (storage.get<CollectionContent<ReturnTypeFlag>>("flags")?.value ?: emptyList()).toMutableSet()
    flags -= flag
    storage.put("flags", CollectionContent(flags.toList()))
}

/**
 * Node to define flags.
 */
enum class ReturnTypeFlag : ReturnTypeDefinitionNode.Content {
    /**
     * Meant to be used when returning structs.
     * The value of the static return type is set to the object field created statically.
     */
    STATIC,

    /**
     * This flag is only useful on the JNI side.
     * When true, the value from JNI will be cast to the REF.
     */
    REF;

    override fun copy() = this
}
