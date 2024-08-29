package imgui.extension.imguifiledialog;

import imgui.ImVec2;
import imgui.binding.annotation.ArgValue;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.OptArg;
import imgui.binding.annotation.ReturnValue;
import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;

import java.util.HashMap;

/**
 * ImGuiFileDialog extension for ImGui
 * Repo: <a href="https://github.com/aiekick/ImGuiFileDialog">https://github.com/aiekick/ImGuiFileDialog</a>
 */
@BindingSource(callPtr = "ImGuiFileDialog::Instance()", callOperator = "->")
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
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vPath              path
     * @param vFileName          default file name
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    /**
     * Open simple dialog (path and filename are obtained from filePathName)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName      file path name (will be decompsoed in path and fileName)
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    /**
     * Open dialog with custom right pane (path and fileName can be specified)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vPath              path
     * @param vFileName          default file name
     * @param vSidePane          side pane
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenDialog(String vKey, String vTitle, String vFilters, String vPath, String vFileName, @ArgValue(callValue = "PaneFunCallback(env, vSidePane)") ImGuiFileDialogPaneFun vSidePane, @OptArg(callValue = "250.0f") float vSidePaneWidth, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    /**
     * Open dialog with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName      file path name (will be decompsoed in path and fileName)
     * @param vSidePane          side pane
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenDialog(String vKey, String vTitle, String vFilters, String vFilePathName, @ArgValue(callValue = "PaneFunCallback(env, vSidePane)") ImGuiFileDialogPaneFun vSidePane, @OptArg(callValue = "250.0f") float vSidePaneWidth, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);


    /**
     * Open simple modal (path and fileName can be specified)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vPath              path
     * @param vFileName          default file name
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    /**
     * open simple modal (path and filename are obtained from filePathName)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName      file path name (will be decomposed in path and fileName)
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    //, @ArgValue(callValue = "PaneFunCallback(env, vSidePane)") ImGuiFileDialogPaneFun vSidePane, @OptArg(callValue = "250.0f") float vSidePaneWidth

    /**
     * Open modal with custom right pane (path and fileName can be specified)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vPath              path
     * @param vFileName          default file name
     * @param vSidePane          side pane
     * @param vSidePaneWidth     side pane width
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenModal(String vKey, String vTitle, String vFilters, String vPath, String vFileName, @ArgValue(callValue = "PaneFunCallback(env, vSidePane)") ImGuiFileDialogPaneFun vSidePane, @OptArg(callValue = "250.0f") float vSidePaneWidth, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    /**
     * Open modal with custom right pane (path and filename are obtained from filePathName)
     *
     * @param vKey               key dialog
     * @param vTitle             title
     * @param vFilters           filters (in comma separated form i.e. ".png,.jpg" or ".*") or null for directories
     * @param vFilePathName      file path name (will be decomposed in path and fileName)
     * @param vSidePane          side pane
     * @param vSidePaneWidth     side pane width
     * @param vCountSelectionMax count selection max
     * @param vUserDatas         user datas (can be retrieved in pane)
     * @param vFlags             ImGuiFileDialogFlags
     */
    @BindingMethod
    public static native void OpenModal(String vKey, String vTitle, String vFilters, String vFilePathName, @ArgValue(callValue = "PaneFunCallback(env, vSidePane)") ImGuiFileDialogPaneFun vSidePane, @OptArg(callValue = "250.0f") float vSidePaneWidth, @OptArg int vCountSelectionMax, @OptArg @ArgValue(reinterpretCast = "void*") long vUserDatas, @OptArg int vFlags);

    /**
     * Display / Close dialog form
     * Display the dialog. return true if a result was obtained (Ok or not)
     *
     * @param vKey     key dialog to display (if not the same key as defined by OpenDialog/Modal =&gt; no opening)
     * @param vFlags   ImGuiWindowFlags
     * @param vMinSize minimal size constraint for the ImGuiWindow
     * @param vMaxSize maximal size constraint for the ImGuiWindow
     * @return true if a result was obtained (Ok or not)
     */
    @BindingMethod
    public static native boolean Display(String vKey, @OptArg int vFlags, @OptArg ImVec2 vMinSize, @OptArg ImVec2 vMaxSize);


    /**
     * Close dialog
     */
    @BindingMethod
    public static native void Close();


    /**
     * Say if the dialog key was already opened this frame
     *
     * @param vKey key dialog
     * @return if the dialog key was already opened this frame
     */
    @BindingMethod
    public static native boolean WasOpenedThisFrame(@OptArg String vKey);

    /**
     * Say if the key is opened
     *
     * @param vKey key dialog
     * @return if the key is opened
     */
    @BindingMethod
    public static native boolean IsOpened(@OptArg String vKey);

    /**
     * Return the dialog key who is opened, return nothing if not opened
     *
     * @return the dialog key who is opened or nothing is not opened
     */
    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public static native String GetOpenedKey();


    /**
     * true: Dialog Closed with Ok result / false: Dialog closed with cancel result
     *
     * @return True if the dialog closed with Ok result, or false with cancel result
     */
    @BindingMethod
    public static native boolean IsOk();


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
    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public static native String GetFilePathName();

    /**
     * Save File behavior : will always return the content of the field with current filter extension
     *
     * @return the content of the field with current filter extension
     */
    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public static native String GetCurrentFileName();

    /**
     * Will return current path
     *
     * @return the current path
     */
    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public static native String GetCurrentPath();

    /**
     * Will return selected filter
     *
     * @return the selected filter
     */
    @BindingMethod
    @ReturnValue(callSuffix = ".c_str()")
    public static native String GetCurrentFilter();


    /**
     * Will return user datas sent with Open Dialog/Modal
     * <p>
     * Can be used to pass a long value to the dialog and get the value back.
     * This long value can be a pointer to a native data structure.
     *
     * @return user datas sent with Open Dialog/Modal
     */
    @BindingMethod
    @ReturnValue(callPrefix = "(uintptr_t)")
    public static native long GetUserDatas();
}
