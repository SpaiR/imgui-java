package tool.generator.api.definition.node.container

class ContentStorage(
    private val storage: MutableMap<String, CloneableContent> = mutableMapOf()
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> get(id: String): T? {
        return storage[id]?.let { it as T }
    }

    fun <T> value(id: String): T {
        return get(id)!!
    }

    fun put(id: String, value: CloneableContent) {
        storage[id] = value
    }

    fun has(id: String): Boolean {
        return storage.contains(id)
    }

    fun copy(): ContentStorage {
        return ContentStorage(storage.mapValues { (_, content) -> content.copy() }.toMutableMap())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentStorage

        return storage == other.storage
    }

    override fun hashCode(): Int {
        return storage.hashCode()
    }

    override fun toString(): String {
        return "ContentStorage(storage=$storage)"
    }
}
