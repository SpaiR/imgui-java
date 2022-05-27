package tool.generator.api.definition.node

/**
 * Interface to declare definition node.
 */
interface DefinitionNode {
    /**
     * Clone alternative. Definition nodes can be transformed.
     * So to avoid unpredictable behaviour we need to be able to freely copy nodes.
     */
    fun copy(): DefinitionNode

    /**
     * This is mostly required for debug purposes.
     */
    override fun toString(): String
}
