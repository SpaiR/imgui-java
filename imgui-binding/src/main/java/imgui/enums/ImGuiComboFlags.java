package imgui.enums;

/**
 * Flags for ImGui::BeginCombo()
 */
public final class ImGuiComboFlags {
    private ImGuiComboFlags() {
    }

    public static final int None = 0;
    /**
     * Align the popup toward the left by default
     */
    public static final int PopupAlignLeft = 1;
    /**
     * Max ~4 items visible. Tip: If you want your combo popup to be a specific size you can use SetNextWindowSizeConstraints() prior to calling BeginCombo()
     */
    public static final int HeightSmall = 1 << 1;
    /**
     * Max ~8 items visible (default)
     */
    public static final int HeightRegular = 1 << 2;
    /**
     * Max ~20 items visible
     */
    public static final int HeightLarge = 1 << 3;
    /**
     * As many fitting items as possible
     */
    public static final int HeightLargest = 1 << 4;
    /**
     * Display on the preview box without the square arrow button
     */
    public static final int NoArrowButton = 1 << 5;
    /**
     * Display only a square arrow button
     */
    public static final int NoPreview = 1 << 6;
    public static final int HeightMask_ = HeightSmall | HeightRegular | HeightLarge | HeightLargest;
}
