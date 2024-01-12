package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.Definition
import tool.generator.api.definition.DisableDefinition
import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.decl.convertMethodDeclsToDsl
import tool.generator.api.definition.dsl.decl.defineConvertedFieldDeclsToDsl
import tool.generator.api.definition.node.transform.method.`sort methods`
import tool.generator.api.definition.node.transform.template.methodsTransformationsTemplate
import tool.generator.ast.*

@DisableDefinition
class ImGuiStyle : Definition {
    companion object {
        private const val CLASS_NAME = "ImGuiStyle"
    }

    private fun getAstStructDecls(): List<Decl> {
        return parseAstResource("ast-imgui.json").decls
            .filterIsInstance<AstRecordDecl>()
            .first { it.name == CLASS_NAME }.decls
    }

    private fun getAstFields(): Collection<AstFieldDecl> {
        return getAstStructDecls()
            .filterIsInstance<AstFieldDecl>()
    }

    private fun getAstFunctions(): Collection<AstFunctionDecl> {
        return getAstStructDecls()
            .filterIsInstance<AstFunctionDecl>()
    }

    override fun getNodes() = defines(
        define {
            jniIncludeCommon()
            imGuiInitStructDestroyable(CLASS_NAME)
            jniDefineThis(CLASS_NAME)
        },
        define({
            transformation {
                chain(`sort methods`())
            }
        }, defines(
            defineConvertedFieldDeclsToDsl(getAstFields()),
            define({
                methodsTransformationsTemplate()
            }) {
                methods {
                    convertMethodDeclsToDsl(getAstFunctions())
                }
            },
        )
        ),
        define {
            jniUndefThis()
        }
    )
}
