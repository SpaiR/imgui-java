package tool.generator.api

import spoon.reflect.code.CtJavaDoc
import spoon.reflect.declaration.CtElement
import spoon.reflect.declaration.CtField
import spoon.reflect.declaration.CtMethod
import spoon.reflect.declaration.CtType

class BindingSourceProcessor(
    private val type: CtType<*>
) {
    companion object {
        private const val BLANK_SPACE = "    "

        private val CLEANUP_ANNOTATIONS_REGEX = Regex(
            "@" + CLEANUP_ANNOTATIONS_LIST.joinToString(separator = "|", prefix = "(", postfix = ")") + ".*"
        )

        private val CLEANUP_IMPORTS_REGEX = Regex(
            "import.+" + CLEANUP_ANNOTATIONS_LIST.joinToString(separator = "|", prefix = "(", postfix = ")")
        )

        fun isProcessable(type: CtType<*>): Boolean {
            return type.hasAnnotation(A_NAME_BINDING_SOURCE)
        }
    }

    private val originalContent: String = type.position.file.readText()

    fun process() {
        val contentToReplace = mutableMapOf<ReplacePos, String>()
        contentToReplace += collectBindingFields()
        contentToReplace += collectBindingMethods()
        contentToReplace += collectBindingAstEnums()

        val contentBuilder = StringBuilder(originalContent)
        contentToReplace.keys.sortedDescending().forEach { replacePos ->
            contentBuilder.replace(replacePos.startIdx, replacePos.endIdx, contentToReplace[replacePos])
        }

        cleanupBindingAnnotations(contentBuilder)

        type.position.file.writeText(contentBuilder.toString())
    }

    private fun collectBindingFields(): Map<ReplacePos, String> {
        val result = mutableMapOf<ReplacePos, String>()
        for (field in type.fields) {
            if (field.hasAnnotation(A_NAME_BINDING_FIELD)) {
                result += processBindingField(field)
            }
        }
        return result
    }

    private fun collectBindingMethods(): Map<ReplacePos, String> {
        val result = mutableMapOf<ReplacePos, String>()
        for (method in type.methods) {
            if (method.hasAnnotation(A_NAME_BINDING_METHOD)) {
                result += processBindingMethod(method)
            }
        }
        return result
    }

    private fun collectBindingAstEnums(): Map<ReplacePos, String> {
        val result = mutableMapOf<ReplacePos, String>()
        for (field in type.fields) {
            if (field.hasAnnotation(A_NAME_BINDING_AST_ENUM)) {
                result += processBindingAstEnum(field)
            }
        }
        return result
    }

    private fun cleanupBindingAnnotations(contentBuilder: StringBuilder) {
        val linesToRemove = mutableListOf<String>()
        contentBuilder.lineSequence().forEach { line ->
            if (line.contains(CLEANUP_IMPORTS_REGEX)) {
                linesToRemove += line
            }
            if (line.contains(CLEANUP_ANNOTATIONS_REGEX)) {
                linesToRemove += line
            }
        }
        linesToRemove.reversed().forEach { line ->
            val idx = contentBuilder.indexOf(line)
            contentBuilder.delete(idx, idx + line.length + 1)
        }
    }

    private fun processBindingMethod(method: CtMethod<*>): Map<ReplacePos, String> {
        val contentToReplace = mutableMapOf<ReplacePos, String>()

        // Remove the original javadoc comment from the generated content.
        method.comments.find { it is CtJavaDoc }?.let {
            // sourceEnd has an additional offset of 6.
            // 1   - '/'
            // 2   - '\n'
            // 3-6 - '    '
            contentToReplace[ReplacePos(it.position.sourceStart, it.position.sourceEnd + 6)] = ""
        }

        val content = jvmMethodContent(method) + jniMethodContent(method)
        contentToReplace[method.findReplacePos()] = content.joinWithIndention()
        return contentToReplace
    }

    private fun processBindingField(field: CtField<*>): Map<ReplacePos, String> {
        val contentToReplace = mutableMapOf<ReplacePos, String>()

        // Remove the original javadoc comment from the generated content.
        field.comments.find { it is CtJavaDoc }?.let {
            // sourceEnd has an additional offset of 6.
            // 1   - '/'
            // 2   - '\n'
            // 3-6 - '    '
            contentToReplace[ReplacePos(it.position.sourceStart, it.position.sourceEnd + 6)] = ""
        }

        val content = jvmFieldContent(field) + jniFieldContent(field)
        contentToReplace[field.findReplacePos()] = content.joinWithIndention()
        return contentToReplace
    }

    private fun processBindingAstEnum(field: CtField<*>): Map<ReplacePos, String> {
        val content = astEnumContent(field.getAnnotation(A_NAME_BINDING_AST_ENUM)!!)
        val contentStr = content.joinWithIndention()
        return mapOf(field.findReplacePos() to contentStr)
    }

    private fun List<String>.joinWithIndention(): String {
        return joinToString("\n\n") { str ->
            str.lines().joinToString("\n") {
                it.takeIf(String::isNotBlank)?.prependIndent(BLANK_SPACE) ?: it
            }
        }
    }

    private fun CtElement.findReplacePos(): ReplacePos {
        var startIdx = annotations[0].position.sourceStart
        while (originalContent[startIdx] != '\n') {
            startIdx--
        }
        startIdx++
        val endIdx = position.sourceEnd + 1
        return ReplacePos(startIdx, endIdx)
    }

    private data class ReplacePos(val startIdx: Int, val endIdx: Int) : Comparable<ReplacePos> {
        override fun compareTo(other: ReplacePos): Int {
            return startIdx.compareTo(other.startIdx)
        }
    }
}
