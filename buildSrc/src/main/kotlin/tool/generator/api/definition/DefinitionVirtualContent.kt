package tool.generator.api.definition

data class DefinitionVirtualContent(
    val packageName: String,
    val contentName: String,
    val javaDoc: String = "",
    val isFinal: Boolean = false,
    val type: Type = Type.CLASS,
) {
    enum class Type {
        CLASS,
        ENUM
    }
}
