#include "jni_assertion.h"

namespace Jni
{
    jmethodID jImAssertCallbackMID;
    jobject jImAssertCallbackInstance = NULL;
    
    void InitAssertion(JNIEnv* env) {
         jclass jImAssertCallback = env->FindClass("imgui/callback/ImAssertCallback");
         jImAssertCallbackMID = env->GetMethodID(jImAssertCallback, "imAssert", "(Ljava/lang/String;)V");
    }
    
    void SetAssertionCallback(jobject func) {
        if (jImAssertCallbackInstance != NULL) {
            env->DeleteGlobalRef(jImAssertCallbackInstance);
        }
        if (func != NULL) {
            jImAssertCallbackInstance = env->NewGlobalRef(func);
        } else {
            jImAssertCallbackInstance = NULL;   
        }
    }
        
    void ImAssertToException(const char* assertion) {
        CallImAssert(Jni::GetEnv(), jImAssertCallbackInstance, assertion)
    }
       
    void CallImAssert(JNIEnv* env, jobject func, const char* assertion) {
        if (jImAssertCallbackInstance != null) {
            env->CallVoidMethod(func, jImAssertCallbackMID, env->NewStringUTF(str));
        } else {
               assert(assertion);
        }
    }
}
