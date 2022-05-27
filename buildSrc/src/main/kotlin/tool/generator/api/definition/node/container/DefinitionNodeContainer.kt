package tool.generator.api.definition.node.container

import tool.generator.api.definition.node.DefinitionNode
import kotlin.reflect.KClass

class DefinitionNodeContainer<T : DefinitionNode>(
    private val content: MutableList<T> = mutableListOf(),
) {
    fun add(node: T) {
        content += node
    }

    fun add(index: Int, node: T) {
        content.add(index, node)
    }

    fun addAll(vararg nodes: T) {
        content.addAll(nodes)
    }

    fun addAll(nodes: Collection<T>) {
        content.addAll(nodes)
    }

    fun remove(node: T) {
        content -= node
    }

    fun clear() {
        content.clear()
    }

    fun clear(klass: KClass<out T>) {
        content.removeIf { it::class == klass }
    }

    fun contains(node: T): Boolean {
        return content.find { it === node } != null
    }

    fun contains(klass: KClass<out T>): Boolean {
        return find(klass) != null
    }

    fun <R : T> get(klass: KClass<R>): R {
        return find(klass) ?: error("No $klass node found in $this")
    }

    fun <R : T> getAll(klass: KClass<R>): List<R> {
        return content.filterIsInstance(klass.java)
    }

    fun <R : T> find(klass: KClass<R>): R? {
        return getAll(klass).firstOrNull()
    }

    @Suppress("UNCHECKED_CAST")
    fun copy(): DefinitionNodeContainer<T> {
        return DefinitionNodeContainer(content.map { it.copy() as T }.toMutableList(),)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DefinitionNodeContainer<*>

        return content == other.content
    }

    override fun hashCode(): Int {
        return content.hashCode()
    }

    override fun toString(): String {
        return "DefinitionNodeContainer(content=$content)"
    }
}
