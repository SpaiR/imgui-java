#pragma once

#include "jni.h"
#include "imgui.h"

namespace Jni
{
    void InitCommon(JNIEnv* env);

    void ImVec2Cpy(JNIEnv* env, ImVec2* src, jobject dst);
    void ImVec2Cpy(JNIEnv* env, ImVec2 src, jobject dst);
    void ImVec2Cpy(JNIEnv* env, jobject src, ImVec2* dst);

    void ImVec4Cpy(JNIEnv* env, ImVec4* src, jobject dst);
    void ImVec4Cpy(JNIEnv* env, ImVec4 src, jobject dst);
    void ImVec4Cpy(JNIEnv* env, jobject src, ImVec4* dst);

    jobjectArray NewImVec2Array(JNIEnv* env, ImVec2* src, int size);
    jobjectArray NewImVec4Array(JNIEnv* env, ImVec4* src, int size);

    void ImVec2ArrayCpy(JNIEnv* env, jobjectArray src, ImVec2* dst, int size);
    void ImVec4ArrayCpy(JNIEnv* env, jobjectArray src, ImVec4* dst, int size);
}
