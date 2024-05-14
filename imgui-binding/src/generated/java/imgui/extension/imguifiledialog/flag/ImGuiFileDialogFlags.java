package imgui.extension.imguifiledialog.flag;


public final class ImGuiFileDialogFlags {
    private ImGuiFileDialogFlags() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * show confirm to overwrite dialog
     *
     * <p>Definition: {@code (1 << 0)}
     */
    public static final int ConfirmOverwrite = 1;

    /**
     * dont show hidden file (file starting with a .)
     *
     * <p>Definition: {@code (1 << 1)}
     */
    public static final int DontShowHiddenFiles = 2;

    /**
     * disable the create directory button
     *
     * <p>Definition: {@code (1 << 2)}
     */
    public static final int DisableCreateDirectoryButton = 4;

    /**
     * hide column file type
     *
     * <p>Definition: {@code (1 << 3)}
     */
    public static final int HideColumnType = 8;

    /**
     * hide column file size
     *
     * <p>Definition: {@code (1 << 4)}
     */
    public static final int HideColumnSize = 16;

    /**
     * hide column file date
     *
     * <p>Definition: {@code (1 << 5)}
     */
    public static final int HideColumnDate = 32;

    /**
     * Definition: {@code ImGuiFileDialogFlags_ConfirmOverwrite}
     */
    public static final int Default = 1;
}
