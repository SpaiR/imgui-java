package imgui;

import imgui.binding.ImGuiStruct;

public class ImGuiInputTextCallbackData extends ImGuiStruct {
    public ImGuiInputTextCallbackData(long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IMGUI_CALLBACK_DATA ((ImGuiInputTextCallbackData*)STRUCT_PTR)
     */


    public native int getEventFlag(); /*
        return IMGUI_CALLBACK_DATA->EventFlag;
    */

    public native int getFlags(); /*
        return IMGUI_CALLBACK_DATA->Flags;
    */

    public native int getEventChar(); /*
        return IMGUI_CALLBACK_DATA->EventChar;
    */

    public native void setEventChar(char c); /*
        IMGUI_CALLBACK_DATA->EventChar = c;
    */

    public native int getEventKey(); /*
        return IMGUI_CALLBACK_DATA->EventKey;
    */

    public native String getBuf(); /*
        return env->NewStringUTF(IMGUI_CALLBACK_DATA->Buf);
    */

    public native boolean getBufDirty(); /*
        return IMGUI_CALLBACK_DATA->BufDirty;
    */

    public native void setBufDirty(boolean dirty); /*
        IMGUI_CALLBACK_DATA->BufDirty = dirty;
    */

    public native int getCursorPos(); /*
        return IMGUI_CALLBACK_DATA->CursorPos;
    */

    public native void setCursorPos(int pos); /*
        IMGUI_CALLBACK_DATA->CursorPos = pos;
    */

    public native int getSelectionStart(); /*
        return IMGUI_CALLBACK_DATA->SelectionStart;
    */

    public native void setSelectionStart(int pos); /*
        IMGUI_CALLBACK_DATA->SelectionStart = pos;
    */

    public native int getSelectionEnd(); /*
        return IMGUI_CALLBACK_DATA->SelectionEnd;
    */

    public native void setSelectionEnd(int pos); /*
        IMGUI_CALLBACK_DATA->SelectionEnd = pos;
    */

    public native void deleteChars(int pos, int bytes_count); /*
        IMGUI_CALLBACK_DATA->DeleteChars(pos, bytes_count);
    */

    public native void insertChars(int pos, String str); /*
        IMGUI_CALLBACK_DATA->InsertChars(pos, str);
    */
}
