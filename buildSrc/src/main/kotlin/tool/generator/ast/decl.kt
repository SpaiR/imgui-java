package tool.generator.ast

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
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
)
interface Decl {
    val offset: Int
}

interface DeclContainer {
    val decls: List<Decl>
}

data class AstRoot(
    val info: AstInfo = AstInfo(),
    override val decls: List<Decl> = emptyList()
) : DeclContainer

data class AstInfo(
    val version: String = "",
    val source: String = "",
    val hash: String = "",
    val url: String = "",
    val revision: String = "",
)

data class AstNamespaceDecl(
    override val offset: Int = -1,
    val name: String = "",
    override val decls: List<Decl> = emptyList(),
) : Decl, DeclContainer

data class AstFullComment(
    override val offset: Int = -1,
    override val decls: List<Decl> = emptyList(),
) : Decl, DeclContainer

data class AstParagraphComment(
    override val offset: Int = -1,
    override val decls: List<Decl> = emptyList(),
) : Decl, DeclContainer

data class AstTextComment(
    override val offset: Int = -1,
    val text: String = "",
) : Decl

data class AstFunctionDecl(
    override val offset: Int = -1,
    val name: String = "",
    val resultType: String = "",
    override val decls: List<Decl> = emptyList(),
) : Decl, DeclContainer {
    @JsonIgnore
    fun getParams(): List<AstParmVarDecl> {
        return decls.filterIsInstance<AstParmVarDecl>()
    }
}

data class AstParmVarDecl(
    override val offset: Int = -1,
    val name: String = "",
    val qualType: String = "",
    val desugaredQualType: String = "",
    val defaultValue: String? = null,
) : Decl {
    companion object {
        val FORMAT_ATTR: AstParmVarDecl = AstParmVarDecl(
            offset = -1,
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
    override val offset: Int = -1,
    val name: String = "",
    override val decls: List<Decl> = emptyList(),
) : Decl, DeclContainer

data class AstEnumConstantDecl(
    override val offset: Int = -1,
    val name: String = "",
    val qualType: String = "",
    val order: Int = -1,
    val value: String? = null,
    val evaluatedValue: String? = null,
) : Decl

data class AstRecordDecl(
    override val offset: Int = -1,
    val name: String = "",
    override val decls: List<Decl> = emptyList(),
) : Decl, DeclContainer

data class AstFieldDecl(
    override val offset: Int = -1,
    val name: String = "",
    val qualType: String = "",
    val desugaredQualType: String = "",
) : Decl
