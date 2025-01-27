package imgui.flag;


public final class ImGuiSliderFlags {
    private ImGuiSliderFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Clamp value to min/max bounds when input manually with CTRL+Click. By default CTRL+Click allows going out of bounds.
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int AlwaysClamp = 16;

    /**
     * Make the widget logarithmic (linear otherwise). Consider using ImGuiSliderFlags_NoRoundToFormat with this if using a format-string with small amount of digits.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int Logarithmic = 32;

    /**
     * Disable rounding underlying value to match precision of the display format string (e.g. %.3f values are rounded to those 3 digits)
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoRoundToFormat = 64;

    /**
     * Disable CTRL+Click or Enter key allowing to input text directly into the widget
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoInput = 128;

    /**
     * [Internal] We treat using those bits as being potentially a 'float power' argument from the previous API that has got miscast to this enum, and will trigger an assert if needed.
     *
     * <p>Definition: {@code 0x7000000F}
     */
    public static final int InvalidMask_ = 1879048207;
}
