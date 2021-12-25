#include "jni_jvm.h"

static JavaVM* jvm;

namespace Jni
{
    void InitJvm(JNIEnv* env) {
        env->GetJavaVM(&jvm);
    }

    JNIEnv* GetEnv() {
        JNIEnv* env;
        jint res = jvm->GetEnv((void**)(&env), JNI_VERSION_1_8);
        assert(res == JNI_OK);
        return env;
    }
}
