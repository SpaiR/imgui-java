#include "jni_callbacks.h"

jmethodID jImListClipperCallbackAcceptMID;

jmethodID jImStrConsumerAcceptMID;
jmethodID jImStrSupplierGetMID;

namespace Jni
{
    void InitCallbacks(JNIEnv* env) {
        jclass jImListClipperCallback = env->FindClass("imgui/callbacks/ImListClipperCallback");
        jImListClipperCallbackAcceptMID = env->GetMethodID(jImListClipperCallback, "accept", "(I)V");

        jclass jImStrConsumer = env->FindClass("imgui/callbacks/ImStrConsumer");
        jImStrConsumerAcceptMID = env->GetMethodID(jImStrConsumer, "accept", "(Ljava/lang/String;)V");

        jclass jImStrSupplier = env->FindClass("imgui/callbacks/ImStrSupplier");
        jImStrSupplierGetMID = env->GetMethodID(jImStrSupplier, "get", "()Ljava/lang/String;");
    }

    void CallImListClipperCallback(JNIEnv* env, jobject consumer, int index) {
        env->CallVoidMethod(consumer, jImListClipperCallbackAcceptMID, index);
    }

    void CallImStrConsumer(JNIEnv* env, jobject consumer, const char* str) {
        env->CallVoidMethod(consumer, jImStrConsumerAcceptMID, env->NewStringUTF(str));
    }

    jstring CallImStrSupplier(JNIEnv* env, jobject supplier) {
        return (jstring)env->CallObjectMethod(supplier, jImStrSupplierGetMID);
    }
}
