package imgui.extension.nodeditor.flag;


public final class NodeEditorCanvasSizeMode {
    private NodeEditorCanvasSizeMode() {
    }

    /**
     * Previous view will be scaled to fit new view on Y axis
     */
    public static final int FitVerticalView = 0;

    /**
     * Previous view will be scaled to fit new view on X axis
     */
    public static final int FitHorizontalView = 1;

    /**
     * Previous view will be centered on new view
     */
    public static final int CenterOnly = 2;
}
