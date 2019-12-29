package imgui.enums;

/**
 * Enumeration for PushStyleVar() / PopStyleVar() to temporarily modify the ImGuiStyle structure.
 * NB: the enum only refers to fields of ImGuiStyle which makes sense to be pushed/popped inside UI code. During initialization, feel free to just poke into ImGuiStyle directly.
 * NB: if changing this enum, you need to update the associated internal table GStyleVarInfo[] accordingly. This is where we link enum values to members offset/type.
 */
public final class ImGuiStyleVar {
    private ImGuiStyleVar() {
    }

    public static final int Alpha = 0;                // float     Alpha
    public static final int WindowPadding = 1;        // ImVec2    WindowPadding
    public static final int WindowRounding = 2;       // float     WindowRounding
    public static final int WindowBorderSize = 3;     // float     WindowBorderSize
    public static final int WindowMinSize = 4;        // ImVec2    WindowMinSize
    public static final int WindowTitleAlign = 5;     // ImVec2    WindowTitleAlign
    public static final int ChildRounding = 6;        // float     ChildRounding
    public static final int ChildBorderSize = 7;      // float     ChildBorderSize
    public static final int PopupRounding = 8;        // float     PopupRounding
    public static final int PopupBorderSize = 9;      // float     PopupBorderSize
    public static final int FramePadding = 10;        // ImVec2    FramePadding
    public static final int FrameRounding = 11;       // float     FrameRounding
    public static final int FrameBorderSize = 12;     // float     FrameBorderSize
    public static final int ItemSpacing = 13;         // ImVec2    ItemSpacing
    public static final int ItemInnerSpacing = 14;    // ImVec2    ItemInnerSpacing
    public static final int IndentSpacing = 15;       // float     IndentSpacing
    public static final int ScrollbarSize = 16;       // float     ScrollbarSize
    public static final int ScrollbarRounding = 17;   // float     ScrollbarRounding
    public static final int GrabMinSize = 18;         // float     GrabMinSize
    public static final int GrabRounding = 19;        // float     GrabRounding
    public static final int TabRounding = 20;         // float     TabRounding
    public static final int ButtonTextAlign = 21;     // ImVec2    ButtonTextAlign
    public static final int SelectableTextAlign = 22; // ImVec2    SelectableTextAlign
    public static final int COUNT = 23;
}
