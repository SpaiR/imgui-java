package tool.generator.api

import spoon.reflect.code.CtCodeSnippetStatement
import spoon.reflect.code.CtExpression
import spoon.reflect.code.CtFieldRead
import spoon.reflect.code.CtNewArray
import spoon.reflect.declaration.*
import spoon.reflect.factory.Factory
import spoon.reflect.reference.CtTypeParameterReference
import tool.generator.ast.Decl
import tool.generator.ast.DeclContainer

const val A_NAME_BINDING_SOURCE = "BindingSource"
const val A_NAME_BINDING_METHOD = "BindingMethod"
const val A_NAME_BINDING_FIELD = "BindingField"
const val A_NAME_BINDING_AST_ENUM = "BindingAstEnum"
const val A_NAME_OPT_ARG = "OptArg"
const val A_NAME_EXCLUDED_SOURCE = "ExcludedSource"
const val A_NAME_RETURN_VALUE = "ReturnValue"
const val A_NAME_ARG_VALUE = "ArgValue"
const val A_NAME_TYPE_ARRAY = "TypeArray"
const val A_NAME_TYPE_STD_STRING = "TypeStdString"

const val A_VALUE_CALL_PTR = "callPtr"
const val A_VALUE_CALL_OPERATOR = "callOperator"
const val A_VALUE_CALL_PREFIX = "callPrefix"
const val A_VALUE_CALL_SUFFIX = "callSuffix"
const val A_VALUE_STATIC_CAST = "staticCast"
const val A_VALUE_REINTERPRET_CAST = "reinterpretCast"
const val A_VALUE_IS_STATIC = "isStatic"
const val A_VALUE_CALL_VALUE = "callValue"
const val A_VALUE_NAME = "name"
const val A_VALUE_CALL_NAME = "callName"
const val A_VALUE_ACCESSORS = "accessors"
const val A_VALUE_IS_FLAG = "isFlag"
const val A_VALUE_TYPE = "type"
const val A_VALUE_SIZE = "size"
const val A_VALUE_FILE = "file"
const val A_VALUE_QUAL_TYPE = "qualType"
const val A_VALUE_SANITIZE_NAME = "sanitizeName"

const val A_TYPE_VALUE_ACCESSOR_SETTER = "SETTER"
const val A_TYPE_VALUE_ACCESSOR_GETTER = "GETTER"

const val PTR_JVM_FIELD = "ptr"
const val PTR_JNI_CAST = "intptr_t"
const val PTR_JNI_THIS = "THIS"

const val PARAM_ARR_LEN_POSTFIX = "Count"

val CLEANUP_ANNOTATIONS_LIST = listOf(
    A_NAME_BINDING_SOURCE,
    A_NAME_BINDING_METHOD,
    A_NAME_BINDING_FIELD,
    A_NAME_RETURN_VALUE,
    A_NAME_OPT_ARG,
    A_NAME_ARG_VALUE,
    A_NAME_TYPE_ARRAY,
    A_NAME_TYPE_STD_STRING,
    A_NAME_BINDING_AST_ENUM,
)

val PRIMITIVE_PTR_TYPECAST_MAP = mapOf(
    "boolean[]" to "bool*",
    "byte[]" to "char*",
    "short[]" to "short*",
    "int[]" to "int*",
    "float[]" to "float*",
    "long[]" to "long*",
    "double[]" to "double*",
)

val DST_RETURN_TYPE_SET = setOf(
    "ImVec2",
    "ImVec4",
    "ImRect",
    "ImPlotPoint",
    "ImPlotRange",
    "ImPlotRect",
    "TextEditorCoordinates",
)

fun CtElement.hasAnnotation(annotationName: String): Boolean {
    return getAnnotation(annotationName) != null
}

fun CtElement.getAnnotation(annotationName: String): CtAnnotation<*>? {
    return annotations.find { a -> a?.name == annotationName }
}

fun CtAnnotation<*>.containsValue(annotationField: String, value: String): Boolean {
    val v = getValue<CtExpression<Any>>(annotationField)
    if (v is CtFieldRead) {
        return v.prettyprint().contains(value)
    }
    if (v is CtNewArray) {
        return v.elements.find { it.prettyprint().contains(value) } != null
    }
    return false
}

fun Factory.createTypeParam(name: String): CtTypeParameterReference = createTypeParameterReference().apply {
    setSimpleName<Nothing>(name)
}

fun Factory.createCodeSnippet(code: String): CtCodeSnippetStatement = createCodeSnippetStatement().apply {
    setValue<Nothing>(code)
}

fun CtTypedElement<*>.isType(type: String): Boolean = this.type.simpleName == type

fun CtTypedElement<*>.isPrimitivePtrType(): Boolean {
    return PRIMITIVE_PTR_TYPECAST_MAP.containsKey(type.simpleName)
}

fun CtTypeInformation.isPtrClass(): Boolean {
    return getDeclaredOrInheritedField(PTR_JVM_FIELD) != null
}

fun CtMethod<*>.getJniName(): String = "n${simpleName.capitalize()}"

fun CtMethod<*>.getName(): String {
    return getAnnotation(A_NAME_BINDING_METHOD)?.getValueAsString(A_VALUE_NAME)?.takeIf(String::isNotEmpty)
        ?: simpleName.decapitalize()
}

fun CtField<*>.getCallName(): String {
    return getAnnotation(A_NAME_BINDING_FIELD)?.getValueAsString(A_VALUE_CALL_NAME)?.takeIf(String::isNotEmpty)
        ?: simpleName
}

fun findDefaultsCombinations(method: CtMethod<*>): List<IntArray> {
    fun findDefaults(params: List<CtParameter<*>>): Map<Int, String> {
        val defaults = mutableMapOf<Int, String>()
        for ((index, p) in params.withIndex()) {
            p.getAnnotation(A_NAME_OPT_ARG)?.let { a ->
                val value = a.getValueAsString(A_VALUE_CALL_VALUE)
                if (value.isNotEmpty()) {
                    defaults[index] = value
                }
            }
        }
        return defaults
    }

    fun concatNextParam0(combinations: MutableList<IntArray>, idx0: Int, idxN: Int) {
        val p1 = method.parameters[idx0]
        val p2 = method.parameters[idxN]
        if (p1.type.simpleName != p2.type.simpleName) {
            combinations.add(((0 until idx0) + (idxN until method.parameters.size)).toIntArray())
            if (p2.getAnnotation(A_NAME_OPT_ARG)?.getValueAsString(A_VALUE_CALL_VALUE)?.isNotEmpty() == true) {
                concatNextParam0(combinations, idx0, idxN + 1)
            }
        }
    }

    val combinations = mutableListOf<IntArray>()
    findDefaults(method.parameters).forEach { (idx, _) -> concatNextParam0(combinations, idx, idx + 1) }
    return combinations
}

fun findFirstOptParam(method: CtMethod<*>): Int {
    if (method.parameters.isEmpty()) {
        return method.parameters.size
    }
    for ((index, p) in method.parameters.withIndex()) {
        if (p.hasAnnotation(A_NAME_OPT_ARG)) {
            return index
        }
    }
    return method.parameters.size
}

fun Collection<Decl>.filterDecls0(filter: (Decl) -> Boolean): Collection<Decl> {
    val result = mutableListOf<Decl>()
    fun filter0(decls: Collection<Decl>) {
        decls.forEach { decl ->
            if (filter(decl)) {
                result += decl
            }
            if (decl is DeclContainer) {
                filter0(decl.decls)
            }
        }
    }
    filter0(this)
    return result
}
