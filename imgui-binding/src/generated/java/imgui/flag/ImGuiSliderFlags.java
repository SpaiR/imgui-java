package imgui.flag;

public final class ImGuiSliderFlags {
    private ImGuiSliderFlags() {
    }

    public static final int None = 0;
    /**
     * Clamp value to min/max bounds when input manually with CTRL+Click. By default CTRL+Click allows going out of bounds.
     */
    public static final int AlwaysClamp = 1 << 4;
    /**
     * Make the widget logarithmic (linear otherwise).
     * Consider using ImGuiSliderFlags_NoRoundToFormat with this if using a format-string with small amount of digits.
     */
    public static final int Logarithmic = 1 << 5;
    /**
     * Disable rounding underlying value to match precision of the display format string (e.g. %.3f values are rounded to those 3 digits).
     */
    public static final int NoRoundToFormat = 1 << 6;
    /**
     * Disable CTRL+Click or Enter key allowing to input text directly into the widget
     */
    public static final int NoInput = 1 << 7;
}
