package tool.generator.api.definition._package.imgui.binding

import tool.generator.api.definition.dsl.*
import tool.generator.api.definition.dsl.method.MethodsDsl

fun MethodsDsl.manualMethods() {
    comboMethods()
    listBoxMethods()
    isMousePosValidMethod()
    getColorU32Method()
}

private fun MethodsDsl.comboMethods() {
    method {
        signature {
            public()
            name("combo")
            args {
                argString("label")
                argIntPtr("currentItem")
                argStringArr("items")
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
                argIntPtr("currentItem")
                argStringArr("items")
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
            name("nCombo")
            args {
                argString("label")
                argIntArr("currentItem")
                argStringArr("items")
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
                argIntPtr("currentItem")
                argStringArr("items")
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
                argIntPtr("currentItem")
                argStringArr("items")
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
            name("nListBox")
            args {
                argString("label")
                argIntArr("currentItem")
                argStringArr("items")
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
                    bool flag = ImGui::Combo(label, &currentItem[0], listBoxItems, itemsCount, popupMaxHeightInItems);
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
            name("nIsMousePosValid")
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

private fun MethodsDsl.getColorU32Method() {
    method {
        signature {
            public()
            name("getColorU32i")
            args {
                argInt("col")
            }
        }
        body {
            line("return this.nGetColorU32i(col);")
        }
        returnType {
            asInt()
        }
    }
    method {
        signature {
            private()
            native()
            name("nGetColorU32i")
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
