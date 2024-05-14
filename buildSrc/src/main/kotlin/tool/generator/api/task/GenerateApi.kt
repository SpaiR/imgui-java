package tool.generator.api.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import spoon.Launcher
import spoon.support.compiler.FileSystemFolder
import tool.generator.api.BindingSourceProcessor
import tool.generator.api.ExcludedSourceProcessor

open class GenerateApi : DefaultTask() {
    @Internal
    override fun getGroup() = "build"

    @Internal
    override fun getDescription() = "Generate API for native binaries."

    private val genSrcDir = project.file("src/main/java")
    private val genDstDir = project.file("src/generated/java")

    @TaskAction
    fun run() {
        logger.info("Generating API...")

        logger.info("Removing old generated files in $genDstDir...")
        project.file(genDstDir).deleteRecursively()

        logger.info("Copying raw sources...")
        logger.info("| from: $genSrcDir")
        logger.info("| into: $genDstDir")
        project.copy {
            from(genSrcDir)
            into(genDstDir)
        }

        logger.info("Processing generated sources...")

        val launcher = Launcher()
        launcher.addInputResource(FileSystemFolder(genDstDir))
        val model = launcher.buildModel()
        model.allTypes.filter(BindingSourceProcessor::isProcessable).forEach {
            BindingSourceProcessor(it).process()
        }
        model.allTypes.filter(ExcludedSourceProcessor::isProcessable).forEach {
            ExcludedSourceProcessor(it).process()
        }
    }
}
