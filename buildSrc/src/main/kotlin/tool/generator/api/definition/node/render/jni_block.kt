package tool.generator.api.definition.node.render

import tool.generator.api.definition.node.type.JniBlockDefinitionNode

fun renderJniBlock(jniBlock: JniBlockDefinitionNode): String {
    return buildString {
        appendLine("/*JNI")
        jniBlock.value.lineSequence().map(String::trim).forEach { appendLine("    $it") }
        append("*/")
    }
}
