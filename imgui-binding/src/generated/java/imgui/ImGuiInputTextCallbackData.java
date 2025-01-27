package imgui;

import imgui.binding.ImGuiStruct;
import imgui.internal.ImGuiContext;

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
        #define THIS ((ImGuiInputTextCallbackData*)STRUCT_PTR)
     */

    private static final ImGuiContext _GETCTX_1 = new ImGuiContext(0);

    /**
     * Parent UI context
     */
    public ImGuiContext getCtx() {
        _GETCTX_1.ptr = nGetCtx();
        return _GETCTX_1;
    }

    /**
     * Parent UI context
     */
    public void setCtx(final ImGuiContext value) {
        nSetCtx(value.ptr);
    }

    private native long nGetCtx(); /*
        return (uintptr_t)THIS->Ctx;
    */

    private native void nSetCtx(long value); /*
        THIS->Ctx = reinterpret_cast<ImGuiContext*>(value);
    */

    /**
     * One ImGuiInputTextFlags_Callback*
     */
    public int getEventFlag() {
        return nGetEventFlag();
    }

    /**
     * One ImGuiInputTextFlags_Callback*
     */
    public boolean hasEventFlag(final int flags) {
        return (getEventFlag() & flags) != 0;
    }

    private native int nGetEventFlag(); /*
        return THIS->EventFlag;
    */

    /**
     * What user passed to InputText()
     */
    public int getFlags() {
        return nGetFlags();
    }

    /**
     * What user passed to InputText()
     */
    public boolean hasFlags(final int flags) {
        return (getFlags() & flags) != 0;
    }

    private native int nGetFlags(); /*
        return THIS->Flags;
    */

    // TODO: UserData

    /**
     * [CharFilter] Replace character with another one, or set to zero to drop. return 1 is equivalent to setting EventChar=0;
     */
    public int getEventChar() {
        return nGetEventChar();
    }

    /**
     * [CharFilter] Replace character with another one, or set to zero to drop. return 1 is equivalent to setting EventChar=0;
     */
    public void setEventChar(final int value) {
        nSetEventChar(value);
    }

    private native int nGetEventChar(); /*
        return THIS->EventChar;
    */

    private native void nSetEventChar(int value); /*
        THIS->EventChar = value;
    */

    /**
     * [Completion,History]
     */
    public int getEventKey() {
        return nGetEventKey();
    }

    private native int nGetEventKey(); /*
        return THIS->EventKey;
    */

    /**
     * [Resize] Can replace pointer <p>
     * [Completion,History,Always] Only write to pointed data, don't replace the actual pointer!
     */
    public String getBuf() {
        return nGetBuf();
    }

    /**
     * [Resize] Can replace pointer <p>
     * [Completion,History,Always] Only write to pointed data, don't replace the actual pointer!
     */
    public void setBuf(final String value) {
        nSetBuf(value);
    }

    private native String nGetBuf(); /*
        return env->NewStringUTF(THIS->Buf);
    */

    private native void nSetBuf(String value); /*MANUAL
        auto value = obj_value == NULL ? NULL : (char*)env->GetStringUTFChars(obj_value, JNI_FALSE);
        SET_STRING_FIELD(THIS->Buf, value);
        if (value != NULL) env->ReleaseStringUTFChars(obj_value, value);
    */

    /**
     * [Resize,Completion,History,Always] Exclude zero-terminator storage. In C land: == strlen(some_text), in C++ land: string.length()
     */
    public int getBufTextLen() {
        return nGetBufTextLen();
    }

    /**
     * [Resize,Completion,History,Always] Exclude zero-terminator storage. In C land: == strlen(some_text), in C++ land: string.length()
     */
    public void setBufTextLen(final int value) {
        nSetBufTextLen(value);
    }

    private native int nGetBufTextLen(); /*
        return THIS->BufTextLen;
    */

    private native void nSetBufTextLen(int value); /*
        THIS->BufTextLen = value;
    */

    /**
     * Set if you modify Buf/BufTextLen!
     */
    public boolean getBufDirty() {
        return nGetBufDirty();
    }

    /**
     * Set if you modify Buf/BufTextLen!
     */
    public void setBufDirty(final boolean value) {
        nSetBufDirty(value);
    }

    private native boolean nGetBufDirty(); /*
        return THIS->BufDirty;
    */

    private native void nSetBufDirty(boolean value); /*
        THIS->BufDirty = value;
    */

    /**
     * Current cursor position
     */
    public int getCursorPos() {
        return nGetCursorPos();
    }

    /**
     * Current cursor position
     */
    public void setCursorPos(final int value) {
        nSetCursorPos(value);
    }

    private native int nGetCursorPos(); /*
        return THIS->CursorPos;
    */

    private native void nSetCursorPos(int value); /*
        THIS->CursorPos = value;
    */

    /**
     * Selection Start
     */
    public int getSelectionStart() {
        return nGetSelectionStart();
    }

    /**
     * Selection Start
     */
    public void setSelectionStart(final int value) {
        nSetSelectionStart(value);
    }

    private native int nGetSelectionStart(); /*
        return THIS->SelectionStart;
    */

    private native void nSetSelectionStart(int value); /*
        THIS->SelectionStart = value;
    */

    /**
     * Selection End
     */
    public int getSelectionEnd() {
        return nGetSelectionEnd();
    }

    /**
     * Selection End
     */
    public void setSelectionEnd(final int value) {
        nSetSelectionEnd(value);
    }

    private native int nGetSelectionEnd(); /*
        return THIS->SelectionEnd;
    */

    private native void nSetSelectionEnd(int value); /*
        THIS->SelectionEnd = value;
    */

    /**
     * Delete Chars
     *
     * @param pos
     * 		Start Delete Pos
     * @param bytesCount
     * 		Delete Char Count
     */
    public void deleteChars(final int pos, final int bytesCount) {
        nDeleteChars(pos, bytesCount);
    }

    private native void nDeleteChars(int pos, int bytesCount); /*
        THIS->DeleteChars(pos, bytesCount);
    */

    /**
     * Insert Chars
     *
     * @param pos
     * 		insert Psos
     * @param str
     * 		insert String
     */
    public void insertChars(final int pos, final String str) {
        nInsertChars(pos, str);
    }

    private native void nInsertChars(int pos, String str); /*MANUAL
        auto str = obj_str == NULL ? NULL : (char*)env->GetStringUTFChars(obj_str, JNI_FALSE);
        THIS->InsertChars(pos, str);
        if (str != NULL) env->ReleaseStringUTFChars(obj_str, str);
    */

    public void selectAll() {
        nSelectAll();
    }

    private native void nSelectAll(); /*
        THIS->SelectAll();
    */

    public void clearSelection() {
        nClearSelection();
    }

    private native void nClearSelection(); /*
        THIS->ClearSelection();
    */

    public boolean hasSelection() {
        return nHasSelection();
    }

    private native boolean nHasSelection(); /*
        return THIS->HasSelection();
    */

    /*JNI
        #undef THIS
     */
}
