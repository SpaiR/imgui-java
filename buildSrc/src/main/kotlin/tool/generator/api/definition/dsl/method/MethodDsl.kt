package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.MethodDefinitionNode

@DefinitionDsl
class MethodDsl {
    val data = MethodDefinitionNode()

    fun signature(action: SignatureDsl.() -> Unit) {
        data.container.add(SignatureDsl().apply(action).data)
    }

    fun body(action: BodyDsl.() -> Unit) {
        data.container.add(BodyDsl().apply(action).data)
    }

    fun returnType(action: ReturnTypeDsl.() -> Unit) {
        data.container.add(ReturnTypeDsl().apply(action).data)
    }
}
