package tool.generator.api.definition.dsl.method

import tool.generator.api.definition.dsl.DefinitionDsl
import tool.generator.api.definition.node.transform.method.MARKER_AUTO_BODY
import tool.generator.api.definition.node.type.method.BodyDefinitionNode
import tool.generator.api.definition.node.type.method.ext.addLine

@DefinitionDsl
class BodyDsl {
    val data = BodyDefinitionNode()

    fun line(line: String) {
        data.addLine(line)
    }

    fun autoBody() {
        line(MARKER_AUTO_BODY)
    }

    fun code(value: String) {
        line(buildString {
            append(value)
            if (!value.endsWith(';')) {
                append(';')
            }
        })
    }

    fun comment(value: String) {
        line("// $value")
    }
}
