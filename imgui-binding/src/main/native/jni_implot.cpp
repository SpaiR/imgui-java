#include "jni_implot.h"

static jfieldID imPlotPointXID;
static jfieldID imPlotPointYID;

static jfieldID imPlotRangeMinID;
static jfieldID imPlotRangeMaxID;

static jfieldID imPlotLimitsX;
static jfieldID imPlotLimitsY;

static void initialize(JNIEnv* env) {
    static bool initialized = false;
    if (initialized) {
        return;
    }

    jclass jImPlotPointClass = env->FindClass("imgui/extension/implot/ImPlotPoint");
    imPlotPointXID = env->GetFieldID(jImPlotPointClass, "x", "D");
    imPlotPointYID = env->GetFieldID(jImPlotPointClass, "y", "D");

    jclass jImPlotRangeClass = env->FindClass("imgui/extension/implot/ImPlotRange");
    imPlotRangeMinID = env->GetFieldID(jImPlotRangeClass, "min", "D");
    imPlotRangeMaxID = env->GetFieldID(jImPlotRangeClass, "max", "D");

    jclass jImPlotLimitsClass = env->FindClass("imgui/extension/implot/ImPlotLimits");
    imPlotLimitsX = env->GetFieldID(jImPlotLimitsClass, "x", "Limgui/extension/implot/ImPlotRange;");
    imPlotLimitsY = env->GetFieldID(jImPlotLimitsClass, "y", "Limgui/extension/implot/ImPlotRange;");

    initialized = true;
}

namespace Jni
{
    void ImPlotPointCpy(JNIEnv* env, ImPlotPoint* src, jobject dst) {
        initialize(env);
        env->SetDoubleField(dst, imPlotPointXID, src->x);
        env->SetDoubleField(dst, imPlotPointYID, src->y);
    }

    void ImPlotPointCpy(JNIEnv* env, ImPlotPoint src, jobject dst) {
        initialize(env);
        env->SetDoubleField(dst, imPlotPointXID, src.x);
        env->SetDoubleField(dst, imPlotPointYID, src.y);
    }

    void ImPlotPointCpy(JNIEnv* env, jobject src, ImPlotPoint* dst) {
        initialize(env);
        dst->x = env->GetDoubleField(src, imPlotPointXID);
        dst->y = env->GetDoubleField(src, imPlotPointYID);
    }

    void ImPlotRangeCpy(JNIEnv* env, ImPlotRange* src, jobject dst) {
        initialize(env);
        env->SetDoubleField(dst, imPlotRangeMinID, src->Min);
        env->SetDoubleField(dst, imPlotRangeMaxID, src->Max);
    }

    void ImPlotRangeCpy(JNIEnv* env, ImPlotRange src, jobject dst) {
        initialize(env);
        env->SetDoubleField(dst, imPlotRangeMinID, src.Min);
        env->SetDoubleField(dst, imPlotRangeMaxID, src.Max);
    }

    void ImPlotRangeCpy(JNIEnv* env, jobject src, ImPlotRange* dst) {
        initialize(env);
        dst->Min = env->GetDoubleField(src, imPlotRangeMinID);
        dst->Max = env->GetDoubleField(src, imPlotRangeMaxID);
    }

    void ImPlotLimitsCpy(JNIEnv* env, ImPlotLimits* src, jobject dst) {
        initialize(env);
        jobject x = env->GetObjectField(dst, imPlotLimitsX);
        jobject y = env->GetObjectField(dst, imPlotLimitsY);
        Jni::ImPlotRangeCpy(env, src->X, x);
        Jni::ImPlotRangeCpy(env, src->Y, y);
    }

    void ImPlotLimitsCpy(JNIEnv* env, ImPlotLimits src, jobject dst) {
        initialize(env);
        jobject x = env->GetObjectField(dst, imPlotLimitsX);
        jobject y = env->GetObjectField(dst, imPlotLimitsY);
        Jni::ImPlotRangeCpy(env, src.X, x);
        Jni::ImPlotRangeCpy(env, src.Y, y);
    }

    void ImPlotLimitsCpy(JNIEnv* env, jobject src, ImPlotLimits* dst) {
        initialize(env);
        jobject x = env->GetObjectField(src, imPlotLimitsX);
        jobject y = env->GetObjectField(src, imPlotLimitsY);
        Jni::ImPlotRangeCpy(env, x, &dst->X);
        Jni::ImPlotRangeCpy(env, y, &dst->Y);
    }
}
