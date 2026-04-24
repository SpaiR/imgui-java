#include "jni_texteditor.h"

static jfieldID cursorPositionLineID;
static jfieldID cursorPositionColumnID;
static jfieldID cursorSelectionStartID;
static jfieldID cursorSelectionEndID;

static void initialize(JNIEnv* env) {
    static bool initialized = false;
    if (initialized) {
        return;
    }

    jclass cursorPositionClass = env->FindClass("imgui/extension/texteditor/TextEditorCursorPosition");
    cursorPositionLineID = env->GetFieldID(cursorPositionClass, "line", "I");
    cursorPositionColumnID = env->GetFieldID(cursorPositionClass, "column", "I");

    jclass cursorSelectionClass = env->FindClass("imgui/extension/texteditor/TextEditorCursorSelection");
    cursorSelectionStartID = env->GetFieldID(cursorSelectionClass, "start", "Limgui/extension/texteditor/TextEditorCursorPosition;");
    cursorSelectionEndID = env->GetFieldID(cursorSelectionClass, "end", "Limgui/extension/texteditor/TextEditorCursorPosition;");

    initialized = true;
}

namespace Jni
{
    void TextEditorCursorPositionCpy(JNIEnv* env, TextEditor::CursorPosition src, jobject dst) {
        initialize(env);
        env->SetIntField(dst, cursorPositionLineID, src.line);
        env->SetIntField(dst, cursorPositionColumnID, src.column);
    }

    void TextEditorCursorSelectionCpy(JNIEnv* env, TextEditor::CursorSelection src, jobject dst) {
        initialize(env);

        jobject start = env->GetObjectField(dst, cursorSelectionStartID);
        jobject end = env->GetObjectField(dst, cursorSelectionEndID);

        TextEditorCursorPositionCpy(env, src.start, start);
        TextEditorCursorPositionCpy(env, src.end, end);

        env->DeleteLocalRef(start);
        env->DeleteLocalRef(end);
    }
}
