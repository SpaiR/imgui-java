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

data class CollectionContent<T : CloneableContent>(val value: Collection<T>) : CloneableContent {
    override fun copy() = CollectionContent(value.map { it.copy() })
}
