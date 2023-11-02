package tool.generator.api.definition.dsl.decl

import tool.generator.api.definition.node.MethodSignature
import tool.generator.ast.AstFunctionDecl
import tool.generator.ast.AstParmVarDecl

fun containsMethodName(methodNames: Collection<String>): (AstFunctionDecl) -> Boolean {
    return {
        methodNames.contains(it.name)
    }
}

fun containsMethodSignature(methodsWithArgs: Collection<MethodSignature>): (AstFunctionDecl) -> Boolean {
    return {
        methodsWithArgs.contains(
            MethodSignature(it.name, it.getParams().map(AstParmVarDecl::qualType).toSet())
        )
    }
}
