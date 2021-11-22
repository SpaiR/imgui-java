#include "jni_binding_struct.h"

jfieldID imGuiStructPtrID;

namespace Jni
{
    void InitBindingStruct(JNIEnv* env) {
        jclass jImGuiStructClass = env->FindClass("io/github/spair/imgui/binding/ImGuiStruct");
        imGuiStructPtrID = env->GetFieldID(jImGuiStructClass, "ptr", "J");
    }

    jfieldID GetBindingStructPtrID() {
        return imGuiStructPtrID;
    }
}
