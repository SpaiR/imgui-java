package tool.generator.api.definition.dsl.decl

import tool.generator.api.definition.dsl.define
import tool.generator.api.definition.node.DefinitionNode
import tool.generator.api.definition.node.Nodes
import tool.generator.api.definition.node.transform.method.METHOD_JNI_PREFIX
import tool.generator.api.definition.node.transform.method.THIS_PTR_CALL_JNI
import tool.generator.api.definition.node.transform.method.THIS_PTR_CALL_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_VEC4_JVM
import tool.generator.api.definition.node.transform.template.fieldsTransformationsTemplate
import tool.generator.api.definition.node.type.field.ext.FieldTypeConst
import tool.generator.api.definition.node.type.method.ext.arg.ArgTypeCommon
import tool.generator.api.definition.node.type.method.ext.ret.ReturnTypeCommon
import tool.generator.ast.AstFieldDecl

/**
 * Defines methods/fields for struct fields declarations.
 * Unlike for methods, we need to define (not convert), since some field types require manual methods definitions.
 */
fun defineConvertedFieldDeclsToDsl(fieldDecls: Collection<AstFieldDecl>): Nodes {
    val nodes: MutableList<DefinitionNode> = mutableListOf()

    fieldDecls.forEach { decl ->
        if (decl.desugaredQualType.startsWith("ImVec4[")) {
            nodes += defineImVec4FieldMethods(decl)
        } else {
            nodes += define({
                fieldsTransformationsTemplate()
            }) {
                fields {
                    field {
                        name(decl.name)

                        when (decl.desugaredQualType) {
                            "bool" -> FieldTypeConst.BOOL
                            "short" -> FieldTypeConst.SHORT
                            "int" -> FieldTypeConst.INT
                            "float" -> FieldTypeConst.FLOAT
                            "double" -> FieldTypeConst.DOUBLE
                            "long" -> FieldTypeConst.LONG
                            "const char *" -> FieldTypeConst.STRING
                            "ImVec2" -> FieldTypeConst.VEC2
                            "ImVec4" -> FieldTypeConst.VEC4
                            else -> error("No field type for [$decl]")
                        }.let {
                            type(it)
                        }

                        withGetterAndSetter()
                    }
                }
            }
        }
    }

    return nodes
}

private fun argType(value: String) = object : ArgTypeCommon {
    override fun value() = value
    override fun copy() = this
}

private fun returnType(value: String) = object : ReturnTypeCommon {
    override fun value() = value
    override fun copy() = this
}

private val FloatArray2DArg = argType("float[][]")
private val ImVec4ArrayArg = argType("$TYPE_IM_VEC4_JVM[]")

private val ImVec4ArrayReturn = returnType("$TYPE_IM_VEC4_JVM[]")

/**
 * Defines methods definitions for `ImVec4` field type.
 */
private fun defineImVec4FieldMethods(decl: AstFieldDecl): Collection<DefinitionNode> {
    val arraySize = decl.desugaredQualType.substring(
        decl.desugaredQualType.indexOf('[') + 1,
        decl.desugaredQualType.indexOf(']'),
    ).toInt()

    val buffFieldName = "__gen_${decl.name}_buff"

    val jvmGetMethodName = "get${decl.name.capitalize()}"
    val jvmSetMethodName = "set${decl.name.capitalize()}"
    val jniGetMethodName = "${METHOD_JNI_PREFIX}Get${decl.name.capitalize()}"
    val jniSetMethodName = "${METHOD_JNI_PREFIX}Set${decl.name.capitalize()}"

    return define {
        line("private final float[][] $buffFieldName = new float[$arraySize][4];")

        methods {
            method {
                signature {
                    public()
                    name(jvmGetMethodName)
                }
                body {
                    line("""
                        final $TYPE_IM_VEC4_JVM[] values = new $TYPE_IM_VEC4_JVM[$arraySize];
                        $THIS_PTR_CALL_JVM$jvmGetMethodName(values);
                        return values;
                    """.trimIndent())
                }
                returnType {
                    type(ImVec4ArrayReturn)
                }
            }
            method {
                signature {
                    public()
                    name(jvmGetMethodName)
                    args {
                        arg {
                            final()
                            name("values")
                            type {
                                type(ImVec4ArrayArg)
                            }
                        }
                    }
                }
                body {
                    val extractBuff = "$buffFieldName[idx][0], $buffFieldName[idx][1], $buffFieldName[idx][2], $buffFieldName[idx][3]"
                    line("""
                        if (values.length < $arraySize) {
                            throw new IllegalArgumentException("Invalid array size: required=$arraySize, current=" + values.length);
                        }
                        $THIS_PTR_CALL_JVM$jniGetMethodName($buffFieldName);
                        for (int idx = 0; idx < $buffFieldName.length; idx++) {
                            if (values[idx] == null) {
                                values[idx] = new $TYPE_IM_VEC4_JVM($extractBuff); 
                            } else {
                                values[idx].set($extractBuff);
                            }
                        }
                    """.trimIndent())
                }
                returnType {
                    asVoid()
                }
            }
            method {
                signature {
                    public()
                    name(jvmGetMethodName)
                    args {
                        arg {
                            final()
                            name("values")
                            type {
                                type(FloatArray2DArg)
                            }
                        }
                    }
                }
                body {
                    line("""
                        if (values.length < $arraySize) {
                            throw new IllegalArgumentException("Invalid array size: required=$arraySize, current=" + values.length);
                        }
                        $THIS_PTR_CALL_JVM$jniGetMethodName(values);
                    """.trimIndent())
                }
                returnType {
                    asVoid()
                }
            }
            method {
                signature {
                    private()
                    native()
                    name(jniGetMethodName)
                    args {
                        arg {
                            name("values")
                            type {
                                type(FloatArray2DArg)
                            }
                        }
                    }
                }
                body {
                    line("""
                        for (int idx = 0; idx < $arraySize; idx++) {
                            jfloatArray jValues = (jfloatArray)env->GetObjectArrayElement(values, idx);
                            jfloat* jBuffValues = env->GetFloatArrayElements(jValues, JNI_FALSE);
                            jBuffValues[0] = ${THIS_PTR_CALL_JNI}${decl.name}[idx].x;
                            jBuffValues[1] = ${THIS_PTR_CALL_JNI}${decl.name}[idx].y;
                            jBuffValues[2] = ${THIS_PTR_CALL_JNI}${decl.name}[idx].z;
                            jBuffValues[3] = ${THIS_PTR_CALL_JNI}${decl.name}[idx].w;
                            env->ReleaseFloatArrayElements(jValues, jBuffValues, JNI_FALSE);
                            env->DeleteLocalRef(jValues);
                        }
                    """.trimIndent())
                }
                returnType {
                    asVoid()
                }
            }
            method {
                signature {
                    public()
                    name(jvmSetMethodName)
                    args {
                        arg {
                            final()
                            name("values")
                            type {
                                type(ImVec4ArrayArg)
                            }
                        }
                    }
                }
                body {
                    line("""
                        if (values.length < $arraySize) {
                            throw new IllegalArgumentException("Invalid array size: required=$arraySize, current=" + values.length);
                        }
                        for (int idx = 0; idx < values.length; idx++) {
                            $buffFieldName[idx][0] = values[idx].x;
                            $buffFieldName[idx][1] = values[idx].y;
                            $buffFieldName[idx][2] = values[idx].z;
                            $buffFieldName[idx][3] = values[idx].w;
                        }
                        $THIS_PTR_CALL_JVM$jniSetMethodName($buffFieldName);
                    """.trimIndent())
                }
                returnType {
                    asVoid()
                }
            }
            method {
                signature {
                    public()
                    name(jvmSetMethodName)
                    args {
                        arg {
                            final()
                            name("values")
                            type {
                                type(FloatArray2DArg)
                            }
                        }
                    }
                }
                body {
                    line("""
                        if (values.length < $arraySize) {
                            throw new IllegalArgumentException("Invalid array size: required=$arraySize, current=" + values.length);
                        }
                        $THIS_PTR_CALL_JVM$jniSetMethodName(values);
                    """.trimIndent())
                }
                returnType {
                    asVoid()
                }
            }
            method {
                signature {
                    private()
                    native()
                    name(jniSetMethodName)
                    args {
                        arg {
                            name("values")
                            type {
                                type(FloatArray2DArg)
                            }
                        }
                    }
                }
                body {
                    line("""
                        for (int idx = 0; idx < $arraySize; idx++) {
                            jfloatArray jValues = (jfloatArray)env->GetObjectArrayElement(values, idx);
                            jfloat* jBuffValues = env->GetFloatArrayElements(jValues, JNI_FALSE);
                            ${THIS_PTR_CALL_JNI}${decl.name}[idx] = ImVec4(jBuffValues[0], jBuffValues[1], jBuffValues[2], jBuffValues[3]);
                            env->ReleaseFloatArrayElements(jValues, jBuffValues, JNI_FALSE);
                            env->DeleteLocalRef(jValues);
                        }
                    """.trimIndent())
                }
                returnType {
                    asVoid()
                }
            }
        }
    }
}
