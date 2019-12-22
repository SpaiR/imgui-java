package imgui;

import java.util.Objects;

public final class ImBool {
    boolean[] data = new boolean[]{false};

    public ImBool() {
    }

    public ImBool(boolean value) {
        data[0] = value;
    }

    public boolean get() {
        return data[0];
    }

    public void set(boolean value) {
        data[0] = value;
    }

    @Override
    public String toString() {
        return String.valueOf(data[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImBool imBool = (ImBool) o;
        return data[0] == imBool.data[0];
    }

    @Override
    public int hashCode() {
        return Objects.hash(data[0]);
    }
}
