package tool.generator.api.definition.node

import tool.generator.api.definition.node.container.CloneableContent

/**
 * Interface to declare definition node.
 */
interface DefinitionNode : CloneableContent {
    /**
     * This is mostly required for debug purposes.
     */
    override fun toString(): String
}
