package imgui.flag;


/**
 * Flags for ImDrawList functions
 */
public final class ImDrawFlags {
    private ImDrawFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * PathStroke(), AddPolyline(): specify that shape should be closed (Important: this is always == 1 for legacy reason)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Closed = 1;

    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding top-left corner only (when rounding{@code >}0.0f, we default to all corners). Was 0x01.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int RoundCornersTopLeft = 16;

    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding top-right corner only (when rounding{@code >}0.0f, we default to all corners). Was 0x02.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int RoundCornersTopRight = 32;

    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding bottom-left corner only (when rounding{@code >}0.0f, we default to all corners). Was 0x04.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int RoundCornersBottomLeft = 64;

    /**
     * AddRect(), AddRectFilled(), PathRect(): enable rounding bottom-right corner only (when rounding{@code >}0.0f, we default to all corners). Wax 0x08.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int RoundCornersBottomRight = 128;

    /**
     * AddRect(), AddRectFilled(), PathRect(): disable rounding on all corners (when rounding{@code >}0.0f). This is NOT zero, NOT an implicit flag!
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int RoundCornersNone = 256;

    /**
     * Definition: {@code ImDrawFlags_RoundCornersTopLeft | ImDrawFlags_RoundCornersTopRight}
     */
    public static final int RoundCornersTop = 48;

    /**
     * Definition: {@code ImDrawFlags_RoundCornersBottomLeft | ImDrawFlags_RoundCornersBottomRight}
     */
    public static final int RoundCornersBottom = 192;

    /**
     * Definition: {@code ImDrawFlags_RoundCornersBottomLeft | ImDrawFlags_RoundCornersTopLeft}
     */
    public static final int RoundCornersLeft = 80;

    /**
     * Definition: {@code ImDrawFlags_RoundCornersBottomRight | ImDrawFlags_RoundCornersTopRight}
     */
    public static final int RoundCornersRight = 160;

    /**
     * Definition: {@code ImDrawFlags_RoundCornersTopLeft | ImDrawFlags_RoundCornersTopRight | ImDrawFlags_RoundCornersBottomLeft | ImDrawFlags_RoundCornersBottomRight}
     */
    public static final int RoundCornersAll = 240;

    /**
     * Default to ALL corners if none of the _RoundCornersXX flags are specified.
     *
     * <p>Definition: {@code ImDrawFlags_RoundCornersAll}
     */
    public static final int RoundCornersDefault_ = 240;

    /**
     * Definition: {@code ImDrawFlags_RoundCornersAll | ImDrawFlags_RoundCornersNone}
     */
    public static final int RoundCornersMask_ = 496;
}
