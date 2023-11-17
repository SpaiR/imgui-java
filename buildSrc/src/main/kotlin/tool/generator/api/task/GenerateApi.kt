package tool.generator.api.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import tool.generator.api.definition.Definition
import tool.generator.api.definition.DefinitionMap
import tool.generator.api.definition.DefinitionRenderer
import tool.generator.api.definition.DefinitionVirtualContent
import tool.generator.api.definition._package.virtualContents
import java.io.File

/**
 * Task generates API by processing provided definitions.
 * To define a new definition create a proper package and class in the [tool.generator.api.definition._package] package.
 * Package name after `_` will match with the real package name of the class.
 * For example: [tool.generator.api.definition._package.imgui.ImGui] is the definition for [imgui.ImGui].
 *
 * The definition itself must implement [Definition] interface and provide a collection of [tool.generator.api.definition.node.DefinitionNode].
 * Definition nodes can be provided in multiple ways. The most common one is to use dsl declared in [tool.generator.api.definition.dsl].
 */
open class GenerateApi : DefaultTask() {
    companion object {
        private const val BLANK_SPACE = "    "
        private const val GENERATED_API_MARKER_BEGIN = "// GENERATED API: BEGIN"
        private const val GENERATED_API_MARKER_END = "// GENERATED API: END"
    }

    @Internal
    override fun getGroup() = "build"

    @Internal
    override fun getDescription() = "Generate API for native binaries."

    private val definitionMap = DefinitionMap.create()
    private val definitionRenderer = DefinitionRenderer()

    private val genSrcDir = "${project.projectDir}/src/main/java"
    private val genDstDir = "${project.projectDir}/src/generated/java"

    @TaskAction
    fun run() {
        logger.info("Generating API...")

        logger.info("Removing old generated files in $genDstDir...")
        project.file(genDstDir).deleteRecursively()

        logger.info("Copying raw sources...")
        logger.info(" - from: $genSrcDir")
        logger.info(" - into: $genDstDir")
        project.copy {
            from(genSrcDir)
            into(genDstDir)
        }

        logger.info("Creating virtual content...")
        createVirtualContent()

        logger.info("Processing raw sources...")

        for (sourceFile in project.file(genDstDir).walkTopDown()) {
            if (!sourceFile.isFile) {
                continue
            }

            definitionMap[fileToPackage(sourceFile)]?.let { definition ->
                logger.info(" - $sourceFile")
                processSourceFile(sourceFile, definition)
            }
        }
    }

    /**
     * Method creates classes and enum defined in [virtualContents] field.
     * This is helpful to use when a class is fully auto-generated.
     */
    private fun createVirtualContent() {
        virtualContents.forEach { vc ->
            val dirFile = File(genDstDir + '/' + vc.packageName.replace('.', '/'))
            dirFile.mkdirs()

            val contentFile = dirFile.resolve("${vc.contentName}.java")
            if (contentFile.exists()) {
                logger.error("Virtual content defined for content that exists: $contentFile")
            }
            contentFile.createNewFile()

            contentFile.writeText(
                buildString {
                    appendLine("package ${vc.packageName};")
                    appendLine()
                    appendLine("/**")
                    appendLine(" * GENERATED CONTENT")
                    if (vc.javaDoc.isNotEmpty()) {
                        vc.javaDoc.lineSequence().forEach {
                            appendLine(" * $it")
                        }
                    }
                    appendLine(" */")
                    append("public ")
                    if (vc.isFinal) {
                        append("final ")
                    }
                    when (vc.type) {
                        DefinitionVirtualContent.Type.CLASS -> append("class ")
                        DefinitionVirtualContent.Type.ENUM -> append("enum ")
                    }
                    append(vc.contentName)
                    appendLine(" {")
                    appendLine("}")
                }
            )
        }
    }

    private fun fileToPackage(file: File): String {
        return file.relativeTo(File(genDstDir)).toString().replace("/", ".").removeSuffix(".java")
    }

    private fun processSourceFile(sourceFile: File, definition: Definition) {
        sourceFile.writeText(buildString {
            appendLine(sourceFile.readText().substringBeforeLast("}"))
            appendLine("$BLANK_SPACE$GENERATED_API_MARKER_BEGIN")
            appendLine(renderDefinition(definition))
            appendLine("$BLANK_SPACE$GENERATED_API_MARKER_END")
            appendLine("}")
        })
    }

    /**
     * Method renders provided definition and adds a 4-space indent in front of every line in it.
     * The indent is required since every definition is rendered inside a class.
     * The definition itself can contain its own internal indents, we don't touch them and render definition as they are.
     */
    private fun renderDefinition(definition: Definition): String {
        return definitionRenderer.render(definition).lineSequence().joinToString("\n") {
            if (it.isBlank()) it else it.prependIndent(BLANK_SPACE)
        }.trimEnd()
    }
}
