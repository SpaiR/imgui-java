package imgui.flag;

public final class ImDrawFlags {
    private ImDrawFlags() {
    }

    public static final int None = 0;
    /**
     * PathStroke(), AddPolyline(): specify that shape should be closed (Important: this is always == 1 for legacy reason)
     */
    public static final int Closed = 1;
    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding top-left corner only (when rounding {@code >} 0.0f, we default to all corners). Was 0x01.
     */
    public static final int RoundCornersTopLeft = 1 << 4;
    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding top-right corner only (when rounding {@code >} 0.0f, we default to all corners). Was 0x02.
     */
    public static final int RoundCornersTopRight = 1 << 5;
    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding bottom-left corner only (when rounding {@code >} 0.0f, we default to all corners). Was 0x04.
     */
    public static final int RoundCornersBottomLeft = 1 << 6;
    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding bottom-right corner only (when rounding {@code >} 0.0f, we default to all corners). Wax 0x08.
     */
    public static final int RoundCornersBottomRight = 1 << 7;
    /**
     * AddRect(), AddRectFilled(), PathRect(): disable rounding on all corners (when rounding {@code >} 0.0f). This is NOT zero, NOT an implicit flag!
     */
    public static final int RoundCornersNone = 1 << 8;
    public static final int RoundCornersTop = RoundCornersTopLeft | RoundCornersTopRight;
    public static final int RoundCornersBottom = RoundCornersBottomLeft | RoundCornersBottomRight;
    public static final int RoundCornersLeft = RoundCornersBottomLeft | RoundCornersTopLeft;
    public static final int RoundCornersRight = RoundCornersBottomRight | RoundCornersTopRight;
    public static final int RoundCornersAll = RoundCornersTopLeft | RoundCornersTopRight | RoundCornersBottomLeft | RoundCornersBottomRight;
    /**
     * Default to ALL corners if none of the _RoundCornersXX flags are specified.
     */
    public static final int RoundCornersDefault = RoundCornersAll;
    public static final int RoundCornersMask = RoundCornersAll | RoundCornersNone;
}
