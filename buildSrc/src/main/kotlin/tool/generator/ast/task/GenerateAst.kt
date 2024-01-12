package tool.generator.ast.task

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.lordcodes.turtle.shellRun
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import tool.generator.ast.*
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

open class GenerateAst : DefaultTask() {
    @Internal
    override fun getGroup() = "build"

    @Internal
    override fun getDescription() = "Generate AST tree for declared header files."

    @InputFiles
    lateinit var headerFiles: List<File>

    private val dstDir: File = File("${project.rootDir}/buildSrc/src/main/resources/$AST_RESOURCE_PATH")

    private val objectMapper: ObjectMapper = ObjectMapper()

    /**
     * During the parsing of ast-bump we need to access an initial header file content.
     * Required for cases like: get the content of the file (comment or default param value) by offset pos in file.
     */
    private var currentParsingHeaderContent: String = ""

    /**
     * This is used only to log stuff properly.
     */
    private var currentParsingIndent: Int = 0

    init {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }

    @TaskAction
    fun run() {
        logger.info("Generating AST...")

        dstDir.mkdirs()

        val scriptPath = "$dstDir/_gen_ast.sh"
        File(scriptPath).setExecutable(true)

        logger.info("Processing headers...")

        headerFiles.forEach { header ->
            logger.info(" - $header")

            // Store the header content in the field, so we can access it when parsing.
            currentParsingHeaderContent = header.readText()

            // Header hash content to create a unique path for generated content.
            val headerHash = md5Hash(currentParsingHeaderContent)

            // We create a unique folder to store files generated for the header.
            // This is necessary because when we store all headers in the same directory,
            // their AST bump can overlap with each other.
            val astBuildDir = File("${project.buildDir}/generated/ast/$headerHash")
            astBuildDir.mkdirs()

            // Move header content to the temp file to make an ast-bump of it.
            val headerName = header.nameWithoutExtension
            val tmpHeaderFile = File("$astBuildDir/$headerName.h")
            tmpHeaderFile.createNewFile()
            tmpHeaderFile.writeText(currentParsingHeaderContent)

            logger.info("   | Making an ast-bump...")

            // Destination for ast-bump.
            val astBumpJson = File("$astBuildDir/$headerName.json")

            // Call clang++ though a script.
            // During the process of making an ast-bump there could be errors/warnings.
            // Thus making a call like that can help to ignore them.
            callClangAstBump(scriptPath, tmpHeaderFile, astBumpJson)

            logger.info("   | Processing an ast-bump result: $astBumpJson...")

            // Read the ast-bump of the whole header content.
            // It will contain technical information, like unneeded typdefs, std types etc.
            val fullDeclsList = mutableListOf<Decl>()
            objectMapper.readTree(astBumpJson).get("inner").forEach { topDecl ->
                parseDeclNode0(topDecl)?.let(fullDeclsList::add)
            }

            //////////////
            // After parsing the entire header file, we need to remove unnecessary information from it.
            // So we pre-process the initial content and parse it again.
            // This way, we can compare the initial result with the trimmed version
            // to remove types and declarations that represent compiler information.


            // Remove all includes and typedefs.
            // They are not needed to process the AST, but they add extra information to the final dump.
            // And since we want to have an ast-dump as minimal as possible we remove that information.
            currentParsingHeaderContent = currentParsingHeaderContent
                .replace("#include .*".toRegex(), "")
                .replace("typedef .*".toRegex(), "")

            // Re-write with pre-processed content.
            tmpHeaderFile.writeText(currentParsingHeaderContent)

            callClangAstBump(scriptPath, tmpHeaderFile, astBumpJson)

            // As its said - we do the parsing once again, but now the final result won't contain std types.
            val strippedDeclsList = mutableListOf<Decl>()
            objectMapper.readTree(astBumpJson).get("inner").forEach { topDecl ->
                parseDeclNode0(topDecl)?.let(strippedDeclsList::add)
            }

            logger.info("   | Removing an ast-decls diff...")
            val resultDeclsList = removeAstDiff(fullDeclsList, strippedDeclsList)

            logger.info("   | Sorting an ast-decls...")
            sortDecls0(resultDeclsList)

            ///////////////
            // In the end we write down the ast-decls result to the file.
            // The file itself will be stored in VCS,
            // so we could understand if there were any changes in native code.

            val astResultJson = File("$dstDir/ast-$headerName.json")

            logger.info("   | Writing processed AST: $astResultJson...")

            objectMapper.writer().writeValue(
                astResultJson, AstRoot(
                    info = AstInfo(
                        version = project.version as String,
                        source = header.relativeTo(project.rootDir).path,
                        hash = headerHash,
                        url = shellRun(header.parentFile) {
                            command("git", listOf("config", "--get", "remote.origin.url"))
                        },
                        revision = shellRun(header.parentFile) {
                            command("git", listOf("rev-parse", "HEAD"))
                        },
                    ),
                    decls = resultDeclsList
                )
            )
        }
    }

    private fun parseDeclNode0(declNode: JsonNode): Decl? {
        fun logParsingDecl(name: String) {
            logger.info("    ${" ".repeat(currentParsingIndent)}- Parsing $name...")
        }

        fun logParsingInner(name: String) {
            logger.info("    ${" ".repeat(currentParsingIndent)}| $name")
        }

        fun JsonNode.hasNoName(): Boolean {
            return !has("name") || get("name").asText().isBlank()
        }

        fun JsonNode.hasNoInnerContent(): Boolean {
            return !has("inner") || get("inner").isEmpty
        }

        fun JsonNode.getOffset(): Int {
            val loc = (get("loc") ?: get("range").get("begin")).let { loc ->
                if (loc.has("expansionLoc")) {
                    loc.get("expansionLoc")
                } else {
                    loc
                }
            }
            return loc.get("offset")?.asInt() ?: -1
        }

        fun JsonNode.getDefaultParamValue(): String {
            fun getOffset(jsonNode: JsonNode): Pair<Int, Int> {
                val loc = if (jsonNode.has("expansionLoc")) {
                    jsonNode.get("expansionLoc")
                } else {
                    jsonNode
                }
                return loc.get("offset").asInt() to loc.get("tokLen").asInt()
            }
            return get("inner").get(0).get("range").let { range ->
                val (beginOffset, _) = getOffset(range.get("begin"))
                val (endOffset, endTokLoc) = getOffset(range.get("end"))
                currentParsingHeaderContent.substring(beginOffset, endOffset + endTokLoc)
            }
        }

        try {
            return when (declNode.get("kind").textValue()) {
                "NamespaceDecl" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("namespace $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstNamespaceDecl(offset, name, decls)
                }

                "FullComment" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("full comment")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstFullComment(offset, decls)
                }

                "ParagraphComment" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("paragraph comment")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstParagraphComment(offset, decls)
                }

                "TextComment" -> {
                    val offset = declNode.getOffset()
                    val text = declNode.get("text").asText()
                    logParsingInner("text comment")
                    return AstTextComment(offset, text)
                }

                "FunctionDecl", "CXXMethodDecl" -> {
                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val resultType = declNode.get("type").get("qualType").asText().substringBefore("(").trim()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("function $name")

                    currentParsingIndent++
                    declNode.get("inner")?.forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstFunctionDecl(offset, name, resultType, decls)
                }

                "ParmVarDecl" -> {
                    if (declNode.hasNoName()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val qualType = declNode.get("type").get("qualType").asText()
                    val desugaredQualType = declNode.get("type").get("desugaredQualType")?.asText() ?: qualType

                    val defaultValue: String? = if (declNode.has("init")) {
                        declNode.getDefaultParamValue()
                    } else {
                        null
                    }

                    logParsingInner("param $name")

                    return AstParmVarDecl(offset, name, qualType, desugaredQualType, defaultValue)
                }

                "FormatAttr" -> {
                    logParsingInner("param ...")
                    return AstParmVarDecl.asFormatAttr(declNode.getOffset())
                }

                "EnumDecl" -> {
                    if (declNode.hasNoInnerContent() || declNode.hasNoName()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("enum $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent

                    // Sort enums by their real pos in the header file.
                    sortDecls0(decls)

                    var order = 0
                    val sortedDecls = decls.map { decl ->
                        if (decl is AstEnumConstantDecl) {
                            if (order == 0 && decl.value != null) {
                                order = decl.value.toIntOrNull() ?: 0
                            }

                            AstEnumConstantDecl(
                                decl.offset,
                                decl.name,
                                decl.qualType,
                                order++,
                                decl.value,
                                decl.evaluatedValue
                            )
                        } else {
                            decl
                        }
                    }

                    return AstEnumDecl(offset, name, sortedDecls)
                }

                "EnumConstantDecl" -> {
                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val qualType = declNode.get("type").get("qualType").asText()

                    // ->inner[0]->
                    val declValue: String? = declNode.get("inner")?.get(0)?.let {
                        // Check if the first node is not a comment.
                        if (it.get("kind").asText() != "FullComment") {
                            declNode.getDefaultParamValue()
                        } else {
                            null
                        }
                    }

                    // ->inner[0]->inner->value
                    val evaluatedValue: String? = declNode.get("inner")?.get(0)?.get("inner")?.get(0)?.let {
                        if (it.get("kind")?.asText() == "ConstantExpr") {
                            it.get("value").asText()
                        } else {
                            null
                        }
                    }

                    logParsingInner("enum value $name")

                    return AstEnumConstantDecl(
                        offset,
                        name,
                        qualType,
                        // We provide a proper order in EnumDecl, after sorting all enums by their offset.
                        -1,
                        declValue,
                        evaluatedValue
                    )
                }

                "CXXRecordDecl" -> {
                    if (declNode.hasNoInnerContent() || declNode.hasNoName()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("record $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstRecordDecl(offset, name, decls)
                }

                "FieldDecl" -> {
                    if (declNode.hasNoName()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val name = declNode.get("name").asText()
                    val qualType = declNode.get("type").get("qualType").asText()
                    val desugaredQualType = declNode.get("type").get("desugaredQualType")?.asText() ?: qualType

                    logParsingInner("field $name")

                    return AstFieldDecl(offset, name, qualType, desugaredQualType)
                }

                else -> {
                    null
                }
            }
        } catch (e: Exception) {
            logger.error("Unable to parse: {}", declNode)
            throw Error(e)
        }
    }

    private fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

    private fun callClangAstBump(scriptPath: String, srcHeader: File, dstJson: File) {
        dstJson.delete()
        val pb = ProcessBuilder()
        pb.command(scriptPath, srcHeader.absolutePath, dstJson.absolutePath)
        pb.start().waitFor()
    }

    /**
     * Function removes difference between ast-trees.
     * Basically, it returns the list which contains only types with the same name.
     */
    private fun removeAstDiff(fullDeclsList: List<Decl>, strippedDeclsList: List<Decl>): List<Decl> {
        fun isSameNamespaceName(d1: Decl, d2: Decl): Boolean {
            return d1 is AstNamespaceDecl && d2 is AstNamespaceDecl && d1.name == d2.name
        }

        fun isSameRecordName(d1: Decl, d2: Decl): Boolean {
            return d1 is AstRecordDecl && d2 is AstRecordDecl && d1.name == d2.name
        }

        fun isSameEnumName(d1: Decl, d2: Decl): Boolean {
            return d1 is AstEnumDecl && d2 is AstEnumDecl && d1.name == d2.name
        }

        fun isSameFunctionName(d1: Decl, d2: Decl): Boolean {
            return d1 is AstFunctionDecl && d2 is AstFunctionDecl && d1.name == d2.name
        }

        return fullDeclsList
            .filter { d1 ->
                strippedDeclsList
                    .find { d2 ->
                        d1 == d2
                                || isSameRecordName(d1, d2)
                                || isSameNamespaceName(d1, d2)
                                || isSameEnumName(d1, d2)
                                || isSameFunctionName(d1, d2)
                    } != null
            }
            .filterNot { it is AstFunctionDecl && it.name.startsWith("operator") }
            .toMutableList() // Ensure final list will be mutable for later sorting.
    }

    /**
     * Sort decls in the provided list recursively using offset value.
     */
    @Suppress("JavaCollectionsStaticMethodOnImmutableList")
    private fun sortDecls0(decls: List<Decl>) {
        Collections.sort(decls) { d1, d2 ->
            d1.offset.compareTo(d2.offset)
        }
        decls.forEach {
            if (it is DeclContainer) {
                sortDecls0(it.decls)
            }
        }
    }
}
