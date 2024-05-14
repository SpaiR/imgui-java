#include "jni_texteditor.h"

static jfieldID coordinatesLineID;
static jfieldID coordinatesColumnID;

static void initialize(JNIEnv* env) {
    static bool initialized = false;
    if (initialized) {
        return;
    }

    jclass coordinatesClass = env->FindClass("imgui/extension/texteditor/TextEditorCoordinates");
    coordinatesLineID = env->GetFieldID(coordinatesClass, "mLine", "I");
    coordinatesColumnID = env->GetFieldID(coordinatesClass, "mColumn", "I");

    initialized = true;
}

namespace Jni
{
    void TextEditorCoordinatesCpy(JNIEnv* env, TextEditor::Coordinates* src, jobject dst) {
        initialize(env);
        env->SetIntField(dst, coordinatesLineID, src->mLine);
        env->SetIntField(dst, coordinatesColumnID, src->mColumn);
    }

    void TextEditorCoordinatesCpy(JNIEnv* env, TextEditor::Coordinates src, jobject dst) {
        initialize(env);
        env->SetIntField(dst, coordinatesLineID, src.mLine);
        env->SetIntField(dst, coordinatesColumnID, src.mColumn);
    }

    void TextEditorCoordinatesCpy(JNIEnv* env, jobject src, TextEditor::Coordinates* dst) {
        initialize(env);
        dst->mLine = env->GetIntField(src, coordinatesLineID);
        dst->mColumn = env->GetIntField(src, coordinatesColumnID);
    }
}
