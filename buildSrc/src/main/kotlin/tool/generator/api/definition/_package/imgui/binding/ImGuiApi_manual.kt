package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.ArgsDsl
import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.node.transform.method.METHOD_JNI_PREFIX
import tool.generator.api.definition.node.transform.method.THIS_PTR_CALL_JVM
import tool.generator.api.definition.node.transform.method.TYPE_IM_BOOL

fun DefineDsl.manualMethods() {
    methods {
        checkboxMethod()
        comboMethods()
        listBoxMethods()
        isMousePosValidMethod()
        colorU32Method()
        colorConvert()
    }
    dragNDrop()
    inputText()
}

private fun MethodsDsl.checkboxMethod() {
    method {
        signature {
            public()
            name("checkbox")
            args {
                argString("label")
                argBoolean("v")
            }
        }
        body {
            line("return ${THIS_PTR_CALL_JVM}checkbox(label, new $TYPE_IM_BOOL(v));")
        }
        returnType {
            asBoolean()
        }
    }
}

private fun MethodsDsl.comboMethods() {
    val methodName = "combo"
    val nMethodName = "${METHOD_JNI_PREFIX}Combo"

    method {
        signature {
            public()
            name(methodName)
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
            }
        }
        body {
            // Will be generated in other place.
            line("return $THIS_PTR_CALL_JVM$methodName(label, currentItem, items, items.length);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            public()
            name(methodName)
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
                argInt("popupMaxHeightInItems")
            }
        }
        body {
            line("return $THIS_PTR_CALL_JVM$nMethodName(label, currentItem.getData(), items, items.length, popupMaxHeightInItems);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            private()
            native()
            name(nMethodName)
            args {
                argString("label")
                argInt("currentItem", isArray = true)
                argString("items", isArray = true)
                argInt("itemsCount")
                argInt("popupMaxHeightInItems")
            }
        }
        body {
            line(
                """
                |${nTmplStringsToCharsGet("itemsChars", "items", "itemsCount")}
                |bool flag = ImGui::Combo(label, &currentItem[0], itemsChars, itemsCount, popupMaxHeightInItems);
                |${nTmplStringsToCharsRelease("itemsChars", "items", "itemsCount")}
                |return flag;
                """.trimMargin()
            )
        }
        returnType {
            asBoolean()
        }
    }
}

private fun MethodsDsl.listBoxMethods() {
    val methodName = "listBox"
    val nMethodName = "${METHOD_JNI_PREFIX}ListBox"

    method {
        signature {
            public()
            name(methodName)
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
            }
        }
        body {
            // Will be generated in other place.
            line("return $THIS_PTR_CALL_JVM$methodName(label, currentItem, items, items.length);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            public()
            name(methodName)
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
                argInt("heightInItems")
            }
        }
        body {
            line("return $THIS_PTR_CALL_JVM$nMethodName(label, currentItem.getData(), items, items.length, heightInItems);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            private()
            native()
            name(nMethodName)
            args {
                argString("label")
                argInt("currentItem", isArray = true)
                argString("items", isArray = true)
                argInt("itemsCount")
                argInt("heightInItems")
            }
        }
        body {
            line(
                """
                |${nTmplStringsToCharsGet("itemsChars", "items", "itemsCount")}
                |bool flag = ImGui::ListBox(label, &currentItem[0], itemsChars, itemsCount, heightInItems);
                |${nTmplStringsToCharsRelease("itemsChars", "items", "itemsCount")}
                |return flag;
                """.trimMargin()
            )
        }
        returnType {
            asBoolean()
        }
    }
}

private fun nTmplStringsToCharsGet(cArrName: String, sArrName: String, countName: String): String {
    return """
        const char* $cArrName[$countName];
        for (int i = 0; i < $countName; i++) {
            jstring str = (jstring)env->GetObjectArrayElement($sArrName, i);
            const char* rawStr = env->GetStringUTFChars(str, JNI_FALSE);
            $cArrName[i] = rawStr;
        }
    """.trimIndent()
}

private fun nTmplStringsToCharsRelease(cArrName: String, sArrName: String, countName: String): String {
    return """
        for (int i = 0; i< $countName; i++) {
            jstring str = (jstring)env->GetObjectArrayElement($sArrName, i);
            env->ReleaseStringUTFChars(str, $cArrName[i]);
        }
    """.trimIndent()
}

private fun MethodsDsl.isMousePosValidMethod() {
    val nMethodName = "${METHOD_JNI_PREFIX}IsMousePosValid"

    method {
        signature {
            private()
            native()
            name(nMethodName)
            args {
                argFloat("mousePosX")
                argFloat("mousePosY")
            }
        }
        body {
            line(
                """
                    ImVec2 pos = ImVec2(mousePosX, mousePosY);
                    return ImGui::IsMousePosValid(&pos);
                """.trimIndent()
            )
        }
        returnType {
            asBoolean()
        }
    }
}

private fun MethodsDsl.colorU32Method() {
    val methodName = "getColorU32i"
    val nMethodName = "${METHOD_JNI_PREFIX}GetColorU32i"

    method {
        signature {
            public()
            name(methodName)
            args {
                argInt("col")
            }
        }
        body {
            line("return $THIS_PTR_CALL_JVM$nMethodName(col);")
        }
        returnType {
            asInt()
        }
    }
    method {
        signature {
            private()
            native()
            name(nMethodName)
            args {
                argInt("col")
            }
        }
        body {
            line("return ImGui::GetColorU32((ImU32)col);")
        }
        returnType {
            asInt()
        }
    }
}

private fun DefineDsl.dragNDrop() {
    val methodNameSet = "setDragDropPayload"
    val nMethodNameSet = "${METHOD_JNI_PREFIX}SetDragDropPayload"
    val methodNameAccept = "acceptDragDropPayload"
    val nMethodNameAccept = "${METHOD_JNI_PREFIX}AcceptDragDropPayload"
    val methodNameGet = "getDragDropPayload"
    val nMethodNameHas = "${METHOD_JNI_PREFIX}HasDragDropPayload"

    line("private static java.lang.ref.WeakReference<Object> payloadRef = null;")
    line("private static final byte[] PAYLOAD_PLACEHOLDER_DATA = new byte[1];")

    methods {
        method {
            signature {
                public()
                name(methodNameSet)
                args {
                    argString("dataType")
                    argObject("payload")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameSet(dataType, payload, imgui.flag.ImGuiCond.None);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name(methodNameSet)
                args {
                    argString("dataType")
                    argObject("payload")
                    argInt("cond")
                }
            }
            body {
                line(
                    """
                    if (payloadRef == null || payloadRef.get() != payload) {
                        payloadRef = new java.lang.ref.WeakReference<>(payload);
                    }
                    return $THIS_PTR_CALL_JVM$nMethodNameSet(dataType, PAYLOAD_PLACEHOLDER_DATA, 1, cond);
                """.trimIndent()
                )
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name(methodNameSet)
                args {
                    argObject("payload")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameSet(payload, imgui.flag.ImGuiCond.None);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name(methodNameSet)
                args {
                    argObject("payload")
                    argInt("cond")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameSet(String.valueOf(payload.getClass().hashCode()), payload, cond);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                private()
                native()
                name(nMethodNameSet)
                args {
                    argString("dataType")
                    argByte("data", isArray = true)
                    argInt("sz")
                    argInt("cond")
                }
            }
            body {
                line("return ImGui::SetDragDropPayload(dataType, &data[0], sz, cond);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name(methodNameAccept)
                args {
                    argString("dataType")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameAccept(dataType, imgui.flag.ImGuiDragDropFlags.None);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameAccept)
                args {
                    argString("dataType")
                    argGenericClass("aClass")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameAccept(dataType, imgui.flag.ImGuiDragDropFlags.None, aClass);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameAccept)
                args {
                    argString("dataType")
                    argInt("flags")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameAccept(dataType, flags, null);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameAccept)
                args {
                    argString("dataType")
                    argInt("flags")
                    argGenericClass("aClass")
                }
            }
            body {
                line(
                    """
                    if (payloadRef != null && $THIS_PTR_CALL_JVM$nMethodNameAccept(dataType, flags)) {
                        final Object rawPayload = payloadRef.get();
                        if (rawPayload != null) {
                            if (aClass == null || rawPayload.getClass().isAssignableFrom(aClass)) {
                                return (T) rawPayload;
                            }
                        }
                    }
                    return null;
                """.trimIndent()
                )
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameAccept)
                args {
                    argGenericClass("aClass")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameAccept(String.valueOf(aClass.hashCode()), imgui.flag.ImGuiDragDropFlags.None, aClass);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameAccept)
                args {
                    argGenericClass("aClass")
                    argInt("flags")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameAccept(String.valueOf(aClass.hashCode()), flags, aClass);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                private()
                native()
                name(nMethodNameAccept)
                args {
                    argString("dataType")
                    argInt("flags")
                }
            }
            body {
                line("return ImGui::AcceptDragDropPayload(dataType, flags) != NULL;")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name(methodNameGet)
            }
            body {
                line(
                    """
                    if (payloadRef != null && $THIS_PTR_CALL_JVM$nMethodNameHas()) {
                        final Object rawPayload = payloadRef.get();
                        if (rawPayload != null) {
                            return (T) rawPayload;
                        }
                    }
                    return null;
                """.trimIndent()
                )
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameGet)
                args {
                    argString("dataType")
                }
            }
            body {
                line(
                    """
                    if (payloadRef != null && $THIS_PTR_CALL_JVM$nMethodNameHas(dataType)) {
                        final Object rawPayload = payloadRef.get();
                        if (rawPayload != null) {
                            return (T) rawPayload;
                        }
                    }
                    return null;
                """.trimIndent()
                )
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name(methodNameGet)
                args {
                    argGenericClass("aClass")
                }
            }
            body {
                line("return $THIS_PTR_CALL_JVM$methodNameGet(String.valueOf(aClass.hashCode()));")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                private()
                native()
                name(nMethodNameHas)
            }
            body {
                line(
                    """
                    const ImGuiPayload* payload = ImGui::GetDragDropPayload();
                    return payload != NULL && payload->Data != NULL;
                """.trimIndent()
                )
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                private()
                native()
                name(nMethodNameHas)
                args {
                    argString("dataType")
                }
            }
            body {
                line(
                    """
                    const ImGuiPayload* payload = ImGui::GetDragDropPayload();
                    return payload != NULL && payload->IsDataType(dataType);
                """.trimIndent()
                )
            }
            returnType {
                asBoolean()
            }
        }
    }
}

fun MethodsDsl.colorConvert() {
    val methodNameRGB2HSV = "colorConvertRGBtoHSV"
    val nMethodNameRGB2HSV = "${METHOD_JNI_PREFIX}ColorConvertRGBtoHSV"
    val methodNameHSV2RGB = "colorConvertHSVtoRGB"
    val nMethodNameHSV2RGB = "${METHOD_JNI_PREFIX}ColorConvertHSVtoRGB"

    method {
        signature {
            public()
            name(methodNameRGB2HSV)
            args {
                argFloat("rgb", isArray = true)
                argFloat("hsv", isArray = true)
            }
        }
        body {
            line("$THIS_PTR_CALL_JVM$nMethodNameRGB2HSV(rgb, hsv);")
        }
        returnType {
            asVoid()
        }
    }
    method {
        signature {
            private()
            native()
            name(nMethodNameRGB2HSV)
            args {
                argFloat("rgb", isArray = true)
                argFloat("hsv", isArray = true)
            }
        }
        body {
            line("ImGui::ColorConvertRGBtoHSV(rgb[0], rgb[1], rgb[2], hsv[0], hsv[1], hsv[2]);")
        }
        returnType {
            asVoid()
        }
    }
    method {
        signature {
            public()
            name(methodNameHSV2RGB)
            args {
                argFloat("hsv", isArray = true)
                argFloat("rgb", isArray = true)
            }
        }
        body {
            line("$THIS_PTR_CALL_JVM$nMethodNameHSV2RGB(hsv, rgb);")
        }
        returnType {
            asVoid()
        }
    }
    method {
        signature {
            private()
            native()
            name(nMethodNameHSV2RGB)
            args {
                argFloat("hsv", isArray = true)
                argFloat("rgb", isArray = true)
            }
        }
        body {
            line("ImGui::ColorConvertHSVtoRGB(hsv[0], hsv[1], hsv[2], rgb[0], rgb[1], rgb[2]);")
        }
        returnType {
            asVoid()
        }
    }
}

private fun DefineDsl.inputText() {
    jniBlock(
        """
        jmethodID jImStringResizeInternalMID;
        jmethodID jInputTextCallbackMID;

        jfieldID inputDataSizeID;
        jfieldID inputDataIsDirtyID;
        jfieldID inputDataIsResizedID;

        struct InputTextCallbackUserData {
            JNIEnv* env;
            jobject* imString;
            int maxSize;
            jbyteArray jResizedBuf;
            char* resizedBuf;
            jobject* textInputData;
            char* allowedChars;
            jobject* handler;
        };

        static int TextEditCallbackStub(ImGuiInputTextCallbackData* data) {
            InputTextCallbackUserData* userData = (InputTextCallbackUserData*)data->UserData;

            if (data->EventFlag == ImGuiInputTextFlags_CallbackCharFilter) {
                int allowedCharLength = strlen(userData->allowedChars);
                if(allowedCharLength > 0) {
                    bool found = false;
                    for(int i = 0; i < allowedCharLength; i++) {
                        if(userData->allowedChars[i] == data->EventChar) {
                            found = true;
                            break;
                        }
                    }
                    return found ? 0 : 1;
                }
            } else if (data->EventFlag == ImGuiInputTextFlags_CallbackResize) {
                int newSize = data->BufTextLen;
                if (newSize >= userData->maxSize) {
                    JNIEnv* env = userData->env;

                    jbyteArray newBufArr = (jbyteArray)env->CallObjectMethod(*userData->imString, jImStringResizeInternalMID, newSize);
                    char* newBuf = (char*)env->GetPrimitiveArrayCritical(newBufArr, 0);

                    data->Buf = newBuf;

                    userData->jResizedBuf = newBufArr;
                    userData->resizedBuf = newBuf;
                }
            }

            if (userData->handler != NULL) {
                JNIEnv* env = userData->env;
                env->CallObjectMethod(*userData->handler, jInputTextCallbackMID, data);
            }

            return 0;
        }
    """.trimIndent()
    )

    line("""
        {
            ${THIS_PTR_CALL_JVM}${METHOD_JNI_PREFIX}InitInputTextData();
        }
    """.trimIndent())

    methods {
        method {
            signature {
                private()
                native()
                name("${METHOD_JNI_PREFIX}InitInputTextData")
            }
            body {
                line(
                    """
                    jclass jInputDataClass = env->FindClass("imgui/type/ImString${'$'}InputData");
                    inputDataSizeID = env->GetFieldID(jInputDataClass, "size", "I");
                    inputDataIsDirtyID = env->GetFieldID(jInputDataClass, "isDirty", "Z");
                    inputDataIsResizedID = env->GetFieldID(jInputDataClass, "isResized", "Z");
                
                    jclass jImString = env->FindClass("imgui/type/ImString");
                    jImStringResizeInternalMID = env->GetMethodID(jImString, "resizeInternal", "(I)[B");
                
                    jclass jCallback = env->FindClass("imgui/callback/ImGuiInputTextCallback");
                    jInputTextCallbackMID = env->GetMethodID(jCallback, "accept", "(J)V");
                """.trimIndent()
                )
            }
        }

        val methodNamePreInputText = "preInputText"
        val nMethodName = "${METHOD_JNI_PREFIX}InputText"

        data class MethodTmpl(
            val name: String,
            val args: List<ArgsDsl.() -> Unit>,
            val argsCall: List<String>,
        )

        fun preInputTextByTemplate(template: MethodTmpl, argsIndexes: IntArray, argsCallIndex: Int) {
            method {
                signature {
                    public()
                    name(template.name)
                    args {
                        argsIndexes.forEach { index ->
                            template.args[index]()
                        }
                    }
                }
                body {
                    line("return $THIS_PTR_CALL_JVM$methodNamePreInputText(${template.argsCall[argsCallIndex]});")
                }
                returnType {
                    asBoolean()
                }
            }
        }

        val inputTextTemplate = MethodTmpl(
            "inputText",
            listOf(
                { argString("label") },
                { argString("text", isPointer = true) },
                { argInt("flags") },
                { argStruct("imgui.callback.ImGuiInputTextCallback", "callback") },
            ),
            listOf(
                "false, label, null, text, 0, 0, imgui.flag.ImGuiInputTextFlags.None, null",
                "false, label, null, text, 0, 0, flags, null",
                "false, label, null, text, 0, 0, flags, callback",
            )
        )

        preInputTextByTemplate(inputTextTemplate, intArrayOf(0, 1), 0)
        preInputTextByTemplate(inputTextTemplate, intArrayOf(0, 1, 2), 1)
        preInputTextByTemplate(inputTextTemplate, intArrayOf(0, 1, 2, 3), 2)

        val inputTextMultilineTemplate = MethodTmpl(
            "inputTextMultiline",
            listOf(
                { argString("label") },
                { argString("text", isPointer = true) },
                { argFloat("width") },
                { argFloat("height") },
                { argInt("flags") },
                { argStruct("imgui.callback.ImGuiInputTextCallback", "callback") },
            ),
            listOf(
                "true, label, null, text, 0, 0, imgui.flag.ImGuiInputTextFlags.None, null",
                "true, label, null, text, width, height, imgui.flag.ImGuiInputTextFlags.None, null",
                "true, label, null, text, width, height, flags, null",
                "true, label, null, text, width, height, flags, callback",

                "true, label, null, text, 0, 0, flags, null",
                "true, label, null, text, 0, 0, flags, callback",
            )
        )

        preInputTextByTemplate(inputTextMultilineTemplate, intArrayOf(0, 1), 0)
        preInputTextByTemplate(inputTextMultilineTemplate, intArrayOf(0, 1, 2, 3), 1)
        preInputTextByTemplate(inputTextMultilineTemplate, intArrayOf(0, 1, 2, 3, 4), 2)
        preInputTextByTemplate(inputTextMultilineTemplate, intArrayOf(0, 1, 2, 3, 4, 5), 3)
        preInputTextByTemplate(inputTextMultilineTemplate, intArrayOf(0, 1, 4), 4)
        preInputTextByTemplate(inputTextMultilineTemplate, intArrayOf(0, 1, 4, 5), 5)

        method {
            signature {
                private()
                name(methodNamePreInputText)
                args {
                    argBoolean("multiline")
                    argString("label")
                    argString("hint")
                    argString("text", isPointer = true)
                    argFloat("width")
                    argFloat("height")
                    argInt("flags")
                    argStruct("imgui.callback.ImGuiInputTextCallback", "callback")
                }
            }
            body {
                line("""
                    final imgui.type.ImString.InputData inputData = text.inputData;
                    int inputFlags = flags;
                    
                    if (inputData.isResizable) {
                        inputFlags |= imgui.flag.ImGuiInputTextFlags.CallbackResize;
                    }
            
                    if (!inputData.allowedChars.isEmpty()) {
                        inputFlags |= imgui.flag.ImGuiInputTextFlags.CallbackCharFilter;
                    }
            
                    String hintLabel = hint;
                    if (hintLabel == null) {
                        hintLabel = "";
                    }
                    
                    return $THIS_PTR_CALL_JVM$nMethodName(
                        multiline,
                        hint != null,
                        label,
                        hintLabel,
                        text,
                        text.getData(),
                        text.getData().length,
                        width,
                        height,
                        inputFlags,
                        inputData,
                        inputData.allowedChars,
                        callback
                    );
                """.trimIndent())
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                private()
                native()
                name(nMethodName)
                args {
                    argBoolean("multiline")
                    argBoolean("hint")
                    argString("label")
                    argString("hintLabel")
                    argString("imString", isPointer = true)
                    argByte("buf", isArray = true)
                    argInt("maxSize")
                    argFloat("width")
                    argFloat("height")
                    argInt("flags")
                    argObject("imgui.type.ImString.InputData", "textInputData")
                    argString("allowedChars")
                    argObject("imgui.callback.ImGuiInputTextCallback", "callback")
                }
            }
            body {
                line("""
                    InputTextCallbackUserData userData;
                    userData.imString = &imString;
                    userData.maxSize = maxSize;
                    userData.jResizedBuf = NULL;
                    userData.resizedBuf = NULL;
                    userData.textInputData = &textInputData;
                    userData.env = env;
                    userData.allowedChars = allowedChars;
                    userData.handler = callback != NULL ? &callback : NULL;
            
                    bool valueChanged;
            
                    if (multiline) {
                        valueChanged = ImGui::InputTextMultiline(label, buf, maxSize, ImVec2(width, height), flags, &TextEditCallbackStub, &userData);
                    } else if (hint) {
                        valueChanged = ImGui::InputTextWithHint(label, hintLabel, buf, maxSize, flags, &TextEditCallbackStub, &userData);
                    } else {
                        valueChanged = ImGui::InputText(label, buf, maxSize, flags, &TextEditCallbackStub, &userData);
                    }
            
                    if (valueChanged) {
                        int size;
            
                        if (userData.jResizedBuf != NULL) {
                            size = strlen(userData.resizedBuf);
                            env->ReleasePrimitiveArrayCritical(userData.jResizedBuf, userData.resizedBuf, 0);
                        } else {
                            size = strlen(buf);
                        }
            
                        env->SetIntField(textInputData, inputDataSizeID, size);
                        env->SetBooleanField(textInputData, inputDataIsDirtyID, true);
                    }
            
                    return valueChanged;
                """.trimIndent())
            }
            returnType {
                asBoolean()
            }
        }
    }
}
