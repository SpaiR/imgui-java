package tool.generator.api.definition.node.type

import tool.generator.api.definition.node.DefinitionNode

/**
 * Definition node for a raw line.
 * Rendered as it is, without extra pre-processing.
 */
class LineDefinitionNode(var value: String) : DefinitionNode {
    override fun copy() = LineDefinitionNode(value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineDefinitionNode

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "LineDefinitionNode(value='$value')"
    }
}
