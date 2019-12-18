package imgui.enums;

public enum ImGuiDir {
    None(-1),
    Left(0),
    Right(1),
    Up(2),
    Down(3),
    COUNT(4);

    private final int code;

    ImGuiDir(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
