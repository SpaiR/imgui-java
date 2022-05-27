package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.BooleanContent
import tool.generator.api.definition.node.container.CloneableContent
import tool.generator.api.definition.node.container.StringContent
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgsDefinitionNode
import tool.generator.api.definition.node.type.method.SignatureDefinitionNode

/**
 * Name of the method.
 */
var SignatureDefinitionNode.name: String
    get() = storage.value<StringContent>("name").value
    set(value) = storage.put("name", StringContent(value))

/**
 * Is method final.
 */
var SignatureDefinitionNode.isFinal: Boolean
    get() = storage.get<BooleanContent>("isFinal")?.value ?: false
    set(value) = storage.put("isFinal", BooleanContent(value))

/**
 * Is method native.
 */
var SignatureDefinitionNode.isNative: Boolean
    get() = storage.get<BooleanContent>("isNative")?.value ?: false
    set(value) = storage.put("isNative", BooleanContent(value))

/**
 * Is method static.
 */
var SignatureDefinitionNode.isStatic: Boolean
    get() = storage.get<BooleanContent>("isStatic")?.value ?: false
    set(value) = storage.put("isStatic", BooleanContent(value))

/**
 * Visibility of the method.
 */
var SignatureDefinitionNode.visibility: SignatureVisibility
    get() = storage.value("visibility")
    set(value) = storage.put("visibility", value)

/**
 * Node to define method visibility.
 */
enum class SignatureVisibility : CloneableContent {
    PUBLIC,
    PRIVATE,
    PACKAGE_PRIVATE;

    override fun copy() = this

    override fun toString(): String {
        return name.replace("_", " ")
    }
}

/**
 * Arguments of the method.
 */
val SignatureDefinitionNode.args: Collection<ArgDefinitionNode>
    get() = container.find(ArgsDefinitionNode::class)?.args ?: emptyList()
