package imgui.flag;


/**
 * Enumeration for PushStyleVar() / PopStyleVar() to temporarily modify the ImGuiStyle structure.
 * - The enum only refers to fields of ImGuiStyle which makes sense to be pushed/popped inside UI code.
 *   During initialization or between frames, feel free to just poke into ImGuiStyle directly.
 * - Tip: Use your programming IDE navigation facilities on the names in the _second column_ below to find the actual members and their description.
 *   In Visual Studio IDE: CTRL+comma ("Edit.NavigateTo") can follow symbols in comments, whereas CTRL+F12 ("Edit.GoToImplementation") cannot.
 *   With Visual Assist installed: ALT+G ("VAssistX.GoToImplementation") can also follow symbols in comments.
 * - When changing this enum, you need to update the associated internal table GStyleVarInfo[] accordingly. This is where we link enum values to members offset/type.
 */
public final class ImGuiStyleVar {
    private ImGuiStyleVar() {
    }

    /**
     * float     Alpha
     */
    public static final int Alpha = 0;

    /**
     * float     DisabledAlpha
     */
    public static final int DisabledAlpha = 1;

    /**
     * ImVec2    WindowPadding
     */
    public static final int WindowPadding = 2;

    /**
     * float     WindowRounding
     */
    public static final int WindowRounding = 3;

    /**
     * float     WindowBorderSize
     */
    public static final int WindowBorderSize = 4;

    /**
     * ImVec2    WindowMinSize
     */
    public static final int WindowMinSize = 5;

    /**
     * ImVec2    WindowTitleAlign
     */
    public static final int WindowTitleAlign = 6;

    /**
     * float     ChildRounding
     */
    public static final int ChildRounding = 7;

    /**
     * float     ChildBorderSize
     */
    public static final int ChildBorderSize = 8;

    /**
     * float     PopupRounding
     */
    public static final int PopupRounding = 9;

    /**
     * float     PopupBorderSize
     */
    public static final int PopupBorderSize = 10;

    /**
     * ImVec2    FramePadding
     */
    public static final int FramePadding = 11;

    /**
     * float     FrameRounding
     */
    public static final int FrameRounding = 12;

    /**
     * float     FrameBorderSize
     */
    public static final int FrameBorderSize = 13;

    /**
     * ImVec2    ItemSpacing
     */
    public static final int ItemSpacing = 14;

    /**
     * ImVec2    ItemInnerSpacing
     */
    public static final int ItemInnerSpacing = 15;

    /**
     * float     IndentSpacing
     */
    public static final int IndentSpacing = 16;

    /**
     * ImVec2    CellPadding
     */
    public static final int CellPadding = 17;

    /**
     * float     ScrollbarSize
     */
    public static final int ScrollbarSize = 18;

    /**
     * float     ScrollbarRounding
     */
    public static final int ScrollbarRounding = 19;

    /**
     * float     GrabMinSize
     */
    public static final int GrabMinSize = 20;

    /**
     * float     GrabRounding
     */
    public static final int GrabRounding = 21;

    /**
     * float     TabRounding
     */
    public static final int TabRounding = 22;

    /**
     * float     TabBorderSize
     */
    public static final int TabBorderSize = 23;

    /**
     * float     TabBarBorderSize
     */
    public static final int TabBarBorderSize = 24;

    /**
     * float     TableAngledHeadersAngle
     */
    public static final int TableAngledHeadersAngle = 25;

    /**
     * ImVec2  TableAngledHeadersTextAlign
     */
    public static final int TableAngledHeadersTextAlign = 26;

    /**
     * ImVec2    ButtonTextAlign
     */
    public static final int ButtonTextAlign = 27;

    /**
     * ImVec2    SelectableTextAlign
     */
    public static final int SelectableTextAlign = 28;

    /**
     * float     SeparatorTextBorderSize
     */
    public static final int SeparatorTextBorderSize = 29;

    /**
     * ImVec2    SeparatorTextAlign
     */
    public static final int SeparatorTextAlign = 30;

    /**
     * ImVec2    SeparatorTextPadding
     */
    public static final int SeparatorTextPadding = 31;

    /**
     * float     DockingSeparatorSize
     */
    public static final int DockingSeparatorSize = 32;

    public static final int COUNT = 33;
}
