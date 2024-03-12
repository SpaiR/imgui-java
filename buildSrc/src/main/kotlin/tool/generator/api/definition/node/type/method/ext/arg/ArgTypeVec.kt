package tool.generator.api.definition.node.type.method.ext.arg

import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC2_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC4_JVM

enum class ArgTypeVec(val value: String) : ArgTypeCommon {
    V2(TYPE_IM_VEC2_JVM),
    V4(TYPE_IM_VEC4_JVM);

    override fun copy() = this

    override fun value() = value
}
