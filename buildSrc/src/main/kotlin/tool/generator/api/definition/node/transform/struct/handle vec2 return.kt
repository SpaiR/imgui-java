package tool.generator.api.definition.node.transform.struct

import tool.generator.api.definition.node.transform.method.`abstract handle vec2 return`
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral

/**
 * Transformation handles fields with ImVec2 as return type.
 */
@Suppress("ClassName")
object `handle vec2 return` : `abstract handle vec2 return`() {
    /**
     * Creates:
     *
     * ```java
     * public native float nMethodX(); /*
     *     return Method.x;
     * */
     * ```
     *
     * or methodY, depending on the provided arg.
     */
    override fun createJniReturnFloatMethod(node: MethodDefinitionNode, float: String): MethodDefinitionNode {
        val method = node.copy()
        method.signature.name += float.toUpperCase()
        method.body.lines = listOf(
            buildString {
                append("return ")
                append(method.autoBody.thisPointer)
                append(method.autoBody.methodName)
                append(".")
                append(float.toLowerCase())
                append(';')
            }
        )
        method.returnType.type = ReturnTypeGeneral.FLOAT
        return method
    }
}
