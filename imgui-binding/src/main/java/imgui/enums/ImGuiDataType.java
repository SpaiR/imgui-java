package imgui.enums;

/**
 * A primary data type
 */
public enum ImGuiDataType {
    S8(0),
    U8(1),
    S16(2),
    U16(3),
    S32(4),
    U32(5),
    S64(6),
    U64(7),
    Float(8),
    Double(9),
    COUNT(10);

    int value;

    ImGuiDataType(int code) {
        value = code;
    }

    public int getValue() {
        return value;
    }
}
