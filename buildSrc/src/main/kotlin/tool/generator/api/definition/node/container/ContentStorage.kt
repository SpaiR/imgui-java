package tool.generator.api.definition.node.container

data class ContentStorage(
    private val storage: MutableMap<String, CloneableContent> = mutableMapOf()
) {
    @Suppress("UNCHECKED_CAST")
    fun <T : CloneableContent> get(id: String): T? {
        return storage[id]?.let { it as T }
    }

    fun <T : CloneableContent> getOrPut(id: String, default: T): T {
        if (!has(id)) {
            put(id, default)
        }
        return get(id)!!
    }

    fun <T : CloneableContent> value(id: String): T {
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
}
