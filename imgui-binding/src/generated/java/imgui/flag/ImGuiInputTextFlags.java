package imgui.flag;


/**
 * Flags for ImGui::InputText()
 */
public final class ImGuiInputTextFlags {
    private ImGuiInputTextFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Allow 0123456789.+-* /
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int CharsDecimal = 1;

    /**
     * Allow 0123456789ABCDEFabcdef
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int CharsHexadecimal = 2;

    /**
     * Turn a..z into A..Z
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int CharsUppercase = 4;

    /**
     * Filter out spaces, tabs
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int CharsNoBlank = 8;

    /**
     * Select entire text when first taking mouse focus
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int AutoSelectAll = 16;

    /**
     * Return 'true' when Enter is pressed (as opposed to every time the value was modified). Consider looking at the IsItemDeactivatedAfterEdit() function.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int EnterReturnsTrue = 32;

    /**
     * Callback on pressing TAB (for completion handling)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int CallbackCompletion = 64;

    /**
     * Callback on pressing Up/Down arrows (for history handling)
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int CallbackHistory = 128;

    /**
     * Callback on each iteration. User code may query cursor position, modify text buffer.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int CallbackAlways = 256;

    /**
     * Callback on character inputs to replace or discard them. Modify 'EventChar' to replace or discard, or return 1 in callback to discard.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int CallbackCharFilter = 512;

    /**
     * Pressing TAB input a ' ' character into the text field
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int AllowTabInput = 1024;

    /**
     * In multi-line mode, unfocus with Enter, add new line with Ctrl+Enter (default is opposite: unfocus with Ctrl+Enter, add line with Enter).
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int CtrlEnterForNewLine = 2048;

    /**
     * Disable following the cursor horizontally
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int NoHorizontalScroll = 4096;

    /**
     * Overwrite mode
     *
     * <p>Definition: {@code 1 << 13}
     */
    public static final int AlwaysOverwrite = 8192;

    /**
     * Read-only mode
     *
     * <p>Definition: {@code 1 << 14}
     */
    public static final int ReadOnly = 16384;

    /**
     * Password mode, display all characters as '*'
     *
     * <p>Definition: {@code 1 << 15}
     */
    public static final int Password = 32768;

    /**
     * Disable undo/redo. Note that input text owns the text data while active, if you want to provide your own undo/redo stack you need e.g. to call ClearActiveID().
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int NoUndoRedo = 65536;

    /**
     * Allow 0123456789.+-* /eE (Scientific notation input)
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int CharsScientific = 131072;

    /**
     * Callback on buffer capacity changes request (beyond 'buf_size' parameter value), allowing the string to grow. Notify when the string wants to be resized (for string types which hold a cache of their Size). You will be provided a new BufSize in the callback and NEED to honor it. (see misc/cpp/imgui_stdlib.h for an example of using this)
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int CallbackResize = 262144;

    /**
     * Callback on any edit (note that InputText() already returns true on edit, the callback is useful mainly to manipulate the underlying buffer while focus is active)
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int CallbackEdit = 524288;

    /**
     * Escape key clears content if not empty, and deactivate otherwise (contrast to default behavior of Escape to revert)
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int EscapeClearsAll = 1048576;
}
