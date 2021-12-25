#include "jni_assertion.h"

static JavaVM* assertJvm;

jmethodID jImAssertCallbackMID = NULL;
jobject jImAssertCallbackInstance = NULL;

namespace Jni
{    
    void InitAssertion(JNIEnv* env) {
        env->GetJavaVM(&assertJvm);
        
        jclass jImAssertCallback = env->FindClass("imgui/assertion/ImAssertCallback");
        jImAssertCallbackMID = env->GetMethodID(jImAssertCallback, "imAssert", "(Ljava/lang/String;ILjava/lang/String;)V");
    }
    
    JNIEnv* GetAssertEnv() {
        JNIEnv* env;
        jint res = assertJvm->GetEnv((void**)(&env), JNI_VERSION_1_8);
        assert(res == JNI_OK);
        return env;
    }
    
    void SetAssertionCallback(jobject func) {
        JNIEnv* env = Jni::GetAssertEnv();
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
        JNIEnv* env = Jni::GetAssertEnv();
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
