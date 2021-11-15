package imgui.extension.imguifiledialog.flag;

public final class ImGuiFileDialogFlags {

    private ImGuiFileDialogFlags() {

    }

    public static final int None = 0;

    /**
     * show confirm to overwrite dialog
     */
    public static final int ConfirmOverwrite = (1 << 0);

    /**
     * dont show hidden file (file starting with a .)
     */
    public static final int DontShowHiddenFiles = (1 << 1);

    /**
     * disable the create directory button
     */
    public static final int DisableCreateDirectoryButton = (1 << 2);

    /**
     * hide column file type
     */
    public static final int HideColumnType = (1 << 3);

    /**
     * hide column file size
     */
    public static final int HideColumnSize = (1 << 4);

    /**
     * hide column file date
     */
    public static final int HideColumnDate = (1 << 5);
}
