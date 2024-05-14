package tool.generator.ast

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream

object AstParser {
    const val RESOURCE_PATH: String = "generator/api/ast"

    private val astCache = mutableMapOf<String, AstRoot>()

    fun readFromResource(astResourcePath: String): AstRoot {
        return astCache.computeIfAbsent(astResourcePath) {
            val astResourceContent = getAtResourceStream(astResourcePath).use { s -> s.reader().use { r -> r.readText() } }
            val objectMapper = ObjectMapper()
            objectMapper.readValue(astResourceContent, AstRoot::class.java)
        }
    }

    private fun getAtResourceStream(astResourcePath: String): InputStream {
        return AstRoot::class.java.classLoader.getResourceAsStream("$RESOURCE_PATH/$astResourcePath")!!
    }
}
