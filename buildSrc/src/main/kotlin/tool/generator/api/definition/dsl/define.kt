package tool.generator.api.definition.dsl

import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.dsl.struct.FieldsDsl
import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.JniBlockDefinitionNode
import tool.generator.api.definition.node.type.LineDefinitionNode

fun define(action: DefineDsl.() -> Unit): Nodes {
    return DefineDsl().apply(action).data
}

fun define(
    transformationChainAction: TransformationDsl.() -> Unit,
    defineAction: DefineDsl.() -> Unit
): Nodes {
    return define(transformationChainAction, DefineDsl().apply(defineAction).data)
}

fun define(
    transformationChainAction: TransformationDsl.() -> Unit,
    defines: Nodes
): Nodes {
    var result = defines

    TransformationDsl().apply(transformationChainAction).chains.forEach { transformationChainDsl ->
        val transformationChain = TransformationChain(result)

        result = mutableListOf<DefinitionNode>().apply {
            transformationChainDsl.transforms.forEach { transformations ->
                addAll(transformationChain.transform(transformations))
            }
        }
    }

    return result
}

@DefinitionDsl
class DefineDsl {
    val data: MutableList<DefinitionNode> = mutableListOf()

    fun line(vararg value: String) {
        data += LineDefinitionNode(value.joinToString("\n"))
    }

    fun jniBlock(vararg value: String) {
        data += JniBlockDefinitionNode(value.joinToString("\n"))
    }

    fun methods(action: MethodsDsl.() -> Unit) {
        data += MethodsDsl().apply(action).data
    }

    fun fields(action: FieldsDsl.() -> Unit) {
        data += FieldsDsl().apply(action).data
    }
}
