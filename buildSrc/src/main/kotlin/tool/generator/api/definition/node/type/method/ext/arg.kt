package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CollectionContent
import tool.generator.api.definition.node.container.StringContent
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode

/**
 * Name of the argument.
 */
var ArgDefinitionNode.name: String
    get() = storage.value<StringContent>("name").value
    set(value) = storage.put("name", StringContent(value))

/**
 * True if the argument has a default JNI value.
 */
val ArgDefinitionNode.hasDefaultJniValue: Boolean
    get() = storage.has("defaultJniValue")

/**
 * Default JNI value of the argument.
 * The value is provided as a default argument for the native method.
 *
 * For example:
 * ```java
 * void foo(String s) {
 *     nFoo(s);
 * }
 *
 * void foo(int i, String s) {
 *     nFoo(i, s);
 * }
 *
 * native void nFoo(String s); /*
 *     Foo(defaultJniValue, s); // defaultJniValue instead of i
 * */
 *
 * native void nFoo(int i, String s); /*
 *     Foo(i, s);
 * */
 * ```
 */
var ArgDefinitionNode.defaultJniValue: String
    get() = storage.value<StringContent>("defaultJniValue").value
    set(value) = storage.put("defaultJniValue", StringContent(value))

/**
 * Type of the argument.
 */
var ArgDefinitionNode.argType: ArgTypeDefinitionNode
    get() = storage.value("argType")
    set(value) = storage.put("argType", value)

/**
 * True if there is a JNI cast value.
 */
val ArgDefinitionNode.hasJniCast: Boolean
    get() = storage.has("jniCast")

/**
 * JNI cast value applied when rendering JNI method.
 *
 * For example:
 * ```java
 * // if jniCast=intptr_t
 * native void nFoo(int i); /*
 *     Foo((intptr_t)i);
 * */
 * ```
 */
var ArgDefinitionNode.jniCast: String
    get() = storage.value<StringContent>("jniCast").value
    set(value) = storage.put("jniCast", StringContent(value))

fun ArgDefinitionNode.hasFlag(flag: ArgFlag): Boolean {
    return storage.get<CollectionContent<ArgFlag>>("flags")?.value?.contains(flag) ?: false
}

fun ArgDefinitionNode.addFlag(flag: ArgFlag) {
    val flags = (storage.get<CollectionContent<ArgFlag>>("flags")?.value ?: emptyList()).toMutableSet()
    flags += flag
    storage.put("flags", CollectionContent(flags.toList()))
}

fun ArgDefinitionNode.removeFlag(flag: ArgFlag) {
    val flags = (storage.get<CollectionContent<ArgFlag>>("flags")?.value ?: emptyList()).toMutableSet()
    flags -= flag
    storage.put("flags", CollectionContent(flags.toList()))
}

enum class ArgFlag : ArgDefinitionNode.Content {
    /**
     * If contains, `final` keyword will be added to the argument.
     */
    FINAL,

    /**
     * If contains, additional method will be generated with the same signature, excluding optional argument.
     */
    OPTIONAL;

    override fun copy() = this
}
