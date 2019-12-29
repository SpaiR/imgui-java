package imgui.enums;

/**
 * A primary data type
 */
public final class ImGuiDataType {
    private ImGuiDataType() {
    }

    public static final int S8 = 0;     // signed char / char (with sensible compilers)
    public static final int U8 = 1;     // unsigned char
    public static final int S16 = 2;    // short
    public static final int U16 = 3;    // unsigned short
    public static final int S32 = 4;    // int
    public static final int U32 = 5;    // unsigned int
    public static final int S64 = 6;    // long long / __int64
    public static final int U64 = 7;    // unsigned long long / unsigned __int64
    public static final int Float = 8;  // float
    public static final int Double = 9; // double
    public static final int COUNT = 10;
}
