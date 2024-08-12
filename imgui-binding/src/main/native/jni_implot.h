#pragma once

#include "jni.h"
#include "implot.h"

namespace Jni
{
    void ImPlotPointCpy(JNIEnv* env, ImPlotPoint* src, jobject dst);
    void ImPlotPointCpy(JNIEnv* env, ImPlotPoint src, jobject dst);
    void ImPlotPointCpy(JNIEnv* env, jobject src, ImPlotPoint* dst);

    void ImPlotRangeCpy(JNIEnv* env, ImPlotRange* src, jobject dst);
    void ImPlotRangeCpy(JNIEnv* env, ImPlotRange src, jobject dst);
    void ImPlotRangeCpy(JNIEnv* env, jobject src, ImPlotRange* dst);

    void ImPlotRectCpy(JNIEnv* env, ImPlotRect* src, jobject dst);
    void ImPlotRectCpy(JNIEnv* env, ImPlotRect src, jobject dst);
    void ImPlotRectCpy(JNIEnv* env, jobject src, ImPlotRect* dst);
}
