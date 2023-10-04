package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CloneableContent
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode

/**
 * The type defined by the class.
 */
var ReturnTypeDefinitionNode.type: ReturnType
    get() = storage.value("type")
    set(value) = storage.put("type", value)

/**
 * Node to define return type.
 */
sealed class ReturnType : CloneableContent {
    /**
     * No return value.
     */
    object Void : ReturnType()

    /**
     * [String]
     */
    object String : ReturnType()

    /**
     * [Int]
     */
    object Int : ReturnType()

    /**
     * [Double]
     */
    object Double : ReturnType()

    /**
     * [Float]
     */
    object Float : ReturnType()

    /**
     * [Boolean]
     */
    object Boolean : ReturnType()

    /**
     * [imgui.ImVec2]
     */
    object Vec2 : ReturnType()

    /**
     * [imgui.ImVec4]
     */
    object Vec4 : ReturnType()

    /**
     * Marker, that the return type is a struct.
     */
    data class Struct(val name: kotlin.String) : ReturnType()

    override fun copy() = this
}

fun ReturnTypeDefinitionNode.hasFlag(flag: ReturnTypeFlag): Boolean {
    return container.getAll(ReturnTypeFlag::class).contains(flag)
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
