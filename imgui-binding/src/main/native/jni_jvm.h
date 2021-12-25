#pragma once

#include <jni.h>
#include <assert.h>

namespace Jni
{
    void InitJvm(JNIEnv* env);

    JNIEnv* GetEnv();
}
