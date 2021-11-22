#pragma once

#include <jni.h>

#ifndef STRUCT_PTR
#define STRUCT_PTR env->GetLongField(object, Jni::GetBindingStructPtrID())
#endif

namespace Jni
{
    void InitBindingStruct(JNIEnv* env);

    jfieldID GetBindingStructPtrID();
}
