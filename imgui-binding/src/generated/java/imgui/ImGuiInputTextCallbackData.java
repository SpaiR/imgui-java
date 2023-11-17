package imgui;

import imgui.binding.ImGuiStruct;

/**
 * Shared state of InputText(), passed as an argument to your callback when a ImGuiInputTextFlags_Callback* flag is used.<p>
 * The callback function should return 0 by default.<p>
 * Callbacks (follow a flag name and see comments in ImGuiInputTextFlags_ declarations for more details)<p>
 * - ImGuiInputTextFlags_CallbackEdit:        Callback on buffer edit (note that InputText() already returns true on edit, the callback is useful mainly to manipulate the underlying buffer while focus is active)<p>
 * - ImGuiInputTextFlags_CallbackAlways:      Callback on each iteration<p>
 * - ImGuiInputTextFlags_CallbackCompletion:  Callback on pressing TAB<p>
 * - ImGuiInputTextFlags_CallbackHistory:     Callback on pressing Up/Down arrows<p>
 * - ImGuiInputTextFlags_CallbackCharFilter:  Callback on character inputs to replace or discard them. Modify 'EventChar' to replace or discard, or return 1 in callback to discard.<p>
 * - ImGuiInputTextFlags_CallbackResize:      Callback on buffer capacity changes request (beyond 'buf_size' parameter value), allowing the string to grow.<p>
 */
public class ImGuiInputTextCallbackData extends ImGuiStruct {
    public ImGuiInputTextCallbackData(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"

        #define IMGUI_CALLBACK_DATA ((ImGuiInputTextCallbackData*)STRUCT_PTR)
     */

    /**
     * One ImGuiInputTextFlags_Callback*
     *
     * @return ImGuiInputTextFlags
     */
    public native int getEventFlag(); /*
        return IMGUI_CALLBACK_DATA->EventFlag;
    */

    /**
     * What user passed to InputText()
     *
     * @return ImGuiInputTextFlags
     */
    public native int getFlags(); /*
        return IMGUI_CALLBACK_DATA->Flags;
    */

    /**
     * [CharFilter] Character input;
     *
     * @return Character input
     */
    public native int getEventChar(); /*
        return IMGUI_CALLBACK_DATA->EventChar;
    */

    /**
     * [CharFilter] Replace character with another one, or set to zero to drop. return 1 is equivalent to setting EventChar=0;
     *
     * @param c Replaced characters
     */
    public void setEventChar(final char c) {
        setEventChar((int) c);
    }

    /**
     * [CharFilter] Replace character with another one, or set to zero to drop. return 1 is equivalent to setting EventChar=0;
     *
     * @param c Replaced characters
     */
    public native void setEventChar(int c); /*
        IMGUI_CALLBACK_DATA->EventChar = c;
    */

    /**
     * [Completion,History]
     *
     * @return Key pressed (Up/Down/TAB)
     */
    public native int getEventKey(); /*
        return IMGUI_CALLBACK_DATA->EventKey;
    */

    /**
     * [Resize] Can replace pointer <p>
     * [Completion,History,Always] Only write to pointed data, don't replace the actual pointer!
     *
     * @return Buf
     */
    public native String getBuf(); /*
        return env->NewStringUTF(IMGUI_CALLBACK_DATA->Buf);
    */

    /**
     * Set if you modify Buf/BufTextLen!
     *
     * @return Dirty
     */
    public native boolean getBufDirty(); /*
        return IMGUI_CALLBACK_DATA->BufDirty;
    */

    /**
     * Set if you modify Buf/BufTextLen!
     *
     * @param dirty Dirty
     */
    public native void setBufDirty(boolean dirty); /*
        IMGUI_CALLBACK_DATA->BufDirty = dirty;
    */

    /**
     * Current cursor position
     *
     * @return Current cursor position
     */
    public native int getCursorPos(); /*
        return IMGUI_CALLBACK_DATA->CursorPos;
    */

    /**
     * Set the current cursor position
     *
     * @param pos Set the current cursor position
     */
    public native void setCursorPos(int pos); /*
        IMGUI_CALLBACK_DATA->CursorPos = pos;
    */

    /**
     * Selection Start
     *
     * @return Selection Start
     */
    public native int getSelectionStart(); /*
        return IMGUI_CALLBACK_DATA->SelectionStart;
    */

    /**
     * Set Selection Start
     *
     * @param pos Selection Start
     */
    public native void setSelectionStart(int pos); /*
        IMGUI_CALLBACK_DATA->SelectionStart = pos;
    */

    /**
     * Selection End
     *
     * @return Selection End
     */
    public native int getSelectionEnd(); /*
        return IMGUI_CALLBACK_DATA->SelectionEnd;
    */

    /**
     * Set Selection End
     *
     * @param pos Selection End
     */
    public native void setSelectionEnd(int pos); /*
        IMGUI_CALLBACK_DATA->SelectionEnd = pos;
    */

    /**
     * Delete Chars
     *
     * @param pos        Start Delete Pos
     * @param bytesCount Delete Char Count
     */
    public native void deleteChars(int pos, int bytesCount); /*
        IMGUI_CALLBACK_DATA->DeleteChars(pos, bytesCount);
    */

    /**
     * Insert Chars
     *
     * @param pos insert Psos
     * @param str insert String
     */
    public native void insertChars(int pos, String str); /*
        IMGUI_CALLBACK_DATA->InsertChars(pos, str);
    */
}
