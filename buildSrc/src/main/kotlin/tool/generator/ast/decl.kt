package tool.generator.ast

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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
@JsonIgnoreProperties(value = ["offset"])
interface Decl {
    // Offset property is necessary only for maintaining the correct relative order during parsing.
    // We should avoid storing it in a JSON file as it significantly increases the file differences,
    // making version control more challenging.
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
        const val FORMAT_ATTR_NAME = "#FORMAT_ATTR_MARKER#"

        fun asFormatAttr(offset: Int): AstParmVarDecl {
            return AstParmVarDecl(
                offset = offset,
                name = FORMAT_ATTR_NAME,
                qualType = FORMAT_ATTR_NAME,
                desugaredQualType = FORMAT_ATTR_NAME,
                defaultValue = null
            )
        }
    }

    @JsonIgnore
    fun isFormatAttr(): Boolean {
        return this.name == FORMAT_ATTR_NAME
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
    val docComment: String? = null,
    val qualType: String = "",
    val order: Int = -1,
    val value: String? = null,
    val evaluatedValue: Int? = null,
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
