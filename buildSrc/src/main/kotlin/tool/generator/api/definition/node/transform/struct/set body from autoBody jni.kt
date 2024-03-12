package tool.generator.api.definition.node.transform.struct

import tool.generator.api.definition.node.transform.method.`abstract set body from autoBody jni`
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.argsCall
import tool.generator.api.definition.node.type.method.ext.autoBody
import tool.generator.api.definition.node.type.method.ext.methodName
import tool.generator.api.definition.node.type.method.ext.thisPointer

@Suppress("ClassName")
object `set body from autoBody jni` : `abstract set body from autoBody jni`() {
    override fun renderAutoBodyMethodCall(method: MethodDefinitionNode): String {
        return buildString {
            append(method.autoBody.thisPointer)
            append(method.autoBody.methodName)
            if (method.autoBody.argsCall.isNotEmpty()) {
                append(" = ")
                append(method.autoBody.argsCall.joinToString())
            }
        }
    }
}
