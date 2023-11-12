package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.type.method.*

/**
 * Signature node of the method.
 */
var MethodDefinitionNode.signature: SignatureDefinitionNode
    get() = storage.getOrPut("signature", SignatureDefinitionNode())
    set(value) = storage.put("signature", value)

/**
 * Auto-body node of the method.
 * Auto-body is a virtual node, which is used for further code generation.
 */
var MethodDefinitionNode.autoBody: AutoBodyDefinitionNode
    get() = storage.getOrPut("autoBody", AutoBodyDefinitionNode())
    set(value) = storage.put("autoBody", value)

/**
 * Body node of the method.
 */
var MethodDefinitionNode.body: BodyDefinitionNode
    get() = storage.getOrPut("body", BodyDefinitionNode())
    set(value) = storage.put("body", value)

/**
 * Return type node of the method.
 */
var MethodDefinitionNode.returnType: ReturnTypeDefinitionNode
    get() = storage.getOrPut("returnType", ReturnTypeDefinitionNode())
    set(value) = storage.put("returnType", value)
