package imgui.extension.imguifiledialog;

import imgui.ImVec2;





import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;

import java.util.HashMap;

/**
 * ImGuiFileDialog extension for ImGui
 * Repo: <a href="https://github.com/aiekick/ImGuiFileDialog">https://github.com/aiekick/ImGuiFileDialog</a>
 */

public final class ImGuiFileDialog {
    private ImGuiFileDialog() {
    }

    /*JNI
        #include "_imguifiledialog.h"

        static auto PaneFunCallback(JNIEnv* env, jobject fn) {
            static jobject cb = NULL;
            if (cb != NULL) {
                env->DeleteGlobalRef(cb);
            }
            cb = env->NewGlobalRef(fn);
            return [](const char* filter, void* userData, bool* canWeContinue) {
                if (cb != NULL) {
                    Jni::CallImGuiFileDialogPaneFun(Jni::GetEnv(), cb, filter, reinterpret_cast<jlong>(userData), *canWeContinue);
                }
            };
        }
    */

     /**
     * Open simple dialog (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName);
    }

    /**
     * Open simple dialog (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final int vCountSelectionMax) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax);
    }

    /**
     * Open simple dialog (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final int vCountSelectionMax, final long vUserDatas) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open simple dialog (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

     /**
     * Open simple dialog (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName);
    }

    /**
     * Open simple dialog (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final int vCountSelectionMax) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax);
    }

    /**
     * Open simple dialog (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final int vCountSelectionMax, final long vUserDatas) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open simple dialog (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

     /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vSidePane);
    }

    /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth);
    }

    /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth, vCountSelectionMax);
    }

    /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas, vFlags);
    }

    /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenDialog(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), 250.0f, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

     /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vSidePane
     * 		side pane
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vSidePane);
    }

    /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vSidePane
     * 		side pane
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth);
    }

    /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth, vCountSelectionMax);
    }

    /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas, vFlags);
    }

    /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decompsoed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openDialog(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenDialog(vKey, vTitle, vFilters, vFilePathName, vSidePane, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenDialog(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), 250.0f, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */


     /**
     * Open simple modal (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName);
    }

    /**
     * Open simple modal (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final int vCountSelectionMax) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax);
    }

    /**
     * Open simple modal (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final int vCountSelectionMax, final long vUserDatas) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open simple modal (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

     /**
     * open simple modal (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName);
    }

    /**
     * open simple modal (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final int vCountSelectionMax) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax);
    }

    /**
     * open simple modal (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final int vCountSelectionMax, final long vUserDatas) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, vUserDatas);
    }

    /**
     * open simple modal (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */



     /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vSidePane);
    }

    /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth);
    }

    /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth, vCountSelectionMax);
    }

    /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas, vFlags);
    }

    /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFileName
     * 		default file name
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vPath, final String vFileName, final ImGuiFileDialogPaneFun vSidePane, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenModal(vKey, vTitle, vFilters, vPath, vFileName, vSidePane, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, ImGuiFileDialogPaneFun vSidePane, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vPath = obj_vPath == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vPath, JNI_FALSE);
        auto vFileName = obj_vFileName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFileName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vPath, vFileName, PaneFunCallback(env, vSidePane), 250.0f, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vPath != NULL) env->ReleaseStringUTFChars(obj_vPath, vPath);
        if (vFileName != NULL) env->ReleaseStringUTFChars(obj_vFileName, vFileName);
    */

     /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vSidePane
     * 		side pane
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vSidePane);
    }

    /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth);
    }

    /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     * @param vCountSelectionMax
     * 		count selection max
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth, vCountSelectionMax);
    }

    /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas);
    }

    /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vSidePaneWidth
     * 		side pane width
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final float vSidePaneWidth, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vSidePane, vSidePaneWidth, vCountSelectionMax, vUserDatas, vFlags);
    }

    /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey
     * 		key dialog
     * @param vFilters
     * 		filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName
     * 		file path name (will be decomposed in path and fileName)
     * @param vSidePane
     * 		side pane
     * @param vCountSelectionMax
     * 		count selection max
     * @param vUserDatas
     * 		user datas (can be retrieved in pane)
     */
    public static void openModal(final String vKey, final String vTitle, final String vFilters, final String vFilePathName, final ImGuiFileDialogPaneFun vSidePane, final int vCountSelectionMax, final long vUserDatas, final int vFlags) {
        nOpenModal(vKey, vTitle, vFilters, vFilePathName, vSidePane, vCountSelectionMax, vUserDatas, vFlags);
    }

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas));
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, float vSidePaneWidth, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), vSidePaneWidth, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

    private static native void nOpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, ImGuiFileDialogPaneFun vSidePane, int vCountSelectionMax, long vUserDatas, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto vTitle = obj_vTitle == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vTitle, JNI_FALSE);
        auto vFilters = obj_vFilters == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilters, JNI_FALSE);
        auto vFilePathName = obj_vFilePathName == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vFilePathName, JNI_FALSE);
        ImGuiFileDialog::Instance()->OpenModal(vKey, vTitle, vFilters, vFilePathName, PaneFunCallback(env, vSidePane), 250.0f, vCountSelectionMax, reinterpret_cast<void*>(vUserDatas), vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        if (vTitle != NULL) env->ReleaseStringUTFChars(obj_vTitle, vTitle);
        if (vFilters != NULL) env->ReleaseStringUTFChars(obj_vFilters, vFilters);
        if (vFilePathName != NULL) env->ReleaseStringUTFChars(obj_vFilePathName, vFilePathName);
    */

     /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey
     * 		key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @return true if a result was obtained (Ok or not)
     */
    public static boolean display(final String vKey) {
        return nDisplay(vKey);
    }

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey
     * 		key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @return true if a result was obtained (Ok or not)
     */
    public static boolean display(final String vKey, final int vFlags) {
        return nDisplay(vKey, vFlags);
    }

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey
     * 		key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @param vMinSize
     * 		minimal size constraint for the ImGuiWindow
     * @return true if a result was obtained (Ok or not)
     */
    public static boolean display(final String vKey, final int vFlags, final ImVec2 vMinSize) {
        return nDisplay(vKey, vFlags, vMinSize.x, vMinSize.y);
    }

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey
     * 		key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @return true if a result was obtained (Ok or not)
     */
    public static boolean display(final String vKey, final int vFlags, final float vMinSizeX, final float vMinSizeY) {
        return nDisplay(vKey, vFlags, vMinSizeX, vMinSizeY);
    }

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey
     * 		key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @param vMinSize
     * 		minimal size constraint for the ImGuiWindow
     * @param vMaxSize
     * 		maximal size constraint for the ImGuiWindow
     * @return true if a result was obtained (Ok or not)
     */
    public static boolean display(final String vKey, final int vFlags, final ImVec2 vMinSize, final ImVec2 vMaxSize) {
        return nDisplay(vKey, vFlags, vMinSize.x, vMinSize.y, vMaxSize.x, vMaxSize.y);
    }

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey
     * 		key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @return true if a result was obtained (Ok or not)
     */
    public static boolean display(final String vKey, final int vFlags, final float vMinSizeX, final float vMinSizeY, final float vMaxSizeX, final float vMaxSizeY) {
        return nDisplay(vKey, vFlags, vMinSizeX, vMinSizeY, vMaxSizeX, vMaxSizeY);
    }

    private static native boolean nDisplay(String obj_vKey); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto _result = ImGuiFileDialog::Instance()->Display(vKey);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        return _result;
    */

    private static native boolean nDisplay(String obj_vKey, int vFlags); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto _result = ImGuiFileDialog::Instance()->Display(vKey, vFlags);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        return _result;
    */

    private static native boolean nDisplay(String obj_vKey, int vFlags, float vMinSizeX, float vMinSizeY); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        ImVec2 vMinSize = ImVec2(vMinSizeX, vMinSizeY);
        auto _result = ImGuiFileDialog::Instance()->Display(vKey, vFlags, vMinSize);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        return _result;
    */

    private static native boolean nDisplay(String obj_vKey, int vFlags, float vMinSizeX, float vMinSizeY, float vMaxSizeX, float vMaxSizeY); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        ImVec2 vMinSize = ImVec2(vMinSizeX, vMinSizeY);
        ImVec2 vMaxSize = ImVec2(vMaxSizeX, vMaxSizeY);
        auto _result = ImGuiFileDialog::Instance()->Display(vKey, vFlags, vMinSize, vMaxSize);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        return _result;
    */


     /**
     * Close dialog
     */
    public static void close() {
        nClose();
    }

    private static native void nClose(); /*
        ImGuiFileDialog::Instance()->Close();
    */


     /**
     * Say if the dialog key was already opened this frame
     *
     * @return if the dialog key was already opened this frame
     */
    public static boolean wasOpenedThisFrame() {
        return nWasOpenedThisFrame();
    }

    /**
     * Say if the dialog key was already opened this frame
     *
     * @param vKey
     * 		key dialog
     * @return if the dialog key was already opened this frame
     */
    public static boolean wasOpenedThisFrame(final String vKey) {
        return nWasOpenedThisFrame(vKey);
    }

    private static native boolean nWasOpenedThisFrame(); /*
        return ImGuiFileDialog::Instance()->WasOpenedThisFrame();
    */

    private static native boolean nWasOpenedThisFrame(String obj_vKey); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto _result = ImGuiFileDialog::Instance()->WasOpenedThisFrame(vKey);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        return _result;
    */

     /**
     * Say if the key is opened
     *
     * @return if the key is opened
     */
    public static boolean isOpened() {
        return nIsOpened();
    }

    /**
     * Say if the key is opened
     *
     * @param vKey
     * 		key dialog
     * @return if the key is opened
     */
    public static boolean isOpened(final String vKey) {
        return nIsOpened(vKey);
    }

    private static native boolean nIsOpened(); /*
        return ImGuiFileDialog::Instance()->IsOpened();
    */

    private static native boolean nIsOpened(String obj_vKey); /*MANUAL
        auto vKey = obj_vKey == NULL ? NULL : (char*)env->GetStringUTFChars(obj_vKey, JNI_FALSE);
        auto _result = ImGuiFileDialog::Instance()->IsOpened(vKey);
        if (vKey != NULL) env->ReleaseStringUTFChars(obj_vKey, vKey);
        return _result;
    */

     /**
     * Return the dialog key who is opened, return nothing if not opened
     *
     * @return the dialog key who is opened or nothing is not opened
     */
    public static String getOpenedKey() {
        return nGetOpenedKey();
    }

    private static native String nGetOpenedKey(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetOpenedKey().c_str());
    */


     /**
     * true: Dialog Closed with Ok result / false: Dialog closed with cancel result
     *
     * @return True if the dialog closed with Ok result, or false with cancel result
     */
    public static boolean isOk() {
        return nIsOk();
    }

    private static native boolean nIsOk(); /*
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
    public static HashMap<String, String> getSelection() {
        return nGetSelection();
    }

    private static native HashMap<String, String> nGetSelection(); /*
        // Get the map from ImGuiFileDialog
        std::map<std::string, std::string> mMap = ImGuiFileDialog::Instance()->GetSelection();

        env->PushLocalFrame(mMap.size() * 2); // Expands stack size to not overflow

        // Get reference to java's HashMap
        jclass hashMapClass = env->FindClass("java/util/HashMap");
        jmethodID hashMapInit = env->GetMethodID(hashMapClass, "<init>", "(I)V");
        jobject hashMapObj = env->NewObject(hashMapClass, hashMapInit, mMap.size());
        jmethodID hashMapPut = env->GetMethodID(hashMapClass, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

        //Copy key,value pairs from map to hashmap
        for (auto it : mMap) {
            env->CallObjectMethod(hashMapObj, hashMapPut,
                 env->NewStringUTF(it.first.c_str()),
                 env->NewStringUTF(it.second.c_str())
            );
        }

        env->PopLocalFrame(hashMapObj); // Cleanup stack

        return hashMapObj;
    */


     /**
     * Save File behavior : will always return the content of the field with current filter extention and current path
     */
    public static String getFilePathName() {
        return nGetFilePathName();
    }

    private static native String nGetFilePathName(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetFilePathName().c_str());
    */

     /**
     * Save File behavior : will always return the content of the field with current filter extension
     *
     * @return the content of the field with current filter extension
     */
    public static String getCurrentFileName() {
        return nGetCurrentFileName();
    }

    private static native String nGetCurrentFileName(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentFileName().c_str());
    */

     /**
     * Will return current path
     *
     * @return the current path
     */
    public static String getCurrentPath() {
        return nGetCurrentPath();
    }

    private static native String nGetCurrentPath(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentPath().c_str());
    */

     /**
     * Will return selected filter
     *
     * @return the selected filter
     */
    public static String getCurrentFilter() {
        return nGetCurrentFilter();
    }

    private static native String nGetCurrentFilter(); /*
        return env->NewStringUTF(ImGuiFileDialog::Instance()->GetCurrentFilter().c_str());
    */


     /**
     * Will return user datas sent with Open Dialog/Modal
     * <p>
     * Can be used to pass a long value to the dialog and get the value back.
     * This long value can be a pointer to a native data structure.
     *
     * @return user datas sent with Open Dialog/Modal
     */
    public static long getUserDatas() {
        return nGetUserDatas();
    }

    private static native long nGetUserDatas(); /*
        return (uintptr_t)ImGuiFileDialog::Instance()->GetUserDatas();
    */
}
