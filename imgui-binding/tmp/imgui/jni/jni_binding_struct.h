#include <jni.h>

#ifndef JNI_BINDING_STRUCT_H
#define JNI_BINDING_STRUCT_H

#ifndef STRUCT_PTR
#define STRUCT_PTR env->GetLongField(object, Jni::GetBindingStructPtrID())
#endif

namespace Jni
{
    void InitBindingStruct(JNIEnv* env);

    jfieldID GetBindingStructPtrID();
}

#endif
