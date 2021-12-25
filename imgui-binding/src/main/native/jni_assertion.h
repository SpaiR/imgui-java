#pragma once

#include <jni.h>
#include <assert.h>

namespace Jni
{
    void InitAssertion(JNIEnv* env);
    
    JNIEnv* GetAssertEnv();
    
    void SetAssertionCallback(jobject func);
    
    void ImAssertToCallback(const char* assertion, int line, const char* file);
    
    bool ImAssertionSet();
}
