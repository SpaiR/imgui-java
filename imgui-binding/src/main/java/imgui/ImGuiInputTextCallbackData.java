package imgui;

import imgui.binding.ImGuiStruct;
import imgui.binding.annotation.BindingField;
import imgui.binding.annotation.BindingMethod;
import imgui.binding.annotation.BindingSource;
import imgui.binding.annotation.ReturnValue;
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
@BindingSource
public class ImGuiInputTextCallbackData extends ImGuiStruct {
    public ImGuiInputTextCallbackData(final long ptr) {
        super(ptr);
    }

    /*JNI
        #include "_common.h"
        #define THIS ((ImGuiInputTextCallbackData*)STRUCT_PTR)
     */

    /**
     * Parent UI context
     */
    @BindingField
    @ReturnValue(isStatic = true)
    public ImGuiContext Ctx;

    /**
     * One ImGuiInputTextFlags_Callback*
     */
    @BindingField(isFlag = true, accessors = BindingField.Accessor.GETTER)
    public int EventFlag;

    /**
     * What user passed to InputText()
     */
    @BindingField(isFlag = true, accessors = BindingField.Accessor.GETTER)
    public int Flags;

    // TODO: UserData

    /**
     * [CharFilter] Replace character with another one, or set to zero to drop. return 1 is equivalent to setting EventChar=0;
     */
    @BindingField
    public int EventChar;

    /**
     * [Completion,History]
     */
    @BindingField(accessors = BindingField.Accessor.GETTER)
    public int EventKey;

    /**
     * [Resize] Can replace pointer <p>
     * [Completion,History,Always] Only write to pointed data, don't replace the actual pointer!
     */
    @BindingField
    public String Buf;

    /**
     * [Resize,Completion,History,Always] Exclude zero-terminator storage. In C land: == strlen(some_text), in C++ land: string.length()
     */
    @BindingField
    public int BufTextLen;

    /**
     * Set if you modify Buf/BufTextLen!
     */
    @BindingField
    public boolean BufDirty;

    /**
     * Current cursor position
     */
    @BindingField
    public int CursorPos;

    /**
     * Selection Start
     */
    @BindingField
    public int SelectionStart;

    /**
     * Selection End
     */
    @BindingField
    public int SelectionEnd;

    /**
     * Delete Chars
     *
     * @param pos        Start Delete Pos
     * @param bytesCount Delete Char Count
     */
    @BindingMethod
    public native void DeleteChars(int pos, int bytesCount);

    /**
     * Insert Chars
     *
     * @param pos insert Psos
     * @param str insert String
     */
    @BindingMethod
    public native void InsertChars(int pos, String str);

    @BindingMethod
    public native void SelectAll();

    @BindingMethod
    public native void ClearSelection();

    @BindingMethod
    public native boolean HasSelection();

    /*JNI
        #undef THIS
     */
}
