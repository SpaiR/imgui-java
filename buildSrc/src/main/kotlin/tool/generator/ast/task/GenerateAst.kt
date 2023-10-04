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
    lateinit var headerFiles: List<File>

    private val dstDir: File = File("${project.rootDir}/buildSrc/src/main/resources/$AST_RESOURCE_PATH")

    private val objectMapper: ObjectMapper = ObjectMapper()

    private var currentParsingHeaderContent: String = ""
    private var currentParsingEnumOrder: Int = 0
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

        val buildDir = "${project.buildDir}/generated/ast"
        File(buildDir).mkdirs()

        logger.info("Processing headers...")

        headerFiles.forEach { header ->
            logger.info(" - $header")

            // Path to the header file relative to the root project directory.
            val relativeHeaderPath = header.relativeTo(project.rootDir).path
            val headerName = header.nameWithoutExtension

            // Remove all includes and typedefs.
            // They are not needed to process the AST, but they add extra information to the final dump.
            // And since we want to have an ast-dump as minimal as possible we remove that information.
            currentParsingHeaderContent = header.readText()
                .replace("#include .*".toRegex(), "")
                .replace("typedef .*".toRegex(), "")

            val tmpHeaderFile = File("$buildDir/$headerName.h")
            tmpHeaderFile.createNewFile()
            tmpHeaderFile.writeText(currentParsingHeaderContent)

            val astBumpJson = File("$buildDir/$headerName.json")
            val astResultJson = File("$dstDir/ast-$headerName.json")

            astBumpJson.delete()
            astResultJson.delete()

            logger.info("   | Making ast-bump...")

            val pb = ProcessBuilder()
            pb.command(scriptPath, tmpHeaderFile.absolutePath, astBumpJson.absolutePath)
            pb.start().waitFor()

            logger.info("   | Processing ast-bump result: $astBumpJson...")

            val decls = mutableListOf<Decl>()

            objectMapper.readTree(astBumpJson).get("inner").forEach { topDecl ->
                parseDeclNode(topDecl)?.let(decls::add)
            }

            logger.info("   | Writing processed AST: $astResultJson...")

            objectMapper.writer().writeValue(
                astResultJson, AstRoot(
                    info = AstInfo(
                        version = project.version as String,
                        source = relativeHeaderPath,
                        hash = md5Hash(currentParsingHeaderContent),
                        url = getGitRemoteUrl(header.parentFile),
                        revision = getGitRemoteHash(header.parentFile),
                    ),
                    decls = decls
                )
            )
        }
    }

    private fun getGitRemoteUrl(dir: File): String {
        return shellRun(dir) {
            command("git", listOf("config", "--get", "remote.origin.url"))
        }
    }

    private fun getGitRemoteHash(dir: File): String {
        return shellRun(dir) {
            command("git", listOf("rev-parse", "HEAD"))
        }
    }

    private fun parseDeclNode(declNode: JsonNode): Decl? {
        fun logParsingDecl(name: String) {
            logger.info("    ${" ".repeat(currentParsingIndent)}- Parsing $name...")
        }

        fun logParsingInner(name: String) {
            logger.info("    ${" ".repeat(currentParsingIndent)}| $name")
        }
        try {
            return when (declNode.get("kind").textValue()) {
                "NamespaceDecl" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("namespace $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstNamespaceDecl(fileLoc, name, decls)
                }

                "FullComment" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("full comment")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstFullComment(fileLoc, decls)
                }

                "ParagraphComment" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("paragraph comment")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstParagraphComment(fileLoc, decls)
                }

                "TextComment" -> {
                    val fileLoc = getFileLoc(declNode)
                    val text = declNode.get("text").asText()
                    logParsingInner("text comment")
                    return AstTextComment(fileLoc, text)
                }

                "FunctionDecl" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val resultType = declNode.get("type").get("qualType").asText().substringBefore("(").trim()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("function $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstFunctionDecl(fileLoc, name, resultType, decls)
                }

                "ParmVarDecl" -> {
                    if (declNode.hasNoName()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val qualType = declNode.get("type").get("qualType").asText()
                    val desugaredQualType = declNode.get("type").get("desugaredQualType")?.asText() ?: qualType

                    val defaultValue: String? = if (declNode.has("init")) {
                        getDefaultParamValue(declNode)
                    } else {
                        null
                    }

                    logParsingInner("param $name")

                    return AstParmVarDecl(fileLoc, name, qualType, desugaredQualType, defaultValue)
                }

                "FormatAttr" -> {
                    logParsingInner("param ...")
                    return AstParmVarDecl.FORMAT_ATTR
                }

                "EnumDecl" -> {
                    if (declNode.hasNoInnerContent()) {
                        return null
                    }

                    // Reset enum order value.
                    currentParsingEnumOrder = 0

                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("enum $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstEnumDecl(fileLoc, name, decls)
                }

                "EnumConstantDecl" -> {
                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val qualType = declNode.get("type").get("qualType").asText()

                    // ->inner[0]->
                    val declValue: String? = declNode.get("inner")?.get(0)?.let {
                        // Check if the first node is not a comment.
                        if (it.get("kind").asText() != "FullComment") {
                            getDefaultParamValue(declNode)
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

                    if (currentParsingEnumOrder == 0 && declValue != null) {
                        currentParsingEnumOrder = declValue.toIntOrNull() ?: 0
                    }

                    logParsingInner("enum value $name")

                    return AstEnumConstantDecl(
                        fileLoc,
                        name,
                        qualType,
                        currentParsingEnumOrder++,
                        declValue,
                        evaluatedValue
                    )
                }

                "CXXRecordDecl" -> {
                    if (declNode.hasNoInnerContent() || declNode.hasNoName()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val decls = mutableListOf<Decl>()

                    logParsingDecl("record $name")

                    currentParsingIndent++
                    declNode.get("inner").forEach { innerDecl ->
                        parseDeclNode(innerDecl)?.let(decls::add)
                    }
                    currentParsingIndent--

                    return AstRecordDecl(fileLoc, name, decls)
                }

                "FieldDecl" -> {
                    if (declNode.hasNoName()) {
                        return null
                    }

                    val fileLoc = getFileLoc(declNode)
                    val name = declNode.get("name").asText()
                    val qualType = declNode.get("type").get("qualType").asText()
                    val desugaredQualType = declNode.get("type").get("desugaredQualType")?.asText() ?: qualType

                    logParsingInner("field $name")

                    return AstFieldDecl(fileLoc, name, qualType, desugaredQualType)
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

    private fun JsonNode.hasNoName(): Boolean {
        return !has("name") || get("name").asText().isBlank()
    }

    private fun JsonNode.hasNoInnerContent(): Boolean {
        return !has("inner") || get("inner").isEmpty
    }

    private fun getFileLoc(declNode: JsonNode): FileLoc {
        return declNode.get("loc").let { loc ->
            if (loc.has("expansionLoc")) {
                parseLocNode(loc.get("expansionLoc"))
            } else {
                parseLocNode(loc)
            }
        }
    }

    private fun getDefaultParamValue(declNode: JsonNode): String {
        fun getOffset(jsonNode: JsonNode): Pair<Int, Int> {
            return if (jsonNode.has("expansionLoc")) {
                // ->expansionLoc->offset & tokLen
                jsonNode.get("expansionLoc").let { loc ->
                    loc.get("offset").asInt() to loc.get("tokLen").asInt()
                }
            } else {
                // ->offset & tokLen
                jsonNode.get("offset").asInt() to jsonNode.get("tokLen").asInt()
            }
        }
        // ->inner[0]->range->
        return declNode.get("inner").get(0).get("range").let { range ->
            // ->range->begin->
            val (beginOffset, _) = getOffset(range.get("begin"))
            // ->range->end->
            val (endOffset, endTokLoc) = getOffset(range.get("end"))
            currentParsingHeaderContent.substring(beginOffset, endOffset + endTokLoc)
        }
    }

    private fun parseLocNode(locNode: JsonNode): FileLoc {
        return FileLoc(
            locNode.get("offset")?.asInt() ?: -1,
            locNode.get("line")?.asInt() ?: -1,
            locNode.get("col")?.asInt() ?: -1,
            locNode.get("tokLen")?.asInt() ?: -1,
        )
    }

    private fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}
