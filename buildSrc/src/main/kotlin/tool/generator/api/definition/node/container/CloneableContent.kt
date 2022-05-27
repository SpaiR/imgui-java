package tool.generator.api.definition.node.container

interface CloneableContent {
    fun copy(): CloneableContent
}

data class StringContent(val value: String) : CloneableContent {
    override fun copy() = this
}

data class BooleanContent(val value: Boolean) : CloneableContent {
    override fun copy() = this
}

data class ListContent<T : CloneableContent>(val value: List<T>) : CloneableContent {
    override fun copy() = ListContent(value.map { it.copy() })
}
