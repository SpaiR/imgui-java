package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.CloneableContent
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC2_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC4_JVM
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode

/**
 * Field to store [ArgType].
 */
var ArgTypeDefinitionNode.type: ArgType
    get() = storage.value("type")
    set(value) = storage.put("type", value)

sealed class ArgType : CloneableContent {
    abstract class Definition(
        val jvmName: kotlin.String,
        val jvmPointerName: kotlin.String,
        val jniName: kotlin.String,
        val jniPointerName: kotlin.String,
    ) : ArgType() {
        constructor(name: kotlin.String) : this(name, name, name, name)
    }

    /**
     * [Object]
     */
    object Raw : Definition("Object")

    /**
     * [String]
     */
    object String : Definition("String")

    /**
     * `ImString`
     */
    object ImString : Definition("imgui.type.ImString")

    /**
     * [Int]
     */
    object Int : Definition(
        "int",
        "imgui.type.ImInt",
        "int",
        "int[]",
    )

    /**
     * [Float]
     */
    object Float : Definition(
        "float",
        "imgui.type.ImFloat",
        "float",
        "float[]",
    )

    /**
     * [Short]
     */
    object Short : Definition(
        "short",
        "imgui.type.ImShort",
        "short",
        "short[]",
    )

    /**
     * [Long]
     */
    object Long : Definition(
        "long",
        "imgui.type.ImLong",
        "long",
        "long[]",
    )

    /**
     * [Double]
     */
    object Double : Definition(
        "double",
        "imgui.type.ImDouble",
        "double",
        "double[]",
    )

    /**
     * [Boolean]
     */
    object Boolean : Definition(
        "boolean",
        "imgui.type.ImBoolean",
        "boolean",
        "boolean[]",
    )

    /**
     * [imgui.ImVec2]
     */
    object Vec2 : Definition(TYPE_IM_VEC2_JVM)

    /**
     * [imgui.ImVec4]
     */
    object Vec4 : Definition(TYPE_IM_VEC4_JVM)

    /**
     * Marker, that the argument type is a struct.
     */
    data class Struct(val name: kotlin.String) : ArgType()

    /**
     * Marker, that the argument type is a generalized class.
     */
    data class GenericClass(val name: kotlin.String) : ArgType()

    /**
     * Marker, that the argument type is a NULL value.
     */
    object Null : ArgType()

    /**
     * Marker interface to see if the argument is an array.
     */
    interface Array

    /**
     * `float[]`
     */
    object FloatArray : Definition("float[]"), Array

    /**
     * `int[]`
     */
    object IntArray : Definition("int[]"), Array

    /**
     * `short[]`
     */
    object ShortArray : Definition("short[]"), Array

    /**
     * `byte[]`
     */
    object ByeArray : Definition("byte[]"), Array

    /**
     * `long[]`
     */
    object LongArray : Definition("long[]"), Array

    /**
     * `long[]`
     */
    object DoubleArray : Definition("double[]"), Array

    /**
     * string[]
     */
    object StringArray : Definition(
        "String[]",
        "String[]",
        "String[]",
        "String[]",
    ), Array

    override fun copy() = this
}

fun ArgTypeDefinitionNode.hasFlag(flag: ArgTypeFlag): Boolean {
    return container.getAll(ArgTypeFlag::class).contains(flag)
}

enum class ArgTypeFlag : ArgTypeDefinitionNode.Content {
    /**
     * Marker, that the argument type is a pointer.
     * Result in specific argument rendering, like using `ImBoolean` instead of `boolean`.
     */
    POINTER,

    /**
     * Marker, that the argument type is a pointer.
     * The difference between [POINTER] and [POINTER_WITH_ARRAY] is that,
     * there will be generated an additional JVM version of the method, which accepts appropriate array.
     *
     * For example:
     * ```java
     * void foo(ImFloat v) {
     *     nFoo(v.getData());
     * }
     *
     * void foo(float[] v) {
     *     nFoo(v);
     * }
     *
     * native void nFoo(float[] v); /*
     *     Foo(&v[0]);
     * */
     * ```
     */
    POINTER_WITH_ARRAY;

    override fun copy() = this
}
