#include "jni_assertion.h"

static JavaVM* assertJvm;

namespace Jni
{
    jmethodID jImAssertCallbackMID;
    jobject jImAssertCallbackInstance = NULL;
    
    void InitAssertion(JNIEnv* env) {
        env->GetJavaVM(&assertJvm);
        
        jclass jImAssertCallback = env->FindClass("imgui/assertion/ImAssertCallback");
        jImAssertCallbackMID = env->GetMethodID(jImAssertCallback, "imAssert", "(Ljava/lang/String;)V");
    }
    
    JNIEnv* GetAssertEnv() {
        JNIEnv* env;
        jint res = assertJvm->GetEnv((void**)(&env), JNI_VERSION_1_8);
        assert(res == JNI_OK);
        return env;
    }
    
    void SetAssertionCallback(JNIEnv* env, jobject func) {
        if (jImAssertCallbackInstance != NULL) {
            env->DeleteGlobalRef(jImAssertCallbackInstance);
        }
        if (func != NULL) {
            jImAssertCallbackInstance = env->NewGlobalRef(func);
        } else {
            jImAssertCallbackInstance = NULL;   
        }
    }
        
    void ImAssertToException(JNIEnv* env, const char* assertion) {
        CallImAssert(env, jImAssertCallbackInstance, assertion);
    }
       
    void CallImAssert(JNIEnv* env, jobject func, const char* assertion) {
        assert(assertion != NULL); //Sanity check for EXCEPTION_ACCESS_VIOLATION
        if (func != NULL) {
            env->CallVoidMethod(func, jImAssertCallbackMID, env->NewStringUTF(assertion));
        }
    }
    
    bool ImAssertionSet() {
        return jImAssertCallbackInstance != NULL;
    }
}
