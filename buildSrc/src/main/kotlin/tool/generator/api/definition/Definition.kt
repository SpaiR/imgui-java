package tool.generator.api.definition

import tool.generator.api.definition.node.DefinitionNode

/**
 * Interface to implement definition provider.
 */
interface Definition {
    companion object {
        private const val PACKAGE_POINT = "._package"
        val ROOT_PATH = Definition::class.java.`package`.name
        val PACKAGE_PATH = ROOT_PATH + PACKAGE_POINT
    }

    /**
     * A list of all definition nodes. This is the thing which is used to render definitions.
     */
    fun getNodes(): Collection<DefinitionNode>
}
