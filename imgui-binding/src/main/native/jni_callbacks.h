#pragma once

#include <jni.h>

namespace Jni
{
    void InitCallbacks(JNIEnv* env);

    void CallImListClipperCallback(JNIEnv* env, jobject consumer, int index);

    void CallImStrConsumer(JNIEnv* env, jobject consumer, const char* str);

    jstring CallImStrSupplier(JNIEnv* env, jobject supplier);

    void CallImPlatformFuncViewport(JNIEnv* env, jobject func, jobject vp);

    void CallImPlatformFuncViewportFloat(JNIEnv* env, jobject func, jobject vp, float f);

    void CallImPlatformFuncViewportImVec2(JNIEnv* env, jobject func, jobject vp, jobject imVec2);

    void CallImPlatformFuncViewportString(JNIEnv* env, jobject func, jobject vp, const char* str);

    void CallImPlatformFuncViewportSuppImVec2(JNIEnv* env, jobject func, jobject vp, jobject imVec2);

    jboolean CallImPlatformFuncViewportSuppBoolean(JNIEnv* env, jobject func, jobject vp);

    jfloat CallImPlatformFuncViewportSuppFloat(JNIEnv* env, jobject func, jobject vp);
}
