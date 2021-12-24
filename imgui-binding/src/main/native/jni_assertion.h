#pragma once

#include <jni.h>
#include <assert.h>

namespace Jni
{
    void InitAssertion(JNIEnv* env);
    
    void SetAssertionCallback(jobject func);
        
    void CallImAssert(JNIEnv* env, jobject func, const char* assertion);
    
    void ImAssertToException(const char* assertion);
}
