package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.body
import tool.generator.api.definition.node.type.method.ext.returnType
import tool.generator.api.definition.node.type.method.ext.signature

@DefinitionDsl
class MethodDsl {
    val data = MethodDefinitionNode()

    fun signature(action: SignatureDsl.() -> Unit) {
        data.signature = SignatureDsl().apply(action).data
    }

    fun body(action: BodyDsl.() -> Unit) {
        data.body = BodyDsl().apply(action).data
    }

    fun returnType(action: ReturnTypeDsl.() -> Unit) {
        data.returnType = ReturnTypeDsl().apply(action).data
    }
}
