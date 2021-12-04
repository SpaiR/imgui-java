#include "jni_callbacks.h"

jmethodID jImListClipperCallbackAcceptMID;

jmethodID jImStrConsumerAcceptMID;
jmethodID jImStrSupplierGetMID;

jmethodID jImPlatformFuncViewportAcceptMID;
jmethodID jImPlatformFuncViewportFloatAcceptMID;
jmethodID jImPlatformFuncViewportImVec2AcceptMID;
jmethodID jImPlatformFuncViewportStringAcceptMID;
jmethodID jImPlatformFuncViewportSuppImVec2GetMID;
jmethodID jImPlatformFuncViewportSuppBooleanGetMID;
jmethodID jImPlatformFuncViewportSuppFloatGetMID;
jmethodID jImGuiFileDialogPaneFunMID;

namespace Jni
{
    void InitCallbacks(JNIEnv* env) {
        jclass jImListClipperCallback = env->FindClass("imgui/callback/ImListClipperCallback");
        jImListClipperCallbackAcceptMID = env->GetMethodID(jImListClipperCallback, "accept", "(I)V");

        jclass jImStrConsumer = env->FindClass("imgui/callback/ImStrConsumer");
        jImStrConsumerAcceptMID = env->GetMethodID(jImStrConsumer, "accept", "(Ljava/lang/String;)V");

        jclass jImStrSupplier = env->FindClass("imgui/callback/ImStrSupplier");
        jImStrSupplierGetMID = env->GetMethodID(jImStrSupplier, "get", "()Ljava/lang/String;");

        jclass jImPlatformFuncViewport = env->FindClass("imgui/callback/ImPlatformFuncViewport");
        jImPlatformFuncViewportAcceptMID = env->GetMethodID(jImPlatformFuncViewport, "accept", "(Limgui/ImGuiViewport;)V");

        jclass jImPlatformFuncViewportFloat = env->FindClass("imgui/callback/ImPlatformFuncViewportFloat");
        jImPlatformFuncViewportFloatAcceptMID = env->GetMethodID(jImPlatformFuncViewportFloat, "accept", "(Limgui/ImGuiViewport;F)V");

        jclass jImPlatformFuncViewportImVec2 = env->FindClass("imgui/callback/ImPlatformFuncViewportImVec2");
        jImPlatformFuncViewportImVec2AcceptMID = env->GetMethodID(jImPlatformFuncViewportImVec2, "accept", "(Limgui/ImGuiViewport;Limgui/ImVec2;)V");

        jclass jImPlatformFuncViewportString = env->FindClass("imgui/callback/ImPlatformFuncViewportString");
        jImPlatformFuncViewportStringAcceptMID = env->GetMethodID(jImPlatformFuncViewportString, "accept", "(Limgui/ImGuiViewport;Ljava/lang/String;)V");

        jclass jImPlatformFuncViewportSuppImVec2 = env->FindClass("imgui/callback/ImPlatformFuncViewportSuppImVec2");
        jImPlatformFuncViewportSuppImVec2GetMID = env->GetMethodID(jImPlatformFuncViewportSuppImVec2, "get", "(Limgui/ImGuiViewport;Limgui/ImVec2;)V");

        jclass jImPlatformFuncViewportSuppBoolean = env->FindClass("imgui/callback/ImPlatformFuncViewportSuppBoolean");
        jImPlatformFuncViewportSuppBooleanGetMID = env->GetMethodID(jImPlatformFuncViewportSuppBoolean, "get", "(Limgui/ImGuiViewport;)Z");

        jclass jImPlatformFuncViewportSuppFloat = env->FindClass("imgui/callback/ImPlatformFuncViewportSuppFloat");
        jImPlatformFuncViewportSuppFloatGetMID = env->GetMethodID(jImPlatformFuncViewportSuppFloat, "get", "(Limgui/ImGuiViewport;)F");

        jclass jImGuiFileDialogPaneFun = env->FindClass("imgui/extension/imguifiledialog/callback/ImGuiFileDialogPaneFun");
        jImGuiFileDialogPaneFunMID = env->GetMethodID(jImGuiFileDialogPaneFun, "paneFun", "(Ljava/lang/String;JZ)V");
    }

    void CallImListClipperCallback(JNIEnv* env, jobject consumer, int index) {
        env->CallVoidMethod(consumer, jImListClipperCallbackAcceptMID, index);
    }

    void CallImStrConsumer(JNIEnv* env, jobject consumer, const char* str) {
        env->CallVoidMethod(consumer, jImStrConsumerAcceptMID, env->NewStringUTF(str));
    }

    jstring CallImStrSupplier(JNIEnv* env, jobject supplier) {
        return (jstring)env->CallObjectMethod(supplier, jImStrSupplierGetMID);
    }

    void CallImPlatformFuncViewport(JNIEnv* env, jobject func, jobject vp) {
        env->CallVoidMethod(func, jImPlatformFuncViewportAcceptMID, vp);
    }

    void CallImPlatformFuncViewportFloat(JNIEnv* env, jobject func, jobject vp, float f) {
        env->CallVoidMethod(func, jImPlatformFuncViewportFloatAcceptMID, vp, f);
    }

    void CallImPlatformFuncViewportImVec2(JNIEnv* env, jobject func, jobject vp, jobject imVec2) {
        env->CallVoidMethod(func, jImPlatformFuncViewportImVec2AcceptMID, vp, imVec2);
    }

    void CallImPlatformFuncViewportString(JNIEnv* env, jobject func, jobject vp, const char* str) {
        env->CallVoidMethod(func, jImPlatformFuncViewportStringAcceptMID, vp, env->NewStringUTF(str));
    }

    void CallImPlatformFuncViewportSuppImVec2(JNIEnv* env, jobject func, jobject vp, jobject imVec2) {
        env->CallVoidMethod(func, jImPlatformFuncViewportSuppImVec2GetMID, vp, imVec2);
    }

    jboolean CallImPlatformFuncViewportSuppBoolean(JNIEnv* env, jobject func, jobject vp) {
        return (jboolean)env->CallBooleanMethod(func, jImPlatformFuncViewportSuppBooleanGetMID, vp);
    }

    jfloat CallImPlatformFuncViewportSuppFloat(JNIEnv* env, jobject func, jobject vp) {
        return (jfloat)env->CallFloatMethod(func, jImPlatformFuncViewportSuppFloatGetMID, vp);
    }

    void CallImGuiFileDialogPaneFun(JNIEnv* env, jobject func, const char* filter, long user_datas, bool canWeContinue) {
        env->CallVoidMethod(func, jImGuiFileDialogPaneFunMID, env->NewStringUTF(filter), user_datas, canWeContinue);
    }
}
