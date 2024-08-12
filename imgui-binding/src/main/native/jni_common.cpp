#include "jni_common.h"
#include "jni_binding_struct.h"

static jfieldID imVec2XID;
static jfieldID imVec2YID;

static jfieldID imVec4XID;
static jfieldID imVec4YID;
static jfieldID imVec4ZID;
static jfieldID imVec4WID;

namespace Jni
{
    void InitCommon(JNIEnv* env) {
        jclass jImVec2Class = env->FindClass("imgui/ImVec2");
        imVec2XID = env->GetFieldID(jImVec2Class, "x", "F");
        imVec2YID = env->GetFieldID(jImVec2Class, "y", "F");

        jclass jImVec4Class = env->FindClass("imgui/ImVec4");
        imVec4XID = env->GetFieldID(jImVec4Class, "x", "F");
        imVec4YID = env->GetFieldID(jImVec4Class, "y", "F");
        imVec4ZID = env->GetFieldID(jImVec4Class, "z", "F");
        imVec4WID = env->GetFieldID(jImVec4Class, "w", "F");
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

    void ImVec4Cpy(JNIEnv* env, jobject src, ImVec4* dst) {
        dst->x = env->GetFloatField(src, imVec4XID);
        dst->y = env->GetFloatField(src, imVec4YID);
        dst->z = env->GetFloatField(src, imVec4ZID);
        dst->w = env->GetFloatField(src, imVec4WID);
    }

    jobjectArray NewImVec2Array(JNIEnv* env, ImVec2* src, int size) {
        jclass cls = env->FindClass("imgui/ImVec2");
        jmethodID cstr = env->GetMethodID(cls, "<init>", "(FF)V");
        jobjectArray dst = env->NewObjectArray(size, cls, NULL);
        for (int i = 0; i < size; i++) {
            jobject vec = env->NewObject(cls, cstr, src[i].x, src[i].y);
            env->SetObjectArrayElement(dst, i, vec);
            env->DeleteLocalRef(vec);
        }
        return dst;
    }

    jobjectArray NewImVec4Array(JNIEnv* env, ImVec4* src, int size) {
        jclass cls = env->FindClass("imgui/ImVec4");
        jmethodID cstr = env->GetMethodID(cls, "<init>", "(FFFF)V");
        jobjectArray dst = env->NewObjectArray(size, cls, NULL);
        for (int i = 0; i < size; i++) {
            jobject vec = env->NewObject(cls, cstr, src[i].x, src[i].y, src[i].z, src[i].w);
            env->SetObjectArrayElement(dst, i, vec);
            env->DeleteLocalRef(vec);
        }
        return dst;
    }

    void ImVec2ArrayCpy(JNIEnv* env, jobjectArray src, ImVec2* dst, int size) {
        for (int i = 0; i < size; i++) {
            jobject value = env->GetObjectArrayElement(src, i);
            Jni::ImVec2Cpy(env, value, &dst[i]);
            env->DeleteLocalRef(value);
        }
    }

    void ImVec4ArrayCpy(JNIEnv* env, jobjectArray src, ImVec4* dst, int size) {
        for (int i = 0; i < size; i++) {
            jobject value = env->GetObjectArrayElement(src, i);
            Jni::ImVec4Cpy(env, value, &dst[i]);
            env->DeleteLocalRef(value);
        }
    }

    jobjectArray NewImGuiKeyDataArray(JNIEnv* env, const ImGuiKeyData* src, int size) {
        jclass cls = env->FindClass("imgui/ImGuiKeyData");
        jmethodID cstr = env->GetMethodID(cls, "<init>", "(J)V");
        jobjectArray dst = env->NewObjectArray(size, cls, NULL);
        for (int i = 0; i < size; i++) {
            jobject obj = env->NewObject(cls, cstr, (jlong)(intptr_t)&src[i]);
            env->SetObjectArrayElement(dst, i, obj);
            env->DeleteLocalRef(obj);
        }
        return dst;
    }

    void ImGuiKeyDataArrayCpy(JNIEnv* env, jobjectArray src, ImGuiKeyData* dst, int size) {
        for (int i = 0; i < size; i++) {
            jobject value = env->GetObjectArrayElement(src, i);
            jlong ptr = env->GetLongField(value, Jni::GetBindingStructPtrID());
            dst[i] = *reinterpret_cast<ImGuiKeyData*>(ptr);
            env->DeleteLocalRef(value);
        }
    }
}
