#pragma once

#include "imgui_internal.h"
#include "jni_common.h"

namespace Jni
{
    void InitInternal(JNIEnv* env);

    void ImRectCpy(JNIEnv* env, ImRect* src, jobject dst);
    void ImRectCpy(JNIEnv* env, ImRect src, jobject dst);
    void ImRectCpy(JNIEnv* env, jobject src, ImRect* dst);
}
