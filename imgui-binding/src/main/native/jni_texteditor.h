#pragma once

#include "jni.h"
#include "TextEditor.h"

namespace Jni
{
    void TextEditorCursorPositionCpy(JNIEnv* env, TextEditor::CursorPosition src, jobject dst);
    void TextEditorCursorSelectionCpy(JNIEnv* env, TextEditor::CursorSelection src, jobject dst);
}
