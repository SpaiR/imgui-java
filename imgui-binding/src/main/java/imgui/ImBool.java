package imgui;

import java.util.Objects;

public final class ImBool {
    final boolean[] data = new boolean[]{false};

    public ImBool() {
    }

    public ImBool(final boolean value) {
        data[0] = value;
    }

    public boolean get() {
        return data[0];
    }

    public void set(final boolean value) {
        data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(data[0]);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImBool imBool = (ImBool) o;
        return data[0] == imBool.data[0];
    }

    @Override
    public int hashCode() {
        return Objects.hash(data[0]);
    }
}
