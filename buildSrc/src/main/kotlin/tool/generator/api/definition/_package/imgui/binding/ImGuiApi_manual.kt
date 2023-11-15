package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl
import tool.generator.api.definition.node.transform.method.METHOD_JNI_PREFIX

fun DefineDsl.manualMethods() {
    methods {
        comboMethods()
        listBoxMethods()
        isMousePosValidMethod()
        colorU32Method()
        colorConvert()
    }
    dragNDrop()
//    inputText()
}

private fun MethodsDsl.comboMethods() {
    method {
        signature {
            public()
            name("combo")
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
            }
        }
        body {
            line("return nCombo(label, currentItem.getData(), items, items.length, -1);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            public()
            name("combo")
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
                argInt("popupMaxHeightInItems")
            }
        }
        body {
            line("return nCombo(label, currentItem.getData(), items, items.length, popupMaxHeightInItems);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            private()
            native()
            name("${METHOD_JNI_PREFIX}Combo")
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
                    const char* comboItems[itemsCount];
                    for (int i = 0; i < itemsCount; i++) {
                        jstring string = (jstring)env->GetObjectArrayElement(items, i);
                        const char* rawString = env->GetStringUTFChars(string, JNI_FALSE);
                        comboItems[i] = rawString;
                    }
                    bool flag = ImGui::Combo(label, &currentItem[0], comboItems, itemsCount, popupMaxHeightInItems);
                    for (int i = 0; i< itemsCount; i++) {
                        jstring string = (jstring)env->GetObjectArrayElement(items, i);
                        env->ReleaseStringUTFChars(string, comboItems[i]);
                    }
                    return flag;
                """.trimIndent()
            )
        }
        returnType {
            asBoolean()
        }
    }
}

private fun MethodsDsl.listBoxMethods() {
    method {
        signature {
            public()
            name("listBox")
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
            }
        }
        body {
            line("return nListBox(label, currentItem.getData(), items, items.length, -1);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            public()
            name("listBox")
            args {
                argString("label")
                argInt("currentItem", isPointer = true)
                argString("items", isArray = true)
                argInt("heightInItems")
            }
        }
        body {
            line("return nListBox(label, currentItem.getData(), items, items.length, heightInItems);")
        }
        returnType {
            asBoolean()
        }
    }
    method {
        signature {
            private()
            native()
            name("${METHOD_JNI_PREFIX}ListBox")
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
                    const char* listBoxItems[itemsCount];
                    for (int i = 0; i < itemsCount; i++) {
                        jstring string = (jstring)env->GetObjectArrayElement(items, i);
                        const char* rawString = env->GetStringUTFChars(string, JNI_FALSE);
                        listBoxItems[i] = rawString;
                    }
                    bool flag = ImGui::Combo(label, &currentItem[0], listBoxItems, itemsCount, heightInItems);
                    for (int i = 0; i< itemsCount; i++) {
                        jstring string = (jstring)env->GetObjectArrayElement(items, i);
                        env->ReleaseStringUTFChars(string, listBoxItems[i]);
                    }
                    return flag;
                """.trimIndent()
            )
        }
        returnType {
            asBoolean()
        }
    }
}

private fun MethodsDsl.isMousePosValidMethod() {
    method {
        signature {
            private()
            native()
            name("${METHOD_JNI_PREFIX}IsMousePosValid")
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
    method {
        signature {
            public()
            name("getColorU32i")
            args {
                argInt("col")
            }
        }
        body {
            line("return this.${METHOD_JNI_PREFIX}GetColorU32i(col);")
        }
        returnType {
            asInt()
        }
    }
    method {
        signature {
            private()
            native()
            name("${METHOD_JNI_PREFIX}GetColorU32i")
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
    line("private static java.lang.ref.WeakReference<Object> payloadRef = null;")
    line("private static final byte[] PAYLOAD_PLACEHOLDER_DATA = new byte[1];")

    methods {
        method {
            signature {
                public()
                name("setDragDropPayload")
                args {
                    argString("dataType")
                    argObject("payload")
                }
            }
            body {
                line("return setDragDropPayload(dataType, payload, imgui.flag.ImGuiCond.None);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name("setDragDropPayload")
                args {
                    argString("dataType")
                    argObject("payload")
                    argInt("cond")
                }
            }
            body {
                line("""
                    if (payloadRef == null || payloadRef.get() != payload) {
                        payloadRef = new java.lang.ref.WeakReference<>(payload);
                    }
                    return ${METHOD_JNI_PREFIX}SetDragDropPayload(dataType, PAYLOAD_PLACEHOLDER_DATA, 1, cond);
                """.trimIndent())
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name("setDragDropPayload")
                args {
                    argObject("payload")
                }
            }
            body {
                line("return setDragDropPayload(payload, imgui.flag.ImGuiCond.None);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name("setDragDropPayload")
                args {
                    argObject("payload")
                    argInt("cond")
                }
            }
            body {
                line("return setDragDropPayload(String.valueOf(payload.getClass().hashCode()), payload, cond);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                private()
                native()
                name("${METHOD_JNI_PREFIX}SetDragDropPayload")
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
                name("acceptDragDropPayload")
                args {
                    argString("dataType")
                }
            }
            body {
                line("return acceptDragDropPayload(dataType, imgui.flag.ImGuiDragDropFlags.None);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("acceptDragDropPayload")
                args {
                    argString("dataType")
                    argGenericClass("aClass")
                }
            }
            body {
                line("return acceptDragDropPayload(dataType, imgui.flag.ImGuiDragDropFlags.None, aClass);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("acceptDragDropPayload")
                args {
                    argString("dataType")
                    argInt("flags")
                }
            }
            body {
                line("return acceptDragDropPayload(dataType, flags, null);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("acceptDragDropPayload")
                args {
                    argString("dataType")
                    argInt("flags")
                    argGenericClass("aClass")
                }
            }
            body {
                line("""
                    if (payloadRef != null && ${METHOD_JNI_PREFIX}AcceptDragDropPayload(dataType, flags)) {
                        final Object rawPayload = payloadRef.get();
                        if (rawPayload != null) {
                            if (aClass == null || rawPayload.getClass().isAssignableFrom(aClass)) {
                                return (T) rawPayload;
                            }
                        }
                    }
                    return null;
                """.trimIndent())
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("acceptDragDropPayload")
                args {
                    argGenericClass("aClass")
                }
            }
            body {
                line("return acceptDragDropPayload(String.valueOf(aClass.hashCode()), imgui.flag.ImGuiDragDropFlags.None, aClass);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("acceptDragDropPayload")
                args {
                    argGenericClass("aClass")
                    argInt("flags")
                }
            }
            body {
                line("return acceptDragDropPayload(String.valueOf(aClass.hashCode()), flags, aClass);")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                private()
                native()
                name("${METHOD_JNI_PREFIX}AcceptDragDropPayload")
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
                name("getDragDropPayload")
            }
            body {
                line("""
                    if (payloadRef != null && ${METHOD_JNI_PREFIX}HasDragDropPayload()) {
                        final Object rawPayload = payloadRef.get();
                        if (rawPayload != null) {
                            return (T) rawPayload;
                        }
                    }
                    return null;
                """.trimIndent())
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("getDragDropPayload")
                args {
                    argString("dataType")
                }
            }
            body {
                line("""
                    if (payloadRef != null && ${METHOD_JNI_PREFIX}HasDragDropPayload(dataType)) {
                        final Object rawPayload = payloadRef.get();
                        if (rawPayload != null) {
                            return (T) rawPayload;
                        }
                    }
                    return null;
                """.trimIndent())
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                public()
                name("getDragDropPayload")
                args {
                    argGenericClass("aClass")
                }
            }
            body {
                line("return getDragDropPayload(String.valueOf(aClass.hashCode()));")
            }
            returnType {
                asGenericLiteral()
            }
        }
        method {
            signature {
                private()
                native()
                name("${METHOD_JNI_PREFIX}HasDragDropPayload")
            }
            body {
                line("""
                    const ImGuiPayload* payload = ImGui::GetDragDropPayload();
                    return payload != NULL && payload->Data != NULL;
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
                name("${METHOD_JNI_PREFIX}HasDragDropPayload")
                args {
                    argString("dataType")
                }
            }
            body {
                line("""
                    const ImGuiPayload* payload = ImGui::GetDragDropPayload();
                    return payload != NULL && payload->IsDataType(dataType);
                """.trimIndent())
            }
            returnType {
                asBoolean()
            }
        }
    }
}

fun MethodsDsl.colorConvert() {
    method {
        signature {
            public()
            name("colorConvertRGBtoHSV")
            args {
                argFloat("rgb", isArray = true)
                argFloat("hsv", isArray = true)
            }
        }
        body {
            line("this.${METHOD_JNI_PREFIX}ColorConvertRGBtoHSV(rgb, hsv);")
        }
        returnType {
            asVoid()
        }
    }
    method {
        signature {
            private()
            native()
            name("${METHOD_JNI_PREFIX}ColorConvertRGBtoHSV")
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
            name("colorConvertHSVtoRGB")
            args {
                argFloat("hsv", isArray = true)
                argFloat("rgb", isArray = true)
            }
        }
        body {
            line("this.${METHOD_JNI_PREFIX}ColorConvertHSVtoRGB(hsv, rgb);")
        }
        returnType {
            asVoid()
        }
    }
    method {
        signature {
            private()
            native()
            name("${METHOD_JNI_PREFIX}ColorConvertHSVtoRGB")
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
    jniBlock("""
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
    """.trimIndent())

    methods {
        method {
            signature {
                private()
                native()
                name("${METHOD_JNI_PREFIX}InitInputTextData")
            }
            body {
                line("""
                    jclass jInputDataClass = env->FindClass("imgui/type/ImString${'$'}InputData");
                    inputDataSizeID = env->GetFieldID(jInputDataClass, "size", "I");
                    inputDataIsDirtyID = env->GetFieldID(jInputDataClass, "isDirty", "Z");
                    inputDataIsResizedID = env->GetFieldID(jInputDataClass, "isResized", "Z");
                
                    jclass jImString = env->FindClass("imgui/type/ImString");
                    jImStringResizeInternalMID = env->GetMethodID(jImString, "resizeInternal", "(I)[B");
                
                    jclass jCallback = env->FindClass("imgui/callback/ImGuiInputTextCallback");
                    jInputTextCallbackMID = env->GetMethodID(jCallback, "accept", "(J)V");
                """.trimIndent())
            }
            returnType {
                asVoid()
            }
        }

        method {
            signature {
                public()
                name("inputText")
                args {
                    argString("label")
                    argString("text", isPointer = true)
                }
            }
            body {
                line("return preInputText(false, label, null, text);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name("inputText")
                args {
                    argString("label")
                    argString("text", isPointer = true)
                    argInt("flags")
                }
            }
            body {
                line("return preInputText(false, label, null, text, 0, 0, flags);")
            }
            returnType {
                asBoolean()
            }
        }
        method {
            signature {
                public()
                name("inputText")
                args {
                    argString("label")
                    argString("text", isPointer = true)
                    argInt("flags")
                    argStruct("imgui.callback.ImGuiInputTextCallback", "callback")
                }
            }
            body {
                line("return preInputText(false, label, null, text, 0, 0, flags, callback);")
            }
            returnType {
                asBoolean()
            }
        }
    }
}
