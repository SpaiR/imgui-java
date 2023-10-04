package tool.generator.ast

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(AstInfo::class),
    JsonSubTypes.Type(AstNamespaceDecl::class),
    JsonSubTypes.Type(AstFullComment::class),
    JsonSubTypes.Type(AstParagraphComment::class),
    JsonSubTypes.Type(AstTextComment::class),
    JsonSubTypes.Type(AstFunctionDecl::class),
    JsonSubTypes.Type(AstParmVarDecl::class),
    JsonSubTypes.Type(AstEnumDecl::class),
    JsonSubTypes.Type(AstEnumConstantDecl::class),
    JsonSubTypes.Type(AstRecordDecl::class),
    JsonSubTypes.Type(AstFieldDecl::class),
    JsonSubTypes.Type(FileLoc::class),
)
interface Decl

data class AstRoot(
    val info: AstInfo = AstInfo(),
    val decls: List<Decl> = emptyList(),
) : Decl

data class AstInfo(
    val version: String = "",
    val source: String = "",
    val hash: String = "",
    val url: String = "",
    val revision: String = "",
) : Decl

data class AstNamespaceDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val decls: List<Decl> = emptyList()
) : Decl

data class AstFullComment(
    val loc: FileLoc = FileLoc(),
    val decls: List<Decl> = emptyList()
) : Decl

data class AstParagraphComment(
    val loc: FileLoc = FileLoc(),
    val decls: List<Decl> = emptyList()
) : Decl

data class AstTextComment(
    val loc: FileLoc = FileLoc(),
    val text: String = "",
) : Decl

data class AstFunctionDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val resultType: String = "",
    val decls: List<Decl> = emptyList(),
) : Decl {
    @JsonIgnore
    fun getParams(): List<AstParmVarDecl> {
        return decls.filterIsInstance<AstParmVarDecl>()
    }
}

data class AstParmVarDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val qualType: String = "",
    val desugaredQualType: String = "",
    val defaultValue: String? = null,
) : Decl {
    companion object {
        val FORMAT_ATTR: AstParmVarDecl = AstParmVarDecl(
            loc = FileLoc(-1, -1, -1, -1),
            name = "#FORMAT_ATTR_MARKER#",
            qualType = "#FORMAT_ATTR_MARKER#",
            desugaredQualType = "#FORMAT_ATTR_MARKER#",
            defaultValue = null
        )
    }

    @JsonIgnore
    fun isFormatAttr(): Boolean {
        return this == FORMAT_ATTR
    }
}

data class AstEnumDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val decls: List<Decl> = emptyList(),
) : Decl

data class AstEnumConstantDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val qualType: String = "",
    val order: Int = -1,
    val declValue: String? = null,
    val evaluatedValue: String? = null,
) : Decl

data class AstRecordDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val decls: List<Decl> = emptyList(),
) : Decl

data class AstFieldDecl(
    val loc: FileLoc = FileLoc(),
    val name: String = "",
    val qualType: String = "",
    val desugaredQualType: String = "",
) : Decl

data class FileLoc(
    val offset: Int = -1,
    val line: Int = -1,
    val col: Int = -1,
    val tokLen: Int = -1,
) : Decl
