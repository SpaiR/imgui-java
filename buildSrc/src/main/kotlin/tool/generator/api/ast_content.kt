package tool.generator.api

import spoon.reflect.declaration.CtAnnotation
import spoon.reflect.declaration.ModifierKind
import tool.generator.ast.AstEnumConstantDecl
import tool.generator.ast.AstParser

fun astEnumContent(markerAnnotation: CtAnnotation<*>): List<String> {
    val result = mutableListOf<String>()

    val file = markerAnnotation.getValueAsString(A_VALUE_FILE)!!
    val qualType = markerAnnotation.getValueAsString(A_VALUE_QUAL_TYPE)!!
    val astRoot = AstParser.readFromResource(file)

    val enums = astRoot.decls.filterDecls0 {
        it is AstEnumConstantDecl && it.qualType == qualType
    }.map { it as AstEnumConstantDecl }

    val factory = markerAnnotation.factory

    enums.forEach { e ->
        val f = factory.createField<Nothing>()

        f.setModifiers<Nothing>(setOf(ModifierKind.PUBLIC, ModifierKind.STATIC, ModifierKind.FINAL))
        f.setType<Nothing>(factory.createTypeParam("int"))
        f.setSimpleName<Nothing>(sanitizeName(markerAnnotation.getValueSanitizeName(qualType), e.name))
        buildString {
            if (e.docComment != null) {
                appendLine(e.docComment)
                if (e.value != null) {
                    appendLine()
                    append("<p>")
                }
            }
            if (e.value != null) {
                append("Definition: {@code ${e.value}}")
            }
        }.let {
            if (it.isNotEmpty()) {
                f.setDocComment<Nothing>(sanitizeDocComment(it))
            }
        }
        f.setAssignment<Nothing>(factory.createCodeSnippetExpression((e.evaluatedValue ?: e.order).toString()))

        result += f.prettyprint()
    }

    return result
}

private fun CtAnnotation<*>.getValueSanitizeName(default: String): String {
    return getValueAsString(A_VALUE_SANITIZE_NAME)?.takeIf(String::isNotEmpty) ?: default
}

private fun sanitizeName(aValueSanitizeName: String, name: String): String {
    var result =  name.replace(aValueSanitizeName, "").trim()
    if (result.toIntOrNull() != null) {
        result = "_$result"
    }
    return result
}

private fun sanitizeDocComment(doc: String): String {
    return doc
        .replace("*/", "* /")
        .replace(" > ", "{@code >}")
        .replace("< <", "{@code <<}")
        .replace(" < ", "{@code <}")
        .replace("->", "{@code ->}")
        .replace("&", "{@code &}")
}
