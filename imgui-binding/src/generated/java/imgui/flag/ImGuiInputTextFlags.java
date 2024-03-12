package imgui.flag;

/**
 * Flags for ImGui::InputText()
 */
public final class ImGuiInputTextFlags {
    private ImGuiInputTextFlags() {
    }

    public static final int None = 0;
    /**
     * Allow 0123456789.+-* /
     */
    public static final int CharsDecimal = 1;
    /**
     * Allow 0123456789ABCDEFabcdef
     */
    public static final int CharsHexadecimal = 1 << 1;
    /**
     * Turn a..z into A..Z
     */
    public static final int CharsUppercase = 1 << 2;
    /**
     * Filter out spaces, tabs
     */
    public static final int CharsNoBlank = 1 << 3;
    /**
     * Select entire text when first taking mouse focus
     */
    public static final int AutoSelectAll = 1 << 4;
    /**
     * Return 'true' when Enter is pressed (as opposed to every time the value was modified). Consider looking at the IsItemDeactivatedAfterEdit() function.
     */
    public static final int EnterReturnsTrue = 1 << 5;
    /**
     * Callback on pressing TAB (for completion handling)
     */
    public static final int CallbackCompletion = 1 << 6;
    /**
     * Callback on pressing Up/Down arrows (for history handling)
     */
    public static final int CallbackHistory = 1 << 7;
    /**
     * Callback on each iteration. User code may query cursor position, modify text buffer.
     */
    public static final int CallbackAlways = 1 << 8;
    /**
     * Callback on character inputs to replace or discard them. Modify 'EventChar' to replace or discard, or return 1 in callback to discard.
     */
    public static final int CallbackCharFilter = 1 << 9;
    /**
     * Pressing TAB input a '\t' character into the text field
     */
    public static final int AllowTabInput = 1 << 10;
    /**
     * In multi-line mode, unfocus with Enter, add new line with Ctrl+Enter (default is opposite: unfocus with Ctrl+Enter, add line with Enter).
     */
    public static final int CtrlEnterForNewLine = 1 << 11;
    /**
     * Disable following the cursor horizontally
     */
    public static final int NoHorizontalScroll = 1 << 12;
    /**
     * Overwrite mode
     */
    public static final int AlwaysOverwrite = 1 << 13;
    /**
     * Read-only mode
     */
    public static final int ReadOnly = 1 << 14;
    /**
     * Password mode, display all characters as '*'
     */
    public static final int Password = 1 << 15;
    /**
     * Disable undo/redo. Note that input text owns the text data while active, if you want to provide your own undo/redo stack you need e.g. to call ClearActiveID().
     */
    public static final int NoUndoRedo = 1 << 16;
    /**
     * Allow 0123456789.+-* /eE (Scientific notation input)
     */
    public static final int CharsScientific = 1 << 17;
    /**
     * Callback on buffer capacity changes request (beyond 'buf_size' parameter value), allowing the string to grow.
     * Notify when the string wants to be resized (for string types which hold a cache of their Size).
     * You will be provided a new BufSize in the callback and NEED to honor it. (see misc/cpp/imgui_stdlib.h for an example of using this)
     */
    public static final int CallbackResize = 1 << 18;
    /**
     * Callback on any edit (note that InputText() already returns true on edit, the callback is useful mainly to manipulate the underlying buffer while focus is active).
     */
    public static final int CallbackEdit = 1 << 19;
}
