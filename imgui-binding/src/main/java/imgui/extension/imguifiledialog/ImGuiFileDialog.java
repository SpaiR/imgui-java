package imgui.extension.imguifiledialog;

import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;

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
    */

    //TODO:
    /*
    // standard dialog
    void OpenDialog(                                   // open simple dialog (path and fileName can be specified)
        const std::string& vKey,                       // key dialog
        const std::string& vTitle,                     // title
        const char* vFilters,                          // filters
        const std::string& vPath,                      // path
        const std::string& vFileName,                  // defaut file name
        const int& vCountSelectionMax = 1,             // count selection max
        UserDatas vUserDatas = nullptr,                // user datas (can be retrieved in pane)
        ImGuiFileDialogFlags vFlags = 0);              // ImGuiFileDialogFlags
     */

    /*JNI
        #define IGFD_PANE_FUN(name)\
            jobject paneFun##name = NULL;\
            void PaneFunStub##name(char* filters, UserDatas user_datas, bool canWeContinue) {\
                if (panFun##name != NULL) {\
                    JNIEnv* env = Jni::GetEnv();\
                    Jni::CallImGuiFileDialogPaneFun(env, paneFun##name, filters, user_datas, canWeContinue);\
                }\
            }\

        IGFD_PANE_FUN(OpenModal)

        #define IGFD_PANE_FUN_METHOD(name)\
            if (paneFun##name != NULL) {\
                env->DeleteGlobalRef(paneFun##name);\
            }\
            paneFun##name = env->NewGlobalRef(func);

        #define IGFD_PANE_FUN(name)\
            paneFun##name
     */


    //TODO:
    /*
    // modal dialog
    void OpenModal(                                    // open simple modal (path and fileName can be specified)
        const std::string& vKey,                       // key dialog
        const std::string& vTitle,                     // title
        const char* vFilters,                          // filters
        const std::string& vPath,                      // path
        const std::string& vFileName,                  // defaut file name
        const int& vCountSelectionMax = 1,             // count selection max
        UserDatas vUserDatas = nullptr,                // user datas (can be retrieved in pane)
        ImGuiFileDialogFlags vFlags = 0);              // ImGuiFileDialogFlags
     */

    //TODO:
    /*
    OpenModal(                                    // open simple modal (path and fielname are obtained from filePathName)
        const std::string& vKey,                       // key dialog
        const std::string& vTitle,                     // title
        const char* vFilters,                          // filters
        const std::string& vFilePathName,              // file path name (will be decompsoed in path and fileName)
        const int& vCountSelectionMax = 1,             // count selection max
        UserDatas vUserDatas = nullptr,                // user datas (can be retrieved in pane)
        ImGuiFileDialogFlags vFlags = 0);              // ImGuiFileDialogFlags
     */

    //TODO:
    /*
    // with pane
    void OpenModal(                                    // open modal with custom right pane (path and filename are obtained from filePathName)
        const std::string& vKey,                       // key dialog
        const std::string& vTitle,                     // title
        const char* vFilters,                          // filters
        const std::string& vPath,                      // path
        const std::string& vFileName,                  // defaut file name
        const PaneFun& vSidePane,                      // side pane
        const float& vSidePaneWidth = 250.0f,                               // side pane width
        const int& vCountSelectionMax = 1,             // count selection max
        UserDatas vUserDatas = nullptr,                // user datas (can be retrieved in pane)
        ImGuiFileDialogFlags vFlags = 0);              // ImGuiFileDialogFlags
     */

    //TODO:
    /*
    void OpenModal(                                    // open modal with custom right pane (path and fielname are obtained from filePathName)
        const std::string& vKey,                       // key dialog
        const std::string& vTitle,                     // title
        const char* vFilters,                          // filters
        const std::string& vFilePathName,              // file path name (will be decompsoed in path and fileName)
        const PaneFun& vSidePane,                      // side pane
        const float& vSidePaneWidth = 250.0f,                               // side pane width
        const int& vCountSelectionMax = 1,             // count selection max
        UserDatas vUserDatas = nullptr,                // user datas (can be retrieved in pane)
        ImGuiFileDialogFlags vFlags = 0);              // ImGuiFileDialogFlags
     */
        public static native void openModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane,
                                            float vSidePaneWidth, int vCountSelectionMax, Object vUserDatas, int vFlags); /*
            ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, )
        */

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @param vFlags ImGuiWindowFlags
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
     * @return Map of FileName to FilePathName in key,value pair
     */
    private static native HashMap<String, String> getSelection(); /*
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
     * @return the content of the field with current filter extension
     */
    public static native String getCurrentFileName(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentFileName().c_str());
    */


    /**
     * will return current path
     * @return the current path
     */
    public static native String getCurrentPath(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentPath().c_str());
    */


    /**
     * will return selected filter
     * @return the selected filter
     */
    public static native String getCurrentFilter(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentFilter().c_str());
    */

    //TODO:
    //UserDatas GetUserDatas();                          // will return user datas send with Open Dialog/Modal
    public static native Object getUserDatas(); /*
        return (jobject) ImGuiFileDialog::Instance()->GetUserDatas();
    */
}
