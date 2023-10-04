package tool.generator.api.definition

import org.reflections.Reflections

/**
 * Class collects and provides definitions map collected with the usage of reflection.
 * It basically reads everything under the [tool.generator.api.definition._package] package
 * and instantiates collected classes to read definitions from the map.
 */
class DefinitionMap private constructor() {
    companion object {
        private val definitionsPackageName: String = DefinitionMap::class.java.`package`.name + "._package"

        fun create(): DefinitionMap {
            return DefinitionMap().apply {
                Reflections(definitionsPackageName)
                    .getSubTypesOf(Definition::class.java)
                    .map { it.getDeclaredConstructor().newInstance() }
                    .forEach { definition ->
                        val packageName = getPackageName(definition)
                        val className = definition::class.java.simpleName
                        packageNameToDefinition["$packageName.$className"] = definition
                    }
            }
        }
    }

    private val packageNameToDefinition = mutableMapOf<String, Definition>()

    private fun getPackageName(definition: Definition): String {
        return stripDefinitionPackageName(getDefinitionPackageName(definition))
    }

    private fun getDefinitionPackageName(definition: Definition): String {
        return definition.javaClass.`package`.name
    }

    private fun stripDefinitionPackageName(apiPackageName: String): String {
        return apiPackageName.removePrefix("${definitionsPackageName}.")
    }

    operator fun get(packageName: String): Definition? = packageNameToDefinition[packageName]
}
