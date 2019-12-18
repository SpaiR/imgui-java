package imgui.enums;

/**
 * Flags for ImGui::InputText()
 */
public class ImGuiInputTextFlags {
    public static ImGuiInputTextFlags None = new ImGuiInputTextFlags(0);
    /**
     * Allow 0123456789.+-*
     */
    public static ImGuiInputTextFlags CharsDecimal = new ImGuiInputTextFlags(1 << 0);
    /**
     * Allow 0123456789ABCDEFabcdef
     */
    public static ImGuiInputTextFlags CharsHexadecimal = new ImGuiInputTextFlags(1 << 1);
    /**
     * Turn a..z into A..Z
     */
    public static ImGuiInputTextFlags CharsUppercase = new ImGuiInputTextFlags(1 << 2);
    /**
     * Filter out spaces, tabs
     */
    public static ImGuiInputTextFlags CharsNoBlank = new ImGuiInputTextFlags(1 << 3);
    /**
     * Select entire text when first taking mouse focus
     */
    public static ImGuiInputTextFlags AutoSelectAll = new ImGuiInputTextFlags(1 << 4);
    /**
     * Return 'true' when Enter is pressed (as opposed to every time the value was modified). Consider looking at the IsItemDeactivatedAfterEdit() function.
     */
    public static ImGuiInputTextFlags EnterReturnsTrue = new ImGuiInputTextFlags(1 << 5);
    /**
     * Callback on pressing TAB (for completion handling)
     */
    public static ImGuiInputTextFlags CallbackCompletion = new ImGuiInputTextFlags(1 << 6);
    /**
     * Callback on pressing Up/Down arrows (for history handling)
     */
    public static ImGuiInputTextFlags CallbackHistory = new ImGuiInputTextFlags(1 << 7);
    /**
     * Callback on each iteration. User code may query cursor position, modify text buffer.
     */
    public static ImGuiInputTextFlags CallbackAlways = new ImGuiInputTextFlags(1 << 8);
    /**
     * Callback on character inputs to replace or discard them. Modify 'EventChar' to replace or discard, or return 1 in callback to discard.
     */
    public static ImGuiInputTextFlags CallbackCharFilter = new ImGuiInputTextFlags(1 << 9);
    /**
     * Pressing TAB input a '\t' character into the text field
     */
    public static ImGuiInputTextFlags AllowTabInput = new ImGuiInputTextFlags(1 << 10);
    /**
     * In multi-line mode, unfocus with Enter, add new line with Ctrl+Enter (default is opposite: unfocus with Ctrl+Enter, add line with Enter).
     */
    public static ImGuiInputTextFlags CtrlEnterForNewLine = new ImGuiInputTextFlags(1 << 11);
    /**
     * Disable following the cursor horizontally
     */
    public static ImGuiInputTextFlags NoHorizontalScroll = new ImGuiInputTextFlags(1 << 12);
    /**
     * Insert mode
     */
    public static ImGuiInputTextFlags AlwaysInsertMode = new ImGuiInputTextFlags(1 << 13);
    /**
     * Read-only mode
     */
    public static ImGuiInputTextFlags ReadOnly = new ImGuiInputTextFlags(1 << 14);
    /**
     * Password mode, display all characters as '*'
     */
    public static ImGuiInputTextFlags Password = new ImGuiInputTextFlags(1 << 15);
    /**
     * Disable undo/redo. Note that input text owns the text data while active, if you want to provide your own undo/redo stack you need e.g. to call ClearActiveID().
     */
    public static ImGuiInputTextFlags NoUndoRedo = new ImGuiInputTextFlags(1 << 16);
    /**
     * Allow 0123456789.+-*\\/eE (Scientific notation input)
     */
    public static ImGuiInputTextFlags CharsScientific = new ImGuiInputTextFlags(1 << 17);
    /**
     * Callback on buffer capacity changes request (beyond 'buf_size' parameter value), allowing the string to grow. Notify when the string wants to be resized (for string types which hold a cache of their Size). You will be provided a new BufSize in the callback and NEED to honor it. (see misc/cpp/imgui_stdlib.h for an example of using this)
     */
    public static ImGuiInputTextFlags CallbackResize = new ImGuiInputTextFlags(1 << 18);
    private static ImGuiInputTextFlags Custom = new ImGuiInputTextFlags(0);
    public int data;

    private ImGuiInputTextFlags(int code) {
        data = code;
    }

    public ImGuiInputTextFlags or(ImGuiInputTextFlags otherEnum) {
        ImGuiInputTextFlags.Custom.data = data | otherEnum.data;
        return ImGuiInputTextFlags.Custom;
    }

    public int getValue() {
        return data;
    }
}
