package tool.generator.ast

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream

const val AST_RESOURCE_PATH: String = "generator/api/ast"

private val astCache = mutableMapOf<String, AstRoot>()

fun parseAstResource(astResourcePath: String): AstRoot {
    return astCache.computeIfAbsent(astResourcePath) {
        val astResourceContent = getAtResourceStream(astResourcePath).use { it.reader().readText() }
        val objectMapper = ObjectMapper()
        objectMapper.readValue(astResourceContent, AstRoot::class.java)
    }
}

private fun getAtResourceStream(astResourcePath: String): InputStream {
    return AstRoot::class.java.classLoader.getResourceAsStream("$AST_RESOURCE_PATH/$astResourcePath")!!
}
