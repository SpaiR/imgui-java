package imgui.extension.texteditor;

import java.util.Objects;

public final class TextEditorCursorSelection implements Cloneable {
    public final TextEditorCursorPosition start = new TextEditorCursorPosition();
    public final TextEditorCursorPosition end = new TextEditorCursorPosition();

    public TextEditorCursorSelection() {
    }

    public TextEditorCursorSelection(final int startLine, final int startColumn, final int endLine, final int endColumn) {
        set(startLine, startColumn, endLine, endColumn);
    }

    public TextEditorCursorSelection(final TextEditorCursorSelection value) {
        set(value);
    }

    public TextEditorCursorSelection set(final int startLine, final int startColumn, final int endLine, final int endColumn) {
        start.set(startLine, startColumn);
        end.set(endLine, endColumn);
        return this;
    }

    public TextEditorCursorSelection set(final TextEditorCursorPosition start, final TextEditorCursorPosition end) {
        this.start.set(start);
        this.end.set(end);
        return this;
    }

    public TextEditorCursorSelection set(final TextEditorCursorSelection value) {
        return set(value.start, value.end);
    }

    @Override
    public String toString() {
        return "TextEditorCursorSelection{"
            + "start=" + start
            + ", end=" + end
            + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TextEditorCursorSelection that = (TextEditorCursorSelection) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public TextEditorCursorSelection clone() {
        return new TextEditorCursorSelection(this);
    }
}
