package tool.generator.api

import org.apache.commons.io.FileUtils
import spoon.reflect.declaration.CtType

class ExcludedSourceProcessor(
    private val type: CtType<*>
) {
    companion object {
        fun isProcessable(type: CtType<*>): Boolean {
            return type.hasAnnotation(A_NAME_EXCLUDED_SOURCE)
        }
    }

    fun process() {
        val sourceFile = type.position.file
        val parentDir = sourceFile.parentFile
        sourceFile.delete()
        if (FileUtils.isEmptyDirectory(parentDir)) {
            parentDir.delete()
        }
    }
}
