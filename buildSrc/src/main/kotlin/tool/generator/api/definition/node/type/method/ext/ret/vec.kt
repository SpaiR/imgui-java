package tool.generator.api.definition.node.type.method.ext.ret

import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC2_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC4_JVM

enum class ReturnTypeVec(val value: String) : ReturnTypeCommon {
    V2(TYPE_IM_VEC2_JVM),
    V4(TYPE_IM_VEC4_JVM);

    override fun value() = value

    override fun copy() = this
}
