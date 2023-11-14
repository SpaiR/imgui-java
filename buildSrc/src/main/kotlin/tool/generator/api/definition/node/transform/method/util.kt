package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import kotlin.math.abs

const val METHOD_JNI_PREFIX = 'n'

const val MARKER_AUTO_BODY = "# AUTO BODY MARKER #"

const val THIS_PTR_CALL_JNI = "THIS->"
const val THIS_PTR_CALL_JVM = "this."

const val FIELD_PTR_JVM = "ptr"

const val TYPE_IM_VEC2_JVM = "imgui.ImVec2"
const val TYPE_IM_VEC4_JVM = "imgui.ImVec4"

const val TYPE_IM_BOOL = "imgui.type.ImBoolean"
const val TYPE_IM_BYTE = "imgui.type.ImByte"
const val TYPE_IM_SHORT = "imgui.type.ImShort"
const val TYPE_IM_INT = "imgui.type.ImInt"
const val TYPE_IM_FLOAT = "imgui.type.ImFloat"
const val TYPE_IM_DOUBLE = "imgui.type.ImDouble"
const val TYPE_IM_LONG = "imgui.type.ImLong"

const val TYPE_IM_STRING = "imgui.type.ImString"

const val TYPE_NULL_JNI = "NULL"

const val CAST_PTR_JNI = "intptr_t"

/**
 * Returns a unique field name for a field used to store static instances.
 */
fun getStaticStructFieldName(method: MethodDefinitionNode): String {
    return "__gen_${method.signature.name}_${abs(method.signature.argsList.map { it.argType.type.hashCode() }.hashCode())}"
}
