#pragma once

#include "jni.h"
#include "TextEditor.h"

namespace Jni
{
    void TextEditorCoordinatesCpy(JNIEnv* env, TextEditor::Coordinates* src, jobject dst);
    void TextEditorCoordinatesCpy(JNIEnv* env, TextEditor::Coordinates src, jobject dst);
    void TextEditorCoordinatesCpy(JNIEnv* env, jobject src, TextEditor::Coordinates* dst);
}
