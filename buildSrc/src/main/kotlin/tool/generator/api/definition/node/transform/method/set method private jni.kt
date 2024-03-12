package tool.generator.api.definition.node.transform.method

import org.gradle.configurationcache.extensions.capitalized
import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*

/**
 * Transformation transforms methods to JNI form.
 * 1. Makes them private
 * 2. Makes them native
 * 3. Tweaks the name
 */
@Suppress("ClassName")
object `set method private jni` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode) {
                transform(node)
            }

            result += node
        }

        return result
    }

    private fun transform(method: MethodDefinitionNode) {
        method.signature.visibility = SignatureVisibility.PRIVATE
        method.signature.isNative = true
        method.signature.name = METHOD_JNI_PREFIX + method.signature.name.capitalized()
    }

}
