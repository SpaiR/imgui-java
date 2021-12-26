#include "jni_assertion.h"

jmethodID jImAssertCallbackMID = NULL;
jobject jImAssertCallbackInstance = NULL;

namespace Jni
{    
    void InitAssertion(JNIEnv* env) {      
        jclass jImAssertCallback = env->FindClass("imgui/assertion/ImAssertCallback");
        jImAssertCallbackMID = env->GetMethodID(jImAssertCallback, "imAssert", "(Ljava/lang/String;ILjava/lang/String;)V");
    }
    
    void SetAssertionCallback(jobject func) {
        JNIEnv* env = Jni::GetEnv();
        if (jImAssertCallbackInstance != NULL) {
            env->DeleteGlobalRef(jImAssertCallbackInstance);
        }
        if (func != NULL) {
            jImAssertCallbackInstance = env->NewGlobalRef(func);
        } else {
            jImAssertCallbackInstance = NULL;   
        }
    }
        
    void ImAssertToCallback(const char* assertion, int line, const char* file) {
        JNIEnv* env = Jni::GetEnv();
        assert(jImAssertCallbackMID != NULL);
        assert(assertion != NULL);
        if (jImAssertCallbackInstance != NULL) {
            env->CallVoidMethod(jImAssertCallbackInstance, jImAssertCallbackMID, env->NewStringUTF(assertion), line, env->NewStringUTF(file));
        }
    }
    
    bool ImAssertionSet() {
        return jImAssertCallbackInstance != NULL;
    }
}
