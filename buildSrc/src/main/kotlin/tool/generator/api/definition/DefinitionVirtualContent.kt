package tool.generator.api.definition

data class DefinitionVirtualContent(
    val packageName: String,
    val contentName: String,
    val javaDoc: String = "",
    val isFinal: Boolean = false,
    val type: Type = Type.CLASS,
    val extends: String = "",
    val implements: Set<String> = emptySet(),
) {
    enum class Type {
        CLASS,
        ENUM
    }
}
