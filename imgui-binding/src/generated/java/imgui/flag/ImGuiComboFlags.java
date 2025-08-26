package imgui.flag;


/**
 * Flags for ImGui::BeginCombo()
 */
public final class ImGuiComboFlags {
    private ImGuiComboFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Align the popup toward the left by default
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int PopupAlignLeft = 1;

    /**
     * Max ~4 items visible. Tip: If you want your combo popup to be a specific size you can use SetNextWindowSizeConstraints() prior to calling BeginCombo()
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int HeightSmall = 2;

    /**
     * Max ~8 items visible (default)
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int HeightRegular = 4;

    /**
     * Max ~20 items visible
     *
     * <p>Definition: {@code 1 << 3}
     */
    public static final int HeightLarge = 8;

    /**
     * As many fitting items as possible
     *
     * <p>Definition: {@code 1 << 4}
     */
    public static final int HeightLargest = 16;

    /**
     * Display on the preview box without the square arrow button
     *
     * <p>Definition: {@code 1 << 5}
     */
    public static final int NoArrowButton = 32;

    /**
     * Display only a square arrow button
     *
     * <p>Definition: {@code 1 << 6}
     */
    public static final int NoPreview = 64;

    /**
     * Width dynamically calculated from preview contents
     *
     * <p>Definition: {@code 1 << 7}
     */
    public static final int WidthFitPreview = 128;

    /**
     * Definition: {@code ImGuiComboFlags_HeightSmall | ImGuiComboFlags_HeightRegular | ImGuiComboFlags_HeightLarge | ImGuiComboFlags_HeightLargest}
     */
    public static final int HeightMask_ = 30;
}
