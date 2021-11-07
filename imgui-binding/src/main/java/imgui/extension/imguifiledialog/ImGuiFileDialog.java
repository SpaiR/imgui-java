package imgui.extension.imguifiledialog;

/**
 * ImGuiFileDialog extension for ImGui
 * Repo: https://github.com/aiekick/ImGuiFileDialog
 */
public class ImGuiFileDialog {

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

    //TODO:
    /*
    // Display / Close dialog form
    bool Display(                                      // Display the dialog. return true if a result was obtained (Ok or not)
        const std::string& vKey,                       // key dialog to display (if not the same key as defined by OpenDialog/Modal => no opening)
        ImGuiWindowFlags vFlags = ImGuiWindowFlags_NoCollapse, // ImGuiWindowFlags
        ImVec2 vMinSize = ImVec2(0, 0),                // mininmal size contraint for the ImGuiWindow
        ImVec2 vMaxSize = ImVec2(FLT_MAX, FLT_MAX));   // maximal size contraint for the ImGuiWindow
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
        return ImGuiFileDialog::Instance()->GetOpenedKey();
    */


    /**
     * true => Dialog Closed with Ok result / false : Dialog closed with cancel result
     *
     * @return True is the dialog closed with Ok result, or false with cancel result
     */
    public static native boolean isOk(); /*
        return ImGuiFileDialog::Instance()->IsOK();
    */

    //TODO:
    //std::map<std::string, std::string> GetSelection(); // Open File behavior : will return selection via a map<FileName, FilePathName>

    /**
     * Save File behavior : will always return the content of the field with current filter extention and current path
     */
    public static native String getFilePathName(); /*
        return ImGuiFileDialog::Instance()->GetFilePathName();
    */

    //TODO:
    //std::string GetCurrentFileName();                  // Save File behavior : will always return the content of the field with current filter extention

    //TODO:
    //std::string GetCurrentPath();                      // will return current path

    //TODO:
    //std::string GetCurrentFilter();                    // will return selected filter

    //TODO:
    //UserDatas GetUserDatas();                          // will return user datas send with Open Dialog/Modal

    //TODO:
    /*
    void SetExtentionInfos(                            // SetExtention datas for have custom display of particular file type
        const std::string& vFilter,                    // extention filter to tune
        const FileExtentionInfosStruct& vInfos);       // Filter Extention Struct who contain Color and Icon/Text for the display of the file with extention filter
     */

    //TODO:
    /*
    void SetExtentionInfos(                            // SetExtention datas for have custom display of particular file type
        const std::string& vFilter,                    // extention filter to tune
        const ImVec4& vColor,                          // wanted color for the display of the file with extention filter
        const std::string& vIcon = "");                // wanted text or icon of the file with extention filter
     */

    //TODO:
    /*
    bool GetExtentionInfos(                            // GetExtention datas. return true is extention exist
        const std::string& vFilter,                    // extention filter (same as used in SetExtentionInfos)
        ImVec4 *vOutColor,                             // color to retrieve
        std::string* vOutIcon = 0);                    // icon or text to retrieve
     */


    /**
     * clear extentions setttings
     */
    public static native void clearExtentionInfos(); /*
        ImGuiFileDialog::Instance()->ClearExtentionInfos();
    */
}
