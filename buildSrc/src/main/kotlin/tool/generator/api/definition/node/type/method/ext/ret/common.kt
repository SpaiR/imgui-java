package tool.generator.api.definition.node.type.method.ext.ret

import tool.generator.api.definition.node.render.MethodReturnRender
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ReturnType
import tool.generator.api.definition.node.type.method.ext.type

interface ReturnTypeCommon : ReturnType {
    fun value(): String
}

class MethodReturnCommonRender : MethodReturnRender {
    override fun render(returnType: ReturnTypeDefinitionNode): String {
        return (returnType.type as ReturnTypeCommon).value()
    }

    override fun isApplicable(method: MethodDefinitionNode, returnType: ReturnTypeDefinitionNode): Boolean {
        return returnType.type is ReturnTypeCommon
    }
}
