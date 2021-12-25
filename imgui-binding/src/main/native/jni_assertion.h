#pragma once

#include <jni.h>
#include <assert.h>

namespace Jni
{
    void InitAssertion(JNIEnv* env);
    
    JNIEnv* GetAssertEnv();
    
    void SetAssertionCallback(JNIEnv* env, jobject func);
        
    void CallImAssert(JNIEnv* env, jobject func, const char* assertion);
    
    void ImAssertToException(JNIEnv* env, const char* assertion);
    
    bool ImAssertionSet();
}
