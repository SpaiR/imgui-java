package tool.generator.api.definition.node.transform.method

import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.TransformationChain
import tool.generator.api.definition.node.type.method.ArgDefinitionNode
import tool.generator.api.definition.node.type.method.ArgTypeDefinitionNode
import tool.generator.api.definition.node.type.method.MethodDefinitionNode
import tool.generator.api.definition.node.type.method.ext.*
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeVec
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeGeneral
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeVec

/**
 * Transformation handles methods with ImVec2 as return type.
 */
@Suppress("ClassName")
abstract class `abstract handle vec2 return` : TransformationChain.Transform {
    override fun process(nodes: Nodes): Nodes {
        val result = mutableListOf<DefinitionNode>()

        nodes.forEach { node ->
            if (node is MethodDefinitionNode && node.returnType.type == ReturnTypeVec.V2) {
                if (node.signature.isNative) {
                    result += createJniReturnFloatMethod(node, "x")
                    result += createJniReturnFloatMethod(node, "y")
                } else {
                    result += createJvmNewVecMethod(node)
                    result += createJvmDstVecMethod(node)
                    result += createJvmReturnFloatMethod(node, "x")
                    result += createJvmReturnFloatMethod(node, "y")
                }
            } else {
                result += node
            }
        }

        return result
    }

    /**
     * Creates:
     * ```java
     * public ImVec2 method() {
     *     ImVec2 dst = new ImVec2();
     *     method(dst);
     *     return dst;
     * }
     * ```
     */
    private fun createJvmNewVecMethod(node: MethodDefinitionNode): MethodDefinitionNode {
        val method = node.copy()
        method.body.lines = listOf(
            buildString {
                append("final ")
                append(TYPE_IM_VEC2_JVM)
                append(" dst = new ")
                append(TYPE_IM_VEC2_JVM)
                append("();")
            },
            buildString {
                append(THIS_PTR_CALL_JVM)
                append(method.signature.name)
                append("(dst")
                if (method.signature.argsList.isNotEmpty()) {
                    append(", ")
                    append(method.signature.argsList.joinToString { it.name })
                }
                append(");")
            },
            "return dst;",
        )
        return method
    }

    /**
     * Creates:
     * ```java
     * public void method(ImVec2 dst) {
     *     dst.x = methodX();
     *     dst.y = methodY();
     * }
     * ```
     */
    private fun createJvmDstVecMethod(node: MethodDefinitionNode): MethodDefinitionNode {
        val method = node.copy()

        method.body.lines = listOf(
            buildString {
                append("dst.x = ")
                append(THIS_PTR_CALL_JVM)
                append(method.signature.name)
                append("X(")
                append(method.signature.argsList.joinToString { it.name })
                append(");")
            },
            buildString {
                append("dst.y = ")
                append(THIS_PTR_CALL_JVM)
                append(method.signature.name)
                append("Y(")
                append(method.signature.argsList.joinToString { it.name })
                append(");")
            },
        )

        val newArgs = method.signature.argsList.toMutableList()
        newArgs.add(0, ArgDefinitionNode().apply {
            name = "dst"
            addFlag(ArgFlag.FINAL)
            argType = ArgTypeDefinitionNode().apply {
                type = ArgTypeVec.V2
            }
        })

        method.signature.args.args = newArgs
        method.returnType.type = ReturnTypeGeneral.VOID

        return method
    }

    /**
     * Creates:
     * ```java
     * public float methodX() {
     *     return nMethodX();
     * }
     * ```
     * or methodY, depending on the provided arg.
     */
    private fun createJvmReturnFloatMethod(node: MethodDefinitionNode, float: String): MethodDefinitionNode {
        val method = node.copy()
        method.signature.name += float.toUpperCase()
        method.body.lines = listOf(
            buildString {
                append("return ")
                append(method.autoBody.thisPointer)
                append(METHOD_JNI_PREFIX)
                append(method.autoBody.methodName)
                append(float.toUpperCase())
                append('(')
                append(method.autoBody.argsCall.joinToString())
                append(");")
            }
        )
        method.returnType.type = ReturnTypeGeneral.FLOAT
        return method
    }

    /**
     * Creates:
     *
     * ```java
     * public native float nMethodX(); /*
     *     return Method().x;
     * */
     * ```
     *
     * or methodY, depending on the provided arg.
     */
    open fun createJniReturnFloatMethod(node: MethodDefinitionNode, float: String): MethodDefinitionNode {
        val method = node.copy()
        method.signature.name += float.toUpperCase()
        method.body.lines = listOf(
            buildString {
                append("return ")
                append(method.autoBody.thisPointer)
                append(method.autoBody.methodName)
                append('(')
                append(method.autoBody.argsCall.joinToString())
                append(").")
                append(float.toLowerCase())
                append(';')
            }
        )
        method.returnType.type = ReturnTypeGeneral.FLOAT
        return method
    }
}

@Suppress("ClassName")
object `handle vec2 return` : `abstract handle vec2 return`()
