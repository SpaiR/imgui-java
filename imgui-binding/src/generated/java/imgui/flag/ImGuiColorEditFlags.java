package imgui.flag;


/**
 * Flags for ColorEdit3() / ColorEdit4() / ColorPicker3() / ColorPicker4() / ColorButton()
 */
public final class ImGuiColorEditFlags {
    private ImGuiColorEditFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * ColorEdit, ColorPicker, ColorButton: ignore Alpha component (will only read 3 components from the input pointer).
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoAlpha = 2;

    /**
     * ColorEdit: disable picker when clicking on color square.
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoPicker = 4;

    /**
     * ColorEdit: disable toggling options menu when right-clicking on inputs/small preview.
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int NoOptions = 8;

    /**
     * ColorEdit, ColorPicker: disable color square preview next to the inputs. (e.g. to show only the inputs)
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int NoSmallPreview = 16;

    /**
     * ColorEdit, ColorPicker: disable inputs sliders/text widgets (e.g. to show only the small preview color square).
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoInputs = 32;

    /**
     * ColorEdit, ColorPicker, ColorButton: disable tooltip when hovering the preview.
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoTooltip = 64;

    /**
     * ColorEdit, ColorPicker: disable display of inline text label (the label is still forwarded to the tooltip and picker).
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int NoLabel = 128;

    /**
     * ColorPicker: disable bigger color preview on right side of the picker, use small color square preview instead.
     *
     * <p>Definition: {@code 1 << 8}
     */
    public static final int NoSidePreview = 256;

    /**
     * ColorEdit: disable drag and drop target. ColorButton: disable drag and drop source.
     *
     * <p>Definition: {@code 1 << 9}
     */
    public static final int NoDragDrop = 512;

    /**
     * ColorButton: disable border (which is enforced by default)
     *
     * <p>Definition: {@code 1 << 10}
     */
    public static final int NoBorder = 1024;

    /**
     * ColorEdit, ColorPicker: show vertical alpha bar/gradient in picker.
     *
     * <p>Definition: {@code 1 << 16}
     */
    public static final int AlphaBar = 65536;

    /**
     * ColorEdit, ColorPicker, ColorButton: display preview as a transparent color over a checkerboard, instead of opaque.
     *
     * <p>Definition: {@code 1 << 17}
     */
    public static final int AlphaPreview = 131072;

    /**
     * ColorEdit, ColorPicker, ColorButton: display half opaque / half checkerboard, instead of opaque.
     *
     * <p>Definition: {@code 1 << 18}
     */
    public static final int AlphaPreviewHalf = 262144;

    /**
     * (WIP) ColorEdit: Currently only disable 0.0f..1.0f limits in RGBA edition (note: you probably want to use ImGuiColorEditFlags_Float flag as well).
     *
     * <p>Definition: {@code 1 << 19}
     */
    public static final int HDR = 524288;

    /**
     * [Display]    // ColorEdit: override _display_ type among RGB/HSV/Hex. ColorPicker: select any combination using one or more of RGB/HSV/Hex.
     *
     * <p>Definition: {@code 1 << 20}
     */
    public static final int DisplayRGB = 1048576;

    /**
     * [Display]    // "
     *
     * <p>Definition: {@code 1 << 21}
     */
    public static final int DisplayHSV = 2097152;

    /**
     * [Display]    // "
     *
     * <p>Definition: {@code 1 << 22}
     */
    public static final int DisplayHex = 4194304;

    /**
     * [DataType]   // ColorEdit, ColorPicker, ColorButton: _display_ values formatted as 0..255.
     *
     * <p>Definition: {@code 1 << 23}
     */
    public static final int Uint8 = 8388608;

    /**
     * [DataType]   // ColorEdit, ColorPicker, ColorButton: _display_ values formatted as 0.0f..1.0f floats instead of 0..255 integers. No round-trip of value via integers.
     *
     * <p>Definition: {@code 1 << 24}
     */
    public static final int Float = 16777216;

    /**
     * [Picker]     // ColorPicker: bar for Hue, rectangle for Sat/Value.
     *
     * <p>Definition: {@code 1 << 25}
     */
    public static final int PickerHueBar = 33554432;

    /**
     * [Picker]     // ColorPicker: wheel for Hue, triangle for Sat/Value.
     *
     * <p>Definition: {@code 1 << 26}
     */
    public static final int PickerHueWheel = 67108864;

    /**
     * [Input]      // ColorEdit, ColorPicker: input and output data in RGB format.
     *
     * <p>Definition: {@code 1 << 27}
     */
    public static final int InputRGB = 134217728;

    /**
     * [Input]      // ColorEdit, ColorPicker: input and output data in HSV format.
     *
     * <p>Definition: {@code 1 << 28}
     */
    public static final int InputHSV = 268435456;

    /**
     * Defaults Options. You can set application defaults using SetColorEditOptions(). The intent is that you probably don't want to override them in most of your calls. Let the user choose via the option menu and/or call SetColorEditOptions() once during startup.
     *
     * <p>Definition: {@code ImGuiColorEditFlags_Uint8 | ImGuiColorEditFlags_DisplayRGB | ImGuiColorEditFlags_InputRGB | ImGuiColorEditFlags_PickerHueBar}
     */
    public static final int DefaultOptions_ = 177209344;

    /**
     * [Internal] Masks
     *
     * <p>Definition: {@code ImGuiColorEditFlags_DisplayRGB | ImGuiColorEditFlags_DisplayHSV | ImGuiColorEditFlags_DisplayHex}
     */
    public static final int DisplayMask_ = 7340032;

    /**
     * [Internal] Masks
     *
     * <p>Definition: {@code ImGuiColorEditFlags_Uint8 | ImGuiColorEditFlags_Float}
     */
    public static final int DataTypeMask_ = 25165824;

    /**
     * [Internal] Masks
     *
     * <p>Definition: {@code ImGuiColorEditFlags_PickerHueWheel | ImGuiColorEditFlags_PickerHueBar}
     */
    public static final int PickerMask_ = 100663296;

    /**
     * [Internal] Masks
     *
     * <p>Definition: {@code ImGuiColorEditFlags_InputRGB | ImGuiColorEditFlags_InputHSV}
     */
    public static final int InputMask_ = 402653184;
}
