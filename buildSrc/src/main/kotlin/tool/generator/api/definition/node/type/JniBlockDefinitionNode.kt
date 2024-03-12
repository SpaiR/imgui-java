package tool.generator.api.definition.node.type

import tool.generator.api.definition.node.DefinitionNode

/**
 * Definition node for a jni block.
 *
 * Rendered like:
 * ```
 * /*JNI
 *  * content
 *  */
 * ```
 */
class JniBlockDefinitionNode(var value: String) : DefinitionNode {
    override fun copy() = JniBlockDefinitionNode(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JniBlockDefinitionNode

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "JniBlockDefinitionNode(value='$value')"
    }
}
