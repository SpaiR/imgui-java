package imgui.extension.texteditor;

import java.util.Objects;

public final class TextEditorCoordinates implements Cloneable {
    public int mLine;
    public int mColumn;

    public TextEditorCoordinates() {
    }

    public TextEditorCoordinates(final int mLine, final int mColumn) {
        set(mLine, mColumn);
    }

    public TextEditorCoordinates(final TextEditorCoordinates value) {
        set(value.mLine, value.mColumn);
    }

    public TextEditorCoordinates set(final int mLine, final int mColumn) {
        this.mLine = mLine;
        this.mColumn = mColumn;
        return this;
    }

    public TextEditorCoordinates set(final TextEditorCoordinates value) {
        return set(value.mLine, value.mColumn);
    }

    public TextEditorCoordinates plus(final int mLine, final int mColumn) {
        this.mLine += mLine;
        this.mColumn += mColumn;
        return this;
    }

    public TextEditorCoordinates plus(final TextEditorCoordinates value) {
        return plus(value.mLine, value.mColumn);
    }

    public TextEditorCoordinates minus(final int mLine, final int mColumn) {
        this.mLine -= mLine;
        this.mColumn -= mColumn;
        return this;
    }

    public TextEditorCoordinates minus(final TextEditorCoordinates value) {
        return minus(value.mLine, value.mColumn);
    }

    public TextEditorCoordinates times(final int mLine, final int mColumn) {
        this.mLine *= mLine;
        this.mColumn *= mColumn;
        return this;
    }

    public TextEditorCoordinates times(final TextEditorCoordinates value) {
        return times(value.mLine, value.mColumn);
    }

    @Override
    public String toString() {
        return "TextEditorCoordinates{"
            + "mLine=" + mLine
            + ", mColumn=" + mColumn
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
        final TextEditorCoordinates textEditorCoordinates = (TextEditorCoordinates) o;
        return textEditorCoordinates.mLine == mLine && textEditorCoordinates.mColumn == mColumn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mLine, mColumn);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public TextEditorCoordinates clone() {
        return new TextEditorCoordinates(this);
    }
}
