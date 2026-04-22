package imgui.extension.texteditor.flag;

public enum TextEditorScroll {
    alignTop(0),
    alignMiddle(1),
    alignBottom(2);

    public final int value;

    TextEditorScroll(final int value) {
        this.value = value;
    }
}
