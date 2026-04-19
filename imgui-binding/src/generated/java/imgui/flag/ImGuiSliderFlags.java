package imgui.flag;


public final class ImGuiSliderFlags {
    private ImGuiSliderFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Make the widget logarithmic (linear otherwise). Consider using ImGuiSliderFlags_NoRoundToFormat with this if using a format-string with small amount of digits.
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int Logarithmic = 32;

    /**
     * Disable rounding underlying value to match precision of the display format string (e.g. %.3f values are rounded to those 3 digits).
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoRoundToFormat = 64;

    /**
     * Disable Ctrl+Click or Enter key allowing to input text directly into the widget.
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoInput = 128;

    /**
     * Enable wrapping around from max to min and from min to max. Only supported by DragXXX() functions for now.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int WrapAround = 256;

    /**
     * Clamp value to min/max bounds when input manually with Ctrl+Click. By default Ctrl+Click allows going out of bounds.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int ClampOnInput = 512;

    /**
     * Clamp even if min==max==0.0f. Otherwise due to legacy reason DragXXX functions don't clamp with those values. When your clamping limits are dynamic you almost always want to use it.
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int ClampZeroRange = 1024;

    /**
     * Disable keyboard modifiers altering tweak speed. Useful if you want to alter tweak speed yourself based on your own logic.
     *
     * <p>Definition: {@code 1 << 11}
     */
    public static final int NoSpeedTweaks = 2048;

    /**
     * DragScalarN(), SliderScalarN(): Draw R/G/B/A color markers on each component.
     *
     * <p>Definition: {@code 1 << 12}
     */
    public static final int ColorMarkers = 4096;

    /**
     * Definition: {@code ImGuiSliderFlags_ClampOnInput | ImGuiSliderFlags_ClampZeroRange}
     */
    public static final int AlwaysClamp = 1536;

    /**
     * [Internal] We treat using those bits as being potentially a 'float power' argument from legacy API (obsoleted 2020-08) that has got miscast to this enum, and will trigger an assert if needed.
     *
     * <p>Definition: {@code 0x7000000F}
     */
    public static final int InvalidMask_ = 1879048207;
}
