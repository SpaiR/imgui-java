package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.type.method.SignatureDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

@DefinitionDsl
class SignatureDsl {
    val data = SignatureDefinitionNode()

    fun visibility(value: SignatureVisibility) {
        data.visibility = value
    }

    fun public() {
        visibility(SignatureVisibility.PUBLIC)
    }

    fun private() {
        visibility(SignatureVisibility.PRIVATE)
    }

    fun packagePrivate() {
        visibility(SignatureVisibility.PACKAGE_PRIVATE)
    }

    fun final() {
        data.isFinal = true
    }

    fun native() {
        data.isNative = true
    }

    fun static() {
        data.isStatic = true
    }

    fun name(value: String) {
        data.name = value
    }

    fun args(action: ArgsDsl.() -> Unit) {
        data.args = ArgsDsl().apply(action).data
    }
}
