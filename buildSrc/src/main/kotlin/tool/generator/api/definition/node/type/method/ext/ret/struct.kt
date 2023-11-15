package tool.generator.api.definition.node.type.method.ext.ret

import tool.generator.api.definition.node.render.MethodReturnRender
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ReturnTypeDefinitionNode
import tool.generator.api.definition.node.type.method.ext.ReturnType
import tool.generator.api.definition.node.type.method.ext.isNative
import tool.generator.api.definition.node.type.method.ext.signature
import tool.generator.api.definition.node.type.method.ext.type

class ReturnTypeStruct(val type: String) : ReturnType {
    override fun copy() = this
}

class MethodReturnStructRender : MethodReturnRender {
    override fun render(returnType: ReturnTypeDefinitionNode): String {
        return (returnType.type as ReturnTypeStruct).type
    }

    override fun isApplicable(method: MethodDefinitionNode, returnType: ReturnTypeDefinitionNode): Boolean {
        if (method.signature.isNative) {
            return false
        }
        return returnType.type is ReturnTypeStruct
    }
}

class MethodReturnStructJniRender : MethodReturnRender {
    override fun render(returnType: ReturnTypeDefinitionNode): String {
        return "long"
    }

    override fun isApplicable(method: MethodDefinitionNode, returnType: ReturnTypeDefinitionNode): Boolean {
        return returnType.type is ReturnTypeStruct && method.signature.isNative
    }
}
