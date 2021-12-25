#include "jni_assertion.h"

namespace Jni
{
    jmethodID jImAssertCallbackMID;
    jobject jImAssertCallbackInstance = NULL;
    JNIEnv* globalEnv;
    
    void InitAssertion(JNIEnv* env) {
        globalEnv = env;
        jclass jImAssertCallback = env->FindClass("imgui/assertion/ImAssertCallback");
        jImAssertCallbackMID = env->GetMethodID(jImAssertCallback, "imAssert", "(Ljava/lang/String;)V");
    }
    
    void SetAssertionCallback(jobject func) {
        if (jImAssertCallbackInstance != NULL) {
            globalEnv->DeleteGlobalRef(jImAssertCallbackInstance);
        }
        if (func != NULL) {
            jImAssertCallbackInstance = globalEnv->NewGlobalRef(func);
        } else {
            jImAssertCallbackInstance = NULL;   
        }
    }
        
    void ImAssertToException(const char* assertion) {
        CallImAssert(globalEnv, jImAssertCallbackInstance, assertion);
    }
       
    void CallImAssert(JNIEnv* env, jobject func, const char* assertion) {
        if (jImAssertCallbackInstance != NULL) {
            env->CallVoidMethod(func, jImAssertCallbackMID, env->NewStringUTF(assertion));
        } else {
               assert(assertion);
        }
    }
}
