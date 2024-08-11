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

open class GenerateAst : DefaultTask() {
    @Internal
    override fun getGroup() = "build"

    @Internal
    override fun getDescription() = "Generate AST tree for declared header files."

    @InputFiles
    lateinit var headerFiles: Collection<File>

    private val dstDir: File = File("${project.rootDir}/buildSrc/src/main/resources/${AstParser.RESOURCE_PATH}")
    private val objectMapper: ObjectMapper = ObjectMapper()

    // Content of the currently parsed header file. Can be used during parsing to extract additional information.
    private lateinit var currentParsingHeaderContent: String

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
            System.gc()

            logger.info("| $header")

            // Read the header.
            currentParsingHeaderContent = header.readText()

            // Header hash content to create a unique path for generated content.
            val headerHash = md5Hash(currentParsingHeaderContent)

            // We create a unique folder to store files generated for the header.
            // This is necessary because when we store all headers in the same directory,
            // their AST dump can overlap with each other.
            val astBuildDir = project.layout.buildDirectory.dir("generated/ast/$headerHash").get().asFile.apply {
                mkdirs()
            }

            logger.info("  | Making an ast-dump...")

            // Destination for ast-dump.
            val headerName = header.nameWithoutExtension
            val astBumpJson = File("$astBuildDir/$headerName.json")

            // Call clang++ with the script.
            // During the process of making an ast-dump there could be errors/warnings.
            // Thus making a call like that can help to ignore them.
            callClangAstBump(scriptPath, header, astBumpJson)

            logger.info("  | Processing an ast-dump result: $astBumpJson...")

            // Read the ast-dump of the whole header content.
            // It will contain technical information, like unneeded typdefs, std types etc.
            val fullDeclsList = mutableListOf<Decl>()
            objectMapper.readTree(astBumpJson).get("inner").forEach { topDecl ->
                parseDeclNode0(topDecl)?.let(fullDeclsList::add)
            }

            logger.info("  | Sorting an ast-decls...")
            sortDecls0(fullDeclsList)

            ///////////////
            // In the end we write down the ast-decls result to the file.
            // The file itself will be stored in VCS, so we can understand if there were any changes in native code.

            val astResultJson = File("$dstDir/ast-$headerName.json")

            logger.info("  | Writing processed AST: $astResultJson...")

            objectMapper.writer().writeValue(
                astResultJson, AstRoot(
                    info = AstInfo(
                        source = header.relativeTo(project.rootDir).path,
                        hash = headerHash,
                        url = shellRun(header.parentFile) {
                            command("git", listOf("config", "--get", "remote.origin.url"))
                        },
                        revision = shellRun(header.parentFile) {
                            command("git", listOf("rev-parse", "HEAD"))
                        },
                    ),
                    decls = fullDeclsList
                )
            )
        }
    }

    private fun parseDeclNode0(declNode: JsonNode): Decl? {
        fun logParsingDecl(name: String) {
            logger.info("| Parsing $name...")
        }

        fun logParsingInner(name: String) {
            logger.info(" | $name")
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

        fun JsonNode.getDefaultParamValue(): String? {
            fun getOffset(jsonNode: JsonNode): Pair<Int, Int> {
                val loc = if (jsonNode.has("expansionLoc")) {
                    jsonNode.get("expansionLoc")
                } else {
                    jsonNode
                }
                return loc.get("offset").asInt() to loc.get("tokLen").asInt()
            }
            return findValue("inner").findValue("range").let { range ->
                val (beginOffset, _) = getOffset(range.get("begin"))
                val (endOffset, endTokLoc) = getOffset(range.get("end"))
                if (endOffset + endTokLoc < currentParsingHeaderContent.length) {
                    currentParsingHeaderContent.substring(beginOffset, endOffset + endTokLoc)
                } else {
                    null
                }
            }
        }

        // Ignore declaration nodes with technical information.
        // This information can be specific for the OS where the script was called.
        declNode.findValue("name")?.asText()?.let {
            if (it.startsWith("_")
                || it.startsWith("operator")
                || it.startsWith("rus")
                || it.startsWith("sig")
                || it.startsWith("proc_")
            ) {
                return null
            }
            when (it) {
                "std", "timeval", "wait", "rlimit" -> return null
                else -> null // do nothing
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

                    if (name.startsWith("_")) { // internal API namespaces
                        return null
                    }

                    logParsingDecl("namespace $name")

                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }

                    return AstNamespaceDecl(offset, name, decls)
                }

                "FullComment" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("full comment")

                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }

                    return AstFullComment(offset, decls)
                }

                "ParagraphComment" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val offset = declNode.getOffset()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("paragraph comment")

                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }

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

                    declNode.get("inner")?.forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }

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

                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }

                    // Sort enums by their real pos in the header file.
                    sortDecls0(decls)

                    var enumDecls = decls.filterIsInstance<AstEnumConstantDecl>()
                    val otherDecls = decls - enumDecls.toSet()

                    var order = 0
                    enumDecls = enumDecls.mapIndexed { idx, decl ->
                        AstEnumConstantDecl(
                            decl.offset,
                            decl.name,
                            decl.docComment,
                            decl.qualType,
                            order++,
                            decl.value,
                            lookupEnumEvaluatedValue0(enumDecls, idx),
                        )
                    }

                    return AstEnumDecl(offset, name, otherDecls + enumDecls)
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

                    val evaluatedValue: Int? = declNode.findValue("value")?.asInt()
                    val docComment: String? = declNode.findValuesAsText("text")?.joinToString(" ") { it.trim() }

                    logParsingInner("enum value $name")

                    return AstEnumConstantDecl(
                        offset,
                        name,
                        docComment,
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

                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode0(innerDecl)?.let(decls::add)
                    }

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
     * Sort decls in the provided list recursively using offset value.
     */
    private fun sortDecls0(decls: MutableList<Decl>) {
        decls.sortWith { d1, d2 ->
            d1.offset.compareTo(d2.offset)
        }
        decls.forEach {
            if (it is DeclContainer) {
                sortDecls0(it.decls.toMutableList())
            }
        }
    }

    /**
     * This method helps to find the value of the enum constant.
     * It relies on C++ behaviour, when the value is something defined or the order of the enum itself.
     */
    private fun lookupEnumEvaluatedValue0(decls: List<AstEnumConstantDecl>, idx: Int): Int {
        val decl = decls[idx]

        if (decl.evaluatedValue != null) {
            return decl.evaluatedValue
        }

        if (decl.value != null) {
            decl.value.toIntOrNull()?.let {
                return it
            }
        }

        if (idx == 0) {
            return 0
        }

        // This will behave like if we're using an order of the enum as the value.
        return lookupEnumEvaluatedValue0(decls, idx - 1) + 1
    }
}
