#pragma once

#include "jni_jvm.h"
#include "assert.h"

namespace Jni
{
    void InitAssertion(JNIEnv* env);
    
    void SetAssertionCallback(jobject func);
    
    void ImAssertToCallback(const char* assertion, int line, const char* file);
    
    bool ImAssertionSet();
}
