package tool.generator.api.definition

import org.reflections.Reflections
import tool.generator.api.definition.node.DefinitionNode

/**
 * Interface to implement definition provider.
 */
interface Definition {
    companion object {
        private const val PACKAGE_POINT = "._package"
        private val ROOT_PATH: String = Definition::class.java.`package`.name
        val PACKAGE_PATH: String = ROOT_PATH + PACKAGE_POINT
        val ROOT_REFLECTION: Reflections = Reflections(ROOT_PATH)
    }

    /**
     * A list of all definition nodes. This is the thing which is used to render definitions.
     */
    fun getNodes(): Collection<DefinitionNode>
}
