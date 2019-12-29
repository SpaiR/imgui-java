package imgui.enums;

/**
 * Flags for ColorEdit3() / ColorEdit4() / ColorPicker3() / ColorPicker4() / ColorButton()
 */
public final class ImGuiColorEditFlags {
    private ImGuiColorEditFlags() {
    }

    public static final int None = 0;
    public static final int NoAlpha = 1 << 1;        //              // ColorEdit, ColorPicker, ColorButton: ignore Alpha component (will only read 3 components from the input pointer).
    public static final int NoPicker = 1 << 2;       //              // ColorEdit: disable picker when clicking on colored square.
    public static final int NoOptions = 1 << 3;      //              // ColorEdit: disable toggling options menu when right-clicking on inputs/small preview.
    public static final int NoSmallPreview = 1 << 4; //              // ColorEdit, ColorPicker: disable colored square preview next to the inputs. (e.g. to show only the inputs)
    public static final int NoInputs = 1 << 5;       //              // ColorEdit, ColorPicker: disable inputs sliders/text widgets (e.g. to show only the small preview colored square).
    public static final int NoTooltip = 1 << 6;      //              // ColorEdit, ColorPicker, ColorButton: disable tooltip when hovering the preview.
    public static final int NoLabel = 1 << 7;        //              // ColorEdit, ColorPicker: disable display of inline text label (the label is still forwarded to the tooltip and picker).
    public static final int NoSidePreview = 1 << 8;  //              // ColorPicker: disable bigger color preview on right side of the picker, use small colored square preview instead.
    public static final int NoDragDrop = 1 << 9;     //              // ColorEdit: disable drag and drop target. ColorButton: disable drag and drop source.

    // User Options (right-click on widget to change some of them).
    public static final int AlphaBar = 1 << 16;         //              // ColorEdit, ColorPicker: show vertical alpha bar/gradient in picker.
    public static final int AlphaPreview = 1 << 17;     //              // ColorEdit, ColorPicker, ColorButton: display preview as a transparent color over a checkerboard, instead of opaque.
    public static final int AlphaPreviewHalf = 1 << 18; //              // ColorEdit, ColorPicker, ColorButton: display half opaque / half checkerboard, instead of opaque.
    public static final int HDR = 1 << 19;              //              // (WIP) ColorEdit: Currently only disable 0.0f..1.0f limits in RGBA edition (note: you probably want to use ImGuiColorEditFlags_Float flag as well).
    public static final int DisplayRGB = 1 << 20;       // [Display]    // ColorEdit: override _display_ type among RGB/HSV/Hex. ColorPicker: select any combination using one or more of RGB/HSV/Hex.
    public static final int DisplayHSV = 1 << 21;       // [Display]    // "
    public static final int DisplayHex = 1 << 22;       // [Display]    // "
    public static final int Uint8 = 1 << 23;            // [DataType]   // ColorEdit, ColorPicker, ColorButton: _display_ values formatted as 0..255.
    public static final int Float = 1 << 24;            // [DataType]   // ColorEdit, ColorPicker, ColorButton: _display_ values formatted as 0.0f..1.0f floats instead of 0..255 integers. No round-trip of value via integers.
    public static final int PickerHueBar = 1 << 25;     // [Picker]     // ColorPicker: bar for Hue, rectangle for Sat/Value.
    public static final int PickerHueWheel = 1 << 26;   // [Picker]     // ColorPicker: wheel for Hue, triangle for Sat/Value.
    public static final int InputRGB = 1 << 27;         // [Input]      // ColorEdit, ColorPicker: input and output data in RGB format.
    public static final int InputHSV = 1 << 28;         // [Input]      // ColorEdit, ColorPicker: input and output data in HSV format.

    // Defaults Options. You can set application defaults using SetColorEditOptions(). The intent is that you probably don't want to
    // override them in most of your calls. Let the user choose via the option menu and/or call SetColorEditOptions() once during startup.
    public static final int OptionsDefault = Uint8 | DisplayRGB | InputRGB | PickerHueBar;
}
