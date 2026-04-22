package imgui.extension.texteditor;

import java.util.Objects;

public final class TextEditorCursorPosition implements Cloneable {
    public int line;
    public int column;

    public TextEditorCursorPosition() {
    }

    public TextEditorCursorPosition(final int line, final int column) {
        set(line, column);
    }

    public TextEditorCursorPosition(final TextEditorCursorPosition value) {
        set(value.line, value.column);
    }

    public TextEditorCursorPosition set(final int line, final int column) {
        this.line = line;
        this.column = column;
        return this;
    }

    public TextEditorCursorPosition set(final TextEditorCursorPosition value) {
        return set(value.line, value.column);
    }

    @Override
    public String toString() {
        return "TextEditorCursorPosition{"
            + "line=" + line
            + ", column=" + column
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
        final TextEditorCursorPosition that = (TextEditorCursorPosition) o;
        return line == that.line && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public TextEditorCursorPosition clone() {
        return new TextEditorCursorPosition(this);
    }
}
