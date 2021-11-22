package io.github.spair.imgui.extension.imguifiledialog;

import io.github.spair.imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;

import java.util.HashMap;

/**
 * ImGuiFileDialog extension for ImGui
 * Repo: https://github.com/aiekick/ImGuiFileDialog
 */
public final class ImGuiFileDialog {

    private ImGuiFileDialog() {

    }

    /*JNI
        #include "_imguifiledialog.h"

        jobject imgfdPaneFunCallback = NULL;
        const void paneFunCallback(const char* filter, void* user_data, bool* can_we_continue) {
            if (imgfdPaneFunCallback != NULL) {
                JNIEnv* env = Jni::GetEnv();
                std::string vKey = ImGuiFileDialog::Instance()->GetOpenedKey();
                jlong ret_user_datas = reinterpret_cast<jlong>(user_data);
                Jni::CallImGuiFileDialogPaneFun(env, imgfdPaneFunCallback, filter, ret_user_datas, *can_we_continue);
            }
        }

        #define IMGFD_PANE_FUN_METHOD_TMPL()\
            if (imgfdPaneFunCallback != NULL) {\
                env->DeleteGlobalRef(imgfdPaneFunCallback);\
            }\
            imgfdPaneFunCallback = env->NewGlobalRef(vSidePane);
    */

    /**
     * open simple dialog (path and fileName can be specified)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters
     * @param vPath              path
     * @param vFileName          default file name
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    public static native void openDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName,
                                         int vCountSelectionMax, long vUserDatas, int vFlags); /*
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
    */


    /**
     * open simple modal (path and fileName can be specified)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters
     * @param vPath              path
     * @param vFileName          default file name
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    public static native void openModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName,
                                        int vCountSelectionMax, long vUserDatas, int vFlags);  /*
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
    */


    /**
     * open simple modal (path and filename are obtained from filePathName)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters
     * @param vFilePathName      file path name (will be decomposed in path and fileName)
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    public static native void openModal(String vKey, String vTitle, String vFilters, String vFilePathName,
                                        int vCountSelectionMax, long vUserDatas, int vFlags);  /*
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
    */


    /**
     * open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters
     * @param vPath              path
     * @param vFileName          default file name
     * @param vSidePane          side pane
     * @param vSidePaneWidth     side pane width
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    public static native void openModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane,
                                        float vSidePaneWidth, int vCountSelectionMax, long vUserDatas, int vFlags); /*
        IMGFD_PANE_FUN_METHOD_TMPL()
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, paneFunCallback, vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
     */


    /**
     * open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters
     * @param vFilePathName      file path name (will be decomposed in path and fileName)
     * @param vSidePane          side pane
     * @param vSidePaneWidth     side pane width
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    public static native void openModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane,
                                        float vSidePaneWidth, int vCountSelectionMax, long vUserDatas, int vFlags); /*
        IMGFD_PANE_FUN_METHOD_TMPL()
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, paneFunCallback, vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
     */

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey      key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @param vFlags    ImGuiWindowFlags
     * @param vMinSizeX minimal size constraint for the ImGuiWindow
     * @param vMinSizeY minimal size constraint for the ImGuiWindow
     * @param vMaxSizeX maximal size constraint for the ImGuiWindow
     * @param vMaxSizeY maximal size constraint for the ImGuiWindow
     * @return true if a result was obtained (Ok or not)
     */
    public static native boolean display(String vKey, int vFlags, float vMinSizeX, float vMinSizeY, float vMaxSizeX, float vMaxSizeY); /*
        return ImGuiFileDialog::Instance()->Display(vKey, vFlags, ImVec2(vMinSizeX, vMinSizeY), ImVec2(vMaxSizeX, vMaxSizeY));
    */


    /**
     * close dialog
     */
    public static native void close(); /*
         ImGuiFileDialog::Instance()->Close();
    */


    /**
     * say if the dialog key was already opened this frame
     *
     * @param vKey key dialog
     * @return if the dialog key was already opened this frame
     */
    public static native boolean wasOpenedThisFrame(String vKey); /*
        return ImGuiFileDialog::Instance()->WasOpenedThisFrame(vKey);
     */


    /**
     * say if the dialog was already opened this frame
     *
     * @return if the dialog was already opened this frame
     */
    public static native boolean wasOpenedThisFrame(); /*
        return ImGuiFileDialog::Instance()->WasOpenedThisFrame();
    */


    /**
     * say if the key is opened
     *
     * @param vKey key dialog
     * @return if the key is opened
     */
    public static native boolean isOpened(String vKey); /*
        return ImGuiFileDialog::Instance()->IsOpened(vKey);
    */


    /**
     * say if the dialog is opened somewhere
     *
     * @return if the dialog is opened somewhere
     */
    public static native boolean isOpened(); /*
        return ImGuiFileDialog::Instance()->IsOpened();
    */


    /**
     * return the dialog key who is opened, return nothing if not opened
     *
     * @return the dialog key who is opened or nothing is not opened
     */
    public static native String getOpenedKey(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetOpenedKey().c_str());
    */


    /**
     * true: Dialog Closed with Ok result / false: Dialog closed with cancel result
     *
     * @return True if the dialog closed with Ok result, or false with cancel result
     */
    public static native boolean isOk(); /*
        return ImGuiFileDialog::Instance()->IsOk();
    */


    /**
     * Open File behavior : will return selection via a map&lt;FileName, FilePathName&gt;
     * <p>
     * For example, if a file is selected, say test.txt. Then the key value pair will be:
     * 'test.txt', '/some/path/to/test.txt'
     *
     * @return Map of FileName to FilePathName in key,value pair
     */
    public static native HashMap<String, String> getSelection(); /*
        //Get the map from ImGuiFileDialog
        std::map<std::string, std::string> mMap = ImGuiFileDialog::Instance()->GetSelection();

        env->PushLocalFrame(mMap.size() * 2); // Expands stack size to not overflow

        //Get reference to java's HashMap
        jclass hashMapClass = env->FindClass("java/util/HashMap");
        jmethodID hashMapInit = env->GetMethodID(hashMapClass, "<init>", "(I)V");
        jobject hashMapObj = env->NewObject(hashMapClass, hashMapInit, mMap.size());
        jmethodID hashMapPut = env->GetMethodID(hashMapClass, "put",
                    "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

        //Copy key,value pairs from map to hashmap
        for (auto it : mMap)
        {
            env->CallObjectMethod(hashMapObj, hashMapPut,
                 env->NewStringUTF(it.first.c_str()),
                 env->NewStringUTF(it.second.c_str())
             );
        }

        env->PopLocalFrame(hashMapObj); //Cleanup stack

        return hashMapObj;
    */


    /**
     * Save File behavior : will always return the content of the field with current filter extention and current path
     */
    public static native String getFilePathName(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetFilePathName().c_str());
    */


    /**
     * Save File behavior : will always return the content of the field with current filter extension
     *
     * @return the content of the field with current filter extension
     */
    public static native String getCurrentFileName(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentFileName().c_str());
    */


    /**
     * will return current path
     *
     * @return the current path
     */
    public static native String getCurrentPath(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentPath().c_str());
    */


    /**
     * will return selected filter
     *
     * @return the selected filter
     */
    public static native String getCurrentFilter(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentFilter().c_str());
    */


    /**
     * will return user datas sent with Open Dialog/Modal
     * <p>
     * Can be used to pass a long value to the dialog and get the value back.
     * This long value can be a pointer to a native data structure.
     *
     * @return user datas sent with Open Dialog/Modal
     */
    public static native long getUserDatas(); /*
        return reinterpret_cast<jlong>(ImGuiFileDialog::Instance()->GetUserDatas());
    */
}
