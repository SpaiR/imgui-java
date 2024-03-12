package tool.generator.api.definition.dsl

import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain.Transform

@DefinitionDsl
class TransformationDsl {
    val chains: MutableList<TransformationChainDsl> = mutableListOf()

    fun transformation(action: TransformationChainDsl.() -> Unit) {
        chains += TransformationChainDsl().apply(action)
    }
}

@DefinitionDsl
class TransformationChainDsl {
    val transforms: MutableList<Collection<Transform>> = mutableListOf()

    fun chain(vararg transformations: Transform) {
        transforms += transformations.toList()
    }

    fun chain(vararg transformations: (Nodes) -> Nodes) {
        transforms += transformations.map {
            object : Transform {
                override fun process(nodes: Nodes): Nodes {
                    return it(nodes)
                }
            }
        }
    }
}
