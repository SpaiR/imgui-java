package imgui.flag;


/**
 * User fill ImGuiIO.KeyMap[] array with indices into the ImGuiIO.KeysDown[512] array
 */
public final class ImGuiKey {
    private ImGuiKey() {
    }

    public static final int Tab = 0;

    public static final int LeftArrow = 1;

    public static final int RightArrow = 2;

    public static final int UpArrow = 3;

    public static final int DownArrow = 4;

    public static final int PageUp = 5;

    public static final int PageDown = 6;

    public static final int Home = 7;

    public static final int End = 8;

    public static final int Insert = 9;

    public static final int Delete = 10;

    public static final int Backspace = 11;

    public static final int Space = 12;

    public static final int Enter = 13;

    public static final int Escape = 14;

    public static final int KeyPadEnter = 15;

    /**
     * for text edit CTRL+A: select all
     */
    public static final int A = 16;

    /**
     * for text edit CTRL+C: copy
     */
    public static final int C = 17;

    /**
     * for text edit CTRL+V: paste
     */
    public static final int V = 18;

    /**
     * for text edit CTRL+X: cut
     */
    public static final int X = 19;

    /**
     * for text edit CTRL+Y: redo
     */
    public static final int Y = 20;

    /**
     * for text edit CTRL+Z: undo
     */
    public static final int Z = 21;

    public static final int COUNT = 22;
}
