#pragma once

#include "stdlib.h"
#include "stdint.h"

#include "jni.h"
#include "imgui.h"

#ifndef SET_STRING_FIELD
#define SET_STRING_FIELD(field, value) \
    do { \
        static bool freeField = false; \
        if (freeField) { \
            free((void*)(field)); \
        } \
        if ((value) != NULL) { \
            size_t length = strlen(value) + 1; \
            (field) = (char*)malloc(length); \
            if ((field) != NULL) { \
                strcpy((char*)(field), value); \
                freeField = true; \
            } \
        } else { \
            (field) = NULL; \
            freeField = false; \
        } \
    } while (0)
#endif

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

    jobjectArray NewImGuiKeyDataArray(JNIEnv* env, const ImGuiKeyData* src, int size);
    void ImGuiKeyDataArrayCpy(JNIEnv* env, jobjectArray src, ImGuiKeyData* dst, int size);
}
