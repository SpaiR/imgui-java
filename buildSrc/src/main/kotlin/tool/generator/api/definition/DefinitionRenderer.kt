package tool.generator.api.definition

import tool.generator.api.definition.node.render.NodeRenderer

/**
 * Renderer for [Definition] class.
 */
class DefinitionRenderer {
    private val nodeRenderer = NodeRenderer()

    fun render(definition: Definition): String {
        return buildString {
            definition.getNodes().map(nodeRenderer::render).toSet().forEach {
                appendLine(it)
                appendLine() // This adds an empty line between rendered nodes.
            }
        }
    }
}
