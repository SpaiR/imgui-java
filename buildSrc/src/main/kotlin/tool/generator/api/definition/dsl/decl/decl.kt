package tool.generator.api.definition.dsl.decl

import tool.generator.api.definition.dsl.method.ArgDsl
import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.dsl.method.ReturnTypeDsl
import tool.generator.api.definition.node.transform.method.CAST_PTR_JNI
import tool.generator.api.definition.node.type.method.ext.jniCast
import tool.generator.ast.AstFunctionDecl
import tool.generator.ast.AstParmVarDecl

fun MethodsDsl.convertFunctionDeclsToDsl(functionDecls: Collection<AstFunctionDecl>) {
    functionDecls.forEach { decl ->
        method {
            signature {
                name(decl.name)
                args {
                    decl.getParams().forEach { param ->
                        arg {
                            declParamToDsl(decl, param, this)
                        }
                    }
                }
            }
            body {
                autoBody()
            }
            returnType {
                declResultToDsl(decl, this)
            }
        }
    }
}

private fun declParamToDsl(decl: AstFunctionDecl, param: AstParmVarDecl, dsl: ArgDsl) {
    dsl.apply {
        if (param.isFormatAttr()) {
            name("# GENERATED NAME #")
            type {
                asNull()
            }
            return
        }

        name(param.name.toCamelCase())

        type {
            when (param.qualType) {
                "ImTextureID" -> {
                    asInt()
                }
            }
            when (param.desugaredQualType) {
                "ImFontAtlas *" -> {
                    asStruct("imgui.ImFontAtlas")
                }

                "ImGuiContext *" -> {
                    asStruct("imgui.internal.ImGuiContext")
                }

                "ImGuiStyle *" -> {
                    asStruct("imgui.ImGuiStyle")
                }

                "ImDrawList *" -> {
                    asStruct("imgui.ImDrawList")
                }

                "ImGuiViewport *", "const ImGuiViewport *" -> {
                    asStruct("imgui.ImGuiViewport")
                }

                "ImFont *" -> {
                    asStruct("imgui.ImFont")
                }

                "ImGuiWindowClass *", "const ImGuiWindowClass *" -> {
                    asStruct("imgui.ImGuiWindowClass")
                }

                "ImGuiStorage *" -> {
                    asStruct("imgui.ImGuiStorage")
                }

                "ImVec2", "const ImVec2 &", "const ImVec2 *" -> {
                    asVec2()
                }

                "ImVec4", "const ImVec4 &", "const ImVec4 *" -> {
                    asVec4()
                }

                "ImU32", "ImGuiID", "ImGuiTableColumnFlags",
                "unsigned int", "int" -> {
                    asInt()
                }

                "const char *" -> {
                    asString()
                }

                "bool *" -> {
                    asBoolean()
                    flagPointer()
                }

                "int *", "unsigned int *" -> {
                    asInt()
                    flagPointer()
                }

                "float *" -> {
                    asFloat()
                    flagPointer()
                }

                "double *" -> {
                    asDouble()
                    flagPointer()
                }

                "bool" -> {
                    asBoolean()
                }

                "float" -> {
                    asFloat()
                }

                "double" -> {
                    asDouble()
                }

                "long", "unsigned long" -> {
                    asLong()
                }
            }

            if (!data.storage.has("type")) {
                error("No arg type found for [${param}] in [$decl]")
            }
        }

        if (param.defaultValue != null) {
            optional()
            defaultJniValue(param.defaultValue)
        }

        when (param.qualType) {
            "ImTextureID" -> {
                data.jniCast = "(ImTextureID)($CAST_PTR_JNI)"
            }
        }
    }
}

private fun declResultToDsl(decl: AstFunctionDecl, dsl: ReturnTypeDsl) {
    dsl.apply {
        when (decl.resultType) {
            "ImGuiContext *" -> {
                asStruct("imgui.internal.ImGuiContext")
            }

            "ImDrawData *" -> {
                asStruct("imgui.ImDrawData")
            }

            "ImDrawList *" -> {
                asStruct("imgui.ImDrawList")
            }

            "ImGuiViewport *" -> {
                asStruct("imgui.ImGuiViewport")
            }

            "ImFont *" -> {
                asStruct("imgui.ImFont")
            }

            "ImGuiStorage *" -> {
                asStruct("imgui.ImGuiStorage")
            }

            "ImGuiIO &" -> {
                asStruct("imgui.ImGuiIO")
                flagRef()
            }

            "ImGuiStyle &" -> {
                asStruct("imgui.ImGuiStyle")
                flagRef()
            }

            "ImGuiPlatformIO &" -> {
                asStruct("imgui.ImGuiPlatformIO")
                flagRef()
            }

            "ImVec2", "const ImVec2 &" -> {
                asVec2()
            }

            "ImVec4", "const ImVec4 &" -> {
                asVec4()
            }

            "ImU32", "ImGuiID", "ImGuiTableColumnFlags", "ImGuiMouseCursor",
            "int" -> {
                asInt()
            }

            "const char *" -> {
                asString()
            }

            "bool" -> {
                asBoolean()
            }

            "float" -> {
                asFloat()
            }

            "double" -> {
                asDouble()
            }

            "void" -> {
                asVoid()
            }
        }

        if (!data.storage.has("type")) {
            error("No result type found for [${decl.resultType}] in [$decl]")
        }
    }
}

private fun String.toCamelCase(): String {
    val words = split("\\s+|_|-".toRegex())
    val camelCaseWords = words.mapIndexed { index, word ->
        if (index == 0) {
            word.toLowerCase()
        } else {
            word.capitalize()
        }
    }
    return camelCaseWords.joinToString("")
}
