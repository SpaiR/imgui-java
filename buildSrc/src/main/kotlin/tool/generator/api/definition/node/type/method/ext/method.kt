package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.type.method.*

/**
 * Signature node of the method.
 */
val MethodDefinitionNode.signature: SignatureDefinitionNode
    get() = container.get(SignatureDefinitionNode::class)

/**
 * Auto-body node of the method.
 * Auto-body is a virtual node, which is used for further code generation.
 */
val MethodDefinitionNode.autoBody: AutoBodyDefinitionNode
    get() = container.get(AutoBodyDefinitionNode::class)

/**
 * Body node of the method.
 */
val MethodDefinitionNode.body: BodyDefinitionNode
    get() = container.get(BodyDefinitionNode::class)

/**
 * Return type node of the method.
 */
val MethodDefinitionNode.returnType: ReturnTypeDefinitionNode
    get() = container.get(ReturnTypeDefinitionNode::class)
