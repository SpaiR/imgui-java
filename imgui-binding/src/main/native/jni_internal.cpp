#include "jni_internal.h"

static jfieldID imRectMin;
static jfieldID imRectMax;

namespace Jni
{
    void InitInternal(JNIEnv* env) {
        jclass jImRectClass = env->FindClass("imgui/internal/ImRect");
        imRectMin = env->GetFieldID(jImRectClass, "min", "Limgui/ImVec2;");
        imRectMax = env->GetFieldID(jImRectClass, "max", "Limgui/ImVec2;");
    }

    void ImRectCpy(JNIEnv* env, ImRect* src, jobject dst) {
        jobject min = env->GetObjectField(dst, imRectMin);
        jobject max = env->GetObjectField(dst, imRectMax);
        Jni::ImVec2Cpy(env, src->Min, min);
        Jni::ImVec2Cpy(env, src->Max, max);
    }

    void ImRectCpy(JNIEnv* env, ImRect src, jobject dst) {
        jobject min = env->GetObjectField(dst, imRectMin);
        jobject max = env->GetObjectField(dst, imRectMax);
        Jni::ImVec2Cpy(env, src.Min, min);
        Jni::ImVec2Cpy(env, src.Max, max);
    }

    void ImRectCpy(JNIEnv* env, jobject src, ImRect* dst) {
        jobject min = env->GetObjectField(src, imRectMin);
        jobject max = env->GetObjectField(src, imRectMax);
        Jni::ImVec2Cpy(env, min, &dst->Min);
        Jni::ImVec2Cpy(env, max, &dst->Max);
    }
}
