#include "jni_callbacks.h"

jmethodID jImStrConsumerAcceptMID;
jmethodID jImStrSupplierGetMID;

namespace Jni
{
void InitCallbacks(JNIEnv* env) {
    jclass jImStrConsumer = env->FindClass("imgui/callbacks/ImStrConsumer");
    jImStrConsumerAcceptMID = env->GetMethodID(jImStrConsumer, "accept", "(Ljava/lang/String;)V");

    jclass jImStrSupplier = env->FindClass("imgui/callbacks/ImStrSupplier");
    jImStrSupplierGetMID = env->GetMethodID(jImStrSupplier, "get", "()Ljava/lang/String;");
}

void CallImStrConsumer(JNIEnv* env, jobject consumer, const char* str) {
    env->CallVoidMethod(consumer, jImStrConsumerAcceptMID, env->NewStringUTF(str));
}

jstring CallImStrSupplier(JNIEnv* env, jobject supplier) {
    return (jstring)env->CallObjectMethod(supplier, jImStrSupplierGetMID);
}
}
