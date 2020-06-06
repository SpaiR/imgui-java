#include "jni_common.h"

jfieldID imVec2XID;
jfieldID imVec2YID;

jfieldID imVec4XID;
jfieldID imVec4YID;
jfieldID imVec4ZID;
jfieldID imVec4WID;

static JavaVM* jvm;

namespace Jni
{
    void InitCommon(JNIEnv* env) {
        env->GetJavaVM(&jvm);

        jclass jImVec2Class = env->FindClass("imgui/ImVec2");
        imVec2XID = env->GetFieldID(jImVec2Class, "x", "F");
        imVec2YID = env->GetFieldID(jImVec2Class, "y", "F");

        jclass jImVec4Class = env->FindClass("imgui/ImVec4");
        imVec4XID = env->GetFieldID(jImVec4Class, "x", "F");
        imVec4YID = env->GetFieldID(jImVec4Class, "y", "F");
        imVec4ZID = env->GetFieldID(jImVec4Class, "z", "F");
        imVec4WID = env->GetFieldID(jImVec4Class, "w", "F");
    }

    JNIEnv* GetEnv() {
        JNIEnv* env;
        jint res = jvm->GetEnv((void**)(&env), JNI_VERSION_1_8);
        assert(res == JNI_OK);
        return env;
    }

    void ImVec2Cpy(JNIEnv* env, ImVec2* src, jobject dst) {
        env->SetFloatField(dst, imVec2XID, src->x);
        env->SetFloatField(dst, imVec2YID, src->y);
    }

    void ImVec2Cpy(JNIEnv* env, ImVec2 src, jobject dst) {
        env->SetFloatField(dst, imVec2XID, src.x);
        env->SetFloatField(dst, imVec2YID, src.y);
    }

    void ImVec2Cpy(JNIEnv* env, jobject src, ImVec2* dst) {
        dst->x = env->GetFloatField(src, imVec2XID);
        dst->y = env->GetFloatField(src, imVec2YID);
    }

    void ImVec2Cpy(JNIEnv* env, jobject src, ImVec2 dst) {
        dst.x = env->GetFloatField(src, imVec2XID);
        dst.y = env->GetFloatField(src, imVec2YID);
    }

    void ImVec4Cpy(JNIEnv* env, ImVec4* src, jobject dst) {
        env->SetFloatField(dst, imVec4XID, src->x);
        env->SetFloatField(dst, imVec4YID, src->y);
        env->SetFloatField(dst, imVec4ZID, src->z);
        env->SetFloatField(dst, imVec4WID, src->w);
    }

    void ImVec4Cpy(JNIEnv* env, ImVec4 src, jobject dst) {
        env->SetFloatField(dst, imVec4XID, src.x);
        env->SetFloatField(dst, imVec4YID, src.y);
        env->SetFloatField(dst, imVec4ZID, src.z);
        env->SetFloatField(dst, imVec4WID, src.w);
    }
}
